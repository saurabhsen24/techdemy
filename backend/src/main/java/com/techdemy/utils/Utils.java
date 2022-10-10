package com.techdemy.utils;

import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.HashSet;
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
}
