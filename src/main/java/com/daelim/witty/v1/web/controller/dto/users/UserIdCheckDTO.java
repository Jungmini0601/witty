package com.daelim.witty.v1.web.controller.dto.users;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserIdCheckDTO {

    @NotBlank @NotNull
    private String user_id;
}
