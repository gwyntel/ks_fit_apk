package com.aliyun.iot.link.ui.component.nav;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import com.aliyun.iot.link.ui.component.R;
import com.aliyun.iot.link.ui.component.nav.UIBarItem;
import java.util.ArrayList;
import java.util.List;

@SuppressLint({"ClickableViewAccessibility"})
/* loaded from: classes3.dex */
public class UINavigationBar extends FrameLayout {
    private static final int DISPLAY_TITLE_CENTER_IN_BAR = 4;
    private static final int DISPLAY_TITLE_CENTER_IN_CONTENT = 2;
    private static final int DISPLAY_TITLE_UNDECLARED = 1;
    public static final String TAG = "UINavigationBar";
    private ColorStateList actionImageTintList;
    private ColorStateList actionTextTintList;
    private FrameLayout contentView;
    private int displayMode;
    private View divider;
    private TextView internalTitleView;
    private TextView navigationBack;
    private final View.OnTouchListener observableAlphaListener;
    private List<UIBarButtonItem> rightBarButtonItems;
    private LinearLayout rightBarNavigationView;
    private Rect touchRect;

    public UINavigationBar(Context context) {
        this(context, null);
    }

    private void findSubViews() {
        this.internalTitleView = new TextView(new ContextThemeWrapper(getContext(), R.style.ui__navigationBar_Title));
        this.contentView = (FrameLayout) findViewById(R.id.ui_nav_bar_content_view);
        TextView textView = (TextView) findViewById(R.id.ui_nav_bar_nav_back);
        this.navigationBack = textView;
        textView.setOnTouchListener(this.observableAlphaListener);
        this.navigationBack.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.iot.link.ui.component.nav.UINavigationBar.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
        this.divider = findViewById(R.id.ui_nav_bar_divider);
        this.rightBarNavigationView = (LinearLayout) findViewById(R.id.ui_nav_bar_menu_view);
    }

    private int getMaxTextControlWidth() throws Resources.NotFoundException {
        return (((int) (getTotalWidth() * 0.32d)) - getResources().getDimensionPixelOffset(R.dimen.ui_nav_menu_view_inset_start_text)) - getResources().getDimensionPixelOffset(R.dimen.ui_nav_menu_view_inset_end_text);
    }

