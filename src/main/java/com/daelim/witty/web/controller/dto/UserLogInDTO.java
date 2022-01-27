package com.daelim.witty.web.controller.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class UserLogInDTO {

    @Length(min = 1, max = 100)
    @NotEmpty @NotBlank
    private String user_id;

    @Length(min = 1, max = 45)
    @NotEmpty @NotBlank
    private String password;

}
