package androidx.media3.exoplayer;

import androidx.media3.exoplayer.RendererCapabilities;

/* loaded from: classes.dex */
public abstract /* synthetic */ class v2 {
    public static int c(int i2) {
        return e(i2, 0, 0, 0);
    }

    public static int d(int i2, int i3, int i4) {
        return g(i2, i3, i4, 0, 128, 0);
    }

    public static int e(int i2, int i3, int i4, int i5) {
        return g(i2, i3, i4, 0, 128, i5);
    }

    public static int f(int i2, int i3, int i4, int i5, int i6) {
        return g(i2, i3, i4, i5, i6, 0);
    }

    public static int g(int i2, int i3, int i4, int i5, int i6, int i7) {
        return i2 | i3 | i4 | i5 | i6 | i7;
    }

    public static int h(int i2) {
        return i2 & 24;
    }

    public static int i(int i2) {
        return i2 & RendererCapabilities.AUDIO_OFFLOAD_SUPPORT_MASK;
    }

    public static int j(int i2) {
        return i2 & RendererCapabilities.DECODER_SUPPORT_MASK;
    }

    public static int k(int i2) {
        return i2 & 7;
    }

    public static int l(int i2) {
        return i2 & 64;
    }

    public static int m(int i2) {
        return i2 & 32;
    }

    public static boolean n(int i2, boolean z2) {
        int iK = k(i2);
        return iK == 4 || (z2 && iK == 3);
    }

    public static void a(RendererCapabilities rendererCapabilities) {
    }

    public static void b(RendererCapabilities rendererCapabilities, RendererCapabilities.Listener listener) {
    }
}
