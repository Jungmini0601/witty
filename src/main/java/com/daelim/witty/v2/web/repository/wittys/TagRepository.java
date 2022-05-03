package com.daelim.witty.v2.web.repository.wittys;

import com.daelim.witty.v2.domain.Tag;
import com.daelim.witty.v2.domain.Witty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findAllByName(String name);
    void deleteAllByWitty(Witty witty);
}
