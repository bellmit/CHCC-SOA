package com.guihe.platform.middle.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guihe.platform.core.domain.SysUser;
import com.guihe.platform.core.form.SysUserForm;
import com.guihe.platform.core.result.SysUserResult;
import com.guihe.platform.dao.mapper.middle.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Chengcheng on 2019/6/13.
 */
@Service
public class SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    public SysUser findByUsername(String username) {
        return sysUserMapper.findByUsername(username);
    }

    public IPage<SysUserResult> findList(SysUserForm sysUserForm) {
        return sysUserMapper.findList(new Page<>(sysUserForm.getOffset(),sysUserForm.getLimmit()),sysUserForm);
    }

    public void createUser(SysUser user) {
        sysUserMapper.insert(user);
    }

    public void updateUser(SysUser users) {
        sysUserMapper.updateById(users);
    }

    public void updateUser(List<SysUser> users) {
        users.forEach(user -> {
            sysUserMapper.updateById(user);
        });
    }

    public SysUser findInfo(Integer id) {
        return sysUserMapper.findInfo(id);
    }

    public void commitRole(Integer id, String ids) {
        String[] split = ids.split(",");
        if(split.length > 0){
            sysUserMapper.roleSubmit(id,split);
        }
    }

    public List<Integer> findRoles(Integer id) {
        return sysUserMapper.findRoles(id);
    }

}
