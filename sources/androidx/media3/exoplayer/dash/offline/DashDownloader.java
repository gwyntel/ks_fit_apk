package androidx.media3.exoplayer.dash.offline;

import androidx.annotation.Nullable;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.RunnableFutureTask;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.cache.CacheDataSource;
import androidx.media3.exoplayer.dash.BaseUrlExclusionList;
import androidx.media3.exoplayer.dash.DashSegmentIndex;
import androidx.media3.exoplayer.dash.DashUtil;
import androidx.media3.exoplayer.dash.DashWrappingSegmentIndex;
import androidx.media3.exoplayer.dash.manifest.AdaptationSet;
import androidx.media3.exoplayer.dash.manifest.DashManifest;
import androidx.media3.exoplayer.dash.manifest.DashManifestParser;
import androidx.media3.exoplayer.dash.manifest.Period;
import androidx.media3.exoplayer.dash.manifest.RangedUri;
import androidx.media3.exoplayer.dash.manifest.Representation;
import androidx.media3.exoplayer.offline.SegmentDownloader;
import androidx.media3.exoplayer.upstream.ParsingLoadable;
import androidx.media3.extractor.ChunkIndex;
import com.google.common.collect.ImmutableMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

@UnstableApi
/* loaded from: classes.dex */
public final class DashDownloader extends SegmentDownloader<DashManifest> {
    private final BaseUrlExclusionList baseUrlExclusionList;

