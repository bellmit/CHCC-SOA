package com.guihe.platform.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CHCC
 * @FileName QrtzJobDetails
 * @Date 2020/9/27 8:56 上午
 * @Version 1.0
 * @Description TODO
 */
@Data
@ApiModel
@TableName("QRTZ_JOB_DETAILS")
public class QrtzJobDetails {

    @TableField("SCHED_NAME")
    private String schedName;

    @TableField("JOB_NAME")
    private String jobName;

    @TableField("JOB_GROUP")
    private String jobGroup;

    @TableField("DESCRIPTION")
    private String description;

    @TableField("JOB_CLASS_NAME")
    private String jobClassName;

    @TableField("IS_DURABLE")
    private String isDurable;

    @TableField("IS_NONCONCURRENT")
    private String isNonconcurrent;

    @TableField("IS_UPDATE_DATA")
    private String isUpdateData;

    @TableField("REQUESTS_RECOVERY")
    private String requestsRecovery;

    @TableField("JOB_DATA")
    private String jobData;

    @TableField(exist = false)
    private String jobTime;

    @TableField(exist = false)
    private Map jobDataMap;

}
