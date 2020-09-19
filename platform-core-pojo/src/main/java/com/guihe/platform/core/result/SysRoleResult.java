package com.guihe.platform.core.result;


import com.guihe.platform.core.domain.SysRole;

/**
 * Created by Chengcheng on 2019/4/25.
 */
public class SysRoleResult extends SysRole {

    public String getStatusStr(){
        if(this.getStatus() == null) return null;
        return this.getStatus() == 1? "启用": "禁用";
    }

}
