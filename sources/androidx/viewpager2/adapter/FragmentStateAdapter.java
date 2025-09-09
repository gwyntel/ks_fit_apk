package androidx.viewpager2.adapter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArraySet;
import androidx.collection.LongSparseArray;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import java.util.Iterator;

/* loaded from: classes2.dex */
public abstract class FragmentStateAdapter extends RecyclerView.Adapter<FragmentViewHolder> implements StatefulAdapter {
    private static final long GRACE_WINDOW_TIME_MS = 10000;
    private static final String KEY_PREFIX_FRAGMENT = "f#";
    private static final String KEY_PREFIX_STATE = "s#";

    /* renamed from: a, reason: collision with root package name */
    final Lifecycle f6471a;

    /* renamed from: b, reason: collision with root package name */
    final FragmentManager f6472b;

    /* renamed from: c, reason: collision with root package name */
    final LongSparseArray f6473c;

    /* renamed from: d, reason: collision with root package name */
    boolean f6474d;
    private FragmentMaxLifecycleEnforcer mFragmentMaxLifecycleEnforcer;
    private boolean mHasStaleFragments;
    private final LongSparseArray<Integer> mItemIdToViewHolder;
    private final LongSparseArray<Fragment.SavedState> mSavedStates;

    private static abstract class DataSetChangeObserver extends RecyclerView.AdapterDataObserver {
        private DataSetChangeObserver() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public abstract void onChanged();

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeChanged(int i2, int i3) {
            onChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeInserted(int i2, int i3) {
            onChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeMoved(int i2, int i3, int i4) {
            onChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeRemoved(int i2, int i3) {
            onChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeChanged(int i2, int i3, @Nullable Object obj) {
            onChanged();
        }
    }

    class FragmentMaxLifecycleEnforcer {
        private RecyclerView.AdapterDataObserver mDataObserver;
        private LifecycleEventObserver mLifecycleObserver;
        private ViewPager2.OnPageChangeCallback mPageChangeCallback;
        private long mPrimaryItemId = -1;
        private ViewPager2 mViewPager;

        FragmentMaxLifecycleEnforcer() {
        }

        @NonNull
        private ViewPager2 inferViewPager(@NonNull RecyclerView recyclerView) {
            ViewParent parent = recyclerView.getParent();
            if (parent instanceof ViewPager2) {
                return (ViewPager2) parent;
            }
            throw new IllegalStateException("Expected ViewPager2 instance. Got: " + parent);
        }

        void a(RecyclerView recyclerView) {
            this.mViewPager = inferViewPager(recyclerView);
            ViewPager2.OnPageChangeCallback onPageChangeCallback = new ViewPager2.OnPageChangeCallback() { // from class: androidx.viewpager2.adapter.FragmentStateAdapter.FragmentMaxLifecycleEnforcer.1
                @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
                public void onPageScrollStateChanged(int i2) {
                    FragmentMaxLifecycleEnforcer.this.c(false);
                }

                @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
                public void onPageSelected(int i2) {
                    FragmentMaxLifecycleEnforcer.this.c(false);
                }
            };
            this.mPageChangeCallback = onPageChangeCallback;
            this.mViewPager.registerOnPageChangeCallback(onPageChangeCallback);
            DataSetChangeObserver dataSetChangeObserver = new DataSetChangeObserver() { // from class: androidx.viewpager2.adapter.FragmentStateAdapter.FragmentMaxLifecycleEnforcer.2
                @Override // androidx.viewpager2.adapter.FragmentStateAdapter.DataSetChangeObserver, androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
                public void onChanged() {
                    FragmentMaxLifecycleEnforcer.this.c(true);
                }
            };
            this.mDataObserver = dataSetChangeObserver;
            FragmentStateAdapter.this.registerAdapterDataObserver(dataSetChangeObserver);
            LifecycleEventObserver lifecycleEventObserver = new LifecycleEventObserver() { // from class: androidx.viewpager2.adapter.FragmentStateAdapter.FragmentMaxLifecycleEnforcer.3
                @Override // androidx.lifecycle.LifecycleEventObserver
                public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
                    FragmentMaxLifecycleEnforcer.this.c(false);
                }
            };
            this.mLifecycleObserver = lifecycleEventObserver;
            FragmentStateAdapter.this.f6471a.addObserver(lifecycleEventObserver);
        }

        void b(RecyclerView recyclerView) {
            inferViewPager(recyclerView).unregisterOnPageChangeCallback(this.mPageChangeCallback);
            FragmentStateAdapter.this.unregisterAdapterDataObserver(this.mDataObserver);
            FragmentStateAdapter.this.f6471a.removeObserver(this.mLifecycleObserver);
            this.mViewPager = null;
        }

        void c(boolean z2) {
            int currentItem;
            Fragment fragment;
            if (FragmentStateAdapter.this.d() || this.mViewPager.getScrollState() != 0 || FragmentStateAdapter.this.f6473c.isEmpty() || FragmentStateAdapter.this.getItemCount() == 0 || (currentItem = this.mViewPager.getCurrentItem()) >= FragmentStateAdapter.this.getItemCount()) {
                return;
            }
            long itemId = FragmentStateAdapter.this.getItemId(currentItem);
            if ((itemId != this.mPrimaryItemId || z2) && (fragment = (Fragment) FragmentStateAdapter.this.f6473c.get(itemId)) != null && fragment.isAdded()) {
                this.mPrimaryItemId = itemId;
                FragmentTransaction fragmentTransactionBeginTransaction = FragmentStateAdapter.this.f6472b.beginTransaction();
                Fragment fragment2 = null;
                for (int i2 = 0; i2 < FragmentStateAdapter.this.f6473c.size(); i2++) {
                    long jKeyAt = FragmentStateAdapter.this.f6473c.keyAt(i2);
                    Fragment fragment3 = (Fragment) FragmentStateAdapter.this.f6473c.valueAt(i2);
                    if (fragment3.isAdded()) {
                        if (jKeyAt != this.mPrimaryItemId) {
                            fragmentTransactionBeginTransaction.setMaxLifecycle(fragment3, Lifecycle.State.STARTED);
                        } else {
                            fragment2 = fragment3;
                        }
                        fragment3.setMenuVisibility(jKeyAt == this.mPrimaryItemId);
                    }
                }
                if (fragment2 != null) {
                    fragmentTransactionBeginTransaction.setMaxLifecycle(fragment2, Lifecycle.State.RESUMED);
                }
                if (fragmentTransactionBeginTransaction.isEmpty()) {
                    return;
                }
                fragmentTransactionBeginTransaction.commitNow();
            }
        }
    }

    public FragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
        this(fragmentActivity.getSupportFragmentManager(), fragmentActivity.getLifecycle());
    }

    @NonNull
    private static String createKey(@NonNull String str, long j2) {
        return str + j2;
    }

    private void ensureFragment(int i2) {
        long itemId = getItemId(i2);
        if (this.f6473c.containsKey(itemId)) {
            return;
        }
        Fragment fragmentCreateFragment = createFragment(i2);
        fragmentCreateFragment.setInitialSavedState(this.mSavedStates.get(itemId));
        this.f6473c.put(itemId, fragmentCreateFragment);
    }

    private boolean isFragmentViewBound(long j2) {
        View view;
        if (this.mItemIdToViewHolder.containsKey(j2)) {
            return true;
        }
        Fragment fragment = (Fragment) this.f6473c.get(j2);
        return (fragment == null || (view = fragment.getView()) == null || view.getParent() == null) ? false : true;
    }

    private static boolean isValidKey(@NonNull String str, @NonNull String str2) {
        return str.startsWith(str2) && str.length() > str2.length();
    }

    private Long itemForViewHolder(int i2) {
        Long lValueOf = null;
        for (int i3 = 0; i3 < this.mItemIdToViewHolder.size(); i3++) {
            if (this.mItemIdToViewHolder.valueAt(i3).intValue() == i2) {
                if (lValueOf != null) {
                    throw new IllegalStateException("Design assumption violated: a ViewHolder can only be bound to one item at a time.");
                }
                lValueOf = Long.valueOf(this.mItemIdToViewHolder.keyAt(i3));
            }
        }
        return lValueOf;
    }

    private static long parseIdFromKey(@NonNull String str, @NonNull String str2) {
        return Long.parseLong(str.substring(str2.length()));
    }

    private void removeFragment(long j2) {
        ViewParent parent;
        Fragment fragment = (Fragment) this.f6473c.get(j2);
        if (fragment == null) {
            return;
        }
        if (fragment.getView() != null && (parent = fragment.getView().getParent()) != null) {
            ((FrameLayout) parent).removeAllViews();
        }
        if (!containsItem(j2)) {
            this.mSavedStates.remove(j2);
        }
        if (!fragment.isAdded()) {
            this.f6473c.remove(j2);
            return;
        }
        if (d()) {
            this.mHasStaleFragments = true;
            return;
        }
        if (fragment.isAdded() && containsItem(j2)) {
            this.mSavedStates.put(j2, this.f6472b.saveFragmentInstanceState(fragment));
        }
        this.f6472b.beginTransaction().remove(fragment).commitNow();
        this.f6473c.remove(j2);
    }

    private void scheduleGracePeriodEnd() {
        final Handler handler = new Handler(Looper.getMainLooper());
        final Runnable runnable = new Runnable() { // from class: androidx.viewpager2.adapter.FragmentStateAdapter.4
            @Override // java.lang.Runnable
            public void run() {
                FragmentStateAdapter fragmentStateAdapter = FragmentStateAdapter.this;
                fragmentStateAdapter.f6474d = false;
                fragmentStateAdapter.b();
            }
        };
        this.f6471a.addObserver(new LifecycleEventObserver() { // from class: androidx.viewpager2.adapter.FragmentStateAdapter.5
            @Override // androidx.lifecycle.LifecycleEventObserver
            public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    handler.removeCallbacks(runnable);
                    lifecycleOwner.getLifecycle().removeObserver(this);
                }
            }
        });
        handler.postDelayed(runnable, 10000L);
    }

