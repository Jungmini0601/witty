package com.daelim.witty.v2.web.controller.dto.comments;

import com.daelim.witty.v2.domain.Comment;
import com.daelim.witty.v2.domain.User;
import com.daelim.witty.v2.domain.Witty;
import com.daelim.witty.v2.web.controller.dto.wittys.GetWittyResponse;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetCommentResponse {
    private Long id;
    private User user;
    private String content;
    private LocalDateTime createdDateTime;

    public static GetCommentResponse success(Comment comment) {
        GetCommentResponse response = new GetCommentResponse();
        response.id = comment.getId();
        response.user = comment.getUser();
        response.content = comment.getContent();
        response.createdDateTime = comment.getCreatedDateTime();
        return response;
    }
}
