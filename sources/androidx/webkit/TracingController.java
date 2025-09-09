package androidx.webkit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.webkit.internal.TracingControllerImpl;
import java.io.OutputStream;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public abstract class TracingController {

    private static class LAZY_HOLDER {

        /* renamed from: a, reason: collision with root package name */
        static final TracingController f6522a = new TracingControllerImpl();

        private LAZY_HOLDER() {
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public TracingController() {
    }

    @NonNull
    public static TracingController getInstance() {
        return LAZY_HOLDER.f6522a;
    }

    public abstract boolean isTracing();

    public abstract void start(@NonNull TracingConfig tracingConfig);

    public abstract boolean stop(@Nullable OutputStream outputStream, @NonNull Executor executor);
}
