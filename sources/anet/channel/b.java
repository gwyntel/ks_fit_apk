package anet.channel;

import anet.channel.entity.EventCb;
import anet.channel.util.ALog;
import java.util.Map;

/* loaded from: classes2.dex */
class b implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ int f6688a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ anet.channel.entity.b f6689b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ Session f6690c;

    b(Session session, int i2, anet.channel.entity.b bVar) {
        this.f6690c = session;
        this.f6688a = i2;
        this.f6689b = bVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            Map<EventCb, Integer> map = this.f6690c.f6618b;
            if (map != null) {
                for (EventCb eventCb : map.keySet()) {
                    if (eventCb != null) {
                        int iIntValue = this.f6690c.f6618b.get(eventCb).intValue();
                        int i2 = this.f6688a;
                        if ((iIntValue & i2) != 0) {
                            try {
                                eventCb.onEvent(this.f6690c, i2, this.f6689b);
                            } catch (Exception e2) {
                                ALog.e("awcn.Session", e2.toString(), this.f6690c.f6632p, new Object[0]);
                            }
                        }
                    }
                }
            }
        } catch (Exception e3) {
            ALog.e("awcn.Session", "handleCallbacks", this.f6690c.f6632p, e3, new Object[0]);
        }
    }
}
