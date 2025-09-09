package androidx.transition;

import android.view.View;
import androidx.annotation.NonNull;

/* loaded from: classes2.dex */
class ViewGroupOverlayApi14 extends ViewOverlayApi14 implements ViewGroupOverlayImpl {
    @Override // androidx.transition.ViewGroupOverlayImpl
    public void add(@NonNull View view) {
        this.f6349a.add(view);
    }

    @Override // androidx.transition.ViewGroupOverlayImpl
    public void remove(@NonNull View view) {
        this.f6349a.remove(view);
    }
}
