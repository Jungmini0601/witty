package com.daelim.witty.web.repository.user;

import com.daelim.witty.domain.EmailConfrim;

import java.util.Optional;

public interface EmailConfirmRepository {

    public boolean save(EmailConfrim emailConfrim);

    public Optional<EmailConfrim> findByEmail(String email);
}
