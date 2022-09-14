package com.example.slowdlvy.cart.domain;

import lombok.AccessLevel;
import lombok.Builder;
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
    private Long optionGroupId;
    private String name;

    @Enumerated(EnumType.STRING)
    private RequiredStatus requiredStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CART_LINE_ITEM_ID")
    private CartLineItem cartLineItem;

    @OneToMany(mappedBy = "cartItemOptionGroup", cascade = CascadeType.ALL)
    private List<CartItemOption> cartItemOptions = new ArrayList<>();

    public CartItemOptionGroup(Long optionGroupId, String name, RequiredStatus requiredStatus,List<CartItemOption> options){
        this.optionGroupId = optionGroupId;
        this.name = name;
        this.requiredStatus = requiredStatus;
        this.cartItemOptions.addAll(options);
        options.stream().forEach(cartItemOption -> cartItemOption.setCartItemOptionGroup(this));
    }


    public void setCartLineItem(CartLineItem cartLineItem){
        this.cartLineItem = cartLineItem;
    }

}
