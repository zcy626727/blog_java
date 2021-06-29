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
 * @since 2021-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ApiModel(value="ArticleTag对象", description="")
public class ArticleTag implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "文章标签映射id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "文章id")
    private String aId;

    @ApiModelProperty(value = "标签id")
    private String tId;

    public ArticleTag(String aId, String tId) {
        this.aId = aId;
        this.tId = tId;
    }

    public ArticleTag(String id, String aId, String tId) {
        this.id = id;
        this.aId = aId;
        this.tId = tId;
    }
}
