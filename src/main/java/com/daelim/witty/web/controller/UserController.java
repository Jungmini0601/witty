package com.daelim.witty.web.controller;

import com.daelim.witty.domain.v1.EmailConfrim;
import com.daelim.witty.domain.v1.User;
import com.daelim.witty.web.SessionConst;
import com.daelim.witty.web.argumentResolver.Login;
import com.daelim.witty.web.controller.dto.users.*;
import com.daelim.witty.web.exception.BadRequestException;
import com.daelim.witty.web.service.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    private void showErrorLog(String methodName,BindingResult bindingResult) throws BadRequestException{
        List<ObjectError> errorList = bindingResult.getAllErrors();

        for (ObjectError error: errorList) {
            log.error("[{} 에러] : {}", methodName, error.toString());
        }

        throw new BadRequestException("입력값 확인 필요");
    }
    /**
    *  회원가입
    *  담당자 : 김진솔
    * */
    @PostMapping
    public ResponseEntity<Object> signUp(@RequestBody @Validated UserSignUpDTO userSignUpDTO, BindingResult bindingResult) throws Exception {
        // 입력값이 잘못 들어온 경우
        if (bindingResult.hasErrors()){
            showErrorLog("회원가입", bindingResult);
        }

        User user = new User(userSignUpDTO);

        userService.signUp(user);

        HashMap<String, Object> response = new HashMap<>();

        // 성공
        response.put("result", "성공");

        HashMap<String, Object> userResponse = new HashMap<>();
        userResponse.put("user_id", user.getId());
        userResponse.put("user_email", user.getEmail());
        userResponse.put("user_department", user.getDepartment());

        response.put("user", userResponse);


        return ResponseEntity.ok()
                .body(response);
    }

    /**
     * 아이디중복체크
     * 오성민
     */
    @PostMapping("/id_check")
    public ResponseEntity<Object> id_check(@RequestBody @Validated UserIdCheckDTO userIdCheckDTO, BindingResult bindingResult) throws Exception{
        HashMap<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()){
            showErrorLog("아이디 중복 체크", bindingResult);
        }

        boolean ret = userService.isDuplicatedId(userIdCheckDTO.getUser_id());

        if(!ret) {
            response.put("result", "아이디 중복 체크 완료");
            response.put("user_id", userIdCheckDTO.getUser_id());

            return ResponseEntity.ok().body(response);
        }


        response.put("result", "존재하는 아이디 입니다.");
        response.put("user_id", userIdCheckDTO.getUser_id());

        return ResponseEntity.badRequest().body(response);
    }

    /**
    *  이메일 전송
    *  인증번호를 이메일로 전송한다
    *  작성자: 김정민
    */
    @PostMapping("/sendEmail")
    public ResponseEntity<Object> sendVerificationCode(@RequestBody @Validated SendVerificationCodeDTO sendVerificationCodeDTO, BindingResult bindingResult) throws Exception{
        HashMap<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()){
            showErrorLog("이메일 전송", bindingResult);
        }

        EmailConfrim emailConfrim = new EmailConfrim();
        emailConfrim.setEmail(sendVerificationCodeDTO.getEmail());

        try {
            userService.emailConfirm(emailConfrim);
        }catch (BadRequestException e) {
            log.error("[이메일 전송]: {}", e.getMessage());

            response.put("result", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

        response.put("result", "성공");
        response.put("email", emailConfrim.getEmail());

        return ResponseEntity.ok().body(response);
    }

    /**
     * 인증번호 확인
     * 작성자: 김정민
     * */
    @PostMapping("/verification")
    public ResponseEntity<Object> verification(@RequestBody @Validated VerificationCodeDTO verificationCodeDTO, BindingResult bindingResult) throws Exception {
        if(bindingResult.hasErrors()) {
            showErrorLog("인증번호 확인", bindingResult);
        }

        boolean ret = userService.verification(verificationCodeDTO);

        HashMap<String, Object> response = new HashMap<>();

        if(ret) {
            response.put("result", "성공");
            return ResponseEntity.ok().body(response);
        }

        response.put("result", "인증번호를 확인 해 주세요");
        return ResponseEntity.badRequest().body(response);
    }

    /**
     *  로그인
     *  김정민
     * */
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Validated UserLogInDTO userLogInDTO, BindingResult bindingResult, HttpServletRequest request) throws Exception{

        if(bindingResult.hasErrors()){
            showErrorLog("로그인", bindingResult);
        }

        User user = userService.login(userLogInDTO);

        HashMap<String, Object> response = new HashMap<>();

        // 입력값이 통과 되었지만 맞지 않는 경우
        if(user == null
                || !(user.getId().equals(userLogInDTO.getUser_id()) && user.getPassword().equals(userLogInDTO.getPassword()))) {
            response.put("result", "아이디 비밀번호 확인 필요");
            return ResponseEntity.badRequest().body(response);
        }



        HashMap<String, String> userResponse = new HashMap<>();
        userResponse.put("user_id", user.getId());
        userResponse.put("user_email", user.getEmail());
        userResponse.put("user_department", user.getDepartment());

        response.put("result", "성공");
        response.put("user", userResponse);

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_USER, user);

        return ResponseEntity.ok().body(response);
    }

    /**
     *  로그아웃
     * */
    @PostMapping("/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        HashMap<String, String> response = new HashMap<>();
        response.put("result", "성공");

        return ResponseEntity.ok().body(response);
    }

    /**인증 확인
     * 김정민
     * */
    @GetMapping("/auth")
    public ResponseEntity<Object> auth(@Login User user) {
        HashMap<String, Object> response = new HashMap<>();

        if(user == null) {
            response.put("result", "로그인 정보가 없음");
            return ResponseEntity.badRequest().body(response);
        }

        response.put("result", "성공");

        HashMap<String, Object> userResponse = new HashMap<>();
        userResponse.put("user_id", user.getId());
        userResponse.put("user_email", user.getEmail());
        userResponse.put("user_department", user.getDepartment());

        response.put("user", userResponse);

        return ResponseEntity.ok().body(response);
    }
}
