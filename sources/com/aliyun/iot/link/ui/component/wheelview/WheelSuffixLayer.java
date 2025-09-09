package com.aliyun.iot.link.ui.component.wheelview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class WheelSuffixLayer implements WheelLayer {
    public String mSuffix;
    public int mTextColor;
    public float mTextContentMaxWidth;
    public float mTextPadding;
    public float mTextSize;
    private ILopWheelView mWheelView;

    public WheelSuffixLayer(String str, float f2, int i2, int i3) {
        this.mSuffix = str;
        this.mTextPadding = DimensionUtil.dip2px(i3);
        this.mTextSize = DimensionUtil.dip2px(f2);
        this.mTextColor = i2;
    }

    @Override // com.aliyun.iot.link.ui.component.wheelview.WheelLayer
    public void onDraw(ILopWheelView iLopWheelView, Canvas canvas, Rect rect) {
        if (this.mSuffix == null) {
            return;
        }
        if (this.mWheelView == null) {
            this.mWheelView = iLopWheelView;
        }
        Paint paint = iLopWheelView.getPaint();
        if (this.mTextContentMaxWidth == 0.0f) {
            paint.setTextSize(iLopWheelView.getSelectedItemTextSize());
            Iterator it = iLopWheelView.getData().iterator();
            while (it.hasNext()) {
                this.mTextContentMaxWidth = Math.max(this.mTextContentMaxWidth, paint.measureText(iLopWheelView.getItemDisplayText(it.next())));
            }
        }
        paint.setTextSize(this.mTextSize);
        paint.setColor(this.mTextColor);
        canvas.drawText(this.mSuffix, rect.centerX() + (this.mTextContentMaxWidth / 2.0f) + this.mTextPadding, (Math.abs(paint.getFontMetrics().ascent + paint.getFontMetrics().descent) / 2.0f) + rect.centerY(), paint);
    }

    public void setSuffix(String str) {
        this.mSuffix = str;
        ILopWheelView iLopWheelView = this.mWheelView;
        if (iLopWheelView != null) {
            iLopWheelView.invalidate();
        }
    }
}
