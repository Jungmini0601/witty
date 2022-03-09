package com.daelim.witty.web.controller.v2;

import com.daelim.witty.domain.v2.User;
import com.daelim.witty.domain.v2.Witty;
import com.daelim.witty.web.argumentResolver.Login;
import com.daelim.witty.web.controller.v2.dto.wittys.*;
import com.daelim.witty.web.exception.BadRequestException;
import com.daelim.witty.web.exception.ForbbiddenException;
import com.daelim.witty.web.service.wittys.v2.WittyServiceV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping
    public List<GetWittyResponse> find(Pageable pageable) throws Exception{
        Page<Witty> wittyPage = wittyService.findAllWittys(pageable);

        List<GetWittyResponse> wittyResponses = wittyPage.getContent().stream().map(GetWittyResponse::success).collect(Collectors.toList());

        return wittyResponses;
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
