package com.aliyun.downloader;

import android.content.Context;
import com.aliyun.downloader.AliMediaDownloader;
import com.aliyun.downloader.nativeclass.JniDownloader;
import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.player.nativeclass.MediaInfo;
import com.aliyun.player.source.VidAuth;
import com.aliyun.player.source.VidSts;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
class ApsaraDownloader extends AliMediaDownloader {
    private Context mAppContext;
    private JniDownloader mCoreDownloader;
    private AliMediaDownloader.OnCompletionListener mOuterCompletionListener = null;
    private AliMediaDownloader.OnErrorListener mOuterOnErrorListener = null;
    private AliMediaDownloader.OnPreparedListener mOuterOnPreparedListener = null;
    private AliMediaDownloader.OnProgressListener mOuterOnProgressListener = null;

    private static class InnerCompletionListener implements AliMediaDownloader.OnCompletionListener {
        private WeakReference<ApsaraDownloader> downloaderWk;

        private InnerCompletionListener(ApsaraDownloader apsaraDownloader) {
            this.downloaderWk = new WeakReference<>(apsaraDownloader);
        }

        @Override // com.aliyun.downloader.AliMediaDownloader.OnCompletionListener
        public void onCompletion() {
            ApsaraDownloader apsaraDownloader = this.downloaderWk.get();
            if (apsaraDownloader != null) {
                apsaraDownloader.onCompletion();
            }
        }
    }

    private static class InnerErrorListener implements AliMediaDownloader.OnErrorListener {
        private WeakReference<ApsaraDownloader> downloaderWk;

        private InnerErrorListener(ApsaraDownloader apsaraDownloader) {
            this.downloaderWk = new WeakReference<>(apsaraDownloader);
        }

        @Override // com.aliyun.downloader.AliMediaDownloader.OnErrorListener
        public void onError(ErrorInfo errorInfo) {
            ApsaraDownloader apsaraDownloader = this.downloaderWk.get();
            if (apsaraDownloader != null) {
                apsaraDownloader.onError(errorInfo);
            }
        }
    }

    private static class InnerOnProgressListener implements AliMediaDownloader.OnProgressListener {
        private WeakReference<ApsaraDownloader> downloaderWk;

        private InnerOnProgressListener(ApsaraDownloader apsaraDownloader) {
            this.downloaderWk = new WeakReference<>(apsaraDownloader);
        }

        @Override // com.aliyun.downloader.AliMediaDownloader.OnProgressListener
        public void onDownloadingProgress(int i2) {
            ApsaraDownloader apsaraDownloader = this.downloaderWk.get();
            if (apsaraDownloader != null) {
                apsaraDownloader.onDownloadingProgress(i2);
            }
        }

        @Override // com.aliyun.downloader.AliMediaDownloader.OnProgressListener
        public void onProcessingProgress(int i2) {
            ApsaraDownloader apsaraDownloader = this.downloaderWk.get();
            if (apsaraDownloader != null) {
                apsaraDownloader.onProcessingProgress(i2);
            }
        }
    }

    private static class InnerPreparedListener implements AliMediaDownloader.OnPreparedListener {
        private WeakReference<ApsaraDownloader> downloaderWk;

        private InnerPreparedListener(ApsaraDownloader apsaraDownloader) {
            this.downloaderWk = new WeakReference<>(apsaraDownloader);
        }

        @Override // com.aliyun.downloader.AliMediaDownloader.OnPreparedListener
        public void onPrepared(MediaInfo mediaInfo) {
            ApsaraDownloader apsaraDownloader = this.downloaderWk.get();
            if (apsaraDownloader != null) {
                apsaraDownloader.onPrepared(mediaInfo);
            }
        }
    }

