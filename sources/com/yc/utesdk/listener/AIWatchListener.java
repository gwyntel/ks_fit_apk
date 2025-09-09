package com.yc.utesdk.listener;

/* loaded from: classes4.dex */
public interface AIWatchListener {
    public static final int AI_WATCH_STATUS_NOTIFY = 630;
    public static final int AI_WATCH_VOICE_DATA_NOTIFY = 631;
    public static final int QUERY_AI_WATCH_ENABLE = 3;
    public static final int SET_AI_WATCH_ENABLE = 2;
    public static final int SET_AI_WATCH_STATUS = 1;
    public static final int SET_AI_WATCH_VOICE_CONTENT = 4;

    <T> void onAIWatchNotify(int i2, T t2);

    void onAIWatchStatus(boolean z2, int i2);
}
