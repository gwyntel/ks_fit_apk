package anetwork.channel.aidl.adapter;

import android.os.RemoteException;
import anet.channel.bytes.ByteArray;
import anetwork.channel.aidl.ParcelableInputStream;
import anetwork.channel.entity.g;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes2.dex */
public class ParcelableInputStreamImpl extends ParcelableInputStream.Stub {
    private static final ByteArray EOS = ByteArray.create(0);
    private static final String TAG = "anet.ParcelableInputStreamImpl";
    private int blockIndex;
    private int blockOffset;
    private int contentLength;
    final ReentrantLock lock;
    final Condition newDataArrive;
    private final AtomicBoolean isClosed = new AtomicBoolean(false);
    private LinkedList<ByteArray> byteList = new LinkedList<>();
    private int rto = 10000;
    private String seqNo = "";

    public ParcelableInputStreamImpl() {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.lock = reentrantLock;
        this.newDataArrive = reentrantLock.newCondition();
    }

    private void recycleCurrentItem() {
        this.lock.lock();
        try {
            this.byteList.set(this.blockIndex, EOS).recycle();
        } finally {
            this.lock.unlock();
        }
    }

    @Override // anetwork.channel.aidl.ParcelableInputStream
    public int available() throws RemoteException {
        if (this.isClosed.get()) {
            throw new RuntimeException("Stream is closed");
        }
        this.lock.lock();
        try {
            int dataLength = 0;
            if (this.blockIndex == this.byteList.size()) {
                this.lock.unlock();
                return 0;
            }
            ListIterator<ByteArray> listIterator = this.byteList.listIterator(this.blockIndex);
            while (listIterator.hasNext()) {
                dataLength += listIterator.next().getDataLength();
            }
            int i2 = dataLength - this.blockOffset;
            this.lock.unlock();
            return i2;
        } catch (Throwable th) {
            this.lock.unlock();
            throw th;
        }
    }

    @Override // anetwork.channel.aidl.ParcelableInputStream
    public void close() throws RemoteException {
        if (this.isClosed.compareAndSet(false, true)) {
            this.lock.lock();
            try {
                Iterator<ByteArray> it = this.byteList.iterator();
                while (it.hasNext()) {
                    ByteArray next = it.next();
                    if (next != EOS) {
                        next.recycle();
                    }
                }
                this.byteList.clear();
                this.byteList = null;
                this.blockIndex = -1;
                this.blockOffset = -1;
                this.contentLength = 0;
                this.lock.unlock();
            } catch (Throwable th) {
                this.lock.unlock();
                throw th;
            }
        }
    }

    public void init(g gVar, int i2) {
        this.contentLength = i2;
        this.seqNo = gVar.f7233e;
        this.rto = gVar.f7232d;
    }

    @Override // anetwork.channel.aidl.ParcelableInputStream
    public int length() throws RemoteException {
        return this.contentLength;
    }

    @Override // anetwork.channel.aidl.ParcelableInputStream
    public int read(byte[] bArr) throws RemoteException {
        return readBytes(bArr, 0, bArr.length);
    }

    @Override // anetwork.channel.aidl.ParcelableInputStream
    public int readByte() throws RemoteException {
        byte b2;
        if (this.isClosed.get()) {
            throw new RuntimeException("Stream is closed");
        }
        this.lock.lock();
        while (true) {
            try {
                try {
                    if (this.blockIndex == this.byteList.size() && !this.newDataArrive.await(this.rto, TimeUnit.MILLISECONDS)) {
                        close();
                        throw new RuntimeException("await timeout.");
                    }
                    ByteArray byteArray = this.byteList.get(this.blockIndex);
                    if (byteArray == EOS) {
                        b2 = -1;
                        break;
                    }
                    if (this.blockOffset < byteArray.getDataLength()) {
                        byte[] buffer = byteArray.getBuffer();
                        int i2 = this.blockOffset;
                        b2 = buffer[i2];
                        this.blockOffset = i2 + 1;
                        break;
                    }
                    recycleCurrentItem();
                    this.blockIndex++;
                    this.blockOffset = 0;
                } catch (InterruptedException unused) {
                    close();
                    throw new RuntimeException("await interrupt");
                }
            } finally {
                this.lock.unlock();
            }
        }
        return b2;
    }

    /* JADX WARN: Finally extract failed */
    @Override // anetwork.channel.aidl.ParcelableInputStream
    public int readBytes(byte[] bArr, int i2, int i3) throws RemoteException {
        int i4;
        if (this.isClosed.get()) {
            throw new RuntimeException("Stream is closed");
        }
        bArr.getClass();
        if (i2 < 0 || i3 < 0 || (i4 = i3 + i2) > bArr.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        this.lock.lock();
        int i5 = i2;
        while (i5 < i4) {
            try {
                try {
                    if (this.blockIndex == this.byteList.size() && !this.newDataArrive.await(this.rto, TimeUnit.MILLISECONDS)) {
                        close();
                        throw new RuntimeException("await timeout.");
                    }
                    ByteArray byteArray = this.byteList.get(this.blockIndex);
                    if (byteArray == EOS) {
                        break;
                    }
                    int dataLength = byteArray.getDataLength() - this.blockOffset;
                    int i6 = i4 - i5;
                    if (dataLength < i6) {
                        System.arraycopy(byteArray.getBuffer(), this.blockOffset, bArr, i5, dataLength);
                        i5 += dataLength;
                        recycleCurrentItem();
                        this.blockIndex++;
                        this.blockOffset = 0;
                    } else {
                        System.arraycopy(byteArray.getBuffer(), this.blockOffset, bArr, i5, i6);
                        this.blockOffset += i6;
                        i5 += i6;
                    }
                } catch (InterruptedException unused) {
                    close();
                    throw new RuntimeException("await interrupt");
                }
            } catch (Throwable th) {
                this.lock.unlock();
                throw th;
            }
        }
        this.lock.unlock();
        int i7 = i5 - i2;
        if (i7 > 0) {
            return i7;
        }
        return -1;
    }

    /* JADX WARN: Finally extract failed */
    @Override // anetwork.channel.aidl.ParcelableInputStream
    public long skip(int i2) throws RemoteException {
        ByteArray byteArray;
        this.lock.lock();
        int i3 = 0;
        while (i3 < i2) {
            try {
                if (this.blockIndex != this.byteList.size() && (byteArray = this.byteList.get(this.blockIndex)) != EOS) {
                    int dataLength = byteArray.getDataLength();
                    int i4 = this.blockOffset;
                    int i5 = i2 - i3;
                    if (dataLength - i4 < i5) {
                        i3 += dataLength - i4;
                        recycleCurrentItem();
                        this.blockIndex++;
                        this.blockOffset = 0;
                    } else {
                        this.blockOffset = i4 + i5;
                        i3 = i2;
                    }
                }
            } catch (Throwable th) {
                this.lock.unlock();
                throw th;
            }
        }
        this.lock.unlock();
        return i3;
    }

    public void write(ByteArray byteArray) {
        if (this.isClosed.get()) {
            return;
        }
        this.lock.lock();
        try {
            this.byteList.add(byteArray);
            this.newDataArrive.signal();
        } finally {
            this.lock.unlock();
        }
    }

    public void writeEnd() {
        write(EOS);
    }
}
