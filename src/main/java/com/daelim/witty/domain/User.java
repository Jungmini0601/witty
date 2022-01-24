package com.daelim.witty.domain;

import lombok.Getter;

@Getter
public class User {

    private String id;
    private String password;
    private String email;
    private String department;

    // TODO 이거 지워야함! 임시 데이터 테스트용
    public User() {
        this.id = "test2";
        this.password = "test2";
        this.email = "test2@email.daelim.ac.kr";
        this.department = "컴퓨터정보학부";
    }
}
