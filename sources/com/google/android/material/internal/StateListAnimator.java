package com.google.android.material.internal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.util.StateSet;
import androidx.annotation.RestrictTo;
import java.util.ArrayList;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes3.dex */
public final class StateListAnimator {
    private final ArrayList<Tuple> tuples = new ArrayList<>();
    private Tuple lastMatch = null;

    /* renamed from: a, reason: collision with root package name */
    ValueAnimator f13507a = null;
    private final Animator.AnimatorListener animationListener = new AnimatorListenerAdapter() { // from class: com.google.android.material.internal.StateListAnimator.1
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            StateListAnimator stateListAnimator = StateListAnimator.this;
            if (stateListAnimator.f13507a == animator) {
                stateListAnimator.f13507a = null;
            }
        }
    };

    static class Tuple {

        /* renamed from: a, reason: collision with root package name */
        final int[] f13509a;

        /* renamed from: b, reason: collision with root package name */
        final ValueAnimator f13510b;

        Tuple(int[] iArr, ValueAnimator valueAnimator) {
            this.f13509a = iArr;
            this.f13510b = valueAnimator;
        }
    }

    private void cancel() {
        ValueAnimator valueAnimator = this.f13507a;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.f13507a = null;
        }
    }

    private void start(Tuple tuple) {
        ValueAnimator valueAnimator = tuple.f13510b;
        this.f13507a = valueAnimator;
        valueAnimator.start();
    }

    public void addState(int[] iArr, ValueAnimator valueAnimator) {
        Tuple tuple = new Tuple(iArr, valueAnimator);
        valueAnimator.addListener(this.animationListener);
        this.tuples.add(tuple);
    }

    public void jumpToCurrentState() {
        ValueAnimator valueAnimator = this.f13507a;
        if (valueAnimator != null) {
            valueAnimator.end();
            this.f13507a = null;
        }
    }

    public void setState(int[] iArr) {
        Tuple tuple;
        int size = this.tuples.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                tuple = null;
                break;
            }
            tuple = this.tuples.get(i2);
            if (StateSet.stateSetMatches(tuple.f13509a, iArr)) {
                break;
            } else {
                i2++;
            }
        }
        Tuple tuple2 = this.lastMatch;
        if (tuple == tuple2) {
            return;
        }
        if (tuple2 != null) {
            cancel();
        }
        this.lastMatch = tuple;
        if (tuple != null) {
            start(tuple);
        }
    }
}
