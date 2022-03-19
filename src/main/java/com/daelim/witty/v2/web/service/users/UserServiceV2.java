package com.daelim.witty.v2.web.service.users;


import com.daelim.witty.v2.domain.EmailVerification;
import com.daelim.witty.v2.domain.User;
import com.daelim.witty.v2.web.controller.dto.users.UserLogInDTO;
import com.daelim.witty.v2.web.controller.dto.users.VerificationCodeDTO;

public interface UserServiceV2 {
    /**
     * 회원가입
     * */
    User signUp(User user) throws Exception;
    /**
     *  로그인
     * */
    User login(UserLogInDTO userLogInDTO) throws Exception;
    /** 아이디 중복 체크*/
    boolean isDuplicatedId(String id) throws Exception;

    /** 이메일 인증 저장*/
    void emailConfirm(EmailVerification emailVerification) throws Exception;

    boolean verification(VerificationCodeDTO verificationCodeDTO) throws Exception;

    /* 팔로우 추가 */
    void addFollow(String toUserName, String fromUsername) throws Exception;

    /*위티 좋아요*/
    void likeWitty(Long wittyId, User user) throws Exception;

    /*위티 좋아요 취소*/
    void unlikeWitty(Long wittyId, User user) throws Exception;
}
