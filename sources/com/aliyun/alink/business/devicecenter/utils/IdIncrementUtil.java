package com.aliyun.alink.business.devicecenter.utils;

import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class IdIncrementUtil {

    /* renamed from: a, reason: collision with root package name */
    public static AtomicInteger f10641a = new AtomicInteger();

    public static int getId() {
        return f10641a.incrementAndGet();
    }
}
