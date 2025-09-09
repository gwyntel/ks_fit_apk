package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public class ObservableInputStream extends ProxyInputStream {
    private final List<Observer> observers;

    public ObservableInputStream(InputStream inputStream) {
        super(inputStream);
        this.observers = new ArrayList();
    }

    public void add(Observer observer) {
        this.observers.add(observer);
    }

    @Override // org.apache.commons.io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        try {
            super.close();
            e = null;
        } catch (IOException e2) {
            e = e2;
        }
        if (e == null) {
            f();
        } else {
            i(e);
        }
    }

    public void consume() throws IOException {
        while (read(new byte[8192]) != -1) {
        }
    }

    protected List e() {
        return this.observers;
    }

    protected void f() {
        Iterator it = e().iterator();
        while (it.hasNext()) {
            ((Observer) it.next()).a();
        }
    }

    protected void g(int i2) {
        Iterator it = e().iterator();
        while (it.hasNext()) {
            ((Observer) it.next()).b(i2);
        }
    }

    protected void h(byte[] bArr, int i2, int i3) {
        Iterator it = e().iterator();
        while (it.hasNext()) {
            ((Observer) it.next()).c(bArr, i2, i3);
        }
    }

    protected void i(IOException iOException) throws IOException {
        Iterator it = e().iterator();
        while (it.hasNext()) {
            ((Observer) it.next()).d(iOException);
        }
    }

    protected void j() {
        Iterator it = e().iterator();
        while (it.hasNext()) {
            ((Observer) it.next()).e();
        }
    }

    @Override // org.apache.commons.io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        int i2;
        try {
            i2 = super.read();
            e = null;
        } catch (IOException e2) {
            e = e2;
            i2 = 0;
        }
        if (e != null) {
            i(e);
        } else if (i2 == -1) {
            j();
        } else {
            g(i2);
        }
        return i2;
    }

    public void remove(Observer observer) {
        this.observers.remove(observer);
    }

    public void removeAllObservers() {
        this.observers.clear();
    }

    @Override // org.apache.commons.io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        int i2;
        try {
            i2 = super.read(bArr);
            e = null;
        } catch (IOException e2) {
            e = e2;
            i2 = 0;
        }
        if (e != null) {
            i(e);
        } else if (i2 == -1) {
            j();
        } else if (i2 > 0) {
            h(bArr, 0, i2);
        }
        return i2;
    }

    @Override // org.apache.commons.io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        int i4;
        try {
            i4 = super.read(bArr, i2, i3);
            e = null;
        } catch (IOException e2) {
            e = e2;
            i4 = 0;
        }
        if (e != null) {
            i(e);
        } else if (i4 == -1) {
            j();
        } else if (i4 > 0) {
            h(bArr, i2, i4);
        }
        return i4;
    }

    public static abstract class Observer {
        void a() {
        }

        void b(int i2) {
        }

        void c(byte[] bArr, int i2, int i3) {
        }

        void e() {
        }

        void d(IOException iOException) throws IOException {
            throw iOException;
        }
    }
}
