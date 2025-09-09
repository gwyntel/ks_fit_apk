package javax.jmdns.impl;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.umeng.analytics.pro.bc;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.impl.DNSOutgoing;
import javax.jmdns.impl.constants.DNSRecordClass;
import javax.jmdns.impl.constants.DNSRecordType;

/* loaded from: classes4.dex */
public abstract class DNSRecord extends DNSEntry {
    private long _created;
    private InetAddress _source;
    private int _ttl;
    private static Logger logger = Logger.getLogger(DNSRecord.class.getName());
    public static final byte[] EMPTY_TXT = {0};

    public static class HostInformation extends DNSRecord {

        /* renamed from: b, reason: collision with root package name */
        String f25332b;

        /* renamed from: c, reason: collision with root package name */
        String f25333c;

        public HostInformation(String str, DNSRecordClass dNSRecordClass, boolean z2, int i2, String str2, String str3) {
            super(str, DNSRecordType.TYPE_HINFO, dNSRecordClass, z2, i2);
            this.f25333c = str2;
            this.f25332b = str3;
        }

        @Override // javax.jmdns.impl.DNSRecord
        boolean e(JmDNSImpl jmDNSImpl, long j2) {
            return false;
        }

        @Override // javax.jmdns.impl.DNSRecord
        boolean f(JmDNSImpl jmDNSImpl) {
            return false;
        }

        @Override // javax.jmdns.impl.DNSRecord
        public ServiceEvent getServiceEvent(JmDNSImpl jmDNSImpl) {
            ServiceInfo serviceInfo = getServiceInfo(false);
            ((ServiceInfoImpl) serviceInfo).setDns(jmDNSImpl);
            return new ServiceEventImpl(jmDNSImpl, serviceInfo.getType(), serviceInfo.getName(), serviceInfo);
        }

        @Override // javax.jmdns.impl.DNSRecord
        public ServiceInfo getServiceInfo(boolean z2) {
            HashMap map = new HashMap(2);
            map.put(bc.f21424w, this.f25333c);
            map.put("os", this.f25332b);
            return new ServiceInfoImpl(getQualifiedNameMap(), 0, 0, 0, z2, map);
        }

        @Override // javax.jmdns.impl.DNSRecord
        boolean i(DNSRecord dNSRecord) {
            if (!(dNSRecord instanceof HostInformation)) {
                return false;
            }
            HostInformation hostInformation = (HostInformation) dNSRecord;
            String str = this.f25333c;
            if (str != null || hostInformation.f25333c == null) {
                return (this.f25332b != null || hostInformation.f25332b == null) && str.equals(hostInformation.f25333c) && this.f25332b.equals(hostInformation.f25332b);
            }
            return false;
        }

        @Override // javax.jmdns.impl.DNSRecord
        public boolean isSingleValued() {
            return true;
        }

        @Override // javax.jmdns.impl.DNSRecord
        void m(DNSOutgoing.MessageOutputStream messageOutputStream) {
            String str = this.f25333c + " " + this.f25332b;
            messageOutputStream.i(str, 0, str.length());
        }

        @Override // javax.jmdns.impl.DNSRecord, javax.jmdns.impl.DNSEntry
        protected void toString(StringBuilder sb) {
            super.toString(sb);
            sb.append(" cpu: '" + this.f25333c + "' os: '" + this.f25332b + "'");
        }
    }

    public static class IPv4Address extends Address {
        IPv4Address(String str, DNSRecordClass dNSRecordClass, boolean z2, int i2, InetAddress inetAddress) {
            super(str, DNSRecordType.TYPE_A, dNSRecordClass, z2, i2, inetAddress);
        }

        @Override // javax.jmdns.impl.DNSRecord.Address, javax.jmdns.impl.DNSRecord
        public ServiceInfo getServiceInfo(boolean z2) {
            ServiceInfoImpl serviceInfoImpl = (ServiceInfoImpl) super.getServiceInfo(z2);
            serviceInfoImpl.b((Inet4Address) this.f25331b);
            return serviceInfoImpl;
        }

        @Override // javax.jmdns.impl.DNSRecord
        void m(DNSOutgoing.MessageOutputStream messageOutputStream) {
            InetAddress inetAddress = this.f25331b;
            if (inetAddress != null) {
                byte[] address = inetAddress.getAddress();
                if (!(this.f25331b instanceof Inet4Address)) {
                    byte[] bArr = new byte[4];
                    System.arraycopy(address, 12, bArr, 0, 4);
                    address = bArr;
                }
                messageOutputStream.b(address, 0, address.length);
            }
        }

