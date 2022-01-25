package com.daelim.witty.web.service;

import com.daelim.witty.domain.User;
import com.daelim.witty.web.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public boolean signUp(User user) {
        return userRepository.save(user);
    }
}
