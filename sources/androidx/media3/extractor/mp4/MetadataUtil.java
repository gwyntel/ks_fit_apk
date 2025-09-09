package androidx.media3.extractor.mp4;

import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.Format;
import androidx.media3.common.Metadata;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.container.MdtaMetadataEntry;
import androidx.media3.extractor.GaplessInfoHolder;
import androidx.media3.extractor.metadata.id3.ApicFrame;
import androidx.media3.extractor.metadata.id3.CommentFrame;
import androidx.media3.extractor.metadata.id3.Id3Frame;
import androidx.media3.extractor.metadata.id3.Id3Util;
import androidx.media3.extractor.metadata.id3.InternalFrame;
import androidx.media3.extractor.metadata.id3.TextInformationFrame;
import com.google.common.collect.ImmutableList;

/* loaded from: classes2.dex */
final class MetadataUtil {
    private static final int PICTURE_TYPE_FRONT_COVER = 3;
    private static final int SHORT_TYPE_ALBUM = 6384738;
    private static final int SHORT_TYPE_ARTIST = 4280916;
    private static final int SHORT_TYPE_COMMENT = 6516084;
    private static final int SHORT_TYPE_COMPOSER_1 = 6516589;
    private static final int SHORT_TYPE_COMPOSER_2 = 7828084;
    private static final int SHORT_TYPE_ENCODER = 7630703;
    private static final int SHORT_TYPE_GENRE = 6776174;
    private static final int SHORT_TYPE_LYRICS = 7108978;
    private static final int SHORT_TYPE_NAME_1 = 7233901;
    private static final int SHORT_TYPE_NAME_2 = 7631467;
    private static final int SHORT_TYPE_YEAR = 6578553;
    private static final String TAG = "MetadataUtil";
    private static final int TYPE_ALBUM_ARTIST = 1631670868;
    private static final int TYPE_COMPILATION = 1668311404;
    private static final int TYPE_COVER_ART = 1668249202;
    private static final int TYPE_DISK_NUMBER = 1684632427;
    private static final int TYPE_GAPLESS_ALBUM = 1885823344;
    private static final int TYPE_GENRE = 1735291493;
    private static final int TYPE_GROUPING = 6779504;
    private static final int TYPE_INTERNAL = 757935405;
    private static final int TYPE_RATING = 1920233063;
    private static final int TYPE_SORT_ALBUM = 1936679276;
    private static final int TYPE_SORT_ALBUM_ARTIST = 1936679265;
    private static final int TYPE_SORT_ARTIST = 1936679282;
    private static final int TYPE_SORT_COMPOSER = 1936679791;
    private static final int TYPE_SORT_TRACK_NAME = 1936682605;
    private static final int TYPE_TEMPO = 1953329263;
    private static final int TYPE_TOP_BYTE_COPYRIGHT = 169;
    private static final int TYPE_TOP_BYTE_REPLACEMENT = 253;
    private static final int TYPE_TRACK_NUMBER = 1953655662;
    private static final int TYPE_TV_SHOW = 1953919848;
    private static final int TYPE_TV_SORT_SHOW = 1936683886;

    private MetadataUtil() {
    }

    @Nullable
    private static CommentFrame parseCommentAttribute(int i2, ParsableByteArray parsableByteArray) {
        int i3 = parsableByteArray.readInt();
        if (parsableByteArray.readInt() == 1684108385) {
            parsableByteArray.skipBytes(8);
            String nullTerminatedString = parsableByteArray.readNullTerminatedString(i3 - 16);
            return new CommentFrame(C.LANGUAGE_UNDETERMINED, nullTerminatedString, nullTerminatedString);
        }
        Log.w(TAG, "Failed to parse comment attribute: " + Atom.getAtomTypeString(i2));
        return null;
    }

