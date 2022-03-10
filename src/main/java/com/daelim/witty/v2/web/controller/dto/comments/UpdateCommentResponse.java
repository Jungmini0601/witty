package com.daelim.witty.v2.web.controller.dto.comments;

import com.daelim.witty.v2.domain.Comment;
import com.daelim.witty.v2.domain.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateCommentResponse {

    private Long id;
    private String content;
    private LocalDateTime createDateTime;
    private String userId;

    public static UpdateCommentResponse success(Comment comment, User user) {
        UpdateCommentResponse response = new UpdateCommentResponse();
        response.id = comment.getId();
        response.content = comment.getContent();
        response.createDateTime = comment.getCreatedDateTime();
        response.userId = user.getId();
        return response;
    }
}
