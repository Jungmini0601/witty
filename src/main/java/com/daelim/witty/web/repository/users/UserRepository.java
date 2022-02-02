package com.daelim.witty.web.repository.users;

import com.daelim.witty.domain.User;

import java.util.Optional;

public interface UserRepository {
    /**
      @Param User 저장할 user 객체
      @Return true 저장성공, false 저장실패
     */
    public boolean save(User user);

    public Optional<User> findById(String id);
}
