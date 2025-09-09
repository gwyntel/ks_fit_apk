package anetwork.channel.entity;

import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.detect.n;
import anet.channel.statist.RequestMonitor;
import anet.channel.statist.RequestStatistic;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.util.ALog;
import anetwork.channel.aidl.DefaultFinishEvent;
import anetwork.channel.aidl.ParcelableNetworkListener;
import anetwork.channel.config.NetworkConfigCenter;
import anetwork.channel.stat.NetworkStat;
import anetwork.channel.util.RequestConstant;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONObject;

/* loaded from: classes2.dex */
class f implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ DefaultFinishEvent f7226a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ ParcelableNetworkListener f7227b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ c f7228c;

    f(c cVar, DefaultFinishEvent defaultFinishEvent, ParcelableNetworkListener parcelableNetworkListener) {
        this.f7228c = cVar;
        this.f7226a = defaultFinishEvent;
        this.f7227b = parcelableNetworkListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        DefaultFinishEvent defaultFinishEvent = this.f7226a;
        String strOptString = null;
        if (defaultFinishEvent != null) {
            defaultFinishEvent.setContext(null);
        }
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            RequestStatistic requestStatistic = this.f7226a.rs;
            if (requestStatistic != null) {
                requestStatistic.rspCbStart = jCurrentTimeMillis;
                requestStatistic.lastProcessTime = jCurrentTimeMillis - requestStatistic.rspEnd;
                requestStatistic.oneWayTime = requestStatistic.retryCostTime + (jCurrentTimeMillis - requestStatistic.start);
                this.f7226a.getStatisticData().filledBy(requestStatistic);
            }
            this.f7227b.onFinished(this.f7226a);
            if (requestStatistic != null) {
                long jCurrentTimeMillis2 = System.currentTimeMillis();
                requestStatistic.rspCbEnd = jCurrentTimeMillis2;
                requestStatistic.callbackTime = jCurrentTimeMillis2 - jCurrentTimeMillis;
                anet.channel.fulltrace.a.a().commitRequest(requestStatistic.traceId, requestStatistic);
            }
            if (this.f7228c.f7214c != null) {
                this.f7228c.f7214c.writeEnd();
            }
            if (requestStatistic != null) {
                ALog.e("anet.Repeater", "[traceId:" + requestStatistic.traceId + "]end, " + requestStatistic.toString(), this.f7228c.f7213b, new Object[0]);
                CopyOnWriteArrayList<String> bucketInfo = GlobalAppRuntimeInfo.getBucketInfo();
                if (bucketInfo != null) {
                    int size = bucketInfo.size();
                    for (int i2 = 0; i2 < size - 1; i2 += 2) {
                        requestStatistic.putExtra(bucketInfo.get(i2), bucketInfo.get(i2 + 1));
                    }
                }
                if (GlobalAppRuntimeInfo.isAppBackground()) {
                    requestStatistic.putExtra("restrictBg", Integer.valueOf(NetworkStatusHelper.getRestrictBackgroundStatus()));
                }
                anet.channel.fulltrace.b sceneInfo = anet.channel.fulltrace.a.a().getSceneInfo();
                if (sceneInfo != null) {
                    ALog.i("anet.Repeater", sceneInfo.toString(), this.f7228c.f7213b, new Object[0]);
                    long j2 = requestStatistic.start;
                    long j3 = sceneInfo.f6788c;
                    requestStatistic.sinceInitTime = j2 - j3;
                    int i3 = sceneInfo.f6786a;
                    requestStatistic.startType = i3;
                    if (i3 != 1) {
                        requestStatistic.sinceLastLaunchTime = j3 - sceneInfo.f6789d;
                    }
                    requestStatistic.deviceLevel = sceneInfo.f6790e;
                    requestStatistic.isFromExternal = sceneInfo.f6787b ? 1 : 0;
                    requestStatistic.speedBucket = sceneInfo.f6791f;
                    requestStatistic.abTestBucket = sceneInfo.f6792g;
                }
                requestStatistic.serializeTransferTime = requestStatistic.reqServiceTransmissionEnd - requestStatistic.netReqStart;
                requestStatistic.userInfo = this.f7228c.f7216e.a(RequestConstant.REQUEST_USER_INFO);
                AppMonitor.getInstance().commitStat(requestStatistic);
                if (NetworkConfigCenter.isRequestInMonitorList(requestStatistic)) {
                    AppMonitor.getInstance().commitStat(new RequestMonitor(requestStatistic));
                }
                try {
                    String str = requestStatistic.ip;
                    JSONObject jSONObject = requestStatistic.extra;
                    if (jSONObject != null) {
                        strOptString = jSONObject.optString("firstIp");
                    }
                    if (anet.channel.strategy.utils.c.b(str) || anet.channel.strategy.utils.c.b(strOptString)) {
                        AppMonitor.getInstance().commitStat(new RequestMonitor(requestStatistic));
                    }
                } catch (Exception unused) {
                }
                NetworkStat.getNetworkStat().put(this.f7228c.f7216e.g(), this.f7226a.getStatisticData());
                n.a(requestStatistic);
            }
        } catch (Throwable unused2) {
        }
    }
}
