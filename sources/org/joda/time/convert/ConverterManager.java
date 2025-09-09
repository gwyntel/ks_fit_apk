package org.joda.time.convert;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import org.joda.time.JodaTimePermission;

/* loaded from: classes5.dex */
public final class ConverterManager {
    private static ConverterManager INSTANCE;
    private ConverterSet iDurationConverters;
    private ConverterSet iInstantConverters;
    private ConverterSet iIntervalConverters;
    private ConverterSet iPartialConverters;
    private ConverterSet iPeriodConverters;

    protected ConverterManager() {
        ReadableInstantConverter readableInstantConverter = ReadableInstantConverter.f26710a;
        StringConverter stringConverter = StringConverter.f26714a;
        CalendarConverter calendarConverter = CalendarConverter.f26703a;
        DateConverter dateConverter = DateConverter.f26706a;
        LongConverter longConverter = LongConverter.f26707a;
        NullConverter nullConverter = NullConverter.f26708a;
        this.iInstantConverters = new ConverterSet(new Converter[]{readableInstantConverter, stringConverter, calendarConverter, dateConverter, longConverter, nullConverter});
        this.iPartialConverters = new ConverterSet(new Converter[]{ReadablePartialConverter.f26712a, readableInstantConverter, stringConverter, calendarConverter, dateConverter, longConverter, nullConverter});
        ReadableDurationConverter readableDurationConverter = ReadableDurationConverter.f26709a;
        ReadableIntervalConverter readableIntervalConverter = ReadableIntervalConverter.f26711a;
        this.iDurationConverters = new ConverterSet(new Converter[]{readableDurationConverter, readableIntervalConverter, stringConverter, longConverter, nullConverter});
        this.iPeriodConverters = new ConverterSet(new Converter[]{readableDurationConverter, ReadablePeriodConverter.f26713a, readableIntervalConverter, stringConverter, nullConverter});
        this.iIntervalConverters = new ConverterSet(new Converter[]{readableIntervalConverter, stringConverter, nullConverter});
    }

