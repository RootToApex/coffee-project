package com.coffee.domain.order.controller;

import com.coffee.domain.order.dto.OrderRequest;
import com.coffee.domain.order.dto.OrderResponse;
import com.coffee.domain.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponse order(@Valid @RequestBody OrderRequest request) {
        return orderService.order(request);
    }
}