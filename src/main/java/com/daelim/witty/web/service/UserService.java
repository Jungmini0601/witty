package com.daelim.witty.web.service;

import com.daelim.witty.domain.User;

public interface UserService {
    /**
     * 회원가입
     * */
    public boolean signUp(User user);
}
