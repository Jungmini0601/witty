package com.daelim.witty.domain.v2;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Witty {

    @Column(name = "witty_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //witty에 유저정보는 필수적으로 사용할것
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "witty", cascade = CascadeType.ALL) // 글이 지워지면 댓글을 다 지워준다.
    private List<Comment> comments = new ArrayList<>();

    private String content;

    @OneToMany(mappedBy = "witty", cascade = CascadeType.ALL)
    private List<WittyTag> wittyTags = new ArrayList<>();

    private LocalDateTime createdDateTime;
}
