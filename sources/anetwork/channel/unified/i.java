package anetwork.channel.unified;

import android.support.v4.media.session.PlaybackStateCompat;
import anet.channel.RequestCb;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.bytes.ByteArray;
import anet.channel.flow.FlowStat;
import anet.channel.flow.NetworkAnalysis;
import anet.channel.request.Request;
import anet.channel.statist.ExceptionStatistic;
import anet.channel.statist.RequestStatistic;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.thread.ThreadPoolExecutorFactory;
import anet.channel.util.ALog;
import anet.channel.util.ErrorConstant;
import anet.channel.util.HttpConstant;
import anet.channel.util.HttpHelper;
import anet.channel.util.HttpUrl;
import anetwork.channel.aidl.DefaultFinishEvent;
import anetwork.channel.cache.Cache;
import anetwork.channel.config.NetworkConfigCenter;
import anetwork.channel.cookie.CookieManager;
import anetwork.channel.interceptor.Callback;
import anetwork.channel.unified.e;
import com.aliyun.alink.linksdk.alcs.coap.resources.LinkFormat;
import com.kingsmith.miot.KsProperty;
import com.xiaomi.infra.galaxy.fds.Common;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
class i implements RequestCb {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Request f7283a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ RequestStatistic f7284b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ e f7285c;

    i(e eVar, Request request, RequestStatistic requestStatistic) {
        this.f7285c = eVar;
        this.f7283a = request;
        this.f7284b = requestStatistic;
    }

