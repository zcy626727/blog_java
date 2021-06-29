package com.zcy.blog.entity.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CategoryListQuery implements Serializable {

    private static final long serialVersionUID=1L;

    private String CountCondition;
}
