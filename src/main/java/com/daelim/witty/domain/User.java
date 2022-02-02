package com.daelim.witty.domain;

import com.daelim.witty.web.controller.dto.users.UserSignUpDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter @Setter @ToString
public class User {

    private String id;
    private String password;
    private String email;
    private String department;

    public User(UserSignUpDTO userSignUpDTO) {
        this.id = userSignUpDTO.getUser_id();
        this.password = userSignUpDTO.getPassword();
        this.email = userSignUpDTO.getUser_email();
        this.department = userSignUpDTO.getUser_department();
    }
}
