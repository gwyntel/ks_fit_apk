package anet.channel.request;

import anet.channel.util.ALog;
import java.util.concurrent.Future;

/* loaded from: classes2.dex */
public class b implements Cancelable {
    public static final b NULL = new b(null, null);

    /* renamed from: a, reason: collision with root package name */
    private final Future<?> f6886a;

    /* renamed from: b, reason: collision with root package name */
    private final String f6887b;

    public b(Future<?> future, String str) {
        this.f6886a = future;
        this.f6887b = str;
    }

    @Override // anet.channel.request.Cancelable
    public void cancel() {
        if (this.f6886a != null) {
            ALog.i("awcn.FutureCancelable", "cancel request", this.f6887b, new Object[0]);
            this.f6886a.cancel(true);
        }
    }
}
