package com.aliyun.alink.business.devicecenter.utils;

import android.content.Context;

/* loaded from: classes2.dex */
public class ContextHolder {

    /* renamed from: a, reason: collision with root package name */
    public Context f10632a = null;

    private static class SingletonHolder {

        /* renamed from: a, reason: collision with root package name */
        public static final ContextHolder f10633a = new ContextHolder();
    }

    public static ContextHolder getInstance() {
        return SingletonHolder.f10633a;
    }

    public Context getAppContext() {
        return this.f10632a;
    }

    public void init(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.f10632a = applicationContext;
        if (applicationContext == null) {
            this.f10632a = context;
        }
    }
}
