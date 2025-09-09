package com.huawei.hms.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.huawei.hms.activity.internal.BusResponseCallback;
import com.huawei.hms.activity.internal.ForegroundBusResponseMgr;
import com.huawei.hms.activity.internal.ForegroundInnerHeader;
import com.huawei.hms.common.internal.RequestHeader;
import com.huawei.hms.common.internal.TransactionIdCreater;
import com.huawei.hms.support.api.entity.core.CoreNaming;
import com.huawei.hms.utils.Util;

/* loaded from: classes.dex */
public class ForegroundIntentBuilder {

    /* renamed from: a, reason: collision with root package name */
    private Activity f15731a;

    /* renamed from: b, reason: collision with root package name */
    private RequestHeader f15732b;

    /* renamed from: c, reason: collision with root package name */
    private String f15733c;

    /* renamed from: d, reason: collision with root package name */
    private ForegroundInnerHeader f15734d;

    /* renamed from: e, reason: collision with root package name */
    private String f15735e;

    /* renamed from: f, reason: collision with root package name */
    private Context f15736f;

    public ForegroundIntentBuilder(Activity activity) throws IllegalArgumentException {
        if (activity == null) {
            throw new IllegalArgumentException("listener must not be null.");
        }
        this.f15731a = activity;
        RequestHeader requestHeader = new RequestHeader();
        this.f15732b = requestHeader;
        requestHeader.setSdkVersion(60800300);
        this.f15733c = "";
        ForegroundInnerHeader foregroundInnerHeader = new ForegroundInnerHeader();
        this.f15734d = foregroundInnerHeader;
        foregroundInnerHeader.setApkVersion(30000000);
    }

    public static void registerResponseCallback(String str, BusResponseCallback busResponseCallback) {
        ForegroundBusResponseMgr.getInstance().registerObserver(str, busResponseCallback);
    }

    public static void unregisterResponseCallback(String str) {
        ForegroundBusResponseMgr.getInstance().unRegisterObserver(str);
    }

    public Intent build() {
        String packageName;
        String appId;
        Intent intentStartBridgeActivity = BridgeActivity.getIntentStartBridgeActivity(this.f15731a, ForegroundBusDelegate.class.getName());
        Context context = this.f15736f;
        if (context != null) {
            packageName = context.getPackageName();
            appId = Util.getAppId(this.f15736f);
        } else {
            packageName = this.f15731a.getPackageName();
            appId = Util.getAppId(this.f15731a);
        }
        if (this.f15732b.getAppID() == null) {
            this.f15732b.setAppID(appId + "|");
        } else {
            this.f15732b.setAppID(appId + "|" + this.f15732b.getAppID());
        }
        if (TextUtils.isEmpty(this.f15732b.getTransactionId())) {
            RequestHeader requestHeader = this.f15732b;
            requestHeader.setTransactionId(TransactionIdCreater.getId(requestHeader.getAppID(), CoreNaming.HUBREQUEST));
        }
        this.f15732b.setPkgName(packageName);
        intentStartBridgeActivity.putExtra(ForegroundBusDelegate.HMS_FOREGROUND_REQ_HEADER, this.f15732b.toJson());
        intentStartBridgeActivity.putExtra(ForegroundBusDelegate.HMS_FOREGROUND_REQ_BODY, this.f15733c);
        intentStartBridgeActivity.putExtra(ForegroundBusDelegate.HMS_FOREGROUND_REQ_INNER, this.f15734d.toJson());
        if (!TextUtils.isEmpty(this.f15735e)) {
            intentStartBridgeActivity.putExtra(ForegroundBusDelegate.INNER_PKG_NAME, this.f15735e);
        }
        return intentStartBridgeActivity;
    }

    public ForegroundIntentBuilder setAction(String str) {
        this.f15732b.setApiName(str);
        return this;
    }

    public ForegroundIntentBuilder setApiLevel(int i2) {
        this.f15732b.setApiLevel(i2);
        return this;
    }

    public ForegroundIntentBuilder setApplicationContext(Context context) {
        this.f15736f = context;
        return this;
    }

    public ForegroundIntentBuilder setInnerHms() {
        this.f15735e = this.f15731a.getPackageName();
        return this;
    }

    public ForegroundIntentBuilder setKitSdkVersion(int i2) {
        this.f15732b.setKitSdkVersion(i2);
        return this;
    }

    public ForegroundIntentBuilder setMinApkVersion(int i2) {
        this.f15734d.setApkVersion(i2);
        return this;
    }

    public ForegroundIntentBuilder setRequestBody(String str) {
        this.f15733c = str;
        return this;
    }

    public ForegroundIntentBuilder setResponseCallback(String str, BusResponseCallback busResponseCallback) {
        this.f15734d.setResponseCallbackKey(str);
        ForegroundBusResponseMgr.getInstance().registerObserver(str, busResponseCallback);
        return this;
    }

    public ForegroundIntentBuilder setServiceName(String str) {
        this.f15732b.setSrvName(str);
        return this;
    }

    public ForegroundIntentBuilder setSubAppId(String str) {
        this.f15732b.setAppID(str);
        return this;
    }

    public ForegroundIntentBuilder setTransactionId(String str) {
        this.f15732b.setTransactionId(str);
        return this;
    }

    public ForegroundIntentBuilder setResponseCallback(String str) {
        this.f15734d.setResponseCallbackKey(str);
        return this;
    }
}
