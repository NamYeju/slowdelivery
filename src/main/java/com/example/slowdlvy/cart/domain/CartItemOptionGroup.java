package com.example.slowdlvy.cart.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItemOptionGroup {

    @Id
    @Column(name = "CART_ITEM_OPTION_GROUP_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CART_LINE_ITEM_ID")
    private CartLineItem cartLineItem;

    @OneToMany(mappedBy = "cartItemOptionGroup", cascade = CascadeType.ALL)
    private List<CartItemOption> cartItemOptions = new ArrayList<>();

    public CartItemOptionGroup(String name, List<CartItemOption> cartItemOptions){
        this.name = name;
        this.cartItemOptions.addAll(cartItemOptions);
        cartItemOptions.forEach(a->a.setCartItemOptionGroup(this));
    }

    public void setCartLineItem(CartLineItem cartLineItem){
        this.cartLineItem = cartLineItem;
    }

}
