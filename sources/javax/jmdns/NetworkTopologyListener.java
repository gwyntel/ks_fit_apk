package javax.jmdns;

import java.util.EventListener;

/* loaded from: classes4.dex */
public interface NetworkTopologyListener extends EventListener {
    void inetAddressAdded(NetworkTopologyEvent networkTopologyEvent);

    void inetAddressRemoved(NetworkTopologyEvent networkTopologyEvent);
}
