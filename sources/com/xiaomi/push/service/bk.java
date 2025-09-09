package com.xiaomi.push.service;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import com.xiaomi.push.C0472r;
import com.xiaomi.push.cz;
import com.xiaomi.push.dc;
import com.xiaomi.push.dd;
import com.xiaomi.push.ew;
import com.xiaomi.push.ex;
import com.xiaomi.push.ge;
import com.xiaomi.push.gm;
import com.xiaomi.push.go;
import com.xiaomi.push.hb;
import com.xiaomi.push.id;
import com.xiaomi.push.service.bw;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONException;

/* loaded from: classes4.dex */
public class bk extends bw.a implements dd.a {

    /* renamed from: a, reason: collision with root package name */
    private long f24543a;

    /* renamed from: a, reason: collision with other field name */
    private XMPushService f1049a;

    static class a implements dd.b {
        a() {
        }

        @Override // com.xiaomi.push.dd.b
        public String a(String str) throws IOException {
            Uri.Builder builderBuildUpon = Uri.parse(str).buildUpon();
            builderBuildUpon.appendQueryParameter("sdkver", String.valueOf(48));
            builderBuildUpon.appendQueryParameter("osver", String.valueOf(Build.VERSION.SDK_INT));
            builderBuildUpon.appendQueryParameter("os", id.a(Build.MODEL + ":" + Build.VERSION.INCREMENTAL));
            builderBuildUpon.appendQueryParameter("mi", String.valueOf(C0472r.a()));
            String string = builderBuildUpon.toString();
            com.xiaomi.channel.commonutils.logger.b.c("fetch bucket from : " + string);
            URL url = new URL(string);
            int port = url.getPort() == -1 ? 80 : url.getPort();
            try {
                long jCurrentTimeMillis = System.currentTimeMillis();
                String strA = com.xiaomi.push.bg.a(C0472r.m684a(), url);
                go.a(url.getHost() + ":" + port, (int) (System.currentTimeMillis() - jCurrentTimeMillis), null);
                return strA;
            } catch (IOException e2) {
                go.a(url.getHost() + ":" + port, -1, e2);
                throw e2;
            }
        }
    }

    static class b extends dd {
        protected b(Context context, dc dcVar, dd.b bVar, String str) {
            super(context, dcVar, bVar, str);
        }

        @Override // com.xiaomi.push.dd
        protected String a(ArrayList<String> arrayList, String str, String str2, boolean z2) throws IOException {
            try {
                if (gm.m436a().m441a()) {
                    str2 = bw.m767a();
                }
                return super.a(arrayList, str, str2, z2);
            } catch (IOException e2) {
                go.a(0, ge.GSLB_ERR.a(), 1, null, com.xiaomi.push.bg.c(dd.f23570a) ? 1 : 0);
                throw e2;
            }
        }
    }

    bk(XMPushService xMPushService) {
        this.f1049a = xMPushService;
    }

    @Override // com.xiaomi.push.service.bw.a
    public void a(ew.a aVar) {
    }

    public static void a(XMPushService xMPushService) {
        bk bkVar = new bk(xMPushService);
        bw.a().a(bkVar);
        synchronized (dd.class) {
            dd.a(bkVar);
            dd.a(xMPushService, null, new a(), "0", "push", "2.2");
        }
    }

    @Override // com.xiaomi.push.service.bw.a
    public void a(ex.b bVar) throws JSONException {
        cz czVarB;
        if (bVar.m341b() && bVar.m340a() && System.currentTimeMillis() - this.f24543a > 3600000) {
            com.xiaomi.channel.commonutils.logger.b.m91a("fetch bucket :" + bVar.m340a());
            this.f24543a = System.currentTimeMillis();
            dd ddVarA = dd.a();
            ddVarA.m272a();
            ddVarA.m275b();
            hb hbVarM708a = this.f1049a.m708a();
            if (hbVarM708a == null || (czVarB = ddVarA.b(hbVarM708a.m467a().c())) == null) {
                return;
            }
            ArrayList<String> arrayListM259a = czVarB.m259a();
            Iterator<String> it = arrayListM259a.iterator();
            while (it.hasNext()) {
                if (it.next().equals(hbVarM708a.mo468a())) {
                    return;
                }
            }
            if (arrayListM259a.isEmpty()) {
                return;
            }
            com.xiaomi.channel.commonutils.logger.b.m91a("bucket changed, force reconnect");
            this.f1049a.a(0, (Exception) null);
            this.f1049a.a(false);
        }
    }

    @Override // com.xiaomi.push.dd.a
    public dd a(Context context, dc dcVar, dd.b bVar, String str) {
        return new b(context, dcVar, bVar, str);
    }
}
