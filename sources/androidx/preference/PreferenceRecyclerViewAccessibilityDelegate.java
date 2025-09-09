package androidx.preference;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
@Deprecated
/* loaded from: classes2.dex */
public class PreferenceRecyclerViewAccessibilityDelegate extends RecyclerViewAccessibilityDelegate {

    /* renamed from: b, reason: collision with root package name */
    final RecyclerView f5690b;

    /* renamed from: c, reason: collision with root package name */
    final AccessibilityDelegateCompat f5691c;

    /* renamed from: d, reason: collision with root package name */
    final AccessibilityDelegateCompat f5692d;

    public PreferenceRecyclerViewAccessibilityDelegate(@NonNull RecyclerView recyclerView) {
        super(recyclerView);
        this.f5691c = super.getItemDelegate();
        this.f5692d = new AccessibilityDelegateCompat() { // from class: androidx.preference.PreferenceRecyclerViewAccessibilityDelegate.1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                Preference item;
                PreferenceRecyclerViewAccessibilityDelegate.this.f5691c.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                int childAdapterPosition = PreferenceRecyclerViewAccessibilityDelegate.this.f5690b.getChildAdapterPosition(view);
                RecyclerView.Adapter adapter = PreferenceRecyclerViewAccessibilityDelegate.this.f5690b.getAdapter();
                if ((adapter instanceof PreferenceGroupAdapter) && (item = ((PreferenceGroupAdapter) adapter).getItem(childAdapterPosition)) != null) {
                    item.onInitializeAccessibilityNodeInfo(accessibilityNodeInfoCompat);
                }
            }

            @Override // androidx.core.view.AccessibilityDelegateCompat
            public boolean performAccessibilityAction(View view, int i2, Bundle bundle) {
                return PreferenceRecyclerViewAccessibilityDelegate.this.f5691c.performAccessibilityAction(view, i2, bundle);
            }
        };
        this.f5690b = recyclerView;
    }

    @Override // androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate
    @NonNull
    public AccessibilityDelegateCompat getItemDelegate() {
        return this.f5692d;
    }
}
