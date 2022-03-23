package com.daelim.witty.v2.web.controller.dto.users;

import lombok.Data;

import java.math.BigInteger;

@Data
public class GetFollowerResponse {
    private String id;
    private String email;
    private String department;
    private BigInteger followState; // 1이면 follow 상태

    public GetFollowerResponse(String id, String email, String department, BigInteger followState) {
        this.id = id;
        this.email = email;
        this.department = department;
        this.followState = followState;
    }
}
