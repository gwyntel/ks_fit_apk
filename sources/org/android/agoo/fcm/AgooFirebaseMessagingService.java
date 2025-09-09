package org.android.agoo.fcm;

import android.content.Context;
import com.facebook.share.internal.MessengerShareContentUtility;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.utl.ALog;
import org.android.agoo.control.AgooFactory;
import org.android.agoo.control.NotifManager;

/* loaded from: classes5.dex */
public class AgooFirebaseMessagingService extends FirebaseMessagingService {
    public AgooFactory agooFactory;

    @Override // com.google.firebase.messaging.FirebaseMessagingService
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {
            ALog.i("FCM", "onMessageReceived id:", remoteMessage.getMessageId());
            String str = remoteMessage.getData().get(MessengerShareContentUtility.ATTACHMENT_PAYLOAD);
            if (str == null || str.length() <= 0) {
                return;
            }
            if (this.agooFactory == null) {
                AgooFactory agooFactory = new AgooFactory();
                this.agooFactory = agooFactory;
                agooFactory.init(getApplicationContext(), null, null);
            }
            this.agooFactory.msgRecevie(str.getBytes(), "gcm");
        } catch (Throwable th) {
            ALog.e("FCM", "onMessageReceived failed! msg:", th.getMessage());
        }
    }

    @Override // com.google.firebase.messaging.FirebaseMessagingService
    public void onNewToken(String str) {
        try {
            ALog.i("FCM", "onNewToken: ", str);
            if (AccsClientConfig.getConfigByTag(AccsClientConfig.DEFAULT_CONFIGTAG) != null) {
                Context applicationContext = getApplicationContext();
                NotifManager notifManager = new NotifManager();
                notifManager.init(applicationContext);
                notifManager.reportThirdPushToken(str, "gcm");
            }
        } catch (Throwable th) {
            ALog.e("FCM", "onNewToken failed! msg:", th.getMessage());
        }
    }
}
