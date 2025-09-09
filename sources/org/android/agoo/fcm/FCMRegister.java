package org.android.agoo.fcm;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UtilityImpl;
import com.xiaomi.mipush.sdk.MiPushClient;
import org.android.agoo.control.NotifManager;

/* loaded from: classes5.dex */
public class FCMRegister {
    public static final String TAG = "FCM";
    public static final String VERSION = "2.1.0";

    public static void getToken(OnCompleteListener<String> onCompleteListener) {
        try {
            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(onCompleteListener);
        } catch (Throwable th) {
            ALog.e("FCM", "getToken failed! msg:", th, new Object[0]);
        }
    }

    public static void register(Context context) {
        try {
            final Context applicationContext = context.getApplicationContext();
            if (!UtilityImpl.isMainProcess(applicationContext)) {
                ALog.i("FCM", "not in main process, skipped.", new Object[0]);
                return;
            }
            int iIsGooglePlayServicesAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(applicationContext);
            if (iIsGooglePlayServicesAvailable != 0 && 18 != iIsGooglePlayServicesAvailable) {
                ALog.i("FCM", "device check, skipped. code:", Integer.valueOf(iIsGooglePlayServicesAvailable));
            } else {
                ALog.i("FCM", "ver:", VERSION);
                FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() { // from class: org.android.agoo.fcm.FCMRegister.1
                    @Override // com.google.android.gms.tasks.OnCompleteListener
                    public void onComplete(Task<String> task) {
                        try {
                            if (!task.isSuccessful()) {
                                ALog.i("FCM", "fcm getToken() failed:", task.getException());
                                return;
                            }
                            String result = task.getResult();
                            ALog.i("FCM", "token:", result);
                            if (TextUtils.isEmpty(result)) {
                                return;
                            }
                            NotifManager notifManager = new NotifManager();
                            notifManager.init(applicationContext);
                            notifManager.reportThirdPushToken(result, "gcm");
                        } catch (Throwable th) {
                            ALog.e("FCM", "reportThirdPushToken failed! msg:", th, new Object[0]);
                        }
                    }
                });
            }
        } catch (Throwable th) {
            ALog.e("FCM", "register failed! msg:", th, new Object[0]);
        }
    }

    public static void unregister() {
        try {
            ALog.i("FCM", MiPushClient.COMMAND_UNREGISTER, new Object[0]);
            FirebaseMessaging.getInstance().deleteToken();
        } catch (Throwable th) {
            ALog.e("FCM", "unregister failed! msg:", th, new Object[0]);
        }
    }
}
