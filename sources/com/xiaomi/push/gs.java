package com.xiaomi.push;

import android.text.TextUtils;
import com.xiaomi.push.ex;
import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.zip.Adler32;

/* loaded from: classes4.dex */
class gs {

    /* renamed from: a, reason: collision with other field name */
    private gx f490a;

    /* renamed from: a, reason: collision with other field name */
    private InputStream f491a;

    /* renamed from: a, reason: collision with other field name */
    private volatile boolean f494a;

    /* renamed from: a, reason: collision with other field name */
    private byte[] f495a;

    /* renamed from: a, reason: collision with other field name */
    private ByteBuffer f492a = ByteBuffer.allocate(2048);

    /* renamed from: b, reason: collision with root package name */
    private ByteBuffer f23847b = ByteBuffer.allocate(4);

    /* renamed from: a, reason: collision with other field name */
    private Adler32 f493a = new Adler32();

    /* renamed from: a, reason: collision with root package name */
    private gv f23846a = new gv();

    gs(InputStream inputStream, gx gxVar) {
        this.f491a = new BufferedInputStream(inputStream);
        this.f490a = gxVar;
    }

    private void c() throws IOException {
        boolean z2 = false;
        this.f494a = false;
        gq gqVarM455a = m455a();
        if ("CONN".equals(gqVarM455a.m444a())) {
            ex.f fVarA = ex.f.a(gqVarM455a.m448a());
            if (fVarA.m375a()) {
                this.f490a.a(fVarA.m374a());
                z2 = true;
            }
            if (fVarA.c()) {
                ex.b bVarM373a = fVarA.m373a();
                gq gqVar = new gq();
                gqVar.a("SYNC", "CONF");
                gqVar.a(bVarM373a.m303a(), (String) null);
                this.f490a.a(gqVar);
            }
            com.xiaomi.channel.commonutils.logger.b.m91a("[Slim] CONN: host = " + fVarA.m376b());
        }
        if (!z2) {
            com.xiaomi.channel.commonutils.logger.b.m91a("[Slim] Invalid CONN");
            throw new IOException("Invalid Connection");
        }
        this.f495a = this.f490a.m462a();
        while (!this.f494a) {
            gq gqVarM455a2 = m455a();
            long jCurrentTimeMillis = System.currentTimeMillis();
            this.f490a.c();
            short sM446a = gqVarM455a2.m446a();
            if (sM446a == 1) {
                this.f490a.a(gqVarM455a2);
            } else if (sM446a != 2) {
                if (sM446a != 3) {
                    com.xiaomi.channel.commonutils.logger.b.m91a("[Slim] unknow blob type " + ((int) gqVarM455a2.m446a()));
                } else {
                    try {
                        this.f490a.b(this.f23846a.a(gqVarM455a2.m448a(), this.f490a));
                    } catch (Exception e2) {
                        com.xiaomi.channel.commonutils.logger.b.m91a("[Slim] Parse packet from Blob chid=" + gqVarM455a2.a() + "; Id=" + gqVarM455a2.e() + " failure:" + e2.getMessage());
                    }
                }
            } else if ("SECMSG".equals(gqVarM455a2.m444a()) && ((gqVarM455a2.a() == 2 || gqVarM455a2.a() == 3) && TextUtils.isEmpty(gqVarM455a2.m451b()))) {
                try {
                    hs hsVarA = this.f23846a.a(gqVarM455a2.m449a(com.xiaomi.push.service.bf.a().a(Integer.valueOf(gqVarM455a2.a()).toString(), gqVarM455a2.g()).f24504h), this.f490a);
                    hsVarA.f551a = jCurrentTimeMillis;
                    this.f490a.b(hsVarA);
                } catch (Exception e3) {
                    com.xiaomi.channel.commonutils.logger.b.m91a("[Slim] Parse packet from Blob chid=" + gqVarM455a2.a() + "; Id=" + gqVarM455a2.e() + " failure:" + e3.getMessage());
                }
            } else {
                this.f490a.a(gqVarM455a2);
            }
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    void m456a() throws IOException {
        try {
            c();
        } catch (IOException e2) {
            if (!this.f494a) {
                throw e2;
            }
        }
    }

    void b() {
        this.f494a = true;
    }

    private ByteBuffer a() throws IOException {
        this.f492a.clear();
        a(this.f492a, 8);
        short s2 = this.f492a.getShort(0);
        short s3 = this.f492a.getShort(2);
        if (s2 == -15618 && s3 == 5) {
            int i2 = this.f492a.getInt(4);
            int iPosition = this.f492a.position();
            if (i2 <= 32768) {
                if (i2 + 4 > this.f492a.remaining()) {
                    ByteBuffer byteBufferAllocate = ByteBuffer.allocate(i2 + 2048);
                    byteBufferAllocate.put(this.f492a.array(), 0, this.f492a.arrayOffset() + this.f492a.position());
                    this.f492a = byteBufferAllocate;
                } else if (this.f492a.capacity() > 4096 && i2 < 2048) {
                    ByteBuffer byteBufferAllocate2 = ByteBuffer.allocate(2048);
                    byteBufferAllocate2.put(this.f492a.array(), 0, this.f492a.arrayOffset() + this.f492a.position());
                    this.f492a = byteBufferAllocate2;
                }
                a(this.f492a, i2);
                this.f23847b.clear();
                a(this.f23847b, 4);
                this.f23847b.position(0);
                int i3 = this.f23847b.getInt();
                this.f493a.reset();
                this.f493a.update(this.f492a.array(), 0, this.f492a.position());
                if (i3 == ((int) this.f493a.getValue())) {
                    byte[] bArr = this.f495a;
                    if (bArr != null) {
                        com.xiaomi.push.service.bo.a(bArr, this.f492a.array(), true, iPosition, i2);
                    }
                    return this.f492a;
                }
                com.xiaomi.channel.commonutils.logger.b.m91a("CRC = " + ((int) this.f493a.getValue()) + " and " + i3);
                throw new IOException("Corrupted Blob bad CRC");
            }
            throw new IOException("Blob size too large");
        }
        throw new IOException("Malformed Input");
    }

    /* renamed from: a, reason: collision with other method in class */
    gq m455a() throws IOException {
        int iPosition;
        ByteBuffer byteBufferA;
        gq gqVarA;
        try {
            byteBufferA = a();
            iPosition = byteBufferA.position();
        } catch (IOException e2) {
            e = e2;
            iPosition = 0;
        }
        try {
            byteBufferA.flip();
            byteBufferA.position(8);
            if (iPosition == 8) {
                gqVarA = new gw();
            } else {
                gqVarA = gq.a(byteBufferA.slice());
            }
            com.xiaomi.channel.commonutils.logger.b.c("[Slim] Read {cmd=" + gqVarA.m444a() + ";chid=" + gqVarA.a() + ";len=" + iPosition + com.alipay.sdk.m.u.i.f9804d);
            return gqVarA;
        } catch (IOException e3) {
            e = e3;
            if (iPosition == 0) {
                iPosition = this.f492a.position();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("[Slim] read Blob [");
            byte[] bArrArray = this.f492a.array();
            if (iPosition > 128) {
                iPosition = 128;
            }
            sb.append(ae.a(bArrArray, 0, iPosition));
            sb.append("] Err:");
            sb.append(e.getMessage());
            com.xiaomi.channel.commonutils.logger.b.m91a(sb.toString());
            throw e;
        }
    }

    private void a(ByteBuffer byteBuffer, int i2) throws IOException {
        int iPosition = byteBuffer.position();
        do {
            int i3 = this.f491a.read(byteBuffer.array(), iPosition, i2);
            if (i3 == -1) {
                throw new EOFException();
            }
            i2 -= i3;
            iPosition += i3;
        } while (i2 > 0);
        byteBuffer.position(iPosition);
    }
}
