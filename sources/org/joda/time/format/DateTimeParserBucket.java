package org.joda.time.format;

import java.util.Arrays;
import java.util.Locale;
import kotlin.text.Typography;
import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.IllegalInstantException;

/* loaded from: classes5.dex */
public class DateTimeParserBucket {
    private final Chronology iChrono;
    private int iDefaultYear;
    private Locale iLocale;
    private final long iMillis;
    private Integer iOffset;
    private Integer iPivotYear;
    private SavedField[] iSavedFields;
    private int iSavedFieldsCount;
    private boolean iSavedFieldsShared;
    private Object iSavedState;
    private DateTimeZone iZone;

    static class SavedField implements Comparable<SavedField> {

        /* renamed from: a, reason: collision with root package name */
        final DateTimeField f26727a;

        /* renamed from: b, reason: collision with root package name */
        final int f26728b;

        /* renamed from: c, reason: collision with root package name */
        final String f26729c;

        /* renamed from: d, reason: collision with root package name */
        final Locale f26730d;

        SavedField(DateTimeField dateTimeField, int i2) {
            this.f26727a = dateTimeField;
            this.f26728b = i2;
            this.f26729c = null;
            this.f26730d = null;
        }

        long a(long j2, boolean z2) {
            String str = this.f26729c;
            long j3 = str == null ? this.f26727a.set(j2, this.f26728b) : this.f26727a.set(j2, str, this.f26730d);
            return z2 ? this.f26727a.roundFloor(j3) : j3;
        }

        @Override // java.lang.Comparable
        public int compareTo(SavedField savedField) {
            DateTimeField dateTimeField = savedField.f26727a;
            int iJ = DateTimeParserBucket.j(this.f26727a.getRangeDurationField(), dateTimeField.getRangeDurationField());
            return iJ != 0 ? iJ : DateTimeParserBucket.j(this.f26727a.getDurationField(), dateTimeField.getDurationField());
        }

        SavedField(DateTimeField dateTimeField, String str, Locale locale) {
            this.f26727a = dateTimeField;
            this.f26728b = 0;
            this.f26729c = str;
            this.f26730d = locale;
        }
    }

    class SavedState {

        /* renamed from: a, reason: collision with root package name */
        final DateTimeZone f26731a;

        /* renamed from: b, reason: collision with root package name */
        final Integer f26732b;

        /* renamed from: c, reason: collision with root package name */
        final SavedField[] f26733c;

        /* renamed from: d, reason: collision with root package name */
        final int f26734d;

        SavedState() {
            this.f26731a = DateTimeParserBucket.this.iZone;
            this.f26732b = DateTimeParserBucket.this.iOffset;
            this.f26733c = DateTimeParserBucket.this.iSavedFields;
            this.f26734d = DateTimeParserBucket.this.iSavedFieldsCount;
        }

        boolean a(DateTimeParserBucket dateTimeParserBucket) {
            if (dateTimeParserBucket != DateTimeParserBucket.this) {
                return false;
            }
            dateTimeParserBucket.iZone = this.f26731a;
            dateTimeParserBucket.iOffset = this.f26732b;
            dateTimeParserBucket.iSavedFields = this.f26733c;
            if (this.f26734d < dateTimeParserBucket.iSavedFieldsCount) {
                dateTimeParserBucket.iSavedFieldsShared = true;
            }
            dateTimeParserBucket.iSavedFieldsCount = this.f26734d;
            return true;
        }
    }

    @Deprecated
    public DateTimeParserBucket(long j2, Chronology chronology, Locale locale) {
        this(j2, chronology, locale, null, 2000);
    }

    static int j(DurationField durationField, DurationField durationField2) {
        if (durationField == null || !durationField.isSupported()) {
            return (durationField2 == null || !durationField2.isSupported()) ? 0 : -1;
        }
        if (durationField2 == null || !durationField2.isSupported()) {
            return 1;
        }
        return -durationField.compareTo(durationField2);
    }

    private static void sort(SavedField[] savedFieldArr, int i2) {
        if (i2 > 10) {
            Arrays.sort(savedFieldArr, 0, i2);
            return;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            for (int i4 = i3; i4 > 0; i4--) {
                int i5 = i4 - 1;
                if (savedFieldArr[i5].compareTo(savedFieldArr[i4]) > 0) {
                    SavedField savedField = savedFieldArr[i4];
                    savedFieldArr[i4] = savedFieldArr[i5];
                    savedFieldArr[i5] = savedField;
                }
            }
        }
    }

    public long computeMillis() {
        return computeMillis(false, null);
    }

    public Chronology getChronology() {
        return this.iChrono;
    }

    public Locale getLocale() {
        return this.iLocale;
    }

