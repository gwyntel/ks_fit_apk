package com.aliyun.player.nativeclass;

import android.content.Context;
import com.aliyun.player.AliPlayer;
import com.aliyun.player.source.LiveSts;
import com.aliyun.player.source.StsInfo;
import com.aliyun.player.source.VidAuth;
import com.aliyun.player.source.VidMps;
import com.aliyun.player.source.VidSts;
import com.aliyun.utils.f;

/* loaded from: classes3.dex */
public class JniSaasPlayer extends JniUrlPlayer {
    private static final String TAG = "JniSaasPlayer";
    private AliPlayer.OnVerifyTimeExpireCallback mOnVerifyTimeExpireCallback;

    static {
        f.f();
    }

    public JniSaasPlayer(Context context) {
        super(context);
        this.mOnVerifyTimeExpireCallback = null;
    }

    public static void loadClass() {
    }

    private native void nSetDataSource(LiveSts liveSts);

    private native void nSetDataSource(VidAuth vidAuth);

    private native void nSetDataSource(VidMps vidMps);

    private native void nSetDataSource(VidSts vidSts);

    private native void nUpdateStsInfo(StsInfo stsInfo);

    private native void nUpdateVidAuth(VidAuth vidAuth);

    protected int onVerifyAuthCallback(Object obj) {
        VidAuth vidAuth = (VidAuth) obj;
        AliPlayer.OnVerifyTimeExpireCallback onVerifyTimeExpireCallback = this.mOnVerifyTimeExpireCallback;
        return (onVerifyTimeExpireCallback != null ? onVerifyTimeExpireCallback.onVerifyAuth(vidAuth) : AliPlayer.Status.Invalid).ordinal();
    }

    protected int onVerifyStsCallback(Object obj) {
        StsInfo stsInfo = (StsInfo) obj;
        AliPlayer.OnVerifyTimeExpireCallback onVerifyTimeExpireCallback = this.mOnVerifyTimeExpireCallback;
        return (onVerifyTimeExpireCallback != null ? onVerifyTimeExpireCallback.onVerifySts(stsInfo) : AliPlayer.Status.Invalid).ordinal();
    }

    public void setDataSource(LiveSts liveSts) {
        if (f.b()) {
            nSetDataSource(liveSts);
        }
    }

    public void setOnVerifyTimeExpireCallback(AliPlayer.OnVerifyTimeExpireCallback onVerifyTimeExpireCallback) {
        this.mOnVerifyTimeExpireCallback = onVerifyTimeExpireCallback;
    }

    public void updateStsInfo(StsInfo stsInfo) {
        if (f.b()) {
            nUpdateStsInfo(stsInfo);
        }
    }

    public void updateVidAuth(VidAuth vidAuth) {
        if (f.b()) {
            nUpdateVidAuth(vidAuth);
        }
    }

    public void setDataSource(VidAuth vidAuth) {
        if (f.b()) {
            nSetDataSource(vidAuth);
        }
    }

    public void setDataSource(VidMps vidMps) {
        if (f.b()) {
            nSetDataSource(vidMps);
        }
    }

    public void setDataSource(VidSts vidSts) {
        if (f.b()) {
            nSetDataSource(vidSts);
        }
    }
}
