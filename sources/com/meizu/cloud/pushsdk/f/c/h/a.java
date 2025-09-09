package com.meizu.cloud.pushsdk.f.c.h;

import com.meizu.cloud.pushsdk.e.d.i;
import com.meizu.cloud.pushsdk.f.c.a;
import com.meizu.cloud.pushsdk.f.c.f;
import com.meizu.cloud.pushsdk.f.c.g;
import com.meizu.cloud.pushsdk.f.e.d;
import com.meizu.cloud.pushsdk.f.g.e;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes4.dex */
public class a extends com.meizu.cloud.pushsdk.f.c.a {

    /* renamed from: s, reason: collision with root package name */
    private final String f19574s;

    /* renamed from: t, reason: collision with root package name */
    private d f19575t;

    /* renamed from: u, reason: collision with root package name */
    private int f19576u;

    /* renamed from: com.meizu.cloud.pushsdk.f.c.h.a$a, reason: collision with other inner class name */
    class RunnableC0156a implements Runnable {
        RunnableC0156a() {
        }

        @Override // java.lang.Runnable
        public void run() throws InterruptedException {
            if (((com.meizu.cloud.pushsdk.f.c.a) a.this).f19544r.compareAndSet(false, true)) {
                a.this.d();
            }
        }
    }

    class b implements Callable<Integer> {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ i f19578a;

        b(i iVar) {
            this.f19578a = iVar;
        }

        @Override // java.util.concurrent.Callable
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public Integer call() {
            return Integer.valueOf(a.this.a(this.f19578a));
        }
    }

    class c implements Callable<Boolean> {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Long f19580a;

        c(Long l2) {
            this.f19580a = l2;
        }

        @Override // java.util.concurrent.Callable
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public Boolean call() {
            return Boolean.valueOf(a.this.f19575t.a(this.f19580a.longValue()));
        }
    }

    public a(a.C0155a c0155a) {
        super(c0155a);
        String simpleName = a.class.getSimpleName();
        this.f19574s = simpleName;
        com.meizu.cloud.pushsdk.f.e.a aVar = new com.meizu.cloud.pushsdk.f.e.a(this.f19529c, this.f19539m);
        this.f19575t = aVar;
        if (aVar.a()) {
            return;
        }
        this.f19575t = new com.meizu.cloud.pushsdk.f.e.c(this.f19539m);
        com.meizu.cloud.pushsdk.f.g.c.b(simpleName, "init memory store", new Object[0]);
    }

