package anet.channel;

import anet.channel.util.HttpConstant;
import anetwork.channel.cache.CachePrediction;
import java.util.Map;

/* loaded from: classes2.dex */
class k implements CachePrediction {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ j f6809a;

    k(j jVar) {
        this.f6809a = jVar;
    }

    @Override // anetwork.channel.cache.CachePrediction
    public boolean handleCache(String str, Map<String, String> map) {
        return "weex".equals(map.get(HttpConstant.F_REFER));
    }
}
