package anet.channel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes2.dex */
class e {

    /* renamed from: a, reason: collision with root package name */
    private final Map<SessionRequest, List<Session>> f6747a = new HashMap();

    /* renamed from: b, reason: collision with root package name */
    private final ReentrantReadWriteLock f6748b;

    /* renamed from: c, reason: collision with root package name */
    private final ReentrantReadWriteLock.ReadLock f6749c;

    /* renamed from: d, reason: collision with root package name */
    private final ReentrantReadWriteLock.WriteLock f6750d;

    e() {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        this.f6748b = reentrantReadWriteLock;
        this.f6749c = reentrantReadWriteLock.readLock();
        this.f6750d = reentrantReadWriteLock.writeLock();
    }

    public void a(SessionRequest sessionRequest, Session session) {
        if (sessionRequest == null || sessionRequest.a() == null || session == null) {
            return;
        }
        this.f6750d.lock();
        try {
            List<Session> arrayList = this.f6747a.get(sessionRequest);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                this.f6747a.put(sessionRequest, arrayList);
            }
            if (arrayList.indexOf(session) != -1) {
                this.f6750d.unlock();
                return;
            }
            arrayList.add(session);
            Collections.sort(arrayList);
            this.f6750d.unlock();
        } catch (Throwable th) {
            this.f6750d.unlock();
            throw th;
        }
    }

    public void b(SessionRequest sessionRequest, Session session) {
        this.f6750d.lock();
        try {
            List<Session> list = this.f6747a.get(sessionRequest);
            if (list == null) {
                return;
            }
            list.remove(session);
            if (list.size() == 0) {
                this.f6747a.remove(sessionRequest);
            }
        } finally {
            this.f6750d.unlock();
        }
    }

    public boolean c(SessionRequest sessionRequest, Session session) {
        this.f6749c.lock();
        try {
            List<Session> list = this.f6747a.get(sessionRequest);
            boolean z2 = false;
            if (list != null) {
                if (list.indexOf(session) != -1) {
                    z2 = true;
                }
            }
            return z2;
        } finally {
            this.f6749c.unlock();
        }
    }

    public List<Session> a(SessionRequest sessionRequest) {
        this.f6749c.lock();
        try {
            List<Session> list = this.f6747a.get(sessionRequest);
            if (list != null) {
                return new ArrayList(list);
            }
            return Collections.EMPTY_LIST;
        } finally {
            this.f6749c.unlock();
        }
    }

    public Session a(SessionRequest sessionRequest, int i2) {
        this.f6749c.lock();
        try {
            List<Session> list = this.f6747a.get(sessionRequest);
            Session session = null;
            if (list != null && !list.isEmpty()) {
                for (Session session2 : list) {
                    if (session2 != null && session2.isAvailable() && (i2 == anet.channel.entity.c.f6779c || session2.f6626j.getType() == i2)) {
                        session = session2;
                        break;
                    }
                }
                return session;
            }
            return null;
        } finally {
            this.f6749c.unlock();
        }
    }

    public List<SessionRequest> a() {
        List<SessionRequest> list = Collections.EMPTY_LIST;
        this.f6749c.lock();
        try {
            return this.f6747a.isEmpty() ? list : new ArrayList(this.f6747a.keySet());
        } finally {
            this.f6749c.unlock();
        }
    }
}
