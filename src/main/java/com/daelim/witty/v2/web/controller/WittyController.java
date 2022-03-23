package com.daelim.witty.v2.web.controller;


import com.daelim.witty.v2.domain.User;
import com.daelim.witty.v2.domain.Witty;
import com.daelim.witty.v2.web.argumentResolver.Login;
import com.daelim.witty.v2.web.controller.dto.wittys.*;
import com.daelim.witty.v2.web.exception.BadRequestException;
import com.daelim.witty.v2.web.exception.ForbbiddenException;
import com.daelim.witty.v2.web.service.wittys.WittyServiceV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("/v2/wittys")
@RequiredArgsConstructor
@RestController
public class WittyController {

    private final WittyServiceV2 wittyService;

    //TODO 조회 성능 최적화 필요 할 수도 있음.
    @GetMapping
    public List<GetWittyResponse> find(@RequestParam("page") Integer page, @RequestParam("size") Integer size, @Login User user) throws Exception{
        List<Witty> witties = wittyService.findAll(page, size);
        return witties.stream().map(witty -> GetWittyResponse.success(witty, user)).collect(Collectors.toList());
    }

    //위티 생성
    @PostMapping
    public ResponseEntity<Object> createWitty(@RequestBody @Validated CreateWittyRequest createWittyRequest, BindingResult bindingResult,
                                              @Login User user) throws Exception{
        if(user == null) {
            throw new ForbbiddenException("로그인이 필요합니다!");
        }

        if(bindingResult.hasErrors()) {
            throw new BadRequestException("입력값 확인 필요");
        }

        Witty witty = wittyService.writeWitty(createWittyRequest, user);
        CreateWittyResponse wittyResponse = CreateWittyResponse.success(witty);
        HashMap<String, Object> response = new HashMap<>();
        response.put("result", "성공");
        response.put("witty", wittyResponse);

        return ResponseEntity.ok(response);
    }

    // 위티 수정
    @PatchMapping("/{wittyId}")
    public ResponseEntity<Object> updateWitty(@PathVariable Long wittyId, @Login User user,
                                              @RequestBody @Validated UpdateWittyRequest updateWittyRequest, BindingResult bindingResult) throws Exception{

        if(user == null) {
            throw new ForbbiddenException("로그인이 필요합니다!");
        }

        if(bindingResult.hasErrors()) {
            throw new BadRequestException("입력값 확인 필요");
        }

        Witty witty = wittyService.updateWitty(wittyId, updateWittyRequest, user);
        UpdateWittyResponse updateWittyResponse = UpdateWittyResponse.success(witty);
        HashMap<String, Object> response = new HashMap<>();
        response.put("result", "성공");
        response.put("witty", updateWittyResponse);
        return ResponseEntity.ok(response);
    }

    // 위티 삭제
    @DeleteMapping("/{wittyId}")
    public ResponseEntity<Object> deleteWitty(@PathVariable Long wittyId, @Login User user) throws Exception{

        if(user == null) {
            throw new ForbbiddenException("로그인이 필요합니다!");
        }

        Witty witty = wittyService.deleteWitty(wittyId, user);

        DeleteWittyResponse deleteWittyResponse = DeleteWittyResponse.success(witty);
        HashMap<String, Object> response = new HashMap<>();

        response.put("result", "성공");
        response.put("witty", deleteWittyResponse);

        return ResponseEntity.ok(response);
    }
}
