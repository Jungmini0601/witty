package com.daelim.witty.web.controller.dto.users;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class VerificationCodeDTO {

    @NotNull @Pattern(regexp = ".+@email\\.daelim\\.ac\\.kr")
    private String email;

    @NotNull
    private String key;
}
