package com.guihe.platform.core.form;

import lombok.Data;

/**
 * Created by Chengcheng on 2019/5/20.
 */
@Data
public class LoginForm {

    private String username;

    private String password;

    private String code;

    private String randomString;

}
