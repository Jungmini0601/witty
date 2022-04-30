package com.daelim.witty.v2.web.controller.dto.users;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UpdateUserRequest {
    @NotBlank
    private String password;
    @NotBlank
    private String introduction;
}
