package com.coffee.domain.user.service;

import com.coffee.domain.user.dto.PointChargeRequest;
import com.coffee.domain.user.dto.PointResponse;
import com.coffee.domain.user.entity.User;
import com.coffee.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public PointResponse chargePoint(Long userId, PointChargeRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        user.charge(request.amount());

        return PointResponse.from(user);
    }
}