package com.luck.picture.lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.luck.picture.lib.R;
import com.luck.picture.lib.style.TitleBarStyle;
import com.luck.picture.lib.utils.StyleUtils;

/* loaded from: classes4.dex */
public class PreviewTitleBar extends TitleBar {
    public PreviewTitleBar(Context context) {
        super(context);
    }

    @Override // com.luck.picture.lib.widget.TitleBar
    public void setTitleBarStyle() {
        super.setTitleBarStyle();
        TitleBarStyle titleBarStyle = this.f19156i.selectorStyle.getTitleBarStyle();
        if (StyleUtils.checkStyleValidity(titleBarStyle.getPreviewTitleBackgroundColor())) {
            setBackgroundColor(titleBarStyle.getPreviewTitleBackgroundColor());
        } else if (StyleUtils.checkSizeValidity(titleBarStyle.getTitleBackgroundColor())) {
            setBackgroundColor(titleBarStyle.getTitleBackgroundColor());
        }
        if (StyleUtils.checkStyleValidity(titleBarStyle.getPreviewTitleLeftBackResource())) {
            this.f19149b.setImageResource(titleBarStyle.getPreviewTitleLeftBackResource());
        }
        this.f19148a.setOnClickListener(null);
        this.f19155h.setOnClickListener(null);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.f19148a.getLayoutParams();
        layoutParams.removeRule(17);
        layoutParams.addRule(14);
        this.f19148a.setBackgroundResource(R.drawable.ps_ic_trans_1px);
        this.f19153f.setVisibility(8);
        this.f19150c.setVisibility(8);
        this.f19155h.setVisibility(8);
    }

    public PreviewTitleBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PreviewTitleBar(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }
}
