package com.huawei.hmf.tasks.a;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.util.Log;
import com.huawei.hmf.tasks.ExecuteResult;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;

/* loaded from: classes3.dex */
public final class g extends Fragment {

    /* renamed from: b, reason: collision with root package name */
    private static final WeakHashMap<Activity, WeakReference<g>> f15677b = new WeakHashMap<>();

    /* renamed from: a, reason: collision with root package name */
    private final List<WeakReference<ExecuteResult<?>>> f15678a = new ArrayList();

    private static g a(Activity activity) {
        g gVarA;
        WeakHashMap<Activity, WeakReference<g>> weakHashMap = f15677b;
        WeakReference<g> weakReference = weakHashMap.get(activity);
        if (weakReference != null && weakReference.get() != null) {
            return weakReference.get();
        }
        FragmentManager fragmentManager = activity.getFragmentManager();
        try {
            g gVar = (g) fragmentManager.findFragmentByTag("com.huawei.hmf.tasks.lifecycle_fragment_tag");
            if (gVar == null) {
                try {
                    gVarA = a(fragmentManager);
                } catch (ClassCastException e2) {
                    e = e2;
                    gVarA = gVar;
                    Log.e("LifecycleCallbackFrg", "found LifecycleCallbackFragment but the type do not match. " + e.getMessage());
                    return gVarA;
                }
            } else {
                gVarA = gVar;
            }
        } catch (ClassCastException e3) {
            e = e3;
            gVarA = null;
        }
        try {
            weakHashMap.put(activity, new WeakReference<>(gVarA));
        } catch (ClassCastException e4) {
            e = e4;
            Log.e("LifecycleCallbackFrg", "found LifecycleCallbackFragment but the type do not match. " + e.getMessage());
            return gVarA;
        }
        return gVarA;
    }

    @Override // android.app.Fragment
    public final void onStop() {
        super.onStop();
        synchronized (this.f15678a) {
            try {
                Iterator<WeakReference<ExecuteResult<?>>> it = this.f15678a.iterator();
                while (it.hasNext()) {
                    ExecuteResult<?> executeResult = it.next().get();
                    if (executeResult != null) {
                        executeResult.cancel();
                    }
                }
                this.f15678a.clear();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private static g a(FragmentManager fragmentManager) {
        g gVar = null;
        try {
            g gVar2 = new g();
            try {
                fragmentManager.beginTransaction().add(gVar2, "com.huawei.hmf.tasks.lifecycle_fragment_tag").commitAllowingStateLoss();
                return gVar2;
            } catch (Exception e2) {
                e = e2;
                gVar = gVar2;
                Log.e("LifecycleCallbackFrg", "create fragment failed." + e.getMessage());
                return gVar;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    public static void a(Activity activity, ExecuteResult executeResult) {
        g gVarA = a(activity);
        if (gVarA != null) {
            synchronized (gVarA.f15678a) {
                gVarA.f15678a.add(new WeakReference<>(executeResult));
            }
        }
    }
}
