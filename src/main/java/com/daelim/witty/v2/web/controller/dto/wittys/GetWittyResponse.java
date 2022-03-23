package com.daelim.witty.v2.web.controller.dto.wittys;

import com.daelim.witty.v2.domain.Comment;
import com.daelim.witty.v2.domain.Tag;
import com.daelim.witty.v2.domain.User;
import com.daelim.witty.v2.domain.Witty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetWittyResponse {
    private Long id;
    private LocalDateTime createdDateTime;
    private User user;
    private String content;
    private List<Tag> tags = new ArrayList<>();
    private Integer likes;
    private int likeStatus;

    public static GetWittyResponse success(Witty witty, User loginUser) {
        GetWittyResponse response = new GetWittyResponse();
        response.id = witty.getId();
        response.createdDateTime = witty.getCreatedDateTime();
        response.user = witty.getUser();
        response.content = witty.getContent();
        response.tags = witty.getTags();
        response.likes = witty.getLikeList().size();
        response.likeStatus = (int) witty.getLikeList().stream()
                .filter(like -> like.getUser().getId().equals(loginUser.getId())).count();
        return response;
    }
}
