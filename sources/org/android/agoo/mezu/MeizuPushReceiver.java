package org.android.agoo.mezu;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;
import com.taobao.accs.utl.ALog;
import org.android.agoo.control.AgooFactory;
import org.android.agoo.control.NotifManager;

/* loaded from: classes5.dex */
public class MeizuPushReceiver extends MzPushMessageReceiver {
    public static final String TAG = "MeizuPushReceiver";
    public static final String TOKEN = "MZ_TOKEN";
    public AgooFactory agooFactory;

    public static void reportToken(Context context, String str) {
        if (TextUtils.isEmpty(str) || context == null) {
            return;
        }
        try {
            Context applicationContext = context.getApplicationContext();
            NotifManager notifManager = new NotifManager();
            notifManager.init(applicationContext);
            notifManager.reportThirdPushToken(str, TOKEN);
        } catch (Throwable th) {
            ALog.e(TAG, "onToken", th, new Object[0]);
        }
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onMessage(Context context, Intent intent) {
        ALog.i(TAG, "onMessage", CommonCode.Resolution.HAS_RESOLUTION_FROM_APK, intent == null ? "" : intent.toString());
        if (intent != null) {
            try {
                String stringExtra = intent.getStringExtra("content");
                ALog.i(TAG, "onPushMsg", "flyme4 content", stringExtra);
                if (stringExtra == null || stringExtra.length() <= 0) {
                    return;
                }
                AgooFactory agooFactory = new AgooFactory();
                this.agooFactory = agooFactory;
                agooFactory.init(context, null, null);
                this.agooFactory.msgRecevie(stringExtra.getBytes(), "meizu");
            } catch (Throwable th) {
                ALog.e(TAG, "onPushMsg", th, new Object[0]);
            }
        }
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onPushStatus(Context context, PushSwitchStatus pushSwitchStatus) {
        ALog.i(TAG, "onPushStatus", "status", pushSwitchStatus == null ? "" : pushSwitchStatus.toString());
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onRegister(Context context, String str) {
        ALog.i(TAG, "onRegister", PushConstants.KEY_PUSH_ID, str);
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onRegisterStatus(Context context, RegisterStatus registerStatus) {
        if (registerStatus == null || TextUtils.isEmpty(registerStatus.getPushId())) {
            ALog.e(TAG, "onRegisterStatus", "status", registerStatus == null ? "" : registerStatus.toString());
        } else {
            ALog.i(TAG, "onRegister", "status", registerStatus.toString());
            reportToken(context, registerStatus.getPushId());
        }
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onSubAliasStatus(Context context, SubAliasStatus subAliasStatus) {
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onSubTagsStatus(Context context, SubTagsStatus subTagsStatus) {
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onUnRegister(Context context, boolean z2) {
        ALog.i(TAG, "onUnRegister", "result", Boolean.valueOf(z2));
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onUnRegisterStatus(Context context, UnRegisterStatus unRegisterStatus) {
        ALog.i(TAG, "onUnRegisterStatus", "status", unRegisterStatus == null ? "" : unRegisterStatus.toString());
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onMessage(Context context, String str, String str2) {
        ALog.i(TAG, "onMessage", "msg", str, "platformExtra", str2);
        try {
            ALog.i(TAG, "onPushMsg", "content", str);
            AgooFactory agooFactory = new AgooFactory();
            this.agooFactory = agooFactory;
            agooFactory.init(context, null, null);
            this.agooFactory.msgRecevie(str.getBytes(), "meizu");
        } catch (Throwable th) {
            ALog.e(TAG, "onPushMsg", th, new Object[0]);
        }
    }
}
