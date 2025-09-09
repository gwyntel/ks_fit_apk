package a.a.a.a.b.a;

import android.content.Context;
import android.util.Pair;
import meshprovisioner.configuration.bean.CfgMsgModelSubscriptionStatus;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class u implements I<Object> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ Context f1291a;

    public u(Context context) {
        this.f1291a = context;
    }

    @Override // a.a.a.a.b.a.I
    public Pair<Integer, ?> parseResponse(Object obj) {
        if (obj instanceof CfgMsgModelSubscriptionStatus) {
            return CfgMsgModelSubscriptionStatus.parseStatus(this.f1291a, ((CfgMsgModelSubscriptionStatus) obj).getStatus());
        }
        if (obj instanceof byte[]) {
            return CfgMsgModelSubscriptionStatus.parseStatus(this.f1291a, ((byte[]) obj)[0]);
        }
        return null;
    }
}
