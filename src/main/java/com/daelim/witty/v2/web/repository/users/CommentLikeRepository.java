package com.daelim.witty.v2.web.repository.users;

import com.daelim.witty.v2.domain.Comment;
import com.daelim.witty.v2.domain.CommentLike;
import com.daelim.witty.v2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByCommentAndUser(Comment comment, User user);
}
