package androidx.media3.extractor.mp4;

import android.util.Pair;
import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.ColorInfo;
import androidx.media3.common.DrmInitData;
import androidx.media3.common.Format;
import androidx.media3.common.Metadata;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.ParserException;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.ParsableBitArray;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.Util;
import androidx.media3.container.MdtaMetadataEntry;
import androidx.media3.container.Mp4LocationData;
import androidx.media3.container.Mp4TimestampData;
import androidx.media3.extractor.AvcConfig;
import androidx.media3.extractor.DolbyVisionConfig;
import androidx.media3.extractor.ExtractorUtil;
import androidx.media3.extractor.GaplessInfoHolder;
import androidx.media3.extractor.HevcConfig;
import androidx.media3.extractor.mp4.Atom;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Ints;
import com.yc.utesdk.ble.close.KeyType;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes2.dex */
final class AtomParsers {
    private static final int MAX_GAPLESS_TRIM_SIZE_SAMPLES = 4;
    private static final String TAG = "AtomParsers";
    private static final int TYPE_clcp = 1668047728;
    private static final int TYPE_mdta = 1835299937;
    private static final int TYPE_meta = 1835365473;
    private static final int TYPE_nclc = 1852009571;
    private static final int TYPE_nclx = 1852009592;
    private static final int TYPE_sbtl = 1935832172;
    private static final int TYPE_soun = 1936684398;
    private static final int TYPE_subt = 1937072756;
    private static final int TYPE_text = 1952807028;
    private static final int TYPE_vide = 1986618469;
    private static final byte[] opusMagic = Util.getUtf8Bytes("OpusHead");

    private static final class ChunkIterator {
        private final ParsableByteArray chunkOffsets;
        private final boolean chunkOffsetsAreLongs;
        public int index;
        public final int length;
        private int nextSamplesPerChunkChangeIndex;
        public int numSamples;
        public long offset;
        private int remainingSamplesPerChunkChanges;
        private final ParsableByteArray stsc;

        public ChunkIterator(ParsableByteArray parsableByteArray, ParsableByteArray parsableByteArray2, boolean z2) throws ParserException {
            this.stsc = parsableByteArray;
            this.chunkOffsets = parsableByteArray2;
            this.chunkOffsetsAreLongs = z2;
            parsableByteArray2.setPosition(12);
            this.length = parsableByteArray2.readUnsignedIntToInt();
            parsableByteArray.setPosition(12);
            this.remainingSamplesPerChunkChanges = parsableByteArray.readUnsignedIntToInt();
            ExtractorUtil.checkContainerInput(parsableByteArray.readInt() == 1, "first_chunk must be 1");
            this.index = -1;
        }

        public boolean moveNext() {
            int i2 = this.index + 1;
            this.index = i2;
            if (i2 == this.length) {
                return false;
            }
            this.offset = this.chunkOffsetsAreLongs ? this.chunkOffsets.readUnsignedLongToLong() : this.chunkOffsets.readUnsignedInt();
            if (this.index == this.nextSamplesPerChunkChangeIndex) {
                this.numSamples = this.stsc.readUnsignedIntToInt();
                this.stsc.skipBytes(4);
                int i3 = this.remainingSamplesPerChunkChanges - 1;
                this.remainingSamplesPerChunkChanges = i3;
                this.nextSamplesPerChunkChangeIndex = i3 > 0 ? this.stsc.readUnsignedIntToInt() - 1 : -1;
            }
            return true;
        }
    }

    private static final class EsdsData {
        private final long bitrate;
        private final byte[] initializationData;
        private final String mimeType;
        private final long peakBitrate;

        public EsdsData(String str, byte[] bArr, long j2, long j3) {
            this.mimeType = str;
            this.initializationData = bArr;
            this.bitrate = j2;
            this.peakBitrate = j3;
        }
    }

    private interface SampleSizeBox {
        int getFixedSampleSize();

        int getSampleCount();

        int readNextSampleSize();
    }

    private static final class StsdData {
        public static final int STSD_HEADER_SIZE = 8;

        @Nullable
        public Format format;
        public int nalUnitLengthFieldLength;
        public int requiredSampleTransformation = 0;
        public final TrackEncryptionBox[] trackEncryptionBoxes;

        public StsdData(int i2) {
            this.trackEncryptionBoxes = new TrackEncryptionBox[i2];
        }
    }

    static final class StszSampleSizeBox implements SampleSizeBox {
        private final ParsableByteArray data;
        private final int fixedSampleSize;
        private final int sampleCount;

        public StszSampleSizeBox(Atom.LeafAtom leafAtom, Format format) {
            ParsableByteArray parsableByteArray = leafAtom.data;
            this.data = parsableByteArray;
            parsableByteArray.setPosition(12);
            int unsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
            if (MimeTypes.AUDIO_RAW.equals(format.sampleMimeType)) {
                int pcmFrameSize = Util.getPcmFrameSize(format.pcmEncoding, format.channelCount);
                if (unsignedIntToInt == 0 || unsignedIntToInt % pcmFrameSize != 0) {
                    Log.w(AtomParsers.TAG, "Audio sample size mismatch. stsd sample size: " + pcmFrameSize + ", stsz sample size: " + unsignedIntToInt);
                    unsignedIntToInt = pcmFrameSize;
                }
            }
            this.fixedSampleSize = unsignedIntToInt == 0 ? -1 : unsignedIntToInt;
            this.sampleCount = parsableByteArray.readUnsignedIntToInt();
        }

        @Override // androidx.media3.extractor.mp4.AtomParsers.SampleSizeBox
        public int getFixedSampleSize() {
            return this.fixedSampleSize;
        }

        @Override // androidx.media3.extractor.mp4.AtomParsers.SampleSizeBox
        public int getSampleCount() {
            return this.sampleCount;
        }

        @Override // androidx.media3.extractor.mp4.AtomParsers.SampleSizeBox
        public int readNextSampleSize() {
            int i2 = this.fixedSampleSize;
            return i2 == -1 ? this.data.readUnsignedIntToInt() : i2;
        }
    }

    static final class Stz2SampleSizeBox implements SampleSizeBox {
        private int currentByte;
        private final ParsableByteArray data;
        private final int fieldSize;
        private final int sampleCount;
        private int sampleIndex;

        public Stz2SampleSizeBox(Atom.LeafAtom leafAtom) {
            ParsableByteArray parsableByteArray = leafAtom.data;
            this.data = parsableByteArray;
            parsableByteArray.setPosition(12);
            this.fieldSize = parsableByteArray.readUnsignedIntToInt() & 255;
            this.sampleCount = parsableByteArray.readUnsignedIntToInt();
        }

        @Override // androidx.media3.extractor.mp4.AtomParsers.SampleSizeBox
        public int getFixedSampleSize() {
            return -1;
        }

        @Override // androidx.media3.extractor.mp4.AtomParsers.SampleSizeBox
        public int getSampleCount() {
            return this.sampleCount;
        }

