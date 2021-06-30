package com.zcy.blog.mapper;

import com.zcy.blog.config.redis.RedisCache;
import com.zcy.blog.entity.ArticleTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zcy
 * @since 2021-05-12
 */
@CacheNamespace(implementation = RedisCache.class,eviction = RedisCache.class)
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

}
