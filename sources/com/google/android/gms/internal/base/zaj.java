package com.google.android.gms.internal.base;

import android.graphics.drawable.Drawable;

/* loaded from: classes3.dex */
final class zaj extends Drawable.ConstantState {

    /* renamed from: a, reason: collision with root package name */
    int f13034a;

    /* renamed from: b, reason: collision with root package name */
    int f13035b;

    zaj(zaj zajVar) {
        if (zajVar != null) {
            this.f13034a = zajVar.f13034a;
            this.f13035b = zajVar.f13035b;
        }
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public final int getChangingConfigurations() {
        return this.f13034a;
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public final Drawable newDrawable() {
        return new zak(this);
    }
}
