package com.daelim.witty.v2.web.service.wittys;



import com.daelim.witty.v2.domain.User;
import com.daelim.witty.v2.domain.Witty;
import com.daelim.witty.v2.web.controller.dto.wittys.CreateWittyRequest;
import com.daelim.witty.v2.web.controller.dto.wittys.UpdateWittyRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WittyServiceV2 {
    Witty writeWitty(CreateWittyRequest createWittyRequest, User user) throws Exception;

    Witty updateWitty(Long wittyId, UpdateWittyRequest updateWittyRequest, User user) throws Exception;

    Witty deleteWitty(Long wittyId, User user) throws Exception;

    List<Witty> findAll(Integer page, Integer size) throws Exception;
}
