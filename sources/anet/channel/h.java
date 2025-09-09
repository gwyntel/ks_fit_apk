package anet.channel;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import anet.channel.util.ALog;
import com.huawei.hms.support.api.entity.core.CommonCode;

/* loaded from: classes2.dex */
class h implements ServiceConnection {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Intent f6795a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ Context f6796b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ SessionRequest f6797c;

    h(SessionRequest sessionRequest, Intent intent, Context context) {
        this.f6797c = sessionRequest;
        this.f6795a = intent;
        this.f6796b = context;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        ALog.d("awcn.SessionRequest", "onServiceConnected", null, new Object[0]);
        try {
            try {
                Messenger messenger = new Messenger(iBinder);
                Message message = new Message();
                message.getData().putParcelable(CommonCode.Resolution.HAS_RESOLUTION_FROM_APK, this.f6795a);
                messenger.send(message);
            } catch (Exception e2) {
                ALog.e("awcn.SessionRequest", "onServiceConnected sendMessage error.", null, e2, new Object[0]);
            }
        } finally {
            this.f6796b.unbindService(this);
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        ALog.d("awcn.SessionRequest", "onServiceDisconnected", null, new Object[0]);
        this.f6796b.unbindService(this);
    }
}
