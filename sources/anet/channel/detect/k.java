package anet.channel.detect;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import anet.channel.AwcnConfig;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.statist.MtuDetectStat;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.IConnStrategy;
import anet.channel.strategy.StrategyCenter;
import anet.channel.util.ALog;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.jmdns.impl.constants.DNSConstants;
import org.android.netutil.PingEntry;
import org.android.netutil.PingResponse;
import org.android.netutil.PingTask;
import org.android.spdy.SpdyAgent;
import org.android.spdy.SpdyErrorException;
import org.android.spdy.SpdySessionKind;
import org.android.spdy.SpdyVersion;

/* loaded from: classes2.dex */
class k {

    /* renamed from: a, reason: collision with root package name */
    private static HashMap<String, Long> f6739a = new HashMap<>();

    k() {
    }

    public void a() {
        NetworkStatusHelper.addStatusChangeListener(new l(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) throws ExecutionException, InterruptedException, SpdyErrorException, UnsatisfiedLinkError {
        PingResponse pingResponse;
        if (!AwcnConfig.isNetworkDetectEnable()) {
            ALog.i("anet.MTUDetector", "network detect closed.", null, new Object[0]);
            return;
        }
        ALog.i("anet.MTUDetector", "mtuDetectTask start", null, new Object[0]);
        SpdyAgent.getInstance(GlobalAppRuntimeInfo.getContext(), SpdyVersion.SPDY3, SpdySessionKind.NONE_SESSION);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        Long l2 = f6739a.get(str);
        if (l2 == null || jCurrentTimeMillis >= l2.longValue()) {
            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(GlobalAppRuntimeInfo.getContext());
            long j2 = defaultSharedPreferences.getLong("sp_mtu_" + str, 0L);
            if (jCurrentTimeMillis < j2) {
                f6739a.put(str, Long.valueOf(j2));
                ALog.i("anet.MTUDetector", "mtuDetectTask in period of validity", null, new Object[0]);
                return;
            }
            List<IConnStrategy> connStrategyListByHost = StrategyCenter.getInstance().getConnStrategyListByHost("guide-acs.m.taobao.com");
            String ip = (connStrategyListByHost == null || connStrategyListByHost.isEmpty()) ? null : connStrategyListByHost.get(0).getIp();
            if (TextUtils.isEmpty(ip)) {
                return;
            }
            String str2 = ip;
            Future futureLaunch = new PingTask(str2, 1000, 3, 0, 0).launch();
            Future<PingResponse> futureLaunch2 = new PingTask(str2, 1000, 3, 1172, 0).launch();
            Future<PingResponse> futureLaunch3 = new PingTask(str2, 1000, 3, 1272, 0).launch();
            Future<PingResponse> futureLaunch4 = new PingTask(str2, 1000, 3, 1372, 0).launch();
            Future<PingResponse> futureLaunch5 = new PingTask(str2, 1000, 3, 1432, 0).launch();
            try {
                pingResponse = (PingResponse) futureLaunch.get();
            } catch (Exception unused) {
                pingResponse = null;
            }
            if (pingResponse == null) {
                return;
            }
            if (pingResponse.getSuccessCnt() < 2) {
                ALog.e("anet.MTUDetector", "MTU detect preTask error", null, "errCode", Integer.valueOf(pingResponse.getErrcode()), "successCount", Integer.valueOf(pingResponse.getSuccessCnt()));
                return;
            }
            a(1200, futureLaunch2);
            a(1300, futureLaunch3);
            a(1400, futureLaunch4);
            a(DNSConstants.MAX_MSG_TYPICAL, futureLaunch5);
            long j3 = jCurrentTimeMillis + 432000000;
            f6739a.put(str, Long.valueOf(j3));
            defaultSharedPreferences.edit().putLong("sp_mtu_" + str, j3).apply();
        }
    }

    private void a(int i2, Future<PingResponse> future) throws ExecutionException, InterruptedException {
        PingResponse pingResponse;
        try {
            pingResponse = future.get();
        } catch (Exception unused) {
            pingResponse = null;
        }
        if (pingResponse == null) {
            return;
        }
        int successCnt = pingResponse.getSuccessCnt();
        int i3 = 3 - successCnt;
        StringBuilder sb = new StringBuilder();
        PingEntry[] results = pingResponse.getResults();
        int length = results.length;
        for (int i4 = 0; i4 < length; i4++) {
            sb.append(results[i4].rtt);
            if (i4 != length - 1) {
                sb.append(",");
            }
        }
        ALog.i("anet.MTUDetector", "MTU detect result", null, "mtu", Integer.valueOf(i2), "successCount", Integer.valueOf(successCnt), "timeoutCount", Integer.valueOf(i3));
        MtuDetectStat mtuDetectStat = new MtuDetectStat();
        mtuDetectStat.mtu = i2;
        mtuDetectStat.pingSuccessCount = successCnt;
        mtuDetectStat.pingTimeoutCount = i3;
        mtuDetectStat.rtt = sb.toString();
        mtuDetectStat.errCode = pingResponse.getErrcode();
        AppMonitor.getInstance().commitStat(mtuDetectStat);
    }
}
