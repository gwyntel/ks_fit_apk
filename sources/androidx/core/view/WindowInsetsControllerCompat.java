package androidx.core.view;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.CancellationSignal;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsAnimationControlListener;
import android.view.WindowInsetsAnimationController;
import android.view.WindowInsetsController;
import android.view.animation.Interpolator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.collection.SimpleArrayMap;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import androidx.media3.common.C;

/* loaded from: classes.dex */
public final class WindowInsetsControllerCompat {
    public static final int BEHAVIOR_DEFAULT = 1;

    @Deprecated
    public static final int BEHAVIOR_SHOW_BARS_BY_SWIPE = 1;

    @Deprecated
    public static final int BEHAVIOR_SHOW_BARS_BY_TOUCH = 0;
    public static final int BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE = 2;
    private final Impl mImpl;

    private static class Impl {
        Impl() {
        }

        void a(OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        }

        void b(int i2, long j2, Interpolator interpolator, CancellationSignal cancellationSignal, WindowInsetsAnimationControlListenerCompat windowInsetsAnimationControlListenerCompat) {
        }

        int c() {
            return 0;
        }

        void d(int i2) {
        }

        void e(OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        }

        void f(int i2) {
        }

        void g(int i2) {
        }

        public boolean isAppearanceLightNavigationBars() {
            return false;
        }

        public boolean isAppearanceLightStatusBars() {
            return false;
        }

        public void setAppearanceLightNavigationBars(boolean z2) {
        }

        public void setAppearanceLightStatusBars(boolean z2) {
        }
    }

    @RequiresApi(20)
    private static class Impl20 extends Impl {

        /* renamed from: a, reason: collision with root package name */
        protected final Window f3794a;

        @NonNull
        private final SoftwareKeyboardControllerCompat mSoftwareKeyboardControllerCompat;

        Impl20(Window window, SoftwareKeyboardControllerCompat softwareKeyboardControllerCompat) {
            this.f3794a = window;
            this.mSoftwareKeyboardControllerCompat = softwareKeyboardControllerCompat;
        }

        private void hideForType(int i2) {
            if (i2 == 1) {
                h(4);
            } else if (i2 == 2) {
                h(2);
            } else {
                if (i2 != 8) {
                    return;
                }
                this.mSoftwareKeyboardControllerCompat.hide();
            }
        }

