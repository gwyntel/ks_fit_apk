package com.yc.utesdk.command;

/* loaded from: classes4.dex */
public class MoodCommandUtil {
    private static MoodCommandUtil Instance;

    public static MoodCommandUtil getInstance() {
        if (Instance == null) {
            Instance = new MoodCommandUtil();
        }
        return Instance;
    }
}
