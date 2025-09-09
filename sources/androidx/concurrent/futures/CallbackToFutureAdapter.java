package androidx.concurrent.futures;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.common.util.concurrent.ListenableFuture;
import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes.dex */
public final class CallbackToFutureAdapter {

    public static final class Completer<T> {

        /* renamed from: a, reason: collision with root package name */
        Object f2543a;
        private boolean attemptedSetting;

        /* renamed from: b, reason: collision with root package name */
        SafeFuture f2544b;
        private ResolvableFuture<Void> cancellationFuture = ResolvableFuture.create();

        Completer() {
        }

        private void setCompletedNormally() {
            this.f2543a = null;
            this.f2544b = null;
            this.cancellationFuture = null;
        }

        void a() {
            this.f2543a = null;
            this.f2544b = null;
            this.cancellationFuture.set(null);
        }

        public void addCancellationListener(@NonNull Runnable runnable, @NonNull Executor executor) {
            ResolvableFuture<Void> resolvableFuture = this.cancellationFuture;
            if (resolvableFuture != null) {
                resolvableFuture.addListener(runnable, executor);
            }
        }

        protected void finalize() {
            ResolvableFuture<Void> resolvableFuture;
            SafeFuture safeFuture = this.f2544b;
            if (safeFuture != null && !safeFuture.isDone()) {
                safeFuture.c(new FutureGarbageCollectedException("The completer object was garbage collected - this future would otherwise never complete. The tag was: " + this.f2543a));
            }
            if (this.attemptedSetting || (resolvableFuture = this.cancellationFuture) == null) {
                return;
            }
            resolvableFuture.set(null);
        }

        public boolean set(T t2) {
            this.attemptedSetting = true;
            SafeFuture safeFuture = this.f2544b;
            boolean z2 = safeFuture != null && safeFuture.b(t2);
            if (z2) {
                setCompletedNormally();
            }
            return z2;
        }

        public boolean setCancelled() {
            this.attemptedSetting = true;
            SafeFuture safeFuture = this.f2544b;
            boolean z2 = safeFuture != null && safeFuture.a(true);
            if (z2) {
                setCompletedNormally();
            }
            return z2;
        }

        public boolean setException(@NonNull Throwable th) {
            this.attemptedSetting = true;
            SafeFuture safeFuture = this.f2544b;
            boolean z2 = safeFuture != null && safeFuture.c(th);
            if (z2) {
                setCompletedNormally();
            }
            return z2;
        }
    }

    static final class FutureGarbageCollectedException extends Throwable {
        FutureGarbageCollectedException(String str) {
            super(str);
        }

        @Override // java.lang.Throwable
        public synchronized Throwable fillInStackTrace() {
            return this;
        }
    }

    public interface Resolver<T> {
        @Nullable
        Object attachCompleter(@NonNull Completer<T> completer) throws Exception;
    }

    private static final class SafeFuture<T> implements ListenableFuture<T> {

        /* renamed from: a, reason: collision with root package name */
        final WeakReference f2545a;
        private final AbstractResolvableFuture<T> delegate = new AbstractResolvableFuture<T>() { // from class: androidx.concurrent.futures.CallbackToFutureAdapter.SafeFuture.1
            @Override // androidx.concurrent.futures.AbstractResolvableFuture
            protected String g() {
                Completer completer = (Completer) SafeFuture.this.f2545a.get();
                if (completer == null) {
                    return "Completer object has been garbage collected, future will fail soon";
                }
                return "tag=[" + completer.f2543a + "]";
            }
        };

        SafeFuture(Completer completer) {
            this.f2545a = new WeakReference(completer);
        }

        boolean a(boolean z2) {
            return this.delegate.cancel(z2);
        }

        @Override // com.google.common.util.concurrent.ListenableFuture
        public void addListener(@NonNull Runnable runnable, @NonNull Executor executor) {
            this.delegate.addListener(runnable, executor);
        }

        boolean b(Object obj) {
            return this.delegate.set(obj);
        }

        boolean c(Throwable th) {
            return this.delegate.setException(th);
        }

        @Override // java.util.concurrent.Future
        public boolean cancel(boolean z2) {
            Completer completer = (Completer) this.f2545a.get();
            boolean zCancel = this.delegate.cancel(z2);
            if (zCancel && completer != null) {
                completer.a();
            }
            return zCancel;
        }

        @Override // java.util.concurrent.Future
        public T get() throws ExecutionException, InterruptedException {
            return this.delegate.get();
        }

        @Override // java.util.concurrent.Future
        public boolean isCancelled() {
            return this.delegate.isCancelled();
        }

        @Override // java.util.concurrent.Future
        public boolean isDone() {
            return this.delegate.isDone();
        }

        public String toString() {
            return this.delegate.toString();
        }

        @Override // java.util.concurrent.Future
        public T get(long j2, @NonNull TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
            return this.delegate.get(j2, timeUnit);
        }
    }

    private CallbackToFutureAdapter() {
    }

    @NonNull
    public static <T> ListenableFuture<T> getFuture(@NonNull Resolver<T> resolver) {
        Completer<T> completer = new Completer<>();
        SafeFuture safeFuture = new SafeFuture(completer);
        completer.f2544b = safeFuture;
        completer.f2543a = resolver.getClass();
        try {
            Object objAttachCompleter = resolver.attachCompleter(completer);
            if (objAttachCompleter != null) {
                completer.f2543a = objAttachCompleter;
            }
        } catch (Exception e2) {
            safeFuture.c(e2);
        }
        return safeFuture;
    }
}
