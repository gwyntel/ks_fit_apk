package javax.jmdns.impl;

import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.NetworkTopologyDiscovery;
import javax.jmdns.impl.DNSRecord;
import javax.jmdns.impl.DNSStatefulObject;
import javax.jmdns.impl.constants.DNSRecordClass;
import javax.jmdns.impl.constants.DNSRecordType;
import javax.jmdns.impl.constants.DNSState;
import javax.jmdns.impl.tasks.DNSTask;

/* loaded from: classes4.dex */
public class HostInfo implements DNSStatefulObject {
    private static Logger logger = Logger.getLogger(HostInfo.class.getName());
    private final HostInfoState _state;

    /* renamed from: a, reason: collision with root package name */
    protected String f25334a;

    /* renamed from: b, reason: collision with root package name */
    protected InetAddress f25335b;

    /* renamed from: c, reason: collision with root package name */
    protected NetworkInterface f25336c;
    private int hostNameCount;

    /* renamed from: javax.jmdns.impl.HostInfo$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f25337a;

        static {
            int[] iArr = new int[DNSRecordType.values().length];
            f25337a = iArr;
            try {
                iArr[DNSRecordType.TYPE_A.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f25337a[DNSRecordType.TYPE_A6.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f25337a[DNSRecordType.TYPE_AAAA.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private static final class HostInfoState extends DNSStatefulObject.DefaultImplementation {
        private static final long serialVersionUID = -8191476803620402088L;

        public HostInfoState(JmDNSImpl jmDNSImpl) {
            setDns(jmDNSImpl);
        }
    }

    private HostInfo(InetAddress inetAddress, String str, JmDNSImpl jmDNSImpl) {
        this._state = new HostInfoState(jmDNSImpl);
        this.f25335b = inetAddress;
        this.f25334a = str;
        if (inetAddress != null) {
            try {
                this.f25336c = NetworkInterface.getByInetAddress(inetAddress);
            } catch (Exception e2) {
                logger.log(Level.SEVERE, "LocalHostInfo() exception ", (Throwable) e2);
            }
        }
    }

    private DNSRecord.Address getDNS4AddressRecord(boolean z2, int i2) {
        if ((getInetAddress() instanceof Inet4Address) || ((getInetAddress() instanceof Inet6Address) && ((Inet6Address) getInetAddress()).isIPv4CompatibleAddress())) {
            return new DNSRecord.IPv4Address(getName(), DNSRecordClass.CLASS_IN, z2, i2, getInetAddress());
        }
        return null;
    }

    private DNSRecord.Pointer getDNS4ReverseAddressRecord(boolean z2, int i2) {
        if (getInetAddress() instanceof Inet4Address) {
            return new DNSRecord.Pointer(getInetAddress().getHostAddress() + ".in-addr.arpa.", DNSRecordClass.CLASS_IN, z2, i2, getName());
        }
        if (!(getInetAddress() instanceof Inet6Address) || !((Inet6Address) getInetAddress()).isIPv4CompatibleAddress()) {
            return null;
        }
        byte[] address = getInetAddress().getAddress();
        return new DNSRecord.Pointer(((address[12] & 255) + "." + (address[13] & 255) + "." + (address[14] & 255) + "." + (address[15] & 255)) + ".in-addr.arpa.", DNSRecordClass.CLASS_IN, z2, i2, getName());
    }

    private DNSRecord.Address getDNS6AddressRecord(boolean z2, int i2) {
        if (getInetAddress() instanceof Inet6Address) {
            return new DNSRecord.IPv6Address(getName(), DNSRecordClass.CLASS_IN, z2, i2, getInetAddress());
        }
        return null;
    }

    private DNSRecord.Pointer getDNS6ReverseAddressRecord(boolean z2, int i2) {
        if (!(getInetAddress() instanceof Inet6Address)) {
            return null;
        }
        return new DNSRecord.Pointer(getInetAddress().getHostAddress() + ".ip6.arpa.", DNSRecordClass.CLASS_IN, z2, i2, getName());
    }

    private static InetAddress loopbackAddress() {
        try {
            return InetAddress.getByName(null);
        } catch (UnknownHostException unused) {
            return null;
        }
    }

    public static HostInfo newHostInfo(InetAddress inetAddress, JmDNSImpl jmDNSImpl, String str) {
        InetAddress inetAddressLoopbackAddress;
        String hostName;
        try {
            if (inetAddress == null) {
                String property = System.getProperty("net.mdns.interface");
                if (property != null) {
                    inetAddressLoopbackAddress = InetAddress.getByName(property);
                } else {
                    inetAddressLoopbackAddress = InetAddress.getLocalHost();
                    if (inetAddressLoopbackAddress.isLoopbackAddress()) {
                        InetAddress[] inetAddresses = NetworkTopologyDiscovery.Factory.getInstance().getInetAddresses();
                        if (inetAddresses.length > 0) {
                            inetAddressLoopbackAddress = inetAddresses[0];
                        }
                    }
                }
                hostName = inetAddressLoopbackAddress.getHostName();
                if (inetAddressLoopbackAddress.isLoopbackAddress()) {
                    logger.warning("Could not find any address beside the loopback.");
                }
            } else {
                hostName = inetAddress.getHostName();
                inetAddressLoopbackAddress = inetAddress;
            }
        } catch (IOException e2) {
            logger.log(Level.WARNING, "Could not intialize the host network interface on " + inetAddress + "because of an error: " + e2.getMessage(), (Throwable) e2);
            inetAddressLoopbackAddress = loopbackAddress();
            if (str == null || str.length() <= 0) {
                str = "computer";
            }
        }
        if (hostName.contains("in-addr.arpa") || hostName.equals(inetAddressLoopbackAddress.getHostAddress())) {
            if (str == null || str.length() <= 0) {
                str = inetAddressLoopbackAddress.getHostAddress();
            }
            hostName = str;
        }
        return new HostInfo(inetAddressLoopbackAddress, hostName.replace('.', '-') + ".local.", jmDNSImpl);
    }

    DNSRecord.Address a(DNSRecordType dNSRecordType, boolean z2, int i2) {
        int i3 = AnonymousClass1.f25337a[dNSRecordType.ordinal()];
        if (i3 == 1) {
            return getDNS4AddressRecord(z2, i2);
        }
        if (i3 == 2 || i3 == 3) {
            return getDNS6AddressRecord(z2, i2);
        }
        return null;
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public boolean advanceState(DNSTask dNSTask) {
        return this._state.advanceState(dNSTask);
    }

    public Collection<DNSRecord> answers(boolean z2, int i2) {
        ArrayList arrayList = new ArrayList();
        DNSRecord.Address dNS4AddressRecord = getDNS4AddressRecord(z2, i2);
        if (dNS4AddressRecord != null) {
            arrayList.add(dNS4AddressRecord);
        }
        DNSRecord.Address dNS6AddressRecord = getDNS6AddressRecord(z2, i2);
        if (dNS6AddressRecord != null) {
            arrayList.add(dNS6AddressRecord);
        }
        return arrayList;
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public void associateWithTask(DNSTask dNSTask, DNSState dNSState) {
        this._state.associateWithTask(dNSTask, dNSState);
    }

    DNSRecord.Pointer b(DNSRecordType dNSRecordType, boolean z2, int i2) {
        int i3 = AnonymousClass1.f25337a[dNSRecordType.ordinal()];
        if (i3 == 1) {
            return getDNS4ReverseAddressRecord(z2, i2);
        }
        if (i3 == 2 || i3 == 3) {
            return getDNS6ReverseAddressRecord(z2, i2);
        }
        return null;
    }

    Inet4Address c() {
        if (getInetAddress() instanceof Inet4Address) {
            return (Inet4Address) this.f25335b;
        }
        return null;
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public boolean cancelState() {
        return this._state.cancelState();
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public boolean closeState() {
        return this._state.closeState();
    }

    public boolean conflictWithRecord(DNSRecord.Address address) {
        DNSRecord.Address addressA = a(address.getRecordType(), address.isUnique(), 3600);
        return addressA != null && addressA.h(address) && addressA.o(address) && !addressA.i(address);
    }

    Inet6Address d() {
        if (getInetAddress() instanceof Inet6Address) {
            return (Inet6Address) this.f25335b;
        }
        return null;
    }

    synchronized String e() {
        String string;
        this.hostNameCount++;
        int iIndexOf = this.f25334a.indexOf(".local.");
        int iLastIndexOf = this.f25334a.lastIndexOf(45);
        StringBuilder sb = new StringBuilder();
        String str = this.f25334a;
        if (iLastIndexOf != -1) {
            iIndexOf = iLastIndexOf;
        }
        sb.append(str.substring(0, iIndexOf));
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
        sb.append(this.hostNameCount);
        sb.append(".local.");
        string = sb.toString();
        this.f25334a = string;
        return string;
    }

    boolean f(DatagramPacket datagramPacket) {
        InetAddress address;
        boolean z2 = false;
        if (getInetAddress() == null || (address = datagramPacket.getAddress()) == null) {
            return false;
        }
        if (address.isLinkLocalAddress() && !getInetAddress().isLinkLocalAddress()) {
            z2 = true;
        }
        if (!address.isLoopbackAddress() || getInetAddress().isLoopbackAddress()) {
            return z2;
        }
        return true;
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public JmDNSImpl getDns() {
        return this._state.getDns();
    }

    public InetAddress getInetAddress() {
        return this.f25335b;
    }

    public NetworkInterface getInterface() {
        return this.f25336c;
    }

    public String getName() {
        return this.f25334a;
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public boolean isAnnounced() {
        return this._state.isAnnounced();
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public boolean isAnnouncing() {
        return this._state.isAnnouncing();
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public boolean isAssociatedWithTask(DNSTask dNSTask, DNSState dNSState) {
        return this._state.isAssociatedWithTask(dNSTask, dNSState);
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public boolean isCanceled() {
        return this._state.isCanceled();
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public boolean isCanceling() {
        return this._state.isCanceling();
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public boolean isClosed() {
        return this._state.isClosed();
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public boolean isClosing() {
        return this._state.isClosing();
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public boolean isProbing() {
        return this._state.isProbing();
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public boolean recoverState() {
        return this._state.recoverState();
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public void removeAssociationWithTask(DNSTask dNSTask) {
        this._state.removeAssociationWithTask(dNSTask);
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public boolean revertState() {
        return this._state.revertState();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(1024);
        sb.append("local host info[");
        sb.append(getName() != null ? getName() : "no name");
        sb.append(", ");
        sb.append(getInterface() != null ? getInterface().getDisplayName() : "???");
        sb.append(":");
        sb.append(getInetAddress() != null ? getInetAddress().getHostAddress() : "no address");
        sb.append(", ");
        sb.append(this._state);
        sb.append("]");
        return sb.toString();
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public boolean waitForAnnounced(long j2) {
        return this._state.waitForAnnounced(j2);
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public boolean waitForCanceled(long j2) {
        if (this.f25335b == null) {
            return true;
        }
        return this._state.waitForCanceled(j2);
    }
}
