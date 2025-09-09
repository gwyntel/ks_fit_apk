package com.xiaomi.push;

import android.os.Build;
import com.xiaomi.push.ex;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.TimeZone;
import java.util.zip.Adler32;

/* loaded from: classes4.dex */
public class gt {

    /* renamed from: a, reason: collision with root package name */
    private int f23848a;

    /* renamed from: a, reason: collision with other field name */
    private gx f496a;

    /* renamed from: a, reason: collision with other field name */
    private OutputStream f497a;

    /* renamed from: a, reason: collision with other field name */
    private byte[] f500a;

    /* renamed from: b, reason: collision with root package name */
    private int f23849b;

    /* renamed from: a, reason: collision with other field name */
    ByteBuffer f498a = ByteBuffer.allocate(2048);

    /* renamed from: b, reason: collision with other field name */
    private ByteBuffer f501b = ByteBuffer.allocate(4);

    /* renamed from: a, reason: collision with other field name */
    private Adler32 f499a = new Adler32();

    gt(OutputStream outputStream, gx gxVar) {
        this.f497a = new BufferedOutputStream(outputStream);
        this.f496a = gxVar;
        TimeZone timeZone = TimeZone.getDefault();
        this.f23848a = timeZone.getRawOffset() / 3600000;
        this.f23849b = timeZone.useDaylightTime() ? 1 : 0;
    }

    public int a(gq gqVar) {
        int iC = gqVar.c();
        if (iC > 32768) {
            com.xiaomi.channel.commonutils.logger.b.m91a("Blob size=" + iC + " should be less than 32768 Drop blob chid=" + gqVar.a() + " id=" + gqVar.e());
            return 0;
        }
        this.f498a.clear();
        int i2 = iC + 12;
        if (i2 > this.f498a.capacity() || this.f498a.capacity() > 4096) {
            this.f498a = ByteBuffer.allocate(i2);
        }
        this.f498a.putShort((short) -15618);
        this.f498a.putShort((short) 5);
        this.f498a.putInt(iC);
        int iPosition = this.f498a.position();
        this.f498a = gqVar.mo445a(this.f498a);
        if (!"CONN".equals(gqVar.m444a())) {
            if (this.f500a == null) {
                this.f500a = this.f496a.m462a();
            }
            com.xiaomi.push.service.bo.a(this.f500a, this.f498a.array(), true, iPosition, iC);
        }
        this.f499a.reset();
        this.f499a.update(this.f498a.array(), 0, this.f498a.position());
        this.f501b.putInt(0, (int) this.f499a.getValue());
        this.f497a.write(this.f498a.array(), 0, this.f498a.position());
        this.f497a.write(this.f501b.array(), 0, 4);
        this.f497a.flush();
        int iPosition2 = this.f498a.position() + 4;
        com.xiaomi.channel.commonutils.logger.b.c("[Slim] Wrote {cmd=" + gqVar.m444a() + ";chid=" + gqVar.a() + ";len=" + iPosition2 + com.alipay.sdk.m.u.i.f9804d);
        return iPosition2;
    }

    public void b() throws IOException {
        gq gqVar = new gq();
        gqVar.a("CLOSE", (String) null);
        a(gqVar);
        this.f497a.close();
    }

    public void a() {
        ex.e eVar = new ex.e();
        eVar.a(106);
        String str = Build.MODEL;
        eVar.a(str);
        eVar.b(C0472r.m685a());
        eVar.c(com.xiaomi.push.service.bw.m767a());
        eVar.b(48);
        eVar.d(this.f496a.m470b());
        eVar.e(this.f496a.mo468a());
        eVar.f(Locale.getDefault().toString());
        int i2 = Build.VERSION.SDK_INT;
        eVar.c(i2);
        eVar.d(g.a(this.f496a.a(), "com.xiaomi.xmsf"));
        byte[] bArrMo476a = this.f496a.m467a().mo476a();
        if (bArrMo476a != null) {
            eVar.a(ex.b.a(bArrMo476a));
        }
        gq gqVar = new gq();
        gqVar.a(0);
        gqVar.a("CONN", (String) null);
        gqVar.a(0L, "xiaomi.com", null);
        gqVar.a(eVar.m303a(), (String) null);
        a(gqVar);
        com.xiaomi.channel.commonutils.logger.b.m91a("[slim] open conn: andver=" + i2 + " sdk=48 tz=" + this.f23848a + ":" + this.f23849b + " Model=" + str + " os=" + Build.VERSION.INCREMENTAL);
    }
}
