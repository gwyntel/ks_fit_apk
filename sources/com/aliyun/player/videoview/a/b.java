package com.aliyun.player.videoview.a;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import com.aliyun.player.IPlayer;
import com.aliyun.player.videoview.a.a;
import com.cicada.player.utils.Logger;

/* loaded from: classes3.dex */
public class b extends com.aliyun.player.videoview.a.a {

    /* renamed from: l, reason: collision with root package name */
    private static final String f12109l = "AliDisplayView_" + b.class.getSimpleName();

    /* renamed from: m, reason: collision with root package name */
    private SurfaceView f12110m;

    class a implements SurfaceHolder.Callback2 {
        a() {
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
            Logger.i(b.f12109l, "surfaceChanged ");
            a.h hVar = b.this.f12092c;
            if (hVar != null) {
                hVar.onSurfaceSizeChanged();
            }
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            Surface surface = surfaceHolder.getSurface();
            Logger.i(b.f12109l, "onSurfaceCreated  " + surface);
            a.h hVar = b.this.f12092c;
            if (hVar != null) {
                hVar.onSurfaceCreated(surface);
            }
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            Logger.i(b.f12109l, "surfaceDestroyed ");
            a.h hVar = b.this.f12092c;
            if (hVar != null) {
                hVar.onSurfaceDestroy();
            }
        }

        @Override // android.view.SurfaceHolder.Callback2
        public void surfaceRedrawNeeded(SurfaceHolder surfaceHolder) {
            Logger.i(b.f12109l, "surfaceRedrawNeeded ");
        }
    }

    public b(ViewGroup viewGroup) {
        super(viewGroup);
        this.f12110m = null;
    }

    @Override // com.aliyun.player.videoview.a.a
    protected View a(Context context) {
        SurfaceView surfaceView = new SurfaceView(context);
        this.f12110m = surfaceView;
        surfaceView.getHolder().addCallback(new a());
        return this.f12110m;
    }

    @Override // com.aliyun.player.videoview.a.a
    public void b(boolean z2) {
    }

    @Override // com.aliyun.player.videoview.a.a
    protected Bitmap f() {
        return null;
    }

    @Override // com.aliyun.player.videoview.a.a
    protected boolean a(IPlayer.MirrorMode mirrorMode) {
        return false;
    }

    @Override // com.aliyun.player.videoview.a.a
    protected boolean a(IPlayer.RotateMode rotateMode) {
        return false;
    }
}
