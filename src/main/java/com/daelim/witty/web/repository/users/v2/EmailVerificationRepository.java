package com.daelim.witty.web.repository.users.v2;

import com.daelim.witty.domain.v2.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification, String> {

    Optional<EmailVerification> findByEmail(String email);
}
