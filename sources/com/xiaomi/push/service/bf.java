package com.xiaomi.push.service;

import android.content.Context;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import com.alibaba.sdk.android.openaccount.ui.message.UIMessageConstants;
import com.xiaomi.push.service.XMPushService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.android.agoo.message.MessageService;

/* loaded from: classes4.dex */
public class bf {

    /* renamed from: a, reason: collision with root package name */
    private static bf f24496a;

    /* renamed from: a, reason: collision with other field name */
    private ConcurrentHashMap<String, HashMap<String, b>> f1031a = new ConcurrentHashMap<>();

    /* renamed from: a, reason: collision with other field name */
    private List<a> f1030a = new ArrayList();

    public interface a {
        void a();
    }

    public static class b {

        /* renamed from: a, reason: collision with other field name */
        public Context f1032a;

        /* renamed from: a, reason: collision with other field name */
        Messenger f1034a;

        /* renamed from: a, reason: collision with other field name */
        private XMPushService f1036a;

        /* renamed from: a, reason: collision with other field name */
        public k f1039a;

        /* renamed from: a, reason: collision with other field name */
        public String f1040a;

        /* renamed from: a, reason: collision with other field name */
        public boolean f1042a;

        /* renamed from: b, reason: collision with other field name */
        public String f1043b;

        /* renamed from: c, reason: collision with root package name */
        public String f24499c;

        /* renamed from: d, reason: collision with root package name */
        public String f24500d;

        /* renamed from: e, reason: collision with root package name */
        public String f24501e;

        /* renamed from: f, reason: collision with root package name */
        public String f24502f;

        /* renamed from: g, reason: collision with root package name */
        public String f24503g;

        /* renamed from: h, reason: collision with root package name */
        public String f24504h;

        /* renamed from: i, reason: collision with root package name */
        public String f24505i;

        /* renamed from: a, reason: collision with other field name */
        c f1038a = c.unbind;

        /* renamed from: a, reason: collision with root package name */
        private int f24497a = 0;

        /* renamed from: a, reason: collision with other field name */
        private final CopyOnWriteArrayList<a> f1041a = new CopyOnWriteArrayList<>();

        /* renamed from: b, reason: collision with root package name */
        c f24498b = null;

        /* renamed from: b, reason: collision with other field name */
        private boolean f1044b = false;

        /* renamed from: a, reason: collision with other field name */
        private XMPushService.c f1035a = new XMPushService.c(this);

        /* renamed from: a, reason: collision with other field name */
        IBinder.DeathRecipient f1033a = null;

        /* renamed from: a, reason: collision with other field name */
        final C0194b f1037a = new C0194b();

        public interface a {
            void a(c cVar, c cVar2, int i2);
        }

        class c implements IBinder.DeathRecipient {

            /* renamed from: a, reason: collision with root package name */
            final Messenger f24509a;

            /* renamed from: a, reason: collision with other field name */
            final b f1047a;

            c(b bVar, Messenger messenger) {
                this.f1047a = bVar;
                this.f24509a = messenger;
            }

            @Override // android.os.IBinder.DeathRecipient
            public void binderDied() {
                com.xiaomi.channel.commonutils.logger.b.b("peer died, chid = " + this.f1047a.f24503g);
                b.this.f1036a.a(new bh(this, 0), 0L);
                if (MessageService.MSG_ACCS_NOTIFY_DISMISS.equals(this.f1047a.f24503g) && "com.xiaomi.xmsf".equals(b.this.f1036a.getPackageName())) {
                    b.this.f1036a.a(new bi(this, 0), 60000L);
                }
            }
        }

        public b() {
        }

        private boolean b(int i2, int i3, String str) {
            if (i2 == 1) {
                return (this.f1038a == c.binded || !this.f1036a.m716c() || i3 == 21 || (i3 == 7 && "wait".equals(str))) ? false : true;
            }
            if (i2 == 2) {
                return this.f1036a.m716c();
            }
            if (i2 != 3) {
                return false;
            }
            return !"wait".equals(str);
        }

        /* renamed from: com.xiaomi.push.service.bf$b$b, reason: collision with other inner class name */
        class C0194b extends XMPushService.j {

            /* renamed from: a, reason: collision with other field name */
            String f1045a;

            /* renamed from: b, reason: collision with root package name */
            int f24507b;

            /* renamed from: b, reason: collision with other field name */
            String f1046b;

            /* renamed from: c, reason: collision with root package name */
            int f24508c;

            public C0194b() {
                super(0);
            }

