package com.daelim.witty.web.service;

import com.daelim.witty.domain.EmailConfrim;
import com.daelim.witty.domain.User;
import com.daelim.witty.web.controller.dto.UserLogInDTO;
import com.daelim.witty.web.controller.dto.VerificationCodeDTO;
import com.daelim.witty.web.repository.user.EmailConfirmRepository;
import com.daelim.witty.web.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final EmailConfirmRepository emailConfirmRepository;
    private final MailService mailService;


    @Override
    public boolean signUp(User user) {
        return userRepository.save(user);
    }

    @Override
    public User login(UserLogInDTO userLogInDTO) {
        return userRepository.findById(userLogInDTO.getUser_id())
                .filter(user -> user.getPassword().equals(userLogInDTO.getPassword()))
                .orElse(null);
    }

    @Override
    public boolean isDuplicatedId(String id) {
        Optional<User> user = userRepository.findById(id);

        return user.isPresent();
    }

    @Override
    public void emailConfirm(EmailConfrim emailConfrim) throws Exception{
           mailService.sendMail(emailConfrim);
    }

    //TODO 여기 인터페이스 수정하고 개발 다시해야 함
    @Override
    public boolean verification(VerificationCodeDTO verificationCodeDTO) {

        EmailConfrim emailConfrim = emailConfirmRepository.findByEmail(verificationCodeDTO.getEmail()).get();

        return verificationCodeDTO.getKey().equals(emailConfrim.getKey());
    }
}
