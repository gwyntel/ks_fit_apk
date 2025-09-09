package com.umeng.analytics.pro;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes4.dex */
public class db extends dd {

    /* renamed from: a, reason: collision with root package name */
    protected InputStream f21666a;

    /* renamed from: b, reason: collision with root package name */
    protected OutputStream f21667b;

    protected db() {
        this.f21666a = null;
        this.f21667b = null;
    }

    @Override // com.umeng.analytics.pro.dd
    public boolean a() {
        return true;
    }

    @Override // com.umeng.analytics.pro.dd
    public void b() throws de {
    }

    @Override // com.umeng.analytics.pro.dd
    public void c() throws IOException {
        InputStream inputStream = this.f21666a;
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            this.f21666a = null;
        }
        OutputStream outputStream = this.f21667b;
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            this.f21667b = null;
        }
    }

    @Override // com.umeng.analytics.pro.dd
    public void d() throws de, IOException {
        OutputStream outputStream = this.f21667b;
        if (outputStream == null) {
            throw new de(1, "Cannot flush null outputStream");
        }
        try {
            outputStream.flush();
        } catch (IOException e2) {
            throw new de(0, e2);
        }
    }

    @Override // com.umeng.analytics.pro.dd
    public int a(byte[] bArr, int i2, int i3) throws de, IOException {
        InputStream inputStream = this.f21666a;
        if (inputStream == null) {
            throw new de(1, "Cannot read from null inputStream");
        }
        try {
            int i4 = inputStream.read(bArr, i2, i3);
            if (i4 >= 0) {
                return i4;
            }
            throw new de(4);
        } catch (IOException e2) {
            throw new de(0, e2);
        }
    }

    @Override // com.umeng.analytics.pro.dd
    public void b(byte[] bArr, int i2, int i3) throws de, IOException {
        OutputStream outputStream = this.f21667b;
        if (outputStream == null) {
            throw new de(1, "Cannot write to null outputStream");
        }
        try {
            outputStream.write(bArr, i2, i3);
        } catch (IOException e2) {
            throw new de(0, e2);
        }
    }

    public db(InputStream inputStream) {
        this.f21667b = null;
        this.f21666a = inputStream;
    }

    public db(OutputStream outputStream) {
        this.f21666a = null;
        this.f21667b = outputStream;
    }

    public db(InputStream inputStream, OutputStream outputStream) {
        this.f21666a = inputStream;
        this.f21667b = outputStream;
    }
}
