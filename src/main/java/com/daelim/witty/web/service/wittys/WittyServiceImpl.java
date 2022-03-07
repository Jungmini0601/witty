package com.daelim.witty.web.service.wittys;

import com.daelim.witty.domain.v1.User;
import com.daelim.witty.domain.v1.Witty;
import com.daelim.witty.web.controller.v1.dto.wittys.CreateWittyDTO;
import com.daelim.witty.web.controller.v1.dto.wittys.UpdateWittyDTO;
import com.daelim.witty.web.exception.BadRequestException;
import com.daelim.witty.web.exception.ForbbiddenException;
import com.daelim.witty.web.repository.wittys.WittyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class WittyServiceImpl implements WittyService{

    private final WittyRepository wittyRepository;

    @Override
    public Witty writeWitty(CreateWittyDTO createWittyDTO, User user) throws Exception{
        return wittyRepository.save(createWittyDTO, user);
    }

    @Override
    public Witty updateWitty(Integer wittyId, UpdateWittyDTO updateWittyDTO, User user) throws Exception {
        Optional<Witty> wittyOptional = wittyRepository.findById(wittyId);

        if(wittyOptional.isEmpty()) throw new BadRequestException("witty id에 해당하는 글을 찾을 수 없습니다.");

        Witty witty = wittyOptional.get();

        if(!witty.getUserId().equals(user.getId())) throw new ForbbiddenException("작성자만 수정 할 수 있습니다");

        return wittyRepository.update(wittyId, updateWittyDTO, user);
    }

    @Override
    public Witty deleteWitty(Integer wittyId, User user) throws Exception {
        Optional<Witty> wittyOptional = wittyRepository.findById(wittyId);

        if(wittyOptional.isEmpty()) throw new BadRequestException("witty id에 해당하는 글을 찾을 수 없습니다.");

        Witty witty = wittyOptional.get();

        if(!witty.getUserId().equals(user.getId())) throw new ForbbiddenException("작성자만 삭제 할 수 있습니다");

        return wittyRepository.delete(wittyId, user);
    }

}
