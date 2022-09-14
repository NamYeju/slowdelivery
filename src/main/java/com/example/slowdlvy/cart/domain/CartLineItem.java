package com.example.slowdlvy.cart.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartLineItem {

    @Id
    @Column(name = "CART_LINE_ITEM_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long shopId;
    private Long productId;
    private int quantity;
    private String name;
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CART_ID")
    private Cart cart;

    @OneToMany(mappedBy = "cartLineItem", cascade = CascadeType.ALL)
    private List<CartItemOptionGroup> cartItemOptionGroups = new ArrayList<>();

    @Builder
    public CartLineItem(Long shopId, Long productId, int quantity, String name, int price, List<CartItemOptionGroup> cartItemOptionGroups){
        this.shopId = shopId;
        this.productId = productId;
        this.quantity = quantity;
        this.name = name;
        this.price = price;
        this.cartItemOptionGroups.addAll(cartItemOptionGroups);
        cartItemOptionGroups.stream().forEach(cartItemOptionGroup -> cartItemOptionGroup.setCartLineItem(this));
    }

    public void setCart(Cart cart){
        this.cart = cart;
        //cart.getCartLineItems().add(this);
    }
}
