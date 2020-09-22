package com.guihe.platform.dao.mapper.middle;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guihe.platform.core.domain.SysDepartment;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CHCC
 * @FileName SysDepartmentMapper
 * @Date 2020/9/22 9:35 上午
 * @Version 1.0
 * @Description TODO
 */
public interface SysDepartmentMapper extends BaseMapper<SysDepartment> {

    List<Integer> findUserId(Integer id);

    void batchSaveUser(@Param("id") Integer id, @Param("list") ArrayList list);

    void clearUser(Integer id);
}
