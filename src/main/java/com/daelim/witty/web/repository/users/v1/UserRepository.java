package com.daelim.witty.web.repository.users.v1;

import com.daelim.witty.domain.v1.User;

import java.util.Optional;

public interface UserRepository {
    /**
      @Param User 저장할 user 객체
      @Return true 저장성공, false 저장실패
     */
    public User save(User user);

    public Optional<User> findById(String id);
}
