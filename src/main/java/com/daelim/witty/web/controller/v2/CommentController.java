package com.daelim.witty.web.controller.v2;

import com.daelim.witty.domain.v2.Comment;
import com.daelim.witty.domain.v2.User;
import com.daelim.witty.web.argumentResolver.Login;
import com.daelim.witty.web.controller.v2.dto.comments.CreateCommentRequest;
import com.daelim.witty.web.controller.v2.dto.comments.CreateCommentResponse;
import com.daelim.witty.web.exception.BadRequestException;
import com.daelim.witty.web.exception.UnAuthorizedException;
import com.daelim.witty.web.service.comments.v2.CommentServiceV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Slf4j
@RequestMapping("/v2/comments")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentServiceV2 commentService;

    @PostMapping
    public ResponseEntity<Object> createComment(@RequestBody @Validated CreateCommentRequest createCommentRequest,
                                                BindingResult bindingResult,
                                                @Login User user) throws Exception{
        if(user == null) {
            throw new UnAuthorizedException("로그인이 필요합니다");
        }

        if(bindingResult.hasErrors()) {
            throw new BadRequestException("입력값을 확인 해 주세요");
        }

        Comment comment = commentService.save(createCommentRequest, user);
        CreateCommentResponse createCommentResponse = CreateCommentResponse.success(comment, user);

        HashMap<String, Object> response = new HashMap<>();
        response.put("result", "성공");
        response.put("comment", createCommentResponse);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<Object> updateComment(@PathVariable("commentId") Integer id,
                                                @RequestBody @Validated CommentUpdateDTO commentUpdateDTO,
                                                BindingResult bindingResult,
                                                @Login User user) throws Exception {

        if(user == null) {
            throw new UnAuthorizedException("로그인이 필요합니다");
        }

        if(bindingResult.hasErrors()) {
            throw new BadRequestException("입력값을 확인 해 주세요");
        }

        Comment updatedComment = commentService.update(id, commentUpdateDTO, user);

        HashMap<String, Object> response = new HashMap<>();
        response.put("result", "성공");
        response.put("comment", updatedComment);

        return ResponseEntity.ok().body(response);
    }
//
//    @DeleteMapping("/{commentId}")
//    public ResponseEntity<Object> deleteComment(@PathVariable("commentId") Integer id,
//                                                @Login User user) throws Exception {
//
//        if(user == null) {
//            throw new UnAuthorizedException("로그인이 필요합니다");
//        }
//
//        Comment deletedComment = commentService.delete(id, user);
//
//        HashMap<String, Object> response = new HashMap<>();
//        response.put("result", "성공");
//        response.put("comment", deletedComment);
//
//        return ResponseEntity.ok().body(response);
//    }
}
