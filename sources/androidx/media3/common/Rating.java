package androidx.media3.common;

import android.os.Bundle;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;

/* loaded from: classes.dex */
public abstract class Rating {

    /* renamed from: a, reason: collision with root package name */
    static final String f4751a = Util.intToStringMaxRadix(0);

    Rating() {
    }

    @UnstableApi
    public static Rating fromBundle(Bundle bundle) {
        int i2 = bundle.getInt(f4751a, -1);
        if (i2 == 0) {
            return HeartRating.fromBundle(bundle);
        }
        if (i2 == 1) {
            return PercentageRating.fromBundle(bundle);
        }
        if (i2 == 2) {
            return StarRating.fromBundle(bundle);
        }
        if (i2 == 3) {
            return ThumbRating.fromBundle(bundle);
        }
        throw new IllegalArgumentException("Unknown RatingType: " + i2);
    }

    public abstract boolean isRated();

    @UnstableApi
    public abstract Bundle toBundle();
}
