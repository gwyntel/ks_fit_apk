package javax.jmdns.impl.tasks;

import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.impl.JmDNSImpl;

/* loaded from: classes4.dex */
public class RecordReaper extends DNSTask {

    /* renamed from: a, reason: collision with root package name */
    static Logger f25386a = Logger.getLogger(RecordReaper.class.getName());

    public RecordReaper(JmDNSImpl jmDNSImpl) {
        super(jmDNSImpl);
    }

    @Override // javax.jmdns.impl.tasks.DNSTask
    public String getName() {
        StringBuilder sb = new StringBuilder();
        sb.append("RecordReaper(");
        sb.append(getDns() != null ? getDns().getName() : "");
        sb.append(")");
        return sb.toString();
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public void run() {
        if (getDns().isCanceling() || getDns().isCanceled()) {
            return;
        }
        if (f25386a.isLoggable(Level.FINEST)) {
            f25386a.finest(getName() + ".run() JmDNS reaping cache");
        }
        getDns().cleanCache();
    }

    @Override // javax.jmdns.impl.tasks.DNSTask
    public void start(Timer timer) {
        if (getDns().isCanceling() || getDns().isCanceled()) {
            return;
        }
        timer.schedule(this, 10000L, 10000L);
    }
}
