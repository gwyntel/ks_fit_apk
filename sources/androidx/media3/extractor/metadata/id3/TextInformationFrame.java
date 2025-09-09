package androidx.media3.extractor.metadata.id3;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import androidx.media3.common.MediaMetadata;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.InlineMe;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.lang.CharUtils;

@UnstableApi
/* loaded from: classes2.dex */
public final class TextInformationFrame extends Id3Frame {
    public static final Parcelable.Creator<TextInformationFrame> CREATOR = new Parcelable.Creator<TextInformationFrame>() { // from class: androidx.media3.extractor.metadata.id3.TextInformationFrame.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextInformationFrame createFromParcel(Parcel parcel) {
            return new TextInformationFrame(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextInformationFrame[] newArray(int i2) {
            return new TextInformationFrame[i2];
        }
    };

    @Nullable
    public final String description;

    @Deprecated
    public final String value;
    public final ImmutableList<String> values;

    private static List<Integer> parseId3v2point4TimestampFrameForDate(String str) {
        ArrayList arrayList = new ArrayList();
        try {
            if (str.length() >= 10) {
                arrayList.add(Integer.valueOf(Integer.parseInt(str.substring(0, 4))));
                arrayList.add(Integer.valueOf(Integer.parseInt(str.substring(5, 7))));
                arrayList.add(Integer.valueOf(Integer.parseInt(str.substring(8, 10))));
            } else if (str.length() >= 7) {
                arrayList.add(Integer.valueOf(Integer.parseInt(str.substring(0, 4))));
                arrayList.add(Integer.valueOf(Integer.parseInt(str.substring(5, 7))));
            } else if (str.length() >= 4) {
                arrayList.add(Integer.valueOf(Integer.parseInt(str.substring(0, 4))));
            }
            return arrayList;
        } catch (NumberFormatException unused) {
            return new ArrayList();
        }
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || TextInformationFrame.class != obj.getClass()) {
            return false;
        }
        TextInformationFrame textInformationFrame = (TextInformationFrame) obj;
        return Util.areEqual(this.id, textInformationFrame.id) && Util.areEqual(this.description, textInformationFrame.description) && this.values.equals(textInformationFrame.values);
    }

