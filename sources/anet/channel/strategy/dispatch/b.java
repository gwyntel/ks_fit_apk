package anet.channel.strategy.dispatch;

import android.util.Base64InputStream;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.flow.FlowStat;
import anet.channel.flow.NetworkAnalysis;
import anet.channel.statist.AmdcStatistic;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.ConnEvent;
import anet.channel.strategy.IConnStrategy;
import anet.channel.strategy.StrategyCenter;
import anet.channel.util.ALog;
import anet.channel.util.HttpConstant;
import com.taobao.accs.common.Constants;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HostnameVerifier;

/* loaded from: classes2.dex */
class b {

    /* renamed from: a, reason: collision with root package name */
    static AtomicInteger f7002a = new AtomicInteger(0);

    /* renamed from: b, reason: collision with root package name */
    static HostnameVerifier f7003b = new c();

    /* renamed from: c, reason: collision with root package name */
    static Random f7004c = new Random();

    b() {
    }

    static List<IConnStrategy> a(String str) {
        List<IConnStrategy> connStrategyListByHost = Collections.EMPTY_LIST;
        if (!NetworkStatusHelper.isProxy()) {
            connStrategyListByHost = StrategyCenter.getInstance().getConnStrategyListByHost(DispatchConstants.getAmdcServerDomain());
            ListIterator<IConnStrategy> listIterator = connStrategyListByHost.listIterator();
            while (listIterator.hasNext()) {
                if (!listIterator.next().getProtocol().protocol.equalsIgnoreCase(str)) {
                    listIterator.remove();
                }
            }
        }
        return connStrategyListByHost;
    }

    public static void a(Map map) {
        String schemeByHost;
        String strA;
        IConnStrategy iConnStrategyRemove;
        String strA2;
        if (map == null) {
            return;
        }
        if (AmdcRuntimeInfo.isForceHttps()) {
            schemeByHost = "https";
        } else {
            schemeByHost = StrategyCenter.getInstance().getSchemeByHost(DispatchConstants.getAmdcServerDomain(), "http");
        }
        List<IConnStrategy> listA = a(schemeByHost);
        for (int i2 = 0; i2 < 3; i2++) {
            HashMap map2 = new HashMap(map);
            if (i2 != 2) {
                iConnStrategyRemove = !listA.isEmpty() ? listA.remove(0) : null;
                if (iConnStrategyRemove != null) {
                    strA2 = a(schemeByHost, iConnStrategyRemove.getIp(), iConnStrategyRemove.getPort(), map2, i2);
                } else {
                    strA2 = a(schemeByHost, (String) null, 0, map2, i2);
                }
            } else {
                String[] amdcServerFixIp = DispatchConstants.getAmdcServerFixIp();
                if (amdcServerFixIp != null && amdcServerFixIp.length > 0) {
                    strA = a(schemeByHost, amdcServerFixIp[f7004c.nextInt(amdcServerFixIp.length)], 0, map2, i2);
                } else {
                    strA = a(schemeByHost, (String) null, 0, map2, i2);
                }
                String str = strA;
                iConnStrategyRemove = null;
                strA2 = str;
            }
            int iA = a(strA2, map2, i2);
            if (iConnStrategyRemove != null) {
                ConnEvent connEvent = new ConnEvent();
                connEvent.isSuccess = iA == 0;
                StrategyCenter.getInstance().notifyConnEvent(DispatchConstants.getAmdcServerDomain(), iConnStrategyRemove, connEvent);
            }
            if (iA == 0 || iA == 2) {
                return;
            }
        }
    }

