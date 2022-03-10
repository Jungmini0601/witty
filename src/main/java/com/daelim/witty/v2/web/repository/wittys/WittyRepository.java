package com.daelim.witty.v2.web.repository.wittys;

import com.daelim.witty.v2.domain.Witty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WittyRepository extends JpaRepository<Witty, Long> {
}
