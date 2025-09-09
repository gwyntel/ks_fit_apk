package javax.jmdns.impl.tasks;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.impl.DNSIncoming;
import javax.jmdns.impl.DNSOutgoing;
import javax.jmdns.impl.DNSQuestion;
import javax.jmdns.impl.DNSRecord;
import javax.jmdns.impl.JmDNSImpl;
import javax.jmdns.impl.constants.DNSConstants;

/* loaded from: classes4.dex */
public class Responder extends DNSTask {

    /* renamed from: a, reason: collision with root package name */
    static Logger f25387a = Logger.getLogger(Responder.class.getName());
    private final DNSIncoming _in;
    private final boolean _unicast;

    public Responder(JmDNSImpl jmDNSImpl, DNSIncoming dNSIncoming, int i2) {
        super(jmDNSImpl);
        this._in = dNSIncoming;
        this._unicast = i2 != DNSConstants.MDNS_PORT;
    }

    @Override // javax.jmdns.impl.tasks.DNSTask
    public String getName() {
        StringBuilder sb = new StringBuilder();
        sb.append("Responder(");
        sb.append(getDns() != null ? getDns().getName() : "");
        sb.append(")");
        return sb.toString();
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public void run() throws IOException {
        getDns().respondToQuery(this._in);
        HashSet<DNSQuestion> hashSet = new HashSet();
        Set<DNSRecord> hashSet2 = new HashSet<>();
        if (getDns().isAnnounced()) {
            try {
                for (DNSQuestion dNSQuestion : this._in.getQuestions()) {
                    if (f25387a.isLoggable(Level.FINER)) {
                        f25387a.finer(getName() + "run() JmDNS responding to: " + dNSQuestion);
                    }
                    if (this._unicast) {
                        hashSet.add(dNSQuestion);
                    }
                    dNSQuestion.addAnswers(getDns(), hashSet2);
                }
                long jCurrentTimeMillis = System.currentTimeMillis();
                for (DNSRecord dNSRecord : this._in.getAnswers()) {
                    if (dNSRecord.isStale(jCurrentTimeMillis)) {
                        hashSet2.remove(dNSRecord);
                        if (f25387a.isLoggable(Level.FINER)) {
                            f25387a.finer(getName() + "JmDNS Responder Known Answer Removed");
                        }
                    }
                }
                if (hashSet2.isEmpty()) {
                    return;
                }
                if (f25387a.isLoggable(Level.FINER)) {
                    f25387a.finer(getName() + "run() JmDNS responding");
                }
                DNSOutgoing dNSOutgoing = new DNSOutgoing(33792, !this._unicast, this._in.getSenderUDPPayload());
                dNSOutgoing.setId(this._in.getId());
                for (DNSQuestion dNSQuestion2 : hashSet) {
                    if (dNSQuestion2 != null) {
                        dNSOutgoing = addQuestion(dNSOutgoing, dNSQuestion2);
                    }
                }
                for (DNSRecord dNSRecord2 : hashSet2) {
                    if (dNSRecord2 != null) {
                        dNSOutgoing = addAnswer(dNSOutgoing, this._in, dNSRecord2);
                    }
                }
                if (dNSOutgoing.isEmpty()) {
                    return;
                }
                getDns().send(dNSOutgoing);
            } catch (Throwable th) {
                f25387a.log(Level.WARNING, getName() + "run() exception ", th);
                getDns().close();
            }
        }
    }

    @Override // javax.jmdns.impl.tasks.DNSTask
    public void start(Timer timer) {
        boolean zIAmTheOnlyOne = true;
        for (DNSQuestion dNSQuestion : this._in.getQuestions()) {
            if (f25387a.isLoggable(Level.FINEST)) {
                f25387a.finest(getName() + "start() question=" + dNSQuestion);
            }
            zIAmTheOnlyOne = dNSQuestion.iAmTheOnlyOne(getDns());
            if (!zIAmTheOnlyOne) {
                break;
            }
        }
        int iNextInt = (!zIAmTheOnlyOne || this._in.isTruncated()) ? (JmDNSImpl.getRandom().nextInt(96) + 20) - this._in.elapseSinceArrival() : 0;
        int i2 = iNextInt >= 0 ? iNextInt : 0;
        if (f25387a.isLoggable(Level.FINEST)) {
            f25387a.finest(getName() + "start() Responder chosen delay=" + i2);
        }
        if (getDns().isCanceling() || getDns().isCanceled()) {
            return;
        }
        timer.schedule(this, i2);
    }

    @Override // javax.jmdns.impl.tasks.DNSTask
    public String toString() {
        return super.toString() + " incomming: " + this._in;
    }
}
