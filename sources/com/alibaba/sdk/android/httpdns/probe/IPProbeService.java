package com.alibaba.sdk.android.httpdns.probe;

/* loaded from: classes2.dex */
public interface IPProbeService {

    public enum a {
        NO_PROBING,
        PROBING
    }

    a getProbeStatus(String str);

    void launchIPProbeTask(String str, int i2, String[] strArr);

    void setIPListUpdateCallback(b bVar);

    boolean stopIPProbeTask(String str);
}
