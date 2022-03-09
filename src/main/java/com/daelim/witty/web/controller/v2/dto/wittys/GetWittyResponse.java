package com.daelim.witty.web.controller.v2.dto.wittys;

import com.daelim.witty.domain.v2.Comment;
import com.daelim.witty.domain.v2.Tag;
import com.daelim.witty.domain.v2.User;
import com.daelim.witty.domain.v2.Witty;
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
    private List<Comment> comments = new ArrayList<>();
    private List<Tag> tags = new ArrayList<>();

    public static GetWittyResponse success(Witty witty) {
        GetWittyResponse response = new GetWittyResponse();
        response.id = witty.getId();
        response.createdDateTime = witty.getCreatedDateTime();
        response.user = witty.getUser();
        response.content = witty.getContent();
        response.tags = witty.getTags();
        response.comments = witty.getComments();
        return response;
    }
}
