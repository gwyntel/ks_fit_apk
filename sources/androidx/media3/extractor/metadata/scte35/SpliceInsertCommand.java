package androidx.media3.extractor.metadata.scte35;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.media3.common.C;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.TimestampAdjuster;
import androidx.media3.common.util.UnstableApi;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@UnstableApi
/* loaded from: classes2.dex */
public final class SpliceInsertCommand extends SpliceCommand {
    public static final Parcelable.Creator<SpliceInsertCommand> CREATOR = new Parcelable.Creator<SpliceInsertCommand>() { // from class: androidx.media3.extractor.metadata.scte35.SpliceInsertCommand.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SpliceInsertCommand createFromParcel(Parcel parcel) {
            return new SpliceInsertCommand(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SpliceInsertCommand[] newArray(int i2) {
            return new SpliceInsertCommand[i2];
        }
    };
    public final boolean autoReturn;
    public final int availNum;
    public final int availsExpected;
    public final long breakDurationUs;
    public final List<ComponentSplice> componentSpliceList;
    public final boolean outOfNetworkIndicator;
    public final boolean programSpliceFlag;
    public final long programSplicePlaybackPositionUs;
    public final long programSplicePts;
    public final boolean spliceEventCancelIndicator;
    public final long spliceEventId;
    public final boolean spliceImmediateFlag;
    public final int uniqueProgramId;

    public static final class ComponentSplice {
        public final long componentSplicePlaybackPositionUs;
        public final long componentSplicePts;
        public final int componentTag;

        public static ComponentSplice createFromParcel(Parcel parcel) {
            return new ComponentSplice(parcel.readInt(), parcel.readLong(), parcel.readLong());
        }

        public void writeToParcel(Parcel parcel) {
            parcel.writeInt(this.componentTag);
            parcel.writeLong(this.componentSplicePts);
            parcel.writeLong(this.componentSplicePlaybackPositionUs);
        }

        private ComponentSplice(int i2, long j2, long j3) {
            this.componentTag = i2;
            this.componentSplicePts = j2;
            this.componentSplicePlaybackPositionUs = j3;
        }
    }

    static SpliceInsertCommand a(ParsableByteArray parsableByteArray, long j2, TimestampAdjuster timestampAdjuster) {
        List list;
        boolean z2;
        boolean z3;
        long j3;
        boolean z4;
        long j4;
        int unsignedShort;
        int unsignedByte;
        int unsignedByte2;
        boolean z5;
        boolean z6;
        long unsignedInt;
        long unsignedInt2 = parsableByteArray.readUnsignedInt();
        boolean z7 = (parsableByteArray.readUnsignedByte() & 128) != 0;
        List listEmptyList = Collections.emptyList();
        if (z7) {
            list = listEmptyList;
            z2 = false;
            z3 = false;
            j3 = C.TIME_UNSET;
            z4 = false;
            j4 = C.TIME_UNSET;
            unsignedShort = 0;
            unsignedByte = 0;
            unsignedByte2 = 0;
            z5 = false;
        } else {
            int unsignedByte3 = parsableByteArray.readUnsignedByte();
            boolean z8 = (unsignedByte3 & 128) != 0;
            boolean z9 = (unsignedByte3 & 64) != 0;
            boolean z10 = (unsignedByte3 & 32) != 0;
            boolean z11 = (unsignedByte3 & 16) != 0;
            long jB = (!z9 || z11) ? C.TIME_UNSET : TimeSignalCommand.b(parsableByteArray, j2);
            if (!z9) {
                int unsignedByte4 = parsableByteArray.readUnsignedByte();
                ArrayList arrayList = new ArrayList(unsignedByte4);
                for (int i2 = 0; i2 < unsignedByte4; i2++) {
                    int unsignedByte5 = parsableByteArray.readUnsignedByte();
                    long jB2 = !z11 ? TimeSignalCommand.b(parsableByteArray, j2) : C.TIME_UNSET;
                    arrayList.add(new ComponentSplice(unsignedByte5, jB2, timestampAdjuster.adjustTsTimestamp(jB2)));
                }
                listEmptyList = arrayList;
            }
            if (z10) {
                long unsignedByte6 = parsableByteArray.readUnsignedByte();
                boolean z12 = (128 & unsignedByte6) != 0;
                unsignedInt = ((((unsignedByte6 & 1) << 32) | parsableByteArray.readUnsignedInt()) * 1000) / 90;
                z6 = z12;
            } else {
                z6 = false;
                unsignedInt = C.TIME_UNSET;
            }
            unsignedShort = parsableByteArray.readUnsignedShort();
            z5 = z9;
            unsignedByte = parsableByteArray.readUnsignedByte();
            unsignedByte2 = parsableByteArray.readUnsignedByte();
            list = listEmptyList;
            long j5 = jB;
            z4 = z6;
            j4 = unsignedInt;
            z3 = z11;
            z2 = z8;
            j3 = j5;
        }
        return new SpliceInsertCommand(unsignedInt2, z7, z2, z5, z3, j3, timestampAdjuster.adjustTsTimestamp(j3), list, z4, j4, unsignedShort, unsignedByte, unsignedByte2);
    }

    @Override // androidx.media3.extractor.metadata.scte35.SpliceCommand
    public String toString() {
        return "SCTE-35 SpliceInsertCommand { programSplicePts=" + this.programSplicePts + ", programSplicePlaybackPositionUs= " + this.programSplicePlaybackPositionUs + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeLong(this.spliceEventId);
        parcel.writeByte(this.spliceEventCancelIndicator ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.outOfNetworkIndicator ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.programSpliceFlag ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.spliceImmediateFlag ? (byte) 1 : (byte) 0);
        parcel.writeLong(this.programSplicePts);
        parcel.writeLong(this.programSplicePlaybackPositionUs);
        int size = this.componentSpliceList.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            this.componentSpliceList.get(i3).writeToParcel(parcel);
        }
        parcel.writeByte(this.autoReturn ? (byte) 1 : (byte) 0);
        parcel.writeLong(this.breakDurationUs);
        parcel.writeInt(this.uniqueProgramId);
        parcel.writeInt(this.availNum);
        parcel.writeInt(this.availsExpected);
    }

