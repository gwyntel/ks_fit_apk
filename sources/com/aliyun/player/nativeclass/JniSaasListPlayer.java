package com.aliyun.player.nativeclass;

import android.content.Context;
import com.aliyun.player.source.PlayAuthInfo;
import com.aliyun.player.source.StsInfo;
import com.aliyun.utils.f;

/* loaded from: classes3.dex */
public class JniSaasListPlayer extends JniUrlListPlayer {
    private static final String TAG = "JniSaasListPlayer";

    static {
        f.f();
    }

    public JniSaasListPlayer(Context context, long j2, long j3) {
        super(context, j2, j3);
    }

    public static void loadClass() {
    }

    public void addVid(String str, String str2) {
        if (f.b()) {
            nAddVid(str, str2);
        }
    }

    public boolean moveTo(String str, PlayAuthInfo playAuthInfo) {
        if (f.b()) {
            return nMoveToWithPlayAuth(str, playAuthInfo);
        }
        return false;
    }

    public boolean moveToNext(PlayAuthInfo playAuthInfo, boolean z2) {
        if (f.b()) {
            return nMoveToNextWithPlayAuth(playAuthInfo, z2);
        }
        return false;
    }

    public boolean moveToPrev(PlayAuthInfo playAuthInfo) {
        if (f.b()) {
            return nMoveToPrevWithPlayAuth(playAuthInfo);
        }
        return false;
    }

    native void nAddVid(String str, String str2);

    native boolean nMoveToNextWithPlayAuth(PlayAuthInfo playAuthInfo, boolean z2);

    native boolean nMoveToNextWithSts(StsInfo stsInfo, boolean z2);

    native boolean nMoveToPrevWithPlayAuth(PlayAuthInfo playAuthInfo);

    native boolean nMoveToPrevWithSts(StsInfo stsInfo);

    native boolean nMoveToWithPlayAuth(String str, PlayAuthInfo playAuthInfo);

    native boolean nMoveToWithSts(String str, StsInfo stsInfo);

    native void nSetDefinition(String str);

    public void setDefinition(String str) {
        if (f.b()) {
            nSetDefinition(str);
        }
    }

    public boolean moveTo(String str, StsInfo stsInfo) {
        if (f.b()) {
            return nMoveToWithSts(str, stsInfo);
        }
        return false;
    }

    public boolean moveToNext(StsInfo stsInfo, boolean z2) {
        if (f.b()) {
            return nMoveToNextWithSts(stsInfo, z2);
        }
        return false;
    }

    public boolean moveToPrev(StsInfo stsInfo) {
        if (f.b()) {
            return nMoveToPrevWithSts(stsInfo);
        }
        return false;
    }
}
