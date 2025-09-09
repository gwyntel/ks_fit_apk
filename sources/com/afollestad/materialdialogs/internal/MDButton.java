package com.afollestad.materialdialogs.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.R;
import com.afollestad.materialdialogs.util.DialogUtils;

@SuppressLint({"AppCompatCustomView"})
/* loaded from: classes2.dex */
public class MDButton extends TextView {
    private Drawable defaultBackground;
    private boolean stacked;
    private Drawable stackedBackground;
    private int stackedEndPadding;
    private GravityEnum stackedGravity;

    public MDButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.stacked = false;
        init(context);
    }

    private void init(Context context) {
        this.stackedEndPadding = context.getResources().getDimensionPixelSize(R.dimen.md_dialog_frame_margin);
        this.stackedGravity = GravityEnum.END;
    }

    void a(boolean z2, boolean z3) {
        if (this.stacked != z2 || z3) {
            setGravity(z2 ? this.stackedGravity.getGravityInt() | 16 : 17);
            setTextAlignment(z2 ? this.stackedGravity.getTextAlignment() : 4);
            DialogUtils.setBackgroundCompat(this, z2 ? this.stackedBackground : this.defaultBackground);
            if (z2) {
                setPadding(this.stackedEndPadding, getPaddingTop(), this.stackedEndPadding, getPaddingBottom());
            }
            this.stacked = z2;
        }
    }

    public void setAllCapsCompat(boolean z2) {
        setAllCaps(z2);
    }

    public void setDefaultSelector(Drawable drawable) {
        this.defaultBackground = drawable;
        if (this.stacked) {
            return;
        }
        a(false, true);
    }

    public void setStackedGravity(GravityEnum gravityEnum) {
        this.stackedGravity = gravityEnum;
    }

    public void setStackedSelector(Drawable drawable) {
        this.stackedBackground = drawable;
        if (this.stacked) {
            a(true, true);
        }
    }

    public MDButton(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.stacked = false;
        init(context);
    }
}
