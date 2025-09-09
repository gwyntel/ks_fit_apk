package com.aliyun.subtitle;

import android.content.Context;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class SubtitleView extends RelativeLayout {
    public static final String EXTRA_COLOR__STRING = "extra_color";
    public static final String EXTRA_GRAVITY__ENUM = "extra_gravity";
    public static final String EXTRA_LOCATION__INT = "extra_location";
    public static final String EXTRA_SIZE_PX__INT = "extra_size_px";
    private static final String TAG = "SubtitleView";
    private String mDefaultColor;
    private int mDefaultLocation;
    private float mDefaultPercent;
    private int mDefaultSize;
    private LinearLayout mLinerLayout;
    private Map<String, TextView> mSubtitleView;
    private TextViewPool mTextViewPool;
    private int maxSubtitleShowCount;

    public static class DefaultValueBuilder {
        int mLocation = 24;
        int mTextSize = -1;
        float mTextSizePercent = 0.08f;
        String mTextColor = "#FFFFFFFF";

        public DefaultValueBuilder setColor(String str) {
            this.mTextColor = str;
            return this;
        }

        public DefaultValueBuilder setLocation(int i2) {
            this.mLocation = i2;
            return this;
        }

        public DefaultValueBuilder setSize(int i2) {
            this.mTextSize = i2;
            return this;
        }

        public DefaultValueBuilder setSizePercent(float f2) {
            this.mTextSizePercent = f2;
            return this;
        }
    }

    public static class Subtitle {
        public String content;
        public Map<String, Object> extraInfo;
        public String id;
    }

    public SubtitleView(Context context) {
        super(context);
        this.maxSubtitleShowCount = 6;
        this.mSubtitleView = new HashMap();
        init();
    }

    private RelativeLayout.LayoutParams getFinalParam(Subtitle subtitle) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        LocationStyle.setLocation(layoutParams, subtitle.extraInfo, this.mDefaultLocation);
        return layoutParams;
    }

    private SpannableStringBuilder getFinalText(Subtitle subtitle) {
        String str;
        if (subtitle == null || (str = subtitle.content) == null) {
            return new SpannableStringBuilder("");
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(Html.fromHtml(str.replace("\n", "<br>")));
        Map<String, Object> map = subtitle.extraInfo;
        TextSytle.setTextColor(spannableStringBuilder, map, this.mDefaultColor);
        TextSytle.setTextSize(spannableStringBuilder, map, this.mDefaultSize);
        return spannableStringBuilder;
    }

    private TextView getFinalTextView(Subtitle subtitle) {
        TextView textView = new TextView(getContext());
        Map<String, Object> map = subtitle.extraInfo;
        if (map == null) {
            textView.setGravity(17);
        } else {
            map.containsKey(EXTRA_GRAVITY__ENUM);
        }
        return textView;
    }

    private void init() {
        this.mTextViewPool = new TextViewPool(getContext());
        setDefaultValue(new DefaultValueBuilder());
        if (this.mLinerLayout == null) {
            this.mLinerLayout = new LinearLayout(getContext());
        }
    }

    public void clearSubtitles() {
        this.mLinerLayout.removeAllViews();
        this.mSubtitleView.clear();
    }

    public void dismiss(String str) {
        this.mTextViewPool.recycle(this.mSubtitleView.remove(str));
    }

    public int getMaxSubtitleShowCount() {
        return this.maxSubtitleShowCount;
    }

    @Override // android.widget.RelativeLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        int i6 = i5 - i3;
        if (this.mDefaultSize > 0) {
            return;
        }
        float f2 = this.mDefaultPercent;
        if (f2 > 0.0f) {
            this.mDefaultSize = (int) (f2 * i6);
        }
        if (this.mDefaultSize <= 0) {
            this.mDefaultSize = 20;
        }
    }

    public void setDefaultValue(DefaultValueBuilder defaultValueBuilder) {
        this.mDefaultLocation = defaultValueBuilder.mLocation;
        this.mDefaultPercent = defaultValueBuilder.mTextSizePercent;
        this.mDefaultSize = defaultValueBuilder.mTextSize;
        this.mDefaultColor = defaultValueBuilder.mTextColor;
    }

    public void setMaxSubtitleShowCount(int i2) {
        this.maxSubtitleShowCount = i2;
    }

    public void show(Subtitle subtitle) {
        SpannableStringBuilder finalText = getFinalText(subtitle);
        RelativeLayout.LayoutParams finalParam = getFinalParam(subtitle);
        TextView finalTextView = getFinalTextView(subtitle);
        finalTextView.setLayoutParams(finalParam);
        WeakReference weakReference = new WeakReference(finalTextView);
        ((TextView) weakReference.get()).setText(finalText);
        if (this.mLinerLayout.getChildCount() >= getMaxSubtitleShowCount()) {
            this.mLinerLayout.removeAllViews();
        }
        this.mLinerLayout.setOrientation(1);
        if (this.mLinerLayout.getParent() != null) {
            ((ViewGroup) this.mLinerLayout.getParent()).removeView(this.mLinerLayout);
        }
        this.mLinerLayout.setGravity(17);
        this.mLinerLayout.setLayoutParams(finalParam);
        this.mLinerLayout.addView((View) weakReference.get());
        addView(this.mLinerLayout);
        this.mSubtitleView.put(subtitle.id, (TextView) weakReference.get());
    }

    public SubtitleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.maxSubtitleShowCount = 6;
        this.mSubtitleView = new HashMap();
        init();
    }

    public SubtitleView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.maxSubtitleShowCount = 6;
        this.mSubtitleView = new HashMap();
        init();
    }
}
