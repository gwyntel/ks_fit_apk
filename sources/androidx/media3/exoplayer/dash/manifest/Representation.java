package androidx.media3.exoplayer.dash.manifest;

import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.media3.common.Format;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.dash.DashSegmentIndex;
import androidx.media3.exoplayer.dash.manifest.SegmentBase;
import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@UnstableApi
/* loaded from: classes.dex */
public abstract class Representation {
    public static final long REVISION_ID_DEFAULT = -1;
    public final ImmutableList<BaseUrl> baseUrls;
    public final List<Descriptor> essentialProperties;
    public final Format format;
    public final List<Descriptor> inbandEventStreams;

    @Nullable
    private final RangedUri initializationUri;
    public final long presentationTimeOffsetUs;
    public final long revisionId;
    public final List<Descriptor> supplementalProperties;

    public static class MultiSegmentRepresentation extends Representation implements DashSegmentIndex {

        /* renamed from: a, reason: collision with root package name */
        final SegmentBase.MultiSegmentBase f5172a;

        public MultiSegmentRepresentation(long j2, Format format, List<BaseUrl> list, SegmentBase.MultiSegmentBase multiSegmentBase, @Nullable List<Descriptor> list2, List<Descriptor> list3, List<Descriptor> list4) {
            super(j2, format, list, multiSegmentBase, list2, list3, list4);
            this.f5172a = multiSegmentBase;
        }

        @Override // androidx.media3.exoplayer.dash.DashSegmentIndex
        public long getAvailableSegmentCount(long j2, long j3) {
            return this.f5172a.getAvailableSegmentCount(j2, j3);
        }

        @Override // androidx.media3.exoplayer.dash.manifest.Representation
        @Nullable
        public String getCacheKey() {
            return null;
        }

        @Override // androidx.media3.exoplayer.dash.DashSegmentIndex
        public long getDurationUs(long j2, long j3) {
            return this.f5172a.getSegmentDurationUs(j2, j3);
        }

        @Override // androidx.media3.exoplayer.dash.DashSegmentIndex
        public long getFirstAvailableSegmentNum(long j2, long j3) {
            return this.f5172a.getFirstAvailableSegmentNum(j2, j3);
        }

        @Override // androidx.media3.exoplayer.dash.DashSegmentIndex
        public long getFirstSegmentNum() {
            return this.f5172a.getFirstSegmentNum();
        }

        @Override // androidx.media3.exoplayer.dash.manifest.Representation
        public DashSegmentIndex getIndex() {
            return this;
        }

        @Override // androidx.media3.exoplayer.dash.manifest.Representation
        @Nullable
        public RangedUri getIndexUri() {
            return null;
        }

        @Override // androidx.media3.exoplayer.dash.DashSegmentIndex
        public long getNextSegmentAvailableTimeUs(long j2, long j3) {
            return this.f5172a.getNextSegmentAvailableTimeUs(j2, j3);
        }

        @Override // androidx.media3.exoplayer.dash.DashSegmentIndex
        public long getSegmentCount(long j2) {
            return this.f5172a.getSegmentCount(j2);
        }

        @Override // androidx.media3.exoplayer.dash.DashSegmentIndex
        public long getSegmentNum(long j2, long j3) {
            return this.f5172a.getSegmentNum(j2, j3);
        }

        @Override // androidx.media3.exoplayer.dash.DashSegmentIndex
        public RangedUri getSegmentUrl(long j2) {
            return this.f5172a.getSegmentUrl(this, j2);
        }

        @Override // androidx.media3.exoplayer.dash.DashSegmentIndex
        public long getTimeUs(long j2) {
            return this.f5172a.getSegmentTimeUs(j2);
        }

        @Override // androidx.media3.exoplayer.dash.DashSegmentIndex
        public boolean isExplicit() {
            return this.f5172a.isExplicit();
        }
    }

    public static class SingleSegmentRepresentation extends Representation {

        @Nullable
        private final String cacheKey;
        public final long contentLength;

