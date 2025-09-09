package com.hihonor.push.sdk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.hihonor.push.framework.aidl.entity.PushTokenResult;
import com.hihonor.push.sdk.common.data.DownMsgType;
import com.hihonor.push.sdk.common.data.UpMsgType;
import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
public class m implements Callable<String> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ boolean f15519a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ s f15520b;

    public m(s sVar, boolean z2) {
        this.f15520b = sVar;
        this.f15519a = z2;
    }

    @Override // java.util.concurrent.Callable
    public String call() throws Exception {
        this.f15520b.f15537b.getClass();
        try {
            e1 e1Var = new e1(UpMsgType.REQUEST_PUSH_TOKEN, null);
            e1Var.f15492e = b.a();
            String pushToken = ((PushTokenResult) b.a(z.f15569c.a(e1Var))).getPushToken();
            if (this.f15519a) {
                s sVar = this.f15520b;
                sVar.getClass();
                if (!TextUtils.isEmpty(pushToken)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("event_type", DownMsgType.RECEIVE_TOKEN);
                    bundle.putString("push_token", pushToken);
                    g0 g0Var = new g0();
                    Context context = sVar.f15536a;
                    Log.i("MessengerSrvConnection", "start bind service.");
                    try {
                        Intent intent = new Intent();
                        intent.setPackage(context.getPackageName());
                        intent.setAction("com.hihonor.push.action.MESSAGING_EVENT");
                        Context applicationContext = context.getApplicationContext();
                        g0Var.f15497c = applicationContext;
                        g0Var.f15496b = bundle;
                        if (applicationContext.bindService(intent, g0Var, 1)) {
                            Log.i("MessengerSrvConnection", "bind service succeeded.");
                        }
                    } catch (Exception e2) {
                        e2.getMessage();
                    }
                }
            }
            return pushToken;
        } catch (Exception e3) {
            throw b.a(e3);
        }
    }
}
