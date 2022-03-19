package com.daelim.witty.v2.web.repository.users;

import com.daelim.witty.v2.domain.Follow;
import com.daelim.witty.v2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFromUserAndToUser(User from, User to);
}
