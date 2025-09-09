package anetwork.channel.unified;

import anetwork.channel.interceptor.Callback;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
class j {

    /* renamed from: a, reason: collision with root package name */
    public final anetwork.channel.entity.g f7286a;

    /* renamed from: b, reason: collision with root package name */
    public Callback f7287b;

    /* renamed from: c, reason: collision with root package name */
    public final String f7288c;

    /* renamed from: d, reason: collision with root package name */
    public volatile AtomicBoolean f7289d = new AtomicBoolean();

    /* renamed from: e, reason: collision with root package name */
    public volatile IUnifiedTask f7290e = null;

    /* renamed from: f, reason: collision with root package name */
    public volatile Future f7291f = null;

    public j(anetwork.channel.entity.g gVar, Callback callback) {
        this.f7286a = gVar;
        this.f7288c = gVar.f7233e;
        this.f7287b = callback;
    }

    public void a() {
        Future future = this.f7291f;
        if (future != null) {
            future.cancel(true);
            this.f7291f = null;
        }
    }

    public void b() {
        if (this.f7290e != null) {
            this.f7290e.cancel();
            this.f7290e = null;
        }
    }
}
