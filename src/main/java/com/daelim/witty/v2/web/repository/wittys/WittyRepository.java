package com.daelim.witty.v2.web.repository.wittys;

import com.daelim.witty.v2.domain.Tag;
import com.daelim.witty.v2.domain.User;
import com.daelim.witty.v2.domain.Witty;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface WittyRepository extends JpaRepository<Witty, Long> {

    List<Witty> findWittyByUserIn(List<User> users, Pageable pageable);

    List<Witty> findAllByTagsIn(List<Tag> tags, Pageable pageable);

    @Query(value = "SELECT AUTO_INCREMENT\n" +
            "FROM information_schema.TABLES\n" +
            "WHERE TABLE_SCHEMA = \"daelim_witty\"\n" +
            "AND TABLE_NAME = \"witty\"", nativeQuery = true)
    Long nextId();
}
