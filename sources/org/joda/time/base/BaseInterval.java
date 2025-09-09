package org.joda.time.base;

import java.io.Serializable;
import org.joda.time.Chronology;
import org.joda.time.DateTimeUtils;
import org.joda.time.MutableInterval;
import org.joda.time.ReadWritableInterval;
import org.joda.time.ReadableDuration;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadableInterval;
import org.joda.time.ReadablePeriod;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.convert.ConverterManager;
import org.joda.time.convert.IntervalConverter;
import org.joda.time.field.FieldUtils;

/* loaded from: classes5.dex */
public abstract class BaseInterval extends AbstractInterval implements ReadableInterval, Serializable {
    private static final long serialVersionUID = 576586928732749278L;
    private volatile Chronology iChronology;
    private volatile long iEndMillis;
    private volatile long iStartMillis;

    protected BaseInterval(long j2, long j3, Chronology chronology) {
        this.iChronology = DateTimeUtils.getChronology(chronology);
        checkInterval(j2, j3);
        this.iStartMillis = j2;
        this.iEndMillis = j3;
    }

    @Override // org.joda.time.ReadableInterval
    public Chronology getChronology() {
        return this.iChronology;
    }

    @Override // org.joda.time.ReadableInterval
    public long getEndMillis() {
        return this.iEndMillis;
    }

    @Override // org.joda.time.ReadableInterval
    public long getStartMillis() {
        return this.iStartMillis;
    }

    protected void setInterval(long j2, long j3, Chronology chronology) {
        checkInterval(j2, j3);
        this.iStartMillis = j2;
        this.iEndMillis = j3;
        this.iChronology = DateTimeUtils.getChronology(chronology);
    }

    protected BaseInterval(ReadableInstant readableInstant, ReadableInstant readableInstant2) {
        if (readableInstant == null && readableInstant2 == null) {
            long jCurrentTimeMillis = DateTimeUtils.currentTimeMillis();
            this.iEndMillis = jCurrentTimeMillis;
            this.iStartMillis = jCurrentTimeMillis;
            this.iChronology = ISOChronology.getInstance();
            return;
        }
        this.iChronology = DateTimeUtils.getInstantChronology(readableInstant);
        this.iStartMillis = DateTimeUtils.getInstantMillis(readableInstant);
        this.iEndMillis = DateTimeUtils.getInstantMillis(readableInstant2);
        checkInterval(this.iStartMillis, this.iEndMillis);
    }

    protected BaseInterval(ReadableInstant readableInstant, ReadableDuration readableDuration) {
        this.iChronology = DateTimeUtils.getInstantChronology(readableInstant);
        this.iStartMillis = DateTimeUtils.getInstantMillis(readableInstant);
        this.iEndMillis = FieldUtils.safeAdd(this.iStartMillis, DateTimeUtils.getDurationMillis(readableDuration));
        checkInterval(this.iStartMillis, this.iEndMillis);
    }

    protected BaseInterval(ReadableDuration readableDuration, ReadableInstant readableInstant) {
        this.iChronology = DateTimeUtils.getInstantChronology(readableInstant);
        this.iEndMillis = DateTimeUtils.getInstantMillis(readableInstant);
        this.iStartMillis = FieldUtils.safeAdd(this.iEndMillis, -DateTimeUtils.getDurationMillis(readableDuration));
        checkInterval(this.iStartMillis, this.iEndMillis);
    }

    protected BaseInterval(ReadableInstant readableInstant, ReadablePeriod readablePeriod) {
        Chronology instantChronology = DateTimeUtils.getInstantChronology(readableInstant);
        this.iChronology = instantChronology;
        this.iStartMillis = DateTimeUtils.getInstantMillis(readableInstant);
        if (readablePeriod == null) {
            this.iEndMillis = this.iStartMillis;
        } else {
            this.iEndMillis = instantChronology.add(readablePeriod, this.iStartMillis, 1);
        }
        checkInterval(this.iStartMillis, this.iEndMillis);
    }

    protected BaseInterval(ReadablePeriod readablePeriod, ReadableInstant readableInstant) {
        Chronology instantChronology = DateTimeUtils.getInstantChronology(readableInstant);
        this.iChronology = instantChronology;
        this.iEndMillis = DateTimeUtils.getInstantMillis(readableInstant);
        if (readablePeriod == null) {
            this.iStartMillis = this.iEndMillis;
        } else {
            this.iStartMillis = instantChronology.add(readablePeriod, this.iEndMillis, -1);
        }
        checkInterval(this.iStartMillis, this.iEndMillis);
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected BaseInterval(Object obj, Chronology chronology) {
        IntervalConverter intervalConverter = ConverterManager.getInstance().getIntervalConverter(obj);
        if (intervalConverter.isReadableInterval(obj, chronology)) {
            ReadableInterval readableInterval = (ReadableInterval) obj;
            this.iChronology = chronology == null ? readableInterval.getChronology() : chronology;
            this.iStartMillis = readableInterval.getStartMillis();
            this.iEndMillis = readableInterval.getEndMillis();
        } else if (this instanceof ReadWritableInterval) {
            intervalConverter.setInto((ReadWritableInterval) this, obj, chronology);
        } else {
            MutableInterval mutableInterval = new MutableInterval();
            intervalConverter.setInto(mutableInterval, obj, chronology);
            this.iChronology = mutableInterval.getChronology();
            this.iStartMillis = mutableInterval.getStartMillis();
            this.iEndMillis = mutableInterval.getEndMillis();
        }
        checkInterval(this.iStartMillis, this.iEndMillis);
    }
}
