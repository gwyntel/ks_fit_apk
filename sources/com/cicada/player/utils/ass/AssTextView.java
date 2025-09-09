package com.cicada.player.utils.ass;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/* loaded from: classes3.dex */
public class AssTextView extends TextView {
    private String mContent;
    private Long mId;

    public AssTextView(Context context) {
        super(context);
        this.mContent = null;
        this.mId = null;
    }

    public String getContent() {
        return this.mContent;
    }

    public Long getSubtitleId() {
        return this.mId;
    }

    public void setContent(String str) {
        this.mContent = str;
    }

    public void setSubtitleId(Long l2) {
        this.mId = l2;
    }

    public AssTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContent = null;
        this.mId = null;
    }

    public AssTextView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mContent = null;
        this.mId = null;
    }
}
