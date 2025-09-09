package com.facebook.appevents.codeless.internal;

import android.util.Log;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes3.dex */
public class UnityReflection {
    private static final String CAPTURE_VIEW_HIERARCHY_METHOD = "CaptureViewHierarchy";
    private static final String EVENT_MAPPING_METHOD = "OnReceiveMapping";
    private static final String FB_UNITY_GAME_OBJECT = "UnityFacebookSDKPlugin";
    private static final String TAG = "com.facebook.appevents.codeless.internal.UnityReflection";
    private static final String UNITY_PLAYER_CLASS = "com.unity3d.player.UnityPlayer";
    private static final String UNITY_SEND_MESSAGE_METHOD = "UnitySendMessage";
    private static Class<?> unityPlayer;

    public static void captureViewHierarchy() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        sendMessage(FB_UNITY_GAME_OBJECT, CAPTURE_VIEW_HIERARCHY_METHOD, "");
    }

    public static void sendEventMapping(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        sendMessage(FB_UNITY_GAME_OBJECT, EVENT_MAPPING_METHOD, str);
    }

    public static void sendMessage(String str, String str2, String str3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            if (unityPlayer == null) {
                unityPlayer = Class.forName(UNITY_PLAYER_CLASS);
            }
            unityPlayer.getMethod(UNITY_SEND_MESSAGE_METHOD, String.class, String.class, String.class).invoke(unityPlayer, str, str2, str3);
        } catch (Exception e2) {
            Log.e(TAG, "Failed to send message to Unity", e2);
        }
    }
}
