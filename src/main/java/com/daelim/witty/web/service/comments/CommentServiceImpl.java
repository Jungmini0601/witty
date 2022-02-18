package com.daelim.witty.web.service.comments;

import com.daelim.witty.domain.Comment;
import com.daelim.witty.domain.User;
import com.daelim.witty.domain.Witty;
import com.daelim.witty.web.controller.dto.comments.CommentUpdateDTO;
import com.daelim.witty.web.exception.BadRequestException;
import com.daelim.witty.web.exception.ForbbiddenException;
import com.daelim.witty.web.repository.comments.CommentRepository;
import com.daelim.witty.web.repository.users.UserRepository;
import com.daelim.witty.web.repository.wittys.WittyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final WittyRepository wittyRepository;
    private final UserRepository userRepository;

    /**
     * 진솔이형
     * @Param comment: 저장할 댓글
     * @Param user: 요청 보낸 유저
     * */
    @Override
    public Comment save(Comment comment, Integer wittyId, User user) throws Exception {
        //User에 userId값 가져오기
        Witty findWitty = wittyRepository.findById(wittyId).get();
        //commentRepository에서 save 호출 후 반환하기
        return commentRepository.save(comment,findWitty,user);
    }

    /**
     * 요환이
     * @Param id: 수정할 댓글 번호
     * @Param coomentUpdateDTO 수정할 내용 content
     * @Param user: 요청 보낸 유저
     * */
    @Override
    public Comment update(Integer id, CommentUpdateDTO commentUpdateDTO, User user) throws Exception {
        Optional<Comment> optionalComment = commentRepository.findByCommentId(id);

        if(optionalComment.isEmpty())throw new BadRequestException("해당하는 댓글을 찾을 수 없습니다.");

        Comment comment = optionalComment.get();

        if(!comment.getUserId().equals(user.getId())) throw new ForbbiddenException("작성자만 수정 할 수 있습니다");

        comment.setContent(commentUpdateDTO.getContent());

        return commentRepository.update(comment,user);
    }
    /**
     * 요환이
     * @Param id: 수정할 댓글 번호
     * @Param user: 요청 보낸 유저
     * */
    @Override
    public Comment delete(Integer id, User user) throws Exception {
        Optional<Comment> optionalComment = commentRepository.findByCommentId(id);

        if(optionalComment.isEmpty())throw new BadRequestException("해당하는 댓글을 찾을 수 없습니다.");

        Comment comment = optionalComment.get();

        if(!comment.getUserId().equals(user.getId())) throw new ForbbiddenException("작성자만 수정 할 수 있습니다");

        return commentRepository.delete(comment,user);
    }
}