    private int getTotalWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void inflateMenuControl(final UIBarButtonItem uIBarButtonItem, int i2) throws Resources.NotFoundException {
        LinearLayout.LayoutParams layoutParams;
        TextView textView;
        if (this.rightBarNavigationView == null) {
            return;
        }
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.ui_nav_bar_control_size);
        if (uIBarButtonItem.getIcon() != null) {
            layoutParams = new LinearLayout.LayoutParams(dimensionPixelSize, dimensionPixelSize);
            layoutParams.gravity = 16;
            ImageView imageView = new ImageView(new ContextThemeWrapper(getContext(), R.style.ui__navigationBar_ImageControl));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setImageDrawable(uIBarButtonItem.getIcon());
            ColorStateList colorStateList = this.actionImageTintList;
            textView = imageView;
            if (colorStateList != null) {
                imageView.setImageTintList(colorStateList);
                textView = imageView;
            }
        } else {
            if (uIBarButtonItem.getTitle() == null) {
                throw new IllegalArgumentException("UIBarItem should has either title or image");
            }
            layoutParams = new LinearLayout.LayoutParams(-2, dimensionPixelSize);
            layoutParams.gravity = 16;
            TextView textView2 = new TextView(new ContextThemeWrapper(getContext(), R.style.ui__navigationBar_TextControl));
            textView2.setMaxWidth(getMaxTextControlWidth());
            textView2.setText(uIBarButtonItem.getTitle());
            ColorStateList colorStateList2 = this.actionTextTintList;
            textView = textView2;
            if (colorStateList2 != null) {
                textView2.setTextColor(colorStateList2);
                textView = textView2;
            }
        }
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.iot.link.ui.component.nav.UINavigationBar.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UIBarItem.Action action = uIBarButtonItem.action;
                if (action != null) {
                    action.invoke(view);
                }
            }
        });
        textView.setOnTouchListener(this.observableAlphaListener);
        textView.setTag(R.string.ui_navigation_menu_item_raw, uIBarButtonItem);
        textView.setAlpha(uIBarButtonItem.isEnable ? 1.0f : 0.4f);
        textView.setEnabled(uIBarButtonItem.isEnable);
        this.rightBarNavigationView.addView(textView, i2, layoutParams);
    }

    private void inflateView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.link_actionbar_layout, (ViewGroup) this, true);
    }

    private void requestLayoutTitle() throws Resources.NotFoundException {
        setRightViewPadding();
        TextView textView = this.internalTitleView;
        if (textView == null) {
            return;
        }
        CharSequence text = textView.getText();
        Paint paint = this.internalTitleView.getPaint();
        if (paint == null) {
            paint = new Paint();
            paint.setTextSize(getResources().getDimensionPixelSize(R.dimen.ui_nav_bar_title_size));
            paint.setTypeface(Typeface.DEFAULT);
        }
        Rect rect = new Rect();
        boolean z2 = text != null && text.length() > 0;
        paint.getTextBounds(z2 ? text.toString() : "", 0, z2 ? text.length() : 0, rect);
        final int iWidth = z2 ? rect.width() : 0;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.aliyun.iot.link.ui.component.nav.UINavigationBar.6
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                UINavigationBar.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                UINavigationBar uINavigationBar = UINavigationBar.this;
                uINavigationBar.updateTitleDisplayMode(iWidth, uINavigationBar.rightBarNavigationView.getWidth());
            }
        });
    }

    private void setRightViewPadding() throws Resources.NotFoundException {
        int dimensionPixelOffset;
        int dimensionPixelOffset2;
        if (this.rightBarNavigationView == null) {
            return;
        }
        if (this.rightBarButtonItems.isEmpty()) {
            this.rightBarNavigationView.setPadding(0, 0, getResources().getDimensionPixelOffset(R.dimen.ui_nav_menu_view_inset_end_text), 0);
            return;
        }
        if (this.rightBarButtonItems.get(0).getTitle() != null) {
            dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.ui_nav_menu_view_inset_start_text);
            dimensionPixelOffset2 = getResources().getDimensionPixelOffset(R.dimen.ui_nav_menu_view_inset_end_text);
        } else {
            dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.ui_nav_menu_view_inset_start_icon);
            dimensionPixelOffset2 = getResources().getDimensionPixelOffset(R.dimen.ui_nav_menu_view_inset_end_icon);
        }
        this.rightBarNavigationView.setPadding(dimensionPixelOffset, 0, dimensionPixelOffset2, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTitleDisplayMode(int i2, int i3) {
        int i4 = Math.min((getTotalWidth() / 2) - (this.navigationBack.getVisibility() == 0 ? this.navigationBack.getWidth() : 0), (getTotalWidth() / 2) - i3) <= i2 / 2 ? 2 : 4;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        int i5 = this.displayMode;
        if (i5 != i4) {
            if (i4 == 2) {
                if (i5 != 1) {
                    removeView(this.internalTitleView);
                }
                this.contentView.addView(this.internalTitleView, layoutParams);
            } else if (i4 == 4) {
                if (i5 != 1) {
                    this.contentView.removeView(this.internalTitleView);
                }
                addView(this.internalTitleView, layoutParams);
            }
            this.displayMode = i4;
        }
    }

    public void addItem(UIBarButtonItem uIBarButtonItem) throws Resources.NotFoundException {
        LinearLayout linearLayout = this.rightBarNavigationView;
        if (linearLayout != null) {
            setItem(uIBarButtonItem, linearLayout.getChildCount());
        }
    }

    public TextView getNavigationBack() {
        return this.navigationBack;
    }

    public void removeItem(UIBarButtonItem uIBarButtonItem) throws Resources.NotFoundException {
        if (this.rightBarNavigationView != null) {
            removeItemAt(this.rightBarButtonItems.indexOf(uIBarButtonItem));
        }
    }

    public void removeItemAt(int i2) throws Resources.NotFoundException {
        if (this.rightBarNavigationView == null || i2 < 0) {
            return;
        }
        this.rightBarButtonItems.remove(i2);
        this.rightBarNavigationView.removeViewAt(i2);
        requestLayoutTitle();
    }

    public void setDisplayDividerEnable(boolean z2) {
        if (z2) {
            this.divider.setVisibility(0);
        } else {
            this.divider.setVisibility(4);
        }
    }

    public void setDisplayNavBackEnabled(boolean z2) throws Resources.NotFoundException {
        int i2 = z2 ? 0 : 4;
        TextView textView = this.navigationBack;
        if (textView != null) {
            textView.setVisibility(i2);
            requestLayoutTitle();
        }
    }

    public void setDisplayTitleEnabled(boolean z2) {
        int i2 = z2 ? 0 : 4;
        TextView textView = this.internalTitleView;
        if (textView != null) {
            textView.setVisibility(i2);
        }
    }

    public void setDivider(@DrawableRes int i2) {
        if (i2 != 0) {
            this.divider.setBackgroundResource(i2);
        } else {
            this.divider.setBackground(null);
        }
    }

    public void setItem(UIBarButtonItem uIBarButtonItem, int i2) throws Resources.NotFoundException {
        if (this.rightBarNavigationView != null) {
            if (i2 == this.rightBarButtonItems.size()) {
                this.rightBarButtonItems.add(uIBarButtonItem);
                requestLayoutTitle();
            } else {
                this.rightBarButtonItems.set(i2, uIBarButtonItem);
                this.rightBarNavigationView.removeViewAt(i2);
            }
            inflateMenuControl(uIBarButtonItem, i2);
        }
    }

    public void setNavigationBackAction(@Nullable final UIBarItem.Action action) {
        TextView textView = this.navigationBack;
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.iot.link.ui.component.nav.UINavigationBar.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    UIBarItem.Action action2 = action;
                    if (action2 != null) {
                        action2.invoke(view);
                    }
                }
            });
        }
    }

    public void setNavigationIcon(Drawable drawable) throws Resources.NotFoundException {
        TextView textView = this.navigationBack;
        if (textView != null) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, (Drawable) null, (Drawable) null, (Drawable) null);
            requestLayoutTitle();
        }
    }

    public void setNavigationText(@Nullable String str) throws Resources.NotFoundException {
        TextView textView = this.navigationBack;
        if (textView != null) {
            textView.setText(str);
            requestLayoutTitle();
        }
    }

    public void setTitle(@StringRes int i2) throws Resources.NotFoundException {
        setTitle(getResources().getString(i2));
    }

    public void updateItem(int i2) throws Resources.NotFoundException {
        if (this.rightBarNavigationView != null) {
            UIBarButtonItem uIBarButtonItem = null;
            int i3 = 0;
            while (true) {
                if (i3 >= this.rightBarNavigationView.getChildCount()) {
                    i3 = -1;
                    break;
                }
                uIBarButtonItem = (UIBarButtonItem) this.rightBarNavigationView.getChildAt(i3).getTag(R.string.ui_navigation_menu_item_raw);
                if (uIBarButtonItem != null && uIBarButtonItem.tag == i2) {
                    break;
                } else {
                    i3++;
                }
            }
            if (-1 == i3) {
                return;
            }
            View childAt = this.rightBarNavigationView.getChildAt(i3);
            if (childAt instanceof TextView) {
                ((TextView) childAt).setText(uIBarButtonItem.getTitle());
            } else if (!(childAt instanceof ImageView)) {
                return;
            } else {
                ((ImageView) childAt).setImageDrawable(uIBarButtonItem.getIcon());
            }
            childAt.setAlpha(uIBarButtonItem.isEnable ? 1.0f : 0.4f);
            childAt.setEnabled(uIBarButtonItem.isEnable);
            childAt.setTag(R.string.ui_navigation_menu_item_raw, uIBarButtonItem);
            final UIBarItem.Action action = uIBarButtonItem.action;
            childAt.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.iot.link.ui.component.nav.UINavigationBar.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    UIBarItem.Action action2 = action;
                    if (action2 != null) {
                        action2.invoke(view);
                    }
                }
            });
            this.rightBarButtonItems.set(i3, uIBarButtonItem);
            requestLayoutTitle();
        }
    }

    public UINavigationBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public void setTitle(CharSequence charSequence) throws Resources.NotFoundException {
        TextView textView = this.internalTitleView;
        if (textView != null) {
            textView.setText(charSequence);
            requestLayoutTitle();
        }
    }

    public UINavigationBar(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.displayMode = 1;
        this.rightBarButtonItems = new ArrayList();
        this.touchRect = new Rect();
        this.observableAlphaListener = new View.OnTouchListener() { // from class: com.aliyun.iot.link.ui.component.nav.UINavigationBar.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == 2) {
                    view.getDrawingRect(UINavigationBar.this.touchRect);
                    if (!UINavigationBar.this.touchRect.contains((int) motionEvent.getX(), (int) motionEvent.getY()) && view.getAlpha() != 1.0f) {
                        view.setAlpha(1.0f);
                        return false;
                    }
                }
                if (action != 0 && action != 1 && action != 3) {
                    return false;
                }
                try {
                    UIBarItem uIBarItem = (UIBarItem) view.getTag(R.string.ui_navigation_menu_item_raw);
                    if (uIBarItem != null) {
                        if (!uIBarItem.isEnable) {
                            return false;
                        }
                    }
                } catch (Exception e2) {
                    Log.e(UINavigationBar.TAG, "onTouch: ", e2);
                }
                if (action != 0) {
                    if ((action == 1 || action == 3) && view.getAlpha() != 1.0f) {
                        view.setAlpha(1.0f);
                    }
                } else if (view.getAlpha() != 0.4f) {
                    view.setAlpha(0.4f);
                }
                return false;
            }
        };
        if (getBackground() == null) {
            setBackgroundColor(-1);
        }
        setMinimumHeight(getResources().getDimensionPixelOffset(R.dimen.ui_nav_bar_height));
        inflateView(context);
        findSubViews();
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.UINavigationBar, 0, 0);
        try {
            Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.UINavigationBar_divider);
            if (drawable != null) {
                this.divider.setBackground(drawable);
            }
            Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(R.styleable.UINavigationBar_navigationIcon);
            if (drawable2 == null) {
                setNavigationIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_nav_back));
            } else {
                setNavigationIcon(drawable2);
            }
            setNavigationText(typedArrayObtainStyledAttributes.getString(R.styleable.UINavigationBar_navigationText));
            int resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.UINavigationBar_titleTextStyle, R.style.ui__navigationBar_TitleTextAppearance);
            if (-1 != resourceId) {
                this.internalTitleView.setTextAppearance(resourceId);
            }
            String string = typedArrayObtainStyledAttributes.getString(R.styleable.UINavigationBar_title);
            if (!TextUtils.isEmpty(string)) {
                setTitle(string);
            }
            this.actionTextTintList = typedArrayObtainStyledAttributes.getColorStateList(R.styleable.UINavigationBar_actionTextTintList);
            this.actionImageTintList = typedArrayObtainStyledAttributes.getColorStateList(R.styleable.UINavigationBar_actionImageTintList);
            typedArrayObtainStyledAttributes.recycle();
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
            throw th;
        }
    }

    public static final class UIBarButtonItem extends UIBarItem {
        static final int NO_TAG = Integer.MIN_VALUE;

        public UIBarButtonItem(String str, @Nullable UIBarItem.Action action) {
            this.tag = Integer.MIN_VALUE;
            setTitle(str);
            setAction(action);
            setEnable(true);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof UIBarButtonItem)) {
                return false;
            }
            UIBarButtonItem uIBarButtonItem = (UIBarButtonItem) obj;
            String str = uIBarButtonItem.title;
            if (str != null) {
                str.equals(this.title);
            }
            return uIBarButtonItem.tag == this.tag;
        }

        public int hashCode() {
            return this.tag * 31;
        }

        public String toString() {
            return "UIBarButtonItem{tag=" + this.tag + ", title='" + this.title + "', icon=" + this.icon + ", isEnable=" + this.isEnable + ", action=" + this.action + '}';
        }

        public UIBarButtonItem(Drawable drawable, @Nullable UIBarItem.Action action) {
            this.tag = Integer.MIN_VALUE;
            setIcon(drawable);
            setAction(action);
            setEnable(true);
        }

        public UIBarButtonItem(int i2, String str, boolean z2, @Nullable UIBarItem.Action action) {
            this.tag = i2;
            setTitle(str);
            setEnable(z2);
            setAction(action);
        }
    }
}
