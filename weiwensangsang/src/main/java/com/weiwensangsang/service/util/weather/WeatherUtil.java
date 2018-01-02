package com.weiwensangsang.service.util.weather;

import com.weiwensangsang.service.util.weather.demo.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

import static com.weiwensangsang.config.Constants.APICODE;

public class WeatherUtil {
    public static String ask(String city) {
        String host = "http://jisutqybmf.market.alicloudapi.com";
        String path = "/weather/query";
        String method = "GET";
        String appcode = APICODE;
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("city", city);
        querys.put("citycode", "citycode");
        querys.put("cityid", "cityid");
        querys.put("ip", "ip");
        querys.put("location", "location");

        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            return EntityUtils.toString(response.getEntity());

        } catch (Exception e) {
            return "error";
        }
    }


}
