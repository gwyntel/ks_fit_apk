package com.umeng.message.proguard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.Button;
import androidx.core.view.ViewCompat;

/* loaded from: classes4.dex */
public final class bd extends Button {

    /* renamed from: a, reason: collision with root package name */
    private Paint f22819a;

    /* renamed from: b, reason: collision with root package name */
    private float f22820b;

    /* renamed from: c, reason: collision with root package name */
    private float f22821c;

    /* renamed from: d, reason: collision with root package name */
    private float f22822d;

    /* renamed from: e, reason: collision with root package name */
    private float f22823e;

    /* renamed from: f, reason: collision with root package name */
    private float f22824f;

    /* renamed from: g, reason: collision with root package name */
    private int f22825g;

    public bd(Context context) {
        super(context);
        setBackgroundColor(0);
        this.f22819a = new Paint();
        this.f22825g = bb.a(1.0f);
        this.f22824f = bb.a(2.0f);
    }

    @Override // android.widget.TextView, android.view.View
    protected final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.f22820b = getWidth() / 2;
        this.f22821c = getHeight() / 2;
        float fMin = (Math.min(getHeight(), getWidth()) / 2) - this.f22825g;
        this.f22822d = fMin;
        this.f22823e = fMin / 1.4142f;
        this.f22819a.setAntiAlias(true);
        this.f22819a.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.f22819a.setStyle(Paint.Style.FILL);
        canvas.drawCircle(this.f22820b, this.f22821c, this.f22822d, this.f22819a);
        this.f22819a.setColor(-1);
        this.f22819a.setStrokeWidth(this.f22824f);
        this.f22819a.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(this.f22820b, this.f22821c, this.f22822d, this.f22819a);
        float f2 = this.f22820b;
        float f3 = this.f22823e;
        float f4 = this.f22821c;
        canvas.drawLine(f2 - f3, f4 - f3, f2 + f3, f4 + f3, this.f22819a);
        float f5 = this.f22820b;
        float f6 = this.f22823e;
        float f7 = this.f22821c;
        canvas.drawLine(f5 + f6, f7 - f6, f5 - f6, f7 + f6, this.f22819a);
    }
}
