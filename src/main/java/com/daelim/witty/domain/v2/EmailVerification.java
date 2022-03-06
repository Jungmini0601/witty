package com.daelim.witty.domain.v2;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class EmailVerification {

    @Id
    private String email;

    private String verificationKey;
}
