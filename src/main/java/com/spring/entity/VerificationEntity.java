package com.spring.entity;

import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class VerificationEntity {
    private static final int EXPIRATION = 60 * 24;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "verification_token")
    private String verificationToken;
    @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private UserEntity user;
    @Column
    private Date expiryDate;

    public VerificationEntity() {
        super();
    }

    public VerificationEntity(String verificationToken) {
        super();
        this.verificationToken = verificationToken;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    public VerificationEntity(String verificationToken, UserEntity user) {
        super();
        this.verificationToken = verificationToken;
        this.user = user;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public void updateVerification(String newVerification) {
        this.verificationToken = newVerification;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }
}
