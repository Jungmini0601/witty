package com.daelim.witty.v2.web.service.users;


import com.daelim.witty.v2.domain.*;
import com.daelim.witty.v2.web.controller.dto.users.UserLogInDTO;
import com.daelim.witty.v2.web.controller.dto.users.VerificationCodeDTO;
import com.daelim.witty.v2.web.exception.BadRequestException;
import com.daelim.witty.v2.web.repository.users.EmailVerificationRepository;
import com.daelim.witty.v2.web.repository.users.FollowRepository;
import com.daelim.witty.v2.web.repository.users.UserRepository;
import com.daelim.witty.v2.web.repository.users.WittyLikeRepository;
import com.daelim.witty.v2.web.repository.wittys.WittyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImplV2 implements UserServiceV2 {

    private final UserRepository userRepository;
    private final MailService mailService;
    private final EmailVerificationRepository emailVerificationRepository;
    private final FollowRepository followRepository;
    private final WittyLikeRepository wittyLikeRepository;
    private final WittyRepository wittyRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User signUp(User user) throws Exception {
        userRepository.save(user);
        return user;
    }


    @Override
    public User login(UserLogInDTO userLogInDTO) throws Exception {
        return userRepository.findById(userLogInDTO.getUser_id())
                .filter(user -> user.getPassword().equals(userLogInDTO.getPassword())).orElse(null);
    }

    //아이디가 존재하지않으면 true 존재하면 false
    @Override
    public boolean isDuplicatedId(String id) throws Exception {
        return userRepository.findById(id).isPresent();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void emailConfirm(EmailVerification emailVerification) throws Exception {
        mailService.sendMail(emailVerification);
    }


    @Override
    public boolean verification(VerificationCodeDTO verificationCodeDTO) throws Exception {
        Optional<EmailVerification> emailVerificationOptional = emailVerificationRepository.findByEmail(verificationCodeDTO.getEmail());
        if(emailVerificationOptional.isEmpty()) throw new BadRequestException("인증번호 요청을 먼저 해 주어야 합니다.");

        EmailVerification emailVerification = emailVerificationOptional.get();

        return emailVerification.getEmail().equals(verificationCodeDTO.getEmail()) &&
                emailVerification.getVerificationKey().equals(verificationCodeDTO.getKey());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addFollow(String toUserName, String fromUsername) throws Exception {
        User fromUser = userRepository.findById(fromUsername).orElseThrow(() -> new BadRequestException("입력값 확인 필요"));
        User toUser = userRepository.findById(toUserName).orElseThrow(() -> new BadRequestException("입력값 확인 필요"));

        Follow follow = new Follow(toUser, fromUser);
        followRepository.save(follow);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void likeWitty(Long wittyId, User user) throws Exception {
        Witty witty = wittyRepository.findById(wittyId).orElseThrow(() -> new BadRequestException("입력값 확인 필요"));
        WittyLike likeInfo = new WittyLike(user, witty);
        wittyLikeRepository.save(likeInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void unlikeWitty(Long wittyId, User user) throws Exception {
        Witty witty = wittyRepository.findById(wittyId).orElseThrow(() -> new BadRequestException("입력값 확인 필요"));
        WittyLike wittyLike = wittyLikeRepository.findByWittyAndUser(witty, user)
                .orElseThrow(() -> new BadRequestException("입력값 확인 필요"));

        wittyLikeRepository.delete(wittyLike);
    }
}
