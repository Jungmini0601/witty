package com.daelim.witty.web.service;

import com.daelim.witty.domain.EmailConfrim;
import com.daelim.witty.domain.User;
import com.daelim.witty.web.controller.dto.UserLogInDTO;
import com.daelim.witty.web.controller.dto.VerificationCodeDTO;
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
    /** 아이디 중복 체크*/
    public boolean isDuplicatedId(String id);

    /** 이메일 인증 저장*/
    public void emailConfirm(EmailConfrim emailConfrim) throws Exception;

    boolean verification(VerificationCodeDTO verificationCodeDTO);
}
