package com.umeng.commonsdk.service;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.framework.UMFrUtils;
import com.umeng.commonsdk.utils.UMUtils;

/* loaded from: classes4.dex */
public class UMGlobalContext {
    private static final String TAG = "UMGlobalContext";
    private String mAppVersion;
    private String mAppkey;
    private Context mApplicationContext;
    private String mChannel;
    private String mProcessName;

    private static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final UMGlobalContext f22287a = new UMGlobalContext();

        private a() {
        }
    }

    public static Context getAppContext(Context context) {
        if (a.f22287a.mApplicationContext == null && context != null) {
            a.f22287a.mApplicationContext = context.getApplicationContext();
        }
        return a.f22287a.mApplicationContext;
    }

    public static UMGlobalContext getInstance(Context context) {
        if (a.f22287a.mApplicationContext == null && context != null) {
            a.f22287a.mApplicationContext = context;
        }
        return a.f22287a;
    }

    public String getAppVersion() {
        if (TextUtils.isEmpty(this.mAppVersion)) {
            this.mAppVersion = UMUtils.getAppVersionName(this.mApplicationContext);
        }
        return this.mAppVersion;
    }

    public String getAppkey() {
        if (TextUtils.isEmpty(this.mAppkey)) {
            this.mAppkey = UMConfigure.sAppkey;
        }
        return this.mAppkey;
    }

    public String getChannel() {
        if (TextUtils.isEmpty(this.mChannel)) {
            this.mChannel = UMConfigure.sChannel;
        }
        return this.mChannel;
    }

    public String getProcessName(Context context) {
        if (TextUtils.isEmpty(this.mProcessName)) {
            if (context != null) {
                Context context2 = a.f22287a.mApplicationContext;
                if (context2 != null) {
                    this.mProcessName = UMFrUtils.getCurrentProcessName(context2);
                } else {
                    this.mProcessName = UMFrUtils.getCurrentProcessName(context);
                }
            } else {
                this.mProcessName = UMFrUtils.getCurrentProcessName(a.f22287a.mApplicationContext);
            }
        }
        return this.mProcessName;
    }

    public boolean isMainProcess(Context context) {
        return UMUtils.isMainProgress(context);
    }

    public String toString() {
        if (a.f22287a.mApplicationContext == null) {
            return "uninitialized.";
        }
        StringBuilder sb = new StringBuilder("[");
        sb.append("appkey:" + this.mAppkey + ",");
        sb.append("channel:" + this.mChannel + ",");
        sb.append("procName:" + this.mProcessName + "]");
        return sb.toString();
    }

    private UMGlobalContext() {
        this.mProcessName = "";
    }

    public static Context getAppContext() {
        return a.f22287a.mApplicationContext;
    }
}
