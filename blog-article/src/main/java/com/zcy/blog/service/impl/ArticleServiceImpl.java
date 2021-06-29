package com.zcy.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zcy.blog.entity.Article;
import com.zcy.blog.entity.pojo.ArticleListQuery;
import com.zcy.blog.mapper.ArticleMapper;
import com.zcy.blog.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zcy
 * @since 2021-05-08
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired(required = false)
    private ArticleMapper articleMapper;

    @Override
    public List<Article> getNewArticleList(ArticleListQuery articleListQuery) {
        return articleMapper.getNewArticleList(articleListQuery);
    }

    @Override
    public Article getArticleById(String id) {
        return articleMapper.getArticleById(id);
    }

    @Override
    public IPage<Article> getArticleByListQuery(ArticleListQuery articleListQuery) {
        Page<Article> page = new Page<>(articleListQuery.getPage(), articleListQuery.getSize());

        return articleMapper.getArticleByListQuery(page,articleListQuery);
    }
}
