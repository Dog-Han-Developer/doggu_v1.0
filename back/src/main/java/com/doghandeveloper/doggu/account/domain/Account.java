package com.doghandeveloper.doggu.account.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private String userName;

    @Column(name = "dogModel")
    @Enumerated(EnumType.STRING)
    private DogModel dogModel;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    //TODO : 추후 확장성을 위해 일대다로 변경
    @OneToOne(mappedBy = "account", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    private Dog dog;

    @Builder
    public Account(UserRole role, String email, String password, String userName, DogModel dogModel) {
        this.role = role;
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.dogModel = dogModel;
    }

    public void updateDogModel(String dogModel){
        this.dogModel = DogModel.valueOf(dogModel);
    }
}
