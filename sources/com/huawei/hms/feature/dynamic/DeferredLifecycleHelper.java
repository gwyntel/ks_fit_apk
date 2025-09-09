package com.huawei.hms.feature.dynamic;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.huawei.hms.feature.dynamic.LifecycleDelegate;
import java.util.Iterator;
import java.util.LinkedList;

/* loaded from: classes4.dex */
public abstract class DeferredLifecycleHelper<T extends LifecycleDelegate> {
    public static final int STATUS_ONCREATED = 1;
    public static final int STATUS_ONCREATEVIEW = 2;
    public static final int STATUS_ONINFLATE = 0;
    public static final int STATUS_ONRESUME = 5;
    public static final int STATUS_ONSTART = 4;

    /* renamed from: e, reason: collision with root package name */
    public static final String f16046e = "DeferredLifecycleHelper";

    /* renamed from: a, reason: collision with root package name */
    public T f16047a;

    /* renamed from: b, reason: collision with root package name */
    public Bundle f16048b;

    /* renamed from: c, reason: collision with root package name */
    public LinkedList<g> f16049c;

    /* renamed from: d, reason: collision with root package name */
    public OnDelegateCreatedListener<T> f16050d = new a();

    public class a implements OnDelegateCreatedListener<T> {
        public a() {
        }

        @Override // com.huawei.hms.feature.dynamic.OnDelegateCreatedListener
        public void onDelegateCreated(T t2) {
            DeferredLifecycleHelper.this.f16047a = t2;
            Iterator it = DeferredLifecycleHelper.this.f16049c.iterator();
            while (it.hasNext()) {
                ((g) it.next()).a(DeferredLifecycleHelper.this.f16047a);
            }
            DeferredLifecycleHelper.this.f16049c.clear();
            DeferredLifecycleHelper.this.f16048b = null;
        }
    }

    public class b implements g {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ Activity f16052a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ Bundle f16053b;

        /* renamed from: c, reason: collision with root package name */
        public final /* synthetic */ Bundle f16054c;

        public b(Activity activity, Bundle bundle, Bundle bundle2) {
            this.f16052a = activity;
            this.f16053b = bundle;
            this.f16054c = bundle2;
        }

        @Override // com.huawei.hms.feature.dynamic.DeferredLifecycleHelper.g
        public int a() {
            return 0;
        }

        @Override // com.huawei.hms.feature.dynamic.DeferredLifecycleHelper.g
        public void a(LifecycleDelegate lifecycleDelegate) {
            DeferredLifecycleHelper.this.f16047a.onInflate(this.f16052a, this.f16053b, this.f16054c);
        }
    }

    public class c implements g {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ Bundle f16056a;

        public c(Bundle bundle) {
            this.f16056a = bundle;
        }

        @Override // com.huawei.hms.feature.dynamic.DeferredLifecycleHelper.g
        public int a() {
            return 1;
        }

        @Override // com.huawei.hms.feature.dynamic.DeferredLifecycleHelper.g
        public void a(LifecycleDelegate lifecycleDelegate) {
            Log.d(DeferredLifecycleHelper.f16046e, "IDelegateLifeCycleCall onCreate:");
            lifecycleDelegate.onCreate(this.f16056a);
        }
    }

    public class d implements g {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ FrameLayout f16058a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ LayoutInflater f16059b;

        /* renamed from: c, reason: collision with root package name */
        public final /* synthetic */ ViewGroup f16060c;

        /* renamed from: d, reason: collision with root package name */
        public final /* synthetic */ Bundle f16061d;

        public d(FrameLayout frameLayout, LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            this.f16058a = frameLayout;
            this.f16059b = layoutInflater;
            this.f16060c = viewGroup;
            this.f16061d = bundle;
        }

        @Override // com.huawei.hms.feature.dynamic.DeferredLifecycleHelper.g
        public int a() {
            return 2;
        }

