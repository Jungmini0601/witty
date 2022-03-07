package com.daelim.witty.web.controller.v2;

import com.daelim.witty.domain.v2.User;
import com.daelim.witty.web.controller.v2.dto.users.UserIdCheckDTO;
import com.daelim.witty.web.controller.v2.dto.users.UserSignUpDTO;
import com.daelim.witty.web.exception.BadRequestException;
import com.daelim.witty.web.service.users.v2.UserServiceV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RequestMapping("/v2/users")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserServiceV2 userService;

    //에러 로그
    private void showErrorLog(String methodName, BindingResult bindingResult) throws BadRequestException {
        List<ObjectError> errorList = bindingResult.getAllErrors();

        for (ObjectError error: errorList) {
            log.error("[{} 에러] : {}", methodName, error.toString());
        }

        throw new BadRequestException("입력값 확인 필요");
    }

    // 회원가입
    @PostMapping
    public ResponseEntity<Object> signUp(@RequestBody @Validated UserSignUpDTO userSignUpDTO, BindingResult bindingResult) throws Exception {
        // 입력값이 잘못 들어온 경우
        if (bindingResult.hasErrors()){
            showErrorLog("회원가입", bindingResult);
        }

        User user = User.createUserByDTO(userSignUpDTO);
        user = userService.signUp(user);

        HashMap<String, Object> response = new HashMap<>();
        response.put("result", "성공");

        HashMap<String, Object> userResponse = new HashMap<>();
        userResponse.put("user_id", user.getId());
        userResponse.put("user_email", user.getEmail());
        userResponse.put("user_department", user.getDepartment());
        response.put("user", userResponse);

        return ResponseEntity.ok()
                .body(response);
    }

    // 아이디중복확인
    @PostMapping("/id_check")
    public ResponseEntity<Object> id_check(@RequestBody @Validated UserIdCheckDTO userIdCheckDTO, BindingResult bindingResult) throws Exception{
        HashMap<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()){
            showErrorLog("아이디 중복 체크", bindingResult);
        }

        if(!userService.isDuplicatedId(userIdCheckDTO.getUser_id())) {
            response.put("result", "아이디 중복 체크 완료");
            response.put("user_id", userIdCheckDTO.getUser_id());
            return ResponseEntity.ok().body(response);
        }

        response.put("result", "존재하는 아이디 입니다.");
        response.put("user_id", userIdCheckDTO.getUser_id());
        return ResponseEntity.badRequest().body(response);
    }
}
