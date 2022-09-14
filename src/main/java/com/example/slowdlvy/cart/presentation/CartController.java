package com.example.slowdlvy.cart.presentation;

import com.example.slowdlvy.cart.presentation.dto.CartRequestDto;
import com.example.slowdlvy.cart.application.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("carts/{memberId}")
    public ResponseEntity addCartLineItem(@PathVariable Long memberId, @RequestBody CartRequestDto.addCartLineItem cartRequest){
        cartService.addCartLineItem(memberId, cartRequest);
        return ResponseEntity.ok("상품 담기 완료");
    }
}