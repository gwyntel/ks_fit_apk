package androidx.media3.exoplayer.image;

import android.graphics.Bitmap;
import androidx.media3.common.C;
import androidx.media3.common.Format;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.TraceUtil;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.decoder.DecoderException;
import androidx.media3.decoder.DecoderInputBuffer;
import androidx.media3.exoplayer.BaseRenderer;
import androidx.media3.exoplayer.ExoPlaybackException;
import androidx.media3.exoplayer.FormatHolder;
import androidx.media3.exoplayer.image.ImageDecoder;
import androidx.media3.exoplayer.v2;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

@UnstableApi
/* loaded from: classes.dex */
public class ImageRenderer extends BaseRenderer {
    private static final long IMAGE_PRESENTATION_WINDOW_THRESHOLD_US = 30000;
    private static final int REINITIALIZATION_STATE_NONE = 0;
    private static final int REINITIALIZATION_STATE_SIGNAL_END_OF_STREAM_THEN_WAIT = 2;
    private static final int REINITIALIZATION_STATE_WAIT_END_OF_STREAM = 3;
    private static final String TAG = "ImageRenderer";
    private int currentTileIndex;
    private ImageDecoder decoder;
    private final ImageDecoder.Factory decoderFactory;
    private int decoderReinitializationState;
    private int firstFrameState;
    private final DecoderInputBuffer flagsOnlyBuffer;
    private ImageOutput imageOutput;
    private DecoderInputBuffer inputBuffer;
    private Format inputFormat;
    private boolean inputStreamEnded;
    private long largestQueuedPresentationTimeUs;
    private long lastProcessedOutputBufferTimeUs;
    private TileInfo nextTileInfo;
    private Bitmap outputBitmap;
    private boolean outputStreamEnded;
    private OutputStreamInfo outputStreamInfo;
    private final ArrayDeque<OutputStreamInfo> pendingOutputStreamChanges;
    private boolean readyToOutputTiles;
    private TileInfo tileInfo;

    private static final class OutputStreamInfo {
        public static final OutputStreamInfo UNSET = new OutputStreamInfo(C.TIME_UNSET, C.TIME_UNSET);
        public final long previousStreamLastBufferTimeUs;
        public final long streamOffsetUs;

        public OutputStreamInfo(long j2, long j3) {
            this.previousStreamLastBufferTimeUs = j2;
            this.streamOffsetUs = j3;
        }
    }

    private static class TileInfo {
        private final long presentationTimeUs;
        private Bitmap tileBitmap;
        private final int tileIndex;

        public TileInfo(int i2, long j2) {
            this.tileIndex = i2;
            this.presentationTimeUs = j2;
        }

        public long getPresentationTimeUs() {
            return this.presentationTimeUs;
        }

        public Bitmap getTileBitmap() {
            return this.tileBitmap;
        }

        public int getTileIndex() {
            return this.tileIndex;
        }

        public boolean hasTileBitmap() {
            return this.tileBitmap != null;
        }

        public void setTileBitmap(Bitmap bitmap) {
            this.tileBitmap = bitmap;
        }
    }

    public ImageRenderer(ImageDecoder.Factory factory, ImageOutput imageOutput) {
        super(4);
        this.decoderFactory = factory;
        this.imageOutput = getImageOutput(imageOutput);
        this.flagsOnlyBuffer = DecoderInputBuffer.newNoDataInstance();
        this.outputStreamInfo = OutputStreamInfo.UNSET;
        this.pendingOutputStreamChanges = new ArrayDeque<>();
        this.largestQueuedPresentationTimeUs = C.TIME_UNSET;
        this.lastProcessedOutputBufferTimeUs = C.TIME_UNSET;
        this.decoderReinitializationState = 0;
        this.firstFrameState = 1;
    }

    private boolean canCreateDecoderForFormat(Format format) {
        int iSupportsFormat = this.decoderFactory.supportsFormat(format);
        return iSupportsFormat == v2.c(4) || iSupportsFormat == v2.c(3);
    }

