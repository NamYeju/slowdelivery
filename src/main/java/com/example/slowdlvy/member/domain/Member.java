package com.example.slowdlvy.member.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID", nullable = false)
    private Long id;

    private String username;

    @Column
    private String password;

    @Column
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Provider provider;


    public Member(String username, String password, Provider provider){
        this.username = username;
        this.password = password;
        this.provider = provider;
    }


    public void setDefaultUser(){
        this.nickname = "유저_"+(char)(int)(Math.random()*1000);
        this.role = Role.USER;
    }

    public void setDefaultAdmin(){
        this.nickname = "관리자_"+(char)(int)(Math.random()*1000);
        this.role = Role.ADMIN;
    }

}
