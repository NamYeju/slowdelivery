package com.example.slowdlvy.security.oauth.provider;

import com.example.slowdlvy.member.domain.Provider;

public interface OAuth2UserInfo {
    String getProviderId();
    Provider getProvider();
    String getUsername();
    String getEmail();
}
