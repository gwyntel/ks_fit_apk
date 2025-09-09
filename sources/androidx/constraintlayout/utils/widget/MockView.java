package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.widget.R;

/* loaded from: classes.dex */
public class MockView extends View {

    /* renamed from: a, reason: collision with root package name */
    protected String f3187a;
    private int mDiagonalsColor;
    private boolean mDrawDiagonals;
    private boolean mDrawLabel;
    private int mMargin;
    private Paint mPaintDiagonals;
    private Paint mPaintText;
    private Paint mPaintTextBackground;
    private int mTextBackgroundColor;
    private Rect mTextBounds;
    private int mTextColor;

    public MockView(Context context) {
        super(context);
        this.mPaintDiagonals = new Paint();
        this.mPaintText = new Paint();
        this.mPaintTextBackground = new Paint();
        this.mDrawDiagonals = true;
        this.mDrawLabel = true;
        this.f3187a = null;
        this.mTextBounds = new Rect();
        this.mDiagonalsColor = Color.argb(255, 0, 0, 0);
        this.mTextColor = Color.argb(255, 200, 200, 200);
        this.mTextBackgroundColor = Color.argb(255, 50, 50, 50);
        this.mMargin = 4;
        init(context, null);
    }

    private void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MockView);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i2);
                if (index == R.styleable.MockView_mock_label) {
                    this.f3187a = typedArrayObtainStyledAttributes.getString(index);
                } else if (index == R.styleable.MockView_mock_showDiagonals) {
                    this.mDrawDiagonals = typedArrayObtainStyledAttributes.getBoolean(index, this.mDrawDiagonals);
                } else if (index == R.styleable.MockView_mock_diagonalsColor) {
                    this.mDiagonalsColor = typedArrayObtainStyledAttributes.getColor(index, this.mDiagonalsColor);
                } else if (index == R.styleable.MockView_mock_labelBackgroundColor) {
                    this.mTextBackgroundColor = typedArrayObtainStyledAttributes.getColor(index, this.mTextBackgroundColor);
                } else if (index == R.styleable.MockView_mock_labelColor) {
                    this.mTextColor = typedArrayObtainStyledAttributes.getColor(index, this.mTextColor);
                } else if (index == R.styleable.MockView_mock_showLabel) {
                    this.mDrawLabel = typedArrayObtainStyledAttributes.getBoolean(index, this.mDrawLabel);
                }
            }
            typedArrayObtainStyledAttributes.recycle();
        }
        if (this.f3187a == null) {
            try {
                this.f3187a = context.getResources().getResourceEntryName(getId());
            } catch (Exception unused) {
            }
        }
        this.mPaintDiagonals.setColor(this.mDiagonalsColor);
        this.mPaintDiagonals.setAntiAlias(true);
        this.mPaintText.setColor(this.mTextColor);
        this.mPaintText.setAntiAlias(true);
        this.mPaintTextBackground.setColor(this.mTextBackgroundColor);
        this.mMargin = Math.round(this.mMargin * (getResources().getDisplayMetrics().xdpi / 160.0f));
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        if (this.mDrawDiagonals) {
            width--;
            height--;
            float f2 = width;
            float f3 = height;
            canvas.drawLine(0.0f, 0.0f, f2, f3, this.mPaintDiagonals);
            canvas.drawLine(0.0f, f3, f2, 0.0f, this.mPaintDiagonals);
            canvas.drawLine(0.0f, 0.0f, f2, 0.0f, this.mPaintDiagonals);
            canvas.drawLine(f2, 0.0f, f2, f3, this.mPaintDiagonals);
            canvas.drawLine(f2, f3, 0.0f, f3, this.mPaintDiagonals);
            canvas.drawLine(0.0f, f3, 0.0f, 0.0f, this.mPaintDiagonals);
        }
        String str = this.f3187a;
        if (str == null || !this.mDrawLabel) {
            return;
        }
        this.mPaintText.getTextBounds(str, 0, str.length(), this.mTextBounds);
        float fWidth = (width - this.mTextBounds.width()) / 2.0f;
        float fHeight = ((height - this.mTextBounds.height()) / 2.0f) + this.mTextBounds.height();
        this.mTextBounds.offset((int) fWidth, (int) fHeight);
        Rect rect = this.mTextBounds;
        int i2 = rect.left;
        int i3 = this.mMargin;
        rect.set(i2 - i3, rect.top - i3, rect.right + i3, rect.bottom + i3);
        canvas.drawRect(this.mTextBounds, this.mPaintTextBackground);
        canvas.drawText(this.f3187a, fWidth, fHeight, this.mPaintText);
    }

    public MockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPaintDiagonals = new Paint();
        this.mPaintText = new Paint();
        this.mPaintTextBackground = new Paint();
        this.mDrawDiagonals = true;
        this.mDrawLabel = true;
        this.f3187a = null;
        this.mTextBounds = new Rect();
        this.mDiagonalsColor = Color.argb(255, 0, 0, 0);
        this.mTextColor = Color.argb(255, 200, 200, 200);
        this.mTextBackgroundColor = Color.argb(255, 50, 50, 50);
        this.mMargin = 4;
        init(context, attributeSet);
    }

    public MockView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mPaintDiagonals = new Paint();
        this.mPaintText = new Paint();
        this.mPaintTextBackground = new Paint();
        this.mDrawDiagonals = true;
        this.mDrawLabel = true;
        this.f3187a = null;
        this.mTextBounds = new Rect();
        this.mDiagonalsColor = Color.argb(255, 0, 0, 0);
        this.mTextColor = Color.argb(255, 200, 200, 200);
        this.mTextBackgroundColor = Color.argb(255, 50, 50, 50);
        this.mMargin = 4;
        init(context, attributeSet);
    }
}
