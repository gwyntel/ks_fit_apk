package com.aliyun.alink.linksdk.tmp.device.a;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.aliyun.alink.linksdk.tmp.connect.f;
import com.aliyun.alink.linksdk.tmp.event.INotifyHandler;
import com.aliyun.alink.linksdk.tmp.utils.LogCat;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class f extends Handler {

    /* renamed from: a, reason: collision with root package name */
    protected static final String f11343a = "[Tmp]MessageHandler";

    /* renamed from: b, reason: collision with root package name */
    public static final int f11344b = 1;

    /* renamed from: c, reason: collision with root package name */
    public static final int f11345c = 2;

    /* renamed from: d, reason: collision with root package name */
    public static final int f11346d = 3;

    public f(Looper looper) {
        super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        INotifyHandler iNotifyHandler;
        int i2 = message.what;
        if (i2 == 1) {
            LogCat.d(f11343a, "handleMessage ONLOAD_MSG");
            f.c cVar = (f.c) message.obj;
            if (cVar != null) {
                f.b bVar = cVar.f11195d;
                if (bVar != null) {
                    bVar.a(cVar.f11193b, cVar.f11194c);
                }
                com.aliyun.alink.linksdk.tmp.connect.c cVar2 = cVar.f11192a;
                if (cVar2 != null) {
                    cVar2.a(cVar.f11193b, cVar.f11194c);
                    return;
                }
                return;
            }
            return;
        }
        if (i2 != 2) {
            if (i2 != 3) {
                LogCat.e(f11343a, "handleMessage other");
                super.handleMessage(message);
                return;
            }
            LogCat.d(f11343a, "handleMessage ONNOTIFY_MSG");
            f.d dVar = (f.d) message.obj;
            if (dVar == null || (iNotifyHandler = dVar.f11196a) == null) {
                return;
            }
            iNotifyHandler.onMessage(dVar.f11197b, dVar.f11198c);
            return;
        }
        LogCat.d(f11343a, "handleMessage ONERROR_MSG");
        f.a aVar = (f.a) message.obj;
        if (aVar != null) {
            f.b bVar2 = aVar.f11191d;
            if (bVar2 != null) {
                bVar2.a(aVar.f11189b, aVar.f11190c);
            }
            com.aliyun.alink.linksdk.tmp.connect.c cVar3 = aVar.f11188a;
            if (cVar3 != null) {
                cVar3.a(aVar.f11189b, aVar.f11190c);
            }
        }
    }
}