        IPv4Address(String str, DNSRecordClass dNSRecordClass, boolean z2, int i2, byte[] bArr) {
            super(str, DNSRecordType.TYPE_A, dNSRecordClass, z2, i2, bArr);
        }
    }

    public static class IPv6Address extends Address {
        IPv6Address(String str, DNSRecordClass dNSRecordClass, boolean z2, int i2, InetAddress inetAddress) {
            super(str, DNSRecordType.TYPE_AAAA, dNSRecordClass, z2, i2, inetAddress);
        }

        @Override // javax.jmdns.impl.DNSRecord.Address, javax.jmdns.impl.DNSRecord
        public ServiceInfo getServiceInfo(boolean z2) {
            ServiceInfoImpl serviceInfoImpl = (ServiceInfoImpl) super.getServiceInfo(z2);
            serviceInfoImpl.c((Inet6Address) this.f25331b);
            return serviceInfoImpl;
        }

        @Override // javax.jmdns.impl.DNSRecord
        void m(DNSOutgoing.MessageOutputStream messageOutputStream) {
            InetAddress inetAddress = this.f25331b;
            if (inetAddress != null) {
                byte[] address = inetAddress.getAddress();
                if (this.f25331b instanceof Inet4Address) {
                    byte[] bArr = new byte[16];
                    for (int i2 = 0; i2 < 16; i2++) {
                        if (i2 < 11) {
                            bArr[i2] = address[i2 - 12];
                        } else {
                            bArr[i2] = 0;
                        }
                    }
                    address = bArr;
                }
                messageOutputStream.b(address, 0, address.length);
            }
        }

        IPv6Address(String str, DNSRecordClass dNSRecordClass, boolean z2, int i2, byte[] bArr) {
            super(str, DNSRecordType.TYPE_AAAA, dNSRecordClass, z2, i2, bArr);
        }
    }

    public static class Pointer extends DNSRecord {
        private final String _alias;

        public Pointer(String str, DNSRecordClass dNSRecordClass, boolean z2, int i2, String str2) {
            super(str, DNSRecordType.TYPE_PTR, dNSRecordClass, z2, i2);
            this._alias = str2;
        }

        @Override // javax.jmdns.impl.DNSRecord
        boolean e(JmDNSImpl jmDNSImpl, long j2) {
            return false;
        }

        @Override // javax.jmdns.impl.DNSRecord
        boolean f(JmDNSImpl jmDNSImpl) {
            return false;
        }

        @Override // javax.jmdns.impl.DNSRecord
        public ServiceEvent getServiceEvent(JmDNSImpl jmDNSImpl) {
            ServiceInfo serviceInfo = getServiceInfo(false);
            ((ServiceInfoImpl) serviceInfo).setDns(jmDNSImpl);
            String type = serviceInfo.getType();
            return new ServiceEventImpl(jmDNSImpl, type, JmDNSImpl.m(type, n()), serviceInfo);
        }

        @Override // javax.jmdns.impl.DNSRecord
        public ServiceInfo getServiceInfo(boolean z2) {
            if (isServicesDiscoveryMetaQuery()) {
                return new ServiceInfoImpl(ServiceInfoImpl.decodeQualifiedNameMapForType(n()), 0, 0, 0, z2, (byte[]) null);
            }
            if (isReverseLookup()) {
                return new ServiceInfoImpl(getQualifiedNameMap(), 0, 0, 0, z2, (byte[]) null);
            }
            if (isDomainDiscoveryQuery()) {
                return new ServiceInfoImpl(getQualifiedNameMap(), 0, 0, 0, z2, (byte[]) null);
            }
            Map<ServiceInfo.Fields, String> mapDecodeQualifiedNameMapForType = ServiceInfoImpl.decodeQualifiedNameMapForType(n());
            ServiceInfo.Fields fields = ServiceInfo.Fields.Subtype;
            mapDecodeQualifiedNameMapForType.put(fields, getQualifiedNameMap().get(fields));
            return new ServiceInfoImpl(mapDecodeQualifiedNameMapForType, 0, 0, 0, z2, n());
        }

        @Override // javax.jmdns.impl.DNSRecord
        boolean i(DNSRecord dNSRecord) {
            if (!(dNSRecord instanceof Pointer)) {
                return false;
            }
            Pointer pointer = (Pointer) dNSRecord;
            String str = this._alias;
            if (str != null || pointer._alias == null) {
                return str.equals(pointer._alias);
            }
            return false;
        }

