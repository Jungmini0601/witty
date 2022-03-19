package com.daelim.witty.v2.domain;

import com.daelim.witty.v2.web.controller.dto.users.UserSignUpDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


// TODO JSONIGNORE 제거 어디서 해야 하는지 조사 필요.

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity @Getter
public class User {

    @Id @Column(name = "user_id")
    private String id;
    private String email;
    private String department; // 학부
    @JsonIgnore
    private String password;


    public static User createUserByDTO(UserSignUpDTO userSignUpDTO) {
        User user = new User();
        user.id = userSignUpDTO.getUser_id();
        user.email = userSignUpDTO.getUser_email();
        user.department = userSignUpDTO.getUser_department();
        user.password = userSignUpDTO.getPassword();
        return user;
    }
}
