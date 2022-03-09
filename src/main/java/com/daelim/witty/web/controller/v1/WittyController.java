package com.daelim.witty.web.controller.v1;

import com.daelim.witty.domain.v1.User;
import com.daelim.witty.domain.v1.Witty;
import com.daelim.witty.web.argumentResolver.Login;
import com.daelim.witty.web.controller.v1.dto.wittys.CreateWittyDTO;
import com.daelim.witty.web.controller.v1.dto.wittys.UpdateWittyDTO;
import com.daelim.witty.web.exception.BadRequestException;
import com.daelim.witty.web.exception.ForbbiddenException;
import com.daelim.witty.web.service.wittys.v1.WittyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Slf4j
@RequestMapping("/wittys")
@RequiredArgsConstructor
//@RestController
public class WittyController {

    private final WittyService wittyService;

    // xx
    // TODO 생성시 tag 테이블에 데이터 잘 넣어줘야 함
    /**
     *  위티 생성
     *  작성자: 김정민
     * */
    @PostMapping
    public HashMap<String, Object> createWitty(@RequestBody @Validated CreateWittyDTO createWittyDTO, BindingResult bindingResult, @Login User user) throws Exception{
        if(user == null) {
            throw new ForbbiddenException("로그인이 필요합니다!");
        }

        if(bindingResult.hasErrors()) {
            throw new BadRequestException("입력값 확인 필요");
        }

        Witty witty = wittyService.writeWitty(createWittyDTO, user);

        HashMap<String, Object> response = new HashMap<>();

        response.put("result", "성공");
        response.put("witty", witty);

        return response;
    }

    /**
     *  위티 수정
     *  작성자: 김정민
     * */
    @PatchMapping("/{wittyId}")
    public HashMap<String, Object> updateWitty(@PathVariable Integer wittyId, @Login User user,
                                               @RequestBody @Validated UpdateWittyDTO updateWittyDTO, BindingResult bindingResult) throws Exception{

        if(user == null) {
            throw new ForbbiddenException("로그인이 필요합니다!");
        }

        if(bindingResult.hasErrors()) {
            throw new BadRequestException("입력값 확인 필요");
        }

        Witty witty = wittyService.updateWitty(wittyId, updateWittyDTO, user);

        HashMap<String, Object> response = new HashMap<>();

        response.put("result", "성공");
        response.put("witty", witty);

        return response;
    }

    /**
     *  위티 삭제
     *  작성자: 김정민
     * */
    @DeleteMapping("/{wittyId}")
    public HashMap<String, Object> deleteWitty(@PathVariable Integer wittyId, @Login User user) throws Exception{

        if(user == null) {
            throw new ForbbiddenException("로그인이 필요합니다!");
        }

        Witty witty = wittyService.deleteWitty(wittyId, user);

        HashMap<String, Object> response = new HashMap<>();

        response.put("result", "성공");
        response.put("witty", witty);

        return response;
    }
}