    @Nullable
    private static ApicFrame parseCoverArt(ParsableByteArray parsableByteArray) {
        int i2 = parsableByteArray.readInt();
        if (parsableByteArray.readInt() != 1684108385) {
            Log.w(TAG, "Failed to parse cover art attribute");
            return null;
        }
        int fullAtomFlags = Atom.parseFullAtomFlags(parsableByteArray.readInt());
        String str = fullAtomFlags == 13 ? "image/jpeg" : fullAtomFlags == 14 ? "image/png" : null;
        if (str == null) {
            Log.w(TAG, "Unrecognized cover art flags: " + fullAtomFlags);
            return null;
        }
        parsableByteArray.skipBytes(4);
        int i3 = i2 - 16;
        byte[] bArr = new byte[i3];
        parsableByteArray.readBytes(bArr, 0, i3);
        return new ApicFrame(str, null, 3, bArr);
    }

    @Nullable
    public static Metadata.Entry parseIlstElement(ParsableByteArray parsableByteArray) {
        int position = parsableByteArray.getPosition() + parsableByteArray.readInt();
        int i2 = parsableByteArray.readInt();
        int i3 = (i2 >> 24) & 255;
        try {
            if (i3 == 169 || i3 == 253) {
                int i4 = 16777215 & i2;
                if (i4 == SHORT_TYPE_COMMENT) {
                    return parseCommentAttribute(i2, parsableByteArray);
                }
                if (i4 == SHORT_TYPE_NAME_1 || i4 == SHORT_TYPE_NAME_2) {
                    return parseTextAttribute(i2, "TIT2", parsableByteArray);
                }
                if (i4 == SHORT_TYPE_COMPOSER_1 || i4 == SHORT_TYPE_COMPOSER_2) {
                    return parseTextAttribute(i2, "TCOM", parsableByteArray);
                }
                if (i4 == SHORT_TYPE_YEAR) {
                    return parseTextAttribute(i2, "TDRC", parsableByteArray);
                }
                if (i4 == SHORT_TYPE_ARTIST) {
                    return parseTextAttribute(i2, "TPE1", parsableByteArray);
                }
                if (i4 == SHORT_TYPE_ENCODER) {
                    return parseTextAttribute(i2, "TSSE", parsableByteArray);
                }
                if (i4 == SHORT_TYPE_ALBUM) {
                    return parseTextAttribute(i2, "TALB", parsableByteArray);
                }
                if (i4 == SHORT_TYPE_LYRICS) {
                    return parseTextAttribute(i2, "USLT", parsableByteArray);
                }
                if (i4 == SHORT_TYPE_GENRE) {
                    return parseTextAttribute(i2, "TCON", parsableByteArray);
                }
                if (i4 == TYPE_GROUPING) {
                    return parseTextAttribute(i2, "TIT1", parsableByteArray);
                }
            } else {
                if (i2 == TYPE_GENRE) {
                    return parseStandardGenreAttribute(parsableByteArray);
                }
                if (i2 == TYPE_DISK_NUMBER) {
                    return parseIndexAndCountAttribute(i2, "TPOS", parsableByteArray);
                }
                if (i2 == TYPE_TRACK_NUMBER) {
                    return parseIndexAndCountAttribute(i2, "TRCK", parsableByteArray);
                }
                if (i2 == TYPE_TEMPO) {
                    return parseIntegerAttribute(i2, "TBPM", parsableByteArray, true, false);
                }
                if (i2 == TYPE_COMPILATION) {
                    return parseIntegerAttribute(i2, "TCMP", parsableByteArray, true, true);
                }
                if (i2 == TYPE_COVER_ART) {
                    return parseCoverArt(parsableByteArray);
                }
                if (i2 == TYPE_ALBUM_ARTIST) {
                    return parseTextAttribute(i2, "TPE2", parsableByteArray);
                }
                if (i2 == TYPE_SORT_TRACK_NAME) {
                    return parseTextAttribute(i2, "TSOT", parsableByteArray);
                }
                if (i2 == TYPE_SORT_ALBUM) {
                    return parseTextAttribute(i2, "TSOA", parsableByteArray);
                }
                if (i2 == TYPE_SORT_ARTIST) {
                    return parseTextAttribute(i2, "TSOP", parsableByteArray);
                }
                if (i2 == TYPE_SORT_ALBUM_ARTIST) {
                    return parseTextAttribute(i2, "TSO2", parsableByteArray);
                }
                if (i2 == TYPE_SORT_COMPOSER) {
                    return parseTextAttribute(i2, "TSOC", parsableByteArray);
                }
                if (i2 == TYPE_RATING) {
                    return parseIntegerAttribute(i2, "ITUNESADVISORY", parsableByteArray, false, false);
                }
                if (i2 == TYPE_GAPLESS_ALBUM) {
                    return parseIntegerAttribute(i2, "ITUNESGAPLESS", parsableByteArray, false, true);
                }
                if (i2 == TYPE_TV_SORT_SHOW) {
                    return parseTextAttribute(i2, "TVSHOWSORT", parsableByteArray);
                }
                if (i2 == TYPE_TV_SHOW) {
                    return parseTextAttribute(i2, "TVSHOW", parsableByteArray);
                }
                if (i2 == TYPE_INTERNAL) {
                    return parseInternalAttribute(parsableByteArray, position);
                }
            }
            Log.d(TAG, "Skipped unknown metadata entry: " + Atom.getAtomTypeString(i2));
            parsableByteArray.setPosition(position);
            return null;
        } finally {
            parsableByteArray.setPosition(position);
        }
    }

