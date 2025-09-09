package javax.jmdns;

import java.net.InetAddress;
import java.util.EventObject;

/* loaded from: classes4.dex */
public abstract class NetworkTopologyEvent extends EventObject {
    private static final long serialVersionUID = -8630033521752540987L;

    protected NetworkTopologyEvent(Object obj) {
        super(obj);
    }

    public abstract JmDNS getDNS();

    public abstract InetAddress getInetAddress();
}
