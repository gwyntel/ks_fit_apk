package anetwork.channel.unified;

import anetwork.channel.Response;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes2.dex */
class d implements Future<Response> {

    /* renamed from: a, reason: collision with root package name */
    private k f7251a;

    /* renamed from: b, reason: collision with root package name */
    private boolean f7252b;

    public d(k kVar) {
        this.f7251a = kVar;
    }

    @Override // java.util.concurrent.Future
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public Response get() throws ExecutionException, InterruptedException {
        throw new RuntimeException("NOT SUPPORT!");
    }

    public Response b() throws ExecutionException, InterruptedException, TimeoutException {
        throw new RuntimeException("NOT SUPPORT!");
    }

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean z2) {
        if (!this.f7252b) {
            this.f7251a.b();
            this.f7252b = true;
        }
        return true;
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        return this.f7252b;
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        throw new RuntimeException("NOT SUPPORT!");
    }

    @Override // java.util.concurrent.Future
    public /* synthetic */ Response get(long j2, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        return b();
    }
}
