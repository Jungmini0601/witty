package com.daelim.witty.domain.v2;

import com.daelim.witty.web.controller.v2.dto.users.SendVerificationCodeDTO;
import com.daelim.witty.web.controller.v2.dto.users.VerificationCodeDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class EmailVerification {

    @Id
    private String email;

    private String verificationKey;


    /*생성 메소드*/

    public static EmailVerification createEmailVerificationByDTO(SendVerificationCodeDTO sendVerificationCodeDTO) {
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.email = sendVerificationCodeDTO.getEmail();
        return emailVerification;
    }

    /*비즈니슬 로직*/
    public void setVerificationKey(String verificationKey) {
        this.verificationKey = verificationKey;
    }
}
