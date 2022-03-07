package com.hotnerds.common.security.oauth2.service;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuth2Attributes {
    private final Map<String, Object> attributes;
    private final String nameAttributeName;
    private final String name;
    private final String email;

    @Builder
    public OAuth2Attributes(Map<String, Object> attributes, String nameAttributeName, String name, String email) {
        this.attributes = attributes;
        this.nameAttributeName = nameAttributeName;
        this.name = name;
        this.email = email;
    }

    public static OAuth2Attributes of(String registrationId, String usernameAttributeName, Map<String, Object> attributes) {
        return ofKakao(usernameAttributeName, attributes);
    }

    private static OAuth2Attributes ofKakao(String usernameAttributeName, Map<String, Object> attributes) {
        return OAuth2Attributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeName((String) usernameAttributeName)
                .build();
    }


}