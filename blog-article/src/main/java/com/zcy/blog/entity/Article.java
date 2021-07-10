package com.zcy.blog.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.sql.Blob;
import java.io.Serializable;
import java.util.List;

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
@ApiModel(value="Article对象", description="")
public class Article implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "文章id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章描述")
    private String des;

    @ApiModelProperty(value = "文章内容")
    private String content;

    @ApiModelProperty(value = "0草稿/1发布")
    private Boolean published;

    @ApiModelProperty(value = "0正常/1删除")
    private Boolean deleted;

    @ApiModelProperty(value = "创建时间")
//    @JSONField(format="yyyy-MM-dd HH:mm:ss") //fastjson设置时间格式
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "文章类型")
    private String cId;

    @TableField(exist = false)
    private String cName;

    @ApiModelProperty(value = "文章封面")
    private String avatar;

    @TableField(exist = false)
    private List<TagOfArticle> tags;

    @TableField(exist = false)
    private String avatarUrl;

    @TableField(exist = false)
    private String watchCount;

    @TableField(exist = false)
    private String favourCount;
}
