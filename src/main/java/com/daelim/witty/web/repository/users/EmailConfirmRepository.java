package com.daelim.witty.web.repository.users;

import com.daelim.witty.domain.v1.EmailConfrim;

import java.util.Optional;

public interface EmailConfirmRepository {

    public boolean save(EmailConfrim emailConfrim);

    public Optional<EmailConfrim> findByEmail(String email);
}
