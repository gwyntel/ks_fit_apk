package com.umeng.message.inapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import androidx.media3.common.C;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.message.entity.UInAppMessage;

/* loaded from: classes4.dex */
public class UmengInAppClickHandler implements UInAppHandler {

    /* renamed from: a, reason: collision with root package name */
    private static final String f22669a = "com.umeng.message.inapp.UmengInAppClickHandler";

    /* renamed from: b, reason: collision with root package name */
    private String f22670b = null;

    /* renamed from: c, reason: collision with root package name */
    private String f22671c = null;

    /* renamed from: d, reason: collision with root package name */
    private String f22672d = null;

    @Override // com.umeng.message.inapp.UInAppHandler
    public final void handleInAppMessage(Activity activity, UInAppMessage uInAppMessage, int i2) {
        switch (i2) {
            case 16:
                this.f22670b = uInAppMessage.action_type;
                this.f22671c = uInAppMessage.action_activity;
                this.f22672d = uInAppMessage.action_url;
                break;
            case 17:
                this.f22670b = uInAppMessage.bottom_action_type;
                this.f22671c = uInAppMessage.bottom_action_activity;
                this.f22672d = uInAppMessage.bottom_action_url;
                break;
            case 18:
                this.f22670b = uInAppMessage.plainTextActionType;
                this.f22671c = uInAppMessage.plainTextActivity;
                this.f22672d = uInAppMessage.plainTextUrl;
                break;
            case 19:
                this.f22670b = uInAppMessage.customButtonActionType;
                this.f22671c = uInAppMessage.customButtonActivity;
                this.f22672d = uInAppMessage.customButtonUrl;
                break;
        }
        if (TextUtils.isEmpty(this.f22670b)) {
            return;
        }
        if (TextUtils.equals("go_activity", this.f22670b)) {
            openActivity(activity, this.f22671c);
        } else if (TextUtils.equals("go_url", this.f22670b)) {
            openUrl(activity, this.f22672d);
        } else {
            TextUtils.equals("go_app", this.f22670b);
        }
    }

    public void openActivity(Activity activity, String str) {
        if (str != null) {
            try {
                if (TextUtils.isEmpty(str.trim())) {
                    return;
                }
                UMLog.mutlInfo(f22669a, 2, "打开Activity: ".concat(str));
                Intent intent = new Intent();
                intent.setClassName(activity, str);
                intent.setFlags(C.BUFFER_FLAG_LAST_SAMPLE);
                activity.startActivity(intent);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void openUrl(Activity activity, String str) {
        if (str != null) {
            try {
                if (TextUtils.isEmpty(str.trim())) {
                    return;
                }
                UMLog.mutlInfo(f22669a, 2, "打开链接: ".concat(str));
                activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
