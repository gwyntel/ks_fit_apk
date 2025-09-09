package androidx.media3.exoplayer.source;

import android.os.Looper;
import androidx.annotation.CallSuper;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.DataReader;
import androidx.media3.common.DrmInitData;
import androidx.media3.common.Format;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Consumer;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.decoder.DecoderInputBuffer;
import androidx.media3.exoplayer.FormatHolder;
import androidx.media3.exoplayer.analytics.PlayerId;
import androidx.media3.exoplayer.drm.DrmSession;
import androidx.media3.exoplayer.drm.DrmSessionEventListener;
import androidx.media3.exoplayer.drm.DrmSessionManager;
import androidx.media3.exoplayer.source.SampleQueue;
import androidx.media3.exoplayer.upstream.Allocator;
import androidx.media3.extractor.TrackOutput;
import java.io.IOException;

@UnstableApi
/* loaded from: classes2.dex */
public class SampleQueue implements TrackOutput {
    private static final String TAG = "SampleQueue";
    private int absoluteFirstIndex;

    @Nullable
    private DrmSession currentDrmSession;

    @Nullable
    private Format downstreamFormat;

    @Nullable
    private final DrmSessionEventListener.EventDispatcher drmEventDispatcher;

    @Nullable
    private final DrmSessionManager drmSessionManager;
    private boolean isLastSampleQueued;
    private int length;
    private boolean loggedUnexpectedNonSyncSample;
    private boolean pendingSplice;
    private int readPosition;
    private int relativeFirstIndex;
    private final SampleDataQueue sampleDataQueue;
    private long sampleOffsetUs;

    @Nullable
    private Format unadjustedUpstreamFormat;

    @Nullable
    private Format upstreamFormat;
    private boolean upstreamFormatAdjustmentRequired;

    @Nullable
    private UpstreamFormatChangedListener upstreamFormatChangeListener;
    private long upstreamSourceId;
    private final SampleExtrasHolder extrasHolder = new SampleExtrasHolder();
    private int capacity = 1000;
    private long[] sourceIds = new long[1000];
    private long[] offsets = new long[1000];
    private long[] timesUs = new long[1000];
    private int[] flags = new int[1000];
    private int[] sizes = new int[1000];
    private TrackOutput.CryptoData[] cryptoDatas = new TrackOutput.CryptoData[1000];
    private final SpannedData<SharedSampleMetadata> sharedSampleMetadata = new SpannedData<>(new Consumer() { // from class: androidx.media3.exoplayer.source.e0
        @Override // androidx.media3.common.util.Consumer
        public final void accept(Object obj) {
            SampleQueue.lambda$new$0((SampleQueue.SharedSampleMetadata) obj);
        }
    });
    private long startTimeUs = Long.MIN_VALUE;
    private long largestDiscardedTimestampUs = Long.MIN_VALUE;
    private long largestQueuedTimestampUs = Long.MIN_VALUE;
    private boolean upstreamFormatRequired = true;
    private boolean upstreamKeyframeRequired = true;
    private boolean allSamplesAreSyncSamples = true;

    static final class SampleExtrasHolder {

        @Nullable
        public TrackOutput.CryptoData cryptoData;
        public long offset;
        public int size;

        SampleExtrasHolder() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class SharedSampleMetadata {
        public final DrmSessionManager.DrmSessionReference drmSessionReference;
        public final Format format;

        private SharedSampleMetadata(Format format, DrmSessionManager.DrmSessionReference drmSessionReference) {
            this.format = format;
            this.drmSessionReference = drmSessionReference;
        }
    }

    public interface UpstreamFormatChangedListener {
        void onUpstreamFormatChanged(Format format);
    }

    protected SampleQueue(Allocator allocator, DrmSessionManager drmSessionManager, DrmSessionEventListener.EventDispatcher eventDispatcher) {
        this.drmSessionManager = drmSessionManager;
        this.drmEventDispatcher = eventDispatcher;
        this.sampleDataQueue = new SampleDataQueue(allocator);
    }

