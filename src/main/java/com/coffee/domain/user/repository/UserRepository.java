package com.coffee.domain.user.repository;

import com.coffee.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Long> {

    // 포인트 차감 (조건부 업데이트)
    @Modifying
    @Query("UPDATE User u SET u.point = u.point - :amount " +
            "WHERE u.id = :userId AND u.point >= :amount")
    int deductPoint(@Param("userId") Long userId, @Param("amount") Long amount);
}
