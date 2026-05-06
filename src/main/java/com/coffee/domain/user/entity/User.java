package com.coffee.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 id를 자동으로 생성
    private Long id;

    @Column(nullable = false)
    private Long point;

    public static User create() {
        User user = new User();
        user.point = 0L;
        return user;
    }
}
