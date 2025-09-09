package androidx.media3.common;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.BundleCollectionUtil;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.InlineMe;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/* loaded from: classes.dex */
public final class MediaItem {
    public static final String DEFAULT_MEDIA_ID = "";
    public final ClippingConfiguration clippingConfiguration;

    @UnstableApi
    @Deprecated
    public final ClippingProperties clippingProperties;
    public final LiveConfiguration liveConfiguration;

    @Nullable
    public final LocalConfiguration localConfiguration;
    public final String mediaId;
    public final MediaMetadata mediaMetadata;

    @Nullable
    @UnstableApi
    @Deprecated
    public final LocalConfiguration playbackProperties;
    public final RequestMetadata requestMetadata;
    public static final MediaItem EMPTY = new Builder().build();
    private static final String FIELD_MEDIA_ID = Util.intToStringMaxRadix(0);
    private static final String FIELD_LIVE_CONFIGURATION = Util.intToStringMaxRadix(1);
    private static final String FIELD_MEDIA_METADATA = Util.intToStringMaxRadix(2);
    private static final String FIELD_CLIPPING_PROPERTIES = Util.intToStringMaxRadix(3);
    private static final String FIELD_REQUEST_METADATA = Util.intToStringMaxRadix(4);
    private static final String FIELD_LOCAL_CONFIGURATION = Util.intToStringMaxRadix(5);

    public static final class AdsConfiguration {
        private static final String FIELD_AD_TAG_URI = Util.intToStringMaxRadix(0);
        public final Uri adTagUri;

        @Nullable
        public final Object adsId;

        public static final class Builder {
            private Uri adTagUri;

            @Nullable
            private Object adsId;

            public Builder(Uri uri) {
                this.adTagUri = uri;
            }

            public AdsConfiguration build() {
                return new AdsConfiguration(this);
            }

            @CanIgnoreReturnValue
            public Builder setAdTagUri(Uri uri) {
                this.adTagUri = uri;
                return this;
            }

            @CanIgnoreReturnValue
            public Builder setAdsId(@Nullable Object obj) {
                this.adsId = obj;
                return this;
            }
        }

        @UnstableApi
        public static AdsConfiguration fromBundle(Bundle bundle) {
            Uri uri = (Uri) bundle.getParcelable(FIELD_AD_TAG_URI);
            Assertions.checkNotNull(uri);
            return new Builder(uri).build();
        }

