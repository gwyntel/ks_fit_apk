package org.android.agoo.oppo;

import android.content.Context;
import android.text.TextUtils;
import com.heytap.mcssdk.utils.LogUtil;
import com.heytap.msp.push.HeytapPushManager;
import com.heytap.msp.push.callback.ICallBackResultService;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.agoo.BaseNotifyClickActivity;
import com.xiaomi.mipush.sdk.MiPushClient;
import org.android.agoo.control.NotifManager;

/* loaded from: classes5.dex */
public class OppoRegister {
    public static final String OPPO_TOKEN = "OPPO_TOKEN";
    public static final String TAG = "OppoPush";
    public static final String VERSION = "2.0.0";

    public static String getToken(Context context) {
        try {
            return HeytapPushManager.getRegisterID();
        } catch (Throwable th) {
            ALog.e(TAG, "getToken failed:", th, new Object[0]);
            return "";
        }
    }

    public static void register(Context context, String str, String str2) {
        try {
            final Context applicationContext = context.getApplicationContext();
            if (!UtilityImpl.isMainProcess(applicationContext)) {
                ALog.i(TAG, "not in main process, skipped.", new Object[0]);
                return;
            }
            boolean zIsPrintLog = ALog.isPrintLog();
            LogUtil.setDebugs(zIsPrintLog);
            if (!HeytapPushManager.isSupportPush(context)) {
                ALog.i(TAG, "device check, skipped.", new Object[0]);
                return;
            }
            ALog.i(TAG, "ver:", "2.0.0");
            HeytapPushManager.init(applicationContext, zIsPrintLog);
            BaseNotifyClickActivity.addNotifyListener(new OppoMsgParseImpl());
            ALog.i(TAG, "register oppo begin ", new Object[0]);
            HeytapPushManager.register(applicationContext, str, str2, new ICallBackResultService() { // from class: org.android.agoo.oppo.OppoRegister.1
                @Override // com.heytap.msp.push.callback.ICallBackResultService
                public void onError(int i2, String str3) {
                    ALog.i(OppoRegister.TAG, "onError code:", Integer.valueOf(i2), "msg:", str3);
                }

                @Override // com.heytap.msp.push.callback.ICallBackResultService
                public void onGetNotificationStatus(int i2, int i3) {
                    ALog.i(OppoRegister.TAG, "onGetNotificationStatus", new Object[0]);
                }

                @Override // com.heytap.msp.push.callback.ICallBackResultService
                public void onGetPushStatus(int i2, int i3) {
                    ALog.i(OppoRegister.TAG, "onGetPushStatus", new Object[0]);
                }

                @Override // com.heytap.msp.push.callback.ICallBackResultService
                public void onRegister(int i2, String str3) {
                    ALog.i(OppoRegister.TAG, "onRegister regid:", str3, "code:", Integer.valueOf(i2));
                    if (TextUtils.isEmpty(str3)) {
                        return;
                    }
                    NotifManager notifManager = new NotifManager();
                    notifManager.init(applicationContext);
                    notifManager.reportThirdPushToken(str3, OppoRegister.OPPO_TOKEN);
                }

                @Override // com.heytap.msp.push.callback.ICallBackResultService
                public void onSetPushTime(int i2, String str3) {
                    ALog.i(OppoRegister.TAG, "onSetPushTime", new Object[0]);
                }

                @Override // com.heytap.msp.push.callback.ICallBackResultService
                public void onUnRegister(int i2) {
                    ALog.e(OppoRegister.TAG, "onUnRegister code:", Integer.valueOf(i2));
                }
            });
        } catch (Throwable th) {
            ALog.e(TAG, "register failed:", th, new Object[0]);
        }
    }

    public static void unregister() {
        try {
            ALog.i(TAG, MiPushClient.COMMAND_UNREGISTER, new Object[0]);
            HeytapPushManager.unRegister();
        } catch (Throwable th) {
            ALog.e(TAG, "unregister failed:", th, new Object[0]);
        }
    }
}
