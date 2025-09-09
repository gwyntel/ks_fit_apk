package anetwork.channel.unified;

import android.text.TextUtils;
import anet.channel.request.Cancelable;
import anet.channel.request.Request;
import anet.channel.util.StringUtils;
import anetwork.channel.cookie.CookieManager;

/* loaded from: classes2.dex */
public class b implements IUnifiedTask {

    /* renamed from: c, reason: collision with root package name */
    private j f7246c;

    /* renamed from: f, reason: collision with root package name */
    private Request f7249f;

    /* renamed from: b, reason: collision with root package name */
    private volatile boolean f7245b = false;

    /* renamed from: a, reason: collision with root package name */
    volatile Cancelable f7244a = null;

    /* renamed from: d, reason: collision with root package name */
    private int f7247d = 0;

    /* renamed from: e, reason: collision with root package name */
    private int f7248e = 0;

    public b(j jVar) {
        this.f7246c = jVar;
        this.f7249f = jVar.f7286a.a();
    }

    static /* synthetic */ int b(b bVar) {
        int i2 = bVar.f7248e;
        bVar.f7248e = i2 + 1;
        return i2;
    }

    @Override // anet.channel.request.Cancelable
    public void cancel() {
        this.f7245b = true;
        if (this.f7244a != null) {
            this.f7244a.cancel();
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.f7245b) {
            return;
        }
        if (this.f7246c.f7286a.i()) {
            String cookie = CookieManager.getCookie(this.f7246c.f7286a.g());
            if (!TextUtils.isEmpty(cookie)) {
                Request.Builder builderNewBuilder = this.f7249f.newBuilder();
                String str = this.f7249f.getHeaders().get("Cookie");
                if (!TextUtils.isEmpty(str)) {
                    cookie = StringUtils.concatString(str, "; ", cookie);
                }
                builderNewBuilder.addHeader("Cookie", cookie);
                this.f7249f = builderNewBuilder.build();
            }
        }
        this.f7249f.f6850a.degraded = 2;
        this.f7249f.f6850a.sendBeforeTime = System.currentTimeMillis() - this.f7249f.f6850a.reqStart;
        anet.channel.session.b.a(this.f7249f, new c(this));
    }
}