    @Override // anet.channel.RequestCb
    public void onDataReceive(ByteArray byteArray, boolean z2) {
        if (this.f7285c.f7260h.get()) {
            return;
        }
        e eVar = this.f7285c;
        if (eVar.f7262j == 0) {
            ALog.i(e.TAG, "[onDataReceive] receive first data chunk!", eVar.f7253a.f7288c, new Object[0]);
        }
        if (z2) {
            ALog.i(e.TAG, "[onDataReceive] receive last data chunk!", this.f7285c.f7253a.f7288c, new Object[0]);
        }
        e eVar2 = this.f7285c;
        int i2 = eVar2.f7262j + 1;
        eVar2.f7262j = i2;
        try {
            e.a aVar = eVar2.f7265m;
            if (aVar != null) {
                aVar.f7268c.add(byteArray);
                if (this.f7284b.recDataSize > PlaybackStateCompat.ACTION_PREPARE_FROM_URI || z2) {
                    e eVar3 = this.f7285c;
                    eVar3.f7262j = eVar3.f7265m.a(eVar3.f7253a.f7287b, eVar3.f7261i);
                    e eVar4 = this.f7285c;
                    eVar4.f7263k = true;
                    eVar4.f7264l = eVar4.f7262j > 1;
                    eVar4.f7265m = null;
                }
            } else {
                eVar2.f7253a.f7287b.onDataReceiveSize(i2, eVar2.f7261i, byteArray);
                this.f7285c.f7264l = true;
            }
            ByteArrayOutputStream byteArrayOutputStream = this.f7285c.f7256d;
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.write(byteArray.getBuffer(), 0, byteArray.getDataLength());
                if (z2) {
                    String strG = this.f7285c.f7253a.f7286a.g();
                    e eVar5 = this.f7285c;
                    eVar5.f7255c.data = eVar5.f7256d.toByteArray();
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    e eVar6 = this.f7285c;
                    eVar6.f7254b.put(strG, eVar6.f7255c);
                    ALog.i(e.TAG, "write cache", this.f7285c.f7253a.f7288c, "cost", Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis), "size", Integer.valueOf(this.f7285c.f7255c.data.length), KsProperty.Key, strG);
                }
            }
        } catch (Exception e2) {
            ALog.w(e.TAG, "[onDataReceive] error.", this.f7285c.f7253a.f7288c, e2, new Object[0]);
        }
    }

    @Override // anet.channel.RequestCb
    public void onFinish(int i2, String str, RequestStatistic requestStatistic) {
        String strValueOf;
        DefaultFinishEvent defaultFinishEvent;
        int i3 = 3;
        if (this.f7285c.f7260h.getAndSet(true)) {
            return;
        }
        if (ALog.isPrintLog(2)) {
            ALog.i(e.TAG, "[onFinish]", this.f7285c.f7253a.f7288c, "code", Integer.valueOf(i2), "msg", str);
        }
        if (i2 < 0) {
            try {
                if (this.f7285c.f7253a.f7286a.d()) {
                    e eVar = this.f7285c;
                    if (!eVar.f7263k && !eVar.f7264l) {
                        ALog.e(e.TAG, "clear response buffer and retry", eVar.f7253a.f7288c, new Object[0]);
                        e.a aVar = this.f7285c.f7265m;
                        if (aVar != null) {
                            if (!aVar.f7268c.isEmpty()) {
                                i3 = 4;
                            }
                            requestStatistic.roaming = i3;
                            this.f7285c.f7265m.a();
                            this.f7285c.f7265m = null;
                        }
                        if (this.f7285c.f7253a.f7286a.f7229a == 0) {
                            requestStatistic.firstProtocol = requestStatistic.protocolType;
                            requestStatistic.firstErrorCode = requestStatistic.tnetErrorCode != 0 ? requestStatistic.tnetErrorCode : i2;
                        }
                        this.f7285c.f7253a.f7286a.k();
                        this.f7285c.f7253a.f7289d = new AtomicBoolean();
                        e eVar2 = this.f7285c;
                        j jVar = eVar2.f7253a;
                        jVar.f7290e = new e(jVar, eVar2.f7254b, eVar2.f7255c);
                        if (requestStatistic.tnetErrorCode != 0) {
                            strValueOf = i2 + "|" + requestStatistic.protocolType + "|" + requestStatistic.tnetErrorCode;
                            requestStatistic.tnetErrorCode = 0;
                        } else {
                            strValueOf = String.valueOf(i2);
                        }
                        requestStatistic.appendErrorTrace(strValueOf);
                        long jCurrentTimeMillis = System.currentTimeMillis();
                        requestStatistic.retryCostTime += jCurrentTimeMillis - requestStatistic.start;
                        requestStatistic.start = jCurrentTimeMillis;
                        ThreadPoolExecutorFactory.submitPriorityTask(this.f7285c.f7253a.f7290e, ThreadPoolExecutorFactory.Priority.HIGH);
                        return;
                    }
                    requestStatistic.msg += ":回调后触发重试";
                    e eVar3 = this.f7285c;
                    if (eVar3.f7264l) {
                        requestStatistic.roaming = 2;
                    } else if (eVar3.f7263k) {
                        requestStatistic.roaming = 1;
                    }
                    ALog.e(e.TAG, "Cannot retry request after onHeader/onDataReceived callback!", eVar3.f7253a.f7288c, new Object[0]);
                }
            } catch (Exception unused) {
                return;
            }
        }
        e eVar4 = this.f7285c;
        e.a aVar2 = eVar4.f7265m;
        if (aVar2 != null) {
            aVar2.a(eVar4.f7253a.f7287b, eVar4.f7261i);
        }
        this.f7285c.f7253a.a();
        requestStatistic.isDone.set(true);
        if (this.f7285c.f7253a.f7286a.j() && requestStatistic.contentLength != 0 && requestStatistic.contentLength != requestStatistic.rspBodyDeflateSize) {
            requestStatistic.ret = 0;
            i2 = -206;
            requestStatistic.statusCode = -206;
            str = ErrorConstant.getErrMsg(-206);
            requestStatistic.msg = str;
            e eVar5 = this.f7285c;
            ALog.e(e.TAG, "received data length not match with content-length", eVar5.f7253a.f7288c, Common.CONTENT_LENGTH, Integer.valueOf(eVar5.f7261i), "recDataLength", Long.valueOf(requestStatistic.rspBodyDeflateSize));
            ExceptionStatistic exceptionStatistic = new ExceptionStatistic(-206, str, LinkFormat.RESOURCE_TYPE);
            exceptionStatistic.url = this.f7285c.f7253a.f7286a.g();
            AppMonitor.getInstance().commitStat(exceptionStatistic);
        }
        if (i2 != 304 || this.f7285c.f7255c == null) {
            defaultFinishEvent = new DefaultFinishEvent(i2, str, this.f7283a);
        } else {
            requestStatistic.protocolType = "cache";
            defaultFinishEvent = new DefaultFinishEvent(200, str, this.f7283a);
        }
        this.f7285c.f7253a.f7287b.onFinish(defaultFinishEvent);
        if (i2 >= 0) {
            anet.channel.monitor.b.a().a(requestStatistic.sendStart, requestStatistic.rspEnd, requestStatistic.rspHeadDeflateSize + requestStatistic.rspBodyDeflateSize);
        } else {
            requestStatistic.netType = NetworkStatusHelper.getNetworkSubType();
        }
        NetworkAnalysis.getInstance().commitFlow(new FlowStat(this.f7285c.f7257e, requestStatistic));
    }

    @Override // anet.channel.RequestCb
    public void onResponseCode(int i2, Map<String, List<String>> map) {
        String singleHeaderFieldByKey;
        if (this.f7285c.f7260h.get()) {
            return;
        }
        if (ALog.isPrintLog(2)) {
            ALog.i(e.TAG, "onResponseCode", this.f7283a.getSeq(), "code", Integer.valueOf(i2));
            ALog.i(e.TAG, "onResponseCode", this.f7283a.getSeq(), "headers", map);
        }
        if (HttpHelper.checkRedirect(this.f7283a, i2) && (singleHeaderFieldByKey = HttpHelper.getSingleHeaderFieldByKey(map, "Location")) != null) {
            HttpUrl httpUrl = HttpUrl.parse(singleHeaderFieldByKey);
            if (httpUrl != null) {
                if (this.f7285c.f7260h.compareAndSet(false, true)) {
                    httpUrl.lockScheme();
                    this.f7285c.f7253a.f7286a.a(httpUrl);
                    this.f7285c.f7253a.f7289d = new AtomicBoolean();
                    j jVar = this.f7285c.f7253a;
                    jVar.f7290e = new e(jVar, null, null);
                    this.f7284b.recordRedirect(i2, httpUrl.simpleUrlString());
                    this.f7284b.locationUrl = singleHeaderFieldByKey;
                    ThreadPoolExecutorFactory.submitPriorityTask(this.f7285c.f7253a.f7290e, ThreadPoolExecutorFactory.Priority.HIGH);
                    return;
                }
                return;
            }
            ALog.e(e.TAG, "redirect url is invalid!", this.f7283a.getSeq(), "redirect url", singleHeaderFieldByKey);
        }
        try {
            this.f7285c.f7253a.a();
            CookieManager.setCookie(this.f7285c.f7253a.f7286a.g(), map);
            this.f7285c.f7261i = HttpHelper.parseContentLength(map);
            String strG = this.f7285c.f7253a.f7286a.g();
            e eVar = this.f7285c;
            Cache.Entry entry = eVar.f7255c;
            if (entry != null && i2 == 304) {
                entry.responseHeaders.putAll(map);
                Cache.Entry entryA = anetwork.channel.cache.a.a(map);
                if (entryA != null) {
                    long j2 = entryA.ttl;
                    Cache.Entry entry2 = this.f7285c.f7255c;
                    if (j2 > entry2.ttl) {
                        entry2.ttl = j2;
                    }
                }
                e eVar2 = this.f7285c;
                eVar2.f7253a.f7287b.onResponseCode(200, eVar2.f7255c.responseHeaders);
                e eVar3 = this.f7285c;
                Callback callback = eVar3.f7253a.f7287b;
                byte[] bArr = eVar3.f7255c.data;
                callback.onDataReceiveSize(1, bArr.length, ByteArray.wrap(bArr));
                long jCurrentTimeMillis = System.currentTimeMillis();
                e eVar4 = this.f7285c;
                eVar4.f7254b.put(strG, eVar4.f7255c);
                ALog.i(e.TAG, "update cache", this.f7285c.f7253a.f7288c, "cost", Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis), KsProperty.Key, strG);
                return;
            }
            if (eVar.f7254b != null) {
                if ("no-store".equals(HttpHelper.getSingleHeaderFieldByKey(map, "Cache-Control"))) {
                    this.f7285c.f7254b.remove(strG);
                } else {
                    e eVar5 = this.f7285c;
                    Cache.Entry entryA2 = anetwork.channel.cache.a.a(map);
                    eVar5.f7255c = entryA2;
                    if (entryA2 != null) {
                        HttpHelper.removeHeaderFiledByKey(map, "Cache-Control");
                        map.put("Cache-Control", Arrays.asList("no-store"));
                        e eVar6 = this.f7285c;
                        int i3 = this.f7285c.f7261i;
                        if (i3 == 0) {
                            i3 = 5120;
                        }
                        eVar6.f7256d = new ByteArrayOutputStream(i3);
                    }
                }
            }
            map.put(HttpConstant.X_PROTOCOL, Arrays.asList(this.f7284b.protocolType));
            if (!"open".equalsIgnoreCase(HttpHelper.getSingleHeaderFieldByKey(map, HttpConstant.STREAMING_PARSER)) && NetworkConfigCenter.isResponseBufferEnable()) {
                e eVar7 = this.f7285c;
                if (eVar7.f7261i <= 131072) {
                    eVar7.f7265m = new e.a(i2, map);
                    return;
                }
            }
            this.f7285c.f7253a.f7287b.onResponseCode(i2, map);
            this.f7285c.f7263k = true;
        } catch (Exception e2) {
            ALog.w(e.TAG, "[onResponseCode] error.", this.f7285c.f7253a.f7288c, e2, new Object[0]);
        }
    }
}
