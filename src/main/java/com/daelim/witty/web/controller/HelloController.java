package com.daelim.witty.web.controller;

import com.daelim.witty.domain.User;
import com.daelim.witty.web.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO 설명용 컨트롤러 나중에 제거 해야함!
@RequiredArgsConstructor
@RestController
public class HelloController {

    private final UserRepository userRepository;

    @GetMapping("/hello")
    public String test() {
        return "hello";
    }

    @GetMapping("/test")
    public String test2() {
        User user = new User();

        boolean isSave = userRepository.save(user);

        if(!isSave){
            return "error 발생!";
        }else{
            return "success";
        }
    }
}
