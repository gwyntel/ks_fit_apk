package anetwork.channel.unified;

import androidx.media3.exoplayer.rtsp.SessionDescription;
import anet.channel.bytes.ByteArray;
import anet.channel.request.Request;
import anet.channel.statist.RequestStatistic;
import anet.channel.util.ALog;
import anetwork.channel.aidl.DefaultFinishEvent;
import anetwork.channel.cache.Cache;
import anetwork.channel.interceptor.Callback;
import com.kingsmith.miot.KsProperty;

/* loaded from: classes2.dex */
public class a implements IUnifiedTask {

    /* renamed from: a, reason: collision with root package name */
    private j f7241a;

    /* renamed from: b, reason: collision with root package name */
    private Cache f7242b;

    /* renamed from: c, reason: collision with root package name */
    private volatile boolean f7243c = false;

    public a(j jVar, Cache cache) {
        this.f7241a = null;
        this.f7242b = null;
        this.f7241a = jVar;
        this.f7242b = cache;
    }

    @Override // anet.channel.request.Cancelable
    public void cancel() {
        this.f7243c = true;
        this.f7241a.f7286a.f7230b.ret = 2;
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean zEquals;
        Cache.Entry entry;
        if (this.f7243c) {
            return;
        }
        anetwork.channel.entity.g gVar = this.f7241a.f7286a;
        RequestStatistic requestStatistic = gVar.f7230b;
        if (this.f7242b != null) {
            String strG = gVar.g();
            Request requestA = this.f7241a.f7286a.a();
            String str = requestA.getHeaders().get("Cache-Control");
            boolean zEquals2 = "no-store".equals(str);
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (zEquals2) {
                this.f7242b.remove(strG);
                zEquals = false;
                entry = null;
            } else {
                zEquals = "no-cache".equals(str);
                entry = this.f7242b.get(strG);
                if (ALog.isPrintLog(2)) {
                    ALog.i("anet.CacheTask", "read cache", this.f7241a.f7288c, "hit", Boolean.valueOf(entry != null), "cost", Long.valueOf(requestStatistic.cacheTime), SessionDescription.ATTR_LENGTH, Integer.valueOf(entry != null ? entry.data.length : 0), KsProperty.Key, strG);
                }
            }
            long jCurrentTimeMillis2 = System.currentTimeMillis();
            requestStatistic.cacheTime = jCurrentTimeMillis2 - jCurrentTimeMillis;
            if (entry == null || zEquals || !entry.isFresh()) {
                if (this.f7243c) {
                    return;
                }
                e eVar = new e(this.f7241a, zEquals2 ? null : this.f7242b, entry);
                this.f7241a.f7290e = eVar;
                eVar.run();
                return;
            }
            if (this.f7241a.f7289d.compareAndSet(false, true)) {
                this.f7241a.a();
                requestStatistic.ret = 1;
                requestStatistic.statusCode = 200;
                requestStatistic.msg = "SUCCESS";
                requestStatistic.protocolType = "cache";
                requestStatistic.rspEnd = jCurrentTimeMillis2;
                requestStatistic.processTime = jCurrentTimeMillis2 - requestStatistic.start;
                if (ALog.isPrintLog(2)) {
                    j jVar = this.f7241a;
                    ALog.i("anet.CacheTask", "hit fresh cache", jVar.f7288c, "URL", jVar.f7286a.f().urlString());
                }
                this.f7241a.f7287b.onResponseCode(200, entry.responseHeaders);
                Callback callback = this.f7241a.f7287b;
                byte[] bArr = entry.data;
                callback.onDataReceiveSize(1, bArr.length, ByteArray.wrap(bArr));
                this.f7241a.f7287b.onFinish(new DefaultFinishEvent(200, "SUCCESS", requestA));
            }
        }
    }
}
