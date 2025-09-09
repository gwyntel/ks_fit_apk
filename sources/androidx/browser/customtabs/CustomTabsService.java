package androidx.browser.customtabs;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.customtabs.ICustomTabsCallback;
import android.support.customtabs.ICustomTabsService;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.collection.SimpleArrayMap;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.NoSuchElementException;

/* loaded from: classes.dex */
public abstract class CustomTabsService extends Service {
    public static final String ACTION_CUSTOM_TABS_CONNECTION = "android.support.customtabs.action.CustomTabsService";
    public static final String CATEGORY_COLOR_SCHEME_CUSTOMIZATION = "androidx.browser.customtabs.category.ColorSchemeCustomization";
    public static final String CATEGORY_NAVBAR_COLOR_CUSTOMIZATION = "androidx.browser.customtabs.category.NavBarColorCustomization";
    public static final String CATEGORY_TRUSTED_WEB_ACTIVITY_IMMERSIVE_MODE = "androidx.browser.trusted.category.ImmersiveMode";
    public static final String CATEGORY_WEB_SHARE_TARGET_V2 = "androidx.browser.trusted.category.WebShareTargetV2";
    public static final int FILE_PURPOSE_TRUSTED_WEB_ACTIVITY_SPLASH_IMAGE = 1;
    public static final String KEY_SUCCESS = "androidx.browser.customtabs.SUCCESS";
    public static final String KEY_URL = "android.support.customtabs.otherurls.URL";
    public static final int RELATION_HANDLE_ALL_URLS = 2;
    public static final int RELATION_USE_AS_ORIGIN = 1;
    public static final int RESULT_FAILURE_DISALLOWED = -1;
    public static final int RESULT_FAILURE_MESSAGING_ERROR = -3;
    public static final int RESULT_FAILURE_REMOTE_ERROR = -2;
    public static final int RESULT_SUCCESS = 0;
    private static final String TAG = "CustomTabsService";
    public static final String TRUSTED_WEB_ACTIVITY_CATEGORY = "androidx.browser.trusted.category.TrustedWebActivities";

    /* renamed from: a, reason: collision with root package name */
    final SimpleArrayMap f2439a = new SimpleArrayMap();
    private ICustomTabsService.Stub mBinder = new AnonymousClass1();

    /* renamed from: androidx.browser.customtabs.CustomTabsService$1, reason: invalid class name */
    class AnonymousClass1 extends ICustomTabsService.Stub {
        AnonymousClass1() {
        }

        @Nullable
        private PendingIntent getSessionIdFromBundle(@Nullable Bundle bundle) {
            if (bundle == null) {
                return null;
            }
            PendingIntent pendingIntent = (PendingIntent) bundle.getParcelable(CustomTabsIntent.EXTRA_SESSION_ID);
            bundle.remove(CustomTabsIntent.EXTRA_SESSION_ID);
            return pendingIntent;
        }

