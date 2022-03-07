package com.daelim.witty.web.controller.v1.dto.comments;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CommentCreateDTO {

    @NotNull
    private String content;

    @NotNull
    private Integer wittyId;
}
