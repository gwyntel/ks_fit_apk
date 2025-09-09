package javax.jmdns.impl;

import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.huawei.hms.framework.common.ContainerUtils;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import javax.jmdns.ServiceTypeListener;
import javax.jmdns.impl.DNSRecord;
import javax.jmdns.impl.DNSTaskStarter;
import javax.jmdns.impl.ListenerStatus;
import javax.jmdns.impl.constants.DNSConstants;
import javax.jmdns.impl.constants.DNSRecordClass;
import javax.jmdns.impl.constants.DNSRecordType;
import javax.jmdns.impl.constants.DNSState;
import javax.jmdns.impl.tasks.DNSTask;

/* loaded from: classes4.dex */
public class JmDNSImpl extends JmDNS implements DNSStatefulObject, DNSTaskStarter {
    private final DNSCache _cache;
    private volatile JmDNS.Delegate _delegate;
    private volatile InetAddress _group;
    private Thread _incomingListener;
    private long _lastThrottleIncrement;
    private final List<DNSListener> _listeners;
    private HostInfo _localHost;
    private final String _name;
    private DNSIncoming _plannedAnswer;
    private final ConcurrentMap<String, ServiceCollector> _serviceCollectors;
    private final ConcurrentMap<String, List<ListenerStatus.ServiceListenerStatus>> _serviceListeners;
    private final ConcurrentMap<String, ServiceTypeEntry> _serviceTypes;
    private final ConcurrentMap<String, ServiceInfo> _services;
    private volatile MulticastSocket _socket;
    private int _throttle;
    private final Set<ListenerStatus.ServiceTypeListenerStatus> _typeListeners;

    /* renamed from: a, reason: collision with root package name */
    protected Thread f25338a;
    private static Logger logger = Logger.getLogger(JmDNSImpl.class.getName());
    private static final Random _random = new Random();
    private final ExecutorService _executor = Executors.newSingleThreadExecutor();
    private final ReentrantLock _ioLock = new ReentrantLock();
    private final Object _recoverLock = new Object();

    /* renamed from: javax.jmdns.impl.JmDNSImpl$7, reason: invalid class name */
    static /* synthetic */ class AnonymousClass7 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f25355a;