        public Builder buildUpon() {
            return new Builder(this.adTagUri).setAdsId(this.adsId);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AdsConfiguration)) {
                return false;
            }
            AdsConfiguration adsConfiguration = (AdsConfiguration) obj;
            return this.adTagUri.equals(adsConfiguration.adTagUri) && Util.areEqual(this.adsId, adsConfiguration.adsId);
        }

        public int hashCode() {
            int iHashCode = this.adTagUri.hashCode() * 31;
            Object obj = this.adsId;
            return iHashCode + (obj != null ? obj.hashCode() : 0);
        }

        @UnstableApi
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putParcelable(FIELD_AD_TAG_URI, this.adTagUri);
            return bundle;
        }

        private AdsConfiguration(Builder builder) {
            this.adTagUri = builder.adTagUri;
            this.adsId = builder.adsId;
        }
    }

    public static final class Builder {

        @Nullable
        private AdsConfiguration adsConfiguration;
        private ClippingConfiguration.Builder clippingConfiguration;

        @Nullable
        private String customCacheKey;
        private DrmConfiguration.Builder drmConfiguration;
        private long imageDurationMs;
        private LiveConfiguration.Builder liveConfiguration;

        @Nullable
        private String mediaId;

        @Nullable
        private MediaMetadata mediaMetadata;

        @Nullable
        private String mimeType;
        private RequestMetadata requestMetadata;
        private List<StreamKey> streamKeys;
        private ImmutableList<SubtitleConfiguration> subtitleConfigurations;

        @Nullable
        private Object tag;

        @Nullable
        private Uri uri;

        public MediaItem build() {
            LocalConfiguration localConfiguration;
            Assertions.checkState(this.drmConfiguration.licenseUri == null || this.drmConfiguration.scheme != null);
            Uri uri = this.uri;
            if (uri != null) {
                localConfiguration = new LocalConfiguration(uri, this.mimeType, this.drmConfiguration.scheme != null ? this.drmConfiguration.build() : null, this.adsConfiguration, this.streamKeys, this.customCacheKey, this.subtitleConfigurations, this.tag, this.imageDurationMs);
            } else {
                localConfiguration = null;
            }
            String str = this.mediaId;
            if (str == null) {
                str = "";
            }
            String str2 = str;
            ClippingProperties clippingPropertiesBuildClippingProperties = this.clippingConfiguration.buildClippingProperties();
            LiveConfiguration liveConfigurationBuild = this.liveConfiguration.build();
            MediaMetadata mediaMetadata = this.mediaMetadata;
            if (mediaMetadata == null) {
                mediaMetadata = MediaMetadata.EMPTY;
            }
            return new MediaItem(str2, clippingPropertiesBuildClippingProperties, localConfiguration, liveConfigurationBuild, mediaMetadata, this.requestMetadata);
        }

        @CanIgnoreReturnValue
        @UnstableApi
        @Deprecated
        public Builder setAdTagUri(@Nullable String str) {
            return setAdTagUri(str != null ? Uri.parse(str) : null);
        }

        @CanIgnoreReturnValue
        public Builder setAdsConfiguration(@Nullable AdsConfiguration adsConfiguration) {
            this.adsConfiguration = adsConfiguration;
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        @Deprecated
        public Builder setClipEndPositionMs(long j2) {
            this.clippingConfiguration.setEndPositionMs(j2);
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        @Deprecated
        public Builder setClipRelativeToDefaultPosition(boolean z2) {
            this.clippingConfiguration.setRelativeToDefaultPosition(z2);
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        @Deprecated
        public Builder setClipRelativeToLiveWindow(boolean z2) {
            this.clippingConfiguration.setRelativeToLiveWindow(z2);
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        @Deprecated
        public Builder setClipStartPositionMs(@IntRange(from = 0) long j2) {
            this.clippingConfiguration.setStartPositionMs(j2);
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        @Deprecated
        public Builder setClipStartsAtKeyFrame(boolean z2) {
            this.clippingConfiguration.setStartsAtKeyFrame(z2);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setClippingConfiguration(ClippingConfiguration clippingConfiguration) {
            this.clippingConfiguration = clippingConfiguration.buildUpon();
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Builder setCustomCacheKey(@Nullable String str) {
            this.customCacheKey = str;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setDrmConfiguration(@Nullable DrmConfiguration drmConfiguration) {
            this.drmConfiguration = drmConfiguration != null ? drmConfiguration.buildUpon() : new DrmConfiguration.Builder();
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        @Deprecated
        public Builder setDrmForceDefaultLicenseUri(boolean z2) {
            this.drmConfiguration.setForceDefaultLicenseUri(z2);
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        @Deprecated
        public Builder setDrmKeySetId(@Nullable byte[] bArr) {
            this.drmConfiguration.setKeySetId(bArr);
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        @Deprecated
        public Builder setDrmLicenseRequestHeaders(@Nullable Map<String, String> map) {
            DrmConfiguration.Builder builder = this.drmConfiguration;
            if (map == null) {
                map = ImmutableMap.of();
            }
            builder.setLicenseRequestHeaders(map);
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        @Deprecated
        public Builder setDrmLicenseUri(@Nullable Uri uri) {
            this.drmConfiguration.setLicenseUri(uri);
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        @Deprecated
        public Builder setDrmMultiSession(boolean z2) {
            this.drmConfiguration.setMultiSession(z2);
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        @Deprecated
        public Builder setDrmPlayClearContentWithoutKey(boolean z2) {
            this.drmConfiguration.setPlayClearContentWithoutKey(z2);
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        @Deprecated
        public Builder setDrmSessionForClearPeriods(boolean z2) {
            this.drmConfiguration.setForceSessionsForAudioAndVideoTracks(z2);
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        @Deprecated
        public Builder setDrmSessionForClearTypes(@Nullable List<Integer> list) {
            DrmConfiguration.Builder builder = this.drmConfiguration;
            if (list == null) {
                list = ImmutableList.of();
            }
            builder.setForcedSessionTrackTypes(list);
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        @Deprecated
        public Builder setDrmUuid(@Nullable UUID uuid) {
            this.drmConfiguration.setNullableScheme(uuid);
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Builder setImageDurationMs(long j2) {
            Assertions.checkArgument(j2 > 0 || j2 == C.TIME_UNSET);
            this.imageDurationMs = j2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setLiveConfiguration(LiveConfiguration liveConfiguration) {
            this.liveConfiguration = liveConfiguration.buildUpon();
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        @Deprecated
        public Builder setLiveMaxOffsetMs(long j2) {
            this.liveConfiguration.setMaxOffsetMs(j2);
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        @Deprecated
        public Builder setLiveMaxPlaybackSpeed(float f2) {
            this.liveConfiguration.setMaxPlaybackSpeed(f2);
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        @Deprecated
        public Builder setLiveMinOffsetMs(long j2) {
            this.liveConfiguration.setMinOffsetMs(j2);
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        @Deprecated
        public Builder setLiveMinPlaybackSpeed(float f2) {
            this.liveConfiguration.setMinPlaybackSpeed(f2);
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        @Deprecated
        public Builder setLiveTargetOffsetMs(long j2) {
            this.liveConfiguration.setTargetOffsetMs(j2);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setMediaId(String str) {
            this.mediaId = (String) Assertions.checkNotNull(str);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setMediaMetadata(MediaMetadata mediaMetadata) {
            this.mediaMetadata = mediaMetadata;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setMimeType(@Nullable String str) {
            this.mimeType = str;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setRequestMetadata(RequestMetadata requestMetadata) {
            this.requestMetadata = requestMetadata;
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Builder setStreamKeys(@Nullable List<StreamKey> list) {
            this.streamKeys = (list == null || list.isEmpty()) ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList(list));
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setSubtitleConfigurations(List<SubtitleConfiguration> list) {
            this.subtitleConfigurations = ImmutableList.copyOf((Collection) list);
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        @Deprecated
        public Builder setSubtitles(@Nullable List<Subtitle> list) {
            this.subtitleConfigurations = list != null ? ImmutableList.copyOf((Collection) list) : ImmutableList.of();
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setTag(@Nullable Object obj) {
            this.tag = obj;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setUri(@Nullable String str) {
            return setUri(str == null ? null : Uri.parse(str));
        }

        public Builder() {
            this.clippingConfiguration = new ClippingConfiguration.Builder();
            this.drmConfiguration = new DrmConfiguration.Builder();
            this.streamKeys = Collections.emptyList();
            this.subtitleConfigurations = ImmutableList.of();
            this.liveConfiguration = new LiveConfiguration.Builder();
            this.requestMetadata = RequestMetadata.EMPTY;
            this.imageDurationMs = C.TIME_UNSET;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        @Deprecated
        public Builder setAdTagUri(@Nullable Uri uri) {
            return setAdTagUri(uri, null);
        }

        @CanIgnoreReturnValue
        @UnstableApi
        @Deprecated
        public Builder setDrmLicenseUri(@Nullable String str) {
            this.drmConfiguration.setLicenseUri(str);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setUri(@Nullable Uri uri) {
            this.uri = uri;
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        @Deprecated
        public Builder setAdTagUri(@Nullable Uri uri, @Nullable Object obj) {
            this.adsConfiguration = uri != null ? new AdsConfiguration.Builder(uri).setAdsId(obj).build() : null;
            return this;
        }

        private Builder(MediaItem mediaItem) {
            DrmConfiguration.Builder builder;
            this();
            this.clippingConfiguration = mediaItem.clippingConfiguration.buildUpon();
            this.mediaId = mediaItem.mediaId;
            this.mediaMetadata = mediaItem.mediaMetadata;
            this.liveConfiguration = mediaItem.liveConfiguration.buildUpon();
            this.requestMetadata = mediaItem.requestMetadata;
            LocalConfiguration localConfiguration = mediaItem.localConfiguration;
            if (localConfiguration != null) {
                this.customCacheKey = localConfiguration.customCacheKey;
                this.mimeType = localConfiguration.mimeType;
                this.uri = localConfiguration.uri;
                this.streamKeys = localConfiguration.streamKeys;
                this.subtitleConfigurations = localConfiguration.subtitleConfigurations;
                this.tag = localConfiguration.tag;
                DrmConfiguration drmConfiguration = localConfiguration.drmConfiguration;
                if (drmConfiguration != null) {
                    builder = drmConfiguration.buildUpon();
                } else {
                    builder = new DrmConfiguration.Builder();
                }
                this.drmConfiguration = builder;
                this.adsConfiguration = localConfiguration.adsConfiguration;
                this.imageDurationMs = localConfiguration.imageDurationMs;
            }
        }
    }

    public static class ClippingConfiguration {
        public final long endPositionMs;

        @UnstableApi
        public final long endPositionUs;
        public final boolean relativeToDefaultPosition;
        public final boolean relativeToLiveWindow;

        @IntRange(from = 0)
        public final long startPositionMs;

        @IntRange(from = 0)
        @UnstableApi
        public final long startPositionUs;
        public final boolean startsAtKeyFrame;
        public static final ClippingConfiguration UNSET = new Builder().build();
        private static final String FIELD_START_POSITION_MS = Util.intToStringMaxRadix(0);
        private static final String FIELD_END_POSITION_MS = Util.intToStringMaxRadix(1);
        private static final String FIELD_RELATIVE_TO_LIVE_WINDOW = Util.intToStringMaxRadix(2);
        private static final String FIELD_RELATIVE_TO_DEFAULT_POSITION = Util.intToStringMaxRadix(3);
        private static final String FIELD_STARTS_AT_KEY_FRAME = Util.intToStringMaxRadix(4);

        /* renamed from: a, reason: collision with root package name */
        static final String f4744a = Util.intToStringMaxRadix(5);

        /* renamed from: b, reason: collision with root package name */
        static final String f4745b = Util.intToStringMaxRadix(6);

        public static final class Builder {
            private long endPositionUs;
            private boolean relativeToDefaultPosition;
            private boolean relativeToLiveWindow;
            private long startPositionUs;
            private boolean startsAtKeyFrame;

            public ClippingConfiguration build() {
                return new ClippingConfiguration(this);
            }

            @UnstableApi
            @Deprecated
            public ClippingProperties buildClippingProperties() {
                return new ClippingProperties(this);
            }

            @CanIgnoreReturnValue
            public Builder setEndPositionMs(long j2) {
                return setEndPositionUs(Util.msToUs(j2));
            }

            @CanIgnoreReturnValue
            @UnstableApi
            public Builder setEndPositionUs(long j2) {
                Assertions.checkArgument(j2 == Long.MIN_VALUE || j2 >= 0);
                this.endPositionUs = j2;
                return this;
            }

            @CanIgnoreReturnValue
            public Builder setRelativeToDefaultPosition(boolean z2) {
                this.relativeToDefaultPosition = z2;
                return this;
            }

            @CanIgnoreReturnValue
            public Builder setRelativeToLiveWindow(boolean z2) {
                this.relativeToLiveWindow = z2;
                return this;
            }

            @CanIgnoreReturnValue
            public Builder setStartPositionMs(@IntRange(from = 0) long j2) {
                return setStartPositionUs(Util.msToUs(j2));
            }

            @CanIgnoreReturnValue
            @UnstableApi
            public Builder setStartPositionUs(@IntRange(from = 0) long j2) {
                Assertions.checkArgument(j2 >= 0);
                this.startPositionUs = j2;
                return this;
            }

            @CanIgnoreReturnValue
            public Builder setStartsAtKeyFrame(boolean z2) {
                this.startsAtKeyFrame = z2;
                return this;
            }

            public Builder() {
                this.endPositionUs = Long.MIN_VALUE;
            }

            private Builder(ClippingConfiguration clippingConfiguration) {
                this.startPositionUs = clippingConfiguration.startPositionUs;
                this.endPositionUs = clippingConfiguration.endPositionUs;
                this.relativeToLiveWindow = clippingConfiguration.relativeToLiveWindow;
                this.relativeToDefaultPosition = clippingConfiguration.relativeToDefaultPosition;
                this.startsAtKeyFrame = clippingConfiguration.startsAtKeyFrame;
            }
        }

        @UnstableApi
        public static ClippingProperties fromBundle(Bundle bundle) {
            Builder builder = new Builder();
            String str = FIELD_START_POSITION_MS;
            ClippingConfiguration clippingConfiguration = UNSET;
            Builder startsAtKeyFrame = builder.setStartPositionMs(bundle.getLong(str, clippingConfiguration.startPositionMs)).setEndPositionMs(bundle.getLong(FIELD_END_POSITION_MS, clippingConfiguration.endPositionMs)).setRelativeToLiveWindow(bundle.getBoolean(FIELD_RELATIVE_TO_LIVE_WINDOW, clippingConfiguration.relativeToLiveWindow)).setRelativeToDefaultPosition(bundle.getBoolean(FIELD_RELATIVE_TO_DEFAULT_POSITION, clippingConfiguration.relativeToDefaultPosition)).setStartsAtKeyFrame(bundle.getBoolean(FIELD_STARTS_AT_KEY_FRAME, clippingConfiguration.startsAtKeyFrame));
            long j2 = bundle.getLong(f4744a, clippingConfiguration.startPositionUs);
            if (j2 != clippingConfiguration.startPositionUs) {
                startsAtKeyFrame.setStartPositionUs(j2);
            }
            long j3 = bundle.getLong(f4745b, clippingConfiguration.endPositionUs);
            if (j3 != clippingConfiguration.endPositionUs) {
                startsAtKeyFrame.setEndPositionUs(j3);
            }
            return startsAtKeyFrame.buildClippingProperties();
        }

        public Builder buildUpon() {
            return new Builder();
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ClippingConfiguration)) {
                return false;
            }
            ClippingConfiguration clippingConfiguration = (ClippingConfiguration) obj;
            return this.startPositionUs == clippingConfiguration.startPositionUs && this.endPositionUs == clippingConfiguration.endPositionUs && this.relativeToLiveWindow == clippingConfiguration.relativeToLiveWindow && this.relativeToDefaultPosition == clippingConfiguration.relativeToDefaultPosition && this.startsAtKeyFrame == clippingConfiguration.startsAtKeyFrame;
        }

        public int hashCode() {
            long j2 = this.startPositionUs;
            int i2 = ((int) (j2 ^ (j2 >>> 32))) * 31;
            long j3 = this.endPositionUs;
            return ((((((i2 + ((int) ((j3 >>> 32) ^ j3))) * 31) + (this.relativeToLiveWindow ? 1 : 0)) * 31) + (this.relativeToDefaultPosition ? 1 : 0)) * 31) + (this.startsAtKeyFrame ? 1 : 0);
        }

        @UnstableApi
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            long j2 = this.startPositionMs;
            ClippingConfiguration clippingConfiguration = UNSET;
            if (j2 != clippingConfiguration.startPositionMs) {
                bundle.putLong(FIELD_START_POSITION_MS, j2);
            }
            long j3 = this.endPositionMs;
            if (j3 != clippingConfiguration.endPositionMs) {
                bundle.putLong(FIELD_END_POSITION_MS, j3);
            }
            long j4 = this.startPositionUs;
            if (j4 != clippingConfiguration.startPositionUs) {
                bundle.putLong(f4744a, j4);
            }
            long j5 = this.endPositionUs;
            if (j5 != clippingConfiguration.endPositionUs) {
                bundle.putLong(f4745b, j5);
            }
            boolean z2 = this.relativeToLiveWindow;
            if (z2 != clippingConfiguration.relativeToLiveWindow) {
                bundle.putBoolean(FIELD_RELATIVE_TO_LIVE_WINDOW, z2);
            }
            boolean z3 = this.relativeToDefaultPosition;
            if (z3 != clippingConfiguration.relativeToDefaultPosition) {
                bundle.putBoolean(FIELD_RELATIVE_TO_DEFAULT_POSITION, z3);
            }
            boolean z4 = this.startsAtKeyFrame;
            if (z4 != clippingConfiguration.startsAtKeyFrame) {
                bundle.putBoolean(FIELD_STARTS_AT_KEY_FRAME, z4);
            }
            return bundle;
        }

        private ClippingConfiguration(Builder builder) {
            this.startPositionMs = Util.usToMs(builder.startPositionUs);
            this.endPositionMs = Util.usToMs(builder.endPositionUs);
            this.startPositionUs = builder.startPositionUs;
            this.endPositionUs = builder.endPositionUs;
            this.relativeToLiveWindow = builder.relativeToLiveWindow;
            this.relativeToDefaultPosition = builder.relativeToDefaultPosition;
            this.startsAtKeyFrame = builder.startsAtKeyFrame;
        }
    }

    @UnstableApi
    @Deprecated
    public static final class ClippingProperties extends ClippingConfiguration {
        public static final ClippingProperties UNSET = new ClippingConfiguration.Builder().buildClippingProperties();

        private ClippingProperties(ClippingConfiguration.Builder builder) {
            super(builder);
        }
    }

    public static final class DrmConfiguration {
        public final boolean forceDefaultLicenseUri;
        public final ImmutableList<Integer> forcedSessionTrackTypes;

        @Nullable
        private final byte[] keySetId;
        public final ImmutableMap<String, String> licenseRequestHeaders;

        @Nullable
        public final Uri licenseUri;
        public final boolean multiSession;
        public final boolean playClearContentWithoutKey;

        @UnstableApi
        @Deprecated
        public final ImmutableMap<String, String> requestHeaders;
        public final UUID scheme;

        @UnstableApi
        @Deprecated
        public final ImmutableList<Integer> sessionForClearTypes;

        @UnstableApi
        @Deprecated
        public final UUID uuid;
        private static final String FIELD_SCHEME = Util.intToStringMaxRadix(0);
        private static final String FIELD_LICENSE_URI = Util.intToStringMaxRadix(1);
        private static final String FIELD_LICENSE_REQUEST_HEADERS = Util.intToStringMaxRadix(2);
        private static final String FIELD_MULTI_SESSION = Util.intToStringMaxRadix(3);

        /* renamed from: a, reason: collision with root package name */
        static final String f4746a = Util.intToStringMaxRadix(4);
        private static final String FIELD_FORCE_DEFAULT_LICENSE_URI = Util.intToStringMaxRadix(5);
        private static final String FIELD_FORCED_SESSION_TRACK_TYPES = Util.intToStringMaxRadix(6);
        private static final String FIELD_KEY_SET_ID = Util.intToStringMaxRadix(7);

        public static final class Builder {
            private boolean forceDefaultLicenseUri;
            private ImmutableList<Integer> forcedSessionTrackTypes;

            @Nullable
            private byte[] keySetId;
            private ImmutableMap<String, String> licenseRequestHeaders;

            @Nullable
            private Uri licenseUri;
            private boolean multiSession;
            private boolean playClearContentWithoutKey;

            @Nullable
            private UUID scheme;

            /* JADX INFO: Access modifiers changed from: private */
            @CanIgnoreReturnValue
            @Deprecated
            public Builder setNullableScheme(@Nullable UUID uuid) {
                this.scheme = uuid;
                return this;
            }

            public DrmConfiguration build() {
                return new DrmConfiguration(this);
            }

            @Deprecated
            @CanIgnoreReturnValue
            @InlineMe(replacement = "this.setForceSessionsForAudioAndVideoTracks(forceSessionsForAudioAndVideoTracks)")
            @UnstableApi
            public Builder forceSessionsForAudioAndVideoTracks(boolean z2) {
                return setForceSessionsForAudioAndVideoTracks(z2);
            }

            @CanIgnoreReturnValue
            public Builder setForceDefaultLicenseUri(boolean z2) {
                this.forceDefaultLicenseUri = z2;
                return this;
            }

            @CanIgnoreReturnValue
            public Builder setForceSessionsForAudioAndVideoTracks(boolean z2) {
                setForcedSessionTrackTypes(z2 ? ImmutableList.of(2, 1) : ImmutableList.of());
                return this;
            }

            @CanIgnoreReturnValue
            public Builder setForcedSessionTrackTypes(List<Integer> list) {
                this.forcedSessionTrackTypes = ImmutableList.copyOf((Collection) list);
                return this;
            }

            @CanIgnoreReturnValue
            public Builder setKeySetId(@Nullable byte[] bArr) {
                this.keySetId = bArr != null ? Arrays.copyOf(bArr, bArr.length) : null;
                return this;
            }

            @CanIgnoreReturnValue
            public Builder setLicenseRequestHeaders(Map<String, String> map) {
                this.licenseRequestHeaders = ImmutableMap.copyOf((Map) map);
                return this;
            }

            @CanIgnoreReturnValue
            public Builder setLicenseUri(@Nullable Uri uri) {
                this.licenseUri = uri;
                return this;
            }

            @CanIgnoreReturnValue
            public Builder setMultiSession(boolean z2) {
                this.multiSession = z2;
                return this;
            }

            @CanIgnoreReturnValue
            public Builder setPlayClearContentWithoutKey(boolean z2) {
                this.playClearContentWithoutKey = z2;
                return this;
            }

            @CanIgnoreReturnValue
            public Builder setScheme(UUID uuid) {
                this.scheme = uuid;
                return this;
            }

            @CanIgnoreReturnValue
            public Builder setLicenseUri(@Nullable String str) {
                this.licenseUri = str == null ? null : Uri.parse(str);
                return this;
            }

            public Builder(UUID uuid) {
                this();
                this.scheme = uuid;
            }

            @Deprecated
            private Builder() {
                this.licenseRequestHeaders = ImmutableMap.of();
                this.playClearContentWithoutKey = true;
                this.forcedSessionTrackTypes = ImmutableList.of();
            }

            private Builder(DrmConfiguration drmConfiguration) {
                this.scheme = drmConfiguration.scheme;
                this.licenseUri = drmConfiguration.licenseUri;
                this.licenseRequestHeaders = drmConfiguration.licenseRequestHeaders;
                this.multiSession = drmConfiguration.multiSession;
                this.playClearContentWithoutKey = drmConfiguration.playClearContentWithoutKey;
                this.forceDefaultLicenseUri = drmConfiguration.forceDefaultLicenseUri;
                this.forcedSessionTrackTypes = drmConfiguration.forcedSessionTrackTypes;
                this.keySetId = drmConfiguration.keySetId;
            }
        }

        @UnstableApi
        public static DrmConfiguration fromBundle(Bundle bundle) {
            UUID uuidFromString = UUID.fromString((String) Assertions.checkNotNull(bundle.getString(FIELD_SCHEME)));
            Uri uri = (Uri) bundle.getParcelable(FIELD_LICENSE_URI);
            ImmutableMap<String, String> immutableMapBundleToStringImmutableMap = BundleCollectionUtil.bundleToStringImmutableMap(BundleCollectionUtil.getBundleWithDefault(bundle, FIELD_LICENSE_REQUEST_HEADERS, Bundle.EMPTY));
            boolean z2 = bundle.getBoolean(FIELD_MULTI_SESSION, false);
            boolean z3 = bundle.getBoolean(f4746a, false);
            boolean z4 = bundle.getBoolean(FIELD_FORCE_DEFAULT_LICENSE_URI, false);
            ImmutableList immutableListCopyOf = ImmutableList.copyOf((Collection) BundleCollectionUtil.getIntegerArrayListWithDefault(bundle, FIELD_FORCED_SESSION_TRACK_TYPES, new ArrayList()));
            return new Builder(uuidFromString).setLicenseUri(uri).setLicenseRequestHeaders(immutableMapBundleToStringImmutableMap).setMultiSession(z2).setForceDefaultLicenseUri(z4).setPlayClearContentWithoutKey(z3).setForcedSessionTrackTypes(immutableListCopyOf).setKeySetId(bundle.getByteArray(FIELD_KEY_SET_ID)).build();
        }

        public Builder buildUpon() {
            return new Builder();
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DrmConfiguration)) {
                return false;
            }
            DrmConfiguration drmConfiguration = (DrmConfiguration) obj;
            return this.scheme.equals(drmConfiguration.scheme) && Util.areEqual(this.licenseUri, drmConfiguration.licenseUri) && Util.areEqual(this.licenseRequestHeaders, drmConfiguration.licenseRequestHeaders) && this.multiSession == drmConfiguration.multiSession && this.forceDefaultLicenseUri == drmConfiguration.forceDefaultLicenseUri && this.playClearContentWithoutKey == drmConfiguration.playClearContentWithoutKey && this.forcedSessionTrackTypes.equals(drmConfiguration.forcedSessionTrackTypes) && Arrays.equals(this.keySetId, drmConfiguration.keySetId);
        }

        @Nullable
        public byte[] getKeySetId() {
            byte[] bArr = this.keySetId;
            if (bArr != null) {
                return Arrays.copyOf(bArr, bArr.length);
            }
            return null;
        }

        public int hashCode() {
            int iHashCode = this.scheme.hashCode() * 31;
            Uri uri = this.licenseUri;
            return ((((((((((((iHashCode + (uri != null ? uri.hashCode() : 0)) * 31) + this.licenseRequestHeaders.hashCode()) * 31) + (this.multiSession ? 1 : 0)) * 31) + (this.forceDefaultLicenseUri ? 1 : 0)) * 31) + (this.playClearContentWithoutKey ? 1 : 0)) * 31) + this.forcedSessionTrackTypes.hashCode()) * 31) + Arrays.hashCode(this.keySetId);
        }

        @UnstableApi
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putString(FIELD_SCHEME, this.scheme.toString());
            Uri uri = this.licenseUri;
            if (uri != null) {
                bundle.putParcelable(FIELD_LICENSE_URI, uri);
            }
            if (!this.licenseRequestHeaders.isEmpty()) {
                bundle.putBundle(FIELD_LICENSE_REQUEST_HEADERS, BundleCollectionUtil.stringMapToBundle(this.licenseRequestHeaders));
            }
            boolean z2 = this.multiSession;
            if (z2) {
                bundle.putBoolean(FIELD_MULTI_SESSION, z2);
            }
            boolean z3 = this.playClearContentWithoutKey;
            if (z3) {
                bundle.putBoolean(f4746a, z3);
            }
            boolean z4 = this.forceDefaultLicenseUri;
            if (z4) {
                bundle.putBoolean(FIELD_FORCE_DEFAULT_LICENSE_URI, z4);
            }
            if (!this.forcedSessionTrackTypes.isEmpty()) {
                bundle.putIntegerArrayList(FIELD_FORCED_SESSION_TRACK_TYPES, new ArrayList<>(this.forcedSessionTrackTypes));
            }
            byte[] bArr = this.keySetId;
            if (bArr != null) {
                bundle.putByteArray(FIELD_KEY_SET_ID, bArr);
            }
            return bundle;
        }

        private DrmConfiguration(Builder builder) {
            Assertions.checkState((builder.forceDefaultLicenseUri && builder.licenseUri == null) ? false : true);
            UUID uuid = (UUID) Assertions.checkNotNull(builder.scheme);
            this.scheme = uuid;
            this.uuid = uuid;
            this.licenseUri = builder.licenseUri;
            this.requestHeaders = builder.licenseRequestHeaders;
            this.licenseRequestHeaders = builder.licenseRequestHeaders;
            this.multiSession = builder.multiSession;
            this.forceDefaultLicenseUri = builder.forceDefaultLicenseUri;
            this.playClearContentWithoutKey = builder.playClearContentWithoutKey;
            this.sessionForClearTypes = builder.forcedSessionTrackTypes;
            this.forcedSessionTrackTypes = builder.forcedSessionTrackTypes;
            this.keySetId = builder.keySetId != null ? Arrays.copyOf(builder.keySetId, builder.keySetId.length) : null;
        }
    }

    public static final class LiveConfiguration {
        public final long maxOffsetMs;
        public final float maxPlaybackSpeed;
        public final long minOffsetMs;
        public final float minPlaybackSpeed;
        public final long targetOffsetMs;
        public static final LiveConfiguration UNSET = new Builder().build();
        private static final String FIELD_TARGET_OFFSET_MS = Util.intToStringMaxRadix(0);
        private static final String FIELD_MIN_OFFSET_MS = Util.intToStringMaxRadix(1);
        private static final String FIELD_MAX_OFFSET_MS = Util.intToStringMaxRadix(2);
        private static final String FIELD_MIN_PLAYBACK_SPEED = Util.intToStringMaxRadix(3);
        private static final String FIELD_MAX_PLAYBACK_SPEED = Util.intToStringMaxRadix(4);

        public static final class Builder {
            private long maxOffsetMs;
            private float maxPlaybackSpeed;
            private long minOffsetMs;
            private float minPlaybackSpeed;
            private long targetOffsetMs;

            public LiveConfiguration build() {
                return new LiveConfiguration(this);
            }

            @CanIgnoreReturnValue
            public Builder setMaxOffsetMs(long j2) {
                this.maxOffsetMs = j2;
                return this;
            }

            @CanIgnoreReturnValue
            public Builder setMaxPlaybackSpeed(float f2) {
                this.maxPlaybackSpeed = f2;
                return this;
            }

            @CanIgnoreReturnValue
            public Builder setMinOffsetMs(long j2) {
                this.minOffsetMs = j2;
                return this;
            }

            @CanIgnoreReturnValue
            public Builder setMinPlaybackSpeed(float f2) {
                this.minPlaybackSpeed = f2;
                return this;
            }

            @CanIgnoreReturnValue
            public Builder setTargetOffsetMs(long j2) {
                this.targetOffsetMs = j2;
                return this;
            }

            public Builder() {
                this.targetOffsetMs = C.TIME_UNSET;
                this.minOffsetMs = C.TIME_UNSET;
                this.maxOffsetMs = C.TIME_UNSET;
                this.minPlaybackSpeed = -3.4028235E38f;
                this.maxPlaybackSpeed = -3.4028235E38f;
            }

            private Builder(LiveConfiguration liveConfiguration) {
                this.targetOffsetMs = liveConfiguration.targetOffsetMs;
                this.minOffsetMs = liveConfiguration.minOffsetMs;
                this.maxOffsetMs = liveConfiguration.maxOffsetMs;
                this.minPlaybackSpeed = liveConfiguration.minPlaybackSpeed;
                this.maxPlaybackSpeed = liveConfiguration.maxPlaybackSpeed;
            }
        }

        @UnstableApi
        public static LiveConfiguration fromBundle(Bundle bundle) {
            Builder builder = new Builder();
            String str = FIELD_TARGET_OFFSET_MS;
            LiveConfiguration liveConfiguration = UNSET;
            return builder.setTargetOffsetMs(bundle.getLong(str, liveConfiguration.targetOffsetMs)).setMinOffsetMs(bundle.getLong(FIELD_MIN_OFFSET_MS, liveConfiguration.minOffsetMs)).setMaxOffsetMs(bundle.getLong(FIELD_MAX_OFFSET_MS, liveConfiguration.maxOffsetMs)).setMinPlaybackSpeed(bundle.getFloat(FIELD_MIN_PLAYBACK_SPEED, liveConfiguration.minPlaybackSpeed)).setMaxPlaybackSpeed(bundle.getFloat(FIELD_MAX_PLAYBACK_SPEED, liveConfiguration.maxPlaybackSpeed)).build();
        }

        public Builder buildUpon() {
            return new Builder();
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof LiveConfiguration)) {
                return false;
            }
            LiveConfiguration liveConfiguration = (LiveConfiguration) obj;
            return this.targetOffsetMs == liveConfiguration.targetOffsetMs && this.minOffsetMs == liveConfiguration.minOffsetMs && this.maxOffsetMs == liveConfiguration.maxOffsetMs && this.minPlaybackSpeed == liveConfiguration.minPlaybackSpeed && this.maxPlaybackSpeed == liveConfiguration.maxPlaybackSpeed;
        }

        public int hashCode() {
            long j2 = this.targetOffsetMs;
            long j3 = this.minOffsetMs;
            int i2 = ((((int) (j2 ^ (j2 >>> 32))) * 31) + ((int) (j3 ^ (j3 >>> 32)))) * 31;
            long j4 = this.maxOffsetMs;
            int i3 = (i2 + ((int) ((j4 >>> 32) ^ j4))) * 31;
            float f2 = this.minPlaybackSpeed;
            int iFloatToIntBits = (i3 + (f2 != 0.0f ? Float.floatToIntBits(f2) : 0)) * 31;
            float f3 = this.maxPlaybackSpeed;
            return iFloatToIntBits + (f3 != 0.0f ? Float.floatToIntBits(f3) : 0);
        }

        @UnstableApi
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            long j2 = this.targetOffsetMs;
            LiveConfiguration liveConfiguration = UNSET;
            if (j2 != liveConfiguration.targetOffsetMs) {
                bundle.putLong(FIELD_TARGET_OFFSET_MS, j2);
            }
            long j3 = this.minOffsetMs;
            if (j3 != liveConfiguration.minOffsetMs) {
                bundle.putLong(FIELD_MIN_OFFSET_MS, j3);
            }
            long j4 = this.maxOffsetMs;
            if (j4 != liveConfiguration.maxOffsetMs) {
                bundle.putLong(FIELD_MAX_OFFSET_MS, j4);
            }
            float f2 = this.minPlaybackSpeed;
            if (f2 != liveConfiguration.minPlaybackSpeed) {
                bundle.putFloat(FIELD_MIN_PLAYBACK_SPEED, f2);
            }
            float f3 = this.maxPlaybackSpeed;
            if (f3 != liveConfiguration.maxPlaybackSpeed) {
                bundle.putFloat(FIELD_MAX_PLAYBACK_SPEED, f3);
            }
            return bundle;
        }

        private LiveConfiguration(Builder builder) {
            this(builder.targetOffsetMs, builder.minOffsetMs, builder.maxOffsetMs, builder.minPlaybackSpeed, builder.maxPlaybackSpeed);
        }

        @UnstableApi
        @Deprecated
        public LiveConfiguration(long j2, long j3, long j4, float f2, float f3) {
            this.targetOffsetMs = j2;
            this.minOffsetMs = j3;
            this.maxOffsetMs = j4;
            this.minPlaybackSpeed = f2;
            this.maxPlaybackSpeed = f3;
        }
    }

    public static final class LocalConfiguration {

        @Nullable
        public final AdsConfiguration adsConfiguration;

        @Nullable
        @UnstableApi
        public final String customCacheKey;

        @Nullable
        public final DrmConfiguration drmConfiguration;

        @UnstableApi
        public final long imageDurationMs;

        @Nullable
        public final String mimeType;

        @UnstableApi
        public final List<StreamKey> streamKeys;
        public final ImmutableList<SubtitleConfiguration> subtitleConfigurations;

        @UnstableApi
        @Deprecated
        public final List<Subtitle> subtitles;

        @Nullable
        public final Object tag;
        public final Uri uri;
        private static final String FIELD_URI = Util.intToStringMaxRadix(0);
        private static final String FIELD_MIME_TYPE = Util.intToStringMaxRadix(1);
        private static final String FIELD_DRM_CONFIGURATION = Util.intToStringMaxRadix(2);
        private static final String FIELD_ADS_CONFIGURATION = Util.intToStringMaxRadix(3);
        private static final String FIELD_STREAM_KEYS = Util.intToStringMaxRadix(4);
        private static final String FIELD_CUSTOM_CACHE_KEY = Util.intToStringMaxRadix(5);
        private static final String FIELD_SUBTITLE_CONFIGURATION = Util.intToStringMaxRadix(6);
        private static final String FIELD_IMAGE_DURATION_MS = Util.intToStringMaxRadix(7);

        @UnstableApi
        public static LocalConfiguration fromBundle(Bundle bundle) {
            Bundle bundle2 = bundle.getBundle(FIELD_DRM_CONFIGURATION);
            DrmConfiguration drmConfigurationFromBundle = bundle2 == null ? null : DrmConfiguration.fromBundle(bundle2);
            Bundle bundle3 = bundle.getBundle(FIELD_ADS_CONFIGURATION);
            AdsConfiguration adsConfigurationFromBundle = bundle3 != null ? AdsConfiguration.fromBundle(bundle3) : null;
            ArrayList parcelableArrayList = bundle.getParcelableArrayList(FIELD_STREAM_KEYS);
            ImmutableList immutableListOf = parcelableArrayList == null ? ImmutableList.of() : BundleCollectionUtil.fromBundleList(new Function() { // from class: androidx.media3.common.m
                @Override // com.google.common.base.Function
                public final Object apply(Object obj) {
                    return StreamKey.fromBundle((Bundle) obj);
                }
            }, parcelableArrayList);
            ArrayList parcelableArrayList2 = bundle.getParcelableArrayList(FIELD_SUBTITLE_CONFIGURATION);
            return new LocalConfiguration((Uri) Assertions.checkNotNull((Uri) bundle.getParcelable(FIELD_URI)), bundle.getString(FIELD_MIME_TYPE), drmConfigurationFromBundle, adsConfigurationFromBundle, immutableListOf, bundle.getString(FIELD_CUSTOM_CACHE_KEY), parcelableArrayList2 == null ? ImmutableList.of() : BundleCollectionUtil.fromBundleList(new Function() { // from class: androidx.media3.common.n
                @Override // com.google.common.base.Function
                public final Object apply(Object obj) {
                    return MediaItem.SubtitleConfiguration.fromBundle((Bundle) obj);
                }
            }, parcelableArrayList2), null, bundle.getLong(FIELD_IMAGE_DURATION_MS, C.TIME_UNSET));
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof LocalConfiguration)) {
                return false;
            }
            LocalConfiguration localConfiguration = (LocalConfiguration) obj;
            return this.uri.equals(localConfiguration.uri) && Util.areEqual(this.mimeType, localConfiguration.mimeType) && Util.areEqual(this.drmConfiguration, localConfiguration.drmConfiguration) && Util.areEqual(this.adsConfiguration, localConfiguration.adsConfiguration) && this.streamKeys.equals(localConfiguration.streamKeys) && Util.areEqual(this.customCacheKey, localConfiguration.customCacheKey) && this.subtitleConfigurations.equals(localConfiguration.subtitleConfigurations) && Util.areEqual(this.tag, localConfiguration.tag) && Util.areEqual(Long.valueOf(this.imageDurationMs), Long.valueOf(localConfiguration.imageDurationMs));
        }

        public int hashCode() {
            int iHashCode = this.uri.hashCode() * 31;
            String str = this.mimeType;
            int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
            DrmConfiguration drmConfiguration = this.drmConfiguration;
            int iHashCode3 = (iHashCode2 + (drmConfiguration == null ? 0 : drmConfiguration.hashCode())) * 31;
            AdsConfiguration adsConfiguration = this.adsConfiguration;
            int iHashCode4 = (((iHashCode3 + (adsConfiguration == null ? 0 : adsConfiguration.hashCode())) * 31) + this.streamKeys.hashCode()) * 31;
            String str2 = this.customCacheKey;
            int iHashCode5 = (((iHashCode4 + (str2 == null ? 0 : str2.hashCode())) * 31) + this.subtitleConfigurations.hashCode()) * 31;
            return (int) (((iHashCode5 + (this.tag != null ? r1.hashCode() : 0)) * 31) + this.imageDurationMs);
        }

        @UnstableApi
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putParcelable(FIELD_URI, this.uri);
            String str = this.mimeType;
            if (str != null) {
                bundle.putString(FIELD_MIME_TYPE, str);
            }
            DrmConfiguration drmConfiguration = this.drmConfiguration;
            if (drmConfiguration != null) {
                bundle.putBundle(FIELD_DRM_CONFIGURATION, drmConfiguration.toBundle());
            }
            AdsConfiguration adsConfiguration = this.adsConfiguration;
            if (adsConfiguration != null) {
                bundle.putBundle(FIELD_ADS_CONFIGURATION, adsConfiguration.toBundle());
            }
            if (!this.streamKeys.isEmpty()) {
                bundle.putParcelableArrayList(FIELD_STREAM_KEYS, BundleCollectionUtil.toBundleArrayList(this.streamKeys, new Function() { // from class: androidx.media3.common.k
                    @Override // com.google.common.base.Function
                    public final Object apply(Object obj) {
                        return ((StreamKey) obj).toBundle();
                    }
                }));
            }
            String str2 = this.customCacheKey;
            if (str2 != null) {
                bundle.putString(FIELD_CUSTOM_CACHE_KEY, str2);
            }
            if (!this.subtitleConfigurations.isEmpty()) {
                bundle.putParcelableArrayList(FIELD_SUBTITLE_CONFIGURATION, BundleCollectionUtil.toBundleArrayList(this.subtitleConfigurations, new Function() { // from class: androidx.media3.common.l
                    @Override // com.google.common.base.Function
                    public final Object apply(Object obj) {
                        return ((MediaItem.SubtitleConfiguration) obj).toBundle();
                    }
                }));
            }
            long j2 = this.imageDurationMs;
            if (j2 != C.TIME_UNSET) {
                bundle.putLong(FIELD_IMAGE_DURATION_MS, j2);
            }
            return bundle;
        }

        private LocalConfiguration(Uri uri, @Nullable String str, @Nullable DrmConfiguration drmConfiguration, @Nullable AdsConfiguration adsConfiguration, List<StreamKey> list, @Nullable String str2, ImmutableList<SubtitleConfiguration> immutableList, @Nullable Object obj, long j2) {
            this.uri = uri;
            this.mimeType = MimeTypes.normalizeMimeType(str);
            this.drmConfiguration = drmConfiguration;
            this.adsConfiguration = adsConfiguration;
            this.streamKeys = list;
            this.customCacheKey = str2;
            this.subtitleConfigurations = immutableList;
            ImmutableList.Builder builder = ImmutableList.builder();
            for (int i2 = 0; i2 < immutableList.size(); i2++) {
                builder.add((ImmutableList.Builder) immutableList.get(i2).buildUpon().buildSubtitle());
            }
            this.subtitles = builder.build();
            this.tag = obj;
            this.imageDurationMs = j2;
        }
    }

    public static final class RequestMetadata {

        @Nullable
        public final Bundle extras;

        @Nullable
        public final Uri mediaUri;

        @Nullable
        public final String searchQuery;
        public static final RequestMetadata EMPTY = new Builder().build();
        private static final String FIELD_MEDIA_URI = Util.intToStringMaxRadix(0);
        private static final String FIELD_SEARCH_QUERY = Util.intToStringMaxRadix(1);
        private static final String FIELD_EXTRAS = Util.intToStringMaxRadix(2);

        @UnstableApi
        public static RequestMetadata fromBundle(Bundle bundle) {
            return new Builder().setMediaUri((Uri) bundle.getParcelable(FIELD_MEDIA_URI)).setSearchQuery(bundle.getString(FIELD_SEARCH_QUERY)).setExtras(bundle.getBundle(FIELD_EXTRAS)).build();
        }

        public Builder buildUpon() {
            return new Builder();
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof RequestMetadata)) {
                return false;
            }
            RequestMetadata requestMetadata = (RequestMetadata) obj;
            if (Util.areEqual(this.mediaUri, requestMetadata.mediaUri) && Util.areEqual(this.searchQuery, requestMetadata.searchQuery)) {
                if ((this.extras == null) == (requestMetadata.extras == null)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            Uri uri = this.mediaUri;
            int iHashCode = (uri == null ? 0 : uri.hashCode()) * 31;
            String str = this.searchQuery;
            return ((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + (this.extras != null ? 1 : 0);
        }

        @UnstableApi
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            Uri uri = this.mediaUri;
            if (uri != null) {
                bundle.putParcelable(FIELD_MEDIA_URI, uri);
            }
            String str = this.searchQuery;
            if (str != null) {
                bundle.putString(FIELD_SEARCH_QUERY, str);
            }
            Bundle bundle2 = this.extras;
            if (bundle2 != null) {
                bundle.putBundle(FIELD_EXTRAS, bundle2);
            }
            return bundle;
        }

        public static final class Builder {

            @Nullable
            private Bundle extras;

            @Nullable
            private Uri mediaUri;

            @Nullable
            private String searchQuery;

            public Builder() {
            }

            public RequestMetadata build() {
                return new RequestMetadata(this);
            }

            @CanIgnoreReturnValue
            public Builder setExtras(@Nullable Bundle bundle) {
                this.extras = bundle;
                return this;
            }

            @CanIgnoreReturnValue
            public Builder setMediaUri(@Nullable Uri uri) {
                this.mediaUri = uri;
                return this;
            }

            @CanIgnoreReturnValue
            public Builder setSearchQuery(@Nullable String str) {
                this.searchQuery = str;
                return this;
            }

            private Builder(RequestMetadata requestMetadata) {
                this.mediaUri = requestMetadata.mediaUri;
                this.searchQuery = requestMetadata.searchQuery;
                this.extras = requestMetadata.extras;
            }
        }

        private RequestMetadata(Builder builder) {
            this.mediaUri = builder.mediaUri;
            this.searchQuery = builder.searchQuery;
            this.extras = builder.extras;
        }
    }

    @UnstableApi
    @Deprecated
    public static final class Subtitle extends SubtitleConfiguration {
        @UnstableApi
        @Deprecated
        public Subtitle(Uri uri, String str, @Nullable String str2) {
            this(uri, str, str2, 0);
        }

        @UnstableApi
        @Deprecated
        public Subtitle(Uri uri, String str, @Nullable String str2, int i2) {
            this(uri, str, str2, i2, 0, null);
        }

        @UnstableApi
        @Deprecated
        public Subtitle(Uri uri, String str, @Nullable String str2, int i2, int i3, @Nullable String str3) {
            super(uri, str, str2, i2, i3, str3, null);
        }

        private Subtitle(SubtitleConfiguration.Builder builder) {
            super(builder);
        }
    }

    @UnstableApi
    public static MediaItem fromBundle(Bundle bundle) {
        String str = (String) Assertions.checkNotNull(bundle.getString(FIELD_MEDIA_ID, ""));
        Bundle bundle2 = bundle.getBundle(FIELD_LIVE_CONFIGURATION);
        LiveConfiguration liveConfigurationFromBundle = bundle2 == null ? LiveConfiguration.UNSET : LiveConfiguration.fromBundle(bundle2);
        Bundle bundle3 = bundle.getBundle(FIELD_MEDIA_METADATA);
        MediaMetadata mediaMetadataFromBundle = bundle3 == null ? MediaMetadata.EMPTY : MediaMetadata.fromBundle(bundle3);
        Bundle bundle4 = bundle.getBundle(FIELD_CLIPPING_PROPERTIES);
        ClippingProperties clippingPropertiesFromBundle = bundle4 == null ? ClippingProperties.UNSET : ClippingConfiguration.fromBundle(bundle4);
        Bundle bundle5 = bundle.getBundle(FIELD_REQUEST_METADATA);
        RequestMetadata requestMetadataFromBundle = bundle5 == null ? RequestMetadata.EMPTY : RequestMetadata.fromBundle(bundle5);
        Bundle bundle6 = bundle.getBundle(FIELD_LOCAL_CONFIGURATION);
        return new MediaItem(str, clippingPropertiesFromBundle, bundle6 == null ? null : LocalConfiguration.fromBundle(bundle6), liveConfigurationFromBundle, mediaMetadataFromBundle, requestMetadataFromBundle);
    }

    public static MediaItem fromUri(String str) {
        return new Builder().setUri(str).build();
    }

    @UnstableApi
    private Bundle toBundle(boolean z2) {
        LocalConfiguration localConfiguration;
        Bundle bundle = new Bundle();
        if (!this.mediaId.equals("")) {
            bundle.putString(FIELD_MEDIA_ID, this.mediaId);
        }
        if (!this.liveConfiguration.equals(LiveConfiguration.UNSET)) {
            bundle.putBundle(FIELD_LIVE_CONFIGURATION, this.liveConfiguration.toBundle());
        }
        if (!this.mediaMetadata.equals(MediaMetadata.EMPTY)) {
            bundle.putBundle(FIELD_MEDIA_METADATA, this.mediaMetadata.toBundle());
        }
        if (!this.clippingConfiguration.equals(ClippingConfiguration.UNSET)) {
            bundle.putBundle(FIELD_CLIPPING_PROPERTIES, this.clippingConfiguration.toBundle());
        }
        if (!this.requestMetadata.equals(RequestMetadata.EMPTY)) {
            bundle.putBundle(FIELD_REQUEST_METADATA, this.requestMetadata.toBundle());
        }
        if (z2 && (localConfiguration = this.localConfiguration) != null) {
            bundle.putBundle(FIELD_LOCAL_CONFIGURATION, localConfiguration.toBundle());
        }
        return bundle;
    }

    public Builder buildUpon() {
        return new Builder();
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaItem)) {
            return false;
        }
        MediaItem mediaItem = (MediaItem) obj;
        return Util.areEqual(this.mediaId, mediaItem.mediaId) && this.clippingConfiguration.equals(mediaItem.clippingConfiguration) && Util.areEqual(this.localConfiguration, mediaItem.localConfiguration) && Util.areEqual(this.liveConfiguration, mediaItem.liveConfiguration) && Util.areEqual(this.mediaMetadata, mediaItem.mediaMetadata) && Util.areEqual(this.requestMetadata, mediaItem.requestMetadata);
    }

    public int hashCode() {
        int iHashCode = this.mediaId.hashCode() * 31;
        LocalConfiguration localConfiguration = this.localConfiguration;
        return ((((((((iHashCode + (localConfiguration != null ? localConfiguration.hashCode() : 0)) * 31) + this.liveConfiguration.hashCode()) * 31) + this.clippingConfiguration.hashCode()) * 31) + this.mediaMetadata.hashCode()) * 31) + this.requestMetadata.hashCode();
    }

    @UnstableApi
    public Bundle toBundleIncludeLocalConfiguration() {
        return toBundle(true);
    }

    public static class SubtitleConfiguration {

        @Nullable
        public final String id;

        @Nullable
        public final String label;

        @Nullable
        public final String language;

        @Nullable
        public final String mimeType;
        public final int roleFlags;
        public final int selectionFlags;
        public final Uri uri;
        private static final String FIELD_URI = Util.intToStringMaxRadix(0);
        private static final String FIELD_MIME_TYPE = Util.intToStringMaxRadix(1);
        private static final String FIELD_LANGUAGE = Util.intToStringMaxRadix(2);
        private static final String FIELD_SELECTION_FLAGS = Util.intToStringMaxRadix(3);
        private static final String FIELD_ROLE_FLAGS = Util.intToStringMaxRadix(4);
        private static final String FIELD_LABEL = Util.intToStringMaxRadix(5);
        private static final String FIELD_ID = Util.intToStringMaxRadix(6);

        public static final class Builder {

            @Nullable
            private String id;

            @Nullable
            private String label;

            @Nullable
            private String language;

            @Nullable
            private String mimeType;
            private int roleFlags;
            private int selectionFlags;
            private Uri uri;

            /* JADX INFO: Access modifiers changed from: private */
            public Subtitle buildSubtitle() {
                return new Subtitle(this);
            }

            public SubtitleConfiguration build() {
                return new SubtitleConfiguration(this);
            }

            @CanIgnoreReturnValue
            public Builder setId(@Nullable String str) {
                this.id = str;
                return this;
            }

            @CanIgnoreReturnValue
            public Builder setLabel(@Nullable String str) {
                this.label = str;
                return this;
            }

            @CanIgnoreReturnValue
            public Builder setLanguage(@Nullable String str) {
                this.language = str;
                return this;
            }

            @CanIgnoreReturnValue
            public Builder setMimeType(@Nullable String str) {
                this.mimeType = MimeTypes.normalizeMimeType(str);
                return this;
            }

            @CanIgnoreReturnValue
            public Builder setRoleFlags(int i2) {
                this.roleFlags = i2;
                return this;
            }

            @CanIgnoreReturnValue
            public Builder setSelectionFlags(int i2) {
                this.selectionFlags = i2;
                return this;
            }

            @CanIgnoreReturnValue
            public Builder setUri(Uri uri) {
                this.uri = uri;
                return this;
            }

            public Builder(Uri uri) {
                this.uri = uri;
            }

            private Builder(SubtitleConfiguration subtitleConfiguration) {
                this.uri = subtitleConfiguration.uri;
                this.mimeType = subtitleConfiguration.mimeType;
                this.language = subtitleConfiguration.language;
                this.selectionFlags = subtitleConfiguration.selectionFlags;
                this.roleFlags = subtitleConfiguration.roleFlags;
                this.label = subtitleConfiguration.label;
                this.id = subtitleConfiguration.id;
            }
        }

        @UnstableApi
        public static SubtitleConfiguration fromBundle(Bundle bundle) {
            Uri uri = (Uri) Assertions.checkNotNull((Uri) bundle.getParcelable(FIELD_URI));
            String string = bundle.getString(FIELD_MIME_TYPE);
            String string2 = bundle.getString(FIELD_LANGUAGE);
            int i2 = bundle.getInt(FIELD_SELECTION_FLAGS, 0);
            int i3 = bundle.getInt(FIELD_ROLE_FLAGS, 0);
            String string3 = bundle.getString(FIELD_LABEL);
            return new Builder(uri).setMimeType(string).setLanguage(string2).setSelectionFlags(i2).setRoleFlags(i3).setLabel(string3).setId(bundle.getString(FIELD_ID)).build();
        }

        public Builder buildUpon() {
            return new Builder();
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SubtitleConfiguration)) {
                return false;
            }
            SubtitleConfiguration subtitleConfiguration = (SubtitleConfiguration) obj;
            return this.uri.equals(subtitleConfiguration.uri) && Util.areEqual(this.mimeType, subtitleConfiguration.mimeType) && Util.areEqual(this.language, subtitleConfiguration.language) && this.selectionFlags == subtitleConfiguration.selectionFlags && this.roleFlags == subtitleConfiguration.roleFlags && Util.areEqual(this.label, subtitleConfiguration.label) && Util.areEqual(this.id, subtitleConfiguration.id);
        }

        public int hashCode() {
            int iHashCode = this.uri.hashCode() * 31;
            String str = this.mimeType;
            int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
            String str2 = this.language;
            int iHashCode3 = (((((iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31) + this.selectionFlags) * 31) + this.roleFlags) * 31;
            String str3 = this.label;
            int iHashCode4 = (iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
            String str4 = this.id;
            return iHashCode4 + (str4 != null ? str4.hashCode() : 0);
        }

        @UnstableApi
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putParcelable(FIELD_URI, this.uri);
            String str = this.mimeType;
            if (str != null) {
                bundle.putString(FIELD_MIME_TYPE, str);
            }
            String str2 = this.language;
            if (str2 != null) {
                bundle.putString(FIELD_LANGUAGE, str2);
            }
            int i2 = this.selectionFlags;
            if (i2 != 0) {
                bundle.putInt(FIELD_SELECTION_FLAGS, i2);
            }
            int i3 = this.roleFlags;
            if (i3 != 0) {
                bundle.putInt(FIELD_ROLE_FLAGS, i3);
            }
            String str3 = this.label;
            if (str3 != null) {
                bundle.putString(FIELD_LABEL, str3);
            }
            String str4 = this.id;
            if (str4 != null) {
                bundle.putString(FIELD_ID, str4);
            }
            return bundle;
        }

        private SubtitleConfiguration(Uri uri, String str, @Nullable String str2, int i2, int i3, @Nullable String str3, @Nullable String str4) {
            this.uri = uri;
            this.mimeType = MimeTypes.normalizeMimeType(str);
            this.language = str2;
            this.selectionFlags = i2;
            this.roleFlags = i3;
            this.label = str3;
            this.id = str4;
        }

        private SubtitleConfiguration(Builder builder) {
            this.uri = builder.uri;
            this.mimeType = builder.mimeType;
            this.language = builder.language;
            this.selectionFlags = builder.selectionFlags;
            this.roleFlags = builder.roleFlags;
            this.label = builder.label;
            this.id = builder.id;
        }
    }

    private MediaItem(String str, ClippingProperties clippingProperties, @Nullable LocalConfiguration localConfiguration, LiveConfiguration liveConfiguration, MediaMetadata mediaMetadata, RequestMetadata requestMetadata) {
        this.mediaId = str;
        this.localConfiguration = localConfiguration;
        this.playbackProperties = localConfiguration;
        this.liveConfiguration = liveConfiguration;
        this.mediaMetadata = mediaMetadata;
        this.clippingConfiguration = clippingProperties;
        this.clippingProperties = clippingProperties;
        this.requestMetadata = requestMetadata;
    }

    public static MediaItem fromUri(Uri uri) {
        return new Builder().setUri(uri).build();
    }

    @UnstableApi
    public Bundle toBundle() {
        return toBundle(false);
    }
}
