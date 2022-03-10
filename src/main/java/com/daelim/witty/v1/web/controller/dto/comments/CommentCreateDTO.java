package com.daelim.witty.v1.web.controller.dto.comments;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CommentCreateDTO {

    @NotNull
    private String content;

    @NotNull
    private Integer wittyId;
}