        static {
            int[] iArr = new int[Operation.values().length];
            f25355a = iArr;
            try {
                iArr[Operation.Add.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f25355a[Operation.Remove.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public enum Operation {
        Remove,
        Update,
        Add,
        RegisterServiceType,
        Noop
    }

    private static class ServiceCollector implements ServiceListener {
        private final String _type;
        private final ConcurrentMap<String, ServiceInfo> _infos = new ConcurrentHashMap();
        private final ConcurrentMap<String, ServiceEvent> _events = new ConcurrentHashMap();
        private volatile boolean _needToWaitForInfos = true;

        public ServiceCollector(String str) {
            this._type = str;
        }

        public ServiceInfo[] list(long j2) throws InterruptedException {
            if (this._infos.isEmpty() || !this._events.isEmpty() || this._needToWaitForInfos) {
                long j3 = j2 / 200;
                if (j3 < 1) {
                    j3 = 1;
                }
                for (int i2 = 0; i2 < j3; i2++) {
                    try {
                        Thread.sleep(200L);
                    } catch (InterruptedException unused) {
                    }
                    if (this._events.isEmpty() && !this._infos.isEmpty() && !this._needToWaitForInfos) {
                        break;
                    }
                }
            }
            this._needToWaitForInfos = false;
            return (ServiceInfo[]) this._infos.values().toArray(new ServiceInfo[this._infos.size()]);
        }

        @Override // javax.jmdns.ServiceListener
        public void serviceAdded(ServiceEvent serviceEvent) {
            synchronized (this) {
                try {
                    ServiceInfo info2 = serviceEvent.getInfo();
                    if (info2 == null || !info2.hasData()) {
                        ServiceInfoImpl serviceInfoImplL = ((JmDNSImpl) serviceEvent.getDNS()).l(serviceEvent.getType(), serviceEvent.getName(), info2 != null ? info2.getSubtype() : "", true);
                        if (serviceInfoImplL != null) {
                            this._infos.put(serviceEvent.getName(), serviceInfoImplL);
                        } else {
                            this._events.put(serviceEvent.getName(), serviceEvent);
                        }
                    } else {
                        this._infos.put(serviceEvent.getName(), info2);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // javax.jmdns.ServiceListener
        public void serviceRemoved(ServiceEvent serviceEvent) {
            synchronized (this) {
                this._infos.remove(serviceEvent.getName());
                this._events.remove(serviceEvent.getName());
            }
        }

        @Override // javax.jmdns.ServiceListener
        public void serviceResolved(ServiceEvent serviceEvent) {
            synchronized (this) {
                this._infos.put(serviceEvent.getName(), serviceEvent.getInfo());
                this._events.remove(serviceEvent.getName());
            }
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("\n\tType: ");
            stringBuffer.append(this._type);
            if (this._infos.isEmpty()) {
                stringBuffer.append("\n\tNo services collected.");
            } else {
                stringBuffer.append("\n\tServices");
                for (String str : this._infos.keySet()) {
                    stringBuffer.append("\n\t\tService: ");
                    stringBuffer.append(str);
                    stringBuffer.append(": ");
                    stringBuffer.append(this._infos.get(str));
                }
            }
            if (this._events.isEmpty()) {
                stringBuffer.append("\n\tNo event queued.");
            } else {
                stringBuffer.append("\n\tEvents");
                for (String str2 : this._events.keySet()) {
                    stringBuffer.append("\n\t\tEvent: ");
                    stringBuffer.append(str2);
                    stringBuffer.append(": ");
                    stringBuffer.append(this._events.get(str2));
                }
            }
            return stringBuffer.toString();
        }
    }

    public static class ServiceTypeEntry extends AbstractMap<String, String> implements Cloneable {
        private final Set<Map.Entry<String, String>> _entrySet = new HashSet();
        private final String _type;

        private static class SubTypeEntry implements Map.Entry<String, String>, Serializable, Cloneable {
            private static final long serialVersionUID = 9188503522395855322L;
            private final String _key;
            private final String _value;

            public SubTypeEntry(String str) {
                str = str == null ? "" : str;
                this._value = str;
                this._key = str.toLowerCase();
            }

            public SubTypeEntry clone() {
                return this;
            }

            @Override // java.util.Map.Entry
            public boolean equals(Object obj) {
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                return getKey().equals(entry.getKey()) && getValue().equals(entry.getValue());
            }

            @Override // java.util.Map.Entry
            public int hashCode() {
                String str = this._key;
                int iHashCode = str == null ? 0 : str.hashCode();
                String str2 = this._value;
                return iHashCode ^ (str2 != null ? str2.hashCode() : 0);
            }

            public String toString() {
                return this._key + ContainerUtils.KEY_VALUE_DELIMITER + this._value;
            }

            @Override // java.util.Map.Entry
            public String getKey() {
                return this._key;
            }

            @Override // java.util.Map.Entry
            public String getValue() {
                return this._value;
            }

            @Override // java.util.Map.Entry
            public String setValue(String str) {
                throw new UnsupportedOperationException();
            }
        }

        public ServiceTypeEntry(String str) {
            this._type = str;
        }

        public boolean add(String str) {
            if (str == null || contains(str)) {
                return false;
            }
            this._entrySet.add(new SubTypeEntry(str));
            return true;
        }

        public boolean contains(String str) {
            return str != null && containsKey(str.toLowerCase());
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<Map.Entry<String, String>> entrySet() {
            return this._entrySet;
        }

        public String getType() {
            return this._type;
        }

        public Iterator<String> iterator() {
            return keySet().iterator();
        }

        @Override // java.util.AbstractMap
        public String toString() {
            StringBuilder sb = new StringBuilder(200);
            if (isEmpty()) {
                sb.append("empty");
            } else {
                Iterator<String> it = values().iterator();
                while (it.hasNext()) {
                    sb.append(it.next());
                    sb.append(", ");
                }
                sb.setLength(sb.length() - 2);
            }
            return sb.toString();
        }

        @Override // java.util.AbstractMap
        public ServiceTypeEntry clone() {
            ServiceTypeEntry serviceTypeEntry = new ServiceTypeEntry(getType());
            Iterator<Map.Entry<String, String>> it = entrySet().iterator();
            while (it.hasNext()) {
                serviceTypeEntry.add(it.next().getValue());
            }
            return serviceTypeEntry;
        }
    }

    protected class Shutdown implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ JmDNSImpl f25356a;

        @Override // java.lang.Runnable
        public void run() {
            try {
                JmDNSImpl jmDNSImpl = this.f25356a;
                jmDNSImpl.f25338a = null;
                jmDNSImpl.close();
            } catch (Throwable th) {
                System.err.println("Error while shuting down. " + th);
            }
        }
    }

    public JmDNSImpl(InetAddress inetAddress, String str) throws IOException {
        if (logger.isLoggable(Level.FINER)) {
            logger.finer("JmDNS instance created");
        }
        this._cache = new DNSCache(100);
        this._listeners = Collections.synchronizedList(new ArrayList());
        this._serviceListeners = new ConcurrentHashMap();
        this._typeListeners = Collections.synchronizedSet(new HashSet());
        this._serviceCollectors = new ConcurrentHashMap();
        this._services = new ConcurrentHashMap(20);
        this._serviceTypes = new ConcurrentHashMap(20);
        HostInfo hostInfoNewHostInfo = HostInfo.newHostInfo(inetAddress, this, str);
        this._localHost = hostInfoNewHostInfo;
        this._name = str == null ? hostInfoNewHostInfo.getName() : str;
        openMulticastSocket(getLocalHost());
        start(getServices().values());
        startReaper();
    }

    private void closeMulticastSocket() throws IOException {
        if (logger.isLoggable(Level.FINER)) {
            logger.finer("closeMulticastSocket()");
        }
        if (this._socket != null) {
            try {
                try {
                    this._socket.leaveGroup(this._group);
                } catch (SocketException unused) {
                }
                this._socket.close();
                while (true) {
                    Thread thread = this._incomingListener;
                    if (thread == null || !thread.isAlive()) {
                        break;
                    }
                    synchronized (this) {
                        try {
                            try {
                                Thread thread2 = this._incomingListener;
                                if (thread2 != null && thread2.isAlive()) {
                                    if (logger.isLoggable(Level.FINER)) {
                                        logger.finer("closeMulticastSocket(): waiting for jmDNS monitor");
                                    }
                                    wait(1000L);
                                }
                            } finally {
                            }
                        } catch (InterruptedException unused2) {
                        }
                    }
                }
                this._incomingListener = null;
            } catch (Exception e2) {
                logger.log(Level.WARNING, "closeMulticastSocket() Close socket exception ", (Throwable) e2);
            }
            this._socket = null;
        }
    }

    private void disposeServiceCollectors() {
        if (logger.isLoggable(Level.FINER)) {
            logger.finer("disposeServiceCollectors()");
        }
        for (String str : this._serviceCollectors.keySet()) {
            ServiceCollector serviceCollector = this._serviceCollectors.get(str);
            if (serviceCollector != null) {
                removeServiceListener(str, serviceCollector);
                this._serviceCollectors.remove(str, serviceCollector);
            }
        }
    }

    public static Random getRandom() {
        return _random;
    }

    static String m(String str, String str2) {
        String lowerCase = str.toLowerCase();
        String lowerCase2 = str2.toLowerCase();
        return (!lowerCase2.endsWith(lowerCase) || lowerCase2.equals(lowerCase)) ? str2 : str2.substring(0, (str2.length() - str.length()) - 1);
    }

    public static void main(String[] strArr) throws IOException {
        String property;
        try {
            Properties properties = new Properties();
            properties.load(JmDNSImpl.class.getResourceAsStream("/META-INF/maven/javax.jmdns/jmdns/pom.properties"));
            property = properties.getProperty("version");
        } catch (Exception unused) {
            property = "RUNNING.IN.IDE.FULL";
        }
        PrintStream printStream = System.out;
        printStream.println("JmDNS version \"" + property + "\"");
        printStream.println(" ");
        printStream.println("Running on java version \"" + System.getProperty("java.version") + "\" (build " + System.getProperty("java.runtime.version") + ") from " + System.getProperty("java.vendor"));
        StringBuilder sb = new StringBuilder();
        sb.append("Operating environment \"");
        sb.append(System.getProperty("os.name"));
        sb.append("\"");
        sb.append(" version ");
        sb.append(System.getProperty("os.version"));
        sb.append(" on ");
        sb.append(System.getProperty("os.arch"));
        printStream.println(sb.toString());
        printStream.println("For more information on JmDNS please visit https://sourceforge.net/projects/jmdns/");
    }

    private boolean makeServiceNameUnique(ServiceInfoImpl serviceInfoImpl) {
        boolean z2;
        ServiceInfo serviceInfo;
        String key = serviceInfoImpl.getKey();
        long jCurrentTimeMillis = System.currentTimeMillis();
        do {
            for (DNSEntry dNSEntry : getCache().getDNSEntryList(serviceInfoImpl.getKey())) {
                if (DNSRecordType.TYPE_SRV.equals(dNSEntry.getRecordType()) && !dNSEntry.isExpired(jCurrentTimeMillis)) {
                    DNSRecord.Service service = (DNSRecord.Service) dNSEntry;
                    if (service.getPort() != serviceInfoImpl.getPort() || !service.n().equals(this._localHost.getName())) {
                        if (logger.isLoggable(Level.FINER)) {
                            logger.finer("makeServiceNameUnique() JmDNS.makeServiceNameUnique srv collision:" + dNSEntry + " s.server=" + service.n() + " " + this._localHost.getName() + " equals:" + service.n().equals(this._localHost.getName()));
                        }
                        serviceInfoImpl.h(k(serviceInfoImpl.getName()));
                        z2 = true;
                        serviceInfo = this._services.get(serviceInfoImpl.getKey());
                        if (serviceInfo == null && serviceInfo != serviceInfoImpl) {
                            serviceInfoImpl.h(k(serviceInfoImpl.getName()));
                            z2 = true;
                        }
                    }
                }
            }
            z2 = false;
            serviceInfo = this._services.get(serviceInfoImpl.getKey());
            if (serviceInfo == null) {
            }
        } while (z2);
        return !key.equals(serviceInfoImpl.getKey());
    }

    private void openMulticastSocket(HostInfo hostInfo) throws IOException {
        if (this._group == null) {
            if (hostInfo.getInetAddress() instanceof Inet6Address) {
                this._group = InetAddress.getByName(DNSConstants.MDNS_GROUP_IPV6);
            } else {
                this._group = InetAddress.getByName(DNSConstants.MDNS_GROUP);
            }
        }
        if (this._socket != null) {
            closeMulticastSocket();
        }
        this._socket = new MulticastSocket(DNSConstants.MDNS_PORT);
        if (hostInfo != null && hostInfo.getInterface() != null) {
            try {
                this._socket.setNetworkInterface(hostInfo.getInterface());
            } catch (SocketException e2) {
                if (logger.isLoggable(Level.FINE)) {
                    logger.fine("openMulticastSocket() Set network interface exception: " + e2.getMessage());
                }
            }
        }
        this._socket.setTimeToLive(255);
        this._socket.joinGroup(this._group);
    }

    private void start(Collection<? extends ServiceInfo> collection) {
        if (this._incomingListener == null) {
            SocketListener socketListener = new SocketListener(this);
            this._incomingListener = socketListener;
            socketListener.start();
        }
        startProber();
        Iterator<? extends ServiceInfo> it = collection.iterator();
        while (it.hasNext()) {
            try {
                registerService(new ServiceInfoImpl(it.next()));
            } catch (Exception e2) {
                logger.log(Level.WARNING, "start() Registration exception ", (Throwable) e2);
            }
        }
    }

    private void waitForInfoData(ServiceInfo serviceInfo, long j2) {
        synchronized (serviceInfo) {
            long j3 = j2 / 200;
            if (j3 < 1) {
                j3 = 1;
            }
            for (int i2 = 0; i2 < j3 && !serviceInfo.hasData(); i2++) {
                try {
                    serviceInfo.wait(200L);
                } catch (InterruptedException unused) {
                }
            }
        }
    }

    public DNSOutgoing addAnswer(DNSIncoming dNSIncoming, InetAddress inetAddress, int i2, DNSOutgoing dNSOutgoing, DNSRecord dNSRecord) throws IOException {
        if (dNSOutgoing == null) {
            dNSOutgoing = new DNSOutgoing(33792, false, dNSIncoming.getSenderUDPPayload());
        }
        try {
            dNSOutgoing.addAnswer(dNSIncoming, dNSRecord);
            return dNSOutgoing;
        } catch (IOException unused) {
            dNSOutgoing.setFlags(dNSOutgoing.getFlags() | 512);
            dNSOutgoing.setId(dNSIncoming.getId());
            send(dNSOutgoing);
            DNSOutgoing dNSOutgoing2 = new DNSOutgoing(33792, false, dNSIncoming.getSenderUDPPayload());
            dNSOutgoing2.addAnswer(dNSIncoming, dNSRecord);
            return dNSOutgoing2;
        }
    }

    public void addListener(DNSListener dNSListener, DNSQuestion dNSQuestion) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        this._listeners.add(dNSListener);
        if (dNSQuestion != null) {
            for (DNSEntry dNSEntry : getCache().getDNSEntryList(dNSQuestion.getName().toLowerCase())) {
                if (dNSQuestion.d(dNSEntry) && !dNSEntry.isExpired(jCurrentTimeMillis)) {
                    dNSListener.updateRecord(getCache(), jCurrentTimeMillis, dNSEntry);
                }
            }
        }
    }

    @Override // javax.jmdns.JmDNS
    public void addServiceListener(String str, ServiceListener serviceListener) {
        addServiceListener(str, serviceListener, false);
    }

    @Override // javax.jmdns.JmDNS
    public void addServiceTypeListener(ServiceTypeListener serviceTypeListener) throws IOException {
        ListenerStatus.ServiceTypeListenerStatus serviceTypeListenerStatus = new ListenerStatus.ServiceTypeListenerStatus(serviceTypeListener, false);
        this._typeListeners.add(serviceTypeListenerStatus);
        Iterator<String> it = this._serviceTypes.keySet().iterator();
        while (it.hasNext()) {
            serviceTypeListenerStatus.a(new ServiceEventImpl(this, it.next(), "", null));
        }
        startTypeResolver();
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public boolean advanceState(DNSTask dNSTask) {
        return this._localHost.advanceState(dNSTask);
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public void associateWithTask(DNSTask dNSTask, DNSState dNSState) {
        this._localHost.associateWithTask(dNSTask, dNSState);
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public boolean cancelState() {
        return this._localHost.cancelState();
    }

    @Override // javax.jmdns.impl.DNSTaskStarter
    public void cancelStateTimer() {
        DNSTaskStarter.Factory.getInstance().getStarter(getDns()).cancelStateTimer();
    }

    @Override // javax.jmdns.impl.DNSTaskStarter
    public void cancelTimer() {
        DNSTaskStarter.Factory.getInstance().getStarter(getDns()).cancelTimer();
    }

    public void cleanCache() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        for (DNSEntry dNSEntry : getCache().allValues()) {
            try {
                DNSRecord dNSRecord = (DNSRecord) dNSEntry;
                if (dNSRecord.isExpired(jCurrentTimeMillis)) {
                    updateRecord(jCurrentTimeMillis, dNSRecord, Operation.Remove);
                    getCache().removeDNSEntry(dNSRecord);
                } else if (dNSRecord.isStale(jCurrentTimeMillis)) {
                    renewServiceCollector(dNSRecord);
                }
            } catch (Exception e2) {
                logger.log(Level.SEVERE, getName() + ".Error while reaping records: " + dNSEntry, (Throwable) e2);
                logger.severe(toString());
            }
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (isClosing()) {
            return;
        }
        Logger logger2 = logger;
        Level level = Level.FINER;
        if (logger2.isLoggable(level)) {
            logger.finer("Cancelling JmDNS: " + this);
        }
        if (closeState()) {
            logger.finer("Canceling the timer");
            cancelTimer();
            unregisterAllServices();
            disposeServiceCollectors();
            if (logger.isLoggable(level)) {
                logger.finer("Wait for JmDNS cancel: " + this);
            }
            waitForCanceled(5000L);
            logger.finer("Canceling the state timer");
            cancelStateTimer();
            this._executor.shutdown();
            closeMulticastSocket();
            if (this.f25338a != null) {
                Runtime.getRuntime().removeShutdownHook(this.f25338a);
            }
            if (logger.isLoggable(level)) {
                logger.finer("JmDNS closed.");
            }
        }
        advanceState(null);
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public boolean closeState() {
        return this._localHost.closeState();
    }

    void e() throws IOException {
        Logger logger2 = logger;
        Level level = Level.FINER;
        if (logger2.isLoggable(level)) {
            logger.finer(getName() + "recover() Cleanning up");
        }
        logger.warning("RECOVERING");
        purgeTimer();
        ArrayList arrayList = new ArrayList(getServices().values());
        unregisterAllServices();
        disposeServiceCollectors();
        waitForCanceled(5000L);
        purgeStateTimer();
        closeMulticastSocket();
        getCache().clear();
        if (logger.isLoggable(level)) {
            logger.finer(getName() + "recover() All is clean");
        }
        if (!isCanceled()) {
            logger.log(Level.WARNING, getName() + "recover() Could not recover we are Down!");
            if (getDelegate() != null) {
                getDelegate().cannotRecoverFromIOError(getDns(), arrayList);
                return;
            }
            return;
        }
        Iterator<ServiceInfo> it = arrayList.iterator();
        while (it.hasNext()) {
            ((ServiceInfoImpl) it.next()).recoverState();
        }
        recoverState();
        try {
            openMulticastSocket(getLocalHost());
            start(arrayList);
        } catch (Exception e2) {
            logger.log(Level.WARNING, getName() + "recover() Start services exception ", (Throwable) e2);
        }
        logger.log(Level.WARNING, getName() + "recover() We are back!");
    }

    ServiceInfoImpl f(String str, String str2, String str3, boolean z2) {
        ServiceInfoImpl serviceInfoImpl;
        byte[] bArr;
        String server;
        ServiceInfo serviceInfo;
        ServiceInfo serviceInfo2;
        ServiceInfo serviceInfo3;
        ServiceInfo serviceInfo4;
        ServiceInfoImpl serviceInfoImpl2 = new ServiceInfoImpl(str, str2, str3, 0, 0, 0, z2, (byte[]) null);
        DNSCache cache = getCache();
        DNSRecordClass dNSRecordClass = DNSRecordClass.CLASS_ANY;
        DNSEntry dNSEntry = cache.getDNSEntry(new DNSRecord.Pointer(str, dNSRecordClass, false, 0, serviceInfoImpl2.getQualifiedName()));
        if (!(dNSEntry instanceof DNSRecord) || (serviceInfoImpl = (ServiceInfoImpl) ((DNSRecord) dNSEntry).getServiceInfo(z2)) == null) {
            return serviceInfoImpl2;
        }
        Map<ServiceInfo.Fields, String> qualifiedNameMap = serviceInfoImpl.getQualifiedNameMap();
        DNSEntry dNSEntry2 = getCache().getDNSEntry(serviceInfoImpl2.getQualifiedName(), DNSRecordType.TYPE_SRV, dNSRecordClass);
        if (!(dNSEntry2 instanceof DNSRecord) || (serviceInfo4 = ((DNSRecord) dNSEntry2).getServiceInfo(z2)) == null) {
            bArr = null;
            server = "";
        } else {
            ServiceInfoImpl serviceInfoImpl3 = new ServiceInfoImpl(qualifiedNameMap, serviceInfo4.getPort(), serviceInfo4.getWeight(), serviceInfo4.getPriority(), z2, (byte[]) null);
            byte[] textBytes = serviceInfo4.getTextBytes();
            server = serviceInfo4.getServer();
            bArr = textBytes;
            serviceInfoImpl = serviceInfoImpl3;
        }
        DNSEntry dNSEntry3 = getCache().getDNSEntry(server, DNSRecordType.TYPE_A, dNSRecordClass);
        if ((dNSEntry3 instanceof DNSRecord) && (serviceInfo3 = ((DNSRecord) dNSEntry3).getServiceInfo(z2)) != null) {
            for (Inet4Address inet4Address : serviceInfo3.getInet4Addresses()) {
                serviceInfoImpl.b(inet4Address);
            }
            serviceInfoImpl.a(serviceInfo3.getTextBytes());
        }
        DNSEntry dNSEntry4 = getCache().getDNSEntry(server, DNSRecordType.TYPE_AAAA, DNSRecordClass.CLASS_ANY);
        if ((dNSEntry4 instanceof DNSRecord) && (serviceInfo2 = ((DNSRecord) dNSEntry4).getServiceInfo(z2)) != null) {
            for (Inet6Address inet6Address : serviceInfo2.getInet6Addresses()) {
                serviceInfoImpl.c(inet6Address);
            }
            serviceInfoImpl.a(serviceInfo2.getTextBytes());
        }
        DNSEntry dNSEntry5 = getCache().getDNSEntry(serviceInfoImpl.getQualifiedName(), DNSRecordType.TYPE_TXT, DNSRecordClass.CLASS_ANY);
        if ((dNSEntry5 instanceof DNSRecord) && (serviceInfo = ((DNSRecord) dNSEntry5).getServiceInfo(z2)) != null) {
            serviceInfoImpl.a(serviceInfo.getTextBytes());
        }
        if (serviceInfoImpl.getTextBytes().length == 0) {
            serviceInfoImpl.a(bArr);
        }
        return serviceInfoImpl.hasData() ? serviceInfoImpl : serviceInfoImpl2;
    }

    void g(DNSIncoming dNSIncoming, InetAddress inetAddress, int i2) {
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(getName() + ".handle query: " + dNSIncoming);
        }
        long jCurrentTimeMillis = System.currentTimeMillis() + 120;
        Iterator<? extends DNSRecord> it = dNSIncoming.getAllAnswers().iterator();
        boolean zE = false;
        while (it.hasNext()) {
            zE |= it.next().e(this, jCurrentTimeMillis);
        }
        ioLock();
        try {
            DNSIncoming dNSIncoming2 = this._plannedAnswer;
            if (dNSIncoming2 != null) {
                dNSIncoming2.c(dNSIncoming);
            } else {
                DNSIncoming dNSIncomingM830clone = dNSIncoming.clone();
                if (dNSIncoming.isTruncated()) {
                    this._plannedAnswer = dNSIncomingM830clone;
                }
                startResponder(dNSIncomingM830clone, i2);
            }
            ioUnlock();
            long jCurrentTimeMillis2 = System.currentTimeMillis();
            Iterator<? extends DNSRecord> it2 = dNSIncoming.getAnswers().iterator();
            while (it2.hasNext()) {
                h(it2.next(), jCurrentTimeMillis2);
            }
            if (zE) {
                startProber();
            }
        } catch (Throwable th) {
            ioUnlock();
            throw th;
        }
    }

    public DNSCache getCache() {
        return this._cache;
    }

    @Override // javax.jmdns.JmDNS
    public JmDNS.Delegate getDelegate() {
        return this._delegate;
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public JmDNSImpl getDns() {
        return this;
    }

    public InetAddress getGroup() {
        return this._group;
    }

    @Override // javax.jmdns.JmDNS
    public String getHostName() {
        return this._localHost.getName();
    }

    @Override // javax.jmdns.JmDNS
    public InetAddress getInterface() throws IOException {
        return this._socket.getInterface();
    }

    public long getLastThrottleIncrement() {
        return this._lastThrottleIncrement;
    }

    public HostInfo getLocalHost() {
        return this._localHost;
    }

    @Override // javax.jmdns.JmDNS
    public String getName() {
        return this._name;
    }

    public DNSIncoming getPlannedAnswer() {
        return this._plannedAnswer;
    }

    @Override // javax.jmdns.JmDNS
    public ServiceInfo getServiceInfo(String str, String str2) {
        return getServiceInfo(str, str2, false, DNSConstants.SERVICE_INFO_TIMEOUT);
    }

    public Map<String, ServiceTypeEntry> getServiceTypes() {
        return this._serviceTypes;
    }

    public Map<String, ServiceInfo> getServices() {
        return this._services;
    }

    public MulticastSocket getSocket() {
        return this._socket;
    }

    public int getThrottle() {
        return this._throttle;
    }

    void h(DNSRecord dNSRecord, long j2) {
        Operation operation = Operation.Noop;
        boolean zIsExpired = dNSRecord.isExpired(j2);
        Logger logger2 = logger;
        Level level = Level.FINE;
        if (logger2.isLoggable(level)) {
            logger.fine(getName() + " handle response: " + dNSRecord);
        }
        if (!dNSRecord.isServicesDiscoveryMetaQuery() && !dNSRecord.isDomainDiscoveryQuery()) {
            boolean zIsUnique = dNSRecord.isUnique();
            DNSRecord dNSRecord2 = (DNSRecord) getCache().getDNSEntry(dNSRecord);
            if (logger.isLoggable(level)) {
                logger.fine(getName() + " handle response cached record: " + dNSRecord2);
            }
            if (zIsUnique) {
                for (DNSEntry dNSEntry : getCache().getDNSEntryList(dNSRecord.getKey())) {
                    if (dNSRecord.getRecordType().equals(dNSEntry.getRecordType()) && dNSRecord.getRecordClass().equals(dNSEntry.getRecordClass()) && dNSEntry != dNSRecord2) {
                        ((DNSRecord) dNSEntry).j(j2);
                    }
                }
            }
            if (dNSRecord2 != null) {
                if (zIsExpired) {
                    if (dNSRecord.getTTL() == 0) {
                        operation = Operation.Noop;
                        dNSRecord2.j(j2);
                    } else {
                        operation = Operation.Remove;
                        getCache().removeDNSEntry(dNSRecord2);
                    }
                } else if (dNSRecord.i(dNSRecord2) && (dNSRecord.sameSubtype(dNSRecord2) || dNSRecord.getSubtype().length() <= 0)) {
                    dNSRecord2.g(dNSRecord);
                    dNSRecord = dNSRecord2;
                } else if (dNSRecord.isSingleValued()) {
                    operation = Operation.Update;
                    getCache().replaceDNSEntry(dNSRecord, dNSRecord2);
                } else {
                    operation = Operation.Add;
                    getCache().addDNSEntry(dNSRecord);
                }
            } else if (!zIsExpired) {
                operation = Operation.Add;
                getCache().addDNSEntry(dNSRecord);
            }
        }
        if (dNSRecord.getRecordType() == DNSRecordType.TYPE_PTR) {
            if (dNSRecord.isServicesDiscoveryMetaQuery()) {
                if (zIsExpired) {
                    return;
                }
                registerServiceType(((DNSRecord.Pointer) dNSRecord).n());
                return;
            } else if (registerServiceType(dNSRecord.getName()) && operation == Operation.Noop) {
                operation = Operation.RegisterServiceType;
            }
        }
        if (operation != Operation.Noop) {
            updateRecord(j2, dNSRecord, operation);
        }
    }

    void i(DNSIncoming dNSIncoming) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        boolean zF = false;
        boolean zF2 = false;
        for (DNSRecord dNSRecord : dNSIncoming.getAllAnswers()) {
            h(dNSRecord, jCurrentTimeMillis);
            if (DNSRecordType.TYPE_A.equals(dNSRecord.getRecordType()) || DNSRecordType.TYPE_AAAA.equals(dNSRecord.getRecordType())) {
                zF |= dNSRecord.f(this);
            } else {
                zF2 |= dNSRecord.f(this);
            }
        }
        if (zF || zF2) {
            startProber();
        }
    }

    public void ioLock() {
        this._ioLock.lock();
    }

    public void ioUnlock() {
        this._ioLock.unlock();
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public boolean isAnnounced() {
        return this._localHost.isAnnounced();
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public boolean isAnnouncing() {
        return this._localHost.isAnnouncing();
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public boolean isAssociatedWithTask(DNSTask dNSTask, DNSState dNSState) {
        return this._localHost.isAssociatedWithTask(dNSTask, dNSState);
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public boolean isCanceled() {
        return this._localHost.isCanceled();
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public boolean isCanceling() {
        return this._localHost.isCanceling();
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public boolean isClosed() {
        return this._localHost.isClosed();
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public boolean isClosing() {
        return this._localHost.isClosing();
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public boolean isProbing() {
        return this._localHost.isProbing();
    }

    void j(final ServiceEvent serviceEvent) {
        ArrayList<ListenerStatus.ServiceListenerStatus> arrayList;
        List<ListenerStatus.ServiceListenerStatus> list = this._serviceListeners.get(serviceEvent.getType().toLowerCase());
        if (list == null || list.isEmpty() || serviceEvent.getInfo() == null || !serviceEvent.getInfo().hasData()) {
            return;
        }
        synchronized (list) {
            arrayList = new ArrayList(list);
        }
        for (final ListenerStatus.ServiceListenerStatus serviceListenerStatus : arrayList) {
            this._executor.submit(new Runnable() { // from class: javax.jmdns.impl.JmDNSImpl.1
                @Override // java.lang.Runnable
                public void run() {
                    serviceListenerStatus.c(serviceEvent);
                }
            });
        }
    }

    String k(String str) {
        try {
            int iLastIndexOf = str.lastIndexOf(40);
            int iLastIndexOf2 = str.lastIndexOf(41);
            if (iLastIndexOf < 0 || iLastIndexOf >= iLastIndexOf2) {
                str = str + " (2)";
            } else {
                str = str.substring(0, iLastIndexOf) + "(" + (Integer.parseInt(str.substring(iLastIndexOf + 1, iLastIndexOf2)) + 1) + ")";
            }
            return str;
        } catch (NumberFormatException unused) {
            return str + " (2)";
        }
    }

    ServiceInfoImpl l(String str, String str2, String str3, boolean z2) {
        cleanCache();
        String lowerCase = str.toLowerCase();
        registerServiceType(str);
        if (this._serviceCollectors.putIfAbsent(lowerCase, new ServiceCollector(str)) == null) {
            addServiceListener(lowerCase, this._serviceCollectors.get(lowerCase), true);
        }
        ServiceInfoImpl serviceInfoImplF = f(str, str2, str3, z2);
        startServiceInfoResolver(serviceInfoImplF);
        return serviceInfoImplF;
    }

    @Override // javax.jmdns.JmDNS
    public ServiceInfo[] list(String str) {
        return list(str, DNSConstants.SERVICE_INFO_TIMEOUT);
    }

    @Override // javax.jmdns.JmDNS
    public Map<String, ServiceInfo[]> listBySubtype(String str) {
        return listBySubtype(str, DNSConstants.SERVICE_INFO_TIMEOUT);
    }

    @Override // javax.jmdns.JmDNS
    @Deprecated
    public void printServices() {
        System.err.println(toString());
    }

    @Override // javax.jmdns.impl.DNSTaskStarter
    public void purgeStateTimer() {
        DNSTaskStarter.Factory.getInstance().getStarter(getDns()).purgeStateTimer();
    }

    @Override // javax.jmdns.impl.DNSTaskStarter
    public void purgeTimer() {
        DNSTaskStarter.Factory.getInstance().getStarter(getDns()).purgeTimer();
    }

    public void recover() {
        logger.finer(getName() + "recover()");
        if (isClosing() || isClosed() || isCanceling() || isCanceled()) {
            return;
        }
        synchronized (this._recoverLock) {
            try {
                if (cancelState()) {
                    logger.finer(getName() + "recover() thread " + Thread.currentThread().getName());
                    StringBuilder sb = new StringBuilder();
                    sb.append(getName());
                    sb.append(".recover()");
                    new Thread(sb.toString()) { // from class: javax.jmdns.impl.JmDNSImpl.6
                        @Override // java.lang.Thread, java.lang.Runnable
                        public void run() throws IOException {
                            JmDNSImpl.this.e();
                        }
                    }.start();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public boolean recoverState() {
        return this._localHost.recoverState();
    }

    @Override // javax.jmdns.JmDNS
    public void registerService(ServiceInfo serviceInfo) throws IOException {
        if (isClosing() || isClosed()) {
            throw new IllegalStateException("This DNS is closed.");
        }
        ServiceInfoImpl serviceInfoImpl = (ServiceInfoImpl) serviceInfo;
        if (serviceInfoImpl.getDns() != null) {
            if (serviceInfoImpl.getDns() != this) {
                throw new IllegalStateException("A service information can only be registered with a single instamce of JmDNS.");
            }
            if (this._services.get(serviceInfoImpl.getKey()) != null) {
                throw new IllegalStateException("A service information can only be registered once.");
            }
        }
        serviceInfoImpl.setDns(this);
        registerServiceType(serviceInfoImpl.getTypeWithSubtype());
        serviceInfoImpl.recoverState();
        serviceInfoImpl.i(this._localHost.getName());
        serviceInfoImpl.b(this._localHost.c());
        serviceInfoImpl.c(this._localHost.d());
        waitForAnnounced(DNSConstants.SERVICE_INFO_TIMEOUT);
        makeServiceNameUnique(serviceInfoImpl);
        while (this._services.putIfAbsent(serviceInfoImpl.getKey(), serviceInfoImpl) != null) {
            makeServiceNameUnique(serviceInfoImpl);
        }
        startProber();
        serviceInfoImpl.waitForAnnounced(DNSConstants.SERVICE_INFO_TIMEOUT);
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("registerService() JmDNS registered service as " + serviceInfoImpl);
        }
    }

    @Override // javax.jmdns.JmDNS
    public boolean registerServiceType(String str) {
        boolean z2;
        ServiceTypeEntry serviceTypeEntry;
        Map<ServiceInfo.Fields, String> mapDecodeQualifiedNameMapForType = ServiceInfoImpl.decodeQualifiedNameMapForType(str);
        String str2 = mapDecodeQualifiedNameMapForType.get(ServiceInfo.Fields.Domain);
        String str3 = mapDecodeQualifiedNameMapForType.get(ServiceInfo.Fields.Protocol);
        String str4 = mapDecodeQualifiedNameMapForType.get(ServiceInfo.Fields.Application);
        String str5 = mapDecodeQualifiedNameMapForType.get(ServiceInfo.Fields.Subtype);
        StringBuilder sb = new StringBuilder();
        sb.append(str4.length() > 0 ? OpenAccountUIConstants.UNDER_LINE + str4 + "." : "");
        sb.append(str3.length() > 0 ? OpenAccountUIConstants.UNDER_LINE + str3 + "." : "");
        sb.append(str2);
        sb.append(".");
        String string = sb.toString();
        String lowerCase = string.toLowerCase();
        if (logger.isLoggable(Level.FINE)) {
            Logger logger2 = logger;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(getName());
            sb2.append(".registering service type: ");
            sb2.append(str);
            sb2.append(" as: ");
            sb2.append(string);
            sb2.append(str5.length() > 0 ? " subtype: " + str5 : "");
            logger2.fine(sb2.toString());
        }
        boolean z3 = true;
        if (this._serviceTypes.containsKey(lowerCase) || str4.toLowerCase().equals("dns-sd") || str2.toLowerCase().endsWith("in-addr.arpa") || str2.toLowerCase().endsWith("ip6.arpa")) {
            z2 = false;
        } else {
            z2 = this._serviceTypes.putIfAbsent(lowerCase, new ServiceTypeEntry(string)) == null;
            if (z2) {
                Set<ListenerStatus.ServiceTypeListenerStatus> set = this._typeListeners;
                ListenerStatus.ServiceTypeListenerStatus[] serviceTypeListenerStatusArr = (ListenerStatus.ServiceTypeListenerStatus[]) set.toArray(new ListenerStatus.ServiceTypeListenerStatus[set.size()]);
                final ServiceEventImpl serviceEventImpl = new ServiceEventImpl(this, string, "", null);
                for (final ListenerStatus.ServiceTypeListenerStatus serviceTypeListenerStatus : serviceTypeListenerStatusArr) {
                    this._executor.submit(new Runnable() { // from class: javax.jmdns.impl.JmDNSImpl.2
                        @Override // java.lang.Runnable
                        public void run() {
                            serviceTypeListenerStatus.a(serviceEventImpl);
                        }
                    });
                }
            }
        }
        if (str5.length() <= 0 || (serviceTypeEntry = this._serviceTypes.get(lowerCase)) == null || serviceTypeEntry.contains(str5)) {
            return z2;
        }
        synchronized (serviceTypeEntry) {
            try {
                if (serviceTypeEntry.contains(str5)) {
                    z3 = z2;
                } else {
                    serviceTypeEntry.add(str5);
                    Set<ListenerStatus.ServiceTypeListenerStatus> set2 = this._typeListeners;
                    ListenerStatus.ServiceTypeListenerStatus[] serviceTypeListenerStatusArr2 = (ListenerStatus.ServiceTypeListenerStatus[]) set2.toArray(new ListenerStatus.ServiceTypeListenerStatus[set2.size()]);
                    final ServiceEventImpl serviceEventImpl2 = new ServiceEventImpl(this, OpenAccountUIConstants.UNDER_LINE + str5 + "._sub." + string, "", null);
                    for (final ListenerStatus.ServiceTypeListenerStatus serviceTypeListenerStatus2 : serviceTypeListenerStatusArr2) {
                        this._executor.submit(new Runnable() { // from class: javax.jmdns.impl.JmDNSImpl.3
                            @Override // java.lang.Runnable
                            public void run() {
                                serviceTypeListenerStatus2.b(serviceEventImpl2);
                            }
                        });
                    }
                }
            } finally {
            }
        }
        return z3;
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public void removeAssociationWithTask(DNSTask dNSTask) {
        this._localHost.removeAssociationWithTask(dNSTask);
    }

    public void removeListener(DNSListener dNSListener) {
        this._listeners.remove(dNSListener);
    }

    @Override // javax.jmdns.JmDNS
    public void removeServiceListener(String str, ServiceListener serviceListener) {
        String lowerCase = str.toLowerCase();
        List<ListenerStatus.ServiceListenerStatus> list = this._serviceListeners.get(lowerCase);
        if (list != null) {
            synchronized (list) {
                try {
                    list.remove(new ListenerStatus.ServiceListenerStatus(serviceListener, false));
                    if (list.isEmpty()) {
                        this._serviceListeners.remove(lowerCase, list);
                    }
                } finally {
                }
            }
        }
    }

    @Override // javax.jmdns.JmDNS
    public void removeServiceTypeListener(ServiceTypeListener serviceTypeListener) {
        this._typeListeners.remove(new ListenerStatus.ServiceTypeListenerStatus(serviceTypeListener, false));
    }

    public void renewServiceCollector(DNSRecord dNSRecord) {
        ServiceInfo serviceInfo = dNSRecord.getServiceInfo();
        if (this._serviceCollectors.containsKey(serviceInfo.getType().toLowerCase())) {
            startServiceResolver(serviceInfo.getType());
        }
    }

    @Override // javax.jmdns.JmDNS
    public void requestServiceInfo(String str, String str2) {
        requestServiceInfo(str, str2, false, DNSConstants.SERVICE_INFO_TIMEOUT);
    }

    public void respondToQuery(DNSIncoming dNSIncoming) {
        ioLock();
        try {
            if (this._plannedAnswer == dNSIncoming) {
                this._plannedAnswer = null;
            }
        } finally {
            ioUnlock();
        }
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public boolean revertState() {
        return this._localHost.revertState();
    }

    public void send(DNSOutgoing dNSOutgoing) throws IOException {
        if (dNSOutgoing.isEmpty()) {
            return;
        }
        byte[] bArrData = dNSOutgoing.data();
        DatagramPacket datagramPacket = new DatagramPacket(bArrData, bArrData.length, this._group, DNSConstants.MDNS_PORT);
        Logger logger2 = logger;
        Level level = Level.FINEST;
        if (logger2.isLoggable(level)) {
            try {
                DNSIncoming dNSIncoming = new DNSIncoming(datagramPacket);
                if (logger.isLoggable(level)) {
                    logger.finest("send(" + getName() + ") JmDNS out:" + dNSIncoming.d(true));
                }
            } catch (IOException e2) {
                logger.throwing(getClass().toString(), "send(" + getName() + ") - JmDNS can not parse what it sends!!!", e2);
            }
        }
        MulticastSocket multicastSocket = this._socket;
        if (multicastSocket == null || multicastSocket.isClosed()) {
            return;
        }
        multicastSocket.send(datagramPacket);
    }

    @Override // javax.jmdns.JmDNS
    public JmDNS.Delegate setDelegate(JmDNS.Delegate delegate) {
        JmDNS.Delegate delegate2 = this._delegate;
        this._delegate = delegate;
        return delegate2;
    }

    public void setLastThrottleIncrement(long j2) {
        this._lastThrottleIncrement = j2;
    }

    public void setPlannedAnswer(DNSIncoming dNSIncoming) {
        this._plannedAnswer = dNSIncoming;
    }

    public void setThrottle(int i2) {
        this._throttle = i2;
    }

    @Override // javax.jmdns.impl.DNSTaskStarter
    public void startAnnouncer() {
        DNSTaskStarter.Factory.getInstance().getStarter(getDns()).startAnnouncer();
    }

    @Override // javax.jmdns.impl.DNSTaskStarter
    public void startCanceler() {
        DNSTaskStarter.Factory.getInstance().getStarter(getDns()).startCanceler();
    }

    @Override // javax.jmdns.impl.DNSTaskStarter
    public void startProber() {
        DNSTaskStarter.Factory.getInstance().getStarter(getDns()).startProber();
    }

    @Override // javax.jmdns.impl.DNSTaskStarter
    public void startReaper() {
        DNSTaskStarter.Factory.getInstance().getStarter(getDns()).startReaper();
    }

    @Override // javax.jmdns.impl.DNSTaskStarter
    public void startRenewer() {
        DNSTaskStarter.Factory.getInstance().getStarter(getDns()).startRenewer();
    }

    @Override // javax.jmdns.impl.DNSTaskStarter
    public void startResponder(DNSIncoming dNSIncoming, int i2) {
        DNSTaskStarter.Factory.getInstance().getStarter(getDns()).startResponder(dNSIncoming, i2);
    }

    @Override // javax.jmdns.impl.DNSTaskStarter
    public void startServiceInfoResolver(ServiceInfoImpl serviceInfoImpl) {
        DNSTaskStarter.Factory.getInstance().getStarter(getDns()).startServiceInfoResolver(serviceInfoImpl);
    }

    @Override // javax.jmdns.impl.DNSTaskStarter
    public void startServiceResolver(String str) {
        DNSTaskStarter.Factory.getInstance().getStarter(getDns()).startServiceResolver(str);
    }

    @Override // javax.jmdns.impl.DNSTaskStarter
    public void startTypeResolver() {
        DNSTaskStarter.Factory.getInstance().getStarter(getDns()).startTypeResolver();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v10, types: [java.util.AbstractMap, javax.jmdns.impl.JmDNSImpl$ServiceTypeEntry] */
    /* JADX WARN: Type inference failed for: r4v12, types: [java.lang.String] */
    public String toString() {
        StringBuilder sb = new StringBuilder(2048);
        sb.append("\t---- Local Host -----");
        sb.append("\n\t");
        sb.append(this._localHost);
        sb.append("\n\t---- Services -----");
        for (String str : this._services.keySet()) {
            sb.append("\n\t\tService: ");
            sb.append(str);
            sb.append(": ");
            sb.append(this._services.get(str));
        }
        sb.append("\n");
        sb.append("\t---- Types ----");
        Iterator<String> it = this._serviceTypes.keySet().iterator();
        while (it.hasNext()) {
            ServiceTypeEntry serviceTypeEntry = this._serviceTypes.get(it.next());
            sb.append("\n\t\tType: ");
            sb.append(serviceTypeEntry.getType());
            sb.append(": ");
            if (serviceTypeEntry.isEmpty()) {
                serviceTypeEntry = "no subtypes";
            }
            sb.append(serviceTypeEntry);
        }
        sb.append("\n");
        sb.append(this._cache.toString());
        sb.append("\n");
        sb.append("\t---- Service Collectors ----");
        for (String str2 : this._serviceCollectors.keySet()) {
            sb.append("\n\t\tService Collector: ");
            sb.append(str2);
            sb.append(": ");
            sb.append(this._serviceCollectors.get(str2));
        }
        sb.append("\n");
        sb.append("\t---- Service Listeners ----");
        for (String str3 : this._serviceListeners.keySet()) {
            sb.append("\n\t\tService Listener: ");
            sb.append(str3);
            sb.append(": ");
            sb.append(this._serviceListeners.get(str3));
        }
        return sb.toString();
    }

    @Override // javax.jmdns.JmDNS
    public void unregisterAllServices() {
        if (logger.isLoggable(Level.FINER)) {
            logger.finer("unregisterAllServices()");
        }
        Iterator<String> it = this._services.keySet().iterator();
        while (it.hasNext()) {
            ServiceInfoImpl serviceInfoImpl = (ServiceInfoImpl) this._services.get(it.next());
            if (serviceInfoImpl != null) {
                if (logger.isLoggable(Level.FINER)) {
                    logger.finer("Cancelling service info: " + serviceInfoImpl);
                }
                serviceInfoImpl.cancelState();
            }
        }
        startCanceler();
        for (String str : this._services.keySet()) {
            ServiceInfoImpl serviceInfoImpl2 = (ServiceInfoImpl) this._services.get(str);
            if (serviceInfoImpl2 != null) {
                if (logger.isLoggable(Level.FINER)) {
                    logger.finer("Wait for service info cancel: " + serviceInfoImpl2);
                }
                serviceInfoImpl2.waitForCanceled(5000L);
                this._services.remove(str, serviceInfoImpl2);
            }
        }
    }

    @Override // javax.jmdns.JmDNS
    public void unregisterService(ServiceInfo serviceInfo) {
        ServiceInfoImpl serviceInfoImpl = (ServiceInfoImpl) this._services.get(serviceInfo.getKey());
        if (serviceInfoImpl == null) {
            logger.warning("Removing unregistered service info: " + serviceInfo.getKey());
            return;
        }
        serviceInfoImpl.cancelState();
        startCanceler();
        serviceInfoImpl.waitForCanceled(5000L);
        this._services.remove(serviceInfoImpl.getKey(), serviceInfoImpl);
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("unregisterService() JmDNS unregistered service as " + serviceInfoImpl);
        }
    }

    public void updateRecord(long j2, DNSRecord dNSRecord, Operation operation) {
        ArrayList arrayList;
        List<ListenerStatus.ServiceListenerStatus> listEmptyList;
        synchronized (this._listeners) {
            arrayList = new ArrayList(this._listeners);
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((DNSListener) it.next()).updateRecord(getCache(), j2, dNSRecord);
        }
        if (DNSRecordType.TYPE_PTR.equals(dNSRecord.getRecordType())) {
            final ServiceEvent serviceEvent = dNSRecord.getServiceEvent(this);
            if (serviceEvent.getInfo() == null || !serviceEvent.getInfo().hasData()) {
                ServiceInfoImpl serviceInfoImplF = f(serviceEvent.getType(), serviceEvent.getName(), "", false);
                if (serviceInfoImplF.hasData()) {
                    serviceEvent = new ServiceEventImpl(this, serviceEvent.getType(), serviceEvent.getName(), serviceInfoImplF);
                }
            }
            List<ListenerStatus.ServiceListenerStatus> list = this._serviceListeners.get(serviceEvent.getType().toLowerCase());
            if (list != null) {
                synchronized (list) {
                    listEmptyList = new ArrayList(list);
                }
            } else {
                listEmptyList = Collections.emptyList();
            }
            if (logger.isLoggable(Level.FINEST)) {
                logger.finest(getName() + ".updating record for event: " + serviceEvent + " list " + listEmptyList + " operation: " + operation);
            }
            if (listEmptyList.isEmpty()) {
                return;
            }
            int i2 = AnonymousClass7.f25355a[operation.ordinal()];
            if (i2 == 1) {
                for (final ListenerStatus.ServiceListenerStatus serviceListenerStatus : listEmptyList) {
                    if (serviceListenerStatus.isSynchronous()) {
                        serviceListenerStatus.a(serviceEvent);
                    } else {
                        this._executor.submit(new Runnable() { // from class: javax.jmdns.impl.JmDNSImpl.4
                            @Override // java.lang.Runnable
                            public void run() {
                                serviceListenerStatus.a(serviceEvent);
                            }
                        });
                    }
                }
                return;
            }
            if (i2 != 2) {
                return;
            }
            for (final ListenerStatus.ServiceListenerStatus serviceListenerStatus2 : listEmptyList) {
                if (serviceListenerStatus2.isSynchronous()) {
                    serviceListenerStatus2.b(serviceEvent);
                } else {
                    this._executor.submit(new Runnable() { // from class: javax.jmdns.impl.JmDNSImpl.5
                        @Override // java.lang.Runnable
                        public void run() {
                            serviceListenerStatus2.b(serviceEvent);
                        }
                    });
                }
            }
        }
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public boolean waitForAnnounced(long j2) {
        return this._localHost.waitForAnnounced(j2);
    }

    @Override // javax.jmdns.impl.DNSStatefulObject
    public boolean waitForCanceled(long j2) {
        return this._localHost.waitForCanceled(j2);
    }

    private void addServiceListener(String str, ServiceListener serviceListener, boolean z2) {
        ListenerStatus.ServiceListenerStatus serviceListenerStatus = new ListenerStatus.ServiceListenerStatus(serviceListener, z2);
        String lowerCase = str.toLowerCase();
        List<ListenerStatus.ServiceListenerStatus> list = this._serviceListeners.get(lowerCase);
        if (list == null) {
            if (this._serviceListeners.putIfAbsent(lowerCase, new LinkedList()) == null && this._serviceCollectors.putIfAbsent(lowerCase, new ServiceCollector(str)) == null) {
                addServiceListener(lowerCase, this._serviceCollectors.get(lowerCase), true);
            }
            list = this._serviceListeners.get(lowerCase);
        }
        if (list != null) {
            synchronized (list) {
                try {
                    if (!list.contains(serviceListener)) {
                        list.add(serviceListenerStatus);
                    }
                } finally {
                }
            }
        }
        ArrayList arrayList = new ArrayList();
        Iterator<DNSEntry> it = getCache().allValues().iterator();
        while (it.hasNext()) {
            DNSRecord dNSRecord = (DNSRecord) it.next();
            if (dNSRecord.getRecordType() == DNSRecordType.TYPE_SRV && dNSRecord.getKey().endsWith(lowerCase)) {
                arrayList.add(new ServiceEventImpl(this, dNSRecord.getType(), m(dNSRecord.getType(), dNSRecord.getName()), dNSRecord.getServiceInfo()));
            }
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            serviceListenerStatus.a((ServiceEvent) it2.next());
        }
        startServiceResolver(str);
    }

    @Override // javax.jmdns.JmDNS
    public ServiceInfo getServiceInfo(String str, String str2, long j2) {
        return getServiceInfo(str, str2, false, j2);
    }

    @Override // javax.jmdns.JmDNS
    public ServiceInfo[] list(String str, long j2) {
        cleanCache();
        String lowerCase = str.toLowerCase();
        if (isCanceling() || isCanceled()) {
            return new ServiceInfo[0];
        }
        ServiceCollector serviceCollector = this._serviceCollectors.get(lowerCase);
        if (serviceCollector == null) {
            boolean z2 = this._serviceCollectors.putIfAbsent(lowerCase, new ServiceCollector(str)) == null;
            ServiceCollector serviceCollector2 = this._serviceCollectors.get(lowerCase);
            if (z2) {
                addServiceListener(str, serviceCollector2, true);
            }
            serviceCollector = serviceCollector2;
        }
        if (logger.isLoggable(Level.FINER)) {
            logger.finer(getName() + ".collector: " + serviceCollector);
        }
        return serviceCollector != null ? serviceCollector.list(j2) : new ServiceInfo[0];
    }

    @Override // javax.jmdns.JmDNS
    public Map<String, ServiceInfo[]> listBySubtype(String str, long j2) {
        HashMap map = new HashMap(5);
        for (ServiceInfo serviceInfo : list(str, j2)) {
            String lowerCase = serviceInfo.getSubtype().toLowerCase();
            if (!map.containsKey(lowerCase)) {
                map.put(lowerCase, new ArrayList(10));
            }
            ((List) map.get(lowerCase)).add(serviceInfo);
        }
        HashMap map2 = new HashMap(map.size());
        for (String str2 : map.keySet()) {
            List list = (List) map.get(str2);
            map2.put(str2, list.toArray(new ServiceInfo[list.size()]));
        }
        return map2;
    }

    @Override // javax.jmdns.JmDNS
    public void requestServiceInfo(String str, String str2, boolean z2) {
        requestServiceInfo(str, str2, z2, DNSConstants.SERVICE_INFO_TIMEOUT);
    }

    @Override // javax.jmdns.JmDNS
    public ServiceInfo getServiceInfo(String str, String str2, boolean z2) {
        return getServiceInfo(str, str2, z2, DNSConstants.SERVICE_INFO_TIMEOUT);
    }

    @Override // javax.jmdns.JmDNS
    public void requestServiceInfo(String str, String str2, long j2) {
        requestServiceInfo(str, str2, false, DNSConstants.SERVICE_INFO_TIMEOUT);
    }

    @Override // javax.jmdns.JmDNS
    public ServiceInfo getServiceInfo(String str, String str2, boolean z2, long j2) {
        ServiceInfoImpl serviceInfoImplL = l(str, str2, "", z2);
        waitForInfoData(serviceInfoImplL, j2);
        if (serviceInfoImplL.hasData()) {
            return serviceInfoImplL;
        }
        return null;
    }

    @Override // javax.jmdns.JmDNS
    public void requestServiceInfo(String str, String str2, boolean z2, long j2) {
        waitForInfoData(l(str, str2, "", z2), j2);
    }
}
