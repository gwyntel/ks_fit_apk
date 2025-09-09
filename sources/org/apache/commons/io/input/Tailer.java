package org.apache.commons.io.input;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import org.apache.commons.io.FileUtils;

/* loaded from: classes5.dex */
public class Tailer implements Runnable {
    private static final int DEFAULT_BUFSIZE = 4096;
    private static final Charset DEFAULT_CHARSET = Charset.defaultCharset();
    private static final int DEFAULT_DELAY_MILLIS = 1000;
    private static final String RAF_MODE = "r";
    private final Charset cset;
    private final long delayMillis;
    private final boolean end;
    private final File file;
    private final byte[] inbuf;
    private final TailerListener listener;
    private final boolean reOpen;
    private volatile boolean run;

    public Tailer(File file, TailerListener tailerListener) {
        this(file, tailerListener, 1000L);
    }

    public static Tailer create(File file, TailerListener tailerListener, long j2, boolean z2, int i2) {
        return create(file, tailerListener, j2, z2, false, i2);
    }

    private long readLines(RandomAccessFile randomAccessFile) throws IOException {
        int i2;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(64);
        try {
            long filePointer = randomAccessFile.getFilePointer();
            long filePointer2 = filePointer;
            boolean z2 = false;
            while (a() && (i2 = randomAccessFile.read(this.inbuf)) != -1) {
                for (int i3 = 0; i3 < i2; i3++) {
                    byte b2 = this.inbuf[i3];
                    if (b2 == 10) {
                        this.listener.handle(new String(byteArrayOutputStream.toByteArray(), this.cset));
                        byteArrayOutputStream.reset();
                        filePointer = i3 + filePointer2 + 1;
                        z2 = false;
                    } else if (b2 != 13) {
                        if (z2) {
                            this.listener.handle(new String(byteArrayOutputStream.toByteArray(), this.cset));
                            byteArrayOutputStream.reset();
                            filePointer = i3 + filePointer2 + 1;
                            z2 = false;
                        }
                        byteArrayOutputStream.write(b2);
                    } else {
                        if (z2) {
                            byteArrayOutputStream.write(13);
                        }
                        z2 = true;
                    }
                }
                filePointer2 = randomAccessFile.getFilePointer();
            }
            randomAccessFile.seek(filePointer);
            TailerListener tailerListener = this.listener;
            if (tailerListener instanceof TailerListenerAdapter) {
                ((TailerListenerAdapter) tailerListener).endOfFileReached();
            }
            byteArrayOutputStream.close();
            return filePointer;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable th3) {
                    th.addSuppressed(th3);
                }
                throw th2;
            }
        }
    }

    protected boolean a() {
        return this.run;
    }

    public long getDelay() {
        return this.delayMillis;
    }

    public File getFile() {
        return this.file;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        RandomAccessFile randomAccessFile;
        long lines;
        long jLastModified;
        RandomAccessFile randomAccessFile2 = null;
        long jLastModified2 = 0;
        long length = 0;
        while (a() && randomAccessFile2 == null) {
            try {
                try {
                    try {
                        randomAccessFile2 = new RandomAccessFile(this.file, "r");
                    } catch (FileNotFoundException unused) {
                        this.listener.fileNotFound();
                    }
                    if (randomAccessFile2 == null) {
                        Thread.sleep(this.delayMillis);
                    } else {
                        length = this.end ? this.file.length() : 0L;
                        jLastModified2 = this.file.lastModified();
                        randomAccessFile2.seek(length);
                    }
                } catch (InterruptedException e2) {
                    e = e2;
                } catch (Exception e3) {
                    e = e3;
                }
            } catch (Throwable th) {
                th = th;
            }
        }
        while (a()) {
            boolean zIsFileNewer = FileUtils.isFileNewer(this.file, jLastModified2);
            long length2 = this.file.length();
            if (length2 < length) {
                this.listener.fileRotated();
                try {
                    randomAccessFile = new RandomAccessFile(this.file, "r");
                } catch (Throwable th2) {
                    th = th2;
                    randomAccessFile = randomAccessFile2;
                }
                try {
                    try {
                        readLines(randomAccessFile2);
                    } catch (IOException e4) {
                        this.listener.handle(e4);
                    }
                    if (randomAccessFile2 != null) {
                        try {
                            try {
                                randomAccessFile2.close();
                            } catch (InterruptedException e5) {
                                e = e5;
                                randomAccessFile2 = randomAccessFile;
                                Thread.currentThread().interrupt();
                                this.listener.handle(e);
                                if (randomAccessFile2 != null) {
                                    try {
                                        randomAccessFile2.close();
                                    } catch (IOException e6) {
                                        e = e6;
                                        this.listener.handle(e);
                                        stop();
                                    }
                                }
                                stop();
                            } catch (Exception e7) {
                                e = e7;
                                randomAccessFile2 = randomAccessFile;
                                this.listener.handle(e);
                                if (randomAccessFile2 != null) {
                                    try {
                                        randomAccessFile2.close();
                                    } catch (IOException e8) {
                                        e = e8;
                                        this.listener.handle(e);
                                        stop();
                                    }
                                }
                                stop();
                            } catch (Throwable th3) {
                                th = th3;
                                randomAccessFile2 = randomAccessFile;
                                if (randomAccessFile2 != null) {
                                    try {
                                        randomAccessFile2.close();
                                    } catch (IOException e9) {
                                        this.listener.handle(e9);
                                    }
                                }
                                stop();
                                throw th;
                            }
                        } catch (FileNotFoundException unused2) {
                            length = 0;
                            randomAccessFile2 = randomAccessFile;
                            this.listener.fileNotFound();
                            Thread.sleep(this.delayMillis);
                        }
                    }
                    length = 0;
                    randomAccessFile2 = randomAccessFile;
                } catch (Throwable th4) {
                    th = th4;
                    try {
                        throw th;
                    } catch (Throwable th5) {
                        if (randomAccessFile2 != null) {
                            try {
                                randomAccessFile2.close();
                            } catch (Throwable th6) {
                                try {
                                    th.addSuppressed(th6);
                                } catch (FileNotFoundException unused3) {
                                    randomAccessFile2 = randomAccessFile;
                                    this.listener.fileNotFound();
                                    Thread.sleep(this.delayMillis);
                                }
                            }
                        }
                        throw th5;
                    }
                }
            } else {
                if (length2 > length) {
                    lines = readLines(randomAccessFile2);
                    jLastModified = this.file.lastModified();
                } else {
                    if (zIsFileNewer) {
                        randomAccessFile2.seek(0L);
                        lines = readLines(randomAccessFile2);
                        jLastModified = this.file.lastModified();
                    }
                    if (this.reOpen && randomAccessFile2 != null) {
                        randomAccessFile2.close();
                    }
                    Thread.sleep(this.delayMillis);
                    if (!a() && this.reOpen) {
                        randomAccessFile = new RandomAccessFile(this.file, "r");
                        randomAccessFile.seek(length);
                        randomAccessFile2 = randomAccessFile;
                    }
                }
                long j2 = lines;
                jLastModified2 = jLastModified;
                length = j2;
                if (this.reOpen) {
                    randomAccessFile2.close();
                }
                Thread.sleep(this.delayMillis);
                if (!a()) {
                }
            }
        }
        if (randomAccessFile2 != null) {
            try {
                randomAccessFile2.close();
            } catch (IOException e10) {
                e = e10;
                this.listener.handle(e);
                stop();
            }
        }
        stop();
    }

    public void stop() {
        this.run = false;
    }

    public Tailer(File file, TailerListener tailerListener, long j2) {
        this(file, tailerListener, j2, false);
    }

    public static Tailer create(File file, TailerListener tailerListener, long j2, boolean z2, boolean z3, int i2) {
        return create(file, DEFAULT_CHARSET, tailerListener, j2, z2, z3, i2);
    }

    public Tailer(File file, TailerListener tailerListener, long j2, boolean z2) {
        this(file, tailerListener, j2, z2, 4096);
    }

    public static Tailer create(File file, Charset charset, TailerListener tailerListener, long j2, boolean z2, boolean z3, int i2) {
        Tailer tailer = new Tailer(file, charset, tailerListener, j2, z2, z3, i2);
        Thread thread = new Thread(tailer);
        thread.setDaemon(true);
        thread.start();
        return tailer;
    }

    public Tailer(File file, TailerListener tailerListener, long j2, boolean z2, boolean z3) {
        this(file, tailerListener, j2, z2, z3, 4096);
    }

    public Tailer(File file, TailerListener tailerListener, long j2, boolean z2, int i2) {
        this(file, tailerListener, j2, z2, false, i2);
    }

    public Tailer(File file, TailerListener tailerListener, long j2, boolean z2, boolean z3, int i2) {
        this(file, DEFAULT_CHARSET, tailerListener, j2, z2, z3, i2);
    }

    public Tailer(File file, Charset charset, TailerListener tailerListener, long j2, boolean z2, boolean z3, int i2) {
        this.run = true;
        this.file = file;
        this.delayMillis = j2;
        this.end = z2;
        this.inbuf = new byte[i2];
        this.listener = tailerListener;
        tailerListener.init(this);
        this.reOpen = z3;
        this.cset = charset;
    }

    public static Tailer create(File file, TailerListener tailerListener, long j2, boolean z2) {
        return create(file, tailerListener, j2, z2, 4096);
    }

    public static Tailer create(File file, TailerListener tailerListener, long j2, boolean z2, boolean z3) {
        return create(file, tailerListener, j2, z2, z3, 4096);
    }

    public static Tailer create(File file, TailerListener tailerListener, long j2) {
        return create(file, tailerListener, j2, false);
    }

    public static Tailer create(File file, TailerListener tailerListener) {
        return create(file, tailerListener, 1000L, false);
    }
}
