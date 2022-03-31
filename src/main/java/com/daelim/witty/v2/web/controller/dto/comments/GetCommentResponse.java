package com.daelim.witty.v2.web.controller.dto.comments;

import com.daelim.witty.v2.domain.Comment;
import com.daelim.witty.v2.domain.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetCommentResponse {
    private Long id;
    private User user;
    private String content;
    private LocalDateTime createdDateTime;
    private Integer likes;
    private long likeStatus;
    public static GetCommentResponse success(Comment comment, User loginUser) {
        GetCommentResponse response = new GetCommentResponse();
        response.id = comment.getId();
        response.user = comment.getUser();
        response.content = comment.getContent();
        response.createdDateTime = comment.getCreatedDateTime();
        response.likes = comment.getLikeList().size();
        response.likeStatus = comment.getLikeList().stream().filter(like -> like.getUser().getId().equals(loginUser.getId())).count();
        return response;
    }
}