        @Override // javax.jmdns.impl.DNSEntry
        public boolean isSameEntry(DNSEntry dNSEntry) {
            return super.isSameEntry(dNSEntry) && (dNSEntry instanceof Pointer) && i((Pointer) dNSEntry);
        }

        @Override // javax.jmdns.impl.DNSRecord
        public boolean isSingleValued() {
            return false;
        }

        @Override // javax.jmdns.impl.DNSRecord
        void m(DNSOutgoing.MessageOutputStream messageOutputStream) {
            messageOutputStream.d(this._alias);
        }

        String n() {
            return this._alias;
        }

        @Override // javax.jmdns.impl.DNSRecord, javax.jmdns.impl.DNSEntry
        protected void toString(StringBuilder sb) {
            super.toString(sb);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(" alias: '");
            String str = this._alias;
            sb2.append(str != null ? str.toString() : TmpConstant.GROUP_ROLE_UNKNOWN);
            sb2.append("'");
            sb.append(sb2.toString());
        }
    }

    public static class Service extends DNSRecord {
        private static Logger logger1 = Logger.getLogger(Service.class.getName());
        private final int _port;
        private final int _priority;
        private final String _server;
        private final int _weight;

        public Service(String str, DNSRecordClass dNSRecordClass, boolean z2, int i2, int i3, int i4, int i5, String str2) {
            super(str, DNSRecordType.TYPE_SRV, dNSRecordClass, z2, i2);
            this._priority = i3;
            this._weight = i4;
            this._port = i5;
            this._server = str2;
        }

