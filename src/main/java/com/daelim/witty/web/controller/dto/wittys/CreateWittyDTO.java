package com.daelim.witty.web.controller.dto.wittys;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
