package com.daelim.witty.web.controller.v1.dto.users;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class UserLogInDTO {

    @Length(min = 1, max = 100)
    @NotEmpty @NotBlank
    private String user_id;

    @Length(min = 1, max = 45)
    @NotEmpty @NotBlank
    private String password;

}