package com.daelim.witty.web.controller;

import com.daelim.witty.domain.User;
import com.daelim.witty.web.SessionConst;
import com.daelim.witty.web.controller.dto.UserLogInDTO;
import com.daelim.witty.web.controller.dto.UserSignUpDTO;
import com.daelim.witty.web.exception.BadRequestException;
import com.daelim.witty.web.repository.user.UserRepository;
import com.daelim.witty.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
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
    @PostMapping("/signUp")
    public String signUp(@RequestBody @Validated UserSignUpDTO userSignUpDTO, BindingResult bindingResult ) {
        User user = new User(userSignUpDTO);

        boolean ret = userService.signUp(user);

        if(!ret){
            /**
             * false
             * result : "입력값 확인 필요"
             */
            //bindingResult.addError(new FieldError("user","userSignUpDTO","회원가입 오류"));
            if (bindingResult.hasErrors()){
                log.info(bindingResult.toString());
                throw new BadRequestException("입력값 확인 필요");
                //return "signUp";
            }
        }else{
            /**
             * true
             * 가입된 유저 정보
             * "user":{
             *     "user_id":
             *     "user_email":
             *     "user_department":
             * */
            //log로 뿌려주면 어떻게 되지??
            log.info(user.toString());
            return "redirect:/login";
        }
        //return 값 ??
        return null;
    }

    /**
     *  로그인
     * @Author: 김정민
     * */
    @PostMapping("/login")
    public HashMap<String, Object> login(@RequestBody @Validated UserLogInDTO userLogInDTO, BindingResult bindingResult, HttpServletRequest request) {

        if(bindingResult.hasErrors()){
            log.info(bindingResult.toString());
            throw new BadRequestException("입력값 확인 필요");
        }

        User user = userService.login(userLogInDTO);

        HashMap<String, Object> resultMap = new HashMap<>();

        // 입력값이 통과 되었지만 맞지 않는 경우
        if(user == null
                || !(user.getId().equals(userLogInDTO.getUser_id()) && user.getPassword().equals(userLogInDTO.getPassword()))) {
            resultMap.put("result", "아이디 비밀번호 확인 필요");
            return resultMap;
        }



        HashMap<String, String> userResponse = new HashMap<>();
        userResponse.put("user_id", user.getId());
        userResponse.put("user_email", user.getEmail());
        userResponse.put("user_department", user.getDepartment());

        resultMap.put("result", "성공");
        resultMap.put("user", userResponse);

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_USER, user);

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
        resultMap.put("result", "성공");

        return resultMap;
    }
}
