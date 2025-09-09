package androidx.media3.exoplayer.text;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.Format;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.text.Cue;
import androidx.media3.common.text.CueGroup;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.decoder.DecoderException;
import androidx.media3.decoder.DecoderInputBuffer;
import androidx.media3.exoplayer.BaseRenderer;
import androidx.media3.exoplayer.FormatHolder;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.v2;
import androidx.media3.extractor.text.CueDecoder;
import androidx.media3.extractor.text.CuesWithTiming;
import androidx.media3.extractor.text.SubtitleDecoder;
import androidx.media3.extractor.text.SubtitleDecoderException;
import androidx.media3.extractor.text.SubtitleInputBuffer;
import androidx.media3.extractor.text.SubtitleOutputBuffer;
import com.facebook.share.internal.MessengerShareContentUtility;
import com.google.common.collect.ImmutableList;
import java.nio.ByteBuffer;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;

@UnstableApi
/* loaded from: classes2.dex */
public final class TextRenderer extends BaseRenderer implements Handler.Callback {
    private static final int MSG_UPDATE_OUTPUT = 1;
    private static final int REPLACEMENT_STATE_NONE = 0;
    private static final int REPLACEMENT_STATE_SIGNAL_END_OF_STREAM = 1;
    private static final int REPLACEMENT_STATE_WAIT_END_OF_STREAM = 2;
    private static final String TAG = "TextRenderer";
    private final CueDecoder cueDecoder;
    private final DecoderInputBuffer cueDecoderInputBuffer;
    private CuesResolver cuesResolver;
    private int decoderReplacementState;
    private long finalStreamEndPositionUs;
    private final FormatHolder formatHolder;
    private boolean inputStreamEnded;
    private long lastRendererPositionUs;
    private boolean legacyDecodingEnabled;

    @Nullable
    private SubtitleOutputBuffer nextSubtitle;
    private int nextSubtitleEventIndex;

    @Nullable
    private SubtitleInputBuffer nextSubtitleInputBuffer;
    private final TextOutput output;

    @Nullable
    private final Handler outputHandler;
    private boolean outputStreamEnded;
    private long outputStreamOffsetUs;

    @Nullable
    private Format streamFormat;

    @Nullable
    private SubtitleOutputBuffer subtitle;

    @Nullable
    private SubtitleDecoder subtitleDecoder;
    private final SubtitleDecoderFactory subtitleDecoderFactory;
    private boolean waitingForKeyFrame;

    public TextRenderer(TextOutput textOutput, @Nullable Looper looper) {
        this(textOutput, looper, SubtitleDecoderFactory.DEFAULT);
    }

    @RequiresNonNull({"streamFormat"})
    private void assertLegacyDecodingEnabledIfRequired() {
        Assertions.checkState(this.legacyDecodingEnabled || Objects.equals(this.streamFormat.sampleMimeType, MimeTypes.APPLICATION_CEA608) || Objects.equals(this.streamFormat.sampleMimeType, MimeTypes.APPLICATION_MP4CEA608) || Objects.equals(this.streamFormat.sampleMimeType, MimeTypes.APPLICATION_CEA708), "Legacy decoding is disabled, can't handle " + this.streamFormat.sampleMimeType + " samples (expected " + MimeTypes.APPLICATION_MEDIA3_CUES + ").");
    }

    private void clearOutput() {
        updateOutput(new CueGroup(ImmutableList.of(), getPresentationTimeUs(this.lastRendererPositionUs)));
    }

    @RequiresNonNull({MessengerShareContentUtility.SUBTITLE})
    @SideEffectFree
    private long getCurrentEventTimeUs(long j2) {
        int nextEventTimeIndex = this.subtitle.getNextEventTimeIndex(j2);
        if (nextEventTimeIndex == 0 || this.subtitle.getEventTimeCount() == 0) {
            return this.subtitle.timeUs;
        }
        if (nextEventTimeIndex != -1) {
            return this.subtitle.getEventTime(nextEventTimeIndex - 1);
        }
        return this.subtitle.getEventTime(r2.getEventTimeCount() - 1);
    }

    private long getNextEventTime() {
        if (this.nextSubtitleEventIndex == -1) {
            return Long.MAX_VALUE;
        }
        Assertions.checkNotNull(this.subtitle);
        if (this.nextSubtitleEventIndex >= this.subtitle.getEventTimeCount()) {
            return Long.MAX_VALUE;
        }
        return this.subtitle.getEventTime(this.nextSubtitleEventIndex);
    }

    @SideEffectFree
    private long getPresentationTimeUs(long j2) {
        Assertions.checkState(j2 != C.TIME_UNSET);
        Assertions.checkState(this.outputStreamOffsetUs != C.TIME_UNSET);
        return j2 - this.outputStreamOffsetUs;
    }

