package androidx.browser.customtabs;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.customtabs.ICustomTabsCallback;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

/* loaded from: classes.dex */
public class CustomTabsSessionToken {
    private static final String TAG = "CustomTabsSessionToken";

    /* renamed from: a, reason: collision with root package name */
    final ICustomTabsCallback f2447a;

    @Nullable
    private final CustomTabsCallback mCallback;

    @Nullable
    private final PendingIntent mSessionId;

    static class MockCallback extends ICustomTabsCallback.Stub {
        MockCallback() {
        }

        @Override // android.support.customtabs.ICustomTabsCallback.Stub, android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.support.customtabs.ICustomTabsCallback
        public void extraCallback(String str, Bundle bundle) {
        }

        @Override // android.support.customtabs.ICustomTabsCallback
        public Bundle extraCallbackWithResult(String str, Bundle bundle) {
            return null;
        }

        @Override // android.support.customtabs.ICustomTabsCallback
        public void onActivityLayout(int i2, int i3, int i4, int i5, int i6, @NonNull Bundle bundle) {
        }

        @Override // android.support.customtabs.ICustomTabsCallback
        public void onActivityResized(int i2, int i3, Bundle bundle) {
        }

        @Override // android.support.customtabs.ICustomTabsCallback
        public void onMessageChannelReady(Bundle bundle) {
        }

        @Override // android.support.customtabs.ICustomTabsCallback
        public void onMinimized(@NonNull Bundle bundle) {
        }

        @Override // android.support.customtabs.ICustomTabsCallback
        public void onNavigationEvent(int i2, Bundle bundle) {
        }

        @Override // android.support.customtabs.ICustomTabsCallback
        public void onPostMessage(String str, Bundle bundle) {
        }

        @Override // android.support.customtabs.ICustomTabsCallback
        public void onRelationshipValidationResult(int i2, Uri uri, boolean z2, Bundle bundle) {
        }

        @Override // android.support.customtabs.ICustomTabsCallback
        public void onUnminimized(@NonNull Bundle bundle) {
        }

        @Override // android.support.customtabs.ICustomTabsCallback
        public void onWarmupCompleted(Bundle bundle) {
        }
    }

    CustomTabsSessionToken(ICustomTabsCallback iCustomTabsCallback, PendingIntent pendingIntent) {
        if (iCustomTabsCallback == null && pendingIntent == null) {
            throw new IllegalStateException("CustomTabsSessionToken must have either a session id or a callback (or both).");
        }
        this.f2447a = iCustomTabsCallback;
        this.mSessionId = pendingIntent;
        this.mCallback = iCustomTabsCallback == null ? null : new CustomTabsCallback() { // from class: androidx.browser.customtabs.CustomTabsSessionToken.1
            @Override // androidx.browser.customtabs.CustomTabsCallback
            public void extraCallback(@NonNull String str, @Nullable Bundle bundle) {
                try {
                    CustomTabsSessionToken.this.f2447a.extraCallback(str, bundle);
                } catch (RemoteException unused) {
                    Log.e(CustomTabsSessionToken.TAG, "RemoteException during ICustomTabsCallback transaction");
                }
            }

            @Override // androidx.browser.customtabs.CustomTabsCallback
            @NonNull
            public Bundle extraCallbackWithResult(@NonNull String str, @Nullable Bundle bundle) {
                try {
                    return CustomTabsSessionToken.this.f2447a.extraCallbackWithResult(str, bundle);
                } catch (RemoteException unused) {
                    Log.e(CustomTabsSessionToken.TAG, "RemoteException during ICustomTabsCallback transaction");
                    return null;
                }
            }

            @Override // androidx.browser.customtabs.CustomTabsCallback
            public void onActivityLayout(int i2, int i3, int i4, int i5, int i6, @NonNull Bundle bundle) {
                try {
                    CustomTabsSessionToken.this.f2447a.onActivityLayout(i2, i3, i4, i5, i6, bundle);
                } catch (RemoteException unused) {
                    Log.e(CustomTabsSessionToken.TAG, "RemoteException during ICustomTabsCallback transaction");
                }
            }

            @Override // androidx.browser.customtabs.CustomTabsCallback
            public void onActivityResized(int i2, int i3, @NonNull Bundle bundle) {
                try {
                    CustomTabsSessionToken.this.f2447a.onActivityResized(i2, i3, bundle);
                } catch (RemoteException unused) {
                    Log.e(CustomTabsSessionToken.TAG, "RemoteException during ICustomTabsCallback transaction");
                }
            }

            @Override // androidx.browser.customtabs.CustomTabsCallback
            public void onMessageChannelReady(@Nullable Bundle bundle) {
                try {
                    CustomTabsSessionToken.this.f2447a.onMessageChannelReady(bundle);
                } catch (RemoteException unused) {
                    Log.e(CustomTabsSessionToken.TAG, "RemoteException during ICustomTabsCallback transaction");
                }
            }

            @Override // androidx.browser.customtabs.CustomTabsCallback
            public void onMinimized(@NonNull Bundle bundle) {
                try {
                    CustomTabsSessionToken.this.f2447a.onMinimized(bundle);
                } catch (RemoteException unused) {
                    Log.e(CustomTabsSessionToken.TAG, "RemoteException during ICustomTabsCallback transaction");
                }
            }

            @Override // androidx.browser.customtabs.CustomTabsCallback
            public void onNavigationEvent(int i2, @Nullable Bundle bundle) {
                try {
                    CustomTabsSessionToken.this.f2447a.onNavigationEvent(i2, bundle);
                } catch (RemoteException unused) {
                    Log.e(CustomTabsSessionToken.TAG, "RemoteException during ICustomTabsCallback transaction");
                }
            }

            @Override // androidx.browser.customtabs.CustomTabsCallback
            public void onPostMessage(@NonNull String str, @Nullable Bundle bundle) {
                try {
                    CustomTabsSessionToken.this.f2447a.onPostMessage(str, bundle);
                } catch (RemoteException unused) {
                    Log.e(CustomTabsSessionToken.TAG, "RemoteException during ICustomTabsCallback transaction");
                }
            }

            @Override // androidx.browser.customtabs.CustomTabsCallback
            public void onRelationshipValidationResult(int i2, @NonNull Uri uri, boolean z2, @Nullable Bundle bundle) {
                try {
                    CustomTabsSessionToken.this.f2447a.onRelationshipValidationResult(i2, uri, z2, bundle);
                } catch (RemoteException unused) {
                    Log.e(CustomTabsSessionToken.TAG, "RemoteException during ICustomTabsCallback transaction");
                }
            }

            @Override // androidx.browser.customtabs.CustomTabsCallback
            public void onUnminimized(@NonNull Bundle bundle) {
                try {
                    CustomTabsSessionToken.this.f2447a.onUnminimized(bundle);
                } catch (RemoteException unused) {
                    Log.e(CustomTabsSessionToken.TAG, "RemoteException during ICustomTabsCallback transaction");
                }
            }

            @Override // androidx.browser.customtabs.CustomTabsCallback
            public void onWarmupCompleted(@NonNull Bundle bundle) {
                try {
                    CustomTabsSessionToken.this.f2447a.onWarmupCompleted(bundle);
                } catch (RemoteException unused) {
                    Log.e(CustomTabsSessionToken.TAG, "RemoteException during ICustomTabsCallback transaction");
                }
            }
        };
    }

