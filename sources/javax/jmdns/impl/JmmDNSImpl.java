package javax.jmdns.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.JmDNS;
import javax.jmdns.JmmDNS;
import javax.jmdns.NetworkTopologyDiscovery;
import javax.jmdns.NetworkTopologyEvent;
import javax.jmdns.NetworkTopologyListener;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import javax.jmdns.ServiceTypeListener;
import javax.jmdns.impl.ServiceInfoImpl;
import javax.jmdns.impl.constants.DNSConstants;

/* loaded from: classes4.dex */
public class JmmDNSImpl implements JmmDNS, NetworkTopologyListener, ServiceInfoImpl.Delegate {
    private static Logger logger = Logger.getLogger(JmmDNSImpl.class.getName());
    private final Timer _timer;
    private final Set<NetworkTopologyListener> _networkListeners = Collections.synchronizedSet(new HashSet());
    private final ConcurrentMap<InetAddress, JmDNS> _knownMDNS = new ConcurrentHashMap();
    private final ConcurrentMap<String, ServiceInfo> _services = new ConcurrentHashMap(20);
    private final ExecutorService _ListenerExecutor = Executors.newSingleThreadExecutor();
    private final ExecutorService _jmDNSExecutor = Executors.newCachedThreadPool();

    static class NetworkChecker extends TimerTask {
        private static Logger logger1 = Logger.getLogger(NetworkChecker.class.getName());
        private Set<InetAddress> _knownAddresses = Collections.synchronizedSet(new HashSet());
        private final NetworkTopologyListener _mmDNS;
        private final NetworkTopologyDiscovery _topology;

        public NetworkChecker(NetworkTopologyListener networkTopologyListener, NetworkTopologyDiscovery networkTopologyDiscovery) {
            this._mmDNS = networkTopologyListener;
            this._topology = networkTopologyDiscovery;
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            try {
                InetAddress[] inetAddresses = this._topology.getInetAddresses();
                HashSet hashSet = new HashSet(inetAddresses.length);
                for (InetAddress inetAddress : inetAddresses) {
                    hashSet.add(inetAddress);
                    if (!this._knownAddresses.contains(inetAddress)) {
                        this._mmDNS.inetAddressAdded(new NetworkTopologyEventImpl(this._mmDNS, inetAddress));
                    }
                }
                for (InetAddress inetAddress2 : this._knownAddresses) {
                    if (!hashSet.contains(inetAddress2)) {
                        this._mmDNS.inetAddressRemoved(new NetworkTopologyEventImpl(this._mmDNS, inetAddress2));
                    }
                }
                this._knownAddresses = hashSet;
            } catch (Exception e2) {
                logger1.warning("Unexpected unhandled exception: " + e2);
            }
        }

        public void start(Timer timer) {
            timer.schedule(this, 0L, 10000L);
        }
    }

    public JmmDNSImpl() {
        Timer timer = new Timer("Multihommed mDNS.Timer", true);
        this._timer = timer;
        new NetworkChecker(this, NetworkTopologyDiscovery.Factory.getInstance()).start(timer);
    }

    @Override // javax.jmdns.JmmDNS
    public void addNetworkTopologyListener(NetworkTopologyListener networkTopologyListener) {
        this._networkListeners.add(networkTopologyListener);
    }

    @Override // javax.jmdns.JmmDNS
    public void addServiceListener(String str, ServiceListener serviceListener) {
        Iterator<JmDNS> it = this._knownMDNS.values().iterator();
        while (it.hasNext()) {
            it.next().addServiceListener(str, serviceListener);
        }
    }

