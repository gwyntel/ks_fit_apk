package javax.jmdns;

import java.util.EventListener;

/* loaded from: classes4.dex */
public interface ServiceListener extends EventListener {
    void serviceAdded(ServiceEvent serviceEvent);

    void serviceRemoved(ServiceEvent serviceEvent);

    void serviceResolved(ServiceEvent serviceEvent);
}