    private void scheduleViewAttach(final Fragment fragment, @NonNull final FrameLayout frameLayout) {
        this.f6472b.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() { // from class: androidx.viewpager2.adapter.FragmentStateAdapter.3
            @Override // androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
            public void onFragmentViewCreated(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment2, @NonNull View view, @Nullable Bundle bundle) {
                if (fragment2 == fragment) {
                    fragmentManager.unregisterFragmentLifecycleCallbacks(this);
                    FragmentStateAdapter.this.a(view, frameLayout);
                }
            }
        }, false);
    }

    void a(View view, FrameLayout frameLayout) {
        if (frameLayout.getChildCount() > 1) {
            throw new IllegalStateException("Design assumption violated.");
        }
        if (view.getParent() == frameLayout) {
            return;
        }
        if (frameLayout.getChildCount() > 0) {
            frameLayout.removeAllViews();
        }
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        frameLayout.addView(view);
    }

    void b() {
        if (!this.mHasStaleFragments || d()) {
            return;
        }
        ArraySet arraySet = new ArraySet();
        for (int i2 = 0; i2 < this.f6473c.size(); i2++) {
            long jKeyAt = this.f6473c.keyAt(i2);
            if (!containsItem(jKeyAt)) {
                arraySet.add(Long.valueOf(jKeyAt));
                this.mItemIdToViewHolder.remove(jKeyAt);
            }
        }
        if (!this.f6474d) {
            this.mHasStaleFragments = false;
            for (int i3 = 0; i3 < this.f6473c.size(); i3++) {
                long jKeyAt2 = this.f6473c.keyAt(i3);
                if (!isFragmentViewBound(jKeyAt2)) {
                    arraySet.add(Long.valueOf(jKeyAt2));
                }
            }
        }
        Iterator<E> it = arraySet.iterator();
        while (it.hasNext()) {
            removeFragment(((Long) it.next()).longValue());
        }
    }

    void c(final FragmentViewHolder fragmentViewHolder) {
        Fragment fragment = (Fragment) this.f6473c.get(fragmentViewHolder.getItemId());
        if (fragment == null) {
            throw new IllegalStateException("Design assumption violated.");
        }
        FrameLayout frameLayoutB = fragmentViewHolder.b();
        View view = fragment.getView();
        if (!fragment.isAdded() && view != null) {
            throw new IllegalStateException("Design assumption violated.");
        }
        if (fragment.isAdded() && view == null) {
            scheduleViewAttach(fragment, frameLayoutB);
            return;
        }
        if (fragment.isAdded() && view.getParent() != null) {
            if (view.getParent() != frameLayoutB) {
                a(view, frameLayoutB);
                return;
            }
            return;
        }
        if (fragment.isAdded()) {
            a(view, frameLayoutB);
            return;
        }
        if (d()) {
            if (this.f6472b.isDestroyed()) {
                return;
            }
            this.f6471a.addObserver(new LifecycleEventObserver() { // from class: androidx.viewpager2.adapter.FragmentStateAdapter.2
                @Override // androidx.lifecycle.LifecycleEventObserver
                public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
                    if (FragmentStateAdapter.this.d()) {
                        return;
                    }
                    lifecycleOwner.getLifecycle().removeObserver(this);
                    if (ViewCompat.isAttachedToWindow(fragmentViewHolder.b())) {
                        FragmentStateAdapter.this.c(fragmentViewHolder);
                    }
                }
            });
            return;
        }
        scheduleViewAttach(fragment, frameLayoutB);
        this.f6472b.beginTransaction().add(fragment, "f" + fragmentViewHolder.getItemId()).setMaxLifecycle(fragment, Lifecycle.State.STARTED).commitNow();
        this.mFragmentMaxLifecycleEnforcer.c(false);
    }

    public boolean containsItem(long j2) {
        return j2 >= 0 && j2 < ((long) getItemCount());
    }

    @NonNull
    public abstract Fragment createFragment(int i2);

    boolean d() {
        return this.f6472b.isStateSaved();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i2) {
        return i2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @CallSuper
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        Preconditions.checkArgument(this.mFragmentMaxLifecycleEnforcer == null);
        FragmentMaxLifecycleEnforcer fragmentMaxLifecycleEnforcer = new FragmentMaxLifecycleEnforcer();
        this.mFragmentMaxLifecycleEnforcer = fragmentMaxLifecycleEnforcer;
        fragmentMaxLifecycleEnforcer.a(recyclerView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @CallSuper
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        this.mFragmentMaxLifecycleEnforcer.b(recyclerView);
        this.mFragmentMaxLifecycleEnforcer = null;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final boolean onFailedToRecycleView(@NonNull FragmentViewHolder fragmentViewHolder) {
        return true;
    }

    @Override // androidx.viewpager2.adapter.StatefulAdapter
    public final void restoreState(@NonNull Parcelable parcelable) {
        if (!this.mSavedStates.isEmpty() || !this.f6473c.isEmpty()) {
            throw new IllegalStateException("Expected the adapter to be 'fresh' while restoring state.");
        }
        Bundle bundle = (Bundle) parcelable;
        if (bundle.getClassLoader() == null) {
            bundle.setClassLoader(getClass().getClassLoader());
        }
        for (String str : bundle.keySet()) {
            if (isValidKey(str, KEY_PREFIX_FRAGMENT)) {
                this.f6473c.put(parseIdFromKey(str, KEY_PREFIX_FRAGMENT), this.f6472b.getFragment(bundle, str));
            } else {
                if (!isValidKey(str, KEY_PREFIX_STATE)) {
                    throw new IllegalArgumentException("Unexpected key in savedState: " + str);
                }
                long idFromKey = parseIdFromKey(str, KEY_PREFIX_STATE);
                Fragment.SavedState savedState = (Fragment.SavedState) bundle.getParcelable(str);
                if (containsItem(idFromKey)) {
                    this.mSavedStates.put(idFromKey, savedState);
                }
            }
        }
        if (this.f6473c.isEmpty()) {
            return;
        }
        this.mHasStaleFragments = true;
        this.f6474d = true;
        b();
        scheduleGracePeriodEnd();
    }

    @Override // androidx.viewpager2.adapter.StatefulAdapter
    @NonNull
    public final Parcelable saveState() {
        Bundle bundle = new Bundle(this.f6473c.size() + this.mSavedStates.size());
        for (int i2 = 0; i2 < this.f6473c.size(); i2++) {
            long jKeyAt = this.f6473c.keyAt(i2);
            Fragment fragment = (Fragment) this.f6473c.get(jKeyAt);
            if (fragment != null && fragment.isAdded()) {
                this.f6472b.putFragment(bundle, createKey(KEY_PREFIX_FRAGMENT, jKeyAt), fragment);
            }
        }
        for (int i3 = 0; i3 < this.mSavedStates.size(); i3++) {
            long jKeyAt2 = this.mSavedStates.keyAt(i3);
            if (containsItem(jKeyAt2)) {
                bundle.putParcelable(createKey(KEY_PREFIX_STATE, jKeyAt2), this.mSavedStates.get(jKeyAt2));
            }
        }
        return bundle;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void setHasStableIds(boolean z2) {
        throw new UnsupportedOperationException("Stable Ids are required for the adapter to function properly, and the adapter takes care of setting the flag.");
    }

    public FragmentStateAdapter(@NonNull Fragment fragment) {
        this(fragment.getChildFragmentManager(), fragment.getLifecycle());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(@NonNull final FragmentViewHolder fragmentViewHolder, int i2) {
        long itemId = fragmentViewHolder.getItemId();
        int id = fragmentViewHolder.b().getId();
        Long lItemForViewHolder = itemForViewHolder(id);
        if (lItemForViewHolder != null && lItemForViewHolder.longValue() != itemId) {
            removeFragment(lItemForViewHolder.longValue());
            this.mItemIdToViewHolder.remove(lItemForViewHolder.longValue());
        }
        this.mItemIdToViewHolder.put(itemId, Integer.valueOf(id));
        ensureFragment(i2);
        final FrameLayout frameLayoutB = fragmentViewHolder.b();
        if (ViewCompat.isAttachedToWindow(frameLayoutB)) {
            if (frameLayoutB.getParent() != null) {
                throw new IllegalStateException("Design assumption violated.");
            }
            frameLayoutB.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: androidx.viewpager2.adapter.FragmentStateAdapter.1
                @Override // android.view.View.OnLayoutChangeListener
                public void onLayoutChange(View view, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
                    if (frameLayoutB.getParent() != null) {
                        frameLayoutB.removeOnLayoutChangeListener(this);
                        FragmentStateAdapter.this.c(fragmentViewHolder);
                    }
                }
            });
        }
        b();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public final FragmentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i2) {
        return FragmentViewHolder.a(viewGroup);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onViewAttachedToWindow(@NonNull FragmentViewHolder fragmentViewHolder) {
        c(fragmentViewHolder);
        b();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onViewRecycled(@NonNull FragmentViewHolder fragmentViewHolder) {
        Long lItemForViewHolder = itemForViewHolder(fragmentViewHolder.b().getId());
        if (lItemForViewHolder != null) {
            removeFragment(lItemForViewHolder.longValue());
            this.mItemIdToViewHolder.remove(lItemForViewHolder.longValue());
        }
    }

    public FragmentStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        this.f6473c = new LongSparseArray();
        this.mSavedStates = new LongSparseArray<>();
        this.mItemIdToViewHolder = new LongSparseArray<>();
        this.f6474d = false;
        this.mHasStaleFragments = false;
        this.f6472b = fragmentManager;
        this.f6471a = lifecycle;
        super.setHasStableIds(true);
    }
}
