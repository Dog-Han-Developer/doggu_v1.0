package com.doghandeveloper.doggu.account.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //TODO: 추후 강아지 중복 등록 회피를 위해 동물등록번호 저장 필요

    private String name;

    private String kind;

    private Integer weight;

    private String birth;

    @Column(name = "sex")
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Builder
    public Dog(String name, String kind, Integer weight, String birth, Sex sex, Account account) {
        this.name = name;
        this.kind = kind;
        this.weight = weight;
        this.birth = birth;
        this.sex = sex;
        this.account = account;
    }
}
