package com.guihe.platform.dao.mapper.middle;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guihe.platform.core.domain.SysUser;
import com.guihe.platform.core.form.SysUserForm;
import com.guihe.platform.core.result.SysUserResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Chengcheng on 2019/6/13.
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser findByUsername(String username);

    IPage<SysUserResult> findList(Page page,@Param("param") SysUserForm sysUserForm);

    SysUser findInfo(Integer id);

    void roleSubmit(@Param("userId") Integer userId, @Param("roleIds") String[] roleIds);

    List<Integer> findRoles(Integer id);

    IPage<SysUser> findUserTypeList(Page<Object> objectPage, @Param("type") Integer type);
}
