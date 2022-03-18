package com.daelim.witty.v2.web.controller.dto.users;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FollowRequest {
    @NotNull
    private String toUserId;
}
