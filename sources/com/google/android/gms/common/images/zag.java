package com.google.android.gms.common.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.internal.base.zam;

/* loaded from: classes3.dex */
public abstract class zag {

    /* renamed from: a, reason: collision with root package name */
    final zad f12806a;

    /* renamed from: b, reason: collision with root package name */
    protected int f12807b;

    public zag(Uri uri, int i2) {
        this.f12807b = 0;
        this.f12806a = new zad(uri);
        this.f12807b = i2;
    }

    protected abstract void a(Drawable drawable, boolean z2, boolean z3, boolean z4);

    final void b(Context context, zam zamVar, boolean z2) {
        int i2 = this.f12807b;
        a(i2 != 0 ? context.getResources().getDrawable(i2) : null, z2, false, false);
    }

    final void c(Context context, Bitmap bitmap, boolean z2) {
        Asserts.checkNotNull(bitmap);
        a(new BitmapDrawable(context.getResources(), bitmap), false, false, true);
    }
}
