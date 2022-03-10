package com.daelim.witty.v2.web.controller.dto.comments;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateCommentRequest {
    @NotNull
    private String content;

    @NotNull
    private Long wittyId;
}
