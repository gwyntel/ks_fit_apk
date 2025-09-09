package anet.channel.monitor;

/* loaded from: classes2.dex */
public enum NetworkSpeed {
    Slow("弱网络", 1),
    Fast("强网络", 5);


    /* renamed from: a, reason: collision with root package name */
    private final String f6810a;

    /* renamed from: b, reason: collision with root package name */
    private final int f6811b;

    NetworkSpeed(String str, int i2) {
        this.f6810a = str;
        this.f6811b = i2;
    }

    public static NetworkSpeed valueOfCode(int i2) {
        return i2 == 1 ? Slow : Fast;
    }

    public int getCode() {
        return this.f6811b;
    }

    public String getDesc() {
        return this.f6810a;
    }
}
