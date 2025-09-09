package androidx.media3.extractor.mp4;

import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.DataReader;
import androidx.media3.common.Format;
import androidx.media3.common.Metadata;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.ParserException;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.container.NalUnitUtil;
import androidx.media3.extractor.Ac4Util;
import androidx.media3.extractor.Extractor;
import androidx.media3.extractor.ExtractorInput;
import androidx.media3.extractor.ExtractorOutput;
import androidx.media3.extractor.ExtractorsFactory;
import androidx.media3.extractor.GaplessInfoHolder;
import androidx.media3.extractor.PositionHolder;
import androidx.media3.extractor.SeekMap;
import androidx.media3.extractor.SniffFailure;
import androidx.media3.extractor.TrackOutput;
import androidx.media3.extractor.TrueHdSampleRechunker;
import androidx.media3.extractor.metadata.mp4.MotionPhotoMetadata;
import androidx.media3.extractor.mp4.Atom;
import androidx.media3.extractor.text.SubtitleParser;
import androidx.media3.extractor.text.SubtitleTranscodingExtractorOutput;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@UnstableApi
/* loaded from: classes2.dex */
public final class Mp4Extractor implements Extractor, SeekMap {

    @Deprecated
    public static final ExtractorsFactory FACTORY = new ExtractorsFactory() { // from class: androidx.media3.extractor.mp4.f
        @Override // androidx.media3.extractor.ExtractorsFactory
        public final Extractor[] createExtractors() {
            return Mp4Extractor.lambda$static$1();
        }

        @Override // androidx.media3.extractor.ExtractorsFactory
        public /* synthetic */ ExtractorsFactory experimentalSetTextTrackTranscodingEnabled(boolean z2) {
            return androidx.media3.extractor.f.b(this, z2);
        }

        @Override // androidx.media3.extractor.ExtractorsFactory
        public /* synthetic */ ExtractorsFactory setSubtitleParserFactory(SubtitleParser.Factory factory) {
            return androidx.media3.extractor.f.c(this, factory);
        }

        @Override // androidx.media3.extractor.ExtractorsFactory
        public /* synthetic */ Extractor[] createExtractors(Uri uri, Map map) {
            return androidx.media3.extractor.f.a(this, uri, map);
        }
    };
    private static final int FILE_TYPE_HEIC = 2;
    private static final int FILE_TYPE_MP4 = 0;
    private static final int FILE_TYPE_QUICKTIME = 1;
    public static final int FLAG_EMIT_RAW_SUBTITLE_DATA = 16;
    public static final int FLAG_MARK_FIRST_VIDEO_TRACK_WITH_MAIN_ROLE = 8;
    public static final int FLAG_READ_MOTION_PHOTO_METADATA = 2;
    public static final int FLAG_READ_SEF_DATA = 4;
    public static final int FLAG_WORKAROUND_IGNORE_EDIT_LISTS = 1;
    private static final long MAXIMUM_READ_AHEAD_BYTES_STREAM = 10485760;
    private static final long RELOAD_MINIMUM_SEEK_DISTANCE = 262144;
    private static final int STATE_READING_ATOM_HEADER = 0;
    private static final int STATE_READING_ATOM_PAYLOAD = 1;
    private static final int STATE_READING_SAMPLE = 2;
    private static final int STATE_READING_SEF = 3;
    private long[][] accumulatedSampleSizes;

    @Nullable
    private ParsableByteArray atomData;
    private final ParsableByteArray atomHeader;
    private int atomHeaderBytesRead;
    private long atomSize;
    private int atomType;
    private final ArrayDeque<Atom.ContainerAtom> containerAtoms;
    private long durationUs;
    private ExtractorOutput extractorOutput;
    private int fileType;
    private int firstVideoTrackIndex;
    private final int flags;
    private ImmutableList<SniffFailure> lastSniffFailures;

    @Nullable
    private MotionPhotoMetadata motionPhotoMetadata;
    private final ParsableByteArray nalLength;
    private final ParsableByteArray nalStartCode;
    private int parserState;
    private int sampleBytesRead;
    private int sampleBytesWritten;
    private int sampleCurrentNalBytesRemaining;
    private int sampleTrackIndex;
    private final ParsableByteArray scratch;
    private boolean seenFtypAtom;
    private final SefReader sefReader;
    private final List<Metadata.Entry> slowMotionMetadataEntries;
    private final SubtitleParser.Factory subtitleParserFactory;
    private Mp4Track[] tracks;