    public int hashCode() {
        int iHashCode = (527 + this.id.hashCode()) * 31;
        String str = this.description;
        return ((iHashCode + (str != null ? str.hashCode() : 0)) * 31) + this.values.hashCode();
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // androidx.media3.extractor.metadata.id3.Id3Frame, androidx.media3.common.Metadata.Entry
    public void populateMediaMetadata(MediaMetadata.Builder builder) throws NumberFormatException {
        String str = this.id;
        str.hashCode();
        char c2 = 65535;
        switch (str.hashCode()) {
            case 82815:
                if (str.equals("TAL")) {
                    c2 = 0;
                    break;
                }
                break;
            case 82878:
                if (str.equals("TCM")) {
                    c2 = 1;
                    break;
                }
                break;
            case 82897:
                if (str.equals("TDA")) {
                    c2 = 2;
                    break;
                }
                break;
            case 83253:
                if (str.equals("TP1")) {
                    c2 = 3;
                    break;
                }
                break;
            case 83254:
                if (str.equals("TP2")) {
                    c2 = 4;
                    break;
                }
                break;
            case 83255:
                if (str.equals("TP3")) {
                    c2 = 5;
                    break;
                }
                break;
            case 83341:
                if (str.equals("TRK")) {
                    c2 = 6;
                    break;
                }
                break;
            case 83378:
                if (str.equals("TT2")) {
                    c2 = 7;
                    break;
                }
                break;
            case 83536:
                if (str.equals("TXT")) {
                    c2 = '\b';
                    break;
                }
                break;
            case 83552:
                if (str.equals("TYE")) {
                    c2 = '\t';
                    break;
                }
                break;
            case 2567331:
                if (str.equals("TALB")) {
                    c2 = '\n';
                    break;
                }
                break;
            case 2569357:
                if (str.equals("TCOM")) {
                    c2 = 11;
                    break;
                }
                break;
            case 2569358:
                if (str.equals("TCON")) {
                    c2 = '\f';
                    break;
                }
                break;
            case 2569891:
                if (str.equals("TDAT")) {
                    c2 = CharUtils.CR;
                    break;
                }
                break;
            case 2570401:
                if (str.equals("TDRC")) {
                    c2 = 14;
                    break;
                }
                break;
            case 2570410:
                if (str.equals("TDRL")) {
                    c2 = 15;
                    break;
                }
                break;
            case 2571565:
                if (str.equals("TEXT")) {
                    c2 = 16;
                    break;
                }
                break;
            case 2575251:
                if (str.equals("TIT2")) {
                    c2 = 17;
                    break;
                }
                break;
            case 2581512:
                if (str.equals("TPE1")) {
                    c2 = 18;
                    break;
                }
                break;
            case 2581513:
                if (str.equals("TPE2")) {
                    c2 = 19;
                    break;
                }
                break;
            case 2581514:
                if (str.equals("TPE3")) {
                    c2 = 20;
                    break;
                }
                break;
            case 2583398:
                if (str.equals("TRCK")) {
                    c2 = 21;
                    break;
                }
                break;
            case 2590194:
                if (str.equals("TYER")) {
                    c2 = 22;
                    break;
                }
                break;
        }
        try {
            switch (c2) {
                case 0:
                case '\n':
                    builder.setAlbumTitle(this.values.get(0));
                    break;
                case 1:
                case 11:
                    builder.setComposer(this.values.get(0));
                    break;
                case 2:
                case '\r':
                    String str2 = this.values.get(0);
                    builder.setRecordingMonth(Integer.valueOf(Integer.parseInt(str2.substring(2, 4)))).setRecordingDay(Integer.valueOf(Integer.parseInt(str2.substring(0, 2))));
                    break;
                case 3:
                case 18:
                    builder.setArtist(this.values.get(0));
                    break;
                case 4:
                case 19:
                    builder.setAlbumArtist(this.values.get(0));
                    break;
                case 5:
                case 20:
                    builder.setConductor(this.values.get(0));
                    break;
                case 6:
                case 21:
                    String[] strArrSplit = Util.split(this.values.get(0), "/");
                    builder.setTrackNumber(Integer.valueOf(Integer.parseInt(strArrSplit[0]))).setTotalTrackCount(strArrSplit.length > 1 ? Integer.valueOf(Integer.parseInt(strArrSplit[1])) : null);
                    break;
                case 7:
                case 17:
                    builder.setTitle(this.values.get(0));
                    break;
                case '\b':
                case 16:
                    builder.setWriter(this.values.get(0));
                    break;
                case '\t':
                case 22:
                    builder.setRecordingYear(Integer.valueOf(Integer.parseInt(this.values.get(0))));
                    break;
                case '\f':
                    Integer numTryParse = Ints.tryParse(this.values.get(0));
                    if (numTryParse != null) {
                        String strResolveV1Genre = Id3Util.resolveV1Genre(numTryParse.intValue());
                        if (strResolveV1Genre != null) {
                            builder.setGenre(strResolveV1Genre);
                            break;
                        }
                    } else {
                        builder.setGenre(this.values.get(0));
                        break;
                    }
                    break;
                case 14:
                    List<Integer> id3v2point4TimestampFrameForDate = parseId3v2point4TimestampFrameForDate(this.values.get(0));
                    int size = id3v2point4TimestampFrameForDate.size();
                    if (size != 1) {
                        if (size != 2) {
                            if (size == 3) {
                                builder.setRecordingDay(id3v2point4TimestampFrameForDate.get(2));
                            }
                        }
                        builder.setRecordingMonth(id3v2point4TimestampFrameForDate.get(1));
                    }
                    builder.setRecordingYear(id3v2point4TimestampFrameForDate.get(0));
                    break;
                case 15:
                    List<Integer> id3v2point4TimestampFrameForDate2 = parseId3v2point4TimestampFrameForDate(this.values.get(0));
                    int size2 = id3v2point4TimestampFrameForDate2.size();
                    if (size2 != 1) {
                        if (size2 != 2) {
                            if (size2 == 3) {
                                builder.setReleaseDay(id3v2point4TimestampFrameForDate2.get(2));
                            }
                        }
                        builder.setReleaseMonth(id3v2point4TimestampFrameForDate2.get(1));
                    }
                    builder.setReleaseYear(id3v2point4TimestampFrameForDate2.get(0));
                    break;
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException unused) {
        }
    }

    @Override // androidx.media3.extractor.metadata.id3.Id3Frame
    public String toString() {
        return this.id + ": description=" + this.description + ": values=" + this.values;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.id);
        parcel.writeString(this.description);
        parcel.writeStringArray((String[]) this.values.toArray(new String[0]));
    }

    public TextInformationFrame(String str, @Nullable String str2, List<String> list) {
        super(str);
        Assertions.checkArgument(!list.isEmpty());
        this.description = str2;
        ImmutableList<String> immutableListCopyOf = ImmutableList.copyOf((Collection) list);
        this.values = immutableListCopyOf;
        this.value = immutableListCopyOf.get(0);
    }

    @InlineMe(imports = {"com.google.common.collect.ImmutableList"}, replacement = "this(id, description, ImmutableList.of(value))")
    @Deprecated
    public TextInformationFrame(String str, @Nullable String str2, String str3) {
        this(str, str2, ImmutableList.of(str3));
    }

    private TextInformationFrame(Parcel parcel) {
        this((String) Assertions.checkNotNull(parcel.readString()), parcel.readString(), ImmutableList.copyOf((String[]) Assertions.checkNotNull(parcel.createStringArray())));
    }
}
