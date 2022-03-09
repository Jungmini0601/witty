package com.daelim.witty.web.controller.v2.dto.wittys;

import com.daelim.witty.domain.v2.Tag;
import com.daelim.witty.domain.v2.User;
import com.daelim.witty.domain.v2.Witty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class UpdateWittyResponse {

    private Long id;
    private LocalDateTime createdDateTime;
    private User user;
    private String content;
    private List<Tag> tags = new ArrayList<>();

    public static UpdateWittyResponse success(Witty witty) {
        UpdateWittyResponse response = new UpdateWittyResponse();
        response.id = witty.getId();
        response.createdDateTime = witty.getCreatedDateTime();
        response.user = witty.getUser();
        response.content = witty.getContent();
        response.tags = witty.getTags();
        return response;
    }
}
