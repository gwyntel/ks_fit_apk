package androidx.media3.exoplayer.drm;

import android.net.Uri;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.exoplayer.drm.DefaultDrmSessionManager;
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.primitives.Ints;
import com.kingsmith.miot.KsProperty;
import java.util.Map;

@UnstableApi
/* loaded from: classes.dex */
public final class DefaultDrmSessionManagerProvider implements DrmSessionManagerProvider {

    @GuardedBy(KsProperty.Lock)
    private MediaItem.DrmConfiguration drmConfiguration;

    @Nullable
    private DataSource.Factory drmHttpDataSourceFactory;

    @Nullable
    private LoadErrorHandlingPolicy drmLoadErrorHandlingPolicy;
    private final Object lock = new Object();

    @GuardedBy(KsProperty.Lock)
    private DrmSessionManager manager;

    @Nullable
    private String userAgent;

    private DrmSessionManager createManager(MediaItem.DrmConfiguration drmConfiguration) {
        DataSource.Factory userAgent = this.drmHttpDataSourceFactory;
        if (userAgent == null) {
            userAgent = new DefaultHttpDataSource.Factory().setUserAgent(this.userAgent);
        }
        Uri uri = drmConfiguration.licenseUri;
        HttpMediaDrmCallback httpMediaDrmCallback = new HttpMediaDrmCallback(uri == null ? null : uri.toString(), drmConfiguration.forceDefaultLicenseUri, userAgent);
        UnmodifiableIterator<Map.Entry<String, String>> it = drmConfiguration.licenseRequestHeaders.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> next = it.next();
            httpMediaDrmCallback.setKeyRequestProperty(next.getKey(), next.getValue());
        }
        DefaultDrmSessionManager.Builder useDrmSessionsForClearContent = new DefaultDrmSessionManager.Builder().setUuidAndExoMediaDrmProvider(drmConfiguration.scheme, FrameworkMediaDrm.DEFAULT_PROVIDER).setMultiSession(drmConfiguration.multiSession).setPlayClearSamplesWithoutKeys(drmConfiguration.playClearContentWithoutKey).setUseDrmSessionsForClearContent(Ints.toArray(drmConfiguration.forcedSessionTrackTypes));
        LoadErrorHandlingPolicy loadErrorHandlingPolicy = this.drmLoadErrorHandlingPolicy;
        if (loadErrorHandlingPolicy != null) {
            useDrmSessionsForClearContent.setLoadErrorHandlingPolicy(loadErrorHandlingPolicy);
        }
        DefaultDrmSessionManager defaultDrmSessionManagerBuild = useDrmSessionsForClearContent.build(httpMediaDrmCallback);
        defaultDrmSessionManagerBuild.setMode(0, drmConfiguration.getKeySetId());
        return defaultDrmSessionManagerBuild;
    }

    @Override // androidx.media3.exoplayer.drm.DrmSessionManagerProvider
    public DrmSessionManager get(MediaItem mediaItem) {
        DrmSessionManager drmSessionManager;
        Assertions.checkNotNull(mediaItem.localConfiguration);
        MediaItem.DrmConfiguration drmConfiguration = mediaItem.localConfiguration.drmConfiguration;
        if (drmConfiguration == null) {
            return DrmSessionManager.DRM_UNSUPPORTED;
        }
        synchronized (this.lock) {
            try {
                if (!Util.areEqual(drmConfiguration, this.drmConfiguration)) {
                    this.drmConfiguration = drmConfiguration;
                    this.manager = createManager(drmConfiguration);
                }
                drmSessionManager = (DrmSessionManager) Assertions.checkNotNull(this.manager);
            } catch (Throwable th) {
                throw th;
            }
        }
        return drmSessionManager;
    }

    public void setDrmHttpDataSourceFactory(@Nullable DataSource.Factory factory) {
        this.drmHttpDataSourceFactory = factory;
    }

    public void setDrmLoadErrorHandlingPolicy(LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
        this.drmLoadErrorHandlingPolicy = loadErrorHandlingPolicy;
    }

    @Deprecated
    public void setDrmUserAgent(@Nullable String str) {
        this.userAgent = str;
    }
}