    @Nullable
    private static TextInformationFrame parseIndexAndCountAttribute(int i2, String str, ParsableByteArray parsableByteArray) {
        int i3 = parsableByteArray.readInt();
        if (parsableByteArray.readInt() == 1684108385 && i3 >= 22) {
            parsableByteArray.skipBytes(10);
            int unsignedShort = parsableByteArray.readUnsignedShort();
            if (unsignedShort > 0) {
                String str2 = "" + unsignedShort;
                int unsignedShort2 = parsableByteArray.readUnsignedShort();
                if (unsignedShort2 > 0) {
                    str2 = str2 + "/" + unsignedShort2;
                }
                return new TextInformationFrame(str, (String) null, ImmutableList.of(str2));
            }
        }
        Log.w(TAG, "Failed to parse index/count attribute: " + Atom.getAtomTypeString(i2));
        return null;
    }

    @Nullable
    private static Id3Frame parseIntegerAttribute(int i2, String str, ParsableByteArray parsableByteArray, boolean z2, boolean z3) {
        int integerAttribute = parseIntegerAttribute(parsableByteArray);
        if (z3) {
            integerAttribute = Math.min(1, integerAttribute);
        }
        if (integerAttribute >= 0) {
            return z2 ? new TextInformationFrame(str, (String) null, ImmutableList.of(Integer.toString(integerAttribute))) : new CommentFrame(C.LANGUAGE_UNDETERMINED, str, Integer.toString(integerAttribute));
        }
        Log.w(TAG, "Failed to parse uint8 attribute: " + Atom.getAtomTypeString(i2));
        return null;
    }

    @Nullable
    private static Id3Frame parseInternalAttribute(ParsableByteArray parsableByteArray, int i2) {
        String nullTerminatedString = null;
        String nullTerminatedString2 = null;
        int i3 = -1;
        int i4 = -1;
        while (parsableByteArray.getPosition() < i2) {
            int position = parsableByteArray.getPosition();
            int i5 = parsableByteArray.readInt();
            int i6 = parsableByteArray.readInt();
            parsableByteArray.skipBytes(4);
            if (i6 == 1835360622) {
                nullTerminatedString = parsableByteArray.readNullTerminatedString(i5 - 12);
            } else if (i6 == 1851878757) {
                nullTerminatedString2 = parsableByteArray.readNullTerminatedString(i5 - 12);
            } else {
                if (i6 == 1684108385) {
                    i3 = position;
                    i4 = i5;
                }
                parsableByteArray.skipBytes(i5 - 12);
            }
        }
        if (nullTerminatedString == null || nullTerminatedString2 == null || i3 == -1) {
            return null;
        }
        parsableByteArray.setPosition(i3);
        parsableByteArray.skipBytes(16);
        return new InternalFrame(nullTerminatedString, nullTerminatedString2, parsableByteArray.readNullTerminatedString(i4 - 16));
    }

