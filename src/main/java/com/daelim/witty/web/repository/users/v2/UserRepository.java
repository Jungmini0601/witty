package com.daelim.witty.web.repository.users.v2;

import com.daelim.witty.domain.v2.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
