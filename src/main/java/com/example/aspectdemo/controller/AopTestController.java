package com.example.aspectdemo.controller;

import com.example.aspectdemo.annotation.SysLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zhoutf
 * @Date 2019/3/20 9:53
 * @Description
 */
@RestController
public class AopTestController {
    @SysLog("aaaa")
    @GetMapping("/test")
    public String getTest(String name, Integer age) {

        return  name+age;
    }

}
