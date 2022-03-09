package com.daelim.witty.web.repository.wittys.v2;

import com.daelim.witty.domain.v2.Witty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface WittyRepository extends JpaRepository<Witty, Long> {
}
