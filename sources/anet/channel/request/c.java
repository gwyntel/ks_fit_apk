package anet.channel.request;

import anet.channel.util.ALog;
import org.android.spdy.SpdyErrorException;
import org.android.spdy.SpdySession;

/* loaded from: classes2.dex */
public class c implements Cancelable {
    public static final c NULL = new c(null, 0, null);

    /* renamed from: a, reason: collision with root package name */
    private final int f6888a;

    /* renamed from: b, reason: collision with root package name */
    private final SpdySession f6889b;

    /* renamed from: c, reason: collision with root package name */
    private final String f6890c;

    public c(SpdySession spdySession, int i2, String str) {
        this.f6889b = spdySession;
        this.f6888a = i2;
        this.f6890c = str;
    }

    @Override // anet.channel.request.Cancelable
    public void cancel() {
        int i2;
        try {
            if (this.f6889b == null || (i2 = this.f6888a) == 0) {
                return;
            }
            ALog.i("awcn.TnetCancelable", "cancel tnet request", this.f6890c, "streamId", Integer.valueOf(i2));
            this.f6889b.streamReset(this.f6888a, 5);
        } catch (SpdyErrorException e2) {
            ALog.e("awcn.TnetCancelable", "request cancel failed.", this.f6890c, e2, "errorCode", Integer.valueOf(e2.SpdyErrorGetCode()));
        }
    }
}
