package com.guihe.platform.core.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Chengcheng on 2018/12/12.
 */
@Data
@TableName("sys_user")
@ApiModel(description= "系统用户实体")
public class SysUser extends BaseDomain {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "头像")
    private String img;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "盐")
    private String salt;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "密钥")
    private String secret;

    @TableField(exist = false)
    @ApiModelProperty(value = "确认密码")
    private String newPassword;

}