    private void checkAlterDurationConverters() throws SecurityException {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new JodaTimePermission("ConverterManager.alterDurationConverters"));
        }
    }

    private void checkAlterInstantConverters() throws SecurityException {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new JodaTimePermission("ConverterManager.alterInstantConverters"));
        }
    }

    private void checkAlterIntervalConverters() throws SecurityException {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new JodaTimePermission("ConverterManager.alterIntervalConverters"));
        }
    }

    private void checkAlterPartialConverters() throws SecurityException {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new JodaTimePermission("ConverterManager.alterPartialConverters"));
        }
    }

    private void checkAlterPeriodConverters() throws SecurityException {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new JodaTimePermission("ConverterManager.alterPeriodConverters"));
        }
    }

    public static ConverterManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConverterManager();
        }
        return INSTANCE;
    }

    public DurationConverter addDurationConverter(DurationConverter durationConverter) throws SecurityException {
        checkAlterDurationConverters();
        if (durationConverter == null) {
            return null;
        }
        DurationConverter[] durationConverterArr = new DurationConverter[1];
        this.iDurationConverters = this.iDurationConverters.a(durationConverter, durationConverterArr);
        return durationConverterArr[0];
    }

    public InstantConverter addInstantConverter(InstantConverter instantConverter) throws SecurityException {
        checkAlterInstantConverters();
        if (instantConverter == null) {
            return null;
        }
        InstantConverter[] instantConverterArr = new InstantConverter[1];
        this.iInstantConverters = this.iInstantConverters.a(instantConverter, instantConverterArr);
        return instantConverterArr[0];
    }

    public IntervalConverter addIntervalConverter(IntervalConverter intervalConverter) throws SecurityException {
        checkAlterIntervalConverters();
        if (intervalConverter == null) {
            return null;
        }
        IntervalConverter[] intervalConverterArr = new IntervalConverter[1];
        this.iIntervalConverters = this.iIntervalConverters.a(intervalConverter, intervalConverterArr);
        return intervalConverterArr[0];
    }

    public PartialConverter addPartialConverter(PartialConverter partialConverter) throws SecurityException {
        checkAlterPartialConverters();
        if (partialConverter == null) {
            return null;
        }
        PartialConverter[] partialConverterArr = new PartialConverter[1];
        this.iPartialConverters = this.iPartialConverters.a(partialConverter, partialConverterArr);
        return partialConverterArr[0];
    }

    public PeriodConverter addPeriodConverter(PeriodConverter periodConverter) throws SecurityException {
        checkAlterPeriodConverters();
        if (periodConverter == null) {
            return null;
        }
        PeriodConverter[] periodConverterArr = new PeriodConverter[1];
        this.iPeriodConverters = this.iPeriodConverters.a(periodConverter, periodConverterArr);
        return periodConverterArr[0];
    }

    public DurationConverter getDurationConverter(Object obj) {
        DurationConverter durationConverter = (DurationConverter) this.iDurationConverters.e(obj == null ? null : obj.getClass());
        if (durationConverter != null) {
            return durationConverter;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("No duration converter found for type: ");
        sb.append(obj == null ? TmpConstant.GROUP_ROLE_UNKNOWN : obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public DurationConverter[] getDurationConverters() {
        ConverterSet converterSet = this.iDurationConverters;
        DurationConverter[] durationConverterArr = new DurationConverter[converterSet.f()];
        converterSet.b(durationConverterArr);
        return durationConverterArr;
    }

    public InstantConverter getInstantConverter(Object obj) {
        InstantConverter instantConverter = (InstantConverter) this.iInstantConverters.e(obj == null ? null : obj.getClass());
        if (instantConverter != null) {
            return instantConverter;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("No instant converter found for type: ");
        sb.append(obj == null ? TmpConstant.GROUP_ROLE_UNKNOWN : obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public InstantConverter[] getInstantConverters() {
        ConverterSet converterSet = this.iInstantConverters;
        InstantConverter[] instantConverterArr = new InstantConverter[converterSet.f()];
        converterSet.b(instantConverterArr);
        return instantConverterArr;
    }

    public IntervalConverter getIntervalConverter(Object obj) {
        IntervalConverter intervalConverter = (IntervalConverter) this.iIntervalConverters.e(obj == null ? null : obj.getClass());
        if (intervalConverter != null) {
            return intervalConverter;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("No interval converter found for type: ");
        sb.append(obj == null ? TmpConstant.GROUP_ROLE_UNKNOWN : obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public IntervalConverter[] getIntervalConverters() {
        ConverterSet converterSet = this.iIntervalConverters;
        IntervalConverter[] intervalConverterArr = new IntervalConverter[converterSet.f()];
        converterSet.b(intervalConverterArr);
        return intervalConverterArr;
    }

    public PartialConverter getPartialConverter(Object obj) {
        PartialConverter partialConverter = (PartialConverter) this.iPartialConverters.e(obj == null ? null : obj.getClass());
        if (partialConverter != null) {
            return partialConverter;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("No partial converter found for type: ");
        sb.append(obj == null ? TmpConstant.GROUP_ROLE_UNKNOWN : obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public PartialConverter[] getPartialConverters() {
        ConverterSet converterSet = this.iPartialConverters;
        PartialConverter[] partialConverterArr = new PartialConverter[converterSet.f()];
        converterSet.b(partialConverterArr);
        return partialConverterArr;
    }

    public PeriodConverter getPeriodConverter(Object obj) {
        PeriodConverter periodConverter = (PeriodConverter) this.iPeriodConverters.e(obj == null ? null : obj.getClass());
        if (periodConverter != null) {
            return periodConverter;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("No period converter found for type: ");
        sb.append(obj == null ? TmpConstant.GROUP_ROLE_UNKNOWN : obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public PeriodConverter[] getPeriodConverters() {
        ConverterSet converterSet = this.iPeriodConverters;
        PeriodConverter[] periodConverterArr = new PeriodConverter[converterSet.f()];
        converterSet.b(periodConverterArr);
        return periodConverterArr;
    }

    public DurationConverter removeDurationConverter(DurationConverter durationConverter) throws SecurityException {
        checkAlterDurationConverters();
        if (durationConverter == null) {
            return null;
        }
        DurationConverter[] durationConverterArr = new DurationConverter[1];
        this.iDurationConverters = this.iDurationConverters.d(durationConverter, durationConverterArr);
        return durationConverterArr[0];
    }

    public InstantConverter removeInstantConverter(InstantConverter instantConverter) throws SecurityException {
        checkAlterInstantConverters();
        if (instantConverter == null) {
            return null;
        }
        InstantConverter[] instantConverterArr = new InstantConverter[1];
        this.iInstantConverters = this.iInstantConverters.d(instantConverter, instantConverterArr);
        return instantConverterArr[0];
    }

    public IntervalConverter removeIntervalConverter(IntervalConverter intervalConverter) throws SecurityException {
        checkAlterIntervalConverters();
        if (intervalConverter == null) {
            return null;
        }
        IntervalConverter[] intervalConverterArr = new IntervalConverter[1];
        this.iIntervalConverters = this.iIntervalConverters.d(intervalConverter, intervalConverterArr);
        return intervalConverterArr[0];
    }

    public PartialConverter removePartialConverter(PartialConverter partialConverter) throws SecurityException {
        checkAlterPartialConverters();
        if (partialConverter == null) {
            return null;
        }
        PartialConverter[] partialConverterArr = new PartialConverter[1];
        this.iPartialConverters = this.iPartialConverters.d(partialConverter, partialConverterArr);
        return partialConverterArr[0];
    }

    public PeriodConverter removePeriodConverter(PeriodConverter periodConverter) throws SecurityException {
        checkAlterPeriodConverters();
        if (periodConverter == null) {
            return null;
        }
        PeriodConverter[] periodConverterArr = new PeriodConverter[1];
        this.iPeriodConverters = this.iPeriodConverters.d(periodConverter, periodConverterArr);
        return periodConverterArr[0];
    }

    public String toString() {
        return "ConverterManager[" + this.iInstantConverters.f() + " instant," + this.iPartialConverters.f() + " partial," + this.iDurationConverters.f() + " duration," + this.iPeriodConverters.f() + " period," + this.iIntervalConverters.f() + " interval]";
    }
}