    private synchronized boolean attemptSplice(long j2) {
        if (this.length == 0) {
            return j2 > this.largestDiscardedTimestampUs;
        }
        if (getLargestReadTimestampUs() >= j2) {
            return false;
        }
        discardUpstreamSampleMetadata(this.absoluteFirstIndex + countUnreadSamplesBefore(j2));
        return true;
    }

    private synchronized void commitSample(long j2, int i2, long j3, int i3, @Nullable TrackOutput.CryptoData cryptoData) {
        try {
            int i4 = this.length;
            if (i4 > 0) {
                int relativeIndex = getRelativeIndex(i4 - 1);
                Assertions.checkArgument(this.offsets[relativeIndex] + ((long) this.sizes[relativeIndex]) <= j3);
            }
            this.isLastSampleQueued = (536870912 & i2) != 0;
            this.largestQueuedTimestampUs = Math.max(this.largestQueuedTimestampUs, j2);
            int relativeIndex2 = getRelativeIndex(this.length);
            this.timesUs[relativeIndex2] = j2;
            this.offsets[relativeIndex2] = j3;
            this.sizes[relativeIndex2] = i3;
            this.flags[relativeIndex2] = i2;
            this.cryptoDatas[relativeIndex2] = cryptoData;
            this.sourceIds[relativeIndex2] = this.upstreamSourceId;
            if (this.sharedSampleMetadata.isEmpty() || !this.sharedSampleMetadata.getEndValue().format.equals(this.upstreamFormat)) {
                Format format = (Format) Assertions.checkNotNull(this.upstreamFormat);
                DrmSessionManager drmSessionManager = this.drmSessionManager;
                this.sharedSampleMetadata.appendSpan(getWriteIndex(), new SharedSampleMetadata(format, drmSessionManager != null ? drmSessionManager.preacquireSession(this.drmEventDispatcher, format) : DrmSessionManager.DrmSessionReference.EMPTY));
            }
            int i5 = this.length + 1;
            this.length = i5;
            int i6 = this.capacity;
            if (i5 == i6) {
                int i7 = i6 + 1000;
                long[] jArr = new long[i7];
                long[] jArr2 = new long[i7];
                long[] jArr3 = new long[i7];
                int[] iArr = new int[i7];
                int[] iArr2 = new int[i7];
                TrackOutput.CryptoData[] cryptoDataArr = new TrackOutput.CryptoData[i7];
                int i8 = this.relativeFirstIndex;
                int i9 = i6 - i8;
                System.arraycopy(this.offsets, i8, jArr2, 0, i9);
                System.arraycopy(this.timesUs, this.relativeFirstIndex, jArr3, 0, i9);
                System.arraycopy(this.flags, this.relativeFirstIndex, iArr, 0, i9);
                System.arraycopy(this.sizes, this.relativeFirstIndex, iArr2, 0, i9);
                System.arraycopy(this.cryptoDatas, this.relativeFirstIndex, cryptoDataArr, 0, i9);
                System.arraycopy(this.sourceIds, this.relativeFirstIndex, jArr, 0, i9);
                int i10 = this.relativeFirstIndex;
                System.arraycopy(this.offsets, 0, jArr2, i9, i10);
                System.arraycopy(this.timesUs, 0, jArr3, i9, i10);
                System.arraycopy(this.flags, 0, iArr, i9, i10);
                System.arraycopy(this.sizes, 0, iArr2, i9, i10);
                System.arraycopy(this.cryptoDatas, 0, cryptoDataArr, i9, i10);
                System.arraycopy(this.sourceIds, 0, jArr, i9, i10);
                this.offsets = jArr2;
                this.timesUs = jArr3;
                this.flags = iArr;
                this.sizes = iArr2;
                this.cryptoDatas = cryptoDataArr;
                this.sourceIds = jArr;
                this.relativeFirstIndex = 0;
                this.capacity = i7;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    private int countUnreadSamplesBefore(long j2) {
        int i2 = this.length;
        int relativeIndex = getRelativeIndex(i2 - 1);
        while (i2 > this.readPosition && this.timesUs[relativeIndex] >= j2) {
            i2--;
            relativeIndex--;
            if (relativeIndex == -1) {
                relativeIndex = this.capacity - 1;
            }
        }
        return i2;
    }

    public static SampleQueue createWithDrm(Allocator allocator, DrmSessionManager drmSessionManager, DrmSessionEventListener.EventDispatcher eventDispatcher) {
        return new SampleQueue(allocator, (DrmSessionManager) Assertions.checkNotNull(drmSessionManager), (DrmSessionEventListener.EventDispatcher) Assertions.checkNotNull(eventDispatcher));
    }

    public static SampleQueue createWithoutDrm(Allocator allocator) {
        return new SampleQueue(allocator, null, null);
    }

    private synchronized long discardSampleMetadataTo(long j2, boolean z2, boolean z3) {
        int i2;
        try {
            int i3 = this.length;
            if (i3 != 0) {
                long[] jArr = this.timesUs;
                int i4 = this.relativeFirstIndex;
                if (j2 >= jArr[i4]) {
                    if (z3 && (i2 = this.readPosition) != i3) {
                        i3 = i2 + 1;
                    }
                    int iFindSampleBefore = findSampleBefore(i4, i3, j2, z2);
                    if (iFindSampleBefore == -1) {
                        return -1L;
                    }
                    return discardSamples(iFindSampleBefore);
                }
            }
            return -1L;
        } finally {
        }
    }

    private synchronized long discardSampleMetadataToEnd() {
        int i2 = this.length;
        if (i2 == 0) {
            return -1L;
        }
        return discardSamples(i2);
    }

    @GuardedBy("this")
    private long discardSamples(int i2) {
        this.largestDiscardedTimestampUs = Math.max(this.largestDiscardedTimestampUs, getLargestTimestamp(i2));
        this.length -= i2;
        int i3 = this.absoluteFirstIndex + i2;
        this.absoluteFirstIndex = i3;
        int i4 = this.relativeFirstIndex + i2;
        this.relativeFirstIndex = i4;
        int i5 = this.capacity;
        if (i4 >= i5) {
            this.relativeFirstIndex = i4 - i5;
        }
        int i6 = this.readPosition - i2;
        this.readPosition = i6;
        if (i6 < 0) {
            this.readPosition = 0;
        }
        this.sharedSampleMetadata.discardTo(i3);
        if (this.length != 0) {
            return this.offsets[this.relativeFirstIndex];
        }
        int i7 = this.relativeFirstIndex;
        if (i7 == 0) {
            i7 = this.capacity;
        }
        return this.offsets[i7 - 1] + this.sizes[r6];
    }

    private long discardUpstreamSampleMetadata(int i2) {
        int writeIndex = getWriteIndex() - i2;
        boolean z2 = false;
        Assertions.checkArgument(writeIndex >= 0 && writeIndex <= this.length - this.readPosition);
        int i3 = this.length - writeIndex;
        this.length = i3;
        this.largestQueuedTimestampUs = Math.max(this.largestDiscardedTimestampUs, getLargestTimestamp(i3));
        if (writeIndex == 0 && this.isLastSampleQueued) {
            z2 = true;
        }
        this.isLastSampleQueued = z2;
        this.sharedSampleMetadata.discardFrom(i2);
        int i4 = this.length;
        if (i4 == 0) {
            return 0L;
        }
        return this.offsets[getRelativeIndex(i4 - 1)] + this.sizes[r9];
    }

    private int findSampleAfter(int i2, int i3, long j2, boolean z2) {
        for (int i4 = 0; i4 < i3; i4++) {
            if (this.timesUs[i2] >= j2) {
                return i4;
            }
            i2++;
            if (i2 == this.capacity) {
                i2 = 0;
            }
        }
        if (z2) {
            return i3;
        }
        return -1;
    }

    private int findSampleBefore(int i2, int i3, long j2, boolean z2) {
        int i4 = -1;
        for (int i5 = 0; i5 < i3; i5++) {
            long j3 = this.timesUs[i2];
            if (j3 > j2) {
                return i4;
            }
            if (!z2 || (this.flags[i2] & 1) != 0) {
                if (j3 == j2) {
                    return i5;
                }
                i4 = i5;
            }
            i2++;
            if (i2 == this.capacity) {
                i2 = 0;
            }
        }
        return i4;
    }

    private long getLargestTimestamp(int i2) {
        long jMax = Long.MIN_VALUE;
        if (i2 == 0) {
            return Long.MIN_VALUE;
        }
        int relativeIndex = getRelativeIndex(i2 - 1);
        for (int i3 = 0; i3 < i2; i3++) {
            jMax = Math.max(jMax, this.timesUs[relativeIndex]);
            if ((this.flags[relativeIndex] & 1) != 0) {
                break;
            }
            relativeIndex--;
            if (relativeIndex == -1) {
                relativeIndex = this.capacity - 1;
            }
        }
        return jMax;
    }

    private int getRelativeIndex(int i2) {
        int i3 = this.relativeFirstIndex + i2;
        int i4 = this.capacity;
        return i3 < i4 ? i3 : i3 - i4;
    }

    private boolean hasNextSample() {
        return this.readPosition != this.length;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$0(SharedSampleMetadata sharedSampleMetadata) {
        sharedSampleMetadata.drmSessionReference.release();
    }

    private boolean mayReadSample(int i2) {
        DrmSession drmSession = this.currentDrmSession;
        return drmSession == null || drmSession.getState() == 4 || ((this.flags[i2] & 1073741824) == 0 && this.currentDrmSession.playClearSamplesWithoutKeys());
    }

    private void onFormatResult(Format format, FormatHolder formatHolder) {
        Format format2 = this.downstreamFormat;
        boolean z2 = format2 == null;
        DrmInitData drmInitData = format2 == null ? null : format2.drmInitData;
        this.downstreamFormat = format;
        DrmInitData drmInitData2 = format.drmInitData;
        DrmSessionManager drmSessionManager = this.drmSessionManager;
        formatHolder.format = drmSessionManager != null ? format.copyWithCryptoType(drmSessionManager.getCryptoType(format)) : format;
        formatHolder.drmSession = this.currentDrmSession;
        if (this.drmSessionManager == null) {
            return;
        }
        if (z2 || !Util.areEqual(drmInitData, drmInitData2)) {
            DrmSession drmSession = this.currentDrmSession;
            DrmSession drmSessionAcquireSession = this.drmSessionManager.acquireSession(this.drmEventDispatcher, format);
            this.currentDrmSession = drmSessionAcquireSession;
            formatHolder.drmSession = drmSessionAcquireSession;
            if (drmSession != null) {
                drmSession.release(this.drmEventDispatcher);
            }
        }
    }

    private synchronized int peekSampleMetadata(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, boolean z2, boolean z3, SampleExtrasHolder sampleExtrasHolder) {
        try {
            decoderInputBuffer.waitingForKeys = false;
            if (!hasNextSample()) {
                if (!z3 && !this.isLastSampleQueued) {
                    Format format = this.upstreamFormat;
                    if (format == null || (!z2 && format == this.downstreamFormat)) {
                        return -3;
                    }
                    onFormatResult((Format) Assertions.checkNotNull(format), formatHolder);
                    return -5;
                }
                decoderInputBuffer.setFlags(4);
                decoderInputBuffer.timeUs = Long.MIN_VALUE;
                return -4;
            }
            Format format2 = this.sharedSampleMetadata.get(getReadIndex()).format;
            if (!z2 && format2 == this.downstreamFormat) {
                int relativeIndex = getRelativeIndex(this.readPosition);
                if (!mayReadSample(relativeIndex)) {
                    decoderInputBuffer.waitingForKeys = true;
                    return -3;
                }
                decoderInputBuffer.setFlags(this.flags[relativeIndex]);
                if (this.readPosition == this.length - 1 && (z3 || this.isLastSampleQueued)) {
                    decoderInputBuffer.addFlag(C.BUFFER_FLAG_LAST_SAMPLE);
                }
                decoderInputBuffer.timeUs = this.timesUs[relativeIndex];
                sampleExtrasHolder.size = this.sizes[relativeIndex];
                sampleExtrasHolder.offset = this.offsets[relativeIndex];
                sampleExtrasHolder.cryptoData = this.cryptoDatas[relativeIndex];
                return -4;
            }
            onFormatResult(format2, formatHolder);
            return -5;
        } catch (Throwable th) {
            throw th;
        }
    }

    private void releaseDrmSessionReferences() {
        DrmSession drmSession = this.currentDrmSession;
        if (drmSession != null) {
            drmSession.release(this.drmEventDispatcher);
            this.currentDrmSession = null;
            this.downstreamFormat = null;
        }
    }

    private synchronized void rewind() {
        this.readPosition = 0;
        this.sampleDataQueue.rewind();
    }

    private synchronized boolean setUpstreamFormat(Format format) {
        try {
            this.upstreamFormatRequired = false;
            if (Util.areEqual(format, this.upstreamFormat)) {
                return false;
            }
            if (this.sharedSampleMetadata.isEmpty() || !this.sharedSampleMetadata.getEndValue().format.equals(format)) {
                this.upstreamFormat = format;
            } else {
                this.upstreamFormat = this.sharedSampleMetadata.getEndValue().format;
            }
            boolean z2 = this.allSamplesAreSyncSamples;
            Format format2 = this.upstreamFormat;
            this.allSamplesAreSyncSamples = z2 & MimeTypes.allSamplesAreSyncSamples(format2.sampleMimeType, format2.codecs);
            this.loggedUnexpectedNonSyncSample = false;
            return true;
        } catch (Throwable th) {
            throw th;
        }
    }

    protected final void b() {
        this.upstreamFormatAdjustmentRequired = true;
    }

    public synchronized long discardSampleMetadataToRead() {
        int i2 = this.readPosition;
        if (i2 == 0) {
            return -1L;
        }
        return discardSamples(i2);
    }

    public final void discardTo(long j2, boolean z2, boolean z3) {
        this.sampleDataQueue.discardDownstreamTo(discardSampleMetadataTo(j2, z2, z3));
    }

    public final void discardToEnd() {
        this.sampleDataQueue.discardDownstreamTo(discardSampleMetadataToEnd());
    }

    public final void discardToRead() {
        this.sampleDataQueue.discardDownstreamTo(discardSampleMetadataToRead());
    }

    public final void discardUpstreamFrom(long j2) {
        if (this.length == 0) {
            return;
        }
        Assertions.checkArgument(j2 > getLargestReadTimestampUs());
        discardUpstreamSamples(this.absoluteFirstIndex + countUnreadSamplesBefore(j2));
    }

    public final void discardUpstreamSamples(int i2) {
        this.sampleDataQueue.discardUpstreamSampleBytes(discardUpstreamSampleMetadata(i2));
    }

    @Override // androidx.media3.extractor.TrackOutput
    public final void format(Format format) {
        Format adjustedUpstreamFormat = getAdjustedUpstreamFormat(format);
        this.upstreamFormatAdjustmentRequired = false;
        this.unadjustedUpstreamFormat = format;
        boolean upstreamFormat = setUpstreamFormat(adjustedUpstreamFormat);
        UpstreamFormatChangedListener upstreamFormatChangedListener = this.upstreamFormatChangeListener;
        if (upstreamFormatChangedListener == null || !upstreamFormat) {
            return;
        }
        upstreamFormatChangedListener.onUpstreamFormatChanged(adjustedUpstreamFormat);
    }

    protected Format getAdjustedUpstreamFormat(Format format) {
        return (this.sampleOffsetUs == 0 || format.subsampleOffsetUs == Long.MAX_VALUE) ? format : format.buildUpon().setSubsampleOffsetUs(format.subsampleOffsetUs + this.sampleOffsetUs).build();
    }

    public final int getFirstIndex() {
        return this.absoluteFirstIndex;
    }

    public final synchronized long getFirstTimestampUs() {
        return this.length == 0 ? Long.MIN_VALUE : this.timesUs[this.relativeFirstIndex];
    }

    public final synchronized long getLargestQueuedTimestampUs() {
        return this.largestQueuedTimestampUs;
    }

    public final synchronized long getLargestReadTimestampUs() {
        return Math.max(this.largestDiscardedTimestampUs, getLargestTimestamp(this.readPosition));
    }

    public final int getReadIndex() {
        return this.absoluteFirstIndex + this.readPosition;
    }

    public final synchronized int getSkipCount(long j2, boolean z2) {
        int relativeIndex = getRelativeIndex(this.readPosition);
        if (hasNextSample() && j2 >= this.timesUs[relativeIndex]) {
            if (j2 > this.largestQueuedTimestampUs && z2) {
                return this.length - this.readPosition;
            }
            int iFindSampleBefore = findSampleBefore(relativeIndex, this.length - this.readPosition, j2, true);
            if (iFindSampleBefore == -1) {
                return 0;
            }
            return iFindSampleBefore;
        }
        return 0;
    }

    @Nullable
    public final synchronized Format getUpstreamFormat() {
        return this.upstreamFormatRequired ? null : this.upstreamFormat;
    }

    public final int getWriteIndex() {
        return this.absoluteFirstIndex + this.length;
    }

    public final synchronized boolean isLastSampleQueued() {
        return this.isLastSampleQueued;
    }

    @CallSuper
    public synchronized boolean isReady(boolean z2) {
        Format format;
        boolean z3 = true;
        if (hasNextSample()) {
            if (this.sharedSampleMetadata.get(getReadIndex()).format != this.downstreamFormat) {
                return true;
            }
            return mayReadSample(getRelativeIndex(this.readPosition));
        }
        if (!z2 && !this.isLastSampleQueued && ((format = this.upstreamFormat) == null || format == this.downstreamFormat)) {
            z3 = false;
        }
        return z3;
    }

    @CallSuper
    public void maybeThrowError() throws IOException {
        DrmSession drmSession = this.currentDrmSession;
        if (drmSession != null && drmSession.getState() == 1) {
            throw ((DrmSession.DrmSessionException) Assertions.checkNotNull(this.currentDrmSession.getError()));
        }
    }

    public final synchronized long peekSourceId() {
        try {
        } catch (Throwable th) {
            throw th;
        }
        return hasNextSample() ? this.sourceIds[getRelativeIndex(this.readPosition)] : this.upstreamSourceId;
    }

    @CallSuper
    public void preRelease() {
        discardToEnd();
        releaseDrmSessionReferences();
    }

    @CallSuper
    public int read(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i2, boolean z2) {
        int iPeekSampleMetadata = peekSampleMetadata(formatHolder, decoderInputBuffer, (i2 & 2) != 0, z2, this.extrasHolder);
        if (iPeekSampleMetadata == -4 && !decoderInputBuffer.isEndOfStream()) {
            boolean z3 = (i2 & 1) != 0;
            if ((i2 & 4) == 0) {
                if (z3) {
                    this.sampleDataQueue.peekToBuffer(decoderInputBuffer, this.extrasHolder);
                } else {
                    this.sampleDataQueue.readToBuffer(decoderInputBuffer, this.extrasHolder);
                }
            }
            if (!z3) {
                this.readPosition++;
            }
        }
        return iPeekSampleMetadata;
    }

    @CallSuper
    public void release() {
        reset(true);
        releaseDrmSessionReferences();
    }

    public final void reset() {
        reset(false);
    }

    @Override // androidx.media3.extractor.TrackOutput
    public /* synthetic */ int sampleData(DataReader dataReader, int i2, boolean z2) {
        return androidx.media3.extractor.g.a(this, dataReader, i2, z2);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0054  */
    @Override // androidx.media3.extractor.TrackOutput
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void sampleMetadata(long r12, int r14, int r15, int r16, @androidx.annotation.Nullable androidx.media3.extractor.TrackOutput.CryptoData r17) {
        /*
            r11 = this;
            r8 = r11
            boolean r0 = r8.upstreamFormatAdjustmentRequired
            if (r0 == 0) goto L10
            androidx.media3.common.Format r0 = r8.unadjustedUpstreamFormat
            java.lang.Object r0 = androidx.media3.common.util.Assertions.checkStateNotNull(r0)
            androidx.media3.common.Format r0 = (androidx.media3.common.Format) r0
            r11.format(r0)
        L10:
            r0 = r14 & 1
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L18
            r3 = r2
            goto L19
        L18:
            r3 = r1
        L19:
            boolean r4 = r8.upstreamKeyframeRequired
            if (r4 == 0) goto L22
            if (r3 != 0) goto L20
            return
        L20:
            r8.upstreamKeyframeRequired = r1
        L22:
            long r4 = r8.sampleOffsetUs
            long r4 = r4 + r12
            boolean r6 = r8.allSamplesAreSyncSamples
            if (r6 == 0) goto L54
            long r6 = r8.startTimeUs
            int r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r6 >= 0) goto L30
            return
        L30:
            if (r0 != 0) goto L54
            boolean r0 = r8.loggedUnexpectedNonSyncSample
            if (r0 != 0) goto L50
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r6 = "Overriding unexpected non-sync sample for format: "
            r0.append(r6)
            androidx.media3.common.Format r6 = r8.upstreamFormat
            r0.append(r6)
            java.lang.String r0 = r0.toString()
            java.lang.String r6 = "SampleQueue"
            androidx.media3.common.util.Log.w(r6, r0)
            r8.loggedUnexpectedNonSyncSample = r2
        L50:
            r0 = r14 | 1
            r6 = r0
            goto L55
        L54:
            r6 = r14
        L55:
            boolean r0 = r8.pendingSplice
            if (r0 == 0) goto L66
            if (r3 == 0) goto L65
            boolean r0 = r11.attemptSplice(r4)
            if (r0 != 0) goto L62
            goto L65
        L62:
            r8.pendingSplice = r1
            goto L66
        L65:
            return
        L66:
            androidx.media3.exoplayer.source.SampleDataQueue r0 = r8.sampleDataQueue
            long r0 = r0.getTotalBytesWritten()
            r7 = r15
            long r2 = (long) r7
            long r0 = r0 - r2
            r2 = r16
            long r2 = (long) r2
            long r9 = r0 - r2
            r0 = r11
            r1 = r4
            r3 = r6
            r4 = r9
            r6 = r15
            r7 = r17
            r0.commitSample(r1, r3, r4, r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.exoplayer.source.SampleQueue.sampleMetadata(long, int, int, int, androidx.media3.extractor.TrackOutput$CryptoData):void");
    }

    public final synchronized boolean seekTo(int i2) {
        rewind();
        int i3 = this.absoluteFirstIndex;
        if (i2 >= i3 && i2 <= this.length + i3) {
            this.startTimeUs = Long.MIN_VALUE;
            this.readPosition = i2 - i3;
            return true;
        }
        return false;
    }

    public final void setSampleOffsetUs(long j2) {
        if (this.sampleOffsetUs != j2) {
            this.sampleOffsetUs = j2;
            b();
        }
    }

    public final void setStartTimeUs(long j2) {
        this.startTimeUs = j2;
    }

    public final void setUpstreamFormatChangeListener(@Nullable UpstreamFormatChangedListener upstreamFormatChangedListener) {
        this.upstreamFormatChangeListener = upstreamFormatChangedListener;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x000e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized void skip(int r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            if (r3 < 0) goto Le
            int r0 = r2.readPosition     // Catch: java.lang.Throwable -> Lc
            int r0 = r0 + r3
            int r1 = r2.length     // Catch: java.lang.Throwable -> Lc
            if (r0 > r1) goto Le
            r0 = 1
            goto Lf
        Lc:
            r3 = move-exception
            goto L19
        Le:
            r0 = 0
        Lf:
            androidx.media3.common.util.Assertions.checkArgument(r0)     // Catch: java.lang.Throwable -> Lc
            int r0 = r2.readPosition     // Catch: java.lang.Throwable -> Lc
            int r0 = r0 + r3
            r2.readPosition = r0     // Catch: java.lang.Throwable -> Lc
            monitor-exit(r2)
            return
        L19:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> Lc
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.exoplayer.source.SampleQueue.skip(int):void");
    }

    public final void sourceId(long j2) {
        this.upstreamSourceId = j2;
    }

    public final void splice() {
        this.pendingSplice = true;
    }

    @CallSuper
    public void reset(boolean z2) {
        this.sampleDataQueue.reset();
        this.length = 0;
        this.absoluteFirstIndex = 0;
        this.relativeFirstIndex = 0;
        this.readPosition = 0;
        this.upstreamKeyframeRequired = true;
        this.startTimeUs = Long.MIN_VALUE;
        this.largestDiscardedTimestampUs = Long.MIN_VALUE;
        this.largestQueuedTimestampUs = Long.MIN_VALUE;
        this.isLastSampleQueued = false;
        this.sharedSampleMetadata.clear();
        if (z2) {
            this.unadjustedUpstreamFormat = null;
            this.upstreamFormat = null;
            this.upstreamFormatRequired = true;
            this.allSamplesAreSyncSamples = true;
        }
    }

    @Override // androidx.media3.extractor.TrackOutput
    public /* synthetic */ void sampleData(ParsableByteArray parsableByteArray, int i2) {
        androidx.media3.extractor.g.b(this, parsableByteArray, i2);
    }

    @Override // androidx.media3.extractor.TrackOutput
    public final int sampleData(DataReader dataReader, int i2, boolean z2, int i3) throws IOException {
        return this.sampleDataQueue.sampleData(dataReader, i2, z2);
    }

    @Deprecated
    public static SampleQueue createWithDrm(Allocator allocator, Looper looper, DrmSessionManager drmSessionManager, DrmSessionEventListener.EventDispatcher eventDispatcher) {
        drmSessionManager.setPlayer(looper, PlayerId.UNSET);
        return new SampleQueue(allocator, (DrmSessionManager) Assertions.checkNotNull(drmSessionManager), (DrmSessionEventListener.EventDispatcher) Assertions.checkNotNull(eventDispatcher));
    }

    @Override // androidx.media3.extractor.TrackOutput
    public final void sampleData(ParsableByteArray parsableByteArray, int i2, int i3) {
        this.sampleDataQueue.sampleData(parsableByteArray, i2);
    }

    public final synchronized boolean seekTo(long j2, boolean z2) {
        int iFindSampleBefore;
        try {
            rewind();
            int relativeIndex = getRelativeIndex(this.readPosition);
            if (hasNextSample() && j2 >= this.timesUs[relativeIndex] && (j2 <= this.largestQueuedTimestampUs || z2)) {
                if (this.allSamplesAreSyncSamples) {
                    iFindSampleBefore = findSampleAfter(relativeIndex, this.length - this.readPosition, j2, z2);
                } else {
                    iFindSampleBefore = findSampleBefore(relativeIndex, this.length - this.readPosition, j2, true);
                }
                if (iFindSampleBefore == -1) {
                    return false;
                }
                this.startTimeUs = j2;
                this.readPosition += iFindSampleBefore;
                return true;
            }
            return false;
        } finally {
        }
    }
}
