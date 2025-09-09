package androidx.media3.exoplayer.rtsp;

import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.media3.common.util.Util;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
final class SessionDescription {
    public static final String ATTR_CONTROL = "control";
    public static final String ATTR_FMTP = "fmtp";
    public static final String ATTR_LENGTH = "length";
    public static final String ATTR_RANGE = "range";
    public static final String ATTR_RTPMAP = "rtpmap";
    public static final String ATTR_TOOL = "tool";
    public static final String ATTR_TYPE = "type";
    public static final String SUPPORTED_SDP_VERSION = "0";
    public final ImmutableMap<String, String> attributes;
    public final int bitrate;

    @Nullable
    public final String connection;

    @Nullable
    public final String emailAddress;

    @Nullable
    public final String key;
    public final ImmutableList<MediaDescription> mediaDescriptionList;

    @Nullable
    public final String origin;

    @Nullable
    public final String phoneNumber;

    @Nullable
    public final String sessionInfo;

    @Nullable
    public final String sessionName;

    @Nullable
    public final String timing;

    @Nullable
    public final Uri uri;

    public static final class Builder {

        @Nullable
        private String connection;

        @Nullable
        private String emailAddress;

        @Nullable
        private String key;

        @Nullable
        private String origin;

        @Nullable
        private String phoneNumber;

        @Nullable
        private String sessionInfo;

        @Nullable
        private String sessionName;

        @Nullable
        private String timing;

        @Nullable
        private Uri uri;
        private final HashMap<String, String> attributes = new HashMap<>();
        private final ImmutableList.Builder<MediaDescription> mediaDescriptionListBuilder = new ImmutableList.Builder<>();
        private int bitrate = -1;

        @CanIgnoreReturnValue
        public Builder addAttribute(String str, String str2) {
            this.attributes.put(str, str2);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder addMediaDescription(MediaDescription mediaDescription) {
            this.mediaDescriptionListBuilder.add((ImmutableList.Builder<MediaDescription>) mediaDescription);
            return this;
        }

        public SessionDescription build() {
            return new SessionDescription(this);
        }

        @CanIgnoreReturnValue
        public Builder setBitrate(int i2) {
            this.bitrate = i2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setConnection(String str) {
            this.connection = str;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setEmailAddress(String str) {
            this.emailAddress = str;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setKey(String str) {
            this.key = str;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setOrigin(String str) {
            this.origin = str;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setPhoneNumber(String str) {
            this.phoneNumber = str;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setSessionInfo(String str) {
            this.sessionInfo = str;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setSessionName(String str) {
            this.sessionName = str;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setTiming(String str) {
            this.timing = str;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setUri(Uri uri) {
            this.uri = uri;
            return this;
        }
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || SessionDescription.class != obj.getClass()) {
            return false;
        }
        SessionDescription sessionDescription = (SessionDescription) obj;
        return this.bitrate == sessionDescription.bitrate && this.attributes.equals(sessionDescription.attributes) && this.mediaDescriptionList.equals(sessionDescription.mediaDescriptionList) && Util.areEqual(this.origin, sessionDescription.origin) && Util.areEqual(this.sessionName, sessionDescription.sessionName) && Util.areEqual(this.timing, sessionDescription.timing) && Util.areEqual(this.sessionInfo, sessionDescription.sessionInfo) && Util.areEqual(this.uri, sessionDescription.uri) && Util.areEqual(this.emailAddress, sessionDescription.emailAddress) && Util.areEqual(this.phoneNumber, sessionDescription.phoneNumber) && Util.areEqual(this.connection, sessionDescription.connection) && Util.areEqual(this.key, sessionDescription.key);
    }

    public int hashCode() {
        int iHashCode = (((217 + this.attributes.hashCode()) * 31) + this.mediaDescriptionList.hashCode()) * 31;
        String str = this.origin;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.sessionName;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.timing;
        int iHashCode4 = (((iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31) + this.bitrate) * 31;
        String str4 = this.sessionInfo;
        int iHashCode5 = (iHashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31;
        Uri uri = this.uri;
        int iHashCode6 = (iHashCode5 + (uri == null ? 0 : uri.hashCode())) * 31;
        String str5 = this.emailAddress;
        int iHashCode7 = (iHashCode6 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.phoneNumber;
        int iHashCode8 = (iHashCode7 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.connection;
        int iHashCode9 = (iHashCode8 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.key;
        return iHashCode9 + (str8 != null ? str8.hashCode() : 0);
    }

    private SessionDescription(Builder builder) {
        this.attributes = ImmutableMap.copyOf((Map) builder.attributes);
        this.mediaDescriptionList = builder.mediaDescriptionListBuilder.build();
        this.sessionName = (String) Util.castNonNull(builder.sessionName);
        this.origin = (String) Util.castNonNull(builder.origin);
        this.timing = (String) Util.castNonNull(builder.timing);
        this.uri = builder.uri;
        this.connection = builder.connection;
        this.bitrate = builder.bitrate;
        this.key = builder.key;
        this.emailAddress = builder.emailAddress;
        this.phoneNumber = builder.phoneNumber;
        this.sessionInfo = builder.sessionInfo;
    }
}
