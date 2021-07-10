package com.zcy.blog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zcy.blog.config.redis.RedisCache;
import com.zcy.blog.entity.Article;
import com.zcy.blog.entity.IllustrationOfArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zcy
 * @since 2021-05-26
 */
@CacheNamespace(implementation = RedisCache.class,eviction = RedisCache.class)
public interface IllustrationOfArticleMapper extends BaseMapper<IllustrationOfArticle> {

}
