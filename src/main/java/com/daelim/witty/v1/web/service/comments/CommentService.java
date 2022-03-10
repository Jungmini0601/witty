package com.daelim.witty.v1.web.service.comments;

import com.daelim.witty.v1.domain.Comment;
import com.daelim.witty.v1.domain.User;
import com.daelim.witty.v1.web.controller.dto.comments.CommentUpdateDTO;


public interface CommentService {
    // 생성
    public Comment save(Comment comment, Integer wittyId, User user) throws Exception;
    // 수정
    public Comment update(Integer id, CommentUpdateDTO commentUpdateDTO, User user) throws Exception;
    // 삭제
    public Comment delete(Integer id, User user) throws Exception;

}
