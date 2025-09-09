package androidx.media3.exoplayer.mediacodec;

import android.content.Context;
import androidx.media3.exoplayer.mediacodec.MediaCodecAdapter;

/* loaded from: classes.dex */
public abstract /* synthetic */ class h {
    static {
        MediaCodecAdapter.Factory factory = MediaCodecAdapter.Factory.DEFAULT;
    }

    public static MediaCodecAdapter.Factory a(Context context) {
        return new DefaultMediaCodecAdapterFactory(context);
    }
}
