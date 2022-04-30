package com.daelim.witty.v2.web.controller;


import com.daelim.witty.v2.domain.EmailVerification;
import com.daelim.witty.v2.domain.User;
import com.daelim.witty.v2.web.SessionConst;
import com.daelim.witty.v2.web.argumentResolver.Login;
import com.daelim.witty.v2.web.controller.dto.users.*;

import com.daelim.witty.v2.web.exception.BadRequestException;
import com.daelim.witty.v2.web.exception.ForbbiddenException;
import com.daelim.witty.v2.web.exception.UnAuthorizedException;
import com.daelim.witty.v2.web.service.users.UserServiceV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RequestMapping("/v2/users")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserServiceV2 userService;
    @Value("${profileImg.path}")
    private String profileUploadFolder;
    //에러 로그
    private void showErrorLog(String methodName, BindingResult bindingResult) throws BadRequestException {
        List<ObjectError> errorList = bindingResult.getAllErrors();

        for (ObjectError error: errorList) {
            log.error("[{} 에러] : {}", methodName, error.toString());
        }

        throw new BadRequestException("입력값 확인 필요");
    }

    @GetMapping("/follower/count/{user_id}")
    private ResponseEntity<Object> getFollowerCount(@PathVariable("user_id") String userId) throws Exception{
        Long count = userService.getFollowerCount(userId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/following/count/{user_id}")
    private ResponseEntity<Object> getFollowingCount(@PathVariable("user_id") String userId) throws Exception{
        Long count = userService.getFollowingCount(userId);
        return ResponseEntity.ok(count);
    }

    @GetMapping(value = "image/{imagename}")
    public ResponseEntity<Resource> showUserImage(@PathVariable("imagename") String imageName) {
        // 사진이 저장된 폴더 경로 변수 선언
        String imageRoot = profileUploadFolder;

        // 서버 로컬 경로 + 파일 명 저장 실시
        imageRoot = imageRoot + imageName;

        // Resorce를 사용해서 로컬 서버에 저장된 이미지 경로 및 파일 명을 지정
        Resource resource = new FileSystemResource(imageRoot);

        // 로컬 서버에 저장된 이미지 파일이 없을 경우
        if(!resource.exists()){
            return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND); // 리턴 결과 반환 404
        }

        // 로컬 서버에 저장된 이미지가 있는 경우 로직 처리
        HttpHeaders header = new HttpHeaders();
        Path filePath = null;
        try {
            filePath = Paths.get(imageRoot);
            // 인풋으로 들어온 파일명 .png / .jpg 에 맞게 헤더 타입 설정
            header.add("Content-Type", Files.probeContentType(filePath));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
    }

    // 회원가입
    @PostMapping
    public ResponseEntity<Object> signUp(@ModelAttribute @Validated UserSignUpDTO userSignUpDTO, BindingResult bindingResult,
                                         @RequestParam("profileImgUrl") MultipartFile file) throws Exception {
        // 입력값이 잘못 들어온 경우
        if (bindingResult.hasErrors()){
            showErrorLog("회원가입", bindingResult);
        }

        User user = User.createUserByDTO(userSignUpDTO);
        user = userService.signUp(user, file);
        userService.addFollow(user.getId(), user.getId());

        HashMap<String, Object> response = new HashMap<>();
        response.put("result", "성공");

        HashMap<String, Object> userResponse = new HashMap<>();
        userResponse.put("user_id", user.getId());
        userResponse.put("user_email", user.getEmail());
        userResponse.put("user_introduction", user.getIntroduction());
        userResponse.put("user_department", user.getDepartment());
        userResponse.put("profileImgUrl", user.getProfileImgUrl());

        response.put("user", userResponse);

        return ResponseEntity.ok()
                .body(response);
    }
    // 회원 정보 수정
    @PatchMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@ModelAttribute @Validated UpdateUserRequest updateUserRequest,
                                              BindingResult bindingResult,
                                              @RequestParam("profileImgUrl") MultipartFile file,
                                              @PathVariable("userId") String userId,
                                              HttpServletRequest request,
                                              @Login User user) throws Exception {
        if (bindingResult.hasErrors()){
            showErrorLog("회원정보 수정", bindingResult);
        }

        if (user == null) throw new UnAuthorizedException("로그인이 필요합니다");
        if (!user.getId().equals(userId)) throw new ForbbiddenException("회원 정보는 본인만 수정 할 수 있습니다.");
        User updateUser = userService.updateUser(updateUserRequest, file, user);

        HashMap<String, Object> response = new HashMap<>();
        response.put("result", "성공");

        HashMap<String, Object> userResponse = new HashMap<>();
        userResponse.put("user_id", updateUser.getId());
        userResponse.put("user_email", updateUser.getEmail());
        userResponse.put("user_introduction", updateUser.getIntroduction());
        userResponse.put("user_department", updateUser.getDepartment());
        userResponse.put("profileImgUrl", updateUser.getProfileImgUrl());

        response.put("user", userResponse);

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_USER, user);
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
            throw new BadRequestException("아이디 비밀번호 확인 필요");
        }

        HashMap<String, String> userResponse = new HashMap<>();
        userResponse.put("user_id", user.getId());
        userResponse.put("user_email", user.getEmail());
        userResponse.put("user_department", user.getDepartment());
        userResponse.put("user_introduction", user.getIntroduction());
        userResponse.put("profileImgUrl", user.getProfileImgUrl());


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
        userResponse.put("introduction", user.getIntroduction());
        userResponse.put("profile_imageUrl", user.getProfileImgUrl());

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
