package androidx.media3.common;

import android.os.Bundle;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* loaded from: classes.dex */
public final class DeviceInfo {
    public static final int PLAYBACK_TYPE_LOCAL = 0;
    public static final int PLAYBACK_TYPE_REMOTE = 1;

    @IntRange(from = 0)
    public final int maxVolume;

    @IntRange(from = 0)
    public final int minVolume;
    public final int playbackType;

    @Nullable
    public final String routingControllerId;
    public static final DeviceInfo UNKNOWN = new Builder(0).build();
    private static final String FIELD_PLAYBACK_TYPE = Util.intToStringMaxRadix(0);
    private static final String FIELD_MIN_VOLUME = Util.intToStringMaxRadix(1);
    private static final String FIELD_MAX_VOLUME = Util.intToStringMaxRadix(2);
    private static final String FIELD_ROUTING_CONTROLLER_ID = Util.intToStringMaxRadix(3);

    public static final class Builder {
        private int maxVolume;
        private int minVolume;
        private final int playbackType;

        @Nullable
        private String routingControllerId;

        public Builder(int i2) {
            this.playbackType = i2;
        }

        public DeviceInfo build() {
            Assertions.checkArgument(this.minVolume <= this.maxVolume);
            return new DeviceInfo(this);
        }

        @CanIgnoreReturnValue
        public Builder setMaxVolume(@IntRange(from = 0) int i2) {
            this.maxVolume = i2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setMinVolume(@IntRange(from = 0) int i2) {
            this.minVolume = i2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setRoutingControllerId(@Nullable String str) {
            Assertions.checkArgument(this.playbackType != 0 || str == null);
            this.routingControllerId = str;
            return this;
        }
    }

    @Target({ElementType.TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface PlaybackType {
    }

    @UnstableApi
    public static DeviceInfo fromBundle(Bundle bundle) {
        int i2 = bundle.getInt(FIELD_PLAYBACK_TYPE, 0);
        int i3 = bundle.getInt(FIELD_MIN_VOLUME, 0);
        int i4 = bundle.getInt(FIELD_MAX_VOLUME, 0);
        return new Builder(i2).setMinVolume(i3).setMaxVolume(i4).setRoutingControllerId(bundle.getString(FIELD_ROUTING_CONTROLLER_ID)).build();
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DeviceInfo)) {
            return false;
        }
        DeviceInfo deviceInfo = (DeviceInfo) obj;
        return this.playbackType == deviceInfo.playbackType && this.minVolume == deviceInfo.minVolume && this.maxVolume == deviceInfo.maxVolume && Util.areEqual(this.routingControllerId, deviceInfo.routingControllerId);
    }

    public int hashCode() {
        int i2 = (((((527 + this.playbackType) * 31) + this.minVolume) * 31) + this.maxVolume) * 31;
        String str = this.routingControllerId;
        return i2 + (str == null ? 0 : str.hashCode());
    }

    @UnstableApi
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        int i2 = this.playbackType;
        if (i2 != 0) {
            bundle.putInt(FIELD_PLAYBACK_TYPE, i2);
        }
        int i3 = this.minVolume;
        if (i3 != 0) {
            bundle.putInt(FIELD_MIN_VOLUME, i3);
        }
        int i4 = this.maxVolume;
        if (i4 != 0) {
            bundle.putInt(FIELD_MAX_VOLUME, i4);
        }
        String str = this.routingControllerId;
        if (str != null) {
            bundle.putString(FIELD_ROUTING_CONTROLLER_ID, str);
        }
        return bundle;
    }

    @UnstableApi
    @Deprecated
    public DeviceInfo(int i2, @IntRange(from = 0) int i3, @IntRange(from = 0) int i4) {
        this(new Builder(i2).setMinVolume(i3).setMaxVolume(i4));
    }

    private DeviceInfo(Builder builder) {
        this.playbackType = builder.playbackType;
        this.minVolume = builder.minVolume;
        this.maxVolume = builder.maxVolume;
        this.routingControllerId = builder.routingControllerId;
    }
}
