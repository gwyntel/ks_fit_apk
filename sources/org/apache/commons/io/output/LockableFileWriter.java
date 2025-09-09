package org.apache.commons.io.output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;

/* loaded from: classes5.dex */
public class LockableFileWriter extends Writer {
    private static final String LCK = ".lck";
    private final File lockFile;
    private final Writer out;

    public LockableFileWriter(String str) throws IOException {
        this(str, false, (String) null);
    }

    private void createLock() throws IOException {
        synchronized (LockableFileWriter.class) {
            try {
                if (!this.lockFile.createNewFile()) {
                    throw new IOException("Can't write file, lock " + this.lockFile.getAbsolutePath() + " exists");
                }
                this.lockFile.deleteOnExit();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private Writer initWriter(File file, Charset charset, boolean z2) throws IOException {
        boolean zExists = file.exists();
        try {
            return new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath(), z2), Charsets.toCharset(charset));
        } catch (IOException | RuntimeException e2) {
            FileUtils.deleteQuietly(this.lockFile);
            if (!zExists) {
                FileUtils.deleteQuietly(file);
            }
            throw e2;
        }
    }

    private void testLockDir(File file) throws IOException {
        if (!file.exists()) {
            throw new IOException("Could not find lockDir: " + file.getAbsolutePath());
        }
        if (file.canWrite()) {
            return;
        }
        throw new IOException("Could not write to lockDir: " + file.getAbsolutePath());
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        try {
            this.out.close();
        } finally {
            this.lockFile.delete();
        }
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() throws IOException {
        this.out.flush();
    }

    @Override // java.io.Writer
    public void write(int i2) throws IOException {
        this.out.write(i2);
    }

    public LockableFileWriter(String str, boolean z2) throws IOException {
        this(str, z2, (String) null);
    }

    @Override // java.io.Writer
    public void write(char[] cArr) throws IOException {
        this.out.write(cArr);
    }

    public LockableFileWriter(String str, boolean z2, String str2) throws IOException {
        this(new File(str), z2, str2);
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i2, int i3) throws IOException {
        this.out.write(cArr, i2, i3);
    }

    public LockableFileWriter(File file) throws IOException {
        this(file, false, (String) null);
    }

    @Override // java.io.Writer
    public void write(String str) throws IOException {
        this.out.write(str);
    }

    public LockableFileWriter(File file, boolean z2) throws IOException {
        this(file, z2, (String) null);
    }

    @Override // java.io.Writer
    public void write(String str, int i2, int i3) throws IOException {
        this.out.write(str, i2, i3);
    }

    @Deprecated
    public LockableFileWriter(File file, boolean z2, String str) throws IOException {
        this(file, Charset.defaultCharset(), z2, str);
    }

    public LockableFileWriter(File file, Charset charset) throws IOException {
        this(file, charset, false, (String) null);
    }

    public LockableFileWriter(File file, String str) throws IOException {
        this(file, str, false, (String) null);
    }

    public LockableFileWriter(File file, Charset charset, boolean z2, String str) throws IOException {
        File absoluteFile = file.getAbsoluteFile();
        if (absoluteFile.getParentFile() != null) {
            FileUtils.forceMkdir(absoluteFile.getParentFile());
        }
        if (!absoluteFile.isDirectory()) {
            File file2 = new File(str == null ? System.getProperty("java.io.tmpdir") : str);
            FileUtils.forceMkdir(file2);
            testLockDir(file2);
            this.lockFile = new File(file2, absoluteFile.getName() + LCK);
            createLock();
            this.out = initWriter(absoluteFile, charset, z2);
            return;
        }
        throw new IOException("File specified is a directory");
    }

    public LockableFileWriter(File file, String str, boolean z2, String str2) throws IOException {
        this(file, Charsets.toCharset(str), z2, str2);
    }
}
