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

    private Long shopId;
    private Long memberId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.REMOVE)
    private List<CartLineItem> cartLineItems = new ArrayList<>();

    @Builder
    public Cart(Long shopId, Long memberId){
        this.shopId = shopId;
        this.memberId = memberId;
    }

    public List<CartLineItem> getCartLineItems(){
        return this.cartLineItems;
    }

    public Boolean isEqualByShop(Long shopId){
        return this.shopId.equals(shopId);
    }

    public Boolean isEmptyCart(){
        if(this.cartLineItems.size()==0){
            return true;
        }else{
            return false;
        }
    }

    public Long getId(){
        return this.id;
    }
}

