package com.alibaba.sdk.android.openaccount.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import androidx.exifinterface.media.ExifInterface;
import androidx.webkit.ProxyConfig;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes2.dex */
public class SiderBar extends View {

    /* renamed from: b, reason: collision with root package name */
    public static String[] f8934b = {ProxyConfig.MATCH_ALL_SCHEMES, ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, "B", "C", "D", ExifInterface.LONGITUDE_EAST, "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", ExifInterface.LATITUDE_SOUTH, ExifInterface.GPS_DIRECTION_TRUE, "U", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, ExifInterface.LONGITUDE_WEST, "X", "Y", "Z", MqttTopic.MULTI_LEVEL_WILDCARD};
    protected int choose;
    protected Context mContext;
    protected TextView mTextDialog;
    protected OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    protected Paint paint;
    protected int viewHeight;

    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String str);
    }

    public SiderBar(Context context) {
        super(context);
        this.choose = -1;
        this.paint = new Paint();
        this.mContext = context;
    }

    @Override // android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        float y2 = motionEvent.getY();
        int i2 = this.choose;
        OnTouchingLetterChangedListener onTouchingLetterChangedListener = this.onTouchingLetterChangedListener;
        String[] strArr = f8934b;
        int viewHeight = (int) ((y2 / getViewHeight()) * strArr.length);
        if (action == 1) {
            this.choose = -1;
            invalidate();
            TextView textView = this.mTextDialog;
            if (textView != null) {
                textView.setVisibility(4);
            }
        } else if (i2 != viewHeight && viewHeight >= 0 && viewHeight < strArr.length) {
            if (onTouchingLetterChangedListener != null) {
                onTouchingLetterChangedListener.onTouchingLetterChanged(strArr[viewHeight]);
            }
            TextView textView2 = this.mTextDialog;
            if (textView2 != null) {
                textView2.setText(f8934b[viewHeight]);
                this.mTextDialog.setVisibility(0);
            }
            this.choose = viewHeight;
            invalidate();
        }
        return true;
    }

    protected int getViewHeight() {
        if (this.viewHeight == 0) {
            this.viewHeight = getHeight();
        }
        return this.viewHeight;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
        float viewHeight = (getViewHeight() * 1.0f) / f8934b.length;
        int i2 = 0;
        while (true) {
            String[] strArr = f8934b;
            if (i2 >= strArr.length) {
                return;
            }
            float f2 = (i2 * viewHeight) + viewHeight;
            if (f2 > height) {
                return;
            }
            float fMeasureText = (width / 2) - (this.paint.measureText(strArr[i2]) / 2.0f);
            setColor(Color.rgb(23, 122, 126));
            setTypeface(Typeface.DEFAULT_BOLD);
            setTextSize(20.0f);
            if (i2 == this.choose) {
                setColor(-16776961);
                setFakeBoldText(true);
            }
            canvas.drawText(f8934b[i2], fMeasureText, f2, this.paint);
            this.paint.reset();
            i2++;
        }
    }

    protected void setColor(int i2) {
        this.paint.setColor(i2);
    }

    protected void setFakeBoldText(boolean z2) {
        this.paint.setFakeBoldText(z2);
    }

    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    protected void setTextSize(float f2) {
        this.paint.setTextSize(f2);
    }

    public void setTextView(TextView textView) {
        this.mTextDialog = textView;
    }

    protected void setTypeface(Typeface typeface) {
        this.paint.setTypeface(typeface);
    }

    public SiderBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.choose = -1;
        this.paint = new Paint();
        this.mContext = context;
    }

    public SiderBar(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.choose = -1;
        this.paint = new Paint();
        this.mContext = context;
    }
}
