package com.alibaba.ailabs.iot.mesh.grouping;

import com.alibaba.ailabs.iot.mesh.callback.ConfigActionListener;

/* loaded from: classes2.dex */
public interface IMeshGroupingService {
    void configModelSubscription(String str, String str2, String str3, ConfigActionListener<Boolean> configActionListener);
}
