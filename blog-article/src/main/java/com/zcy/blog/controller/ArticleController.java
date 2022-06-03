package com.zcy.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zcy.blog.entity.*;
import com.zcy.blog.entity.pojo.ArticleListQuery;
import com.zcy.blog.service.ArticleService;
import com.zcy.blog.service.ArticleTagService;
import com.zcy.blog.service.CategoryOfArticleService;
import com.zcy.blog.service.IllustrationOfArticleService;

import com.zcy.blog.utils.FileUtil;
import com.zcy.blog.utils.Result;
import com.zcy.blog.utils.ResultArgs;
import org.apache.velocity.runtime.directive.Foreach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private CategoryOfArticleService categoryService;

    @Autowired
    private IllustrationOfArticleService illustrationService;

    @Autowired
    RedisTemplate redisTemplate;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd hh:mm:ss")
            .create();

    /**
     * 保存或更新文章
     * @param article 文章详细信息
     * @return
     */
    @Transactional
    @PostMapping("/saveOrUpdate")
    public String saveOrUpdate(@RequestBody Article article){
        boolean b = articleService.saveOrUpdate(article);
        if(b){
            List<ArticleTag> articleTags = new ArrayList<>();
            for (TagOfArticle tag :article.getTags()) {
                articleTags.add(new ArticleTag(article.getId(),tag.getId()));
            }
            QueryWrapper<ArticleTag> qw = new QueryWrapper<>();
            qw.eq("a_id",article.getId());
            articleTagService.remove(qw);
            boolean ss;
            if(articleTags.size()!=0){
                ss = articleTagService.saveBatch(articleTags);
            }
            return gson.toJson(new Result(ResultArgs.SUCCESS_CODE,"保存成功").addKV("article",article));
        }
        return gson.toJson(new Result(ResultArgs.FAILURE_CODE,"保存失败"));
    }

    /**
     * 获取文章列表
     * @param articleListQuery 查询条件
     * @return
     */
    @PostMapping("/getArticleList")
    public String getArticleList(@RequestBody ArticleListQuery articleListQuery){
        IPage<Article> res = articleService.getArticleByListQuery(articleListQuery);
        long current = res.getCurrent();
        List<Article> articleList = res.getRecords();

        for (Article a :articleList) {
            a.setWatchCount(articleService.getWatchCountById(a.getId()));
            a.setFavourCount(articleService.getFavourCountById(a.getId()));
//            logger.info("注入文章观看和点赞数，文章："+articleService.getWatchCountById(a.getId())+"，点赞："+articleService.getFavourCountById(a.getId()));
        }


        long size = res.getSize();
        long total = res.getTotal();
        for (Article article : articleList){
            String avatar = article.getAvatar();
            if(avatar!="无"&&avatar!=null){
                article.setAvatarUrl(FileUtil.getArticleAvatarUrl()+avatar);
            }
        }
        int count = articleService.count();
        
        return gson.toJson(new Result(ResultArgs.SUCCESS_CODE,ResultArgs.SUCCESS_MSG)
                .addKV("articleList",articleList).addKV("total",count).addKV("total",total).addKV("size",size).addKV("current",current));
    }

    /**
     * 获取最新文章（更新文章）
     * @param articleListQuery 筛选条件
     * @return
     */
    @PostMapping("/getNewArticle")
    public String getNewArticle(@RequestBody ArticleListQuery articleListQuery){
        List<Article> articleList = articleService.getNewArticleList(articleListQuery);
        int count = articleService.count();
        return gson.toJson(new Result(ResultArgs.SUCCESS_CODE,ResultArgs.SUCCESS_MSG)
                .addKV("articleList",articleList));
    }

    /**
     * 上传封面
     * @param picture 图片数据
     * @param id 文章id
     * @return
     */
    @PostMapping("/uploadAvatar")
    public String uploadAvatar(@RequestParam("avatar") MultipartFile picture,@RequestParam("id") String id) {
        String fileName = FileUtil.saveArticleAvatar(picture);
        if(fileName!=null){
            Article article = new Article();
            article.setId(id);
            article.setAvatar(fileName);
            boolean b = articleService.updateById(article);
            if(b){
                return gson.toJson(new Result(ResultArgs.SUCCESS_CODE,"封面上传成功")
                        .addKV("name",fileName).addKV("url",FileUtil.getArticleAvatarUrl()+fileName));
            }
            FileUtil.deleteArticleAvatar(fileName);
        }
        return gson.toJson(new Result(ResultArgs.FAILURE_CODE,"封面上传失败"));
    }

    /**
     * 上传单个插图
     * @param picture
     * @param id
     * @return
     */
    @PostMapping("/uploadIllustration")
    public String uploadIllustration(@RequestParam("picture") MultipartFile picture,@RequestParam("id") String id) {
        String fileName = FileUtil.saveArticleIllustration(picture);
        if(id==null){
            return gson.toJson(new Result(ResultArgs.FAILURE_CODE,"id为空"));
        }
        //添加成功，记录图片和文章的关系，方便删除文章时删除其相关图片
        if(fileName!=null)
        {
            IllustrationOfArticle illustration = new IllustrationOfArticle(fileName, id);
            boolean b = illustrationService.saveOrUpdate(illustration);

            if(b){
                return gson.toJson(new Result(ResultArgs.SUCCESS_CODE,"插图上传成功")
                        .addKV("name",fileName).addKV("url",FileUtil.getArticleIllustrationUrl()+fileName));
            }
        }
        FileUtil.deleteArticleIllustration(fileName);
        return gson.toJson(new Result(ResultArgs.FAILURE_CODE,"插图上传失败"));
    }

    /**
     * 删除一个插图
     * @param id 文章id
     * @param name 图片名字
     * @return
     */
    @DeleteMapping("/removeIllustration")
    public String removeIllustration(@RequestParam("id") String id,@RequestParam("name") String name){
        boolean b1 = FileUtil.deleteArticleIllustration(name);
        QueryWrapper<IllustrationOfArticle> qw = new QueryWrapper<>();
        qw.eq("a_id",id).eq("name",name);
        boolean b = illustrationService.remove(qw);
        if(b&&b1){
            return gson.toJson(new Result(ResultArgs.SUCCESS_CODE,"图片移除成功"));
        }
        return gson.toJson(new Result(ResultArgs.FAILURE_CODE,"图片移除失败"));
    }

    /**
     * 移除封面
     * @param id 文章id
     * @param name 图片名字
     * @return
     */
    @DeleteMapping("/removeAvatar")
    public String removeAvatar(@RequestParam("id") String id,@RequestParam("name") String name){
        boolean b1 = FileUtil.deleteArticleAvatar(name);
        Article article = new Article();
        article.setId(id);
        article.setAvatar("无");
        boolean b = articleService.updateById(article);
        if(b&&b1){
            return gson.toJson(new Result(ResultArgs.SUCCESS_CODE,"图片移除成功"));
        }
        return gson.toJson(new Result(ResultArgs.FAILURE_CODE,"图片移除失败"));
    }

    /**
     * 获取文章详细信息
     * @param id 文章id
     * @return
     */
    @GetMapping("/getArticleById")
    public String getArticleById(@RequestParam("id") String id){
        Article article = articleService.getArticleById(id);
        String avatar = article.getAvatar();
        if(avatar!=null&&!avatar.equals("无")){
            article.setAvatarUrl(FileUtil.getArticleAvatarUrl()+avatar);
        }
        //设置观看数
        article.setWatchCount(articleService.getWatchCountById(article.getId()));
        if(article!=null){
            return gson.toJson(new Result(ResultArgs.SUCCESS_CODE,ResultArgs.SUCCESS_MSG).addKV("article",article));
        }
        return gson.toJson(new Result(ResultArgs.FAILURE_CODE,ResultArgs.FAILURE_MSG));
    }


    /**
     * 增加观看数
     * @param id
     * @return
     */
    @GetMapping("/incWatchCount")
    public String incWatchCount(@RequestParam("id") String id){
        articleService.incrementWatchCountById(id);
        logger.info("增加观看数成功");
        return "增加观看数成功";
    }

    /**
     * 增加点赞数
     * @param id
     * @return
     */
    @GetMapping("/incFavourCount")
    public String incFavourCount(@RequestParam("id") String id){
        articleService.incrementFavourCountById(id);
        return "增加点赞数成功";
    }

    /**
     * 更换文章状态(删除/发布)
     * @param article 文章信息
     * @return
     */
    @Transactional
    @PutMapping("/putStatus")
    public String putStatus(@RequestBody Article article){
        Article old_article = articleService.getArticleById(article.getId());
        if(article.getDeleted()!=null && old_article.getDeleted()!=article.getDeleted()){
            CategoryOfArticle category = categoryService.getById(old_article.getCId());//获取分类信息
            if(article.getDeleted()){//正常->删除（todo要顺便删除图片）
                category.setArticleNum(category.getArticleNum()-1);
                category.setPublishedNum(category.getPublishedNum()-1);
            }else {//删除->正常
                category.setArticleNum(category.getArticleNum()+1);
                category.setPublishedNum(category.getPublishedNum()+1);
            }
            categoryService.updateById(category);
        }
        if(article.getPublished()!=null && old_article.getPublished()!=article.getPublished()){
            CategoryOfArticle category = categoryService.getById(old_article.getCId());//获取分类信息
            if(article.getPublished()){//草稿->发布
                category.setPublishedNum(category.getPublishedNum()+1);
            }else {//发布->草稿
                category.setPublishedNum(category.getPublishedNum()-1);
            }
            categoryService.updateById(category);
        }
        boolean b = articleService.updateById(article);
        if(b){
            return gson.toJson(new Result(ResultArgs.SUCCESS_CODE,"更新成功")
                    .addKV("deleted",article.getDeleted()).addKV("published",article.getPublished()));
        }
        return gson.toJson(new Result(ResultArgs.FAILURE_CODE,"更新失败"));
    }
}

