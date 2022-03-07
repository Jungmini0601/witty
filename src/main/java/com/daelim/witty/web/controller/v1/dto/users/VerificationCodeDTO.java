package com.daelim.witty.web.controller.v1.dto.users;

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
