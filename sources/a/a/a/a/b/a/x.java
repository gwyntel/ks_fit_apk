package a.a.a.a.b.a;

import android.content.Context;
import android.util.Pair;
import meshprovisioner.configuration.bean.SceneRegisterStatus;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class x implements I<Object> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ Context f1294a;

    public x(Context context) {
        this.f1294a = context;
    }

    @Override // a.a.a.a.b.a.I
    public Pair<Integer, ?> parseResponse(Object obj) {
        return obj instanceof SceneRegisterStatus ? ((SceneRegisterStatus) obj).parseStatus(this.f1294a) : new Pair<>(-30, "internal error");
    }
}
