package com.daelim.witty.web.controller;

import com.daelim.witty.domain.User;
import com.daelim.witty.web.SessionConst;
import com.daelim.witty.web.controller.dto.UserLogInDTO;
import com.daelim.witty.web.controller.dto.UserSignUpDTO;
import com.daelim.witty.web.repository.user.UserRepository;
import com.daelim.witty.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Optional;

@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    /**
     *  회원가입
     *  담당자 : 김진솔
     * */
    @PostMapping
    public String signUp(@RequestBody UserSignUpDTO userSignUpDTO) {
        User user = new User(userSignUpDTO);

        boolean ret = userService.signUp(user);

        if(!ret){
            return "error 발생!";
        }else{
            return "success";
        }
    }

    /**
     *  로그인
     * */
    @PostMapping("/login")
    public HashMap<String, String> login(@RequestBody UserLogInDTO userLogInDTO, HttpServletRequest request) {
        User user = userService.login(userLogInDTO);

        HashMap<String, String> resultMap = new HashMap<>();

        if(user == null) {
            resultMap.put("result", "check content");
        }else{
            resultMap.put("result", "success");

            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_USER, user);
        }

        return resultMap;
    }


    /**
     *  로그아웃
     * */
    @PostMapping("/logout")
    public HashMap<String, String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        HashMap<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");

        return resultMap;
    }
}
