package com.xuhailiang5794.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 *
 * </pre>
 *
 * @author hailiang.xu
 * @version 1.0
 * @since 2018/2/2 17:23
 */
@RestController
@RequestMapping("test")
public class TestController {
    @RequestMapping
    public String test() {
        return "test";
    }
}
