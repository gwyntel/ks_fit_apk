package com.luck.picture.lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.luck.picture.lib.R;
import com.luck.picture.lib.config.SelectorConfig;
import com.luck.picture.lib.config.SelectorProviders;
import com.luck.picture.lib.style.BottomNavBarStyle;
import com.luck.picture.lib.utils.DensityUtil;
import com.luck.picture.lib.utils.PictureFileUtils;
import com.luck.picture.lib.utils.StyleUtils;

/* loaded from: classes4.dex */
public class BottomNavBar extends RelativeLayout implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    protected TextView f19141a;

    /* renamed from: b, reason: collision with root package name */
    protected TextView f19142b;

    /* renamed from: c, reason: collision with root package name */
    protected SelectorConfig f19143c;

    /* renamed from: d, reason: collision with root package name */
    protected OnBottomNavBarListener f19144d;
    private CheckBox originalCheckbox;

    public static class OnBottomNavBarListener {
        public void onCheckOriginalChange() {
        }

        public void onEditImage() {
        }

        public void onFirstCheckOriginalSelectedChange() {
        }

        public void onPreview() {
        }
    }

    public BottomNavBar(Context context) {
        super(context);
        d();
    }

    private void calculateFileTotalSize() {
        if (!this.f19143c.isOriginalControl) {
            this.originalCheckbox.setText(getContext().getString(R.string.ps_default_original_image));
            return;
        }
        long size = 0;
        for (int i2 = 0; i2 < this.f19143c.getSelectCount(); i2++) {
            size += this.f19143c.getSelectedResult().get(i2).getSize();
        }
        if (size <= 0) {
            this.originalCheckbox.setText(getContext().getString(R.string.ps_default_original_image));
        } else {
            this.originalCheckbox.setText(getContext().getString(R.string.ps_original_image, PictureFileUtils.formatAccurateUnitFileSize(size)));
        }
    }

    protected void b() {
    }

    protected void c() {
        View.inflate(getContext(), R.layout.ps_bottom_nav_bar, this);
    }

    protected void d() {
        c();
        setClickable(true);
        setFocusable(true);
        this.f19143c = SelectorProviders.getInstance().getSelectorConfig();
        this.f19141a = (TextView) findViewById(R.id.ps_tv_preview);
        this.f19142b = (TextView) findViewById(R.id.ps_tv_editor);
        this.originalCheckbox = (CheckBox) findViewById(R.id.cb_original);
        this.f19141a.setOnClickListener(this);
        this.f19142b.setVisibility(8);
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.ps_color_grey));
        this.originalCheckbox.setChecked(this.f19143c.isCheckOriginalImage);
        this.originalCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.luck.picture.lib.widget.BottomNavBar.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                BottomNavBar bottomNavBar = BottomNavBar.this;
                bottomNavBar.f19143c.isCheckOriginalImage = z2;
                bottomNavBar.originalCheckbox.setChecked(BottomNavBar.this.f19143c.isCheckOriginalImage);
                OnBottomNavBarListener onBottomNavBarListener = BottomNavBar.this.f19144d;
                if (onBottomNavBarListener != null) {
                    onBottomNavBarListener.onCheckOriginalChange();
                    if (z2 && BottomNavBar.this.f19143c.getSelectCount() == 0) {
                        BottomNavBar.this.f19144d.onFirstCheckOriginalSelectedChange();
                    }
                }
            }
        });
        b();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (this.f19144d != null && view.getId() == R.id.ps_tv_preview) {
            this.f19144d.onPreview();
        }
    }

    public void setBottomNavBarStyle() {
        SelectorConfig selectorConfig = this.f19143c;
        if (selectorConfig.isDirectReturnSingle) {
            setVisibility(8);
            return;
        }
        BottomNavBarStyle bottomBarStyle = selectorConfig.selectorStyle.getBottomBarStyle();
        if (this.f19143c.isOriginalControl) {
            this.originalCheckbox.setVisibility(0);
            int bottomOriginalDrawableLeft = bottomBarStyle.getBottomOriginalDrawableLeft();
            if (StyleUtils.checkStyleValidity(bottomOriginalDrawableLeft)) {
                this.originalCheckbox.setButtonDrawable(bottomOriginalDrawableLeft);
            }
            String string = StyleUtils.checkStyleValidity(bottomBarStyle.getBottomOriginalTextResId()) ? getContext().getString(bottomBarStyle.getBottomOriginalTextResId()) : bottomBarStyle.getBottomOriginalText();
            if (StyleUtils.checkTextValidity(string)) {
                this.originalCheckbox.setText(string);
            }
            int bottomOriginalTextSize = bottomBarStyle.getBottomOriginalTextSize();
            if (StyleUtils.checkSizeValidity(bottomOriginalTextSize)) {
                this.originalCheckbox.setTextSize(bottomOriginalTextSize);
            }
            int bottomOriginalTextColor = bottomBarStyle.getBottomOriginalTextColor();
            if (StyleUtils.checkStyleValidity(bottomOriginalTextColor)) {
                this.originalCheckbox.setTextColor(bottomOriginalTextColor);
            }
        }
        int bottomNarBarHeight = bottomBarStyle.getBottomNarBarHeight();
        if (StyleUtils.checkSizeValidity(bottomNarBarHeight)) {
            getLayoutParams().height = bottomNarBarHeight;
        } else {
            getLayoutParams().height = DensityUtil.dip2px(getContext(), 46.0f);
        }
        int bottomNarBarBackgroundColor = bottomBarStyle.getBottomNarBarBackgroundColor();
        if (StyleUtils.checkStyleValidity(bottomNarBarBackgroundColor)) {
            setBackgroundColor(bottomNarBarBackgroundColor);
        }
        int bottomPreviewNormalTextColor = bottomBarStyle.getBottomPreviewNormalTextColor();
        if (StyleUtils.checkStyleValidity(bottomPreviewNormalTextColor)) {
            this.f19141a.setTextColor(bottomPreviewNormalTextColor);
        }
        int bottomPreviewNormalTextSize = bottomBarStyle.getBottomPreviewNormalTextSize();
        if (StyleUtils.checkSizeValidity(bottomPreviewNormalTextSize)) {
            this.f19141a.setTextSize(bottomPreviewNormalTextSize);
        }
        String string2 = StyleUtils.checkStyleValidity(bottomBarStyle.getBottomPreviewNormalTextResId()) ? getContext().getString(bottomBarStyle.getBottomPreviewNormalTextResId()) : bottomBarStyle.getBottomPreviewNormalText();
        if (StyleUtils.checkTextValidity(string2)) {
            this.f19141a.setText(string2);
        }
        String string3 = StyleUtils.checkStyleValidity(bottomBarStyle.getBottomEditorTextResId()) ? getContext().getString(bottomBarStyle.getBottomEditorTextResId()) : bottomBarStyle.getBottomEditorText();
        if (StyleUtils.checkTextValidity(string3)) {
            this.f19142b.setText(string3);
        }
        int bottomEditorTextSize = bottomBarStyle.getBottomEditorTextSize();
        if (StyleUtils.checkSizeValidity(bottomEditorTextSize)) {
            this.f19142b.setTextSize(bottomEditorTextSize);
        }
        int bottomEditorTextColor = bottomBarStyle.getBottomEditorTextColor();
        if (StyleUtils.checkStyleValidity(bottomEditorTextColor)) {
            this.f19142b.setTextColor(bottomEditorTextColor);
        }
        int bottomOriginalDrawableLeft2 = bottomBarStyle.getBottomOriginalDrawableLeft();
        if (StyleUtils.checkStyleValidity(bottomOriginalDrawableLeft2)) {
            this.originalCheckbox.setButtonDrawable(bottomOriginalDrawableLeft2);
        }
        String string4 = StyleUtils.checkStyleValidity(bottomBarStyle.getBottomOriginalTextResId()) ? getContext().getString(bottomBarStyle.getBottomOriginalTextResId()) : bottomBarStyle.getBottomOriginalText();
        if (StyleUtils.checkTextValidity(string4)) {
            this.originalCheckbox.setText(string4);
        }
        int bottomOriginalTextSize2 = bottomBarStyle.getBottomOriginalTextSize();
        if (StyleUtils.checkSizeValidity(bottomOriginalTextSize2)) {
            this.originalCheckbox.setTextSize(bottomOriginalTextSize2);
        }
        int bottomOriginalTextColor2 = bottomBarStyle.getBottomOriginalTextColor();
        if (StyleUtils.checkStyleValidity(bottomOriginalTextColor2)) {
            this.originalCheckbox.setTextColor(bottomOriginalTextColor2);
        }
    }

    public void setOnBottomNavBarListener(OnBottomNavBarListener onBottomNavBarListener) {
        this.f19144d = onBottomNavBarListener;
    }

    public void setOriginalCheck() {
        this.originalCheckbox.setChecked(this.f19143c.isCheckOriginalImage);
    }

    public void setSelectedChange() {
        calculateFileTotalSize();
        BottomNavBarStyle bottomBarStyle = this.f19143c.selectorStyle.getBottomBarStyle();
        if (this.f19143c.getSelectCount() <= 0) {
            this.f19141a.setEnabled(false);
            int bottomPreviewNormalTextColor = bottomBarStyle.getBottomPreviewNormalTextColor();
            if (StyleUtils.checkStyleValidity(bottomPreviewNormalTextColor)) {
                this.f19141a.setTextColor(bottomPreviewNormalTextColor);
            } else {
                this.f19141a.setTextColor(ContextCompat.getColor(getContext(), R.color.ps_color_9b));
            }
            String string = StyleUtils.checkStyleValidity(bottomBarStyle.getBottomPreviewNormalTextResId()) ? getContext().getString(bottomBarStyle.getBottomPreviewNormalTextResId()) : bottomBarStyle.getBottomPreviewNormalText();
            if (StyleUtils.checkTextValidity(string)) {
                this.f19141a.setText(string);
                return;
            } else {
                this.f19141a.setText(getContext().getString(R.string.ps_preview));
                return;
            }
        }
        this.f19141a.setEnabled(true);
        int bottomPreviewSelectTextColor = bottomBarStyle.getBottomPreviewSelectTextColor();
        if (StyleUtils.checkStyleValidity(bottomPreviewSelectTextColor)) {
            this.f19141a.setTextColor(bottomPreviewSelectTextColor);
        } else {
            this.f19141a.setTextColor(ContextCompat.getColor(getContext(), R.color.ps_color_fa632d));
        }
        String string2 = StyleUtils.checkStyleValidity(bottomBarStyle.getBottomPreviewSelectTextResId()) ? getContext().getString(bottomBarStyle.getBottomPreviewSelectTextResId()) : bottomBarStyle.getBottomPreviewSelectText();
        if (!StyleUtils.checkTextValidity(string2)) {
            this.f19141a.setText(getContext().getString(R.string.ps_preview_num, Integer.valueOf(this.f19143c.getSelectCount())));
            return;
        }
        int formatCount = StyleUtils.getFormatCount(string2);
        if (formatCount == 1) {
            this.f19141a.setText(String.format(string2, Integer.valueOf(this.f19143c.getSelectCount())));
        } else if (formatCount == 2) {
            this.f19141a.setText(String.format(string2, Integer.valueOf(this.f19143c.getSelectCount()), Integer.valueOf(this.f19143c.maxSelectNum)));
        } else {
            this.f19141a.setText(string2);
        }
    }

    public BottomNavBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        d();
    }

    public BottomNavBar(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        d();
    }
}
