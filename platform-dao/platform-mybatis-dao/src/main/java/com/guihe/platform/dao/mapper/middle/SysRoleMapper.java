package com.guihe.platform.dao.mapper.middle;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guihe.platform.core.domain.SysRole;
import com.guihe.platform.core.form.SysRoleForm;
import com.guihe.platform.core.result.SysRoleResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Chengcheng on 2019/4/11.
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<Integer> findByUserId(Integer userId);

    IPage<SysRoleResult> findList(Page page, @Param("param") SysRoleForm form);

    void commitTree(@Param("roleId") Integer id, @Param("treeIds") String[] split);

    List<Integer> findTree(Integer id);

    List<SysRole> getRoles();

    void removeTree(Integer id);
}
