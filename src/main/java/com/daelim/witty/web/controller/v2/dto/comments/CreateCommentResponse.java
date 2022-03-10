package com.daelim.witty.web.controller.v2.dto.comments;

import com.daelim.witty.domain.v2.Comment;
import com.daelim.witty.domain.v2.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateCommentResponse {
    private Long id;
    private String content;
    private LocalDateTime createDateTime;
    private String userId;

    public static CreateCommentResponse success(Comment comment, User user) {
        CreateCommentResponse response = new CreateCommentResponse();
        response.id = comment.getId();
        response.content = comment.getContent();
        response.createDateTime = comment.getCreatedDateTime();
        response.userId = user.getId();

        return response;
    }
}
