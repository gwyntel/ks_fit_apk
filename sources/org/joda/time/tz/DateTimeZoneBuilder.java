package org.joda.time.tz;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.chrono.ISOChronology;

/* loaded from: classes5.dex */
public class DateTimeZoneBuilder {
    private final ArrayList<RuleSet> iRuleSets = new ArrayList<>(10);

    private static final class DSTZone extends DateTimeZone {
        private static final long serialVersionUID = 6941492635554961361L;
        final Recurrence iEndRecurrence;
        final int iStandardOffset;
        final Recurrence iStartRecurrence;

        DSTZone(String str, int i2, Recurrence recurrence, Recurrence recurrence2) {
            super(str);
            this.iStandardOffset = i2;
            this.iStartRecurrence = recurrence;
            this.iEndRecurrence = recurrence2;
        }

        private Recurrence findMatchingRecurrence(long j2) {
            long next;
            int i2 = this.iStandardOffset;
            Recurrence recurrence = this.iStartRecurrence;
            Recurrence recurrence2 = this.iEndRecurrence;
            try {
                next = recurrence.next(j2, i2, recurrence2.getSaveMillis());
            } catch (ArithmeticException | IllegalArgumentException unused) {
                next = j2;
            }
            try {
                j2 = recurrence2.next(j2, i2, recurrence.getSaveMillis());
            } catch (ArithmeticException | IllegalArgumentException unused2) {
            }
            return next > j2 ? recurrence : recurrence2;
        }

        static DSTZone readFrom(DataInput dataInput, String str) throws IOException {
            return new DSTZone(str, (int) DateTimeZoneBuilder.a(dataInput), Recurrence.a(dataInput), Recurrence.a(dataInput));
        }

