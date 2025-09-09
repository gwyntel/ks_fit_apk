package com.huawei.hms.activity.internal;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class ForegroundBusResponseMgr {

    /* renamed from: b, reason: collision with root package name */
    private static final ForegroundBusResponseMgr f15739b = new ForegroundBusResponseMgr();

    /* renamed from: a, reason: collision with root package name */
    private final Map<String, BusResponseCallback> f15740a = new HashMap();

    public static ForegroundBusResponseMgr getInstance() {
        return f15739b;
    }

    public BusResponseCallback get(String str) {
        BusResponseCallback busResponseCallback;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        synchronized (this.f15740a) {
            busResponseCallback = this.f15740a.get(str);
        }
        return busResponseCallback;
    }

    public void registerObserver(String str, BusResponseCallback busResponseCallback) {
        if (TextUtils.isEmpty(str) || busResponseCallback == null) {
            return;
        }
        synchronized (this.f15740a) {
            try {
                if (!this.f15740a.containsKey(str)) {
                    this.f15740a.put(str, busResponseCallback);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void unRegisterObserver(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        synchronized (this.f15740a) {
            this.f15740a.remove(str);
        }
    }
}
