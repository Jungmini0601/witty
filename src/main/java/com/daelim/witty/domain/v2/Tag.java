package com.daelim.witty.domain.v2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Tag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "tag_id")
    private Long id;

    private String name;

    @ManyToOne // 태그정보는 필수로 포함 할 것임
    @JoinColumn(name = "witty_id")
    @JsonIgnore
    private Witty witty;

    public static Tag createTag(String name) {
        Tag tag = new Tag();
        tag.name = name;
        return tag;
    }

    public void setWitty(Witty witty) {
        this.witty = witty;
    }
}
