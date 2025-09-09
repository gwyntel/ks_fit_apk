package com.yc.utesdk.listener;

/* loaded from: classes4.dex */
public interface BluetoothDisconnectReminderListener {
    public static final int CLOSE_REMINDER_SWITCH = 2;
    public static final int OPEN_REMINDER_SWITCH = 1;
    public static final int QUERY_REMINDER_SWITCH_STATUS = 3;
    public static final int REMINDER_SWITCH_NO = 5;
    public static final int REMINDER_SWITCH_YES = 4;

    void onBlutoothDisconnectReminder(boolean z2, int i2);
}
