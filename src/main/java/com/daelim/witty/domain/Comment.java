package com.daelim.witty.domain;

import java.sql.Timestamp;

public class Comment {
    private Integer id;
    private Integer wittyId;
    private String userId;
    private String content;
    private Timestamp createdAt;
}
