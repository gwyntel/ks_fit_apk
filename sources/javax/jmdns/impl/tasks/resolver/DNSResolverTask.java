package javax.jmdns.impl.tasks.resolver;

import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.impl.DNSOutgoing;
import javax.jmdns.impl.JmDNSImpl;
import javax.jmdns.impl.tasks.DNSTask;

/* loaded from: classes4.dex */
public abstract class DNSResolverTask extends DNSTask {
    private static Logger logger = Logger.getLogger(DNSResolverTask.class.getName());

    /* renamed from: a, reason: collision with root package name */
    protected int f25388a;

    public DNSResolverTask(JmDNSImpl jmDNSImpl) {
        super(jmDNSImpl);
        this.f25388a = 0;
    }

    protected abstract DNSOutgoing a(DNSOutgoing dNSOutgoing);

    protected abstract DNSOutgoing b(DNSOutgoing dNSOutgoing);

    protected abstract String c();

    @Override // java.util.TimerTask, java.lang.Runnable
    public void run() {
        try {
            if (!getDns().isCanceling() && !getDns().isCanceled()) {
                int i2 = this.f25388a;
                this.f25388a = i2 + 1;
                if (i2 >= 3) {
                    cancel();
                    return;
                }
                if (logger.isLoggable(Level.FINER)) {
                    logger.finer(getName() + ".run() JmDNS " + c());
                }
                DNSOutgoing dNSOutgoingB = b(new DNSOutgoing(0));
                if (getDns().isAnnounced()) {
                    dNSOutgoingB = a(dNSOutgoingB);
                }
                if (dNSOutgoingB.isEmpty()) {
                    return;
                }
                getDns().send(dNSOutgoingB);
                return;
            }
            cancel();
        } catch (Throwable th) {
            logger.log(Level.WARNING, getName() + ".run() exception ", th);
            getDns().recover();
        }
    }

    @Override // javax.jmdns.impl.tasks.DNSTask
    public void start(Timer timer) {
        if (getDns().isCanceling() || getDns().isCanceled()) {
            return;
        }
        timer.schedule(this, 225L, 225L);
    }

    @Override // javax.jmdns.impl.tasks.DNSTask
    public String toString() {
        return super.toString() + " count: " + this.f25388a;
    }
}
