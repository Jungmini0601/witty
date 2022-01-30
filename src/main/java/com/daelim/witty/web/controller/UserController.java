package com.daelim.witty.web.controller;

import com.daelim.witty.domain.EmailConfrim;
import com.daelim.witty.domain.User;
import com.daelim.witty.web.SessionConst;
import com.daelim.witty.web.controller.dto.*;
import com.daelim.witty.web.exception.BadRequestException;
import com.daelim.witty.web.repository.user.UserRepository;
import com.daelim.witty.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

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
    public HashMap<String, Object> signUp(@RequestBody @Validated UserSignUpDTO userSignUpDTO, BindingResult bindingResult) throws Exception {
        // 입력값이 잘못 들어온 경우
        if (bindingResult.hasErrors()){
            throw new BadRequestException("입력값 확인 필요");
        }

        //입력값이 제대로 들어 왔으니까 저장을하면 되잖아?
        User user = new User(userSignUpDTO);

        boolean ret = userService.signUp(user);

        HashMap<String, Object> response = new HashMap<>();

        if(!ret) {
            response.put("result", "서버 에러");
            return response;
        }

        // 성공
        response.put("result", "성공");

        HashMap<String, Object> userResponse = new HashMap<>();
        userResponse.put("user_id", user.getId());
        userResponse.put("user_email", user.getEmail());
        userResponse.put("user_department", user.getDepartment());

        response.put("user", userResponse);


        return response;
    }

    /**
     * 아이디중복체크
     * 오성민
     */
    @PostMapping("/id_check")
    public HashMap<String, Object> id_check(@RequestBody @Validated UserIdCheckDTO userIdCheckDTO, BindingResult bindingResult) {
        HashMap<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()){
            throw new BadRequestException("입력값 확인 필요");
        }

        log.info(userIdCheckDTO.getUser_id());

        boolean ret = userService.isDuplicatedId(userIdCheckDTO.getUser_id());

        if(!ret) {
            response.put("result", "아이디 중복 체크 완료");
            response.put("user_id", userIdCheckDTO.getUser_id());

            return response;
        }


        response.put("result", "아이디가 이미 존재합니다");
        response.put("user_id", userIdCheckDTO.getUser_id());

        return response;
    }

    /**
    *  이메일 전송
    *  클라이언트에 이메일 인증번호까지 같이 전송한 후 클라이언트에서 확인 하도록 한다.
    *  김정민
    */
    @PostMapping("/sendEmail")
    public HashMap<String, Object> sendVerificationCode(@RequestBody SendVerificationCodeDTO sendVerificationCodeDTO) throws Exception{
        HashMap<String, Object> response = new HashMap<>();

        EmailConfrim emailConfrim = new EmailConfrim();
        emailConfrim.setEmail(sendVerificationCodeDTO.getEmail());

        try {
            userService.emailConfirm(emailConfrim);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        response.put("result", "성공");
        response.put("email", emailConfrim.getEmail());

        return response;
    }

    // TODO 에러처리 및 리팩토링 좀 필요함 기능은 개발 완료
    @PostMapping("/verification")
    public HashMap<String, Object> verification(@RequestBody VerificationCodeDTO verificationCodeDTO) throws Exception {
        boolean ret = userService.verification(verificationCodeDTO); // 여기 인터

        HashMap<String, Object> response = new HashMap<>();

        if(ret) {
            response.put("result", "성공");
        }else {
            response.put("result", "인증번호를 확인 해 주세요");
        }

        return response;
    }

    /**
     *  로그인
     *  김정민
     * */
    @PostMapping("/login")
    public HashMap<String, Object> login(@RequestBody @Validated UserLogInDTO userLogInDTO, BindingResult bindingResult, HttpServletRequest request) {

        if(bindingResult.hasErrors()){
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
