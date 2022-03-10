package com.daelim.witty.v2.web.service.users;


import com.daelim.witty.v2.domain.EmailVerification;
import com.daelim.witty.v2.web.exception.BadRequestException;
import com.daelim.witty.v2.web.repository.users.EmailVerificationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailService {

    private final JavaMailSender mailSender;
    private final EmailVerificationRepository emailVerificationRepository;

    private final static Random random = new Random(System.currentTimeMillis());
    //TODO 나중에 환경변수로 제거 해야함
    private final String subject = "witty 인증 메일입니다.";
    private final String from = "jungmini0601@gmail.com";
    private String content;

    public void setContent(String key) {
        this.content = "인증 번호는 " + key + " 입니다.";
    }

    public void sendMail(EmailVerification emailVerification) throws Exception{
        if(emailVerificationRepository.findById(emailVerification.getEmail()).isPresent()) {
            throw new BadRequestException("이미 가입된 이메일 입니다.");
        }

        MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper mailHelper = new MimeMessageHelper(mail, false, "UTF-8");

        String key = generateKey();
        emailVerification.setVerificationKey(key);
        setContent(key);

        mailHelper.setFrom(from);
        mailHelper.setTo(emailVerification.getEmail());
        mailHelper.setSubject(subject);
        mailHelper.setText(content, true);

        try {
            mailSender.send(mail);
            log.info("DB 저장 전!!!!!");
            log.info("[이메일 : {}]", emailVerification.getEmail());
            log.info("[인증키 : {}]", emailVerification.getVerificationKey());
            emailVerificationRepository.save(emailVerification);
            log.info("DB 저장 후!!!!!");
        }catch (Exception e) {
            throw new Exception("메일 전송 실패!");
        }
    }

    private String generateKey() {
        return 111111 + random.nextInt(888888) + "";
    }
}
