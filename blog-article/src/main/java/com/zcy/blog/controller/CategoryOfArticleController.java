package com.zcy.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zcy.blog.entity.Article;
import com.zcy.blog.entity.CategoryOfArticle;
import com.zcy.blog.entity.pojo.CategoryListQuery;
import com.zcy.blog.service.ArticleService;
import com.zcy.blog.service.CategoryOfArticleService;
import com.zcy.blog.utils.Result;
import com.zcy.blog.utils.ResultArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zcy
 * @since 2021-05-11
 */
@RestController
@RequestMapping("/category")
public class CategoryOfArticleController {

    private Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd hh:mm:ss")
            .create();

    @Autowired
    private CategoryOfArticleService categoryService;

    @Autowired
    private ArticleService articleService;

    @GetMapping("/getCategoryList")
    public String getCategoryList(@RequestParam(value = "CountCondition",required = false) String CountCondition){
        QueryWrapper<CategoryOfArticle> qw = new QueryWrapper<>();
        qw.eq("started",true);
        CategoryListQuery categoryListQuery = new CategoryListQuery();
        categoryListQuery.setCountCondition(CountCondition);

        List<CategoryOfArticle> categoryList = categoryService.getCategoryList(categoryListQuery);
        return gson.toJson(new Result(ResultArgs.SUCCESS_CODE,ResultArgs.SUCCESS_MSG).addKV("categoryList",categoryList));
    }

    @GetMapping("/getCategoryListPlus")
    public String getCategoryListPlus(@RequestParam("current") Integer current, @RequestParam("size") Integer size){
        IPage<CategoryOfArticle> categoryList = categoryService.getCategoryListPage(current, size);
        return gson.toJson(new Result(ResultArgs.SUCCESS_CODE,ResultArgs.SUCCESS_MSG)
                .addKV("categoryList",categoryList.getRecords()).addKV("total",categoryList.getTotal()));
    }


    @PostMapping("/saveCategory")
    public String addCategory(@RequestBody CategoryOfArticle categoryOfArticle){
        boolean b = categoryService.save(categoryOfArticle);
        if(b){
            return gson.toJson(new Result(ResultArgs.SUCCESS_CODE,"添加成功"));
        }
        return gson.toJson(new Result(ResultArgs.FAILURE_CODE,"添加失败,请检查用户名是否重复"));
    }

    @PutMapping("/updateCategory")
    public String updateCategory(@RequestBody CategoryOfArticle categoryOfArticle){
        boolean b = categoryService.updateById(categoryOfArticle);
        if(b){
            return gson.toJson(new Result(ResultArgs.SUCCESS_CODE,"修改成功"));
        }
        return gson.toJson(new Result(ResultArgs.FAILURE_CODE,"修改失败,请检查用户名是否重复"));
    }

    @DeleteMapping("/deleteCategory")
    public String deleteCategory(String id){
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        QueryWrapper<Article> eq = queryWrapper.eq("c_id", id);
        int count = articleService.count(eq);
        if(count>0){
            return gson.toJson(new Result(ResultArgs.FAILURE_CODE,"当前分类文章数不为零，无法删除"));
        }
        boolean b = categoryService.removeById(id);
        if(b){
            return gson.toJson(new Result(ResultArgs.SUCCESS_CODE,"删除成功"));
        }
        return gson.toJson(new Result(ResultArgs.FAILURE_CODE,"删除失败"));
    }
}

