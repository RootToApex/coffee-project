package com.coffee.domain.order.dto;

import jakarta.validation.constraints.NotNull;

public record OrderRequest(
        @NotNull(message = "유저 ID는 필수입니다.")
        Long userId,

        @NotNull(message = "메뉴 ID는 필수입니다.")
        Long menuId
) {}
