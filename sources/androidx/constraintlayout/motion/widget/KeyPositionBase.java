package androidx.constraintlayout.motion.widget;

import android.graphics.RectF;
import android.view.View;
import java.util.HashSet;

/* loaded from: classes.dex */
abstract class KeyPositionBase extends Key {

    /* renamed from: f, reason: collision with root package name */
    int f3013f = Key.UNSET;

    KeyPositionBase() {
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    void getAttributeNames(HashSet hashSet) {
    }

    public abstract boolean intersects(int i2, int i3, RectF rectF, RectF rectF2, float f2, float f3);

    abstract void positionAttributes(View view, RectF rectF, RectF rectF2, float f2, float f3, String[] strArr, float[] fArr);
}
