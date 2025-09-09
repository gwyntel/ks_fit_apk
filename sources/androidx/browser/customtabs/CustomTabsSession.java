package androidx.browser.customtabs;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.support.customtabs.ICustomTabsCallback;
import android.support.customtabs.ICustomTabsService;
import android.support.customtabs.IEngagementSignalsCallback;
import android.widget.RemoteViews;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.browser.customtabs.CustomTabsSessionToken;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final class CustomTabsSession {
    private static final String TAG = "CustomTabsSession";
    private final ICustomTabsCallback mCallback;
    private final ComponentName mComponentName;

    @Nullable
    private final PendingIntent mId;
    private final Object mLock = new Object();
    private final ICustomTabsService mService;

    /* renamed from: androidx.browser.customtabs.CustomTabsSession$1, reason: invalid class name */
    class AnonymousClass1 extends IEngagementSignalsCallback.Stub {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ EngagementSignalsCallback f2442a;
        private final Handler mHandler = new Handler(Looper.getMainLooper());

        AnonymousClass1(EngagementSignalsCallback engagementSignalsCallback) {
            this.f2442a = engagementSignalsCallback;
        }

        @Override // android.support.customtabs.IEngagementSignalsCallback
        public void onGreatestScrollPercentageIncreased(final int i2, final Bundle bundle) {
            Handler handler = this.mHandler;
            final EngagementSignalsCallback engagementSignalsCallback = this.f2442a;
            handler.post(new Runnable() { // from class: androidx.browser.customtabs.c
                @Override // java.lang.Runnable
                public final void run() {
                    engagementSignalsCallback.onGreatestScrollPercentageIncreased(i2, bundle);
                }
            });
        }

        @Override // android.support.customtabs.IEngagementSignalsCallback
        public void onSessionEnded(final boolean z2, final Bundle bundle) {
            Handler handler = this.mHandler;
            final EngagementSignalsCallback engagementSignalsCallback = this.f2442a;
            handler.post(new Runnable() { // from class: androidx.browser.customtabs.b
                @Override // java.lang.Runnable
                public final void run() {
                    engagementSignalsCallback.onSessionEnded(z2, bundle);
                }
            });
        }

        @Override // android.support.customtabs.IEngagementSignalsCallback
        public void onVerticalScrollEvent(final boolean z2, final Bundle bundle) {
            Handler handler = this.mHandler;
            final EngagementSignalsCallback engagementSignalsCallback = this.f2442a;
            handler.post(new Runnable() { // from class: androidx.browser.customtabs.d
                @Override // java.lang.Runnable
                public final void run() {
                    engagementSignalsCallback.onVerticalScrollEvent(z2, bundle);
                }
            });
        }
    }

    /* renamed from: androidx.browser.customtabs.CustomTabsSession$2, reason: invalid class name */
    class AnonymousClass2 extends IEngagementSignalsCallback.Stub {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Executor f2444a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ EngagementSignalsCallback f2445b;
        private final Executor mExecutor;

        AnonymousClass2(Executor executor, EngagementSignalsCallback engagementSignalsCallback) {
            this.f2444a = executor;
            this.f2445b = engagementSignalsCallback;
            this.mExecutor = executor;
        }

        @Override // android.support.customtabs.IEngagementSignalsCallback
        public void onGreatestScrollPercentageIncreased(final int i2, final Bundle bundle) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.mExecutor;
                final EngagementSignalsCallback engagementSignalsCallback = this.f2445b;
                executor.execute(new Runnable() { // from class: androidx.browser.customtabs.f
                    @Override // java.lang.Runnable
                    public final void run() {
                        engagementSignalsCallback.onGreatestScrollPercentageIncreased(i2, bundle);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        @Override // android.support.customtabs.IEngagementSignalsCallback
        public void onSessionEnded(final boolean z2, final Bundle bundle) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.mExecutor;
                final EngagementSignalsCallback engagementSignalsCallback = this.f2445b;
                executor.execute(new Runnable() { // from class: androidx.browser.customtabs.g
                    @Override // java.lang.Runnable
                    public final void run() {
                        engagementSignalsCallback.onSessionEnded(z2, bundle);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        @Override // android.support.customtabs.IEngagementSignalsCallback
        public void onVerticalScrollEvent(final boolean z2, final Bundle bundle) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.mExecutor;
                final EngagementSignalsCallback engagementSignalsCallback = this.f2445b;
                executor.execute(new Runnable() { // from class: androidx.browser.customtabs.e
                    @Override // java.lang.Runnable
                    public final void run() {
                        engagementSignalsCallback.onVerticalScrollEvent(z2, bundle);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }
    }

    static class MockSession extends ICustomTabsService.Stub {
        MockSession() {
        }

        @Override // android.support.customtabs.ICustomTabsService
        public Bundle extraCommand(String str, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // android.support.customtabs.ICustomTabsService
        public boolean isEngagementSignalsApiAvailable(ICustomTabsCallback iCustomTabsCallback, Bundle bundle) throws RemoteException {
            return false;
        }

        @Override // android.support.customtabs.ICustomTabsService
        public boolean mayLaunchUrl(ICustomTabsCallback iCustomTabsCallback, Uri uri, Bundle bundle, List<Bundle> list) throws RemoteException {
            return false;
        }

        @Override // android.support.customtabs.ICustomTabsService
        public boolean newSession(ICustomTabsCallback iCustomTabsCallback) throws RemoteException {
            return false;
        }

        @Override // android.support.customtabs.ICustomTabsService
        public boolean newSessionWithExtras(ICustomTabsCallback iCustomTabsCallback, Bundle bundle) throws RemoteException {
            return false;
        }

        @Override // android.support.customtabs.ICustomTabsService
        public int postMessage(ICustomTabsCallback iCustomTabsCallback, String str, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // android.support.customtabs.ICustomTabsService
        public boolean receiveFile(ICustomTabsCallback iCustomTabsCallback, Uri uri, int i2, Bundle bundle) throws RemoteException {
            return false;
        }

        @Override // android.support.customtabs.ICustomTabsService
        public boolean requestPostMessageChannel(ICustomTabsCallback iCustomTabsCallback, Uri uri) throws RemoteException {
            return false;
        }

        @Override // android.support.customtabs.ICustomTabsService
        public boolean requestPostMessageChannelWithExtras(ICustomTabsCallback iCustomTabsCallback, Uri uri, Bundle bundle) throws RemoteException {
            return false;
        }

        @Override // android.support.customtabs.ICustomTabsService
        public boolean setEngagementSignalsCallback(ICustomTabsCallback iCustomTabsCallback, IBinder iBinder, Bundle bundle) throws RemoteException {
            return false;
        }

        @Override // android.support.customtabs.ICustomTabsService
        public boolean updateVisuals(ICustomTabsCallback iCustomTabsCallback, Bundle bundle) throws RemoteException {
            return false;
        }

        @Override // android.support.customtabs.ICustomTabsService
        public boolean validateRelationship(ICustomTabsCallback iCustomTabsCallback, int i2, Uri uri, Bundle bundle) throws RemoteException {
            return false;
        }

        @Override // android.support.customtabs.ICustomTabsService
        public boolean warmup(long j2) throws RemoteException {
            return false;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static class PendingSession {

        @Nullable
        private final CustomTabsCallback mCallback;

        @Nullable
        private final PendingIntent mId;

        PendingSession(CustomTabsCallback customTabsCallback, PendingIntent pendingIntent) {
            this.mCallback = customTabsCallback;
            this.mId = pendingIntent;
        }

        CustomTabsCallback a() {
            return this.mCallback;
        }

        PendingIntent b() {
            return this.mId;
        }
    }

    CustomTabsSession(ICustomTabsService iCustomTabsService, ICustomTabsCallback iCustomTabsCallback, ComponentName componentName, PendingIntent pendingIntent) {
        this.mService = iCustomTabsService;
        this.mCallback = iCustomTabsCallback;
        this.mComponentName = componentName;
        this.mId = pendingIntent;
    }

    private void addIdToBundle(Bundle bundle) {
        PendingIntent pendingIntent = this.mId;
        if (pendingIntent != null) {
            bundle.putParcelable(CustomTabsIntent.EXTRA_SESSION_ID, pendingIntent);
        }
    }

    private Bundle createBundleWithId(@Nullable Bundle bundle) {
        Bundle bundle2 = new Bundle();
        if (bundle != null) {
            bundle2.putAll(bundle);
        }
        addIdToBundle(bundle2);
        return bundle2;
    }

    private IEngagementSignalsCallback.Stub createEngagementSignalsCallbackWrapper(@NonNull EngagementSignalsCallback engagementSignalsCallback) {
        return new AnonymousClass1(engagementSignalsCallback);
    }

    @NonNull
    @VisibleForTesting
    public static CustomTabsSession createMockSessionForTesting(@NonNull ComponentName componentName) {
        return new CustomTabsSession(new MockSession(), new CustomTabsSessionToken.MockCallback(), componentName, null);
    }

    @Nullable
    private Bundle createPostMessageExtraBundle(@Nullable Uri uri) {
        Bundle bundle = new Bundle();
        if (uri != null) {
            bundle.putParcelable("target_origin", uri);
        }
        if (this.mId != null) {
            addIdToBundle(bundle);
        }
        if (bundle.isEmpty()) {
            return null;
        }
        return bundle;
    }

    IBinder a() {
        return this.mCallback.asBinder();
    }

    ComponentName b() {
        return this.mComponentName;
    }

    PendingIntent c() {
        return this.mId;
    }

    public boolean isEngagementSignalsApiAvailable(@NonNull Bundle bundle) throws RemoteException {
        try {
            return this.mService.isEngagementSignalsApiAvailable(this.mCallback, createBundleWithId(bundle));
        } catch (SecurityException e2) {
            throw new UnsupportedOperationException("This method isn't supported by the Custom Tabs implementation.", e2);
        }
    }

    public boolean mayLaunchUrl(@Nullable Uri uri, @Nullable Bundle bundle, @Nullable List<Bundle> list) {
        try {
            return this.mService.mayLaunchUrl(this.mCallback, uri, createBundleWithId(bundle), list);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public int postMessage(@NonNull String str, @Nullable Bundle bundle) {
        int iPostMessage;
        Bundle bundleCreateBundleWithId = createBundleWithId(bundle);
        synchronized (this.mLock) {
            try {
                try {
                    iPostMessage = this.mService.postMessage(this.mCallback, str, bundleCreateBundleWithId);
                } catch (RemoteException unused) {
                    return -2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return iPostMessage;
    }

    public boolean receiveFile(@NonNull Uri uri, int i2, @Nullable Bundle bundle) {
        try {
            return this.mService.receiveFile(this.mCallback, uri, i2, createBundleWithId(bundle));
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean requestPostMessageChannel(@NonNull Uri uri) {
        return requestPostMessageChannel(uri, null, new Bundle());
    }

    public boolean setActionButton(@NonNull Bitmap bitmap, @NonNull String str) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(CustomTabsIntent.KEY_ICON, bitmap);
        bundle.putString(CustomTabsIntent.KEY_DESCRIPTION, str);
        Bundle bundle2 = new Bundle();
        bundle2.putBundle(CustomTabsIntent.EXTRA_ACTION_BUTTON_BUNDLE, bundle);
        addIdToBundle(bundle);
        try {
            return this.mService.updateVisuals(this.mCallback, bundle2);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean setEngagementSignalsCallback(@NonNull EngagementSignalsCallback engagementSignalsCallback, @NonNull Bundle bundle) throws RemoteException {
        try {
            return this.mService.setEngagementSignalsCallback(this.mCallback, createEngagementSignalsCallbackWrapper(engagementSignalsCallback).asBinder(), createBundleWithId(bundle));
        } catch (SecurityException e2) {
            throw new UnsupportedOperationException("This method isn't supported by the Custom Tabs implementation.", e2);
        }
    }

    public boolean setSecondaryToolbarSwipeUpGesture(@Nullable PendingIntent pendingIntent) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(CustomTabsIntent.EXTRA_SECONDARY_TOOLBAR_SWIPE_UP_GESTURE, pendingIntent);
        addIdToBundle(bundle);
        try {
            return this.mService.updateVisuals(this.mCallback, bundle);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean setSecondaryToolbarViews(@Nullable RemoteViews remoteViews, @Nullable int[] iArr, @Nullable PendingIntent pendingIntent) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(CustomTabsIntent.EXTRA_REMOTEVIEWS, remoteViews);
        bundle.putIntArray(CustomTabsIntent.EXTRA_REMOTEVIEWS_VIEW_IDS, iArr);
        bundle.putParcelable(CustomTabsIntent.EXTRA_REMOTEVIEWS_PENDINGINTENT, pendingIntent);
        addIdToBundle(bundle);
        try {
            return this.mService.updateVisuals(this.mCallback, bundle);
        } catch (RemoteException unused) {
            return false;
        }
    }

    @Deprecated
    public boolean setToolbarItem(int i2, @NonNull Bitmap bitmap, @NonNull String str) {
        Bundle bundle = new Bundle();
        bundle.putInt(CustomTabsIntent.KEY_ID, i2);
        bundle.putParcelable(CustomTabsIntent.KEY_ICON, bitmap);
        bundle.putString(CustomTabsIntent.KEY_DESCRIPTION, str);
        Bundle bundle2 = new Bundle();
        bundle2.putBundle(CustomTabsIntent.EXTRA_ACTION_BUTTON_BUNDLE, bundle);
        addIdToBundle(bundle2);
        try {
            return this.mService.updateVisuals(this.mCallback, bundle2);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean validateRelationship(int i2, @NonNull Uri uri, @Nullable Bundle bundle) {
        if (i2 >= 1 && i2 <= 2) {
            try {
                return this.mService.validateRelationship(this.mCallback, i2, uri, createBundleWithId(bundle));
            } catch (RemoteException unused) {
            }
        }
        return false;
    }

    private IEngagementSignalsCallback.Stub createEngagementSignalsCallbackWrapper(@NonNull EngagementSignalsCallback engagementSignalsCallback, @NonNull Executor executor) {
        return new AnonymousClass2(executor, engagementSignalsCallback);
    }

    public boolean requestPostMessageChannel(@NonNull Uri uri, @Nullable Uri uri2, @NonNull Bundle bundle) {
        try {
            Bundle bundleCreatePostMessageExtraBundle = createPostMessageExtraBundle(uri2);
            if (bundleCreatePostMessageExtraBundle == null) {
                return this.mService.requestPostMessageChannel(this.mCallback, uri);
            }
            bundle.putAll(bundleCreatePostMessageExtraBundle);
            return this.mService.requestPostMessageChannelWithExtras(this.mCallback, uri, bundle);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean setEngagementSignalsCallback(@NonNull Executor executor, @NonNull EngagementSignalsCallback engagementSignalsCallback, @NonNull Bundle bundle) throws RemoteException {
        try {
            return this.mService.setEngagementSignalsCallback(this.mCallback, createEngagementSignalsCallbackWrapper(engagementSignalsCallback, executor).asBinder(), createBundleWithId(bundle));
        } catch (SecurityException e2) {
            throw new UnsupportedOperationException("This method isn't supported by the Custom Tabs implementation.", e2);
        }
    }
}
