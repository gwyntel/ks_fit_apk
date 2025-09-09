package com.crazecoder.flutterbugly;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.crazecoder.flutterbugly.bean.BuglyInitResultInfo;
import com.crazecoder.flutterbugly.utils.JsonUtil;
import com.crazecoder.flutterbugly.utils.MapUtil;
import com.heytap.mcssdk.constant.IntentConstant;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.kingsmith.miot.KsProperty;
import com.tekartik.sqflite.Constant;
import com.tencent.bugly.crashreport.BuglyLog;
import com.tencent.bugly.crashreport.CrashReport;
import io.flutter.Log;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.util.Map;

/* loaded from: classes3.dex */
public class FlutterBuglyPlugin implements FlutterPlugin, MethodChannel.MethodCallHandler, ActivityAware {

    @SuppressLint({"StaticFieldLeak"})
    private static Activity activity;
    private static MethodChannel channel;
    private FlutterPlugin.FlutterPluginBinding flutterPluginBinding;
    private boolean isResultSubmitted = false;
    private MethodChannel.Result result;

    private BuglyInitResultInfo getResultBean(boolean z2, String str, String str2) {
        BuglyInitResultInfo buglyInitResultInfo = new BuglyInitResultInfo();
        buglyInitResultInfo.setSuccess(z2);
        buglyInitResultInfo.setAppId(str);
        buglyInitResultInfo.setMessage(str2);
        return buglyInitResultInfo;
    }

    private void log(MethodCall methodCall) {
        int iIntValue = methodCall.hasArgument("log_level") ? ((Integer) methodCall.argument("log_level")).intValue() : 0;
        String str = methodCall.hasArgument("log_tag") ? (String) methodCall.argument("log_tag") : null;
        String str2 = methodCall.hasArgument("log_message") ? (String) methodCall.argument("log_message") : null;
        if (iIntValue == 1) {
            BuglyLog.e(str, str2);
            return;
        }
        if (iIntValue == 2) {
            BuglyLog.w(str, str2);
            return;
        }
        if (iIntValue == 3) {
            BuglyLog.i(str, str2);
        } else if (iIntValue == 4) {
            BuglyLog.d(str, str2);
        } else {
            if (iIntValue != 5) {
                return;
            }
            BuglyLog.v(str, str2);
        }
    }

    private void postException(MethodCall methodCall) {
        String str = methodCall.hasArgument(CrashHianalyticsData.CRASH_TYPE) ? (String) methodCall.argument(CrashHianalyticsData.CRASH_TYPE) : null;
        String str2 = methodCall.hasArgument("crash_message") ? (String) methodCall.argument("crash_message") : "";
        String str3 = methodCall.hasArgument("crash_detail") ? (String) methodCall.argument("crash_detail") : null;
        Map map = methodCall.hasArgument("crash_data") ? (Map) methodCall.argument("crash_data") : null;
        if (TextUtils.isEmpty(str3)) {
            return;
        }
        if (TextUtils.isEmpty(str)) {
            str = str2;
        }
        CrashReport.postException(8, str, str2, str3, map);
    }

    private void result(Object obj) {
        MethodChannel.Result result = this.result;
        if (result == null || this.isResultSubmitted) {
            return;
        }
        if (obj == null) {
            result.success(null);
        } else {
            result.success(JsonUtil.toJson(MapUtil.deepToMap(obj)));
        }
        this.isResultSubmitted = true;
    }

    private void setAppPackageName(MethodCall methodCall, CrashReport.UserStrategy userStrategy) {
        if (methodCall.hasArgument(IntentConstant.APP_PACKAGE)) {
            String str = (String) methodCall.argument(IntentConstant.APP_PACKAGE);
            if (TextUtils.isEmpty(str)) {
                return;
            }
            if (userStrategy == null) {
                CrashReport.setAppPackage(activity.getApplicationContext(), str);
            } else {
                userStrategy.setAppPackageName(str);
            }
        }
    }

    private void setAppVersion(MethodCall methodCall, CrashReport.UserStrategy userStrategy) {
        if (methodCall.hasArgument("appVersion")) {
            String str = (String) methodCall.argument("appVersion");
            if (TextUtils.isEmpty(str)) {
                return;
            }
            if (userStrategy == null) {
                CrashReport.setAppVersion(activity.getApplicationContext(), str);
            } else {
                userStrategy.setAppVersion(str);
            }
        }
    }

    private void setChannel(MethodCall methodCall, CrashReport.UserStrategy userStrategy) {
        if (methodCall.hasArgument("channel")) {
            String str = (String) methodCall.argument("channel");
            if (TextUtils.isEmpty(str)) {
                return;
            }
            if (userStrategy == null) {
                CrashReport.setAppChannel(activity.getApplicationContext(), str);
            } else {
                userStrategy.setAppChannel(str);
            }
        }
    }

