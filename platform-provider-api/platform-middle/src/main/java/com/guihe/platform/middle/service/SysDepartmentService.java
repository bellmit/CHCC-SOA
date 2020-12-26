package com.guihe.platform.middle.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guihe.platform.core.domain.SysDepartment;
import com.guihe.platform.core.domain.SysUser;
import com.guihe.platform.dao.mapper.middle.SysDepartmentMapper;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CHCC
 * @FileName SysDepartmentService
 * @Date 2020/9/22 9:36 上午
 * @Version 1.0
 * @Description TODO
 */
@Service
public class SysDepartmentService extends ServiceImpl<SysDepartmentMapper, SysDepartment> {

    @Resource
    private SysUserService sysUserService;

    public IPage<SysDepartment> findList(SysDepartment department) {
        QueryWrapper<SysDepartment> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(department.getName())){
            wrapper.like("name",department.getName());
        }
        wrapper.eq("type",1);
        wrapper.notIn("status",3);
        wrapper.orderByDesc("create_time");
        return baseMapper.selectPage(new Page<>(department.getOffset(),department.getLimmit()),wrapper);
    }

    public void findChildren(List<SysDepartment> records) {
        if(records == null || records.isEmpty()) return;
        records.forEach(record -> {
            QueryWrapper<SysDepartment> wrapper = new QueryWrapper<>();
            wrapper.eq("parent_id",record.getId());
            List<SysDepartment> selectList = baseMapper.selectList(wrapper);
            findChildren(selectList);
            record.setChildren(selectList);
        });
    }

    public Map findUser(Integer id) {
        HashMap map = new HashMap();
        List<Integer> ids = baseMapper.findUserId(id);
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        List<SysUser> userList = sysUserService.list(wrapper);
        List<Transfer> list = new ArrayList<>(userList.size());
        userList.forEach(user -> {
            Transfer transfer = new Transfer();
            transfer.setKey(user.getId());
            transfer.setLabel(user.getNickname());
            list.add(transfer);
        });
        map.put("ids",ids);
        map.put("list",list);
        return map;
    }

    @Transactional
    public void commitUser(Integer id, ArrayList list) {
        baseMapper.clearUser(id);
        if(list.isEmpty()) return;
        baseMapper.batchSaveUser(id, list);
    }

    @Data
    class Transfer{
        private Integer key;
        private String label;
    }
}
