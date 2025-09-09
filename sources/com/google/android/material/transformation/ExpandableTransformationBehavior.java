package com.google.android.material.transformation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

/* loaded from: classes3.dex */
public abstract class ExpandableTransformationBehavior extends ExpandableBehavior {

    @Nullable
    private AnimatorSet currentAnimation;

    public ExpandableTransformationBehavior() {
    }

    @Override // com.google.android.material.transformation.ExpandableBehavior
    protected boolean c(View view, View view2, boolean z2, boolean z3) {
        AnimatorSet animatorSet = this.currentAnimation;
        boolean z4 = animatorSet != null;
        if (z4) {
            animatorSet.cancel();
        }
        AnimatorSet animatorSetE = e(view, view2, z2, z4);
        this.currentAnimation = animatorSetE;
        animatorSetE.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.transformation.ExpandableTransformationBehavior.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                ExpandableTransformationBehavior.this.currentAnimation = null;
            }
        });
        this.currentAnimation.start();
        if (!z3) {
            this.currentAnimation.end();
        }
        return true;
    }

    protected abstract AnimatorSet e(View view, View view2, boolean z2, boolean z3);

    public ExpandableTransformationBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
