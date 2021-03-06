package com.daelim.witty.v2.web.controller.dto.users;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class VerificationCodeDTO {

    @NotNull @Pattern(regexp = ".+@email\\.daelim\\.ac\\.kr")
    private String email;

    @NotNull
    private String key;
}
