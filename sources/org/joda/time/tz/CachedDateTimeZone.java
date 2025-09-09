package org.joda.time.tz;

import org.joda.time.DateTimeZone;

/* loaded from: classes5.dex */
public class CachedDateTimeZone extends DateTimeZone {
    private static final int cInfoCacheMask;
    private static final long serialVersionUID = 5472298452022250685L;
    private final Info[] iInfoCache;
    private final DateTimeZone iZone;

    private static final class Info {

        /* renamed from: a, reason: collision with root package name */
        Info f26738a;
        private String iNameKey;
        public final long iPeriodStart;
        public final DateTimeZone iZoneRef;
        private int iOffset = Integer.MIN_VALUE;
        private int iStandardOffset = Integer.MIN_VALUE;

        Info(DateTimeZone dateTimeZone, long j2) {
            this.iPeriodStart = j2;
            this.iZoneRef = dateTimeZone;
        }

        public String getNameKey(long j2) {
            Info info2 = this.f26738a;
            if (info2 != null && j2 >= info2.iPeriodStart) {
                return info2.getNameKey(j2);
            }
            if (this.iNameKey == null) {
                this.iNameKey = this.iZoneRef.getNameKey(this.iPeriodStart);
            }
            return this.iNameKey;
        }

        public int getOffset(long j2) {
            Info info2 = this.f26738a;
            if (info2 != null && j2 >= info2.iPeriodStart) {
                return info2.getOffset(j2);
            }
            if (this.iOffset == Integer.MIN_VALUE) {
                this.iOffset = this.iZoneRef.getOffset(this.iPeriodStart);
            }
            return this.iOffset;
        }

        public int getStandardOffset(long j2) {
            Info info2 = this.f26738a;
            if (info2 != null && j2 >= info2.iPeriodStart) {
                return info2.getStandardOffset(j2);
            }
            if (this.iStandardOffset == Integer.MIN_VALUE) {
                this.iStandardOffset = this.iZoneRef.getStandardOffset(this.iPeriodStart);
            }
            return this.iStandardOffset;
        }
    }

    static {
        Integer integer;
        int i2;
        try {
            integer = Integer.getInteger("org.joda.time.tz.CachedDateTimeZone.size");
        } catch (SecurityException unused) {
            integer = null;
        }
        if (integer == null) {
            i2 = 512;
        } else {
            int i3 = 0;
            for (int iIntValue = integer.intValue() - 1; iIntValue > 0; iIntValue >>= 1) {
                i3++;
            }
            i2 = 1 << i3;
        }
        cInfoCacheMask = i2 - 1;
    }

    private CachedDateTimeZone(DateTimeZone dateTimeZone) {
        super(dateTimeZone.getID());
        this.iInfoCache = new Info[cInfoCacheMask + 1];
        this.iZone = dateTimeZone;
    }

    private Info createInfo(long j2) {
        long j3 = j2 & (-4294967296L);
        Info info2 = new Info(this.iZone, j3);
        long j4 = 4294967295L | j3;
        Info info3 = info2;
        while (true) {
            long jNextTransition = this.iZone.nextTransition(j3);
            if (jNextTransition == j3 || jNextTransition > j4) {
                break;
            }
            Info info4 = new Info(this.iZone, jNextTransition);
            info3.f26738a = info4;
            info3 = info4;
            j3 = jNextTransition;
        }
        return info2;
    }

    public static CachedDateTimeZone forZone(DateTimeZone dateTimeZone) {
        return dateTimeZone instanceof CachedDateTimeZone ? (CachedDateTimeZone) dateTimeZone : new CachedDateTimeZone(dateTimeZone);
    }

    private Info getInfo(long j2) {
        int i2 = (int) (j2 >> 32);
        Info[] infoArr = this.iInfoCache;
        int i3 = cInfoCacheMask & i2;
        Info info2 = infoArr[i3];
        if (info2 != null && ((int) (info2.iPeriodStart >> 32)) == i2) {
            return info2;
        }
        Info infoCreateInfo = createInfo(j2);
        infoArr[i3] = infoCreateInfo;
        return infoCreateInfo;
    }

    @Override // org.joda.time.DateTimeZone
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof CachedDateTimeZone) {
            return this.iZone.equals(((CachedDateTimeZone) obj).iZone);
        }
        return false;
    }

    @Override // org.joda.time.DateTimeZone
    public String getNameKey(long j2) {
        return getInfo(j2).getNameKey(j2);
    }

    @Override // org.joda.time.DateTimeZone
    public int getOffset(long j2) {
        return getInfo(j2).getOffset(j2);
    }

    @Override // org.joda.time.DateTimeZone
    public int getStandardOffset(long j2) {
        return getInfo(j2).getStandardOffset(j2);
    }

    public DateTimeZone getUncachedZone() {
        return this.iZone;
    }

    @Override // org.joda.time.DateTimeZone
    public int hashCode() {
        return this.iZone.hashCode();
    }

    @Override // org.joda.time.DateTimeZone
    public boolean isFixed() {
        return this.iZone.isFixed();
    }

    @Override // org.joda.time.DateTimeZone
    public long nextTransition(long j2) {
        return this.iZone.nextTransition(j2);
    }

    @Override // org.joda.time.DateTimeZone
    public long previousTransition(long j2) {
        return this.iZone.previousTransition(j2);
    }
}
