package androidx.core.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public final class ViewPropertyAnimatorCompat {
    private final WeakReference<View> mView;

    @RequiresApi(21)
    static class Api21Impl {
        private Api21Impl() {
        }

        @DoNotInline
        static ViewPropertyAnimator a(ViewPropertyAnimator viewPropertyAnimator, float f2) {
            return viewPropertyAnimator.translationZ(f2);
        }

        @DoNotInline
        static ViewPropertyAnimator b(ViewPropertyAnimator viewPropertyAnimator, float f2) {
            return viewPropertyAnimator.translationZBy(f2);
        }

        @DoNotInline
        static ViewPropertyAnimator c(ViewPropertyAnimator viewPropertyAnimator, float f2) {
            return viewPropertyAnimator.z(f2);
        }

        @DoNotInline
        static ViewPropertyAnimator d(ViewPropertyAnimator viewPropertyAnimator, float f2) {
            return viewPropertyAnimator.zBy(f2);
        }
    }

    ViewPropertyAnimatorCompat(View view) {
        this.mView = new WeakReference<>(view);
    }

    private void setListenerInternal(final View view, final ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        if (viewPropertyAnimatorListener != null) {
            view.animate().setListener(new AnimatorListenerAdapter() { // from class: androidx.core.view.ViewPropertyAnimatorCompat.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    viewPropertyAnimatorListener.onAnimationCancel(view);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    viewPropertyAnimatorListener.onAnimationEnd(view);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    viewPropertyAnimatorListener.onAnimationStart(view);
                }
            });
        } else {
            view.animate().setListener(null);
        }
    }

    @NonNull
    public ViewPropertyAnimatorCompat alpha(float f2) {
        View view = this.mView.get();
        if (view != null) {
            view.animate().alpha(f2);
        }
        return this;
    }

    @NonNull
    public ViewPropertyAnimatorCompat alphaBy(float f2) {
        View view = this.mView.get();
        if (view != null) {
            view.animate().alphaBy(f2);
        }
        return this;
    }

    public void cancel() {
        View view = this.mView.get();
        if (view != null) {
            view.animate().cancel();
        }
    }

    public long getDuration() {
        View view = this.mView.get();
        if (view != null) {
            return view.animate().getDuration();
        }
        return 0L;
    }

    @Nullable
    public Interpolator getInterpolator() {
        View view = this.mView.get();
        if (view != null) {
            return (Interpolator) view.animate().getInterpolator();
        }
        return null;
    }

    public long getStartDelay() {
        View view = this.mView.get();
        if (view != null) {
            return view.animate().getStartDelay();
        }
        return 0L;
    }

    @NonNull
    public ViewPropertyAnimatorCompat rotation(float f2) {
        View view = this.mView.get();
        if (view != null) {
            view.animate().rotation(f2);
        }
        return this;
    }

    @NonNull
    public ViewPropertyAnimatorCompat rotationBy(float f2) {
        View view = this.mView.get();
        if (view != null) {
            view.animate().rotationBy(f2);
        }
        return this;
    }

    @NonNull
    public ViewPropertyAnimatorCompat rotationX(float f2) {
        View view = this.mView.get();
        if (view != null) {
            view.animate().rotationX(f2);
        }
        return this;
    }

    @NonNull
    public ViewPropertyAnimatorCompat rotationXBy(float f2) {
        View view = this.mView.get();
        if (view != null) {
            view.animate().rotationXBy(f2);
        }
        return this;
    }

    @NonNull
    public ViewPropertyAnimatorCompat rotationY(float f2) {
        View view = this.mView.get();
        if (view != null) {
            view.animate().rotationY(f2);
        }
        return this;
    }

    @NonNull
    public ViewPropertyAnimatorCompat rotationYBy(float f2) {
        View view = this.mView.get();
        if (view != null) {
            view.animate().rotationYBy(f2);
        }
        return this;
    }

    @NonNull
    public ViewPropertyAnimatorCompat scaleX(float f2) {
        View view = this.mView.get();
        if (view != null) {
            view.animate().scaleX(f2);
        }
        return this;
    }

    @NonNull
    public ViewPropertyAnimatorCompat scaleXBy(float f2) {
        View view = this.mView.get();
        if (view != null) {
            view.animate().scaleXBy(f2);
        }
        return this;
    }

    @NonNull
    public ViewPropertyAnimatorCompat scaleY(float f2) {
        View view = this.mView.get();
        if (view != null) {
            view.animate().scaleY(f2);
        }
        return this;
    }

    @NonNull
    public ViewPropertyAnimatorCompat scaleYBy(float f2) {
        View view = this.mView.get();
        if (view != null) {
            view.animate().scaleYBy(f2);
        }
        return this;
    }

    @NonNull
    public ViewPropertyAnimatorCompat setDuration(long j2) {
        View view = this.mView.get();
        if (view != null) {
            view.animate().setDuration(j2);
        }
        return this;
    }

    @NonNull
    public ViewPropertyAnimatorCompat setInterpolator(@Nullable Interpolator interpolator) {
        View view = this.mView.get();
        if (view != null) {
            view.animate().setInterpolator(interpolator);
        }
        return this;
    }

    @NonNull
    public ViewPropertyAnimatorCompat setListener(@Nullable ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        View view = this.mView.get();
        if (view != null) {
            setListenerInternal(view, viewPropertyAnimatorListener);
        }
        return this;
    }

    @NonNull
    public ViewPropertyAnimatorCompat setStartDelay(long j2) {
        View view = this.mView.get();
        if (view != null) {
            view.animate().setStartDelay(j2);
        }
        return this;
    }

    @NonNull
    public ViewPropertyAnimatorCompat setUpdateListener(@Nullable final ViewPropertyAnimatorUpdateListener viewPropertyAnimatorUpdateListener) {
        final View view = this.mView.get();
        if (view != null) {
            view.animate().setUpdateListener(viewPropertyAnimatorUpdateListener != null ? new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.core.view.m0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    viewPropertyAnimatorUpdateListener.onAnimationUpdate(view);
                }
            } : null);
        }
        return this;
    }

    public void start() {
        View view = this.mView.get();
        if (view != null) {
            view.animate().start();
        }
    }

    @NonNull
    public ViewPropertyAnimatorCompat translationX(float f2) {
        View view = this.mView.get();
        if (view != null) {
            view.animate().translationX(f2);
        }
        return this;
    }

    @NonNull
    public ViewPropertyAnimatorCompat translationXBy(float f2) {
        View view = this.mView.get();
        if (view != null) {
            view.animate().translationXBy(f2);
        }
        return this;
    }

    @NonNull
    public ViewPropertyAnimatorCompat translationY(float f2) {
        View view = this.mView.get();
        if (view != null) {
            view.animate().translationY(f2);
        }
        return this;
    }

    @NonNull
    public ViewPropertyAnimatorCompat translationYBy(float f2) {
        View view = this.mView.get();
        if (view != null) {
            view.animate().translationYBy(f2);
        }
        return this;
    }

    @NonNull
    public ViewPropertyAnimatorCompat translationZ(float f2) {
        View view = this.mView.get();
        if (view != null) {
            Api21Impl.a(view.animate(), f2);
        }
        return this;
    }

    @NonNull
    public ViewPropertyAnimatorCompat translationZBy(float f2) {
        View view = this.mView.get();
        if (view != null) {
            Api21Impl.b(view.animate(), f2);
        }
        return this;
    }

    @NonNull
    public ViewPropertyAnimatorCompat withEndAction(@NonNull Runnable runnable) {
        View view = this.mView.get();
        if (view != null) {
            view.animate().withEndAction(runnable);
        }
        return this;
    }

    @NonNull
    @SuppressLint({"WrongConstant"})
    public ViewPropertyAnimatorCompat withLayer() {
        View view = this.mView.get();
        if (view != null) {
            view.animate().withLayer();
        }
        return this;
    }

    @NonNull
    public ViewPropertyAnimatorCompat withStartAction(@NonNull Runnable runnable) {
        View view = this.mView.get();
        if (view != null) {
            view.animate().withStartAction(runnable);
        }
        return this;
    }

    @NonNull
    public ViewPropertyAnimatorCompat x(float f2) {
        View view = this.mView.get();
        if (view != null) {
            view.animate().x(f2);
        }
        return this;
    }

    @NonNull
    public ViewPropertyAnimatorCompat xBy(float f2) {
        View view = this.mView.get();
        if (view != null) {
            view.animate().xBy(f2);
        }
        return this;
    }

    @NonNull
    public ViewPropertyAnimatorCompat y(float f2) {
        View view = this.mView.get();
        if (view != null) {
            view.animate().y(f2);
        }
        return this;
    }

    @NonNull
    public ViewPropertyAnimatorCompat yBy(float f2) {
        View view = this.mView.get();
        if (view != null) {
            view.animate().yBy(f2);
        }
        return this;
    }

    @NonNull
    public ViewPropertyAnimatorCompat z(float f2) {
        View view = this.mView.get();
        if (view != null) {
            Api21Impl.c(view.animate(), f2);
        }
        return this;
    }

    @NonNull
    public ViewPropertyAnimatorCompat zBy(float f2) {
        View view = this.mView.get();
        if (view != null) {
            Api21Impl.d(view.animate(), f2);
        }
        return this;
    }
}
