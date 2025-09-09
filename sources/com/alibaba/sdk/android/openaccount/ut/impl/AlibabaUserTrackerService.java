package com.alibaba.sdk.android.openaccount.ut.impl;

import android.app.Activity;
import android.content.Context;
import com.alibaba.sdk.android.openaccount.Environment;
import com.alibaba.sdk.android.openaccount.OpenAccountSDK;
import com.alibaba.sdk.android.openaccount.config.ConfigService;
import com.alibaba.sdk.android.openaccount.config.EnvironmentChangeListener;
import com.alibaba.sdk.android.openaccount.model.User;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.alibaba.sdk.android.openaccount.ut.UTConstants;
import com.alibaba.sdk.android.openaccount.ut.UserTrackerService;
import com.alibaba.sdk.android.pluto.annotation.Autowired;
import com.ut.device.UTDevice;
import java.util.Map;

/* loaded from: classes2.dex */
public class AlibabaUserTrackerService implements UserTrackerService, EnvironmentChangeListener {
    public static final String DISABLE_UT_INITIALIZATION_KEY = "disableUTInit";
    private static final String TAG = "oa_ut";
    public static final String USE_BASE_REQUEST_AUTHENTICATION_KEY = "useBaseRequestAuthentication";
    private String appKey;
    private String appVersion;

    @Autowired
    private ConfigService configService;
    private Context context;

    private void initBaseRequestAuthentication() {
    }

    private boolean is2ndUTLibrary() throws ClassNotFoundException {
        try {
            Class.forName("com.ut.mini.IUTApplication");
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    private boolean isSecurityGuardAvaiable() throws ClassNotFoundException {
        try {
            Class.forName("com.alibaba.wireless.security.open.SecurityGuardManager");
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    private void updateUTUser(User user) {
    }

    @Override // com.alibaba.sdk.android.openaccount.ut.UserTrackerService
    public String getDefaultUserTrackerId() {
        return UTDevice.getUtdid(this.context);
    }

    public synchronized void init(Context context) {
        try {
            AliSDKLogger.i(TAG, "do NOT ini ut in this version");
        } catch (Exception e2) {
            AliSDKLogger.printStackTraceAndMore(e2);
            throw new RuntimeException(e2);
        }
    }

    @Override // com.alibaba.sdk.android.openaccount.config.EnvironmentChangeListener
    public void onEnvironmentChange(Environment environment, Environment environment2) {
        init(OpenAccountSDK.getAndroidContext());
    }

    @Override // com.alibaba.sdk.android.openaccount.ut.UserTrackerService
    public void sendCustomHit(String str, long j2, String str2, Map<String, String> map) {
    }

    @Override // com.alibaba.sdk.android.openaccount.ut.UserTrackerService
    public void updateUserTrackerProperties(Map<String, Object> map) {
    }

    @Override // com.alibaba.sdk.android.openaccount.ut.UserTrackerService
    public void sendCustomHit(String str, String str2, int i2, String str3, long j2, String str4, Map<String, String> map) {
    }

    @Override // com.alibaba.sdk.android.openaccount.ut.UserTrackerService
    public void sendCustomHit(String str, Activity activity) {
        sendCustomHit(str, 60L, activity != null ? activity.getTitle().toString() : str, null);
    }

    @Override // com.alibaba.sdk.android.openaccount.ut.UserTrackerService
    public void sendCustomHit(String str, String str2, Map<String, String> map) {
        sendCustomHit(str, 60L, str2, map);
    }

    @Override // com.alibaba.sdk.android.openaccount.ut.UserTrackerService
    public void sendCustomHit(String str, int i2, String str2, long j2, String str3, Map<String, String> map) {
        sendCustomHit(UTConstants.TRACKER_ID, str, i2, str2, j2, str3, map);
    }
}
