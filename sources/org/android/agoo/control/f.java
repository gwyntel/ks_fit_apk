package org.android.agoo.control;

import com.taobao.accs.utl.ALog;
import org.android.agoo.control.AgooFactory;

/* loaded from: classes5.dex */
class f implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ AgooFactory.a f26533a;

    f(AgooFactory.a aVar) {
        this.f26533a = aVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            ALog.d("AgooFactory", "onConnected running tid:" + Thread.currentThread().getId(), new Object[0]);
            this.f26533a.f26514c.doSend(this.f26533a.f26512a);
            ALog.d("AgooFactory", "send finish. close this connection", new Object[0]);
            this.f26533a.f26514c = null;
            try {
                AgooFactory.mContext.unbindService(this.f26533a.f26515d);
            } catch (Throwable th) {
                ALog.e("AgooFactory", "unbindService error", th, new Object[0]);
            }
        } catch (Throwable th2) {
            try {
                ALog.e("AgooFactory", "send error", th2, new Object[0]);
                ALog.d("AgooFactory", "send finish. close this connection", new Object[0]);
                this.f26533a.f26514c = null;
                try {
                    AgooFactory.mContext.unbindService(this.f26533a.f26515d);
                } catch (Throwable th3) {
                    ALog.e("AgooFactory", "unbindService error", th3, new Object[0]);
                }
            } catch (Throwable th4) {
                ALog.d("AgooFactory", "send finish. close this connection", new Object[0]);
                this.f26533a.f26514c = null;
                try {
                    AgooFactory.mContext.unbindService(this.f26533a.f26515d);
                } catch (Throwable th5) {
                    ALog.e("AgooFactory", "unbindService error", th5, new Object[0]);
                }
                throw th4;
            }
        }
    }
}