    private Bitmap cropTileFromImageGrid(int i2) {
        Assertions.checkStateNotNull(this.outputBitmap);
        int width = this.outputBitmap.getWidth() / ((Format) Assertions.checkStateNotNull(this.inputFormat)).tileCountHorizontal;
        int height = this.outputBitmap.getHeight() / ((Format) Assertions.checkStateNotNull(this.inputFormat)).tileCountVertical;
        int i3 = this.inputFormat.tileCountHorizontal;
        return Bitmap.createBitmap(this.outputBitmap, (i2 % i3) * width, (i2 / i3) * height, width, height);
    }

    private boolean drainOutput(long j2, long j3) throws ExoPlaybackException, ImageDecoderException {
        if (this.outputBitmap != null && this.tileInfo == null) {
            return false;
        }
        if (this.firstFrameState == 0 && getState() != 2) {
            return false;
        }
        if (this.outputBitmap == null) {
            Assertions.checkStateNotNull(this.decoder);
            ImageOutputBuffer imageOutputBufferDequeueOutputBuffer = this.decoder.dequeueOutputBuffer();
            if (imageOutputBufferDequeueOutputBuffer == null) {
                return false;
            }
            if (((ImageOutputBuffer) Assertions.checkStateNotNull(imageOutputBufferDequeueOutputBuffer)).isEndOfStream()) {
                if (this.decoderReinitializationState == 3) {
                    releaseDecoderResources();
                    Assertions.checkStateNotNull(this.inputFormat);
                    initDecoder();
                } else {
                    ((ImageOutputBuffer) Assertions.checkStateNotNull(imageOutputBufferDequeueOutputBuffer)).release();
                    if (this.pendingOutputStreamChanges.isEmpty()) {
                        this.outputStreamEnded = true;
                    }
                }
                return false;
            }
            Assertions.checkStateNotNull(imageOutputBufferDequeueOutputBuffer.bitmap, "Non-EOS buffer came back from the decoder without bitmap.");
            this.outputBitmap = imageOutputBufferDequeueOutputBuffer.bitmap;
            ((ImageOutputBuffer) Assertions.checkStateNotNull(imageOutputBufferDequeueOutputBuffer)).release();
        }
        if (!this.readyToOutputTiles || this.outputBitmap == null || this.tileInfo == null) {
            return false;
        }
        Assertions.checkStateNotNull(this.inputFormat);
        Format format = this.inputFormat;
        int i2 = format.tileCountHorizontal;
        boolean z2 = ((i2 == 1 && format.tileCountVertical == 1) || i2 == -1 || format.tileCountVertical == -1) ? false : true;
        if (!this.tileInfo.hasTileBitmap()) {
            TileInfo tileInfo = this.tileInfo;
            tileInfo.setTileBitmap(z2 ? cropTileFromImageGrid(tileInfo.getTileIndex()) : (Bitmap) Assertions.checkStateNotNull(this.outputBitmap));
        }
        if (!x(j2, j3, (Bitmap) Assertions.checkStateNotNull(this.tileInfo.getTileBitmap()), this.tileInfo.getPresentationTimeUs())) {
            return false;
        }
        onProcessedOutputBuffer(((TileInfo) Assertions.checkStateNotNull(this.tileInfo)).getPresentationTimeUs());
        this.firstFrameState = 3;
        if (!z2 || ((TileInfo) Assertions.checkStateNotNull(this.tileInfo)).getTileIndex() == (((Format) Assertions.checkStateNotNull(this.inputFormat)).tileCountVertical * ((Format) Assertions.checkStateNotNull(this.inputFormat)).tileCountHorizontal) - 1) {
            this.outputBitmap = null;
        }
        this.tileInfo = this.nextTileInfo;
        this.nextTileInfo = null;
        return true;
    }

