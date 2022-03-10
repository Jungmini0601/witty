package com.daelim.witty.v2.web.service.wittys;

import com.daelim.witty.v2.domain.Tag;
import com.daelim.witty.v2.domain.User;
import com.daelim.witty.v2.domain.Witty;
import com.daelim.witty.v2.web.controller.dto.wittys.CreateWittyRequest;
import com.daelim.witty.v2.web.controller.dto.wittys.UpdateWittyRequest;
import com.daelim.witty.v2.web.exception.BadRequestException;
import com.daelim.witty.v2.web.repository.wittys.TagRepository;
import com.daelim.witty.v2.web.repository.wittys.WittyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
@Service
public class WittyServiceImplV2 implements WittyServiceV2 {
    private final WittyRepository wittyRepository;
    private final TagRepository tagRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Witty writeWitty(CreateWittyRequest createWittyRequest, User user) throws Exception {
        Witty witty = Witty.createWitty(user, createWittyRequest);
        return wittyRepository.save(witty);
    }


    //TODO 성능이슈있어서 나중에 해결 해야함.
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Witty updateWitty(Long wittyId, UpdateWittyRequest updateWittyRequest, User user) throws Exception {
        Optional<Witty> wittyOptional = wittyRepository.findById(wittyId);

        if (wittyOptional.isEmpty()) throw new BadRequestException("입력값 확인 필요");

        Witty witty = wittyOptional.get();
        tagRepository.deleteAllById(witty.getTags().stream().map(Tag::getId).collect(Collectors.toList()));
        witty.update(updateWittyRequest);
        return witty;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Witty deleteWitty(Long wittyId, User user) throws Exception {
        Optional<Witty> wittyOptional = wittyRepository.findById(wittyId);

        if (wittyOptional.isEmpty()) throw new BadRequestException("입력값 확인 필요");

        wittyRepository.deleteById(wittyId);
        return wittyOptional.get();
    }

    public Page<Witty> findAllWittys(Pageable pageable)throws Exception {
        return wittyRepository.findAll(pageable);
    }
}
