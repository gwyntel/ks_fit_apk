package com.alibaba.sdk.android.utils;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.sdk.android.utils.crashdefend.SDKMessageCallback;
import com.alibaba.sdk.android.utils.crashdefend.c;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class AlicloudTrackerManager {

    /* renamed from: a, reason: collision with root package name */
    private static AlicloudTrackerManager f8938a;

    /* renamed from: a, reason: collision with other field name */
    private a f42a = new a();

    /* renamed from: a, reason: collision with other field name */
    private com.alibaba.sdk.android.utils.crashdefend.b f43a;

    /* renamed from: b, reason: collision with root package name */
    private Map<String, AlicloudTracker> f8939b;

    private AlicloudTrackerManager(Application application) {
        this.f43a = null;
        HashMap map = new HashMap(4);
        map.put("kVersion", "2.0.0");
        map.put("packageName", application.getPackageName());
        this.f42a.a(application, map);
        this.f8939b = new HashMap();
        this.f43a = com.alibaba.sdk.android.utils.crashdefend.b.a(application, this.f42a);
    }

    public static synchronized AlicloudTrackerManager getInstance(Application application) {
        if (application == null) {
            return null;
        }
        try {
            if (f8938a == null) {
                f8938a = new AlicloudTrackerManager(application);
            }
            return f8938a;
        } catch (Throwable th) {
            throw th;
        }
    }

    public AlicloudTracker getTracker(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            Log.e("AlicloudTrackerManager", "sdkId or sdkVersion is null");
            return null;
        }
        String str3 = str + str2;
        if (this.f8939b.containsKey(str3)) {
            return this.f8939b.get(str3);
        }
        AlicloudTracker alicloudTracker = new AlicloudTracker(this.f42a, str, str2);
        this.f8939b.put(str3, alicloudTracker);
        return alicloudTracker;
    }

    public boolean registerCrashDefend(String str, String str2, int i2, int i3, SDKMessageCallback sDKMessageCallback) {
        if (this.f43a == null) {
            return false;
        }
        c cVar = new c();
        cVar.f52a = str;
        cVar.f54b = str2;
        cVar.f8951a = i2;
        cVar.f8952b = i3;
        return this.f43a.m52a(cVar, sDKMessageCallback);
    }

    public void unregisterCrashDefend(String str, String str2) {
        this.f43a.b(str, str2);
    }
}
