package com.guihe.platform.middle.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guihe.platform.core.domain.SysRole;
import com.guihe.platform.core.form.SysRoleForm;
import com.guihe.platform.core.result.SysRoleResult;
import com.guihe.platform.dao.mapper.middle.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Chengcheng on 2019/4/11.
 */
@Service
public class SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    public IPage<SysRoleResult> findList(SysRoleForm form) {
        return sysRoleMapper.findList(new Page<>(form.getOffset(),form.getLimmit()),form);
    }

    public void commitRole(SysRole sysRole) {
        if(sysRole.getId() == null){
            sysRole.setCreateTime(new Date());
            sysRoleMapper.insert(sysRole);
        }else{
            sysRole.setUpdateTime(new Date());
            sysRoleMapper.updateById(sysRole);
        }
    }

    public SysRole findInfo(Integer id) {
        return sysRoleMapper.selectById(id);
    }

    public SysRole findByUserId(Integer id) {
        List<Integer> list = sysRoleMapper.findByUserId(id);
        return list.isEmpty()?null:sysRoleMapper.selectById(list.get(0));
    }

    public void enable(SysRole sysRole) {
        sysRoleMapper.updateById(sysRole);
    }

    public void disable(SysRole sysRole) {
        sysRoleMapper.updateById(sysRole);
    }

    public void commitTree(Integer id, String ids) {
        sysRoleMapper.removeTree(id);
        String[] split = ids.split(",");
        if(split.length > 0){
            sysRoleMapper.commitTree(id,split);
        }
    }

    public List<Integer> findTree(Integer id) {
        return sysRoleMapper.findTree(id);
    }

    public List<SysRole> getRoles() {
        return sysRoleMapper.getRoles();
    }
}
