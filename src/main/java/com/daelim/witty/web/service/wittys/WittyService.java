package com.daelim.witty.web.service.wittys;


import com.daelim.witty.domain.v1.User;
import com.daelim.witty.domain.v1.Witty;
import com.daelim.witty.web.controller.dto.wittys.CreateWittyDTO;
import com.daelim.witty.web.controller.dto.wittys.UpdateWittyDTO;

public interface WittyService {
    public Witty writeWitty(CreateWittyDTO createWittyDTO, User user) throws Exception;

    public Witty updateWitty(Integer wittyId,UpdateWittyDTO updateWittyDTO, User user) throws Exception;

    public Witty deleteWitty(Integer wittyId, User user) throws Exception;
}
