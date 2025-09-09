package com.luck.picture.lib.adapter.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.luck.picture.lib.R;
import com.luck.picture.lib.adapter.holder.BasePreviewHolder;
import com.luck.picture.lib.config.SelectorConfig;
import com.luck.picture.lib.engine.MediaPlayerEngine;
import com.luck.picture.lib.engine.VideoPlayerEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnPlayerListener;
import com.luck.picture.lib.photoview.OnViewTapListener;
import com.luck.picture.lib.utils.IntentUtils;

/* loaded from: classes4.dex */
public class PreviewVideoHolder extends BasePreviewHolder {
    private boolean isPlayed;
    public ImageView ivPlayButton;
    private final OnPlayerListener mPlayerListener;
    public ProgressBar progress;
    public View videoPlayer;

    public PreviewVideoHolder(@NonNull View view) {
        super(view);
        this.isPlayed = false;
        this.mPlayerListener = new OnPlayerListener() { // from class: com.luck.picture.lib.adapter.holder.PreviewVideoHolder.5
            @Override // com.luck.picture.lib.interfaces.OnPlayerListener
            public void onPlayerEnd() {
                PreviewVideoHolder.this.playerDefaultUI();
            }

            @Override // com.luck.picture.lib.interfaces.OnPlayerListener
            public void onPlayerError() {
                PreviewVideoHolder.this.playerDefaultUI();
            }

            @Override // com.luck.picture.lib.interfaces.OnPlayerListener
            public void onPlayerLoading() {
                PreviewVideoHolder.this.progress.setVisibility(0);
            }

            @Override // com.luck.picture.lib.interfaces.OnPlayerListener
            public void onPlayerReady() {
                PreviewVideoHolder.this.playerIngUI();
            }
        };
        this.ivPlayButton = (ImageView) view.findViewById(R.id.iv_play_video);
        this.progress = (ProgressBar) view.findViewById(R.id.progress);
        this.ivPlayButton.setVisibility(this.f18978e.isPreviewZoomEffect ? 8 : 0);
        SelectorConfig selectorConfig = this.f18978e;
        if (selectorConfig.videoPlayerEngine == null) {
            selectorConfig.videoPlayerEngine = new MediaPlayerEngine();
        }
        View viewOnCreateVideoPlayer = this.f18978e.videoPlayerEngine.onCreateVideoPlayer(view.getContext());
        this.videoPlayer = viewOnCreateVideoPlayer;
        if (viewOnCreateVideoPlayer == null) {
            throw new NullPointerException("onCreateVideoPlayer cannot be empty,Please implement " + VideoPlayerEngine.class);
        }
        if (viewOnCreateVideoPlayer.getLayoutParams() == null) {
            this.videoPlayer.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        }
        ViewGroup viewGroup = (ViewGroup) view;
        if (viewGroup.indexOfChild(this.videoPlayer) != -1) {
            viewGroup.removeView(this.videoPlayer);
        }
        viewGroup.addView(this.videoPlayer, 0);
        this.videoPlayer.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchPlay() {
        if (!this.isPlayed) {
            startPlay();
        } else if (isPlaying()) {
            onPause();
        } else {
            onResume();
        }
    }

    private void onResume() {
        this.ivPlayButton.setVisibility(8);
        VideoPlayerEngine videoPlayerEngine = this.f18978e.videoPlayerEngine;
        if (videoPlayerEngine != null) {
            videoPlayerEngine.onResume(this.videoPlayer);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playerDefaultUI() {
        this.isPlayed = false;
        this.ivPlayButton.setVisibility(0);
        this.progress.setVisibility(8);
        this.coverImageView.setVisibility(0);
        this.videoPlayer.setVisibility(8);
        BasePreviewHolder.OnPreviewEventListener onPreviewEventListener = this.f18979f;
        if (onPreviewEventListener != null) {
            onPreviewEventListener.onPreviewVideoTitle(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playerIngUI() {
        this.progress.setVisibility(8);
        this.ivPlayButton.setVisibility(8);
        this.coverImageView.setVisibility(8);
        this.videoPlayer.setVisibility(0);
    }

    @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder
    protected void a(View view) {
    }

    @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder
    public void bindData(LocalMedia localMedia, int i2) {
        super.bindData(localMedia, i2);
        g(localMedia);
        this.ivPlayButton.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.adapter.holder.PreviewVideoHolder.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PreviewVideoHolder previewVideoHolder = PreviewVideoHolder.this;
                if (previewVideoHolder.f18978e.isPauseResumePlay) {
                    previewVideoHolder.dispatchPlay();
                } else {
                    previewVideoHolder.startPlay();
                }
            }
        });
        this.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.adapter.holder.PreviewVideoHolder.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PreviewVideoHolder previewVideoHolder = PreviewVideoHolder.this;
                if (previewVideoHolder.f18978e.isPauseResumePlay) {
                    previewVideoHolder.dispatchPlay();
                    return;
                }
                BasePreviewHolder.OnPreviewEventListener onPreviewEventListener = previewVideoHolder.f18979f;
                if (onPreviewEventListener != null) {
                    onPreviewEventListener.onBackPressed();
                }
            }
        });
    }

