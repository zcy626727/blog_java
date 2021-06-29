package com.zcy.blog.controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zcy.blog.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zcy
 * @since 2021-05-08
 */
@RestController
@RequestMapping("/test")
public class test2Controller {
    @PostMapping("/post")
    public String reg(@RequestParam String param){
        return "服务端收到请求，传递的参数为："+param;
    }
}

