package com.daelim.witty.v1.web.controller.dto.users;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UserSignUpDTO {

    @NotBlank
    private String user_id;
    @NotBlank @Pattern(regexp = ".+@email\\.daelim\\.ac\\.kr")
    private String user_email;
    @NotBlank
    private String user_department;
    @NotBlank
    private String password;
}
