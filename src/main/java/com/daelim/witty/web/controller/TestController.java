package com.daelim.witty.web.controller;

import com.daelim.witty.domain.User;
import com.daelim.witty.web.argumentResolver.Login;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(@Login User user) {

        log.info("****로그인된 유저**** : {}"  , user);

        return "ok";
    }
}
