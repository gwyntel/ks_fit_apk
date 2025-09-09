package anet.channel.util;

import android.text.TextUtils;
import anet.channel.AwcnConfig;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.statist.NetTypeStat;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.thread.ThreadPoolExecutorFactory;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.android.netutil.UdpConnectType;
import org.android.spdy.SpdyAgent;
import org.android.spdy.SpdyErrorException;
import org.android.spdy.SpdySessionKind;
import org.android.spdy.SpdyVersion;

/* loaded from: classes2.dex */
public class c {
    public static final int IPV4_ONLY = 1;
    public static final int IPV6_ONLY = 2;
    public static final int IP_DUAL_STACK = 3;
    public static final int IP_STACK_UNKNOWN = 0;

    /* renamed from: b, reason: collision with root package name */
    static volatile String f7080b;

    /* renamed from: c, reason: collision with root package name */
    static f f7081c;

    /* renamed from: a, reason: collision with root package name */
    static final byte[][] f7079a = {new byte[]{-64, 0, 0, -86}, new byte[]{-64, 0, 0, -85}};

    /* renamed from: d, reason: collision with root package name */
    static ConcurrentHashMap<String, f> f7082d = new ConcurrentHashMap<>();

    /* renamed from: e, reason: collision with root package name */
    static ConcurrentHashMap<String, Integer> f7083e = new ConcurrentHashMap<>();

    static {
        f7080b = null;
        f7081c = null;
        try {
            f7081c = new f((Inet6Address) InetAddress.getAllByName("64:ff9b::")[0], 96);
            f7080b = b(NetworkStatusHelper.getStatus());
        } catch (Exception unused) {
        }
    }

    public static boolean a() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String b(NetworkStatusHelper.NetworkStatus networkStatus) {
        if (networkStatus.isWifi()) {
            String wifiBSSID = NetworkStatusHelper.getWifiBSSID();
            if (TextUtils.isEmpty(wifiBSSID)) {
                wifiBSSID = "";
            }
            return "WIFI$" + wifiBSSID;
        }
        if (!networkStatus.isMobile()) {
            return "UnknownNetwork";
        }
        return networkStatus.getType() + "$" + NetworkStatusHelper.getApn();
    }

