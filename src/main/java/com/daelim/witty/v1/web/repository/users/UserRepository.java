package com.daelim.witty.v1.web.repository.users;



import com.daelim.witty.v1.domain.User;

import java.util.Optional;

public interface UserRepository {
    /**
      @Param User 저장할 user 객체
      @Return true 저장성공, false 저장실패
     */
    public User save(User user);

    public Optional<User> findById(String id);
}
