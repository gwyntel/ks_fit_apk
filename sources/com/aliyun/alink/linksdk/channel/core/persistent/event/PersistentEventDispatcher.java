package com.aliyun.alink.linksdk.channel.core.persistent.event;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.alibaba.fastjson.JSON;
import com.aliyun.alink.linksdk.tools.AError;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class PersistentEventDispatcher {

    /* renamed from: a, reason: collision with root package name */
    protected HashMap<IOnPushListener, Boolean> f10864a;

    /* renamed from: b, reason: collision with root package name */
    protected HashMap<IConnectionStateListener, Boolean> f10865b;

    /* renamed from: c, reason: collision with root package name */
    protected HashMap<INetSessionStateListener, Boolean> f10866c;

    /* renamed from: d, reason: collision with root package name */
    protected b f10867d;

    private static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final PersistentEventDispatcher f10868a;

        static {
            PersistentEventDispatcher persistentEventDispatcher = new PersistentEventDispatcher();
            f10868a = persistentEventDispatcher;
            persistentEventDispatcher.a();
        }
    }

    public static PersistentEventDispatcher getInstance() {
        return a.f10868a;
    }

    void a() {
        if (this.f10867d == null) {
            this.f10867d = new b();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x007e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void broadcastMessage(int r4, java.lang.String r5, byte[] r6, int r7, java.lang.String r8) {
        /*
            Method dump skipped, instructions count: 465
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.alink.linksdk.channel.core.persistent.event.PersistentEventDispatcher.broadcastMessage(int, java.lang.String, byte[], int, java.lang.String):void");
    }

    public void registerNetSessionStateListener(INetSessionStateListener iNetSessionStateListener, boolean z2) {
        com.aliyun.alink.linksdk.channel.core.b.a.a("PersistentEventDispatch", "registerNetSessionStateListener()");
        synchronized (this) {
            try {
                if (iNetSessionStateListener == null) {
                    return;
                }
                if (this.f10866c == null) {
                    this.f10866c = new HashMap<>();
                }
                this.f10866c.put(iNetSessionStateListener, Boolean.valueOf(z2));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void registerOnPushListener(IOnPushListener iOnPushListener, boolean z2) {
        com.aliyun.alink.linksdk.channel.core.b.a.a("PersistentEventDispatch", "registerOnPushListener() needUISafety=" + z2);
        synchronized (this) {
            try {
                if (iOnPushListener == null) {
                    return;
                }
                if (this.f10864a == null) {
                    this.f10864a = new HashMap<>();
                }
                this.f10864a.put(iOnPushListener, Boolean.valueOf(z2));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void registerOnTunnelStateListener(IConnectionStateListener iConnectionStateListener, boolean z2) {
        com.aliyun.alink.linksdk.channel.core.b.a.a("PersistentEventDispatch", "registerOnTunnelStateListener() called with: listener = [" + iConnectionStateListener + "], needUISafety = [" + z2 + "]");
        synchronized (this) {
            try {
                if (iConnectionStateListener == null) {
                    return;
                }
                if (this.f10865b == null) {
                    this.f10865b = new HashMap<>();
                }
                this.f10865b.put(iConnectionStateListener, Boolean.valueOf(z2));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void unregisterNetSessionStateListener(INetSessionStateListener iNetSessionStateListener) {
        com.aliyun.alink.linksdk.channel.core.b.a.a("PersistentEventDispatch", "unregisterNetSessionStateListener()");
        synchronized (this) {
            if (iNetSessionStateListener != null) {
                try {
                    HashMap<INetSessionStateListener, Boolean> map = this.f10866c;
                    if (map != null && map.size() > 0) {
                        this.f10866c.remove(iNetSessionStateListener);
                    }
                } finally {
                }
            }
        }
    }

    public void unregisterOnPushListener(IOnPushListener iOnPushListener) {
        com.aliyun.alink.linksdk.channel.core.b.a.a("PersistentEventDispatch", "unregisterOnPushListener() called with: listener = [" + iOnPushListener + "]");
        synchronized (this) {
            if (iOnPushListener != null) {
                try {
                    HashMap<IOnPushListener, Boolean> map = this.f10864a;
                    if (map != null && map.size() > 0) {
                        this.f10864a.remove(iOnPushListener);
                    }
                } finally {
                }
            }
        }
    }

    public void unregisterOnTunnelStateListener(IConnectionStateListener iConnectionStateListener) {
        com.aliyun.alink.linksdk.channel.core.b.a.a("PersistentEventDispatch", "unregisterOnTunnelStateListener() called with: listener = [" + iConnectionStateListener + "]");
        synchronized (this) {
            if (iConnectionStateListener != null) {
                try {
                    HashMap<IConnectionStateListener, Boolean> map = this.f10865b;
                    if (map != null && map.size() > 0) {
                        this.f10865b.remove(iConnectionStateListener);
                    }
                } finally {
                }
            }
        }
    }

    private PersistentEventDispatcher() {
        this.f10864a = null;
        this.f10865b = null;
        this.f10866c = null;
        this.f10867d = null;
    }

    private static class b extends Handler {
        public b() {
            super(Looper.getMainLooper());
        }

        public void a(int i2, Object obj, int i3, String str) {
            Message messageObtainMessage = obtainMessage();
            messageObtainMessage.what = i2;
            messageObtainMessage.obj = new a(obj, i3, str);
            sendMessageDelayed(messageObtainMessage, 10L);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Object obj;
            if (message == null || (obj = message.obj) == null || !(obj instanceof a)) {
                return;
            }
            a aVar = (a) obj;
            Object obj2 = aVar.f10869a;
            if (obj2 instanceof IOnPushListener) {
                IOnPushListener iOnPushListener = (IOnPushListener) obj2;
                if (message.what == 3) {
                    iOnPushListener.onCommand(aVar.f10872d, aVar.f10873e);
                    return;
                }
                return;
            }
            if (obj2 instanceof IConnectionStateListener) {
                PersistentEventDispatcher.a(message.what, (IConnectionStateListener) obj2, aVar.f10871c, aVar.f10870b);
            } else if (obj2 instanceof INetSessionStateListener) {
                PersistentEventDispatcher.a(message.what, (INetSessionStateListener) obj2);
            }
        }

        private static class a {

            /* renamed from: a, reason: collision with root package name */
            public Object f10869a;

            /* renamed from: b, reason: collision with root package name */
            public String f10870b;

            /* renamed from: c, reason: collision with root package name */
            public int f10871c;

            /* renamed from: d, reason: collision with root package name */
            public String f10872d;

            /* renamed from: e, reason: collision with root package name */
            public byte[] f10873e;

            public a(Object obj, int i2, String str) {
                this.f10869a = obj;
                this.f10871c = i2;
                this.f10870b = str;
            }

            public a(Object obj, String str, byte[] bArr) {
                this.f10869a = obj;
                this.f10872d = str;
                this.f10873e = bArr;
            }
        }

        public void a(int i2, Object obj, String str, byte[] bArr) {
            Message messageObtainMessage = obtainMessage();
            messageObtainMessage.what = i2;
            messageObtainMessage.obj = new a(obj, str, bArr);
            sendMessageDelayed(messageObtainMessage, 10L);
        }
    }

    static void a(int i2, IConnectionStateListener iConnectionStateListener, int i3, String str) {
        com.aliyun.alink.linksdk.channel.core.b.a.a("PersistentEventDispatch", "OnTunnelState()");
        if (iConnectionStateListener != null) {
            try {
                if (i2 == 1) {
                    iConnectionStateListener.onConnected();
                } else if (i2 == 2) {
                    iConnectionStateListener.onDisconnect();
                } else {
                    if (i2 != 7) {
                        return;
                    }
                    AError aError = new AError();
                    aError.setCode(i3);
                    aError.setMsg(str);
                    iConnectionStateListener.onConnectFail(JSON.toJSONString(aError));
                }
            } catch (Exception unused) {
                com.aliyun.alink.linksdk.channel.core.b.a.d("PersistentEventDispatch", "catch exception from IConnectionStateListener");
            }
        }
    }

    static void a(int i2, INetSessionStateListener iNetSessionStateListener) {
        com.aliyun.alink.linksdk.channel.core.b.a.a("PersistentEventDispatch", "OnSessionState()");
        if (iNetSessionStateListener != null) {
            try {
                if (i2 == 5) {
                    iNetSessionStateListener.onSessionEffective();
                } else if (i2 == 6) {
                    iNetSessionStateListener.onSessionInvalid();
                } else if (i2 != 4) {
                } else {
                    iNetSessionStateListener.onNeedLogin();
                }
            } catch (Exception unused) {
                com.aliyun.alink.linksdk.channel.core.b.a.d("PersistentEventDispatch", "catch exception from INetSessionStateListener");
            }
        }
    }
}
