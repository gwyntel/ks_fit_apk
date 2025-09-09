package io.flutter.plugins.videoplayer;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.annotation.VisibleForTesting;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DefaultDataSource;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory;
import androidx.media3.exoplayer.source.MediaSource;
import io.flutter.plugins.videoplayer.VideoAsset;
import java.util.Map;

/* loaded from: classes4.dex */
final class HttpVideoAsset extends VideoAsset {
    private static final String DEFAULT_USER_AGENT = "ExoPlayer";
    private static final String HEADER_USER_AGENT = "User-Agent";

    @NonNull
    private final Map<String, String> httpHeaders;

    @NonNull
    private final VideoAsset.StreamingFormat streamingFormat;

    /* renamed from: io.flutter.plugins.videoplayer.HttpVideoAsset$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$flutter$plugins$videoplayer$VideoAsset$StreamingFormat;

        static {
            int[] iArr = new int[VideoAsset.StreamingFormat.values().length];
            $SwitchMap$io$flutter$plugins$videoplayer$VideoAsset$StreamingFormat = iArr;
            try {
                iArr[VideoAsset.StreamingFormat.SMOOTH.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$flutter$plugins$videoplayer$VideoAsset$StreamingFormat[VideoAsset.StreamingFormat.DYNAMIC_ADAPTIVE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$io$flutter$plugins$videoplayer$VideoAsset$StreamingFormat[VideoAsset.StreamingFormat.HTTP_LIVE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    HttpVideoAsset(@Nullable String str, @NonNull VideoAsset.StreamingFormat streamingFormat, @NonNull Map<String, String> map) {
        super(str);
        this.streamingFormat = streamingFormat;
        this.httpHeaders = map;
    }

    @OptIn(markerClass = {UnstableApi.class})
    private static void unstableUpdateDataSourceFactory(@NonNull DefaultHttpDataSource.Factory factory, @NonNull Map<String, String> map, @Nullable String str) {
        factory.setUserAgent(str).setAllowCrossProtocolRedirects(true);
        if (map.isEmpty()) {
            return;
        }
        factory.setDefaultRequestProperties(map);
    }

    @Override // io.flutter.plugins.videoplayer.VideoAsset
    @NonNull
    MediaItem getMediaItem() {
        MediaItem.Builder uri = new MediaItem.Builder().setUri(this.assetUrl);
        int i2 = AnonymousClass1.$SwitchMap$io$flutter$plugins$videoplayer$VideoAsset$StreamingFormat[this.streamingFormat.ordinal()];
        String str = i2 != 1 ? i2 != 2 ? i2 != 3 ? null : MimeTypes.APPLICATION_M3U8 : MimeTypes.APPLICATION_MPD : MimeTypes.APPLICATION_SS;
        if (str != null) {
            uri.setMimeType(str);
        }
        return uri.build();
    }

    @Override // io.flutter.plugins.videoplayer.VideoAsset
    MediaSource.Factory getMediaSourceFactory(Context context) {
        return getMediaSourceFactory(context, new DefaultHttpDataSource.Factory());
    }

    @VisibleForTesting
    MediaSource.Factory getMediaSourceFactory(Context context, DefaultHttpDataSource.Factory factory) {
        unstableUpdateDataSourceFactory(factory, this.httpHeaders, (this.httpHeaders.isEmpty() || !this.httpHeaders.containsKey("User-Agent")) ? "ExoPlayer" : this.httpHeaders.get("User-Agent"));
        return new DefaultMediaSourceFactory(context).setDataSourceFactory(new DefaultDataSource.Factory(context, factory));
    }
}