    @Deprecated
    public int getOffset() {
        Integer num = this.iOffset;
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public Integer getOffsetInteger() {
        return this.iOffset;
    }

    public Integer getPivotYear() {
        return this.iPivotYear;
    }

    public DateTimeZone getZone() {
        return this.iZone;
    }

    public boolean restoreState(Object obj) {
        if (!(obj instanceof SavedState) || !((SavedState) obj).a(this)) {
            return false;
        }
        this.iSavedState = obj;
        return true;
    }

    public void saveField(DateTimeField dateTimeField, int i2) {
        saveField(new SavedField(dateTimeField, i2));
    }

    public Object saveState() {
        if (this.iSavedState == null) {
            this.iSavedState = new SavedState();
        }
        return this.iSavedState;
    }

    @Deprecated
    public void setOffset(int i2) {
        this.iSavedState = null;
        this.iOffset = Integer.valueOf(i2);
    }

    public void setPivotYear(Integer num) {
        this.iPivotYear = num;
    }

    public void setZone(DateTimeZone dateTimeZone) {
        this.iSavedState = null;
        this.iZone = dateTimeZone;
    }

    @Deprecated
    public DateTimeParserBucket(long j2, Chronology chronology, Locale locale, Integer num) {
        this(j2, chronology, locale, num, 2000);
    }

    public long computeMillis(boolean z2) {
        return computeMillis(z2, null);
    }

    public void saveField(DateTimeFieldType dateTimeFieldType, int i2) {
        saveField(new SavedField(dateTimeFieldType.getField(this.iChrono), i2));
    }

    public DateTimeParserBucket(long j2, Chronology chronology, Locale locale, Integer num, int i2) {
        this.iSavedFields = new SavedField[8];
        Chronology chronology2 = DateTimeUtils.getChronology(chronology);
        this.iMillis = j2;
        this.iZone = chronology2.getZone();
        this.iChrono = chronology2.withUTC();
        this.iLocale = locale == null ? Locale.getDefault() : locale;
        this.iPivotYear = num;
        this.iDefaultYear = i2;
    }

    public long computeMillis(boolean z2, String str) {
        SavedField[] savedFieldArr = this.iSavedFields;
        int i2 = this.iSavedFieldsCount;
        if (this.iSavedFieldsShared) {
            savedFieldArr = (SavedField[]) savedFieldArr.clone();
            this.iSavedFields = savedFieldArr;
            this.iSavedFieldsShared = false;
        }
        sort(savedFieldArr, i2);
        if (i2 > 0) {
            DurationField field = DurationFieldType.months().getField(this.iChrono);
            DurationField field2 = DurationFieldType.days().getField(this.iChrono);
            DurationField durationField = savedFieldArr[0].f26727a.getDurationField();
            if (j(durationField, field) >= 0 && j(durationField, field2) <= 0) {
                saveField(DateTimeFieldType.year(), this.iDefaultYear);
                return computeMillis(z2, str);
            }
        }
        long jA = this.iMillis;
        for (int i3 = 0; i3 < i2; i3++) {
            try {
                jA = savedFieldArr[i3].a(jA, z2);
            } catch (IllegalFieldValueException e2) {
                if (str != null) {
                    e2.prependMessage("Cannot parse \"" + str + Typography.quote);
                }
                throw e2;
            }
        }
        if (z2) {
            int i4 = 0;
            while (i4 < i2) {
                jA = savedFieldArr[i4].a(jA, i4 == i2 + (-1));
                i4++;
            }
        }
        if (this.iOffset != null) {
            return jA - r9.intValue();
        }
        DateTimeZone dateTimeZone = this.iZone;
        if (dateTimeZone == null) {
            return jA;
        }
        int offsetFromLocal = dateTimeZone.getOffsetFromLocal(jA);
        long j2 = jA - offsetFromLocal;
        if (offsetFromLocal == this.iZone.getOffset(j2)) {
            return j2;
        }
        String str2 = "Illegal instant due to time zone offset transition (" + this.iZone + ')';
        if (str != null) {
            str2 = "Cannot parse \"" + str + "\": " + str2;
        }
        throw new IllegalInstantException(str2);
    }

    public void saveField(DateTimeFieldType dateTimeFieldType, String str, Locale locale) {
        saveField(new SavedField(dateTimeFieldType.getField(this.iChrono), str, locale));
    }

    public void setOffset(Integer num) {
        this.iSavedState = null;
        this.iOffset = num;
    }

    private void saveField(SavedField savedField) {
        SavedField[] savedFieldArr = this.iSavedFields;
        int i2 = this.iSavedFieldsCount;
        if (i2 == savedFieldArr.length || this.iSavedFieldsShared) {
            SavedField[] savedFieldArr2 = new SavedField[i2 == savedFieldArr.length ? i2 * 2 : savedFieldArr.length];
            System.arraycopy(savedFieldArr, 0, savedFieldArr2, 0, i2);
            this.iSavedFields = savedFieldArr2;
            this.iSavedFieldsShared = false;
            savedFieldArr = savedFieldArr2;
        }
        this.iSavedState = null;
        savedFieldArr[i2] = savedField;
        this.iSavedFieldsCount = i2 + 1;
    }
}
