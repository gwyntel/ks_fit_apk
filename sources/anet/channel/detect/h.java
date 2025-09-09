package anet.channel.detect;

import anet.channel.Session;
import anet.channel.entity.EventCb;
import anet.channel.request.Request;
import anet.channel.session.TnetSpdySession;
import anet.channel.statist.HorseRaceStat;
import anet.channel.strategy.l;
import anet.channel.util.ALog;
import anet.channel.util.HttpUrl;

/* loaded from: classes2.dex */
class h implements EventCb {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ HorseRaceStat f6730a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ long f6731b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ String f6732c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ l.e f6733d;

    /* renamed from: e, reason: collision with root package name */
    final /* synthetic */ TnetSpdySession f6734e;

    /* renamed from: f, reason: collision with root package name */
    final /* synthetic */ d f6735f;

    h(d dVar, HorseRaceStat horseRaceStat, long j2, String str, l.e eVar, TnetSpdySession tnetSpdySession) {
        this.f6735f = dVar;
        this.f6730a = horseRaceStat;
        this.f6731b = j2;
        this.f6732c = str;
        this.f6733d = eVar;
        this.f6734e = tnetSpdySession;
    }

    @Override // anet.channel.entity.EventCb
    public void onEvent(Session session, int i2, anet.channel.entity.b bVar) {
        if (this.f6730a.connTime != 0) {
            return;
        }
        this.f6730a.connTime = System.currentTimeMillis() - this.f6731b;
        if (i2 != 1) {
            this.f6730a.connErrorCode = bVar.f6775b;
            synchronized (this.f6730a) {
                this.f6730a.notify();
            }
            return;
        }
        ALog.i("anet.HorseRaceDetector", "tnetSpdySession connect success", this.f6732c, new Object[0]);
        this.f6730a.connRet = 1;
        HttpUrl httpUrl = HttpUrl.parse(session.getHost() + this.f6733d.f7053c);
        if (httpUrl == null) {
            return;
        }
        this.f6734e.request(new Request.Builder().setUrl(httpUrl).setReadTimeout(this.f6733d.f7052b.f7024d).setRedirectEnable(false).setSeq(this.f6732c).build(), new i(this));
    }
}
