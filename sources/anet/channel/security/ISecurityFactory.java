package anet.channel.security;

/* loaded from: classes2.dex */
public interface ISecurityFactory {
    ISecurity createNonSecurity(String str);

    ISecurity createSecurity(String str);
}
