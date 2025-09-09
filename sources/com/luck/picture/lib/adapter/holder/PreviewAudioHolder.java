package com.luck.picture.lib.adapter.holder;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.luck.picture.lib.R;
import com.luck.picture.lib.adapter.holder.BasePreviewHolder;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.photoview.OnViewTapListener;
import com.luck.picture.lib.utils.DateUtils;
import com.luck.picture.lib.utils.DensityUtil;
import com.luck.picture.lib.utils.DoubleUtils;
import com.luck.picture.lib.utils.PictureFileUtils;
import java.io.IOException;

/* loaded from: classes4.dex */
public class PreviewAudioHolder extends BasePreviewHolder {
    private static final long MAX_BACK_FAST_MS = 3000;
    private static final long MAX_UPDATE_INTERVAL_MS = 1000;
    private static final long MIN_CURRENT_POSITION = 1000;
    private boolean isPausePlayer;
    public ImageView ivPlayBack;
    public ImageView ivPlayButton;
    public ImageView ivPlayFast;
    private final Handler mHandler;
    private final MediaPlayer.OnCompletionListener mPlayCompletionListener;
    private final MediaPlayer.OnErrorListener mPlayErrorListener;
    private final MediaPlayer.OnPreparedListener mPlayPreparedListener;
    private MediaPlayer mPlayer;
    public Runnable mTickerRunnable;
    public SeekBar seekBar;
    public TextView tvAudioName;
    public TextView tvCurrentTime;
    public TextView tvTotalDuration;