    private LinkedList<Boolean> b(LinkedList<Long> linkedList) {
        boolean zBooleanValue;
        LinkedList<Boolean> linkedList2 = new LinkedList<>();
        LinkedList linkedList3 = new LinkedList();
        Iterator<Long> it = linkedList.iterator();
        while (it.hasNext()) {
            linkedList3.add(com.meizu.cloud.pushsdk.f.c.h.b.a(a(it.next())));
        }
        com.meizu.cloud.pushsdk.f.g.c.a(this.f19574s, "Removal Futures: %s", Integer.valueOf(linkedList3.size()));
        for (int i2 = 0; i2 < linkedList3.size(); i2++) {
            try {
                zBooleanValue = ((Boolean) ((Future) linkedList3.get(i2)).get(5L, TimeUnit.SECONDS)).booleanValue();
            } catch (InterruptedException e2) {
                com.meizu.cloud.pushsdk.f.g.c.b(this.f19574s, "Removal Future was interrupted: %s", e2.getMessage());
                zBooleanValue = false;
                linkedList2.add(Boolean.valueOf(zBooleanValue));
            } catch (ExecutionException e3) {
                com.meizu.cloud.pushsdk.f.g.c.b(this.f19574s, "Removal Future failed: %s", e3.getMessage());
                zBooleanValue = false;
                linkedList2.add(Boolean.valueOf(zBooleanValue));
            } catch (TimeoutException e4) {
                com.meizu.cloud.pushsdk.f.g.c.b(this.f19574s, "Removal Future had a timeout: %s", e4.getMessage());
                zBooleanValue = false;
                linkedList2.add(Boolean.valueOf(zBooleanValue));
            }
            linkedList2.add(Boolean.valueOf(zBooleanValue));
        }
        return linkedList2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() throws InterruptedException {
        if (e.d(this.f19529c)) {
            if (this.f19575t.b() > 0) {
                this.f19576u = 0;
                LinkedList<g> linkedListA = a(a(this.f19575t.c()));
                com.meizu.cloud.pushsdk.f.g.c.c(this.f19574s, "Processing emitter results.", new Object[0]);
                LinkedList<Long> linkedList = new LinkedList<>();
                Iterator<g> it = linkedListA.iterator();
                int size = 0;
                int size2 = 0;
                while (it.hasNext()) {
                    g next = it.next();
                    if (next.b()) {
                        linkedList.addAll(next.a());
                        size += next.a().size();
                    } else {
                        size2 += next.a().size();
                        com.meizu.cloud.pushsdk.f.g.c.b(this.f19574s, "Request sending failed but we will retry later.", new Object[0]);
                    }
                }
                b(linkedList);
                com.meizu.cloud.pushsdk.f.g.c.a(this.f19574s, "Success Count: %s", Integer.valueOf(size));
                com.meizu.cloud.pushsdk.f.g.c.a(this.f19574s, "Failure Count: %s", Integer.valueOf(size2));
                f fVar = this.f19531e;
                if (fVar != null) {
                    if (size2 != 0) {
                        fVar.a(size, size2);
                    } else {
                        fVar.a(size);
                    }
                }
                if (size2 > 0 && size == 0) {
                    if (e.d(this.f19529c)) {
                        com.meizu.cloud.pushsdk.f.g.c.b(this.f19574s, "Ensure collector path is valid: %s", c());
                    }
                    com.meizu.cloud.pushsdk.f.g.c.b(this.f19574s, "Emitter loop stopping: failures.", new Object[0]);
                }
            } else {
                int i2 = this.f19576u;
                if (i2 >= this.f19538l) {
                    com.meizu.cloud.pushsdk.f.g.c.b(this.f19574s, "Emitter loop stopping: empty limit reached.", new Object[0]);
                    this.f19544r.compareAndSet(true, false);
                    f fVar2 = this.f19531e;
                    if (fVar2 != null) {
                        fVar2.a(true);
                        return;
                    }
                    return;
                }
                this.f19576u = i2 + 1;
                com.meizu.cloud.pushsdk.f.g.c.b(this.f19574s, "Emitter database empty: " + this.f19576u, new Object[0]);
                try {
                    this.f19542p.sleep(this.f19537k);
                } catch (InterruptedException e2) {
                    com.meizu.cloud.pushsdk.f.g.c.b(this.f19574s, "Emitter thread sleep interrupted: " + e2.toString(), new Object[0]);
                }
            }
            d();
            return;
        }
        com.meizu.cloud.pushsdk.f.g.c.b(this.f19574s, "Emitter loop stopping: emitter offline.", new Object[0]);
        this.f19544r.compareAndSet(true, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00b0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.util.LinkedList<com.meizu.cloud.pushsdk.f.c.g> a(java.util.LinkedList<com.meizu.cloud.pushsdk.f.c.e> r10) {
        /*
            r9 = this;
            r0 = 0
            r1 = 1
            java.util.LinkedList r2 = new java.util.LinkedList
            r2.<init>()
            java.util.LinkedList r3 = new java.util.LinkedList
            r3.<init>()
            java.util.Iterator r4 = r10.iterator()
        L10:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L2c
            java.lang.Object r5 = r4.next()
            com.meizu.cloud.pushsdk.f.c.e r5 = (com.meizu.cloud.pushsdk.f.c.e) r5
            com.meizu.cloud.pushsdk.e.d.i r5 = r5.b()
            java.util.concurrent.Callable r5 = r9.b(r5)
            java.util.concurrent.Future r5 = com.meizu.cloud.pushsdk.f.c.h.b.a(r5)
            r3.add(r5)
            goto L10
        L2c:
            java.lang.String r4 = r9.f19574s
            int r5 = r3.size()
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            java.lang.Object[] r6 = new java.lang.Object[r1]
            r6[r0] = r5
            java.lang.String r5 = "Request Futures: %s"
            com.meizu.cloud.pushsdk.f.g.c.a(r4, r5, r6)
            r4 = r0
        L40:
            int r5 = r3.size()
            if (r4 >= r5) goto Lc9
            java.lang.Object r5 = r3.get(r4)     // Catch: java.util.concurrent.TimeoutException -> L5b java.util.concurrent.ExecutionException -> L5d java.lang.InterruptedException -> L5f
            java.util.concurrent.Future r5 = (java.util.concurrent.Future) r5     // Catch: java.util.concurrent.TimeoutException -> L5b java.util.concurrent.ExecutionException -> L5d java.lang.InterruptedException -> L5f
            java.util.concurrent.TimeUnit r6 = java.util.concurrent.TimeUnit.SECONDS     // Catch: java.util.concurrent.TimeoutException -> L5b java.util.concurrent.ExecutionException -> L5d java.lang.InterruptedException -> L5f
            r7 = 5
            java.lang.Object r5 = r5.get(r7, r6)     // Catch: java.util.concurrent.TimeoutException -> L5b java.util.concurrent.ExecutionException -> L5d java.lang.InterruptedException -> L5f
            java.lang.Integer r5 = (java.lang.Integer) r5     // Catch: java.util.concurrent.TimeoutException -> L5b java.util.concurrent.ExecutionException -> L5d java.lang.InterruptedException -> L5f
            int r5 = r5.intValue()     // Catch: java.util.concurrent.TimeoutException -> L5b java.util.concurrent.ExecutionException -> L5d java.lang.InterruptedException -> L5f
            goto L91
        L5b:
            r5 = move-exception
            goto L61
        L5d:
            r5 = move-exception
            goto L71
        L5f:
            r5 = move-exception
            goto L81
        L61:
            java.lang.String r6 = r9.f19574s
            java.lang.String r5 = r5.getMessage()
            java.lang.Object[] r7 = new java.lang.Object[r1]
            r7[r0] = r5
            java.lang.String r5 = "Request Future had a timeout: %s"
            com.meizu.cloud.pushsdk.f.g.c.b(r6, r5, r7)
            goto L90
        L71:
            java.lang.String r6 = r9.f19574s
            java.lang.String r5 = r5.getMessage()
            java.lang.Object[] r7 = new java.lang.Object[r1]
            r7[r0] = r5
            java.lang.String r5 = "Request Future failed: %s"
            com.meizu.cloud.pushsdk.f.g.c.b(r6, r5, r7)
            goto L90
        L81:
            java.lang.String r6 = r9.f19574s
            java.lang.String r5 = r5.getMessage()
            java.lang.Object[] r7 = new java.lang.Object[r1]
            r7[r0] = r5
            java.lang.String r5 = "Request Future was interrupted: %s"
            com.meizu.cloud.pushsdk.f.g.c.b(r6, r5, r7)
        L90:
            r5 = -1
        L91:
            java.lang.Object r6 = r10.get(r4)
            com.meizu.cloud.pushsdk.f.c.e r6 = (com.meizu.cloud.pushsdk.f.c.e) r6
            boolean r6 = r6.c()
            if (r6 == 0) goto Lb0
            com.meizu.cloud.pushsdk.f.c.g r5 = new com.meizu.cloud.pushsdk.f.c.g
            java.lang.Object r6 = r10.get(r4)
            com.meizu.cloud.pushsdk.f.c.e r6 = (com.meizu.cloud.pushsdk.f.c.e) r6
            java.util.LinkedList r6 = r6.a()
            r5.<init>(r1, r6)
            r2.add(r5)
            goto Lc6
        Lb0:
            com.meizu.cloud.pushsdk.f.c.g r6 = new com.meizu.cloud.pushsdk.f.c.g
            boolean r5 = r9.a(r5)
            java.lang.Object r7 = r10.get(r4)
            com.meizu.cloud.pushsdk.f.c.e r7 = (com.meizu.cloud.pushsdk.f.c.e) r7
            java.util.LinkedList r7 = r7.a()
            r6.<init>(r5, r7)
            r2.add(r6)
        Lc6:
            int r4 = r4 + r1
            goto L40
        Lc9:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.pushsdk.f.c.h.a.a(java.util.LinkedList):java.util.LinkedList");
    }

    private Callable<Integer> b(i iVar) {
        return new b(iVar);
    }

    private Callable<Boolean> a(Long l2) {
        return new c(l2);
    }

    @Override // com.meizu.cloud.pushsdk.f.c.a
    public void b() {
        com.meizu.cloud.pushsdk.f.c.h.b.a(new RunnableC0156a());
    }

    @Override // com.meizu.cloud.pushsdk.f.c.a
    public void a(com.meizu.cloud.pushsdk.f.b.a aVar, boolean z2) throws InterruptedException {
        this.f19575t.a(aVar);
        com.meizu.cloud.pushsdk.f.g.c.b(this.f19574s, "isRunning " + this.f19544r + " attemptEmit " + z2, new Object[0]);
        if (!z2) {
            try {
                this.f19542p.sleep(1L);
            } catch (InterruptedException e2) {
                com.meizu.cloud.pushsdk.f.g.c.b(this.f19574s, "Emitter add thread sleep interrupted: " + e2.toString(), new Object[0]);
            }
        }
        if (this.f19544r.compareAndSet(false, true)) {
            d();
        }
    }
}
