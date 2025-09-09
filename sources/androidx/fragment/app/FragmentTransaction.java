package androidx.fragment.app;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.AnimRes;
import androidx.annotation.AnimatorRes;
import androidx.annotation.IdRes;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.strictmode.FragmentStrictMode;
import androidx.lifecycle.Lifecycle;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public abstract class FragmentTransaction {
    public static final int TRANSIT_ENTER_MASK = 4096;
    public static final int TRANSIT_EXIT_MASK = 8192;
    public static final int TRANSIT_FRAGMENT_CLOSE = 8194;
    public static final int TRANSIT_FRAGMENT_FADE = 4099;
    public static final int TRANSIT_FRAGMENT_MATCH_ACTIVITY_CLOSE = 8197;
    public static final int TRANSIT_FRAGMENT_MATCH_ACTIVITY_OPEN = 4100;
    public static final int TRANSIT_FRAGMENT_OPEN = 4097;
    public static final int TRANSIT_NONE = 0;
    public static final int TRANSIT_UNSET = -1;

    /* renamed from: a, reason: collision with root package name */
    ArrayList f4231a;

    /* renamed from: b, reason: collision with root package name */
    int f4232b;

    /* renamed from: c, reason: collision with root package name */
    int f4233c;

    /* renamed from: d, reason: collision with root package name */
    int f4234d;

    /* renamed from: e, reason: collision with root package name */
    int f4235e;

    /* renamed from: f, reason: collision with root package name */
    int f4236f;

    /* renamed from: g, reason: collision with root package name */
    boolean f4237g;

    /* renamed from: h, reason: collision with root package name */
    boolean f4238h;

    /* renamed from: i, reason: collision with root package name */
    String f4239i;

    /* renamed from: j, reason: collision with root package name */
    int f4240j;

    /* renamed from: k, reason: collision with root package name */
    CharSequence f4241k;

    /* renamed from: l, reason: collision with root package name */
    int f4242l;

    /* renamed from: m, reason: collision with root package name */
    CharSequence f4243m;
    private final ClassLoader mClassLoader;
    private final FragmentFactory mFragmentFactory;

    /* renamed from: n, reason: collision with root package name */
    ArrayList f4244n;

    /* renamed from: o, reason: collision with root package name */
    ArrayList f4245o;

    /* renamed from: p, reason: collision with root package name */
    boolean f4246p;

    /* renamed from: q, reason: collision with root package name */
    ArrayList f4247q;

    static final class Op {

        /* renamed from: a, reason: collision with root package name */
        int f4248a;

        /* renamed from: b, reason: collision with root package name */
        Fragment f4249b;

        /* renamed from: c, reason: collision with root package name */
        boolean f4250c;

        /* renamed from: d, reason: collision with root package name */
        int f4251d;

        /* renamed from: e, reason: collision with root package name */
        int f4252e;

        /* renamed from: f, reason: collision with root package name */
        int f4253f;

        /* renamed from: g, reason: collision with root package name */
        int f4254g;

        /* renamed from: h, reason: collision with root package name */
        Lifecycle.State f4255h;

        /* renamed from: i, reason: collision with root package name */
        Lifecycle.State f4256i;

        Op() {
        }

        Op(int i2, Fragment fragment) {
            this.f4248a = i2;
            this.f4249b = fragment;
            this.f4250c = false;
            Lifecycle.State state = Lifecycle.State.RESUMED;
            this.f4255h = state;
            this.f4256i = state;
        }

        Op(int i2, Fragment fragment, boolean z2) {
            this.f4248a = i2;
            this.f4249b = fragment;
            this.f4250c = z2;
            Lifecycle.State state = Lifecycle.State.RESUMED;
            this.f4255h = state;
            this.f4256i = state;
        }

        Op(int i2, Fragment fragment, Lifecycle.State state) {
            this.f4248a = i2;
            this.f4249b = fragment;
            this.f4250c = false;
            this.f4255h = fragment.mMaxState;
            this.f4256i = state;
        }

        Op(Op op) {
            this.f4248a = op.f4248a;
            this.f4249b = op.f4249b;
            this.f4250c = op.f4250c;
            this.f4251d = op.f4251d;
            this.f4252e = op.f4252e;
            this.f4253f = op.f4253f;
            this.f4254g = op.f4254g;
            this.f4255h = op.f4255h;
            this.f4256i = op.f4256i;
        }
    }

    @Deprecated
    public FragmentTransaction() {
        this.f4231a = new ArrayList();
        this.f4238h = true;
        this.f4246p = false;
        this.mFragmentFactory = null;
        this.mClassLoader = null;
    }

    @NonNull
    private Fragment createFragment(@NonNull Class<? extends Fragment> cls, @Nullable Bundle bundle) {
        FragmentFactory fragmentFactory = this.mFragmentFactory;
        if (fragmentFactory == null) {
            throw new IllegalStateException("Creating a Fragment requires that this FragmentTransaction was built with FragmentManager.beginTransaction()");
        }
        ClassLoader classLoader = this.mClassLoader;
        if (classLoader == null) {
            throw new IllegalStateException("The FragmentManager must be attached to itshost to create a Fragment");
        }
        Fragment fragmentInstantiate = fragmentFactory.instantiate(classLoader, cls.getName());
        if (bundle != null) {
            fragmentInstantiate.setArguments(bundle);
        }
        return fragmentInstantiate;
    }

    FragmentTransaction a(ViewGroup viewGroup, Fragment fragment, String str) {
        fragment.mContainer = viewGroup;
        return add(viewGroup.getId(), fragment, str);
    }

    @NonNull
    public final FragmentTransaction add(@NonNull Class<? extends Fragment> cls, @Nullable Bundle bundle, @Nullable String str) {
        return add(createFragment(cls, bundle), str);
    }

    @NonNull
    public FragmentTransaction addSharedElement(@NonNull View view, @NonNull String str) {
        if (FragmentTransition.supportsTransition()) {
            String transitionName = ViewCompat.getTransitionName(view);
            if (transitionName == null) {
                throw new IllegalArgumentException("Unique transitionNames are required for all sharedElements");
            }
            if (this.f4244n == null) {
                this.f4244n = new ArrayList();
                this.f4245o = new ArrayList();
            } else {
                if (this.f4245o.contains(str)) {
                    throw new IllegalArgumentException("A shared element with the target name '" + str + "' has already been added to the transaction.");
                }
                if (this.f4244n.contains(transitionName)) {
                    throw new IllegalArgumentException("A shared element with the source name '" + transitionName + "' has already been added to the transaction.");
                }
            }
            this.f4244n.add(transitionName);
            this.f4245o.add(str);
        }
        return this;
    }

    @NonNull
    public FragmentTransaction addToBackStack(@Nullable String str) {
        if (!this.f4238h) {
            throw new IllegalStateException("This FragmentTransaction is not allowed to be added to the back stack.");
        }
        this.f4237g = true;
        this.f4239i = str;
        return this;
    }

    @NonNull
    public FragmentTransaction attach(@NonNull Fragment fragment) {
        b(new Op(7, fragment));
        return this;
    }

    void b(Op op) {
        this.f4231a.add(op);
        op.f4251d = this.f4232b;
        op.f4252e = this.f4233c;
        op.f4253f = this.f4234d;
        op.f4254g = this.f4235e;
    }

    void c(int i2, Fragment fragment, String str, int i3) {
        String str2 = fragment.mPreviousWho;
        if (str2 != null) {
            FragmentStrictMode.onFragmentReuse(fragment, str2);
        }
        Class<?> cls = fragment.getClass();
        int modifiers = cls.getModifiers();
        if (cls.isAnonymousClass() || !Modifier.isPublic(modifiers) || (cls.isMemberClass() && !Modifier.isStatic(modifiers))) {
            throw new IllegalStateException("Fragment " + cls.getCanonicalName() + " must be a public static class to be  properly recreated from instance state.");
        }
        if (str != null) {
            String str3 = fragment.mTag;
            if (str3 != null && !str.equals(str3)) {
                throw new IllegalStateException("Can't change tag of fragment " + fragment + ": was " + fragment.mTag + " now " + str);
            }
            fragment.mTag = str;
        }
        if (i2 != 0) {
            if (i2 == -1) {
                throw new IllegalArgumentException("Can't add fragment " + fragment + " with tag " + str + " to container view with no id");
            }
            int i4 = fragment.mFragmentId;
            if (i4 != 0 && i4 != i2) {
                throw new IllegalStateException("Can't change container ID of fragment " + fragment + ": was " + fragment.mFragmentId + " now " + i2);
            }
            fragment.mFragmentId = i2;
            fragment.mContainerId = i2;
        }
        b(new Op(i3, fragment));
    }

    public abstract int commit();

    public abstract int commitAllowingStateLoss();

    @MainThread
    public abstract void commitNow();

    @MainThread
    public abstract void commitNowAllowingStateLoss();

    @NonNull
    public FragmentTransaction detach(@NonNull Fragment fragment) {
        b(new Op(6, fragment));
        return this;
    }

    @NonNull
    public FragmentTransaction disallowAddToBackStack() {
        if (this.f4237g) {
            throw new IllegalStateException("This transaction is already being added to the back stack");
        }
        this.f4238h = false;
        return this;
    }

    @NonNull
    public FragmentTransaction hide(@NonNull Fragment fragment) {
        b(new Op(4, fragment));
        return this;
    }

    public boolean isAddToBackStackAllowed() {
        return this.f4238h;
    }

    public boolean isEmpty() {
        return this.f4231a.isEmpty();
    }

    @NonNull
    public FragmentTransaction remove(@NonNull Fragment fragment) {
        b(new Op(3, fragment));
        return this;
    }

    @NonNull
    public final FragmentTransaction replace(@IdRes int i2, @NonNull Class<? extends Fragment> cls, @Nullable Bundle bundle) {
        return replace(i2, cls, bundle, null);
    }

    @NonNull
    public FragmentTransaction runOnCommit(@NonNull Runnable runnable) {
        disallowAddToBackStack();
        if (this.f4247q == null) {
            this.f4247q = new ArrayList();
        }
        this.f4247q.add(runnable);
        return this;
    }

    @NonNull
    @Deprecated
    public FragmentTransaction setAllowOptimization(boolean z2) {
        return setReorderingAllowed(z2);
    }

    @NonNull
    @Deprecated
    public FragmentTransaction setBreadCrumbShortTitle(@StringRes int i2) {
        this.f4242l = i2;
        this.f4243m = null;
        return this;
    }

    @NonNull
    @Deprecated
    public FragmentTransaction setBreadCrumbTitle(@StringRes int i2) {
        this.f4240j = i2;
        this.f4241k = null;
        return this;
    }

    @NonNull
    public FragmentTransaction setCustomAnimations(@AnimRes @AnimatorRes int i2, @AnimRes @AnimatorRes int i3) {
        return setCustomAnimations(i2, i3, 0, 0);
    }

    @NonNull
    public FragmentTransaction setMaxLifecycle(@NonNull Fragment fragment, @NonNull Lifecycle.State state) {
        b(new Op(10, fragment, state));
        return this;
    }

    @NonNull
    public FragmentTransaction setPrimaryNavigationFragment(@Nullable Fragment fragment) {
        b(new Op(8, fragment));
        return this;
    }

    @NonNull
    public FragmentTransaction setReorderingAllowed(boolean z2) {
        this.f4246p = z2;
        return this;
    }

    @NonNull
    public FragmentTransaction setTransition(int i2) {
        this.f4236f = i2;
        return this;
    }

    @NonNull
    @Deprecated
    public FragmentTransaction setTransitionStyle(@StyleRes int i2) {
        return this;
    }

    @NonNull
    public FragmentTransaction show(@NonNull Fragment fragment) {
        b(new Op(5, fragment));
        return this;
    }

    @NonNull
    public FragmentTransaction add(@NonNull Fragment fragment, @Nullable String str) {
        c(0, fragment, str, 1);
        return this;
    }

    @NonNull
    public FragmentTransaction replace(@IdRes int i2, @NonNull Fragment fragment) {
        return replace(i2, fragment, (String) null);
    }

    @NonNull
    public FragmentTransaction setCustomAnimations(@AnimRes @AnimatorRes int i2, @AnimRes @AnimatorRes int i3, @AnimRes @AnimatorRes int i4, @AnimRes @AnimatorRes int i5) {
        this.f4232b = i2;
        this.f4233c = i3;
        this.f4234d = i4;
        this.f4235e = i5;
        return this;
    }

    @NonNull
    public final FragmentTransaction add(@IdRes int i2, @NonNull Class<? extends Fragment> cls, @Nullable Bundle bundle) {
        return add(i2, createFragment(cls, bundle));
    }

    @NonNull
    public final FragmentTransaction replace(@IdRes int i2, @NonNull Class<? extends Fragment> cls, @Nullable Bundle bundle, @Nullable String str) {
        return replace(i2, createFragment(cls, bundle), str);
    }

    @NonNull
    @Deprecated
    public FragmentTransaction setBreadCrumbShortTitle(@Nullable CharSequence charSequence) {
        this.f4242l = 0;
        this.f4243m = charSequence;
        return this;
    }

    @NonNull
    @Deprecated
    public FragmentTransaction setBreadCrumbTitle(@Nullable CharSequence charSequence) {
        this.f4240j = 0;
        this.f4241k = charSequence;
        return this;
    }

    @NonNull
    public FragmentTransaction add(@IdRes int i2, @NonNull Fragment fragment) {
        c(i2, fragment, null, 1);
        return this;
    }

    @NonNull
    public FragmentTransaction replace(@IdRes int i2, @NonNull Fragment fragment, @Nullable String str) {
        if (i2 != 0) {
            c(i2, fragment, str, 2);
            return this;
        }
        throw new IllegalArgumentException("Must use non-zero containerViewId");
    }

    @NonNull
    public final FragmentTransaction add(@IdRes int i2, @NonNull Class<? extends Fragment> cls, @Nullable Bundle bundle, @Nullable String str) {
        return add(i2, createFragment(cls, bundle), str);
    }

    @NonNull
    public FragmentTransaction add(@IdRes int i2, @NonNull Fragment fragment, @Nullable String str) {
        c(i2, fragment, str, 1);
        return this;
    }

    FragmentTransaction(FragmentFactory fragmentFactory, ClassLoader classLoader) {
        this.f4231a = new ArrayList();
        this.f4238h = true;
        this.f4246p = false;
        this.mFragmentFactory = fragmentFactory;
        this.mClassLoader = classLoader;
    }

    FragmentTransaction(FragmentFactory fragmentFactory, ClassLoader classLoader, FragmentTransaction fragmentTransaction) {
        this(fragmentFactory, classLoader);
        Iterator it = fragmentTransaction.f4231a.iterator();
        while (it.hasNext()) {
            this.f4231a.add(new Op((Op) it.next()));
        }
        this.f4232b = fragmentTransaction.f4232b;
        this.f4233c = fragmentTransaction.f4233c;
        this.f4234d = fragmentTransaction.f4234d;
        this.f4235e = fragmentTransaction.f4235e;
        this.f4236f = fragmentTransaction.f4236f;
        this.f4237g = fragmentTransaction.f4237g;
        this.f4238h = fragmentTransaction.f4238h;
        this.f4239i = fragmentTransaction.f4239i;
        this.f4242l = fragmentTransaction.f4242l;
        this.f4243m = fragmentTransaction.f4243m;
        this.f4240j = fragmentTransaction.f4240j;
        this.f4241k = fragmentTransaction.f4241k;
        if (fragmentTransaction.f4244n != null) {
            ArrayList arrayList = new ArrayList();
            this.f4244n = arrayList;
            arrayList.addAll(fragmentTransaction.f4244n);
        }
        if (fragmentTransaction.f4245o != null) {
            ArrayList arrayList2 = new ArrayList();
            this.f4245o = arrayList2;
            arrayList2.addAll(fragmentTransaction.f4245o);
        }
        this.f4246p = fragmentTransaction.f4246p;
    }
}
