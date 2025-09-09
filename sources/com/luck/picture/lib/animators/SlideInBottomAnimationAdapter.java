package com.luck.picture.lib.animators;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes4.dex */
public class SlideInBottomAnimationAdapter extends BaseAnimationAdapter {
    public SlideInBottomAnimationAdapter(RecyclerView.Adapter adapter) {
        super(adapter);
    }

    @Override // com.luck.picture.lib.animators.BaseAnimationAdapter
    protected Animator[] a(View view) {
        return new Animator[]{ObjectAnimator.ofFloat(view, "translationY", view.getMeasuredHeight(), 0.0f)};
    }
}
