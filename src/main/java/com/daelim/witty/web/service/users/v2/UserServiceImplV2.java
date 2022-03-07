package com.daelim.witty.web.service.users.v2;

import com.daelim.witty.domain.v2.EmailVerification;
import com.daelim.witty.domain.v2.User;
import com.daelim.witty.web.controller.v1.dto.users.UserLogInDTO;
import com.daelim.witty.web.controller.v1.dto.users.VerificationCodeDTO;
import com.daelim.witty.web.repository.users.v2.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImplV2 implements UserServiceV2 {

    private final UserRepository userRepository;

    @Override
    public User signUp(User user) throws Exception {
        return userRepository.save(user);
    }

    @Override
    public User login(UserLogInDTO userLogInDTO) throws Exception {
        return null;
    }

    @Override
    public boolean isDuplicatedId(String id) throws Exception {
        return false;
    }

    @Override
    public void emailConfirm(EmailVerification emailVerification) throws Exception {

    }

    @Override
    public boolean verification(VerificationCodeDTO verificationCodeDTO) throws Exception {
        return false;
    }
}
