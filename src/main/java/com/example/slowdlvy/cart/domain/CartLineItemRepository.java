package com.example.slowdlvy.cart.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartLineItemRepository extends JpaRepository<CartLineItem, Long> {
}
