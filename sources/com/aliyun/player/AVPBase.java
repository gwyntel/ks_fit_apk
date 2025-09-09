package com.aliyun.player;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.aliyun.player.FilterConfig;
import com.aliyun.player.IPlayer;
import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.player.bean.InfoBean;
import com.aliyun.player.nativeclass.CacheConfig;
import com.aliyun.player.nativeclass.MediaInfo;
import com.aliyun.player.nativeclass.NativePlayerBase;
import com.aliyun.player.nativeclass.PlayerConfig;
import com.aliyun.player.nativeclass.PlayerScene;
import com.aliyun.player.nativeclass.TrackInfo;
import com.aliyun.player.videoview.AliDisplayView;
import com.aliyun.utils.g;
import com.cicada.player.utils.Logger;
import com.cicada.player.utils.media.DrmCallback;
import java.lang.ref.WeakReference;
import java.util.Map;

/* loaded from: classes3.dex */
public abstract class AVPBase implements IPlayer {
    private static final String TAG = "AVPBase";
    protected Context mContext;
    private NativePlayerBase mCorePlayer;
    protected String mTraceID;
    private MediaInfo mOutMediaInfo = null;
    private MediaInfo mOutSubMediaInfo = null;
    private IPlayer.OnPreparedListener mOutOnPreparedListener = null;
    private IPlayer.OnPreparedListener mInnerOnPreparedListener = new InnerPrepareListener(this);
    private IPlayer.OnInfoListener mOutOnInfoListener = null;
    private IPlayer.OnInfoListener mInnerOnInfoListener = new InnerInfoListener(this);
    private IPlayer.OnChooseTrackIndexListener mOutOnChooseTrackIndexListener = null;
    private IPlayer.OnChooseTrackIndexListener mInnerOnChooseTrackIndexListener = new InnerOnChooseTrackIndexListener(this);
    private IPlayer.OnErrorListener mOutOnErrorListener = null;
    private IPlayer.OnErrorListener mInnerOnErrorListener = new InnerErrorListener(this);
    private IPlayer.OnCompletionListener mOutOnCompletionListener = null;
    private IPlayer.OnCompletionListener mInnerOnCompletionListener = new InnerCompletionListener(this);
    private IPlayer.OnRenderingStartListener mOutOnRenderingStartListener = null;
    private IPlayer.OnRenderingStartListener mInnerOnFirstFrameShowListener = new InnerRenderListener(this);
    private IPlayer.OnVideoSizeChangedListener mOutOnVideoSizeChangedListener = null;
    private IPlayer.OnVideoSizeChangedListener mInnerOnVideoSizeChangedListener = new InnerVideoSizeChangedListener(this);
    private IPlayer.OnTrackReadyListener mOutOnTrackReadyListener = null;
    private IPlayer.OnTrackReadyListener mInnerOnTrackReadyListener = new InnerTrackReadyListener(this);
    private IPlayer.OnSubTrackReadyListener mOutOnSubTrackReadyListener = null;
    private IPlayer.OnSubTrackReadyListener mInnerOnSubTrackReadyListener = new InnerSubTrackReadyListener(this);
    private IPlayer.OnAVNotSyncStatusListener mOutOnAVNotSyncStatusListener = null;
    private IPlayer.OnAVNotSyncStatusListener mInnerOnAVNotSyncStatusListener = new InnerAVNotSyncStatusListener(this);
    private IPlayer.OnLoadingStatusListener mOutOnLoadingStatusListener = null;
    private IPlayer.OnLoadingStatusListener mInnerOnLoadingStatusListener = new InnerLoadingStatusListener(this);
    private IPlayer.OnSeekCompleteListener mOutOnSeekEndListener = null;
    private IPlayer.OnSeekCompleteListener mInnerOnSeekEndListener = new InnerSeekEndListener(this);
    private IPlayer.OnSubtitleDisplayListener mOutOnSubtitleDisplayListener = null;
    private IPlayer.OnSubtitleDisplayListener mInnerOnSubtitleDisplayListener = new InnerSubtitleDisplayListener(this);
    private IPlayer.OnSeiDataListener mOutOnSeiDataListener = null;
    private IPlayer.OnSeiDataListener mInnerOnSeiDataListener = new InnerSeiDataListener(this);
    private IPlayer.OnStreamSwitchedListener mOutOnStreamSwitchedListener = null;
    private IPlayer.OnStreamSwitchedListener mInnerOnStreamSwitchedListener = new InnerStreamSwitchedListener(this);
    private IPlayer.OnTrackChangedListener mOutOnTrackChangedListener = null;
    private IPlayer.OnTrackChangedListener mInnerOnTrackChangedListener = new InnerTrackChangedListener(this);
    private IPlayer.OnSnapShotListener mOutOnSnapShotListener = null;
    private IPlayer.OnSnapShotListener mInnerOnSnapShotListener = new InnerSnapShotListener(this);
    private IPlayer.OnStateChangedListener mOutOnStatusChangedListener = null;
    private IPlayer.OnStateChangedListener mInnerOnStatusChangedListener = new InnerStatusChangedListener(this);
    private IPlayer.OnVideoRenderedListener mOutOnVideoRenderedListener = null;
    private IPlayer.OnVideoRenderedListener mInnerOnVideoRenderedListener = new InnerVideoRenderedListener(this);
    private IPlayer.OnReportEventListener mOutEventListener = null;
    private InnerOnReportEventListener mInnerOnReportEventListener = new InnerOnReportEventListener(this);

    private static class InnerAVNotSyncStatusListener implements IPlayer.OnAVNotSyncStatusListener {
        private WeakReference<AVPBase> avpBaseWR;

        InnerAVNotSyncStatusListener(AVPBase aVPBase) {
            this.avpBaseWR = new WeakReference<>(aVPBase);
        }

        @Override // com.aliyun.player.IPlayer.OnAVNotSyncStatusListener
        public void onAVNotSyncEnd() {
            AVPBase aVPBase = this.avpBaseWR.get();
            if (aVPBase != null) {
                aVPBase.onAVNotSyncEnd();
            }
        }

        @Override // com.aliyun.player.IPlayer.OnAVNotSyncStatusListener
        public void onAVNotSyncStart(int i2) {
            AVPBase aVPBase = this.avpBaseWR.get();
            if (aVPBase != null) {
                aVPBase.onAVNotSyncStart(i2);
            }
        }
    }

