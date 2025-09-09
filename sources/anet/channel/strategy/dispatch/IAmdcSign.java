package anet.channel.strategy.dispatch;

/* loaded from: classes2.dex */
public interface IAmdcSign {
    String getAppkey();

    String sign(String str);

    boolean useSecurityGuard();
}
