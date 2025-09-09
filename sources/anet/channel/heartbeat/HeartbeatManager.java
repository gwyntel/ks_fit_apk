package anet.channel.heartbeat;

/* loaded from: classes2.dex */
public class HeartbeatManager {
    public static IHeartbeat getDefaultBackgroundAccsHeartbeat() {
        return new a();
    }

    public static IHeartbeat getDefaultHeartbeat() {
        return new b();
    }
}
