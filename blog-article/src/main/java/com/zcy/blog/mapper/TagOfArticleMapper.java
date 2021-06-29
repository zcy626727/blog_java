package com.zcy.blog.mapper;

import com.zcy.blog.entity.TagOfArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface TagOfArticleMapper extends BaseMapper<TagOfArticle> {

    public List<TagOfArticle> getTagsByAId(String a_id);

}
