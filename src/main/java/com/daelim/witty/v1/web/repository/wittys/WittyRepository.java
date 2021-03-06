package com.daelim.witty.v1.web.repository.wittys;



import com.daelim.witty.v1.domain.User;
import com.daelim.witty.v1.domain.Witty;
import com.daelim.witty.v1.web.controller.dto.wittys.CreateWittyDTO;
import com.daelim.witty.v1.web.controller.dto.wittys.UpdateWittyDTO;

import java.util.Optional;

public interface WittyRepository {

    public Witty save(CreateWittyDTO createWittyDTO, User user) throws Exception;

    public Witty update(Integer id, UpdateWittyDTO wittyDTO, User user) throws Exception;

    public Witty delete(Integer id, User user) throws Exception;

    public Optional<Witty> findById(Integer id) throws Exception;
}