            @Override // com.xiaomi.push.service.XMPushService.j
            /* renamed from: a */
            public void mo433a() throws RemoteException {
                if (b.this.a(this.f24507b, this.f24508c, this.f1046b)) {
                    b.this.a(this.f24507b, this.f24508c, this.f1045a, this.f1046b);
                    return;
                }
                com.xiaomi.channel.commonutils.logger.b.b(" ignore notify client :" + b.this.f24503g);
            }

            @Override // com.xiaomi.push.service.XMPushService.j
            public String a() {
                return "notify job";
            }

            public XMPushService.j a(int i2, int i3, String str, String str2) {
                this.f24507b = i2;
                this.f24508c = i3;
                this.f1046b = str2;
                this.f1045a = str;
                return this;
            }
        }

        /* renamed from: a, reason: collision with other method in class */
        void m761a() {
            try {
                Messenger messenger = this.f1034a;
                if (messenger != null && this.f1033a != null) {
                    messenger.getBinder().unlinkToDeath(this.f1033a, 0);
                }
            } catch (Exception unused) {
            }
            this.f24498b = null;
        }

        public void b(a aVar) {
            this.f1041a.remove(aVar);
        }

        void a(Messenger messenger) {
            m761a();
            try {
                if (messenger != null) {
                    this.f1034a = messenger;
                    this.f1044b = true;
                    this.f1033a = new c(this, messenger);
                    messenger.getBinder().linkToDeath(this.f1033a, 0);
                } else {
                    com.xiaomi.channel.commonutils.logger.b.b("peer linked with old sdk chid = " + this.f24503g);
                }
            } catch (Exception e2) {
                com.xiaomi.channel.commonutils.logger.b.b("peer linkToDeath err: " + e2.getMessage());
                this.f1034a = null;
                this.f1044b = false;
            }
        }

        public b(XMPushService xMPushService) {
            this.f1036a = xMPushService;
            a(new bg(this));
        }

