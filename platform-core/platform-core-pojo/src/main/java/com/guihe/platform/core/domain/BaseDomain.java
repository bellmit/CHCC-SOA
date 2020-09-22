package com.guihe.platform.core.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Chengcheng on 2018/6/28.
 */
@Data
public class BaseDomain implements Serializable {

    protected static final long serialVersionUID = 1L;

    protected String createBy;

    protected String updateBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    protected Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    protected Date updateTime;

    @TableField(exist = false)
    @ApiModelProperty("当前页码")
    Integer offset = 1;

    @TableField(exist = false)
    @ApiModelProperty("每页条目")
    Integer limmit = 10;

}
