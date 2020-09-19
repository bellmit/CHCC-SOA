package com.guihe.platform.core.result;


import com.guihe.platform.core.domain.SysUser;

/**
 * Created by Chengcheng on 2019/4/25.
 */
public class SysUserResult extends SysUser {

    public String getStatusStr(){
        return this.getStatus() == 1? "启用": "禁用";
    }

}
