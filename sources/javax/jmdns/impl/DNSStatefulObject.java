package javax.jmdns.impl;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.impl.constants.DNSState;
import javax.jmdns.impl.tasks.DNSTask;

/* loaded from: classes4.dex */
public interface DNSStatefulObject {

    public static final class DNSStatefulObjectSemaphore {
        private static Logger logger = Logger.getLogger(DNSStatefulObjectSemaphore.class.getName());
        private final String _name;
        private final ConcurrentMap<Thread, Semaphore> _semaphores = new ConcurrentHashMap(50);

        public DNSStatefulObjectSemaphore(String str) {
            this._name = str;
        }

        public void signalEvent() {
            Collection<Semaphore> collectionValues = this._semaphores.values();
            for (Semaphore semaphore : collectionValues) {
                semaphore.release();
                collectionValues.remove(semaphore);
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(1000);
            sb.append("Semaphore: ");
            sb.append(this._name);
            if (this._semaphores.size() == 0) {
                sb.append(" no semaphores.");
            } else {
                sb.append(" semaphores:\n");
                for (Thread thread : this._semaphores.keySet()) {
                    sb.append("\tThread: ");
                    sb.append(thread.getName());
                    sb.append(' ');
                    sb.append(this._semaphores.get(thread));
                    sb.append('\n');
                }
            }
            return sb.toString();
        }

        public void waitForEvent(long j2) throws InterruptedException {
            Thread threadCurrentThread = Thread.currentThread();
            if (this._semaphores.get(threadCurrentThread) == null) {
                Semaphore semaphore = new Semaphore(1, true);
                semaphore.drainPermits();
                this._semaphores.putIfAbsent(threadCurrentThread, semaphore);
            }
            try {
                this._semaphores.get(threadCurrentThread).tryAcquire(j2, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e2) {
                logger.log(Level.FINER, "Exception ", (Throwable) e2);
            }
        }
    }

    public static class DefaultImplementation extends ReentrantLock implements DNSStatefulObject {
        private static Logger logger = Logger.getLogger(DefaultImplementation.class.getName());
        private static final long serialVersionUID = -3264781576883412227L;
        private volatile JmDNSImpl _dns = null;
        protected volatile DNSTask _task = null;
        protected volatile DNSState _state = DNSState.PROBING_1;
        private final DNSStatefulObjectSemaphore _announcing = new DNSStatefulObjectSemaphore("Announce");
        private final DNSStatefulObjectSemaphore _canceling = new DNSStatefulObjectSemaphore("Cancel");

        private boolean willCancel() {
            return this._state.isCanceled() || this._state.isCanceling();
        }

        private boolean willClose() {
            return this._state.isClosed() || this._state.isClosing();
        }

        @Override // javax.jmdns.impl.DNSStatefulObject
        public boolean advanceState(DNSTask dNSTask) {
            if (this._task != dNSTask) {
                return true;
            }
            lock();
            try {
                if (this._task == dNSTask) {
                    setState(this._state.advance());
                } else {
                    logger.warning("Trying to advance state whhen not the owner. owner: " + this._task + " perpetrator: " + dNSTask);
                }
                unlock();
                return true;
            } catch (Throwable th) {
                unlock();
                throw th;
            }
        }

        @Override // javax.jmdns.impl.DNSStatefulObject
        public void associateWithTask(DNSTask dNSTask, DNSState dNSState) {
            if (this._task == null && this._state == dNSState) {
                lock();
                try {
                    if (this._task == null && this._state == dNSState) {
                        setTask(dNSTask);
                    }
                } finally {
                    unlock();
                }
            }
        }

        @Override // javax.jmdns.impl.DNSStatefulObject
        public boolean cancelState() {
            boolean z2 = false;
            if (!willCancel()) {
                lock();
                try {
                    if (!willCancel()) {
                        setState(DNSState.CANCELING_1);
                        setTask(null);
                        z2 = true;
                    }
                } finally {
                    unlock();
                }
            }
            return z2;
        }

        @Override // javax.jmdns.impl.DNSStatefulObject
        public boolean closeState() {
            boolean z2 = false;
            if (!willClose()) {
                lock();
                try {
                    if (!willClose()) {
                        setState(DNSState.CLOSING);
                        setTask(null);
                        z2 = true;
                    }
                } finally {
                    unlock();
                }
            }
            return z2;
        }

        @Override // javax.jmdns.impl.DNSStatefulObject
        public JmDNSImpl getDns() {
            return this._dns;
        }

        @Override // javax.jmdns.impl.DNSStatefulObject
        public boolean isAnnounced() {
            return this._state.isAnnounced();
        }

        @Override // javax.jmdns.impl.DNSStatefulObject
        public boolean isAnnouncing() {
            return this._state.isAnnouncing();
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x000f  */
        @Override // javax.jmdns.impl.DNSStatefulObject
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean isAssociatedWithTask(javax.jmdns.impl.tasks.DNSTask r2, javax.jmdns.impl.constants.DNSState r3) {
            /*
                r1 = this;
                r1.lock()
                javax.jmdns.impl.tasks.DNSTask r0 = r1._task     // Catch: java.lang.Throwable -> Ld
                if (r0 != r2) goto Lf
                javax.jmdns.impl.constants.DNSState r2 = r1._state     // Catch: java.lang.Throwable -> Ld
                if (r2 != r3) goto Lf
                r2 = 1
                goto L10
            Ld:
                r2 = move-exception
                goto L14
            Lf:
                r2 = 0
            L10:
                r1.unlock()
                return r2
            L14:
                r1.unlock()
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: javax.jmdns.impl.DNSStatefulObject.DefaultImplementation.isAssociatedWithTask(javax.jmdns.impl.tasks.DNSTask, javax.jmdns.impl.constants.DNSState):boolean");
        }

        @Override // javax.jmdns.impl.DNSStatefulObject
        public boolean isCanceled() {
            return this._state.isCanceled();
        }

        @Override // javax.jmdns.impl.DNSStatefulObject
        public boolean isCanceling() {
            return this._state.isCanceling();
        }

        @Override // javax.jmdns.impl.DNSStatefulObject
        public boolean isClosed() {
            return this._state.isClosed();
        }

        @Override // javax.jmdns.impl.DNSStatefulObject
        public boolean isClosing() {
            return this._state.isClosing();
        }

        @Override // javax.jmdns.impl.DNSStatefulObject
        public boolean isProbing() {
            return this._state.isProbing();
        }

        @Override // javax.jmdns.impl.DNSStatefulObject
        public boolean recoverState() {
            lock();
            try {
                setState(DNSState.PROBING_1);
                setTask(null);
                unlock();
                return false;
            } catch (Throwable th) {
                unlock();
                throw th;
            }
        }

        @Override // javax.jmdns.impl.DNSStatefulObject
        public void removeAssociationWithTask(DNSTask dNSTask) {
            if (this._task == dNSTask) {
                lock();
                try {
                    if (this._task == dNSTask) {
                        setTask(null);
                    }
                } finally {
                    unlock();
                }
            }
        }

        @Override // javax.jmdns.impl.DNSStatefulObject
        public boolean revertState() {
            if (willCancel()) {
                return true;
            }
            lock();
            try {
                if (!willCancel()) {
                    setState(this._state.revert());
                    setTask(null);
                }
                return true;
            } finally {
                unlock();
            }
        }

        protected void setDns(JmDNSImpl jmDNSImpl) {
            this._dns = jmDNSImpl;
        }

        protected void setState(DNSState dNSState) {
            lock();
            try {
                this._state = dNSState;
                if (isAnnounced()) {
                    this._announcing.signalEvent();
                }
                if (isCanceled()) {
                    this._canceling.signalEvent();
                    this._announcing.signalEvent();
                }
                unlock();
            } catch (Throwable th) {
                unlock();
                throw th;
            }
        }

        protected void setTask(DNSTask dNSTask) {
            this._task = dNSTask;
        }

        @Override // java.util.concurrent.locks.ReentrantLock
        public String toString() {
            String str;
            StringBuilder sb = new StringBuilder();
            if (this._dns != null) {
                str = "DNS: " + this._dns.getName();
            } else {
                str = "NO DNS";
            }
            sb.append(str);
            sb.append(" state: ");
            sb.append(this._state);
            sb.append(" task: ");
            sb.append(this._task);
            return sb.toString();
        }

        @Override // javax.jmdns.impl.DNSStatefulObject
        public boolean waitForAnnounced(long j2) throws InterruptedException {
            if (!isAnnounced() && !willCancel()) {
                this._announcing.waitForEvent(j2);
            }
            if (!isAnnounced()) {
                if (willCancel() || willClose()) {
                    logger.fine("Wait for announced cancelled: " + this);
                } else {
                    logger.warning("Wait for announced timed out: " + this);
                }
            }
            return isAnnounced();
        }

        @Override // javax.jmdns.impl.DNSStatefulObject
        public boolean waitForCanceled(long j2) throws InterruptedException {
            if (!isCanceled()) {
                this._canceling.waitForEvent(j2);
            }
            if (!isCanceled() && !willClose()) {
                logger.warning("Wait for canceled timed out: " + this);
            }
            return isCanceled();
        }
    }

    boolean advanceState(DNSTask dNSTask);

    void associateWithTask(DNSTask dNSTask, DNSState dNSState);

    boolean cancelState();

    boolean closeState();

    JmDNSImpl getDns();

    boolean isAnnounced();

    boolean isAnnouncing();

    boolean isAssociatedWithTask(DNSTask dNSTask, DNSState dNSState);

    boolean isCanceled();

    boolean isCanceling();

    boolean isClosed();

    boolean isClosing();

    boolean isProbing();

    boolean recoverState();

    void removeAssociationWithTask(DNSTask dNSTask);

    boolean revertState();

    boolean waitForAnnounced(long j2);

    boolean waitForCanceled(long j2);
}
