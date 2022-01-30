package com.daelim.witty.web.service;

import com.daelim.witty.domain.EmailConfrim;
import com.daelim.witty.web.repository.user.EmailConfirmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class MailService {
    private final JavaMailSender mailSender;
    private final EmailConfirmRepository emailConfirmRepository;
    private final static Random random = new Random(System.currentTimeMillis());

    private final String subject = "witty 인증 메일입니다.";
    private final String from = "jungmini0601@gmail.com";
    private String content;
    private String to;

    public void setTo(String to) {
        this.to = to;
    }

    public void setContent(String key) {
        this.content = "인증 번호는 " + key + " 입니다.";
    }

    //TODO 여기서 이메일 리파지토리에 저장하는 로직 작성해야 함 이메일 로직은 완성
    public void sendMail(EmailConfrim emailConfrim) throws Exception{
        if(emailConfirmRepository.findByEmail(emailConfrim.getEmail()).isPresent()) {
            throw new Exception("이미 가입된 이메일 입니다.");
        }

        String key = generateKey();

        emailConfrim.setKey(key);
        setContent(key);

        MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper mailHelper = new MimeMessageHelper(mail, false, "UTF-8");

        mailHelper.setFrom(from);
        mailHelper.setTo(emailConfrim.getEmail());
        mailHelper.setSubject(subject);
        mailHelper.setText(content, true);

        try {
            mailSender.send(mail);
        }catch (Exception e) {
            throw e;
        }

        if(!emailConfirmRepository.save(emailConfrim)) throw new Exception("DB 저장 실패!");
    }

    private String generateKey() {
        return 111111 + random.nextInt(888888) + "";
    }
}
