package com.daelim.witty.v2.web.repository.users;


import com.daelim.witty.v2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
