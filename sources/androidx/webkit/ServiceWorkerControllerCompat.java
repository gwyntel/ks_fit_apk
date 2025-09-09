package androidx.webkit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.webkit.internal.ServiceWorkerControllerImpl;

/* loaded from: classes2.dex */
public abstract class ServiceWorkerControllerCompat {

    private static class LAZY_HOLDER {

        /* renamed from: a, reason: collision with root package name */
        static final ServiceWorkerControllerCompat f6521a = new ServiceWorkerControllerImpl();

        private LAZY_HOLDER() {
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public ServiceWorkerControllerCompat() {
    }

    @NonNull
    public static ServiceWorkerControllerCompat getInstance() {
        return LAZY_HOLDER.f6521a;
    }

    @NonNull
    public abstract ServiceWorkerWebSettingsCompat getServiceWorkerWebSettings();

    public abstract void setServiceWorkerClient(@Nullable ServiceWorkerClientCompat serviceWorkerClientCompat);
}