    private static class InnerCompletionListener implements IPlayer.OnCompletionListener {
        private WeakReference<AVPBase> avpBaseWR;

        InnerCompletionListener(AVPBase aVPBase) {
            this.avpBaseWR = new WeakReference<>(aVPBase);
        }

        @Override // com.aliyun.player.IPlayer.OnCompletionListener
        public void onCompletion() {
            AVPBase aVPBase = this.avpBaseWR.get();
            if (aVPBase != null) {
                aVPBase.onCompletion();
            }
        }
    }

    private static class InnerErrorListener implements IPlayer.OnErrorListener {
        private WeakReference<AVPBase> avpBaseWR;

        InnerErrorListener(AVPBase aVPBase) {
            this.avpBaseWR = new WeakReference<>(aVPBase);
        }

        @Override // com.aliyun.player.IPlayer.OnErrorListener
        public void onError(ErrorInfo errorInfo) {
            AVPBase aVPBase = this.avpBaseWR.get();
            if (aVPBase != null) {
                aVPBase.onError(errorInfo);
            }
        }
    }

    private static class InnerInfoListener implements IPlayer.OnInfoListener {
        private WeakReference<AVPBase> avpBaseWR;

        InnerInfoListener(AVPBase aVPBase) {
            this.avpBaseWR = new WeakReference<>(aVPBase);
        }

        @Override // com.aliyun.player.IPlayer.OnInfoListener
        public void onInfo(InfoBean infoBean) {
            AVPBase aVPBase = this.avpBaseWR.get();
            if (aVPBase != null) {
                aVPBase.onInfo(infoBean);
            }
        }
    }

    private static class InnerLoadingStatusListener implements IPlayer.OnLoadingStatusListener {
        private WeakReference<AVPBase> avpBaseWR;

        InnerLoadingStatusListener(AVPBase aVPBase) {
            this.avpBaseWR = new WeakReference<>(aVPBase);
        }

        @Override // com.aliyun.player.IPlayer.OnLoadingStatusListener
        public void onLoadingBegin() {
            AVPBase aVPBase = this.avpBaseWR.get();
            if (aVPBase != null) {
                aVPBase.onLoadingBegin();
            }
        }

        @Override // com.aliyun.player.IPlayer.OnLoadingStatusListener
        public void onLoadingEnd() {
            AVPBase aVPBase = this.avpBaseWR.get();
            if (aVPBase != null) {
                aVPBase.onLoadingEnd();
            }
        }

        @Override // com.aliyun.player.IPlayer.OnLoadingStatusListener
        public void onLoadingProgress(int i2, float f2) {
            AVPBase aVPBase = this.avpBaseWR.get();
            if (aVPBase != null) {
                aVPBase.onLoadingProgress(i2, f2);
            }
        }
    }

    private static class InnerOnChooseTrackIndexListener implements IPlayer.OnChooseTrackIndexListener {
        private WeakReference<AVPBase> avpBaseWR;

        InnerOnChooseTrackIndexListener(AVPBase aVPBase) {
            this.avpBaseWR = new WeakReference<>(aVPBase);
        }

        @Override // com.aliyun.player.IPlayer.OnChooseTrackIndexListener
        public int onChooseTrackIndex(TrackInfo[] trackInfoArr) {
            AVPBase aVPBase = this.avpBaseWR.get();
            if (aVPBase != null) {
                return aVPBase.onChooseTrackIndex(trackInfoArr);
            }
            return -1;
        }
    }

    private static class InnerOnReportEventListener implements IPlayer.OnReportEventListener {
        private WeakReference<AVPBase> avpBaseWR;

        InnerOnReportEventListener(AVPBase aVPBase) {
            this.avpBaseWR = new WeakReference<>(aVPBase);
        }

        @Override // com.aliyun.player.IPlayer.OnReportEventListener
        public void onEventParam(Map<String, String> map) {
            AVPBase aVPBase = this.avpBaseWR.get();
            if (aVPBase != null) {
                aVPBase.onEventParam(map);
            }
        }
    }

    private static class InnerPrepareListener implements IPlayer.OnPreparedListener {
        private WeakReference<AVPBase> avpBaseWR;

        InnerPrepareListener(AVPBase aVPBase) {
            this.avpBaseWR = new WeakReference<>(aVPBase);
        }

        @Override // com.aliyun.player.IPlayer.OnPreparedListener
        public void onPrepared() {
            AVPBase aVPBase = this.avpBaseWR.get();
            if (aVPBase != null) {
                aVPBase.onPrepared();
            }
        }
    }

    private static class InnerRenderListener implements IPlayer.OnRenderingStartListener {
        private WeakReference<AVPBase> avpBaseWR;

        InnerRenderListener(AVPBase aVPBase) {
            this.avpBaseWR = new WeakReference<>(aVPBase);
        }

        @Override // com.aliyun.player.IPlayer.OnRenderingStartListener
        public void onRenderingStart() {
            AVPBase aVPBase = this.avpBaseWR.get();
            if (aVPBase != null) {
                aVPBase.onVideoRenderingStart();
            }
        }
    }

    private static class InnerSeekEndListener implements IPlayer.OnSeekCompleteListener {
        private WeakReference<AVPBase> avpBaseWR;

        InnerSeekEndListener(AVPBase aVPBase) {
            this.avpBaseWR = new WeakReference<>(aVPBase);
        }

        @Override // com.aliyun.player.IPlayer.OnSeekCompleteListener
        public void onSeekComplete() {
            AVPBase aVPBase = this.avpBaseWR.get();
            if (aVPBase != null) {
                aVPBase.onSeekComplete();
            }
        }
    }

    private static class InnerSeiDataListener implements IPlayer.OnSeiDataListener {
        private WeakReference<AVPBase> avpBaseWR;

        InnerSeiDataListener(AVPBase aVPBase) {
            this.avpBaseWR = new WeakReference<>(aVPBase);
        }

        @Override // com.aliyun.player.IPlayer.OnSeiDataListener
        public void onSeiData(int i2, byte[] bArr, byte[] bArr2) {
            AVPBase aVPBase = this.avpBaseWR.get();
            if (aVPBase != null) {
                aVPBase.onSeiData(i2, bArr, bArr2);
            }
        }
    }

    private static class InnerSnapShotListener implements IPlayer.OnSnapShotListener {
        private WeakReference<AVPBase> avpBaseWR;

