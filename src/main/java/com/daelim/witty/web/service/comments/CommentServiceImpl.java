package com.daelim.witty.web.service.comments;

import com.daelim.witty.domain.Comment;
import com.daelim.witty.domain.User;
import com.daelim.witty.web.repository.comments.CommentRepository;
import com.daelim.witty.web.repository.wittys.WittyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final WittyRepository wittyRepository;

    /**
     * 진솔이형
     * @Param comment: 저장할 댓글
     * @Param user: 요청 보낸 유저
     * */
    @Override
    public Comment save(Comment comment, Integer wittyId, User user) throws Exception {
        return null;
    }

    /**
     * 요환이
     * @Param comment: 수정할 댓글(수정되어서 넘어온 데이터)
     * @Param user: 요청 보낸 유저
     * */
    @Override
    public Comment update(Comment comment, User user) throws Exception {
        return comment;
    }
    /**
     * 요환이
     * @Param comment: 수정할 댓글(수정되어서 넘어온 데이터)
     * @Param user: 요청 보낸 유저
     * */
    @Override
    public Comment delete(Integer id, User user) throws Exception {
        return null;
    }
}
