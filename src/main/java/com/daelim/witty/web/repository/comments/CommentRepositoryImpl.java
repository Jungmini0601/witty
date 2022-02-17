package com.daelim.witty.web.repository.comments;

import com.daelim.witty.domain.Comment;
import com.daelim.witty.domain.User;
import com.daelim.witty.domain.Witty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Number key = null;
        //댓글 내용 저장
        comment.setId(comment.getId()); //댓글에 쓸 아이디
        comment.setUserId(user.getId()); //요청 보낸 유저아이디 <-어떤 요청인지 정확히 전송에 쓸 요청인지
        comment.setWittyId(witty.getId());//댓글에 달릴 위티아이디
        comment.setContent(comment.getContent());
        comment.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("comment").usingGeneratedKeyColumns("comment_id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("comment_id",comment.getId());
        parameters.put("user_id",comment.getUserId());
        parameters.put("witty_id",comment.getWittyId());
        parameters.put("content",comment.getContent());
        parameters.put("comment_created_date",comment.getCreatedAt());

        try {
            key = jdbcInsert.executeAndReturnKey(parameters);
        }catch (Exception e) {
            throw new Exception("DB 에러");
        }
        return findByCommentId(key.intValue()).get();
    }

    /**
     * @Param comment: 수정될 댓글
     * @Param user: 요청을 보낸 user
     * @Return 수정된 comment
     * */
    @Override
    public Comment update(Comment comment, User user) throws Exception {
        String sql = "update comment set comment = ? where comment_id = ?, user_id = ?";

        try {
            jdbcTemplate.update(sql, comment.getContent(), comment);
        }catch (Exception e) {
            throw new Exception("DB 에러!");
        }

        return findByCommentId(comment.getId()).get();
    }

    /**
     * @Param comment: 삭제될 댓글
     * @Param user: 요청을 보낸 user
     * @Return 삭제된 comment
     * */
    @Override
    public Comment delete(Comment comment, User user) throws Exception {
        String sql = "delete from comment where comment_id = ?";

        try{
            jdbcTemplate.update(sql, comment.getContent(), comment);
        } catch (Exception e) {
            throw new Exception("DB 에러!");
        }

        return findByCommentId(comment.getId()).get();
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
