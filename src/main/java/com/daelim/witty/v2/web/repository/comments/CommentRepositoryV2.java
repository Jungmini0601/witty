package com.daelim.witty.v2.web.repository.comments;

import com.daelim.witty.v2.domain.Comment;
import com.daelim.witty.v2.domain.Witty;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepositoryV2 extends JpaRepository<Comment, Long> {
    List<Comment> findByWittyOrderByCreatedDateTimeDesc(Witty witty, Pageable pageable);
}
