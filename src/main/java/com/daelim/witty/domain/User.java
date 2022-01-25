package com.daelim.witty.domain;

import com.daelim.witty.web.controller.dto.UserSignUpDTO;
import lombok.Getter;

@Getter
public class User {

    private String id;
    private String password;
    private String email;
    private String department;

    // TODO 이거 지워야함! 임시 데이터 테스트용
    public User(UserSignUpDTO userSignUpDTO) {
        this.id = userSignUpDTO.getUser_id();
        this.password = userSignUpDTO.getPassword();
        this.email = userSignUpDTO.getUser_email();
        this.department = userSignUpDTO.getUser_department();
    }
}
