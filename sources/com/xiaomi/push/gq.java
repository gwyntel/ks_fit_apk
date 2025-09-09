package com.xiaomi.push;

import android.text.TextUtils;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.push.ex;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
public class gq {

    /* renamed from: a, reason: collision with other field name */
    int f484a;

    /* renamed from: a, reason: collision with other field name */
    private ex.a f485a;

    /* renamed from: a, reason: collision with other field name */
    String f486a;

    /* renamed from: a, reason: collision with other field name */
    private short f487a;

    /* renamed from: b, reason: collision with other field name */
    private final long f488b;

    /* renamed from: b, reason: collision with other field name */
    private byte[] f489b;

    /* renamed from: b, reason: collision with root package name */
    private static String f23844b = id.a(5) + Constants.ACCEPT_TIME_SEPARATOR_SERVER;

    /* renamed from: a, reason: collision with root package name */
    private static long f23843a = 0;

    /* renamed from: a, reason: collision with other field name */
    private static final byte[] f483a = new byte[0];

    public gq() {
        this.f487a = (short) 2;
        this.f489b = f483a;
        this.f486a = null;
        this.f488b = System.currentTimeMillis();
        this.f485a = new ex.a();
        this.f484a = 1;
    }

    public static synchronized String d() {
        StringBuilder sb;
        sb = new StringBuilder();
        sb.append(f23844b);
        long j2 = f23843a;
        f23843a = 1 + j2;
        sb.append(Long.toString(j2));
        return sb.toString();
    }

    /* renamed from: a, reason: collision with other method in class */
    public long m443a() {
        return this.f488b;
    }

    /* renamed from: b, reason: collision with other method in class */
    public String m451b() {
        return this.f485a.m334d();
    }

    /* renamed from: c, reason: collision with other method in class */
    public String m454c() {
        return this.f485a.m338f();
    }

    public String e() {
        String strM336e = this.f485a.m336e();
        if ("ID_NOT_AVAILABLE".equals(strM336e)) {
            return null;
        }
        if (this.f485a.g()) {
            return strM336e;
        }
        String strD = d();
        this.f485a.e(strD);
        return strD;
    }

    public String f() {
        return this.f486a;
    }

    public String g() {
        if (!this.f485a.m330b()) {
            return null;
        }
        return Long.toString(this.f485a.m324a()) + "@" + this.f485a.m326a() + "/" + this.f485a.m329b();
    }

    public String toString() {
        return "Blob [chid=" + a() + "; Id=" + com.xiaomi.push.service.bc.a(e()) + "; cmd=" + m444a() + "; type=" + ((int) m446a()) + "; from=" + g() + " ]";
    }

