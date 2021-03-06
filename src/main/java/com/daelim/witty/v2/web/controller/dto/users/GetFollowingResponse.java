package com.daelim.witty.v2.web.controller.dto.users;

import lombok.Data;

import java.math.BigInteger;

@Data
public class GetFollowingResponse {
    private String id;
    private String email;
    private String department;
    private String profileImgUrl;
    private BigInteger followState; // 1이면 follow 상태

    public GetFollowingResponse(String id, String email, String department,String profileImgUrl, BigInteger followState) {
        this.id = id;
        this.email = email;
        this.department = department;
        this.profileImgUrl = profileImgUrl;
        this.followState = followState;
    }
}
