package androidx.media3.exoplayer.image;

import android.graphics.Bitmap;
import androidx.media3.common.util.UnstableApi;

@UnstableApi
/* loaded from: classes.dex */
public interface ImageOutput {
    public static final ImageOutput NO_OP = new ImageOutput() { // from class: androidx.media3.exoplayer.image.ImageOutput.1
        @Override // androidx.media3.exoplayer.image.ImageOutput
        public void onDisabled() {
        }

        @Override // androidx.media3.exoplayer.image.ImageOutput
        public void onImageAvailable(long j2, Bitmap bitmap) {
        }
    };

    void onDisabled();

    void onImageAvailable(long j2, Bitmap bitmap);
}