        InnerSnapShotListener(AVPBase aVPBase) {
            this.avpBaseWR = new WeakReference<>(aVPBase);
        }

        @Override // com.aliyun.player.IPlayer.OnSnapShotListener
        public void onSnapShot(Bitmap bitmap, int i2, int i3) {
            AVPBase aVPBase = this.avpBaseWR.get();
            if (aVPBase != null) {
                aVPBase.onSnapShot(bitmap, i2, i3);
            }
        }
    }

    private static class InnerStatusChangedListener implements IPlayer.OnStateChangedListener {
        private WeakReference<AVPBase> avpBaseWR;

        InnerStatusChangedListener(AVPBase aVPBase) {
            this.avpBaseWR = new WeakReference<>(aVPBase);
        }

        @Override // com.aliyun.player.IPlayer.OnStateChangedListener
        public void onStateChanged(int i2) {
            AVPBase aVPBase = this.avpBaseWR.get();
            if (aVPBase != null) {
                aVPBase.onStateChanged(i2);
            }
        }
    }

    private static class InnerStreamSwitchedListener implements IPlayer.OnStreamSwitchedListener {
        private WeakReference<AVPBase> avpBaseWR;

        InnerStreamSwitchedListener(AVPBase aVPBase) {
            this.avpBaseWR = new WeakReference<>(aVPBase);
        }

        @Override // com.aliyun.player.IPlayer.OnStreamSwitchedListener
        public void onSwitchedFail(String str, ErrorInfo errorInfo) {
            AVPBase aVPBase = this.avpBaseWR.get();
            if (aVPBase != null) {
                aVPBase.onSwitchedFail(str, errorInfo);
            }
        }

        @Override // com.aliyun.player.IPlayer.OnStreamSwitchedListener
        public void onSwitchedSuccess(String str) {
            AVPBase aVPBase = this.avpBaseWR.get();
            if (aVPBase != null) {
                aVPBase.onSwitchedSuccess(str);
            }
        }
    }

    private static class InnerSubTrackReadyListener implements IPlayer.OnSubTrackReadyListener {
        private WeakReference<AVPBase> avpBaseWR;

        InnerSubTrackReadyListener(AVPBase aVPBase) {
            this.avpBaseWR = new WeakReference<>(aVPBase);
        }

        @Override // com.aliyun.player.IPlayer.OnSubTrackReadyListener
        public void onSubTrackReady(MediaInfo mediaInfo) {
            AVPBase aVPBase = this.avpBaseWR.get();
            if (aVPBase != null) {
                aVPBase.onSubTrackReady(mediaInfo);
            }
        }
    }

    private static class InnerSubtitleDisplayListener implements IPlayer.OnSubtitleDisplayListener {
        private WeakReference<AVPBase> avpBaseWR;

        InnerSubtitleDisplayListener(AVPBase aVPBase) {
            this.avpBaseWR = new WeakReference<>(aVPBase);
        }

        @Override // com.aliyun.player.IPlayer.OnSubtitleDisplayListener
        public void onSubtitleExtAdded(int i2, String str) {
            AVPBase aVPBase = this.avpBaseWR.get();
            if (aVPBase != null) {
                aVPBase.onSubtitleExtAdded(i2, str);
            }
        }

        @Override // com.aliyun.player.IPlayer.OnSubtitleDisplayListener
        public void onSubtitleHeader(int i2, String str) {
            AVPBase aVPBase = this.avpBaseWR.get();
            if (aVPBase != null) {
                aVPBase.onSubtitleHeader(i2, str);
            }
        }

        @Override // com.aliyun.player.IPlayer.OnSubtitleDisplayListener
        public void onSubtitleHide(int i2, long j2) {
            AVPBase aVPBase = this.avpBaseWR.get();
            if (aVPBase != null) {
                aVPBase.onSubtitleHide(i2, j2);
            }
        }

        @Override // com.aliyun.player.IPlayer.OnSubtitleDisplayListener
        public void onSubtitleShow(int i2, long j2, String str) {
            AVPBase aVPBase = this.avpBaseWR.get();
            if (aVPBase != null) {
                aVPBase.onSubtitleShow(i2, j2, str);
            }
        }
    }

    private static class InnerTrackChangedListener implements IPlayer.OnTrackChangedListener {
        private WeakReference<AVPBase> avpBaseWR;

        InnerTrackChangedListener(AVPBase aVPBase) {
            this.avpBaseWR = new WeakReference<>(aVPBase);
        }

        @Override // com.aliyun.player.IPlayer.OnTrackChangedListener
        public void onChangedFail(TrackInfo trackInfo, ErrorInfo errorInfo) {
            AVPBase aVPBase = this.avpBaseWR.get();
            if (aVPBase != null) {
                aVPBase.onChangedFail(trackInfo, errorInfo);
            }
        }

        @Override // com.aliyun.player.IPlayer.OnTrackChangedListener
        public void onChangedSuccess(TrackInfo trackInfo) {
            AVPBase aVPBase = this.avpBaseWR.get();
            if (aVPBase != null) {
                aVPBase.onChangedSuccess(trackInfo);
            }
        }
    }

    private static class InnerTrackReadyListener implements IPlayer.OnTrackReadyListener {
        private WeakReference<AVPBase> avpBaseWR;

        InnerTrackReadyListener(AVPBase aVPBase) {
            this.avpBaseWR = new WeakReference<>(aVPBase);
        }

        @Override // com.aliyun.player.IPlayer.OnTrackReadyListener
        public void onTrackReady(MediaInfo mediaInfo) {
            AVPBase aVPBase = this.avpBaseWR.get();
            if (aVPBase != null) {
                aVPBase.onTrackReady(mediaInfo);
            }
        }
    }

    private static class InnerVideoRenderedListener implements IPlayer.OnVideoRenderedListener {
        private WeakReference<AVPBase> avpBaseWR;

        InnerVideoRenderedListener(AVPBase aVPBase) {
            this.avpBaseWR = new WeakReference<>(aVPBase);
        }

        @Override // com.aliyun.player.IPlayer.OnVideoRenderedListener
        public void onVideoRendered(long j2, long j3) {
            AVPBase aVPBase = this.avpBaseWR.get();
            if (aVPBase != null) {
                aVPBase.onVideoRendered(j2, j3);
            }
        }
    }

    private static class InnerVideoSizeChangedListener implements IPlayer.OnVideoSizeChangedListener {
        private WeakReference<AVPBase> avpBaseWR;

