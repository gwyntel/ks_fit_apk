package org.joda.time.field;

import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;

/* loaded from: classes5.dex */
public abstract class ImpreciseDateTimeField extends BaseDateTimeField {
    private static final long serialVersionUID = 7190739608550251860L;

    /* renamed from: a, reason: collision with root package name */
    final long f26717a;
    private final DurationField iDurationField;

    private final class LinkedDurationField extends BaseDurationField {
        private static final long serialVersionUID = -203813474600094134L;

        LinkedDurationField(DurationFieldType durationFieldType) {
            super(durationFieldType);
        }

        @Override // org.joda.time.DurationField
        public long add(long j2, int i2) {
            return ImpreciseDateTimeField.this.add(j2, i2);
        }

        @Override // org.joda.time.field.BaseDurationField, org.joda.time.DurationField
        public int getDifference(long j2, long j3) {
            return ImpreciseDateTimeField.this.getDifference(j2, j3);
        }

        @Override // org.joda.time.DurationField
        public long getDifferenceAsLong(long j2, long j3) {
            return ImpreciseDateTimeField.this.getDifferenceAsLong(j2, j3);
        }

        @Override // org.joda.time.DurationField
        public long getMillis(int i2, long j2) {
            return ImpreciseDateTimeField.this.add(j2, i2) - j2;
        }

        @Override // org.joda.time.DurationField
        public long getUnitMillis() {
            return ImpreciseDateTimeField.this.f26717a;
        }

        @Override // org.joda.time.field.BaseDurationField, org.joda.time.DurationField
        public int getValue(long j2, long j3) {
            return ImpreciseDateTimeField.this.getDifference(j2 + j3, j3);
        }

        @Override // org.joda.time.DurationField
        public long getValueAsLong(long j2, long j3) {
            return ImpreciseDateTimeField.this.getDifferenceAsLong(j2 + j3, j3);
        }

        @Override // org.joda.time.DurationField
        public boolean isPrecise() {
            return false;
        }

        @Override // org.joda.time.DurationField
        public long add(long j2, long j3) {
            return ImpreciseDateTimeField.this.add(j2, j3);
        }

        @Override // org.joda.time.DurationField
        public long getMillis(long j2, long j3) {
            return ImpreciseDateTimeField.this.add(j3, j2) - j3;
        }
    }

    public ImpreciseDateTimeField(DateTimeFieldType dateTimeFieldType, long j2) {
        super(dateTimeFieldType);
        this.f26717a = j2;
        this.iDurationField = new LinkedDurationField(dateTimeFieldType.getDurationType());
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public abstract long add(long j2, int i2);

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public abstract long add(long j2, long j3);

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public abstract int get(long j2);

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public int getDifference(long j2, long j3) {
        return FieldUtils.safeToInt(getDifferenceAsLong(j2, j3));
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long getDifferenceAsLong(long j2, long j3) {
        if (j2 < j3) {
            return -getDifferenceAsLong(j3, j2);
        }
        long j4 = (j2 - j3) / this.f26717a;
        if (add(j3, j4) < j2) {
            while (true) {
                long j5 = j4 + 1;
                if (add(j3, j5) > j2) {
                    break;
                }
                j4 = j5;
            }
        } else if (add(j3, j4) > j2) {
            do {
                j4--;
            } while (add(j3, j4) > j2);
        }
        return j4;
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public final DurationField getDurationField() {
        return this.iDurationField;
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public abstract DurationField getRangeDurationField();

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public abstract long roundFloor(long j2);

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public abstract long set(long j2, int i2);
}
