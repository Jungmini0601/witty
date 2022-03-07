package com.daelim.witty.web.service.users.v2;

import com.daelim.witty.domain.v2.User;
import com.daelim.witty.web.controller.v2.dto.UserSignUpDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceImplV2Test {

    @Autowired
    UserServiceImplV2 userServiceImplV2;

    @Test
    void 회원가입() throws Exception{
        UserSignUpDTO userSignUpDTO = new UserSignUpDTO();
        userSignUpDTO.setUser_id("aphopis");
        userSignUpDTO.setUser_department("컴정");
        userSignUpDTO.setUser_email("aphopis@email.daelim.ac.kr");
        userSignUpDTO.setPassword("12341234");
        User user = User.createUserByDTO(userSignUpDTO);

        User retUser = userServiceImplV2.signUp(user);

        assertEquals(user.getId(), retUser.getId());
    }


}