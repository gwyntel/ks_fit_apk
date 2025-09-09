package anetwork.channel.unified;

import anet.channel.RequestCb;
import anet.channel.bytes.ByteArray;
import anet.channel.statist.RequestStatistic;
import anet.channel.util.ALog;
import anet.channel.util.HttpHelper;
import anetwork.channel.aidl.DefaultFinishEvent;
import anetwork.channel.cookie.CookieManager;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
class c implements RequestCb {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ b f7250a;

    c(b bVar) {
        this.f7250a = bVar;
    }

    @Override // anet.channel.RequestCb
    public void onDataReceive(ByteArray byteArray, boolean z2) {
        if (this.f7250a.f7246c.f7289d.get()) {
            return;
        }
        b.b(this.f7250a);
        if (this.f7250a.f7246c.f7287b != null) {
            this.f7250a.f7246c.f7287b.onDataReceiveSize(this.f7250a.f7248e, this.f7250a.f7247d, byteArray);
        }
    }

    @Override // anet.channel.RequestCb
    public void onFinish(int i2, String str, RequestStatistic requestStatistic) {
        if (this.f7250a.f7246c.f7289d.getAndSet(true)) {
            return;
        }
        if (ALog.isPrintLog(2)) {
            ALog.i("anet.DegradeTask", "[onFinish]", this.f7250a.f7246c.f7288c, "code", Integer.valueOf(i2), "msg", str);
        }
        this.f7250a.f7246c.a();
        requestStatistic.isDone.set(true);
        if (this.f7250a.f7246c.f7287b != null) {
            this.f7250a.f7246c.f7287b.onFinish(new DefaultFinishEvent(i2, str, this.f7250a.f7249f));
        }
    }

    @Override // anet.channel.RequestCb
    public void onResponseCode(int i2, Map<String, List<String>> map) {
        if (this.f7250a.f7246c.f7289d.get()) {
            return;
        }
        this.f7250a.f7246c.a();
        CookieManager.setCookie(this.f7250a.f7246c.f7286a.g(), map);
        this.f7250a.f7247d = HttpHelper.parseContentLength(map);
        if (this.f7250a.f7246c.f7287b != null) {
            this.f7250a.f7246c.f7287b.onResponseCode(i2, map);
        }
    }
}
