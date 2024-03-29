package com.example.slowdlvy.member.domain.refreshToken;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "KEY_USERNAME", nullable = false)
    private String username;

    @Column(name = "REFRESH_TOKEN", updatable = false)
    private String refreshToken;

    public RefreshToken(String username, String refreshToken){
        this.username = username;
        this.refreshToken = refreshToken;
    }

    public void updateToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public Boolean isEqual(String Token){
        return this.refreshToken.equals(Token);
    }


}
