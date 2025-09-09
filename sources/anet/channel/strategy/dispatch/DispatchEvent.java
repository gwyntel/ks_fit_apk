package anet.channel.strategy.dispatch;

/* loaded from: classes2.dex */
public class DispatchEvent {
    public static final int DNSFAIL = 0;
    public static final int DNSSUCCESS = 1;
    public final int eventType;
    public final Object extraObject;

    public DispatchEvent(int i2, Object obj) {
        this.eventType = i2;
        this.extraObject = obj;
    }
}
