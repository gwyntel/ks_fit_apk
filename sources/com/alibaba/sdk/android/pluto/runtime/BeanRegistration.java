package com.alibaba.sdk.android.pluto.runtime;

import java.util.Map;

/* loaded from: classes2.dex */
public interface BeanRegistration {
    void setProperties(Map<String, String> map);

    void unregister();
}
