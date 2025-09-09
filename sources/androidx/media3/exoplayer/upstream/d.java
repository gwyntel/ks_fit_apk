package androidx.media3.exoplayer.upstream;

import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.upstream.CmcdConfiguration;
import com.google.common.collect.ImmutableListMultimap;
import java.util.UUID;

/* loaded from: classes2.dex */
public abstract /* synthetic */ class d {
    static {
        CmcdConfiguration.Factory factory = CmcdConfiguration.Factory.DEFAULT;
    }

    public static /* synthetic */ CmcdConfiguration a(MediaItem mediaItem) {
        String string = UUID.randomUUID().toString();
        String str = mediaItem.mediaId;
        if (str == null) {
            str = "";
        }
        return new CmcdConfiguration(string, str, new CmcdConfiguration.RequestConfig() { // from class: androidx.media3.exoplayer.upstream.CmcdConfiguration.Factory.1
            @Override // androidx.media3.exoplayer.upstream.CmcdConfiguration.RequestConfig
            public /* synthetic */ ImmutableListMultimap getCustomData() {
                return e.a(this);
            }

            @Override // androidx.media3.exoplayer.upstream.CmcdConfiguration.RequestConfig
            public /* synthetic */ int getRequestedMaximumThroughputKbps(int i2) {
                return e.b(this, i2);
            }

            @Override // androidx.media3.exoplayer.upstream.CmcdConfiguration.RequestConfig
            public /* synthetic */ boolean isKeyAllowed(String str2) {
                return e.c(this, str2);
            }
        });
    }
}
