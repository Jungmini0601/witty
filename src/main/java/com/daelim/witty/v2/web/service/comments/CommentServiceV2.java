package com.daelim.witty.v2.web.service.comments;


import com.daelim.witty.v2.domain.Comment;
import com.daelim.witty.v2.domain.User;
import com.daelim.witty.v2.web.controller.dto.comments.CreateCommentRequest;
import com.daelim.witty.v2.web.controller.dto.comments.UpdateCommentRequest;

public interface CommentServiceV2 {
    // 생성
    Comment save(CreateCommentRequest createCommentRequest, User user) throws Exception;
    // 수정
    Comment update(Long id, UpdateCommentRequest updateCommentRequest, User user) throws Exception;
    // 삭제
    Comment delete(Long id, User user) throws Exception;

}