    private void handleDecoderError(SubtitleDecoderException subtitleDecoderException) {
        Log.e(TAG, "Subtitle decoding failed. streamFormat=" + this.streamFormat, subtitleDecoderException);
        clearOutput();
        replaceSubtitleDecoder();
    }

    private void initSubtitleDecoder() {
        this.waitingForKeyFrame = true;
        SubtitleDecoder subtitleDecoderCreateDecoder = this.subtitleDecoderFactory.createDecoder((Format) Assertions.checkNotNull(this.streamFormat));
        this.subtitleDecoder = subtitleDecoderCreateDecoder;
        subtitleDecoderCreateDecoder.setOutputStartTimeUs(g());
    }

    private void invokeUpdateOutputInternal(CueGroup cueGroup) {
        this.output.onCues(cueGroup.cues);
        this.output.onCues(cueGroup);
    }

    @SideEffectFree
    private static boolean isCuesWithTiming(Format format) {
        return Objects.equals(format.sampleMimeType, MimeTypes.APPLICATION_MEDIA3_CUES);
    }

    @RequiresNonNull({"this.cuesResolver"})
    private boolean readAndDecodeCuesWithTiming(long j2) {
        if (this.inputStreamEnded || v(this.formatHolder, this.cueDecoderInputBuffer, 0) != -4) {
            return false;
        }
        if (this.cueDecoderInputBuffer.isEndOfStream()) {
            this.inputStreamEnded = true;
            return false;
        }
        this.cueDecoderInputBuffer.flip();
        ByteBuffer byteBuffer = (ByteBuffer) Assertions.checkNotNull(this.cueDecoderInputBuffer.data);
        CuesWithTiming cuesWithTimingDecode = this.cueDecoder.decode(this.cueDecoderInputBuffer.timeUs, byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.limit());
        this.cueDecoderInputBuffer.clear();
        return this.cuesResolver.addCues(cuesWithTimingDecode, j2);
    }

    private void releaseSubtitleBuffers() {
        this.nextSubtitleInputBuffer = null;
        this.nextSubtitleEventIndex = -1;
        SubtitleOutputBuffer subtitleOutputBuffer = this.subtitle;
        if (subtitleOutputBuffer != null) {
            subtitleOutputBuffer.release();
            this.subtitle = null;
        }
        SubtitleOutputBuffer subtitleOutputBuffer2 = this.nextSubtitle;
        if (subtitleOutputBuffer2 != null) {
            subtitleOutputBuffer2.release();
            this.nextSubtitle = null;
        }
    }

    private void releaseSubtitleDecoder() {
        releaseSubtitleBuffers();
        ((SubtitleDecoder) Assertions.checkNotNull(this.subtitleDecoder)).release();
        this.subtitleDecoder = null;
        this.decoderReplacementState = 0;
    }

    @RequiresNonNull({"this.cuesResolver"})
    private void renderFromCuesWithTiming(long j2) {
        boolean andDecodeCuesWithTiming = readAndDecodeCuesWithTiming(j2);
        long nextCueChangeTimeUs = this.cuesResolver.getNextCueChangeTimeUs(this.lastRendererPositionUs);
        if (nextCueChangeTimeUs == Long.MIN_VALUE && this.inputStreamEnded && !andDecodeCuesWithTiming) {
            this.outputStreamEnded = true;
        }
        if (nextCueChangeTimeUs != Long.MIN_VALUE && nextCueChangeTimeUs <= j2) {
            andDecodeCuesWithTiming = true;
        }
        if (andDecodeCuesWithTiming) {
            ImmutableList<Cue> cuesAtTimeUs = this.cuesResolver.getCuesAtTimeUs(j2);
            long previousCueChangeTimeUs = this.cuesResolver.getPreviousCueChangeTimeUs(j2);
            updateOutput(new CueGroup(cuesAtTimeUs, getPresentationTimeUs(previousCueChangeTimeUs)));
            this.cuesResolver.discardCuesBeforeTimeUs(previousCueChangeTimeUs);
        }
        this.lastRendererPositionUs = j2;
    }

