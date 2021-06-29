package com.zcy.blog.entity.pojo;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
public class ArticleListQuery implements Serializable {

    private static final long serialVersionUID=1L;

    private String categoryId;
    private String title;
    private String createTimeFrom;
    private String createTimeTo;
    private List<String> tags;
    private Integer page;
    private Integer size;
    //true:只查发布的
    private String published;
    //delete：
    private String deleted;
    private Integer limit;
    private String order;


    public ArticleListQuery(String categoryId, String title, String createTimeFrom, String createTimeTo, List<String> tags, Integer page, Integer size) {
        this.categoryId = categoryId;
        this.title = title;
        this.createTimeFrom = createTimeFrom;
        this.createTimeTo = createTimeTo;
        this.tags = tags;
        this.page = page;
        this.size = size;
    }
}
