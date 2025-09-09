package com.yc.utesdk.listener;

import com.yc.utesdk.bean.BodyInfo;
import java.util.List;

/* loaded from: classes4.dex */
public interface BodyFatChangeListener {
    public static final int BODY_FAT_TESTING_GET_OFF = 5;
    public static final int BODY_FAT_TESTING_GET_OFF_FINISH = 4;
    public static final int BODY_FAT_TESTING_WEAR = 6;
    public static final int BODY_FAT_TEST_FAIL = 3;
    public static final int BODY_FAT_TEST_STATUS_NO = 9;
    public static final int BODY_FAT_TEST_STATUS_YES = 8;
    public static final int QUERY_BODY_FAT_TEST_STATUS = 7;
    public static final int SET_BODY_FAT_PERSON_INFO = 1;
    public static final int START_TEST_BODY_FAT = 2;

    void onBodyFatRealTime(BodyInfo bodyInfo);

    void onBodyFatStatus(boolean z2, int i2);

    void onBodyFatSyncFail();

    void onBodyFatSyncSuccess(List<BodyInfo> list);

    void onBodyFatSyncing();
}
