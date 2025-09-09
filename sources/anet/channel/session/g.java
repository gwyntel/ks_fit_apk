package anet.channel.session;

import anet.channel.RequestCb;
import anet.channel.bytes.ByteArray;
import anet.channel.statist.RequestStatistic;
import anet.channel.util.ALog;
import anet.channel.util.HttpHelper;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
class g implements RequestCb {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ f f6919a;

    g(f fVar) {
        this.f6919a = fVar;
    }

    @Override // anet.channel.RequestCb
    public void onDataReceive(ByteArray byteArray, boolean z2) {
        this.f6919a.f6916b.onDataReceive(byteArray, z2);
    }

    @Override // anet.channel.RequestCb
    public void onFinish(int i2, String str, RequestStatistic requestStatistic) {
        if (i2 <= 0 && i2 != -204) {
            this.f6919a.f6918d.handleCallbacks(2, new anet.channel.entity.b(2, 0, "Http connect fail"));
        }
        this.f6919a.f6916b.onFinish(i2, str, requestStatistic);
    }

    @Override // anet.channel.RequestCb
    public void onResponseCode(int i2, Map<String, List<String>> map) {
        ALog.i("awcn.HttpSession", "", this.f6919a.f6915a.getSeq(), "httpStatusCode", Integer.valueOf(i2));
        ALog.i("awcn.HttpSession", "", this.f6919a.f6915a.getSeq(), "response headers", map);
        this.f6919a.f6916b.onResponseCode(i2, map);
        this.f6919a.f6917c.serverRT = HttpHelper.parseServerRT(map);
        f fVar = this.f6919a;
        fVar.f6918d.handleResponseCode(fVar.f6915a, i2);
        f fVar2 = this.f6919a;
        fVar2.f6918d.handleResponseHeaders(fVar2.f6915a, map);
    }
}
