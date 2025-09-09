package com.umeng.message;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.api.UPushAliasCallback;
import com.umeng.message.api.UPushRegisterCallback;
import com.umeng.message.api.UPushSettingCallback;
import com.umeng.message.api.UPushTagCallback;
import com.umeng.message.common.UPLog;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.entity.UMessage;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import org.android.agoo.honor.HonorRegister;
import org.android.agoo.huawei.HuaWeiRegister;
import org.android.agoo.mezu.MeizuRegister;
import org.android.agoo.oppo.OppoRegister;
import org.android.agoo.vivo.VivoRegister;
import org.android.agoo.xiaomi.MiPushRegistar;

/* loaded from: classes4.dex */
public class UmengPushSdkPlugin implements FlutterPlugin, MethodChannel.MethodCallHandler {
    private static final String TAG = "UPush.Flutter";

    @SuppressLint({"StaticFieldLeak"})
    private static UmengPushSdkPlugin sInstance;
    private static String sOfflineMsgCache;
    private MethodChannel mChannel;
    private Context mContext;
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public static class META_DATA {
        public static final String MZ_APP_ID = "org.android.agoo.meizu.app_id";
        public static final String MZ_APP_KEY = "org.android.agoo.meizu.app_key";
        public static final String OP_APP_KEY = "org.android.agoo.oppo.app_key";
        public static final String OP_APP_SECRET = "org.android.agoo.oppo.app_secret";
        public static final String XM_APP_ID = "org.android.agoo.xiaomi.app_id";
        public static final String XM_APP_KEY = "org.android.agoo.xiaomi.app_key";

        private META_DATA() {
        }
    }

    public UmengPushSdkPlugin() {
        sInstance = this;
    }

    private void addAlias(MethodCall methodCall, final MethodChannel.Result result) {
        PushAgent.getInstance(this.mContext).addAlias((String) getParam(methodCall, result, PushConstants.SUB_ALIAS_STATUS_NAME), (String) getParam(methodCall, result, "type"), new UPushAliasCallback() { // from class: com.umeng.message.UmengPushSdkPlugin.6
            @Override // com.umeng.message.api.UPushAliasCallback
            public void onMessage(boolean z2, String str) {
                UPLog.i(UmengPushSdkPlugin.TAG, "onMessage:" + z2 + " s:" + str);
                UmengPushSdkPlugin.this.executeOnMain(result, Boolean.valueOf(z2));
            }
        });
    }

