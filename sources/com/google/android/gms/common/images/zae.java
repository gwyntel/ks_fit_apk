package com.google.android.gms.common.images;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.internal.base.zak;
import com.google.android.gms.internal.base.zal;
import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
public final class zae extends zag {
    private final WeakReference zac;

    public zae(ImageView imageView, int i2) {
        super(Uri.EMPTY, i2);
        Asserts.checkNotNull(imageView);
        this.zac = new WeakReference(imageView);
    }

    @Override // com.google.android.gms.common.images.zag
    protected final void a(Drawable drawable, boolean z2, boolean z3, boolean z4) {
        ImageView imageView = (ImageView) this.zac.get();
        if (imageView != null) {
            if (!z3 && !z4 && (imageView instanceof zal)) {
                throw null;
            }
            boolean z5 = false;
            if (!z3 && !z2) {
                z5 = true;
            }
            if (z5) {
                Drawable drawable2 = imageView.getDrawable();
                if (drawable2 == null) {
                    drawable2 = null;
                } else if (drawable2 instanceof zak) {
                    drawable2 = ((zak) drawable2).zaa();
                }
                drawable = new zak(drawable2, drawable);
            }
            imageView.setImageDrawable(drawable);
            if (imageView instanceof zal) {
                throw null;
            }
            if (drawable == null || !z5) {
                return;
            }
            ((zak) drawable).zab(250);
        }
    }

    public final boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zae)) {
            return false;
        }
        ImageView imageView = (ImageView) this.zac.get();
        ImageView imageView2 = (ImageView) ((zae) obj).zac.get();
        return (imageView2 == null || imageView == null || !Objects.equal(imageView2, imageView)) ? false : true;
    }

    public final int hashCode() {
        return 0;
    }

    public zae(ImageView imageView, Uri uri) {
        super(uri, 0);
        Asserts.checkNotNull(imageView);
        this.zac = new WeakReference(imageView);
    }
}
