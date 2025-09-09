package me.zhanghai.android.materialprogressbar;

/* loaded from: classes5.dex */
abstract class BaseProgressDrawable extends BasePaintDrawable implements IntrinsicPaddingDrawable {
    protected boolean mUseIntrinsicPadding = true;

    BaseProgressDrawable() {
    }

    @Override // me.zhanghai.android.materialprogressbar.IntrinsicPaddingDrawable
    public boolean getUseIntrinsicPadding() {
        return this.mUseIntrinsicPadding;
    }

    @Override // me.zhanghai.android.materialprogressbar.IntrinsicPaddingDrawable
    public void setUseIntrinsicPadding(boolean z2) {
        if (this.mUseIntrinsicPadding != z2) {
            this.mUseIntrinsicPadding = z2;
            invalidateSelf();
        }
    }
}
