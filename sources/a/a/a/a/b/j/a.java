package a.a.a.a.b.j;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class a extends Handler {

    /* renamed from: a, reason: collision with root package name */
    public String f1532a;

    /* renamed from: b, reason: collision with root package name */
    public BlockingQueue<Runnable> f1533b;

    /* renamed from: c, reason: collision with root package name */
    public Runnable f1534c;

    /* renamed from: d, reason: collision with root package name */
    public AtomicBoolean f1535d;

    public a(Looper looper) {
        super(looper);
        this.f1532a = a.class.getSimpleName();
        this.f1533b = new LinkedBlockingDeque();
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        this.f1535d = atomicBoolean;
        atomicBoolean.set(false);
    }

    public final void a(int i2) {
        if (i2 < 24) {
            if (b()) {
                b(i2 + 1);
                return;
            } else {
                b("err:takeMsg is status false");
                return;
            }
        }
        a();
        if (b()) {
            b(1);
        } else {
            this.f1535d.set(false);
        }
    }

    public final boolean b() {
        try {
            if (this.f1534c == null) {
                if (this.f1533b.isEmpty()) {
                    return false;
                }
                this.f1534c = this.f1533b.take();
            }
            this.f1534c.run();
            return true;
        } catch (InterruptedException e2) {
            b("InterruptedException:" + e2.getMessage());
            this.f1533b.remove(null);
            return true;
        }
    }

    @Override // android.os.Handler
    public void handleMessage(@NonNull Message message) {
        super.handleMessage(message);
        if (message.what != 1) {
            return;
        }
        a(((Integer) message.obj).intValue());
    }

    public final void b(int i2) {
        a("sendAlarmTime index:" + i2);
        sendMessageDelayed(obtainMessage(1, Integer.valueOf(i2)), 50L);
    }

    public final void a() {
        this.f1534c = null;
    }

    public void a(Runnable runnable) throws InterruptedException {
        try {
            this.f1533b.put(runnable);
        } catch (InterruptedException e2) {
            e2.printStackTrace();
            this.f1533b.remove(runnable);
        }
        if (this.f1535d.compareAndSet(false, true)) {
            b(0);
        }
    }

    public final void b(String str) {
        a.a.a.a.b.m.a.b(this.f1532a, str);
    }

    public final void a(String str) {
        a.a.a.a.b.m.a.a(this.f1532a, str);
    }
}