    @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder
    protected void c(LocalMedia localMedia, int i2, int i3) {
        if (this.f18978e.imageEngine != null) {
            String availablePath = localMedia.getAvailablePath();
            if (i2 == -1 && i3 == -1) {
                this.f18978e.imageEngine.loadImage(this.itemView.getContext(), availablePath, this.coverImageView);
            } else {
                this.f18978e.imageEngine.loadImage(this.itemView.getContext(), this.coverImageView, availablePath, i2, i3);
            }
        }
    }

    @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder
    protected void d() {
        this.coverImageView.setOnViewTapListener(new OnViewTapListener() { // from class: com.luck.picture.lib.adapter.holder.PreviewVideoHolder.1
            @Override // com.luck.picture.lib.photoview.OnViewTapListener
            public void onViewTap(View view, float f2, float f3) {
                BasePreviewHolder.OnPreviewEventListener onPreviewEventListener = PreviewVideoHolder.this.f18979f;
                if (onPreviewEventListener != null) {
                    onPreviewEventListener.onBackPressed();
                }
            }
        });
    }

    @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder
    protected void e(final LocalMedia localMedia) {
        this.coverImageView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.luck.picture.lib.adapter.holder.PreviewVideoHolder.2
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                BasePreviewHolder.OnPreviewEventListener onPreviewEventListener = PreviewVideoHolder.this.f18979f;
                if (onPreviewEventListener == null) {
                    return false;
                }
                onPreviewEventListener.onLongPressDownload(localMedia);
                return false;
            }
        });
    }

    @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder
    protected void g(LocalMedia localMedia) {
        super.g(localMedia);
        if (this.f18978e.isPreviewZoomEffect || this.f18974a >= this.f18975b) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = this.videoPlayer.getLayoutParams();
        if (layoutParams instanceof FrameLayout.LayoutParams) {
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
            layoutParams2.width = this.f18974a;
            layoutParams2.height = this.f18976c;
            layoutParams2.gravity = 17;
            return;
        }
        if (layoutParams instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) layoutParams;
            layoutParams3.width = this.f18974a;
            layoutParams3.height = this.f18976c;
            layoutParams3.addRule(13);
            return;
        }
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) layoutParams;
            layoutParams4.width = this.f18974a;
            layoutParams4.height = this.f18976c;
            layoutParams4.gravity = 17;
            return;
        }
        if (layoutParams instanceof ConstraintLayout.LayoutParams) {
            ConstraintLayout.LayoutParams layoutParams5 = (ConstraintLayout.LayoutParams) layoutParams;
            ((ViewGroup.MarginLayoutParams) layoutParams5).width = this.f18974a;
            ((ViewGroup.MarginLayoutParams) layoutParams5).height = this.f18976c;
            layoutParams5.topToTop = 0;
            layoutParams5.bottomToBottom = 0;
        }
    }

    @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder
    public boolean isPlaying() {
        VideoPlayerEngine videoPlayerEngine = this.f18978e.videoPlayerEngine;
        return videoPlayerEngine != null && videoPlayerEngine.isPlaying(this.videoPlayer);
    }

    public void onPause() {
        this.ivPlayButton.setVisibility(0);
        VideoPlayerEngine videoPlayerEngine = this.f18978e.videoPlayerEngine;
        if (videoPlayerEngine != null) {
            videoPlayerEngine.onPause(this.videoPlayer);
        }
    }

    @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder
    public void onViewAttachedToWindow() {
        VideoPlayerEngine videoPlayerEngine = this.f18978e.videoPlayerEngine;
        if (videoPlayerEngine != null) {
            videoPlayerEngine.onPlayerAttachedToWindow(this.videoPlayer);
            this.f18978e.videoPlayerEngine.addPlayListener(this.mPlayerListener);
        }
    }

    @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder
    public void onViewDetachedFromWindow() {
        VideoPlayerEngine videoPlayerEngine = this.f18978e.videoPlayerEngine;
        if (videoPlayerEngine != null) {
            videoPlayerEngine.onPlayerDetachedFromWindow(this.videoPlayer);
            this.f18978e.videoPlayerEngine.removePlayListener(this.mPlayerListener);
        }
        playerDefaultUI();
    }

    @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder
    public void release() {
        VideoPlayerEngine videoPlayerEngine = this.f18978e.videoPlayerEngine;
        if (videoPlayerEngine != null) {
            videoPlayerEngine.removePlayListener(this.mPlayerListener);
            this.f18978e.videoPlayerEngine.destroy(this.videoPlayer);
        }
    }

    @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder
    public void resumePausePlay() {
        if (isPlaying()) {
            onPause();
        } else {
            onResume();
        }
    }

    public void startPlay() {
        SelectorConfig selectorConfig = this.f18978e;
        if (selectorConfig.isUseSystemVideoPlayer) {
            IntentUtils.startSystemPlayerVideo(this.itemView.getContext(), this.f18977d.getAvailablePath());
            return;
        }
        if (this.videoPlayer == null) {
            throw new NullPointerException("VideoPlayer cannot be empty,Please implement " + VideoPlayerEngine.class);
        }
        if (selectorConfig.videoPlayerEngine != null) {
            this.progress.setVisibility(0);
            this.ivPlayButton.setVisibility(8);
            this.f18979f.onPreviewVideoTitle(this.f18977d.getFileName());
            this.isPlayed = true;
            this.f18978e.videoPlayerEngine.onStarPlayer(this.videoPlayer, this.f18977d);
        }
    }
}
