package com.alibaba.fplayer.flutter_aliplayer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;

/* loaded from: classes2.dex */
public class GsonLoader {
    private static volatile Gson instance;

    private GsonLoader() {
    }

    public static Gson getInstance() {
        if (instance == null) {
            synchronized (GsonLoader.class) {
                try {
                    if (instance == null) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        ToNumberPolicy toNumberPolicy = ToNumberPolicy.LONG_OR_DOUBLE;
                        instance = gsonBuilder.setNumberToNumberStrategy(toNumberPolicy).setObjectToNumberStrategy(toNumberPolicy).serializeNulls().create();
                    }
                } finally {
                }
            }
        }
        return instance;
    }
}
