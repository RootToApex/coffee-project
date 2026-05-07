package com.coffee.domain.user.dto;

import com.coffee.domain.user.entity.User;

public record PointResponse(Long userId, Long point) {
    public static PointResponse from(User user) {
        return new PointResponse(user.getId(), user.getPoint());
    }
}
