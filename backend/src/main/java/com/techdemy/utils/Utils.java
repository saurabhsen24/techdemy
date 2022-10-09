package com.techdemy.utils;

import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

    private static Gson gson;

    static {
        gson = new Gson();
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }
}
