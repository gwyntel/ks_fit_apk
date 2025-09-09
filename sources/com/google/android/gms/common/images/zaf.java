package com.google.android.gms.common.images;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.gms.common.images.ImageManager;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.internal.Objects;
import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
public final class zaf extends zag {
    private final WeakReference zac;

    public zaf(ImageManager.OnImageLoadedListener onImageLoadedListener, Uri uri) {
        super(uri, 0);
        Asserts.checkNotNull(onImageLoadedListener);
        this.zac = new WeakReference(onImageLoadedListener);
    }

    @Override // com.google.android.gms.common.images.zag
    protected final void a(Drawable drawable, boolean z2, boolean z3, boolean z4) {
        ImageManager.OnImageLoadedListener onImageLoadedListener;
        if (z3 || (onImageLoadedListener = (ImageManager.OnImageLoadedListener) this.zac.get()) == null) {
            return;
        }
        onImageLoadedListener.onImageLoaded(this.f12806a.zaa, drawable, z4);
    }

    public final boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zaf)) {
            return false;
        }
        zaf zafVar = (zaf) obj;
        ImageManager.OnImageLoadedListener onImageLoadedListener = (ImageManager.OnImageLoadedListener) this.zac.get();
        ImageManager.OnImageLoadedListener onImageLoadedListener2 = (ImageManager.OnImageLoadedListener) zafVar.zac.get();
        return onImageLoadedListener2 != null && onImageLoadedListener != null && Objects.equal(onImageLoadedListener2, onImageLoadedListener) && Objects.equal(zafVar.f12806a, this.f12806a);
    }

    public final int hashCode() {
        return Objects.hashCode(this.f12806a);
    }
}
