package com.zcy.blog.controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zcy.blog.entity.TagOfArticle;
import com.zcy.blog.service.TagOfArticleService;
import com.zcy.blog.utils.Result;
import com.zcy.blog.utils.ResultArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zcy
 * @since 2021-05-08
 */
@RestController
@RequestMapping("/tag")
public class TagOfArticleController {

    private Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd hh:mm:ss")
            .create();

    @Autowired
    private TagOfArticleService tagService;


    /**
     * 获取标签列表
     * @return
     */
    @GetMapping("/getTagList")
    public String getTagList(){
        return gson.toJson(new Result(ResultArgs.SUCCESS_CODE,ResultArgs.SUCCESS_MSG).addKV("tagList",tagService.list()));
    }

    /**
     * 保存标签
     * @param tagNames
     * @return
     */
    @PostMapping("/saveTags")
    public String saveTags(@RequestBody List<String> tagNames){
        List<TagOfArticle> tags = new ArrayList<>();
        for (String tagName:tagNames) {
            tags.add(new TagOfArticle(tagName));
        }
        boolean b = tagService.saveBatch(tags);

        if(b){
            return gson.toJson(new Result(ResultArgs.SUCCESS_CODE,ResultArgs.SUCCESS_MSG).addKV("tagList",tags));
        }else {
            return gson.toJson(new Result(ResultArgs.FAILURE_CODE,ResultArgs.FAILURE_MSG));

        }
    }

}

