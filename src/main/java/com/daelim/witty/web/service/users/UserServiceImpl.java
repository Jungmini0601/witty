package com.daelim.witty.web.service.users;

import com.daelim.witty.domain.v1.EmailConfrim;
import com.daelim.witty.domain.v1.User;
import com.daelim.witty.web.controller.dto.users.UserLogInDTO;
import com.daelim.witty.web.controller.dto.users.VerificationCodeDTO;
import com.daelim.witty.web.exception.BadRequestException;
import com.daelim.witty.web.repository.users.EmailConfirmRepository;
import com.daelim.witty.web.repository.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final EmailConfirmRepository emailConfirmRepository;
    private final MailService mailService;


    @Override
    public User signUp(User user) {
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

    @Override
    public boolean verification(VerificationCodeDTO verificationCodeDTO) throws BadRequestException{

        Optional<EmailConfrim> emailConfirmOptional = emailConfirmRepository.findByEmail(verificationCodeDTO.getEmail());

        if(emailConfirmOptional.isEmpty()) {
            throw new BadRequestException("인증번호 요청을 먼저 해 주어야 합니다.");
        }

        EmailConfrim emailConfrim = emailConfirmOptional.get();

        return verificationCodeDTO.getKey().equals(emailConfrim.getKey());
    }
}
