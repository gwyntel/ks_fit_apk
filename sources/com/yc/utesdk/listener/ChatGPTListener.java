package com.yc.utesdk.listener;

/* loaded from: classes4.dex */
public interface ChatGPTListener {
    public static final int AI_AGENT_TYPE_NOTIFY = 103;
    public static final int AI_TRANSLATE_LAN_PLAY_NOTIFY = 150;
    public static final int AI_TRANSLATE_LAN_TYPE_NOTIFY = 104;
    public static final int CHAT_GPT_STATUS_NOTIFY = 101;
    public static final int GPT_VOICE_DATA_NOTIFY = 102;
    public static final int SET_AI_AGENT_TYPE = 6;
    public static final int SET_CHAT_GPT_ANSWER_CONTENT = 3;
    public static final int SET_CHAT_GPT_LANGUAGE_ENVIRONMENT = 5;
    public static final int SET_CHAT_GPT_STATUS = 1;
    public static final int SET_CHAT_GPT_VOICE_CONTENT = 2;
    public static final int SET_LARGE_MODE_TYPE = 8;
    public static final int SYNC_AI_AGENT_MEMO_TO_DEVICE = 7;
    public static final int SYNC_CHAT_GPT_MEMO_TO_DEVICE = 4;

    <T> void onChatGPTNotify(int i2, T t2);

    void onChatGPTStatus(boolean z2, int i2);
}