    @Target({ElementType.TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    private static final class Mp4Track {
        public int sampleIndex;
        public final TrackSampleTable sampleTable;
        public final Track track;
        public final TrackOutput trackOutput;

        @Nullable
        public final TrueHdSampleRechunker trueHdSampleRechunker;

        public Mp4Track(Track track, TrackSampleTable trackSampleTable, TrackOutput trackOutput) {
            this.track = track;
            this.sampleTable = trackSampleTable;
            this.trackOutput = trackOutput;
            this.trueHdSampleRechunker = MimeTypes.AUDIO_TRUEHD.equals(track.format.sampleMimeType) ? new TrueHdSampleRechunker() : null;
        }
    }

    @Deprecated
    public Mp4Extractor() {
        this(SubtitleParser.Factory.UNSUPPORTED, 16);
    }

    private static int brandToFileType(int i2) {
        if (i2 != 1751476579) {
            return i2 != 1903435808 ? 0 : 1;
        }
        return 2;
    }

    private static long[][] calculateAccumulatedSampleSizes(Mp4Track[] mp4TrackArr) {
        long[][] jArr = new long[mp4TrackArr.length][];
        int[] iArr = new int[mp4TrackArr.length];
        long[] jArr2 = new long[mp4TrackArr.length];
        boolean[] zArr = new boolean[mp4TrackArr.length];
        for (int i2 = 0; i2 < mp4TrackArr.length; i2++) {
            jArr[i2] = new long[mp4TrackArr[i2].sampleTable.sampleCount];
            jArr2[i2] = mp4TrackArr[i2].sampleTable.timestampsUs[0];
        }
        long j2 = 0;
        int i3 = 0;
        while (i3 < mp4TrackArr.length) {
            long j3 = Long.MAX_VALUE;
            int i4 = -1;
            for (int i5 = 0; i5 < mp4TrackArr.length; i5++) {
                if (!zArr[i5]) {
                    long j4 = jArr2[i5];
                    if (j4 <= j3) {
                        i4 = i5;
                        j3 = j4;
                    }
                }
            }
            int i6 = iArr[i4];
            long[] jArr3 = jArr[i4];
            jArr3[i6] = j2;
            TrackSampleTable trackSampleTable = mp4TrackArr[i4].sampleTable;
            j2 += trackSampleTable.sizes[i6];
            int i7 = i6 + 1;
            iArr[i4] = i7;
            if (i7 < jArr3.length) {
                jArr2[i4] = trackSampleTable.timestampsUs[i7];
            } else {
                zArr[i4] = true;
                i3++;
            }
        }
        return jArr;
    }

    private void enterReadingAtomHeaderState() {
        this.parserState = 0;
        this.atomHeaderBytesRead = 0;
    }

    private static int getSynchronizationSampleIndex(TrackSampleTable trackSampleTable, long j2) {
        int indexOfEarlierOrEqualSynchronizationSample = trackSampleTable.getIndexOfEarlierOrEqualSynchronizationSample(j2);
        return indexOfEarlierOrEqualSynchronizationSample == -1 ? trackSampleTable.getIndexOfLaterOrEqualSynchronizationSample(j2) : indexOfEarlierOrEqualSynchronizationSample;
    }

    private int getTrackIndexOfNextReadSample(long j2) {
        int i2 = -1;
        int i3 = -1;
        int i4 = 0;
        long j3 = Long.MAX_VALUE;
        boolean z2 = true;
        long j4 = Long.MAX_VALUE;
        boolean z3 = true;
        long j5 = Long.MAX_VALUE;
        while (true) {
            Mp4Track[] mp4TrackArr = this.tracks;
            if (i4 >= mp4TrackArr.length) {
                break;
            }
            Mp4Track mp4Track = mp4TrackArr[i4];
            int i5 = mp4Track.sampleIndex;
            TrackSampleTable trackSampleTable = mp4Track.sampleTable;
            if (i5 != trackSampleTable.sampleCount) {
                long j6 = trackSampleTable.offsets[i5];
                long j7 = ((long[][]) Util.castNonNull(this.accumulatedSampleSizes))[i4][i5];
                long j8 = j6 - j2;
                boolean z4 = j8 < 0 || j8 >= 262144;
                if ((!z4 && z3) || (z4 == z3 && j8 < j5)) {
                    z3 = z4;
                    j5 = j8;
                    i3 = i4;
                    j4 = j7;
                }
                if (j7 < j3) {
                    z2 = z4;
                    i2 = i4;
                    j3 = j7;
                }
            }
            i4++;
        }
        return (j3 == Long.MAX_VALUE || !z2 || j4 < j3 + MAXIMUM_READ_AHEAD_BYTES_STREAM) ? i3 : i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Extractor[] lambda$newFactory$0(SubtitleParser.Factory factory) {
        return new Extractor[]{new Mp4Extractor(factory)};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Track lambda$processMoovAtom$2(Track track) {
        return track;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Extractor[] lambda$static$1() {
        return new Extractor[]{new Mp4Extractor(SubtitleParser.Factory.UNSUPPORTED, 16)};
    }

    private static long maybeAdjustSeekOffset(TrackSampleTable trackSampleTable, long j2, long j3) {
        int synchronizationSampleIndex = getSynchronizationSampleIndex(trackSampleTable, j2);
        return synchronizationSampleIndex == -1 ? j3 : Math.min(trackSampleTable.offsets[synchronizationSampleIndex], j3);
    }

    private void maybeSkipRemainingMetaAtomHeaderBytes(ExtractorInput extractorInput) throws IOException {
        this.scratch.reset(8);
        extractorInput.peekFully(this.scratch.getData(), 0, 8);
        AtomParsers.maybeSkipRemainingMetaAtomHeaderBytes(this.scratch);
        extractorInput.skipFully(this.scratch.getPosition());
        extractorInput.resetPeekPosition();
    }

    public static ExtractorsFactory newFactory(final SubtitleParser.Factory factory) {
        return new ExtractorsFactory() { // from class: androidx.media3.extractor.mp4.e
            @Override // androidx.media3.extractor.ExtractorsFactory
            public final Extractor[] createExtractors() {
                return Mp4Extractor.lambda$newFactory$0(factory);
            }

            @Override // androidx.media3.extractor.ExtractorsFactory
            public /* synthetic */ ExtractorsFactory experimentalSetTextTrackTranscodingEnabled(boolean z2) {
                return androidx.media3.extractor.f.b(this, z2);
            }

            @Override // androidx.media3.extractor.ExtractorsFactory
            public /* synthetic */ ExtractorsFactory setSubtitleParserFactory(SubtitleParser.Factory factory2) {
                return androidx.media3.extractor.f.c(this, factory2);
            }

            @Override // androidx.media3.extractor.ExtractorsFactory
            public /* synthetic */ Extractor[] createExtractors(Uri uri, Map map) {
                return androidx.media3.extractor.f.a(this, uri, map);
            }
        };
    }

    private void processAtomEnded(long j2) throws ParserException {
        while (!this.containerAtoms.isEmpty() && this.containerAtoms.peek().endPosition == j2) {
            Atom.ContainerAtom containerAtomPop = this.containerAtoms.pop();
            if (containerAtomPop.type == 1836019574) {
                processMoovAtom(containerAtomPop);
                this.containerAtoms.clear();
                this.parserState = 2;
            } else if (!this.containerAtoms.isEmpty()) {
                this.containerAtoms.peek().add(containerAtomPop);
            }
        }
        if (this.parserState != 2) {
            enterReadingAtomHeaderState();
        }
    }

    private void processEndOfStreamReadingAtomHeader() {
        if (this.fileType != 2 || (this.flags & 2) == 0) {
            return;
        }
        this.extractorOutput.track(0, 4).format(new Format.Builder().setMetadata(this.motionPhotoMetadata == null ? null : new Metadata(this.motionPhotoMetadata)).build());
        this.extractorOutput.endTracks();
        this.extractorOutput.seekMap(new SeekMap.Unseekable(C.TIME_UNSET));
    }

    private static int processFtypAtom(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(8);
        int iBrandToFileType = brandToFileType(parsableByteArray.readInt());
        if (iBrandToFileType != 0) {
            return iBrandToFileType;
        }
        parsableByteArray.skipBytes(4);
        while (parsableByteArray.bytesLeft() > 0) {
            int iBrandToFileType2 = brandToFileType(parsableByteArray.readInt());
            if (iBrandToFileType2 != 0) {
                return iBrandToFileType2;
            }
        }
        return 0;
    }

    private void processMoovAtom(Atom.ContainerAtom containerAtom) throws ParserException {
        Metadata metadata;
        int i2;
        List<TrackSampleTable> list;
        GaplessInfoHolder gaplessInfoHolder;
        int i3;
        int i4;
        ArrayList arrayList = new ArrayList();
        int i5 = 0;
        int i6 = 1;
        boolean z2 = this.fileType == 1;
        GaplessInfoHolder gaplessInfoHolder2 = new GaplessInfoHolder();
        Atom.LeafAtom leafAtomOfType = containerAtom.getLeafAtomOfType(Atom.TYPE_udta);
        if (leafAtomOfType != null) {
            Metadata udta = AtomParsers.parseUdta(leafAtomOfType);
            gaplessInfoHolder2.setFromMetadata(udta);
            metadata = udta;
        } else {
            metadata = null;
        }
        Atom.ContainerAtom containerAtomOfType = containerAtom.getContainerAtomOfType(Atom.TYPE_meta);
        Metadata mdtaFromMeta = containerAtomOfType != null ? AtomParsers.parseMdtaFromMeta(containerAtomOfType) : null;
        Metadata metadata2 = new Metadata(AtomParsers.parseMvhd(((Atom.LeafAtom) Assertions.checkNotNull(containerAtom.getLeafAtomOfType(Atom.TYPE_mvhd))).data));
        boolean z3 = (this.flags & 1) != 0;
        Function function = new Function() { // from class: androidx.media3.extractor.mp4.d
            @Override // com.google.common.base.Function
            public final Object apply(Object obj) {
                return Mp4Extractor.lambda$processMoovAtom$2((Track) obj);
            }
        };
        long j2 = C.TIME_UNSET;
        Metadata metadata3 = mdtaFromMeta;
        List<TrackSampleTable> traks = AtomParsers.parseTraks(containerAtom, gaplessInfoHolder2, C.TIME_UNSET, null, z3, z2, function);
        long jMax = -9223372036854775807L;
        int i7 = 0;
        int size = -1;
        while (i7 < traks.size()) {
            TrackSampleTable trackSampleTable = traks.get(i7);
            if (trackSampleTable.sampleCount == 0) {
                list = traks;
                i2 = i5;
                i3 = i6;
                gaplessInfoHolder = gaplessInfoHolder2;
            } else {
                Track track = trackSampleTable.track;
                GaplessInfoHolder gaplessInfoHolder3 = gaplessInfoHolder2;
                long j3 = track.durationUs;
                if (j3 == j2) {
                    j3 = trackSampleTable.durationUs;
                }
                jMax = Math.max(jMax, j3);
                i2 = i5 + 1;
                list = traks;
                Mp4Track mp4Track = new Mp4Track(track, trackSampleTable, this.extractorOutput.track(i5, track.type));
                int i8 = MimeTypes.AUDIO_TRUEHD.equals(track.format.sampleMimeType) ? trackSampleTable.maximumSize * 16 : trackSampleTable.maximumSize + 30;
                Format.Builder builderBuildUpon = track.format.buildUpon();
                builderBuildUpon.setMaxInputSize(i8);
                if (track.type == 2) {
                    if ((this.flags & 8) != 0) {
                        builderBuildUpon.setRoleFlags(track.format.roleFlags | (size == -1 ? 1 : 2));
                    }
                    if (j3 > 0 && (i4 = trackSampleTable.sampleCount) > 0) {
                        builderBuildUpon.setFrameRate(i4 / (j3 / 1000000.0f));
                    }
                }
                gaplessInfoHolder = gaplessInfoHolder3;
                MetadataUtil.setFormatGaplessInfo(track.type, gaplessInfoHolder, builderBuildUpon);
                MetadataUtil.setFormatMetadata(track.type, metadata3, builderBuildUpon, this.slowMotionMetadataEntries.isEmpty() ? null : new Metadata(this.slowMotionMetadataEntries), metadata, metadata2);
                mp4Track.trackOutput.format(builderBuildUpon.build());
                if (track.type == 2 && size == -1) {
                    size = arrayList.size();
                }
                arrayList.add(mp4Track);
                i3 = 1;
            }
            i7 += i3;
            gaplessInfoHolder2 = gaplessInfoHolder;
            i6 = i3;
            i5 = i2;
            traks = list;
            j2 = C.TIME_UNSET;
        }
        this.firstVideoTrackIndex = size;
        this.durationUs = jMax;
        Mp4Track[] mp4TrackArr = (Mp4Track[]) arrayList.toArray(new Mp4Track[0]);
        this.tracks = mp4TrackArr;
        this.accumulatedSampleSizes = calculateAccumulatedSampleSizes(mp4TrackArr);
        this.extractorOutput.endTracks();
        this.extractorOutput.seekMap(this);
    }

    private void processUnparsedAtom(long j2) {
        if (this.atomType == 1836086884) {
            int i2 = this.atomHeaderBytesRead;
            this.motionPhotoMetadata = new MotionPhotoMetadata(0L, j2, C.TIME_UNSET, j2 + i2, this.atomSize - i2);
        }
    }

    private boolean readAtomHeader(ExtractorInput extractorInput) throws IOException {
        Atom.ContainerAtom containerAtomPeek;
        if (this.atomHeaderBytesRead == 0) {
            if (!extractorInput.readFully(this.atomHeader.getData(), 0, 8, true)) {
                processEndOfStreamReadingAtomHeader();
                return false;
            }
            this.atomHeaderBytesRead = 8;
            this.atomHeader.setPosition(0);
            this.atomSize = this.atomHeader.readUnsignedInt();
            this.atomType = this.atomHeader.readInt();
        }
        long j2 = this.atomSize;
        if (j2 == 1) {
            extractorInput.readFully(this.atomHeader.getData(), 8, 8);
            this.atomHeaderBytesRead += 8;
            this.atomSize = this.atomHeader.readUnsignedLongToLong();
        } else if (j2 == 0) {
            long length = extractorInput.getLength();
            if (length == -1 && (containerAtomPeek = this.containerAtoms.peek()) != null) {
                length = containerAtomPeek.endPosition;
            }
            if (length != -1) {
                this.atomSize = (length - extractorInput.getPosition()) + this.atomHeaderBytesRead;
            }
        }
        if (this.atomSize < this.atomHeaderBytesRead) {
            throw ParserException.createForUnsupportedContainerFeature("Atom size less than header length (unsupported).");
        }
        if (shouldParseContainerAtom(this.atomType)) {
            long position = extractorInput.getPosition();
            long j3 = this.atomSize;
            int i2 = this.atomHeaderBytesRead;
            long j4 = (position + j3) - i2;
            if (j3 != i2 && this.atomType == 1835365473) {
                maybeSkipRemainingMetaAtomHeaderBytes(extractorInput);
            }
            this.containerAtoms.push(new Atom.ContainerAtom(this.atomType, j4));
            if (this.atomSize == this.atomHeaderBytesRead) {
                processAtomEnded(j4);
            } else {
                enterReadingAtomHeaderState();
            }
        } else if (shouldParseLeafAtom(this.atomType)) {
            Assertions.checkState(this.atomHeaderBytesRead == 8);
            Assertions.checkState(this.atomSize <= 2147483647L);
            ParsableByteArray parsableByteArray = new ParsableByteArray((int) this.atomSize);
            System.arraycopy(this.atomHeader.getData(), 0, parsableByteArray.getData(), 0, 8);
            this.atomData = parsableByteArray;
            this.parserState = 1;
        } else {
            processUnparsedAtom(extractorInput.getPosition() - this.atomHeaderBytesRead);
            this.atomData = null;
            this.parserState = 1;
        }
        return true;
    }

    private boolean readAtomPayload(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        boolean z2;
        long j2 = this.atomSize - this.atomHeaderBytesRead;
        long position = extractorInput.getPosition() + j2;
        ParsableByteArray parsableByteArray = this.atomData;
        if (parsableByteArray != null) {
            extractorInput.readFully(parsableByteArray.getData(), this.atomHeaderBytesRead, (int) j2);
            if (this.atomType == 1718909296) {
                this.seenFtypAtom = true;
                this.fileType = processFtypAtom(parsableByteArray);
            } else if (!this.containerAtoms.isEmpty()) {
                this.containerAtoms.peek().add(new Atom.LeafAtom(this.atomType, parsableByteArray));
            }
        } else {
            if (!this.seenFtypAtom && this.atomType == 1835295092) {
                this.fileType = 1;
            }
            if (j2 >= 262144) {
                positionHolder.position = extractorInput.getPosition() + j2;
                z2 = true;
                processAtomEnded(position);
                return (z2 || this.parserState == 2) ? false : true;
            }
            extractorInput.skipFully((int) j2);
        }
        z2 = false;
        processAtomEnded(position);
        if (z2) {
        }
    }

    private int readSample(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        int i2;
        PositionHolder positionHolder2;
        long position = extractorInput.getPosition();
        if (this.sampleTrackIndex == -1) {
            int trackIndexOfNextReadSample = getTrackIndexOfNextReadSample(position);
            this.sampleTrackIndex = trackIndexOfNextReadSample;
            if (trackIndexOfNextReadSample == -1) {
                return -1;
            }
        }
        Mp4Track mp4Track = this.tracks[this.sampleTrackIndex];
        TrackOutput trackOutput = mp4Track.trackOutput;
        int i3 = mp4Track.sampleIndex;
        TrackSampleTable trackSampleTable = mp4Track.sampleTable;
        long j2 = trackSampleTable.offsets[i3];
        int i4 = trackSampleTable.sizes[i3];
        TrueHdSampleRechunker trueHdSampleRechunker = mp4Track.trueHdSampleRechunker;
        long j3 = (j2 - position) + this.sampleBytesRead;
        if (j3 < 0) {
            i2 = 1;
            positionHolder2 = positionHolder;
        } else {
            if (j3 < 262144) {
                if (mp4Track.track.sampleTransformation == 1) {
                    j3 += 8;
                    i4 -= 8;
                }
                extractorInput.skipFully((int) j3);
                Track track = mp4Track.track;
                if (track.nalUnitLengthFieldLength == 0) {
                    if (MimeTypes.AUDIO_AC4.equals(track.format.sampleMimeType)) {
                        if (this.sampleBytesWritten == 0) {
                            Ac4Util.getAc4SampleHeader(i4, this.scratch);
                            trackOutput.sampleData(this.scratch, 7);
                            this.sampleBytesWritten += 7;
                        }
                        i4 += 7;
                    } else if (trueHdSampleRechunker != null) {
                        trueHdSampleRechunker.startSample(extractorInput);
                    }
                    while (true) {
                        int i5 = this.sampleBytesWritten;
                        if (i5 >= i4) {
                            break;
                        }
                        int iSampleData = trackOutput.sampleData((DataReader) extractorInput, i4 - i5, false);
                        this.sampleBytesRead += iSampleData;
                        this.sampleBytesWritten += iSampleData;
                        this.sampleCurrentNalBytesRemaining -= iSampleData;
                    }
                } else {
                    byte[] data = this.nalLength.getData();
                    data[0] = 0;
                    data[1] = 0;
                    data[2] = 0;
                    int i6 = mp4Track.track.nalUnitLengthFieldLength;
                    int i7 = 4 - i6;
                    while (this.sampleBytesWritten < i4) {
                        int i8 = this.sampleCurrentNalBytesRemaining;
                        if (i8 == 0) {
                            extractorInput.readFully(data, i7, i6);
                            this.sampleBytesRead += i6;
                            this.nalLength.setPosition(0);
                            int i9 = this.nalLength.readInt();
                            if (i9 < 0) {
                                throw ParserException.createForMalformedContainer("Invalid NAL length", null);
                            }
                            this.sampleCurrentNalBytesRemaining = i9;
                            this.nalStartCode.setPosition(0);
                            trackOutput.sampleData(this.nalStartCode, 4);
                            this.sampleBytesWritten += 4;
                            i4 += i7;
                        } else {
                            int iSampleData2 = trackOutput.sampleData((DataReader) extractorInput, i8, false);
                            this.sampleBytesRead += iSampleData2;
                            this.sampleBytesWritten += iSampleData2;
                            this.sampleCurrentNalBytesRemaining -= iSampleData2;
                        }
                    }
                }
                int i10 = i4;
                TrackSampleTable trackSampleTable2 = mp4Track.sampleTable;
                long j4 = trackSampleTable2.timestampsUs[i3];
                int i11 = trackSampleTable2.flags[i3];
                if (trueHdSampleRechunker != null) {
                    trueHdSampleRechunker.sampleMetadata(trackOutput, j4, i11, i10, 0, null);
                    if (i3 + 1 == mp4Track.sampleTable.sampleCount) {
                        trueHdSampleRechunker.outputPendingSampleMetadata(trackOutput, null);
                    }
                } else {
                    trackOutput.sampleMetadata(j4, i11, i10, 0, null);
                }
                mp4Track.sampleIndex++;
                this.sampleTrackIndex = -1;
                this.sampleBytesRead = 0;
                this.sampleBytesWritten = 0;
                this.sampleCurrentNalBytesRemaining = 0;
                return 0;
            }
            positionHolder2 = positionHolder;
            i2 = 1;
        }
        positionHolder2.position = j2;
        return i2;
    }

    private int readSefData(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        int i2 = this.sefReader.read(extractorInput, positionHolder, this.slowMotionMetadataEntries);
        if (i2 == 1 && positionHolder.position == 0) {
            enterReadingAtomHeaderState();
        }
        return i2;
    }

    private static boolean shouldParseContainerAtom(int i2) {
        return i2 == 1836019574 || i2 == 1953653099 || i2 == 1835297121 || i2 == 1835626086 || i2 == 1937007212 || i2 == 1701082227 || i2 == 1835365473;
    }

    private static boolean shouldParseLeafAtom(int i2) {
        return i2 == 1835296868 || i2 == 1836476516 || i2 == 1751411826 || i2 == 1937011556 || i2 == 1937011827 || i2 == 1937011571 || i2 == 1668576371 || i2 == 1701606260 || i2 == 1937011555 || i2 == 1937011578 || i2 == 1937013298 || i2 == 1937007471 || i2 == 1668232756 || i2 == 1953196132 || i2 == 1718909296 || i2 == 1969517665 || i2 == 1801812339 || i2 == 1768715124;
    }

    private void updateSampleIndex(Mp4Track mp4Track, long j2) {
        TrackSampleTable trackSampleTable = mp4Track.sampleTable;
        int indexOfEarlierOrEqualSynchronizationSample = trackSampleTable.getIndexOfEarlierOrEqualSynchronizationSample(j2);
        if (indexOfEarlierOrEqualSynchronizationSample == -1) {
            indexOfEarlierOrEqualSynchronizationSample = trackSampleTable.getIndexOfLaterOrEqualSynchronizationSample(j2);
        }
        mp4Track.sampleIndex = indexOfEarlierOrEqualSynchronizationSample;
    }

    @Override // androidx.media3.extractor.SeekMap
    public long getDurationUs() {
        return this.durationUs;
    }

    @Override // androidx.media3.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long j2) {
        return getSeekPoints(j2, -1);
    }

    @Override // androidx.media3.extractor.Extractor
    public /* synthetic */ Extractor getUnderlyingImplementation() {
        return androidx.media3.extractor.d.b(this);
    }

    @Override // androidx.media3.extractor.Extractor
    public void init(ExtractorOutput extractorOutput) {
        if ((this.flags & 16) == 0) {
            extractorOutput = new SubtitleTranscodingExtractorOutput(extractorOutput, this.subtitleParserFactory);
        }
        this.extractorOutput = extractorOutput;
    }

    @Override // androidx.media3.extractor.SeekMap
    public boolean isSeekable() {
        return true;
    }

    @Override // androidx.media3.extractor.Extractor
    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        while (true) {
            int i2 = this.parserState;
            if (i2 != 0) {
                if (i2 != 1) {
                    if (i2 == 2) {
                        return readSample(extractorInput, positionHolder);
                    }
                    if (i2 == 3) {
                        return readSefData(extractorInput, positionHolder);
                    }
                    throw new IllegalStateException();
                }
                if (readAtomPayload(extractorInput, positionHolder)) {
                    return 1;
                }
            } else if (!readAtomHeader(extractorInput)) {
                return -1;
            }
        }
    }

    @Override // androidx.media3.extractor.Extractor
    public void release() {
    }

    @Override // androidx.media3.extractor.Extractor
    public void seek(long j2, long j3) {
        this.containerAtoms.clear();
        this.atomHeaderBytesRead = 0;
        this.sampleTrackIndex = -1;
        this.sampleBytesRead = 0;
        this.sampleBytesWritten = 0;
        this.sampleCurrentNalBytesRemaining = 0;
        if (j2 == 0) {
            if (this.parserState != 3) {
                enterReadingAtomHeaderState();
                return;
            } else {
                this.sefReader.reset();
                this.slowMotionMetadataEntries.clear();
                return;
            }
        }
        for (Mp4Track mp4Track : this.tracks) {
            updateSampleIndex(mp4Track, j3);
            TrueHdSampleRechunker trueHdSampleRechunker = mp4Track.trueHdSampleRechunker;
            if (trueHdSampleRechunker != null) {
                trueHdSampleRechunker.reset();
            }
        }
    }

    @Override // androidx.media3.extractor.Extractor
    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        SniffFailure sniffFailureSniffUnfragmented = Sniffer.sniffUnfragmented(extractorInput, (this.flags & 2) != 0);
        this.lastSniffFailures = sniffFailureSniffUnfragmented != null ? ImmutableList.of(sniffFailureSniffUnfragmented) : ImmutableList.of();
        return sniffFailureSniffUnfragmented == null;
    }

    public Mp4Extractor(SubtitleParser.Factory factory) {
        this(factory, 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x008f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public androidx.media3.extractor.SeekMap.SeekPoints getSeekPoints(long r17, int r19) {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            r3 = r19
            androidx.media3.extractor.mp4.Mp4Extractor$Mp4Track[] r4 = r0.tracks
            int r5 = r4.length
            if (r5 != 0) goto L13
            androidx.media3.extractor.SeekMap$SeekPoints r1 = new androidx.media3.extractor.SeekMap$SeekPoints
            androidx.media3.extractor.SeekPoint r2 = androidx.media3.extractor.SeekPoint.START
            r1.<init>(r2)
            return r1
        L13:
            r5 = -1
            if (r3 == r5) goto L18
            r6 = r3
            goto L1a
        L18:
            int r6 = r0.firstVideoTrackIndex
        L1a:
            r7 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r9 = -1
            if (r6 == r5) goto L58
            r4 = r4[r6]
            androidx.media3.extractor.mp4.TrackSampleTable r4 = r4.sampleTable
            int r6 = getSynchronizationSampleIndex(r4, r1)
            if (r6 != r5) goto L35
            androidx.media3.extractor.SeekMap$SeekPoints r1 = new androidx.media3.extractor.SeekMap$SeekPoints
            androidx.media3.extractor.SeekPoint r2 = androidx.media3.extractor.SeekPoint.START
            r1.<init>(r2)
            return r1
        L35:
            long[] r11 = r4.timestampsUs
            r12 = r11[r6]
            long[] r11 = r4.offsets
            r14 = r11[r6]
            int r11 = (r12 > r1 ? 1 : (r12 == r1 ? 0 : -1))
            if (r11 >= 0) goto L5e
            int r11 = r4.sampleCount
            int r11 = r11 + (-1)
            if (r6 >= r11) goto L5e
            int r1 = r4.getIndexOfLaterOrEqualSynchronizationSample(r1)
            if (r1 == r5) goto L5e
            if (r1 == r6) goto L5e
            long[] r2 = r4.timestampsUs
            r9 = r2[r1]
            long[] r2 = r4.offsets
            r1 = r2[r1]
            goto L60
        L58:
            r14 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            r12 = r1
        L5e:
            r1 = r9
            r9 = r7
        L60:
            if (r3 != r5) goto L80
            r3 = 0
        L63:
            androidx.media3.extractor.mp4.Mp4Extractor$Mp4Track[] r4 = r0.tracks
            int r5 = r4.length
            if (r3 >= r5) goto L80
            int r5 = r0.firstVideoTrackIndex
            if (r3 == r5) goto L7d
            r4 = r4[r3]
            androidx.media3.extractor.mp4.TrackSampleTable r4 = r4.sampleTable
            long r5 = maybeAdjustSeekOffset(r4, r12, r14)
            int r11 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
            if (r11 == 0) goto L7c
            long r1 = maybeAdjustSeekOffset(r4, r9, r1)
        L7c:
            r14 = r5
        L7d:
            int r3 = r3 + 1
            goto L63
        L80:
            androidx.media3.extractor.SeekPoint r3 = new androidx.media3.extractor.SeekPoint
            r3.<init>(r12, r14)
            int r4 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
            if (r4 != 0) goto L8f
            androidx.media3.extractor.SeekMap$SeekPoints r1 = new androidx.media3.extractor.SeekMap$SeekPoints
            r1.<init>(r3)
            return r1
        L8f:
            androidx.media3.extractor.SeekPoint r4 = new androidx.media3.extractor.SeekPoint
            r4.<init>(r9, r1)
            androidx.media3.extractor.SeekMap$SeekPoints r1 = new androidx.media3.extractor.SeekMap$SeekPoints
            r1.<init>(r3, r4)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.extractor.mp4.Mp4Extractor.getSeekPoints(long, int):androidx.media3.extractor.SeekMap$SeekPoints");
    }

    @Override // androidx.media3.extractor.Extractor
    public ImmutableList<SniffFailure> getSniffFailureDetails() {
        return this.lastSniffFailures;
    }

    @Deprecated
    public Mp4Extractor(int i2) {
        this(SubtitleParser.Factory.UNSUPPORTED, i2);
    }

    public Mp4Extractor(SubtitleParser.Factory factory, int i2) {
        this.subtitleParserFactory = factory;
        this.flags = i2;
        this.lastSniffFailures = ImmutableList.of();
        this.parserState = (i2 & 4) != 0 ? 3 : 0;
        this.sefReader = new SefReader();
        this.slowMotionMetadataEntries = new ArrayList();
        this.atomHeader = new ParsableByteArray(16);
        this.containerAtoms = new ArrayDeque<>();
        this.nalStartCode = new ParsableByteArray(NalUnitUtil.NAL_START_CODE);
        this.nalLength = new ParsableByteArray(4);
        this.scratch = new ParsableByteArray();
        this.sampleTrackIndex = -1;
        this.extractorOutput = ExtractorOutput.PLACEHOLDER;
        this.tracks = new Mp4Track[0];
    }
}
