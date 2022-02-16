package com.daelim.witty.web.repository.comments;

import com.daelim.witty.domain.Comment;
import com.daelim.witty.domain.User;
import com.daelim.witty.domain.Witty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepositoryImpl implements CommentRepository{
    private final JdbcTemplate jdbcTemplate;

    public CommentRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * @Param comment: 저장될 댓글
     * @Param witty: 댓글이 달릴 witty
     * @Param user: 요청을 보낸 user
     * @Return 저장된 comment
     * */
    @Override
    public Comment save(Comment comment, Witty witty, User user) throws Exception {
        return null;
    }

    /**
     * @Param comment: 수정될 댓글
     * @Param user: 요청을 보낸 user
     * @Return 수정된 comment
     * */
    @Override
    public Comment update(Comment comment, User user) throws Exception {
        return null;
    }

    /**
     * @Param comment: 삭제될 댓글
     * @Param user: 요청을 보낸 user
     * @Return 삭제된 comment
     * */
    @Override
    public Comment delete(Comment comment, User user) throws Exception {
        return null;
    }

    /**
     * @Param wittyId: 위티 번호
     * @Return 위티번호값이 가지는 댓글들의 리스트
     * */
    @Override
    public List<Comment> findByWittyId(Integer wittyId) throws Exception {
        return null;
    }

    /**
     * @Param wittyId: 댓글 번호
     * @Return 댓글 Optional 한개만 반환
     * */
    @Override
    public Optional<Comment> findByCommentId(Integer commentId) throws Exception {
        return Optional.empty();
    }
}
