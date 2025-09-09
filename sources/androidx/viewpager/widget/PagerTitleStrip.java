package androidx.viewpager.widget;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.method.SingleLineTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;
import androidx.health.connect.client.records.HeartRateVariabilityRmssdRecord;
import androidx.viewpager.widget.ViewPager;
import java.lang.ref.WeakReference;
import java.util.Locale;

@ViewPager.DecorView
/* loaded from: classes2.dex */
public class PagerTitleStrip extends ViewGroup {
    private static final float SIDE_ALPHA = 0.6f;
    private static final int TEXT_SPACING = 16;

    /* renamed from: a, reason: collision with root package name */
    ViewPager f6444a;

    /* renamed from: b, reason: collision with root package name */
    TextView f6445b;

    /* renamed from: c, reason: collision with root package name */
    TextView f6446c;

    /* renamed from: d, reason: collision with root package name */
    TextView f6447d;

    /* renamed from: e, reason: collision with root package name */
    float f6448e;

    /* renamed from: f, reason: collision with root package name */
    int f6449f;
    private int mGravity;
    private int mLastKnownCurrentPage;
    private int mNonPrimaryAlpha;
    private final PageListener mPageListener;
    private int mScaledTextSpacing;
    private boolean mUpdatingPositions;
    private boolean mUpdatingText;
    private WeakReference<PagerAdapter> mWatchingAdapter;
    private static final int[] ATTRS = {R.attr.textAppearance, R.attr.textSize, R.attr.textColor, R.attr.gravity};
    private static final int[] TEXT_ATTRS = {R.attr.textAllCaps};

    private class PageListener extends DataSetObserver implements ViewPager.OnPageChangeListener, ViewPager.OnAdapterChangeListener {
        private int mScrollState;

        PageListener() {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnAdapterChangeListener
        public void onAdapterChanged(ViewPager viewPager, PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
            PagerTitleStrip.this.a(pagerAdapter, pagerAdapter2);
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            PagerTitleStrip pagerTitleStrip = PagerTitleStrip.this;
            pagerTitleStrip.b(pagerTitleStrip.f6444a.getCurrentItem(), PagerTitleStrip.this.f6444a.getAdapter());
            PagerTitleStrip pagerTitleStrip2 = PagerTitleStrip.this;
            float f2 = pagerTitleStrip2.f6448e;
            if (f2 < 0.0f) {
                f2 = 0.0f;
            }
            pagerTitleStrip2.c(pagerTitleStrip2.f6444a.getCurrentItem(), f2, true);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i2) {
            this.mScrollState = i2;
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i2, float f2, int i3) {
            if (f2 > 0.5f) {
                i2++;
            }
            PagerTitleStrip.this.c(i2, f2, false);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i2) {
            if (this.mScrollState == 0) {
                PagerTitleStrip pagerTitleStrip = PagerTitleStrip.this;
                pagerTitleStrip.b(pagerTitleStrip.f6444a.getCurrentItem(), PagerTitleStrip.this.f6444a.getAdapter());
                PagerTitleStrip pagerTitleStrip2 = PagerTitleStrip.this;
                float f2 = pagerTitleStrip2.f6448e;
                if (f2 < 0.0f) {
                    f2 = 0.0f;
                }
                pagerTitleStrip2.c(pagerTitleStrip2.f6444a.getCurrentItem(), f2, true);
            }
        }
    }

    private static class SingleLineAllCapsTransform extends SingleLineTransformationMethod {
        private Locale mLocale;

        SingleLineAllCapsTransform(Context context) {
            this.mLocale = context.getResources().getConfiguration().locale;
        }

        @Override // android.text.method.ReplacementTransformationMethod, android.text.method.TransformationMethod
        public CharSequence getTransformation(CharSequence charSequence, View view) {
            CharSequence transformation = super.getTransformation(charSequence, view);
            if (transformation != null) {
                return transformation.toString().toUpperCase(this.mLocale);
            }
            return null;
        }
    }

