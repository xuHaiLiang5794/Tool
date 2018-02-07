package com.xuhailiang5794.demo.controller;

import com.xuhailiang5794.common.result.OptResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 *
 * </pre>
 *
 * @author hailiang.xu
 * @version 1.0
 * @since 2018/2/7 10:28
 */
@Api(value = "BeforeDevelopmentController", description = "在这里展示一些在开发前必读的内容", position = 11)
@RestController
@RequestMapping
public class BeforeDevelopmentController {

    @ApiOperation(value = "返回json格式外层封装内容", notes = "返回的json格式外层封装内容", response = OptResult.class)
    @GetMapping("wrapperExampleValue")
    public Object wrapperExampleValue () {
        return new OptResult<>();
    }
}
