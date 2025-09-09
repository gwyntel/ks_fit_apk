package org.android.agoo.mezu;

import android.content.Context;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.PushManager;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.agoo.BaseNotifyClickActivity;
import com.xiaomi.mipush.sdk.MiPushClient;

/* loaded from: classes5.dex */
public class MeizuRegister {
    public static final String TAG = "MeizuPush";
    public static final String VERSION = "2.0.0";

    public static String getToken(Context context) {
        try {
            return PushManager.getPushId(context);
        } catch (Throwable th) {
            ALog.e(TAG, "getToken", th, new Object[0]);
            return null;
        }
    }

    public static void register(Context context, String str, String str2) {
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            ALog.i(TAG, "register args empty, skipped.", new Object[0]);
            return;
        }
        try {
            Context applicationContext = context.getApplicationContext();
            if (!UtilityImpl.isMainProcess(applicationContext)) {
                ALog.i(TAG, "not in main process, skipped.", new Object[0]);
            } else {
                if (!MzSystemUtils.isMeizu()) {
                    ALog.i(TAG, "device check, skipped.", new Object[0]);
                    return;
                }
                ALog.i(TAG, "ver:", "2.0.0");
                BaseNotifyClickActivity.addNotifyListener(new MeizuMsgParseImpl());
                PushManager.register(applicationContext, str, str2);
            }
        } catch (Throwable th) {
            ALog.e(TAG, "register", th, new Object[0]);
        }
    }

    public static void unregister(Context context, String str, String str2) {
        try {
            ALog.i(TAG, MiPushClient.COMMAND_UNREGISTER, new Object[0]);
            PushManager.unRegister(context, str, str2);
        } catch (Throwable th) {
            ALog.e(TAG, MiPushClient.COMMAND_UNREGISTER, th, new Object[0]);
        }
    }
}
