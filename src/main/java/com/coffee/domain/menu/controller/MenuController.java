package com.coffee.domain.menu.controller;

import com.coffee.domain.menu.dto.MenuResponse;
import com.coffee.domain.menu.dto.PopularMenuResponse;
import com.coffee.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menus")
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public List<MenuResponse> getAllMenus() {
        return menuService.getAllMenus();
    }

    @GetMapping("/popular")
    public List<PopularMenuResponse> getPopularMenus() {
        return menuService.getPopularMenus();
    }
}