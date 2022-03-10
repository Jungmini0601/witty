package com.daelim.witty.web.repository.comments.v2;

import com.daelim.witty.domain.v2.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepositoryV2 extends JpaRepository<Comment, Long> {
}
