package com.coffee.domain.order.dto;

import com.coffee.domain.order.entity.Order;
import java.time.LocalDateTime;

public record OrderResponse(
        Long orderId,
        Long userId,
        Long menuId,
        Long price,
        LocalDateTime createdAt
) {
    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getUserId(),
                order.getMenuId(),
                order.getPrice(),
                order.getCreatedAt()
        );
    }
}
