package com.luck.picture.lib.style;

import androidx.annotation.AnimRes;
import com.luck.picture.lib.R;

/* loaded from: classes4.dex */
public class PictureWindowAnimationStyle {

    @AnimRes
    public int activityEnterAnimation;

    @AnimRes
    public int activityExitAnimation;

    @AnimRes
    public int activityPreviewEnterAnimation;

    @AnimRes
    public int activityPreviewExitAnimation;

    public PictureWindowAnimationStyle() {
    }

    public static PictureWindowAnimationStyle ofDefaultWindowAnimationStyle() {
        return new PictureWindowAnimationStyle(R.anim.ps_anim_enter, R.anim.ps_anim_exit);
    }

    public int getActivityEnterAnimation() {
        return this.activityEnterAnimation;
    }

    public int getActivityExitAnimation() {
        return this.activityExitAnimation;
    }

    public int getActivityPreviewEnterAnimation() {
        return this.activityPreviewEnterAnimation;
    }

    public int getActivityPreviewExitAnimation() {
        return this.activityPreviewExitAnimation;
    }

    public void setActivityEnterAnimation(int i2) {
        this.activityEnterAnimation = i2;
    }

    public void setActivityExitAnimation(int i2) {
        this.activityExitAnimation = i2;
    }

    public void setActivityPreviewEnterAnimation(int i2) {
        this.activityPreviewEnterAnimation = i2;
    }

    public void setActivityPreviewExitAnimation(int i2) {
        this.activityPreviewExitAnimation = i2;
    }

    public PictureWindowAnimationStyle(@AnimRes int i2, @AnimRes int i3) {
        this.activityEnterAnimation = i2;
        this.activityExitAnimation = i3;
        this.activityPreviewEnterAnimation = i2;
        this.activityPreviewExitAnimation = i3;
    }
}