    private void renderFromSubtitles(long j2) throws DecoderException {
        boolean z2;
        this.lastRendererPositionUs = j2;
        if (this.nextSubtitle == null) {
            ((SubtitleDecoder) Assertions.checkNotNull(this.subtitleDecoder)).setPositionUs(j2);
            try {
                this.nextSubtitle = ((SubtitleDecoder) Assertions.checkNotNull(this.subtitleDecoder)).dequeueOutputBuffer();
            } catch (SubtitleDecoderException e2) {
                handleDecoderError(e2);
                return;
            }
        }
        if (getState() != 2) {
            return;
        }
        if (this.subtitle != null) {
            long nextEventTime = getNextEventTime();
            z2 = false;
            while (nextEventTime <= j2) {
                this.nextSubtitleEventIndex++;
                nextEventTime = getNextEventTime();
                z2 = true;
            }
        } else {
            z2 = false;
        }
        SubtitleOutputBuffer subtitleOutputBuffer = this.nextSubtitle;
        if (subtitleOutputBuffer != null) {
            if (subtitleOutputBuffer.isEndOfStream()) {
                if (!z2 && getNextEventTime() == Long.MAX_VALUE) {
                    if (this.decoderReplacementState == 2) {
                        replaceSubtitleDecoder();
                    } else {
                        releaseSubtitleBuffers();
                        this.outputStreamEnded = true;
                    }
                }
            } else if (subtitleOutputBuffer.timeUs <= j2) {
                SubtitleOutputBuffer subtitleOutputBuffer2 = this.subtitle;
                if (subtitleOutputBuffer2 != null) {
                    subtitleOutputBuffer2.release();
                }
                this.nextSubtitleEventIndex = subtitleOutputBuffer.getNextEventTimeIndex(j2);
                this.subtitle = subtitleOutputBuffer;
                this.nextSubtitle = null;
                z2 = true;
            }
        }
        if (z2) {
            Assertions.checkNotNull(this.subtitle);
            updateOutput(new CueGroup(this.subtitle.getCues(j2), getPresentationTimeUs(getCurrentEventTimeUs(j2))));
        }
        if (this.decoderReplacementState == 2) {
            return;
        }
        while (!this.inputStreamEnded) {
            try {
                SubtitleInputBuffer subtitleInputBufferDequeueInputBuffer = this.nextSubtitleInputBuffer;
                if (subtitleInputBufferDequeueInputBuffer == null) {
                    subtitleInputBufferDequeueInputBuffer = ((SubtitleDecoder) Assertions.checkNotNull(this.subtitleDecoder)).dequeueInputBuffer();
                    if (subtitleInputBufferDequeueInputBuffer == null) {
                        return;
                    } else {
                        this.nextSubtitleInputBuffer = subtitleInputBufferDequeueInputBuffer;
                    }
                }
                if (this.decoderReplacementState == 1) {
                    subtitleInputBufferDequeueInputBuffer.setFlags(4);
                    ((SubtitleDecoder) Assertions.checkNotNull(this.subtitleDecoder)).queueInputBuffer(subtitleInputBufferDequeueInputBuffer);
                    this.nextSubtitleInputBuffer = null;
                    this.decoderReplacementState = 2;
                    return;
                }
                int iV = v(this.formatHolder, subtitleInputBufferDequeueInputBuffer, 0);
                if (iV == -4) {
                    if (subtitleInputBufferDequeueInputBuffer.isEndOfStream()) {
                        this.inputStreamEnded = true;
                        this.waitingForKeyFrame = false;
                    } else {
                        Format format = this.formatHolder.format;
                        if (format == null) {
                            return;
                        }
                        subtitleInputBufferDequeueInputBuffer.subsampleOffsetUs = format.subsampleOffsetUs;
                        subtitleInputBufferDequeueInputBuffer.flip();
                        this.waitingForKeyFrame &= !subtitleInputBufferDequeueInputBuffer.isKeyFrame();
                    }
                    if (!this.waitingForKeyFrame) {
                        ((SubtitleDecoder) Assertions.checkNotNull(this.subtitleDecoder)).queueInputBuffer(subtitleInputBufferDequeueInputBuffer);
                        this.nextSubtitleInputBuffer = null;
                    }
                } else if (iV == -3) {
                    return;
                }
            } catch (SubtitleDecoderException e3) {
                handleDecoderError(e3);
                return;
            }
        }
    }

    private void replaceSubtitleDecoder() {
        releaseSubtitleDecoder();
        initSubtitleDecoder();
    }

    private void updateOutput(CueGroup cueGroup) {
        Handler handler = this.outputHandler;
        if (handler != null) {
            handler.obtainMessage(1, cueGroup).sendToTarget();
        } else {
            invokeUpdateOutputInternal(cueGroup);
        }
    }

    @Deprecated
    public void experimentalSetLegacyDecodingEnabled(boolean z2) {
        this.legacyDecodingEnabled = z2;
    }