        private void showForType(int i2) {
            if (i2 == 1) {
                j(4);
                k(1024);
            } else if (i2 == 2) {
                j(2);
            } else {
                if (i2 != 8) {
                    return;
                }
                this.mSoftwareKeyboardControllerCompat.show();
            }
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        void a(OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        void b(int i2, long j2, Interpolator interpolator, CancellationSignal cancellationSignal, WindowInsetsAnimationControlListenerCompat windowInsetsAnimationControlListenerCompat) {
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        int c() {
            return 0;
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        void d(int i2) {
            for (int i3 = 1; i3 <= 256; i3 <<= 1) {
                if ((i2 & i3) != 0) {
                    hideForType(i3);
                }
            }
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        void e(OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        void f(int i2) {
            if (i2 == 0) {
                j(6144);
                return;
            }
            if (i2 == 1) {
                j(4096);
                h(2048);
            } else {
                if (i2 != 2) {
                    return;
                }
                j(2048);
                h(4096);
            }
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        void g(int i2) {
            for (int i3 = 1; i3 <= 256; i3 <<= 1) {
                if ((i2 & i3) != 0) {
                    showForType(i3);
                }
            }
        }

        protected void h(int i2) {
            View decorView = this.f3794a.getDecorView();
            decorView.setSystemUiVisibility(i2 | decorView.getSystemUiVisibility());
        }

        protected void i(int i2) {
            this.f3794a.addFlags(i2);
        }

        protected void j(int i2) {
            View decorView = this.f3794a.getDecorView();
            decorView.setSystemUiVisibility((~i2) & decorView.getSystemUiVisibility());
        }

        protected void k(int i2) {
            this.f3794a.clearFlags(i2);
        }
    }

    @RequiresApi(23)
    private static class Impl23 extends Impl20 {
        Impl23(Window window, SoftwareKeyboardControllerCompat softwareKeyboardControllerCompat) {
            super(window, softwareKeyboardControllerCompat);
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public boolean isAppearanceLightStatusBars() {
            return (this.f3794a.getDecorView().getSystemUiVisibility() & 8192) != 0;
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public void setAppearanceLightStatusBars(boolean z2) {
            if (!z2) {
                j(8192);
                return;
            }
            k(AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
            i(Integer.MIN_VALUE);
            h(8192);
        }
    }

    @RequiresApi(26)
    private static class Impl26 extends Impl23 {
        Impl26(Window window, SoftwareKeyboardControllerCompat softwareKeyboardControllerCompat) {
            super(window, softwareKeyboardControllerCompat);
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public boolean isAppearanceLightNavigationBars() {
            return (this.f3794a.getDecorView().getSystemUiVisibility() & 16) != 0;
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public void setAppearanceLightNavigationBars(boolean z2) {
            if (!z2) {
                j(16);
                return;
            }
            k(C.BUFFER_FLAG_FIRST_SAMPLE);
            i(Integer.MIN_VALUE);
            h(16);
        }
    }

    public interface OnControllableInsetsChangedListener {
        void onControllableInsetsChanged(@NonNull WindowInsetsControllerCompat windowInsetsControllerCompat, int i2);
    }

    @RequiresApi(30)
    @Deprecated
    private WindowInsetsControllerCompat(@NonNull WindowInsetsController windowInsetsController) {
        this.mImpl = new Impl30(windowInsetsController, this, new SoftwareKeyboardControllerCompat(windowInsetsController));
    }

    @NonNull
    @RequiresApi(30)
    @Deprecated
    public static WindowInsetsControllerCompat toWindowInsetsControllerCompat(@NonNull WindowInsetsController windowInsetsController) {
        return new WindowInsetsControllerCompat(windowInsetsController);
    }

    public void addOnControllableInsetsChangedListener(@NonNull OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        this.mImpl.a(onControllableInsetsChangedListener);
    }

    public void controlWindowInsetsAnimation(int i2, long j2, @Nullable Interpolator interpolator, @Nullable CancellationSignal cancellationSignal, @NonNull WindowInsetsAnimationControlListenerCompat windowInsetsAnimationControlListenerCompat) {
        this.mImpl.b(i2, j2, interpolator, cancellationSignal, windowInsetsAnimationControlListenerCompat);
    }

    @SuppressLint({"WrongConstant"})
    public int getSystemBarsBehavior() {
        return this.mImpl.c();
    }

    public void hide(int i2) {
        this.mImpl.d(i2);
    }

    public boolean isAppearanceLightNavigationBars() {
        return this.mImpl.isAppearanceLightNavigationBars();
    }

    public boolean isAppearanceLightStatusBars() {
        return this.mImpl.isAppearanceLightStatusBars();
    }

    public void removeOnControllableInsetsChangedListener(@NonNull OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        this.mImpl.e(onControllableInsetsChangedListener);
    }

    public void setAppearanceLightNavigationBars(boolean z2) {
        this.mImpl.setAppearanceLightNavigationBars(z2);
    }

    public void setAppearanceLightStatusBars(boolean z2) {
        this.mImpl.setAppearanceLightStatusBars(z2);
    }

    public void setSystemBarsBehavior(int i2) {
        this.mImpl.f(i2);
    }

    public void show(int i2) {
        this.mImpl.g(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(30)
    static class Impl30 extends Impl {

        /* renamed from: a, reason: collision with root package name */
        final WindowInsetsControllerCompat f3795a;

        /* renamed from: b, reason: collision with root package name */
        final WindowInsetsController f3796b;

        /* renamed from: c, reason: collision with root package name */
        final SoftwareKeyboardControllerCompat f3797c;

        /* renamed from: d, reason: collision with root package name */
        protected Window f3798d;
        private final SimpleArrayMap<OnControllableInsetsChangedListener, WindowInsetsController.OnControllableInsetsChangedListener> mListeners;

        Impl30(Window window, WindowInsetsControllerCompat windowInsetsControllerCompat, SoftwareKeyboardControllerCompat softwareKeyboardControllerCompat) {
            this(window.getInsetsController(), windowInsetsControllerCompat, softwareKeyboardControllerCompat);
            this.f3798d = window;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$addOnControllableInsetsChangedListener$0(OnControllableInsetsChangedListener onControllableInsetsChangedListener, WindowInsetsController windowInsetsController, int i2) {
            if (this.f3796b == windowInsetsController) {
                onControllableInsetsChangedListener.onControllableInsetsChanged(this.f3795a, i2);
            }
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        void a(final OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
            if (this.mListeners.containsKey(onControllableInsetsChangedListener)) {
                return;
            }
            WindowInsetsController.OnControllableInsetsChangedListener onControllableInsetsChangedListener2 = new WindowInsetsController.OnControllableInsetsChangedListener() { // from class: androidx.core.view.u2
                @Override // android.view.WindowInsetsController.OnControllableInsetsChangedListener
                public final void onControllableInsetsChanged(WindowInsetsController windowInsetsController, int i2) {
                    this.f3825a.lambda$addOnControllableInsetsChangedListener$0(onControllableInsetsChangedListener, windowInsetsController, i2);
                }
            };
            this.mListeners.put(onControllableInsetsChangedListener, onControllableInsetsChangedListener2);
            this.f3796b.addOnControllableInsetsChangedListener(onControllableInsetsChangedListener2);
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        void b(int i2, long j2, Interpolator interpolator, CancellationSignal cancellationSignal, final WindowInsetsAnimationControlListenerCompat windowInsetsAnimationControlListenerCompat) {
            this.f3796b.controlWindowInsetsAnimation(i2, j2, interpolator, cancellationSignal, new WindowInsetsAnimationControlListener() { // from class: androidx.core.view.WindowInsetsControllerCompat.Impl30.1
                private WindowInsetsAnimationControllerCompat mCompatAnimController = null;

                public void onCancelled(@Nullable WindowInsetsAnimationController windowInsetsAnimationController) {
                    windowInsetsAnimationControlListenerCompat.onCancelled(windowInsetsAnimationController == null ? null : this.mCompatAnimController);
                }

                public void onFinished(@NonNull WindowInsetsAnimationController windowInsetsAnimationController) {
                    windowInsetsAnimationControlListenerCompat.onFinished(this.mCompatAnimController);
                }

                public void onReady(@NonNull WindowInsetsAnimationController windowInsetsAnimationController, int i3) {
                    WindowInsetsAnimationControllerCompat windowInsetsAnimationControllerCompat = new WindowInsetsAnimationControllerCompat(windowInsetsAnimationController);
                    this.mCompatAnimController = windowInsetsAnimationControllerCompat;
                    windowInsetsAnimationControlListenerCompat.onReady(windowInsetsAnimationControllerCompat, i3);
                }
            });
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        int c() {
            return this.f3796b.getSystemBarsBehavior();
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        void d(int i2) {
            if ((i2 & 8) != 0) {
                this.f3797c.hide();
            }
            this.f3796b.hide(i2 & (-9));
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        void e(OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
            WindowInsetsController.OnControllableInsetsChangedListener onControllableInsetsChangedListenerA = s2.a(this.mListeners.remove(onControllableInsetsChangedListener));
            if (onControllableInsetsChangedListenerA != null) {
                this.f3796b.removeOnControllableInsetsChangedListener(onControllableInsetsChangedListenerA);
            }
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        void f(int i2) {
            this.f3796b.setSystemBarsBehavior(i2);
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        void g(int i2) {
            if ((i2 & 8) != 0) {
                this.f3797c.show();
            }
            this.f3796b.show(i2 & (-9));
        }

        protected void i(int i2) {
            View decorView = this.f3798d.getDecorView();
            decorView.setSystemUiVisibility(i2 | decorView.getSystemUiVisibility());
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public boolean isAppearanceLightNavigationBars() {
            this.f3796b.setSystemBarsAppearance(0, 0);
            return (this.f3796b.getSystemBarsAppearance() & 16) != 0;
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public boolean isAppearanceLightStatusBars() {
            this.f3796b.setSystemBarsAppearance(0, 0);
            return (this.f3796b.getSystemBarsAppearance() & 8) != 0;
        }

        protected void j(int i2) {
            View decorView = this.f3798d.getDecorView();
            decorView.setSystemUiVisibility((~i2) & decorView.getSystemUiVisibility());
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public void setAppearanceLightNavigationBars(boolean z2) {
            if (z2) {
                if (this.f3798d != null) {
                    i(16);
                }
                this.f3796b.setSystemBarsAppearance(16, 16);
            } else {
                if (this.f3798d != null) {
                    j(16);
                }
                this.f3796b.setSystemBarsAppearance(0, 16);
            }
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public void setAppearanceLightStatusBars(boolean z2) {
            if (z2) {
                if (this.f3798d != null) {
                    i(8192);
                }
                this.f3796b.setSystemBarsAppearance(8, 8);
            } else {
                if (this.f3798d != null) {
                    j(8192);
                }
                this.f3796b.setSystemBarsAppearance(0, 8);
            }
        }

        Impl30(WindowInsetsController windowInsetsController, WindowInsetsControllerCompat windowInsetsControllerCompat, SoftwareKeyboardControllerCompat softwareKeyboardControllerCompat) {
            this.mListeners = new SimpleArrayMap<>();
            this.f3796b = windowInsetsController;
            this.f3795a = windowInsetsControllerCompat;
            this.f3797c = softwareKeyboardControllerCompat;
        }
    }

    public WindowInsetsControllerCompat(@NonNull Window window, @NonNull View view) {
        SoftwareKeyboardControllerCompat softwareKeyboardControllerCompat = new SoftwareKeyboardControllerCompat(view);
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 30) {
            this.mImpl = new Impl30(window, this, softwareKeyboardControllerCompat);
        } else if (i2 >= 26) {
            this.mImpl = new Impl26(window, softwareKeyboardControllerCompat);
        } else {
            this.mImpl = new Impl23(window, softwareKeyboardControllerCompat);
        }
    }
}
