package androidx.heifwriter;

import android.media.MediaMuxer;
import java.io.FileDescriptor;

/* loaded from: classes.dex */
public abstract /* synthetic */ class b {
    public static /* synthetic */ MediaMuxer a(FileDescriptor fileDescriptor, int i2) {
        return new MediaMuxer(fileDescriptor, i2);
    }
}
