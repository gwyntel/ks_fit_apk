package com.xiaomi.push;

import android.net.NetworkInfo;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public class bj {

    /* renamed from: a, reason: collision with root package name */
    private final NetworkInfo f23495a;

    /* renamed from: a, reason: collision with other field name */
    private final ConcurrentHashMap<String, Object> f211a = new ConcurrentHashMap<>();

    public bj(NetworkInfo networkInfo) {
        this.f23495a = networkInfo;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00bc  */
    /* JADX WARN: Type inference failed for: r1v10, types: [java.lang.Boolean] */
    /* JADX WARN: Type inference failed for: r1v11, types: [android.net.NetworkInfo$State] */
    /* JADX WARN: Type inference failed for: r1v12, types: [android.net.NetworkInfo$DetailedState] */
    /* JADX WARN: Type inference failed for: r1v13, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v14, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v15, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.lang.Integer] */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.lang.Integer] */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v6, types: [java.lang.Boolean] */
    /* JADX WARN: Type inference failed for: r1v7, types: [java.lang.Boolean] */
    /* JADX WARN: Type inference failed for: r1v8, types: [java.lang.Boolean] */
    /* JADX WARN: Type inference failed for: r1v9, types: [java.lang.Boolean] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private <T> T a(java.lang.String r3) {
        /*
            Method dump skipped, instructions count: 424
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.bj.a(java.lang.String):java.lang.Object");
    }

    public int b() {
        return ((Integer) a("getSubtype")).intValue();
    }

    public String c() {
        return (String) a("getExtraInfo");
    }

    public String toString() {
        return (String) a("toString");
    }

    /* renamed from: b, reason: collision with other method in class */
    public String m213b() {
        return (String) a("getSubtypeName");
    }

    public int a() {
        return ((Integer) a("getType")).intValue();
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m211a() {
        return (String) a("getTypeName");
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m212a() {
        return ((Boolean) a("isConnected")).booleanValue();
    }

    /* renamed from: a, reason: collision with other method in class */
    public NetworkInfo.State m210a() {
        return (NetworkInfo.State) a("getState");
    }

    /* renamed from: a, reason: collision with other method in class */
    public NetworkInfo.DetailedState m209a() {
        return (NetworkInfo.DetailedState) a("getDetailedState");
    }
}
