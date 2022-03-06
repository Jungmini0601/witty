package com.daelim.witty.domain.v2;

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

    @ManyToOne(fetch = FetchType.LAZY) // 댓글을 보여 줄 때는 user 정보가 반드시 필요하다
    private User user;

    @ManyToOne(fetch = FetchType.LAZY) // 댓글을 보여 줄 때는 witty 정보가 반드시 필요하다
    private Witty witty;

    private LocalDateTime createdDateTime;
}
