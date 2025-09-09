package javax.jmdns.impl.constants;

/* loaded from: classes4.dex */
public enum DNSOperationCode {
    Query("Query", 0),
    IQuery("Inverse Query", 1),
    Status("Status", 2),
    Unassigned("Unassigned", 3),
    Notify("Notify", 4),
    Update("Update", 5);

    static final int OpCode_MASK = 30720;
    private final String _externalName;
    private final int _index;

    DNSOperationCode(String str, int i2) {
        this._externalName = str;
        this._index = i2;
    }

    public static DNSOperationCode operationCodeForFlags(int i2) {
        int i3 = (i2 & OpCode_MASK) >> 11;
        for (DNSOperationCode dNSOperationCode : values()) {
            if (dNSOperationCode._index == i3) {
                return dNSOperationCode;
            }
        }
        return Unassigned;
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
