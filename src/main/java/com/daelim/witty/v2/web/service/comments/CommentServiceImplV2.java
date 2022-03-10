package com.daelim.witty.v2.web.service.comments;


import com.daelim.witty.v2.domain.Comment;
import com.daelim.witty.v2.domain.User;
import com.daelim.witty.v2.domain.Witty;
import com.daelim.witty.v2.web.controller.dto.comments.CreateCommentRequest;
import com.daelim.witty.v2.web.controller.dto.comments.UpdateCommentRequest;
import com.daelim.witty.v2.web.exception.BadRequestException;
import com.daelim.witty.v2.web.exception.ForbbiddenException;
import com.daelim.witty.v2.web.repository.comments.CommentRepositoryV2;
import com.daelim.witty.v2.web.repository.wittys.WittyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentServiceImplV2 implements CommentServiceV2 {

    private final CommentRepositoryV2 commentRepository;
    private final WittyRepository wittyRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Comment save(CreateCommentRequest createCommentRequest, User user) throws Exception {
        Optional<Witty> wittyOptional = wittyRepository.findById(createCommentRequest.getWittyId());

        if (wittyOptional.isEmpty()) throw new BadRequestException("입력값을 확인 해 주세요");

        Witty witty = wittyOptional.get();
        Comment comment = Comment.createComment(createCommentRequest, witty, user);
        return commentRepository.save(comment);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Comment update(Long id, UpdateCommentRequest updateCommentRequest, User user) throws Exception {
        Optional<Comment> commentOptional = commentRepository.findById(id);

        if(commentOptional.isEmpty()) throw new BadRequestException("입력값을 확인 해 주세요");

        Comment comment = commentOptional.get();

        if(!comment.getUser().getId().equals(user.getId())) throw new ForbbiddenException("작성자만 수정 할 수 있습니다.");

        comment.updateComment(updateCommentRequest);
        return comment;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Comment delete(Long id, User user) throws Exception {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if(commentOptional.isEmpty()) throw new BadRequestException("입력값을 확인 해 주세요");
        Comment comment = commentOptional.get();
        commentRepository.delete(comment);
        return comment;
    }
}
