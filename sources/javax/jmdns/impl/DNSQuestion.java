package javax.jmdns.impl;

import java.net.InetAddress;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.ServiceInfo;
import javax.jmdns.impl.DNSRecord;
import javax.jmdns.impl.constants.DNSRecordClass;
import javax.jmdns.impl.constants.DNSRecordType;

/* loaded from: classes4.dex */
public class DNSQuestion extends DNSEntry {
    private static Logger logger = Logger.getLogger(DNSQuestion.class.getName());

    /* renamed from: javax.jmdns.impl.DNSQuestion$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f25330a;

        static {
            int[] iArr = new int[DNSRecordType.values().length];
            f25330a = iArr;
            try {
                iArr[DNSRecordType.TYPE_A.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f25330a[DNSRecordType.TYPE_A6.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f25330a[DNSRecordType.TYPE_AAAA.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f25330a[DNSRecordType.TYPE_ANY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f25330a[DNSRecordType.TYPE_HINFO.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f25330a[DNSRecordType.TYPE_PTR.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f25330a[DNSRecordType.TYPE_SRV.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f25330a[DNSRecordType.TYPE_TXT.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    private static class AllRecords extends DNSQuestion {
        AllRecords(String str, DNSRecordType dNSRecordType, DNSRecordClass dNSRecordClass, boolean z2) {
            super(str, dNSRecordType, dNSRecordClass, z2);
        }

        @Override // javax.jmdns.impl.DNSQuestion
        public void addAnswers(JmDNSImpl jmDNSImpl, Set<DNSRecord> set) {
            String lowerCase = getName().toLowerCase();
            if (jmDNSImpl.getLocalHost().getName().equalsIgnoreCase(lowerCase)) {
                set.addAll(jmDNSImpl.getLocalHost().answers(isUnique(), 3600));
            } else if (jmDNSImpl.getServiceTypes().containsKey(lowerCase)) {
                new Pointer(getName(), DNSRecordType.TYPE_PTR, getRecordClass(), isUnique()).addAnswers(jmDNSImpl, set);
            } else {
                c(jmDNSImpl, set, (ServiceInfoImpl) jmDNSImpl.getServices().get(lowerCase));
            }
        }

        @Override // javax.jmdns.impl.DNSQuestion
        public boolean iAmTheOnlyOne(JmDNSImpl jmDNSImpl) {
            String lowerCase = getName().toLowerCase();
            return jmDNSImpl.getLocalHost().getName().equals(lowerCase) || jmDNSImpl.getServices().keySet().contains(lowerCase);
        }

        @Override // javax.jmdns.impl.DNSEntry
        public boolean isSameType(DNSEntry dNSEntry) {
            return dNSEntry != null;
        }
    }

    private static class DNS4Address extends DNSQuestion {
        DNS4Address(String str, DNSRecordType dNSRecordType, DNSRecordClass dNSRecordClass, boolean z2) {
            super(str, dNSRecordType, dNSRecordClass, z2);
        }

        @Override // javax.jmdns.impl.DNSQuestion
        public void addAnswers(JmDNSImpl jmDNSImpl, Set<DNSRecord> set) {
            DNSRecord.Address addressA = jmDNSImpl.getLocalHost().a(getRecordType(), true, 3600);
            if (addressA != null) {
                set.add(addressA);
            }
        }

        @Override // javax.jmdns.impl.DNSQuestion
        public boolean iAmTheOnlyOne(JmDNSImpl jmDNSImpl) {
            String lowerCase = getName().toLowerCase();
            return jmDNSImpl.getLocalHost().getName().equals(lowerCase) || jmDNSImpl.getServices().keySet().contains(lowerCase);
        }
    }

    private static class DNS6Address extends DNSQuestion {
        DNS6Address(String str, DNSRecordType dNSRecordType, DNSRecordClass dNSRecordClass, boolean z2) {
            super(str, dNSRecordType, dNSRecordClass, z2);
        }

        @Override // javax.jmdns.impl.DNSQuestion
        public void addAnswers(JmDNSImpl jmDNSImpl, Set<DNSRecord> set) {
            DNSRecord.Address addressA = jmDNSImpl.getLocalHost().a(getRecordType(), true, 3600);
            if (addressA != null) {
                set.add(addressA);
            }
        }

        @Override // javax.jmdns.impl.DNSQuestion
        public boolean iAmTheOnlyOne(JmDNSImpl jmDNSImpl) {
            String lowerCase = getName().toLowerCase();
            return jmDNSImpl.getLocalHost().getName().equals(lowerCase) || jmDNSImpl.getServices().keySet().contains(lowerCase);
        }
    }

    private static class HostInformation extends DNSQuestion {
        HostInformation(String str, DNSRecordType dNSRecordType, DNSRecordClass dNSRecordClass, boolean z2) {
            super(str, dNSRecordType, dNSRecordClass, z2);
        }
    }

    private static class Pointer extends DNSQuestion {
        Pointer(String str, DNSRecordType dNSRecordType, DNSRecordClass dNSRecordClass, boolean z2) {
            super(str, dNSRecordType, dNSRecordClass, z2);
        }

        @Override // javax.jmdns.impl.DNSQuestion
        public void addAnswers(JmDNSImpl jmDNSImpl, Set<DNSRecord> set) {
            Iterator<ServiceInfo> it = jmDNSImpl.getServices().values().iterator();
            while (it.hasNext()) {
                c(jmDNSImpl, set, (ServiceInfoImpl) it.next());
            }
            if (isServicesDiscoveryMetaQuery()) {
                Iterator<String> it2 = jmDNSImpl.getServiceTypes().keySet().iterator();
                while (it2.hasNext()) {
                    set.add(new DNSRecord.Pointer("_services._dns-sd._udp.local.", DNSRecordClass.CLASS_IN, false, 3600, jmDNSImpl.getServiceTypes().get(it2.next()).getType()));
                }
                return;
            }
            if (!isReverseLookup()) {
                isDomainDiscoveryQuery();
                return;
            }
            String str = getQualifiedNameMap().get(ServiceInfo.Fields.Instance);
            if (str == null || str.length() <= 0) {
                return;
            }
            InetAddress inetAddress = jmDNSImpl.getLocalHost().getInetAddress();
            if (str.equalsIgnoreCase(inetAddress != null ? inetAddress.getHostAddress() : "")) {
                if (isV4ReverseLookup()) {
                    set.add(jmDNSImpl.getLocalHost().b(DNSRecordType.TYPE_A, false, 3600));
                }
                if (isV6ReverseLookup()) {
                    set.add(jmDNSImpl.getLocalHost().b(DNSRecordType.TYPE_AAAA, false, 3600));
                }
            }
        }
    }

    private static class Service extends DNSQuestion {
        Service(String str, DNSRecordType dNSRecordType, DNSRecordClass dNSRecordClass, boolean z2) {
            super(str, dNSRecordType, dNSRecordClass, z2);
        }

        @Override // javax.jmdns.impl.DNSQuestion
        public void addAnswers(JmDNSImpl jmDNSImpl, Set<DNSRecord> set) {
            String lowerCase = getName().toLowerCase();
            if (jmDNSImpl.getLocalHost().getName().equalsIgnoreCase(lowerCase)) {
                set.addAll(jmDNSImpl.getLocalHost().answers(isUnique(), 3600));
            } else if (jmDNSImpl.getServiceTypes().containsKey(lowerCase)) {
                new Pointer(getName(), DNSRecordType.TYPE_PTR, getRecordClass(), isUnique()).addAnswers(jmDNSImpl, set);
            } else {
                c(jmDNSImpl, set, (ServiceInfoImpl) jmDNSImpl.getServices().get(lowerCase));
            }
        }

        @Override // javax.jmdns.impl.DNSQuestion
        public boolean iAmTheOnlyOne(JmDNSImpl jmDNSImpl) {
            String lowerCase = getName().toLowerCase();
            return jmDNSImpl.getLocalHost().getName().equals(lowerCase) || jmDNSImpl.getServices().keySet().contains(lowerCase);
        }
    }

    private static class Text extends DNSQuestion {
        Text(String str, DNSRecordType dNSRecordType, DNSRecordClass dNSRecordClass, boolean z2) {
            super(str, dNSRecordType, dNSRecordClass, z2);
        }

        @Override // javax.jmdns.impl.DNSQuestion
        public void addAnswers(JmDNSImpl jmDNSImpl, Set<DNSRecord> set) {
            c(jmDNSImpl, set, (ServiceInfoImpl) jmDNSImpl.getServices().get(getName().toLowerCase()));
        }

        @Override // javax.jmdns.impl.DNSQuestion
        public boolean iAmTheOnlyOne(JmDNSImpl jmDNSImpl) {
            String lowerCase = getName().toLowerCase();
            return jmDNSImpl.getLocalHost().getName().equals(lowerCase) || jmDNSImpl.getServices().keySet().contains(lowerCase);
        }
    }

    DNSQuestion(String str, DNSRecordType dNSRecordType, DNSRecordClass dNSRecordClass, boolean z2) {
        super(str, dNSRecordType, dNSRecordClass, z2);
    }

    public static DNSQuestion newQuestion(String str, DNSRecordType dNSRecordType, DNSRecordClass dNSRecordClass, boolean z2) {
        switch (AnonymousClass1.f25330a[dNSRecordType.ordinal()]) {
            case 1:
                return new DNS4Address(str, dNSRecordType, dNSRecordClass, z2);
            case 2:
                return new DNS6Address(str, dNSRecordType, dNSRecordClass, z2);
            case 3:
                return new DNS6Address(str, dNSRecordType, dNSRecordClass, z2);
            case 4:
                return new AllRecords(str, dNSRecordType, dNSRecordClass, z2);
            case 5:
                return new HostInformation(str, dNSRecordType, dNSRecordClass, z2);
            case 6:
                return new Pointer(str, dNSRecordType, dNSRecordClass, z2);
            case 7:
                return new Service(str, dNSRecordType, dNSRecordClass, z2);
            case 8:
                return new Text(str, dNSRecordType, dNSRecordClass, z2);
            default:
                return new DNSQuestion(str, dNSRecordType, dNSRecordClass, z2);
        }
    }

    public void addAnswers(JmDNSImpl jmDNSImpl, Set<DNSRecord> set) {
    }

    protected void c(JmDNSImpl jmDNSImpl, Set set, ServiceInfoImpl serviceInfoImpl) {
        if (serviceInfoImpl == null || !serviceInfoImpl.isAnnounced()) {
            return;
        }
        if (getName().equalsIgnoreCase(serviceInfoImpl.getQualifiedName()) || getName().equalsIgnoreCase(serviceInfoImpl.getType())) {
            set.addAll(jmDNSImpl.getLocalHost().answers(true, 3600));
            set.addAll(serviceInfoImpl.answers(true, 3600, jmDNSImpl.getLocalHost()));
        }
        if (logger.isLoggable(Level.FINER)) {
            logger.finer(jmDNSImpl.getName() + " DNSQuestion(" + getName() + ").addAnswersForServiceInfo(): info: " + serviceInfoImpl + "\n" + set);
        }
    }

    boolean d(DNSEntry dNSEntry) {
        return isSameRecordClass(dNSEntry) && isSameType(dNSEntry) && getName().equals(dNSEntry.getName());
    }

    public boolean iAmTheOnlyOne(JmDNSImpl jmDNSImpl) {
        return false;
    }

    @Override // javax.jmdns.impl.DNSEntry
    public boolean isExpired(long j2) {
        return false;
    }

    @Override // javax.jmdns.impl.DNSEntry
    public boolean isStale(long j2) {
        return false;
    }

    @Override // javax.jmdns.impl.DNSEntry
    public void toString(StringBuilder sb) {
    }
}
