package com.daelim.witty.web.service.comments.v2;


import com.daelim.witty.domain.v2.Comment;
import com.daelim.witty.domain.v2.User;
import com.daelim.witty.web.controller.v1.dto.comments.CommentUpdateDTO;
import com.daelim.witty.web.controller.v2.dto.comments.CreateCommentRequest;
import com.daelim.witty.web.controller.v2.dto.comments.UpdateCommentRequest;

public interface CommentServiceV2 {
    // 생성
    Comment save(CreateCommentRequest createCommentRequest, User user) throws Exception;
    // 수정
    Comment update(Long id, UpdateCommentRequest updateCommentRequest, User user) throws Exception;
    // 삭제
    Comment delete(Long id, User user) throws Exception;

}
