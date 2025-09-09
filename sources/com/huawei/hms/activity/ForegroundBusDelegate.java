package com.huawei.hms.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.huawei.hms.activity.internal.BusResponseCallback;
import com.huawei.hms.activity.internal.BusResponseResult;
import com.huawei.hms.activity.internal.ForegroundBusResponseMgr;
import com.huawei.hms.activity.internal.ForegroundInnerHeader;
import com.huawei.hms.adapter.AvailableAdapter;
import com.huawei.hms.api.HuaweiApiAvailability;
import com.huawei.hms.common.internal.RequestHeader;
import com.huawei.hms.common.internal.ResponseHeader;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.huawei.hms.support.hianalytics.HiAnalyticsUtil;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.utils.HMSPackageManager;
import com.huawei.hms.utils.IntentUtil;
import com.huawei.hms.utils.JsonUtil;
import com.huawei.hms.utils.UIUtil;
import com.huawei.hms.utils.Util;
import java.lang.ref.WeakReference;
import java.util.Map;

/* loaded from: classes.dex */
public class ForegroundBusDelegate implements IBridgeActivityDelegate {
    public static final String HMS_FOREGROUND_REQ_BODY = "HMS_FOREGROUND_REQ_BODY";
    public static final String HMS_FOREGROUND_REQ_HEADER = "HMS_FOREGROUND_REQ_HEADER";
    public static final String HMS_FOREGROUND_REQ_INNER = "HMS_FOREGROUND_REQ_INNER";
    public static final String HMS_FOREGROUND_RESP_HEADER = "HMS_FOREGROUND_RESP_HEADER";
    public static final String INNER_PKG_NAME = "INNER_PACKAGE_NAME";

    /* renamed from: a, reason: collision with root package name */
    private RequestHeader f15724a;

    /* renamed from: b, reason: collision with root package name */
    private String f15725b;

    /* renamed from: c, reason: collision with root package name */
    private ForegroundInnerHeader f15726c = new ForegroundInnerHeader();

    /* renamed from: d, reason: collision with root package name */
    private ResponseHeader f15727d;

    /* renamed from: e, reason: collision with root package name */
    private WeakReference<Activity> f15728e;

    /* renamed from: f, reason: collision with root package name */
    private boolean f15729f;

    private class b implements AvailableAdapter.AvailableCallBack {
        private b() {
        }

        @Override // com.huawei.hms.adapter.AvailableAdapter.AvailableCallBack
        public void onComplete(int i2) {
            if (i2 == 0) {
                ForegroundBusDelegate.this.h();
            } else {
                HMSLog.i("ForegroundBusDelegate", "version check failed");
                ForegroundBusDelegate.this.a(0, "apk version is invalid");
            }
        }
    }

    private BusResponseCallback b(String str) {
        return ForegroundBusResponseMgr.getInstance().get(str);
    }

    private void c() {
        if (this.f15724a != null) {
            a(HiAnalyticsConstant.HMS_SDK_BASE_ACTIVITY_STARTED);
        }
    }

    private void d() {
        a(HiAnalyticsConstant.HMS_SDK_BASE_START_CORE_ACTIVITY);
    }

    private void e() {
        if (g() == null) {
            HMSLog.e("ForegroundBusDelegate", "checkMinVersion failed, activity must not be null.");
            return;
        }
        if (this.f15729f) {
            h();
            return;
        }
        if (!Util.isAvailableLibExist(g().getApplicationContext())) {
            if (HuaweiApiAvailability.getInstance().isHuaweiMobileServicesAvailable(g().getApplicationContext(), this.f15726c.getApkVersion()) == 0) {
                h();
                return;
            } else {
                HMSLog.e("ForegroundBusDelegate", "checkMinVersion failed, and no available lib exists.");
                a(0, "apk version is invalid");
                return;
            }
        }
        b bVar = new b();
        AvailableAdapter availableAdapter = new AvailableAdapter(this.f15726c.getApkVersion());
        int iIsHuaweiMobileServicesAvailable = availableAdapter.isHuaweiMobileServicesAvailable(g());
        if (iIsHuaweiMobileServicesAvailable == 0) {
            bVar.onComplete(iIsHuaweiMobileServicesAvailable);
        } else if (availableAdapter.isUserResolvableError(iIsHuaweiMobileServicesAvailable)) {
            a(g(), availableAdapter, bVar);
        } else {
            bVar.onComplete(iIsHuaweiMobileServicesAvailable);
        }
    }

