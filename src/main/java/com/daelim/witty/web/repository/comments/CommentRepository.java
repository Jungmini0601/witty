package com.daelim.witty.web.repository.comments;

import com.daelim.witty.domain.Comment;
import com.daelim.witty.domain.User;
import com.daelim.witty.domain.Witty;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    //생성
    public Comment save(Comment comment, Witty witty, User user) throws Exception;
    //수정
    public Comment update(Comment comment, User user) throws Exception;
    //삭제
    public Comment delete(Comment comment, User user) throws Exception;
    //조회
    public List<Comment> findByWittyId(Integer wittyId) throws Exception;
    //조회 1개만
    public Optional<Comment> findByCommentId(Integer commentId) throws Exception;
}
