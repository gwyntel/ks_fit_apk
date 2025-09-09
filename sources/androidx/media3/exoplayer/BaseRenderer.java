package androidx.media3.exoplayer;

import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.media3.common.Format;
import androidx.media3.common.Timeline;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Clock;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.decoder.DecoderInputBuffer;
import androidx.media3.exoplayer.RendererCapabilities;
import androidx.media3.exoplayer.analytics.PlayerId;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.SampleStream;
import com.kingsmith.miot.KsProperty;
import java.io.IOException;

@UnstableApi
/* loaded from: classes.dex */
public abstract class BaseRenderer implements Renderer, RendererCapabilities {
    private Clock clock;

    @Nullable
    private RendererConfiguration configuration;
    private int index;
    private long lastResetPositionUs;
    private PlayerId playerId;

    @Nullable
    @GuardedBy(KsProperty.Lock)
    private RendererCapabilities.Listener rendererCapabilitiesListener;
    private int state;

    @Nullable
    private SampleStream stream;

    @Nullable
    private Format[] streamFormats;
    private boolean streamIsFinal;
    private long streamOffsetUs;
    private boolean throwRendererExceptionIsExecuting;
    private final int trackType;
    private final Object lock = new Object();
    private final FormatHolder formatHolder = new FormatHolder();
    private long readingPositionUs = Long.MIN_VALUE;
    private Timeline timeline = Timeline.EMPTY;

    public BaseRenderer(int i2) {
        this.trackType = i2;
    }

    protected final ExoPlaybackException a(Throwable th, Format format, int i2) {
        return b(th, format, false, i2);
    }

    protected final ExoPlaybackException b(Throwable th, Format format, boolean z2, int i2) {
        int iK;
        if (format == null || this.throwRendererExceptionIsExecuting) {
            iK = 4;
        } else {
            this.throwRendererExceptionIsExecuting = true;
            try {
                iK = v2.k(supportsFormat(format));
            } catch (ExoPlaybackException unused) {
            } finally {
                this.throwRendererExceptionIsExecuting = false;
            }
        }
        return ExoPlaybackException.createForRenderer(th, getName(), f(), format, iK, z2, i2);
    }

    protected final Clock c() {
        return (Clock) Assertions.checkNotNull(this.clock);
    }

    @Override // androidx.media3.exoplayer.RendererCapabilities
    public final void clearListener() {
        synchronized (this.lock) {
            this.rendererCapabilitiesListener = null;
        }
    }

    protected final RendererConfiguration d() {
        return (RendererConfiguration) Assertions.checkNotNull(this.configuration);
    }

    @Override // androidx.media3.exoplayer.Renderer
    public final void disable() {
        Assertions.checkState(this.state == 1);
        this.formatHolder.clear();
        this.state = 0;
        this.stream = null;
        this.streamFormats = null;
        this.streamIsFinal = false;
        k();
    }

    protected final FormatHolder e() {
        this.formatHolder.clear();
        return this.formatHolder;
    }

    @Override // androidx.media3.exoplayer.Renderer
    public final void enable(RendererConfiguration rendererConfiguration, Format[] formatArr, SampleStream sampleStream, long j2, boolean z2, boolean z3, long j3, long j4, MediaSource.MediaPeriodId mediaPeriodId) throws ExoPlaybackException {
        Assertions.checkState(this.state == 0);
        this.configuration = rendererConfiguration;
        this.state = 1;
        l(z2, z3);
        replaceStream(formatArr, sampleStream, j3, j4, mediaPeriodId);
        resetPosition(j3, z2);
    }

    @Override // androidx.media3.exoplayer.Renderer
    public /* synthetic */ void enableMayRenderStartOfStream() {
        u2.a(this);
    }

    protected final int f() {
        return this.index;
    }

    protected final long g() {
        return this.lastResetPositionUs;
    }

    @Override // androidx.media3.exoplayer.Renderer
    public final RendererCapabilities getCapabilities() {
        return this;
    }

    @Override // androidx.media3.exoplayer.Renderer
    public /* synthetic */ long getDurationToProgressUs(long j2, long j3) {
        return u2.b(this, j2, j3);
    }

    @Override // androidx.media3.exoplayer.Renderer
    @Nullable
    public MediaClock getMediaClock() {
        return null;
    }

    @Override // androidx.media3.exoplayer.Renderer
    public final long getReadingPositionUs() {
        return this.readingPositionUs;
    }

    @Override // androidx.media3.exoplayer.Renderer
    public final int getState() {
        return this.state;
    }

    @Override // androidx.media3.exoplayer.Renderer
    @Nullable
    public final SampleStream getStream() {
        return this.stream;
    }

    @Override // androidx.media3.exoplayer.Renderer, androidx.media3.exoplayer.RendererCapabilities
    public final int getTrackType() {
        return this.trackType;
    }

    protected final PlayerId h() {
        return (PlayerId) Assertions.checkNotNull(this.playerId);
    }

    @Override // androidx.media3.exoplayer.PlayerMessage.Target
    public void handleMessage(int i2, @Nullable Object obj) throws ExoPlaybackException {
    }

    @Override // androidx.media3.exoplayer.Renderer
    public final boolean hasReadStreamToEnd() {
        return this.readingPositionUs == Long.MIN_VALUE;
    }