    public void a(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("command should not be empty");
        }
        this.f485a.c(str);
        this.f485a.m325a();
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        this.f485a.d(str2);
    }

    public int b() {
        return this.f485a.f();
    }

    public void c(long j2) {
        this.f485a.c(j2);
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m452b() {
        return this.f485a.l();
    }

    /* renamed from: c, reason: collision with other method in class */
    public long m453c() {
        return this.f485a.m324a();
    }

    public void b(long j2) {
        this.f485a.b(j2);
    }

    public void c(String str) throws NumberFormatException {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        int iIndexOf = str.indexOf("@");
        try {
            long j2 = Long.parseLong(str.substring(0, iIndexOf));
            int iIndexOf2 = str.indexOf("/", iIndexOf);
            String strSubstring = str.substring(iIndexOf + 1, iIndexOf2);
            String strSubstring2 = str.substring(iIndexOf2 + 1);
            this.f485a.a(j2);
            this.f485a.a(strSubstring);
            this.f485a.b(strSubstring2);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m91a("Blob parse user err " + e2.getMessage());
        }
    }

    /* renamed from: b, reason: collision with other method in class */
    public long m450b() {
        return this.f485a.m328b();
    }

    public void b(String str) {
        this.f486a = str;
    }

    gq(ex.a aVar, short s2, byte[] bArr) {
        this.f487a = (short) 2;
        this.f489b = f483a;
        this.f486a = null;
        this.f488b = System.currentTimeMillis();
        this.f485a = aVar;
        this.f487a = s2;
        this.f489b = bArr;
        this.f484a = 2;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m444a() {
        return this.f485a.m332c();
    }

    public void a(int i2) {
        this.f485a.a(i2);
    }

    public int a() {
        return this.f485a.c();
    }

    public void a(String str) {
        this.f485a.e(str);
    }

    public void a(long j2) {
        this.f485a.a(j2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m447a() {
        return this.f485a.j();
    }

    public void a(long j2, String str, String str2) {
        if (j2 != 0) {
            this.f485a.a(j2);
        }
        if (!TextUtils.isEmpty(str)) {
            this.f485a.a(str);
        }
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        this.f485a.b(str2);
    }

    public int c() {
        return this.f485a.b() + 8 + this.f489b.length;
    }

    public void a(byte[] bArr, String str) {
        if (!TextUtils.isEmpty(str)) {
            this.f485a.c(1);
            this.f489b = com.xiaomi.push.service.bo.a(com.xiaomi.push.service.bo.a(str, e()), bArr);
        } else {
            this.f485a.c(0);
            this.f489b = bArr;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public byte[] m448a() {
        return gr.a(this, this.f489b);
    }

    /* renamed from: a, reason: collision with other method in class */
    public byte[] m449a(String str) {
        if (this.f485a.e() == 1) {
            return gr.a(this, com.xiaomi.push.service.bo.a(com.xiaomi.push.service.bo.a(str, e()), this.f489b));
        }
        if (this.f485a.e() == 0) {
            return gr.a(this, this.f489b);
        }
        com.xiaomi.channel.commonutils.logger.b.m91a("unknow cipher = " + this.f485a.e());
        return gr.a(this, this.f489b);
    }

    @Deprecated
    public static gq a(hs hsVar, String str) {
        int i2;
        gq gqVar = new gq();
        try {
            i2 = Integer.parseInt(hsVar.k());
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m91a("Blob parse chid err " + e2.getMessage());
            i2 = 1;
        }
        gqVar.a(i2);
        gqVar.a(hsVar.j());
        gqVar.c(hsVar.m());
        gqVar.b(hsVar.n());
        gqVar.a("XMLMSG", (String) null);
        try {
            gqVar.a(hsVar.mo485a().getBytes("utf8"), str);
            if (TextUtils.isEmpty(str)) {
                gqVar.a((short) 3);
            } else {
                gqVar.a((short) 2);
                gqVar.a("SECMSG", (String) null);
            }
        } catch (UnsupportedEncodingException e3) {
            com.xiaomi.channel.commonutils.logger.b.m91a("Blob setPayload err： " + e3.getMessage());
        }
        return gqVar;
    }

    /* renamed from: a, reason: collision with other method in class */
    ByteBuffer mo445a(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            byteBuffer = ByteBuffer.allocate(c());
        }
        byteBuffer.putShort(this.f487a);
        byteBuffer.putShort((short) this.f485a.a());
        byteBuffer.putInt(this.f489b.length);
        int iPosition = byteBuffer.position();
        this.f485a.m302a(byteBuffer.array(), byteBuffer.arrayOffset() + iPosition, this.f485a.a());
        byteBuffer.position(iPosition + this.f485a.a());
        byteBuffer.put(this.f489b);
        return byteBuffer;
    }

    static gq a(ByteBuffer byteBuffer) throws IOException {
        try {
            ByteBuffer byteBufferSlice = byteBuffer.slice();
            short s2 = byteBufferSlice.getShort(0);
            short s3 = byteBufferSlice.getShort(2);
            int i2 = byteBufferSlice.getInt(4);
            ex.a aVar = new ex.a();
            aVar.a(byteBufferSlice.array(), byteBufferSlice.arrayOffset() + 8, (int) s3);
            byte[] bArr = new byte[i2];
            byteBufferSlice.position(s3 + 8);
            byteBufferSlice.get(bArr, 0, i2);
            return new gq(aVar, s2, bArr);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m91a("read Blob err :" + e2.getMessage());
            throw new IOException("Malformed Input");
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public short m446a() {
        return this.f487a;
    }

    public void a(short s2) {
        this.f487a = s2;
    }
}
