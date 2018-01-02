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
    public static GodAnswer check(Long id) {
        String qq = id.toString() + id.toString() + "46";
        if (qq.length() <= 5) {
            qq += "84";
        }
        String host = "http://qqtest.market.alicloudapi.com";
        String path = "/qqTest";
        String method = "GET";
        String appcode = APICODE;
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("qq", qq);


        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            String s = "{\"grade" + EntityUtils.toString(response.getEntity()).split("grade")[1];
            s = s.substring(0, s.length() - 1);
            //获取response的body
            return GodAnswer.create(s);
        } catch (Exception e) {
            return new GodAnswer();
        }
    }


}
