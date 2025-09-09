package anet.channel.detect;

import anet.channel.RequestCb;
import anet.channel.bytes.ByteArray;
import anet.channel.statist.HorseRaceStat;
import anet.channel.statist.RequestStatistic;
import anet.channel.util.ALog;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
class i implements RequestCb {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ h f6736a;

    i(h hVar) {
        this.f6736a = hVar;
    }

    @Override // anet.channel.RequestCb
    public void onDataReceive(ByteArray byteArray, boolean z2) {
    }

    @Override // anet.channel.RequestCb
    public void onFinish(int i2, String str, RequestStatistic requestStatistic) {
        ALog.i("anet.HorseRaceDetector", "LongLinkTask request finish", this.f6736a.f6732c, HiAnalyticsConstant.HaKey.BI_KEY_RESULT, Integer.valueOf(i2), "msg", str);
        if (this.f6736a.f6730a.reqErrorCode == 0) {
            this.f6736a.f6730a.reqErrorCode = i2;
        } else {
            HorseRaceStat horseRaceStat = this.f6736a.f6730a;
            horseRaceStat.reqRet = horseRaceStat.reqErrorCode == 200 ? 1 : 0;
        }
        HorseRaceStat horseRaceStat2 = this.f6736a.f6730a;
        long jCurrentTimeMillis = System.currentTimeMillis();
        h hVar = this.f6736a;
        horseRaceStat2.reqTime = (jCurrentTimeMillis - hVar.f6731b) + hVar.f6730a.connTime;
        synchronized (this.f6736a.f6730a) {
            this.f6736a.f6730a.notify();
        }
    }

    @Override // anet.channel.RequestCb
    public void onResponseCode(int i2, Map<String, List<String>> map) {
        this.f6736a.f6730a.reqErrorCode = i2;
    }
}
