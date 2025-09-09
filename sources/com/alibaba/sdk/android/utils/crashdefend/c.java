package com.alibaba.sdk.android.utils.crashdefend;

import android.util.Log;

/* loaded from: classes2.dex */
public class c implements Cloneable {

    /* renamed from: a, reason: collision with root package name */
    public int f8951a;

    /* renamed from: a, reason: collision with other field name */
    public long f50a;

    /* renamed from: a, reason: collision with other field name */
    public String f52a;

    /* renamed from: b, reason: collision with root package name */
    public int f8952b;

    /* renamed from: b, reason: collision with other field name */
    public long f53b;

    /* renamed from: b, reason: collision with other field name */
    public String f54b;
    public int crashCount;

    /* renamed from: c, reason: collision with root package name */
    public int f8953c = 0;

    /* renamed from: c, reason: collision with other field name */
    public volatile boolean f55c = false;

    /* renamed from: a, reason: collision with other field name */
    public SDKMessageCallback f51a = null;

    public Object clone() {
        try {
            return (c) super.clone();
        } catch (CloneNotSupportedException e2) {
            Log.e("CrashSDK", "clone fail:", e2);
            return null;
        }
    }
}
