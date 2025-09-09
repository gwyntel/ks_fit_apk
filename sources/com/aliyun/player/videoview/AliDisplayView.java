package com.aliyun.player.videoview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.aliyun.player.nativeclass.DisplayViewHelper;

/* loaded from: classes3.dex */
public class AliDisplayView extends FrameLayout {
    private static final String TAG = "AliDisplayView";
    private DisplayViewHelper mDisplayViewHelper;
    private OnViewStatusListener mOutOnViewStatusListener;
    private DisplayViewType mPreferDisplayViewType;

    public enum DisplayViewType {
        Either,
        SurfaceView,
        TextureView
    }

    public interface OnViewStatusListener {
        void onSurfaceCreated();

        void onSurfaceDestroy();

        void onSurfaceSizeChanged();

        void onViewCreated(DisplayViewType displayViewType);
    }

    public AliDisplayView(Context context) {
        super(context);
        this.mDisplayViewHelper = null;
        this.mPreferDisplayViewType = null;
        this.mOutOnViewStatusListener = null;
        init();
    }

    private void init() {
        this.mDisplayViewHelper = new DisplayViewHelper(this);
    }

    public DisplayViewHelper getDisplayViewHelper() {
        return this.mDisplayViewHelper;
    }

    public OnViewStatusListener getOnViewStatusListener() {
        return this.mOutOnViewStatusListener;
    }

    public DisplayViewType getPreferDisplayViewType() {
        return this.mPreferDisplayViewType;
    }

    public void setOnViewStatusListener(OnViewStatusListener onViewStatusListener) {
        this.mOutOnViewStatusListener = onViewStatusListener;
    }

    public void setPreferDisplayView(DisplayViewType displayViewType) {
        this.mPreferDisplayViewType = displayViewType;
    }

    public void setSurfaceReuse(boolean z2) {
        this.mDisplayViewHelper.setSurfaceReuse(z2);
    }

    public AliDisplayView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDisplayViewHelper = null;
        this.mPreferDisplayViewType = null;
        this.mOutOnViewStatusListener = null;
        init();
    }

    public AliDisplayView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mDisplayViewHelper = null;
        this.mPreferDisplayViewType = null;
        this.mOutOnViewStatusListener = null;
        init();
    }
}
