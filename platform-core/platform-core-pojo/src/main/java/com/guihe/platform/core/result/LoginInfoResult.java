package com.guihe.platform.core.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by Chengcheng on 2019/12/18.
 */
@Data
@ApiModel
public class LoginInfoResult {

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("用户手机号")
    private String mobile;

    @ApiModelProperty("0超级管理员 1平台管理员 2代理 3商户")
    private Integer type;

    @ApiModelProperty("权限菜单")
    private List<MenuTreeResult> menus;

}
