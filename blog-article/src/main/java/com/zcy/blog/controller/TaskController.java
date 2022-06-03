package com.zcy.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zcy.blog.entity.Article;
import com.zcy.blog.entity.ArticleTag;
import com.zcy.blog.entity.IllustrationOfArticle;
import com.zcy.blog.mapper.ArticleMapper;
import com.zcy.blog.mapper.IllustrationOfArticleMapper;
import com.zcy.blog.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;


import java.util.List;

/**
 * 全局任务
 */
public class TaskController {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    IllustrationOfArticleMapper illustrationMapper;

    /**
     * 清理已删除的文章
     */
    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void clearArticle(){
        //获取全部已删除的文章
        QueryWrapper<Article> qw = new QueryWrapper<>();
        qw.eq("deleted",1);

        List<Article> articleList = articleMapper.selectList(qw);
        //删除对应的封面和插图
        for (Article article:articleList) {
            //删除头像
            String avatar = article.getAvatar();
            FileUtil.deleteArticleAvatar(avatar);
            //删除插图
            QueryWrapper<IllustrationOfArticle> qw1 = new QueryWrapper<>();
            qw1.eq("a_id", article.getId());
            List<IllustrationOfArticle> illustrationList = illustrationMapper.selectList(qw1);
            for (IllustrationOfArticle illustration:illustrationList) {
                String iname = illustration.getName();
                FileUtil.deleteArticleIllustration(iname);
            }
            illustrationMapper.delete(qw1);
        }

        articleMapper.delete(qw);
    }
}
