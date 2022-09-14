package com.example.slowdlvy.cart.presentation.dto;

import com.example.slowdlvy.cart.domain.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CartRequestDto {

    @Getter
    public static class addCartLineItem{
        private Long shopId;
        private Long id;
        private String name;
        private int price;
        private int orderQuantity;
        private List<ingredientGroup> ingredientGroups;
        private List<optionGroup> optionGroups;

        public CartLineItem toCartLineItem(){
            return CartLineItem.builder()
                    .shopId(shopId)
                    .productId(id)
                    .quantity(orderQuantity)
                    .name(name)
                    .price(price)
                    .cartItemOptionGroups(this.toCartItemOptionGroups()).build();
        }

        private List<CartItemOptionGroup> toCartItemOptionGroups(){
            List<CartItemOptionGroup> optionGroups = new ArrayList<>();
            optionGroups.addAll(this.ingredientGroups.stream().map(a->a.toCartItemOptionGroup()).collect(Collectors.toList()));
            optionGroups.addAll(this.optionGroups.stream().map(a->a.toCartItemOptionGroup()).collect(Collectors.toList()));
            return optionGroups;
        }
    }


    public static class ingredientGroup{
        private Long id;
        private String name;
        private List<option> ingredients;

        public CartItemOptionGroup toCartItemOptionGroup(){
            return new CartItemOptionGroup(id, name, RequiredStatus.INGREDIENT, ingredients.stream().map(a->a.toCartItemOption()).collect(Collectors.toList()));
        }
    }


    public static class optionGroup{
        private Long id;
        private String name;
        private List<option> options;

        public CartItemOptionGroup toCartItemOptionGroup(){
            return new CartItemOptionGroup(id, name, RequiredStatus.OPTION, options.stream().map(a->a.toCartItemOption()).collect(Collectors.toList()));
        }
    }


    public static class option{
        private Long id;
        private String name;
        private int price;

        private CartItemOption toCartItemOption(){
            return new CartItemOption(id, name, price);
        }
    }
}
