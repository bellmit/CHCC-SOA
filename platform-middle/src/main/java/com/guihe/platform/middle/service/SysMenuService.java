package com.guihe.platform.middle.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guihe.platform.core.domain.SysMenu;
import com.guihe.platform.core.form.SysMenuForm;
import com.guihe.platform.core.result.MenuTreeResult;
import com.guihe.platform.core.result.SysMenuResult;
import com.guihe.platform.dao.mapper.middle.SysMenuMapper;
import com.guihe.platform.dao.mapper.middle.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chengcheng on 2019/4/11.
 */
@Service
public class SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    public List<String> selectList() {
        List<String> permsList = new ArrayList<>();
        List<SysMenu> pojos = sysMenuMapper.selectList(null);
        permsList = new ArrayList<>(pojos.size());
        for(SysMenu menu : pojos){
            permsList.add(menu.getPerms());
        }
        return permsList;
    }

    public List<String> queryAllPerms(Integer userId) {
        if(userId != null){
            List<Integer> roleIds = sysRoleMapper.findByUserId(userId);
            List<String> list = sysMenuMapper.queryAllPerms(roleIds);
            return list;
        }
        return null;
    }

    public SysMenu detailMenu(Integer id) {
        return sysMenuMapper.selectById(id);
    }

    public void editMenu(SysMenu sysMenu) {
        sysMenuMapper.updateById(sysMenu);
    }

    public void addMenu(SysMenu sysMenu) {
        sysMenuMapper.insert(sysMenu);
    }

    public IPage<SysMenuResult> findList(SysMenuForm form) {
        return sysMenuMapper.findList(new Page(form.getOffset(),form.getLimmit()),form);
    }

    public List<MenuTreeResult> findTree() {
        return tree(0);
    }

    private List<MenuTreeResult> tree(Integer parentId){
        List<SysMenu> sysMenus = sysMenuMapper.findByParentId(parentId);
        List<MenuTreeResult> results = new ArrayList<>(sysMenus.size());
        if(!sysMenus.isEmpty()){
            sysMenus.forEach(menu -> {
                MenuTreeResult menuTreeResult = new MenuTreeResult();
                menuTreeResult.setId(menu.getId());
                menuTreeResult.setLabel(menu.getName());
                menuTreeResult.setName(menu.getName());
                menuTreeResult.setPath(menu.getUrl());
                menuTreeResult.setIcon(menu.getIcon());
                menuTreeResult.setChildren(tree(menu.getId()));
                results.add(menuTreeResult);
            });
        }
        return results;
    }

    public Object findMenu() {
        return sysMenuMapper.findMenu();
    }

    public List<SysMenu> findChildrenById(Integer id) {
        return sysMenuMapper.findByParentId(id);
    }

    @Transactional
    public void removeMenu(Integer id) {
        sysMenuMapper.deleteById(id);
        sysMenuMapper.removeMenu(id);
    }

    public List<SysMenu> indexMenu(Integer id) {
        List<Integer> ids = sysRoleMapper.findByUserId(id);
        List<SysMenu> list = sysMenuMapper.findByRoleId(ids);
        return list;
    }

    private List<MenuTreeResult> tree(Integer parentId, List<Integer> roleId){
        List<SysMenuResult> sysMenus = sysMenuMapper.findByParentIdAndRoleId(parentId, roleId);
        List<MenuTreeResult> results = new ArrayList<>(sysMenus.size());
        if(!sysMenus.isEmpty()){
            sysMenus.forEach(menu -> {
                MenuTreeResult menuTreeResult = new MenuTreeResult();
                menuTreeResult.setId(menu.getId());
                menuTreeResult.setName(menu.getName());
                menuTreeResult.setPath(menu.getUrl());
                menuTreeResult.setIcon(menu.getIcon());
                menuTreeResult.setChildren(tree(menu.getId(),roleId));
                results.add(menuTreeResult);
            });
        }
        return results;
    }

}
