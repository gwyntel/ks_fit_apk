package org.apache.commons.io.input;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.io.ByteOrderMark;

/* loaded from: classes5.dex */
public class BOMInputStream extends ProxyInputStream {
    private static final Comparator<ByteOrderMark> ByteOrderMarkLengthComparator = new Comparator<ByteOrderMark>() { // from class: org.apache.commons.io.input.BOMInputStream.1
        @Override // java.util.Comparator
        public int compare(ByteOrderMark byteOrderMark, ByteOrderMark byteOrderMark2) {
            int length = byteOrderMark.length();
            int length2 = byteOrderMark2.length();
            if (length > length2) {
                return -1;
            }
            return length2 > length ? 1 : 0;
        }
    };
    private final List<ByteOrderMark> boms;
    private ByteOrderMark byteOrderMark;
    private int fbIndex;
    private int fbLength;
    private int[] firstBytes;
    private final boolean include;
    private int markFbIndex;
    private boolean markedAtStart;

    public BOMInputStream(InputStream inputStream) {
        this(inputStream, false, ByteOrderMark.UTF_8);
    }

    private ByteOrderMark find() {
        for (ByteOrderMark byteOrderMark : this.boms) {
            if (matches(byteOrderMark)) {
                return byteOrderMark;
            }
        }
        return null;
    }

    private boolean matches(ByteOrderMark byteOrderMark) {
        for (int i2 = 0; i2 < byteOrderMark.length(); i2++) {
            if (byteOrderMark.get(i2) != this.firstBytes[i2]) {
                return false;
            }
        }
        return true;
    }

    private int readFirstBytes() throws IOException {
        getBOM();
        int i2 = this.fbIndex;
        if (i2 >= this.fbLength) {
            return -1;
        }
        int[] iArr = this.firstBytes;
        this.fbIndex = i2 + 1;
        return iArr[i2];
    }

    public ByteOrderMark getBOM() throws IOException {
        if (this.firstBytes == null) {
            this.fbLength = 0;
            this.firstBytes = new int[this.boms.get(0).length()];
            int i2 = 0;
            while (true) {
                int[] iArr = this.firstBytes;
                if (i2 >= iArr.length) {
                    break;
                }
                iArr[i2] = ((FilterInputStream) this).in.read();
                this.fbLength++;
                if (this.firstBytes[i2] < 0) {
                    break;
                }
                i2++;
            }
            ByteOrderMark byteOrderMarkFind = find();
            this.byteOrderMark = byteOrderMarkFind;
            if (byteOrderMarkFind != null && !this.include) {
                if (byteOrderMarkFind.length() < this.firstBytes.length) {
                    this.fbIndex = this.byteOrderMark.length();
                } else {
                    this.fbLength = 0;
                }
            }
        }
        return this.byteOrderMark;
    }

    public String getBOMCharsetName() throws IOException {
        getBOM();
        ByteOrderMark byteOrderMark = this.byteOrderMark;
        if (byteOrderMark == null) {
            return null;
        }
        return byteOrderMark.getCharsetName();
    }

    public boolean hasBOM() throws IOException {
        return getBOM() != null;
    }

    @Override // org.apache.commons.io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
    public synchronized void mark(int i2) {
        this.markFbIndex = this.fbIndex;
        this.markedAtStart = this.firstBytes == null;
        ((FilterInputStream) this).in.mark(i2);
    }

    @Override // org.apache.commons.io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        int firstBytes = readFirstBytes();
        return firstBytes >= 0 ? firstBytes : ((FilterInputStream) this).in.read();
    }

    @Override // org.apache.commons.io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
    public synchronized void reset() throws IOException {
        try {
            this.fbIndex = this.markFbIndex;
            if (this.markedAtStart) {
                this.firstBytes = null;
            }
            ((FilterInputStream) this).in.reset();
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // org.apache.commons.io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
    public long skip(long j2) throws IOException {
        long j3;
        int i2 = 0;
        while (true) {
            j3 = i2;
            if (j2 <= j3 || readFirstBytes() < 0) {
                break;
            }
            i2++;
        }
        return ((FilterInputStream) this).in.skip(j2 - j3) + j3;
    }

    public BOMInputStream(InputStream inputStream, boolean z2) {
        this(inputStream, z2, ByteOrderMark.UTF_8);
    }

    public boolean hasBOM(ByteOrderMark byteOrderMark) throws IOException {
        if (this.boms.contains(byteOrderMark)) {
            getBOM();
            ByteOrderMark byteOrderMark2 = this.byteOrderMark;
            return byteOrderMark2 != null && byteOrderMark2.equals(byteOrderMark);
        }
        throw new IllegalArgumentException("Stream not configure to detect " + byteOrderMark);
    }

    public BOMInputStream(InputStream inputStream, ByteOrderMark... byteOrderMarkArr) {
        this(inputStream, false, byteOrderMarkArr);
    }

    @Override // org.apache.commons.io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        int firstBytes = 0;
        int i4 = 0;
        while (i3 > 0 && firstBytes >= 0) {
            firstBytes = readFirstBytes();
            if (firstBytes >= 0) {
                bArr[i2] = (byte) (firstBytes & 255);
                i3--;
                i4++;
                i2++;
            }
        }
        int i5 = ((FilterInputStream) this).in.read(bArr, i2, i3);
        if (i5 >= 0) {
            return i4 + i5;
        }
        if (i4 > 0) {
            return i4;
        }
        return -1;
    }

    public BOMInputStream(InputStream inputStream, boolean z2, ByteOrderMark... byteOrderMarkArr) {
        super(inputStream);
        if (byteOrderMarkArr != null && byteOrderMarkArr.length != 0) {
            this.include = z2;
            List<ByteOrderMark> listAsList = Arrays.asList(byteOrderMarkArr);
            Collections.sort(listAsList, ByteOrderMarkLengthComparator);
            this.boms = listAsList;
            return;
        }
        throw new IllegalArgumentException("No BOMs specified");
    }

    @Override // org.apache.commons.io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }
}
