package androidx.fragment.app;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
class FragmentLifecycleCallbacksDispatcher {

    @NonNull
    private final FragmentManager mFragmentManager;

    @NonNull
    private final CopyOnWriteArrayList<FragmentLifecycleCallbacksHolder> mLifecycleCallbacks = new CopyOnWriteArrayList<>();

    private static final class FragmentLifecycleCallbacksHolder {

        /* renamed from: a, reason: collision with root package name */
        final FragmentManager.FragmentLifecycleCallbacks f4172a;

        /* renamed from: b, reason: collision with root package name */
        final boolean f4173b;

        FragmentLifecycleCallbacksHolder(FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks, boolean z2) {
            this.f4172a = fragmentLifecycleCallbacks;
            this.f4173b = z2;
        }
    }

    FragmentLifecycleCallbacksDispatcher(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }

    void a(Fragment fragment, Bundle bundle, boolean z2) {
        Fragment fragmentB0 = this.mFragmentManager.b0();
        if (fragmentB0 != null) {
            fragmentB0.getParentFragmentManager().a0().a(fragment, bundle, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder next = it.next();
            if (!z2 || next.f4173b) {
                next.f4172a.onFragmentActivityCreated(this.mFragmentManager, fragment, bundle);
            }
        }
    }

    void b(Fragment fragment, boolean z2) {
        Context contextB = this.mFragmentManager.getHost().b();
        Fragment fragmentB0 = this.mFragmentManager.b0();
        if (fragmentB0 != null) {
            fragmentB0.getParentFragmentManager().a0().b(fragment, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder next = it.next();
            if (!z2 || next.f4173b) {
                next.f4172a.onFragmentAttached(this.mFragmentManager, fragment, contextB);
            }
        }
    }

    void c(Fragment fragment, Bundle bundle, boolean z2) {
        Fragment fragmentB0 = this.mFragmentManager.b0();
        if (fragmentB0 != null) {
            fragmentB0.getParentFragmentManager().a0().c(fragment, bundle, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder next = it.next();
            if (!z2 || next.f4173b) {
                next.f4172a.onFragmentCreated(this.mFragmentManager, fragment, bundle);
            }
        }
    }

    void d(Fragment fragment, boolean z2) {
        Fragment fragmentB0 = this.mFragmentManager.b0();
        if (fragmentB0 != null) {
            fragmentB0.getParentFragmentManager().a0().d(fragment, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder next = it.next();
            if (!z2 || next.f4173b) {
                next.f4172a.onFragmentDestroyed(this.mFragmentManager, fragment);
            }
        }
    }

    void e(Fragment fragment, boolean z2) {
        Fragment fragmentB0 = this.mFragmentManager.b0();
        if (fragmentB0 != null) {
            fragmentB0.getParentFragmentManager().a0().e(fragment, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder next = it.next();
            if (!z2 || next.f4173b) {
                next.f4172a.onFragmentDetached(this.mFragmentManager, fragment);
            }
        }
    }

    void f(Fragment fragment, boolean z2) {
        Fragment fragmentB0 = this.mFragmentManager.b0();
        if (fragmentB0 != null) {
            fragmentB0.getParentFragmentManager().a0().f(fragment, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder next = it.next();
            if (!z2 || next.f4173b) {
                next.f4172a.onFragmentPaused(this.mFragmentManager, fragment);
            }
        }
    }

    void g(Fragment fragment, boolean z2) {
        Context contextB = this.mFragmentManager.getHost().b();
        Fragment fragmentB0 = this.mFragmentManager.b0();
        if (fragmentB0 != null) {
            fragmentB0.getParentFragmentManager().a0().g(fragment, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder next = it.next();
            if (!z2 || next.f4173b) {
                next.f4172a.onFragmentPreAttached(this.mFragmentManager, fragment, contextB);
            }
        }
    }

    void h(Fragment fragment, Bundle bundle, boolean z2) {
        Fragment fragmentB0 = this.mFragmentManager.b0();
        if (fragmentB0 != null) {
            fragmentB0.getParentFragmentManager().a0().h(fragment, bundle, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder next = it.next();
            if (!z2 || next.f4173b) {
                next.f4172a.onFragmentPreCreated(this.mFragmentManager, fragment, bundle);
            }
        }
    }

    void i(Fragment fragment, boolean z2) {
        Fragment fragmentB0 = this.mFragmentManager.b0();
        if (fragmentB0 != null) {
            fragmentB0.getParentFragmentManager().a0().i(fragment, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder next = it.next();
            if (!z2 || next.f4173b) {
                next.f4172a.onFragmentResumed(this.mFragmentManager, fragment);
            }
        }
    }

    void j(Fragment fragment, Bundle bundle, boolean z2) {
        Fragment fragmentB0 = this.mFragmentManager.b0();
        if (fragmentB0 != null) {
            fragmentB0.getParentFragmentManager().a0().j(fragment, bundle, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder next = it.next();
            if (!z2 || next.f4173b) {
                next.f4172a.onFragmentSaveInstanceState(this.mFragmentManager, fragment, bundle);
            }
        }
    }

    void k(Fragment fragment, boolean z2) {
        Fragment fragmentB0 = this.mFragmentManager.b0();
        if (fragmentB0 != null) {
            fragmentB0.getParentFragmentManager().a0().k(fragment, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder next = it.next();
            if (!z2 || next.f4173b) {
                next.f4172a.onFragmentStarted(this.mFragmentManager, fragment);
            }
        }
    }

    void l(Fragment fragment, boolean z2) {
        Fragment fragmentB0 = this.mFragmentManager.b0();
        if (fragmentB0 != null) {
            fragmentB0.getParentFragmentManager().a0().l(fragment, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder next = it.next();
            if (!z2 || next.f4173b) {
                next.f4172a.onFragmentStopped(this.mFragmentManager, fragment);
            }
        }
    }

    void m(Fragment fragment, View view, Bundle bundle, boolean z2) {
        Fragment fragmentB0 = this.mFragmentManager.b0();
        if (fragmentB0 != null) {
            fragmentB0.getParentFragmentManager().a0().m(fragment, view, bundle, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder next = it.next();
            if (!z2 || next.f4173b) {
                next.f4172a.onFragmentViewCreated(this.mFragmentManager, fragment, view, bundle);
            }
        }
    }

    void n(Fragment fragment, boolean z2) {
        Fragment fragmentB0 = this.mFragmentManager.b0();
        if (fragmentB0 != null) {
            fragmentB0.getParentFragmentManager().a0().n(fragment, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder next = it.next();
            if (!z2 || next.f4173b) {
                next.f4172a.onFragmentViewDestroyed(this.mFragmentManager, fragment);
            }
        }
    }

    public void registerFragmentLifecycleCallbacks(@NonNull FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks, boolean z2) {
        this.mLifecycleCallbacks.add(new FragmentLifecycleCallbacksHolder(fragmentLifecycleCallbacks, z2));
    }

    public void unregisterFragmentLifecycleCallbacks(@NonNull FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks) {
        synchronized (this.mLifecycleCallbacks) {
            try {
                int size = this.mLifecycleCallbacks.size();
                int i2 = 0;
                while (true) {
                    if (i2 >= size) {
                        break;
                    }
                    if (this.mLifecycleCallbacks.get(i2).f4172a == fragmentLifecycleCallbacks) {
                        this.mLifecycleCallbacks.remove(i2);
                        break;
                    }
                    i2++;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
