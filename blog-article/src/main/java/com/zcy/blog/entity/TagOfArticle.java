package com.zcy.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2021-05-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ApiModel(value="TagOfArticle对象", description="")
public class TagOfArticle implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "文章标签id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "文章标签名")
    private String name;

    public TagOfArticle(String name) {
        this.name = name;
    }


}
