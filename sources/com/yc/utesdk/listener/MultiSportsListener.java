package com.yc.utesdk.listener;

import com.yc.utesdk.bean.SportsDataInfo;
import com.yc.utesdk.bean.SportsModeInfo;
import com.yc.utesdk.bean.SportsRealDataInfo;
import java.util.List;

/* loaded from: classes4.dex */
public interface MultiSportsListener {
    public static final int BLE_REQUEST_APP_START_LOCATION = 5;
    public static final int BLE_REQUEST_STOP_SEND_LOCATION_DATA = 8;
    public static final int INVALID_OPERATION = -1;
    public static final int SEND_APP_LOCATION_DATA = 7;
    public static final int SEND_APP_LOCATION_STATUS = 6;
    public static final int SPORTS_CONTINUE = 3;
    public static final int SPORTS_PAUSE = 2;
    public static final int SPORTS_REAL_TIME = 4;
    public static final int SPORTS_START = 1;
    public static final int SPORTS_STOP = 0;
    public static final int STOP_SEND_LOCATION_DATA = 9;

    void onMultiSportsRealData(SportsRealDataInfo sportsRealDataInfo);

    void onMultiSportsStatus(boolean z2, int i2, int i3);

    void onMultiSportsSyncFail();

    void onMultiSportsSyncSuccess(List<SportsDataInfo> list);

    void onMultiSportsSyncing();

    void onQuerySportsMode(boolean z2, int i2, int i3);

    void onQuerySportsModeList(boolean z2, int i2, int i3, List<SportsModeInfo> list);

    void onSetSportsModeList(boolean z2);

    void onSportStatusChange(int i2, int i3);

    void onSportsLocation(boolean z2, int i2);
}
