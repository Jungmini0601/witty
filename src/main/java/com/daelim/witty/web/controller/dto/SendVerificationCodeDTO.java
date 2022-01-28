package com.daelim.witty.web.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class SendVerificationCodeDTO {

    @NotBlank
    private String email;
}