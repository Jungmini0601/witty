package com.daelim.witty.domain.v2;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "witty_tag")
@Entity
public class WittyTag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "witty_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "witty_id")
    private Witty witty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tags;
}