        @Override // javax.jmdns.impl.DNSEntry
        protected void a(DataOutputStream dataOutputStream) throws IOException {
            super.a(dataOutputStream);
            dataOutputStream.writeShort(this._priority);
            dataOutputStream.writeShort(this._weight);
            dataOutputStream.writeShort(this._port);
            try {
                dataOutputStream.write(this._server.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException unused) {
            }
        }

        @Override // javax.jmdns.impl.DNSRecord
        boolean e(JmDNSImpl jmDNSImpl, long j2) throws IOException {
            ServiceInfoImpl serviceInfoImpl = (ServiceInfoImpl) jmDNSImpl.getServices().get(getKey());
            if (serviceInfoImpl != null && ((serviceInfoImpl.isAnnouncing() || serviceInfoImpl.isAnnounced()) && (this._port != serviceInfoImpl.getPort() || !this._server.equalsIgnoreCase(jmDNSImpl.getLocalHost().getName())))) {
                logger1.finer("handleQuery() Conflicting probe detected from: " + getRecordSource());
                Service service = new Service(serviceInfoImpl.getQualifiedName(), DNSRecordClass.CLASS_IN, true, 3600, serviceInfoImpl.getPriority(), serviceInfoImpl.getWeight(), serviceInfoImpl.getPort(), jmDNSImpl.getLocalHost().getName());
                try {
                    if (jmDNSImpl.getInterface().equals(getRecordSource())) {
                        logger1.warning("Got conflicting probe from ourselves\nincoming: " + toString() + "\nlocal   : " + service.toString());
                    }
                } catch (IOException e2) {
                    logger1.log(Level.WARNING, "IOException", (Throwable) e2);
                }
                int iCompareTo = compareTo(service);
                if (iCompareTo == 0) {
                    logger1.finer("handleQuery() Ignoring a identical service query");
                    return false;
                }
                if (serviceInfoImpl.isProbing() && iCompareTo > 0) {
                    String lowerCase = serviceInfoImpl.getQualifiedName().toLowerCase();
                    serviceInfoImpl.h(jmDNSImpl.k(serviceInfoImpl.getName()));
                    jmDNSImpl.getServices().remove(lowerCase);
                    jmDNSImpl.getServices().put(serviceInfoImpl.getQualifiedName().toLowerCase(), serviceInfoImpl);
                    logger1.finer("handleQuery() Lost tie break: new unique name chosen:" + serviceInfoImpl.getName());
                    serviceInfoImpl.revertState();
                    return true;
                }
            }
            return false;
        }

        @Override // javax.jmdns.impl.DNSRecord
        boolean f(JmDNSImpl jmDNSImpl) {
            ServiceInfoImpl serviceInfoImpl = (ServiceInfoImpl) jmDNSImpl.getServices().get(getKey());
            if (serviceInfoImpl == null) {
                return false;
            }
            if (this._port == serviceInfoImpl.getPort() && this._server.equalsIgnoreCase(jmDNSImpl.getLocalHost().getName())) {
                return false;
            }
            logger1.finer("handleResponse() Denial detected");
            if (serviceInfoImpl.isProbing()) {
                String lowerCase = serviceInfoImpl.getQualifiedName().toLowerCase();
                serviceInfoImpl.h(jmDNSImpl.k(serviceInfoImpl.getName()));
                jmDNSImpl.getServices().remove(lowerCase);
                jmDNSImpl.getServices().put(serviceInfoImpl.getQualifiedName().toLowerCase(), serviceInfoImpl);
                logger1.finer("handleResponse() New unique name chose:" + serviceInfoImpl.getName());
            }
            serviceInfoImpl.revertState();
            return true;
        }

        public int getPort() {
            return this._port;
        }

        public int getPriority() {
            return this._priority;
        }

        @Override // javax.jmdns.impl.DNSRecord
        public ServiceEvent getServiceEvent(JmDNSImpl jmDNSImpl) {
            ServiceInfo serviceInfo = getServiceInfo(false);
            ((ServiceInfoImpl) serviceInfo).setDns(jmDNSImpl);
            return new ServiceEventImpl(jmDNSImpl, serviceInfo.getType(), serviceInfo.getName(), serviceInfo);
        }

        @Override // javax.jmdns.impl.DNSRecord
        public ServiceInfo getServiceInfo(boolean z2) {
            return new ServiceInfoImpl(getQualifiedNameMap(), this._port, this._weight, this._priority, z2, this._server);
        }

        public int getWeight() {
            return this._weight;
        }

        @Override // javax.jmdns.impl.DNSRecord
        boolean i(DNSRecord dNSRecord) {
            if (!(dNSRecord instanceof Service)) {
                return false;
            }
            Service service = (Service) dNSRecord;
            return this._priority == service._priority && this._weight == service._weight && this._port == service._port && this._server.equals(service._server);
        }

        @Override // javax.jmdns.impl.DNSRecord
        public boolean isSingleValued() {
            return true;
        }

        @Override // javax.jmdns.impl.DNSRecord
        void m(DNSOutgoing.MessageOutputStream messageOutputStream) {
            messageOutputStream.h(this._priority);
            messageOutputStream.h(this._weight);
            messageOutputStream.h(this._port);
            if (DNSIncoming.USE_DOMAIN_NAME_FORMAT_FOR_SRV_TARGET) {
                messageOutputStream.d(this._server);
                return;
            }
            String str = this._server;
            messageOutputStream.i(str, 0, str.length());
            messageOutputStream.a(0);
        }

        String n() {
            return this._server;
        }

        @Override // javax.jmdns.impl.DNSRecord, javax.jmdns.impl.DNSEntry
        protected void toString(StringBuilder sb) {
            super.toString(sb);
            sb.append(" server: '" + this._server + ":" + this._port + "'");
        }
    }

    public static class Text extends DNSRecord {
        private final byte[] _text;

        public Text(String str, DNSRecordClass dNSRecordClass, boolean z2, int i2, byte[] bArr) {
            super(str, DNSRecordType.TYPE_TXT, dNSRecordClass, z2, i2);
            this._text = (bArr == null || bArr.length <= 0) ? DNSRecord.EMPTY_TXT : bArr;
        }

        @Override // javax.jmdns.impl.DNSRecord
        boolean e(JmDNSImpl jmDNSImpl, long j2) {
            return false;
        }

        @Override // javax.jmdns.impl.DNSRecord
        boolean f(JmDNSImpl jmDNSImpl) {
            return false;
        }

        @Override // javax.jmdns.impl.DNSRecord
        public ServiceEvent getServiceEvent(JmDNSImpl jmDNSImpl) {
            ServiceInfo serviceInfo = getServiceInfo(false);
            ((ServiceInfoImpl) serviceInfo).setDns(jmDNSImpl);
            return new ServiceEventImpl(jmDNSImpl, serviceInfo.getType(), serviceInfo.getName(), serviceInfo);
        }

        @Override // javax.jmdns.impl.DNSRecord
        public ServiceInfo getServiceInfo(boolean z2) {
            return new ServiceInfoImpl(getQualifiedNameMap(), 0, 0, 0, z2, this._text);
        }

