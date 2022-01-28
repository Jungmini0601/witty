package com.daelim.witty.web.controller.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class UserIdCheckDTO {

    @NotBlank @NotNull
    private String user_id;
}
