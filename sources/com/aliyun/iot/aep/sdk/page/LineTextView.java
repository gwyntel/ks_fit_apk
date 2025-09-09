package com.aliyun.iot.aep.sdk.page;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import com.aliyun.iot.aep.sdk.framework.R;

/* loaded from: classes3.dex */
public class LineTextView extends AppCompatTextView {

    /* renamed from: a, reason: collision with root package name */
    private TextPaint f11972a;

    /* renamed from: b, reason: collision with root package name */
    private Paint f11973b;

    public LineTextView(Context context) {
        super(context);
        this.f11972a = new TextPaint();
        this.f11973b = new Paint();
    }

    private static float a(float f2) {
        float f3 = 1.0f;
        if (f2 > 1.0f) {
            if (f2 <= 1.5d) {
                return 1.5f;
            }
            f3 = 2.0f;
            if (f2 > 2.0f) {
                f3 = 3.0f;
                if (f2 > 3.0f) {
                    return f2;
                }
            }
        }
        return f3;
    }

    public static int sp2px(Context context, float f2) {
        return (int) ((f2 * a(context)) + 0.5f);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) throws Resources.NotFoundException {
        super.onDraw(canvas);
        this.f11972a.setColor(getTextColors().getDefaultColor());
        this.f11972a.setStyle(Paint.Style.FILL);
        this.f11972a.setTextSize(sp2px(getContext(), 16.0f));
        String string = getResources().getString(R.string.location_failed);
        String str = getResources().getString(R.string.location_failed) + getResources().getString(R.string.location_failed_again);
        TextPaint textPaint = this.f11972a;
        int width = canvas.getWidth();
        Layout.Alignment alignment = Layout.Alignment.ALIGN_NORMAL;
        StaticLayout staticLayout = new StaticLayout(string, textPaint, width, alignment, 1.0f, 0.0f, false);
        StaticLayout staticLayout2 = new StaticLayout(str, this.f11972a, canvas.getWidth(), alignment, 1.0f, 0.0f, false);
        int lineCount = staticLayout.getLineCount();
        int lineCount2 = getLineCount();
        this.f11973b.setColor(getTextColors().getDefaultColor());
        this.f11973b.setStrokeWidth(3.0f);
        int i2 = lineCount - 1;
        int i3 = i2;
        while (i3 < lineCount2) {
            canvas.drawLine(i3 == i2 ? staticLayout.getLineWidth(i2) : 0.0f, getLineHeight() * r12, staticLayout2.getLineWidth(i3), getLineHeight() * r12, this.f11973b);
            i3++;
        }
    }

    private static float a(Context context) {
        return a(context.getResources().getDisplayMetrics().scaledDensity);
    }

    public LineTextView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f11972a = new TextPaint();
        this.f11973b = new Paint();
    }

    public LineTextView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f11972a = new TextPaint();
        this.f11973b = new Paint();
    }
}
