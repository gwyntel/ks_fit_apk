package com.meizu.cloud.pushsdk;

import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.MzPushMessage;
import com.meizu.cloud.pushsdk.handler.c;
import com.meizu.cloud.pushsdk.handler.e.d;
import com.meizu.cloud.pushsdk.handler.e.e;
import com.meizu.cloud.pushsdk.handler.e.f;
import com.meizu.cloud.pushsdk.handler.e.g;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;
import com.meizu.cloud.pushsdk.util.MinSdkChecker;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static volatile b f19164a;

    /* renamed from: b, reason: collision with root package name */
    private final SparseArray<c> f19165b;

    /* renamed from: c, reason: collision with root package name */
    private Map<String, com.meizu.cloud.pushsdk.handler.a> f19166c;

    /* renamed from: d, reason: collision with root package name */
    private com.meizu.cloud.pushsdk.handler.e.m.a f19167d;

    /* renamed from: e, reason: collision with root package name */
    private com.meizu.cloud.pushsdk.handler.e.b.a f19168e;

    public class a extends com.meizu.cloud.pushsdk.handler.a {
        public a() {
        }

        @Override // com.meizu.cloud.pushsdk.handler.a
        public void a(Context context, Intent intent) {
            Iterator it = b.this.f19166c.entrySet().iterator();
            while (it.hasNext()) {
                com.meizu.cloud.pushsdk.handler.a aVar = (com.meizu.cloud.pushsdk.handler.a) ((Map.Entry) it.next()).getValue();
                if (aVar != null) {
                    aVar.a(context, intent);
                }
            }
        }

        @Override // com.meizu.cloud.pushsdk.handler.b
        public void b(Context context, MzPushMessage mzPushMessage) {
            Iterator it = b.this.f19166c.entrySet().iterator();
            while (it.hasNext()) {
                com.meizu.cloud.pushsdk.handler.a aVar = (com.meizu.cloud.pushsdk.handler.a) ((Map.Entry) it.next()).getValue();
                if (aVar != null) {
                    aVar.b(context, mzPushMessage);
                }
            }
        }

        @Override // com.meizu.cloud.pushsdk.handler.b
        public void c(Context context, MzPushMessage mzPushMessage) {
            Iterator it = b.this.f19166c.entrySet().iterator();
            while (it.hasNext()) {
                com.meizu.cloud.pushsdk.handler.a aVar = (com.meizu.cloud.pushsdk.handler.a) ((Map.Entry) it.next()).getValue();
                if (aVar != null) {
                    aVar.c(context, mzPushMessage);
                }
            }
        }

        @Override // com.meizu.cloud.pushsdk.handler.b
        public void a(Context context, MzPushMessage mzPushMessage) {
            Iterator it = b.this.f19166c.entrySet().iterator();
            while (it.hasNext()) {
                com.meizu.cloud.pushsdk.handler.a aVar = (com.meizu.cloud.pushsdk.handler.a) ((Map.Entry) it.next()).getValue();
                if (aVar != null) {
                    aVar.a(context, mzPushMessage);
                }
            }
        }

        @Override // com.meizu.cloud.pushsdk.handler.b
        public void b(Context context, String str) {
            Iterator it = b.this.f19166c.entrySet().iterator();
            while (it.hasNext()) {
                com.meizu.cloud.pushsdk.handler.a aVar = (com.meizu.cloud.pushsdk.handler.a) ((Map.Entry) it.next()).getValue();
                if (aVar != null) {
                    aVar.b(context, str);
                }
            }
        }

        @Override // com.meizu.cloud.pushsdk.handler.b
        public void c(Context context, String str) {
            Iterator it = b.this.f19166c.entrySet().iterator();
            while (it.hasNext()) {
                com.meizu.cloud.pushsdk.handler.a aVar = (com.meizu.cloud.pushsdk.handler.a) ((Map.Entry) it.next()).getValue();
                if (aVar != null) {
                    aVar.c(context, str);
                }
            }
        }

        @Override // com.meizu.cloud.pushsdk.handler.b
        public void a(Context context, PushSwitchStatus pushSwitchStatus) {
            Iterator it = b.this.f19166c.entrySet().iterator();
            while (it.hasNext()) {
                com.meizu.cloud.pushsdk.handler.a aVar = (com.meizu.cloud.pushsdk.handler.a) ((Map.Entry) it.next()).getValue();
                if (aVar != null) {
                    aVar.a(context, pushSwitchStatus);
                }
            }
        }

        @Override // com.meizu.cloud.pushsdk.handler.b
        public void a(Context context, RegisterStatus registerStatus) {
            Iterator it = b.this.f19166c.entrySet().iterator();
            while (it.hasNext()) {
                com.meizu.cloud.pushsdk.handler.a aVar = (com.meizu.cloud.pushsdk.handler.a) ((Map.Entry) it.next()).getValue();
                if (aVar != null) {
                    aVar.a(context, registerStatus);
                }
            }
        }

        @Override // com.meizu.cloud.pushsdk.handler.b
        public void a(Context context, SubAliasStatus subAliasStatus) {
            Iterator it = b.this.f19166c.entrySet().iterator();
            while (it.hasNext()) {
                com.meizu.cloud.pushsdk.handler.a aVar = (com.meizu.cloud.pushsdk.handler.a) ((Map.Entry) it.next()).getValue();
                if (aVar != null) {
                    aVar.a(context, subAliasStatus);
                }
            }
        }

        @Override // com.meizu.cloud.pushsdk.handler.b
        public void a(Context context, SubTagsStatus subTagsStatus) {
            Iterator it = b.this.f19166c.entrySet().iterator();
            while (it.hasNext()) {
                com.meizu.cloud.pushsdk.handler.a aVar = (com.meizu.cloud.pushsdk.handler.a) ((Map.Entry) it.next()).getValue();
                if (aVar != null) {
                    aVar.a(context, subTagsStatus);
                }
            }
        }

        @Override // com.meizu.cloud.pushsdk.handler.b
        public void a(Context context, UnRegisterStatus unRegisterStatus) {
            Iterator it = b.this.f19166c.entrySet().iterator();
            while (it.hasNext()) {
                com.meizu.cloud.pushsdk.handler.a aVar = (com.meizu.cloud.pushsdk.handler.a) ((Map.Entry) it.next()).getValue();
                if (aVar != null) {
                    aVar.a(context, unRegisterStatus);
                }
            }
        }

        @Override // com.meizu.cloud.pushsdk.handler.b
        public void a(Context context, String str) {
            Iterator it = b.this.f19166c.entrySet().iterator();
            while (it.hasNext()) {
                com.meizu.cloud.pushsdk.handler.a aVar = (com.meizu.cloud.pushsdk.handler.a) ((Map.Entry) it.next()).getValue();
                if (aVar != null) {
                    aVar.a(context, str);
                }
            }
        }

        @Override // com.meizu.cloud.pushsdk.handler.b
        public void a(Context context, String str, String str2) {
            Iterator it = b.this.f19166c.entrySet().iterator();
            while (it.hasNext()) {
                com.meizu.cloud.pushsdk.handler.a aVar = (com.meizu.cloud.pushsdk.handler.a) ((Map.Entry) it.next()).getValue();
                if (aVar != null) {
                    aVar.a(context, str, str2);
                }
            }
        }

        @Override // com.meizu.cloud.pushsdk.handler.b
        public void a(Context context, boolean z2) {
            Iterator it = b.this.f19166c.entrySet().iterator();
            while (it.hasNext()) {
                com.meizu.cloud.pushsdk.handler.a aVar = (com.meizu.cloud.pushsdk.handler.a) ((Map.Entry) it.next()).getValue();
                if (aVar != null) {
                    aVar.a(context, z2);
                }
            }
        }

        @Override // com.meizu.cloud.pushsdk.handler.b
        public void a(PushNotificationBuilder pushNotificationBuilder) {
            Iterator it = b.this.f19166c.entrySet().iterator();
            while (it.hasNext()) {
                com.meizu.cloud.pushsdk.handler.a aVar = (com.meizu.cloud.pushsdk.handler.a) ((Map.Entry) it.next()).getValue();
                if (aVar != null) {
                    aVar.a(pushNotificationBuilder);
                }
            }
        }
    }

    public b(Context context) {
        this(context, null);
    }

    public static b a(Context context) {
        if (f19164a == null) {
            synchronized (b.class) {
                try {
                    if (f19164a == null) {
                        DebugLogger.i("PushMessageProxy", "PushMessageProxy init");
                        f19164a = new b(context);
                    }
                } finally {
                }
            }
        }
        return f19164a;
    }

    public com.meizu.cloud.pushsdk.handler.e.m.a b() {
        return this.f19167d;
    }

    public b(Context context, List<c> list) {
        this(context, list, null);
    }

    public b a(c cVar) {
        this.f19165b.put(cVar.a(), cVar);
        return this;
    }

    public b(Context context, List<c> list, com.meizu.cloud.pushsdk.handler.a aVar) {
        this.f19165b = new SparseArray<>();
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null.");
        }
        Context applicationContext = context.getApplicationContext();
        this.f19166c = new HashMap();
        a aVar2 = new a();
        if (PushConstants.PUSH_PACKAGE_NAME.equalsIgnoreCase(applicationContext.getPackageName())) {
            this.f19167d = new com.meizu.cloud.pushsdk.handler.e.m.a(applicationContext);
            if (MinSdkChecker.isSupportNotificationSort()) {
                this.f19168e = new com.meizu.cloud.pushsdk.handler.e.b.a(applicationContext);
            }
        }
        if (list != null) {
            a(list);
            return;
        }
        a(new d(applicationContext, aVar2));
        a(new com.meizu.cloud.pushsdk.handler.e.c(applicationContext, aVar2));
        a(new f(applicationContext, aVar2));
        a(new com.meizu.cloud.pushsdk.handler.e.k.b(applicationContext, aVar2));
        a(new e(applicationContext, aVar2));
        a(new g(applicationContext, aVar2));
        a(new com.meizu.cloud.pushsdk.handler.e.k.d(applicationContext, aVar2));
        a(new com.meizu.cloud.pushsdk.handler.e.l.a(applicationContext, aVar2));
        a(new com.meizu.cloud.pushsdk.handler.e.l.c(applicationContext, aVar2));
        a(new com.meizu.cloud.pushsdk.handler.e.l.f(applicationContext, aVar2));
        a(new com.meizu.cloud.pushsdk.handler.e.l.d(applicationContext, aVar2));
        a(new com.meizu.cloud.pushsdk.handler.e.l.e(applicationContext, aVar2));
        a(new com.meizu.cloud.pushsdk.handler.e.m.c(applicationContext, aVar2));
        a(new com.meizu.cloud.pushsdk.handler.e.l.b(applicationContext, aVar2));
        a(new com.meizu.cloud.pushsdk.handler.e.k.e(applicationContext, aVar2));
        a(new com.meizu.cloud.pushsdk.handler.e.i.a(applicationContext, aVar2));
        a(new com.meizu.cloud.pushsdk.handler.e.k.a(applicationContext, aVar2));
        a(new com.meizu.cloud.pushsdk.handler.e.k.f(applicationContext, aVar2));
        a(new com.meizu.cloud.pushsdk.handler.e.m.b(applicationContext, aVar2));
        a(new com.meizu.cloud.pushsdk.handler.e.k.c(applicationContext, aVar2));
    }

    public b a(String str, com.meizu.cloud.pushsdk.handler.a aVar) {
        this.f19166c.put(str, aVar);
        return this;
    }

    public b a(List<c> list) {
        if (list == null) {
            throw new IllegalArgumentException("messageManagerList must not be null.");
        }
        Iterator<c> it = list.iterator();
        while (it.hasNext()) {
            a(it.next());
        }
        return this;
    }

    public com.meizu.cloud.pushsdk.handler.e.b.a a() {
        return this.f19168e;
    }

    public void a(Intent intent) {
        DebugLogger.e("PushMessageProxy", "process message start");
        try {
            DebugLogger.i("PushMessageProxy", "receive action " + intent.getAction() + " method " + intent.getStringExtra("method"));
            for (int i2 = 0; i2 < this.f19165b.size() && !this.f19165b.valueAt(i2).b(intent); i2++) {
            }
        } catch (Exception e2) {
            DebugLogger.e("PushMessageProxy", "process message error " + e2.getMessage());
        }
    }
}
