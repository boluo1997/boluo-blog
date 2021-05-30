import com.boluo.blog.utils.HttpUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.spark.sql.SparkSession;
import org.junit.Test;

import javax.ws.rs.HttpMethod;

public class HttpTest {

    private static final ObjectMapper mapper = new ObjectMapper();
    private final SparkSession spark = SparkSession.builder().master("local[*]")
            .config("spark.driver.host", "localhost")
            .getOrCreate();

    @Test
    // 调用接口测试
    public void apiTest1() {

        String path = "http://localhost:8848/user/getUserInfoList";

        ObjectNode requestNode = mapper.createObjectNode();
        requestNode.put("pageNo", "1");
        requestNode.put("count", "5");

        JsonNode jsonNode = HttpUtil.apiRequest(HttpMethod.POST,
                path, "", "", requestNode);
        System.out.println(jsonNode);
        // {
        //        "resultInfo": {
        //                "resultCode": "200",
        //                "resultMsg": "成功"
        //        },
        //        "data": [
        //                {
        //                        "userId": 1,
        //                        "userName": "boluo",
        //                        "password": "295111",
        //                        "nickName": "菠萝",
        //                        "email": "1109928059@qq.com",
        //                        "phone": null,
        //                        "headImgUrl": "http://boluo.com/boluo.png",
        //                        "createTime": "2021-05-30T03:42:08.000+00:00",
        //                        "isDelete": 1
        //                }
        //        ],
        //        "total": 0,
        //        "pageNo": 1,
        //        "count": 2,
        //        "search": null,
        //        "totalPage": 1
        //}
    }
}
