package androidx.core.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;

/* loaded from: classes.dex */
public class ContentLoadingProgressBar extends ProgressBar {
    private static final int MIN_DELAY_MS = 500;
    private static final int MIN_SHOW_TIME_MS = 500;

    /* renamed from: a, reason: collision with root package name */
    long f3839a;

    /* renamed from: b, reason: collision with root package name */
    boolean f3840b;

    /* renamed from: c, reason: collision with root package name */
    boolean f3841c;

    /* renamed from: d, reason: collision with root package name */
    boolean f3842d;
    private final Runnable mDelayedHide;
    private final Runnable mDelayedShow;

    public ContentLoadingProgressBar(@NonNull Context context) {
        this(context, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @UiThread
    public void hideOnUiThread() {
        this.f3842d = true;
        removeCallbacks(this.mDelayedShow);
        this.f3841c = false;
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j2 = this.f3839a;
        long j3 = jCurrentTimeMillis - j2;
        if (j3 >= 500 || j2 == -1) {
            setVisibility(8);
        } else {
            if (this.f3840b) {
                return;
            }
            postDelayed(this.mDelayedHide, 500 - j3);
            this.f3840b = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        this.f3840b = false;
        this.f3839a = -1L;
        setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1() {
        this.f3841c = false;
        if (this.f3842d) {
            return;
        }
        this.f3839a = System.currentTimeMillis();
        setVisibility(0);
    }

    private void removeCallbacks() {
        removeCallbacks(this.mDelayedHide);
        removeCallbacks(this.mDelayedShow);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @UiThread
    public void showOnUiThread() {
        this.f3839a = -1L;
        this.f3842d = false;
        removeCallbacks(this.mDelayedHide);
        this.f3840b = false;
        if (this.f3841c) {
            return;
        }
        postDelayed(this.mDelayedShow, 500L);
        this.f3841c = true;
    }

    public void hide() {
        post(new Runnable() { // from class: androidx.core.widget.d
            @Override // java.lang.Runnable
            public final void run() {
                this.f3856a.hideOnUiThread();
            }
        });
    }

    @Override // android.widget.ProgressBar, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        removeCallbacks();
    }

    @Override // android.widget.ProgressBar, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks();
    }

    public void show() {
        post(new Runnable() { // from class: androidx.core.widget.c
            @Override // java.lang.Runnable
            public final void run() {
                this.f3855a.showOnUiThread();
            }
        });
    }

    public ContentLoadingProgressBar(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        this.f3839a = -1L;
        this.f3840b = false;
        this.f3841c = false;
        this.f3842d = false;
        this.mDelayedHide = new Runnable() { // from class: androidx.core.widget.a
            @Override // java.lang.Runnable
            public final void run() {
                this.f3853a.lambda$new$0();
            }
        };
        this.mDelayedShow = new Runnable() { // from class: androidx.core.widget.b
            @Override // java.lang.Runnable
            public final void run() {
                this.f3854a.lambda$new$1();
            }
        };
    }
}
