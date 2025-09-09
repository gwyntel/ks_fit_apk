package com.aliyun.iot.link.ui.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.StringRes;

/* loaded from: classes3.dex */
public class LinkToast {
    private Context mContext;
    private Toast mToast;

    private LinkToast(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static LinkToast makeText(Context context, CharSequence charSequence) {
        return new LinkToast(context).update(context, charSequence);
    }

    @SuppressLint({"ShowToast"})
    private LinkToast update(Context context, CharSequence charSequence) {
        if (this.mToast == null) {
            this.mToast = new Toast(context.getApplicationContext());
        }
        this.mToast.setDuration(0);
        this.mToast.setGravity(17, 0, 0);
        View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.link_toast, (ViewGroup) null);
        TextView textView = (TextView) viewInflate.findViewById(R.id.text);
        if (textView != null) {
            textView.setText(charSequence);
        }
        Toast toast = this.mToast;
        if (toast != null) {
            toast.setView(viewInflate);
        }
        return this;
    }

    public void cancel() {
        Toast toast = this.mToast;
        if (toast != null) {
            toast.cancel();
        }
    }

    public LinkToast setGravity(int i2) {
        Toast toast = this.mToast;
        if (toast != null) {
            if (i2 == 80) {
                toast.setGravity(i2, 0, this.mContext.getResources().getDimensionPixelOffset(R.dimen.link_toast_bottom_margin));
            } else {
                toast.setGravity(i2, 0, 0);
            }
        }
        return this;
    }

    public void show() {
        if (this.mToast != null) {
            Log.d("LinkToast", "show()");
            this.mToast.show();
        }
    }

    public static LinkToast makeText(Context context, @StringRes int i2) {
        return makeText(context, context.getResources().getString(i2));
    }

    @Deprecated
    public static LinkToast makeText(Context context, CharSequence charSequence, int i2) {
        return makeText(context, charSequence);
    }

    public LinkToast setGravity(int i2, int i3, int i4) {
        Toast toast = this.mToast;
        if (toast != null) {
            toast.setGravity(i2, i3, i4);
        }
        return this;
    }

    @Deprecated
    public static LinkToast makeText(Context context, @StringRes int i2, int i3) {
        return makeText(context, context.getResources().getString(i2), i3);
    }
}
