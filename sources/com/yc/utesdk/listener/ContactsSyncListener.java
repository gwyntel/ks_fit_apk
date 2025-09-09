package com.yc.utesdk.listener;

/* loaded from: classes4.dex */
public interface ContactsSyncListener {
    public static final int CONTACTS_DELETE_FAIL = 5;
    public static final int CONTACTS_DELETE_SUCCESS = 4;
    public static final int CONTACTS_SYNC_FAIL = 2;
    public static final int CONTACTS_SYNC_START = 0;
    public static final int CONTACTS_SYNC_SUCCESS = 1;
    public static final int NO_CONTACTS = 3;

    void onContactsSyncProgress(int i2);

    void onContactsSyncStatus(int i2);
}
