package com.daelim.witty.web.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class UserSignUpDTO {

    @NotBlank
    private String user_id;
    @NotBlank
    private String user_email;
    @NotBlank
    private String user_department;
    @NotBlank
    private String password;
}