    protected final Format[] i() {
        return (Format[]) Assertions.checkNotNull(this.streamFormats);
    }

    @Override // androidx.media3.exoplayer.Renderer
    public final void init(int i2, PlayerId playerId, Clock clock) {
        this.index = i2;
        this.playerId = playerId;
        this.clock = clock;
        m();
    }

    @Override // androidx.media3.exoplayer.Renderer
    public final boolean isCurrentStreamFinal() {
        return this.streamIsFinal;
    }

    protected final boolean j() {
        return hasReadStreamToEnd() ? this.streamIsFinal : ((SampleStream) Assertions.checkNotNull(this.stream)).isReady();
    }

    protected void k() {
    }

    protected void l(boolean z2, boolean z3) {
    }

    protected void m() {
    }

    @Override // androidx.media3.exoplayer.Renderer
    public final void maybeThrowStreamError() throws IOException {
        ((SampleStream) Assertions.checkNotNull(this.stream)).maybeThrowError();
    }

    protected void n(long j2, boolean z2) {
    }

    protected void o() {
    }

    protected final void p() {
        RendererCapabilities.Listener listener;
        synchronized (this.lock) {
            listener = this.rendererCapabilitiesListener;
        }
        if (listener != null) {
            listener.onRendererCapabilitiesChanged(this);
        }
    }

    protected void q() {
    }

    protected void r() {
    }

    @Override // androidx.media3.exoplayer.Renderer
    public final void release() {
        Assertions.checkState(this.state == 0);
        o();
    }

    @Override // androidx.media3.exoplayer.Renderer
    public final void replaceStream(Format[] formatArr, SampleStream sampleStream, long j2, long j3, MediaSource.MediaPeriodId mediaPeriodId) throws ExoPlaybackException {
        Assertions.checkState(!this.streamIsFinal);
        this.stream = sampleStream;
        if (this.readingPositionUs == Long.MIN_VALUE) {
            this.readingPositionUs = j2;
        }
        this.streamFormats = formatArr;
        this.streamOffsetUs = j3;
        t(formatArr, j2, j3, mediaPeriodId);
    }

    @Override // androidx.media3.exoplayer.Renderer
    public final void reset() {
        Assertions.checkState(this.state == 0);
        this.formatHolder.clear();
        q();
    }

    @Override // androidx.media3.exoplayer.Renderer
    public final void resetPosition(long j2) throws ExoPlaybackException {
        resetPosition(j2, false);
    }

    protected void s() {
    }

    @Override // androidx.media3.exoplayer.Renderer
    public final void setCurrentStreamFinal() {
        this.streamIsFinal = true;
    }

    @Override // androidx.media3.exoplayer.RendererCapabilities
    public final void setListener(RendererCapabilities.Listener listener) {
        synchronized (this.lock) {
            this.rendererCapabilitiesListener = listener;
        }
    }

    @Override // androidx.media3.exoplayer.Renderer
    public /* synthetic */ void setPlaybackSpeed(float f2, float f3) {
        u2.d(this, f2, f3);
    }

    @Override // androidx.media3.exoplayer.Renderer
    public final void setTimeline(Timeline timeline) {
        if (Util.areEqual(this.timeline, timeline)) {
            return;
        }
        this.timeline = timeline;
        u(timeline);
    }

    @Override // androidx.media3.exoplayer.Renderer
    public final void start() throws ExoPlaybackException {
        Assertions.checkState(this.state == 1);
        this.state = 2;
        r();
    }

    @Override // androidx.media3.exoplayer.Renderer
    public final void stop() {
        Assertions.checkState(this.state == 2);
        this.state = 1;
        s();
    }

    @Override // androidx.media3.exoplayer.RendererCapabilities
    public int supportsMixedMimeTypeAdaptation() throws ExoPlaybackException {
        return 0;
    }

    protected void t(Format[] formatArr, long j2, long j3, MediaSource.MediaPeriodId mediaPeriodId) {
    }

    protected void u(Timeline timeline) {
    }

    protected final int v(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i2) {
        int data = ((SampleStream) Assertions.checkNotNull(this.stream)).readData(formatHolder, decoderInputBuffer, i2);
        if (data == -4) {
            if (decoderInputBuffer.isEndOfStream()) {
                this.readingPositionUs = Long.MIN_VALUE;
                return this.streamIsFinal ? -4 : -3;
            }
            long j2 = decoderInputBuffer.timeUs + this.streamOffsetUs;
            decoderInputBuffer.timeUs = j2;
            this.readingPositionUs = Math.max(this.readingPositionUs, j2);
        } else if (data == -5) {
            Format format = (Format) Assertions.checkNotNull(formatHolder.format);
            if (format.subsampleOffsetUs != Long.MAX_VALUE) {
                formatHolder.format = format.buildUpon().setSubsampleOffsetUs(format.subsampleOffsetUs + this.streamOffsetUs).build();
            }
        }
        return data;
    }

    protected int w(long j2) {
        return ((SampleStream) Assertions.checkNotNull(this.stream)).skipData(j2 - this.streamOffsetUs);
    }

    private void resetPosition(long j2, boolean z2) throws ExoPlaybackException {
        this.streamIsFinal = false;
        this.lastResetPositionUs = j2;
        this.readingPositionUs = j2;
        n(j2, z2);
    }
}
