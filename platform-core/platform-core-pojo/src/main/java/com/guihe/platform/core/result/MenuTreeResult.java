package com.guihe.platform.core.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by Chengcheng on 2019/4/22.
 */
@Data
@ApiModel(description= "菜单树实体")
public class MenuTreeResult {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "路由地址")
    private String path;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "子级树")
    private List<MenuTreeResult> children;
}
