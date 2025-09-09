package androidx.constraintlayout.motion.utils;

import android.view.View;

/* loaded from: classes.dex */
public class ViewState {
    public int bottom;
    public int left;
    public int right;
    public float rotation;

    /* renamed from: top, reason: collision with root package name */
    public int f2988top;

    public void getState(View view) {
        this.left = view.getLeft();
        this.f2988top = view.getTop();
        this.right = view.getRight();
        this.bottom = view.getBottom();
        this.rotation = view.getRotation();
    }

    public int height() {
        return this.bottom - this.f2988top;
    }

    public int width() {
        return this.right - this.left;
    }
}