    private void f() {
        Activity activityG = g();
        if (activityG == null || activityG.isFinishing()) {
            return;
        }
        activityG.finish();
    }

    private Activity g() {
        WeakReference<Activity> weakReference = this.f15728e;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h() {
        HMSLog.i("ForegroundBusDelegate", "startApkHubActivity");
        Activity activityG = g();
        if (activityG == null) {
            HMSLog.e("ForegroundBusDelegate", "startApkHubActivity but activity is null");
            return;
        }
        String packageName = this.f15729f ? activityG.getPackageName() : HMSPackageManager.getInstance(activityG.getApplicationContext()).getHMSPackageNameForMultiService();
        Intent intent = new Intent(this.f15726c.getAction());
        intent.putExtra(HMS_FOREGROUND_REQ_BODY, this.f15725b);
        try {
            intent.setPackage(packageName);
        } catch (IllegalArgumentException unused) {
            HMSLog.e("ForegroundBusDelegate", "IllegalArgumentException when startApkHubActivity intent.setPackage");
        }
        intent.putExtra(BridgeActivity.EXTRA_IS_FULLSCREEN, UIUtil.isActivityFullscreen(activityG));
        intent.setClassName(packageName, "com.huawei.hms.core.activity.UiJumpActivity");
        intent.putExtra(HMS_FOREGROUND_REQ_HEADER, this.f15724a.toJson());
        intent.putExtra("intent.extra.hms.core.DELEGATE_NAME", "com.huawei.hms.core.activity.ForegroundBus");
        try {
            b();
            activityG.startActivityForResult(intent, 431057);
        } catch (ActivityNotFoundException e2) {
            HMSLog.e("ForegroundBusDelegate", "Launch sign in Intent failed. hms is probably being updated：", e2);
            a(0, "launch bus intent failed");
        }
    }

    @Override // com.huawei.hms.activity.IBridgeActivityDelegate
    public int getRequestCode() {
        return 431057;
    }

    @Override // com.huawei.hms.activity.IBridgeActivityDelegate
    public void onBridgeActivityCreate(Activity activity) {
        String stringExtra;
        RequestHeader requestHeader;
        this.f15728e = new WeakReference<>(activity);
        Intent intent = activity.getIntent();
        try {
            stringExtra = intent.getStringExtra(HMS_FOREGROUND_REQ_HEADER);
            requestHeader = new RequestHeader();
            this.f15724a = requestHeader;
        } catch (Exception e2) {
            HMSLog.e("ForegroundBusDelegate", "ForegroundBusDelegate getStringExtra error:" + e2.getMessage());
        }
        if (!requestHeader.fromJson(stringExtra)) {
            a(0, "header is invalid");
            return;
        }
        this.f15725b = intent.getStringExtra(HMS_FOREGROUND_REQ_BODY);
        ForegroundInnerHeader foregroundInnerHeader = this.f15726c;
        if (foregroundInnerHeader == null) {
            a(0, "inner header is invalid");
            return;
        }
        foregroundInnerHeader.fromJson(intent.getStringExtra(HMS_FOREGROUND_REQ_INNER));
        if (TextUtils.isEmpty(this.f15724a.getApiName())) {
            a(0, "action is invalid");
            return;
        }
        a();
        if (!TextUtils.isEmpty(intent.getStringExtra(INNER_PKG_NAME))) {
            HMSLog.i("ForegroundBusDelegate", "isUseInnerHms: true");
            this.f15729f = true;
        }
        e();
    }

    @Override // com.huawei.hms.activity.IBridgeActivityDelegate
    public void onBridgeActivityDestroy() {
        c();
        this.f15728e = null;
    }

    @Override // com.huawei.hms.activity.IBridgeActivityDelegate
    public boolean onBridgeActivityResult(int i2, int i3, Intent intent) throws IllegalArgumentException {
        if (i2 != 431057) {
            return false;
        }
        if (intent != null && intent.hasExtra(HMS_FOREGROUND_RESP_HEADER)) {
            String stringExtra = intent.getStringExtra(HMS_FOREGROUND_RESP_HEADER);
            ResponseHeader responseHeader = new ResponseHeader();
            this.f15727d = responseHeader;
            JsonUtil.jsonToEntity(stringExtra, responseHeader);
        }
        d();
        BusResponseCallback busResponseCallbackB = b(this.f15726c.getResponseCallbackKey());
        if (busResponseCallbackB == null) {
            a(i3, intent);
            return true;
        }
        BusResponseResult busResponseResultSucceedReturn = busResponseCallbackB.succeedReturn(this.f15728e.get(), i3, intent);
        if (busResponseResultSucceedReturn == null) {
            a(i3, intent);
            return true;
        }
        a(busResponseResultSucceedReturn.getCode(), busResponseResultSucceedReturn.getIntent());
        return true;
    }

    @Override // com.huawei.hms.activity.IBridgeActivityDelegate
    public void onBridgeConfigurationChanged() {
    }

    @Override // com.huawei.hms.activity.IBridgeActivityDelegate
    public void onKeyUp(int i2, KeyEvent keyEvent) {
    }

    private void b() {
        Map<String, String> mapFromForegroundRequestHeader = HiAnalyticsUtil.getInstance().getMapFromForegroundRequestHeader(this.f15724a);
        mapFromForegroundRequestHeader.put(HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, "req");
        mapFromForegroundRequestHeader.put("version", HiAnalyticsUtil.versionCodeToName(String.valueOf(this.f15724a.getKitSdkVersion())));
        if (g() != null) {
            HiAnalyticsUtil.getInstance().onNewEvent(g().getApplicationContext(), HiAnalyticsConstant.HMS_SDK_BASE_START_CORE_ACTIVITY, mapFromForegroundRequestHeader);
        }
    }

    private void a(int i2, Intent intent) {
        HMSLog.i("ForegroundBusDelegate", "succeedReturn");
        Activity activityG = g();
        if (activityG == null) {
            return;
        }
        activityG.setResult(i2, IntentUtil.modifyIntentBehaviorsSafe(intent));
        f();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i2, String str) {
        BusResponseResult busResponseResultInnerError;
        HMSLog.e("ForegroundBusDelegate", str);
        Activity activityG = g();
        if (activityG == null) {
            return;
        }
        BusResponseCallback busResponseCallbackB = b(this.f15726c.getResponseCallbackKey());
        if (busResponseCallbackB == null || (busResponseResultInnerError = busResponseCallbackB.innerError(this.f15728e.get(), i2, str)) == null) {
            activityG.setResult(0);
        } else {
            activityG.setResult(busResponseResultInnerError.getCode(), IntentUtil.modifyIntentBehaviorsSafe(busResponseResultInnerError.getIntent()));
        }
        f();
    }

    private static void a(Activity activity, AvailableAdapter availableAdapter, AvailableAdapter.AvailableCallBack availableCallBack) {
        if (activity == null) {
            HMSLog.i("ForegroundBusDelegate", "null activity, could not start resolution intent");
        }
        availableAdapter.startResolution(activity, availableCallBack);
    }

    private void a() {
        Map<String, String> mapFromForegroundRequestHeader = HiAnalyticsUtil.getInstance().getMapFromForegroundRequestHeader(this.f15724a);
        mapFromForegroundRequestHeader.put(HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, "req");
        mapFromForegroundRequestHeader.put("version", HiAnalyticsUtil.versionCodeToName(String.valueOf(this.f15724a.getKitSdkVersion())));
        if (g() != null) {
            HiAnalyticsUtil.getInstance().onNewEvent(g().getApplicationContext(), HiAnalyticsConstant.HMS_SDK_BASE_ACTIVITY_STARTED, mapFromForegroundRequestHeader);
        }
    }

    private void a(String str) {
        Map<String, String> mapFromForegroundRequestHeader = HiAnalyticsUtil.getInstance().getMapFromForegroundRequestHeader(this.f15724a);
        mapFromForegroundRequestHeader.put(HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, HiAnalyticsConstant.Direction.RESPONSE);
        mapFromForegroundRequestHeader.put("version", HiAnalyticsUtil.versionCodeToName(String.valueOf(this.f15724a.getKitSdkVersion())));
        ResponseHeader responseHeader = this.f15727d;
        if (responseHeader != null) {
            mapFromForegroundRequestHeader.put(HiAnalyticsConstant.HaKey.BI_KEY_RESULT, String.valueOf(responseHeader.getStatusCode()));
            mapFromForegroundRequestHeader.put("result", String.valueOf(this.f15727d.getErrorCode()));
        }
        if (g() != null) {
            HiAnalyticsUtil.getInstance().onNewEvent(g().getApplicationContext(), str, mapFromForegroundRequestHeader);
        }
    }
}
