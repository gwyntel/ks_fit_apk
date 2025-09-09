package androidx.media3.exoplayer.upstream;

import androidx.media3.common.C;
import androidx.media3.exoplayer.upstream.CmcdConfiguration;
import com.google.common.collect.ImmutableListMultimap;

/* loaded from: classes2.dex */
public abstract /* synthetic */ class e {
    public static ImmutableListMultimap a(CmcdConfiguration.RequestConfig requestConfig) {
        return ImmutableListMultimap.of();
    }

    public static int b(CmcdConfiguration.RequestConfig requestConfig, int i2) {
        return C.RATE_UNSET_INT;
    }

    public static boolean c(CmcdConfiguration.RequestConfig requestConfig, String str) {
        return true;
    }
}
