package org.android.agoo.huawei;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.push.HmsMessaging;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.agoo.BaseNotifyClickActivity;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.concurrent.TimeUnit;
import org.android.agoo.control.NotifManager;

/* loaded from: classes5.dex */
public class HuaWeiRegister {
    public static final String TAG = "HuaWeiRegister";
    public static final String VERSION = "2.0.0";

    public class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ Context f26548a;

        public a(Context context) {
            this.f26548a = context;
        }

        @Override // java.lang.Runnable
        public void run() {
            ALog.i(HuaWeiRegister.TAG, "register begin", new Object[0]);
            try {
                String token = HuaWeiRegister.getToken(this.f26548a);
                if (TextUtils.isEmpty(token)) {
                    return;
                }
                ALog.i(HuaWeiRegister.TAG, "onToken", "token", token);
                NotifManager notifManager = new NotifManager();
                notifManager.init(this.f26548a);
                notifManager.reportThirdPushToken(token, HuaweiRcvService.HUAWEI_TOKEN);
            } catch (Throwable th) {
                ALog.e(HuaWeiRegister.TAG, "getToken failed.", th, new Object[0]);
            }
        }
    }

    public class b implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ Context f26549a;

        public b(Context context) {
            this.f26549a = context;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                String string = this.f26549a.getPackageManager().getApplicationInfo(this.f26549a.getPackageName(), 128).metaData.getString(Constants.HUAWEI_HMS_CLIENT_APPID);
                String strReplace = "";
                if (string != null && string.length() > 0) {
                    strReplace = string.replace("appid=", "");
                }
                ALog.i(HuaWeiRegister.TAG, MiPushClient.COMMAND_UNREGISTER, "appId", strReplace);
                HmsInstanceId.getInstance(this.f26549a).deleteToken(strReplace, HmsMessaging.DEFAULT_TOKEN_SCOPE);
            } catch (Throwable th) {
                ALog.e(HuaWeiRegister.TAG, MiPushClient.COMMAND_UNREGISTER, th, new Object[0]);
            }
        }
    }

    public static boolean checkDevice(Context context) {
        if ("huawei".equalsIgnoreCase(Build.BRAND) || "huawei".equalsIgnoreCase(Build.MANUFACTURER)) {
            return true;
        }
        if (!TextUtils.isEmpty(get(com.alipay.sdk.m.c.a.f9195a))) {
            return true;
        }
        if (!TextUtils.isEmpty(get(com.alipay.sdk.m.c.a.f9196b))) {
            return true;
        }
        try {
            return context.getPackageManager().getPackageInfo("com.huawei.hwid", 0) != null;
        } catch (Throwable unused) {
            return false;
        }
    }

    @SuppressLint({"PrivateApi"})
    public static String get(String str) {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod(TmpConstant.PROPERTY_IDENTIFIER_GET, String.class).invoke(null, str);
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String getToken(Context context) {
        try {
            String string = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getString(Constants.HUAWEI_HMS_CLIENT_APPID);
            String strReplace = "";
            if (string != null && string.length() > 0) {
                strReplace = string.replace("appid=", "");
            }
            ALog.i(TAG, "getToken", "appId", strReplace);
            return TextUtils.isEmpty(strReplace) ? HmsInstanceId.getInstance(context).getToken() : HmsInstanceId.getInstance(context).getToken(strReplace, HmsMessaging.DEFAULT_TOKEN_SCOPE);
        } catch (Throwable th) {
            ALog.e(TAG, "getToken failed.", th, new Object[0]);
            return null;
        }
    }

    public static void register(Context context) {
        try {
            Context applicationContext = context.getApplicationContext();
            if (!UtilityImpl.isMainProcess(applicationContext)) {
                ALog.i(TAG, "not in main process, skipped.", new Object[0]);
            } else {
                if (!checkDevice(applicationContext)) {
                    ALog.i(TAG, "device check, skipped.", new Object[0]);
                    return;
                }
                ALog.i(TAG, "ver:", "2.0.0");
                BaseNotifyClickActivity.addNotifyListener(new HuaweiMsgParseImpl());
                ThreadPoolExecutorFactory.schedule(new a(applicationContext), 5L, TimeUnit.SECONDS);
            }
        } catch (Throwable th) {
            ALog.e(TAG, "register", th, new Object[0]);
        }
    }

    public static void unregister(Context context) {
        try {
            ALog.i(TAG, MiPushClient.COMMAND_UNREGISTER, new Object[0]);
            ThreadPoolExecutorFactory.execute(new b(context.getApplicationContext()));
        } catch (Throwable th) {
            ALog.e(TAG, "unregister failed:", th, new Object[0]);
        }
    }
}