        InnerVideoSizeChangedListener(AVPBase aVPBase) {
            this.avpBaseWR = new WeakReference<>(aVPBase);
        }

        @Override // com.aliyun.player.IPlayer.OnVideoSizeChangedListener
        public void onVideoSizeChanged(int i2, int i3) {
            AVPBase aVPBase = this.avpBaseWR.get();
            if (aVPBase != null) {
                aVPBase.onVideoSizeChanged(i2, i3);
            }
        }
    }

    public AVPBase(Context context, String str) {
        this.mContext = null;
        this.mTraceID = null;
        this.mCorePlayer = null;
        this.mContext = context;
        this.mTraceID = str;
        NativePlayerBase nativePlayerBaseCreateAlivcMediaPlayer = createAlivcMediaPlayer(context);
        this.mCorePlayer = nativePlayerBaseCreateAlivcMediaPlayer;
        nativePlayerBaseCreateAlivcMediaPlayer.setTraceId(this.mTraceID);
        bindListeners();
    }

    private void bindListeners() {
        this.mCorePlayer.setOnErrorListener(this.mInnerOnErrorListener);
        this.mCorePlayer.setOnPreparedListener(this.mInnerOnPreparedListener);
        this.mCorePlayer.setOnInfoListener(this.mInnerOnInfoListener);
        this.mCorePlayer.setOnCompletionListener(this.mInnerOnCompletionListener);
        this.mCorePlayer.setOnRenderingStartListener(this.mInnerOnFirstFrameShowListener);
        this.mCorePlayer.setOnLoadingStatusListener(this.mInnerOnLoadingStatusListener);
        this.mCorePlayer.setOnAVNotSyncStatusListener(this.mInnerOnAVNotSyncStatusListener);
        this.mCorePlayer.setOnSeekCompleteListener(this.mInnerOnSeekEndListener);
        this.mCorePlayer.setOnStateChangedListener(this.mInnerOnStatusChangedListener);
        this.mCorePlayer.setOnSubtitleDisplayListener(this.mInnerOnSubtitleDisplayListener);
        this.mCorePlayer.setOnVideoSizeChangedListener(this.mInnerOnVideoSizeChangedListener);
        this.mCorePlayer.setOnChooseTrackIndexListener(this.mInnerOnChooseTrackIndexListener);
        this.mCorePlayer.setOnTrackInfoGetListener(this.mInnerOnTrackReadyListener);
        this.mCorePlayer.setOnStreamSwitchedListener(this.mInnerOnStreamSwitchedListener);
        this.mCorePlayer.setOnSubTrackInfoGetListener(this.mInnerOnSubTrackReadyListener);
        this.mCorePlayer.setOnTrackSelectRetListener(this.mInnerOnTrackChangedListener);
        this.mCorePlayer.setOnSeiDataListener(this.mInnerOnSeiDataListener);
        this.mCorePlayer.setOnSnapShotListener(this.mInnerOnSnapShotListener);
        this.mCorePlayer.setOnReportEventListener(this.mInnerOnReportEventListener);
    }

