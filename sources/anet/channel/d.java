package anet.channel;

import anet.channel.security.ISecurity;
import anet.channel.strategy.dispatch.IAmdcSign;

/* loaded from: classes2.dex */
class d implements IAmdcSign {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ String f6704a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ ISecurity f6705b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ SessionCenter f6706c;

    d(SessionCenter sessionCenter, String str, ISecurity iSecurity) {
        this.f6706c = sessionCenter;
        this.f6704a = str;
        this.f6705b = iSecurity;
    }

    @Override // anet.channel.strategy.dispatch.IAmdcSign
    public String getAppkey() {
        return this.f6704a;
    }

    @Override // anet.channel.strategy.dispatch.IAmdcSign
    public String sign(String str) {
        return this.f6705b.sign(this.f6706c.f6645b, ISecurity.SIGN_ALGORITHM_HMAC_SHA1, getAppkey(), str);
    }

    @Override // anet.channel.strategy.dispatch.IAmdcSign
    public boolean useSecurityGuard() {
        return !this.f6705b.isSecOff();
    }
}
