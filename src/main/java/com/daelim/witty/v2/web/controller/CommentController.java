package com.daelim.witty.v2.web.controller;


import com.daelim.witty.v2.domain.Comment;
import com.daelim.witty.v2.domain.User;
import com.daelim.witty.v2.domain.Witty;
import com.daelim.witty.v2.web.argumentResolver.Login;
import com.daelim.witty.v2.web.controller.dto.comments.*;
import com.daelim.witty.v2.web.controller.dto.wittys.GetWittyResponse;
import com.daelim.witty.v2.web.exception.BadRequestException;
import com.daelim.witty.v2.web.exception.UnAuthorizedException;
import com.daelim.witty.v2.web.service.comments.CommentServiceV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("/v2/comments")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentServiceV2 commentService;

    @GetMapping("/{wittyId}")
    public List<GetCommentResponse> find(@PathVariable Long wittyId,
                                         @RequestParam("page") Integer page,
                                         @RequestParam("size") Integer size) throws Exception{
        List<Comment> comments = commentService.getCommentList(wittyId, page, size);
        return comments.stream().map(GetCommentResponse::success).collect(Collectors.toList());
    }

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
    public ResponseEntity<Object> updateComment(@PathVariable("commentId") Long id,
                                                @RequestBody @Validated UpdateCommentRequest updateCommentRequest,
                                                BindingResult bindingResult,
                                                @Login User user) throws Exception {

        if(user == null) {
            throw new UnAuthorizedException("로그인이 필요합니다");
        }

        if(bindingResult.hasErrors()) {
            throw new BadRequestException("입력값을 확인 해 주세요");
        }

        Comment comment = commentService.update(id, updateCommentRequest, user);
        UpdateCommentResponse updateCommentResponse = UpdateCommentResponse.success(comment, user);

        HashMap<String, Object> response = new HashMap<>();
        response.put("result", "성공");
        response.put("comment", updateCommentResponse);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Object> deleteComment(@PathVariable("commentId") Long id,
                                                @Login User user) throws Exception {

        if(user == null) {
            throw new UnAuthorizedException("로그인이 필요합니다");
        }

        Comment comment = commentService.delete(id, user);

        DeleteCommentResponse deletedComment = DeleteCommentResponse.success(comment,user);

        HashMap<String, Object> response = new HashMap<>();
        response.put("result", "성공");
        response.put("comment", deletedComment);
        return ResponseEntity.ok().body(response);
    }
}
