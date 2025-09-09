package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import javax.annotation.CheckForNull;

@Beta
@GwtIncompatible
@ElementTypesAreNonnullByDefault
@J2ktIncompatible
/* loaded from: classes3.dex */
public final class FileBackedOutputStream extends OutputStream {

    @CheckForNull
    @GuardedBy("this")
    private File file;
    private final int fileThreshold;

    @CheckForNull
    @GuardedBy("this")
    private MemoryOutput memory;

    @GuardedBy("this")
    private OutputStream out;
    private final boolean resetOnFinalize;
    private final ByteSource source;

    private static class MemoryOutput extends ByteArrayOutputStream {
        private MemoryOutput() {
        }

        byte[] e() {
            return ((ByteArrayOutputStream) this).buf;
        }

        int getCount() {
            return ((ByteArrayOutputStream) this).count;
        }
    }

    public FileBackedOutputStream(int i2) {
        this(i2, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized InputStream openInputStream() throws IOException {
        if (this.file != null) {
            return new FileInputStream(this.file);
        }
        Objects.requireNonNull(this.memory);
        return new ByteArrayInputStream(this.memory.e(), 0, this.memory.getCount());
    }

    @GuardedBy("this")
    private void update(int i2) throws IOException {
        MemoryOutput memoryOutput = this.memory;
        if (memoryOutput == null || memoryOutput.getCount() + i2 <= this.fileThreshold) {
            return;
        }
        File fileB = TempFileCreator.f14683a.b("FileBackedOutputStream");
        if (this.resetOnFinalize) {
            fileB.deleteOnExit();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileB);
            fileOutputStream.write(this.memory.e(), 0, this.memory.getCount());
            fileOutputStream.flush();
            this.out = fileOutputStream;
            this.file = fileB;
            this.memory = null;
        } catch (IOException e2) {
            fileB.delete();
            throw e2;
        }
    }

    public ByteSource asByteSource() {
        return this.source;
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() throws IOException {
        this.out.close();
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public synchronized void flush() throws IOException {
        this.out.flush();
    }

    public synchronized void reset() throws IOException {
        try {
            close();
            MemoryOutput memoryOutput = this.memory;
            if (memoryOutput == null) {
                this.memory = new MemoryOutput();
            } else {
                memoryOutput.reset();
            }
            this.out = this.memory;
            File file = this.file;
            if (file != null) {
                this.file = null;
                if (!file.delete()) {
                    throw new IOException("Could not delete: " + file);
                }
            }
        } catch (Throwable th) {
            if (this.memory == null) {
                this.memory = new MemoryOutput();
            } else {
                this.memory.reset();
            }
            this.out = this.memory;
            File file2 = this.file;
            if (file2 != null) {
                this.file = null;
                if (!file2.delete()) {
                    throw new IOException("Could not delete: " + file2);
                }
            }
            throw th;
        } finally {
        }
    }

    @Override // java.io.OutputStream
    public synchronized void write(int i2) throws IOException {
        update(1);
        this.out.write(i2);
    }

    public FileBackedOutputStream(int i2, boolean z2) {
        Preconditions.checkArgument(i2 >= 0, "fileThreshold must be non-negative, but was %s", i2);
        this.fileThreshold = i2;
        this.resetOnFinalize = z2;
        MemoryOutput memoryOutput = new MemoryOutput();
        this.memory = memoryOutput;
        this.out = memoryOutput;
        if (z2) {
            this.source = new ByteSource() { // from class: com.google.common.io.FileBackedOutputStream.1
                protected void finalize() {
                    try {
                        FileBackedOutputStream.this.reset();
                    } catch (Throwable th) {
                        th.printStackTrace(System.err);
                    }
                }

                @Override // com.google.common.io.ByteSource
                public InputStream openStream() throws IOException {
                    return FileBackedOutputStream.this.openInputStream();
                }
            };
        } else {
            this.source = new ByteSource() { // from class: com.google.common.io.FileBackedOutputStream.2
                @Override // com.google.common.io.ByteSource
                public InputStream openStream() throws IOException {
                    return FileBackedOutputStream.this.openInputStream();
                }
            };
        }
    }

    @Override // java.io.OutputStream
    public synchronized void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.OutputStream
    public synchronized void write(byte[] bArr, int i2, int i3) throws IOException {
        update(i3);
        this.out.write(bArr, i2, i3);
    }
}
