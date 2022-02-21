package com.daelim.witty.domain;

import com.daelim.witty.web.controller.dto.comments.CommentCreateDTO;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter @Setter
public class Comment {
    private Integer id;
    private Integer wittyId;
    private String userId;
    private String content;
    private Timestamp createdAt;

    public Comment() {
    }

    public Comment(CommentCreateDTO commentCreateDTO, User user) {
        wittyId = commentCreateDTO.getWittyId();
        content = commentCreateDTO.getContent();
        userId = user.getId();
        createdAt = Timestamp.valueOf(LocalDateTime.now());
    }
}
