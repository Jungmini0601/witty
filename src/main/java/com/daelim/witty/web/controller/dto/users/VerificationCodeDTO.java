package com.daelim.witty.web.controller.dto.users;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Data
public class VerificationCodeDTO {

    @NotNull
    private String email;

    @NotNull
    private String key;
}