    private void addTags(List<String> list, final MethodChannel.Result result) {
        String[] strArr = new String[list.size()];
        list.toArray(strArr);
        PushAgent.getInstance(this.mContext).getTagManager().addTags(new UPushTagCallback<ITagManager.Result>() { // from class: com.umeng.message.UmengPushSdkPlugin.4
            @Override // com.umeng.message.api.UPushTagCallback
            public void onMessage(boolean z2, ITagManager.Result result2) {
                UmengPushSdkPlugin.this.executeOnMain(result, Boolean.valueOf(z2));
            }
        }, strArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void executeOnMain(final MethodChannel.Result result, final Object obj) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            this.mHandler.post(new Runnable() { // from class: com.umeng.message.UmengPushSdkPlugin.13
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        result.success(obj);
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
            });
            return;
        }
        try {
            result.success(obj);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void getDeviceToken(MethodChannel.Result result) {
        result.success(PushAgent.getInstance(this.mContext).getRegistrationId());
    }

    public static UmengPushSdkPlugin getInstance() {
        return sInstance;
    }

    public static <T> T getParam(MethodCall methodCall, MethodChannel.Result result, String str) {
        T t2 = (T) methodCall.argument(str);
        if (t2 == null) {
            result.error("missing param", "cannot find param:" + str, 1);
        }
        return t2;
    }

    private void getTags(final MethodChannel.Result result) {
        PushAgent.getInstance(this.mContext).getTagManager().getTags(new UPushTagCallback<List<String>>() { // from class: com.umeng.message.UmengPushSdkPlugin.2
            @Override // com.umeng.message.api.UPushTagCallback
            public void onMessage(boolean z2, List<String> list) {
                if (z2) {
                    UmengPushSdkPlugin.this.executeOnMain(result, list);
                } else {
                    UmengPushSdkPlugin.this.executeOnMain(result, Collections.emptyList());
                }
            }
        });
    }

    private boolean pushMethodCall(MethodCall methodCall, MethodChannel.Result result) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Integer num;
        String str = methodCall.method;
        if ("setLogEnable".equals(str)) {
            Boolean bool = (Boolean) methodCall.arguments();
            if (bool != null) {
                UMConfigure.setLogEnabled(bool.booleanValue());
            }
            executeOnMain(result, null);
            return true;
        }
        if ("register".equals(str)) {
            register(result);
            return true;
        }
        if ("getDeviceToken".equals(str)) {
            getDeviceToken(result);
            return true;
        }
        if ("enable".equals(str)) {
            Boolean bool2 = (Boolean) methodCall.arguments();
            if (bool2 != null) {
                setPushEnable(bool2.booleanValue(), result);
            }
            return true;
        }
        if (com.taobao.agoo.a.a.a.JSON_CMD_SETALIAS.equals(str)) {
            setAlias(methodCall, result);
            return true;
        }
        if ("addAlias".equals(str)) {
            addAlias(methodCall, result);
            return true;
        }
        if (com.taobao.agoo.a.a.a.JSON_CMD_REMOVEALIAS.equals(str)) {
            removeAlias(methodCall, result);
            return true;
        }
        if ("addTag".equals(str)) {
            List<String> list = (List) methodCall.arguments();
            if (list != null) {
                addTags(list, result);
            }
            return true;
        }
        if ("removeTag".equals(str)) {
            List<String> list2 = (List) methodCall.arguments();
            if (list2 != null) {
                removeTags(list2, result);
            }
            return true;
        }
        if ("getTags".equals(str)) {
            getTags(result);
            return true;
        }
        if (!"setBadge".equals(str) || (num = (Integer) methodCall.arguments()) == null) {
            return false;
        }
        setBadge(num.intValue(), result);
        return false;
    }

    private void register(MethodChannel.Result result) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        UMConfigure.init(this.mContext, null, null, 1, null);
        PushAgent pushAgent = PushAgent.getInstance(this.mContext);
        pushAgent.setDisplayNotificationNumber(0);
        pushAgent.setMessageHandler(new UmengMessageHandler() { // from class: com.umeng.message.UmengPushSdkPlugin.9
            @Override // com.umeng.message.UmengMessageHandler
            public void dealWithCustomMessage(Context context, final UMessage uMessage) {
                super.dealWithCustomMessage(context, uMessage);
                UPLog.i(UmengPushSdkPlugin.TAG, "dealWithCustomMessage");
                UmengPushSdkPlugin.this.mHandler.post(new Runnable() { // from class: com.umeng.message.UmengPushSdkPlugin.9.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            MethodChannel methodChannel = UmengPushSdkPlugin.this.mChannel;
                            if (methodChannel != null) {
                                methodChannel.invokeMethod("onMessage", uMessage.getRaw().toString());
                            }
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                    }
                });
            }

            @Override // com.umeng.message.UmengMessageHandler
            public void dealWithNotificationMessage(Context context, final UMessage uMessage) {
                super.dealWithNotificationMessage(context, uMessage);
                UmengPushSdkPlugin.this.mHandler.post(new Runnable() { // from class: com.umeng.message.UmengPushSdkPlugin.9.2
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            MethodChannel methodChannel = UmengPushSdkPlugin.this.mChannel;
                            if (methodChannel != null) {
                                methodChannel.invokeMethod("onNotificationReceive", uMessage.getRaw().toString());
                            }
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                    }
                });
            }
        });
        pushAgent.setNotificationClickHandler(new UmengNotificationClickHandler() { // from class: com.umeng.message.UmengPushSdkPlugin.10
            @Override // com.umeng.message.UmengNotificationClickHandler, com.umeng.message.api.UPushMessageHandler
            public void handleMessage(Context context, final UMessage uMessage) {
                super.handleMessage(context, uMessage);
                if (uMessage.dismiss) {
                    return;
                }
                UmengPushSdkPlugin.this.mHandler.post(new Runnable() { // from class: com.umeng.message.UmengPushSdkPlugin.10.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            MethodChannel methodChannel = UmengPushSdkPlugin.this.mChannel;
                            if (methodChannel != null) {
                                methodChannel.invokeMethod("onNotificationOpen", uMessage.getRaw().toString());
                            } else {
                                UmengPushSdkPlugin.sOfflineMsgCache = uMessage.getRaw().toString();
                            }
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                    }
                });
            }
        });
        pushAgent.register(new UPushRegisterCallback() { // from class: com.umeng.message.UmengPushSdkPlugin.11
            @Override // com.umeng.message.api.UPushRegisterCallback
            public void onFailure(String str, String str2) {
                UPLog.i(UmengPushSdkPlugin.TAG, "register failure s:" + str + " s1:" + str2);
            }

            @Override // com.umeng.message.api.UPushRegisterCallback
            public void onSuccess(final String str) {
                UPLog.i(UmengPushSdkPlugin.TAG, "register success deviceToken:" + str);
                UmengPushSdkPlugin.this.mHandler.post(new Runnable() { // from class: com.umeng.message.UmengPushSdkPlugin.11.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            MethodChannel methodChannel = UmengPushSdkPlugin.this.mChannel;
                            if (methodChannel != null) {
                                methodChannel.invokeMethod("onToken", str);
                            }
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                    }
                });
                try {
                    ApplicationInfo applicationInfo = UmengPushSdkPlugin.this.mContext.getPackageManager().getApplicationInfo(UmengPushSdkPlugin.this.mContext.getPackageName(), 128);
                    try {
                        MiPushRegistar.register(UmengPushSdkPlugin.this.mContext, applicationInfo.metaData.getString(META_DATA.XM_APP_ID).replace("appid=", ""), applicationInfo.metaData.getString(META_DATA.XM_APP_KEY).replace("appkey=", ""));
                    } catch (Throwable th) {
                        UPLog.e(UmengPushSdkPlugin.TAG, "xiaomi register err:", th.getMessage());
                    }
                    try {
                        MeizuRegister.register(UmengPushSdkPlugin.this.mContext, applicationInfo.metaData.getString(META_DATA.MZ_APP_ID).replace("appid=", ""), applicationInfo.metaData.getString(META_DATA.MZ_APP_KEY).replace("appkey=", ""));
                    } catch (Throwable th2) {
                        UPLog.e(UmengPushSdkPlugin.TAG, "mz register err:", th2.getMessage());
                    }
                    try {
                        OppoRegister.register(UmengPushSdkPlugin.this.mContext, applicationInfo.metaData.getString(META_DATA.OP_APP_KEY), applicationInfo.metaData.getString(META_DATA.OP_APP_SECRET));
                    } catch (Throwable th3) {
                        UPLog.e(UmengPushSdkPlugin.TAG, "oppo register err:", th3.getMessage());
                    }
                    try {
                        VivoRegister.register(UmengPushSdkPlugin.this.mContext);
                    } catch (Throwable th4) {
                        UPLog.e(UmengPushSdkPlugin.TAG, "vivo register err:", th4.getMessage());
                    }
                    try {
                        HuaWeiRegister.register(UmengPushSdkPlugin.this.mContext);
                    } catch (Throwable th5) {
                        UPLog.e(UmengPushSdkPlugin.TAG, "huawei register err:", th5.getMessage());
                    }
                    try {
                        HonorRegister.register(UmengPushSdkPlugin.this.mContext);
                    } catch (Throwable th6) {
                        UPLog.e(UmengPushSdkPlugin.TAG, "honor register err:", th6.getMessage());
                    }
                } catch (Throwable th7) {
                    UPLog.e(UmengPushSdkPlugin.TAG, "register err:", th7.getMessage());
                }
            }
        });
        executeOnMain(result, null);
        if (TextUtils.isEmpty(sOfflineMsgCache)) {
            return;
        }
        final String str = sOfflineMsgCache;
        sInstance.mHandler.post(new Runnable() { // from class: com.umeng.message.UmengPushSdkPlugin.12
            @Override // java.lang.Runnable
            public void run() {
                try {
                    MethodChannel methodChannel = UmengPushSdkPlugin.sInstance.mChannel;
                    if (methodChannel != null) {
                        methodChannel.invokeMethod("onNotificationOpen", str);
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        });
        sOfflineMsgCache = null;
    }

    public static void registerWith(PluginRegistry.Registrar registrar) {
        MethodChannel methodChannel = new MethodChannel(registrar.messenger(), "u-push");
        UmengPushSdkPlugin umengPushSdkPlugin = new UmengPushSdkPlugin();
        umengPushSdkPlugin.mContext = registrar.context();
        umengPushSdkPlugin.mChannel = methodChannel;
        methodChannel.setMethodCallHandler(umengPushSdkPlugin);
    }

    private void removeAlias(MethodCall methodCall, final MethodChannel.Result result) {
        PushAgent.getInstance(this.mContext).deleteAlias((String) getParam(methodCall, result, PushConstants.SUB_ALIAS_STATUS_NAME), (String) getParam(methodCall, result, "type"), new UPushAliasCallback() { // from class: com.umeng.message.UmengPushSdkPlugin.5
            @Override // com.umeng.message.api.UPushAliasCallback
            public void onMessage(boolean z2, String str) {
                UPLog.i(UmengPushSdkPlugin.TAG, "onMessage:" + z2 + " s:" + str);
                UmengPushSdkPlugin.this.executeOnMain(result, Boolean.valueOf(z2));
            }
        });
    }

    private void removeTags(List<String> list, final MethodChannel.Result result) {
        String[] strArr = new String[list.size()];
        list.toArray(strArr);
        PushAgent.getInstance(this.mContext).getTagManager().deleteTags(new UPushTagCallback<ITagManager.Result>() { // from class: com.umeng.message.UmengPushSdkPlugin.3
            @Override // com.umeng.message.api.UPushTagCallback
            public void onMessage(boolean z2, ITagManager.Result result2) {
                UmengPushSdkPlugin.this.executeOnMain(result, Boolean.valueOf(z2));
            }
        }, strArr);
    }

    private void setAlias(MethodCall methodCall, final MethodChannel.Result result) {
        PushAgent.getInstance(this.mContext).setAlias((String) getParam(methodCall, result, PushConstants.SUB_ALIAS_STATUS_NAME), (String) getParam(methodCall, result, "type"), new UPushAliasCallback() { // from class: com.umeng.message.UmengPushSdkPlugin.7
            @Override // com.umeng.message.api.UPushAliasCallback
            public void onMessage(boolean z2, String str) {
                UPLog.i(UmengPushSdkPlugin.TAG, "onMessage:" + z2 + " s:" + str);
                UmengPushSdkPlugin.this.executeOnMain(result, Boolean.valueOf(z2));
            }
        });
    }

    private void setBadge(int i2, MethodChannel.Result result) {
        PushAgent.getInstance(this.mContext).setBadgeNum(i2);
        executeOnMain(result, Boolean.TRUE);
    }

    public static void setOfflineMsg(final UMessage uMessage) {
        if (uMessage == null) {
            return;
        }
        UmengPushSdkPlugin umengPushSdkPlugin = getInstance();
        if (umengPushSdkPlugin != null) {
            umengPushSdkPlugin.mHandler.post(new Runnable() { // from class: com.umeng.message.UmengPushSdkPlugin.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        MethodChannel methodChannel = UmengPushSdkPlugin.this.mChannel;
                        if (methodChannel != null) {
                            methodChannel.invokeMethod("onNotificationOpen", uMessage.getRaw().toString());
                        }
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
            });
        } else {
            sOfflineMsgCache = uMessage.getRaw().toString();
        }
    }

    private void setPushEnable(final boolean z2, MethodChannel.Result result) {
        UPushSettingCallback uPushSettingCallback = new UPushSettingCallback() { // from class: com.umeng.message.UmengPushSdkPlugin.8
            @Override // com.umeng.message.api.UPushSettingCallback
            public void onFailure(String str, String str2) {
                UPLog.i(UmengPushSdkPlugin.TAG, "setPushEnable failure:" + z2);
            }

            @Override // com.umeng.message.api.UPushSettingCallback
            public void onSuccess() {
                UPLog.i(UmengPushSdkPlugin.TAG, "setPushEnable success:" + z2);
            }
        };
        if (z2) {
            PushAgent.getInstance(this.mContext).enable(uPushSettingCallback);
        } else {
            PushAgent.getInstance(this.mContext).disable(uPushSettingCallback);
        }
        executeOnMain(result, null);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        UPLog.i(TAG, "onAttachedToEngine");
        this.mContext = flutterPluginBinding.getApplicationContext();
        MethodChannel methodChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "u-push");
        this.mChannel = methodChannel;
        methodChannel.setMethodCallHandler(this);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        UPLog.i(TAG, "onDetachedFromEngine");
        this.mChannel = null;
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        try {
            if (pushMethodCall(methodCall, result)) {
                return;
            }
            result.notImplemented();
        } catch (Exception e2) {
            UPLog.e(TAG, "Exception:" + e2.getMessage());
        }
    }
}
