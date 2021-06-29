package com.zcy.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zcy.blog.entity.CategoryOfArticle;
import com.zcy.blog.entity.pojo.CategoryListQuery;
import com.zcy.blog.mapper.CategoryOfArticleMapper;
import com.zcy.blog.service.CategoryOfArticleService;
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
 * @since 2021-05-11
 */
@Service
public class CategoryOfArticleServiceImpl extends ServiceImpl<CategoryOfArticleMapper, CategoryOfArticle> implements CategoryOfArticleService {

    @Autowired(required = false)
    private CategoryOfArticleMapper categoryMapper;

    @Override
    public IPage<CategoryOfArticle> getCategoryListPage(Integer current, Integer size) {
        Page<CategoryOfArticle> page = new Page<>(current, size);
        return categoryMapper.getCategoryListPage(page);
    }

    @Override
    public List<CategoryOfArticle> getCategoryList(CategoryListQuery categoryListQuery) {
        return categoryMapper.getCategoryList(categoryListQuery);
    }
}
