package com.daelim.witty.v2.web.service.users;


import com.daelim.witty.v2.domain.EmailVerification;
import com.daelim.witty.v2.domain.User;
import com.daelim.witty.v2.web.controller.dto.users.GetFollowerResponse;
import com.daelim.witty.v2.web.controller.dto.users.UserLogInDTO;
import com.daelim.witty.v2.web.controller.dto.users.VerificationCodeDTO;

import java.util.List;

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
    void addFollow(String toUserName, String fromUserName) throws Exception;

    void cancelFollow(String toUserName, String fromUserName) throws Exception;
    /*위티 좋아요*/
    void likeWitty(Long wittyId, User user) throws Exception;

    /*위티 좋아요 취소*/
    void unlikeWitty(Long wittyId, User user) throws Exception;

    /*댓글 좋아요*/
    void likeComment(Long commentId, User user) throws Exception;

    /*위티 좋아요 취소*/
    void unlikeComment(Long commentId, User user) throws Exception;
    /* 팔로워 조회 */
    List<GetFollowerResponse> getFollower(String profileId, String loginId) throws Exception;
}
