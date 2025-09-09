package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes.dex */
final class WrappedDrawableState extends Drawable.ConstantState {

    /* renamed from: a, reason: collision with root package name */
    int f3581a;

    /* renamed from: b, reason: collision with root package name */
    Drawable.ConstantState f3582b;

    /* renamed from: c, reason: collision with root package name */
    ColorStateList f3583c;

    /* renamed from: d, reason: collision with root package name */
    PorterDuff.Mode f3584d;

    WrappedDrawableState(WrappedDrawableState wrappedDrawableState) {
        this.f3583c = null;
        this.f3584d = WrappedDrawableApi14.f3578c;
        if (wrappedDrawableState != null) {
            this.f3581a = wrappedDrawableState.f3581a;
            this.f3582b = wrappedDrawableState.f3582b;
            this.f3583c = wrappedDrawableState.f3583c;
            this.f3584d = wrappedDrawableState.f3584d;
        }
    }

    boolean a() {
        return this.f3582b != null;
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public int getChangingConfigurations() {
        int i2 = this.f3581a;
        Drawable.ConstantState constantState = this.f3582b;
        return i2 | (constantState != null ? constantState.getChangingConfigurations() : 0);
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    @NonNull
    public Drawable newDrawable() {
        return newDrawable(null);
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    @NonNull
    public Drawable newDrawable(@Nullable Resources resources) {
        return new WrappedDrawableApi21(this, resources);
    }
}
