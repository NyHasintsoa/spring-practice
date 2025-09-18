package com.exercise.project.security.service;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        final String RESET = "\u001B[0m";
        final String KEY_COLOR = "\u001B[32m";
        final String VALUE_COLOR = "\u001B[36m";
        final String TYPE_COLOR = "\u001B[33m";

        OAuth2User oAuth2User = super.loadUser(userRequest);

        for (Map.Entry<String, Object> entry : oAuth2User.getAttributes().entrySet()) {
            Object value = entry.getValue();
            String typeName = value != null ? value.getClass().getSimpleName() : "null";

            System.out.println(KEY_COLOR + entry.getKey() + RESET + " : " +
                VALUE_COLOR + value + RESET +
                " " + TYPE_COLOR + "(" + typeName + ")" + RESET);
        }

        return oAuth2User;
    }

}
