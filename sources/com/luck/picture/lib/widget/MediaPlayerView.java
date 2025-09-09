package com.luck.picture.lib.widget;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.luck.picture.lib.config.PictureMimeType;
import java.io.IOException;

/* loaded from: classes4.dex */
public class MediaPlayerView extends FrameLayout implements SurfaceHolder.Callback {
    private MediaPlayer mediaPlayer;
    private VideoSurfaceView surfaceView;

    public static class VideoSurfaceView extends SurfaceView {
        private int videoHeight;
        private int videoWidth;

        public VideoSurfaceView(Context context) {
            this(context, null);
        }

        public void adjustVideoSize(int i2, int i3) {
            if (i2 == 0 || i3 == 0) {
                return;
            }
            this.videoWidth = i2;
            this.videoHeight = i3;
            getHolder().setFixedSize(i2, i3);
            requestLayout();
        }

        @Override // android.view.SurfaceView, android.view.View
        protected void onMeasure(int i2, int i3) {
            int i4;
            int defaultSize = View.getDefaultSize(this.videoWidth, i2);
            int defaultSize2 = View.getDefaultSize(this.videoHeight, i3);
            if (this.videoWidth > 0 && this.videoHeight > 0) {
                int mode = View.MeasureSpec.getMode(i2);
                int size = View.MeasureSpec.getSize(i2);
                int mode2 = View.MeasureSpec.getMode(i3);
                int size2 = View.MeasureSpec.getSize(i3);
                if (mode == 1073741824 && mode2 == 1073741824) {
                    int i5 = this.videoWidth;
                    int i6 = i5 * size2;
                    int i7 = this.videoHeight;
                    if (i6 < size * i7) {
                        defaultSize = (i5 * size2) / i7;
                        defaultSize2 = size2;
                    } else {
                        if (i5 * size2 > size * i7) {
                            defaultSize2 = (i7 * size) / i5;
                            defaultSize = size;
                        }
                        defaultSize = size;
                        defaultSize2 = size2;
                    }
                } else if (mode == 1073741824) {
                    int i8 = (this.videoHeight * size) / this.videoWidth;
                    if (mode2 != Integer.MIN_VALUE || i8 <= size2) {
                        defaultSize2 = i8;
                        defaultSize = size;
                    }
                    defaultSize = size;
                    defaultSize2 = size2;
                } else {
                    if (mode2 == 1073741824) {
                        i4 = (this.videoWidth * size2) / this.videoHeight;
                        if (mode == Integer.MIN_VALUE && i4 > size) {
                            defaultSize = size;
                        }
                        defaultSize2 = size2;
                    } else {
                        int i9 = this.videoWidth;
                        int i10 = this.videoHeight;
                        if (mode2 != Integer.MIN_VALUE || i10 <= size2) {
                            i4 = i9;
                            size2 = i10;
                        } else {
                            i4 = (size2 * i9) / i10;
                        }
                        if (mode == Integer.MIN_VALUE && i4 > size) {
                            defaultSize2 = (i10 * size) / i9;
                            defaultSize = size;
                        }
                    }
                    defaultSize = i4;
                    defaultSize2 = size2;
                }
            }
            setMeasuredDimension(defaultSize, defaultSize2);
        }

        public VideoSurfaceView(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, 0);
        }

        public VideoSurfaceView(Context context, AttributeSet attributeSet, int i2) {
            super(context, attributeSet, i2);
        }
    }

    public MediaPlayerView(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {
        this.surfaceView = new VideoSurfaceView(getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
        layoutParams.gravity = 17;
        this.surfaceView.setLayoutParams(layoutParams);
        addView(this.surfaceView);
        SurfaceHolder holder = this.surfaceView.getHolder();
        holder.setFormat(-2);
        holder.addCallback(this);
    }

    public void clearCanvas() {
        this.surfaceView.getHolder().setFormat(-1);
        this.surfaceView.getHolder().setFormat(-2);
    }

    public MediaPlayer getMediaPlayer() {
        return this.mediaPlayer;
    }

    public VideoSurfaceView getSurfaceView() {
        return this.surfaceView;
    }

    public MediaPlayer initMediaPlayer() {
        if (this.mediaPlayer == null) {
            this.mediaPlayer = new MediaPlayer();
        }
        this.mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() { // from class: com.luck.picture.lib.widget.MediaPlayerView.1
            @Override // android.media.MediaPlayer.OnVideoSizeChangedListener
            public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i2, int i3) {
                MediaPlayerView.this.surfaceView.adjustVideoSize(mediaPlayer.getVideoWidth(), mediaPlayer.getVideoHeight());
            }
        });
        return this.mediaPlayer;
    }

    public void release() {
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.release();
            this.mediaPlayer.setOnPreparedListener(null);
            this.mediaPlayer.setOnCompletionListener(null);
            this.mediaPlayer.setOnErrorListener(null);
            this.mediaPlayer = null;
        }
    }

    public void start(String str) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        try {
            if (PictureMimeType.isContent(str)) {
                this.mediaPlayer.setDataSource(getContext(), Uri.parse(str));
            } else {
                this.mediaPlayer.setDataSource(str);
            }
            this.mediaPlayer.prepareAsync();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        this.mediaPlayer.setAudioStreamType(3);
        this.mediaPlayer.setDisplay(surfaceHolder);
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
    }

    public MediaPlayerView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public MediaPlayerView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        init();
    }
}
