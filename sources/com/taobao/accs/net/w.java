package com.taobao.accs.net;

import com.taobao.accs.data.Message;

/* loaded from: classes4.dex */
class w implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Message f20279a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ boolean f20280b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ v f20281c;

    w(v vVar, Message message, boolean z2) {
        this.f20281c = vVar;
        this.f20279a = message;
        this.f20280b = z2;
    }

    @Override // java.lang.Runnable
    public void run() {
        synchronized (this.f20281c.f20269u) {
            try {
                this.f20281c.a(this.f20279a);
                if (this.f20281c.f20269u.size() == 0) {
                    this.f20281c.f20269u.add(this.f20279a);
                } else {
                    Message message = (Message) this.f20281c.f20269u.getFirst();
                    if (this.f20279a.getType() == 1 || this.f20279a.getType() == 0) {
                        this.f20281c.f20269u.addLast(this.f20279a);
                        if (message.getType() == 2) {
                            this.f20281c.f20269u.removeFirst();
                        }
                    } else if (this.f20279a.getType() != 2 || message.getType() != 2) {
                        this.f20281c.f20269u.addLast(this.f20279a);
                    } else if (!message.force && this.f20279a.force) {
                        this.f20281c.f20269u.removeFirst();
                        this.f20281c.f20269u.addFirst(this.f20279a);
                    }
                }
                if (this.f20280b || this.f20281c.f20268t == 3) {
                    try {
                        this.f20281c.f20269u.notifyAll();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