        @Override // javax.jmdns.impl.DNSRecord
        boolean i(DNSRecord dNSRecord) {
            if (!(dNSRecord instanceof Text)) {
                return false;
            }
            Text text = (Text) dNSRecord;
            byte[] bArr = this._text;
            if ((bArr == null && text._text != null) || text._text.length != bArr.length) {
                return false;
            }
            int length = bArr.length;
            while (true) {
                int i2 = length - 1;
                if (length <= 0) {
                    return true;
                }
                if (text._text[i2] != this._text[i2]) {
                    return false;
                }
                length = i2;
            }
        }

        @Override // javax.jmdns.impl.DNSRecord
        public boolean isSingleValued() {
            return true;
        }

        @Override // javax.jmdns.impl.DNSRecord
        void m(DNSOutgoing.MessageOutputStream messageOutputStream) {
            byte[] bArr = this._text;
            messageOutputStream.b(bArr, 0, bArr.length);
        }

        byte[] n() {
            return this._text;
        }

        @Override // javax.jmdns.impl.DNSRecord, javax.jmdns.impl.DNSEntry
        protected void toString(StringBuilder sb) {
            String str;
            super.toString(sb);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(" text: '");
            byte[] bArr = this._text;
            if (bArr.length > 20) {
                str = new String(this._text, 0, 17) + "...";
            } else {
                str = new String(bArr);
            }
            sb2.append(str);
            sb2.append("'");
            sb.append(sb2.toString());
        }
    }

    DNSRecord(String str, DNSRecordType dNSRecordType, DNSRecordClass dNSRecordClass, boolean z2, int i2) {
        super(str, dNSRecordType, dNSRecordClass, z2);
        this._ttl = i2;
        this._created = System.currentTimeMillis();
    }

    long c(int i2) {
        return this._created + (i2 * this._ttl * 10);
    }

    int d(long j2) {
        return (int) Math.max(0L, (c(100) - j2) / 1000);
    }

    abstract boolean e(JmDNSImpl jmDNSImpl, long j2);

    @Override // javax.jmdns.impl.DNSEntry
    public boolean equals(Object obj) {
        return (obj instanceof DNSRecord) && super.equals(obj) && i((DNSRecord) obj);
    }

    abstract boolean f(JmDNSImpl jmDNSImpl);

    void g(DNSRecord dNSRecord) {
        this._created = dNSRecord._created;
        this._ttl = dNSRecord._ttl;
    }

    public InetAddress getRecordSource() {
        return this._source;
    }

    public abstract ServiceEvent getServiceEvent(JmDNSImpl jmDNSImpl);

    public ServiceInfo getServiceInfo() {
        return getServiceInfo(false);
    }

    public abstract ServiceInfo getServiceInfo(boolean z2);

    public int getTTL() {
        return this._ttl;
    }

    boolean h(DNSRecord dNSRecord) {
        return getRecordType() == dNSRecord.getRecordType();
    }

    abstract boolean i(DNSRecord dNSRecord);

    @Override // javax.jmdns.impl.DNSEntry
    public boolean isExpired(long j2) {
        return c(100) <= j2;
    }

    public abstract boolean isSingleValued();

    @Override // javax.jmdns.impl.DNSEntry
    public boolean isStale(long j2) {
        return c(50) <= j2;
    }

    void j(long j2) {
        this._created = j2;
        this._ttl = 1;
    }

    boolean k(DNSIncoming dNSIncoming) {
        try {
            Iterator<? extends DNSRecord> it = dNSIncoming.getAllAnswers().iterator();
            while (it.hasNext()) {
                if (l(it.next())) {
                    return true;
                }
            }
            return false;
        } catch (ArrayIndexOutOfBoundsException e2) {
            logger.log(Level.WARNING, "suppressedBy() message " + dNSIncoming + " exception ", (Throwable) e2);
            return false;
        }
    }

    boolean l(DNSRecord dNSRecord) {
        return equals(dNSRecord) && dNSRecord._ttl > this._ttl / 2;
    }

    abstract void m(DNSOutgoing.MessageOutputStream messageOutputStream);

    public void setRecordSource(InetAddress inetAddress) {
        this._source = inetAddress;
    }

    public void setTTL(int i2) {
        this._ttl = i2;
    }

    @Override // javax.jmdns.impl.DNSEntry
    protected void toString(StringBuilder sb) {
        super.toString(sb);
        sb.append(" ttl: '" + d(System.currentTimeMillis()) + "/" + this._ttl + "'");
    }

    public static abstract class Address extends DNSRecord {
        private static Logger logger1 = Logger.getLogger(Address.class.getName());

