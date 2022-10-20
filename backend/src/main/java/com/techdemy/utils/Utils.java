package com.techdemy.utils;

import com.google.gson.Gson;
import com.techdemy.security.JwtHelper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

    private static Gson gson;
    private static Set<String> extensionList;

    static {
        gson = new Gson();

        extensionList = new HashSet<>();

        extensionList.add(".png");
        extensionList.add(".jpeg");
        extensionList.add(".jpg");
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }
    public static Set<String> getExtensionList() { return Collections.unmodifiableSet( extensionList ); }

    public static Map<String,Object> getClaims() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
        Map<String,Object> claims = token.getTokenAttributes();
        return claims;
    }

    public static Long getCurrentLoggedInUserId() {
        Long userId = Long.parseLong(JwtHelper.getCurrentLoggedInUserId());
        return userId;
    }

}
