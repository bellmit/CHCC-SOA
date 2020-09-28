package com.guihe.platform.core.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.guihe.platform.core.form.BaseForm;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author CHCC
 * @FileName QrtzLogs
 * @Date 2020/9/28 4:44 下午
 * @Version 1.0
 * @Description TODO
 */
@Data
@ApiModel
@TableName("qrtz_logs")
public class QrtzLogs extends BaseForm {

    @TableId(type = IdType.AUTO)
    private Long id ;
    /** 定时器类名 */
    private String className ;
    /** 定时器方法名 */
    private String methodName ;
    /** 定时器名称 */
    private String jobName ;
    /** 定时器分组 */
    private String jobGroup ;
    /** 定时器说明 */
    private String description ;
    /** 总耗时 */
    private Double playTime ;
    /** 定时器执行时所需参数 */
    private String jobData ;
    /** 定时器执行结果:1成功 2失败 */
    private Integer status;
    /** 报错信息 */
    private String errMsg;
    /** 创建时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createdTime ;

    public QrtzLogs() {}

    public QrtzLogs(String className, String methodName, String jobName, String jobGroup, String description, Double playTime, String jobData, Integer status, String errMsg, Date createdTime) {
        this.className = className;
        this.methodName = methodName;
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.description = description;
        this.playTime = playTime;
        this.jobData = jobData;
        this.status = status;
        this.errMsg = errMsg;
        this.createdTime = createdTime;
    }
}
