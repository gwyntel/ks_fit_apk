package com.aliyun.player;

import android.content.Context;
import com.aliyun.player.nativeclass.JniListPlayerBase;
import com.aliyun.player.nativeclass.JniSaasPlayer;
import com.aliyun.player.nativeclass.JniUrlListPlayer;
import com.aliyun.player.nativeclass.PlayerConfig;
import com.aliyun.player.nativeclass.PreloadConfig;
import com.aliyun.player.source.BitStreamSource;
import com.aliyun.player.source.UrlSource;
import com.cicada.player.utils.Logger;

/* loaded from: classes3.dex */
public class UrlVideoListPlayer extends AVPLBase implements UrlListPlayer, UrlPlayer {
    private static final String TAG = "NativePlayerBase_UrlVideoListPlayer";
    private UrlVideoPlayer mUrlPrerenderPlayer;
    private UrlVideoPlayer mUrlVideoPlayer;

    public UrlVideoListPlayer(Context context, String str) {
        super(context, str);
        this.mUrlVideoPlayer = null;
        this.mUrlPrerenderPlayer = null;
    }

    @Override // com.aliyun.player.UrlListPlayer
    public void addUrl(String str, String str2) {
        JniListPlayerBase corePlayer = getCorePlayer();
        if (corePlayer instanceof JniUrlListPlayer) {
            Logger.v(TAG, "addUrl = " + str + " , uid = " + str2);
            ((JniUrlListPlayer) corePlayer).addUrl(str, str2);
        }
    }

    @Override // com.aliyun.player.AVPLBase
    protected JniListPlayerBase createListPlayer(Context context, String str, long j2, long j3) {
        return new JniUrlListPlayer(context, j2, j3);
    }

    @Override // com.aliyun.player.UrlPlayer
    public void enableDowngrade(UrlSource urlSource, PlayerConfig playerConfig) {
        Object nativePlayer = getNativePlayer();
        if (nativePlayer instanceof JniSaasPlayer) {
            ((JniSaasPlayer) nativePlayer).enableDowngrade(urlSource, playerConfig);
        }
    }

    @Override // com.aliyun.player.AVPLBase
    protected long getCurrentPlayerIndex() {
        JniListPlayerBase corePlayer = getCorePlayer();
        if (!(corePlayer instanceof JniUrlListPlayer)) {
            return 0L;
        }
        Logger.v(TAG, "getCurrentPlayerIndex  ");
        return ((JniUrlListPlayer) corePlayer).getCurrentPlayerIndex();
    }

    @Override // com.aliyun.player.AVPLBase
    protected IPlayer getNativePlayer(Context context, String str) {
        if (this.mUrlVideoPlayer == null) {
            this.mUrlVideoPlayer = new UrlVideoPlayer(context, str);
        }
        return this.mUrlVideoPlayer;
    }

    public IPlayer getPreRenderPlayer() {
        JniListPlayerBase corePlayer = getCorePlayer();
        if (!(corePlayer instanceof JniUrlListPlayer)) {
            return null;
        }
        Logger.v(TAG, "getPreRenderPlayer  ");
        getCurrentPrerenderPlayer(((JniUrlListPlayer) corePlayer).getPreRenderPlayerIndex());
        return null;
    }

    @Override // com.aliyun.player.AVPLBase
    protected IPlayer getPrerenderPlayer(Context context, String str) {
        if (this.mUrlPrerenderPlayer == null) {
            this.mUrlPrerenderPlayer = new UrlVideoPlayer(context, str);
        }
        return this.mUrlPrerenderPlayer;
    }

    @Override // com.aliyun.player.IPlayer
    public String getUserData() {
        return "";
    }

    @Override // com.aliyun.player.UrlListPlayer
    public boolean moveTo(String str) {
        JniListPlayerBase corePlayer = getCorePlayer();
        if (!(corePlayer instanceof JniUrlListPlayer)) {
            return false;
        }
        Logger.v(TAG, "moveTo uid = " + str);
        return ((JniUrlListPlayer) corePlayer).moveTo(str);
    }

    @Override // com.aliyun.player.UrlListPlayer
    public boolean moveToNext() {
        JniListPlayerBase corePlayer = getCorePlayer();
        if (!(corePlayer instanceof JniUrlListPlayer)) {
            return false;
        }
        Logger.v(TAG, "moveToNext  ");
        return ((JniUrlListPlayer) corePlayer).moveToNext(false);
    }

    @Override // com.aliyun.player.UrlListPlayer
    public boolean moveToNextWithPrerendered() {
        JniListPlayerBase corePlayer = getCorePlayer();
        if (!(corePlayer instanceof JniUrlListPlayer)) {
            return false;
        }
        Logger.v(TAG, "moveToNextWithPrerendered  ");
        return ((JniUrlListPlayer) corePlayer).moveToNext(true);
    }

    @Override // com.aliyun.player.UrlListPlayer
    public boolean moveToPrev() {
        JniListPlayerBase corePlayer = getCorePlayer();
        if (!(corePlayer instanceof JniUrlListPlayer)) {
            return false;
        }
        Logger.v(TAG, "moveToPrev  ");
        return ((JniUrlListPlayer) corePlayer).moveToPrev();
    }

    @Override // com.aliyun.player.UrlPlayer
    public void setDataSource(BitStreamSource bitStreamSource) {
        IPlayer nativePlayer = getNativePlayer();
        if (nativePlayer instanceof UrlPlayer) {
            ((UrlPlayer) nativePlayer).setDataSource(bitStreamSource);
        }
    }

    @Override // com.aliyun.player.IListPlayer
    public void setPreloadCount(int i2, int i3) {
        JniListPlayerBase corePlayer = getCorePlayer();
        if (corePlayer instanceof JniUrlListPlayer) {
            ((JniUrlListPlayer) corePlayer).setPreloadCount(i2, i3);
        }
    }

    @Override // com.aliyun.player.IPlayer
    public void setUserData(String str) {
    }

    @Override // com.aliyun.player.UrlListPlayer
    public void updatePreloadConfig(PreloadConfig preloadConfig) {
        JniListPlayerBase corePlayer = getCorePlayer();
        if (corePlayer instanceof JniUrlListPlayer) {
            ((JniUrlListPlayer) corePlayer).updatePreloadConfig(preloadConfig);
        }
    }

    @Override // com.aliyun.player.UrlListPlayer
    public void addUrl(String str, String str2, PreloadConfig preloadConfig) {
        JniListPlayerBase corePlayer = getCorePlayer();
        if (corePlayer instanceof JniUrlListPlayer) {
            ((JniUrlListPlayer) corePlayer).addUrl(str, str2, preloadConfig);
        }
    }

    @Override // com.aliyun.player.UrlPlayer
    public void setDataSource(UrlSource urlSource) {
        IPlayer nativePlayer = getNativePlayer();
        if (nativePlayer instanceof UrlPlayer) {
            ((UrlPlayer) nativePlayer).setDataSource(urlSource);
        }
    }

    @Override // com.aliyun.player.UrlListPlayer
    public void updatePreloadConfig(String str, PreloadConfig preloadConfig) {
        JniListPlayerBase corePlayer = getCorePlayer();
        if (corePlayer instanceof JniUrlListPlayer) {
            ((JniUrlListPlayer) corePlayer).updatePreloadConfig(str, preloadConfig);
        }
    }
}
