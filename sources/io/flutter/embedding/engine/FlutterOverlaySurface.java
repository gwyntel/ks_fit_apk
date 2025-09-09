package io.flutter.embedding.engine;

import android.view.Surface;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;

@Keep
/* loaded from: classes4.dex */
public class FlutterOverlaySurface {
    private final int id;

    @NonNull
    private final Surface surface;

    public FlutterOverlaySurface(int i2, @NonNull Surface surface) {
        this.id = i2;
        this.surface = surface;
    }

    public int getId() {
        return this.id;
    }

    public Surface getSurface() {
        return this.surface;
    }
}
