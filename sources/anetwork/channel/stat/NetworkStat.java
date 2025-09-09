package anetwork.channel.stat;

/* loaded from: classes2.dex */
public class NetworkStat {
    public static INetworkStat getNetworkStat() {
        return NetworkStatCache.getInstance();
    }
}
