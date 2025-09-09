package com.aliyun.iot.link.ui.component.nav;

import android.graphics.drawable.Drawable;
import android.view.View;

/* loaded from: classes3.dex */
public abstract class UIBarItem {
    protected Action action;
    protected Drawable icon;
    protected boolean isEnable;
    protected int tag;
    protected String title;

    public interface Action {
        void invoke(View view);
    }

    public Drawable getIcon() {
        return this.icon;
    }

    public int getTag() {
        return this.tag;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean isEnable() {
        return this.isEnable;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setEnable(boolean z2) {
        this.isEnable = z2;
    }

    public void setIcon(Drawable drawable) {
        this.icon = drawable;
    }

    public void setTitle(String str) {
        this.title = str;
    }
}
