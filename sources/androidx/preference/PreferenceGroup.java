package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.collection.SimpleArrayMap;
import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.Preference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class PreferenceGroup extends Preference {
    private static final String TAG = "PreferenceGroup";

    /* renamed from: a, reason: collision with root package name */
    final SimpleArrayMap f5676a;
    private boolean mAttachedToHierarchy;
    private final Runnable mClearRecycleCacheRunnable;
    private int mCurrentPreferenceOrder;
    private final Handler mHandler;
    private int mInitialExpandedChildrenCount;
    private OnExpandButtonClickListener mOnExpandButtonClickListener;
    private boolean mOrderingAsAdded;
    private final List<Preference> mPreferences;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public interface OnExpandButtonClickListener {
        void onExpandButtonClick();
    }

    public interface PreferencePositionCallback {
        int getPreferenceAdapterPosition(@NonNull Preference preference);

        int getPreferenceAdapterPosition(@NonNull String str);
    }

    public PreferenceGroup(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
        this.f5676a = new SimpleArrayMap();
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mOrderingAsAdded = true;
        this.mCurrentPreferenceOrder = 0;
        this.mAttachedToHierarchy = false;
        this.mInitialExpandedChildrenCount = Integer.MAX_VALUE;
        this.mOnExpandButtonClickListener = null;
        this.mClearRecycleCacheRunnable = new Runnable() { // from class: androidx.preference.PreferenceGroup.1
            @Override // java.lang.Runnable
            public void run() {
                synchronized (this) {
                    PreferenceGroup.this.f5676a.clear();
                }
            }
        };
        this.mPreferences = new ArrayList();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.PreferenceGroup, i2, i3);
        int i4 = R.styleable.PreferenceGroup_orderingFromXml;
        this.mOrderingAsAdded = TypedArrayUtils.getBoolean(typedArrayObtainStyledAttributes, i4, i4, true);
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.PreferenceGroup_initialExpandedChildrenCount)) {
            int i5 = R.styleable.PreferenceGroup_initialExpandedChildrenCount;
            setInitialExpandedChildrenCount(TypedArrayUtils.getInt(typedArrayObtainStyledAttributes, i5, i5, Integer.MAX_VALUE));
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    private boolean removePreferenceInt(@NonNull Preference preference) {
        boolean zRemove;
        synchronized (this) {
            try {
                preference.q();
                if (preference.getParent() == this) {
                    preference.a(null);
                }
                zRemove = this.mPreferences.remove(preference);
                if (zRemove) {
                    String key = preference.getKey();
                    if (key != null) {
                        this.f5676a.put(key, Long.valueOf(preference.g()));
                        this.mHandler.removeCallbacks(this.mClearRecycleCacheRunnable);
                        this.mHandler.post(this.mClearRecycleCacheRunnable);
                    }
                    if (this.mAttachedToHierarchy) {
                        preference.onDetached();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return zRemove;
    }

    protected boolean D() {
        return true;
    }

    protected boolean E(Preference preference) {
        preference.onParentChanged(this, shouldDisableDependents());
        return true;
    }

    void F() {
        synchronized (this) {
            Collections.sort(this.mPreferences);
        }
    }

    public void addItemFromInflater(@NonNull Preference preference) {
        addPreference(preference);
    }

    public boolean addPreference(@NonNull Preference preference) {
        long jB;
        if (this.mPreferences.contains(preference)) {
            return true;
        }
        if (preference.getKey() != null) {
            PreferenceGroup parent = this;
            while (parent.getParent() != null) {
                parent = parent.getParent();
            }
            String key = preference.getKey();
            if (parent.findPreference(key) != null) {
                Log.e(TAG, "Found duplicated key: \"" + key + "\". This can cause unintended behaviour, please use unique keys for every preference.");
            }
        }
        if (preference.getOrder() == Integer.MAX_VALUE) {
            if (this.mOrderingAsAdded) {
                int i2 = this.mCurrentPreferenceOrder;
                this.mCurrentPreferenceOrder = i2 + 1;
                preference.setOrder(i2);
            }
            if (preference instanceof PreferenceGroup) {
                ((PreferenceGroup) preference).setOrderingAsAdded(this.mOrderingAsAdded);
            }
        }
        int iBinarySearch = Collections.binarySearch(this.mPreferences, preference);
        if (iBinarySearch < 0) {
            iBinarySearch = (iBinarySearch * (-1)) - 1;
        }
        if (!E(preference)) {
            return false;
        }
        synchronized (this) {
            this.mPreferences.add(iBinarySearch, preference);
        }
        PreferenceManager preferenceManager = getPreferenceManager();
        String key2 = preference.getKey();
        if (key2 == null || !this.f5676a.containsKey(key2)) {
            jB = preferenceManager.b();
        } else {
            jB = ((Long) this.f5676a.get(key2)).longValue();
            this.f5676a.remove(key2);
        }
        preference.n(preferenceManager, jB);
        preference.a(this);
        if (this.mAttachedToHierarchy) {
            preference.onAttached();
        }
        l();
        return true;
    }

    @Override // androidx.preference.Preference
    protected void c(Bundle bundle) {
        super.c(bundle);
        int preferenceCount = getPreferenceCount();
        for (int i2 = 0; i2 < preferenceCount; i2++) {
            getPreference(i2).c(bundle);
        }
    }

    @Override // androidx.preference.Preference
    protected void d(Bundle bundle) {
        super.d(bundle);
        int preferenceCount = getPreferenceCount();
        for (int i2 = 0; i2 < preferenceCount; i2++) {
            getPreference(i2).d(bundle);
        }
    }

    @Nullable
    public <T extends Preference> T findPreference(@NonNull CharSequence charSequence) {
        T t2;
        if (charSequence == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        if (TextUtils.equals(getKey(), charSequence)) {
            return this;
        }
        int preferenceCount = getPreferenceCount();
        for (int i2 = 0; i2 < preferenceCount; i2++) {
            PreferenceGroup preferenceGroup = (T) getPreference(i2);
            if (TextUtils.equals(preferenceGroup.getKey(), charSequence)) {
                return preferenceGroup;
            }
            if ((preferenceGroup instanceof PreferenceGroup) && (t2 = (T) preferenceGroup.findPreference(charSequence)) != null) {
                return t2;
            }
        }
        return null;
    }

    public int getInitialExpandedChildrenCount() {
        return this.mInitialExpandedChildrenCount;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public OnExpandButtonClickListener getOnExpandButtonClickListener() {
        return this.mOnExpandButtonClickListener;
    }

    @NonNull
    public Preference getPreference(int i2) {
        return this.mPreferences.get(i2);
    }

    public int getPreferenceCount() {
        return this.mPreferences.size();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public boolean isAttached() {
        return this.mAttachedToHierarchy;
    }

    public boolean isOrderingAsAdded() {
        return this.mOrderingAsAdded;
    }

    @Override // androidx.preference.Preference
    public void notifyDependencyChange(boolean z2) {
        super.notifyDependencyChange(z2);
        int preferenceCount = getPreferenceCount();
        for (int i2 = 0; i2 < preferenceCount; i2++) {
            getPreference(i2).onParentChanged(this, z2);
        }
    }

    @Override // androidx.preference.Preference
    public void onAttached() {
        super.onAttached();
        this.mAttachedToHierarchy = true;
        int preferenceCount = getPreferenceCount();
        for (int i2 = 0; i2 < preferenceCount; i2++) {
            getPreference(i2).onAttached();
        }
    }

    @Override // androidx.preference.Preference
    public void onDetached() {
        super.onDetached();
        this.mAttachedToHierarchy = false;
        int preferenceCount = getPreferenceCount();
        for (int i2 = 0; i2 < preferenceCount; i2++) {
            getPreference(i2).onDetached();
        }
    }

    @Override // androidx.preference.Preference
    protected void r(Parcelable parcelable) {
        if (parcelable == null || !parcelable.getClass().equals(SavedState.class)) {
            super.r(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        this.mInitialExpandedChildrenCount = savedState.f5678a;
        super.r(savedState.getSuperState());
    }

    public void removeAll() {
        synchronized (this) {
            try {
                List<Preference> list = this.mPreferences;
                for (int size = list.size() - 1; size >= 0; size--) {
                    removePreferenceInt(list.get(0));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        l();
    }

    public boolean removePreference(@NonNull Preference preference) {
        boolean zRemovePreferenceInt = removePreferenceInt(preference);
        l();
        return zRemovePreferenceInt;
    }

    public boolean removePreferenceRecursively(@NonNull CharSequence charSequence) {
        Preference preferenceFindPreference = findPreference(charSequence);
        if (preferenceFindPreference == null) {
            return false;
        }
        return preferenceFindPreference.getParent().removePreference(preferenceFindPreference);
    }

    @Override // androidx.preference.Preference
    protected Parcelable s() {
        return new SavedState(super.s(), this.mInitialExpandedChildrenCount);
    }

    public void setInitialExpandedChildrenCount(int i2) {
        if (i2 != Integer.MAX_VALUE && !hasKey()) {
            Log.e(TAG, getClass().getSimpleName() + " should have a key defined if it contains an expandable preference");
        }
        this.mInitialExpandedChildrenCount = i2;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setOnExpandButtonClickListener(@Nullable OnExpandButtonClickListener onExpandButtonClickListener) {
        this.mOnExpandButtonClickListener = onExpandButtonClickListener;
    }

    public void setOrderingAsAdded(boolean z2) {
        this.mOrderingAsAdded = z2;
    }

    static class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: androidx.preference.PreferenceGroup.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i2) {
                return new SavedState[i2];
            }
        };

        /* renamed from: a, reason: collision with root package name */
        int f5678a;

        SavedState(Parcel parcel) {
            super(parcel);
            this.f5678a = parcel.readInt();
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeInt(this.f5678a);
        }

        SavedState(Parcelable parcelable, int i2) {
            super(parcelable);
            this.f5678a = i2;
        }
    }

    public PreferenceGroup(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        this(context, attributeSet, i2, 0);
    }

    public PreferenceGroup(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }
}