        @Override // org.joda.time.DateTimeZone
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DSTZone)) {
                return false;
            }
            DSTZone dSTZone = (DSTZone) obj;
            return getID().equals(dSTZone.getID()) && this.iStandardOffset == dSTZone.iStandardOffset && this.iStartRecurrence.equals(dSTZone.iStartRecurrence) && this.iEndRecurrence.equals(dSTZone.iEndRecurrence);
        }

        @Override // org.joda.time.DateTimeZone
        public String getNameKey(long j2) {
            return findMatchingRecurrence(j2).getNameKey();
        }

        @Override // org.joda.time.DateTimeZone
        public int getOffset(long j2) {
            return this.iStandardOffset + findMatchingRecurrence(j2).getSaveMillis();
        }

        @Override // org.joda.time.DateTimeZone
        public int getStandardOffset(long j2) {
            return this.iStandardOffset;
        }

        @Override // org.joda.time.DateTimeZone
        public boolean isFixed() {
            return false;
        }

        @Override // org.joda.time.DateTimeZone
        public long nextTransition(long j2) {
            long next;
            int i2 = this.iStandardOffset;
            Recurrence recurrence = this.iStartRecurrence;
            Recurrence recurrence2 = this.iEndRecurrence;
            try {
                next = recurrence.next(j2, i2, recurrence2.getSaveMillis());
            } catch (ArithmeticException | IllegalArgumentException unused) {
            }
            if (j2 > 0 && next < 0) {
                next = j2;
            }
            try {
                long next2 = recurrence2.next(j2, i2, recurrence.getSaveMillis());
                if (j2 <= 0 || next2 >= 0) {
                    j2 = next2;
                }
            } catch (ArithmeticException | IllegalArgumentException unused2) {
            }
            return next > j2 ? j2 : next;
        }

        @Override // org.joda.time.DateTimeZone
        public long previousTransition(long j2) {
            long jPrevious;
            long j3 = j2 + 1;
            int i2 = this.iStandardOffset;
            Recurrence recurrence = this.iStartRecurrence;
            Recurrence recurrence2 = this.iEndRecurrence;
            try {
                jPrevious = recurrence.previous(j3, i2, recurrence2.getSaveMillis());
            } catch (ArithmeticException | IllegalArgumentException unused) {
            }
            if (j3 < 0 && jPrevious > 0) {
                jPrevious = j3;
            }
            try {
                long jPrevious2 = recurrence2.previous(j3, i2, recurrence.getSaveMillis());
                if (j3 >= 0 || jPrevious2 <= 0) {
                    j3 = jPrevious2;
                }
            } catch (ArithmeticException | IllegalArgumentException unused2) {
            }
            if (jPrevious <= j3) {
                jPrevious = j3;
            }
            return jPrevious - 1;
        }

        public void writeTo(DataOutput dataOutput) throws IOException {
            DateTimeZoneBuilder.b(dataOutput, this.iStandardOffset);
            this.iStartRecurrence.writeTo(dataOutput);
            this.iEndRecurrence.writeTo(dataOutput);
        }
    }

    private static final class OfYear {

        /* renamed from: a, reason: collision with root package name */
        final char f26739a;

        /* renamed from: b, reason: collision with root package name */
        final int f26740b;

        /* renamed from: c, reason: collision with root package name */
        final int f26741c;

        /* renamed from: d, reason: collision with root package name */
        final int f26742d;

        /* renamed from: e, reason: collision with root package name */
        final boolean f26743e;

        /* renamed from: f, reason: collision with root package name */
        final int f26744f;

        OfYear(char c2, int i2, int i3, int i4, boolean z2, int i5) {
            if (c2 != 'u' && c2 != 'w' && c2 != 's') {
                throw new IllegalArgumentException("Unknown mode: " + c2);
            }
            this.f26739a = c2;
            this.f26740b = i2;
            this.f26741c = i3;
            this.f26742d = i4;
            this.f26743e = z2;
            this.f26744f = i5;
        }

        static OfYear a(DataInput dataInput) {
            return new OfYear((char) dataInput.readUnsignedByte(), dataInput.readUnsignedByte(), dataInput.readByte(), dataInput.readUnsignedByte(), dataInput.readBoolean(), (int) DateTimeZoneBuilder.a(dataInput));
        }

        private long setDayOfMonth(Chronology chronology, long j2) {
            if (this.f26741c >= 0) {
                return chronology.dayOfMonth().set(j2, this.f26741c);
            }
            return chronology.dayOfMonth().add(chronology.monthOfYear().add(chronology.dayOfMonth().set(j2, 1), 1), this.f26741c);
        }

        private long setDayOfMonthNext(Chronology chronology, long j2) {
            try {
                return setDayOfMonth(chronology, j2);
            } catch (IllegalArgumentException e2) {
                if (this.f26740b != 2 || this.f26741c != 29) {
                    throw e2;
                }
                while (!chronology.year().isLeap(j2)) {
                    j2 = chronology.year().add(j2, 1);
                }
                return setDayOfMonth(chronology, j2);
            }
        }

        private long setDayOfMonthPrevious(Chronology chronology, long j2) {
            try {
                return setDayOfMonth(chronology, j2);
            } catch (IllegalArgumentException e2) {
                if (this.f26740b != 2 || this.f26741c != 29) {
                    throw e2;
                }
                while (!chronology.year().isLeap(j2)) {
                    j2 = chronology.year().add(j2, -1);
                }
                return setDayOfMonth(chronology, j2);
            }
        }

        private long setDayOfWeek(Chronology chronology, long j2) {
            int i2 = this.f26742d - chronology.dayOfWeek().get(j2);
            if (i2 == 0) {
                return j2;
            }
            if (this.f26743e) {
                if (i2 < 0) {
                    i2 += 7;
                }
            } else if (i2 > 0) {
                i2 -= 7;
            }
            return chronology.dayOfWeek().add(j2, i2);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof OfYear)) {
                return false;
            }
            OfYear ofYear = (OfYear) obj;
            return this.f26739a == ofYear.f26739a && this.f26740b == ofYear.f26740b && this.f26741c == ofYear.f26741c && this.f26742d == ofYear.f26742d && this.f26743e == ofYear.f26743e && this.f26744f == ofYear.f26744f;
        }

        public long next(long j2, int i2, int i3) {
            char c2 = this.f26739a;
            if (c2 == 'w') {
                i2 += i3;
            } else if (c2 != 's') {
                i2 = 0;
            }
            long j3 = i2;
            long j4 = j2 + j3;
            ISOChronology instanceUTC = ISOChronology.getInstanceUTC();
            long dayOfMonthNext = setDayOfMonthNext(instanceUTC, instanceUTC.millisOfDay().add(instanceUTC.millisOfDay().set(instanceUTC.monthOfYear().set(j4, this.f26740b), 0), this.f26744f));
            if (this.f26742d != 0) {
                dayOfMonthNext = setDayOfWeek(instanceUTC, dayOfMonthNext);
                if (dayOfMonthNext <= j4) {
                    dayOfMonthNext = setDayOfWeek(instanceUTC, setDayOfMonthNext(instanceUTC, instanceUTC.monthOfYear().set(instanceUTC.year().add(dayOfMonthNext, 1), this.f26740b)));
                }
            } else if (dayOfMonthNext <= j4) {
                dayOfMonthNext = setDayOfMonthNext(instanceUTC, instanceUTC.year().add(dayOfMonthNext, 1));
            }
            return dayOfMonthNext - j3;
        }

        public long previous(long j2, int i2, int i3) {
            char c2 = this.f26739a;
            if (c2 == 'w') {
                i2 += i3;
            } else if (c2 != 's') {
                i2 = 0;
            }
            long j3 = i2;
            long j4 = j2 + j3;
            ISOChronology instanceUTC = ISOChronology.getInstanceUTC();
            long dayOfMonthPrevious = setDayOfMonthPrevious(instanceUTC, instanceUTC.millisOfDay().add(instanceUTC.millisOfDay().set(instanceUTC.monthOfYear().set(j4, this.f26740b), 0), this.f26744f));
            if (this.f26742d != 0) {
                dayOfMonthPrevious = setDayOfWeek(instanceUTC, dayOfMonthPrevious);
                if (dayOfMonthPrevious >= j4) {
                    dayOfMonthPrevious = setDayOfWeek(instanceUTC, setDayOfMonthPrevious(instanceUTC, instanceUTC.monthOfYear().set(instanceUTC.year().add(dayOfMonthPrevious, -1), this.f26740b)));
                }
            } else if (dayOfMonthPrevious >= j4) {
                dayOfMonthPrevious = setDayOfMonthPrevious(instanceUTC, instanceUTC.year().add(dayOfMonthPrevious, -1));
            }
            return dayOfMonthPrevious - j3;
        }

        public long setInstant(int i2, int i3, int i4) {
            char c2 = this.f26739a;
            if (c2 == 'w') {
                i3 += i4;
            } else if (c2 != 's') {
                i3 = 0;
            }
            ISOChronology instanceUTC = ISOChronology.getInstanceUTC();
            long dayOfMonth = setDayOfMonth(instanceUTC, instanceUTC.millisOfDay().set(instanceUTC.monthOfYear().set(instanceUTC.year().set(0L, i2), this.f26740b), this.f26744f));
            if (this.f26742d != 0) {
                dayOfMonth = setDayOfWeek(instanceUTC, dayOfMonth);
            }
            return dayOfMonth - i3;
        }

        public void writeTo(DataOutput dataOutput) throws IOException {
            dataOutput.writeByte(this.f26739a);
            dataOutput.writeByte(this.f26740b);
            dataOutput.writeByte(this.f26741c);
            dataOutput.writeByte(this.f26742d);
            dataOutput.writeBoolean(this.f26743e);
            DateTimeZoneBuilder.b(dataOutput, this.f26744f);
        }
    }

    private static final class PrecalculatedZone extends DateTimeZone {
        private static final long serialVersionUID = 7811976468055766265L;
        private final String[] iNameKeys;
        private final int[] iStandardOffsets;
        private final DSTZone iTailZone;
        private final long[] iTransitions;
        private final int[] iWallOffsets;

        private PrecalculatedZone(String str, long[] jArr, int[] iArr, int[] iArr2, String[] strArr, DSTZone dSTZone) {
            super(str);
            this.iTransitions = jArr;
            this.iWallOffsets = iArr;
            this.iStandardOffsets = iArr2;
            this.iNameKeys = strArr;
            this.iTailZone = dSTZone;
        }

        static PrecalculatedZone create(String str, boolean z2, ArrayList<Transition> arrayList, DSTZone dSTZone) {
            DSTZone dSTZone2;
            DSTZone dSTZone3 = dSTZone;
            int size = arrayList.size();
            if (size == 0) {
                throw new IllegalArgumentException();
            }
            long[] jArr = new long[size];
            int[] iArr = new int[size];
            int[] iArr2 = new int[size];
            String[] strArr = new String[size];
            int i2 = 0;
            Transition transition = null;
            int i3 = 0;
            while (i3 < size) {
                Transition transition2 = arrayList.get(i3);
                if (!transition2.isTransitionFrom(transition)) {
                    throw new IllegalArgumentException(str);
                }
                jArr[i3] = transition2.getMillis();
                iArr[i3] = transition2.getWallOffset();
                iArr2[i3] = transition2.getStandardOffset();
                strArr[i3] = transition2.getNameKey();
                i3++;
                transition = transition2;
            }
            String[] strArr2 = new String[5];
            for (String[] strArr3 : new DateFormatSymbols(Locale.ENGLISH).getZoneStrings()) {
                if (strArr3 != null && strArr3.length == 5 && str.equals(strArr3[0])) {
                    strArr2 = strArr3;
                }
            }
            ISOChronology instanceUTC = ISOChronology.getInstanceUTC();
            while (i2 < size - 1) {
                String str2 = strArr[i2];
                int i4 = i2 + 1;
                String str3 = strArr[i4];
                long j2 = iArr[i2];
                long j3 = iArr[i4];
                String[] strArr4 = strArr;
                String[] strArr5 = strArr2;
                long j4 = iArr2[i2];
                int[] iArr3 = iArr;
                int[] iArr4 = iArr2;
                long j5 = iArr2[i4];
                int i5 = size;
                Period period = new Period(jArr[i2], jArr[i4], PeriodType.yearMonthDay(), instanceUTC);
                if (j2 != j3 && j4 == j5 && str2.equals(str3) && period.getYears() == 0 && period.getMonths() > 4 && period.getMonths() < 8 && str2.equals(strArr5[2]) && str2.equals(strArr5[4])) {
                    if (ZoneInfoCompiler.verbose()) {
                        PrintStream printStream = System.out;
                        printStream.println("Fixing duplicate name key - " + str3);
                        printStream.println("     - " + new DateTime(jArr[i2], instanceUTC) + " - " + new DateTime(jArr[i4], instanceUTC));
                    }
                    if (j2 > j3) {
                        strArr4[i2] = (str2 + "-Summer").intern();
                    } else if (j2 < j3) {
                        strArr4[i4] = (str3 + "-Summer").intern();
                        i2 = i4;
                    }
                }
                i2++;
                strArr2 = strArr5;
                dSTZone3 = dSTZone;
                strArr = strArr4;
                iArr = iArr3;
                iArr2 = iArr4;
                size = i5;
            }
            DSTZone dSTZone4 = dSTZone3;
            int[] iArr5 = iArr;
            int[] iArr6 = iArr2;
            String[] strArr6 = strArr;
            if (dSTZone4 == null || !dSTZone4.iStartRecurrence.getNameKey().equals(dSTZone4.iEndRecurrence.getNameKey())) {
                dSTZone2 = dSTZone4;
            } else {
                if (ZoneInfoCompiler.verbose()) {
                    System.out.println("Fixing duplicate recurrent name key - " + dSTZone4.iStartRecurrence.getNameKey());
                }
                dSTZone2 = dSTZone4.iStartRecurrence.getSaveMillis() > 0 ? new DSTZone(dSTZone.getID(), dSTZone4.iStandardOffset, dSTZone4.iStartRecurrence.c("-Summer"), dSTZone4.iEndRecurrence) : new DSTZone(dSTZone.getID(), dSTZone4.iStandardOffset, dSTZone4.iStartRecurrence, dSTZone4.iEndRecurrence.c("-Summer"));
            }
            return new PrecalculatedZone(z2 ? str : "", jArr, iArr5, iArr6, strArr6, dSTZone2);
        }

        static PrecalculatedZone readFrom(DataInput dataInput, String str) throws IOException {
            int unsignedByte;
            int unsignedShort = dataInput.readUnsignedShort();
            String[] strArr = new String[unsignedShort];
            for (int i2 = 0; i2 < unsignedShort; i2++) {
                strArr[i2] = dataInput.readUTF();
            }
            int i3 = dataInput.readInt();
            long[] jArr = new long[i3];
            int[] iArr = new int[i3];
            int[] iArr2 = new int[i3];
            String[] strArr2 = new String[i3];
            for (int i4 = 0; i4 < i3; i4++) {
                jArr[i4] = DateTimeZoneBuilder.a(dataInput);
                iArr[i4] = (int) DateTimeZoneBuilder.a(dataInput);
                iArr2[i4] = (int) DateTimeZoneBuilder.a(dataInput);
                if (unsignedShort < 256) {
                    try {
                        unsignedByte = dataInput.readUnsignedByte();
                    } catch (ArrayIndexOutOfBoundsException unused) {
                        throw new IOException("Invalid encoding");
                    }
                } else {
                    unsignedByte = dataInput.readUnsignedShort();
                }
                strArr2[i4] = strArr[unsignedByte];
            }
            return new PrecalculatedZone(str, jArr, iArr, iArr2, strArr2, dataInput.readBoolean() ? DSTZone.readFrom(dataInput, str) : null);
        }

        @Override // org.joda.time.DateTimeZone
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PrecalculatedZone)) {
                return false;
            }
            PrecalculatedZone precalculatedZone = (PrecalculatedZone) obj;
            if (getID().equals(precalculatedZone.getID()) && Arrays.equals(this.iTransitions, precalculatedZone.iTransitions) && Arrays.equals(this.iNameKeys, precalculatedZone.iNameKeys) && Arrays.equals(this.iWallOffsets, precalculatedZone.iWallOffsets) && Arrays.equals(this.iStandardOffsets, precalculatedZone.iStandardOffsets)) {
                DSTZone dSTZone = this.iTailZone;
                DSTZone dSTZone2 = precalculatedZone.iTailZone;
                if (dSTZone == null) {
                    if (dSTZone2 == null) {
                        return true;
                    }
                } else if (dSTZone.equals(dSTZone2)) {
                    return true;
                }
            }
            return false;
        }

        @Override // org.joda.time.DateTimeZone
        public String getNameKey(long j2) {
            long[] jArr = this.iTransitions;
            int iBinarySearch = Arrays.binarySearch(jArr, j2);
            if (iBinarySearch >= 0) {
                return this.iNameKeys[iBinarySearch];
            }
            int i2 = ~iBinarySearch;
            if (i2 < jArr.length) {
                return i2 > 0 ? this.iNameKeys[i2 - 1] : "UTC";
            }
            DSTZone dSTZone = this.iTailZone;
            return dSTZone == null ? this.iNameKeys[i2 - 1] : dSTZone.getNameKey(j2);
        }

        @Override // org.joda.time.DateTimeZone
        public int getOffset(long j2) {
            long[] jArr = this.iTransitions;
            int iBinarySearch = Arrays.binarySearch(jArr, j2);
            if (iBinarySearch >= 0) {
                return this.iWallOffsets[iBinarySearch];
            }
            int i2 = ~iBinarySearch;
            if (i2 >= jArr.length) {
                DSTZone dSTZone = this.iTailZone;
                return dSTZone == null ? this.iWallOffsets[i2 - 1] : dSTZone.getOffset(j2);
            }
            if (i2 > 0) {
                return this.iWallOffsets[i2 - 1];
            }
            return 0;
        }

        @Override // org.joda.time.DateTimeZone
        public int getStandardOffset(long j2) {
            long[] jArr = this.iTransitions;
            int iBinarySearch = Arrays.binarySearch(jArr, j2);
            if (iBinarySearch >= 0) {
                return this.iStandardOffsets[iBinarySearch];
            }
            int i2 = ~iBinarySearch;
            if (i2 >= jArr.length) {
                DSTZone dSTZone = this.iTailZone;
                return dSTZone == null ? this.iStandardOffsets[i2 - 1] : dSTZone.getStandardOffset(j2);
            }
            if (i2 > 0) {
                return this.iStandardOffsets[i2 - 1];
            }
            return 0;
        }

        public boolean isCachable() {
            if (this.iTailZone != null) {
                return true;
            }
            long[] jArr = this.iTransitions;
            if (jArr.length <= 1) {
                return false;
            }
            double d2 = 0.0d;
            int i2 = 0;
            for (int i3 = 1; i3 < jArr.length; i3++) {
                long j2 = jArr[i3] - jArr[i3 - 1];
                if (j2 < 63158400000L) {
                    d2 += j2;
                    i2++;
                }
            }
            return i2 > 0 && (d2 / ((double) i2)) / 8.64E7d >= 25.0d;
        }

        @Override // org.joda.time.DateTimeZone
        public boolean isFixed() {
            return false;
        }

        @Override // org.joda.time.DateTimeZone
        public long nextTransition(long j2) {
            long[] jArr = this.iTransitions;
            int iBinarySearch = Arrays.binarySearch(jArr, j2);
            int i2 = iBinarySearch >= 0 ? iBinarySearch + 1 : ~iBinarySearch;
            if (i2 < jArr.length) {
                return jArr[i2];
            }
            DSTZone dSTZone = this.iTailZone;
            if (dSTZone == null) {
                return j2;
            }
            long j3 = jArr[jArr.length - 1];
            if (j2 < j3) {
                j2 = j3;
            }
            return dSTZone.nextTransition(j2);
        }

        @Override // org.joda.time.DateTimeZone
        public long previousTransition(long j2) {
            long[] jArr = this.iTransitions;
            int iBinarySearch = Arrays.binarySearch(jArr, j2);
            if (iBinarySearch >= 0) {
                return j2 > Long.MIN_VALUE ? j2 - 1 : j2;
            }
            int i2 = ~iBinarySearch;
            if (i2 < jArr.length) {
                if (i2 > 0) {
                    long j3 = jArr[i2 - 1];
                    if (j3 > Long.MIN_VALUE) {
                        return j3 - 1;
                    }
                }
                return j2;
            }
            DSTZone dSTZone = this.iTailZone;
            if (dSTZone != null) {
                long jPreviousTransition = dSTZone.previousTransition(j2);
                if (jPreviousTransition < j2) {
                    return jPreviousTransition;
                }
            }
            long j4 = jArr[i2 - 1];
            return j4 > Long.MIN_VALUE ? j4 - 1 : j2;
        }

        public void writeTo(DataOutput dataOutput) throws IOException {
            int length = this.iTransitions.length;
            HashSet hashSet = new HashSet();
            for (int i2 = 0; i2 < length; i2++) {
                hashSet.add(this.iNameKeys[i2]);
            }
            int size = hashSet.size();
            if (size > 65535) {
                throw new UnsupportedOperationException("String pool is too large");
            }
            String[] strArr = new String[size];
            Iterator it = hashSet.iterator();
            int i3 = 0;
            while (it.hasNext()) {
                strArr[i3] = (String) it.next();
                i3++;
            }
            dataOutput.writeShort(size);
            for (int i4 = 0; i4 < size; i4++) {
                dataOutput.writeUTF(strArr[i4]);
            }
            dataOutput.writeInt(length);
            for (int i5 = 0; i5 < length; i5++) {
                DateTimeZoneBuilder.b(dataOutput, this.iTransitions[i5]);
                DateTimeZoneBuilder.b(dataOutput, this.iWallOffsets[i5]);
                DateTimeZoneBuilder.b(dataOutput, this.iStandardOffsets[i5]);
                String str = this.iNameKeys[i5];
                int i6 = 0;
                while (true) {
                    if (i6 >= size) {
                        break;
                    }
                    if (!strArr[i6].equals(str)) {
                        i6++;
                    } else if (size < 256) {
                        dataOutput.writeByte(i6);
                    } else {
                        dataOutput.writeShort(i6);
                    }
                }
            }
            dataOutput.writeBoolean(this.iTailZone != null);
            DSTZone dSTZone = this.iTailZone;
            if (dSTZone != null) {
                dSTZone.writeTo(dataOutput);
            }
        }
    }

    private static final class Recurrence {

        /* renamed from: a, reason: collision with root package name */
        final OfYear f26745a;

        /* renamed from: b, reason: collision with root package name */
        final String f26746b;

        /* renamed from: c, reason: collision with root package name */
        final int f26747c;

        Recurrence(OfYear ofYear, String str, int i2) {
            this.f26745a = ofYear;
            this.f26746b = str;
            this.f26747c = i2;
        }

        static Recurrence a(DataInput dataInput) {
            return new Recurrence(OfYear.a(dataInput), dataInput.readUTF(), (int) DateTimeZoneBuilder.a(dataInput));
        }

        Recurrence b(String str) {
            return new Recurrence(this.f26745a, str, this.f26747c);
        }

        Recurrence c(String str) {
            return b((this.f26746b + str).intern());
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Recurrence)) {
                return false;
            }
            Recurrence recurrence = (Recurrence) obj;
            return this.f26747c == recurrence.f26747c && this.f26746b.equals(recurrence.f26746b) && this.f26745a.equals(recurrence.f26745a);
        }

        public String getNameKey() {
            return this.f26746b;
        }

        public OfYear getOfYear() {
            return this.f26745a;
        }

        public int getSaveMillis() {
            return this.f26747c;
        }

        public long next(long j2, int i2, int i3) {
            return this.f26745a.next(j2, i2, i3);
        }

        public long previous(long j2, int i2, int i3) {
            return this.f26745a.previous(j2, i2, i3);
        }

        public void writeTo(DataOutput dataOutput) throws IOException {
            this.f26745a.writeTo(dataOutput);
            dataOutput.writeUTF(this.f26746b);
            DateTimeZoneBuilder.b(dataOutput, this.f26747c);
        }
    }

    private static final class Rule {

        /* renamed from: a, reason: collision with root package name */
        final Recurrence f26748a;

        /* renamed from: b, reason: collision with root package name */
        final int f26749b;

        /* renamed from: c, reason: collision with root package name */
        final int f26750c;

        Rule(Recurrence recurrence, int i2, int i3) {
            this.f26748a = recurrence;
            this.f26749b = i2;
            this.f26750c = i3;
        }

        public int getFromYear() {
            return this.f26749b;
        }

        public String getNameKey() {
            return this.f26748a.getNameKey();
        }

        public OfYear getOfYear() {
            return this.f26748a.getOfYear();
        }

        public int getSaveMillis() {
            return this.f26748a.getSaveMillis();
        }

        public int getToYear() {
            return this.f26750c;
        }

        public long next(long j2, int i2, int i3) {
            ISOChronology instanceUTC = ISOChronology.getInstanceUTC();
            int i4 = i2 + i3;
            long next = this.f26748a.next(((j2 > Long.MIN_VALUE ? 1 : (j2 == Long.MIN_VALUE ? 0 : -1)) == 0 ? Integer.MIN_VALUE : instanceUTC.year().get(((long) i4) + j2)) < this.f26749b ? (instanceUTC.year().set(0L, this.f26749b) - i4) - 1 : j2, i2, i3);
            return (next <= j2 || instanceUTC.year().get(((long) i4) + next) <= this.f26750c) ? next : j2;
        }
    }

    static long a(DataInput dataInput) throws IOException {
        long unsignedByte;
        long j2;
        int unsignedByte2 = dataInput.readUnsignedByte();
        int i2 = unsignedByte2 >> 6;
        if (i2 == 1) {
            unsignedByte = dataInput.readUnsignedByte() | ((unsignedByte2 << 26) >> 2) | (dataInput.readUnsignedByte() << 16) | (dataInput.readUnsignedByte() << 8);
            j2 = 60000;
        } else if (i2 == 2) {
            unsignedByte = ((unsignedByte2 << 58) >> 26) | (dataInput.readUnsignedByte() << 24) | (dataInput.readUnsignedByte() << 16) | (dataInput.readUnsignedByte() << 8) | dataInput.readUnsignedByte();
            j2 = 1000;
        } else {
            if (i2 == 3) {
                return dataInput.readLong();
            }
            unsignedByte = (unsignedByte2 << 26) >> 26;
            j2 = 1800000;
        }
        return unsignedByte * j2;
    }

    private boolean addTransition(ArrayList<Transition> arrayList, Transition transition) {
        int size = arrayList.size();
        if (size == 0) {
            arrayList.add(transition);
            return true;
        }
        int i2 = size - 1;
        Transition transition2 = arrayList.get(i2);
        if (!transition.isTransitionFrom(transition2)) {
            return false;
        }
        if (transition.getMillis() + transition2.getWallOffset() != transition2.getMillis() + (size >= 2 ? arrayList.get(size - 2).getWallOffset() : 0)) {
            arrayList.add(transition);
            return true;
        }
        arrayList.remove(i2);
        return addTransition(arrayList, transition);
    }

    static void b(DataOutput dataOutput, long j2) throws IOException {
        if (j2 % 1800000 == 0) {
            long j3 = j2 / 1800000;
            if (((j3 << 58) >> 58) == j3) {
                dataOutput.writeByte((int) (j3 & 63));
                return;
            }
        }
        if (j2 % 60000 == 0) {
            long j4 = j2 / 60000;
            if (((j4 << 34) >> 34) == j4) {
                dataOutput.writeInt(((int) (LockFreeTaskQueueCore.HEAD_MASK & j4)) | 1073741824);
                return;
            }
        }
        if (j2 % 1000 == 0) {
            long j5 = j2 / 1000;
            if (((j5 << 26) >> 26) == j5) {
                dataOutput.writeByte(((int) ((j5 >> 32) & 63)) | 128);
                dataOutput.writeInt((int) j5);
                return;
            }
        }
        dataOutput.writeByte(j2 < 0 ? 255 : 192);
        dataOutput.writeLong(j2);
    }

    private static DateTimeZone buildFixedZone(String str, String str2, int i2, int i3) {
        return ("UTC".equals(str) && str.equals(str2) && i2 == 0 && i3 == 0) ? DateTimeZone.UTC : new FixedDateTimeZone(str, str2, i2, i3);
    }

    private RuleSet getLastRuleSet() {
        if (this.iRuleSets.size() == 0) {
            addCutover(Integer.MIN_VALUE, 'w', 1, 1, 0, false, 0);
        }
        return this.iRuleSets.get(r0.size() - 1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static DateTimeZone readFrom(InputStream inputStream, String str) throws IOException {
        return inputStream instanceof DataInput ? readFrom((DataInput) inputStream, str) : readFrom((DataInput) new DataInputStream(inputStream), str);
    }

    public DateTimeZoneBuilder addCutover(int i2, char c2, int i3, int i4, int i5, boolean z2, int i6) {
        if (this.iRuleSets.size() > 0) {
            this.iRuleSets.get(r10.size() - 1).setUpperLimit(i2, new OfYear(c2, i3, i4, i5, z2, i6));
        }
        this.iRuleSets.add(new RuleSet());
        return this;
    }

    public DateTimeZoneBuilder addRecurringSavings(String str, int i2, int i3, int i4, char c2, int i5, int i6, int i7, boolean z2, int i8) {
        if (i3 <= i4) {
            getLastRuleSet().addRule(new Rule(new Recurrence(new OfYear(c2, i5, i6, i7, z2, i8), str, i2), i3, i4));
        }
        return this;
    }

    public DateTimeZoneBuilder setFixedSavings(String str, int i2) {
        getLastRuleSet().setFixedSavings(str, i2);
        return this;
    }

    public DateTimeZoneBuilder setStandardOffset(int i2) {
        getLastRuleSet().setStandardOffset(i2);
        return this;
    }

    public DateTimeZone toDateTimeZone(String str, boolean z2) {
        if (str == null) {
            throw new IllegalArgumentException();
        }
        ArrayList<Transition> arrayList = new ArrayList<>();
        int size = this.iRuleSets.size();
        DSTZone dSTZoneBuildTailZone = null;
        long upperLimit = Long.MIN_VALUE;
        for (int i2 = 0; i2 < size; i2++) {
            RuleSet ruleSet = this.iRuleSets.get(i2);
            Transition transitionFirstTransition = ruleSet.firstTransition(upperLimit);
            if (transitionFirstTransition != null) {
                addTransition(arrayList, transitionFirstTransition);
                long millis = transitionFirstTransition.getMillis();
                int saveMillis = transitionFirstTransition.getSaveMillis();
                RuleSet ruleSet2 = new RuleSet(ruleSet);
                while (true) {
                    Transition transitionNextTransition = ruleSet2.nextTransition(millis, saveMillis);
                    if (transitionNextTransition == null || (addTransition(arrayList, transitionNextTransition) && dSTZoneBuildTailZone != null)) {
                        break;
                    }
                    long millis2 = transitionNextTransition.getMillis();
                    int saveMillis2 = transitionNextTransition.getSaveMillis();
                    if (dSTZoneBuildTailZone == null && i2 == size - 1) {
                        dSTZoneBuildTailZone = ruleSet2.buildTailZone(str);
                    }
                    saveMillis = saveMillis2;
                    millis = millis2;
                }
                upperLimit = ruleSet2.getUpperLimit(saveMillis);
            }
        }
        if (arrayList.size() == 0) {
            return dSTZoneBuildTailZone != null ? dSTZoneBuildTailZone : buildFixedZone(str, "UTC", 0, 0);
        }
        if (arrayList.size() == 1 && dSTZoneBuildTailZone == null) {
            Transition transition = arrayList.get(0);
            return buildFixedZone(str, transition.getNameKey(), transition.getWallOffset(), transition.getStandardOffset());
        }
        PrecalculatedZone precalculatedZoneCreate = PrecalculatedZone.create(str, z2, arrayList, dSTZoneBuildTailZone);
        return precalculatedZoneCreate.isCachable() ? CachedDateTimeZone.forZone(precalculatedZoneCreate) : precalculatedZoneCreate;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void writeTo(String str, OutputStream outputStream) throws IOException {
        if (outputStream instanceof DataOutput) {
            writeTo(str, (DataOutput) outputStream);
        } else {
            writeTo(str, (DataOutput) new DataOutputStream(outputStream));
        }
    }

    private static final class RuleSet {
        private static final int YEAR_LIMIT = ISOChronology.getInstanceUTC().year().get(DateTimeUtils.currentTimeMillis()) + 100;
        private String iInitialNameKey;
        private int iInitialSaveMillis;
        private ArrayList<Rule> iRules;
        private int iStandardOffset;
        private OfYear iUpperOfYear;
        private int iUpperYear;

        RuleSet() {
            this.iRules = new ArrayList<>(10);
            this.iUpperYear = Integer.MAX_VALUE;
        }

        public void addRule(Rule rule) {
            if (this.iRules.contains(rule)) {
                return;
            }
            this.iRules.add(rule);
        }

        public DSTZone buildTailZone(String str) {
            if (this.iRules.size() != 2) {
                return null;
            }
            Rule rule = this.iRules.get(0);
            Rule rule2 = this.iRules.get(1);
            if (rule.getToYear() == Integer.MAX_VALUE && rule2.getToYear() == Integer.MAX_VALUE) {
                return new DSTZone(str, this.iStandardOffset, rule.f26748a, rule2.f26748a);
            }
            return null;
        }

        public Transition firstTransition(long j2) {
            String str = this.iInitialNameKey;
            if (str != null) {
                int i2 = this.iStandardOffset;
                return new Transition(j2, str, i2 + this.iInitialSaveMillis, i2);
            }
            ArrayList<Rule> arrayList = new ArrayList<>(this.iRules);
            long j3 = Long.MIN_VALUE;
            int saveMillis = 0;
            Transition transition = null;
            while (true) {
                Transition transitionNextTransition = nextTransition(j3, saveMillis);
                if (transitionNextTransition == null) {
                    break;
                }
                long millis = transitionNextTransition.getMillis();
                if (millis == j2) {
                    transition = new Transition(j2, transitionNextTransition);
                    break;
                }
                if (millis > j2) {
                    if (transition == null) {
                        Iterator<Rule> it = arrayList.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            Rule next = it.next();
                            if (next.getSaveMillis() == 0) {
                                transition = new Transition(j2, next, this.iStandardOffset);
                                break;
                            }
                        }
                    }
                    if (transition == null) {
                        String nameKey = transitionNextTransition.getNameKey();
                        int i3 = this.iStandardOffset;
                        transition = new Transition(j2, nameKey, i3, i3);
                    }
                } else {
                    transition = new Transition(j2, transitionNextTransition);
                    saveMillis = transitionNextTransition.getSaveMillis();
                    j3 = millis;
                }
            }
            this.iRules = arrayList;
            return transition;
        }

        public int getStandardOffset() {
            return this.iStandardOffset;
        }

        public long getUpperLimit(int i2) {
            int i3 = this.iUpperYear;
            if (i3 == Integer.MAX_VALUE) {
                return Long.MAX_VALUE;
            }
            return this.iUpperOfYear.setInstant(i3, this.iStandardOffset, i2);
        }

        public Transition nextTransition(long j2, int i2) {
            ISOChronology instanceUTC = ISOChronology.getInstanceUTC();
            Iterator<Rule> it = this.iRules.iterator();
            long j3 = Long.MAX_VALUE;
            Rule rule = null;
            while (it.hasNext()) {
                Rule next = it.next();
                long next2 = next.next(j2, this.iStandardOffset, i2);
                if (next2 <= j2) {
                    it.remove();
                } else if (next2 <= j3) {
                    rule = next;
                    j3 = next2;
                }
            }
            if (rule == null || instanceUTC.year().get(j3) >= YEAR_LIMIT) {
                return null;
            }
            int i3 = this.iUpperYear;
            if (i3 >= Integer.MAX_VALUE || j3 < this.iUpperOfYear.setInstant(i3, this.iStandardOffset, i2)) {
                return new Transition(j3, rule, this.iStandardOffset);
            }
            return null;
        }

        public void setFixedSavings(String str, int i2) {
            this.iInitialNameKey = str;
            this.iInitialSaveMillis = i2;
        }

        public void setStandardOffset(int i2) {
            this.iStandardOffset = i2;
        }

        public void setUpperLimit(int i2, OfYear ofYear) {
            this.iUpperYear = i2;
            this.iUpperOfYear = ofYear;
        }

        RuleSet(RuleSet ruleSet) {
            this.iStandardOffset = ruleSet.iStandardOffset;
            this.iRules = new ArrayList<>(ruleSet.iRules);
            this.iInitialNameKey = ruleSet.iInitialNameKey;
            this.iInitialSaveMillis = ruleSet.iInitialSaveMillis;
            this.iUpperYear = ruleSet.iUpperYear;
            this.iUpperOfYear = ruleSet.iUpperOfYear;
        }
    }

    public static DateTimeZone readFrom(DataInput dataInput, String str) throws IOException {
        int unsignedByte = dataInput.readUnsignedByte();
        if (unsignedByte == 67) {
            return CachedDateTimeZone.forZone(PrecalculatedZone.readFrom(dataInput, str));
        }
        if (unsignedByte != 70) {
            if (unsignedByte == 80) {
                return PrecalculatedZone.readFrom(dataInput, str);
            }
            throw new IOException("Invalid encoding");
        }
        FixedDateTimeZone fixedDateTimeZone = new FixedDateTimeZone(str, dataInput.readUTF(), (int) a(dataInput), (int) a(dataInput));
        DateTimeZone dateTimeZone = DateTimeZone.UTC;
        return fixedDateTimeZone.equals(dateTimeZone) ? dateTimeZone : fixedDateTimeZone;
    }

    public void writeTo(String str, DataOutput dataOutput) throws IOException {
        DateTimeZone dateTimeZone = toDateTimeZone(str, false);
        if (dateTimeZone instanceof FixedDateTimeZone) {
            dataOutput.writeByte(70);
            dataOutput.writeUTF(dateTimeZone.getNameKey(0L));
            b(dataOutput, dateTimeZone.getOffset(0L));
            b(dataOutput, dateTimeZone.getStandardOffset(0L));
            return;
        }
        if (dateTimeZone instanceof CachedDateTimeZone) {
            dataOutput.writeByte(67);
            dateTimeZone = ((CachedDateTimeZone) dateTimeZone).getUncachedZone();
        } else {
            dataOutput.writeByte(80);
        }
        ((PrecalculatedZone) dateTimeZone).writeTo(dataOutput);
    }

    private static final class Transition {
        private final long iMillis;
        private final String iNameKey;
        private final int iStandardOffset;
        private final int iWallOffset;

        Transition(long j2, Transition transition) {
            this.iMillis = j2;
            this.iNameKey = transition.iNameKey;
            this.iWallOffset = transition.iWallOffset;
            this.iStandardOffset = transition.iStandardOffset;
        }

        public long getMillis() {
            return this.iMillis;
        }

        public String getNameKey() {
            return this.iNameKey;
        }

        public int getSaveMillis() {
            return this.iWallOffset - this.iStandardOffset;
        }

        public int getStandardOffset() {
            return this.iStandardOffset;
        }

        public int getWallOffset() {
            return this.iWallOffset;
        }

        public boolean isTransitionFrom(Transition transition) {
            if (transition == null) {
                return true;
            }
            return this.iMillis > transition.iMillis && !(this.iWallOffset == transition.iWallOffset && this.iNameKey.equals(transition.iNameKey));
        }

        Transition(long j2, Rule rule, int i2) {
            this.iMillis = j2;
            this.iNameKey = rule.getNameKey();
            this.iWallOffset = rule.getSaveMillis() + i2;
            this.iStandardOffset = i2;
        }

        Transition(long j2, String str, int i2, int i3) {
            this.iMillis = j2;
            this.iNameKey = str;
            this.iWallOffset = i2;
            this.iStandardOffset = i3;
        }
    }
}
