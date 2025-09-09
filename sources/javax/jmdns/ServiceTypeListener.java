package javax.jmdns;

import java.util.EventListener;

/* loaded from: classes4.dex */
public interface ServiceTypeListener extends EventListener {
    void serviceTypeAdded(ServiceEvent serviceEvent);

    void subTypeForServiceTypeAdded(ServiceEvent serviceEvent);
}
