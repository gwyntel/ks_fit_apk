package androidx.preference;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.ViewCompat;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes2.dex */
public class PreferenceGroupAdapter extends RecyclerView.Adapter<PreferenceViewHolder> implements Preference.OnPreferenceChangeInternalListener, PreferenceGroup.PreferencePositionCallback {
    private final PreferenceGroup mPreferenceGroup;
    private final List<PreferenceResourceDescriptor> mPreferenceResourceDescriptors;
    private List<Preference> mPreferences;
    private List<Preference> mVisiblePreferences;
    private final Runnable mSyncRunnable = new Runnable() { // from class: androidx.preference.PreferenceGroupAdapter.1
        @Override // java.lang.Runnable
        public void run() {
            PreferenceGroupAdapter.this.a();
        }
    };
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    private static class PreferenceResourceDescriptor {

        /* renamed from: a, reason: collision with root package name */
        int f5686a;

        /* renamed from: b, reason: collision with root package name */
        int f5687b;

        /* renamed from: c, reason: collision with root package name */
        String f5688c;

        PreferenceResourceDescriptor(Preference preference) {
            this.f5688c = preference.getClass().getName();
            this.f5686a = preference.getLayoutResource();
            this.f5687b = preference.getWidgetLayoutResource();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof PreferenceResourceDescriptor)) {
                return false;
            }
            PreferenceResourceDescriptor preferenceResourceDescriptor = (PreferenceResourceDescriptor) obj;
            return this.f5686a == preferenceResourceDescriptor.f5686a && this.f5687b == preferenceResourceDescriptor.f5687b && TextUtils.equals(this.f5688c, preferenceResourceDescriptor.f5688c);
        }

        public int hashCode() {
            return ((((527 + this.f5686a) * 31) + this.f5687b) * 31) + this.f5688c.hashCode();
        }
    }

    public PreferenceGroupAdapter(@NonNull PreferenceGroup preferenceGroup) {
        this.mPreferenceGroup = preferenceGroup;
        preferenceGroup.A(this);
        this.mPreferences = new ArrayList();
        this.mVisiblePreferences = new ArrayList();
        this.mPreferenceResourceDescriptors = new ArrayList();
        if (preferenceGroup instanceof PreferenceScreen) {
            setHasStableIds(((PreferenceScreen) preferenceGroup).shouldUseGeneratedIds());
        } else {
            setHasStableIds(true);
        }
        a();
    }

    private ExpandButton createExpandButton(final PreferenceGroup preferenceGroup, List<Preference> list) {
        ExpandButton expandButton = new ExpandButton(preferenceGroup.getContext(), list, preferenceGroup.g());
        expandButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() { // from class: androidx.preference.PreferenceGroupAdapter.3
            @Override // androidx.preference.Preference.OnPreferenceClickListener
            public boolean onPreferenceClick(@NonNull Preference preference) {
                preferenceGroup.setInitialExpandedChildrenCount(Integer.MAX_VALUE);
                PreferenceGroupAdapter.this.onPreferenceHierarchyChange(preference);
                PreferenceGroup.OnExpandButtonClickListener onExpandButtonClickListener = preferenceGroup.getOnExpandButtonClickListener();
                if (onExpandButtonClickListener == null) {
                    return true;
                }
                onExpandButtonClickListener.onExpandButtonClick();
                return true;
            }
        });
        return expandButton;
    }

    private List<Preference> createVisiblePreferencesList(PreferenceGroup preferenceGroup) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int preferenceCount = preferenceGroup.getPreferenceCount();
        int i2 = 0;
        for (int i3 = 0; i3 < preferenceCount; i3++) {
            Preference preference = preferenceGroup.getPreference(i3);
            if (preference.isVisible()) {
                if (!isGroupExpandable(preferenceGroup) || i2 < preferenceGroup.getInitialExpandedChildrenCount()) {
                    arrayList.add(preference);
                } else {
                    arrayList2.add(preference);
                }
                if (preference instanceof PreferenceGroup) {
                    PreferenceGroup preferenceGroup2 = (PreferenceGroup) preference;
                    if (!preferenceGroup2.D()) {
                        continue;
                    } else {
                        if (isGroupExpandable(preferenceGroup) && isGroupExpandable(preferenceGroup2)) {
                            throw new IllegalStateException("Nesting an expandable group inside of another expandable group is not supported!");
                        }
                        for (Preference preference2 : createVisiblePreferencesList(preferenceGroup2)) {
                            if (!isGroupExpandable(preferenceGroup) || i2 < preferenceGroup.getInitialExpandedChildrenCount()) {
                                arrayList.add(preference2);
                            } else {
                                arrayList2.add(preference2);
                            }
                            i2++;
                        }
                    }
                } else {
                    i2++;
                }
            }
        }
        if (isGroupExpandable(preferenceGroup) && i2 > preferenceGroup.getInitialExpandedChildrenCount()) {
            arrayList.add(createExpandButton(preferenceGroup, arrayList2));
        }
        return arrayList;
    }

    private void flattenPreferenceGroup(List<Preference> list, PreferenceGroup preferenceGroup) {
        preferenceGroup.F();
        int preferenceCount = preferenceGroup.getPreferenceCount();
        for (int i2 = 0; i2 < preferenceCount; i2++) {
            Preference preference = preferenceGroup.getPreference(i2);
            list.add(preference);
            PreferenceResourceDescriptor preferenceResourceDescriptor = new PreferenceResourceDescriptor(preference);
            if (!this.mPreferenceResourceDescriptors.contains(preferenceResourceDescriptor)) {
                this.mPreferenceResourceDescriptors.add(preferenceResourceDescriptor);
            }
            if (preference instanceof PreferenceGroup) {
                PreferenceGroup preferenceGroup2 = (PreferenceGroup) preference;
                if (preferenceGroup2.D()) {
                    flattenPreferenceGroup(list, preferenceGroup2);
                }
            }
            preference.A(this);
        }
    }

    private boolean isGroupExpandable(PreferenceGroup preferenceGroup) {
        return preferenceGroup.getInitialExpandedChildrenCount() != Integer.MAX_VALUE;
    }

    void a() {
        Iterator<Preference> it = this.mPreferences.iterator();
        while (it.hasNext()) {
            it.next().A(null);
        }
        ArrayList arrayList = new ArrayList(this.mPreferences.size());
        this.mPreferences = arrayList;
        flattenPreferenceGroup(arrayList, this.mPreferenceGroup);
        final List<Preference> list = this.mVisiblePreferences;
        final List<Preference> listCreateVisiblePreferencesList = createVisiblePreferencesList(this.mPreferenceGroup);
        this.mVisiblePreferences = listCreateVisiblePreferencesList;
        PreferenceManager preferenceManager = this.mPreferenceGroup.getPreferenceManager();
        if (preferenceManager == null || preferenceManager.getPreferenceComparisonCallback() == null) {
            notifyDataSetChanged();
        } else {
            final PreferenceManager.PreferenceComparisonCallback preferenceComparisonCallback = preferenceManager.getPreferenceComparisonCallback();
            DiffUtil.calculateDiff(new DiffUtil.Callback() { // from class: androidx.preference.PreferenceGroupAdapter.2
                @Override // androidx.recyclerview.widget.DiffUtil.Callback
                public boolean areContentsTheSame(int i2, int i3) {
                    return preferenceComparisonCallback.arePreferenceContentsTheSame((Preference) list.get(i2), (Preference) listCreateVisiblePreferencesList.get(i3));
                }

                @Override // androidx.recyclerview.widget.DiffUtil.Callback
                public boolean areItemsTheSame(int i2, int i3) {
                    return preferenceComparisonCallback.arePreferenceItemsTheSame((Preference) list.get(i2), (Preference) listCreateVisiblePreferencesList.get(i3));
                }

                @Override // androidx.recyclerview.widget.DiffUtil.Callback
                public int getNewListSize() {
                    return listCreateVisiblePreferencesList.size();
                }

                @Override // androidx.recyclerview.widget.DiffUtil.Callback
                public int getOldListSize() {
                    return list.size();
                }
            }).dispatchUpdatesTo(this);
        }
        Iterator<Preference> it2 = this.mPreferences.iterator();
        while (it2.hasNext()) {
            it2.next().b();
        }
    }

    @Nullable
    public Preference getItem(int i2) {
        if (i2 < 0 || i2 >= getItemCount()) {
            return null;
        }
        return this.mVisiblePreferences.get(i2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mVisiblePreferences.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i2) {
        if (hasStableIds()) {
            return getItem(i2).g();
        }
        return -1L;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i2) {
        PreferenceResourceDescriptor preferenceResourceDescriptor = new PreferenceResourceDescriptor(getItem(i2));
        int iIndexOf = this.mPreferenceResourceDescriptors.indexOf(preferenceResourceDescriptor);
        if (iIndexOf != -1) {
            return iIndexOf;
        }
        int size = this.mPreferenceResourceDescriptors.size();
        this.mPreferenceResourceDescriptors.add(preferenceResourceDescriptor);
        return size;
    }

    @Override // androidx.preference.PreferenceGroup.PreferencePositionCallback
    public int getPreferenceAdapterPosition(@NonNull String str) {
        int size = this.mVisiblePreferences.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (TextUtils.equals(str, this.mVisiblePreferences.get(i2).getKey())) {
                return i2;
            }
        }
        return -1;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeInternalListener
    public void onPreferenceChange(@NonNull Preference preference) {
        int iIndexOf = this.mVisiblePreferences.indexOf(preference);
        if (iIndexOf != -1) {
            notifyItemChanged(iIndexOf, preference);
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeInternalListener
    public void onPreferenceHierarchyChange(@NonNull Preference preference) {
        this.mHandler.removeCallbacks(this.mSyncRunnable);
        this.mHandler.post(this.mSyncRunnable);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeInternalListener
    public void onPreferenceVisibilityChange(@NonNull Preference preference) {
        onPreferenceHierarchyChange(preference);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull PreferenceViewHolder preferenceViewHolder, int i2) {
        Preference item = getItem(i2);
        preferenceViewHolder.a();
        item.onBindViewHolder(preferenceViewHolder);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public PreferenceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i2) {
        PreferenceResourceDescriptor preferenceResourceDescriptor = this.mPreferenceResourceDescriptors.get(i2);
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(viewGroup.getContext());
        TypedArray typedArrayObtainStyledAttributes = viewGroup.getContext().obtainStyledAttributes((AttributeSet) null, R.styleable.BackgroundStyle);
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.BackgroundStyle_android_selectableItemBackground);
        if (drawable == null) {
            drawable = AppCompatResources.getDrawable(viewGroup.getContext(), android.R.drawable.list_selector_background);
        }
        typedArrayObtainStyledAttributes.recycle();
        View viewInflate = layoutInflaterFrom.inflate(preferenceResourceDescriptor.f5686a, viewGroup, false);
        if (viewInflate.getBackground() == null) {
            ViewCompat.setBackground(viewInflate, drawable);
        }
        ViewGroup viewGroup2 = (ViewGroup) viewInflate.findViewById(android.R.id.widget_frame);
        if (viewGroup2 != null) {
            int i3 = preferenceResourceDescriptor.f5687b;
            if (i3 != 0) {
                layoutInflaterFrom.inflate(i3, viewGroup2);
            } else {
                viewGroup2.setVisibility(8);
            }
        }
        return new PreferenceViewHolder(viewInflate);
    }

    @Override // androidx.preference.PreferenceGroup.PreferencePositionCallback
    public int getPreferenceAdapterPosition(@NonNull Preference preference) {
        int size = this.mVisiblePreferences.size();
        for (int i2 = 0; i2 < size; i2++) {
            Preference preference2 = this.mVisiblePreferences.get(i2);
            if (preference2 != null && preference2.equals(preference)) {
                return i2;
            }
        }
        return -1;
    }
}
