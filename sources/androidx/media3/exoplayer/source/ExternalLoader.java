package androidx.media3.exoplayer.source;

import android.net.Uri;
import androidx.media3.common.util.UnstableApi;
import com.google.common.util.concurrent.ListenableFuture;

@UnstableApi
/* loaded from: classes2.dex */
public interface ExternalLoader {

    public static final class LoadRequest {
        public final Uri uri;

        public LoadRequest(Uri uri) {
            this.uri = uri;
        }
    }

    ListenableFuture<?> load(LoadRequest loadRequest);
}