    private boolean feedInputBuffer(long j2) throws DecoderException {
        if (this.readyToOutputTiles && this.tileInfo != null) {
            return false;
        }
        FormatHolder formatHolderE = e();
        ImageDecoder imageDecoder = this.decoder;
        if (imageDecoder == null || this.decoderReinitializationState == 3 || this.inputStreamEnded) {
            return false;
        }
        if (this.inputBuffer == null) {
            DecoderInputBuffer decoderInputBufferDequeueInputBuffer = imageDecoder.dequeueInputBuffer();
            this.inputBuffer = decoderInputBufferDequeueInputBuffer;
            if (decoderInputBufferDequeueInputBuffer == null) {
                return false;
            }
        }
        if (this.decoderReinitializationState == 2) {
            Assertions.checkStateNotNull(this.inputBuffer);
            this.inputBuffer.setFlags(4);
            ((ImageDecoder) Assertions.checkStateNotNull(this.decoder)).queueInputBuffer2(this.inputBuffer);
            this.inputBuffer = null;
            this.decoderReinitializationState = 3;
            return false;
        }
        int iV = v(formatHolderE, this.inputBuffer, 0);
        if (iV == -5) {
            this.inputFormat = (Format) Assertions.checkStateNotNull(formatHolderE.format);
            this.decoderReinitializationState = 2;
            return true;
        }
        if (iV != -4) {
            if (iV == -3) {
                return false;
            }
            throw new IllegalStateException();
        }
        this.inputBuffer.flip();
        boolean z2 = ((ByteBuffer) Assertions.checkStateNotNull(this.inputBuffer.data)).remaining() > 0 || ((DecoderInputBuffer) Assertions.checkStateNotNull(this.inputBuffer)).isEndOfStream();
        if (z2) {
            ((ImageDecoder) Assertions.checkStateNotNull(this.decoder)).queueInputBuffer2((DecoderInputBuffer) Assertions.checkStateNotNull(this.inputBuffer));
            this.currentTileIndex = 0;
        }
        maybeAdvanceTileInfo(j2, (DecoderInputBuffer) Assertions.checkStateNotNull(this.inputBuffer));
        if (((DecoderInputBuffer) Assertions.checkStateNotNull(this.inputBuffer)).isEndOfStream()) {
            this.inputStreamEnded = true;
            this.inputBuffer = null;
            return false;
        }
        this.largestQueuedPresentationTimeUs = Math.max(this.largestQueuedPresentationTimeUs, ((DecoderInputBuffer) Assertions.checkStateNotNull(this.inputBuffer)).timeUs);
        if (z2) {
            this.inputBuffer = null;
        } else {
            ((DecoderInputBuffer) Assertions.checkStateNotNull(this.inputBuffer)).clear();
        }
        return !this.readyToOutputTiles;
    }

    private static ImageOutput getImageOutput(ImageOutput imageOutput) {
        return imageOutput == null ? ImageOutput.NO_OP : imageOutput;
    }

    @EnsuresNonNull({"decoder"})
    @RequiresNonNull({"inputFormat"})
    private void initDecoder() throws ExoPlaybackException {
        if (!canCreateDecoderForFormat(this.inputFormat)) {
            throw a(new ImageDecoderException("Provided decoder factory can't create decoder for format."), this.inputFormat, PlaybackException.ERROR_CODE_DECODING_FORMAT_UNSUPPORTED);
        }
        ImageDecoder imageDecoder = this.decoder;
        if (imageDecoder != null) {
            imageDecoder.release();
        }
        this.decoder = this.decoderFactory.createImageDecoder();
    }

    private boolean isTileLastInGrid(TileInfo tileInfo) {
        return ((Format) Assertions.checkStateNotNull(this.inputFormat)).tileCountHorizontal == -1 || this.inputFormat.tileCountVertical == -1 || tileInfo.getTileIndex() == (((Format) Assertions.checkStateNotNull(this.inputFormat)).tileCountVertical * this.inputFormat.tileCountHorizontal) - 1;
    }

    private void lowerFirstFrameState(int i2) {
        this.firstFrameState = Math.min(this.firstFrameState, i2);
    }

