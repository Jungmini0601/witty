package com.daelim.witty.v1.web.service.users;

import com.daelim.witty.v1.domain.EmailConfrim;
import com.daelim.witty.v1.web.exception.BadRequestException;
import com.daelim.witty.v1.web.repository.users.EmailConfirmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.util.Random;

@RequiredArgsConstructor
//@Service
public class MailService {
    private final JavaMailSender mailSender;
    private final EmailConfirmRepository emailConfirmRepository;
    private final static Random random = new Random(System.currentTimeMillis());
    //TODO 나중에 환경변수로 제거 해야함
    private final String subject = "witty 인증 메일입니다.";
    private final String from = "jungmini0601@gmail.com";
    private String content;

    public void setContent(String key) {
        this.content = "인증 번호는 " + key + " 입니다.";
    }

    public void sendMail(EmailConfrim emailConfrim) throws Exception{
        if(emailConfirmRepository.findByEmail(emailConfrim.getEmail()).isPresent()) {
            throw new BadRequestException("이미 가입된 이메일 입니다.");
        }

        MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper mailHelper = new MimeMessageHelper(mail, false, "UTF-8");

        String key = generateKey();
        emailConfrim.setKey(key);
        setContent(key);

        mailHelper.setFrom(from);
        mailHelper.setTo(emailConfrim.getEmail());
        mailHelper.setSubject(subject);
        mailHelper.setText(content, true);

        try {
            mailSender.send(mail);
        }catch (Exception e) {
            throw new Exception("메일 전송 실패!");
        }

        if(!emailConfirmRepository.save(emailConfrim)) throw new Exception("DB 저장 실패!");
    }

    private String generateKey() {
        return 111111 + random.nextInt(888888) + "";
    }
}
