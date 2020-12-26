package com.guihe.platform.middle.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guihe.platform.core.domain.SysDepartment;
import com.guihe.platform.core.domain.SysDict;
import com.guihe.platform.dao.mapper.middle.SysDictMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author CHCC
 * @FileName SysDictService
 * @Date 2020/10/13 9:25 上午
 * @Version 1.0
 * @Description TODO
 */
@Service
public class SysDictService extends ServiceImpl<SysDictMapper, SysDict> {

    public IPage<SysDict> findList(SysDict sysDict) {
        QueryWrapper<SysDict> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(sysDict.getName())){
            wrapper.like("name",sysDict.getName());
        }
        if(StringUtils.isNotBlank(sysDict.getDictKey())){
            wrapper.eq("dict_key",sysDict.getDictKey());
        }
        wrapper.isNull("pid");
        wrapper.notIn("status",3);
        wrapper.orderByDesc("create_time");
        return baseMapper.selectPage(new Page<>(sysDict.getOffset(),sysDict.getLimmit()),wrapper);
    }

    public void findChildren(List<SysDict> records) {
        if(records == null || records.isEmpty()) return;
        records.forEach(record -> {
            QueryWrapper<SysDict> wrapper = new QueryWrapper<>();
            wrapper.eq("pid",record.getId());
            List<SysDict> selectList = baseMapper.selectList(wrapper);
            findChildren(selectList);
            record.setChildren(selectList);
        });
    }

    @Transactional
    public void saveDict(SysDict sysDict) {
        this.save(sysDict);
        List<SysDict> children = sysDict.getChildren();
        if(children != null && !children.isEmpty()){
            children.forEach(c -> {
                c.setPid(sysDict.getId());
                c.setCreateTime(sysDict.getCreateTime());
                c.setCreateBy(sysDict.getCreateBy());
                baseMapper.insert(c);
            });
        }
    }

    @Transactional
    public void updateDict(SysDict sysDict) {
        this.updateById(sysDict);
        QueryWrapper<SysDict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",sysDict.getId());
        baseMapper.delete(queryWrapper);
        List<SysDict> children = sysDict.getChildren();
        if(children != null && !children.isEmpty()){
            children.forEach(c -> {
                c.setPid(sysDict.getId());
                c.setCreateTime(sysDict.getCreateTime());
                c.setCreateBy(sysDict.getCreateBy());
                c.setUpdateTime(sysDict.getUpdateTime());
                c.setUpdateBy(sysDict.getUpdateBy());
                baseMapper.insert(c);
            });
        }
    }

    public SysDict findById(Integer id) {
        SysDict dict = getById(id);
        if(dict != null){
            QueryWrapper<SysDict> wrapper = new QueryWrapper<>();
            wrapper.eq("pid",id);
            List<SysDict> selectList = baseMapper.selectList(wrapper);
            findChildren(selectList);
            dict.setChildren(selectList);
        }
        return dict;
    }
}
