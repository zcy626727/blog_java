package com.zcy.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author zcy
 * @since 2021-05-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ApiModel(value="CategoryOfArticle对象", description="")
public class CategoryOfArticle implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "文章类型id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "文章类型名")
    private String name;

    @ApiModelProperty(value = "?级类型")
    private Integer level;

    @ApiModelProperty(value = "0禁止/1启用")
    private Boolean started;

    @ApiModelProperty(value = "父类型id")
    private String fId;

    @ApiModelProperty(value = "包含的文章数")
    private int articleNum;

    @ApiModelProperty(value = "包含的文章数")
    private int publishedNum;


    public CategoryOfArticle(String name, Integer level, Boolean started, String fId) {
        this.name = name;
        this.level = level;
        this.started = started;
        this.fId = fId;
    }
}
