package com.afollestad.materialdialogs.internal;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ScrollView;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.R;
import com.afollestad.materialdialogs.StackingBehavior;
import com.afollestad.materialdialogs.util.DialogUtils;

/* loaded from: classes2.dex */
public class MDRootLayout extends ViewGroup {
    private static final int INDEX_NEGATIVE = 1;
    private static final int INDEX_NEUTRAL = 0;
    private static final int INDEX_POSITIVE = 2;
    private ViewTreeObserver.OnScrollChangedListener bottomOnScrollChangedListener;
    private int buttonBarHeight;
    private GravityEnum buttonGravity;
    private int buttonHorizontalEdgeMargin;
    private int buttonPaddingFull;
    private final MDButton[] buttons;
    private View content;
    private Paint dividerPaint;
    private int dividerWidth;
    private boolean drawBottomDivider;
    private boolean drawTopDivider;
    private boolean isStacked;
    private int maxHeight;
    private boolean noTitleNoPadding;
    private int noTitlePaddingFull;
    private boolean reducePaddingNoTitleNoButtons;
    private StackingBehavior stackBehavior;
    private View titleBar;
    private ViewTreeObserver.OnScrollChangedListener topOnScrollChangedListener;
    private boolean useFullPadding;

    /* renamed from: com.afollestad.materialdialogs.internal.MDRootLayout$4, reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f8255a;

        static {
            int[] iArr = new int[GravityEnum.values().length];
            f8255a = iArr;
            try {
                iArr[GravityEnum.START.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f8255a[GravityEnum.END.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public MDRootLayout(Context context) {
        super(context);
        this.buttons = new MDButton[3];
        this.drawTopDivider = false;
        this.drawBottomDivider = false;
        this.stackBehavior = StackingBehavior.ADAPTIVE;
        this.isStacked = false;
        this.useFullPadding = true;
        this.buttonGravity = GravityEnum.START;
        init(context, null, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addScrollListener(final ViewGroup viewGroup, final boolean z2, final boolean z3) {
        if ((z3 || this.topOnScrollChangedListener != null) && !(z3 && this.bottomOnScrollChangedListener == null)) {
            return;
        }
        if (viewGroup instanceof RecyclerView) {
            RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() { // from class: com.afollestad.materialdialogs.internal.MDRootLayout.2
                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public void onScrolled(RecyclerView recyclerView, int i2, int i3) {
                    super.onScrolled(recyclerView, i2, i3);
                    MDButton[] mDButtonArr = MDRootLayout.this.buttons;
                    int length = mDButtonArr.length;
                    boolean z4 = false;
                    int i4 = 0;
                    while (true) {
                        if (i4 < length) {
                            MDButton mDButton = mDButtonArr[i4];
                            if (mDButton != null && mDButton.getVisibility() != 8) {
                                z4 = true;
                                break;
                            }
                            i4++;
                        } else {
                            break;
                        }
                    }
                    MDRootLayout.this.invalidateDividersForScrollingView(viewGroup, z2, z3, z4);
                    MDRootLayout.this.invalidate();
                }
            };
            RecyclerView recyclerView = (RecyclerView) viewGroup;
            recyclerView.addOnScrollListener(onScrollListener);
            onScrollListener.onScrolled(recyclerView, 0, 0);
            return;
        }
        ViewTreeObserver.OnScrollChangedListener onScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() { // from class: com.afollestad.materialdialogs.internal.MDRootLayout.3
            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public void onScrollChanged() {
                MDButton[] mDButtonArr = MDRootLayout.this.buttons;
                int length = mDButtonArr.length;
                boolean z4 = false;
                int i2 = 0;
                while (true) {
                    if (i2 < length) {
                        MDButton mDButton = mDButtonArr[i2];
                        if (mDButton != null && mDButton.getVisibility() != 8) {
                            z4 = true;
                            break;
                        }
                        i2++;
                    } else {
                        break;
                    }
                }
                ViewGroup viewGroup2 = viewGroup;
                if (viewGroup2 instanceof WebView) {
                    MDRootLayout.this.invalidateDividersForWebView((WebView) viewGroup2, z2, z3, z4);
                } else {
                    MDRootLayout.this.invalidateDividersForScrollingView(viewGroup2, z2, z3, z4);
                }
                MDRootLayout.this.invalidate();
            }
        };
        if (z3) {
            this.bottomOnScrollChangedListener = onScrollChangedListener;
            viewGroup.getViewTreeObserver().addOnScrollChangedListener(this.bottomOnScrollChangedListener);
        } else {
            this.topOnScrollChangedListener = onScrollChangedListener;
            viewGroup.getViewTreeObserver().addOnScrollChangedListener(this.topOnScrollChangedListener);
        }
        onScrollChangedListener.onScrollChanged();
    }

    private static boolean canAdapterViewScroll(AdapterView adapterView) {
        if (adapterView.getLastVisiblePosition() == -1) {
            return false;
        }
        return !(adapterView.getFirstVisiblePosition() == 0) || !(adapterView.getLastVisiblePosition() == adapterView.getCount() - 1) || adapterView.getChildCount() <= 0 || adapterView.getChildAt(0).getTop() < adapterView.getPaddingTop() || adapterView.getChildAt(adapterView.getChildCount() - 1).getBottom() > adapterView.getHeight() - adapterView.getPaddingBottom();
    }

    public static boolean canRecyclerViewScroll(RecyclerView recyclerView) {
        return (recyclerView == null || recyclerView.getLayoutManager() == null || !recyclerView.getLayoutManager().canScrollVertically()) ? false : true;
    }

    private static boolean canScrollViewScroll(ScrollView scrollView) {
        if (scrollView.getChildCount() == 0) {
            return false;
        }
        return (scrollView.getMeasuredHeight() - scrollView.getPaddingTop()) - scrollView.getPaddingBottom() < scrollView.getChildAt(0).getMeasuredHeight();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean canWebViewScroll(WebView webView) {
        return ((float) webView.getMeasuredHeight()) < ((float) webView.getContentHeight()) * webView.getScale();
    }

    @Nullable
    private static View getBottomView(ViewGroup viewGroup) {
        if (viewGroup == null || viewGroup.getChildCount() == 0) {
            return null;
        }
        for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = viewGroup.getChildAt(childCount);
            if (childAt.getVisibility() == 0 && childAt.getBottom() == viewGroup.getMeasuredHeight()) {
                return childAt;
            }
        }
        return null;
    }

    @Nullable
    private static View getTopView(ViewGroup viewGroup) {
        if (viewGroup == null || viewGroup.getChildCount() == 0) {
            return null;
        }
        for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = viewGroup.getChildAt(childCount);
            if (childAt.getVisibility() == 0 && childAt.getTop() == 0) {
                return childAt;
            }
        }
        return null;
    }

    private void init(Context context, AttributeSet attributeSet, int i2) {
        Resources resources = context.getResources();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MDRootLayout, i2, 0);
        this.reducePaddingNoTitleNoButtons = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MDRootLayout_md_reduce_padding_no_title_no_buttons, true);
        typedArrayObtainStyledAttributes.recycle();
        this.noTitlePaddingFull = resources.getDimensionPixelSize(R.dimen.md_notitle_vertical_padding);
        this.buttonPaddingFull = resources.getDimensionPixelSize(R.dimen.md_button_frame_vertical_padding);
        this.buttonHorizontalEdgeMargin = resources.getDimensionPixelSize(R.dimen.md_button_padding_frame_side);
        this.buttonBarHeight = resources.getDimensionPixelSize(R.dimen.md_button_height);
        this.dividerPaint = new Paint();
        this.dividerWidth = resources.getDimensionPixelSize(R.dimen.md_divider_height);
        this.dividerPaint.setColor(DialogUtils.resolveColor(context, R.attr.md_divider_color));
        setWillNotDraw(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidateDividersForScrollingView(ViewGroup viewGroup, boolean z2, boolean z3, boolean z4) {
        if (z2 && viewGroup.getChildCount() > 0) {
            View view = this.titleBar;
            this.drawTopDivider = (view == null || view.getVisibility() == 8 || viewGroup.getScrollY() + viewGroup.getPaddingTop() <= viewGroup.getChildAt(0).getTop()) ? false : true;
        }
        if (!z3 || viewGroup.getChildCount() <= 0) {
            return;
        }
        this.drawBottomDivider = z4 && (viewGroup.getScrollY() + viewGroup.getHeight()) - viewGroup.getPaddingBottom() < viewGroup.getChildAt(viewGroup.getChildCount() - 1).getBottom();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidateDividersForWebView(WebView webView, boolean z2, boolean z3, boolean z4) {
        boolean z5 = false;
        if (z2) {
            View view = this.titleBar;
            this.drawTopDivider = (view == null || view.getVisibility() == 8 || webView.getScrollY() + webView.getPaddingTop() <= 0) ? false : true;
        }
        if (z3) {
            if (z4 && (webView.getScrollY() + webView.getMeasuredHeight()) - webView.getPaddingBottom() < webView.getContentHeight() * webView.getScale()) {
                z5 = true;
            }
            this.drawBottomDivider = z5;
        }
    }

    private void invertGravityIfNecessary() {
        if (getResources().getConfiguration().getLayoutDirection() == 1) {
            int i2 = AnonymousClass4.f8255a[this.buttonGravity.ordinal()];
            if (i2 == 1) {
                this.buttonGravity = GravityEnum.END;
            } else {
                if (i2 != 2) {
                    return;
                }
                this.buttonGravity = GravityEnum.START;
            }
        }
    }

    private static boolean isVisible(View view) {
        boolean z2 = (view == null || view.getVisibility() == 8) ? false : true;
        if (z2 && (view instanceof MDButton)) {
            return ((MDButton) view).getText().toString().trim().length() > 0;
        }
        return z2;
    }

    private void setUpDividersVisibility(final View view, final boolean z2, final boolean z3) {
        if (view == null) {
            return;
        }
        if (view instanceof ScrollView) {
            ScrollView scrollView = (ScrollView) view;
            if (canScrollViewScroll(scrollView)) {
                addScrollListener(scrollView, z2, z3);
                return;
            }
            if (z2) {
                this.drawTopDivider = false;
            }
            if (z3) {
                this.drawBottomDivider = false;
                return;
            }
            return;
        }
        if (view instanceof AdapterView) {
            AdapterView adapterView = (AdapterView) view;
            if (canAdapterViewScroll(adapterView)) {
                addScrollListener(adapterView, z2, z3);
                return;
            }
            if (z2) {
                this.drawTopDivider = false;
            }
            if (z3) {
                this.drawBottomDivider = false;
                return;
            }
            return;
        }
        if (view instanceof WebView) {
            view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.afollestad.materialdialogs.internal.MDRootLayout.1
                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public boolean onPreDraw() {
                    if (view.getMeasuredHeight() == 0) {
                        return true;
                    }
                    if (MDRootLayout.canWebViewScroll((WebView) view)) {
                        MDRootLayout.this.addScrollListener((ViewGroup) view, z2, z3);
                    } else {
                        if (z2) {
                            MDRootLayout.this.drawTopDivider = false;
                        }
                        if (z3) {
                            MDRootLayout.this.drawBottomDivider = false;
                        }
                    }
                    view.getViewTreeObserver().removeOnPreDrawListener(this);
                    return true;
                }
            });
            return;
        }
        if (view instanceof RecyclerView) {
            boolean zCanRecyclerViewScroll = canRecyclerViewScroll((RecyclerView) view);
            if (z2) {
                this.drawTopDivider = zCanRecyclerViewScroll;
            }
            if (z3) {
                this.drawBottomDivider = zCanRecyclerViewScroll;
            }
            if (zCanRecyclerViewScroll) {
                addScrollListener((ViewGroup) view, z2, z3);
                return;
            }
            return;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            View topView = getTopView(viewGroup);
            setUpDividersVisibility(topView, z2, z3);
            View bottomView = getBottomView(viewGroup);
            if (bottomView != topView) {
                setUpDividersVisibility(bottomView, false, true);
            }
        }
    }

    public void noTitleNoPadding() {
        this.noTitleNoPadding = true;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        View view = this.content;
        if (view != null) {
            if (this.drawTopDivider) {
                canvas.drawRect(0.0f, r0 - this.dividerWidth, getMeasuredWidth(), view.getTop(), this.dividerPaint);
            }
            if (this.drawBottomDivider) {
                canvas.drawRect(0.0f, this.content.getBottom(), getMeasuredWidth(), r0 + this.dividerWidth, this.dividerPaint);
            }
        }
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getId() == R.id.md_titleFrame) {
                this.titleBar = childAt;
            } else if (childAt.getId() == R.id.md_buttonDefaultNeutral) {
                this.buttons[0] = (MDButton) childAt;
            } else if (childAt.getId() == R.id.md_buttonDefaultNegative) {
                this.buttons[1] = (MDButton) childAt;
            } else if (childAt.getId() == R.id.md_buttonDefaultPositive) {
                this.buttons[2] = (MDButton) childAt;
            } else {
                this.content = childAt;
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int i6;
        int measuredWidth;
        int measuredWidth2;
        int measuredWidth3;
        int measuredWidth4;
        int measuredWidth5;
        int measuredWidth6;
        int measuredWidth7;
        int measuredWidth8;
        if (isVisible(this.titleBar)) {
            int measuredHeight = this.titleBar.getMeasuredHeight() + i3;
            this.titleBar.layout(i2, i3, i4, measuredHeight);
            i3 = measuredHeight;
        } else if (!this.noTitleNoPadding && this.useFullPadding) {
            i3 += this.noTitlePaddingFull;
        }
        if (isVisible(this.content)) {
            View view = this.content;
            view.layout(i2, i3, i4, view.getMeasuredHeight() + i3);
        }
        if (this.isStacked) {
            int measuredHeight2 = i5 - this.buttonPaddingFull;
            for (MDButton mDButton : this.buttons) {
                if (isVisible(mDButton)) {
                    mDButton.layout(i2, measuredHeight2 - mDButton.getMeasuredHeight(), i4, measuredHeight2);
                    measuredHeight2 -= mDButton.getMeasuredHeight();
                }
            }
        } else {
            if (this.useFullPadding) {
                i5 -= this.buttonPaddingFull;
            }
            int i7 = i5 - this.buttonBarHeight;
            int measuredWidth9 = this.buttonHorizontalEdgeMargin;
            if (isVisible(this.buttons[2])) {
                if (this.buttonGravity == GravityEnum.END) {
                    measuredWidth7 = i2 + measuredWidth9;
                    measuredWidth8 = this.buttons[2].getMeasuredWidth() + measuredWidth7;
                    i6 = -1;
                } else {
                    int i8 = i4 - measuredWidth9;
                    measuredWidth7 = i8 - this.buttons[2].getMeasuredWidth();
                    measuredWidth8 = i8;
                    i6 = measuredWidth7;
                }
                this.buttons[2].layout(measuredWidth7, i7, measuredWidth8, i5);
                measuredWidth9 += this.buttons[2].getMeasuredWidth();
            } else {
                i6 = -1;
            }
            if (isVisible(this.buttons[1])) {
                GravityEnum gravityEnum = this.buttonGravity;
                if (gravityEnum == GravityEnum.END) {
                    measuredWidth5 = measuredWidth9 + i2;
                    measuredWidth6 = this.buttons[1].getMeasuredWidth() + measuredWidth5;
                } else if (gravityEnum == GravityEnum.START) {
                    measuredWidth6 = i4 - measuredWidth9;
                    measuredWidth5 = measuredWidth6 - this.buttons[1].getMeasuredWidth();
                } else {
                    measuredWidth5 = this.buttonHorizontalEdgeMargin + i2;
                    measuredWidth6 = this.buttons[1].getMeasuredWidth() + measuredWidth5;
                    measuredWidth = measuredWidth6;
                    this.buttons[1].layout(measuredWidth5, i7, measuredWidth6, i5);
                }
                measuredWidth = -1;
                this.buttons[1].layout(measuredWidth5, i7, measuredWidth6, i5);
            } else {
                measuredWidth = -1;
            }
            if (isVisible(this.buttons[0])) {
                GravityEnum gravityEnum2 = this.buttonGravity;
                if (gravityEnum2 == GravityEnum.END) {
                    measuredWidth3 = i4 - this.buttonHorizontalEdgeMargin;
                    measuredWidth4 = measuredWidth3 - this.buttons[0].getMeasuredWidth();
                } else if (gravityEnum2 == GravityEnum.START) {
                    measuredWidth4 = i2 + this.buttonHorizontalEdgeMargin;
                    measuredWidth3 = this.buttons[0].getMeasuredWidth() + measuredWidth4;
                } else {
                    if (measuredWidth != -1 || i6 == -1) {
                        if (i6 == -1 && measuredWidth != -1) {
                            measuredWidth2 = this.buttons[0].getMeasuredWidth();
                        } else if (i6 == -1) {
                            measuredWidth = ((i4 - i2) / 2) - (this.buttons[0].getMeasuredWidth() / 2);
                            measuredWidth2 = this.buttons[0].getMeasuredWidth();
                        }
                        i6 = measuredWidth + measuredWidth2;
                    } else {
                        measuredWidth = i6 - this.buttons[0].getMeasuredWidth();
                    }
                    measuredWidth3 = i6;
                    measuredWidth4 = measuredWidth;
                }
                this.buttons[0].layout(measuredWidth4, i7, measuredWidth3, i5);
            }
        }
        setUpDividersVisibility(this.content, true, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x010c  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onMeasure(int r12, int r13) {
        /*
            Method dump skipped, instructions count: 274
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.afollestad.materialdialogs.internal.MDRootLayout.onMeasure(int, int):void");
    }

    public void setButtonGravity(GravityEnum gravityEnum) {
        this.buttonGravity = gravityEnum;
        invertGravityIfNecessary();
    }

    public void setButtonStackedGravity(GravityEnum gravityEnum) {
        for (MDButton mDButton : this.buttons) {
            if (mDButton != null) {
                mDButton.setStackedGravity(gravityEnum);
            }
        }
    }

    public void setDividerColor(int i2) {
        this.dividerPaint.setColor(i2);
        invalidate();
    }

    public void setMaxHeight(int i2) {
        this.maxHeight = i2;
    }

    public void setStackingBehavior(StackingBehavior stackingBehavior) {
        this.stackBehavior = stackingBehavior;
        invalidate();
    }

    public MDRootLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.buttons = new MDButton[3];
        this.drawTopDivider = false;
        this.drawBottomDivider = false;
        this.stackBehavior = StackingBehavior.ADAPTIVE;
        this.isStacked = false;
        this.useFullPadding = true;
        this.buttonGravity = GravityEnum.START;
        init(context, attributeSet, 0);
    }

    @TargetApi(11)
    public MDRootLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.buttons = new MDButton[3];
        this.drawTopDivider = false;
        this.drawBottomDivider = false;
        this.stackBehavior = StackingBehavior.ADAPTIVE;
        this.isStacked = false;
        this.useFullPadding = true;
        this.buttonGravity = GravityEnum.START;
        init(context, attributeSet, i2);
    }

    @TargetApi(21)
    public MDRootLayout(Context context, AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
        this.buttons = new MDButton[3];
        this.drawTopDivider = false;
        this.drawBottomDivider = false;
        this.stackBehavior = StackingBehavior.ADAPTIVE;
        this.isStacked = false;
        this.useFullPadding = true;
        this.buttonGravity = GravityEnum.START;
        init(context, attributeSet, i2);
    }
}
