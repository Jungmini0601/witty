package com.daelim.witty.v2.web.repository.wittys;

import com.daelim.witty.v2.domain.Tag;
import com.daelim.witty.v2.domain.User;
import com.daelim.witty.v2.domain.Witty;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WittyRepository extends JpaRepository<Witty, Long> {

    @Query("select w from Witty w where w.user IN :following")
    List<Witty> findWittyByFollowing(@Param("following") List<User> followings, Pageable pageable);

    List<Witty> findAllByTagsIn(List<Tag> tags, Pageable pageable);
}
