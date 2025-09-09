package androidx.preference;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.PreferenceManager;

/* loaded from: classes2.dex */
public final class PreferenceScreen extends PreferenceGroup {
    private boolean mShouldUseGeneratedIds;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public PreferenceScreen(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet, TypedArrayUtils.getAttr(context, R.attr.preferenceScreenStyle, android.R.attr.preferenceScreenStyle));
        this.mShouldUseGeneratedIds = true;
    }

    @Override // androidx.preference.PreferenceGroup
    protected boolean D() {
        return false;
    }

    @Override // androidx.preference.Preference
    protected void o() {
        PreferenceManager.OnNavigateToScreenListener onNavigateToScreenListener;
        if (getIntent() != null || getFragment() != null || getPreferenceCount() == 0 || (onNavigateToScreenListener = getPreferenceManager().getOnNavigateToScreenListener()) == null) {
            return;
        }
        onNavigateToScreenListener.onNavigateToScreen(this);
    }

    public void setShouldUseGeneratedIds(boolean z2) {
        if (isAttached()) {
            throw new IllegalStateException("Cannot change the usage of generated IDs while attached to the preference hierarchy");
        }
        this.mShouldUseGeneratedIds = z2;
    }

    public boolean shouldUseGeneratedIds() {
        return this.mShouldUseGeneratedIds;
    }
}
