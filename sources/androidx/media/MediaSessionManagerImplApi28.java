package androidx.media;

import android.content.Context;
import android.media.session.MediaSessionManager;
import androidx.annotation.RequiresApi;
import androidx.core.util.ObjectsCompat;
import androidx.media.MediaSessionManager;

@RequiresApi(28)
/* loaded from: classes.dex */
class MediaSessionManagerImplApi28 extends MediaSessionManagerImplApi21 {

    /* renamed from: c, reason: collision with root package name */
    android.media.session.MediaSessionManager f4731c;

    MediaSessionManagerImplApi28(Context context) {
        super(context);
        this.f4731c = (android.media.session.MediaSessionManager) context.getSystemService("media_session");
    }

    @Override // androidx.media.MediaSessionManagerImplApi21, androidx.media.MediaSessionManagerImplBase, androidx.media.MediaSessionManager.MediaSessionManagerImpl
    public boolean isTrustedForMediaControl(MediaSessionManager.RemoteUserInfoImpl remoteUserInfoImpl) {
        if (remoteUserInfoImpl instanceof RemoteUserInfoImplApi28) {
            return this.f4731c.isTrustedForMediaControl(((RemoteUserInfoImplApi28) remoteUserInfoImpl).f4732a);
        }
        return false;
    }

    static final class RemoteUserInfoImplApi28 implements MediaSessionManager.RemoteUserInfoImpl {

        /* renamed from: a, reason: collision with root package name */
        final MediaSessionManager.RemoteUserInfo f4732a;

        RemoteUserInfoImplApi28(String str, int i2, int i3) {
            this.f4732a = j.a(str, i2, i3);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof RemoteUserInfoImplApi28) {
                return this.f4732a.equals(((RemoteUserInfoImplApi28) obj).f4732a);
            }
            return false;
        }

        @Override // androidx.media.MediaSessionManager.RemoteUserInfoImpl
        public String getPackageName() {
            return this.f4732a.getPackageName();
        }

        @Override // androidx.media.MediaSessionManager.RemoteUserInfoImpl
        public int getPid() {
            return this.f4732a.getPid();
        }

        @Override // androidx.media.MediaSessionManager.RemoteUserInfoImpl
        public int getUid() {
            return this.f4732a.getUid();
        }

        public int hashCode() {
            return ObjectsCompat.hash(this.f4732a);
        }

        RemoteUserInfoImplApi28(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
            this.f4732a = remoteUserInfo;
        }
    }
}
