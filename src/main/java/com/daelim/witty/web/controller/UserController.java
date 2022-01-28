package com.daelim.witty.web.controller;

import com.daelim.witty.domain.User;
import com.daelim.witty.web.SessionConst;
import com.daelim.witty.web.controller.dto.UserIdCheckDTO;
import com.daelim.witty.web.controller.dto.UserLogInDTO;
import com.daelim.witty.web.controller.dto.UserSignUpDTO;
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
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final JavaMailSender mailSender;
    /**
    *  회원가입
    *  담당자 : 김진솔
    * */
    @PostMapping
    public HashMap<String, Object> signUp(@RequestBody @Validated UserSignUpDTO userSignUpDTO, BindingResult bindingResult) throws Exception {
        // 입력값이 잘못 들어온 경우
        if (bindingResult.hasErrors()){
            log.info(bindingResult.toString());
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
            log.info(bindingResult.toString());
            throw new BadRequestException("입력값 확인 필요");
        }

        log.info(userIdCheckDTO.getUser_id());

        boolean ret = userService.isDuplicatedId(userIdCheckDTO.getUser_id());


        log.info(ret + "");
        // TODO ret의 결과 값에 따라서 값 반환
        if(!ret) {
            response.put("result", "아이디 중복 체크 완료");
            response.put("user_id", userIdCheckDTO.getUser_id());

            return response;
        }


        response.put("result", "아이디가 이미 존재합니다");
        response.put("user_id", userIdCheckDTO.getUser_id());

        return response;



    }



    /*
    *  이메일 전송
    *  클라이언트에 이메일 인증번호까지 같이 전송한 후 클라이언트에서 확인 하도록 한다.
    *  김정민
    * */
    @PostMapping("/sendVerificationCode")   // TODO 이메일에 값 제대로 안들어옴
    public HashMap<String, Object> sendVerificationCode(@RequestBody String email) throws Exception {
        HashMap<String, Object> response = new HashMap<>();

        String subject = "test 메일";
        String content = "김정민 슈퍼미남 짱짱맨";
        String from = "jungmini0601@gmail.com";
        String to = "aphopis@naver.com";

        try {
            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true, "UTF-8");
            // true 는 멀티 파트 메세지를 사용 하겠다는 의미

            mailHelper.setFrom(from);
            mailHelper.setTo(to);
            mailHelper.setSubject(subject);
            mailHelper.setText(content, true);
            // true는 html 을 사용 하겠다는 의미

            mailSender.send(mail);

        }catch (Exception e) {

            response.put("result", "메일전송 실패");
            return response;
        }

        response.put("result", "성공");

        return response;
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
