package com.guihe.platform.middle.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class SysUserService extends ServiceImpl<SysUserMapper,SysUser> {

    public SysUser findByUsername(String username) {
        return baseMapper.findByUsername(username);
    }

    public IPage<SysUserResult> findList(SysUserForm sysUserForm) {
        return baseMapper.findList(new Page<>(sysUserForm.getOffset(),sysUserForm.getLimmit()),sysUserForm);
    }

    public void createUser(SysUser user) {
        baseMapper.insert(user);
    }

    public void updateUser(SysUser users) {
        baseMapper.updateById(users);
    }

    public void updateUser(List<SysUser> users) {
        users.forEach(user -> {
            baseMapper.updateById(user);
        });
    }

    public SysUser findInfo(Integer id) {
        return baseMapper.findInfo(id);
    }

    public void commitRole(Integer id, String ids) {
        String[] split = ids.split(",");
        if(split.length > 0){
            baseMapper.roleSubmit(id,split);
        }
    }

    public List<Integer> findRoles(Integer id) {
        return baseMapper.findRoles(id);
    }

}
