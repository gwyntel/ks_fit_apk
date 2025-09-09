package com.yc.utesdk.listener;

/* loaded from: classes4.dex */
public interface AIAgentMemoSyncListener {
    public static final int FAIL = 3;
    public static final int FINISH = 2;
    public static final int START = 1;

    void onAIAgentMemoSyncProgress(int i2);

    void onAIAgentMemoSyncState(int i2);
}
