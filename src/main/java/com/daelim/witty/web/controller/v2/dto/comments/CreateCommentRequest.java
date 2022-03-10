package com.daelim.witty.web.controller.v2.dto.comments;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateCommentRequest {
    @NotNull
    private String content;

    @NotNull
    private Long wittyId;
}
