package com.daelim.witty.web.controller;

import com.daelim.witty.domain.User;
import com.daelim.witty.web.controller.dto.UserSignUpDTO;
import com.daelim.witty.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    /**
     *  회원가입
     * */
    @PostMapping
    public String signUp(@RequestBody UserSignUpDTO userSignUpDTO) {
        User user = new User(userSignUpDTO);

        log.info("user Email = {}", user.getEmail());
        log.info("userDTO Email = {}", userSignUpDTO.getUser_email());

        boolean ret = userService.signUp(user);
        if(!ret){
            return "error 발생!";
        }else{
            return "success";
        }
    }
}
