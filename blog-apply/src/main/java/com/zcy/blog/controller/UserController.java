package com.zcy.blog.controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zcy.blog.entity.User;
import com.zcy.blog.mapper.UserMapper;
import com.zcy.blog.service.UserService;
import com.zcy.blog.utils.Result;
import com.zcy.blog.utils.ResultArgs;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/user")
public class UserController {
    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    private Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd hh:mm:ss")
            .create();

    /**
     * @PathVariable 获取url参数，支持get请求
     * @RequestParam 获取url、body，支持POST/PUT/DELETE/PATCH
     * @RequestBody 获取json参数，支持POST/PUT/DELETE/PATCH
     */

    @GetMapping("/getInfo")
    public String getInfo(@RequestParam("token") String token){
        User user = userMapper.selectById(token);
        return gson.toJson(new Result(ResultArgs.SUCCESS_CODE,ResultArgs.SUCCESS_MSG).addKV("userInfo",user));
    }


}

