package com.daelim.witty.web.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserSignUpDTO {
    private String user_id;
    private String user_email;
    private String user_department;
    private String password;
}