    private SpliceInsertCommand(long j2, boolean z2, boolean z3, boolean z4, boolean z5, long j3, long j4, List<ComponentSplice> list, boolean z6, long j5, int i2, int i3, int i4) {
        this.spliceEventId = j2;
        this.spliceEventCancelIndicator = z2;
        this.outOfNetworkIndicator = z3;
        this.programSpliceFlag = z4;
        this.spliceImmediateFlag = z5;
        this.programSplicePts = j3;
        this.programSplicePlaybackPositionUs = j4;
        this.componentSpliceList = Collections.unmodifiableList(list);
        this.autoReturn = z6;
        this.breakDurationUs = j5;
        this.uniqueProgramId = i2;
        this.availNum = i3;
        this.availsExpected = i4;
    }

    private SpliceInsertCommand(Parcel parcel) {
        this.spliceEventId = parcel.readLong();
        this.spliceEventCancelIndicator = parcel.readByte() == 1;
        this.outOfNetworkIndicator = parcel.readByte() == 1;
        this.programSpliceFlag = parcel.readByte() == 1;
        this.spliceImmediateFlag = parcel.readByte() == 1;
        this.programSplicePts = parcel.readLong();
        this.programSplicePlaybackPositionUs = parcel.readLong();
        int i2 = parcel.readInt();
        ArrayList arrayList = new ArrayList(i2);
        for (int i3 = 0; i3 < i2; i3++) {
            arrayList.add(ComponentSplice.createFromParcel(parcel));
        }
        this.componentSpliceList = Collections.unmodifiableList(arrayList);
        this.autoReturn = parcel.readByte() == 1;
        this.breakDurationUs = parcel.readLong();
        this.uniqueProgramId = parcel.readInt();
        this.availNum = parcel.readInt();
        this.availsExpected = parcel.readInt();
    }
}
