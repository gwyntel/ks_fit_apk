package com.jaredrummler.truetypeparser;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes4.dex */
public class FontFileReader {
    private int current;
    private byte[] file;
    private int fsize;

    public FontFileReader(InputStream inputStream) throws IOException {
        init(inputStream);
    }

    private void init(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[4096];
        while (true) {
            int i2 = inputStream.read(bArr);
            if (i2 == -1) {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                this.file = byteArray;
                this.fsize = byteArray.length;
                this.current = 0;
                return;
            }
            byteArrayOutputStream.write(bArr, 0, i2);
        }
    }

    private byte read() throws IOException {
        int i2 = this.current;
        if (i2 < this.fsize) {
            byte[] bArr = this.file;
            this.current = i2 + 1;
            return bArr[i2];
        }
        throw new EOFException("Reached EOF, file size=" + this.fsize);
    }

    public byte[] getAllBytes() {
        return this.file;
    }

    public int getCurrentPos() {
        return this.current;
    }

    public int getFileSize() {
        return this.fsize;
    }

    public byte readTTFByte() throws IOException {
        return read();
    }

    public int readTTFLong() throws IOException {
        return (int) ((((((readTTFUByte() << 8) + readTTFUByte()) << 8) + readTTFUByte()) << 8) + readTTFUByte());
    }

    public String readTTFString(int i2) throws IOException {
        int i3 = this.current;
        if (i2 + i3 <= this.fsize) {
            byte[] bArr = new byte[i2];
            System.arraycopy(this.file, i3, bArr, 0, i2);
            this.current += i2;
            return new String(bArr, (i2 <= 0 || bArr[0] != 0) ? "ISO-8859-1" : "UTF-16BE");
        }
        throw new EOFException("Reached EOF, file size=" + this.fsize);
    }

    public int readTTFUByte() throws IOException {
        byte b2 = read();
        return b2 < 0 ? b2 + 256 : b2;
    }

    public long readTTFULong() throws IOException {
        return (((((readTTFUByte() << 8) + readTTFUByte()) << 8) + readTTFUByte()) << 8) + readTTFUByte();
    }

    public int readTTFUShort() throws IOException {
        return (readTTFUByte() << 8) + readTTFUByte();
    }

    public void seekSet(long j2) throws IOException {
        if (j2 <= this.fsize && j2 >= 0) {
            this.current = (int) j2;
            return;
        }
        throw new EOFException("Reached EOF, file size=" + this.fsize + " offset=" + j2);
    }

    public void skip(long j2) throws IOException {
        seekSet(this.current + j2);
    }

    public FontFileReader(String str) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(str));
        try {
            init(fileInputStream);
        } finally {
            fileInputStream.close();
        }
    }

    public String readTTFString(int i2, int i3) throws IOException {
        int i4 = this.current;
        if (i2 + i4 <= this.fsize) {
            byte[] bArr = new byte[i2];
            System.arraycopy(this.file, i4, bArr, 0, i2);
            this.current += i2;
            return new String(bArr, "UTF-16BE");
        }
        throw new EOFException("Reached EOF, file size=" + this.fsize);
    }
}
