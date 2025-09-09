package com.leeson.image_pickers.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/* loaded from: classes4.dex */
public class FullScreenVideoView extends VideoView {
    private PlayerLisetener playerLisetener;

    public interface PlayerLisetener {
        void onStart();
    }

    public FullScreenVideoView(Context context) {
        super(context);
    }

    @Override // android.widget.VideoView, android.view.SurfaceView, android.view.View
    protected void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
    }

    public void setPlayerLisetener(PlayerLisetener playerLisetener) {
        this.playerLisetener = playerLisetener;
    }

    @Override // android.widget.VideoView, android.widget.MediaController.MediaPlayerControl
    public void start() {
        super.start();
        PlayerLisetener playerLisetener = this.playerLisetener;
        if (playerLisetener != null) {
            playerLisetener.onStart();
        }
    }

    public FullScreenVideoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public FullScreenVideoView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }
}
