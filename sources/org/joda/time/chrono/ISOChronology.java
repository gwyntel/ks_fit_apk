package org.joda.time.chrono;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.joda.time.Chronology;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.AssembledChronology;
import org.joda.time.field.DividedDateTimeField;
import org.joda.time.field.RemainderDateTimeField;

/* loaded from: classes5.dex */
public final class ISOChronology extends AssembledChronology {
    private static final int FAST_CACHE_SIZE = 64;
    private static final ISOChronology INSTANCE_UTC;
    private static final Map<DateTimeZone, ISOChronology> cCache;
    private static final ISOChronology[] cFastCache;
    private static final long serialVersionUID = -6212696554273812441L;

    private static final class Stub implements Serializable {
        private static final long serialVersionUID = -6212696554273812441L;
        private transient DateTimeZone iZone;

        Stub(DateTimeZone dateTimeZone) {
            this.iZone = dateTimeZone;
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            this.iZone = (DateTimeZone) objectInputStream.readObject();
        }

        private Object readResolve() {
            return ISOChronology.getInstance(this.iZone);
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.writeObject(this.iZone);
        }
    }

    static {
        HashMap map = new HashMap();
        cCache = map;
        cFastCache = new ISOChronology[64];
        ISOChronology iSOChronology = new ISOChronology(GregorianChronology.getInstanceUTC());
        INSTANCE_UTC = iSOChronology;
        map.put(DateTimeZone.UTC, iSOChronology);
    }

    private ISOChronology(Chronology chronology) {
        super(chronology, null);
    }

    public static ISOChronology getInstance() {
        return getInstance(DateTimeZone.getDefault());
    }

    public static ISOChronology getInstanceUTC() {
        return INSTANCE_UTC;
    }

    private Object writeReplace() {
        return new Stub(getZone());
    }

    @Override // org.joda.time.chrono.AssembledChronology
    protected void assemble(AssembledChronology.Fields fields) {
        if (getBase().getZone() == DateTimeZone.UTC) {
            DividedDateTimeField dividedDateTimeField = new DividedDateTimeField(ISOYearOfEraDateTimeField.f26695a, DateTimeFieldType.centuryOfEra(), 100);
            fields.centuryOfEra = dividedDateTimeField;
            fields.yearOfCentury = new RemainderDateTimeField(dividedDateTimeField, DateTimeFieldType.yearOfCentury());
            fields.weekyearOfCentury = new RemainderDateTimeField((DividedDateTimeField) fields.centuryOfEra, DateTimeFieldType.weekyearOfCentury());
            fields.centuries = fields.centuryOfEra.getDurationField();
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ISOChronology) {
            return getZone().equals(((ISOChronology) obj).getZone());
        }
        return false;
    }

    public int hashCode() {
        return 800855 + getZone().hashCode();
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public String toString() {
        DateTimeZone zone = getZone();
        if (zone == null) {
            return "ISOChronology";
        }
        return "ISOChronology[" + zone.getID() + ']';
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public Chronology withUTC() {
        return INSTANCE_UTC;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public Chronology withZone(DateTimeZone dateTimeZone) {
        if (dateTimeZone == null) {
            dateTimeZone = DateTimeZone.getDefault();
        }
        return dateTimeZone == getZone() ? this : getInstance(dateTimeZone);
    }

    public static ISOChronology getInstance(DateTimeZone dateTimeZone) {
        ISOChronology iSOChronology;
        if (dateTimeZone == null) {
            dateTimeZone = DateTimeZone.getDefault();
        }
        int iIdentityHashCode = System.identityHashCode(dateTimeZone) & 63;
        ISOChronology[] iSOChronologyArr = cFastCache;
        ISOChronology iSOChronology2 = iSOChronologyArr[iIdentityHashCode];
        if (iSOChronology2 != null && iSOChronology2.getZone() == dateTimeZone) {
            return iSOChronology2;
        }
        Map<DateTimeZone, ISOChronology> map = cCache;
        synchronized (map) {
            try {
                iSOChronology = map.get(dateTimeZone);
                if (iSOChronology == null) {
                    iSOChronology = new ISOChronology(ZonedChronology.getInstance(INSTANCE_UTC, dateTimeZone));
                    map.put(dateTimeZone, iSOChronology);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        iSOChronologyArr[iIdentityHashCode] = iSOChronology;
        return iSOChronology;
    }
}
