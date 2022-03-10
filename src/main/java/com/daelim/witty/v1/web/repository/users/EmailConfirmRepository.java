package com.daelim.witty.v1.web.repository.users;

import com.daelim.witty.v1.domain.EmailConfrim;

import java.util.Optional;

public interface EmailConfirmRepository {

    public boolean save(EmailConfrim emailConfrim);

    public Optional<EmailConfrim> findByEmail(String email);
}
