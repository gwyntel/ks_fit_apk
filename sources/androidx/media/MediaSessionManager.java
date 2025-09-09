package androidx.media;

import android.content.Context;
import android.media.session.MediaSessionManager;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.media.MediaSessionManagerImplApi28;
import androidx.media.MediaSessionManagerImplBase;

/* loaded from: classes.dex */
public final class MediaSessionManager {

    /* renamed from: b, reason: collision with root package name */
    static final boolean f4728b = Log.isLoggable("MediaSessionManager", 3);
    private static final Object sLock = new Object();
    private static volatile MediaSessionManager sSessionManager;

    /* renamed from: a, reason: collision with root package name */
    MediaSessionManagerImpl f4729a;

    interface MediaSessionManagerImpl {
        Context getContext();

        boolean isTrustedForMediaControl(RemoteUserInfoImpl remoteUserInfoImpl);
    }

    interface RemoteUserInfoImpl {
        String getPackageName();

        int getPid();

        int getUid();
    }

    private MediaSessionManager(Context context) {
        if (Build.VERSION.SDK_INT >= 28) {
            this.f4729a = new MediaSessionManagerImplApi28(context);
        } else {
            this.f4729a = new MediaSessionManagerImplApi21(context);
        }
    }

    @NonNull
    public static MediaSessionManager getSessionManager(@NonNull Context context) {
        MediaSessionManager mediaSessionManager = sSessionManager;
        if (mediaSessionManager == null) {
            synchronized (sLock) {
                try {
                    mediaSessionManager = sSessionManager;
                    if (mediaSessionManager == null) {
                        sSessionManager = new MediaSessionManager(context.getApplicationContext());
                        mediaSessionManager = sSessionManager;
                    }
                } finally {
                }
            }
        }
        return mediaSessionManager;
    }

    public boolean isTrustedForMediaControl(@NonNull RemoteUserInfo remoteUserInfo) {
        if (remoteUserInfo != null) {
            return this.f4729a.isTrustedForMediaControl(remoteUserInfo.f4730a);
        }
        throw new IllegalArgumentException("userInfo should not be null");
    }

    public static final class RemoteUserInfo {
        public static final String LEGACY_CONTROLLER = "android.media.session.MediaController";

        /* renamed from: a, reason: collision with root package name */
        RemoteUserInfoImpl f4730a;

        public RemoteUserInfo(@NonNull String str, int i2, int i3) {
            if (Build.VERSION.SDK_INT >= 28) {
                this.f4730a = new MediaSessionManagerImplApi28.RemoteUserInfoImplApi28(str, i2, i3);
            } else {
                this.f4730a = new MediaSessionManagerImplBase.RemoteUserInfoImplBase(str, i2, i3);
            }
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof RemoteUserInfo) {
                return this.f4730a.equals(((RemoteUserInfo) obj).f4730a);
            }
            return false;
        }

        @NonNull
        public String getPackageName() {
            return this.f4730a.getPackageName();
        }

        public int getPid() {
            return this.f4730a.getPid();
        }

        public int getUid() {
            return this.f4730a.getUid();
        }

        public int hashCode() {
            return this.f4730a.hashCode();
        }

        @RequiresApi(28)
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public RemoteUserInfo(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
            this.f4730a = new MediaSessionManagerImplApi28.RemoteUserInfoImplApi28(remoteUserInfo);
        }
    }
}
