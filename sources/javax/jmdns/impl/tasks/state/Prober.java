package javax.jmdns.impl.tasks.state;

import java.io.IOException;
import java.util.Iterator;
import java.util.Timer;
import java.util.logging.Logger;
import javax.jmdns.impl.DNSOutgoing;
import javax.jmdns.impl.DNSQuestion;
import javax.jmdns.impl.DNSRecord;
import javax.jmdns.impl.JmDNSImpl;
import javax.jmdns.impl.ServiceInfoImpl;
import javax.jmdns.impl.constants.DNSRecordClass;
import javax.jmdns.impl.constants.DNSRecordType;
import javax.jmdns.impl.constants.DNSState;

/* loaded from: classes4.dex */
public class Prober extends DNSStateTask {

    /* renamed from: b, reason: collision with root package name */
    static Logger f25392b = Logger.getLogger(Prober.class.getName());

    public Prober(JmDNSImpl jmDNSImpl) {
        super(jmDNSImpl, DNSStateTask.defaultTTL());
        DNSState dNSState = DNSState.PROBING_1;
        k(dNSState);
        c(dNSState);
    }

    @Override // javax.jmdns.impl.tasks.state.DNSStateTask
    protected void b() {
        k(h().advance());
        if (h().isProbing()) {
            return;
        }
        cancel();
        getDns().startAnnouncer();
    }

    @Override // java.util.TimerTask
    public boolean cancel() {
        j();
        return super.cancel();
    }

    @Override // javax.jmdns.impl.tasks.state.DNSStateTask
    protected DNSOutgoing d(DNSOutgoing dNSOutgoing) throws IOException {
        dNSOutgoing.addQuestion(DNSQuestion.newQuestion(getDns().getLocalHost().getName(), DNSRecordType.TYPE_ANY, DNSRecordClass.CLASS_IN, false));
        Iterator<DNSRecord> it = getDns().getLocalHost().answers(false, getTTL()).iterator();
        while (it.hasNext()) {
            dNSOutgoing = addAuthoritativeAnswer(dNSOutgoing, it.next());
        }
        return dNSOutgoing;
    }

    @Override // javax.jmdns.impl.tasks.state.DNSStateTask
    protected DNSOutgoing e(ServiceInfoImpl serviceInfoImpl, DNSOutgoing dNSOutgoing) {
        String qualifiedName = serviceInfoImpl.getQualifiedName();
        DNSRecordType dNSRecordType = DNSRecordType.TYPE_ANY;
        DNSRecordClass dNSRecordClass = DNSRecordClass.CLASS_IN;
        return addAuthoritativeAnswer(addQuestion(dNSOutgoing, DNSQuestion.newQuestion(qualifiedName, dNSRecordType, dNSRecordClass, false)), new DNSRecord.Service(serviceInfoImpl.getQualifiedName(), dNSRecordClass, false, getTTL(), serviceInfoImpl.getPriority(), serviceInfoImpl.getWeight(), serviceInfoImpl.getPort(), getDns().getLocalHost().getName()));
    }

    @Override // javax.jmdns.impl.tasks.state.DNSStateTask
    protected boolean f() {
        return (getDns().isCanceling() || getDns().isCanceled()) ? false : true;
    }

    @Override // javax.jmdns.impl.tasks.state.DNSStateTask
    protected DNSOutgoing g() {
        return new DNSOutgoing(0);
    }

    @Override // javax.jmdns.impl.tasks.DNSTask
    public String getName() {
        StringBuilder sb = new StringBuilder();
        sb.append("Prober(");
        sb.append(getDns() != null ? getDns().getName() : "");
        sb.append(")");
        return sb.toString();
    }

    @Override // javax.jmdns.impl.tasks.state.DNSStateTask
    public String getTaskDescription() {
        return "probing";
    }

    @Override // javax.jmdns.impl.tasks.state.DNSStateTask
    protected void i(Throwable th) {
        getDns().recover();
    }

    @Override // javax.jmdns.impl.tasks.DNSTask
    public void start(Timer timer) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - getDns().getLastThrottleIncrement() < 5000) {
            getDns().setThrottle(getDns().getThrottle() + 1);
        } else {
            getDns().setThrottle(1);
        }
        getDns().setLastThrottleIncrement(jCurrentTimeMillis);
        if (getDns().isAnnounced() && getDns().getThrottle() < 10) {
            timer.schedule(this, JmDNSImpl.getRandom().nextInt(251), 250L);
        } else {
            if (getDns().isCanceling() || getDns().isCanceled()) {
                return;
            }
            timer.schedule(this, 1000L, 1000L);
        }
    }

    @Override // javax.jmdns.impl.tasks.DNSTask
    public String toString() {
        return super.toString() + " state: " + h();
    }
}
