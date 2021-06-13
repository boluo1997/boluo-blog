package com.boluo.blog.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.compress.utils.Charsets;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class ApiRequest {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(ApiRequest.class);
    // private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final Cache<String, JsonNode> requestCache = CacheBuilder.newBuilder().build();
    private static final CloseableHttpClient http = HttpClients.createDefault();

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