        @Nullable
        private Uri getTargetOriginFromBundle(@Nullable Bundle bundle) {
            if (bundle == null) {
                return null;
            }
            return Build.VERSION.SDK_INT >= 33 ? (Uri) Api33Impl.a(bundle, "target_origin", Uri.class) : (Uri) bundle.getParcelable("target_origin");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$newSessionInternal$0(CustomTabsSessionToken customTabsSessionToken) {
            CustomTabsService.this.a(customTabsSessionToken);
        }

        private boolean newSessionInternal(@NonNull ICustomTabsCallback iCustomTabsCallback, @Nullable PendingIntent pendingIntent) {
            final CustomTabsSessionToken customTabsSessionToken = new CustomTabsSessionToken(iCustomTabsCallback, pendingIntent);
            try {
                IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() { // from class: androidx.browser.customtabs.a
                    @Override // android.os.IBinder.DeathRecipient
                    public final void binderDied() {
                        this.f2450a.lambda$newSessionInternal$0(customTabsSessionToken);
                    }
                };
                synchronized (CustomTabsService.this.f2439a) {
                    iCustomTabsCallback.asBinder().linkToDeath(deathRecipient, 0);
                    CustomTabsService.this.f2439a.put(iCustomTabsCallback.asBinder(), deathRecipient);
                }
                return CustomTabsService.this.e(customTabsSessionToken);
            } catch (RemoteException unused) {
                return false;
            }
        }

        @Override // android.support.customtabs.ICustomTabsService
        public Bundle extraCommand(@NonNull String str, @Nullable Bundle bundle) {
            return CustomTabsService.this.b(str, bundle);
        }

        @Override // android.support.customtabs.ICustomTabsService
        public boolean isEngagementSignalsApiAvailable(ICustomTabsCallback iCustomTabsCallback, @NonNull Bundle bundle) {
            return CustomTabsService.this.c(new CustomTabsSessionToken(iCustomTabsCallback, getSessionIdFromBundle(bundle)), bundle);
        }

        @Override // android.support.customtabs.ICustomTabsService
        public boolean mayLaunchUrl(@Nullable ICustomTabsCallback iCustomTabsCallback, @Nullable Uri uri, @Nullable Bundle bundle, @Nullable List<Bundle> list) {
            return CustomTabsService.this.d(new CustomTabsSessionToken(iCustomTabsCallback, getSessionIdFromBundle(bundle)), uri, bundle, list);
        }

        @Override // android.support.customtabs.ICustomTabsService
        public boolean newSession(@NonNull ICustomTabsCallback iCustomTabsCallback) {
            return newSessionInternal(iCustomTabsCallback, null);
        }

        @Override // android.support.customtabs.ICustomTabsService
        public boolean newSessionWithExtras(@NonNull ICustomTabsCallback iCustomTabsCallback, @Nullable Bundle bundle) {
            return newSessionInternal(iCustomTabsCallback, getSessionIdFromBundle(bundle));
        }

        @Override // android.support.customtabs.ICustomTabsService
        public int postMessage(@NonNull ICustomTabsCallback iCustomTabsCallback, @NonNull String str, @Nullable Bundle bundle) {
            return CustomTabsService.this.f(new CustomTabsSessionToken(iCustomTabsCallback, getSessionIdFromBundle(bundle)), str, bundle);
        }

        @Override // android.support.customtabs.ICustomTabsService
        public boolean receiveFile(@NonNull ICustomTabsCallback iCustomTabsCallback, @NonNull Uri uri, int i2, @Nullable Bundle bundle) {
            return CustomTabsService.this.g(new CustomTabsSessionToken(iCustomTabsCallback, getSessionIdFromBundle(bundle)), uri, i2, bundle);
        }

        @Override // android.support.customtabs.ICustomTabsService
        public boolean requestPostMessageChannel(@NonNull ICustomTabsCallback iCustomTabsCallback, @NonNull Uri uri) {
            return CustomTabsService.this.i(new CustomTabsSessionToken(iCustomTabsCallback, null), uri, null, new Bundle());
        }

        @Override // android.support.customtabs.ICustomTabsService
        public boolean requestPostMessageChannelWithExtras(@NonNull ICustomTabsCallback iCustomTabsCallback, @NonNull Uri uri, @NonNull Bundle bundle) {
            return CustomTabsService.this.i(new CustomTabsSessionToken(iCustomTabsCallback, getSessionIdFromBundle(bundle)), uri, getTargetOriginFromBundle(bundle), bundle);
        }

        @Override // android.support.customtabs.ICustomTabsService
        public boolean setEngagementSignalsCallback(@NonNull ICustomTabsCallback iCustomTabsCallback, @NonNull IBinder iBinder, @NonNull Bundle bundle) {
            return CustomTabsService.this.j(new CustomTabsSessionToken(iCustomTabsCallback, getSessionIdFromBundle(bundle)), EngagementSignalsCallbackRemote.a(iBinder), bundle);
        }

        @Override // android.support.customtabs.ICustomTabsService
        public boolean updateVisuals(@NonNull ICustomTabsCallback iCustomTabsCallback, @Nullable Bundle bundle) {
            return CustomTabsService.this.k(new CustomTabsSessionToken(iCustomTabsCallback, getSessionIdFromBundle(bundle)), bundle);
        }

        @Override // android.support.customtabs.ICustomTabsService
        public boolean validateRelationship(@NonNull ICustomTabsCallback iCustomTabsCallback, int i2, @NonNull Uri uri, @Nullable Bundle bundle) {
            return CustomTabsService.this.l(new CustomTabsSessionToken(iCustomTabsCallback, getSessionIdFromBundle(bundle)), i2, uri, bundle);
        }

        @Override // android.support.customtabs.ICustomTabsService
        public boolean warmup(long j2) {
            return CustomTabsService.this.m(j2);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface FilePurpose {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Relation {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Result {
    }

    protected boolean a(CustomTabsSessionToken customTabsSessionToken) {
        try {
            synchronized (this.f2439a) {
                try {
                    IBinder iBinderA = customTabsSessionToken.a();
                    if (iBinderA == null) {
                        return false;
                    }
                    iBinderA.unlinkToDeath((IBinder.DeathRecipient) this.f2439a.get(iBinderA), 0);
                    this.f2439a.remove(iBinderA);
                    return true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (NoSuchElementException unused) {
            return false;
        }
    }

    protected abstract Bundle b(String str, Bundle bundle);

    protected boolean c(CustomTabsSessionToken customTabsSessionToken, Bundle bundle) {
        return false;
    }

    protected abstract boolean d(CustomTabsSessionToken customTabsSessionToken, Uri uri, Bundle bundle, List list);

    protected abstract boolean e(CustomTabsSessionToken customTabsSessionToken);

    protected abstract int f(CustomTabsSessionToken customTabsSessionToken, String str, Bundle bundle);

    protected abstract boolean g(CustomTabsSessionToken customTabsSessionToken, Uri uri, int i2, Bundle bundle);

    protected abstract boolean h(CustomTabsSessionToken customTabsSessionToken, Uri uri);

    protected boolean i(CustomTabsSessionToken customTabsSessionToken, Uri uri, Uri uri2, Bundle bundle) {
        return h(customTabsSessionToken, uri);
    }

    protected boolean j(CustomTabsSessionToken customTabsSessionToken, EngagementSignalsCallback engagementSignalsCallback, Bundle bundle) {
        return false;
    }

    protected abstract boolean k(CustomTabsSessionToken customTabsSessionToken, Bundle bundle);

    protected abstract boolean l(CustomTabsSessionToken customTabsSessionToken, int i2, Uri uri, Bundle bundle);

    protected abstract boolean m(long j2);

    @Override // android.app.Service
    @NonNull
    public IBinder onBind(@Nullable Intent intent) {
        return this.mBinder;
    }
}
