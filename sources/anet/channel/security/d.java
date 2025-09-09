package anet.channel.security;

/* loaded from: classes2.dex */
final class d implements ISecurityFactory {
    d() {
    }

    @Override // anet.channel.security.ISecurityFactory
    public ISecurity createNonSecurity(String str) {
        return new a(str);
    }

    @Override // anet.channel.security.ISecurityFactory
    public ISecurity createSecurity(String str) {
        return new b(str);
    }
}
