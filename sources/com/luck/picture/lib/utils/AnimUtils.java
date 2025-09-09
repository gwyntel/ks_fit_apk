package com.luck.picture.lib.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import androidx.constraintlayout.motion.widget.Key;

/* loaded from: classes4.dex */
public class AnimUtils {
    public static final int DURATION = 250;

    public static void rotateArrow(ImageView imageView, boolean z2) {
        float f2 = 180.0f;
        float f3 = 0.0f;
        if (z2) {
            f3 = 180.0f;
            f2 = 0.0f;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(imageView, Key.ROTATION, f2, f3);
        objectAnimatorOfFloat.setDuration(250L);
        objectAnimatorOfFloat.setInterpolator(new LinearInterpolator());
        objectAnimatorOfFloat.start();
    }

    public static void selectZoom(View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 1.05f, 1.0f), ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 1.05f, 1.0f));
        animatorSet.setDuration(250L);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.start();
    }
}
