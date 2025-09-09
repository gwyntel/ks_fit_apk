package javax.jmdns.impl;

import java.util.EventListener;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import javax.jmdns.ServiceTypeListener;

/* loaded from: classes4.dex */
public class ListenerStatus<T extends EventListener> {
    public static final boolean ASYNCHONEOUS = false;
    public static final boolean SYNCHONEOUS = true;
    private final T _listener;
    private final boolean _synch;

    public static class ServiceListenerStatus extends ListenerStatus<ServiceListener> {
        private static Logger logger = Logger.getLogger(ServiceListenerStatus.class.getName());
        private final ConcurrentMap<String, ServiceInfo> _addedServices;

        public ServiceListenerStatus(ServiceListener serviceListener, boolean z2) {
            super(serviceListener, z2);
            this._addedServices = new ConcurrentHashMap(32);
        }

        private static final boolean _sameInfo(ServiceInfo serviceInfo, ServiceInfo serviceInfo2) {
            if (serviceInfo == null || serviceInfo2 == null || !serviceInfo.equals(serviceInfo2)) {
                return false;
            }
            byte[] textBytes = serviceInfo.getTextBytes();
            byte[] textBytes2 = serviceInfo2.getTextBytes();
            if (textBytes.length != textBytes2.length) {
                return false;
            }
            for (int i2 = 0; i2 < textBytes.length; i2++) {
                if (textBytes[i2] != textBytes2[i2]) {
                    return false;
                }
            }
            return true;
        }

        void a(ServiceEvent serviceEvent) {
            if (this._addedServices.putIfAbsent(serviceEvent.getName() + "." + serviceEvent.getType(), serviceEvent.getInfo().clone()) != null) {
                logger.finer("Service Added called for a service already added: " + serviceEvent);
                return;
            }
            getListener().serviceAdded(serviceEvent);
            ServiceInfo info2 = serviceEvent.getInfo();
            if (info2 == null || !info2.hasData()) {
                return;
            }
            getListener().serviceResolved(serviceEvent);
        }

        void b(ServiceEvent serviceEvent) {
            String str = serviceEvent.getName() + "." + serviceEvent.getType();
            ConcurrentMap<String, ServiceInfo> concurrentMap = this._addedServices;
            if (concurrentMap.remove(str, concurrentMap.get(str))) {
                getListener().serviceRemoved(serviceEvent);
                return;
            }
            logger.finer("Service Removed called for a service already removed: " + serviceEvent);
        }

        synchronized void c(ServiceEvent serviceEvent) {
            try {
                ServiceInfo info2 = serviceEvent.getInfo();
                if (info2 == null || !info2.hasData()) {
                    logger.warning("Service Resolved called for an unresolved event: " + serviceEvent);
                } else {
                    String str = serviceEvent.getName() + "." + serviceEvent.getType();
                    ServiceInfo serviceInfo = this._addedServices.get(str);
                    if (_sameInfo(info2, serviceInfo)) {
                        logger.finer("Service Resolved called for a service already resolved: " + serviceEvent);
                    } else if (serviceInfo == null) {
                        if (this._addedServices.putIfAbsent(str, info2.clone()) == null) {
                            getListener().serviceResolved(serviceEvent);
                        }
                    } else if (this._addedServices.replace(str, serviceInfo, info2.clone())) {
                        getListener().serviceResolved(serviceEvent);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }

        @Override // javax.jmdns.impl.ListenerStatus
        public String toString() {
            StringBuilder sb = new StringBuilder(2048);
            sb.append("[Status for ");
            sb.append(getListener().toString());
            if (this._addedServices.isEmpty()) {
                sb.append(" no type event ");
            } else {
                sb.append(" (");
                Iterator<String> it = this._addedServices.keySet().iterator();
                while (it.hasNext()) {
                    sb.append(it.next() + ", ");
                }
                sb.append(") ");
            }
            sb.append("]");
            return sb.toString();
        }
    }

    public static class ServiceTypeListenerStatus extends ListenerStatus<ServiceTypeListener> {
        private static Logger logger = Logger.getLogger(ServiceTypeListenerStatus.class.getName());
        private final ConcurrentMap<String, String> _addedTypes;

        public ServiceTypeListenerStatus(ServiceTypeListener serviceTypeListener, boolean z2) {
            super(serviceTypeListener, z2);
            this._addedTypes = new ConcurrentHashMap(32);
        }

        void a(ServiceEvent serviceEvent) {
            if (this._addedTypes.putIfAbsent(serviceEvent.getType(), serviceEvent.getType()) == null) {
                getListener().serviceTypeAdded(serviceEvent);
                return;
            }
            logger.finest("Service Type Added called for a service type already added: " + serviceEvent);
        }

        void b(ServiceEvent serviceEvent) {
            if (this._addedTypes.putIfAbsent(serviceEvent.getType(), serviceEvent.getType()) == null) {
                getListener().subTypeForServiceTypeAdded(serviceEvent);
                return;
            }
            logger.finest("Service Sub Type Added called for a service sub type already added: " + serviceEvent);
        }

        @Override // javax.jmdns.impl.ListenerStatus
        public String toString() {
            StringBuilder sb = new StringBuilder(2048);
            sb.append("[Status for ");
            sb.append(getListener().toString());
            if (this._addedTypes.isEmpty()) {
                sb.append(" no type event ");
            } else {
                sb.append(" (");
                Iterator<String> it = this._addedTypes.keySet().iterator();
                while (it.hasNext()) {
                    sb.append(it.next() + ", ");
                }
                sb.append(") ");
            }
            sb.append("]");
            return sb.toString();
        }
    }

    public ListenerStatus(T t2, boolean z2) {
        this._listener = t2;
        this._synch = z2;
    }

    public boolean equals(Object obj) {
        return (obj instanceof ListenerStatus) && getListener().equals(((ListenerStatus) obj).getListener());
    }

    public T getListener() {
        return this._listener;
    }

    public int hashCode() {
        return getListener().hashCode();
    }

    public boolean isSynchronous() {
        return this._synch;
    }

    public String toString() {
        return "[Status for " + getListener().toString() + "]";
    }
}
