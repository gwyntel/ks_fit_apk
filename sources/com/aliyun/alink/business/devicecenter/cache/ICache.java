package com.aliyun.alink.business.devicecenter.cache;

import java.util.List;

/* loaded from: classes2.dex */
public interface ICache<T> {
    void clearCache();

    T getCache(String... strArr);

    void updateCache(List<T> list);
}
