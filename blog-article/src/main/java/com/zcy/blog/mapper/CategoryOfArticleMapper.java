package com.zcy.blog.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zcy.blog.entity.Article;
import com.zcy.blog.entity.CategoryOfArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zcy.blog.entity.pojo.ArticleListQuery;
import com.zcy.blog.entity.pojo.CategoryListQuery;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zcy
 * @since 2021-05-11
 */
public interface CategoryOfArticleMapper extends BaseMapper<CategoryOfArticle> {

    IPage<CategoryOfArticle> getCategoryListPage(Page<CategoryOfArticle> page);

    List<CategoryOfArticle> getCategoryList(CategoryListQuery categoryListQuery);

}
