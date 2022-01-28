package com.daelim.witty.web.service;

import com.daelim.witty.domain.User;
import com.daelim.witty.web.controller.dto.UserLogInDTO;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {
    /**
     * 회원가입
     * */
    public boolean signUp(User user);
    /**
     *  로그인
     * */
    public User login(UserLogInDTO userLogInDTO);
}
