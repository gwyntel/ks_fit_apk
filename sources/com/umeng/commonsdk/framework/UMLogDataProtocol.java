package com.umeng.commonsdk.framework;

import org.json.JSONObject;

/* loaded from: classes4.dex */
public interface UMLogDataProtocol {

    public enum UMBusinessType {
        U_APP,
        U_INTERNAL,
        U_ZeroEnv,
        U_Silent
    }

    void removeCacheData(Object obj);

    JSONObject setupReportData(long j2);

    void workEvent(Object obj, int i2);
}
