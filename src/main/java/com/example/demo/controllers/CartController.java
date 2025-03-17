package com.example.demo.controllers;

import com.example.demo.models.Cart;
import com.example.demo.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cart")

public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Получить корзину пользователя
    @GetMapping("/{userId}")
    public Cart getUserCart(@PathVariable Long userId) {
        return cartService.getUserCart(userId);
    }

    // Добавить услугу в корзину
    @PostMapping("/{userId}/add/{supplierId}")
    public Cart addToCart(@PathVariable Long userId, @PathVariable Long supplierId) {
        return cartService.addToCart(userId, supplierId);
    }

    // Удалить услугу из корзины
    @DeleteMapping("/{userId}/remove/{supplierId}")
    public Cart removeFromCart(@PathVariable Long userId, @PathVariable Long supplierId) {
        return cartService.removeFromCart(userId, supplierId);
    }

    // Очистить корзину
    @DeleteMapping("/{userId}/clear")
    public Cart clearCart(@PathVariable Long userId) {
        return cartService.clearCart(userId);
    }

    // Получить общую стоимость корзины
    @GetMapping("/{userId}/total")
    public BigDecimal getTotalPrice(@PathVariable Long userId) {
        return cartService.getUserCart(userId).getTotalPrice();
    }
}