        @Override // androidx.media3.extractor.mp4.AtomParsers.SampleSizeBox
        public int readNextSampleSize() {
            int i2 = this.fieldSize;
            if (i2 == 8) {
                return this.data.readUnsignedByte();
            }
            if (i2 == 16) {
                return this.data.readUnsignedShort();
            }
            int i3 = this.sampleIndex;
            this.sampleIndex = i3 + 1;
            if (i3 % 2 != 0) {
                return this.currentByte & 15;
            }
            int unsignedByte = this.data.readUnsignedByte();
            this.currentByte = unsignedByte;
            return (unsignedByte & 240) >> 4;
        }
    }

    private static final class TkhdData {
        private final long duration;
        private final int id;
        private final int rotationDegrees;

        public TkhdData(int i2, long j2, int i3) {
            this.id = i2;
            this.duration = j2;
            this.rotationDegrees = i3;
        }
    }

    private AtomParsers() {
    }

    static Pair a(ParsableByteArray parsableByteArray, int i2, int i3) throws ParserException {
        int i4 = i2 + 8;
        int i5 = -1;
        int i6 = 0;
        String string = null;
        Integer numValueOf = null;
        while (i4 - i2 < i3) {
            parsableByteArray.setPosition(i4);
            int i7 = parsableByteArray.readInt();
            int i8 = parsableByteArray.readInt();
            if (i8 == 1718775137) {
                numValueOf = Integer.valueOf(parsableByteArray.readInt());
            } else if (i8 == 1935894637) {
                parsableByteArray.skipBytes(4);
                string = parsableByteArray.readString(4);
            } else if (i8 == 1935894633) {
                i5 = i4;
                i6 = i7;
            }
            i4 += i7;
        }
        if (!C.CENC_TYPE_cenc.equals(string) && !C.CENC_TYPE_cbc1.equals(string) && !C.CENC_TYPE_cens.equals(string) && !C.CENC_TYPE_cbcs.equals(string)) {
            return null;
        }
        ExtractorUtil.checkContainerInput(numValueOf != null, "frma atom is mandatory");
        ExtractorUtil.checkContainerInput(i5 != -1, "schi atom is mandatory");
        TrackEncryptionBox schiFromParent = parseSchiFromParent(parsableByteArray, i5, i6, string);
        ExtractorUtil.checkContainerInput(schiFromParent != null, "tenc atom is mandatory");
        return Pair.create(numValueOf, (TrackEncryptionBox) Util.castNonNull(schiFromParent));
    }

    private static ByteBuffer allocateHdrStaticInfo() {
        return ByteBuffer.allocate(25).order(ByteOrder.LITTLE_ENDIAN);
    }

    private static boolean canApplyEditWithGaplessInfo(long[] jArr, long j2, long j3, long j4) {
        int length = jArr.length - 1;
        return jArr[0] <= j3 && j3 < jArr[Util.constrainValue(4, 0, length)] && jArr[Util.constrainValue(jArr.length - 4, 0, length)] < j4 && j4 <= j2;
    }

    private static boolean canTrimSamplesWithTimestampChange(int i2) {
        return i2 != 1;
    }

    private static int findBoxPosition(ParsableByteArray parsableByteArray, int i2, int i3, int i4) throws ParserException {
        int position = parsableByteArray.getPosition();
        ExtractorUtil.checkContainerInput(position >= i3, null);
        while (position - i3 < i4) {
            parsableByteArray.setPosition(position);
            int i5 = parsableByteArray.readInt();
            ExtractorUtil.checkContainerInput(i5 > 0, "childAtomSize must be positive");
            if (parsableByteArray.readInt() == i2) {
                return position;
            }
            position += i5;
        }
        return -1;
    }

    private static int getTrackTypeForHdlr(int i2) {
        if (i2 == TYPE_soun) {
            return 1;
        }
        if (i2 == TYPE_vide) {
            return 2;
        }
        if (i2 == TYPE_text || i2 == TYPE_sbtl || i2 == TYPE_subt || i2 == TYPE_clcp) {
            return 3;
        }
        return i2 == 1835365473 ? 5 : -1;
    }

