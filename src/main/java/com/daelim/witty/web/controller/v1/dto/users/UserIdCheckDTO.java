package com.daelim.witty.web.controller.v1.dto.users;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserIdCheckDTO {

    @NotBlank @NotNull
    private String user_id;
}
