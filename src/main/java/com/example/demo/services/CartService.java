package com.example.demo.services;

import com.example.demo.models.Cart;
import com.example.demo.models.Supplier;
import com.example.demo.repositories.CartRepository;
import com.example.demo.repositories.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service

public class CartService {

    private final CartRepository cartRepository;
    private final SupplierRepository supplierRepository;

    public CartService(CartRepository cartRepository, SupplierRepository supplierRepository) {
        this.cartRepository = cartRepository;
        this.supplierRepository = supplierRepository;
    }

    // Получение ко рзины пользователя
    public Cart getUserCart(Long userId) {
        return cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            return cartRepository.save(newCart);
        });
    }

    // Добавление услуги в корзину
    @Transactional
    public Cart addToCart(Long userId, Long supplierId) {
        Cart cart = getUserCart(userId);
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow(() ->
                new RuntimeException("Поставщик не найден"));

        if (!cart.getSuppliers().contains(supplier)) {
            cart.getSuppliers().add(supplier);
            cart.setTotalPrice(calculateTotal(cart.getSuppliers()));
        }

        return cartRepository.save(cart);
    }

    // Удаление услуги из корзины
    @Transactional
    public Cart removeFromCart(Long userId, Long supplierId) {
        Cart cart = getUserCart(userId);
        cart.getSuppliers().removeIf(s -> s.getId().equals(supplierId));
        cart.setTotalPrice(calculateTotal(cart.getSuppliers()));

        return cartRepository.save(cart);
    }

    // Очистка корзины
    @Transactional
    public Cart clearCart(Long userId) {
        Cart cart = getUserCart(userId);
        cart.getSuppliers().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
        return cartRepository.save(cart);
    }

    // Подсчет общей стоимости
    private BigDecimal calculateTotal(List<Supplier> suppliers) {
        return suppliers.stream()
                .map(Supplier::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