    private void setDeviceID(MethodCall methodCall, CrashReport.UserStrategy userStrategy) {
        if (methodCall.hasArgument("deviceId")) {
            String str = (String) methodCall.argument("deviceId");
            if (TextUtils.isEmpty(str)) {
                return;
            }
            if (userStrategy == null) {
                CrashReport.setDeviceId(activity.getApplicationContext(), str);
            } else {
                userStrategy.setDeviceID(str);
            }
        }
    }

    private void setDeviceModel(MethodCall methodCall, CrashReport.UserStrategy userStrategy) {
        if (methodCall.hasArgument("deviceModel")) {
            String str = (String) methodCall.argument("deviceModel");
            if (TextUtils.isEmpty(str)) {
                return;
            }
            if (userStrategy == null) {
                CrashReport.setDeviceModel(activity.getApplicationContext(), str);
            } else {
                userStrategy.setDeviceModel(str);
            }
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(@NonNull ActivityPluginBinding activityPluginBinding) {
        activity = activityPluginBinding.getActivity();
        MethodChannel methodChannel = new MethodChannel(this.flutterPluginBinding.getBinaryMessenger(), "crazecoder/flutter_bugly");
        channel = methodChannel;
        methodChannel.setMethodCallHandler(this);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.flutterPluginBinding = flutterPluginBinding;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        channel.setMethodCallHandler(null);
        this.flutterPluginBinding = null;
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(MethodCall methodCall, @NonNull MethodChannel.Result result) {
        Integer num;
        this.isResultSubmitted = false;
        this.result = result;
        if (methodCall.method.equals("initBugly")) {
            if (!methodCall.hasArgument("appId")) {
                result(getResultBean(false, null, "Bugly appId不能为空"));
                return;
            }
            String string = methodCall.argument("appId").toString();
            CrashReport.UserStrategy userStrategy = new CrashReport.UserStrategy(activity);
            setChannel(methodCall, userStrategy);
            setDeviceID(methodCall, userStrategy);
            setDeviceModel(methodCall, userStrategy);
            setAppVersion(methodCall, userStrategy);
            setAppPackageName(methodCall, userStrategy);
            if (methodCall.hasArgument("initDelay")) {
                userStrategy.setAppReportDelay(Long.parseLong(methodCall.argument("initDelay").toString()));
            }
            if (methodCall.hasArgument("enableCatchAnrTrace")) {
                userStrategy.setEnableCatchAnrTrace(((Boolean) methodCall.argument("enableCatchAnrTrace")).booleanValue());
            }
            if (methodCall.hasArgument("enableRecordAnrMainStack")) {
                userStrategy.setEnableRecordAnrMainStack(((Boolean) methodCall.argument("enableRecordAnrMainStack")).booleanValue());
            }
            if (methodCall.hasArgument("isBuglyLogUpload")) {
                userStrategy.setBuglyLogUpload(((Boolean) methodCall.argument("isBuglyLogUpload")).booleanValue());
            }
            boolean zEquals = Boolean.TRUE.equals(methodCall.argument(Constant.METHOD_DEBUG_MODE));
            CrashReport.initCrashReport(activity.getApplicationContext(), string, zEquals, userStrategy);
            if (zEquals) {
                Log.i("FlutterBugly", "Bugly appId: " + string);
            }
            result(getResultBean(true, string, "Bugly 初始化成功"));
            return;
        }
        if (methodCall.method.equals("setUserId")) {
            if (methodCall.hasArgument("userId")) {
                CrashReport.setUserId(activity.getApplicationContext(), (String) methodCall.argument("userId"));
            }
            result(null);
            return;
        }
        if (methodCall.method.equals("setUserTag")) {
            if (methodCall.hasArgument("userTag") && (num = (Integer) methodCall.argument("userTag")) != null) {
                CrashReport.setUserSceneTag(activity.getApplicationContext(), num.intValue());
            }
            result(null);
            return;
        }
        if (methodCall.method.equals("setAppChannel")) {
            setChannel(methodCall, null);
            result(null);
            return;
        }
        if (methodCall.method.equals("setDeviceID")) {
            setDeviceID(methodCall, null);
            result(null);
            return;
        }
        if (methodCall.method.equals("setDeviceModel")) {
            setDeviceModel(methodCall, null);
            result(null);
            return;
        }
        if (methodCall.method.equals("setAppVersion")) {
            setAppVersion(methodCall, null);
            result(null);
            return;
        }
        if (methodCall.method.equals("setAppPackageName")) {
            setAppPackageName(methodCall, null);
            result(null);
            return;
        }
        if (methodCall.method.equals("putUserData")) {
            if (methodCall.hasArgument(KsProperty.Key) && methodCall.hasArgument("value")) {
                CrashReport.putUserData(activity.getApplicationContext(), (String) methodCall.argument(KsProperty.Key), (String) methodCall.argument("value"));
            }
            result(null);
            return;
        }
        if (methodCall.method.equals("postCatchedException")) {
            postException(methodCall);
            result(null);
        } else if (methodCall.method.equals("log")) {
            log(methodCall);
            result(null);
        } else {
            result.notImplemented();
            this.isResultSubmitted = true;
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding activityPluginBinding) {
    }
}
