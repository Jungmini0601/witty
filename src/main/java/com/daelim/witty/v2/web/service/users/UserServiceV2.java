package com.daelim.witty.v2.web.service.users;


import com.daelim.witty.v2.domain.EmailVerification;
import com.daelim.witty.v2.domain.User;
import com.daelim.witty.v2.web.controller.dto.users.UserLogInDTO;
import com.daelim.witty.v2.web.controller.dto.users.VerificationCodeDTO;

public interface UserServiceV2 {
    /**
     * 회원가입
     * */
    public User signUp(User user) throws Exception;
    /**
     *  로그인
     * */
    public User login(UserLogInDTO userLogInDTO) throws Exception;
    /** 아이디 중복 체크*/
    public boolean isDuplicatedId(String id) throws Exception;

    /** 이메일 인증 저장*/
    public void emailConfirm(EmailVerification emailVerification) throws Exception;

    boolean verification(VerificationCodeDTO verificationCodeDTO) throws Exception;
}