        @Override // com.huawei.hms.feature.dynamic.DeferredLifecycleHelper.g
        public void a(LifecycleDelegate lifecycleDelegate) {
            this.f16058a.removeAllViews();
            this.f16058a.addView(DeferredLifecycleHelper.this.f16047a.onCreateView(this.f16059b, this.f16060c, this.f16061d));
        }
    }

    public class e implements g {
        public e() {
        }

        @Override // com.huawei.hms.feature.dynamic.DeferredLifecycleHelper.g
        public int a() {
            return 4;
        }

        @Override // com.huawei.hms.feature.dynamic.DeferredLifecycleHelper.g
        public void a(LifecycleDelegate lifecycleDelegate) {
            Log.d(DeferredLifecycleHelper.f16046e, "IDelegateLifeCycleCall onStart:");
            lifecycleDelegate.onStart();
        }
    }

    public class f implements g {
        public f() {
        }

        @Override // com.huawei.hms.feature.dynamic.DeferredLifecycleHelper.g
        public int a() {
            return 5;
        }

        @Override // com.huawei.hms.feature.dynamic.DeferredLifecycleHelper.g
        public void a(LifecycleDelegate lifecycleDelegate) {
            Log.d(DeferredLifecycleHelper.f16046e, "IDelegateLifeCycleCall onResume:");
            lifecycleDelegate.onResume();
        }
    }

    public interface g {
        int a();

        void a(LifecycleDelegate lifecycleDelegate);
    }

    public abstract void createDelegate(OnDelegateCreatedListener<T> onDelegateCreatedListener);

    public T getDelegate() {
        return this.f16047a;
    }

    public void onCreate(Bundle bundle) {
        a(bundle, new c(bundle));
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FrameLayout frameLayout = new FrameLayout(layoutInflater.getContext());
        a(bundle, new d(frameLayout, layoutInflater, viewGroup, bundle));
        return frameLayout;
    }

    public void onDestroy() {
        T t2 = this.f16047a;
        if (t2 != null) {
            t2.onDestroy();
        } else {
            a(0);
        }
    }

    public void onDestroyView() {
        T t2 = this.f16047a;
        if (t2 != null) {
            t2.onDestroyView();
        } else {
            a(1);
        }
    }

    public void onInflate(Activity activity, Bundle bundle, Bundle bundle2) {
        a(bundle2, new b(activity, bundle, bundle2));
    }

    public void onLowMemory() {
        T t2 = this.f16047a;
        if (t2 != null) {
            t2.onLowMemory();
        }
    }

    public void onPause() {
        T t2 = this.f16047a;
        if (t2 != null) {
            t2.onPause();
        } else {
            a(5);
        }
    }

    public void onResume() {
        a((Bundle) null, new f());
    }

    public void onSaveInstanceState(Bundle bundle) {
        T t2 = this.f16047a;
        if (t2 != null) {
            t2.onSaveInstanceState(bundle);
            return;
        }
        Bundle bundle2 = this.f16048b;
        if (bundle2 != null) {
            bundle.putAll(bundle2);
        }
    }

    public void onStart() {
        a((Bundle) null, new e());
    }

    public void onStop() {
        T t2 = this.f16047a;
        if (t2 != null) {
            t2.onStop();
        } else {
            a(4);
        }
    }

    private void a(int i2) {
        while (!this.f16049c.isEmpty() && this.f16049c.getLast().a() >= i2) {
            this.f16049c.removeLast();
        }
    }

    private void a(Bundle bundle, g gVar) {
        T t2 = this.f16047a;
        if (t2 != null) {
            gVar.a(t2);
            return;
        }
        if (this.f16049c == null) {
            this.f16049c = new LinkedList<>();
        }
        this.f16049c.add(gVar);
        if (bundle != null) {
            Bundle bundle2 = this.f16048b;
            if (bundle2 == null) {
                Object objClone = bundle.clone();
                if (objClone != null && (objClone instanceof Bundle)) {
                    this.f16048b = (Bundle) objClone;
                }
            } else {
                bundle2.putAll(bundle);
            }
        }
        createDelegate(this.f16050d);
    }
}
