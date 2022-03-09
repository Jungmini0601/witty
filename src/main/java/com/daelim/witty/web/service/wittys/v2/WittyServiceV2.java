package com.daelim.witty.web.service.wittys.v2;


import com.daelim.witty.domain.v2.User;
import com.daelim.witty.domain.v2.Witty;
import com.daelim.witty.web.controller.v2.dto.wittys.CreateWittyRequest;
import com.daelim.witty.web.controller.v2.dto.wittys.UpdateWittyRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WittyServiceV2 {
    Witty writeWitty(CreateWittyRequest createWittyRequest, User user) throws Exception;

    Witty updateWitty(Long wittyId, UpdateWittyRequest updateWittyRequest, User user) throws Exception;

    Witty deleteWitty(Long wittyId, User user) throws Exception;

    Page<Witty> findAllWittys(Pageable pageable) throws Exception;
}
