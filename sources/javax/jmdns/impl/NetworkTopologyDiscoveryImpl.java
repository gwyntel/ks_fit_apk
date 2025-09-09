package javax.jmdns.impl;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.NetworkTopologyDiscovery;

/* loaded from: classes4.dex */
public class NetworkTopologyDiscoveryImpl implements NetworkTopologyDiscovery {
    private static final Logger logger = Logger.getLogger(NetworkTopologyDiscoveryImpl.class.getName());
    private final Method _isUp;
    private final Method _supportsMulticast;

    public NetworkTopologyDiscoveryImpl() throws NoSuchMethodException, SecurityException {
        Method method;
        Method method2 = null;
        try {
            method = NetworkInterface.class.getMethod("isUp", null);
        } catch (Exception unused) {
            method = null;
        }
        this._isUp = method;
        try {
            method2 = NetworkInterface.class.getMethod("supportsMulticast", null);
        } catch (Exception unused2) {
        }
        this._supportsMulticast = method2;
    }

    @Override // javax.jmdns.NetworkTopologyDiscovery
    public InetAddress[] getInetAddresses() throws SocketException {
        HashSet hashSet = new HashSet();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterfaceNextElement.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddressNextElement = inetAddresses.nextElement();
                    Logger logger2 = logger;
                    if (logger2.isLoggable(Level.FINEST)) {
                        logger2.finest("Found NetworkInterface/InetAddress: " + networkInterfaceNextElement + " -- " + inetAddressNextElement);
                    }
                    if (useInetAddress(networkInterfaceNextElement, inetAddressNextElement)) {
                        hashSet.add(inetAddressNextElement);
                    }
                }
            }
        } catch (SocketException e2) {
            logger.warning("Error while fetching network interfaces addresses: " + e2);
        }
        return (InetAddress[]) hashSet.toArray(new InetAddress[hashSet.size()]);
    }

    @Override // javax.jmdns.NetworkTopologyDiscovery
    public void lockInetAddress(InetAddress inetAddress) {
    }

    @Override // javax.jmdns.NetworkTopologyDiscovery
    public void unlockInetAddress(InetAddress inetAddress) {
    }

    @Override // javax.jmdns.NetworkTopologyDiscovery
    public boolean useInetAddress(NetworkInterface networkInterface, InetAddress inetAddress) {
        try {
            Method method = this._isUp;
            if (method != null) {
                try {
                    if (!((Boolean) method.invoke(networkInterface, null)).booleanValue()) {
                        return false;
                    }
                } catch (Exception unused) {
                }
            }
            Method method2 = this._supportsMulticast;
            if (method2 != null) {
                try {
                    if (!((Boolean) method2.invoke(networkInterface, null)).booleanValue()) {
                        return false;
                    }
                } catch (Exception unused2) {
                }
            }
            return !inetAddress.isLoopbackAddress();
        } catch (Exception unused3) {
            return false;
        }
    }
}
