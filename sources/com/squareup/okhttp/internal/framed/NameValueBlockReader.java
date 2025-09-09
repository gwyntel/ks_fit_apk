package com.squareup.okhttp.internal.framed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.ForwardingSource;
import okio.InflaterSource;
import okio.Okio;

/* loaded from: classes4.dex */
class NameValueBlockReader {
    private int compressedLimit;
    private final InflaterSource inflaterSource;
    private final BufferedSource source;

    public NameValueBlockReader(BufferedSource bufferedSource) {
        InflaterSource inflaterSource = new InflaterSource(new ForwardingSource(bufferedSource) { // from class: com.squareup.okhttp.internal.framed.NameValueBlockReader.1
            @Override // okio.ForwardingSource, okio.Source
            public long read(Buffer buffer, long j2) throws IOException {
                if (NameValueBlockReader.this.compressedLimit == 0) {
                    return -1L;
                }
                long j3 = super.read(buffer, Math.min(j2, NameValueBlockReader.this.compressedLimit));
                if (j3 == -1) {
                    return -1L;
                }
                NameValueBlockReader.this.compressedLimit = (int) (r8.compressedLimit - j3);
                return j3;
            }
        }, new Inflater() { // from class: com.squareup.okhttp.internal.framed.NameValueBlockReader.2
            @Override // java.util.zip.Inflater
            public int inflate(byte[] bArr, int i2, int i3) throws DataFormatException {
                int iInflate = super.inflate(bArr, i2, i3);
                if (iInflate != 0 || !needsDictionary()) {
                    return iInflate;
                }
                setDictionary(Spdy3.f19983a);
                return super.inflate(bArr, i2, i3);
            }
        });
        this.inflaterSource = inflaterSource;
        this.source = Okio.buffer(inflaterSource);
    }

    private void doneReading() throws IOException {
        if (this.compressedLimit > 0) {
            this.inflaterSource.refill();
            if (this.compressedLimit == 0) {
                return;
            }
            throw new IOException("compressedLimit > 0: " + this.compressedLimit);
        }
    }

    private ByteString readByteString() throws IOException {
        return this.source.readByteString(this.source.readInt());
    }

    public void close() throws IOException {
        this.source.close();
    }

    public List<Header> readNameValueBlock(int i2) throws IOException {
        this.compressedLimit += i2;
        int i3 = this.source.readInt();
        if (i3 < 0) {
            throw new IOException("numberOfPairs < 0: " + i3);
        }
        if (i3 > 1024) {
            throw new IOException("numberOfPairs > 1024: " + i3);
        }
        ArrayList arrayList = new ArrayList(i3);
        for (int i4 = 0; i4 < i3; i4++) {
            ByteString asciiLowercase = readByteString().toAsciiLowercase();
            ByteString byteString = readByteString();
            if (asciiLowercase.size() == 0) {
                throw new IOException("name.size == 0");
            }
            arrayList.add(new Header(asciiLowercase, byteString));
        }
        doneReading();
        return arrayList;
    }
}
