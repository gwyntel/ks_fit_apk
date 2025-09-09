package anetwork.channel.unified;

import androidx.media3.exoplayer.rtsp.RtspHeaders;
import anet.channel.Session;
import anet.channel.SessionCenter;
import anet.channel.SessionGetCallback;
import anet.channel.request.Request;
import anet.channel.statist.RequestStatistic;
import anet.channel.util.ALog;
import anet.channel.util.HttpUrl;

/* loaded from: classes2.dex */
class h implements SessionGetCallback {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ RequestStatistic f7276a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ long f7277b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ Request f7278c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ SessionCenter f7279d;

    /* renamed from: e, reason: collision with root package name */
    final /* synthetic */ HttpUrl f7280e;

    /* renamed from: f, reason: collision with root package name */
    final /* synthetic */ boolean f7281f;

    /* renamed from: g, reason: collision with root package name */
    final /* synthetic */ e f7282g;

    h(e eVar, RequestStatistic requestStatistic, long j2, Request request, SessionCenter sessionCenter, HttpUrl httpUrl, boolean z2) {
        this.f7282g = eVar;
        this.f7276a = requestStatistic;
        this.f7277b = j2;
        this.f7278c = request;
        this.f7279d = sessionCenter;
        this.f7280e = httpUrl;
        this.f7281f = z2;
    }

    @Override // anet.channel.SessionGetCallback
    public void onSessionGetFail() {
        ALog.e(e.TAG, "onSessionGetFail", this.f7282g.f7253a.f7288c, "url", this.f7276a.url);
        this.f7276a.connWaitTime = System.currentTimeMillis() - this.f7277b;
        e eVar = this.f7282g;
        eVar.a(eVar.a(null, this.f7279d, this.f7280e, this.f7281f), this.f7278c);
    }

    @Override // anet.channel.SessionGetCallback
    public void onSessionGetSuccess(Session session) {
        ALog.i(e.TAG, "onSessionGetSuccess", this.f7282g.f7253a.f7288c, RtspHeaders.SESSION, session);
        this.f7276a.connWaitTime = System.currentTimeMillis() - this.f7277b;
        this.f7276a.spdyRequestSend = true;
        this.f7282g.a(session, this.f7278c);
    }
}
