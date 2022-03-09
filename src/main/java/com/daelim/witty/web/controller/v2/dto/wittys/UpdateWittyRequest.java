package com.daelim.witty.web.controller.v2.dto.wittys;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UpdateWittyRequest {
    @NotBlank @NotNull
    private String content;

    @NotNull
    private List<String> tags;
}
