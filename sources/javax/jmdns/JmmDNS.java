package javax.jmdns;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import javax.jmdns.impl.JmmDNSImpl;

/* loaded from: classes4.dex */
public interface JmmDNS extends Closeable {

    public static final class Factory {
        private static final AtomicReference<ClassDelegate> _databaseClassDelegate = new AtomicReference<>();
        private static volatile JmmDNS _instance;

        public interface ClassDelegate {
            JmmDNS newJmmDNS();
        }

        private Factory() {
        }

        protected static JmmDNS a() {
            ClassDelegate classDelegate = _databaseClassDelegate.get();
            JmmDNS jmmDNSNewJmmDNS = classDelegate != null ? classDelegate.newJmmDNS() : null;
            return jmmDNSNewJmmDNS != null ? jmmDNSNewJmmDNS : new JmmDNSImpl();
        }

        public static ClassDelegate classDelegate() {
            return _databaseClassDelegate.get();
        }

        public static JmmDNS getInstance() {
            if (_instance == null) {
                synchronized (Factory.class) {
                    try {
                        if (_instance == null) {
                            _instance = a();
                        }
                    } finally {
                    }
                }
            }
            return _instance;
        }

        public static void setClassDelegate(ClassDelegate classDelegate) {
            _databaseClassDelegate.set(classDelegate);
        }
    }

    void addNetworkTopologyListener(NetworkTopologyListener networkTopologyListener);

    void addServiceListener(String str, ServiceListener serviceListener);

    void addServiceTypeListener(ServiceTypeListener serviceTypeListener) throws IOException;

    String[] getHostNames();

    InetAddress[] getInterfaces() throws IOException;

    String[] getNames();

    ServiceInfo[] getServiceInfos(String str, String str2);

    ServiceInfo[] getServiceInfos(String str, String str2, long j2);

    ServiceInfo[] getServiceInfos(String str, String str2, boolean z2);

    ServiceInfo[] getServiceInfos(String str, String str2, boolean z2, long j2);

    ServiceInfo[] list(String str);

    ServiceInfo[] list(String str, long j2);

    Map<String, ServiceInfo[]> listBySubtype(String str);

    Map<String, ServiceInfo[]> listBySubtype(String str, long j2);

    NetworkTopologyListener[] networkListeners();

    void registerService(ServiceInfo serviceInfo) throws IOException;

    void registerServiceType(String str);

    void removeNetworkTopologyListener(NetworkTopologyListener networkTopologyListener);

    void removeServiceListener(String str, ServiceListener serviceListener);

    void removeServiceTypeListener(ServiceTypeListener serviceTypeListener);

    void requestServiceInfo(String str, String str2);

    void requestServiceInfo(String str, String str2, long j2);

    void requestServiceInfo(String str, String str2, boolean z2);

    void requestServiceInfo(String str, String str2, boolean z2, long j2);

    void unregisterAllServices();

    void unregisterService(ServiceInfo serviceInfo);
}
