package com.guihe.platform.core.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Chengcheng on 2018/12/12.
 */
@Data
@TableName("sys_menu")
@ApiModel(description= "菜单实体")
public class SysMenu extends BaseDomain {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "父级id")
    private Integer parentId;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "菜单url")
    private String url;

    @ApiModelProperty(value = "菜单权限")
    private String perms;

    @ApiModelProperty(value = "菜单类型")
    private Integer type;

    @ApiModelProperty(value = "暂不使用")
    private String icon;

    @ApiModelProperty(value = "排序 1 2 3 4")
    private Integer orderNum;

}
