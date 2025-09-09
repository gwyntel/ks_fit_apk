package anet.channel.strategy;

/* loaded from: classes2.dex */
public class ConnEvent {
    public boolean isSuccess = false;
    public long connTime = Long.MAX_VALUE;
    public boolean isAccs = false;

    public String toString() {
        return this.isSuccess ? "ConnEvent#Success" : "ConnEvent#Fail";
    }
}