    public static void maybeSkipRemainingMetaAtomHeaderBytes(ParsableByteArray parsableByteArray) {
        int position = parsableByteArray.getPosition();
        parsableByteArray.skipBytes(4);
        if (parsableByteArray.readInt() != 1751411826) {
            position += 4;
        }
        parsableByteArray.setPosition(position);
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x013d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void parseAudioSampleEntry(androidx.media3.common.util.ParsableByteArray r24, int r25, int r26, int r27, int r28, java.lang.String r29, boolean r30, @androidx.annotation.Nullable androidx.media3.common.DrmInitData r31, androidx.media3.extractor.mp4.AtomParsers.StsdData r32, int r33) throws androidx.media3.common.ParserException {
        /*
            Method dump skipped, instructions count: 1161
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.extractor.mp4.AtomParsers.parseAudioSampleEntry(androidx.media3.common.util.ParsableByteArray, int, int, int, int, java.lang.String, boolean, androidx.media3.common.DrmInitData, androidx.media3.extractor.mp4.AtomParsers$StsdData, int):void");
    }

    private static ColorInfo parseAv1c(ParsableByteArray parsableByteArray) {
        ColorInfo.Builder builder = new ColorInfo.Builder();
        ParsableBitArray parsableBitArray = new ParsableBitArray(parsableByteArray.getData());
        parsableBitArray.setPosition(parsableByteArray.getPosition() * 8);
        parsableBitArray.skipBytes(1);
        int bits = parsableBitArray.readBits(3);
        parsableBitArray.skipBits(6);
        boolean bit = parsableBitArray.readBit();
        boolean bit2 = parsableBitArray.readBit();
        if (bits == 2 && bit) {
            builder.setLumaBitdepth(bit2 ? 12 : 10);
            builder.setChromaBitdepth(bit2 ? 12 : 10);
        } else if (bits <= 2) {
            builder.setLumaBitdepth(bit ? 10 : 8);
            builder.setChromaBitdepth(bit ? 10 : 8);
        }
        parsableBitArray.skipBits(13);
        parsableBitArray.skipBit();
        int bits2 = parsableBitArray.readBits(4);
        if (bits2 != 1) {
            Log.i(TAG, "Unsupported obu_type: " + bits2);
            return builder.build();
        }
        if (parsableBitArray.readBit()) {
            Log.i(TAG, "Unsupported obu_extension_flag");
            return builder.build();
        }
        boolean bit3 = parsableBitArray.readBit();
        parsableBitArray.skipBit();
        if (bit3 && parsableBitArray.readBits(8) > 127) {
            Log.i(TAG, "Excessive obu_size");
            return builder.build();
        }
        int bits3 = parsableBitArray.readBits(3);
        parsableBitArray.skipBit();
        if (parsableBitArray.readBit()) {
            Log.i(TAG, "Unsupported reduced_still_picture_header");
            return builder.build();
        }
        if (parsableBitArray.readBit()) {
            Log.i(TAG, "Unsupported timing_info_present_flag");
            return builder.build();
        }
        if (parsableBitArray.readBit()) {
            Log.i(TAG, "Unsupported initial_display_delay_present_flag");
            return builder.build();
        }
        int bits4 = parsableBitArray.readBits(5);
        boolean z2 = false;
        for (int i2 = 0; i2 <= bits4; i2++) {
            parsableBitArray.skipBits(12);
            if (parsableBitArray.readBits(5) > 7) {
                parsableBitArray.skipBit();
            }
        }
        int bits5 = parsableBitArray.readBits(4);
        int bits6 = parsableBitArray.readBits(4);
        parsableBitArray.skipBits(bits5 + 1);
        parsableBitArray.skipBits(bits6 + 1);
        if (parsableBitArray.readBit()) {
            parsableBitArray.skipBits(7);
        }
        parsableBitArray.skipBits(7);
        boolean bit4 = parsableBitArray.readBit();
        if (bit4) {
            parsableBitArray.skipBits(2);
        }
        if ((parsableBitArray.readBit() ? 2 : parsableBitArray.readBits(1)) > 0 && !parsableBitArray.readBit()) {
            parsableBitArray.skipBits(1);
        }
        if (bit4) {
            parsableBitArray.skipBits(3);
        }
        parsableBitArray.skipBits(3);
        boolean bit5 = parsableBitArray.readBit();
        if (bits3 == 2 && bit5) {
            parsableBitArray.skipBit();
        }
        if (bits3 != 1 && parsableBitArray.readBit()) {
            z2 = true;
        }
        if (parsableBitArray.readBit()) {
            int bits7 = parsableBitArray.readBits(8);
            int bits8 = parsableBitArray.readBits(8);
            builder.setColorSpace(ColorInfo.isoColorPrimariesToColorSpace(bits7)).setColorRange(((z2 || bits7 != 1 || bits8 != 13 || parsableBitArray.readBits(8) != 0) ? parsableBitArray.readBits(1) : 1) != 1 ? 2 : 1).setColorTransfer(ColorInfo.isoTransferCharacteristicsToColorTransfer(bits8));
        }
        return builder.build();
    }

    @Nullable
    private static Pair<long[], long[]> parseEdts(Atom.ContainerAtom containerAtom) {
        Atom.LeafAtom leafAtomOfType = containerAtom.getLeafAtomOfType(Atom.TYPE_elst);
        if (leafAtomOfType == null) {
            return null;
        }
        ParsableByteArray parsableByteArray = leafAtomOfType.data;
        parsableByteArray.setPosition(8);
        int fullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
        int unsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
        long[] jArr = new long[unsignedIntToInt];
        long[] jArr2 = new long[unsignedIntToInt];
        for (int i2 = 0; i2 < unsignedIntToInt; i2++) {
            jArr[i2] = fullAtomVersion == 1 ? parsableByteArray.readUnsignedLongToLong() : parsableByteArray.readUnsignedInt();
            jArr2[i2] = fullAtomVersion == 1 ? parsableByteArray.readLong() : parsableByteArray.readInt();
            if (parsableByteArray.readShort() != 1) {
                throw new IllegalArgumentException("Unsupported media rate.");
            }
            parsableByteArray.skipBytes(2);
        }
        return Pair.create(jArr, jArr2);
    }

    private static EsdsData parseEsdsFromParent(ParsableByteArray parsableByteArray, int i2) {
        parsableByteArray.setPosition(i2 + 12);
        parsableByteArray.skipBytes(1);
        parseExpandableClassSize(parsableByteArray);
        parsableByteArray.skipBytes(2);
        int unsignedByte = parsableByteArray.readUnsignedByte();
        if ((unsignedByte & 128) != 0) {
            parsableByteArray.skipBytes(2);
        }
        if ((unsignedByte & 64) != 0) {
            parsableByteArray.skipBytes(parsableByteArray.readUnsignedByte());
        }
        if ((unsignedByte & 32) != 0) {
            parsableByteArray.skipBytes(2);
        }
        parsableByteArray.skipBytes(1);
        parseExpandableClassSize(parsableByteArray);
        String mimeTypeFromMp4ObjectType = MimeTypes.getMimeTypeFromMp4ObjectType(parsableByteArray.readUnsignedByte());
        if ("audio/mpeg".equals(mimeTypeFromMp4ObjectType) || MimeTypes.AUDIO_DTS.equals(mimeTypeFromMp4ObjectType) || MimeTypes.AUDIO_DTS_HD.equals(mimeTypeFromMp4ObjectType)) {
            return new EsdsData(mimeTypeFromMp4ObjectType, null, -1L, -1L);
        }
        parsableByteArray.skipBytes(4);
        long unsignedInt = parsableByteArray.readUnsignedInt();
        long unsignedInt2 = parsableByteArray.readUnsignedInt();
        parsableByteArray.skipBytes(1);
        int expandableClassSize = parseExpandableClassSize(parsableByteArray);
        byte[] bArr = new byte[expandableClassSize];
        parsableByteArray.readBytes(bArr, 0, expandableClassSize);
        return new EsdsData(mimeTypeFromMp4ObjectType, bArr, unsignedInt2 > 0 ? unsignedInt2 : -1L, unsignedInt > 0 ? unsignedInt : -1L);
    }

    private static int parseExpandableClassSize(ParsableByteArray parsableByteArray) {
        int unsignedByte = parsableByteArray.readUnsignedByte();
        int i2 = unsignedByte & 127;
        while ((unsignedByte & 128) == 128) {
            unsignedByte = parsableByteArray.readUnsignedByte();
            i2 = (i2 << 7) | (unsignedByte & 127);
        }
        return i2;
    }

    private static int parseHdlr(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(16);
        return parsableByteArray.readInt();
    }

    @Nullable
    private static Metadata parseIlst(ParsableByteArray parsableByteArray, int i2) {
        parsableByteArray.skipBytes(8);
        ArrayList arrayList = new ArrayList();
        while (parsableByteArray.getPosition() < i2) {
            Metadata.Entry ilstElement = MetadataUtil.parseIlstElement(parsableByteArray);
            if (ilstElement != null) {
                arrayList.add(ilstElement);
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new Metadata(arrayList);
    }

    private static Pair<Long, String> parseMdhd(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(8);
        int fullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
        parsableByteArray.skipBytes(fullAtomVersion == 0 ? 8 : 16);
        long unsignedInt = parsableByteArray.readUnsignedInt();
        parsableByteArray.skipBytes(fullAtomVersion == 0 ? 4 : 8);
        int unsignedShort = parsableByteArray.readUnsignedShort();
        return Pair.create(Long.valueOf(unsignedInt), "" + ((char) (((unsignedShort >> 10) & 31) + 96)) + ((char) (((unsignedShort >> 5) & 31) + 96)) + ((char) ((unsignedShort & 31) + 96)));
    }

    @Nullable
    public static Metadata parseMdtaFromMeta(Atom.ContainerAtom containerAtom) {
        Atom.LeafAtom leafAtomOfType = containerAtom.getLeafAtomOfType(Atom.TYPE_hdlr);
        Atom.LeafAtom leafAtomOfType2 = containerAtom.getLeafAtomOfType(Atom.TYPE_keys);
        Atom.LeafAtom leafAtomOfType3 = containerAtom.getLeafAtomOfType(Atom.TYPE_ilst);
        if (leafAtomOfType == null || leafAtomOfType2 == null || leafAtomOfType3 == null || parseHdlr(leafAtomOfType.data) != TYPE_mdta) {
            return null;
        }
        ParsableByteArray parsableByteArray = leafAtomOfType2.data;
        parsableByteArray.setPosition(12);
        int i2 = parsableByteArray.readInt();
        String[] strArr = new String[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            int i4 = parsableByteArray.readInt();
            parsableByteArray.skipBytes(4);
            strArr[i3] = parsableByteArray.readString(i4 - 8);
        }
        ParsableByteArray parsableByteArray2 = leafAtomOfType3.data;
        parsableByteArray2.setPosition(8);
        ArrayList arrayList = new ArrayList();
        while (parsableByteArray2.bytesLeft() > 8) {
            int position = parsableByteArray2.getPosition();
            int i5 = parsableByteArray2.readInt();
            int i6 = parsableByteArray2.readInt() - 1;
            if (i6 < 0 || i6 >= i2) {
                Log.w(TAG, "Skipped metadata with unknown key index: " + i6);
            } else {
                MdtaMetadataEntry mdtaMetadataEntryFromIlst = MetadataUtil.parseMdtaMetadataEntryFromIlst(parsableByteArray2, position + i5, strArr[i6]);
                if (mdtaMetadataEntryFromIlst != null) {
                    arrayList.add(mdtaMetadataEntryFromIlst);
                }
            }
            parsableByteArray2.setPosition(position + i5);
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new Metadata(arrayList);
    }

    private static void parseMetaDataSampleEntry(ParsableByteArray parsableByteArray, int i2, int i3, int i4, StsdData stsdData) {
        parsableByteArray.setPosition(i3 + 16);
        if (i2 == 1835365492) {
            parsableByteArray.readNullTerminatedString();
            String nullTerminatedString = parsableByteArray.readNullTerminatedString();
            if (nullTerminatedString != null) {
                stsdData.format = new Format.Builder().setId(i4).setSampleMimeType(nullTerminatedString).build();
            }
        }
    }

    public static Mp4TimestampData parseMvhd(ParsableByteArray parsableByteArray) {
        long unsignedInt;
        long unsignedInt2;
        parsableByteArray.setPosition(8);
        if (Atom.parseFullAtomVersion(parsableByteArray.readInt()) == 0) {
            unsignedInt = parsableByteArray.readUnsignedInt();
            unsignedInt2 = parsableByteArray.readUnsignedInt();
        } else {
            unsignedInt = parsableByteArray.readLong();
            unsignedInt2 = parsableByteArray.readLong();
        }
        return new Mp4TimestampData(unsignedInt, unsignedInt2, parsableByteArray.readUnsignedInt());
    }

    private static float parsePaspFromParent(ParsableByteArray parsableByteArray, int i2) {
        parsableByteArray.setPosition(i2 + 8);
        return parsableByteArray.readUnsignedIntToInt() / parsableByteArray.readUnsignedIntToInt();
    }

    @Nullable
    private static byte[] parseProjFromParent(ParsableByteArray parsableByteArray, int i2, int i3) {
        int i4 = i2 + 8;
        while (i4 - i2 < i3) {
            parsableByteArray.setPosition(i4);
            int i5 = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == 1886547818) {
                return Arrays.copyOfRange(parsableByteArray.getData(), i4, i5 + i4);
            }
            i4 += i5;
        }
        return null;
    }

    @Nullable
    private static Pair<Integer, TrackEncryptionBox> parseSampleEntryEncryptionData(ParsableByteArray parsableByteArray, int i2, int i3) throws ParserException {
        Pair<Integer, TrackEncryptionBox> pairA;
        int position = parsableByteArray.getPosition();
        while (position - i2 < i3) {
            parsableByteArray.setPosition(position);
            int i4 = parsableByteArray.readInt();
            ExtractorUtil.checkContainerInput(i4 > 0, "childAtomSize must be positive");
            if (parsableByteArray.readInt() == 1936289382 && (pairA = a(parsableByteArray, position, i4)) != null) {
                return pairA;
            }
            position += i4;
        }
        return null;
    }

    @Nullable
    private static TrackEncryptionBox parseSchiFromParent(ParsableByteArray parsableByteArray, int i2, int i3, String str) {
        int i4;
        int i5;
        int i6 = i2 + 8;
        while (true) {
            byte[] bArr = null;
            if (i6 - i2 >= i3) {
                return null;
            }
            parsableByteArray.setPosition(i6);
            int i7 = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == 1952804451) {
                int fullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
                parsableByteArray.skipBytes(1);
                if (fullAtomVersion == 0) {
                    parsableByteArray.skipBytes(1);
                    i5 = 0;
                    i4 = 0;
                } else {
                    int unsignedByte = parsableByteArray.readUnsignedByte();
                    i4 = unsignedByte & 15;
                    i5 = (unsignedByte & 240) >> 4;
                }
                boolean z2 = parsableByteArray.readUnsignedByte() == 1;
                int unsignedByte2 = parsableByteArray.readUnsignedByte();
                byte[] bArr2 = new byte[16];
                parsableByteArray.readBytes(bArr2, 0, 16);
                if (z2 && unsignedByte2 == 0) {
                    int unsignedByte3 = parsableByteArray.readUnsignedByte();
                    bArr = new byte[unsignedByte3];
                    parsableByteArray.readBytes(bArr, 0, unsignedByte3);
                }
                return new TrackEncryptionBox(z2, str, unsignedByte2, bArr2, i5, i4, bArr);
            }
            i6 += i7;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:107:0x0248  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0289  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x028c  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x03b3  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x03b5  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x03cd  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0434  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x0439  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x043c  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x043f  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0442  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x0445  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0447  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x044b  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x044e  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x045d  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x0429 A[EDGE_INSN: B:215:0x0429->B:170:0x0429 BREAK  A[LOOP:2: B:153:0x03c8->B:169:0x0422], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static androidx.media3.extractor.mp4.TrackSampleTable parseStbl(androidx.media3.extractor.mp4.Track r37, androidx.media3.extractor.mp4.Atom.ContainerAtom r38, androidx.media3.extractor.GaplessInfoHolder r39) throws androidx.media3.common.ParserException {
        /*
            Method dump skipped, instructions count: 1326
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.extractor.mp4.AtomParsers.parseStbl(androidx.media3.extractor.mp4.Track, androidx.media3.extractor.mp4.Atom$ContainerAtom, androidx.media3.extractor.GaplessInfoHolder):androidx.media3.extractor.mp4.TrackSampleTable");
    }

    private static StsdData parseStsd(ParsableByteArray parsableByteArray, int i2, int i3, String str, @Nullable DrmInitData drmInitData, boolean z2) throws ParserException {
        int i4;
        parsableByteArray.setPosition(12);
        int i5 = parsableByteArray.readInt();
        StsdData stsdData = new StsdData(i5);
        for (int i6 = 0; i6 < i5; i6++) {
            int position = parsableByteArray.getPosition();
            int i7 = parsableByteArray.readInt();
            ExtractorUtil.checkContainerInput(i7 > 0, "childAtomSize must be positive");
            int i8 = parsableByteArray.readInt();
            if (i8 == 1635148593 || i8 == 1635148595 || i8 == 1701733238 || i8 == 1831958048 || i8 == 1836070006 || i8 == 1752589105 || i8 == 1751479857 || i8 == 1932670515 || i8 == 1211250227 || i8 == 1987063864 || i8 == 1987063865 || i8 == 1635135537 || i8 == 1685479798 || i8 == 1685479729 || i8 == 1685481573 || i8 == 1685481521) {
                i4 = position;
                parseVideoSampleEntry(parsableByteArray, i8, i4, i7, i2, i3, drmInitData, stsdData, i6);
            } else if (i8 == 1836069985 || i8 == 1701733217 || i8 == 1633889587 || i8 == 1700998451 || i8 == 1633889588 || i8 == 1835823201 || i8 == 1685353315 || i8 == 1685353317 || i8 == 1685353320 || i8 == 1685353324 || i8 == 1685353336 || i8 == 1935764850 || i8 == 1935767394 || i8 == 1819304813 || i8 == 1936684916 || i8 == 1953984371 || i8 == 778924082 || i8 == 778924083 || i8 == 1835557169 || i8 == 1835560241 || i8 == 1634492771 || i8 == 1634492791 || i8 == 1970037111 || i8 == 1332770163 || i8 == 1716281667) {
                i4 = position;
                parseAudioSampleEntry(parsableByteArray, i8, position, i7, i2, str, z2, drmInitData, stsdData, i6);
            } else {
                if (i8 == 1414810956 || i8 == 1954034535 || i8 == 2004251764 || i8 == 1937010800 || i8 == 1664495672) {
                    parseTextSampleEntry(parsableByteArray, i8, position, i7, i2, str, stsdData);
                } else if (i8 == 1835365492) {
                    parseMetaDataSampleEntry(parsableByteArray, i8, position, i2, stsdData);
                } else if (i8 == 1667329389) {
                    stsdData.format = new Format.Builder().setId(i2).setSampleMimeType(MimeTypes.APPLICATION_CAMERA_MOTION).build();
                }
                i4 = position;
            }
            parsableByteArray.setPosition(i4 + i7);
        }
        return stsdData;
    }

    private static void parseTextSampleEntry(ParsableByteArray parsableByteArray, int i2, int i3, int i4, int i5, String str, StsdData stsdData) {
        parsableByteArray.setPosition(i3 + 16);
        String str2 = MimeTypes.APPLICATION_TTML;
        ImmutableList immutableListOf = null;
        long j2 = Long.MAX_VALUE;
        if (i2 != 1414810956) {
            if (i2 == 1954034535) {
                int i6 = i4 - 16;
                byte[] bArr = new byte[i6];
                parsableByteArray.readBytes(bArr, 0, i6);
                immutableListOf = ImmutableList.of(bArr);
                str2 = MimeTypes.APPLICATION_TX3G;
            } else if (i2 == 2004251764) {
                str2 = MimeTypes.APPLICATION_MP4VTT;
            } else if (i2 == 1937010800) {
                j2 = 0;
            } else {
                if (i2 != 1664495672) {
                    throw new IllegalStateException();
                }
                stsdData.requiredSampleTransformation = 1;
                str2 = MimeTypes.APPLICATION_MP4CEA608;
            }
        }
        stsdData.format = new Format.Builder().setId(i5).setSampleMimeType(str2).setLanguage(str).setSubsampleOffsetUs(j2).setInitializationData(immutableListOf).build();
    }

    private static TkhdData parseTkhd(ParsableByteArray parsableByteArray) {
        long j2;
        parsableByteArray.setPosition(8);
        int fullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
        parsableByteArray.skipBytes(fullAtomVersion == 0 ? 8 : 16);
        int i2 = parsableByteArray.readInt();
        parsableByteArray.skipBytes(4);
        int position = parsableByteArray.getPosition();
        int i3 = fullAtomVersion == 0 ? 4 : 8;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            j2 = C.TIME_UNSET;
            if (i5 >= i3) {
                parsableByteArray.skipBytes(i3);
                break;
            }
            if (parsableByteArray.getData()[position + i5] != -1) {
                long unsignedInt = fullAtomVersion == 0 ? parsableByteArray.readUnsignedInt() : parsableByteArray.readUnsignedLongToLong();
                if (unsignedInt != 0) {
                    j2 = unsignedInt;
                }
            } else {
                i5++;
            }
        }
        parsableByteArray.skipBytes(16);
        int i6 = parsableByteArray.readInt();
        int i7 = parsableByteArray.readInt();
        parsableByteArray.skipBytes(4);
        int i8 = parsableByteArray.readInt();
        int i9 = parsableByteArray.readInt();
        if (i6 == 0 && i7 == 65536 && i8 == -65536 && i9 == 0) {
            i4 = 90;
        } else if (i6 == 0 && i7 == -65536 && i8 == 65536 && i9 == 0) {
            i4 = KeyType.QUERY_BRIGHT_SCREEN_PARAM;
        } else if (i6 == -65536 && i7 == 0 && i8 == 0 && i9 == -65536) {
            i4 = 180;
        }
        return new TkhdData(i2, j2, i4);
    }

    @Nullable
    private static Track parseTrak(Atom.ContainerAtom containerAtom, Atom.LeafAtom leafAtom, long j2, @Nullable DrmInitData drmInitData, boolean z2, boolean z3) throws ParserException {
        Atom.LeafAtom leafAtom2;
        long j3;
        long[] jArr;
        long[] jArr2;
        Atom.ContainerAtom containerAtomOfType;
        Pair<long[], long[]> edts;
        Atom.ContainerAtom containerAtom2 = (Atom.ContainerAtom) Assertions.checkNotNull(containerAtom.getContainerAtomOfType(Atom.TYPE_mdia));
        int trackTypeForHdlr = getTrackTypeForHdlr(parseHdlr(((Atom.LeafAtom) Assertions.checkNotNull(containerAtom2.getLeafAtomOfType(Atom.TYPE_hdlr))).data));
        if (trackTypeForHdlr == -1) {
            return null;
        }
        TkhdData tkhd = parseTkhd(((Atom.LeafAtom) Assertions.checkNotNull(containerAtom.getLeafAtomOfType(Atom.TYPE_tkhd))).data);
        long jScaleLargeTimestamp = C.TIME_UNSET;
        if (j2 == C.TIME_UNSET) {
            leafAtom2 = leafAtom;
            j3 = tkhd.duration;
        } else {
            leafAtom2 = leafAtom;
            j3 = j2;
        }
        long j4 = parseMvhd(leafAtom2.data).timescale;
        if (j3 != C.TIME_UNSET) {
            jScaleLargeTimestamp = Util.scaleLargeTimestamp(j3, 1000000L, j4);
        }
        long j5 = jScaleLargeTimestamp;
        Atom.ContainerAtom containerAtom3 = (Atom.ContainerAtom) Assertions.checkNotNull(((Atom.ContainerAtom) Assertions.checkNotNull(containerAtom2.getContainerAtomOfType(Atom.TYPE_minf))).getContainerAtomOfType(Atom.TYPE_stbl));
        Pair<Long, String> mdhd = parseMdhd(((Atom.LeafAtom) Assertions.checkNotNull(containerAtom2.getLeafAtomOfType(Atom.TYPE_mdhd))).data);
        Atom.LeafAtom leafAtomOfType = containerAtom3.getLeafAtomOfType(Atom.TYPE_stsd);
        if (leafAtomOfType == null) {
            throw ParserException.createForMalformedContainer("Malformed sample table (stbl) missing sample description (stsd)", null);
        }
        StsdData stsd = parseStsd(leafAtomOfType.data, tkhd.id, tkhd.rotationDegrees, (String) mdhd.second, drmInitData, z3);
        if (z2 || (containerAtomOfType = containerAtom.getContainerAtomOfType(Atom.TYPE_edts)) == null || (edts = parseEdts(containerAtomOfType)) == null) {
            jArr = null;
            jArr2 = null;
        } else {
            long[] jArr3 = (long[]) edts.first;
            jArr2 = (long[]) edts.second;
            jArr = jArr3;
        }
        if (stsd.format == null) {
            return null;
        }
        return new Track(tkhd.id, trackTypeForHdlr, ((Long) mdhd.first).longValue(), j4, j5, stsd.format, stsd.requiredSampleTransformation, stsd.trackEncryptionBoxes, stsd.nalUnitLengthFieldLength, jArr, jArr2);
    }

    public static List<TrackSampleTable> parseTraks(Atom.ContainerAtom containerAtom, GaplessInfoHolder gaplessInfoHolder, long j2, @Nullable DrmInitData drmInitData, boolean z2, boolean z3, Function<Track, Track> function) throws ParserException {
        Track trackApply;
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < containerAtom.containerChildren.size(); i2++) {
            Atom.ContainerAtom containerAtom2 = containerAtom.containerChildren.get(i2);
            if (containerAtom2.type == 1953653099 && (trackApply = function.apply(parseTrak(containerAtom2, (Atom.LeafAtom) Assertions.checkNotNull(containerAtom.getLeafAtomOfType(Atom.TYPE_mvhd)), j2, drmInitData, z2, z3))) != null) {
                arrayList.add(parseStbl(trackApply, (Atom.ContainerAtom) Assertions.checkNotNull(((Atom.ContainerAtom) Assertions.checkNotNull(((Atom.ContainerAtom) Assertions.checkNotNull(containerAtom2.getContainerAtomOfType(Atom.TYPE_mdia))).getContainerAtomOfType(Atom.TYPE_minf))).getContainerAtomOfType(Atom.TYPE_stbl)), gaplessInfoHolder));
            }
        }
        return arrayList;
    }

    public static Metadata parseUdta(Atom.LeafAtom leafAtom) {
        ParsableByteArray parsableByteArray = leafAtom.data;
        parsableByteArray.setPosition(8);
        Metadata metadata = new Metadata(new Metadata.Entry[0]);
        while (parsableByteArray.bytesLeft() >= 8) {
            int position = parsableByteArray.getPosition();
            int i2 = parsableByteArray.readInt();
            int i3 = parsableByteArray.readInt();
            if (i3 == 1835365473) {
                parsableByteArray.setPosition(position);
                metadata = metadata.copyWithAppendedEntriesFrom(parseUdtaMeta(parsableByteArray, position + i2));
            } else if (i3 == 1936553057) {
                parsableByteArray.setPosition(position);
                metadata = metadata.copyWithAppendedEntriesFrom(SmtaAtomUtil.parseSmta(parsableByteArray, position + i2));
            } else if (i3 == -1451722374) {
                metadata = metadata.copyWithAppendedEntriesFrom(parseXyz(parsableByteArray));
            }
            parsableByteArray.setPosition(position + i2);
        }
        return metadata;
    }

    @Nullable
    private static Metadata parseUdtaMeta(ParsableByteArray parsableByteArray, int i2) {
        parsableByteArray.skipBytes(8);
        maybeSkipRemainingMetaAtomHeaderBytes(parsableByteArray);
        while (parsableByteArray.getPosition() < i2) {
            int position = parsableByteArray.getPosition();
            int i3 = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == 1768715124) {
                parsableByteArray.setPosition(position);
                return parseIlst(parsableByteArray, position + i3);
            }
            parsableByteArray.setPosition(position + i3);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static void parseVideoSampleEntry(ParsableByteArray parsableByteArray, int i2, int i3, int i4, int i5, int i6, @Nullable DrmInitData drmInitData, StsdData stsdData, int i7) throws ParserException {
        DrmInitData drmInitData2;
        int i8;
        String str;
        float f2;
        int i9;
        int i10;
        int i11;
        int i12 = i3;
        int i13 = i4;
        DrmInitData drmInitDataCopyWithSchemeType = drmInitData;
        StsdData stsdData2 = stsdData;
        parsableByteArray.setPosition(i12 + 16);
        parsableByteArray.skipBytes(16);
        int unsignedShort = parsableByteArray.readUnsignedShort();
        int unsignedShort2 = parsableByteArray.readUnsignedShort();
        parsableByteArray.skipBytes(50);
        int position = parsableByteArray.getPosition();
        int iIntValue = i2;
        if (iIntValue == 1701733238) {
            Pair<Integer, TrackEncryptionBox> sampleEntryEncryptionData = parseSampleEntryEncryptionData(parsableByteArray, i12, i13);
            if (sampleEntryEncryptionData != null) {
                iIntValue = ((Integer) sampleEntryEncryptionData.first).intValue();
                drmInitDataCopyWithSchemeType = drmInitDataCopyWithSchemeType == null ? null : drmInitDataCopyWithSchemeType.copyWithSchemeType(((TrackEncryptionBox) sampleEntryEncryptionData.second).schemeType);
                stsdData2.trackEncryptionBoxes[i7] = (TrackEncryptionBox) sampleEntryEncryptionData.second;
            }
            parsableByteArray.setPosition(position);
        }
        String str2 = MimeTypes.VIDEO_H263;
        String str3 = iIntValue == 1831958048 ? MimeTypes.VIDEO_MPEG : iIntValue == 1211250227 ? MimeTypes.VIDEO_H263 : null;
        float paspFromParent = 1.0f;
        int i14 = 8;
        int i15 = 8;
        List listOf = null;
        String str4 = null;
        byte[] projFromParent = null;
        int i16 = -1;
        int i17 = -1;
        int iIsoColorPrimariesToColorSpace = -1;
        int i18 = -1;
        int iIsoTransferCharacteristicsToColorTransfer = -1;
        ByteBuffer byteBufferAllocateHdrStaticInfo = null;
        EsdsData esdsFromParent = null;
        boolean z2 = false;
        while (position - i12 < i13) {
            parsableByteArray.setPosition(position);
            int position2 = parsableByteArray.getPosition();
            int i19 = parsableByteArray.readInt();
            if (i19 == 0 && parsableByteArray.getPosition() - i12 == i13) {
                break;
            }
            ExtractorUtil.checkContainerInput(i19 > 0, "childAtomSize must be positive");
            int i20 = parsableByteArray.readInt();
            if (i20 == 1635148611) {
                ExtractorUtil.checkContainerInput(str3 == null, null);
                parsableByteArray.setPosition(position2 + 8);
                AvcConfig avcConfig = AvcConfig.parse(parsableByteArray);
                List list = avcConfig.initializationData;
                stsdData2.nalUnitLengthFieldLength = avcConfig.nalUnitLengthFieldLength;
                if (!z2) {
                    paspFromParent = avcConfig.pixelWidthHeightRatio;
                }
                String str5 = avcConfig.codecs;
                int i21 = avcConfig.maxNumReorderFrames;
                int i22 = avcConfig.colorSpace;
                int i23 = avcConfig.colorRange;
                int i24 = avcConfig.colorTransfer;
                int i25 = avcConfig.bitdepthLuma;
                drmInitData2 = drmInitDataCopyWithSchemeType;
                i8 = iIntValue;
                str = str2;
                i17 = i21;
                iIsoColorPrimariesToColorSpace = i22;
                i18 = i23;
                iIsoTransferCharacteristicsToColorTransfer = i24;
                i15 = avcConfig.bitdepthChroma;
                i14 = i25;
                listOf = list;
                str3 = MimeTypes.VIDEO_H264;
                str4 = str5;
            } else if (i20 == 1752589123) {
                ExtractorUtil.checkContainerInput(str3 == null, null);
                parsableByteArray.setPosition(position2 + 8);
                HevcConfig hevcConfig = HevcConfig.parse(parsableByteArray);
                List list2 = hevcConfig.initializationData;
                stsdData2.nalUnitLengthFieldLength = hevcConfig.nalUnitLengthFieldLength;
                if (!z2) {
                    paspFromParent = hevcConfig.pixelWidthHeightRatio;
                }
                int i26 = hevcConfig.maxNumReorderPics;
                String str6 = hevcConfig.codecs;
                int i27 = hevcConfig.colorSpace;
                int i28 = hevcConfig.colorRange;
                int i29 = hevcConfig.colorTransfer;
                int i30 = hevcConfig.bitdepthLuma;
                int i31 = hevcConfig.bitdepthChroma;
                drmInitData2 = drmInitDataCopyWithSchemeType;
                i17 = i26;
                i8 = iIntValue;
                str = str2;
                iIsoColorPrimariesToColorSpace = i27;
                i18 = i28;
                iIsoTransferCharacteristicsToColorTransfer = i29;
                str3 = MimeTypes.VIDEO_H265;
                i14 = i30;
                str4 = str6;
                listOf = list2;
                i15 = i31;
            } else {
                if (i20 == 1685480259 || i20 == 1685485123) {
                    drmInitData2 = drmInitDataCopyWithSchemeType;
                    i8 = iIntValue;
                    str = str2;
                    f2 = paspFromParent;
                    i9 = i14;
                    i10 = iIsoColorPrimariesToColorSpace;
                    i11 = iIsoTransferCharacteristicsToColorTransfer;
                    DolbyVisionConfig dolbyVisionConfig = DolbyVisionConfig.parse(parsableByteArray);
                    if (dolbyVisionConfig != null) {
                        str4 = dolbyVisionConfig.codecs;
                        str3 = MimeTypes.VIDEO_DOLBY_VISION;
                    }
                } else if (i20 == 1987076931) {
                    ExtractorUtil.checkContainerInput(str3 == null, null);
                    String str7 = iIntValue == 1987063864 ? MimeTypes.VIDEO_VP8 : MimeTypes.VIDEO_VP9;
                    parsableByteArray.setPosition(position2 + 12);
                    parsableByteArray.skipBytes(2);
                    int unsignedByte = parsableByteArray.readUnsignedByte();
                    int i32 = unsignedByte >> 4;
                    boolean z3 = (unsignedByte & 1) != 0;
                    int unsignedByte2 = parsableByteArray.readUnsignedByte();
                    int unsignedByte3 = parsableByteArray.readUnsignedByte();
                    iIsoColorPrimariesToColorSpace = ColorInfo.isoColorPrimariesToColorSpace(unsignedByte2);
                    i18 = z3 ? 1 : 2;
                    iIsoTransferCharacteristicsToColorTransfer = ColorInfo.isoTransferCharacteristicsToColorTransfer(unsignedByte3);
                    drmInitData2 = drmInitDataCopyWithSchemeType;
                    i14 = i32;
                    i15 = i14;
                    i8 = iIntValue;
                    str = str2;
                    str3 = str7;
                } else if (i20 == 1635135811) {
                    int i33 = i19 - 8;
                    byte[] bArr = new byte[i33];
                    parsableByteArray.readBytes(bArr, 0, i33);
                    listOf = ImmutableList.of(bArr);
                    parsableByteArray.setPosition(position2 + 8);
                    ColorInfo av1c = parseAv1c(parsableByteArray);
                    int i34 = av1c.lumaBitdepth;
                    int i35 = av1c.chromaBitdepth;
                    int i36 = av1c.colorSpace;
                    int i37 = av1c.colorRange;
                    iIsoTransferCharacteristicsToColorTransfer = av1c.colorTransfer;
                    drmInitData2 = drmInitDataCopyWithSchemeType;
                    i8 = iIntValue;
                    str = str2;
                    iIsoColorPrimariesToColorSpace = i36;
                    i18 = i37;
                    str3 = MimeTypes.VIDEO_AV1;
                    i14 = i34;
                    i15 = i35;
                } else if (i20 == 1668050025) {
                    if (byteBufferAllocateHdrStaticInfo == null) {
                        byteBufferAllocateHdrStaticInfo = allocateHdrStaticInfo();
                    }
                    ByteBuffer byteBuffer = byteBufferAllocateHdrStaticInfo;
                    byteBuffer.position(21);
                    byteBuffer.putShort(parsableByteArray.readShort());
                    byteBuffer.putShort(parsableByteArray.readShort());
                    byteBufferAllocateHdrStaticInfo = byteBuffer;
                    drmInitData2 = drmInitDataCopyWithSchemeType;
                    i8 = iIntValue;
                    str = str2;
                } else if (i20 == 1835295606) {
                    if (byteBufferAllocateHdrStaticInfo == null) {
                        byteBufferAllocateHdrStaticInfo = allocateHdrStaticInfo();
                    }
                    ByteBuffer byteBuffer2 = byteBufferAllocateHdrStaticInfo;
                    short s2 = parsableByteArray.readShort();
                    short s3 = parsableByteArray.readShort();
                    short s4 = parsableByteArray.readShort();
                    i8 = iIntValue;
                    short s5 = parsableByteArray.readShort();
                    str = str2;
                    short s6 = parsableByteArray.readShort();
                    short s7 = parsableByteArray.readShort();
                    int i38 = i14;
                    short s8 = parsableByteArray.readShort();
                    drmInitData2 = drmInitDataCopyWithSchemeType;
                    short s9 = parsableByteArray.readShort();
                    long unsignedInt = parsableByteArray.readUnsignedInt();
                    long unsignedInt2 = parsableByteArray.readUnsignedInt();
                    byteBuffer2.position(1);
                    byteBuffer2.putShort(s6);
                    byteBuffer2.putShort(s7);
                    byteBuffer2.putShort(s2);
                    byteBuffer2.putShort(s3);
                    byteBuffer2.putShort(s4);
                    byteBuffer2.putShort(s5);
                    byteBuffer2.putShort(s8);
                    byteBuffer2.putShort(s9);
                    byteBuffer2.putShort((short) (unsignedInt / 10000));
                    byteBuffer2.putShort((short) (unsignedInt2 / 10000));
                    byteBufferAllocateHdrStaticInfo = byteBuffer2;
                    i14 = i38;
                    paspFromParent = paspFromParent;
                } else {
                    drmInitData2 = drmInitDataCopyWithSchemeType;
                    i8 = iIntValue;
                    str = str2;
                    f2 = paspFromParent;
                    i9 = i14;
                    if (i20 == 1681012275) {
                        ExtractorUtil.checkContainerInput(str3 == null, null);
                        str3 = str;
                    } else if (i20 == 1702061171) {
                        ExtractorUtil.checkContainerInput(str3 == null, null);
                        esdsFromParent = parseEsdsFromParent(parsableByteArray, position2);
                        String str8 = esdsFromParent.mimeType;
                        byte[] bArr2 = esdsFromParent.initializationData;
                        if (bArr2 != null) {
                            listOf = ImmutableList.of(bArr2);
                        }
                        str3 = str8;
                    } else if (i20 == 1885434736) {
                        paspFromParent = parsePaspFromParent(parsableByteArray, position2);
                        i14 = i9;
                        z2 = true;
                    } else if (i20 == 1937126244) {
                        projFromParent = parseProjFromParent(parsableByteArray, position2, i19);
                    } else if (i20 == 1936995172) {
                        int unsignedByte4 = parsableByteArray.readUnsignedByte();
                        parsableByteArray.skipBytes(3);
                        if (unsignedByte4 == 0) {
                            int unsignedByte5 = parsableByteArray.readUnsignedByte();
                            if (unsignedByte5 == 0) {
                                i16 = 0;
                            } else if (unsignedByte5 == 1) {
                                i16 = 1;
                            } else if (unsignedByte5 == 2) {
                                i16 = 2;
                            } else if (unsignedByte5 == 3) {
                                i16 = 3;
                            }
                        }
                    } else if (i20 == 1668246642) {
                        i10 = iIsoColorPrimariesToColorSpace;
                        i11 = iIsoTransferCharacteristicsToColorTransfer;
                        if (i10 == -1 && i11 == -1) {
                            int i39 = parsableByteArray.readInt();
                            if (i39 == TYPE_nclx || i39 == TYPE_nclc) {
                                int unsignedShort3 = parsableByteArray.readUnsignedShort();
                                int unsignedShort4 = parsableByteArray.readUnsignedShort();
                                parsableByteArray.skipBytes(2);
                                boolean z4 = i19 == 19 && (parsableByteArray.readUnsignedByte() & 128) != 0;
                                iIsoColorPrimariesToColorSpace = ColorInfo.isoColorPrimariesToColorSpace(unsignedShort3);
                                i18 = z4 ? 1 : 2;
                                iIsoTransferCharacteristicsToColorTransfer = ColorInfo.isoTransferCharacteristicsToColorTransfer(unsignedShort4);
                                i14 = i9;
                                paspFromParent = f2;
                            } else {
                                Log.w(TAG, "Unsupported color type: " + Atom.getAtomTypeString(i39));
                            }
                        }
                    } else {
                        i10 = iIsoColorPrimariesToColorSpace;
                        i11 = iIsoTransferCharacteristicsToColorTransfer;
                    }
                    i14 = i9;
                    paspFromParent = f2;
                }
                iIsoColorPrimariesToColorSpace = i10;
                iIsoTransferCharacteristicsToColorTransfer = i11;
                i14 = i9;
                paspFromParent = f2;
            }
            position += i19;
            i12 = i3;
            i13 = i4;
            stsdData2 = stsdData;
            iIntValue = i8;
            str2 = str;
            drmInitDataCopyWithSchemeType = drmInitData2;
        }
        DrmInitData drmInitData3 = drmInitDataCopyWithSchemeType;
        float f3 = paspFromParent;
        int i40 = i14;
        int i41 = iIsoColorPrimariesToColorSpace;
        int i42 = iIsoTransferCharacteristicsToColorTransfer;
        if (str3 == null) {
            return;
        }
        Format.Builder colorInfo = new Format.Builder().setId(i5).setSampleMimeType(str3).setCodecs(str4).setWidth(unsignedShort).setHeight(unsignedShort2).setPixelWidthHeightRatio(f3).setRotationDegrees(i6).setProjectionData(projFromParent).setStereoMode(i16).setInitializationData(listOf).setMaxNumReorderSamples(i17).setDrmInitData(drmInitData3).setColorInfo(new ColorInfo.Builder().setColorSpace(i41).setColorRange(i18).setColorTransfer(i42).setHdrStaticInfo(byteBufferAllocateHdrStaticInfo != null ? byteBufferAllocateHdrStaticInfo.array() : null).setLumaBitdepth(i40).setChromaBitdepth(i15).build());
        if (esdsFromParent != null) {
            colorInfo.setAverageBitrate(Ints.saturatedCast(esdsFromParent.bitrate)).setPeakBitrate(Ints.saturatedCast(esdsFromParent.peakBitrate));
        }
        stsdData.format = colorInfo.build();
    }

    @Nullable
    private static Metadata parseXyz(ParsableByteArray parsableByteArray) {
        short s2 = parsableByteArray.readShort();
        parsableByteArray.skipBytes(2);
        String string = parsableByteArray.readString(s2);
        int iMax = Math.max(string.lastIndexOf(43), string.lastIndexOf(45));
        try {
            return new Metadata(new Mp4LocationData(Float.parseFloat(string.substring(0, iMax)), Float.parseFloat(string.substring(iMax, string.length() - 1))));
        } catch (IndexOutOfBoundsException | NumberFormatException unused) {
            return null;
        }
    }
}
