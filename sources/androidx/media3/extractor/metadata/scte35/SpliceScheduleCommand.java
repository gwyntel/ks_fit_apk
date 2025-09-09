package androidx.media3.extractor.metadata.scte35;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.media3.common.C;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.UnstableApi;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@UnstableApi
/* loaded from: classes2.dex */
public final class SpliceScheduleCommand extends SpliceCommand {
    public static final Parcelable.Creator<SpliceScheduleCommand> CREATOR = new Parcelable.Creator<SpliceScheduleCommand>() { // from class: androidx.media3.extractor.metadata.scte35.SpliceScheduleCommand.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SpliceScheduleCommand createFromParcel(Parcel parcel) {
            return new SpliceScheduleCommand(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SpliceScheduleCommand[] newArray(int i2) {
            return new SpliceScheduleCommand[i2];
        }
    };
    public final List<Event> events;

    public static final class ComponentSplice {
        public final int componentTag;
        public final long utcSpliceTime;

        /* JADX INFO: Access modifiers changed from: private */
        public static ComponentSplice createFromParcel(Parcel parcel) {
            return new ComponentSplice(parcel.readInt(), parcel.readLong());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void writeToParcel(Parcel parcel) {
            parcel.writeInt(this.componentTag);
            parcel.writeLong(this.utcSpliceTime);
        }

        private ComponentSplice(int i2, long j2) {
            this.componentTag = i2;
            this.utcSpliceTime = j2;
        }
    }

    static SpliceScheduleCommand a(ParsableByteArray parsableByteArray) {
        int unsignedByte = parsableByteArray.readUnsignedByte();
        ArrayList arrayList = new ArrayList(unsignedByte);
        for (int i2 = 0; i2 < unsignedByte; i2++) {
            arrayList.add(Event.parseFromSection(parsableByteArray));
        }
        return new SpliceScheduleCommand(arrayList);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        int size = this.events.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            this.events.get(i3).writeToParcel(parcel);
        }
    }

    private SpliceScheduleCommand(List<Event> list) {
        this.events = Collections.unmodifiableList(list);
    }

    private SpliceScheduleCommand(Parcel parcel) {
        int i2 = parcel.readInt();
        ArrayList arrayList = new ArrayList(i2);
        for (int i3 = 0; i3 < i2; i3++) {
            arrayList.add(Event.createFromParcel(parcel));
        }
        this.events = Collections.unmodifiableList(arrayList);
    }

    public static final class Event {
        public final boolean autoReturn;
        public final int availNum;
        public final int availsExpected;
        public final long breakDurationUs;
        public final List<ComponentSplice> componentSpliceList;
        public final boolean outOfNetworkIndicator;
        public final boolean programSpliceFlag;
        public final boolean spliceEventCancelIndicator;
        public final long spliceEventId;
        public final int uniqueProgramId;
        public final long utcSpliceTime;

        private Event(long j2, boolean z2, boolean z3, boolean z4, List<ComponentSplice> list, long j3, boolean z5, long j4, int i2, int i3, int i4) {
            this.spliceEventId = j2;
            this.spliceEventCancelIndicator = z2;
            this.outOfNetworkIndicator = z3;
            this.programSpliceFlag = z4;
            this.componentSpliceList = Collections.unmodifiableList(list);
            this.utcSpliceTime = j3;
            this.autoReturn = z5;
            this.breakDurationUs = j4;
            this.uniqueProgramId = i2;
            this.availNum = i3;
            this.availsExpected = i4;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Event createFromParcel(Parcel parcel) {
            return new Event(parcel);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Event parseFromSection(ParsableByteArray parsableByteArray) {
            ArrayList arrayList;
            boolean z2;
            long j2;
            boolean z3;
            long j3;
            int i2;
            int i3;
            int unsignedByte;
            boolean z4;
            boolean z5;
            long unsignedInt;
            long unsignedInt2 = parsableByteArray.readUnsignedInt();
            boolean z6 = (parsableByteArray.readUnsignedByte() & 128) != 0;
            ArrayList arrayList2 = new ArrayList();
            if (z6) {
                arrayList = arrayList2;
                z2 = false;
                j2 = C.TIME_UNSET;
                z3 = false;
                j3 = C.TIME_UNSET;
                i2 = 0;
                i3 = 0;
                unsignedByte = 0;
                z4 = false;
            } else {
                int unsignedByte2 = parsableByteArray.readUnsignedByte();
                boolean z7 = (unsignedByte2 & 128) != 0;
                boolean z8 = (unsignedByte2 & 64) != 0;
                boolean z9 = (unsignedByte2 & 32) != 0;
                long unsignedInt3 = z8 ? parsableByteArray.readUnsignedInt() : C.TIME_UNSET;
                if (!z8) {
                    int unsignedByte3 = parsableByteArray.readUnsignedByte();
                    ArrayList arrayList3 = new ArrayList(unsignedByte3);
                    for (int i4 = 0; i4 < unsignedByte3; i4++) {
                        arrayList3.add(new ComponentSplice(parsableByteArray.readUnsignedByte(), parsableByteArray.readUnsignedInt()));
                    }
                    arrayList2 = arrayList3;
                }
                if (z9) {
                    long unsignedByte4 = parsableByteArray.readUnsignedByte();
                    boolean z10 = (128 & unsignedByte4) != 0;
                    unsignedInt = ((((unsignedByte4 & 1) << 32) | parsableByteArray.readUnsignedInt()) * 1000) / 90;
                    z5 = z10;
                } else {
                    z5 = false;
                    unsignedInt = C.TIME_UNSET;
                }
                int unsignedShort = parsableByteArray.readUnsignedShort();
                int unsignedByte5 = parsableByteArray.readUnsignedByte();
                z4 = z8;
                unsignedByte = parsableByteArray.readUnsignedByte();
                j3 = unsignedInt;
                arrayList = arrayList2;
                long j4 = unsignedInt3;
                i2 = unsignedShort;
                i3 = unsignedByte5;
                j2 = j4;
                boolean z11 = z7;
                z3 = z5;
                z2 = z11;
            }
            return new Event(unsignedInt2, z6, z2, z4, arrayList, j2, z3, j3, i2, i3, unsignedByte);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void writeToParcel(Parcel parcel) {
            parcel.writeLong(this.spliceEventId);
            parcel.writeByte(this.spliceEventCancelIndicator ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.outOfNetworkIndicator ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.programSpliceFlag ? (byte) 1 : (byte) 0);
            int size = this.componentSpliceList.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                this.componentSpliceList.get(i2).writeToParcel(parcel);
            }
            parcel.writeLong(this.utcSpliceTime);
            parcel.writeByte(this.autoReturn ? (byte) 1 : (byte) 0);
            parcel.writeLong(this.breakDurationUs);
            parcel.writeInt(this.uniqueProgramId);
            parcel.writeInt(this.availNum);
            parcel.writeInt(this.availsExpected);
        }

        private Event(Parcel parcel) {
            this.spliceEventId = parcel.readLong();
            this.spliceEventCancelIndicator = parcel.readByte() == 1;
            this.outOfNetworkIndicator = parcel.readByte() == 1;
            this.programSpliceFlag = parcel.readByte() == 1;
            int i2 = parcel.readInt();
            ArrayList arrayList = new ArrayList(i2);
            for (int i3 = 0; i3 < i2; i3++) {
                arrayList.add(ComponentSplice.createFromParcel(parcel));
            }
            this.componentSpliceList = Collections.unmodifiableList(arrayList);
            this.utcSpliceTime = parcel.readLong();
            this.autoReturn = parcel.readByte() == 1;
            this.breakDurationUs = parcel.readLong();
            this.uniqueProgramId = parcel.readInt();
            this.availNum = parcel.readInt();
            this.availsExpected = parcel.readInt();
        }
    }
}
