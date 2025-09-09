package com.aliyun.player;

import com.aliyun.player.source.LiveSts;
import com.aliyun.player.source.StsInfo;
import com.aliyun.player.source.VidAuth;
import com.aliyun.player.source.VidMps;
import com.aliyun.player.source.VidSts;

/* loaded from: classes3.dex */
public interface AliPlayer extends UrlPlayer {

    public interface OnVerifyTimeExpireCallback {
        Status onVerifyAuth(VidAuth vidAuth);

        Status onVerifySts(StsInfo stsInfo);
    }

    public enum Status {
        Valid,
        Invalid,
        Pending
    }

    void invokeComponent(String str);

    void setDataSource(LiveSts liveSts);

    void setDataSource(VidAuth vidAuth);

    void setDataSource(VidMps vidMps);

    void setDataSource(VidSts vidSts);

    void setOnVerifyTimeExpireCallback(OnVerifyTimeExpireCallback onVerifyTimeExpireCallback);

    void updateStsInfo(StsInfo stsInfo);

    void updateVidAuth(VidAuth vidAuth);
}
