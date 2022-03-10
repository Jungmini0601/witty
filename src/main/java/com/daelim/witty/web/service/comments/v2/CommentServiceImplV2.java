package com.daelim.witty.web.service.comments.v2;

import com.daelim.witty.domain.v2.Comment;
import com.daelim.witty.domain.v2.User;
import com.daelim.witty.domain.v2.Witty;
import com.daelim.witty.web.controller.v2.dto.comments.CreateCommentRequest;
import com.daelim.witty.web.exception.BadRequestException;
import com.daelim.witty.web.repository.comments.v2.CommentRepositoryV2;
import com.daelim.witty.web.repository.wittys.v2.WittyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImplV2 implements CommentServiceV2{

    private final CommentRepositoryV2 commentRepository;
    private final WittyRepository wittyRepository;

    @Override
    public Comment save(CreateCommentRequest createCommentRequest, User user) throws Exception {
        Optional<Witty> wittyOptional = wittyRepository.findById(createCommentRequest.getWittyId());

        if (wittyOptional.isEmpty()) throw new BadRequestException("입력값을 확인 해 주세요");

        Witty witty = wittyOptional.get();
        Comment comment = Comment.createComment(createCommentRequest, witty, user);
        return commentRepository.save(comment);
    }
}
