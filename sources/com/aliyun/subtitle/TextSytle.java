package com.aliyun.subtitle;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import java.util.Map;

/* loaded from: classes3.dex */
public class TextSytle {
    public static void setTextColor(SpannableStringBuilder spannableStringBuilder, Map<String, Object> map, String str) {
        if (map != null && map.containsKey(SubtitleView.EXTRA_COLOR__STRING)) {
            str = (String) map.get(SubtitleView.EXTRA_COLOR__STRING);
        }
        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor(str)), 0, spannableStringBuilder.length(), 17);
    }

    public static void setTextSize(SpannableStringBuilder spannableStringBuilder, Map<String, Object> map, int i2) {
        if (map != null && map.containsKey(SubtitleView.EXTRA_SIZE_PX__INT)) {
            i2 = ((Integer) map.get(SubtitleView.EXTRA_SIZE_PX__INT)).intValue();
        }
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(i2), 0, spannableStringBuilder.length(), 17);
    }
}
