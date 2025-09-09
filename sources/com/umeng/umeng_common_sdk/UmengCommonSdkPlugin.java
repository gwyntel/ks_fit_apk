package com.umeng.umeng_common_sdk;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class UmengCommonSdkPlugin implements FlutterPlugin, MethodChannel.MethodCallHandler {
    private static Context mContext;
    private static Boolean versionMatch = Boolean.FALSE;
    private MethodChannel channel;

    public static Context getContext() {
        return mContext;
    }

    private void initCommon(List list) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        UMConfigure.init(getContext(), (String) list.get(0), (String) list.get(2), 1, list.size() > 3 ? (String) list.get(3) : null);
    }

    private static void onAttachedEngineAdd() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            Method[] declaredMethods = MobclickAgent.class.getDeclaredMethods();
            int length = declaredMethods.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                Method method = declaredMethods[i2];
                Log.e("UMLog", "Reflect:" + method);
                if (method.getName().equals("onEventObject")) {
                    versionMatch = Boolean.TRUE;
                    break;
                }
                i2++;
            }
            if (versionMatch.booleanValue()) {
                Log.e("UMLog", "安卓依赖版本检查成功");
            } else {
                Log.e("UMLog", "安卓SDK版本过低，建议升级至8以上");
            }
            try {
                UMLog uMLog = UMConfigure.umDebugLog;
                Method declaredMethod = UMConfigure.class.getDeclaredMethod("setWraperType", String.class, String.class);
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(null, "flutter", "1.0");
                Log.i("UMLog", "setWraperType:flutter1.0 success");
            } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e2) {
                e2.printStackTrace();
                Log.e("UMLog", "setWraperType:flutter1.0" + e2.toString());
            }
        } catch (Exception e3) {
            e3.printStackTrace();
            Log.e("UMLog", "SDK版本过低，请升级至8以上" + e3.toString());
        }
    }

    private void onEvent(List list) {
        MobclickAgent.onEventObject(getContext(), (String) list.get(0), list.size() > 1 ? (Map) list.get(1) : null);
    }

    private void onPageEnd(List list) {
        String str = (String) list.get(0);
        MobclickAgent.onPageEnd(str);
        Log.i("UMLog", "onPageEnd:" + str);
    }

    private void onPageStart(List list) {
        String str = (String) list.get(0);
        MobclickAgent.onPageStart(str);
        Log.i("UMLog", "onPageStart:" + str);
    }

    private void onProfileSignIn(List list) {
        String str = (String) list.get(0);
        MobclickAgent.onProfileSignIn(str);
        Log.i("UMLog", "onProfileSignIn:" + str);
    }

    private void onProfileSignOff() {
        MobclickAgent.onProfileSignOff();
        Log.i("UMLog", "onProfileSignOff");
    }

    public static void registerWith(PluginRegistry.Registrar registrar) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        MethodChannel methodChannel = new MethodChannel(registrar.messenger(), "umeng_common_sdk");
        UmengCommonSdkPlugin umengCommonSdkPlugin = new UmengCommonSdkPlugin();
        mContext = registrar.context();
        methodChannel.setMethodCallHandler(umengCommonSdkPlugin);
        onAttachedEngineAdd();
    }

    private void reportError(List list) {
        String str = (String) list.get(0);
        MobclickAgent.reportError(getContext(), str);
        Log.i("UMLog", "reportError:" + str);
    }

    public static void setContext(Context context) {
        mContext = context;
    }

    private void setPageCollectionModeAuto() {
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        Log.i("UMLog", "setPageCollectionModeAuto");
    }

    private void setPageCollectionModeManual() {
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.MANUAL);
        Log.i("UMLog", "setPageCollectionModeManual");
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        mContext = flutterPluginBinding.getApplicationContext();
        MethodChannel methodChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "umeng_common_sdk");
        this.channel = methodChannel;
        methodChannel.setMethodCallHandler(this);
        onAttachedEngineAdd();
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.channel.setMethodCallHandler(null);
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x009d  */
    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onMethodCall(@androidx.annotation.NonNull io.flutter.plugin.common.MethodCall r3, @androidx.annotation.NonNull io.flutter.plugin.common.MethodChannel.Result r4) {
        /*
            Method dump skipped, instructions count: 320
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.umeng_common_sdk.UmengCommonSdkPlugin.onMethodCall(io.flutter.plugin.common.MethodCall, io.flutter.plugin.common.MethodChannel$Result):void");
    }
}
