package com.daelim.witty.v2.domain;


import com.daelim.witty.v2.web.controller.dto.comments.CreateCommentRequest;
import com.daelim.witty.v2.web.controller.dto.comments.UpdateCommentRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Comment {

    @Column(name = "comment_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne // 댓글을 보여 줄 때는 user 정보가 반드시 필요하다
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne // 댓글을 보여 줄 때는 witty 정보가 반드시 필요하다
    @JoinColumn(name = "witty_id")
    private Witty witty;

    private LocalDateTime createdDateTime;

    //연관 관계 메서드 생성
    public static Comment createComment(CreateCommentRequest createCommentRequest, Witty witty, User user) {
        Comment comment = new Comment();
        comment.content = createCommentRequest.getContent();
        comment.witty = witty;
        witty.getComments().add(comment);
        comment.user = user;
        comment.createdDateTime = LocalDateTime.now();
        return comment;
    }

    public void updateComment(UpdateCommentRequest updateCommentRequest) {
        this.content = updateCommentRequest.getContent();
    }
}
