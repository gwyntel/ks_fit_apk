package com.yc.utesdk.listener;

import com.yc.utesdk.bean.ReplySmsInfo;
import java.util.List;

/* loaded from: classes4.dex */
public interface QuickReplySmsListener {
    public static final int APP_SEND_SMS_FAIL = 6;
    public static final int APP_SEND_SMS_SUCCESS = 5;
    public static final int CLOSE_QUICK_REPLY_SMS = 2;
    public static final int OPEN_QUICK_REPLY_SMS = 1;
    public static final int SMS_CONTENT_SYNCING = 3;
    public static final int SMS_CONTENT_SYNC_RESULT = 4;

    void onQuerySmsContent(boolean z2, int i2, List<ReplySmsInfo> list);

    void onQuickReplySmsContent(String str, String str2);

    void onQuickReplySmsStatus(boolean z2, int i2);
}
