package com.guihe.platform.core.form;

import lombok.Data;

/**
 * Created by Chengcheng on 2019/4/25.
 */
@Data
public class SysUserForm extends BaseForm {

    private String nickname;

    private Integer status;

    private String startTime;

    private String endTime;
}
