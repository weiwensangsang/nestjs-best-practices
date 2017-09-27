package com.weiwensangsang.web.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xiazhen on 2017/9/27.
 */
@Controller
public class AngularJsForwardController {
    @RequestMapping(value = "/**/{[path:[^\\.]*}")
    public String redirect() {
        return "forward:/";
    }
}
