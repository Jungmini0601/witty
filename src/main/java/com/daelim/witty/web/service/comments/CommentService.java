package com.daelim.witty.web.service.comments;


import com.daelim.witty.domain.Comment;
import com.daelim.witty.domain.User;
import com.daelim.witty.domain.Witty;
import com.daelim.witty.web.controller.dto.comments.CommentUpdateDTO;
import com.daelim.witty.web.controller.dto.wittys.CreateWittyDTO;
import com.daelim.witty.web.controller.dto.wittys.UpdateWittyDTO;

public interface CommentService {
    // 생성
    public Comment save(Comment comment,Integer wittyId, User user) throws Exception;
    // 수정
    public Comment update(Integer id, CommentUpdateDTO commentUpdateDTO, User user) throws Exception;
    // 삭제
    public Comment delete(Integer id, User user) throws Exception;

}
