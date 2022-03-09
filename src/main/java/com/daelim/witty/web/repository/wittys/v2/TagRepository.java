package com.daelim.witty.web.repository.wittys.v2;

import com.daelim.witty.domain.v2.Tag;
import com.daelim.witty.domain.v2.Witty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