        /* renamed from: b, reason: collision with root package name */
        InetAddress f25331b;

        protected Address(String str, DNSRecordType dNSRecordType, DNSRecordClass dNSRecordClass, boolean z2, int i2, InetAddress inetAddress) {
            super(str, dNSRecordType, dNSRecordClass, z2, i2);
            this.f25331b = inetAddress;
        }

        @Override // javax.jmdns.impl.DNSEntry
        protected void a(DataOutputStream dataOutputStream) throws IOException {
            super.a(dataOutputStream);
            for (byte b2 : n().getAddress()) {
                dataOutputStream.writeByte(b2);
            }
        }

        @Override // javax.jmdns.impl.DNSRecord
        boolean e(JmDNSImpl jmDNSImpl, long j2) throws IOException {
            if (!jmDNSImpl.getLocalHost().conflictWithRecord(this)) {
                return false;
            }
            int iCompareTo = compareTo(jmDNSImpl.getLocalHost().a(getRecordType(), isUnique(), 3600));
            if (iCompareTo == 0) {
                logger1.finer("handleQuery() Ignoring an identical address query");
                return false;
            }
            logger1.finer("handleQuery() Conflicting query detected.");
            if (jmDNSImpl.isProbing() && iCompareTo > 0) {
                jmDNSImpl.getLocalHost().e();
                jmDNSImpl.getCache().clear();
                Iterator<ServiceInfo> it = jmDNSImpl.getServices().values().iterator();
                while (it.hasNext()) {
                    ((ServiceInfoImpl) it.next()).revertState();
                }
            }
            jmDNSImpl.revertState();
            return true;
        }

        @Override // javax.jmdns.impl.DNSRecord
        boolean f(JmDNSImpl jmDNSImpl) {
            if (!jmDNSImpl.getLocalHost().conflictWithRecord(this)) {
                return false;
            }
            logger1.finer("handleResponse() Denial detected");
            if (jmDNSImpl.isProbing()) {
                jmDNSImpl.getLocalHost().e();
                jmDNSImpl.getCache().clear();
                Iterator<ServiceInfo> it = jmDNSImpl.getServices().values().iterator();
                while (it.hasNext()) {
                    ((ServiceInfoImpl) it.next()).revertState();
                }
            }
            jmDNSImpl.revertState();
            return true;
        }

        @Override // javax.jmdns.impl.DNSRecord
        public ServiceEvent getServiceEvent(JmDNSImpl jmDNSImpl) {
            ServiceInfo serviceInfo = getServiceInfo(false);
            ((ServiceInfoImpl) serviceInfo).setDns(jmDNSImpl);
            return new ServiceEventImpl(jmDNSImpl, serviceInfo.getType(), serviceInfo.getName(), serviceInfo);
        }

        @Override // javax.jmdns.impl.DNSRecord
        public ServiceInfo getServiceInfo(boolean z2) {
            return new ServiceInfoImpl(getQualifiedNameMap(), 0, 0, 0, z2, (byte[]) null);
        }

        @Override // javax.jmdns.impl.DNSRecord
        boolean i(DNSRecord dNSRecord) {
            if (!(dNSRecord instanceof Address)) {
                return false;
            }
            Address address = (Address) dNSRecord;
            if (n() != null || address.n() == null) {
                return n().equals(address.n());
            }
            return false;
        }

        @Override // javax.jmdns.impl.DNSRecord
        public boolean isSingleValued() {
            return false;
        }

        InetAddress n() {
            return this.f25331b;
        }

        boolean o(DNSRecord dNSRecord) {
            return getName().equalsIgnoreCase(dNSRecord.getName());
        }

        @Override // javax.jmdns.impl.DNSRecord, javax.jmdns.impl.DNSEntry
        protected void toString(StringBuilder sb) {
            super.toString(sb);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(" address: '");
            sb2.append(n() != null ? n().getHostAddress() : TmpConstant.GROUP_ROLE_UNKNOWN);
            sb2.append("'");
            sb.append(sb2.toString());
        }

        protected Address(String str, DNSRecordType dNSRecordType, DNSRecordClass dNSRecordClass, boolean z2, int i2, byte[] bArr) {
            super(str, dNSRecordType, dNSRecordClass, z2, i2);
            try {
                this.f25331b = InetAddress.getByAddress(bArr);
            } catch (UnknownHostException e2) {
                logger1.log(Level.WARNING, "Address() exception ", (Throwable) e2);
            }
        }
    }
}
