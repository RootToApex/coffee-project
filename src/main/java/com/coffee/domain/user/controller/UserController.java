package com.coffee.domain.user.controller;

import com.coffee.domain.user.dto.PointChargeRequest;
import com.coffee.domain.user.dto.PointResponse;
import com.coffee.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/{userId}/point/charge")
    public PointResponse chargePoint(
            @PathVariable Long userId,
            @Valid @RequestBody PointChargeRequest request
    ) {
        return userService.chargePoint(userId, request);
    }
}