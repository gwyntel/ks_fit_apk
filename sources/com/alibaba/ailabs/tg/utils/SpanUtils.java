package com.alibaba.ailabs.tg.utils;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

/* loaded from: classes2.dex */
public class SpanUtils {
    private static final int COLOR_DEFAULT = -16777217;
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private int backgroundColor;
    private ClickableSpan clickSpan;
    private int flag;
    private int fontSize;
    private boolean fontSizeIsDp;
    private int foregroundColor;
    private boolean isBold;
    private boolean isBoldItalic;
    private boolean isItalic;
    private int mType;
    private Object[] spans;
    private String url;
    private final int mTypeCharSequence = 0;
    private SpannableStringBuilder mBuilder = new SpannableStringBuilder();
    private CharSequence mText = "";

    public SpanUtils() {
        setDefault();
    }

    private void apply(int i2) {
        applyLast();
        this.mType = i2;
    }

    private void applyLast() {
        if (this.mType == 0) {
            updateCharCharSequence();
        }
        setDefault();
    }

    @Deprecated
    public static void setBackgroundSpan(TextView textView, String str, String str2, int i2) {
        if (textView == null || TextUtils.isEmpty(str)) {
            return;
        }
        if (TextUtils.isEmpty(str2) || !str.contains(str2) || str.indexOf(str2) < 0) {
            textView.setText(str);
            return;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        spannableStringBuilder.setSpan(new BackgroundColorSpan(i2), str.indexOf(str2), str.indexOf(str2) + str2.length(), 33);
        textView.setText(spannableStringBuilder);
    }

    private void setDefault() {
        this.flag = 33;
        this.foregroundColor = COLOR_DEFAULT;
        this.backgroundColor = COLOR_DEFAULT;
        this.fontSize = -1;
        this.isBold = false;
        this.isItalic = false;
        this.isBoldItalic = false;
        this.clickSpan = null;
        this.url = null;
        this.spans = null;
    }

    private void updateCharCharSequence() {
        if (this.mText.length() == 0) {
            return;
        }
        int length = this.mBuilder.length();
        this.mBuilder.append(this.mText);
        int length2 = this.mBuilder.length();
        if (this.foregroundColor != COLOR_DEFAULT) {
            this.mBuilder.setSpan(new ForegroundColorSpan(this.foregroundColor), length, length2, this.flag);
        }
        if (this.backgroundColor != COLOR_DEFAULT) {
            this.mBuilder.setSpan(new BackgroundColorSpan(this.backgroundColor), length, length2, this.flag);
        }
        if (this.fontSize != -1) {
            this.mBuilder.setSpan(new AbsoluteSizeSpan(this.fontSize, this.fontSizeIsDp), length, length2, this.flag);
        }
        if (this.isBold) {
            this.mBuilder.setSpan(new StyleSpan(1), length, length2, this.flag);
        }
        if (this.isItalic) {
            this.mBuilder.setSpan(new StyleSpan(2), length, length2, this.flag);
        }
        if (this.isBoldItalic) {
            this.mBuilder.setSpan(new StyleSpan(3), length, length2, this.flag);
        }
        ClickableSpan clickableSpan = this.clickSpan;
        if (clickableSpan != null) {
            this.mBuilder.setSpan(clickableSpan, length, length2, this.flag);
        }
        if (this.url != null) {
            this.mBuilder.setSpan(new URLSpan(this.url), length, length2, this.flag);
        }
        Object[] objArr = this.spans;
        if (objArr != null) {
            for (Object obj : objArr) {
                this.mBuilder.setSpan(obj, length, length2, this.flag);
            }
        }
    }

    public SpanUtils append(@NonNull CharSequence charSequence) {
        apply(0);
        this.mText = charSequence;
        return this;
    }

    public SpanUtils appendLine() {
        apply(0);
        this.mText = LINE_SEPARATOR;
        return this;
    }

    public SpannableStringBuilder create() {
        applyLast();
        return this.mBuilder;
    }

    public SpanUtils setBackgroundColor(@ColorInt int i2) {
        this.backgroundColor = i2;
        return this;
    }

    public SpanUtils setBold() {
        this.isBold = true;
        return this;
    }

    public SpanUtils setBoldItalic() {
        this.isBoldItalic = true;
        return this;
    }

    public SpanUtils setClickSpan(@NonNull ClickableSpan clickableSpan) {
        this.clickSpan = clickableSpan;
        return this;
    }

    public SpanUtils setFlag(int i2) {
        this.flag = i2;
        return this;
    }

    public SpanUtils setFontSize(@IntRange(from = 0) int i2) {
        return setFontSize(i2, false);
    }

    public SpanUtils setForegroundColor(@ColorInt int i2) {
        this.foregroundColor = i2;
        return this;
    }

    public SpanUtils setItalic() {
        this.isItalic = true;
        return this;
    }

    public SpanUtils setSpans(@NonNull Object... objArr) {
        if (objArr.length > 0) {
            this.spans = objArr;
        }
        return this;
    }

    public SpanUtils setUrl(@NonNull String str) {
        this.url = str;
        return this;
    }

    public SpanUtils setFontSize(@IntRange(from = 0) int i2, boolean z2) {
        this.fontSize = i2;
        this.fontSizeIsDp = z2;
        return this;
    }

    public SpanUtils appendLine(@NonNull CharSequence charSequence) {
        apply(0);
        this.mText = ((Object) charSequence) + LINE_SEPARATOR;
        return this;
    }
}
