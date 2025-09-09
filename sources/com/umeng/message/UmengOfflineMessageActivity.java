package com.umeng.message;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.umeng.message.common.UPLog;
import com.umeng.message.entity.UMessage;
import java.util.Map;

/* loaded from: classes4.dex */
public class UmengOfflineMessageActivity extends Activity {
    private static final String TAG = "UPush.OfflineMessage";
    private final UmengNotifyClick mNotificationClick = new UmengNotifyClick() { // from class: com.umeng.message.UmengOfflineMessageActivity.1
        @Override // com.umeng.message.UmengNotifyClick
        public void onMessage(UMessage uMessage) {
            UPLog.d(UmengOfflineMessageActivity.TAG, "offline msg: " + uMessage.getRaw().toString());
            UmengPushSdkPlugin.setOfflineMsg(uMessage);
            UmengOfflineMessageActivity.this.launchApp(uMessage);
            UmengOfflineMessageActivity.this.finish();
        }
    };

    private void addMessageToIntent(Intent intent, UMessage uMessage) {
        if (intent == null || uMessage == null || uMessage.getExtra() == null) {
            return;
        }
        for (Map.Entry<String, String> entry : uMessage.getExtra().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key != null) {
                intent.putExtra(key, value);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void launchApp(UMessage uMessage) {
        try {
            String packageName = getPackageName();
            Intent launchIntentForPackage = getPackageManager().getLaunchIntentForPackage(packageName);
            if (launchIntentForPackage == null) {
                UPLog.e(TAG, "can't find launch activity:" + packageName);
            } else {
                launchIntentForPackage.addFlags(805306368);
                addMessageToIntent(launchIntentForPackage, uMessage);
                startActivity(launchIntentForPackage);
                UPLog.d(TAG, "start app: " + packageName);
            }
        } catch (Throwable th) {
            UPLog.e(TAG, "start app fail:", th.getMessage());
        }
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mNotificationClick.onCreate(this, getIntent());
    }

    @Override // android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.mNotificationClick.onNewIntent(intent);
    }
}
