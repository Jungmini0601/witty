package com.daelim.witty.v2.web.controller.dto.wittys;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateWittyRequest {
    @NotBlank @NotNull
    private String content;

    @NotNull
    private List<String> tags;
}
