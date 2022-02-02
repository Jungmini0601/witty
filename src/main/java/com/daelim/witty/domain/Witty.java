package com.daelim.witty.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class Witty {
    private Integer id;
    private String userId;
    private Timestamp createdAt;
    private String content;
}
