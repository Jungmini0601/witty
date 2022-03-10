package com.daelim.witty.v1.web.controller.dto.wittys;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateWittyDTO {
    @NotBlank @NotNull
    private String content;

    @NotNull
    private List<String> tags;
}
