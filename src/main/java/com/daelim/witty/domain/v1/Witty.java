package com.daelim.witty.domain.v1;

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
