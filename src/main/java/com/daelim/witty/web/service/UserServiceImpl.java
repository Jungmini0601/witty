package com.daelim.witty.web.service;

import com.daelim.witty.domain.User;
import com.daelim.witty.web.controller.dto.UserLogInDTO;
import com.daelim.witty.web.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public boolean signUp(User user) {
        return userRepository.save(user);
    }

    @Override
    public User login(UserLogInDTO userLogInDTO) {
        return userRepository.findById(userLogInDTO.getUser_id())
                .filter(user -> user.getPassword().equals(userLogInDTO.getPassword()))
                .orElse(null);
    }
}
