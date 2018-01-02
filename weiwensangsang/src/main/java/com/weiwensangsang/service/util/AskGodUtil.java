package com.weiwensangsang.service.util;

import com.weiwensangsang.service.util.weather.demo.util.HttpUtils;
import com.weiwensangsang.web.rest.vm.GodAnswer;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

import static com.weiwensangsang.config.Constants.APICODE;

public class AskGodUtil {
    public static void main(String[] args) {
        String host = "http://qqtest.market.alicloudapi.com";
        String path = "/qqTest";
        String method = "GET";
        String appcode = APICODE;
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("qq", "12345");


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
            String s = "{\"grade"+EntityUtils.toString(response.getEntity()).split("grade")[1];
            s = s.substring(0,s.length() - 1);
            //获取response的body
            System.out.println(GodAnswer.create(s).getGrade());
            System.out.println(GodAnswer.create(s).getAnalysis());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
