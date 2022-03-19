package com.daelim.witty.v2.web.controller.dto.wittys;

import com.daelim.witty.v2.domain.Comment;
import com.daelim.witty.v2.domain.Tag;
import com.daelim.witty.v2.domain.User;
import com.daelim.witty.v2.domain.Witty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class GetWittyResponse {
    private Long id;
    private LocalDateTime createdDateTime;
    private User user;
    private String content;
    private List<Tag> tags = new ArrayList<>();
    private Integer likes;

    public static GetWittyResponse success(Witty witty) {
        GetWittyResponse response = new GetWittyResponse();
        response.id = witty.getId();
        response.createdDateTime = witty.getCreatedDateTime();
        response.user = witty.getUser();
        response.content = witty.getContent();
        response.tags = witty.getTags();
        response.likes = witty.getLikeList().size();
        return response;
    }
}
