package com.daelim.witty.web.service.users.v1;

import com.daelim.witty.domain.v1.EmailConfrim;
import com.daelim.witty.domain.v1.User;
import com.daelim.witty.web.controller.v1.dto.users.UserLogInDTO;
import com.daelim.witty.web.controller.v1.dto.users.VerificationCodeDTO;

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
