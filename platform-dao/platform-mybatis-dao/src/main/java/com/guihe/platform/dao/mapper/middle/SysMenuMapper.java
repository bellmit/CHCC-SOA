package com.guihe.platform.dao.mapper.middle;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guihe.platform.core.domain.SysMenu;
import com.guihe.platform.core.form.SysMenuForm;
import com.guihe.platform.core.result.SysMenuResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Chengcheng on 2019/4/11.
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<String> queryAllPerms(List<Integer> roleIds);

    IPage<SysMenuResult> findList(Page page, @Param("param") SysMenuForm form);

    List<SysMenu> findByParentId(Integer parentId);

    List<SysMenu> findMenu();

    void removeMenu(Integer id);

    List<SysMenuResult> findByParentIdAndRoleId(@Param("pid") Integer id, @Param("roleId") List<Integer> roleId);

    List<SysMenu> findByRoleId(@Param("roleId")List<Integer> ids);
}