    public DashDownloader(MediaItem mediaItem, CacheDataSource.Factory factory) {
        this(mediaItem, factory, new a());
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x00bc A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00b8 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void addSegmentsForAdaptationSet(androidx.media3.datasource.DataSource r27, androidx.media3.exoplayer.dash.manifest.AdaptationSet r28, long r29, long r31, boolean r33, java.util.ArrayList<androidx.media3.exoplayer.offline.SegmentDownloader.Segment> r34) throws java.lang.InterruptedException, java.io.IOException {
        /*
            r26 = this;
            r7 = r26
            r8 = r28
            r9 = r33
            r10 = r34
            r0 = 0
            r11 = r0
        La:
            java.util.List<androidx.media3.exoplayer.dash.manifest.Representation> r0 = r8.representations
            int r0 = r0.size()
            if (r11 >= r0) goto Lbd
            java.util.List<androidx.media3.exoplayer.dash.manifest.Representation> r0 = r8.representations
            java.lang.Object r0 = r0.get(r11)
            androidx.media3.exoplayer.dash.manifest.Representation r0 = (androidx.media3.exoplayer.dash.manifest.Representation) r0
            int r1 = r8.type     // Catch: java.io.IOException -> Lb2
            r12 = r27
            androidx.media3.exoplayer.dash.DashSegmentIndex r13 = r7.getSegmentIndex(r12, r1, r0, r9)     // Catch: java.io.IOException -> Lae
            if (r13 == 0) goto La2
            r14 = r31
            long r16 = r13.getSegmentCount(r14)
            r1 = -1
            int r1 = (r16 > r1 ? 1 : (r16 == r1 ? 0 : -1))
            if (r1 == 0) goto L9a
            androidx.media3.exoplayer.dash.BaseUrlExclusionList r1 = r7.baseUrlExclusionList
            com.google.common.collect.ImmutableList<androidx.media3.exoplayer.dash.manifest.BaseUrl> r2 = r0.baseUrls
            androidx.media3.exoplayer.dash.manifest.BaseUrl r1 = r1.selectBaseUrl(r2)
            java.lang.Object r1 = androidx.media3.common.util.Util.castNonNull(r1)
            androidx.media3.exoplayer.dash.manifest.BaseUrl r1 = (androidx.media3.exoplayer.dash.manifest.BaseUrl) r1
            java.lang.String r6 = r1.url
            androidx.media3.exoplayer.dash.manifest.RangedUri r18 = r0.getInitializationUri()
            if (r18 == 0) goto L58
            r1 = r26
            r2 = r0
            r3 = r6
            r4 = r29
            r19 = r6
            r6 = r18
            androidx.media3.exoplayer.offline.SegmentDownloader$Segment r1 = r1.createSegment(r2, r3, r4, r6)
            r10.add(r1)
            goto L5a
        L58:
            r19 = r6
        L5a:
            androidx.media3.exoplayer.dash.manifest.RangedUri r6 = r0.getIndexUri()
            if (r6 == 0) goto L6e
            r1 = r26
            r2 = r0
            r3 = r19
            r4 = r29
            androidx.media3.exoplayer.offline.SegmentDownloader$Segment r1 = r1.createSegment(r2, r3, r4, r6)
            r10.add(r1)
        L6e:
            long r1 = r13.getFirstSegmentNum()
            long r16 = r1 + r16
            r20 = 1
            long r16 = r16 - r20
            r4 = r1
        L79:
            int r1 = (r4 > r16 ? 1 : (r4 == r16 ? 0 : -1))
            if (r1 > 0) goto Lb8
            long r1 = r13.getTimeUs(r4)
            long r22 = r29 + r1
            androidx.media3.exoplayer.dash.manifest.RangedUri r6 = r13.getSegmentUrl(r4)
            r1 = r26
            r2 = r0
            r3 = r19
            r24 = r4
            r4 = r22
            androidx.media3.exoplayer.offline.SegmentDownloader$Segment r1 = r1.createSegment(r2, r3, r4, r6)
            r10.add(r1)
            long r4 = r24 + r20
            goto L79
        L9a:
            androidx.media3.exoplayer.offline.DownloadException r0 = new androidx.media3.exoplayer.offline.DownloadException
            java.lang.String r1 = "Unbounded segment index"
            r0.<init>(r1)
            throw r0
        La2:
            r14 = r31
            androidx.media3.exoplayer.offline.DownloadException r0 = new androidx.media3.exoplayer.offline.DownloadException     // Catch: java.io.IOException -> Lac
            java.lang.String r1 = "Missing segment index"
            r0.<init>(r1)     // Catch: java.io.IOException -> Lac
            throw r0     // Catch: java.io.IOException -> Lac
        Lac:
            r0 = move-exception
            goto Lb6
        Lae:
            r0 = move-exception
        Laf:
            r14 = r31
            goto Lb6
        Lb2:
            r0 = move-exception
            r12 = r27
            goto Laf
        Lb6:
            if (r9 == 0) goto Lbc
        Lb8:
            int r11 = r11 + 1
            goto La
        Lbc:
            throw r0
        Lbd:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.exoplayer.dash.offline.DashDownloader.addSegmentsForAdaptationSet(androidx.media3.datasource.DataSource, androidx.media3.exoplayer.dash.manifest.AdaptationSet, long, long, boolean, java.util.ArrayList):void");
    }

    private SegmentDownloader.Segment createSegment(Representation representation, String str, long j2, RangedUri rangedUri) {
        return new SegmentDownloader.Segment(j2, DashUtil.buildDataSpec(representation, str, rangedUri, 0, ImmutableMap.of()));
    }

    @Nullable
    private DashSegmentIndex getSegmentIndex(final DataSource dataSource, final int i2, final Representation representation, boolean z2) throws InterruptedException, IOException {
        DashSegmentIndex index = representation.getIndex();
        if (index != null) {
            return index;
        }
        ChunkIndex chunkIndex = (ChunkIndex) b(new RunnableFutureTask<ChunkIndex, IOException>() { // from class: androidx.media3.exoplayer.dash.offline.DashDownloader.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // androidx.media3.common.util.RunnableFutureTask
            /* renamed from: c, reason: merged with bridge method [inline-methods] */
            public ChunkIndex b() {
                return DashUtil.loadChunkIndex(dataSource, i2, representation);
            }
        }, z2);
        if (chunkIndex == null) {
            return null;
        }
        return new DashWrappingSegmentIndex(chunkIndex, representation.presentationTimeOffsetUs);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.media3.exoplayer.offline.SegmentDownloader
    /* renamed from: f, reason: merged with bridge method [inline-methods] */
    public List e(DataSource dataSource, DashManifest dashManifest, boolean z2) throws InterruptedException, IOException {
        ArrayList<SegmentDownloader.Segment> arrayList = new ArrayList<>();
        for (int i2 = 0; i2 < dashManifest.getPeriodCount(); i2++) {
            Period period = dashManifest.getPeriod(i2);
            long jMsToUs = Util.msToUs(period.startMs);
            long periodDurationUs = dashManifest.getPeriodDurationUs(i2);
            int i3 = 0;
            for (List<AdaptationSet> list = period.adaptationSets; i3 < list.size(); list = list) {
                addSegmentsForAdaptationSet(dataSource, list.get(i3), jMsToUs, periodDurationUs, z2, arrayList);
                i3++;
            }
        }
        return arrayList;
    }

    public DashDownloader(MediaItem mediaItem, CacheDataSource.Factory factory, Executor executor) {
        this(mediaItem, new DashManifestParser(), factory, executor, 20000L);
    }

    @Deprecated
    public DashDownloader(MediaItem mediaItem, ParsingLoadable.Parser<DashManifest> parser, CacheDataSource.Factory factory, Executor executor) {
        this(mediaItem, parser, factory, executor, 20000L);
    }

    public DashDownloader(MediaItem mediaItem, ParsingLoadable.Parser<DashManifest> parser, CacheDataSource.Factory factory, Executor executor, long j2) {
        super(mediaItem, parser, factory, executor, j2);
        this.baseUrlExclusionList = new BaseUrlExclusionList();
    }
}
