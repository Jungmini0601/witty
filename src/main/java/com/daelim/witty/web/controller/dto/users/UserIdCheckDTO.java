package com.daelim.witty.web.controller.dto.users;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserIdCheckDTO {

    @NotBlank @NotNull
    private String user_id;
}
