package anet.channel.detect;

import android.content.Context;
import anet.channel.AwcnConfig;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.RequestCb;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.entity.ConnType;
import anet.channel.request.Request;
import anet.channel.session.TnetSpdySession;
import anet.channel.session.b;
import anet.channel.statist.HorseRaceStat;
import anet.channel.strategy.ConnProtocol;
import anet.channel.strategy.IConnStrategy;
import anet.channel.strategy.StrategyCenter;
import anet.channel.strategy.l;
import anet.channel.util.ALog;
import anet.channel.util.AppLifecycle;
import anet.channel.util.ErrorConstant;
import anet.channel.util.HttpConstant;
import anet.channel.util.HttpUrl;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.android.netutil.PingResponse;
import org.android.netutil.PingTask;
import org.json.JSONException;

/* loaded from: classes2.dex */
class d {

    /* renamed from: a, reason: collision with root package name */
    TreeMap<String, l.c> f6725a = new TreeMap<>();

    /* renamed from: b, reason: collision with root package name */
    private AtomicInteger f6726b = new AtomicInteger(1);

    d() {
    }

    private void c(String str, l.e eVar) throws IOException {
        String str2 = "HR" + this.f6726b.getAndIncrement();
        ALog.i("anet.HorseRaceDetector", "startTcpTask", str2, "ip", eVar.f7051a, "port", Integer.valueOf(eVar.f7052b.f7021a));
        HorseRaceStat horseRaceStat = new HorseRaceStat(str, eVar);
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            Socket socket = new Socket(eVar.f7051a, eVar.f7052b.f7021a);
            int i2 = eVar.f7052b.f7023c;
            if (i2 == 0) {
                i2 = 10000;
            }
            socket.setSoTimeout(i2);
            ALog.i("anet.HorseRaceDetector", "socket connect success", str2, new Object[0]);
            horseRaceStat.connRet = 1;
            horseRaceStat.connTime = System.currentTimeMillis() - jCurrentTimeMillis;
            socket.close();
        } catch (IOException unused) {
            horseRaceStat.connTime = System.currentTimeMillis() - jCurrentTimeMillis;
            horseRaceStat.connErrorCode = ErrorConstant.ERROR_IO_EXCEPTION;
        }
        AppMonitor.getInstance().commitStat(horseRaceStat);
    }

    void a() {
        ALog.e("anet.HorseRaceDetector", "network detect thread start", null, new Object[0]);
        while (true) {
            synchronized (this.f6725a) {
                try {
                    if (!AwcnConfig.isHorseRaceEnable()) {
                        this.f6725a.clear();
                        return;
                    }
                    Map.Entry<String, l.c> entryPollFirstEntry = this.f6725a.pollFirstEntry();
                    if (entryPollFirstEntry == null) {
                        return;
                    }
                    try {
                        a(entryPollFirstEntry.getValue());
                    } catch (Exception e2) {
                        ALog.e("anet.HorseRaceDetector", "start hr task failed", null, e2, new Object[0]);
                    }
                } finally {
                }
            }
        }
    }

    public void b() {
        StrategyCenter.getInstance().registerListener(new e(this));
        AppLifecycle.registerLifecycleListener(new f(this));
    }

    private void b(String str, l.e eVar) {
        ConnProtocol connProtocolValueOf = ConnProtocol.valueOf(eVar.f7052b);
        ConnType connTypeValueOf = ConnType.valueOf(connProtocolValueOf);
        if (connTypeValueOf == null) {
            return;
        }
        ALog.i("anet.HorseRaceDetector", "startLongLinkTask", null, "host", str, "ip", eVar.f7051a, "port", Integer.valueOf(eVar.f7052b.f7021a), "protocol", connProtocolValueOf);
        String str2 = "HR" + this.f6726b.getAndIncrement();
        Context context = GlobalAppRuntimeInfo.getContext();
        StringBuilder sb = new StringBuilder();
        sb.append(connTypeValueOf.isSSL() ? "https://" : "http://");
        sb.append(str);
        TnetSpdySession tnetSpdySession = new TnetSpdySession(context, new anet.channel.entity.a(sb.toString(), str2, a(connProtocolValueOf, eVar)));
        HorseRaceStat horseRaceStat = new HorseRaceStat(str, eVar);
        long jCurrentTimeMillis = System.currentTimeMillis();
        tnetSpdySession.registerEventcb(257, new h(this, horseRaceStat, jCurrentTimeMillis, str2, eVar, tnetSpdySession));
        tnetSpdySession.connect();
        synchronized (horseRaceStat) {
            try {
                try {
                    int i2 = eVar.f7052b.f7023c;
                    if (i2 == 0) {
                        i2 = 10000;
                    }
                    horseRaceStat.wait(i2);
                    if (horseRaceStat.connTime == 0) {
                        horseRaceStat.connTime = System.currentTimeMillis() - jCurrentTimeMillis;
                    }
                    a(eVar.f7051a, horseRaceStat);
                    AppMonitor.getInstance().commitStat(horseRaceStat);
                } catch (Throwable th) {
                    throw th;
                }
            } catch (InterruptedException unused) {
            }
        }
        tnetSpdySession.close(false);
    }

    private void a(l.c cVar) throws JSONException, IOException {
        l.e[] eVarArr = cVar.f7042b;
        if (eVarArr == null || eVarArr.length == 0) {
            return;
        }
        String str = cVar.f7041a;
        int i2 = 0;
        while (true) {
            l.e[] eVarArr2 = cVar.f7042b;
            if (i2 >= eVarArr2.length) {
                return;
            }
            l.e eVar = eVarArr2[i2];
            String str2 = eVar.f7052b.f7022b;
            if (!str2.equalsIgnoreCase("http") && !str2.equalsIgnoreCase("https")) {
                if (!str2.equalsIgnoreCase(ConnType.HTTP2) && !str2.equalsIgnoreCase(ConnType.SPDY) && !str2.equalsIgnoreCase(ConnType.QUIC)) {
                    if (str2.equalsIgnoreCase("tcp")) {
                        c(str, eVar);
                    }
                } else {
                    b(str, eVar);
                }
            } else {
                a(str, eVar);
            }
            i2++;
        }
    }

    private void a(String str, l.e eVar) throws JSONException {
        HttpUrl httpUrl = HttpUrl.parse(eVar.f7052b.f7022b + HttpConstant.SCHEME_SPLIT + str + eVar.f7053c);
        if (httpUrl == null) {
            return;
        }
        ALog.i("anet.HorseRaceDetector", "startShortLinkTask", null, "url", httpUrl);
        Request requestBuild = new Request.Builder().setUrl(httpUrl).addHeader("Connection", "close").setConnectTimeout(eVar.f7052b.f7023c).setReadTimeout(eVar.f7052b.f7024d).setRedirectEnable(false).setSslSocketFactory(new anet.channel.util.j(str)).setSeq("HR" + this.f6726b.getAndIncrement()).build();
        requestBuild.setDnsOptimize(eVar.f7051a, eVar.f7052b.f7021a);
        long jCurrentTimeMillis = System.currentTimeMillis();
        b.a aVarA = anet.channel.session.b.a(requestBuild, (RequestCb) null);
        long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
        HorseRaceStat horseRaceStat = new HorseRaceStat(str, eVar);
        horseRaceStat.connTime = jCurrentTimeMillis2;
        int i2 = aVarA.f6906a;
        if (i2 <= 0) {
            horseRaceStat.connErrorCode = i2;
        } else {
            horseRaceStat.connRet = 1;
            horseRaceStat.reqRet = aVarA.f6906a != 200 ? 0 : 1;
            horseRaceStat.reqErrorCode = aVarA.f6906a;
            horseRaceStat.reqTime = horseRaceStat.connTime;
        }
        a(eVar.f7051a, horseRaceStat);
        AppMonitor.getInstance().commitStat(horseRaceStat);
    }

    private static IConnStrategy a(ConnProtocol connProtocol, l.e eVar) {
        return new j(eVar, connProtocol);
    }

    private void a(String str, HorseRaceStat horseRaceStat) {
        if (AwcnConfig.isPing6Enable() && anet.channel.strategy.utils.c.b(str)) {
            try {
                PingResponse pingResponse = (PingResponse) new PingTask(str, 1000, 3, 0, 0).launch().get();
                if (pingResponse == null) {
                    return;
                }
                horseRaceStat.pingSuccessCount = pingResponse.getSuccessCnt();
                horseRaceStat.pingTimeoutCount = 3 - horseRaceStat.pingSuccessCount;
                horseRaceStat.localIP = pingResponse.getLocalIPStr();
            } catch (Throwable th) {
                ALog.e("anet.HorseRaceDetector", "ping6 task fail.", null, th, new Object[0]);
            }
        }
    }
}
