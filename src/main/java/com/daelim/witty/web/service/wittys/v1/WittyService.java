package com.daelim.witty.web.service.wittys.v1;


import com.daelim.witty.domain.v1.User;
import com.daelim.witty.domain.v1.Witty;
import com.daelim.witty.web.controller.v1.dto.wittys.CreateWittyDTO;
import com.daelim.witty.web.controller.v1.dto.wittys.UpdateWittyDTO;

public interface WittyService {
    public Witty writeWitty(CreateWittyDTO createWittyDTO, User user) throws Exception;

    public Witty updateWitty(Integer wittyId,UpdateWittyDTO updateWittyDTO, User user) throws Exception;

    public Witty deleteWitty(Integer wittyId, User user) throws Exception;
}
