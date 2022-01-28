package com.daelim.witty.web.service;

import com.daelim.witty.domain.User;
import com.daelim.witty.web.controller.dto.UserLogInDTO;

public interface UserService {
    /**
     * 회원가입
     * */
    public boolean signUp(User user);

    /**
     *  로그인
     * */
    public User login(UserLogInDTO userLogInDTO);

    public boolean isDuplicatedId(String id);
}
