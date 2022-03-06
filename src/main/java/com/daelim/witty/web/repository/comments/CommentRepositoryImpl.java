package com.daelim.witty.web.repository.comments;

import com.daelim.witty.domain.v1.Comment;
import com.daelim.witty.domain.v1.User;
import com.daelim.witty.domain.v1.Witty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
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
        comment.setCreatedAt(comment.getCreatedAt());

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("witty_comment").usingGeneratedKeyColumns("witty_comment_id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("witty_comment_id",comment.getId());
        parameters.put("user_id",comment.getUserId());
        parameters.put("witty_id",comment.getWittyId());
        parameters.put("witty_comment_content",comment.getContent());
        parameters.put("witty_comment_created_date",comment.getCreatedAt());

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
        String sql = "update witty_comment set witty_comment_content = ? where witty_comment_id = ?";

        try {
            jdbcTemplate.update(sql, comment.getContent(), comment.getId());
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
        String sql = "delete from witty_comment where witty_comment_id = ?";
        Comment deletedComment = findByCommentId(comment.getId()).get();

        try{
            jdbcTemplate.update(sql, comment.getId());
        } catch (Exception e) {
            throw new Exception("DB 에러!");
        }

        return deletedComment;
    }

    /**
     * @Param wittyId: 위티 번호
     * @Return 위티번호값이 가지는 댓글들의 리스트
     * */
    @Override
    public List<Comment> findByWittyId(Integer wittyId) throws Exception {
        String sql = "select * from witty_comment where witty_id = ?";

        List<Comment> comment = jdbcTemplate.query(sql, commentRowMapper(), wittyId);

        return comment;
    }

    /**
     * @Param wittyId: 댓글 번호
     * @Return 댓글 Optional 한개만 반환
     * */
    @Override
    public Optional<Comment> findByCommentId(Integer commentId) throws Exception {
        String sql = "select * from witty_comment where witty_comment_id = ?";

        List<Comment> optionalComment = jdbcTemplate.query(sql, commentRowMapper(), commentId);

        return optionalComment.stream().findAny();
    }

    private RowMapper<Comment> commentRowMapper() {
        return new RowMapper<Comment>() {
            @Override
            public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
                Comment comment = new Comment();

                comment.setId(rs.getInt("witty_comment_id"));
                comment.setUserId(rs.getString("user_id"));
                comment.setWittyId(rs.getInt("witty_id"));
                comment.setContent(rs.getString("witty_comment_content"));
                comment.setCreatedAt(rs.getTimestamp("witty_comment_created_date"));

                return comment;
            }
        };
    }
}
