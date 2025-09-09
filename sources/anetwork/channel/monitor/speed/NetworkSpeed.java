package anetwork.channel.monitor.speed;

/* loaded from: classes2.dex */
public enum NetworkSpeed {
    Slow("弱网络", 1),
    Fast("强网络", 5);

    private final int code;
    private final String desc;

    NetworkSpeed(String str, int i2) {
        this.desc = str;
        this.code = i2;
    }

    public static NetworkSpeed valueOfCode(int i2) {
        return i2 == 1 ? Slow : Fast;
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