    @Nullable
    public static MdtaMetadataEntry parseMdtaMetadataEntryFromIlst(ParsableByteArray parsableByteArray, int i2, String str) {
        while (true) {
            int position = parsableByteArray.getPosition();
            if (position >= i2) {
                return null;
            }
            int i3 = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == 1684108385) {
                int i4 = parsableByteArray.readInt();
                int i5 = parsableByteArray.readInt();
                int i6 = i3 - 16;
                byte[] bArr = new byte[i6];
                parsableByteArray.readBytes(bArr, 0, i6);
                return new MdtaMetadataEntry(str, bArr, i5, i4);
            }
            parsableByteArray.setPosition(position + i3);
        }
    }

    @Nullable
    private static TextInformationFrame parseStandardGenreAttribute(ParsableByteArray parsableByteArray) {
        String strResolveV1Genre = Id3Util.resolveV1Genre(parseIntegerAttribute(parsableByteArray) - 1);
        if (strResolveV1Genre != null) {
            return new TextInformationFrame("TCON", (String) null, ImmutableList.of(strResolveV1Genre));
        }
        Log.w(TAG, "Failed to parse standard genre code");
        return null;
    }

    @Nullable
    private static TextInformationFrame parseTextAttribute(int i2, String str, ParsableByteArray parsableByteArray) {
        int i3 = parsableByteArray.readInt();
        if (parsableByteArray.readInt() == 1684108385) {
            parsableByteArray.skipBytes(8);
            return new TextInformationFrame(str, (String) null, ImmutableList.of(parsableByteArray.readNullTerminatedString(i3 - 16)));
        }
        Log.w(TAG, "Failed to parse text attribute: " + Atom.getAtomTypeString(i2));
        return null;
    }

    public static void setFormatGaplessInfo(int i2, GaplessInfoHolder gaplessInfoHolder, Format.Builder builder) {
        if (i2 == 1 && gaplessInfoHolder.hasGaplessInfo()) {
            builder.setEncoderDelay(gaplessInfoHolder.encoderDelay).setEncoderPadding(gaplessInfoHolder.encoderPadding);
        }
    }

    public static void setFormatMetadata(int i2, @Nullable Metadata metadata, Format.Builder builder, Metadata... metadataArr) {
        Metadata metadata2 = new Metadata(new Metadata.Entry[0]);
        if (metadata != null) {
            for (int i3 = 0; i3 < metadata.length(); i3++) {
                Metadata.Entry entry = metadata.get(i3);
                if (entry instanceof MdtaMetadataEntry) {
                    MdtaMetadataEntry mdtaMetadataEntry = (MdtaMetadataEntry) entry;
                    if (!mdtaMetadataEntry.key.equals(MdtaMetadataEntry.KEY_ANDROID_CAPTURE_FPS)) {
                        metadata2 = metadata2.copyWithAppendedEntries(mdtaMetadataEntry);
                    } else if (i2 == 2) {
                        metadata2 = metadata2.copyWithAppendedEntries(mdtaMetadataEntry);
                    }
                }
            }
        }
        for (Metadata metadata3 : metadataArr) {
            metadata2 = metadata2.copyWithAppendedEntriesFrom(metadata3);
        }
        if (metadata2.length() > 0) {
            builder.setMetadata(metadata2);
        }
    }

    private static int parseIntegerAttribute(ParsableByteArray parsableByteArray) {
        int i2 = parsableByteArray.readInt();
        if (parsableByteArray.readInt() == 1684108385) {
            parsableByteArray.skipBytes(8);
            int i3 = i2 - 16;
            if (i3 == 1) {
                return parsableByteArray.readUnsignedByte();
            }
            if (i3 == 2) {
                return parsableByteArray.readUnsignedShort();
            }
            if (i3 != 3) {
                if (i3 == 4 && (parsableByteArray.peekUnsignedByte() & 128) == 0) {
                    return parsableByteArray.readUnsignedIntToInt();
                }
            } else {
                return parsableByteArray.readUnsignedInt24();
            }
        }
        Log.w(TAG, "Failed to parse data atom to int");
        return -1;
    }
}
