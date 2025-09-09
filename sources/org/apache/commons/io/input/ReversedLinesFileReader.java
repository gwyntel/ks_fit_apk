package org.apache.commons.io.input;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

/* loaded from: classes5.dex */
public class ReversedLinesFileReader implements Closeable {
    private final int avoidNewlineSplitBufferSize;
    private final int blockSize;
    private final int byteDecrement;
    private FilePart currentFilePart;
    private final Charset encoding;
    private final byte[][] newLineSequences;
    private final RandomAccessFile randomAccessFile;
    private final long totalBlockCount;
    private final long totalByteLength;
    private boolean trailingNewlineOfFileSkipped;

    private class FilePart {
        private int currentLastBytePos;
        private final byte[] data;
        private byte[] leftOver;
        private final long no;

        private void createLeftOver() {
            int i2 = this.currentLastBytePos + 1;
            if (i2 > 0) {
                byte[] bArr = new byte[i2];
                this.leftOver = bArr;
                System.arraycopy(this.data, 0, bArr, 0, i2);
            } else {
                this.leftOver = null;
            }
            this.currentLastBytePos = -1;
        }

        private int getNewLineMatchByteCount(byte[] bArr, int i2) {
            for (byte[] bArr2 : ReversedLinesFileReader.this.newLineSequences) {
                boolean z2 = true;
                for (int length = bArr2.length - 1; length >= 0; length--) {
                    int length2 = (i2 + length) - (bArr2.length - 1);
                    z2 &= length2 >= 0 && bArr[length2] == bArr2[length];
                }
                if (z2) {
                    return bArr2.length;
                }
            }
            return 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x006a, code lost:
        
            r1 = null;
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x006b, code lost:
        
            if (r0 == false) goto L34;
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x006d, code lost:
        
            r0 = r9.leftOver;
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x006f, code lost:
        
            if (r0 == null) goto L35;
         */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x0071, code lost:
        
            r1 = new java.lang.String(r0, r9.f26600a.encoding);
            r9.leftOver = null;
         */
        /* JADX WARN: Code restructure failed: missing block: B:28:0x007e, code lost:
        
            return r1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:34:?, code lost:
        
            return r1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:?, code lost:
        
            return r1;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public java.lang.String readLine() throws java.io.IOException {
            /*
                r9 = this;
                long r0 = r9.no
                r2 = 1
                int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                r1 = 0
                r2 = 1
                if (r0 != 0) goto Lc
                r0 = r2
                goto Ld
            Lc:
                r0 = r1
            Ld:
                int r3 = r9.currentLastBytePos
            Lf:
                r4 = -1
                r5 = 0
                if (r3 <= r4) goto L6a
                if (r0 != 0) goto L21
                org.apache.commons.io.input.ReversedLinesFileReader r4 = org.apache.commons.io.input.ReversedLinesFileReader.this
                int r4 = org.apache.commons.io.input.ReversedLinesFileReader.d(r4)
                if (r3 >= r4) goto L21
                r9.createLeftOver()
                goto L6a
            L21:
                byte[] r4 = r9.data
                int r4 = r9.getNewLineMatchByteCount(r4, r3)
                if (r4 <= 0) goto L5e
                int r6 = r3 + 1
                int r7 = r9.currentLastBytePos
                int r7 = r7 - r6
                int r7 = r7 + r2
                if (r7 < 0) goto L47
                byte[] r2 = new byte[r7]
                byte[] r8 = r9.data
                java.lang.System.arraycopy(r8, r6, r2, r1, r7)
                java.lang.String r1 = new java.lang.String
                org.apache.commons.io.input.ReversedLinesFileReader r6 = org.apache.commons.io.input.ReversedLinesFileReader.this
                java.nio.charset.Charset r6 = org.apache.commons.io.input.ReversedLinesFileReader.c(r6)
                r1.<init>(r2, r6)
                int r3 = r3 - r4
                r9.currentLastBytePos = r3
                goto L6b
            L47:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "Unexpected negative line length="
                r1.append(r2)
                r1.append(r7)
                java.lang.String r1 = r1.toString()
                r0.<init>(r1)
                throw r0
            L5e:
                org.apache.commons.io.input.ReversedLinesFileReader r4 = org.apache.commons.io.input.ReversedLinesFileReader.this
                int r4 = org.apache.commons.io.input.ReversedLinesFileReader.e(r4)
                int r3 = r3 - r4
                if (r3 >= 0) goto Lf
                r9.createLeftOver()
            L6a:
                r1 = r5
            L6b:
                if (r0 == 0) goto L7e
                byte[] r0 = r9.leftOver
                if (r0 == 0) goto L7e
                java.lang.String r1 = new java.lang.String
                org.apache.commons.io.input.ReversedLinesFileReader r2 = org.apache.commons.io.input.ReversedLinesFileReader.this
                java.nio.charset.Charset r2 = org.apache.commons.io.input.ReversedLinesFileReader.c(r2)
                r1.<init>(r0, r2)
                r9.leftOver = r5
            L7e:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.io.input.ReversedLinesFileReader.FilePart.readLine():java.lang.String");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public FilePart rollOver() throws IOException {
            if (this.currentLastBytePos > -1) {
                throw new IllegalStateException("Current currentLastCharPos unexpectedly positive... last readLine() should have returned something! currentLastCharPos=" + this.currentLastBytePos);
            }
            long j2 = this.no;
            if (j2 > 1) {
                ReversedLinesFileReader reversedLinesFileReader = ReversedLinesFileReader.this;
                return reversedLinesFileReader.new FilePart(j2 - 1, reversedLinesFileReader.blockSize, this.leftOver);
            }
            if (this.leftOver == null) {
                return null;
            }
            throw new IllegalStateException("Unexpected leftover of the last block: leftOverOfThisFilePart=" + new String(this.leftOver, ReversedLinesFileReader.this.encoding));
        }

        private FilePart(long j2, int i2, byte[] bArr) throws IOException {
            this.no = j2;
            byte[] bArr2 = new byte[(bArr != null ? bArr.length : 0) + i2];
            this.data = bArr2;
            long j3 = (j2 - 1) * ReversedLinesFileReader.this.blockSize;
            if (j2 > 0) {
                ReversedLinesFileReader.this.randomAccessFile.seek(j3);
                if (ReversedLinesFileReader.this.randomAccessFile.read(bArr2, 0, i2) != i2) {
                    throw new IllegalStateException("Count of requested bytes and actually read bytes don't match");
                }
            }
            if (bArr != null) {
                System.arraycopy(bArr, 0, bArr2, i2, bArr.length);
            }
            this.currentLastBytePos = bArr2.length - 1;
            this.leftOver = null;
        }
    }

    @Deprecated
    public ReversedLinesFileReader(File file) throws IOException {
        this(file, 4096, Charset.defaultCharset());
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.randomAccessFile.close();
    }

    public String readLine() throws IOException {
        String line = this.currentFilePart.readLine();
        while (line == null) {
            FilePart filePartRollOver = this.currentFilePart.rollOver();
            this.currentFilePart = filePartRollOver;
            if (filePartRollOver == null) {
                break;
            }
            line = filePartRollOver.readLine();
        }
        if (!"".equals(line) || this.trailingNewlineOfFileSkipped) {
            return line;
        }
        this.trailingNewlineOfFileSkipped = true;
        return readLine();
    }

    public ReversedLinesFileReader(File file, Charset charset) throws IOException {
        this(file, 4096, charset);
    }

    public ReversedLinesFileReader(File file, int i2, Charset charset) throws IOException {
        int i3;
        this.trailingNewlineOfFileSkipped = false;
        this.blockSize = i2;
        this.encoding = charset;
        Charset charset2 = Charsets.toCharset(charset);
        if (charset2.newEncoder().maxBytesPerChar() != 1.0f && charset2 != StandardCharsets.UTF_8 && charset2 != Charset.forName("Shift_JIS") && charset2 != Charset.forName("windows-31j") && charset2 != Charset.forName("x-windows-949") && charset2 != Charset.forName("gbk") && charset2 != Charset.forName("x-windows-950")) {
            if (charset2 != StandardCharsets.UTF_16BE && charset2 != StandardCharsets.UTF_16LE) {
                if (charset2 == StandardCharsets.UTF_16) {
                    throw new UnsupportedEncodingException("For UTF-16, you need to specify the byte order (use UTF-16BE or UTF-16LE)");
                }
                throw new UnsupportedEncodingException("Encoding " + charset + " is not supported yet (feel free to submit a patch)");
            }
            this.byteDecrement = 2;
        } else {
            this.byteDecrement = 1;
        }
        byte[][] bArr = {IOUtils.LINE_SEPARATOR_WINDOWS.getBytes(charset), "\n".getBytes(charset), "\r".getBytes(charset)};
        this.newLineSequences = bArr;
        this.avoidNewlineSplitBufferSize = bArr[0].length;
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        this.randomAccessFile = randomAccessFile;
        long length = randomAccessFile.length();
        this.totalByteLength = length;
        long j2 = i2;
        int i4 = (int) (length % j2);
        if (i4 > 0) {
            this.totalBlockCount = (length / j2) + 1;
        } else {
            this.totalBlockCount = length / j2;
            i3 = length > 0 ? i2 : i3;
            this.currentFilePart = new FilePart(this.totalBlockCount, i3, null);
        }
        i3 = i4;
        this.currentFilePart = new FilePart(this.totalBlockCount, i3, null);
    }

    public ReversedLinesFileReader(File file, int i2, String str) throws IOException {
        this(file, i2, Charsets.toCharset(str));
    }
}