    private void maybeAdvanceTileInfo(long j2, DecoderInputBuffer decoderInputBuffer) {
        boolean z2 = true;
        if (decoderInputBuffer.isEndOfStream()) {
            this.readyToOutputTiles = true;
            return;
        }
        TileInfo tileInfo = new TileInfo(this.currentTileIndex, decoderInputBuffer.timeUs);
        this.nextTileInfo = tileInfo;
        this.currentTileIndex++;
        if (!this.readyToOutputTiles) {
            long presentationTimeUs = tileInfo.getPresentationTimeUs();
            boolean z3 = presentationTimeUs - 30000 <= j2 && j2 <= 30000 + presentationTimeUs;
            TileInfo tileInfo2 = this.tileInfo;
            boolean z4 = tileInfo2 != null && tileInfo2.getPresentationTimeUs() <= j2 && j2 < presentationTimeUs;
            boolean zIsTileLastInGrid = isTileLastInGrid((TileInfo) Assertions.checkStateNotNull(this.nextTileInfo));
            if (!z3 && !z4 && !zIsTileLastInGrid) {
                z2 = false;
            }
            this.readyToOutputTiles = z2;
            if (z4 && !z3) {
                return;
            }
        }
        this.tileInfo = this.nextTileInfo;
        this.nextTileInfo = null;
    }

    private void onProcessedOutputBuffer(long j2) {
        this.lastProcessedOutputBufferTimeUs = j2;
        while (!this.pendingOutputStreamChanges.isEmpty() && j2 >= this.pendingOutputStreamChanges.peek().previousStreamLastBufferTimeUs) {
            this.outputStreamInfo = this.pendingOutputStreamChanges.removeFirst();
        }
    }

    private void releaseDecoderResources() {
        this.inputBuffer = null;
        this.decoderReinitializationState = 0;
        this.largestQueuedPresentationTimeUs = C.TIME_UNSET;
        ImageDecoder imageDecoder = this.decoder;
        if (imageDecoder != null) {
            imageDecoder.release();
            this.decoder = null;
        }
    }

    private void setImageOutput(ImageOutput imageOutput) {
        this.imageOutput = getImageOutput(imageOutput);
    }

    private boolean shouldForceRender() {
        boolean z2 = getState() == 2;
        int i2 = this.firstFrameState;
        if (i2 == 0) {
            return z2;
        }
        if (i2 == 1) {
            return true;
        }
        if (i2 == 3) {
            return false;
        }
        throw new IllegalStateException();
    }

    @Override // androidx.media3.exoplayer.Renderer, androidx.media3.exoplayer.RendererCapabilities
    public String getName() {
        return TAG;
    }

    @Override // androidx.media3.exoplayer.BaseRenderer, androidx.media3.exoplayer.PlayerMessage.Target
    public void handleMessage(int i2, Object obj) throws ExoPlaybackException {
        if (i2 != 15) {
            super.handleMessage(i2, obj);
        } else {
            setImageOutput(obj instanceof ImageOutput ? (ImageOutput) obj : null);
        }
    }

    @Override // androidx.media3.exoplayer.Renderer
    public boolean isEnded() {
        return this.outputStreamEnded;
    }

    @Override // androidx.media3.exoplayer.Renderer
    public boolean isReady() {
        int i2 = this.firstFrameState;
        return i2 == 3 || (i2 == 0 && this.readyToOutputTiles);
    }

    @Override // androidx.media3.exoplayer.BaseRenderer
    protected void k() {
        this.inputFormat = null;
        this.outputStreamInfo = OutputStreamInfo.UNSET;
        this.pendingOutputStreamChanges.clear();
        releaseDecoderResources();
        this.imageOutput.onDisabled();
    }

    @Override // androidx.media3.exoplayer.BaseRenderer
    protected void l(boolean z2, boolean z3) {
        this.firstFrameState = z3 ? 1 : 0;
    }

    @Override // androidx.media3.exoplayer.BaseRenderer
    protected void n(long j2, boolean z2) {
        lowerFirstFrameState(1);
        this.outputStreamEnded = false;
        this.inputStreamEnded = false;
        this.outputBitmap = null;
        this.tileInfo = null;
        this.nextTileInfo = null;
        this.readyToOutputTiles = false;
        this.inputBuffer = null;
        ImageDecoder imageDecoder = this.decoder;
        if (imageDecoder != null) {
            imageDecoder.flush();
        }
        this.pendingOutputStreamChanges.clear();
    }

