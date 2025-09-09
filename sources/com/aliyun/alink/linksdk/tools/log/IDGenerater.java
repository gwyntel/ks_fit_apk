package com.aliyun.alink.linksdk.tools.log;

import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class IDGenerater {

    /* renamed from: a, reason: collision with root package name */
    private static AtomicInteger f11457a = new AtomicInteger();

    public static int generateId() {
        return f11457a.incrementAndGet();
    }
}
