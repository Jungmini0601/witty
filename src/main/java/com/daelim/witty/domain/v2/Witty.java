package com.daelim.witty.domain.v2;

import com.daelim.witty.web.controller.v2.dto.wittys.CreateWittyRequest;
import com.daelim.witty.web.controller.v2.dto.wittys.UpdateWittyRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity @ToString
public class Witty {

    @Column(name = "witty_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne //witty에 유저정보는 필수적으로 사용할것
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "witty", cascade = CascadeType.ALL) // 글이 지워지면 댓글을 다 지워준다.
    private List<Comment> comments = new ArrayList<>();

    private String content;

    @OneToMany(mappedBy = "witty", cascade = CascadeType.ALL)
    private List<Tag> tags = new ArrayList<>();

    private LocalDateTime createdDateTime;

    // 생성 메서드
    public static Witty createWitty(User user, CreateWittyRequest createWittyRequest) {
        Witty witty = new Witty();
        witty.user = user;
        witty.content = createWittyRequest.getContent();
        witty.createdDateTime = LocalDateTime.now();

        for(String tagName : createWittyRequest.getTags()) {
            Tag tag = Tag.createTag(tagName);
            witty.tags.add(tag);
            tag.setWitty(witty);
        }

        return witty;
    }

    public void update(UpdateWittyRequest updateWittyRequest) {
        this.content = updateWittyRequest.getContent();
        List<Tag> updateTags = new ArrayList<>();

        for(String tagName : updateWittyRequest.getTags()) {
            Tag tag = Tag.createTag(tagName);
            updateTags.add(tag);
            tag.setWitty(this);
        }
        this.tags = updateTags;
    }
}
