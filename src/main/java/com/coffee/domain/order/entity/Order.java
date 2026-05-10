package com.coffee.domain.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long menuId;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // 주문 시점의 가격 함께 저장 (가격 변동에 대비)
    public static Order create(Long userId, Long menuId, Long price) {
        Order order = new Order();
        order.userId = userId;
        order.menuId = menuId;
        order.price = price;
        order.createdAt = LocalDateTime.now();
        return order;
    }
}
