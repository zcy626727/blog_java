package com.zcy.blog.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zcy.blog.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zcy.blog.entity.pojo.ArticleListQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zcy
 * @since 2021-05-08
 */
public interface ArticleMapper extends BaseMapper<Article> {


    List<Article> getNewArticleList(ArticleListQuery articleListQuery);

    Article getArticleById(String id);

    IPage<Article> getArticleByListQuery(Page<Article> page, ArticleListQuery articleListQuery);
}

