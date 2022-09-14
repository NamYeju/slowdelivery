package com.example.slowdlvy.cart.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

    @Id
    @Column(name = "CART_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    @Builder
    public Cart(Long memberId){
        this.memberId = memberId;
    }

//    @OneToMany(mappedBy = "cart", cascade = CascadeType.REMOVE)
//    private List<CartLineItem> cartLineItems = new ArrayList<>();
//
//    public List<CartLineItem> getCartLineItems(){
//        return this.cartLineItems;
//    }

//    public Long getId(){
//        return this.id;
//    }
}

