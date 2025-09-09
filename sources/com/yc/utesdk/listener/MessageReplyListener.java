package com.yc.utesdk.listener;

import com.yc.utesdk.bean.MessageReplyDataInfo;

/* loaded from: classes4.dex */
public interface MessageReplyListener {
    public static final int SEND_MESSAGE_REPLY_DATA = 1;

    void onMessageReplyData(MessageReplyDataInfo messageReplyDataInfo);

    void onMessageReplyState(boolean z2, int i2);
}
