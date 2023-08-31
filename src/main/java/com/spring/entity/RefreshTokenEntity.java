package com.spring.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RefreshTokenEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        private String token;

        private Instant expiryDate;

        @OneToOne
        @JoinColumn(name = "user_id", referencedColumnName = "user_id")
        private UserEntity userEntity;


}
