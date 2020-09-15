package com.guihe.platform.core.result;


import com.guihe.platform.core.domain.SysMenu;

/**
 * Created by Chengcheng on 2019/4/22.
 */
public class SysMenuResult extends SysMenu {

    public String getTypeStr(){
        return this.getType() == 1? "菜单":"功能";
    }

}
