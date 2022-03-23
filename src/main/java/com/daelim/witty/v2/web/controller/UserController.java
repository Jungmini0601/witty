package com.daelim.witty.v2.web.controller;


import com.daelim.witty.v2.domain.EmailVerification;
import com.daelim.witty.v2.domain.User;
import com.daelim.witty.v2.web.SessionConst;
import com.daelim.witty.v2.web.argumentResolver.Login;
import com.daelim.witty.v2.web.controller.dto.users.*;

import com.daelim.witty.v2.web.exception.BadRequestException;
import com.daelim.witty.v2.web.exception.ForbbiddenException;
import com.daelim.witty.v2.web.service.users.UserServiceV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    //이메일 전송
    @PostMapping("/sendEmail")
    public ResponseEntity<Object> sendVerificationCode(@RequestBody @Validated SendVerificationCodeDTO sendVerificationCodeDTO, BindingResult bindingResult) throws Exception{
        HashMap<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()){
            showErrorLog("이메일 전송", bindingResult);
        }

        EmailVerification emailVerification = EmailVerification.createEmailVerificationByDTO(sendVerificationCodeDTO);

        try {
            userService.emailConfirm(emailVerification);
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
        response.put("email", emailVerification.getEmail());

        return ResponseEntity.ok().body(response);
    }

    // 인증번호 확인
    @PostMapping("/verification")
    public ResponseEntity<Object> verification(@RequestBody @Validated VerificationCodeDTO verificationCodeDTO, BindingResult bindingResult) throws Exception {
        if(bindingResult.hasErrors()) {
            showErrorLog("인증번호 확인", bindingResult);
        }

        HashMap<String, Object> response = new HashMap<>();

        if(userService.verification(verificationCodeDTO)) {
            response.put("result", "성공");
            return ResponseEntity.ok().body(response);
        }

        response.put("result", "인증번호를 확인 해 주세요");
        return ResponseEntity.badRequest().body(response);
    }

    // 로그인
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

    // 로그아웃
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

    // 인증확인
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

    // 팔로우 요청
    @PostMapping("/follow")
    public ResponseEntity<Object> follow(@RequestBody @Validated FollowRequest followRequest, BindingResult bindingResult,
                                         @Login User user) throws Exception{
        if(user == null) {
            throw new ForbbiddenException("로그인이 필요합니다!");
        }

        if(bindingResult.hasErrors()) {
            showErrorLog("팔로우 신청", bindingResult);
        }

        userService.addFollow(followRequest.getToUserId(), user.getId());

        return ResponseEntity.ok(followRequest);
    }

    // 팔로우 취소
    @DeleteMapping("/follow")
    public ResponseEntity<Object> followCancel(@RequestBody @Validated FollowCancelRequest followCancelRequest,
                                               BindingResult bindingResult,
                                                @Login User user) throws Exception{
        if(user == null) {
            throw new ForbbiddenException("로그인이 필요합니다!");
        }

        if(bindingResult.hasErrors()) {
            showErrorLog("팔로우 신청", bindingResult);
        }

        userService.cancelFollow(followCancelRequest.getToUserId(), user.getId());

        return ResponseEntity.ok(followCancelRequest);
    }

    // 팔로워 조회
    @GetMapping("/{profileId}/follower")
    public ResponseEntity<?> getFollower(@PathVariable String profileId, @Login User user) throws Exception{
        return new ResponseEntity<>(userService.getFollower(profileId, user.getId()), HttpStatus.OK);
    }

    @GetMapping("/{profileId}/following")
    public ResponseEntity<?> getFollowing(@PathVariable String profileId, @Login User user) throws Exception{
        return new ResponseEntity<>(userService.getFollowing(profileId, user.getId()), HttpStatus.OK);
    }

    // 위티 좋아요
    @PostMapping("/witty/like/{wittyId}")
    public ResponseEntity<Object> wittyLike(@PathVariable Long wittyId, @Login User user) throws Exception {
        if(user == null) {
            throw new ForbbiddenException("로그인이 필요합니다!");
        }

        userService.likeWitty(wittyId, user);

        HashMap<String, Object> response = new HashMap<>();
        response.put("wittyId", wittyId);
        response.put("userId", user.getId());
        return ResponseEntity.ok(response);
    }

    // 위티 좋아요 취소
    @PostMapping("/witty/unlike/{wittyId}")
    public ResponseEntity<Object> wittyUnLike(@PathVariable Long wittyId, @Login User user) throws Exception {
        if(user == null) {
            throw new ForbbiddenException("로그인이 필요합니다!");
        }

        userService.unlikeWitty(wittyId, user);

        HashMap<String, Object> response = new HashMap<>();
        response.put("wittyId", wittyId);
        response.put("userId", user.getId());
        return ResponseEntity.ok(response);
    }

    // 댓글 좋아요
    @PostMapping("/comment/like/{commentId}")
    public ResponseEntity<Object> commentLike(@PathVariable Long commentId, @Login User user) throws Exception {
        if(user == null) {
            throw new ForbbiddenException("로그인이 필요합니다!");
        }

        userService.likeComment(commentId, user);

        HashMap<String, Object> response = new HashMap<>();
        response.put("commentId", commentId);
        response.put("userId", user.getId());
        return ResponseEntity.ok(response);
    }

    // 댓글 좋아요 취소
    @PostMapping("/comment/unlike/{commentId}")
    public ResponseEntity<Object> commentUnLike(@PathVariable Long commentId, @Login User user) throws Exception {
        if(user == null) {
            throw new ForbbiddenException("로그인이 필요합니다!");
        }

        userService.unlikeComment(commentId, user);

        HashMap<String, Object> response = new HashMap<>();
        response.put("commentId", commentId);
        response.put("userId", user.getId());
        return ResponseEntity.ok(response);
    }
}
