package com.daelim.witty.web.service.users.v2;

import com.daelim.witty.domain.v2.User;
import com.daelim.witty.web.controller.v2.dto.users.UserSignUpDTO;
import com.daelim.witty.web.exception.BadRequestException;
import com.daelim.witty.web.repository.users.v2.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceImplV2Test {

    @Autowired
    UserServiceImplV2 userServiceImplV2;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager em;
    @Test
    void 회원가입() throws Exception{
        UserSignUpDTO userSignUpDTO = new UserSignUpDTO();
        userSignUpDTO.setUser_id("aphopis");
        userSignUpDTO.setUser_department("컴정");
        userSignUpDTO.setUser_email("aphopis@email.daelim.ac.kr");
        userSignUpDTO.setPassword("12341234");
        User user = User.createUserByDTO(userSignUpDTO);

        userServiceImplV2.signUp(user);
        em.flush();

        User retUser = userRepository.findById(user.getId()).get();

        assertEquals(user.getId(), retUser.getId());
    }

    @Test
    void 아이디_중복_체크() throws Exception {

        UserSignUpDTO userSignUpDTO = new UserSignUpDTO();
        userSignUpDTO.setUser_id("aphopis");
        userSignUpDTO.setUser_department("컴정");
        userSignUpDTO.setUser_email("aphopis@email.daelim.ac.kr");
        userSignUpDTO.setPassword("12341234");
        User user = User.createUserByDTO(userSignUpDTO);

        userServiceImplV2.signUp(user);

        assertDoesNotThrow(() -> {
            userRepository.findById(user.getId()).orElseThrow(() -> new BadRequestException("해당하는 아이디는 존재하지 않습니다."));
        });


    }
}