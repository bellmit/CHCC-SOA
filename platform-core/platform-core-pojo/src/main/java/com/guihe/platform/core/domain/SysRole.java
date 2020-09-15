package com.guihe.platform.core.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Chengcheng on 2018/12/13.
 */
@Data
@TableName("sys_role")
@ApiModel(description= "系统权限实体")
public class SysRole extends BaseDomain {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "权限代码")
    private String code;

    @ApiModelProperty(value = "状态")
    private Integer status;

}
