package com.zcy.blog.mapper;

import com.zcy.blog.config.redis.RedisCache;
import com.zcy.blog.entity.TagOfArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
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
@CacheNamespace(implementation = RedisCache.class,eviction = RedisCache.class)
public interface TagOfArticleMapper extends BaseMapper<TagOfArticle> {

    public List<TagOfArticle> getTagsByAId(String a_id);

}
