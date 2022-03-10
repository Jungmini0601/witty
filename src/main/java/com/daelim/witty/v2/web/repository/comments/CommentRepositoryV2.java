package com.daelim.witty.v2.web.repository.comments;

import com.daelim.witty.v2.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepositoryV2 extends JpaRepository<Comment, Long> {
}
