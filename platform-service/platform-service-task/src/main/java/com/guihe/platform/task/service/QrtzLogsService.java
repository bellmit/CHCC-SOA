package com.guihe.platform.task.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guihe.platform.core.domain.QrtzLogs;
import com.guihe.platform.dao.mapper.task.QrtzLogsMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author CHCC
 * @FileName QrtzLogsService
 * @Date 2020/9/28 4:47 下午
 * @Version 1.0
 * @Description TODO
 */
@Service
public class QrtzLogsService extends ServiceImpl<QrtzLogsMapper, QrtzLogs> {

    public IPage<QrtzLogs> findList(QrtzLogs logs) {
        QueryWrapper<QrtzLogs> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(logs.getJobName())){
            wrapper.eq("job_name",logs.getJobName());
        }
        wrapper.orderByDesc("create_time");
        return baseMapper.selectPage(new Page(logs.getOffset(),logs.getLimmit()),wrapper);
    }
}