    @Override // androidx.media3.exoplayer.BaseRenderer
    protected void o() {
        releaseDecoderResources();
    }

    @Override // androidx.media3.exoplayer.BaseRenderer
    protected void q() {
        releaseDecoderResources();
        lowerFirstFrameState(1);
    }

    @Override // androidx.media3.exoplayer.Renderer
    public void render(long j2, long j3) throws ExoPlaybackException {
        if (this.outputStreamEnded) {
            return;
        }
        if (this.inputFormat == null) {
            FormatHolder formatHolderE = e();
            this.flagsOnlyBuffer.clear();
            int iV = v(formatHolderE, this.flagsOnlyBuffer, 2);
            if (iV != -5) {
                if (iV == -4) {
                    Assertions.checkState(this.flagsOnlyBuffer.isEndOfStream());
                    this.inputStreamEnded = true;
                    this.outputStreamEnded = true;
                    return;
                }
                return;
            }
            this.inputFormat = (Format) Assertions.checkStateNotNull(formatHolderE.format);
            initDecoder();
        }
        try {
            TraceUtil.beginSection("drainAndFeedDecoder");
            while (drainOutput(j2, j3)) {
            }
            while (feedInputBuffer(j2)) {
            }
            TraceUtil.endSection();
        } catch (ImageDecoderException e2) {
            throw a(e2, null, PlaybackException.ERROR_CODE_DECODING_FAILED);
        }
    }

    @Override // androidx.media3.exoplayer.RendererCapabilities
    public int supportsFormat(Format format) {
        return this.decoderFactory.supportsFormat(format);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0026, code lost:
    
        if (r2 >= r5) goto L14;
     */
    @Override // androidx.media3.exoplayer.BaseRenderer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void t(androidx.media3.common.Format[] r5, long r6, long r8, androidx.media3.exoplayer.source.MediaSource.MediaPeriodId r10) {
        /*
            r4 = this;
            super.t(r5, r6, r8, r10)
            androidx.media3.exoplayer.image.ImageRenderer$OutputStreamInfo r5 = r4.outputStreamInfo
            long r5 = r5.streamOffsetUs
            r0 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r5 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r5 == 0) goto L36
            java.util.ArrayDeque<androidx.media3.exoplayer.image.ImageRenderer$OutputStreamInfo> r5 = r4.pendingOutputStreamChanges
            boolean r5 = r5.isEmpty()
            if (r5 == 0) goto L29
            long r5 = r4.largestQueuedPresentationTimeUs
            int r7 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r7 == 0) goto L36
            long r2 = r4.lastProcessedOutputBufferTimeUs
            int r7 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r7 == 0) goto L29
            int r5 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r5 < 0) goto L29
            goto L36
        L29:
            java.util.ArrayDeque<androidx.media3.exoplayer.image.ImageRenderer$OutputStreamInfo> r5 = r4.pendingOutputStreamChanges
            androidx.media3.exoplayer.image.ImageRenderer$OutputStreamInfo r6 = new androidx.media3.exoplayer.image.ImageRenderer$OutputStreamInfo
            long r0 = r4.largestQueuedPresentationTimeUs
            r6.<init>(r0, r8)
            r5.add(r6)
            goto L3d
        L36:
            androidx.media3.exoplayer.image.ImageRenderer$OutputStreamInfo r5 = new androidx.media3.exoplayer.image.ImageRenderer$OutputStreamInfo
            r5.<init>(r0, r8)
            r4.outputStreamInfo = r5
        L3d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.exoplayer.image.ImageRenderer.t(androidx.media3.common.Format[], long, long, androidx.media3.exoplayer.source.MediaSource$MediaPeriodId):void");
    }

    protected boolean x(long j2, long j3, Bitmap bitmap, long j4) {
        long j5 = j4 - j2;
        if (!shouldForceRender() && j5 >= 30000) {
            return false;
        }
        this.imageOutput.onImageAvailable(j4 - this.outputStreamInfo.streamOffsetUs, bitmap);
        return true;
    }
}
