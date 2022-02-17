package com.daelim.witty.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class Comment {
    private Integer id;
    private Integer wittyId;
    private String userId;
    private String content;
    private Timestamp createdAt;
}
