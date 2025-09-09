package javax.jmdns;

import java.util.EventObject;

/* loaded from: classes4.dex */
public abstract class ServiceEvent extends EventObject implements Cloneable {
    private static final long serialVersionUID = -8558445644541006271L;

    public ServiceEvent(Object obj) {
        super(obj);
    }

    public abstract JmDNS getDNS();

    public abstract ServiceInfo getInfo();

    public abstract String getName();

    public abstract String getType();

    @Override // 
    public ServiceEvent clone() {
        try {
            return (ServiceEvent) super.clone();
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }
}
