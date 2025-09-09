package com.alibaba.fplayer.flutter_aliplayer;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import androidx.annotation.NonNull;
import com.aliyun.player.AliListPlayer;
import com.aliyun.player.IPlayer;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.f;
import java.util.Map;

/* loaded from: classes2.dex */
public class FlutterAliPlayerView implements PlatformView {
    private static final String SURFACE_VIEW_TYPE = "surfaceview";
    private static final String TEXTURE_VIEW_TYPE = "textureview";
    private View flutterAttachedView;
    private Context mContext;
    private FlutterAliPlayerViewListener mFlutterAliPlayerViewListener;
    private IPlayer mPlayer;
    private Surface mSurface;
    private SurfaceHolder mSurfaceHolder;
    private SurfaceView mSurfaceView;
    private TextureView mTextureView;
    private int mViewId;
    private String viewType;

    public interface FlutterAliPlayerViewListener {
        void onDispose(int i2);
    }

    public FlutterAliPlayerView(Context context, int i2, Object obj) {
        this.viewType = SURFACE_VIEW_TYPE;
        if (obj != null) {
            this.viewType = (String) ((Map) obj).get("viewType");
        }
        this.mViewId = i2;
        this.mContext = context;
        if (isTextureView()) {
            TextureView textureView = new TextureView(this.mContext);
            this.mTextureView = textureView;
            initRenderView(textureView);
        } else {
            SurfaceView surfaceView = new SurfaceView(this.mContext);
            this.mSurfaceView = surfaceView;
            initRenderView(surfaceView);
        }
    }

    private void initRenderView(SurfaceView surfaceView) {
        if (isTextureView() || surfaceView == null) {
            return;
        }
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerView.1
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
                if (FlutterAliPlayerView.this.mPlayer != null) {
                    FlutterAliPlayerView.this.mPlayer.surfaceChanged();
                }
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                FlutterAliPlayerView.this.mSurfaceHolder = surfaceHolder;
                FlutterAliPlayerView.this.setRenderView();
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                if ((FlutterAliPlayerView.this.mPlayer instanceof AliListPlayer) || FlutterAliPlayerView.this.mPlayer == null) {
                    return;
                }
                FlutterAliPlayerView.this.mPlayer.setSurface(null);
            }
        });
    }

    @Override // io.flutter.plugin.platform.PlatformView
    public void dispose() {
        FlutterAliPlayerViewListener flutterAliPlayerViewListener = this.mFlutterAliPlayerViewListener;
        if (flutterAliPlayerViewListener != null) {
            flutterAliPlayerViewListener.onDispose(this.mViewId);
        }
        this.mSurfaceHolder = null;
    }

    public IPlayer getPlayer() {
        return this.mPlayer;
    }

    @Override // io.flutter.plugin.platform.PlatformView
    public View getView() {
        return isTextureView() ? this.mTextureView : this.mSurfaceView;
    }

    public boolean isTextureView() {
        return TEXTURE_VIEW_TYPE.equals(this.viewType);
    }

    @Override // io.flutter.plugin.platform.PlatformView
    public /* synthetic */ void onFlutterViewAttached(View view) {
        f.a(this, view);
    }

    @Override // io.flutter.plugin.platform.PlatformView
    public /* synthetic */ void onFlutterViewDetached() {
        f.b(this);
    }

    @Override // io.flutter.plugin.platform.PlatformView
    public /* synthetic */ void onInputConnectionLocked() {
        f.c(this);
    }

    @Override // io.flutter.plugin.platform.PlatformView
    public /* synthetic */ void onInputConnectionUnlocked() {
        f.d(this);
    }

    public void setFlutterAliPlayerViewListener(FlutterAliPlayerViewListener flutterAliPlayerViewListener) {
        this.mFlutterAliPlayerViewListener = flutterAliPlayerViewListener;
    }

    public void setPlayer(IPlayer iPlayer) {
        this.mPlayer = iPlayer;
        setRenderView();
    }

    public void setRenderView() {
        SurfaceHolder surfaceHolder;
        Surface surface;
        if (isTextureView()) {
            IPlayer iPlayer = this.mPlayer;
            if (iPlayer == null || (surface = this.mSurface) == null) {
                return;
            }
            iPlayer.setSurface(surface);
            return;
        }
        if (this.mPlayer == null || (surfaceHolder = this.mSurfaceHolder) == null || surfaceHolder.getSurface() == null) {
            return;
        }
        this.mPlayer.setSurface(this.mSurfaceHolder.getSurface());
    }

    private void initRenderView(TextureView textureView) {
        if (textureView == null || !TEXTURE_VIEW_TYPE.equals(this.viewType)) {
            return;
        }
        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerView.2
            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surfaceTexture, int i2, int i3) {
                FlutterAliPlayerView.this.mSurface = new Surface(surfaceTexture);
                FlutterAliPlayerView.this.setRenderView();
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surfaceTexture) {
                FlutterAliPlayerView.this.mSurface = null;
                if ((FlutterAliPlayerView.this.mPlayer instanceof AliListPlayer) || FlutterAliPlayerView.this.mPlayer == null) {
                    return false;
                }
                FlutterAliPlayerView.this.mPlayer.setSurface(null);
                return false;
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surfaceTexture, int i2, int i3) {
                if (FlutterAliPlayerView.this.mPlayer != null) {
                    FlutterAliPlayerView.this.mPlayer.surfaceChanged();
                }
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surfaceTexture) {
            }
        });
    }
}
