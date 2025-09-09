package com.aliyun.player.videoview.a;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Looper;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import com.aliyun.player.IPlayer;
import com.aliyun.player.videoview.AliDisplayView;
import com.cicada.player.utils.Logger;

/* loaded from: classes3.dex */
public abstract class a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f12090a = "AliDisplayView_" + a.class.getSimpleName();

    /* renamed from: b, reason: collision with root package name */
    private final ViewGroup f12091b;

    /* renamed from: c, reason: collision with root package name */
    protected h f12092c = null;

    /* renamed from: d, reason: collision with root package name */
    private int f12093d = 0;

    /* renamed from: e, reason: collision with root package name */
    private int f12094e = 0;

    /* renamed from: f, reason: collision with root package name */
    private int f12095f = 0;

    /* renamed from: g, reason: collision with root package name */
    private IPlayer.ScaleMode f12096g = IPlayer.ScaleMode.SCALE_ASPECT_FIT;

    /* renamed from: h, reason: collision with root package name */
    private IPlayer.MirrorMode f12097h = IPlayer.MirrorMode.MIRROR_MODE_NONE;

    /* renamed from: i, reason: collision with root package name */
    private IPlayer.RotateMode f12098i = IPlayer.RotateMode.ROTATE_0;

    /* renamed from: j, reason: collision with root package name */
    private boolean f12099j = false;

    /* renamed from: k, reason: collision with root package name */
    private View f12100k = null;

    /* renamed from: com.aliyun.player.videoview.a.a$a, reason: collision with other inner class name */
    class RunnableC0099a implements Runnable {
        RunnableC0099a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            a.this.h();
        }
    }

    class b implements Runnable {
        b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            a.this.h();
        }
    }

    class c implements Runnable {
        c() {
        }

        @Override // java.lang.Runnable
        public void run() {
            a.this.h();
        }
    }

    class d implements Runnable {
        d() {
        }

        @Override // java.lang.Runnable
        public void run() {
            a.this.h();
        }
    }

    class e implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ IPlayer.MirrorMode f12105a;

        e(IPlayer.MirrorMode mirrorMode) {
            this.f12105a = mirrorMode;
        }

        @Override // java.lang.Runnable
        public void run() {
            a.this.a(this.f12105a);
        }
    }

    class f implements Runnable {
        f() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (a.this.f12100k.getParent() == a.this.f12091b) {
                a.this.f12091b.removeView(a.this.f12100k);
            }
        }
    }

    class g implements Runnable {
        g() {
        }

        @Override // java.lang.Runnable
        public void run() {
            a.this.f12091b.addView(a.this.f12100k, 0);
            a aVar = a.this;
            aVar.c(aVar.f12097h);
            a aVar2 = a.this;
            aVar2.b(aVar2.f12096g);
            a aVar3 = a.this;
            aVar3.c(aVar3.f12098i);
            a.this.h();
        }
    }

    public interface h {
        void onSurfaceCreated(Surface surface);

        void onSurfaceDestroy();

        void onSurfaceSizeChanged();

        void onViewCreated(AliDisplayView.DisplayViewType displayViewType);
    }

    a(ViewGroup viewGroup) {
        this.f12091b = viewGroup;
        Logger.v(f12090a, this + " new IDisplayView()");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:36:0x008a A[PHI: r0 r6
      0x008a: PHI (r0v6 int) = (r0v5 int), (r0v11 int) binds: [B:35:0x0088, B:30:0x007a] A[DONT_GENERATE, DONT_INLINE]
      0x008a: PHI (r6v1 int) = (r6v0 int), (r6v3 int) binds: [B:35:0x0088, B:30:0x007a] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0094 A[PHI: r0 r6
      0x0094: PHI (r0v9 int) = (r0v5 int), (r0v11 int) binds: [B:37:0x0092, B:32:0x007d] A[DONT_GENERATE, DONT_INLINE]
      0x0094: PHI (r6v2 int) = (r6v0 int), (r6v3 int) binds: [B:37:0x0092, B:32:0x007d] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void h() {
        /*
            r8 = this;
            java.lang.String r0 = com.aliyun.player.videoview.a.a.f12090a
            java.lang.String r1 = "updateViewParams  "
            com.cicada.player.utils.Logger.v(r0, r1)
            boolean r1 = r8.f12099j
            r2 = -1
            if (r1 != 0) goto L21
            android.widget.FrameLayout$LayoutParams r0 = new android.widget.FrameLayout$LayoutParams
            r0.<init>(r2, r2)
            android.view.View r1 = r8.f12100k
            android.view.ViewParent r1 = r1.getParent()
            android.view.ViewGroup r2 = r8.f12091b
            if (r1 != r2) goto L20
            android.view.View r1 = r8.f12100k
            r1.setLayoutParams(r0)
        L20:
            return
        L21:
            int r1 = r8.f12094e
            if (r1 == 0) goto Lbe
            int r1 = r8.f12093d
            if (r1 != 0) goto L2b
            goto Lbe
        L2b:
            android.view.ViewGroup r1 = r8.f12091b
            int r1 = r1.getMeasuredWidth()
            android.view.ViewGroup r3 = r8.f12091b
            int r3 = r3.getMeasuredHeight()
            if (r1 == 0) goto Lb8
            if (r3 != 0) goto L3d
            goto Lb8
        L3d:
            com.aliyun.player.IPlayer$RotateMode r0 = r8.f12098i
            boolean r0 = r8.a(r0)
            if (r0 == 0) goto L65
            com.aliyun.player.IPlayer$RotateMode r0 = r8.f12098i
            int r0 = r0.getValue()
            float r0 = (float) r0
            int r4 = r8.f12095f
            float r4 = (float) r4
            float r4 = r4 + r0
            int r0 = (int) r4
            r4 = 90
            if (r0 == r4) goto L59
            r4 = 270(0x10e, float:3.78E-43)
            if (r0 != r4) goto L65
        L59:
            android.view.ViewGroup r0 = r8.f12091b
            int r1 = r0.getMeasuredHeight()
            android.view.ViewGroup r0 = r8.f12091b
            int r3 = r0.getMeasuredWidth()
        L65:
            com.aliyun.player.IPlayer$ScaleMode r0 = r8.f12096g
            com.aliyun.player.IPlayer$ScaleMode r4 = com.aliyun.player.IPlayer.ScaleMode.SCALE_TO_FILL
            if (r0 != r4) goto L6c
            goto L9b
        L6c:
            com.aliyun.player.IPlayer$ScaleMode r4 = com.aliyun.player.IPlayer.ScaleMode.SCALE_ASPECT_FILL
            r5 = 1065353216(0x3f800000, float:1.0)
            if (r0 != r4) goto L80
            int r0 = r8.f12093d
            int r4 = r0 * r3
            int r6 = r8.f12094e
            int r7 = r1 * r6
            if (r4 <= r7) goto L7d
            goto L8a
        L7d:
            if (r4 >= r7) goto L9b
            goto L94
        L80:
            int r0 = r8.f12093d
            int r4 = r0 * r3
            int r6 = r8.f12094e
            int r7 = r1 * r6
            if (r4 >= r7) goto L92
        L8a:
            float r1 = (float) r3
            float r1 = r1 * r5
            float r0 = (float) r0
            float r1 = r1 * r0
            float r0 = (float) r6
            float r1 = r1 / r0
            int r1 = (int) r1
            goto L9b
        L92:
            if (r4 <= r7) goto L9b
        L94:
            float r3 = (float) r1
            float r3 = r3 * r5
            float r4 = (float) r6
            float r3 = r3 * r4
            float r0 = (float) r0
            float r3 = r3 / r0
            int r3 = (int) r3
        L9b:
            android.widget.FrameLayout$LayoutParams r0 = new android.widget.FrameLayout$LayoutParams
            r0.<init>(r2, r2)
            r0.width = r1
            r0.height = r3
            r1 = 17
            r0.gravity = r1
            android.view.View r1 = r8.f12100k
            android.view.ViewParent r1 = r1.getParent()
            android.view.ViewGroup r2 = r8.f12091b
            if (r1 != r2) goto Lb7
            android.view.View r1 = r8.f12100k
            r1.setLayoutParams(r0)
        Lb7:
            return
        Lb8:
            java.lang.String r1 = "updateViewParams ，unknow parent height and width "
        Lba:
            com.cicada.player.utils.Logger.w(r0, r1)
            return
        Lbe:
            java.lang.String r1 = "updateViewParams ，unknow videoheight and width "
            goto Lba
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.player.videoview.a.a.h():void");
    }

    protected abstract View a(Context context);

    public void a() {
        Logger.v(f12090a, " attachView");
        a(new g());
    }

    protected abstract boolean a(IPlayer.MirrorMode mirrorMode);

    protected abstract boolean a(IPlayer.RotateMode rotateMode);

    public abstract void b(boolean z2);

    protected abstract Bitmap f();

    public Bitmap g() {
        return f();
    }

    public void a(int i2, int i3, int i4) {
        Logger.v(f12090a, "setVideoSize  " + i2 + " ， " + i3 + " ， " + i4);
        this.f12093d = i2;
        this.f12094e = i3;
        this.f12095f = i4;
        a(new c());
    }

    public void b() {
        Logger.v(f12090a, " detachView");
        a(new f());
    }

    public void c() {
        this.f12100k = a(this.f12091b.getContext());
    }

    public boolean d() {
        return this.f12099j;
    }

    public void e() {
        Logger.v(f12090a, "parentSizeChanged  ");
        a(new RunnableC0099a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(IPlayer.MirrorMode mirrorMode) {
        if (mirrorMode != null) {
            this.f12097h = mirrorMode;
        }
    }

    public void a(IPlayer.ScaleMode scaleMode) {
        Logger.v(f12090a, "setScaleMode  " + scaleMode);
        b(scaleMode);
        a(new d());
    }

    public void b(IPlayer.MirrorMode mirrorMode) {
        Logger.v(f12090a, "setMirrorMode  " + mirrorMode);
        c(mirrorMode);
        this.f12100k.post(new e(mirrorMode));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(IPlayer.RotateMode rotateMode) {
        if (rotateMode != null) {
            this.f12098i = rotateMode;
        }
    }

    public void a(h hVar) {
        Logger.v(f12090a, this + " setOnViewStatusListener " + hVar);
        this.f12092c = hVar;
    }

    public void b(IPlayer.RotateMode rotateMode) {
        Logger.v(f12090a, "setRotateMode  " + rotateMode);
        c(rotateMode);
        h();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(IPlayer.ScaleMode scaleMode) {
        if (scaleMode != null) {
            this.f12096g = scaleMode;
        }
    }

    private void a(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            this.f12091b.post(runnable);
        }
    }

    public void a(boolean z2) {
        Logger.v(f12090a, "setRenderFlag  " + z2);
        this.f12099j = z2;
        a(new b());
    }
}
