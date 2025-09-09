package com.huawei.hms.scankit.p;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import java.util.List;

/* loaded from: classes4.dex */
public class y5 {

    /* renamed from: a, reason: collision with root package name */
    private long f18032a = 0;

    /* renamed from: b, reason: collision with root package name */
    private long f18033b = 0;

    /* renamed from: c, reason: collision with root package name */
    private int f18034c;

    /* renamed from: d, reason: collision with root package name */
    private int f18035d;

    /* renamed from: e, reason: collision with root package name */
    private int f18036e;

    /* renamed from: f, reason: collision with root package name */
    private float f18037f;

    /* renamed from: g, reason: collision with root package name */
    private Rect f18038g;

    /* renamed from: h, reason: collision with root package name */
    private b6 f18039h;

    public y5(@NonNull b6 b6Var, Rect rect, int i2, float f2, int[] iArr) {
        this.f18034c = 0;
        this.f18035d = 0;
        this.f18039h = b6Var;
        this.f18038g = rect;
        this.f18036e = i2;
        if (iArr != null && iArr.length >= 2) {
            this.f18034c = iArr[0];
            this.f18035d = iArr[1];
        }
        this.f18037f = f2;
        c();
    }

    private void a() {
        b6 b6Var = this.f18039h;
        if (b6Var != null) {
            b6Var.a();
        }
    }

    private int b() {
        float fA = n6.a(1.0f);
        int iRed = Color.red(this.f18034c);
        int iBlue = Color.blue(this.f18034c);
        return Color.rgb((int) (iRed + ((Color.red(this.f18035d) - iRed) * fA) + 0.5f), (int) (Color.green(this.f18034c) + ((Color.green(this.f18035d) - r3) * fA) + 0.5f), (int) (iBlue + ((Color.blue(this.f18035d) - iBlue) * fA) + 0.5f));
    }

    private void c() {
        Rect rect;
        a();
        this.f18033b = 0L;
        this.f18032a = System.currentTimeMillis();
        b6 b6Var = this.f18039h;
        if (b6Var == null || (rect = this.f18038g) == null) {
            return;
        }
        b6Var.a(rect, this.f18036e);
    }

    public void a(@NonNull Canvas canvas, @NonNull Rect rect) {
        if (this.f18039h == null || canvas == null || rect == null) {
            return;
        }
        long jCurrentTimeMillis = this.f18033b + (System.currentTimeMillis() - this.f18032a);
        this.f18033b = jCurrentTimeMillis;
        this.f18039h.b(jCurrentTimeMillis);
        List<w5> listC = this.f18039h.c();
        if (listC == null || listC.isEmpty()) {
            return;
        }
        a(canvas, rect, listC);
        this.f18032a = System.currentTimeMillis();
    }

    private void a(Canvas canvas, Rect rect, List<w5> list) {
        for (w5 w5Var : list) {
            Paint paint = new Paint();
            if (w5Var.b() == 0) {
                w5Var.b(b());
            }
            paint.setColor(w5Var.b());
            boolean z2 = w5Var.d() > ((float) Math.max(rect.top, rect.bottom)) || w5Var.d() < ((float) Math.min(rect.top, rect.bottom));
            float fD = 0.0f;
            if (rect.height() != 0 && rect.width() != 0 && !z2) {
                fD = (rect.bottom - w5Var.d()) / rect.height();
            }
            int iA = (int) (w5Var.a() * Math.abs(fD));
            if (iA > 0) {
                paint.setAlpha(iA);
                canvas.drawCircle(w5Var.c(), w5Var.d(), w5Var.f() * this.f18037f, paint);
            }
        }
    }
}
