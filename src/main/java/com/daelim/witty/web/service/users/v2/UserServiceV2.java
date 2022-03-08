package com.daelim.witty.web.service.users.v2;


import com.daelim.witty.domain.v2.EmailVerification;
import com.daelim.witty.domain.v2.User;
import com.daelim.witty.web.controller.v1.dto.users.UserLogInDTO;
import com.daelim.witty.web.controller.v2.dto.users.VerificationCodeDTO;

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
