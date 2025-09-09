package com.facebook.internal;

import android.util.Log;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class Logger {
    public static final String LOG_TAG_BASE = "FacebookSDK.";
    private static final HashMap<String, String> stringsToReplace = new HashMap<>();
    private final LoggingBehavior behavior;
    private StringBuilder contents;
    private int priority = 3;
    private final String tag;

    public Logger(LoggingBehavior loggingBehavior, String str) {
        Validate.notNullOrEmpty(str, "tag");
        this.behavior = loggingBehavior;
        this.tag = LOG_TAG_BASE + str;
        this.contents = new StringBuilder();
    }

    public static void log(LoggingBehavior loggingBehavior, String str, String str2) {
        log(loggingBehavior, 3, str, str2);
    }

    public static synchronized void registerAccessToken(String str) {
        if (!FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.INCLUDE_ACCESS_TOKENS)) {
            registerStringToReplace(str, "ACCESS_TOKEN_REMOVED");
        }
    }

    public static synchronized void registerStringToReplace(String str, String str2) {
        stringsToReplace.put(str, str2);
    }

    private static synchronized String replaceStrings(String str) {
        for (Map.Entry<String, String> entry : stringsToReplace.entrySet()) {
            str = str.replace(entry.getKey(), entry.getValue());
        }
        return str;
    }

    private boolean shouldLog() {
        return FacebookSdk.isLoggingBehaviorEnabled(this.behavior);
    }

    public void append(StringBuilder sb) {
        if (shouldLog()) {
            this.contents.append((CharSequence) sb);
        }
    }

    public void appendKeyValue(String str, Object obj) {
        append("  %s:\t%s\n", str, obj);
    }

    public String getContents() {
        return replaceStrings(this.contents.toString());
    }

    public int getPriority() {
        return this.priority;
    }

    public void logString(String str) {
        log(this.behavior, this.priority, this.tag, str);
    }

    public void setPriority(int i2) {
        Validate.oneOf(Integer.valueOf(i2), "value", 7, 3, 6, 4, 2, 5);
        this.priority = i2;
    }

    public static void log(LoggingBehavior loggingBehavior, String str, String str2, Object... objArr) {
        if (FacebookSdk.isLoggingBehaviorEnabled(loggingBehavior)) {
            log(loggingBehavior, 3, str, String.format(str2, objArr));
        }
    }

    public void append(String str) {
        if (shouldLog()) {
            this.contents.append(str);
        }
    }

    public static void log(LoggingBehavior loggingBehavior, int i2, String str, String str2, Object... objArr) {
        if (FacebookSdk.isLoggingBehaviorEnabled(loggingBehavior)) {
            log(loggingBehavior, i2, str, String.format(str2, objArr));
        }
    }

    public void append(String str, Object... objArr) {
        if (shouldLog()) {
            this.contents.append(String.format(str, objArr));
        }
    }

    public static void log(LoggingBehavior loggingBehavior, int i2, String str, String str2) {
        if (FacebookSdk.isLoggingBehaviorEnabled(loggingBehavior)) {
            String strReplaceStrings = replaceStrings(str2);
            if (!str.startsWith(LOG_TAG_BASE)) {
                str = LOG_TAG_BASE + str;
            }
            Log.println(i2, str, strReplaceStrings);
            if (loggingBehavior == LoggingBehavior.DEVELOPER_ERRORS) {
                new Exception().printStackTrace();
            }
        }
    }

    public void log() {
        logString(this.contents.toString());
        this.contents = new StringBuilder();
    }
}
