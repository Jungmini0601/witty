package com.daelim.witty.v2.web.repository.users;

import com.daelim.witty.v2.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
