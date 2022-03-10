package com.daelim.witty.v2.domain;

import com.daelim.witty.v2.web.controller.dto.users.SendVerificationCodeDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

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
