package com.weiwensangsang.service.util.phone;

import com.alibaba.fastjson.JSON;
import com.github.kevinsawicki.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class PhoneUtil {

    private final static Logger log = LoggerFactory.getLogger(PhoneUtil.class);

    public static String getLocation(String phone) {
        String body = HttpRequest.get("http://sj.apidata.cn/", true, "mobile", phone).body();
        log.debug(body);
        Map maps = (Map) JSON.parse(body);
        if (!maps.get("message").toString().equals("success")) {
            return "未知";
        }
        String x = maps.get("data").toString();
        Map map2 = (Map) JSON.parse(x);
        return map2.get("city").toString();
    }


}
