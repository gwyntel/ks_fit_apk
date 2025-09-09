package anet.channel;

import androidx.media3.exoplayer.rtsp.RtspHeaders;
import anet.channel.SessionRequest;
import anet.channel.entity.EventCb;
import anet.channel.util.ALog;

/* loaded from: classes2.dex */
class f implements EventCb {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ SessionRequest.IConnCb f6780a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ long f6781b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ SessionRequest f6782c;

    f(SessionRequest sessionRequest, SessionRequest.IConnCb iConnCb, long j2) {
        this.f6782c = sessionRequest;
        this.f6780a = iConnCb;
        this.f6781b = j2;
    }

    @Override // anet.channel.entity.EventCb
    public void onEvent(Session session, int i2, anet.channel.entity.b bVar) {
        if (session == null) {
            return;
        }
        int i3 = bVar == null ? 0 : bVar.f6775b;
        String str = bVar == null ? "" : bVar.f6776c;
        if (i2 == 2) {
            ALog.d("awcn.SessionRequest", null, session.f6632p, RtspHeaders.SESSION, session, "EventType", Integer.valueOf(i2), "Event", bVar);
            this.f6782c.a(session, i3, str);
            SessionRequest sessionRequest = this.f6782c;
            if (sessionRequest.f6656b.c(sessionRequest, session)) {
                this.f6780a.onDisConnect(session, this.f6781b, i2);
                return;
            } else {
                this.f6780a.onFailed(session, this.f6781b, i2, i3);
                return;
            }
        }
        if (i2 == 256) {
            ALog.d("awcn.SessionRequest", null, session.f6632p, RtspHeaders.SESSION, session, "EventType", Integer.valueOf(i2), "Event", bVar);
            this.f6780a.onFailed(session, this.f6781b, i2, i3);
        } else {
            if (i2 != 512) {
                return;
            }
            ALog.d("awcn.SessionRequest", null, session.f6632p, RtspHeaders.SESSION, session, "EventType", Integer.valueOf(i2), "Event", bVar);
            this.f6782c.a(session, 0, (String) null);
            this.f6780a.onSuccess(session, this.f6781b);
        }
    }
}
