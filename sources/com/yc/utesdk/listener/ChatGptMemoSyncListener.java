package com.yc.utesdk.listener;

/* loaded from: classes4.dex */
public interface ChatGptMemoSyncListener {
    public static final int FAIL = 3;
    public static final int FINISH = 2;
    public static final int START = 1;

    void onChatGptMemoSyncProgress(int i2);

    void onChatGptMemoSyncState(int i2);
}
