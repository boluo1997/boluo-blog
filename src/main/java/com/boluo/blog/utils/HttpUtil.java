package com.boluo.blog.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.*;
import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import com.google.common.util.concurrent.Uninterruptibles;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.StructType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.HttpMethod;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HttpUtil {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    // private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final Cache<String, JsonNode> requestCache = CacheBuilder.newBuilder().build();
    private static final CloseableHttpClient http = HttpClients.createDefault();

    private static String answer2key(JsonNode answer) {
        int id = answer.at("/queId").intValue();
        String title = answer.at("/queTitle").textValue();
        Preconditions.checkArgument(id >= 0);
        Preconditions.checkArgument(!Strings.isNullOrEmpty(title));
        return title;
    }

    public static JsonNode answer2value(JsonNode answer) {
        if (answer.isNull()) {
            return MissingNode.getInstance();
        } else if (answer.isArray()) {
            ObjectNode r = mapper.createObjectNode();
            Streams.stream(answer).forEach(i -> {
                r.set(answer2key(i), answer2value(i));
            });
            return r;
        }

        int type = answer.at("/queType").intValue();
        JsonNode values = answer.at("/values");
        switch (type) {
            case 1:/*描述文字*/
            case 2:/*单行文字*/
            case 3:/*多行文字*/
            case 4:/*时间*/
            case 6:/*邮箱*/
            case 7:/*手机*/
            case 9:/*链接*/
            case 10:/*单选*/
            case 11:/*下拉选择*/
            case 16:/*富文本*/
            case 19:/*数据关联*/ {
                Preconditions.checkArgument(answer.at("/values").size() == 1, answer);
                JsonNode v = answer.at("/values/0/value");
                Preconditions.checkArgument(v.isTextual());
                return v;
            }
            case 12:/*多选*/
            case 13:/*上传附件*/
            case 15:/*图片选择*/
            case 21:/*地址*/ {
                ArrayNode r = mapper.createArrayNode();
                Streams.stream(answer.at("/values"))
                        .map(i -> {
                            JsonNode v = i.at("/value");
                            Preconditions.checkArgument(v.isTextual());
                            return v;
                        })
                        .forEach(r::add);
                return r;
            }
            case 8: {
                // 数字
                Preconditions.checkArgument(values.size() == 1, answer);
                JsonNode v = answer.at("/values/0/value");
                Preconditions.checkArgument(v.isTextual());
                try {
                    // 整数
                    BigInteger integer = new BigInteger(v.textValue());
                    return LongNode.valueOf(integer.longValue());
                } catch (NumberFormatException ignored) {
                }
                try {
                    // 分数
                    BigDecimal f = new BigDecimal(v.textValue());
                    return DecimalNode.valueOf(f);
                } catch (NumberFormatException ignored) {
                }
                if (v.textValue().length() == 0) {
                    return NullNode.getInstance();
                }
                int queId = answer.at("/queId").intValue();
                if (queId == 0) {
                    // 编号允许是字符串
                    return v;
                }
                return v;
            }
            case 5: {
                // 人
                ArrayNode res = mapper.createArrayNode();
                Streams.stream(values)
                        .map(v -> {
                            int uid = v.at("/id").intValue();
                            Preconditions.checkArgument(uid > 0, "missing id");
                            String name = v.at("/value").textValue();
                            String email = v.at("/email").textValue();
                            String otherInfo = v.at("/otherInfo").textValue();
                            return mapper.createObjectNode()
                                    .put("uid", uid)
                                    .put("name", name)
                                    .put("email", email)
                                    .put("head", otherInfo);
                        })
                        .forEach(res::add);
                return res;
            }
            case 22:/*部门*/ {
                ArrayNode res = mapper.createArrayNode();
                Streams.stream(values)
                        .map(i -> {
                            int id = i.at("/id").intValue();
                            Preconditions.checkArgument(id > 0, "missing id");
                            String name = i.at("/value").textValue();
                            return mapper.createObjectNode()
                                    .put("id", id)
                                    .put("name", name);
                        })
                        .forEach(res::add);
                return res;
            }
            case 14: /*起止时间*/ {
                Preconditions.checkArgument(values.size() == 1, answer);
                JsonNode v = answer.at("/values/0/value");
                Preconditions.checkArgument(v.isTextual());
                String[] value_ = v.textValue().split("~");
                ArrayNode res = mapper.createArrayNode();
                Arrays.stream(value_).forEach(res::add);
                return res;
            }
            case 18: {
                // 表格
                ArrayNode res = mapper.createArrayNode();
                answer.at("/tableValues")
                        .forEach(i -> res.add(answer2value(i)));
                return res;
            }
            default:
                throw new UnsupportedOperationException(String.format("%d:%s", type, answer));
        }
    }

    public static Dataset<Row> load(String app, String token) {
        JsonNode form = form(app, token);
        ObjectNode args = mapper.createObjectNode()
                .put("pageSize", 1000)
                .put("pageNum", 1)
                .put("type", 8)
                .putNull("queryKey");
        args.withArray("queries");
        args.withArray("sorts");
        JsonNode data = apiRequest(HttpMethod.POST,
                String.format("https://api.ding.qingflow.com/app/%s/apply/filter", app),
                token, null, args);
        Preconditions.checkArgument(data.at("/errCode").intValue() == 0);
        int pageAmount = data.at("/result/pageAmount").asInt();
        Stream<JsonNode> str = Stream.iterate(2, i -> i + 1)
                .limit(Math.max(pageAmount - 1, 0))
                .map(i -> {
                    args.put("pageNum", i);
                    return apiRequest(HttpMethod.POST,
                            String.format("https://api.ding.qingflow.com/app/%s/apply/filter", app),
                            token, null, args);
                });
        List<String> list = Stream.concat(Stream.of(data), str)
                .flatMap(d -> {
                    Preconditions.checkArgument(d.at("/errCode").intValue() == 0);
                    return Streams.stream(d.at("/result/result"));
                })
                .map(d -> answer2value(answerMixForm(d.at("/answers"), form.at("/questionBaseInfos"))))
                .map(JsonNode::toString)
                .collect(Collectors.toList());
        if (list.isEmpty()) {
            StructType schema = Streams.stream(form.at("/questionBaseInfos"))
                    .reduce(new StructType(), (s, i) -> {
                        switch (i.at("/queType").intValue()) {
                            case 8:
                                return s.add(i.at("/queTitle").textValue(), "double");
                            default:
                                return s.add(i.at("/queTitle").textValue(), "string");
                        }
                    }, (a, b) -> a);
            return SparkSession.active().createDataFrame(ImmutableList.of(), schema);
        }
        Dataset<String> ds = SparkSession.active().createDataset(list, Encoders.STRING());
        return SparkSession.active().read().json(ds);
    }

    // 导出到轻流
    public static void replace(Dataset<Row> ds, String app, String token) {
        StructType schema = ds.schema();
        // 获取应用字段
        JsonNode form = form(app, token);
        List<JsonNode> fields = Streams.stream(form.at("/questionBaseInfos"))
                .filter(i -> Arrays.asList(schema.fieldNames()).contains(i.at("/queTitle").asText()))
                .collect(Collectors.toList());
        // 获取成员
        Optional<JsonNode> creator = user("菠萝Robot", token);
        String userId = creator.map(i -> i.at("/userId").asText()).orElse(null);

        ds.toLocalIterator().forEachRemaining(row -> {
            ObjectNode args = mapper.createObjectNode();
            answers(fields, row, token)
                    .forEach(args.withArray("answers")::add);
            JsonNode req = apiRequest(HttpMethod.POST, String.format("https://api.ding.qingflow.com/app/%s/apply", app), token, userId, args);
            String requestId = req.at("/result/requestId").textValue();
            Preconditions.checkArgument(!Strings.isNullOrEmpty(requestId), "MISSING requestId");
            // 等待
            while (true) {
                Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(2));
//				JsonNode reqWait = apiRequest(HttpMethod.GET, String.format("https://api.ding.qingflow.com/operation/%s", requestId), token, userId, null);
//				int errorCode = reqWait.at("/errorCode").intValue();
//				String errMsg = reqWait.at("/message").textValue();
//				Preconditions.checkArgument(errorCode == 0, String.format("[%d]%s", errorCode, errMsg));
//				if (!reqWait.at("/result").isNull()) {
//					break;
//				}
                break;
            }
        });
    }

    // 导出到轻流
    public static void replace(Dataset<Row> df, String app, String key, String token) {
        StructType schema = df.schema();
        // 获取应用字段
        JsonNode form = form(app, token);
        List<JsonNode> fields = Streams.stream(form.at("/questionBaseInfos"))
                .filter(i -> Arrays.asList(schema.fieldNames()).contains(i.at("/queTitle").asText()))
                .collect(Collectors.toList());
        // 获取成员
        Optional<JsonNode> creator = user("菠萝Robot", token);
        String userId = creator.map(i -> i.at("/userId").asText()).orElse(null);

        List<String> keySet = ImmutableList.of(key);
        String expr = keySet.stream()
                .map(i -> String.format("t1.`%s`=t2.`%s`", i, i))
                .collect(Collectors.joining(" and "));
        String where = Arrays.stream(schema.fieldNames())
                .map(i -> String.format("t1.`%s`!=t2.`%s`", i, i))
                .collect(Collectors.joining(" or "));
        //Buffer<String> col = JavaConverters.asScalaBufferConverter(keySet).asScala();
        Dataset<Row> exists = load(app, token);
        df = df.as("t1").join(exists.as("t2"), functions.expr(expr), "left")
                .where(where)
                .selectExpr("t1.*");
        //df.show();

        df.toLocalIterator().forEachRemaining(row -> {
            ObjectNode args = mapper.createObjectNode();
            answers(fields, row, token)
                    .forEach(args.withArray("answers")::add);
            JsonNode req = apiRequest(HttpMethod.POST, String.format("https://api.ding.qingflow.com/app/%s/apply", app), token, userId, args);
            String requestId = req.at("/result/requestId").textValue();
            Preconditions.checkArgument(!Strings.isNullOrEmpty(requestId), "MISSING requestId");

            // 等待
            while (true) {
                Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(2));
//				JsonNode reqWait = apiRequest(HttpMethod.GET, String.format("https://api.ding.qingflow.com/operation/%s", requestId), token, userId, null);
//				int errorCode = reqWait.at("/errorCode").intValue();
//				String errMsg = reqWait.at("/message").textValue();
//				Preconditions.checkArgument(errorCode == 0, String.format("[%d]%s", errorCode, errMsg));
//				if (!reqWait.at("/result").isNull()) {
//					break;
//				}
                break;
            }
        });
    }

    private static Optional<JsonNode> user(String name, String token) {
        JsonNode user = requestCache.asMap().computeIfAbsent("https://api.ding.qingflow.com/department/1/user?fetchChild=true", uri -> {
            JsonNode r = apiRequest(HttpMethod.GET, uri, token, null, null);
            Preconditions.checkArgument(r.path("errCode").intValue() == 0, r.path("errMsg").asText());
            return r.at("/result/userList");
        });
        return Streams.stream(user)
                .filter(i -> i.at("/name").asText().equals(name))
                .findAny();
    }

    private static JsonNode form(String app, String token) {
        return requestCache.asMap().computeIfAbsent(String.format("https://api.ding.qingflow.com/app/%s/form", app), uri -> {
            JsonNode form = apiRequest(HttpMethod.GET, uri, token, null, null);
            Preconditions.checkArgument(form.at("/errCode").asInt(500) == 0, form.at("/errMsg").asText());
            return form.at("/result");
        });
    }

    private static JsonNode answerMixForm(JsonNode answer, JsonNode form) {
        Map<Integer, ObjectNode> form_ = Streams.stream(form)
                .flatMap(i -> Stream.concat(Stream.of(i), Streams.stream(i.at("/subQuestionBaseInfos"))))
                .collect(Collectors.toMap(i -> i.at("/queId").intValue(), i -> (ObjectNode) i));
        answer.forEach(i -> {
            int id = i.at("/queId").intValue();
            ObjectNode f = form_.get(id);
            ((ObjectNode) i).setAll(f);
        });
        return answer;
    }

    private static Stream<ObjectNode> answers(Iterable<JsonNode> form, Row p, String token) {
        return Streams.stream(form)
                .map(f -> {
                    int queId = f.at("/queId").intValue();
                    if (queId == 0) return null;
                    String queTitle = f.at("/queTitle").textValue();
                    int queType = f.at("/queType").intValue();
                    Object value = p.getAs(queTitle);
                    if (Objects.isNull(value)) return null;
                    ObjectNode r = mapper.createObjectNode()
                            .put("queId", queId);
                    switch (queType) {
                        default:
                            logger.warn("type={}", queType);
                        case 2:    /*单行文字*/
                        case 3:
                        case 4:
                        case 8:
                        case 10:
                        case 16:
                        case 19: {
                            if (value.getClass().isAssignableFrom(String.class)) {
                                r.withArray("values").addObject()
                                        .put("value", (String) value);
                            } else if (value.getClass().isAssignableFrom(Long.class)
                                    || value.getClass().isAssignableFrom(Double.class)
                                    || value.getClass().isAssignableFrom(BigDecimal.class)) {
                                r.withArray("values").addObject()
                                        .putPOJO("value", value);
                            } else if (value.getClass().isAssignableFrom(Date.class)) {
                                r.withArray("values").addObject()
                                        .put("value", value.toString());
                            } else {
                                r.withArray("values").addObject()
                                        .put("value", (String) value);
                            }
                            return r;
                        }
                        case 5:    /*成员*/ {
                            Optional<JsonNode> creator = user((String) value, token);
                            creator.ifPresent(u -> {
                                r.withArray("values").addObject()
                                        .put("id", u.at("/optionId").asInt())
                                        .put("value", (String) value);
                            });
                            return r;
                        }
                        case 18: {
                            throw new UnsupportedOperationException(queTitle);
						/*JsonNode format = f.at("/subQuestionBaseInfos");
						Map<String, String[]> values = p.keySet().stream()
								.filter(i -> i.startsWith(queTitle + "."))
								.collect(Collectors.toMap(i -> {
									return i.replace(queTitle + ".", "");
								}, i -> {
									return Optional.ofNullable(p.getFirst(i))
											.map(v -> v.split(" "))
											.orElseGet(() -> new String[0]);
								}));
						int rowTotal = values.values().stream().mapToInt(i -> i.length).max().orElse(0);
						ObjectNode r = mapper.createObjectNode()
								.put("queId", queId);
						ArrayNode tableValues = r.withArray("tableValues");
						for (int i = 0; i < rowTotal; ++i) {
							int rowNum = i;
							MultiValueMap<String, String> row = new LinkedMultiValueMap<>();
							values.forEach((k, v) -> {
								if (rowNum < v.length) {
									row.add(k, v[rowNum]);
								}
							});
							answers(format, row)
									.forEach(tableValues.addArray()::add);
						}
						return r;*/
                        }
                        case 12:    /*多项选择*/
                        case 13:
                        case 21: /*地址*/ {
						/*return value(queId, () -> Optional.ofNullable(p.getFirst(queTitle))
								.map(v -> v.split(" "))
								.map(Arrays::stream)
								.orElseGet(Stream::of)
						);*/
                            throw new UnsupportedOperationException(queTitle);
                        }
                    }
                })
                .filter(Objects::nonNull)
                .filter(i -> i.at("/tableValues").size() + i.at("/values").size() > 0);
    }

    public static JsonNode apiRequest(String method, String uri, String token, String userId, JsonNode body) {
        RequestBuilder requestBuilder = RequestBuilder.create(method)
                .setUri(uri)
                .setHeader("accessToken", token)
                .setHeader("Content-Type", "application/json");
        Optional.ofNullable(body).ifPresent(i -> {
            requestBuilder.setEntity(new StringEntity(body.toString(), Charsets.UTF_8));
        });
        Optional.ofNullable(userId).ifPresent(i -> {
            requestBuilder.setHeader("userId", userId);
        });

        try (CloseableHttpResponse response = http.execute(requestBuilder.build());
             InputStream is = response.getEntity().getContent()) {
            logger.info("{} {}:{}", method, uri, body);
            Preconditions.checkArgument(response.getStatusLine().getStatusCode() == 200, response.getStatusLine());
            return mapper.readTree(is);
        } catch (IOException e) {
            throw new RuntimeException(String.format("%s %s:%s", method, uri, body), e);
        }
    }
}