    @Override // javax.jmdns.JmmDNS
    public void addServiceTypeListener(ServiceTypeListener serviceTypeListener) throws IOException {
        Iterator<JmDNS> it = this._knownMDNS.values().iterator();
        while (it.hasNext()) {
            it.next().addServiceTypeListener(serviceTypeListener);
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws InterruptedException, IOException {
        if (logger.isLoggable(Level.FINER)) {
            logger.finer("Cancelling JmmDNS: " + this);
        }
        this._timer.cancel();
        this._ListenerExecutor.shutdown();
        ExecutorService executorServiceNewCachedThreadPool = Executors.newCachedThreadPool();
        for (final JmDNS jmDNS : this._knownMDNS.values()) {
            executorServiceNewCachedThreadPool.submit(new Runnable() { // from class: javax.jmdns.impl.JmmDNSImpl.1
                @Override // java.lang.Runnable
                public void run() throws IOException {
                    try {
                        jmDNS.close();
                    } catch (IOException unused) {
                    }
                }
            });
        }
        executorServiceNewCachedThreadPool.shutdown();
        try {
            executorServiceNewCachedThreadPool.awaitTermination(5000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e2) {
            logger.log(Level.WARNING, "Exception ", (Throwable) e2);
        }
        this._knownMDNS.clear();
    }

    @Override // javax.jmdns.JmmDNS
    public String[] getHostNames() {
        HashSet hashSet = new HashSet();
        Iterator<JmDNS> it = this._knownMDNS.values().iterator();
        while (it.hasNext()) {
            hashSet.add(it.next().getHostName());
        }
        return (String[]) hashSet.toArray(new String[hashSet.size()]);
    }

    @Override // javax.jmdns.JmmDNS
    public InetAddress[] getInterfaces() throws IOException {
        HashSet hashSet = new HashSet();
        Iterator<JmDNS> it = this._knownMDNS.values().iterator();
        while (it.hasNext()) {
            hashSet.add(it.next().getInterface());
        }
        return (InetAddress[]) hashSet.toArray(new InetAddress[hashSet.size()]);
    }

    @Override // javax.jmdns.JmmDNS
    public String[] getNames() {
        HashSet hashSet = new HashSet();
        Iterator<JmDNS> it = this._knownMDNS.values().iterator();
        while (it.hasNext()) {
            hashSet.add(it.next().getName());
        }
        return (String[]) hashSet.toArray(new String[hashSet.size()]);
    }

    @Override // javax.jmdns.JmmDNS
    public ServiceInfo[] getServiceInfos(String str, String str2) {
        return getServiceInfos(str, str2, false, DNSConstants.SERVICE_INFO_TIMEOUT);
    }

    @Override // javax.jmdns.NetworkTopologyListener
    public void inetAddressAdded(NetworkTopologyEvent networkTopologyEvent) {
        InetAddress inetAddress = networkTopologyEvent.getInetAddress();
        try {
            synchronized (this) {
                try {
                    if (!this._knownMDNS.containsKey(inetAddress)) {
                        this._knownMDNS.put(inetAddress, JmDNS.create(inetAddress));
                        final NetworkTopologyEventImpl networkTopologyEventImpl = new NetworkTopologyEventImpl(this._knownMDNS.get(inetAddress), inetAddress);
                        for (final NetworkTopologyListener networkTopologyListener : networkListeners()) {
                            this._ListenerExecutor.submit(new Runnable() { // from class: javax.jmdns.impl.JmmDNSImpl.5
                                @Override // java.lang.Runnable
                                public void run() {
                                    networkTopologyListener.inetAddressAdded(networkTopologyEventImpl);
                                }
                            });
                        }
                    }
                } finally {
                }
            }
        } catch (Exception e2) {
            logger.warning("Unexpected unhandled exception: " + e2);
        }
    }

    @Override // javax.jmdns.NetworkTopologyListener
    public void inetAddressRemoved(NetworkTopologyEvent networkTopologyEvent) {
        InetAddress inetAddress = networkTopologyEvent.getInetAddress();
        try {
            synchronized (this) {
                try {
                    if (this._knownMDNS.containsKey(inetAddress)) {
                        JmDNS jmDNSRemove = this._knownMDNS.remove(inetAddress);
                        jmDNSRemove.close();
                        final NetworkTopologyEventImpl networkTopologyEventImpl = new NetworkTopologyEventImpl(jmDNSRemove, inetAddress);
                        for (final NetworkTopologyListener networkTopologyListener : networkListeners()) {
                            this._ListenerExecutor.submit(new Runnable() { // from class: javax.jmdns.impl.JmmDNSImpl.6
                                @Override // java.lang.Runnable
                                public void run() {
                                    networkTopologyListener.inetAddressRemoved(networkTopologyEventImpl);
                                }
                            });
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (Exception e2) {
            logger.warning("Unexpected unhandled exception: " + e2);
        }
    }

    @Override // javax.jmdns.JmmDNS
    public ServiceInfo[] list(String str) {
        return list(str, DNSConstants.SERVICE_INFO_TIMEOUT);
    }

    @Override // javax.jmdns.JmmDNS
    public Map<String, ServiceInfo[]> listBySubtype(String str) {
        return listBySubtype(str, DNSConstants.SERVICE_INFO_TIMEOUT);
    }

    @Override // javax.jmdns.JmmDNS
    public NetworkTopologyListener[] networkListeners() {
        Set<NetworkTopologyListener> set = this._networkListeners;
        return (NetworkTopologyListener[]) set.toArray(new NetworkTopologyListener[set.size()]);
    }

    @Override // javax.jmdns.JmmDNS
    public void registerService(ServiceInfo serviceInfo) throws IOException {
        synchronized (this._services) {
            try {
                Iterator<JmDNS> it = this._knownMDNS.values().iterator();
                while (it.hasNext()) {
                    it.next().registerService(serviceInfo.clone());
                }
                ((ServiceInfoImpl) serviceInfo).g(this);
                this._services.put(serviceInfo.getQualifiedName(), serviceInfo);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // javax.jmdns.JmmDNS
    public void registerServiceType(String str) {
        Iterator<JmDNS> it = this._knownMDNS.values().iterator();
        while (it.hasNext()) {
            it.next().registerServiceType(str);
        }
    }

    @Override // javax.jmdns.JmmDNS
    public void removeNetworkTopologyListener(NetworkTopologyListener networkTopologyListener) {
        this._networkListeners.remove(networkTopologyListener);
    }

    @Override // javax.jmdns.JmmDNS
    public void removeServiceListener(String str, ServiceListener serviceListener) {
        Iterator<JmDNS> it = this._knownMDNS.values().iterator();
        while (it.hasNext()) {
            it.next().removeServiceListener(str, serviceListener);
        }
    }

    @Override // javax.jmdns.JmmDNS
    public void removeServiceTypeListener(ServiceTypeListener serviceTypeListener) {
        Iterator<JmDNS> it = this._knownMDNS.values().iterator();
        while (it.hasNext()) {
            it.next().removeServiceTypeListener(serviceTypeListener);
        }
    }

    @Override // javax.jmdns.JmmDNS
    public void requestServiceInfo(String str, String str2) {
        requestServiceInfo(str, str2, false, DNSConstants.SERVICE_INFO_TIMEOUT);
    }

    @Override // javax.jmdns.impl.ServiceInfoImpl.Delegate
    public void textValueUpdated(ServiceInfo serviceInfo, byte[] bArr) {
        synchronized (this._services) {
            try {
                Iterator<JmDNS> it = this._knownMDNS.values().iterator();
                while (it.hasNext()) {
                    ServiceInfo serviceInfo2 = ((JmDNSImpl) it.next()).getServices().get(serviceInfo.getQualifiedName());
                    if (serviceInfo2 != null) {
                        serviceInfo2.setText(bArr);
                    } else {
                        logger.warning("We have a mDNS that does not know about the service info being updated.");
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // javax.jmdns.JmmDNS
    public void unregisterAllServices() {
        synchronized (this._services) {
            try {
                Iterator<JmDNS> it = this._knownMDNS.values().iterator();
                while (it.hasNext()) {
                    it.next().unregisterAllServices();
                }
                this._services.clear();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // javax.jmdns.JmmDNS
    public void unregisterService(ServiceInfo serviceInfo) {
        synchronized (this._services) {
            try {
                Iterator<JmDNS> it = this._knownMDNS.values().iterator();
                while (it.hasNext()) {
                    it.next().unregisterService(serviceInfo);
                }
                ((ServiceInfoImpl) serviceInfo).g(null);
                this._services.remove(serviceInfo.getQualifiedName());
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // javax.jmdns.JmmDNS
    public ServiceInfo[] getServiceInfos(String str, String str2, long j2) {
        return getServiceInfos(str, str2, false, j2);
    }

    @Override // javax.jmdns.JmmDNS
    public ServiceInfo[] list(final String str, final long j2) throws InterruptedException {
        final Set setSynchronizedSet = Collections.synchronizedSet(new HashSet(this._knownMDNS.size() * 5));
        ExecutorService executorServiceNewCachedThreadPool = Executors.newCachedThreadPool();
        for (final JmDNS jmDNS : this._knownMDNS.values()) {
            executorServiceNewCachedThreadPool.submit(new Runnable() { // from class: javax.jmdns.impl.JmmDNSImpl.4
                @Override // java.lang.Runnable
                public void run() {
                    setSynchronizedSet.addAll(Arrays.asList(jmDNS.list(str, j2)));
                }
            });
        }
        executorServiceNewCachedThreadPool.shutdown();
        try {
            executorServiceNewCachedThreadPool.awaitTermination(j2, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e2) {
            logger.log(Level.WARNING, "Exception ", (Throwable) e2);
        }
        return (ServiceInfo[]) setSynchronizedSet.toArray(new ServiceInfo[setSynchronizedSet.size()]);
    }

    @Override // javax.jmdns.JmmDNS
    public Map<String, ServiceInfo[]> listBySubtype(String str, long j2) throws InterruptedException {
        HashMap map = new HashMap(5);
        for (ServiceInfo serviceInfo : list(str, j2)) {
            String subtype = serviceInfo.getSubtype();
            if (!map.containsKey(subtype)) {
                map.put(subtype, new ArrayList(10));
            }
            ((List) map.get(subtype)).add(serviceInfo);
        }
        HashMap map2 = new HashMap(map.size());
        for (String str2 : map.keySet()) {
            List list = (List) map.get(str2);
            map2.put(str2, list.toArray(new ServiceInfo[list.size()]));
        }
        return map2;
    }

    @Override // javax.jmdns.JmmDNS
    public void requestServiceInfo(String str, String str2, boolean z2) {
        requestServiceInfo(str, str2, z2, DNSConstants.SERVICE_INFO_TIMEOUT);
    }

    @Override // javax.jmdns.JmmDNS
    public ServiceInfo[] getServiceInfos(String str, String str2, boolean z2) {
        return getServiceInfos(str, str2, z2, DNSConstants.SERVICE_INFO_TIMEOUT);
    }

    @Override // javax.jmdns.JmmDNS
    public void requestServiceInfo(String str, String str2, long j2) {
        requestServiceInfo(str, str2, false, j2);
    }

    @Override // javax.jmdns.JmmDNS
    public ServiceInfo[] getServiceInfos(final String str, final String str2, final boolean z2, final long j2) throws InterruptedException {
        final Set setSynchronizedSet = Collections.synchronizedSet(new HashSet(this._knownMDNS.size()));
        ExecutorService executorServiceNewCachedThreadPool = Executors.newCachedThreadPool();
        for (final JmDNS jmDNS : this._knownMDNS.values()) {
            executorServiceNewCachedThreadPool.submit(new Runnable() { // from class: javax.jmdns.impl.JmmDNSImpl.2
                @Override // java.lang.Runnable
                public void run() {
                    setSynchronizedSet.add(jmDNS.getServiceInfo(str, str2, z2, j2));
                }
            });
        }
        executorServiceNewCachedThreadPool.shutdown();
        try {
            executorServiceNewCachedThreadPool.awaitTermination(j2, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e2) {
            logger.log(Level.WARNING, "Exception ", (Throwable) e2);
        }
        return (ServiceInfo[]) setSynchronizedSet.toArray(new ServiceInfo[setSynchronizedSet.size()]);
    }

    @Override // javax.jmdns.JmmDNS
    public void requestServiceInfo(final String str, final String str2, final boolean z2, final long j2) {
        for (final JmDNS jmDNS : this._knownMDNS.values()) {
            this._jmDNSExecutor.submit(new Runnable() { // from class: javax.jmdns.impl.JmmDNSImpl.3
                @Override // java.lang.Runnable
                public void run() {
                    jmDNS.requestServiceInfo(str, str2, z2, j2);
                }
            });
        }
    }
}
