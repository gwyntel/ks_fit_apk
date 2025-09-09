package javax.jmdns.impl.tasks.state;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.ServiceInfo;
import javax.jmdns.impl.DNSOutgoing;
import javax.jmdns.impl.DNSStatefulObject;
import javax.jmdns.impl.JmDNSImpl;
import javax.jmdns.impl.ServiceInfoImpl;
import javax.jmdns.impl.constants.DNSState;
import javax.jmdns.impl.tasks.DNSTask;

/* loaded from: classes4.dex */
public abstract class DNSStateTask extends DNSTask {
    private DNSState _taskState;
    private final int _ttl;

    /* renamed from: a, reason: collision with root package name */
    static Logger f25391a = Logger.getLogger(DNSStateTask.class.getName());
    private static int _defaultTTL = 3600;

    public DNSStateTask(JmDNSImpl jmDNSImpl, int i2) {
        super(jmDNSImpl);
        this._taskState = null;
        this._ttl = i2;
    }

    public static int defaultTTL() {
        return _defaultTTL;
    }

    public static void setDefaultTTL(int i2) {
        _defaultTTL = i2;
    }

    protected void a(List list) {
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                DNSStatefulObject dNSStatefulObject = (DNSStatefulObject) it.next();
                synchronized (dNSStatefulObject) {
                    dNSStatefulObject.advanceState(this);
                }
            }
        }
    }

    protected abstract void b();

    protected void c(DNSState dNSState) {
        synchronized (getDns()) {
            getDns().associateWithTask(this, dNSState);
        }
        Iterator<ServiceInfo> it = getDns().getServices().values().iterator();
        while (it.hasNext()) {
            ((ServiceInfoImpl) it.next()).associateWithTask(this, dNSState);
        }
    }

    protected abstract DNSOutgoing d(DNSOutgoing dNSOutgoing);

    protected abstract DNSOutgoing e(ServiceInfoImpl serviceInfoImpl, DNSOutgoing dNSOutgoing);

    protected abstract boolean f();

    protected abstract DNSOutgoing g();

    public int getTTL() {
        return this._ttl;
    }

    public abstract String getTaskDescription();

    protected DNSState h() {
        return this._taskState;
    }

    protected abstract void i(Throwable th);

    protected void j() {
        synchronized (getDns()) {
            getDns().removeAssociationWithTask(this);
        }
        Iterator<ServiceInfo> it = getDns().getServices().values().iterator();
        while (it.hasNext()) {
            ((ServiceInfoImpl) it.next()).removeAssociationWithTask(this);
        }
    }

    protected void k(DNSState dNSState) {
        this._taskState = dNSState;
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public void run() {
        DNSOutgoing dNSOutgoingG = g();
        try {
        } catch (Throwable th) {
            f25391a.log(Level.WARNING, getName() + ".run() exception ", th);
            i(th);
        }
        if (!f()) {
            cancel();
            return;
        }
        ArrayList arrayList = new ArrayList();
        synchronized (getDns()) {
            try {
                if (getDns().isAssociatedWithTask(this, h())) {
                    f25391a.finer(getName() + ".run() JmDNS " + getTaskDescription() + " " + getDns().getName());
                    arrayList.add(getDns());
                    dNSOutgoingG = d(dNSOutgoingG);
                }
            } finally {
            }
        }
        Iterator<ServiceInfo> it = getDns().getServices().values().iterator();
        while (it.hasNext()) {
            ServiceInfoImpl serviceInfoImpl = (ServiceInfoImpl) it.next();
            synchronized (serviceInfoImpl) {
                try {
                    if (serviceInfoImpl.isAssociatedWithTask(this, h())) {
                        f25391a.fine(getName() + ".run() JmDNS " + getTaskDescription() + " " + serviceInfoImpl.getQualifiedName());
                        arrayList.add(serviceInfoImpl);
                        dNSOutgoingG = e(serviceInfoImpl, dNSOutgoingG);
                    }
                } finally {
                }
            }
        }
        if (dNSOutgoingG.isEmpty()) {
            a(arrayList);
            cancel();
            return;
        }
        f25391a.finer(getName() + ".run() JmDNS " + getTaskDescription() + " #" + h());
        getDns().send(dNSOutgoingG);
        a(arrayList);
        b();
    }
}