    public PagerTitleStrip(@NonNull Context context) {
        this(context, null);
    }

    private static void setSingleLineAllCaps(TextView textView) {
        textView.setTransformationMethod(new SingleLineAllCapsTransform(textView.getContext()));
    }

    void a(PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
        if (pagerAdapter != null) {
            pagerAdapter.unregisterDataSetObserver(this.mPageListener);
            this.mWatchingAdapter = null;
        }
        if (pagerAdapter2 != null) {
            pagerAdapter2.registerDataSetObserver(this.mPageListener);
            this.mWatchingAdapter = new WeakReference<>(pagerAdapter2);
        }
        ViewPager viewPager = this.f6444a;
        if (viewPager != null) {
            this.mLastKnownCurrentPage = -1;
            this.f6448e = -1.0f;
            b(viewPager.getCurrentItem(), pagerAdapter2);
            requestLayout();
        }
    }

    void b(int i2, PagerAdapter pagerAdapter) {
        int count = pagerAdapter != null ? pagerAdapter.getCount() : 0;
        this.mUpdatingText = true;
        CharSequence pageTitle = null;
        this.f6445b.setText((i2 < 1 || pagerAdapter == null) ? null : pagerAdapter.getPageTitle(i2 - 1));
        this.f6446c.setText((pagerAdapter == null || i2 >= count) ? null : pagerAdapter.getPageTitle(i2));
        int i3 = i2 + 1;
        if (i3 < count && pagerAdapter != null) {
            pageTitle = pagerAdapter.getPageTitle(i3);
        }
        this.f6447d.setText(pageTitle);
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(Math.max(0, (int) (((getWidth() - getPaddingLeft()) - getPaddingRight()) * 0.8f)), Integer.MIN_VALUE);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(Math.max(0, (getHeight() - getPaddingTop()) - getPaddingBottom()), Integer.MIN_VALUE);
        this.f6445b.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.f6446c.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.f6447d.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mLastKnownCurrentPage = i2;
        if (!this.mUpdatingPositions) {
            c(i2, this.f6448e, false);
        }
        this.mUpdatingText = false;
    }

    void c(int i2, float f2, boolean z2) {
        int i3;
        int i4;
        int i5;
        int i6;
        if (i2 != this.mLastKnownCurrentPage) {
            b(i2, this.f6444a.getAdapter());
        } else if (!z2 && f2 == this.f6448e) {
            return;
        }
        this.mUpdatingPositions = true;
        int measuredWidth = this.f6445b.getMeasuredWidth();
        int measuredWidth2 = this.f6446c.getMeasuredWidth();
        int measuredWidth3 = this.f6447d.getMeasuredWidth();
        int i7 = measuredWidth2 / 2;
        int width = getWidth();
        int height = getHeight();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int i8 = paddingRight + i7;
        int i9 = (width - (paddingLeft + i7)) - i8;
        float f3 = 0.5f + f2;
        if (f3 > 1.0f) {
            f3 -= 1.0f;
        }
        int i10 = ((width - i8) - ((int) (i9 * f3))) - i7;
        int i11 = measuredWidth2 + i10;
        int baseline = this.f6445b.getBaseline();
        int baseline2 = this.f6446c.getBaseline();
        int baseline3 = this.f6447d.getBaseline();
        int iMax = Math.max(Math.max(baseline, baseline2), baseline3);
        int i12 = iMax - baseline;
        int i13 = iMax - baseline2;
        int i14 = iMax - baseline3;
        int iMax2 = Math.max(Math.max(this.f6445b.getMeasuredHeight() + i12, this.f6446c.getMeasuredHeight() + i13), this.f6447d.getMeasuredHeight() + i14);
        int i15 = this.mGravity & 112;
        if (i15 == 16) {
            i3 = (((height - paddingTop) - paddingBottom) - iMax2) / 2;
        } else {
            if (i15 != 80) {
                i4 = i12 + paddingTop;
                i5 = i13 + paddingTop;
                i6 = paddingTop + i14;
                TextView textView = this.f6446c;
                textView.layout(i10, i5, i11, textView.getMeasuredHeight() + i5);
                int iMin = Math.min(paddingLeft, (i10 - this.mScaledTextSpacing) - measuredWidth);
                TextView textView2 = this.f6445b;
                textView2.layout(iMin, i4, measuredWidth + iMin, textView2.getMeasuredHeight() + i4);
                int iMax3 = Math.max((width - paddingRight) - measuredWidth3, i11 + this.mScaledTextSpacing);
                TextView textView3 = this.f6447d;
                textView3.layout(iMax3, i6, iMax3 + measuredWidth3, textView3.getMeasuredHeight() + i6);
                this.f6448e = f2;
                this.mUpdatingPositions = false;
            }
            i3 = (height - paddingBottom) - iMax2;
        }
        i4 = i12 + i3;
        i5 = i13 + i3;
        i6 = i3 + i14;
        TextView textView4 = this.f6446c;
        textView4.layout(i10, i5, i11, textView4.getMeasuredHeight() + i5);
        int iMin2 = Math.min(paddingLeft, (i10 - this.mScaledTextSpacing) - measuredWidth);
        TextView textView22 = this.f6445b;
        textView22.layout(iMin2, i4, measuredWidth + iMin2, textView22.getMeasuredHeight() + i4);
        int iMax32 = Math.max((width - paddingRight) - measuredWidth3, i11 + this.mScaledTextSpacing);
        TextView textView32 = this.f6447d;
        textView32.layout(iMax32, i6, iMax32 + measuredWidth3, textView32.getMeasuredHeight() + i6);
        this.f6448e = f2;
        this.mUpdatingPositions = false;
    }

