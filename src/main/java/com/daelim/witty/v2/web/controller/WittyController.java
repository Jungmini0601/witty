package com.daelim.witty.v2.web.controller;


import com.daelim.witty.v2.domain.User;
import com.daelim.witty.v2.domain.Witty;
import com.daelim.witty.v2.web.argumentResolver.Login;
import com.daelim.witty.v2.web.controller.dto.wittys.*;
import com.daelim.witty.v2.web.exception.BadRequestException;
import com.daelim.witty.v2.web.exception.ForbbiddenException;
import com.daelim.witty.v2.web.exception.UnAuthorizedException;
import com.daelim.witty.v2.web.service.users.UserServiceV2;
import com.daelim.witty.v2.web.service.wittys.WittyServiceV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("/v2/wittys")
@RequiredArgsConstructor
@RestController
public class WittyController {

    private final WittyServiceV2 wittyService;
    @Value("${wittyThumbnail.path}")
    private String thumbnailUploadFolder;

    @GetMapping
    public List<GetWittyResponse> find(@RequestParam("page") Integer page, @RequestParam("size") Integer size, @Login User user) throws Exception{
        if (user == null) throw new UnAuthorizedException("로그인이 필요합니다");
        List<Witty> witties = wittyService.findAll(page, size, user);
        return witties.stream().map(witty -> GetWittyResponse.success(witty, user)).collect(Collectors.toList());
    }

    @GetMapping(value = "image/{imagename}")
    public ResponseEntity<Resource> showUserImage(@PathVariable("imagename") String imageName) {
        // 사진이 저장된 폴더 경로 변수 선언
        String imageRoot = thumbnailUploadFolder;

        // 서버 로컬 경로 + 파일 명 저장 실시
        imageRoot = imageRoot + imageName;

        // Resorce를 사용해서 로컬 서버에 저장된 이미지 경로 및 파일 명을 지정
        Resource resource = new FileSystemResource(imageRoot);

        // 로컬 서버에 저장된 이미지 파일이 없을 경우
        if(!resource.exists()){
            return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND); // 리턴 결과 반환 404
        }

        // 로컬 서버에 저장된 이미지가 있는 경우 로직 처리
        HttpHeaders header = new HttpHeaders();
        Path filePath = null;
        try {
            filePath = Paths.get(imageRoot);
            // 인풋으로 들어온 파일명 .png / .jpg 에 맞게 헤더 타입 설정
            header.add("Content-Type", Files.probeContentType(filePath));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
    }

   @GetMapping("/byTag")
    public List<GetWittyResponse> findWittyByTags(@RequestParam("page") Integer page, @RequestParam("size") Integer size,
                                                  @RequestParam("tag") String tag,
                                                  @Login User user) throws Exception{
        if (user == null) throw new UnAuthorizedException("로그인이 필요합니다");
        List<Witty> witties = wittyService.findAll(page, size, tag);
        return witties.stream().map(witty -> GetWittyResponse.success(witty, user)).collect(Collectors.toList());
    }

    //위티 생성
    @PostMapping
    public ResponseEntity<Object> createWitty(@ModelAttribute @Validated CreateWittyRequest createWittyRequest, BindingResult bindingResult,
                                              @RequestParam("thumbnailImgUri") MultipartFile file,
                                              @Login User user) throws Exception{
        if(user == null) {
            throw new ForbbiddenException("로그인이 필요합니다!");
        }

        if(bindingResult.hasErrors()) {
            throw new BadRequestException("입력값 확인 필요");
        }

        //TODO 응답스펙 바꿔야 함
        Witty witty = wittyService.writeWitty(createWittyRequest, user, file);
        CreateWittyResponse wittyResponse = CreateWittyResponse.success(witty);
        HashMap<String, Object> response = new HashMap<>();
        response.put("result", "성공");
        response.put("witty", wittyResponse);

        return ResponseEntity.ok(response);
    }

    // 위티 수정
    @PatchMapping("/{wittyId}")
    public ResponseEntity<Object> updateWitty(@PathVariable Long wittyId, @Login User user,
                                              @ModelAttribute @Validated UpdateWittyRequest updateWittyRequest,
                                              BindingResult bindingResult,
                                              @RequestParam("thumbnailImgUri") MultipartFile file) throws Exception{

        if(user == null) {
            throw new ForbbiddenException("로그인이 필요합니다!");
        }

        if(bindingResult.hasErrors()) {
            throw new BadRequestException("입력값 확인 필요");
        }

        Witty witty = wittyService.updateWitty(wittyId, updateWittyRequest, user, file);
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
