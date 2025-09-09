package javax.jmdns.impl.constants;

import com.facebook.internal.AnalyticsEvents;

/* loaded from: classes4.dex */
public enum DNSOptionCode {
    Unknown(AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN, 65535),
    LLQ("LLQ", 1),
    UL("UL", 2),
    NSID("NSID", 3),
    Owner("Owner", 4);

    private final String _externalName;
    private final int _index;

    DNSOptionCode(String str, int i2) {
        this._externalName = str;
        this._index = i2;
    }

    public static DNSOptionCode resultCodeForFlags(int i2) {
        for (DNSOptionCode dNSOptionCode : values()) {
            if (dNSOptionCode._index == i2) {
                return dNSOptionCode;
            }
        }
        return Unknown;
    }

    public String externalName() {
        return this._externalName;
    }

    public int indexValue() {
        return this._index;
    }

    @Override // java.lang.Enum
    public String toString() {
        return name() + " index " + indexValue();
    }
}