    int getMinHeight() {
        Drawable background = getBackground();
        if (background != null) {
            return background.getIntrinsicHeight();
        }
        return 0;
    }

    public int getTextSpacing() {
        return this.mScaledTextSpacing;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent parent = getParent();
        if (!(parent instanceof ViewPager)) {
            throw new IllegalStateException("PagerTitleStrip must be a direct child of a ViewPager.");
        }
        ViewPager viewPager = (ViewPager) parent;
        PagerAdapter adapter = viewPager.getAdapter();
        viewPager.p(this.mPageListener);
        viewPager.addOnAdapterChangeListener(this.mPageListener);
        this.f6444a = viewPager;
        WeakReference<PagerAdapter> weakReference = this.mWatchingAdapter;
        a(weakReference != null ? weakReference.get() : null, adapter);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ViewPager viewPager = this.f6444a;
        if (viewPager != null) {
            a(viewPager.getAdapter(), null);
            this.f6444a.p(null);
            this.f6444a.removeOnAdapterChangeListener(this.mPageListener);
            this.f6444a = null;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        if (this.f6444a != null) {
            float f2 = this.f6448e;
            if (f2 < 0.0f) {
                f2 = 0.0f;
            }
            c(this.mLastKnownCurrentPage, f2, true);
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i2, int i3) {
        int iMax;
        if (View.MeasureSpec.getMode(i2) != 1073741824) {
            throw new IllegalStateException("Must measure with an exact width");
        }
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i3, paddingTop, -2);
        int size = View.MeasureSpec.getSize(i2);
        int childMeasureSpec2 = ViewGroup.getChildMeasureSpec(i2, (int) (size * 0.2f), -2);
        this.f6445b.measure(childMeasureSpec2, childMeasureSpec);
        this.f6446c.measure(childMeasureSpec2, childMeasureSpec);
        this.f6447d.measure(childMeasureSpec2, childMeasureSpec);
        if (View.MeasureSpec.getMode(i3) == 1073741824) {
            iMax = View.MeasureSpec.getSize(i3);
        } else {
            iMax = Math.max(getMinHeight(), this.f6446c.getMeasuredHeight() + paddingTop);
        }
        setMeasuredDimension(size, View.resolveSizeAndState(iMax, i3, this.f6446c.getMeasuredState() << 16));
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (this.mUpdatingText) {
            return;
        }
        super.requestLayout();
    }

    public void setGravity(int i2) {
        this.mGravity = i2;
        requestLayout();
    }

    public void setNonPrimaryAlpha(@FloatRange(from = 0.0d, to = HeartRateVariabilityRmssdRecord.MIN_HRV_RMSSD) float f2) {
        int i2 = ((int) (f2 * 255.0f)) & 255;
        this.mNonPrimaryAlpha = i2;
        int i3 = (i2 << 24) | (this.f6449f & ViewCompat.MEASURED_SIZE_MASK);
        this.f6445b.setTextColor(i3);
        this.f6447d.setTextColor(i3);
    }

    public void setTextColor(@ColorInt int i2) {
        this.f6449f = i2;
        this.f6446c.setTextColor(i2);
        int i3 = (this.mNonPrimaryAlpha << 24) | (this.f6449f & ViewCompat.MEASURED_SIZE_MASK);
        this.f6445b.setTextColor(i3);
        this.f6447d.setTextColor(i3);
    }

    public void setTextSize(int i2, float f2) {
        this.f6445b.setTextSize(i2, f2);
        this.f6446c.setTextSize(i2, f2);
        this.f6447d.setTextSize(i2, f2);
    }

    public void setTextSpacing(int i2) {
        this.mScaledTextSpacing = i2;
        requestLayout();
    }

    public PagerTitleStrip(@NonNull Context context, @Nullable AttributeSet attributeSet) throws Resources.NotFoundException {
        super(context, attributeSet);
        this.mLastKnownCurrentPage = -1;
        this.f6448e = -1.0f;
        this.mPageListener = new PageListener();
        TextView textView = new TextView(context);
        this.f6445b = textView;
        addView(textView);
        TextView textView2 = new TextView(context);
        this.f6446c = textView2;
        addView(textView2);
        TextView textView3 = new TextView(context);
        this.f6447d = textView3;
        addView(textView3);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ATTRS);
        boolean z2 = false;
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0);
        if (resourceId != 0) {
            TextViewCompat.setTextAppearance(this.f6445b, resourceId);
            TextViewCompat.setTextAppearance(this.f6446c, resourceId);
            TextViewCompat.setTextAppearance(this.f6447d, resourceId);
        }
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(1, 0);
        if (dimensionPixelSize != 0) {
            setTextSize(0, dimensionPixelSize);
        }
        if (typedArrayObtainStyledAttributes.hasValue(2)) {
            int color = typedArrayObtainStyledAttributes.getColor(2, 0);
            this.f6445b.setTextColor(color);
            this.f6446c.setTextColor(color);
            this.f6447d.setTextColor(color);
        }
        this.mGravity = typedArrayObtainStyledAttributes.getInteger(3, 80);
        typedArrayObtainStyledAttributes.recycle();
        this.f6449f = this.f6446c.getTextColors().getDefaultColor();
        setNonPrimaryAlpha(SIDE_ALPHA);
        TextView textView4 = this.f6445b;
        TextUtils.TruncateAt truncateAt = TextUtils.TruncateAt.END;
        textView4.setEllipsize(truncateAt);
        this.f6446c.setEllipsize(truncateAt);
        this.f6447d.setEllipsize(truncateAt);
        if (resourceId != 0) {
            TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(resourceId, TEXT_ATTRS);
            z2 = typedArrayObtainStyledAttributes2.getBoolean(0, false);
            typedArrayObtainStyledAttributes2.recycle();
        }
        if (z2) {
            setSingleLineAllCaps(this.f6445b);
            setSingleLineAllCaps(this.f6446c);
            setSingleLineAllCaps(this.f6447d);
        } else {
            this.f6445b.setSingleLine();
            this.f6446c.setSingleLine();
            this.f6447d.setSingleLine();
        }
        this.mScaledTextSpacing = (int) (context.getResources().getDisplayMetrics().density * 16.0f);
    }
}
