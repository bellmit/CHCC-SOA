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
 * @FileName SysDict
 * @Date 2020/10/13 9:22 上午
 * @Version 1.0
 * @Description TODO 系统字典
 */
@ApiModel
@Data
@TableName("sys_dict")
public class SysDict extends BaseDomain {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("字典名称")
    private String name;

    @ApiModelProperty("字典key")
    private String dictKey;

    @ApiModelProperty("字典value")
    private String value;

    @ApiModelProperty("父字典id")
    private Integer pid;

    @ApiModelProperty("字典备注")
    private String content;

    @ApiModelProperty("1启用 2禁用 3删除")
    private Integer status;

    @TableField(exist = false)
    private List<SysDict> children;

}
