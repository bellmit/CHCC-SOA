package com.guihe.platform.core.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author CHCC
 * @FileName SysDepartment
 * @Date 2020/9/22 9:33 上午
 * @Version 1.0
 * @Description TODO
 */
@Data
@ApiModel
@TableName("sys_department")
public class SysDepartment extends BaseDomain {

    @TableId(type = IdType.AUTO)
    private Integer id ;
    /** 父id */
    @ApiModelProperty("父id")
    private Integer parentId ;
    /** 部门名称 */
    @ApiModelProperty("部门名称")
    private String name ;
    /** 状态1正常2禁用3删除 */
    @ApiModelProperty("状态1正常2禁用3删除")
    private Integer status ;

    @ApiModelProperty("类型1根部门 0非根部门")
    private Integer type;

    @TableField(exist = false)
    private List<SysDepartment> children;
}