        public void a(c cVar, int i2, int i3, String str, String str2) {
            boolean z2;
            int i4 = 0;
            Iterator<a> it = this.f1041a.iterator();
            while (it.hasNext()) {
                a next = it.next();
                if (next != null) {
                    next.a(this.f1038a, cVar, i3);
                }
            }
            c cVar2 = this.f1038a;
            if (cVar2 != cVar) {
                com.xiaomi.channel.commonutils.logger.b.m91a(String.format("update the client %7$s status. %1$s->%2$s %3$s %4$s %5$s %6$s", cVar2, cVar, a(i2), bj.a(i3), str, str2, this.f24503g));
                this.f1038a = cVar;
            }
            if (this.f1039a == null) {
                com.xiaomi.channel.commonutils.logger.b.d("status changed while the client dispatcher is missing");
                return;
            }
            if (cVar == c.binding) {
                return;
            }
            if (this.f24498b != null && (z2 = this.f1044b)) {
                i4 = (this.f1034a == null || !z2) ? UIMessageConstants.OPEN_ACCOUNT_REGISTER_CANCEL : 1000;
            }
            this.f1036a.b(this.f1037a);
            if (b(i2, i3, str2)) {
                a(i2, i3, str, str2);
            } else {
                this.f1036a.a(this.f1037a.a(i2, i3, str, str2), i4);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(int i2, int i3, String str, String str2) throws RemoteException {
            c cVar = this.f1038a;
            this.f24498b = cVar;
            if (i2 == 2) {
                this.f1039a.a(this.f1032a, this, i3);
                return;
            }
            if (i2 == 3) {
                this.f1039a.a(this.f1032a, this, str2, str);
                return;
            }
            if (i2 == 1) {
                boolean z2 = cVar == c.binded;
                if (!z2 && "wait".equals(str2)) {
                    this.f24497a++;
                } else if (z2) {
                    this.f24497a = 0;
                    if (this.f1034a != null) {
                        try {
                            this.f1034a.send(Message.obtain(null, 16, this.f1036a.f953a));
                        } catch (RemoteException unused) {
                        }
                    }
                }
                this.f1039a.a(this.f1036a, this, z2, i3, str);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean a(int i2, int i3, String str) {
            boolean z2;
            c cVar = this.f24498b;
            if (cVar == null || !(z2 = this.f1044b)) {
                return true;
            }
            if (cVar == this.f1038a) {
                com.xiaomi.channel.commonutils.logger.b.b(" status recovered, don't notify client:" + this.f24503g);
                return false;
            }
            if (this.f1034a != null && z2) {
                com.xiaomi.channel.commonutils.logger.b.b("Peer alive notify status to client:" + this.f24503g);
                return true;
            }
            com.xiaomi.channel.commonutils.logger.b.b("peer died, ignore notify " + this.f24503g);
            return false;
        }

        public String a(int i2) {
            if (i2 == 1) {
                return "OPEN";
            }
            if (i2 == 2) {
                return "CLOSE";
            }
            if (i2 != 3) {
                return "unknown";
            }
            return "KICK";
        }

        public void a(a aVar) {
            this.f1041a.add(aVar);
        }

        public long a() {
            return (((long) ((Math.random() * 20.0d) - 10.0d)) + ((this.f24497a + 1) * 15)) * 1000;
        }

        public static String a(String str) {
            int iLastIndexOf;
            return (TextUtils.isEmpty(str) || (iLastIndexOf = str.lastIndexOf("/")) == -1) ? "" : str.substring(iLastIndexOf + 1);
        }
    }

    public enum c {
        unbind,
        binding,
        binded
    }

    private bf() {
    }

    public static synchronized bf a() {
        try {
            if (f24496a == null) {
                f24496a = new bf();
            }
        } catch (Throwable th) {
            throw th;
        }
        return f24496a;
    }

    public synchronized void b() {
        this.f1030a.clear();
    }

    public synchronized void a(b bVar) {
        try {
            HashMap<String, b> map = this.f1031a.get(bVar.f24503g);
            if (map == null) {
                map = new HashMap<>();
                this.f1031a.put(bVar.f24503g, map);
            }
            map.put(a(bVar.f1043b), bVar);
            com.xiaomi.channel.commonutils.logger.b.m91a("add active client. " + bVar.f1040a);
            Iterator<a> it = this.f1030a.iterator();
            while (it.hasNext()) {
                it.next().a();
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized void m759a(String str, String str2) {
        try {
            HashMap<String, b> map = this.f1031a.get(str);
            if (map != null) {
                b bVar = map.get(a(str2));
                if (bVar != null) {
                    bVar.m761a();
                }
                map.remove(a(str2));
                if (map.isEmpty()) {
                    this.f1031a.remove(str);
                }
            }
            Iterator<a> it = this.f1030a.iterator();
            while (it.hasNext()) {
                it.next().a();
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized void m758a(String str) {
        try {
            HashMap<String, b> map = this.f1031a.get(str);
            if (map != null) {
                Iterator<b> it = map.values().iterator();
                while (it.hasNext()) {
                    it.next().m761a();
                }
                map.clear();
                this.f1031a.remove(str);
            }
            Iterator<a> it2 = this.f1030a.iterator();
            while (it2.hasNext()) {
                it2.next().a();
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized List<String> m756a(String str) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        Iterator<HashMap<String, b>> it = this.f1031a.values().iterator();
        while (it.hasNext()) {
            for (b bVar : it.next().values()) {
                if (str.equals(bVar.f1040a)) {
                    arrayList.add(bVar.f24503g);
                }
            }
        }
        return arrayList;
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized ArrayList<b> m754a() {
        ArrayList<b> arrayList;
        arrayList = new ArrayList<>();
        Iterator<HashMap<String, b>> it = this.f1031a.values().iterator();
        while (it.hasNext()) {
            arrayList.addAll(it.next().values());
        }
        return arrayList;
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized Collection<b> m755a(String str) {
        if (!this.f1031a.containsKey(str)) {
            return new ArrayList();
        }
        return ((HashMap) this.f1031a.get(str).clone()).values();
    }

    public synchronized b a(String str, String str2) {
        HashMap<String, b> map = this.f1031a.get(str);
        if (map == null) {
            return null;
        }
        return map.get(a(str2));
    }

    public synchronized void a(Context context, int i2) {
        Iterator<HashMap<String, b>> it = this.f1031a.values().iterator();
        while (it.hasNext()) {
            Iterator<b> it2 = it.next().values().iterator();
            while (it2.hasNext()) {
                it2.next().a(c.unbind, 2, i2, (String) null, (String) null);
            }
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized int m753a() {
        return this.f1031a.size();
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized void m757a() {
        try {
            Iterator<b> it = m754a().iterator();
            while (it.hasNext()) {
                it.next().m761a();
            }
            this.f1031a.clear();
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void a(Context context) {
        Iterator<HashMap<String, b>> it = this.f1031a.values().iterator();
        while (it.hasNext()) {
            Iterator<b> it2 = it.next().values().iterator();
            while (it2.hasNext()) {
                it2.next().a(c.unbind, 1, 3, (String) null, (String) null);
            }
        }
    }

    private String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        int iIndexOf = str.indexOf("@");
        return iIndexOf > 0 ? str.substring(0, iIndexOf) : str;
    }

    public synchronized void a(a aVar) {
        this.f1030a.add(aVar);
    }
}
