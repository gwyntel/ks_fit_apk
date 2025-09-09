package javax.jmdns.impl;

import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.facebook.appevents.UserDataStore;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import javax.jmdns.ServiceInfo;
import javax.jmdns.impl.constants.DNSRecordClass;
import javax.jmdns.impl.constants.DNSRecordType;

/* loaded from: classes4.dex */
public abstract class DNSEntry {
    private final DNSRecordClass _dnsClass;
    private final String _key;
    private final String _name;
    private final DNSRecordType _recordType;
    private final String _type;
    private final boolean _unique;

    /* renamed from: a, reason: collision with root package name */
    final Map f25319a;

    DNSEntry(String str, DNSRecordType dNSRecordType, DNSRecordClass dNSRecordClass, boolean z2) {
        String str2;
        String str3;
        this._name = str;
        this._recordType = dNSRecordType;
        this._dnsClass = dNSRecordClass;
        this._unique = z2;
        Map<ServiceInfo.Fields, String> mapDecodeQualifiedNameMapForType = ServiceInfoImpl.decodeQualifiedNameMapForType(getName());
        this.f25319a = mapDecodeQualifiedNameMapForType;
        String str4 = mapDecodeQualifiedNameMapForType.get(ServiceInfo.Fields.Domain);
        String str5 = mapDecodeQualifiedNameMapForType.get(ServiceInfo.Fields.Protocol);
        String str6 = mapDecodeQualifiedNameMapForType.get(ServiceInfo.Fields.Application);
        String lowerCase = mapDecodeQualifiedNameMapForType.get(ServiceInfo.Fields.Instance).toLowerCase();
        StringBuilder sb = new StringBuilder();
        String str7 = "";
        if (str6.length() > 0) {
            str2 = OpenAccountUIConstants.UNDER_LINE + str6 + ".";
        } else {
            str2 = "";
        }
        sb.append(str2);
        if (str5.length() > 0) {
            str3 = OpenAccountUIConstants.UNDER_LINE + str5 + ".";
        } else {
            str3 = "";
        }
        sb.append(str3);
        sb.append(str4);
        sb.append(".");
        String string = sb.toString();
        this._type = string;
        StringBuilder sb2 = new StringBuilder();
        if (lowerCase.length() > 0) {
            str7 = lowerCase + ".";
        }
        sb2.append(str7);
        sb2.append(string);
        this._key = sb2.toString().toLowerCase();
    }

    protected void a(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.write(getName().getBytes("UTF8"));
        dataOutputStream.writeShort(getRecordType().indexValue());
        dataOutputStream.writeShort(getRecordClass().indexValue());
    }

    protected byte[] b() throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            a(dataOutputStream);
            dataOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException unused) {
            throw new InternalError();
        }
    }

    public int compareTo(DNSEntry dNSEntry) throws IOException {
        byte[] bArrB = b();
        byte[] bArrB2 = dNSEntry.b();
        int iMin = Math.min(bArrB.length, bArrB2.length);
        for (int i2 = 0; i2 < iMin; i2++) {
            byte b2 = bArrB[i2];
            byte b3 = bArrB2[i2];
            if (b2 > b3) {
                return 1;
            }
            if (b2 < b3) {
                return -1;
            }
        }
        return bArrB.length - bArrB2.length;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DNSEntry)) {
            return false;
        }
        DNSEntry dNSEntry = (DNSEntry) obj;
        return getKey().equals(dNSEntry.getKey()) && getRecordType().equals(dNSEntry.getRecordType()) && getRecordClass() == dNSEntry.getRecordClass();
    }

    public String getKey() {
        String str = this._key;
        return str != null ? str : "";
    }

    public String getName() {
        String str = this._name;
        return str != null ? str : "";
    }

    public Map<ServiceInfo.Fields, String> getQualifiedNameMap() {
        return Collections.unmodifiableMap(this.f25319a);
    }

    public DNSRecordClass getRecordClass() {
        DNSRecordClass dNSRecordClass = this._dnsClass;
        return dNSRecordClass != null ? dNSRecordClass : DNSRecordClass.CLASS_UNKNOWN;
    }

    public DNSRecordType getRecordType() {
        DNSRecordType dNSRecordType = this._recordType;
        return dNSRecordType != null ? dNSRecordType : DNSRecordType.TYPE_IGNORE;
    }

    public String getSubtype() {
        String str = getQualifiedNameMap().get(ServiceInfo.Fields.Subtype);
        return str != null ? str : "";
    }

    public String getType() {
        String str = this._type;
        return str != null ? str : "";
    }

    public int hashCode() {
        return getKey().hashCode() + getRecordType().indexValue() + getRecordClass().indexValue();
    }

    public boolean isDomainDiscoveryQuery() {
        if (!((String) this.f25319a.get(ServiceInfo.Fields.Application)).equals("dns-sd")) {
            return false;
        }
        String str = (String) this.f25319a.get(ServiceInfo.Fields.Instance);
        return "b".equals(str) || UserDataStore.DATE_OF_BIRTH.equals(str) || "r".equals(str) || "dr".equals(str) || "lb".equals(str);
    }

    public abstract boolean isExpired(long j2);

    public boolean isReverseLookup() {
        return isV4ReverseLookup() || isV6ReverseLookup();
    }

    public boolean isSameEntry(DNSEntry dNSEntry) {
        return getKey().equals(dNSEntry.getKey()) && getRecordType().equals(dNSEntry.getRecordType()) && (DNSRecordClass.CLASS_ANY == dNSEntry.getRecordClass() || getRecordClass().equals(dNSEntry.getRecordClass()));
    }

    public boolean isSameRecordClass(DNSEntry dNSEntry) {
        return dNSEntry != null && dNSEntry.getRecordClass() == getRecordClass();
    }

    public boolean isSameType(DNSEntry dNSEntry) {
        return dNSEntry != null && dNSEntry.getRecordType() == getRecordType();
    }

    public boolean isServicesDiscoveryMetaQuery() {
        return ((String) this.f25319a.get(ServiceInfo.Fields.Application)).equals("dns-sd") && ((String) this.f25319a.get(ServiceInfo.Fields.Instance)).equals("_services");
    }

    public abstract boolean isStale(long j2);

    public boolean isUnique() {
        return this._unique;
    }

    public boolean isV4ReverseLookup() {
        return ((String) this.f25319a.get(ServiceInfo.Fields.Domain)).endsWith("in-addr.arpa");
    }

    public boolean isV6ReverseLookup() {
        return ((String) this.f25319a.get(ServiceInfo.Fields.Domain)).endsWith("ip6.arpa");
    }

    public boolean sameSubtype(DNSEntry dNSEntry) {
        return getSubtype().equals(dNSEntry.getSubtype());
    }

    protected void toString(StringBuilder sb) {
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(200);
        sb.append("[" + getClass().getSimpleName() + "@" + System.identityHashCode(this));
        StringBuilder sb2 = new StringBuilder();
        sb2.append(" type: ");
        sb2.append(getRecordType());
        sb.append(sb2.toString());
        sb.append(", class: " + getRecordClass());
        sb.append(this._unique ? "-unique," : ",");
        sb.append(" name: " + this._name);
        toString(sb);
        sb.append("]");
        return sb.toString();
    }
}
