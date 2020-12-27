package com.guihe.platform.core.form;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by Chengcheng on 2019/4/17.
 */
public class BaseForm implements Serializable {

    @TableField(exist = false)
    @ApiModelProperty("当前页码")
    Integer offset = 1;

    @TableField(exist = false)
    @ApiModelProperty("每页条目")
    Integer limmit = 10;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimmit() {
        return limmit;
    }

    public void setLimmit(Integer limmit) {
        this.limmit = limmit;
    }

    public Integer getPageSize() {
        return limmit;
    }

    public void setPageSize(Integer pageSize) {
        this.limmit = pageSize;
    }

    public Integer getCurrent() {
        return offset;
    }

    public void setCurrent(Integer current) {
        this.offset = current;
    }
}