    @Override // androidx.media3.exoplayer.Renderer, androidx.media3.exoplayer.RendererCapabilities
    public String getName() {
        return TAG;
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        if (message.what != 1) {
            throw new IllegalStateException();
        }
        invokeUpdateOutputInternal((CueGroup) message.obj);
        return true;
    }

    @Override // androidx.media3.exoplayer.Renderer
    public boolean isEnded() {
        return this.outputStreamEnded;
    }

    @Override // androidx.media3.exoplayer.Renderer
    public boolean isReady() {
        return true;
    }

    @Override // androidx.media3.exoplayer.BaseRenderer
    protected void k() {
        this.streamFormat = null;
        this.finalStreamEndPositionUs = C.TIME_UNSET;
        clearOutput();
        this.outputStreamOffsetUs = C.TIME_UNSET;
        this.lastRendererPositionUs = C.TIME_UNSET;
        if (this.subtitleDecoder != null) {
            releaseSubtitleDecoder();
        }
    }

    @Override // androidx.media3.exoplayer.BaseRenderer
    protected void n(long j2, boolean z2) {
        this.lastRendererPositionUs = j2;
        CuesResolver cuesResolver = this.cuesResolver;
        if (cuesResolver != null) {
            cuesResolver.clear();
        }
        clearOutput();
        this.inputStreamEnded = false;
        this.outputStreamEnded = false;
        this.finalStreamEndPositionUs = C.TIME_UNSET;
        Format format = this.streamFormat;
        if (format == null || isCuesWithTiming(format)) {
            return;
        }
        if (this.decoderReplacementState != 0) {
            replaceSubtitleDecoder();
            return;
        }
        releaseSubtitleBuffers();
        SubtitleDecoder subtitleDecoder = (SubtitleDecoder) Assertions.checkNotNull(this.subtitleDecoder);
        subtitleDecoder.flush();
        subtitleDecoder.setOutputStartTimeUs(g());
    }

    @Override // androidx.media3.exoplayer.Renderer
    public void render(long j2, long j3) throws DecoderException {
        if (isCurrentStreamFinal()) {
            long j4 = this.finalStreamEndPositionUs;
            if (j4 != C.TIME_UNSET && j2 >= j4) {
                releaseSubtitleBuffers();
                this.outputStreamEnded = true;
            }
        }
        if (this.outputStreamEnded) {
            return;
        }
        if (isCuesWithTiming((Format) Assertions.checkNotNull(this.streamFormat))) {
            Assertions.checkNotNull(this.cuesResolver);
            renderFromCuesWithTiming(j2);
        } else {
            assertLegacyDecodingEnabledIfRequired();
            renderFromSubtitles(j2);
        }
    }

    public void setFinalStreamEndPositionUs(long j2) {
        Assertions.checkState(isCurrentStreamFinal());
        this.finalStreamEndPositionUs = j2;
    }

    @Override // androidx.media3.exoplayer.RendererCapabilities
    public int supportsFormat(Format format) {
        if (isCuesWithTiming(format) || this.subtitleDecoderFactory.supportsFormat(format)) {
            return v2.c(format.cryptoType == 0 ? 4 : 2);
        }
        return MimeTypes.isText(format.sampleMimeType) ? v2.c(1) : v2.c(0);
    }

    @Override // androidx.media3.exoplayer.BaseRenderer
    protected void t(Format[] formatArr, long j2, long j3, MediaSource.MediaPeriodId mediaPeriodId) {
        this.outputStreamOffsetUs = j3;
        Format format = formatArr[0];
        this.streamFormat = format;
        if (isCuesWithTiming(format)) {
            this.cuesResolver = this.streamFormat.cueReplacementBehavior == 1 ? new MergingCuesResolver() : new ReplacingCuesResolver();
            return;
        }
        assertLegacyDecodingEnabledIfRequired();
        if (this.subtitleDecoder != null) {
            this.decoderReplacementState = 1;
        } else {
            initSubtitleDecoder();
        }
    }

    public TextRenderer(TextOutput textOutput, @Nullable Looper looper, SubtitleDecoderFactory subtitleDecoderFactory) {
        super(3);
        this.output = (TextOutput) Assertions.checkNotNull(textOutput);
        this.outputHandler = looper == null ? null : Util.createHandler(looper, this);
        this.subtitleDecoderFactory = subtitleDecoderFactory;
        this.cueDecoder = new CueDecoder();
        this.cueDecoderInputBuffer = new DecoderInputBuffer(1);
        this.formatHolder = new FormatHolder();
        this.finalStreamEndPositionUs = C.TIME_UNSET;
        this.outputStreamOffsetUs = C.TIME_UNSET;
        this.lastRendererPositionUs = C.TIME_UNSET;
        this.legacyDecodingEnabled = false;
    }
}