    public ApsaraDownloader(Context context) {
        this.mAppContext = context;
        JniDownloader jniDownloader = new JniDownloader(context);
        this.mCoreDownloader = jniDownloader;
        jniDownloader.setOnCompletionListener(new InnerCompletionListener());
        this.mCoreDownloader.setOnErrorListener(new InnerErrorListener());
        this.mCoreDownloader.setOnPreparedListener(new InnerPreparedListener());
        this.mCoreDownloader.setOnProgressListener(new InnerOnProgressListener());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCompletion() {
        AliMediaDownloader.OnCompletionListener onCompletionListener = this.mOuterCompletionListener;
        if (onCompletionListener != null) {
            onCompletionListener.onCompletion();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDownloadingProgress(int i2) {
        AliMediaDownloader.OnProgressListener onProgressListener = this.mOuterOnProgressListener;
        if (onProgressListener != null) {
            onProgressListener.onDownloadingProgress(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onError(ErrorInfo errorInfo) {
        AliMediaDownloader.OnErrorListener onErrorListener = this.mOuterOnErrorListener;
        if (onErrorListener != null) {
            onErrorListener.onError(errorInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPrepared(MediaInfo mediaInfo) {
        AliMediaDownloader.OnPreparedListener onPreparedListener = this.mOuterOnPreparedListener;
        if (onPreparedListener != null) {
            onPreparedListener.onPrepared(mediaInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onProcessingProgress(int i2) {
        AliMediaDownloader.OnProgressListener onProgressListener = this.mOuterOnProgressListener;
        if (onProgressListener != null) {
            onProgressListener.onProcessingProgress(i2);
        }
    }

    @Override // com.aliyun.downloader.AliMediaDownloader
    public void deleteFile() {
        this.mCoreDownloader.deleteFile();
    }

    @Override // com.aliyun.downloader.AliMediaDownloader
    public String getFilePath() {
        return this.mCoreDownloader.getFilePath();
    }

    @Override // com.aliyun.downloader.AliMediaDownloader
    public void prepare(VidAuth vidAuth) {
        this.mCoreDownloader.prepare(vidAuth);
    }

    @Override // com.aliyun.downloader.AliMediaDownloader
    public void release() {
        this.mCoreDownloader.release();
    }

    @Override // com.aliyun.downloader.AliMediaDownloader
    public void selectItem(int i2) {
        this.mCoreDownloader.selectItem(i2);
    }

    @Override // com.aliyun.downloader.AliMediaDownloader
    public void setDownloaderConfig(DownloaderConfig downloaderConfig) {
        this.mCoreDownloader.setDownloaderConfig(downloaderConfig);
    }

    @Override // com.aliyun.downloader.AliMediaDownloader
    public void setOnCompletionListener(AliMediaDownloader.OnCompletionListener onCompletionListener) {
        this.mOuterCompletionListener = onCompletionListener;
    }

    @Override // com.aliyun.downloader.AliMediaDownloader
    public void setOnErrorListener(AliMediaDownloader.OnErrorListener onErrorListener) {
        this.mOuterOnErrorListener = onErrorListener;
    }

    @Override // com.aliyun.downloader.AliMediaDownloader
    public void setOnPreparedListener(AliMediaDownloader.OnPreparedListener onPreparedListener) {
        this.mOuterOnPreparedListener = onPreparedListener;
    }

    @Override // com.aliyun.downloader.AliMediaDownloader
    public void setOnProgressListener(AliMediaDownloader.OnProgressListener onProgressListener) {
        this.mOuterOnProgressListener = onProgressListener;
    }

    @Override // com.aliyun.downloader.AliMediaDownloader
    public void setSaveDir(String str) {
        this.mCoreDownloader.setSaveDir(str);
    }

    @Override // com.aliyun.downloader.AliMediaDownloader
    public void start() {
        this.mCoreDownloader.start();
    }

    @Override // com.aliyun.downloader.AliMediaDownloader
    public void stop() {
        this.mCoreDownloader.stop();
    }

    @Override // com.aliyun.downloader.AliMediaDownloader
    public void updateSource(VidAuth vidAuth) {
        this.mCoreDownloader.updateSource(vidAuth);
    }

    @Override // com.aliyun.downloader.AliMediaDownloader
    public void prepare(VidSts vidSts) {
        this.mCoreDownloader.prepare(vidSts);
    }

    @Override // com.aliyun.downloader.AliMediaDownloader
    public void updateSource(VidSts vidSts) {
        this.mCoreDownloader.updateSource(vidSts);
    }
}
