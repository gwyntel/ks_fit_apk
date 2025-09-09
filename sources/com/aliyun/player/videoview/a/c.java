package com.aliyun.player.videoview.a;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import com.aliyun.player.IPlayer;
import com.aliyun.player.videoview.a.a;
import com.cicada.player.utils.Logger;

/* loaded from: classes3.dex */
public class c extends com.aliyun.player.videoview.a.a {

    /* renamed from: l, reason: collision with root package name */
    private static final String f12112l = "AliDisplayView_" + c.class.getSimpleName();

    /* renamed from: m, reason: collision with root package name */
    private TextureView f12113m;

    /* renamed from: n, reason: collision with root package name */
    private SurfaceTexture f12114n;

    /* renamed from: o, reason: collision with root package name */
    private Surface f12115o;

    /* renamed from: p, reason: collision with root package name */
    private boolean f12116p;

    /* renamed from: q, reason: collision with root package name */
    private boolean f12117q;

    class a implements TextureView.SurfaceTextureListener {
        a() {
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x006d  */
        /* JADX WARN: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
        @Override // android.view.TextureView.SurfaceTextureListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onSurfaceTextureAvailable(android.graphics.SurfaceTexture r2, int r3, int r4) {
            /*
                r1 = this;
                com.aliyun.player.videoview.a.c r3 = com.aliyun.player.videoview.a.c.this
                android.graphics.SurfaceTexture r3 = com.aliyun.player.videoview.a.c.a(r3)
                if (r3 != 0) goto L18
                com.aliyun.player.videoview.a.c r3 = com.aliyun.player.videoview.a.c.this
                com.aliyun.player.videoview.a.c.a(r3, r2)
                com.aliyun.player.videoview.a.c r3 = com.aliyun.player.videoview.a.c.this
                android.view.Surface r4 = new android.view.Surface
                r4.<init>(r2)
            L14:
                com.aliyun.player.videoview.a.c.a(r3, r4)
                goto L46
            L18:
                com.aliyun.player.videoview.a.c r3 = com.aliyun.player.videoview.a.c.this
                boolean r3 = com.aliyun.player.videoview.a.c.c(r3)
                if (r3 == 0) goto L30
                com.aliyun.player.videoview.a.c r3 = com.aliyun.player.videoview.a.c.this
                android.view.TextureView r3 = com.aliyun.player.videoview.a.c.d(r3)
                com.aliyun.player.videoview.a.c r4 = com.aliyun.player.videoview.a.c.this
                android.graphics.SurfaceTexture r4 = com.aliyun.player.videoview.a.c.a(r4)
                r3.setSurfaceTexture(r4)
                goto L46
            L30:
                com.aliyun.player.videoview.a.c r3 = com.aliyun.player.videoview.a.c.this
                android.view.Surface r3 = com.aliyun.player.videoview.a.c.b(r3)
                r3.release()
                com.aliyun.player.videoview.a.c r3 = com.aliyun.player.videoview.a.c.this
                com.aliyun.player.videoview.a.c.a(r3, r2)
                com.aliyun.player.videoview.a.c r3 = com.aliyun.player.videoview.a.c.this
                android.view.Surface r4 = new android.view.Surface
                r4.<init>(r2)
                goto L14
            L46:
                java.lang.String r3 = com.aliyun.player.videoview.a.c.i()
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                com.aliyun.player.videoview.a.c r0 = com.aliyun.player.videoview.a.c.this
                android.view.TextureView r0 = com.aliyun.player.videoview.a.c.d(r0)
                r4.append(r0)
                java.lang.String r0 = " onSurfaceTextureAvailable  "
                r4.append(r0)
                r4.append(r2)
                java.lang.String r2 = r4.toString()
                com.cicada.player.utils.Logger.i(r3, r2)
                com.aliyun.player.videoview.a.c r2 = com.aliyun.player.videoview.a.c.this
                com.aliyun.player.videoview.a.a$h r3 = r2.f12092c
                if (r3 == 0) goto L9c
                boolean r2 = com.aliyun.player.videoview.a.c.e(r2)
                if (r2 == 0) goto L83
                com.aliyun.player.videoview.a.c r2 = com.aliyun.player.videoview.a.c.this
                android.view.Surface r2 = com.aliyun.player.videoview.a.c.b(r2)
                if (r2 == 0) goto L83
                com.aliyun.player.videoview.a.c r2 = com.aliyun.player.videoview.a.c.this
                boolean r2 = r2.d()
                if (r2 != 0) goto L9c
            L83:
                com.aliyun.player.videoview.a.c r2 = com.aliyun.player.videoview.a.c.this
                android.view.Surface r2 = com.aliyun.player.videoview.a.c.b(r2)
                if (r2 == 0) goto L91
                com.aliyun.player.videoview.a.c r2 = com.aliyun.player.videoview.a.c.this
                r3 = 1
                com.aliyun.player.videoview.a.c.a(r2, r3)
            L91:
                com.aliyun.player.videoview.a.c r2 = com.aliyun.player.videoview.a.c.this
                com.aliyun.player.videoview.a.a$h r3 = r2.f12092c
                android.view.Surface r2 = com.aliyun.player.videoview.a.c.b(r2)
                r3.onSurfaceCreated(r2)
            L9c:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.aliyun.player.videoview.a.c.a.onSurfaceTextureAvailable(android.graphics.SurfaceTexture, int, int):void");
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            Logger.i(c.f12112l, c.this.f12113m + " onSurfaceTextureDestroyed  ");
            a.h hVar = c.this.f12092c;
            if (hVar == null) {
                return false;
            }
            hVar.onSurfaceDestroy();
            return false;
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i2, int i3) {
            Logger.i(c.f12112l, c.this.f12113m + " onSurfaceTextureSizeChanged  ");
            a.h hVar = c.this.f12092c;
            if (hVar != null) {
                hVar.onSurfaceSizeChanged();
            }
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }
    }

    public c(ViewGroup viewGroup) {
        super(viewGroup);
        this.f12113m = null;
        this.f12114n = null;
        this.f12115o = null;
        this.f12116p = false;
        this.f12117q = true;
    }

    private Bitmap a(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.preRotate(this.f12113m.getRotation());
        matrix.preScale(this.f12113m.getScaleX(), this.f12113m.getScaleY());
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    @Override // com.aliyun.player.videoview.a.a
    protected Bitmap f() {
        Bitmap bitmap = this.f12113m.getBitmap();
        Bitmap bitmapA = a(bitmap);
        bitmap.recycle();
        return bitmapA;
    }

    @Override // com.aliyun.player.videoview.a.a
    public void b(boolean z2) {
        this.f12117q = z2;
    }

    @Override // com.aliyun.player.videoview.a.a
    protected View a(Context context) {
        TextureView textureView = new TextureView(context);
        this.f12113m = textureView;
        this.f12116p = false;
        textureView.setSurfaceTextureListener(new a());
        return this.f12113m;
    }

    @Override // com.aliyun.player.videoview.a.a
    protected boolean a(IPlayer.MirrorMode mirrorMode) {
        if (mirrorMode == IPlayer.MirrorMode.MIRROR_MODE_HORIZONTAL) {
            this.f12113m.setScaleX(-1.0f);
            this.f12113m.setScaleY(1.0f);
            return true;
        }
        if (mirrorMode == IPlayer.MirrorMode.MIRROR_MODE_VERTICAL) {
            this.f12113m.setScaleY(-1.0f);
        } else {
            this.f12113m.setScaleY(1.0f);
        }
        this.f12113m.setScaleX(1.0f);
        return true;
    }

    @Override // com.aliyun.player.videoview.a.a
    protected boolean a(IPlayer.RotateMode rotateMode) {
        TextureView textureView;
        float f2;
        if (rotateMode == IPlayer.RotateMode.ROTATE_90) {
            textureView = this.f12113m;
            f2 = 90.0f;
        } else if (rotateMode == IPlayer.RotateMode.ROTATE_180) {
            textureView = this.f12113m;
            f2 = 180.0f;
        } else if (rotateMode == IPlayer.RotateMode.ROTATE_270) {
            textureView = this.f12113m;
            f2 = 270.0f;
        } else {
            textureView = this.f12113m;
            f2 = 0.0f;
        }
        textureView.setRotation(f2);
        return true;
    }
}
