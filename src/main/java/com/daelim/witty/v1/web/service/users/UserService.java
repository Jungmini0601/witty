package com.daelim.witty.v1.web.service.users;

import com.daelim.witty.v1.domain.EmailConfrim;
import com.daelim.witty.v1.domain.User;
import com.daelim.witty.v1.web.controller.dto.users.UserLogInDTO;
import com.daelim.witty.v1.web.controller.dto.users.VerificationCodeDTO;


public interface UserService {
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
    public void emailConfirm(EmailConfrim emailConfrim) throws Exception;

    boolean verification(VerificationCodeDTO verificationCodeDTO) throws Exception;
}
