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
 * @since 2021-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ApiModel(value="IllustrationOfArticle对象", description="")
public class IllustrationOfArticle implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "插图id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "插图名")
    private String name;

    @ApiModelProperty(value = "文章id")
    private String aId;

    public IllustrationOfArticle(String name, String aId) {
        this.name = name;
        this.aId = aId;
    }
}
