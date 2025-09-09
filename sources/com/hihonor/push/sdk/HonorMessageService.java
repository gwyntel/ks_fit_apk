package com.hihonor.push.sdk;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.text.TextUtils;
import android.util.Log;
import com.hihonor.push.sdk.common.data.DownMsgType;

/* loaded from: classes3.dex */
public abstract class HonorMessageService extends Service {

    /* renamed from: c, reason: collision with root package name */
    public static final /* synthetic */ int f15451c = 0;

    /* renamed from: a, reason: collision with root package name */
    public final a f15452a;

    /* renamed from: b, reason: collision with root package name */
    public final Messenger f15453b;

    public class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Log.i("HonorMessageService", "handle message for broadcast.");
            Bundle data = message.getData();
            if (data != null) {
                Intent intent = new Intent();
                intent.putExtras(data);
                HonorMessageService honorMessageService = HonorMessageService.this;
                int i2 = HonorMessageService.f15451c;
                honorMessageService.a(intent);
            }
        }
    }

    public HonorMessageService() {
        a aVar = new a(Looper.getMainLooper());
        this.f15452a = aVar;
        this.f15453b = new Messenger(aVar);
    }

    public final void a(Intent intent) {
        try {
            if (TextUtils.equals(intent.getStringExtra("event_type"), DownMsgType.RECEIVE_TOKEN)) {
                String stringExtra = intent.getStringExtra("push_token");
                Context contextA = l.f15511e.a();
                d dVar = d.f15472b;
                if (!TextUtils.equals(stringExtra, dVar.b(contextA))) {
                    dVar.a(contextA, stringExtra);
                }
                Log.i("HonorMessageService", "onNewToken");
                onNewToken(stringExtra);
            } else {
                Log.i("HonorMessageService", "parse remote data start.");
                a1 a1VarA = b.a(new q0(intent));
                com.hihonor.push.sdk.a aVar = new com.hihonor.push.sdk.a(this);
                a1VarA.getClass();
                a1VarA.a(new t0(o0.f15524c.f15525a, aVar));
            }
            Log.i("HonorMessageService", "dispatch message end.");
        } catch (Exception e2) {
            e2.getMessage();
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return this.f15453b.getBinder();
    }

    public void onMessageReceived(HonorPushDataMsg honorPushDataMsg) {
    }

    public void onNewToken(String str) {
    }

    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i2, int i3) {
        super.onStartCommand(intent, i2, i3);
        Log.i("HonorMessageService", "handle message for service.");
        a(intent);
        return 2;
    }
}
