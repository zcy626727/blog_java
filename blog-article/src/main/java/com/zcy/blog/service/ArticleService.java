package com.zcy.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zcy.blog.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zcy.blog.entity.pojo.ArticleListQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zcy
 * @since 2021-05-08
 */
public interface ArticleService extends IService<Article> {


    List<Article> getNewArticleList(ArticleListQuery articleListQuery);

    Article getArticleById(String id);

    IPage<Article> getArticleByListQuery(ArticleListQuery articleListQuery);
}
