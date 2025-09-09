package javax.jmdns.impl.constants;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.language.bm.Languages;

/* loaded from: classes4.dex */
public enum DNSRecordClass {
    CLASS_UNKNOWN("?", 0),
    CLASS_IN("in", 1),
    CLASS_CS("cs", 2),
    CLASS_CH("ch", 3),
    CLASS_HS("hs", 4),
    CLASS_NONE("none", 254),
    CLASS_ANY(Languages.ANY, 255);

    public static final int CLASS_MASK = 32767;
    public static final int CLASS_UNIQUE = 32768;
    public static final boolean NOT_UNIQUE = false;
    public static final boolean UNIQUE = true;
    private static Logger logger = Logger.getLogger(DNSRecordClass.class.getName());
    private final String _externalName;
    private final int _index;

    DNSRecordClass(String str, int i2) {
        this._externalName = str;
        this._index = i2;
    }

    public static DNSRecordClass classForIndex(int i2) {
        int i3 = i2 & CLASS_MASK;
        for (DNSRecordClass dNSRecordClass : values()) {
            if (dNSRecordClass._index == i3) {
                return dNSRecordClass;
            }
        }
        logger.log(Level.WARNING, "Could not find record class for index: " + i2);
        return CLASS_UNKNOWN;
    }

    public static DNSRecordClass classForName(String str) {
        if (str != null) {
            String lowerCase = str.toLowerCase();
            for (DNSRecordClass dNSRecordClass : values()) {
                if (dNSRecordClass._externalName.equals(lowerCase)) {
                    return dNSRecordClass;
                }
            }
        }
        logger.log(Level.WARNING, "Could not find record class for name: " + str);
        return CLASS_UNKNOWN;
    }

    public String externalName() {
        return this._externalName;
    }

    public int indexValue() {
        return this._index;
    }

    public boolean isUnique(int i2) {
        return (this == CLASS_UNKNOWN || (i2 & 32768) == 0) ? false : true;
    }

    @Override // java.lang.Enum
    public String toString() {
        return name() + " index " + indexValue();
    }
}
