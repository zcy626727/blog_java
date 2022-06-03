package com.zcy.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zcy.blog.entity.Article;
import com.zcy.blog.entity.pojo.ArticleListQuery;
import com.zcy.blog.mapper.ArticleMapper;
import com.zcy.blog.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Autowired
    private RedisTemplate redisTemplate;

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


    /**
     * 根据文章id获取文章观看数
     * @param id
     * @return
     */
    @Override
    public String getWatchCountById(String id) {
        //观看数map
        BoundHashOperations watchMap = redisTemplate.boundHashOps(watchKey);
        Object o = watchMap.get(id);
        return o==null?"0":o.toString();
    }

    @Override
    public void incrementWatchCountById(String id) {
        //观看数map
        BoundHashOperations watchMap = redisTemplate.boundHashOps(watchKey);
        watchMap.increment(id,1);
    }



    /**
     * 根据文章id获取文章点赞数
     * @param id
     * @return
     */
    @Override
    public String getFavourCountById(String id) {
        //点赞数map
        BoundHashOperations favourMap = redisTemplate.boundHashOps(favourKey);
        Object o = favourMap.get(id);
        return o==null?"0":o.toString();
    }

    @Override
    public void incrementFavourCountById(String id) {
        //点赞数map
        BoundHashOperations favourMap = redisTemplate.boundHashOps(favourKey);
        favourMap.increment(id,1);
    }

    @Override
    public void decreaseFavourCountById(String id) {
        //点赞数map
        BoundHashOperations favourMap = redisTemplate.boundHashOps(favourKey);
        favourMap.increment(id,-1);
    }
}
