package com.daelim.witty.web.controller.dto.wittys;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateWittyDTO {
    @NotBlank @NotNull
    private String content;
}
