package com.yc.utesdk.listener;

import com.yc.utesdk.bean.SosCallInfo;
import java.util.List;

/* loaded from: classes4.dex */
public interface SosContactsListener {
    public static final int CLOSE_SOS_CONTACTS_SWITCH = 5;
    public static final int OPEN_SOS_CONTACTS_SWITCH = 4;
    public static final int SYNCING = 1;
    public static final int SYNC_FAIL = 3;
    public static final int SYNC_SUCCESS = 2;

    void onQuerySosContacts(boolean z2, List<SosCallInfo> list);

    void onQuerySosContactsCount(boolean z2, int i2);

    void onSosContactsStatus(boolean z2, int i2);

    void onSosContactsSync(int i2);
}
