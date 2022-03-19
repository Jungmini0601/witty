package com.daelim.witty.v2.web.repository.users;

import com.daelim.witty.v2.domain.User;
import com.daelim.witty.v2.domain.Witty;
import com.daelim.witty.v2.domain.WittyLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WittyLikeRepository extends JpaRepository<WittyLike, Long> {
    Optional<WittyLike> findByWittyAndUser(Witty witty, User user);
}
