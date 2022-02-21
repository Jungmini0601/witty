package com.daelim.witty.web.controller.dto.comments;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommentCreateDTO {

    @NotNull
    private String content;

    @NotNull
    private Integer wittyId;
}