    private static String a(String str, String str2, int i2, Map<String, String> map, int i3) {
        StringBuilder sb = new StringBuilder(64);
        if (!AmdcRuntimeInfo.isForceHttps() && i3 == 2 && "https".equalsIgnoreCase(str) && f7004c.nextBoolean()) {
            str = "http";
        }
        sb.append(str);
        sb.append(HttpConstant.SCHEME_SPLIT);
        if (str2 != null) {
            if (anet.channel.util.c.a() && anet.channel.strategy.utils.c.a(str2)) {
                try {
                    str2 = anet.channel.util.c.a(str2);
                } catch (Exception unused) {
                }
            }
            if (anet.channel.strategy.utils.c.b(str2)) {
                sb.append('[');
                sb.append(str2);
                sb.append(']');
            } else {
                sb.append(str2);
            }
            if (i2 == 0) {
                i2 = "https".equalsIgnoreCase(str) ? Constants.PORT : 80;
            }
            sb.append(":");
            sb.append(i2);
        } else {
            sb.append(DispatchConstants.getAmdcServerDomain());
        }
        sb.append(DispatchConstants.serverPath);
        TreeMap treeMap = new TreeMap();
        treeMap.put("appkey", map.remove("appkey"));
        treeMap.put("v", map.remove("v"));
        treeMap.put(DispatchConstants.PLATFORM, map.remove(DispatchConstants.PLATFORM));
        sb.append('?');
        sb.append(anet.channel.strategy.utils.c.a(treeMap, "utf-8"));
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x029f A[Catch: all -> 0x02a4, TryCatch #1 {all -> 0x02a4, blocks: (B:103:0x0295, B:105:0x029f, B:108:0x02a7), top: B:123:0x0295 }] */
    /* JADX WARN: Removed duplicated region for block: B:138:0x02b7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:152:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int a(java.lang.String r18, java.util.Map r19, int r20) {
        /*
            Method dump skipped, instructions count: 725
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.strategy.dispatch.b.a(java.lang.String, java.util.Map, int):int");
    }

    static String a(InputStream inputStream, boolean z2) throws Throwable {
        Throwable th;
        IOException e2;
        FilterInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        if (z2) {
            try {
                try {
                    bufferedInputStream = new GZIPInputStream(bufferedInputStream);
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        bufferedInputStream.close();
                    } catch (IOException unused) {
                    }
                    throw th;
                }
            } catch (IOException e3) {
                e2 = e3;
                ALog.e("awcn.DispatchCore", "", null, e2, new Object[0]);
                try {
                    bufferedInputStream.close();
                } catch (IOException unused2) {
                }
                return null;
            }
        }
        FilterInputStream base64InputStream = new Base64InputStream(bufferedInputStream, 0);
        try {
            byte[] bArr = new byte[1024];
            while (true) {
                int i2 = base64InputStream.read(bArr);
                if (i2 == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, i2);
            }
            String str = new String(byteArrayOutputStream.toByteArray(), "utf-8");
            try {
                base64InputStream.close();
            } catch (IOException unused3) {
            }
            return str;
        } catch (IOException e4) {
            e2 = e4;
            bufferedInputStream = base64InputStream;
            ALog.e("awcn.DispatchCore", "", null, e2, new Object[0]);
            bufferedInputStream.close();
            return null;
        } catch (Throwable th3) {
            th = th3;
            bufferedInputStream = base64InputStream;
            bufferedInputStream.close();
            throw th;
        }
    }

    static void a(String str, String str2, URL url, int i2, int i3) {
        if ((i3 != 1 || i2 == 2) && GlobalAppRuntimeInfo.isTargetProcess()) {
            try {
                AmdcStatistic amdcStatistic = new AmdcStatistic();
                amdcStatistic.errorCode = str;
                amdcStatistic.errorMsg = str2;
                if (url != null) {
                    amdcStatistic.host = url.getHost();
                    amdcStatistic.url = url.toString();
                }
                amdcStatistic.retryTimes = i2;
                AppMonitor.getInstance().commitStat(amdcStatistic);
            } catch (Exception unused) {
            }
        }
    }

    static void a(String str, long j2, long j3) {
        try {
            FlowStat flowStat = new FlowStat();
            flowStat.refer = "amdc";
            flowStat.protocoltype = "http";
            flowStat.req_identifier = str;
            flowStat.upstream = j2;
            flowStat.downstream = j3;
            NetworkAnalysis.getInstance().commitFlow(flowStat);
        } catch (Exception e2) {
            ALog.e("awcn.DispatchCore", "commit flow info failed!", null, e2, new Object[0]);
        }
    }
}
