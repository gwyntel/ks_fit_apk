package androidx.media3.exoplayer.smoothstreaming.manifest;

import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.Format;
import androidx.media3.common.StreamKey;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.UriUtil;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.offline.FilterableManifest;
import androidx.media3.extractor.mp4.TrackEncryptionBox;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@UnstableApi
/* loaded from: classes.dex */
public class SsManifest implements FilterableManifest<SsManifest> {
    public static final int UNSET_LOOKAHEAD = -1;
    public final long durationUs;
    public final long dvrWindowLengthUs;
    public final boolean isLive;
    public final int lookAheadCount;
    public final int majorVersion;
    public final int minorVersion;

    @Nullable
    public final ProtectionElement protectionElement;
    public final StreamElement[] streamElements;

    public static class ProtectionElement {
        public final byte[] data;
        public final TrackEncryptionBox[] trackEncryptionBoxes;
        public final UUID uuid;

        public ProtectionElement(UUID uuid, byte[] bArr, TrackEncryptionBox[] trackEncryptionBoxArr) {
            this.uuid = uuid;
            this.data = bArr;
            this.trackEncryptionBoxes = trackEncryptionBoxArr;
        }
    }

    public SsManifest(int i2, int i3, long j2, long j3, long j4, int i4, boolean z2, @Nullable ProtectionElement protectionElement, StreamElement[] streamElementArr) {
        this(i2, i3, j3 == 0 ? -9223372036854775807L : Util.scaleLargeTimestamp(j3, 1000000L, j2), j4 != 0 ? Util.scaleLargeTimestamp(j4, 1000000L, j2) : C.TIME_UNSET, i4, z2, protectionElement, streamElementArr);
    }

    @Override // androidx.media3.exoplayer.offline.FilterableManifest
    public /* bridge */ /* synthetic */ SsManifest copy(List list) {
        return copy((List<StreamKey>) list);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.media3.exoplayer.offline.FilterableManifest
    public final SsManifest copy(List<StreamKey> list) {
        ArrayList arrayList = new ArrayList(list);
        Collections.sort(arrayList);
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        StreamElement streamElement = null;
        int i2 = 0;
        while (i2 < arrayList.size()) {
            StreamKey streamKey = (StreamKey) arrayList.get(i2);
            StreamElement streamElement2 = this.streamElements[streamKey.groupIndex];
            if (streamElement2 != streamElement && streamElement != null) {
                arrayList2.add(streamElement.copy((Format[]) arrayList3.toArray(new Format[0])));
                arrayList3.clear();
            }
            arrayList3.add(streamElement2.formats[streamKey.streamIndex]);
            i2++;
            streamElement = streamElement2;
        }
        if (streamElement != null) {
            arrayList2.add(streamElement.copy((Format[]) arrayList3.toArray(new Format[0])));
        }
        return new SsManifest(this.majorVersion, this.minorVersion, this.durationUs, this.dvrWindowLengthUs, this.lookAheadCount, this.isLive, this.protectionElement, (StreamElement[]) arrayList2.toArray(new StreamElement[0]));
    }

    public static class StreamElement {
        private static final String URL_PLACEHOLDER_BITRATE_1 = "{bitrate}";
        private static final String URL_PLACEHOLDER_BITRATE_2 = "{Bitrate}";
        private static final String URL_PLACEHOLDER_START_TIME_1 = "{start time}";
        private static final String URL_PLACEHOLDER_START_TIME_2 = "{start_time}";
        private final String baseUri;
        public final int chunkCount;
        private final List<Long> chunkStartTimes;
        private final long[] chunkStartTimesUs;
        private final String chunkTemplate;
        public final int displayHeight;
        public final int displayWidth;
        public final Format[] formats;

        @Nullable
        public final String language;
        private final long lastChunkDurationUs;
        public final int maxHeight;
        public final int maxWidth;
        public final String name;
        public final String subType;
        public final long timescale;
        public final int type;

        public StreamElement(String str, String str2, int i2, String str3, long j2, String str4, int i3, int i4, int i5, int i6, @Nullable String str5, Format[] formatArr, List<Long> list, long j3) {
            this(str, str2, i2, str3, j2, str4, i3, i4, i5, i6, str5, formatArr, list, Util.scaleLargeTimestamps(list, 1000000L, j2), Util.scaleLargeTimestamp(j3, 1000000L, j2));
        }

        public Uri buildRequestUri(int i2, int i3) {
            Assertions.checkState(this.formats != null);
            Assertions.checkState(this.chunkStartTimes != null);
            Assertions.checkState(i3 < this.chunkStartTimes.size());
            String string = Integer.toString(this.formats[i2].bitrate);
            String string2 = this.chunkStartTimes.get(i3).toString();
            return UriUtil.resolveToUri(this.baseUri, this.chunkTemplate.replace(URL_PLACEHOLDER_BITRATE_1, string).replace(URL_PLACEHOLDER_BITRATE_2, string).replace(URL_PLACEHOLDER_START_TIME_1, string2).replace(URL_PLACEHOLDER_START_TIME_2, string2));
        }

        public StreamElement copy(Format[] formatArr) {
            return new StreamElement(this.baseUri, this.chunkTemplate, this.type, this.subType, this.timescale, this.name, this.maxWidth, this.maxHeight, this.displayWidth, this.displayHeight, this.language, formatArr, this.chunkStartTimes, this.chunkStartTimesUs, this.lastChunkDurationUs);
        }

        public long getChunkDurationUs(int i2) {
            if (i2 == this.chunkCount - 1) {
                return this.lastChunkDurationUs;
            }
            long[] jArr = this.chunkStartTimesUs;
            return jArr[i2 + 1] - jArr[i2];
        }

        public int getChunkIndex(long j2) {
            return Util.binarySearchFloor(this.chunkStartTimesUs, j2, true, true);
        }

        public long getStartTimeUs(int i2) {
            return this.chunkStartTimesUs[i2];
        }

        private StreamElement(String str, String str2, int i2, String str3, long j2, String str4, int i3, int i4, int i5, int i6, @Nullable String str5, Format[] formatArr, List<Long> list, long[] jArr, long j3) {
            this.baseUri = str;
            this.chunkTemplate = str2;
            this.type = i2;
            this.subType = str3;
            this.timescale = j2;
            this.name = str4;
            this.maxWidth = i3;
            this.maxHeight = i4;
            this.displayWidth = i5;
            this.displayHeight = i6;
            this.language = str5;
            this.formats = formatArr;
            this.chunkStartTimes = list;
            this.chunkStartTimesUs = jArr;
            this.lastChunkDurationUs = j3;
            this.chunkCount = list.size();
        }
    }

    private SsManifest(int i2, int i3, long j2, long j3, int i4, boolean z2, @Nullable ProtectionElement protectionElement, StreamElement[] streamElementArr) {
        this.majorVersion = i2;
        this.minorVersion = i3;
        this.durationUs = j2;
        this.dvrWindowLengthUs = j3;
        this.lookAheadCount = i4;
        this.isLive = z2;
        this.protectionElement = protectionElement;
        this.streamElements = streamElementArr;
    }
}
