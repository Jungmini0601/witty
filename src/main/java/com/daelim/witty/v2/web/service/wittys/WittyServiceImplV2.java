package com.daelim.witty.v2.web.service.wittys;

import com.daelim.witty.v2.domain.Follow;
import com.daelim.witty.v2.domain.Tag;
import com.daelim.witty.v2.domain.User;
import com.daelim.witty.v2.domain.Witty;
import com.daelim.witty.v2.web.controller.dto.wittys.CreateWittyRequest;
import com.daelim.witty.v2.web.controller.dto.wittys.UpdateWittyRequest;
import com.daelim.witty.v2.web.exception.BadRequestException;
import com.daelim.witty.v2.web.repository.users.FollowRepository;
import com.daelim.witty.v2.web.repository.wittys.TagRepository;
import com.daelim.witty.v2.web.repository.wittys.WittyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
@Service
public class WittyServiceImplV2 implements WittyServiceV2 {
    private final WittyRepository wittyRepository;
    private final TagRepository tagRepository;
    private final FollowRepository followRepository;
    private final EntityManager em;

    @Value("${wittyThumbnail.path}")
    private String uploadFolder;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Witty writeWitty(CreateWittyRequest createWittyRequest, User user, MultipartFile file) throws Exception {
        Witty witty = Witty.createWitty(user, createWittyRequest);
        Witty save = wittyRepository.save(witty);
        setThumnailImg(user, file, save);
        return save;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Witty updateWitty(Long wittyId, UpdateWittyRequest updateWittyRequest, User user, MultipartFile file) throws Exception {
        Witty witty = wittyRepository.findById(wittyId).orElseThrow(() -> new BadRequestException("입력값 확인 필요"));
        tagRepository.deleteAllByWitty(witty);
        witty.update(updateWittyRequest);
        setThumnailImg(user, file, witty);
        return witty;
    }

    private void setThumnailImg(User user, MultipartFile file, Witty witty) {
        String imageFileName = witty.getId() + "_" + file.getOriginalFilename();
        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        if (file.getSize() != 0) {
            try {
                if (user.getProfileImgUrl() != null) {
                    File thumnailImg = new File(uploadFolder + witty.getThumnailImgUri());
                    thumnailImg.delete();
                }
                Files.write(imageFilePath, file.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        witty.setThumnailImgUri(imageFileName);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Witty deleteWitty(Long wittyId, User user) throws Exception {
        Witty witty = wittyRepository.findById(wittyId).orElseThrow(() -> new BadRequestException("입력값 확인 필요"));
        wittyRepository.deleteById(wittyId);
        return witty;
    }

    @Override
    public List<Witty> findAll(Integer page, Integer size, User user) throws Exception {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDateTime").descending());
        List<User> followingUsers = followRepository.findAllByFromUser(user).stream().map(Follow::getToUser)
                .collect(Collectors.toList());
        return wittyRepository.findWittyByUserIn(followingUsers, pageRequest);
    }

    @Override
    public List<Witty> findAll(Integer page, Integer size, String tag) throws Exception {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDateTime").descending());
        List<Tag> tags = tagRepository.findAllByName(tag);
        return wittyRepository.findAllByTagsIn(tags, pageRequest);
    }
}
