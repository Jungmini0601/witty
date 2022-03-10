package com.daelim.witty.v1.web.controller.dto.users;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class SendVerificationCodeDTO {

    @NotBlank @Pattern(regexp = ".+@email\\.daelim\\.ac\\.kr")
    private String email;
}