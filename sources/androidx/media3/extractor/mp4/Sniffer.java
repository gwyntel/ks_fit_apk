package androidx.media3.extractor.mp4;

import android.support.v4.media.session.PlaybackStateCompat;
import androidx.annotation.Nullable;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.extractor.ExtractorInput;
import androidx.media3.extractor.SniffFailure;
import java.io.IOException;

/* loaded from: classes2.dex */
final class Sniffer {
    public static final int BRAND_HEIC = 1751476579;
    public static final int BRAND_QUICKTIME = 1903435808;
    private static final int[] COMPATIBLE_BRANDS = {1769172845, 1769172786, 1769172787, 1769172788, 1769172789, 1769172790, 1769172793, Atom.TYPE_avc1, Atom.TYPE_hvc1, Atom.TYPE_hev1, Atom.TYPE_av01, 1836069937, 1836069938, 862401121, 862401122, 862417462, 862417718, 862414134, 862414646, 1295275552, 1295270176, 1714714144, 1801741417, 1295275600, BRAND_QUICKTIME, 1297305174, 1684175153, 1769172332, 1885955686};
    private static final int SEARCH_LENGTH = 4096;

    private Sniffer() {
    }

    private static boolean isCompatibleBrand(int i2, boolean z2) {
        if ((i2 >>> 8) == 3368816) {
            return true;
        }
        if (i2 == 1751476579 && z2) {
            return true;
        }
        for (int i3 : COMPATIBLE_BRANDS) {
            if (i3 == i2) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    public static SniffFailure sniffFragmented(ExtractorInput extractorInput) throws IOException {
        return sniffInternal(extractorInput, true, false);
    }

    @Nullable
    private static SniffFailure sniffInternal(ExtractorInput extractorInput, boolean z2, boolean z3) throws IOException {
        int i2;
        int i3;
        int i4;
        boolean z4;
        int[] iArr;
        long length = extractorInput.getLength();
        long j2 = -1;
        long j3 = PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
        if (length != -1 && length <= PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM) {
            j3 = length;
        }
        int i5 = (int) j3;
        ParsableByteArray parsableByteArray = new ParsableByteArray(64);
        int i6 = 0;
        int i7 = 0;
        boolean z5 = false;
        while (i7 < i5) {
            parsableByteArray.reset(8);
            if (!extractorInput.peekFully(parsableByteArray.getData(), i6, 8, true)) {
                break;
            }
            long unsignedInt = parsableByteArray.readUnsignedInt();
            int i8 = parsableByteArray.readInt();
            if (unsignedInt == 1) {
                extractorInput.peekFully(parsableByteArray.getData(), 8, 8);
                i3 = 16;
                parsableByteArray.setLimit(16);
                unsignedInt = parsableByteArray.readLong();
            } else {
                if (unsignedInt == 0) {
                    long length2 = extractorInput.getLength();
                    if (length2 != j2) {
                        unsignedInt = (length2 - extractorInput.getPeekPosition()) + 8;
                    }
                }
                i3 = 8;
            }
            long j4 = unsignedInt;
            long j5 = i3;
            if (j4 < j5) {
                return new AtomSizeTooSmallSniffFailure(i8, j4, i3);
            }
            i7 += i3;
            if (i8 == 1836019574) {
                i5 += (int) j4;
                if (length != -1 && i5 > length) {
                    i5 = (int) length;
                }
            } else {
                if (i8 == 1836019558 || i8 == 1836475768) {
                    i2 = 1;
                    break;
                }
                long j6 = length;
                if (i8 == 1835295092) {
                    z5 = true;
                }
                if ((i7 + j4) - j5 >= i5) {
                    i2 = 0;
                    break;
                }
                int i9 = (int) (j4 - j5);
                i7 += i9;
                if (i8 != 1718909296) {
                    i4 = 0;
                    if (i9 != 0) {
                        extractorInput.advancePeekPosition(i9);
                    }
                } else {
                    if (i9 < 8) {
                        return new AtomSizeTooSmallSniffFailure(i8, i9, 8);
                    }
                    parsableByteArray.reset(i9);
                    i4 = 0;
                    extractorInput.peekFully(parsableByteArray.getData(), 0, i9);
                    int i10 = parsableByteArray.readInt();
                    if (isCompatibleBrand(i10, z3)) {
                        z5 = true;
                    }
                    parsableByteArray.skipBytes(4);
                    int iBytesLeft = parsableByteArray.bytesLeft() / 4;
                    if (!z5 && iBytesLeft > 0) {
                        iArr = new int[iBytesLeft];
                        int i11 = 0;
                        while (true) {
                            if (i11 >= iBytesLeft) {
                                z4 = z5;
                                break;
                            }
                            int i12 = parsableByteArray.readInt();
                            iArr[i11] = i12;
                            if (isCompatibleBrand(i12, z3)) {
                                z4 = true;
                                break;
                            }
                            i11++;
                        }
                    } else {
                        z4 = z5;
                        iArr = null;
                    }
                    if (!z4) {
                        return new UnsupportedBrandsSniffFailure(i10, iArr);
                    }
                    z5 = z4;
                }
                i6 = i4;
                length = j6;
            }
            j2 = -1;
        }
        i2 = i6;
        if (!z5) {
            return NoDeclaredBrandSniffFailure.INSTANCE;
        }
        if (z2 != i2) {
            return i2 != 0 ? IncorrectFragmentationSniffFailure.FILE_FRAGMENTED : IncorrectFragmentationSniffFailure.FILE_NOT_FRAGMENTED;
        }
        return null;
    }

    @Nullable
    public static SniffFailure sniffUnfragmented(ExtractorInput extractorInput, boolean z2) throws IOException {
        return sniffInternal(extractorInput, false, z2);
    }
}
