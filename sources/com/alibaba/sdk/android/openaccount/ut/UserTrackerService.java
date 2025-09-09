package com.alibaba.sdk.android.openaccount.ut;

import android.app.Activity;
import java.util.Map;

/* loaded from: classes2.dex */
public interface UserTrackerService {
    String getDefaultUserTrackerId();

    void sendCustomHit(String str, int i2, String str2, long j2, String str3, Map<String, String> map);

    void sendCustomHit(String str, long j2, String str2, Map<String, String> map);

    void sendCustomHit(String str, Activity activity);

    void sendCustomHit(String str, String str2, int i2, String str3, long j2, String str4, Map<String, String> map);

    void sendCustomHit(String str, String str2, Map<String, String> map);

    void updateUserTrackerProperties(Map<String, Object> map);
}
