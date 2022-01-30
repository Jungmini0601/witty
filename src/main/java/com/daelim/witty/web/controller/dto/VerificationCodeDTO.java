package com.daelim.witty.web.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VerificationCodeDTO {

    private String email;
    private String key;
}
