package javax.jmdns.impl.tasks.state;

import java.io.IOException;
import java.util.Iterator;
import java.util.Timer;
import java.util.logging.Logger;
import javax.jmdns.impl.DNSIncoming;
import javax.jmdns.impl.DNSOutgoing;
import javax.jmdns.impl.DNSRecord;
import javax.jmdns.impl.JmDNSImpl;
import javax.jmdns.impl.ServiceInfoImpl;
import javax.jmdns.impl.constants.DNSState;

/* loaded from: classes4.dex */
public class Renewer extends DNSStateTask {

    /* renamed from: b, reason: collision with root package name */
    static Logger f25393b = Logger.getLogger(Renewer.class.getName());

    public Renewer(JmDNSImpl jmDNSImpl) {
        super(jmDNSImpl, DNSStateTask.defaultTTL());
        DNSState dNSState = DNSState.ANNOUNCED;
        k(dNSState);
        c(dNSState);
    }

    @Override // javax.jmdns.impl.tasks.state.DNSStateTask
    protected void b() {
        k(h().advance());
        if (h().isAnnounced()) {
            return;
        }
        cancel();
    }

    @Override // java.util.TimerTask
    public boolean cancel() {
        j();
        return super.cancel();
    }

    @Override // javax.jmdns.impl.tasks.state.DNSStateTask
    protected DNSOutgoing d(DNSOutgoing dNSOutgoing) throws IOException {
        Iterator<DNSRecord> it = getDns().getLocalHost().answers(true, getTTL()).iterator();
        while (it.hasNext()) {
            dNSOutgoing = addAnswer(dNSOutgoing, (DNSIncoming) null, it.next());
        }
        return dNSOutgoing;
    }

    @Override // javax.jmdns.impl.tasks.state.DNSStateTask
    protected DNSOutgoing e(ServiceInfoImpl serviceInfoImpl, DNSOutgoing dNSOutgoing) throws IOException {
        Iterator<DNSRecord> it = serviceInfoImpl.answers(true, getTTL(), getDns().getLocalHost()).iterator();
        while (it.hasNext()) {
            dNSOutgoing = addAnswer(dNSOutgoing, (DNSIncoming) null, it.next());
        }
        return dNSOutgoing;
    }

    @Override // javax.jmdns.impl.tasks.state.DNSStateTask
    protected boolean f() {
        return (getDns().isCanceling() || getDns().isCanceled()) ? false : true;
    }

    @Override // javax.jmdns.impl.tasks.state.DNSStateTask
    protected DNSOutgoing g() {
        return new DNSOutgoing(33792);
    }

    @Override // javax.jmdns.impl.tasks.DNSTask
    public String getName() {
        StringBuilder sb = new StringBuilder();
        sb.append("Renewer(");
        sb.append(getDns() != null ? getDns().getName() : "");
        sb.append(")");
        return sb.toString();
    }

    @Override // javax.jmdns.impl.tasks.state.DNSStateTask
    public String getTaskDescription() {
        return "renewing";
    }

    @Override // javax.jmdns.impl.tasks.state.DNSStateTask
    protected void i(Throwable th) {
        getDns().recover();
    }

    @Override // javax.jmdns.impl.tasks.DNSTask
    public void start(Timer timer) {
        if (getDns().isCanceling() || getDns().isCanceled()) {
            return;
        }
        timer.schedule(this, 1800000L, 1800000L);
    }

    @Override // javax.jmdns.impl.tasks.DNSTask
    public String toString() {
        return super.toString() + " state: " + h();
    }
}