    private void clearListeners() {
        this.mOutOnCompletionListener = null;
        this.mOutOnErrorListener = null;
        this.mOutOnLoadingStatusListener = null;
        this.mOutOnInfoListener = null;
        this.mOutOnAVNotSyncStatusListener = null;
        this.mOutOnPreparedListener = null;
        this.mOutOnRenderingStartListener = null;
        this.mOutOnSeekEndListener = null;
        this.mOutOnStatusChangedListener = null;
        this.mOutOnSubtitleDisplayListener = null;
        this.mOutOnTrackChangedListener = null;
        this.mOutOnTrackReadyListener = null;
        this.mOutOnSubTrackReadyListener = null;
        this.mOutOnVideoSizeChangedListener = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAVNotSyncEnd() {
        IPlayer.OnAVNotSyncStatusListener onAVNotSyncStatusListener = this.mOutOnAVNotSyncStatusListener;
        if (onAVNotSyncStatusListener != null) {
            onAVNotSyncStatusListener.onAVNotSyncEnd();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAVNotSyncStart(int i2) {
        IPlayer.OnAVNotSyncStatusListener onAVNotSyncStatusListener = this.mOutOnAVNotSyncStatusListener;
        if (onAVNotSyncStatusListener != null) {
            onAVNotSyncStatusListener.onAVNotSyncStart(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onChangedFail(TrackInfo trackInfo, ErrorInfo errorInfo) {
        IPlayer.OnTrackChangedListener onTrackChangedListener = this.mOutOnTrackChangedListener;
        if (onTrackChangedListener != null) {
            onTrackChangedListener.onChangedFail(trackInfo, errorInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onChangedSuccess(TrackInfo trackInfo) {
        IPlayer.OnTrackChangedListener onTrackChangedListener = this.mOutOnTrackChangedListener;
        if (onTrackChangedListener != null) {
            onTrackChangedListener.onChangedSuccess(trackInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int onChooseTrackIndex(TrackInfo[] trackInfoArr) {
        IPlayer.OnChooseTrackIndexListener onChooseTrackIndexListener = this.mOutOnChooseTrackIndexListener;
        if (onChooseTrackIndexListener != null) {
            return onChooseTrackIndexListener.onChooseTrackIndex(trackInfoArr);
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCompletion() {
        IPlayer.OnCompletionListener onCompletionListener = this.mOutOnCompletionListener;
        if (onCompletionListener != null) {
            onCompletionListener.onCompletion();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onError(ErrorInfo errorInfo) {
        IPlayer.OnErrorListener onErrorListener = this.mOutOnErrorListener;
        if (onErrorListener != null) {
            onErrorListener.onError(errorInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onEventParam(Map<String, String> map) {
        IPlayer.OnReportEventListener onReportEventListener = this.mOutEventListener;
        if (onReportEventListener != null) {
            onReportEventListener.onEventParam(map);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onInfo(InfoBean infoBean) {
        IPlayer.OnInfoListener onInfoListener = this.mOutOnInfoListener;
        if (onInfoListener != null) {
            onInfoListener.onInfo(infoBean);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLoadingBegin() {
        IPlayer.OnLoadingStatusListener onLoadingStatusListener = this.mOutOnLoadingStatusListener;
        if (onLoadingStatusListener != null) {
            onLoadingStatusListener.onLoadingBegin();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLoadingEnd() {
        IPlayer.OnLoadingStatusListener onLoadingStatusListener = this.mOutOnLoadingStatusListener;
        if (onLoadingStatusListener != null) {
            onLoadingStatusListener.onLoadingEnd();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLoadingProgress(int i2, float f2) {
        IPlayer.OnLoadingStatusListener onLoadingStatusListener = this.mOutOnLoadingStatusListener;
        if (onLoadingStatusListener != null) {
            onLoadingStatusListener.onLoadingProgress(i2, f2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPrepared() {
        IPlayer.OnPreparedListener onPreparedListener = this.mOutOnPreparedListener;
        if (onPreparedListener != null) {
            onPreparedListener.onPrepared();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSeekComplete() {
        IPlayer.OnSeekCompleteListener onSeekCompleteListener = this.mOutOnSeekEndListener;
        if (onSeekCompleteListener != null) {
            onSeekCompleteListener.onSeekComplete();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSeiData(int i2, byte[] bArr, byte[] bArr2) {
        IPlayer.OnSeiDataListener onSeiDataListener = this.mOutOnSeiDataListener;
        if (onSeiDataListener != null) {
            onSeiDataListener.onSeiData(i2, bArr, bArr2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSnapShot(Bitmap bitmap, int i2, int i3) {
        IPlayer.OnSnapShotListener onSnapShotListener = this.mOutOnSnapShotListener;
        if (onSnapShotListener != null) {
            onSnapShotListener.onSnapShot(bitmap, i2, i3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onStateChanged(int i2) {
        IPlayer.OnStateChangedListener onStateChangedListener = this.mOutOnStatusChangedListener;
        if (onStateChangedListener != null) {
            onStateChangedListener.onStateChanged(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSubTrackReady(MediaInfo mediaInfo) {
        this.mOutSubMediaInfo = mediaInfo;
        IPlayer.OnSubTrackReadyListener onSubTrackReadyListener = this.mOutOnSubTrackReadyListener;
        if (onSubTrackReadyListener != null) {
            onSubTrackReadyListener.onSubTrackReady(mediaInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSubtitleExtAdded(int i2, String str) {
        IPlayer.OnSubtitleDisplayListener onSubtitleDisplayListener = this.mOutOnSubtitleDisplayListener;
        if (onSubtitleDisplayListener != null) {
            onSubtitleDisplayListener.onSubtitleExtAdded(i2, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSubtitleHeader(int i2, String str) {
        IPlayer.OnSubtitleDisplayListener onSubtitleDisplayListener = this.mOutOnSubtitleDisplayListener;
        if (onSubtitleDisplayListener != null) {
            onSubtitleDisplayListener.onSubtitleHeader(i2, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSubtitleHide(int i2, long j2) {
        IPlayer.OnSubtitleDisplayListener onSubtitleDisplayListener = this.mOutOnSubtitleDisplayListener;
        if (onSubtitleDisplayListener != null) {
            onSubtitleDisplayListener.onSubtitleHide(i2, j2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSubtitleShow(int i2, long j2, String str) {
        IPlayer.OnSubtitleDisplayListener onSubtitleDisplayListener = this.mOutOnSubtitleDisplayListener;
        if (onSubtitleDisplayListener != null) {
            onSubtitleDisplayListener.onSubtitleShow(i2, j2, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSwitchedFail(String str, ErrorInfo errorInfo) {
        IPlayer.OnStreamSwitchedListener onStreamSwitchedListener = this.mOutOnStreamSwitchedListener;
        if (onStreamSwitchedListener != null) {
            onStreamSwitchedListener.onSwitchedFail(str, errorInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSwitchedSuccess(String str) {
        IPlayer.OnStreamSwitchedListener onStreamSwitchedListener = this.mOutOnStreamSwitchedListener;
        if (onStreamSwitchedListener != null) {
            onStreamSwitchedListener.onSwitchedSuccess(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTrackReady(MediaInfo mediaInfo) {
        this.mOutMediaInfo = mediaInfo;
        IPlayer.OnTrackReadyListener onTrackReadyListener = this.mOutOnTrackReadyListener;
        if (onTrackReadyListener != null) {
            onTrackReadyListener.onTrackReady(mediaInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onVideoRendered(long j2, long j3) {
        IPlayer.OnVideoRenderedListener onVideoRenderedListener = this.mOutOnVideoRenderedListener;
        if (onVideoRenderedListener != null) {
            onVideoRenderedListener.onVideoRendered(j2, j3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onVideoRenderingStart() {
        IPlayer.OnRenderingStartListener onRenderingStartListener = this.mOutOnRenderingStartListener;
        if (onRenderingStartListener != null) {
            onRenderingStartListener.onRenderingStart();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onVideoSizeChanged(int i2, int i3) {
        IPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener = this.mOutOnVideoSizeChangedListener;
        if (onVideoSizeChangedListener != null) {
            onVideoSizeChangedListener.onVideoSizeChanged(i2, i3);
        }
    }

    private void pauseInner() {
        this.mCorePlayer.pause();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseInner() {
        this.mCorePlayer.release();
        clearListeners();
    }

    private void setGlobalTime(String str) {
        this.mCorePlayer.setGlobalTime(str);
    }

    private void startInner() {
        this.mCorePlayer.start();
    }

    @Override // com.aliyun.player.IPlayer
    public void addExtSubtitle(String str) {
        this.mCorePlayer.addExtSubtitle(str);
    }

    @Override // com.aliyun.player.IPlayer
    public void clearScreen() {
        this.mCorePlayer.clearScreen();
    }

    protected abstract NativePlayerBase createAlivcMediaPlayer(Context context);

    @Override // com.aliyun.player.IPlayer
    @Deprecated
    public TrackInfo currentTrack(int i2) {
        return this.mCorePlayer.getCurrentTrackInfo(i2);
    }

    @Override // com.aliyun.player.IPlayer
    public void enableHardwareDecoder(boolean z2) {
        this.mCorePlayer.enableHardwareDecoder(z2);
    }

    @Override // com.aliyun.player.IPlayer
    public IPlayer.AlphaRenderMode getAlphaRenderMode() {
        return this.mCorePlayer.getAlphaRenderMode();
    }

    @Override // com.aliyun.player.IPlayer
    public long getBufferedPosition() {
        return this.mCorePlayer.getBufferedPosition();
    }

    @Override // com.aliyun.player.IPlayer
    public String getCacheFilePath(String str) {
        return this.mCorePlayer.getCacheFilePath(str);
    }

    @Override // com.aliyun.player.IPlayer
    public PlayerConfig getConfig() {
        return this.mCorePlayer.getConfig();
    }

    protected NativePlayerBase getCorePlayer() {
        return this.mCorePlayer;
    }

    @Override // com.aliyun.player.IPlayer
    public long getCurrentPosition() {
        return this.mCorePlayer.getCurrentPosition();
    }

    @Override // com.aliyun.player.IPlayer
    public long getDuration() {
        return this.mCorePlayer.getDuration();
    }

    @Override // com.aliyun.player.IPlayer
    public MediaInfo getMediaInfo() {
        return this.mOutMediaInfo;
    }

    @Override // com.aliyun.player.IPlayer
    public IPlayer.MirrorMode getMirrorMode() {
        return this.mCorePlayer.getMirrorMode();
    }

    @Override // com.aliyun.player.IPlayer
    public long getNativeContextAddr() {
        return this.mCorePlayer.getNativeContext();
    }

    @Override // com.aliyun.player.IPlayer
    public Object getOption(IPlayer.Option option) {
        return this.mCorePlayer.getOption(option);
    }

    @Override // com.aliyun.player.IPlayer
    public long getPlayedDuration() {
        return this.mCorePlayer.getPlayedDuration();
    }

    @Override // com.aliyun.player.IPlayer
    public String getPlayerName() {
        return this.mCorePlayer.getPlayerName();
    }

    @Override // com.aliyun.player.IPlayer
    public int getPlayerStatus() {
        return this.mCorePlayer.getPlayerStatus();
    }

    @Override // com.aliyun.player.IPlayer
    public String getPropertyString(IPlayer.PropertyKey propertyKey) {
        return this.mCorePlayer.getPropertyString(propertyKey.getValue());
    }

    @Override // com.aliyun.player.IPlayer
    public IPlayer.RotateMode getRotateMode() {
        return this.mCorePlayer.getRotateMode();
    }

    @Override // com.aliyun.player.IPlayer
    public IPlayer.ScaleMode getScaleMode() {
        return this.mCorePlayer.getScaleMode();
    }

    @Override // com.aliyun.player.IPlayer
    public float getSpeed() {
        return this.mCorePlayer.getSpeed();
    }

    @Override // com.aliyun.player.IPlayer
    public MediaInfo getSubMediaInfo() {
        return this.mOutSubMediaInfo;
    }

    @Override // com.aliyun.player.IPlayer
    public String getUserData() {
        return this.mCorePlayer.getUserData();
    }

    @Override // com.aliyun.player.IPlayer
    public int getVideoHeight() {
        return this.mCorePlayer.getVideoHeight();
    }

    @Override // com.aliyun.player.IPlayer
    public int getVideoRotation() {
        return (int) this.mCorePlayer.getVideoRotation();
    }

    @Override // com.aliyun.player.IPlayer
    public int getVideoWidth() {
        return this.mCorePlayer.getVideoWidth();
    }

    @Override // com.aliyun.player.IPlayer
    public float getVolume() {
        return this.mCorePlayer.getVolume();
    }

    @Override // com.aliyun.player.IPlayer
    public boolean isAutoPlay() {
        return this.mCorePlayer.isAutoPlay();
    }

    @Override // com.aliyun.player.IPlayer
    public boolean isLoop() {
        return this.mCorePlayer.isLoop();
    }

    @Override // com.aliyun.player.IPlayer
    public boolean isMute() {
        return this.mCorePlayer.isMuted();
    }

    @Override // com.aliyun.player.IPlayer
    public void pause() {
        pauseInner();
    }

    @Override // com.aliyun.player.IPlayer
    public void prepare() {
        this.mCorePlayer.prepare();
    }

    @Override // com.aliyun.player.IPlayer
    @Deprecated
    public void redraw() {
    }

    @Override // com.aliyun.player.IPlayer
    public void release() {
        setGlobalTime("protected.playEvent.user.destroy");
        releaseInner();
    }

    @Override // com.aliyun.player.IPlayer
    public void releaseAsync() {
        setGlobalTime("protected.playEvent.user.destroy");
        Logger.i(TAG, "releaseAsync");
        pause();
        setSurface(null);
        g.f12145a.execute(new Runnable() { // from class: com.aliyun.player.AVPBase.1
            @Override // java.lang.Runnable
            public void run() {
                AVPBase.this.stopInner();
                AVPBase.this.releaseInner();
            }
        });
    }

    @Override // com.aliyun.player.IPlayer
    public void reload() {
        this.mCorePlayer.reload();
    }

    @Override // com.aliyun.player.IPlayer
    public void reset() {
    }

    @Override // com.aliyun.player.IPlayer
    public void seekTo(long j2) {
        seekTo(j2, IPlayer.SeekMode.Inaccurate);
    }

    @Override // com.aliyun.player.IPlayer
    public void selectExtSubtitle(int i2, boolean z2) {
        this.mCorePlayer.selectExtSubtitle(i2, z2);
    }

    @Override // com.aliyun.player.IPlayer
    public void selectTrack(int i2) {
        this.mCorePlayer.selectTrack(i2);
    }

    @Override // com.aliyun.player.IPlayer
    public void sendCustomEvent(String str) {
        this.mCorePlayer.sendCustomEvent(str);
    }

    @Override // com.aliyun.player.IPlayer
    public void setAlphaRenderMode(IPlayer.AlphaRenderMode alphaRenderMode) {
        this.mCorePlayer.setAlphaRenderMode(alphaRenderMode);
    }

    @Override // com.aliyun.player.IPlayer
    public void setAutoPlay(boolean z2) {
        this.mCorePlayer.setAutoPlay(z2);
    }

    @Override // com.aliyun.player.IPlayer
    public void setCacheConfig(CacheConfig cacheConfig) {
        if (cacheConfig == null) {
            cacheConfig = new CacheConfig();
            cacheConfig.mEnable = false;
        }
        this.mCorePlayer.setCacheConfig(cacheConfig);
    }

    @Override // com.aliyun.player.IPlayer
    public void setConfig(PlayerConfig playerConfig) {
        this.mCorePlayer.setConfig(playerConfig);
    }

    @Override // com.aliyun.player.IPlayer
    public void setDefaultBandWidth(int i2) {
        this.mCorePlayer.setDefaultBandWidth(i2);
    }

    @Override // com.aliyun.player.IPlayer
    public void setDefaultResolution(int i2) {
        this.mCorePlayer.setDefaultResolution(i2);
    }

    @Override // com.aliyun.player.IPlayer
    public void setDisplay(SurfaceHolder surfaceHolder) {
        setSurface(surfaceHolder == null ? null : surfaceHolder.getSurface());
    }

    @Override // com.aliyun.player.IPlayer
    public void setDisplayView(AliDisplayView aliDisplayView) {
        this.mCorePlayer.setDisplayView(aliDisplayView);
    }

    @Override // com.aliyun.player.IPlayer
    public void setDrmCallback(DrmCallback drmCallback) {
        this.mCorePlayer.setDrmCallback(drmCallback);
    }

    @Override // com.aliyun.player.IPlayer
    public void setFastStart(boolean z2) {
        this.mCorePlayer.setFastStart(z2);
    }

    @Override // com.aliyun.player.IPlayer
    public void setFilterConfig(FilterConfig filterConfig) {
        this.mCorePlayer.setFilterConfig(filterConfig);
    }

    @Override // com.aliyun.player.IPlayer
    public void setFilterInvalid(String str, boolean z2) {
        this.mCorePlayer.setFilterInvalid(str, z2);
    }

    @Override // com.aliyun.player.IPlayer
    public void setIPResolveType(IPlayer.IPResolveType iPResolveType) {
        this.mCorePlayer.setIPResolveType(iPResolveType);
    }

    @Override // com.aliyun.player.IPlayer
    public void setLoop(boolean z2) {
        this.mCorePlayer.setLoop(z2);
    }

    @Override // com.aliyun.player.IPlayer
    public void setMaxAccurateSeekDelta(int i2) {
        this.mCorePlayer.setMaxAccurateSeekDelta(i2);
    }

    @Override // com.aliyun.player.IPlayer
    public void setMirrorMode(IPlayer.MirrorMode mirrorMode) {
        this.mCorePlayer.setMirrorMode(mirrorMode);
    }

    @Override // com.aliyun.player.IPlayer
    public void setMute(boolean z2) {
        this.mCorePlayer.setMute(z2);
    }

    @Override // com.aliyun.player.IPlayer
    public void setOnAVNotSyncStatusListener(IPlayer.OnAVNotSyncStatusListener onAVNotSyncStatusListener) {
        this.mOutOnAVNotSyncStatusListener = onAVNotSyncStatusListener;
    }

    @Override // com.aliyun.player.IPlayer
    public void setOnChooseTrackIndexListener(IPlayer.OnChooseTrackIndexListener onChooseTrackIndexListener) {
        this.mOutOnChooseTrackIndexListener = onChooseTrackIndexListener;
    }

    @Override // com.aliyun.player.IPlayer
    public void setOnCompletionListener(IPlayer.OnCompletionListener onCompletionListener) {
        this.mOutOnCompletionListener = onCompletionListener;
    }

    @Override // com.aliyun.player.IPlayer
    public void setOnErrorListener(IPlayer.OnErrorListener onErrorListener) {
        this.mOutOnErrorListener = onErrorListener;
    }

    @Override // com.aliyun.player.IPlayer
    public void setOnInfoListener(IPlayer.OnInfoListener onInfoListener) {
        this.mOutOnInfoListener = onInfoListener;
    }

    @Override // com.aliyun.player.IPlayer
    public void setOnLoadingStatusListener(IPlayer.OnLoadingStatusListener onLoadingStatusListener) {
        this.mOutOnLoadingStatusListener = onLoadingStatusListener;
    }

    @Override // com.aliyun.player.IPlayer
    public void setOnPreRenderFrameCallback(IPlayer.OnPreRenderFrameCallback onPreRenderFrameCallback) {
        this.mCorePlayer.setOnPreRenderFrameCallback(onPreRenderFrameCallback);
    }

    @Override // com.aliyun.player.IPlayer
    public void setOnPreparedListener(IPlayer.OnPreparedListener onPreparedListener) {
        this.mOutOnPreparedListener = onPreparedListener;
    }

    @Override // com.aliyun.player.IPlayer
    public void setOnRenderFrameCallback(IPlayer.OnRenderFrameCallback onRenderFrameCallback) {
        this.mCorePlayer.setOnRenderFrameCallback(onRenderFrameCallback);
    }

    @Override // com.aliyun.player.IPlayer
    public void setOnRenderingStartListener(IPlayer.OnRenderingStartListener onRenderingStartListener) {
        this.mOutOnRenderingStartListener = onRenderingStartListener;
    }

    @Override // com.aliyun.player.IPlayer
    public void setOnReportEventListener(IPlayer.OnReportEventListener onReportEventListener) {
        this.mOutEventListener = onReportEventListener;
    }

    @Override // com.aliyun.player.IPlayer
    public void setOnSeekCompleteListener(IPlayer.OnSeekCompleteListener onSeekCompleteListener) {
        this.mOutOnSeekEndListener = onSeekCompleteListener;
    }

    @Override // com.aliyun.player.IPlayer
    public void setOnSeiDataListener(IPlayer.OnSeiDataListener onSeiDataListener) {
        this.mOutOnSeiDataListener = onSeiDataListener;
    }

    @Override // com.aliyun.player.IPlayer
    public void setOnSnapShotListener(IPlayer.OnSnapShotListener onSnapShotListener) {
        this.mOutOnSnapShotListener = onSnapShotListener;
    }

    @Override // com.aliyun.player.IPlayer
    public void setOnStateChangedListener(IPlayer.OnStateChangedListener onStateChangedListener) {
        this.mOutOnStatusChangedListener = onStateChangedListener;
    }

    @Override // com.aliyun.player.IPlayer
    public void setOnStreamSwitchedListener(IPlayer.OnStreamSwitchedListener onStreamSwitchedListener) {
        this.mOutOnStreamSwitchedListener = onStreamSwitchedListener;
    }

    @Override // com.aliyun.player.IPlayer
    public void setOnSubTrackReadyListener(IPlayer.OnSubTrackReadyListener onSubTrackReadyListener) {
        this.mOutOnSubTrackReadyListener = onSubTrackReadyListener;
    }

    @Override // com.aliyun.player.IPlayer
    public void setOnSubtitleDisplayListener(IPlayer.OnSubtitleDisplayListener onSubtitleDisplayListener) {
        this.mOutOnSubtitleDisplayListener = onSubtitleDisplayListener;
    }

    @Override // com.aliyun.player.IPlayer
    public void setOnTrackChangedListener(IPlayer.OnTrackChangedListener onTrackChangedListener) {
        this.mOutOnTrackChangedListener = onTrackChangedListener;
    }

    @Override // com.aliyun.player.IPlayer
    public void setOnTrackReadyListener(IPlayer.OnTrackReadyListener onTrackReadyListener) {
        this.mOutOnTrackReadyListener = onTrackReadyListener;
    }

    @Override // com.aliyun.player.IPlayer
    public void setOnVideoRenderedListener(IPlayer.OnVideoRenderedListener onVideoRenderedListener) {
        NativePlayerBase nativePlayerBase;
        IPlayer.OnVideoRenderedListener onVideoRenderedListener2;
        this.mOutOnVideoRenderedListener = onVideoRenderedListener;
        if (onVideoRenderedListener != null) {
            nativePlayerBase = this.mCorePlayer;
            onVideoRenderedListener2 = this.mInnerOnVideoRenderedListener;
        } else {
            nativePlayerBase = this.mCorePlayer;
            onVideoRenderedListener2 = null;
        }
        nativePlayerBase.setOnVideoRenderedListener(onVideoRenderedListener2);
    }

    @Override // com.aliyun.player.IPlayer
    public void setOnVideoSizeChangedListener(IPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener) {
        this.mOutOnVideoSizeChangedListener = onVideoSizeChangedListener;
    }

    @Override // com.aliyun.player.IPlayer
    public void setOption(int i2, int i3) {
        this.mCorePlayer.setOption("player_option_" + String.valueOf(i2) + "_int", String.valueOf(i3));
    }

    @Override // com.aliyun.player.IPlayer
    public void setOutputAudioChannel(IPlayer.OutputAudioChannel outputAudioChannel) {
        this.mCorePlayer.setOutputAudioChannel(outputAudioChannel.getValue());
    }

    @Override // com.aliyun.player.IPlayer
    public void setPlayerScene(PlayerScene playerScene) {
        this.mCorePlayer.setPlayerScene(playerScene);
    }

    @Override // com.aliyun.player.IPlayer
    public void setPreferPlayerName(String str) {
        this.mCorePlayer.setPreferPlayerName(str);
    }

    @Override // com.aliyun.player.IPlayer
    public void setRenderFrameCallbackConfig(IPlayer.RenderFrameCallbackConfig renderFrameCallbackConfig) {
        this.mCorePlayer.setRenderFrameCallbackConfig(renderFrameCallbackConfig);
    }

    @Override // com.aliyun.player.IPlayer
    public void setRotateMode(IPlayer.RotateMode rotateMode) {
        this.mCorePlayer.setRotateMode(rotateMode);
    }

    @Override // com.aliyun.player.IPlayer
    public void setScaleMode(IPlayer.ScaleMode scaleMode) {
        this.mCorePlayer.setScaleMode(scaleMode);
    }

    @Override // com.aliyun.player.IPlayer
    public void setSpeed(float f2) {
        this.mCorePlayer.setSpeed(f2);
    }

    @Override // com.aliyun.player.IPlayer
    public void setStartTime(long j2, IPlayer.SeekMode seekMode) {
        this.mCorePlayer.setStartTime(j2, seekMode.getValue());
    }

    @Override // com.aliyun.player.IPlayer
    public void setStreamDelayTime(int i2, int i3) {
        this.mCorePlayer.setStreamDelayTime(i2, i3);
    }

    @Override // com.aliyun.player.IPlayer
    public void setSurface(Surface surface) {
        this.mCorePlayer.setSurface(surface);
    }

    @Override // com.aliyun.player.IPlayer
    public void setTraceId(String str) {
        this.mTraceID = str;
        this.mCorePlayer.setTraceId(str);
    }

    @Override // com.aliyun.player.IPlayer
    public void setUserData(String str) {
        this.mCorePlayer.setUserData(str);
    }

    @Override // com.aliyun.player.IPlayer
    public void setVideoBackgroundColor(int i2) {
        this.mCorePlayer.setVideoBackgroundColor(i2);
    }

    @Override // com.aliyun.player.IPlayer
    public void setVideoTag(int[] iArr) {
        this.mCorePlayer.setVideoTag(iArr);
    }

    @Override // com.aliyun.player.IPlayer
    public void setVolume(float f2) {
        this.mCorePlayer.setVolume(f2);
    }

    @Override // com.aliyun.player.IPlayer
    public void snapshot() {
        this.mCorePlayer.snapShot();
    }

    @Override // com.aliyun.player.IPlayer
    public void start() {
        startInner();
    }

    @Override // com.aliyun.player.IPlayer
    public void stop() {
        setGlobalTime("protected.playEvent.user.stop");
        stopInner();
    }

    protected void stopInner() {
        this.mCorePlayer.stop();
    }

    @Override // com.aliyun.player.IPlayer
    public void surfaceChanged() {
        this.mCorePlayer.surfaceChanged();
    }

    @Override // com.aliyun.player.IPlayer
    public void switchStream(String str) {
        this.mCorePlayer.switchStream(str);
    }

    @Override // com.aliyun.player.IPlayer
    public void updateFilterConfig(String str, FilterConfig.FilterOptions filterOptions) {
        this.mCorePlayer.updateFilterConfig(str, filterOptions);
    }

    @Override // com.aliyun.player.IPlayer
    public TrackInfo currentTrack(TrackInfo.Type type) {
        return this.mCorePlayer.getCurrentTrackInfo(type.ordinal());
    }

    @Override // com.aliyun.player.IPlayer
    public String getCacheFilePath(String str, String str2, String str3, int i2) {
        return this.mCorePlayer.getCacheFilePath(str, str2, str3, i2);
    }

    @Override // com.aliyun.player.IPlayer
    public void seekTo(long j2, IPlayer.SeekMode seekMode) {
        this.mCorePlayer.seekTo(j2, seekMode.getValue());
    }

    @Override // com.aliyun.player.IPlayer
    public void selectTrack(int i2, boolean z2) {
        this.mCorePlayer.selectTrack(i2, z2);
    }

    @Override // com.aliyun.player.IPlayer
    public void setOption(int i2, String str) {
        this.mCorePlayer.setOption("player_option_" + String.valueOf(i2) + "_str", str);
    }
}
