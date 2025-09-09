package com.aliyun.iot.aep.sdk.page;

import android.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import androidx.exifinterface.media.ExifInterface;
import com.alibaba.sdk.android.openaccount.ui.widget.SiderBar;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes3.dex */
public class CountrySiderBar extends SiderBar {

    /* renamed from: a, reason: collision with root package name */
    private float f11968a;

    /* renamed from: c, reason: collision with root package name */
    private int f11969c;

    public CountrySiderBar(Context context) {
        this(context, null);
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

    @Override // com.alibaba.sdk.android.openaccount.ui.widget.SiderBar
    protected int getViewHeight() {
        if (this.viewHeight == 0) {
            this.viewHeight = getHeight();
        }
        return this.viewHeight;
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.widget.SiderBar, android.view.View
    protected void onDraw(Canvas canvas) {
        int height = getHeight();
        int width = getWidth();
        float viewHeight = (getViewHeight() * 1.0f) / SiderBar.f8934b.length;
        for (int i2 = 0; i2 < SiderBar.f8934b.length; i2++) {
            float f2 = (i2 * viewHeight) + viewHeight;
            if (f2 > height) {
                return;
            }
            float f3 = width / 2;
            this.paint.setColor(getResources().getColor(this.f11969c));
            Paint paint = this.paint;
            Typeface typeface = Typeface.DEFAULT_BOLD;
            paint.setTypeface(typeface);
            this.paint.setTextSize(a(getContext(), 11.0f));
            if (i2 == this.choose) {
                setColor(R.color.white);
                setFakeBoldText(true);
            }
            this.paint.setTypeface(typeface);
            this.paint.setAntiAlias(true);
            this.paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(SiderBar.f8934b[i2], f3, f2, this.paint);
            this.paint.reset();
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i2, int i3) {
        this.paint.setAntiAlias(true);
        setTextSize(12.0f);
        int size = View.MeasureSpec.getSize(i3);
        if (View.MeasureSpec.getMode(i3) != 1073741824) {
            size = (int) (((this.paint.descent() - this.paint.ascent()) + this.f11968a) * SiderBar.f8934b.length);
        }
        setMeasuredDimension(View.getDefaultSize(0, i2), size);
    }

    public void setInterval(float f2) {
        this.f11968a = TypedValue.applyDimension(1, f2, getResources().getDisplayMetrics());
        requestLayout();
    }

    public void setTextColor(int i2) {
        this.f11969c = i2;
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.widget.SiderBar
    protected void setTextSize(float f2) {
        super.setTextSize(TypedValue.applyDimension(2, f2, getResources().getDisplayMetrics()));
    }

    public CountrySiderBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    private static int a(Context context, float f2) {
        return (int) ((f2 * a(context)) + 0.5f);
    }

    public CountrySiderBar(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f11969c = R.color.black;
        SiderBar.f8934b = new String[]{MqttTopic.MULTI_LEVEL_WILDCARD, ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, "B", "C", "D", ExifInterface.LONGITUDE_EAST, "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", ExifInterface.LATITUDE_SOUTH, ExifInterface.GPS_DIRECTION_TRUE, "U", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, ExifInterface.LONGITUDE_WEST, "X", "Y", "Z"};
    }

    private static float a(Context context) {
        return a(context.getResources().getDisplayMetrics().scaledDensity);
    }
}
