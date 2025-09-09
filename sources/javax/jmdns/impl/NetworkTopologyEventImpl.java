package javax.jmdns.impl;

import java.net.InetAddress;
import javax.jmdns.JmDNS;
import javax.jmdns.NetworkTopologyEvent;
import javax.jmdns.NetworkTopologyListener;

/* loaded from: classes4.dex */
public class NetworkTopologyEventImpl extends NetworkTopologyEvent implements Cloneable {
    private static final long serialVersionUID = 1445606146153550463L;
    private final InetAddress _inetAddress;

    public NetworkTopologyEventImpl(JmDNS jmDNS, InetAddress inetAddress) {
        super(jmDNS);
        this._inetAddress = inetAddress;
    }

    @Override // javax.jmdns.NetworkTopologyEvent
    public JmDNS getDNS() {
        if (getSource() instanceof JmDNS) {
            return (JmDNS) getSource();
        }
        return null;
    }

    @Override // javax.jmdns.NetworkTopologyEvent
    public InetAddress getInetAddress() {
        return this._inetAddress;
    }

    @Override // java.util.EventObject
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[" + getClass().getSimpleName() + "@" + System.identityHashCode(this) + " ");
        sb.append("\n\tinetAddress: '");
        sb.append(getInetAddress());
        sb.append("']");
        return sb.toString();
    }

    public NetworkTopologyEventImpl clone() throws CloneNotSupportedException {
        return new NetworkTopologyEventImpl(getDNS(), getInetAddress());
    }

    NetworkTopologyEventImpl(NetworkTopologyListener networkTopologyListener, InetAddress inetAddress) {
        super(networkTopologyListener);
        this._inetAddress = inetAddress;
    }
}
