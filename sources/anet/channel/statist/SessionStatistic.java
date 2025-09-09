package anet.channel.statist;

import androidx.health.connect.client.records.HeartRateVariabilityRmssdRecord;
import anet.channel.AwcnConfig;
import anet.channel.entity.a;
import anet.channel.fulltrace.b;
import anet.channel.strategy.IConnStrategy;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import org.json.JSONObject;

@Monitor(module = "networkPrefer", monitorPoint = "session")
/* loaded from: classes2.dex */
public class SessionStatistic extends StatObject {
    public static int maxRetryTime;

    @Measure
    public long ackTime;

    @Measure(max = 15000.0d)
    public long authTime;

    @Measure
    public long cfRCount;

    @Dimension
    public String closeReason;

    @Dimension
    public int congControlKind;

    @Measure(max = 15000.0d, name = "connTime")
    public long connectionTime;

    @Dimension(name = "protocolType")
    public String conntype;

    @Dimension
    public String dcid;

    @Dimension
    public long errorCode;

    @Dimension
    public String host;

    @Measure
    public long inceptCount;

    @Dimension
    public String ip;

    @Dimension
    public int ipRefer;

    @Dimension
    public int ipType;

    @Dimension
    public boolean isBackground;

    @Dimension
    public long isKL;

    @Dimension
    public String isTunnel;

    @Measure
    public int lastPingInterval;

    @Measure
    public double lossRate;

    @Dimension
    public String netType;

    @Measure
    public long pRate;

    @Dimension
    public int port;

    @Measure
    public long ppkgCount;

    @Measure
    public long recvSizeCount;

    @Dimension
    public int ret;

    @Measure
    public double retransmissionRate;

    @Dimension
    public long retryTimes;

    @Measure
    public int rtoCount;

    @Dimension
    public String scid;

    @Dimension
    public int sdkv;

    @Measure
    public long sendSizeCount;

    @Measure
    public long srtt;

    @Measure(max = 15000.0d)
    public long sslCalTime;

    @Measure(max = 15000.0d)
    public long sslTime;

    @Measure
    public int tlpCount;

    @Dimension
    public int xqc0RttStatus;

    @Dimension
    public String xqcConnEnv;

    @Dimension
    public int isProxy = 0;

    @Dimension
    public JSONObject extra = null;

    @Measure(max = 86400.0d)
    public long liveTime = 0;

    @Measure(constantValue = HeartRateVariabilityRmssdRecord.MIN_HRV_RMSSD)
    public long requestCount = 1;

    @Measure(constantValue = 0.0d)
    public long stdRCount = 1;
    public boolean isCommitted = false;

    public SessionStatistic(a aVar) {
        this.ipRefer = 0;
        this.ipType = 1;
        if (aVar == null) {
            return;
        }
        this.ip = aVar.a();
        this.port = aVar.b();
        IConnStrategy iConnStrategy = aVar.f6769a;
        if (iConnStrategy != null) {
            this.ipRefer = iConnStrategy.getIpSource();
            this.ipType = aVar.f6769a.getIpType();
        }
        this.pRate = aVar.g();
        this.conntype = aVar.c().toString();
        this.retryTimes = aVar.f6770b;
        maxRetryTime = aVar.f6771c;
        b sceneInfo = anet.channel.fulltrace.a.a().getSceneInfo();
        String str = sceneInfo != null ? sceneInfo.f6791f : null;
        boolean zB = anet.channel.e.a.b();
        this.xqcConnEnv = AwcnConfig.isHttp3OrangeEnable() + OpenAccountUIConstants.UNDER_LINE + zB + OpenAccountUIConstants.UNDER_LINE + str;
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x001b, code lost:
    
        if (r3 != (-2601)) goto L14;
     */
    @Override // anet.channel.statist.StatObject
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean beforeCommit() {
        /*
            r7 = this;
            int r0 = r7.ret
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L52
            long r3 = r7.retryTimes
            int r0 = anet.channel.statist.SessionStatistic.maxRetryTime
            long r5 = (long) r0
            int r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r0 != 0) goto L1d
            long r3 = r7.errorCode
            r5 = -2613(0xfffffffffffff5cb, double:NaN)
            int r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r0 == 0) goto L1d
            r5 = -2601(0xfffffffffffff5d7, double:NaN)
            int r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r0 != 0) goto L52
        L1d:
            boolean r0 = anet.channel.util.ALog.isPrintLog(r2)
            if (r0 == 0) goto L51
            long r3 = r7.retryTimes
            java.lang.Long r0 = java.lang.Long.valueOf(r3)
            int r3 = anet.channel.statist.SessionStatistic.maxRetryTime
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            long r4 = r7.errorCode
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            r5 = 5
            java.lang.Object[] r5 = new java.lang.Object[r5]
            r5[r1] = r0
            java.lang.String r0 = "maxRetryTime"
            r5[r2] = r0
            r0 = 2
            r5[r0] = r3
            java.lang.String r0 = "errorCode"
            r2 = 3
            r5[r2] = r0
            r0 = 4
            r5[r0] = r4
            java.lang.String r0 = "SessionStat no need commit"
            r2 = 0
            java.lang.String r3 = "retry:"
            anet.channel.util.ALog.d(r0, r2, r3, r5)
        L51:
            return r1
        L52:
            boolean r0 = r7.isCommitted
            if (r0 == 0) goto L57
            return r1
        L57:
            r7.isCommitted = r2
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.statist.SessionStatistic.beforeCommit():boolean");
    }

    public AlarmObject getAlarmObject() {
        AlarmObject alarmObject = new AlarmObject();
        alarmObject.module = "networkPrefer";
        alarmObject.modulePoint = "connect_succ_rate";
        boolean z2 = this.ret != 0;
        alarmObject.isSuccess = z2;
        if (z2) {
            alarmObject.arg = this.closeReason;
        } else {
            alarmObject.errorCode = String.valueOf(this.errorCode);
        }
        return alarmObject;
    }
}
