package com.example.slowdlvy.cart.application;

import com.example.slowdlvy.cart.presentation.dto.CartRequestDto;
import com.example.slowdlvy.cart.domain.Cart;
import com.example.slowdlvy.cart.domain.CartLineItem;
import com.example.slowdlvy.cart.domain.CartLineItemRepository;
import com.example.slowdlvy.cart.domain.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartLineItemRepository cartLineItemRepository;

    @Transactional
    public void addCartLineItem(Long memberId, CartRequestDto.addCartLineItem cartRequest){
        Cart cart = getCartByMemberId(memberId);
        CartLineItem cartLineItem = cartRequest.toCartLineItem();
        cartLineItem.setCart(cart);
        cartLineItemRepository.save(cartLineItem);
    }

    @Transactional
    public Cart getCartByMemberId(Long memberId){
        Optional<Cart> cart = cartRepository.findByMemberId(memberId);
        if(cart.isPresent()){
            return cart.get();
        }else{
            Cart newCart = cartRepository.save(new Cart(memberId));
            return newCart;
        }
    }

}