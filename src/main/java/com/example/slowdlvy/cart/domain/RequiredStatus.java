package com.example.slowdlvy.cart.domain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum RequiredStatus {
    INGREDIENT("INGREDIENT", "필수옵션"),
    OPTION("OPTION", "선택옵션");

    private final String key;
    private final String title;
}