    public PreviewAudioHolder(@NonNull View view) {
        super(view);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mPlayer = new MediaPlayer();
        this.isPausePlayer = false;
        this.mTickerRunnable = new Runnable() { // from class: com.luck.picture.lib.adapter.holder.PreviewAudioHolder.1
            @Override // java.lang.Runnable
            public void run() {
                long currentPosition = PreviewAudioHolder.this.mPlayer.getCurrentPosition();
                String durationTime = DateUtils.formatDurationTime(currentPosition);
                if (!TextUtils.equals(durationTime, PreviewAudioHolder.this.tvCurrentTime.getText())) {
                    PreviewAudioHolder.this.tvCurrentTime.setText(durationTime);
                    if (PreviewAudioHolder.this.mPlayer.getDuration() - currentPosition > 1000) {
                        PreviewAudioHolder.this.seekBar.setProgress((int) currentPosition);
                    } else {
                        PreviewAudioHolder previewAudioHolder = PreviewAudioHolder.this;
                        previewAudioHolder.seekBar.setProgress(previewAudioHolder.mPlayer.getDuration());
                    }
                }
                PreviewAudioHolder.this.mHandler.postDelayed(this, 1000 - (currentPosition % 1000));
            }
        };
        this.mPlayCompletionListener = new MediaPlayer.OnCompletionListener() { // from class: com.luck.picture.lib.adapter.holder.PreviewAudioHolder.10
            @Override // android.media.MediaPlayer.OnCompletionListener
            public void onCompletion(MediaPlayer mediaPlayer) throws IllegalStateException {
                PreviewAudioHolder.this.stopUpdateProgress();
                PreviewAudioHolder.this.resetMediaPlayer();
                PreviewAudioHolder.this.playerDefaultUI(true);
            }
        };
        this.mPlayErrorListener = new MediaPlayer.OnErrorListener() { // from class: com.luck.picture.lib.adapter.holder.PreviewAudioHolder.11
            @Override // android.media.MediaPlayer.OnErrorListener
            public boolean onError(MediaPlayer mediaPlayer, int i2, int i3) throws IllegalStateException {
                PreviewAudioHolder.this.resetMediaPlayer();
                PreviewAudioHolder.this.playerDefaultUI(true);
                return false;
            }
        };
        this.mPlayPreparedListener = new MediaPlayer.OnPreparedListener() { // from class: com.luck.picture.lib.adapter.holder.PreviewAudioHolder.12
            @Override // android.media.MediaPlayer.OnPreparedListener
            public void onPrepared(MediaPlayer mediaPlayer) throws IllegalStateException {
                if (mediaPlayer.isPlaying()) {
                    PreviewAudioHolder.this.seekBar.setMax(mediaPlayer.getDuration());
                    PreviewAudioHolder.this.startUpdateProgress();
                    PreviewAudioHolder.this.playerIngUI();
                } else {
                    PreviewAudioHolder.this.stopUpdateProgress();
                    PreviewAudioHolder.this.resetMediaPlayer();
                    PreviewAudioHolder.this.playerDefaultUI(true);
                }
            }
        };
        this.ivPlayButton = (ImageView) view.findViewById(R.id.iv_play_video);
        this.tvAudioName = (TextView) view.findViewById(R.id.tv_audio_name);
        this.tvCurrentTime = (TextView) view.findViewById(R.id.tv_current_time);
        this.tvTotalDuration = (TextView) view.findViewById(R.id.tv_total_duration);
        this.seekBar = (SeekBar) view.findViewById(R.id.music_seek_bar);
        this.ivPlayBack = (ImageView) view.findViewById(R.id.iv_play_back);
        this.ivPlayFast = (ImageView) view.findViewById(R.id.iv_play_fast);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fastAudioPlay() throws IllegalStateException {
        long progress = this.seekBar.getProgress() + 3000;
        if (progress >= this.seekBar.getMax()) {
            SeekBar seekBar = this.seekBar;
            seekBar.setProgress(seekBar.getMax());
        } else {
            this.seekBar.setProgress((int) progress);
        }
        setCurrentPlayTime(this.seekBar.getProgress());
        this.mPlayer.seekTo(this.seekBar.getProgress());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pausePlayer() throws IllegalStateException {
        this.mPlayer.pause();
        this.isPausePlayer = true;
        playerDefaultUI(false);
        stopUpdateProgress();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playerDefaultUI(boolean z2) {
        stopUpdateProgress();
        if (z2) {
            this.seekBar.setProgress(0);
            this.tvCurrentTime.setText("00:00");
        }
        setBackFastUI(false);
        this.ivPlayButton.setImageResource(R.drawable.ps_ic_audio_play);
        BasePreviewHolder.OnPreviewEventListener onPreviewEventListener = this.f18979f;
        if (onPreviewEventListener != null) {
            onPreviewEventListener.onPreviewVideoTitle(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playerIngUI() {
        startUpdateProgress();
        setBackFastUI(true);
        this.ivPlayButton.setImageResource(R.drawable.ps_ic_audio_stop);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetMediaPlayer() throws IllegalStateException {
        this.isPausePlayer = false;
        this.mPlayer.stop();
        this.mPlayer.reset();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resumePlayer() throws IllegalStateException {
        this.mPlayer.seekTo(this.seekBar.getProgress());
        this.mPlayer.start();
        startUpdateProgress();
        playerIngUI();
    }

    private void setBackFastUI(boolean z2) {
        this.ivPlayBack.setEnabled(z2);
        this.ivPlayFast.setEnabled(z2);
        if (z2) {
            this.ivPlayBack.setAlpha(1.0f);
            this.ivPlayFast.setAlpha(1.0f);
        } else {
            this.ivPlayBack.setAlpha(0.5f);
            this.ivPlayFast.setAlpha(0.5f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCurrentPlayTime(int i2) {
        this.tvCurrentTime.setText(DateUtils.formatDurationTime(i2));
    }

    private void setMediaPlayerListener() {
        this.mPlayer.setOnCompletionListener(this.mPlayCompletionListener);
        this.mPlayer.setOnErrorListener(this.mPlayErrorListener);
        this.mPlayer.setOnPreparedListener(this.mPlayPreparedListener);
    }

    private void setNullMediaPlayerListener() {
        this.mPlayer.setOnCompletionListener(null);
        this.mPlayer.setOnErrorListener(null);
        this.mPlayer.setOnPreparedListener(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void slowAudioPlay() throws IllegalStateException {
        long progress = this.seekBar.getProgress() - 3000;
        if (progress <= 0) {
            this.seekBar.setProgress(0);
        } else {
            this.seekBar.setProgress((int) progress);
        }
        setCurrentPlayTime(this.seekBar.getProgress());
        this.mPlayer.seekTo(this.seekBar.getProgress());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startPlayer(String str) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        try {
            if (PictureMimeType.isContent(str)) {
                this.mPlayer.setDataSource(this.itemView.getContext(), Uri.parse(str));
            } else {
                this.mPlayer.setDataSource(str);
            }
            this.mPlayer.prepare();
            this.mPlayer.seekTo(this.seekBar.getProgress());
            this.mPlayer.start();
            this.isPausePlayer = false;
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startUpdateProgress() {
        this.mHandler.post(this.mTickerRunnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopUpdateProgress() {
        this.mHandler.removeCallbacks(this.mTickerRunnable);
    }

    @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder
    protected void a(View view) {
    }

    @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder
    public void bindData(final LocalMedia localMedia, int i2) {
        final String availablePath = localMedia.getAvailablePath();
        String yearDataFormat = DateUtils.getYearDataFormat(localMedia.getDateAddedTime());
        String accurateUnitFileSize = PictureFileUtils.formatAccurateUnitFileSize(localMedia.getSize());
        c(localMedia, -1, -1);
        StringBuilder sb = new StringBuilder();
        sb.append(localMedia.getFileName());
        sb.append("\n");
        sb.append(yearDataFormat);
        sb.append(" - ");
        sb.append(accurateUnitFileSize);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(sb.toString());
        String str = yearDataFormat + " - " + accurateUnitFileSize;
        int iIndexOf = sb.indexOf(str);
        int length = str.length() + iIndexOf;
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(DensityUtil.dip2px(this.itemView.getContext(), 12.0f)), iIndexOf, length, 17);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(-10132123), iIndexOf, length, 17);
        this.tvAudioName.setText(spannableStringBuilder);
        this.tvTotalDuration.setText(DateUtils.formatDurationTime(localMedia.getDuration()));
        this.seekBar.setMax((int) localMedia.getDuration());
        setBackFastUI(false);
        this.ivPlayBack.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.adapter.holder.PreviewAudioHolder.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) throws IllegalStateException {
                PreviewAudioHolder.this.slowAudioPlay();
            }
        });
        this.ivPlayFast.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.adapter.holder.PreviewAudioHolder.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) throws IllegalStateException {
                PreviewAudioHolder.this.fastAudioPlay();
            }
        });
        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.luck.picture.lib.adapter.holder.PreviewAudioHolder.6
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i3, boolean z2) throws IllegalStateException {
                if (z2) {
                    seekBar.setProgress(i3);
                    PreviewAudioHolder.this.setCurrentPlayTime(i3);
                    if (PreviewAudioHolder.this.isPlaying()) {
                        PreviewAudioHolder.this.mPlayer.seekTo(seekBar.getProgress());
                    }
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.adapter.holder.PreviewAudioHolder.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BasePreviewHolder.OnPreviewEventListener onPreviewEventListener = PreviewAudioHolder.this.f18979f;
                if (onPreviewEventListener != null) {
                    onPreviewEventListener.onBackPressed();
                }
            }
        });
        this.ivPlayButton.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.adapter.holder.PreviewAudioHolder.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                try {
                    if (DoubleUtils.isFastDoubleClick()) {
                        return;
                    }
                    PreviewAudioHolder.this.f18979f.onPreviewVideoTitle(localMedia.getFileName());
                    if (PreviewAudioHolder.this.isPlaying()) {
                        PreviewAudioHolder.this.pausePlayer();
                    } else if (PreviewAudioHolder.this.isPausePlayer) {
                        PreviewAudioHolder.this.resumePlayer();
                    } else {
                        PreviewAudioHolder.this.startPlayer(availablePath);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
        this.itemView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.luck.picture.lib.adapter.holder.PreviewAudioHolder.9
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                BasePreviewHolder.OnPreviewEventListener onPreviewEventListener = PreviewAudioHolder.this.f18979f;
                if (onPreviewEventListener == null) {
                    return false;
                }
                onPreviewEventListener.onLongPressDownload(localMedia);
                return false;
            }
        });
    }

    @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder
    protected void c(LocalMedia localMedia, int i2, int i3) {
        this.tvAudioName.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ps_ic_audio_play_cover, 0, 0);
    }

    @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder
    protected void d() {
        this.coverImageView.setOnViewTapListener(new OnViewTapListener() { // from class: com.luck.picture.lib.adapter.holder.PreviewAudioHolder.2
            @Override // com.luck.picture.lib.photoview.OnViewTapListener
            public void onViewTap(View view, float f2, float f3) {
                BasePreviewHolder.OnPreviewEventListener onPreviewEventListener = PreviewAudioHolder.this.f18979f;
                if (onPreviewEventListener != null) {
                    onPreviewEventListener.onBackPressed();
                }
            }
        });
    }

    @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder
    protected void e(final LocalMedia localMedia) {
        this.coverImageView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.luck.picture.lib.adapter.holder.PreviewAudioHolder.3
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                BasePreviewHolder.OnPreviewEventListener onPreviewEventListener = PreviewAudioHolder.this.f18979f;
                if (onPreviewEventListener == null) {
                    return false;
                }
                onPreviewEventListener.onLongPressDownload(localMedia);
                return false;
            }
        });
    }

    @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder
    public boolean isPlaying() {
        MediaPlayer mediaPlayer = this.mPlayer;
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder
    public void onViewAttachedToWindow() {
        this.isPausePlayer = false;
        setMediaPlayerListener();
        playerDefaultUI(true);
    }

    @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder
    public void onViewDetachedFromWindow() throws IllegalStateException {
        this.isPausePlayer = false;
        this.mHandler.removeCallbacks(this.mTickerRunnable);
        setNullMediaPlayerListener();
        resetMediaPlayer();
        playerDefaultUI(true);
    }

    @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder
    public void release() {
        this.mHandler.removeCallbacks(this.mTickerRunnable);
        if (this.mPlayer != null) {
            setNullMediaPlayerListener();
            this.mPlayer.release();
            this.mPlayer = null;
        }
    }

    @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder
    public void resumePausePlay() throws IllegalStateException {
        if (isPlaying()) {
            pausePlayer();
        } else {
            resumePlayer();
        }
    }
}
