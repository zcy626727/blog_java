package com.zcy.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zcy.blog.entity.CategoryOfArticle;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zcy.blog.entity.pojo.CategoryListQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zcy
 * @since 2021-05-11
 */
public interface CategoryOfArticleService extends IService<CategoryOfArticle> {

    IPage<CategoryOfArticle> getCategoryListPage(Integer current, Integer size);

    List<CategoryOfArticle> getCategoryList(CategoryListQuery categoryListQuery);

}
