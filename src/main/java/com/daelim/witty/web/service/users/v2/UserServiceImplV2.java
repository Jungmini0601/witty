package com.daelim.witty.web.service.users.v2;

import com.daelim.witty.domain.v2.EmailVerification;
import com.daelim.witty.domain.v2.User;
import com.daelim.witty.web.controller.v1.dto.users.UserLogInDTO;
import com.daelim.witty.web.controller.v1.dto.users.VerificationCodeDTO;
import com.daelim.witty.web.repository.users.v2.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImplV2 implements UserServiceV2 {

    private final UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User signUp(User user) throws Exception {
        userRepository.save(user);
        return user;
    }


    @Override
    public User login(UserLogInDTO userLogInDTO) throws Exception {
        return null;
    }

    //아이디가 존재하지않으면 true 존재하면 false
    @Override
    public boolean isDuplicatedId(String id) throws Exception {
        return userRepository.findById(id).isPresent();
    }

    @Override
    public void emailConfirm(EmailVerification emailVerification) throws Exception {

    }

    @Override
    public boolean verification(VerificationCodeDTO verificationCodeDTO) throws Exception {
        return false;
    }
}