        @Nullable
        private final RangedUri indexUri;

        @Nullable
        private final SingleSegmentIndex segmentIndex;
        public final Uri uri;

        public SingleSegmentRepresentation(long j2, Format format, List<BaseUrl> list, SegmentBase.SingleSegmentBase singleSegmentBase, @Nullable List<Descriptor> list2, List<Descriptor> list3, List<Descriptor> list4, @Nullable String str, long j3) {
            super(j2, format, list, singleSegmentBase, list2, list3, list4);
            this.uri = Uri.parse(list.get(0).url);
            RangedUri index = singleSegmentBase.getIndex();
            this.indexUri = index;
            this.cacheKey = str;
            this.contentLength = j3;
            this.segmentIndex = index != null ? null : new SingleSegmentIndex(new RangedUri(null, 0L, j3));
        }

        public static SingleSegmentRepresentation newInstance(long j2, Format format, String str, long j3, long j4, long j5, long j6, List<Descriptor> list, @Nullable String str2, long j7) {
            return new SingleSegmentRepresentation(j2, format, ImmutableList.of(new BaseUrl(str)), new SegmentBase.SingleSegmentBase(new RangedUri(null, j3, (j4 - j3) + 1), 1L, 0L, j5, (j6 - j5) + 1), list, ImmutableList.of(), ImmutableList.of(), str2, j7);
        }

        @Override // androidx.media3.exoplayer.dash.manifest.Representation
        @Nullable
        public String getCacheKey() {
            return this.cacheKey;
        }

        @Override // androidx.media3.exoplayer.dash.manifest.Representation
        @Nullable
        public DashSegmentIndex getIndex() {
            return this.segmentIndex;
        }

        @Override // androidx.media3.exoplayer.dash.manifest.Representation
        @Nullable
        public RangedUri getIndexUri() {
            return this.indexUri;
        }
    }

    public static Representation newInstance(long j2, Format format, List<BaseUrl> list, SegmentBase segmentBase) {
        return newInstance(j2, format, list, segmentBase, null, ImmutableList.of(), ImmutableList.of(), null);
    }

    @Nullable
    public abstract String getCacheKey();

    @Nullable
    public abstract DashSegmentIndex getIndex();

    @Nullable
    public abstract RangedUri getIndexUri();

    @Nullable
    public RangedUri getInitializationUri() {
        return this.initializationUri;
    }

    private Representation(long j2, Format format, List<BaseUrl> list, SegmentBase segmentBase, @Nullable List<Descriptor> list2, List<Descriptor> list3, List<Descriptor> list4) {
        Assertions.checkArgument(!list.isEmpty());
        this.revisionId = j2;
        this.format = format;
        this.baseUrls = ImmutableList.copyOf((Collection) list);
        this.inbandEventStreams = list2 == null ? Collections.emptyList() : Collections.unmodifiableList(list2);
        this.essentialProperties = list3;
        this.supplementalProperties = list4;
        this.initializationUri = segmentBase.getInitialization(this);
        this.presentationTimeOffsetUs = segmentBase.getPresentationTimeOffsetUs();
    }

    public static Representation newInstance(long j2, Format format, List<BaseUrl> list, SegmentBase segmentBase, @Nullable List<Descriptor> list2, List<Descriptor> list3, List<Descriptor> list4, @Nullable String str) {
        if (segmentBase instanceof SegmentBase.SingleSegmentBase) {
            return new SingleSegmentRepresentation(j2, format, list, (SegmentBase.SingleSegmentBase) segmentBase, list2, list3, list4, str, -1L);
        }
        if (segmentBase instanceof SegmentBase.MultiSegmentBase) {
            return new MultiSegmentRepresentation(j2, format, list, (SegmentBase.MultiSegmentBase) segmentBase, list2, list3, list4);
        }
        throw new IllegalArgumentException("segmentBase must be of type SingleSegmentBase or MultiSegmentBase");
    }
}