    @NonNull
    public static CustomTabsSessionToken createMockSessionTokenForTesting() {
        return new CustomTabsSessionToken(new MockCallback(), null);
    }

    private IBinder getCallbackBinderAssertNotNull() {
        ICustomTabsCallback iCustomTabsCallback = this.f2447a;
        if (iCustomTabsCallback != null) {
            return iCustomTabsCallback.asBinder();
        }
        throw new IllegalStateException("CustomTabSessionToken must have valid binder or pending session");
    }

    @Nullable
    public static CustomTabsSessionToken getSessionTokenFromIntent(@NonNull Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras == null) {
            return null;
        }
        IBinder binder = extras.getBinder(CustomTabsIntent.EXTRA_SESSION);
        PendingIntent pendingIntent = (PendingIntent) intent.getParcelableExtra(CustomTabsIntent.EXTRA_SESSION_ID);
        if (binder == null && pendingIntent == null) {
            return null;
        }
        return new CustomTabsSessionToken(binder != null ? ICustomTabsCallback.Stub.asInterface(binder) : null, pendingIntent);
    }

    IBinder a() {
        ICustomTabsCallback iCustomTabsCallback = this.f2447a;
        if (iCustomTabsCallback == null) {
            return null;
        }
        return iCustomTabsCallback.asBinder();
    }

    PendingIntent b() {
        return this.mSessionId;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CustomTabsSessionToken)) {
            return false;
        }
        CustomTabsSessionToken customTabsSessionToken = (CustomTabsSessionToken) obj;
        PendingIntent pendingIntentB = customTabsSessionToken.b();
        PendingIntent pendingIntent = this.mSessionId;
        if ((pendingIntent == null) != (pendingIntentB == null)) {
            return false;
        }
        return pendingIntent != null ? pendingIntent.equals(pendingIntentB) : getCallbackBinderAssertNotNull().equals(customTabsSessionToken.getCallbackBinderAssertNotNull());
    }

    @Nullable
    public CustomTabsCallback getCallback() {
        return this.mCallback;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public boolean hasCallback() {
        return this.f2447a != null;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public boolean hasId() {
        return this.mSessionId != null;
    }

    public int hashCode() {
        PendingIntent pendingIntent = this.mSessionId;
        return pendingIntent != null ? pendingIntent.hashCode() : getCallbackBinderAssertNotNull().hashCode();
    }

    public boolean isAssociatedWith(@NonNull CustomTabsSession customTabsSession) {
        return customTabsSession.a().equals(this.f2447a);
    }
}
