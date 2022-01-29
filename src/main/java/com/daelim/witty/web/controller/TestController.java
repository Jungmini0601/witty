package com.daelim.witty.web.controller;

import com.daelim.witty.domain.User;
import com.daelim.witty.web.argumentResolver.Login;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO 설명용 컨트롤러 추후 삭제 요망
@Slf4j
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(@Login User user) {

        return "ok";
    }
}
