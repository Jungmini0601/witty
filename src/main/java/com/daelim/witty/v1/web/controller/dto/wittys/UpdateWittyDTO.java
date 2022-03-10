package com.daelim.witty.v1.web.controller.dto.wittys;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateWittyDTO {
    @NotBlank @NotNull
    private String content;
}
