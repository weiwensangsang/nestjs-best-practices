package com.weiwensangsang.service.util.weather;


import com.weiwensangsang.service.util.weather.demo.util.HttpUtils;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class WeatherUtil {
    public static void main(String[] args) {
        String host = "http://jisutqybmf.market.alicloudapi.com";
        String path = "/weather/query";
        String method = "GET";
        String appcode = "93005e6bcf754ec1aa057be4883a8ff5";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("city", "北京");
        querys.put("citycode", "citycode");
        querys.put("cityid", "cityid");
        querys.put("ip", "ip");
        querys.put("location", "location");

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            //response.getEntity()
            //System.out.println(response.getEntity().toString());
           // 获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));

            JSONObject obj = JSONObject.fromObject(response.getEntity());
            System.out.println(obj.get("msg"));
               // return tC;

           // JSONObject.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