    public static int c() {
        Integer num = f7083e.get(f7080b);
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    public static f d() {
        f fVar = f7082d.get(f7080b);
        return fVar == null ? f7081c : fVar;
    }

    public static void e() {
        if (!AwcnConfig.isIpv6Enable()) {
            ALog.e("awcn.Inet64Util", "[startIpStackDetect]ipv6Enable=false", null, new Object[0]);
            return;
        }
        f7080b = b(NetworkStatusHelper.getStatus());
        if (f7083e.putIfAbsent(f7080b, 0) != null) {
            return;
        }
        int iJ = j();
        f7083e.put(f7080b, Integer.valueOf(iJ));
        NetTypeStat netTypeStat = new NetTypeStat();
        netTypeStat.ipStackType = iJ;
        String str = f7080b;
        if (iJ == 2 || iJ == 3) {
            ThreadPoolExecutorFactory.submitScheduledTask(new d(str, netTypeStat), 1500L, TimeUnit.MILLISECONDS);
        } else if (GlobalAppRuntimeInfo.isTargetProcess()) {
            AppMonitor.getInstance().commitStat(netTypeStat);
        }
    }

    private static int h() throws SocketException {
        String str;
        int iIntValue;
        TreeMap treeMap = new TreeMap();
        Iterator it = Collections.list(NetworkInterface.getNetworkInterfaces()).iterator();
        while (true) {
            str = null;
            iIntValue = 0;
            if (!it.hasNext()) {
                break;
            }
            NetworkInterface networkInterface = (NetworkInterface) it.next();
            if (!networkInterface.getInterfaceAddresses().isEmpty()) {
                String displayName = networkInterface.getDisplayName();
                ALog.i("awcn.Inet64Util", "find NetworkInterface:" + displayName, null, new Object[0]);
                Iterator<InterfaceAddress> it2 = networkInterface.getInterfaceAddresses().iterator();
                int i2 = 0;
                while (it2.hasNext()) {
                    InetAddress address = it2.next().getAddress();
                    if (address instanceof Inet6Address) {
                        Inet6Address inet6Address = (Inet6Address) address;
                        if (!a(inet6Address)) {
                            ALog.e("awcn.Inet64Util", "Found IPv6 address:" + inet6Address.toString(), null, new Object[0]);
                            i2 |= 2;
                        }
                    } else if (address instanceof Inet4Address) {
                        Inet4Address inet4Address = (Inet4Address) address;
                        if (!a((InetAddress) inet4Address) && !inet4Address.getHostAddress().startsWith("192.168.43.")) {
                            ALog.e("awcn.Inet64Util", "Found IPv4 address:" + inet4Address.toString(), null, new Object[0]);
                            i2 |= 1;
                        }
                    }
                }
                if (i2 != 0) {
                    treeMap.put(displayName.toLowerCase(), Integer.valueOf(i2));
                }
            }
        }
        if (treeMap.isEmpty()) {
            return 0;
        }
        if (treeMap.size() == 1) {
            return ((Integer) treeMap.firstEntry().getValue()).intValue();
        }
        if (NetworkStatusHelper.getStatus().isWifi()) {
            str = "wlan";
        } else if (NetworkStatusHelper.getStatus().isMobile()) {
            str = "rmnet";
        }
        if (str != null) {
            Iterator it3 = treeMap.entrySet().iterator();
            while (true) {
                if (!it3.hasNext()) {
                    break;
                }
                Map.Entry entry = (Map.Entry) it3.next();
                if (((String) entry.getKey()).startsWith(str)) {
                    iIntValue = ((Integer) entry.getValue()).intValue();
                    break;
                }
            }
        }
        return (iIntValue == 2 && treeMap.containsKey("v4-wlan0")) ? iIntValue | ((Integer) treeMap.remove("v4-wlan0")).intValue() : iIntValue;
    }

    private static int i() throws SpdyErrorException, UnsatisfiedLinkError {
        SpdyAgent.getInstance(GlobalAppRuntimeInfo.getContext(), SpdyVersion.SPDY3, SpdySessionKind.NONE_SESSION);
        boolean zTestUdpConnectIpv4 = UdpConnectType.testUdpConnectIpv4();
        return UdpConnectType.testUdpConnectIpv6() ? (zTestUdpConnectIpv4 ? 1 : 0) | 2 : zTestUdpConnectIpv4 ? 1 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v3, types: [boolean] */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r3v9 */
    /* JADX WARN: Type inference failed for: r6v2, types: [java.lang.Object[]] */
    public static int j() {
        ?? IsIpStackDetectByUdpConnect;
        int iH;
        try {
            IsIpStackDetectByUdpConnect = AwcnConfig.isIpStackDetectByUdpConnect();
        } catch (Throwable th) {
            th = th;
            IsIpStackDetectByUdpConnect = 0;
        }
        try {
            if (IsIpStackDetectByUdpConnect != 0) {
                String str = "udp_connect";
                iH = i();
                IsIpStackDetectByUdpConnect = str;
            } else {
                String str2 = "interfaces";
                iH = h();
                IsIpStackDetectByUdpConnect = str2;
            }
        } catch (Throwable th2) {
            th = th2;
            ALog.e("awcn.Inet64Util", "[detectIpStack]error.", null, th, new Object[0]);
            iH = 0;
            ALog.e("awcn.Inet64Util", "startIpStackDetect", null, new Object[]{"ip stack", Integer.valueOf(iH), "detectType", IsIpStackDetectByUdpConnect});
            return iH;
        }
        ALog.e("awcn.Inet64Util", "startIpStackDetect", null, new Object[]{"ip stack", Integer.valueOf(iH), "detectType", IsIpStackDetectByUdpConnect});
        return iH;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static f k() throws UnknownHostException {
        InetAddress byName;
        byte b2;
        try {
            byName = InetAddress.getByName("ipv4only.arpa");
        } catch (Exception unused) {
            byName = null;
        }
        if (byName instanceof Inet6Address) {
            ALog.i("awcn.Inet64Util", "Resolved AAAA: " + byName.toString(), null, new Object[0]);
            byte[] address = byName.getAddress();
            if (address.length != 16) {
                return null;
            }
            for (int i2 = 12; i2 >= 0; i2--) {
                byte b3 = address[i2];
                byte[][] bArr = f7079a;
                byte[] bArr2 = bArr[0];
                if ((b3 & bArr2[0]) != 0 && address[i2 + 1] == 0 && address[i2 + 2] == 0 && ((b2 = address[i2 + 3]) == bArr2[3] || b2 == bArr[1][3])) {
                    address[i2 + 3] = 0;
                    address[i2 + 2] = 0;
                    address[i2 + 1] = 0;
                    address[i2] = 0;
                    return new f(Inet6Address.getByAddress("ipv4only.arpa", address, 0), i2 * 8);
                }
            }
        } else if (byName instanceof Inet4Address) {
            ALog.i("awcn.Inet64Util", "Resolved A: " + byName.toString(), null, new Object[0]);
        }
        return null;
    }

    public static String a(Inet4Address inet4Address) throws Exception {
        if (inet4Address != null) {
            f fVarD = d();
            if (fVarD != null) {
                byte[] address = inet4Address.getAddress();
                byte[] address2 = fVarD.f7088b.getAddress();
                int i2 = fVarD.f7087a / 8;
                int i3 = 0;
                int i4 = 0;
                while (true) {
                    int i5 = i3 + i2;
                    if (i5 > 15 || i4 >= 4) {
                        break;
                    }
                    if (i5 != 8) {
                        address2[i5] = (byte) (address[i4] | address2[i5]);
                        i4++;
                    }
                    i3++;
                }
                return InetAddress.getByAddress(address2).getHostAddress();
            }
            throw new Exception("cannot get nat64 prefix");
        }
        throw new InvalidParameterException("address in null");
    }

    public static boolean b() {
        Integer num = f7083e.get(f7080b);
        return num != null && num.intValue() == 1;
    }

    public static String a(String str) throws Exception {
        return a((Inet4Address) InetAddress.getByName(str));
    }

    private static boolean a(InetAddress inetAddress) {
        return inetAddress.isLoopbackAddress() || inetAddress.isLinkLocalAddress() || inetAddress.isAnyLocalAddress();
    }
}
