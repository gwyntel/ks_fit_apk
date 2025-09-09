package com.facebook.share.internal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.facebook.common.R;

@Deprecated
/* loaded from: classes3.dex */
public class LikeBoxCountView extends FrameLayout {
    private int additionalTextPadding;
    private Paint borderPaint;
    private float borderRadius;
    private float caretHeight;
    private LikeBoxCountViewCaretPosition caretPosition;
    private float caretWidth;
    private TextView likeCountLabel;
    private int textPadding;

    /* renamed from: com.facebook.share.internal.LikeBoxCountView$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$share$internal$LikeBoxCountView$LikeBoxCountViewCaretPosition;

        static {
            int[] iArr = new int[LikeBoxCountViewCaretPosition.values().length];
            $SwitchMap$com$facebook$share$internal$LikeBoxCountView$LikeBoxCountViewCaretPosition = iArr;
            try {
                iArr[LikeBoxCountViewCaretPosition.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$facebook$share$internal$LikeBoxCountView$LikeBoxCountViewCaretPosition[LikeBoxCountViewCaretPosition.TOP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$facebook$share$internal$LikeBoxCountView$LikeBoxCountViewCaretPosition[LikeBoxCountViewCaretPosition.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$facebook$share$internal$LikeBoxCountView$LikeBoxCountViewCaretPosition[LikeBoxCountViewCaretPosition.BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public enum LikeBoxCountViewCaretPosition {
        LEFT,
        TOP,
        RIGHT,
        BOTTOM
    }

    @Deprecated
    public LikeBoxCountView(Context context) {
        super(context);
        this.caretPosition = LikeBoxCountViewCaretPosition.LEFT;
        initialize(context);
    }

    private void drawBorder(Canvas canvas, float f2, float f3, float f4, float f5) {
        Path path = new Path();
        float f6 = this.borderRadius * 2.0f;
        float f7 = f2 + f6;
        float f8 = f3 + f6;
        path.addArc(new RectF(f2, f3, f7, f8), -180.0f, 90.0f);
        if (this.caretPosition == LikeBoxCountViewCaretPosition.TOP) {
            float f9 = f4 - f2;
            path.lineTo(((f9 - this.caretWidth) / 2.0f) + f2, f3);
            path.lineTo((f9 / 2.0f) + f2, f3 - this.caretHeight);
            path.lineTo(((f9 + this.caretWidth) / 2.0f) + f2, f3);
        }
        path.lineTo(f4 - this.borderRadius, f3);
        float f10 = f4 - f6;
        path.addArc(new RectF(f10, f3, f4, f8), -90.0f, 90.0f);
        if (this.caretPosition == LikeBoxCountViewCaretPosition.RIGHT) {
            float f11 = f5 - f3;
            path.lineTo(f4, ((f11 - this.caretWidth) / 2.0f) + f3);
            path.lineTo(this.caretHeight + f4, (f11 / 2.0f) + f3);
            path.lineTo(f4, ((f11 + this.caretWidth) / 2.0f) + f3);
        }
        path.lineTo(f4, f5 - this.borderRadius);
        float f12 = f5 - f6;
        path.addArc(new RectF(f10, f12, f4, f5), 0.0f, 90.0f);
        if (this.caretPosition == LikeBoxCountViewCaretPosition.BOTTOM) {
            float f13 = f4 - f2;
            path.lineTo(((this.caretWidth + f13) / 2.0f) + f2, f5);
            path.lineTo((f13 / 2.0f) + f2, this.caretHeight + f5);
            path.lineTo(((f13 - this.caretWidth) / 2.0f) + f2, f5);
        }
        path.lineTo(this.borderRadius + f2, f5);
        path.addArc(new RectF(f2, f12, f7, f5), 90.0f, 90.0f);
        if (this.caretPosition == LikeBoxCountViewCaretPosition.LEFT) {
            float f14 = f5 - f3;
            path.lineTo(f2, ((this.caretWidth + f14) / 2.0f) + f3);
            path.lineTo(f2 - this.caretHeight, (f14 / 2.0f) + f3);
            path.lineTo(f2, ((f14 - this.caretWidth) / 2.0f) + f3);
        }
        path.lineTo(f2, f3 + this.borderRadius);
        canvas.drawPath(path, this.borderPaint);
    }

    private void initialize(Context context) {
        setWillNotDraw(false);
        this.caretHeight = getResources().getDimension(R.dimen.com_facebook_likeboxcountview_caret_height);
        this.caretWidth = getResources().getDimension(R.dimen.com_facebook_likeboxcountview_caret_width);
        this.borderRadius = getResources().getDimension(R.dimen.com_facebook_likeboxcountview_border_radius);
        Paint paint = new Paint();
        this.borderPaint = paint;
        paint.setColor(getResources().getColor(R.color.com_facebook_likeboxcountview_border_color));
        this.borderPaint.setStrokeWidth(getResources().getDimension(R.dimen.com_facebook_likeboxcountview_border_width));
        this.borderPaint.setStyle(Paint.Style.STROKE);
        initializeLikeCountLabel(context);
        addView(this.likeCountLabel);
        setCaretPosition(this.caretPosition);
    }

    private void initializeLikeCountLabel(Context context) {
        this.likeCountLabel = new TextView(context);
        this.likeCountLabel.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        this.likeCountLabel.setGravity(17);
        this.likeCountLabel.setTextSize(0, getResources().getDimension(R.dimen.com_facebook_likeboxcountview_text_size));
        this.likeCountLabel.setTextColor(getResources().getColor(R.color.com_facebook_likeboxcountview_text_color));
        this.textPadding = getResources().getDimensionPixelSize(R.dimen.com_facebook_likeboxcountview_text_padding);
        this.additionalTextPadding = getResources().getDimensionPixelSize(R.dimen.com_facebook_likeboxcountview_caret_height);
    }

    private void setAdditionalTextPadding(int i2, int i3, int i4, int i5) {
        TextView textView = this.likeCountLabel;
        int i6 = this.textPadding;
        textView.setPadding(i2 + i6, i3 + i6, i4 + i6, i6 + i5);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingTop = getPaddingTop();
        int paddingLeft = getPaddingLeft();
        int width = getWidth() - getPaddingRight();
        int height = getHeight() - getPaddingBottom();
        int i2 = AnonymousClass1.$SwitchMap$com$facebook$share$internal$LikeBoxCountView$LikeBoxCountViewCaretPosition[this.caretPosition.ordinal()];
        if (i2 == 1) {
            paddingLeft = (int) (paddingLeft + this.caretHeight);
        } else if (i2 == 2) {
            paddingTop = (int) (paddingTop + this.caretHeight);
        } else if (i2 == 3) {
            width = (int) (width - this.caretHeight);
        } else if (i2 == 4) {
            height = (int) (height - this.caretHeight);
        }
        drawBorder(canvas, paddingLeft, paddingTop, width, height);
    }

    @Deprecated
    public void setCaretPosition(LikeBoxCountViewCaretPosition likeBoxCountViewCaretPosition) {
        this.caretPosition = likeBoxCountViewCaretPosition;
        int i2 = AnonymousClass1.$SwitchMap$com$facebook$share$internal$LikeBoxCountView$LikeBoxCountViewCaretPosition[likeBoxCountViewCaretPosition.ordinal()];
        if (i2 == 1) {
            setAdditionalTextPadding(this.additionalTextPadding, 0, 0, 0);
            return;
        }
        if (i2 == 2) {
            setAdditionalTextPadding(0, this.additionalTextPadding, 0, 0);
        } else if (i2 == 3) {
            setAdditionalTextPadding(0, 0, this.additionalTextPadding, 0);
        } else {
            if (i2 != 4) {
                return;
            }
            setAdditionalTextPadding(0, 0, 0, this.additionalTextPadding);
        }
    }

    @Deprecated
    public void setText(String str) {
        this.likeCountLabel.setText(str);
    }
}
