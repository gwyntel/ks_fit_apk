package okhttp3.internal.cache;

import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Flushable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okhttp3.internal.io.FileSystem;
import okhttp3.internal.platform.Platform;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

/* loaded from: classes5.dex */
public final class DiskLruCache implements Closeable, Flushable {
    private static final String CLEAN = "CLEAN";
    private static final String DIRTY = "DIRTY";
    private static final String READ = "READ";
    private static final String REMOVE = "REMOVE";

    /* renamed from: l, reason: collision with root package name */
    static final Pattern f26264l = Pattern.compile("[a-z0-9_-]{1,120}");

    /* renamed from: a, reason: collision with root package name */
    final FileSystem f26265a;
    private final int appVersion;

    /* renamed from: b, reason: collision with root package name */
    final File f26266b;

    /* renamed from: c, reason: collision with root package name */
    final int f26267c;

    /* renamed from: d, reason: collision with root package name */
    BufferedSink f26268d;
    private final Executor executor;

    /* renamed from: f, reason: collision with root package name */
    int f26270f;

    /* renamed from: g, reason: collision with root package name */
    boolean f26271g;

    /* renamed from: h, reason: collision with root package name */
    boolean f26272h;

    /* renamed from: i, reason: collision with root package name */
    boolean f26273i;

    /* renamed from: j, reason: collision with root package name */
    boolean f26274j;
    private final File journalFile;
    private final File journalFileBackup;
    private final File journalFileTmp;

    /* renamed from: k, reason: collision with root package name */
    boolean f26275k;
    private long maxSize;
    private long size = 0;

    /* renamed from: e, reason: collision with root package name */
    final LinkedHashMap f26269e = new LinkedHashMap(0, 0.75f, true);
    private long nextSequenceNumber = 0;
    private final Runnable cleanupRunnable = new Runnable() { // from class: okhttp3.internal.cache.DiskLruCache.1
        @Override // java.lang.Runnable
        public void run() {
            synchronized (DiskLruCache.this) {
                DiskLruCache diskLruCache = DiskLruCache.this;
                if ((!diskLruCache.f26272h) || diskLruCache.f26273i) {
                    return;
                }
                try {
                    diskLruCache.h();
                } catch (IOException unused) {
                    DiskLruCache.this.f26274j = true;
                }
                try {
                    if (DiskLruCache.this.e()) {
                        DiskLruCache.this.f();
                        DiskLruCache.this.f26270f = 0;
                    }
                } catch (IOException unused2) {
                    DiskLruCache diskLruCache2 = DiskLruCache.this;
                    diskLruCache2.f26275k = true;
                    diskLruCache2.f26268d = Okio.buffer(Okio.blackhole());
                }
            }
        }
    };

    public final class Editor {

        /* renamed from: a, reason: collision with root package name */
        final Entry f26282a;

        /* renamed from: b, reason: collision with root package name */
        final boolean[] f26283b;
        private boolean done;

        Editor(Entry entry) {
            this.f26282a = entry;
            this.f26283b = entry.f26290e ? null : new boolean[DiskLruCache.this.f26267c];
        }

        void a() {
            if (this.f26282a.f26291f != this) {
                return;
            }
            int i2 = 0;
            while (true) {
                DiskLruCache diskLruCache = DiskLruCache.this;
                if (i2 >= diskLruCache.f26267c) {
                    this.f26282a.f26291f = null;
                    return;
                } else {
                    try {
                        diskLruCache.f26265a.delete(this.f26282a.f26289d[i2]);
                    } catch (IOException unused) {
                    }
                    i2++;
                }
            }
        }

        public void abort() throws IOException {
            synchronized (DiskLruCache.this) {
                try {
                    if (this.done) {
                        throw new IllegalStateException();
                    }
                    if (this.f26282a.f26291f == this) {
                        DiskLruCache.this.a(this, false);
                    }
                    this.done = true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public void abortUnlessCommitted() {
            synchronized (DiskLruCache.this) {
                if (!this.done && this.f26282a.f26291f == this) {
                    try {
                        DiskLruCache.this.a(this, false);
                    } catch (IOException unused) {
                    }
                }
            }
        }

        public void commit() throws IOException {
            synchronized (DiskLruCache.this) {
                try {
                    if (this.done) {
                        throw new IllegalStateException();
                    }
                    if (this.f26282a.f26291f == this) {
                        DiskLruCache.this.a(this, true);
                    }
                    this.done = true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public Sink newSink(int i2) {
            synchronized (DiskLruCache.this) {
                try {
                    if (this.done) {
                        throw new IllegalStateException();
                    }
                    Entry entry = this.f26282a;
                    if (entry.f26291f != this) {
                        return Okio.blackhole();
                    }
                    if (!entry.f26290e) {
                        this.f26283b[i2] = true;
                    }
                    try {
                        return new FaultHidingSink(DiskLruCache.this.f26265a.sink(entry.f26289d[i2])) { // from class: okhttp3.internal.cache.DiskLruCache.Editor.1
                            @Override // okhttp3.internal.cache.FaultHidingSink
                            protected void a(IOException iOException) {
                                synchronized (DiskLruCache.this) {
                                    Editor.this.a();
                                }
                            }
                        };
                    } catch (FileNotFoundException unused) {
                        return Okio.blackhole();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public Source newSource(int i2) {
            synchronized (DiskLruCache.this) {
                try {
                    if (this.done) {
                        throw new IllegalStateException();
                    }
                    Entry entry = this.f26282a;
                    if (!entry.f26290e || entry.f26291f != this) {
                        return null;
                    }
                    try {
                        return DiskLruCache.this.f26265a.source(entry.f26288c[i2]);
                    } catch (FileNotFoundException unused) {
                        return null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    private final class Entry {

        /* renamed from: a, reason: collision with root package name */
        final String f26286a;

        /* renamed from: b, reason: collision with root package name */
        final long[] f26287b;

        /* renamed from: c, reason: collision with root package name */
        final File[] f26288c;

        /* renamed from: d, reason: collision with root package name */
        final File[] f26289d;

        /* renamed from: e, reason: collision with root package name */
        boolean f26290e;

        /* renamed from: f, reason: collision with root package name */
        Editor f26291f;

        /* renamed from: g, reason: collision with root package name */
        long f26292g;

        Entry(String str) {
            this.f26286a = str;
            int i2 = DiskLruCache.this.f26267c;
            this.f26287b = new long[i2];
            this.f26288c = new File[i2];
            this.f26289d = new File[i2];
            StringBuilder sb = new StringBuilder(str);
            sb.append('.');
            int length = sb.length();
            for (int i3 = 0; i3 < DiskLruCache.this.f26267c; i3++) {
                sb.append(i3);
                this.f26288c[i3] = new File(DiskLruCache.this.f26266b, sb.toString());
                sb.append(".tmp");
                this.f26289d[i3] = new File(DiskLruCache.this.f26266b, sb.toString());
                sb.setLength(length);
            }
        }

        private IOException invalidLengths(String[] strArr) throws IOException {
            throw new IOException("unexpected journal line: " + Arrays.toString(strArr));
        }

        void a(String[] strArr) throws IOException {
            if (strArr.length != DiskLruCache.this.f26267c) {
                throw invalidLengths(strArr);
            }
            for (int i2 = 0; i2 < strArr.length; i2++) {
                try {
                    this.f26287b[i2] = Long.parseLong(strArr[i2]);
                } catch (NumberFormatException unused) {
                    throw invalidLengths(strArr);
                }
            }
        }

        Snapshot b() {
            Source source;
            if (!Thread.holdsLock(DiskLruCache.this)) {
                throw new AssertionError();
            }
            Source[] sourceArr = new Source[DiskLruCache.this.f26267c];
            long[] jArr = (long[]) this.f26287b.clone();
            int i2 = 0;
            int i3 = 0;
            while (true) {
                try {
                    DiskLruCache diskLruCache = DiskLruCache.this;
                    if (i3 >= diskLruCache.f26267c) {
                        return diskLruCache.new Snapshot(this.f26286a, this.f26292g, sourceArr, jArr);
                    }
                    sourceArr[i3] = diskLruCache.f26265a.source(this.f26288c[i3]);
                    i3++;
                } catch (FileNotFoundException unused) {
                    while (true) {
                        DiskLruCache diskLruCache2 = DiskLruCache.this;
                        if (i2 >= diskLruCache2.f26267c || (source = sourceArr[i2]) == null) {
                            try {
                                diskLruCache2.g(this);
                                return null;
                            } catch (IOException unused2) {
                                return null;
                            }
                        }
                        Util.closeQuietly(source);
                        i2++;
                    }
                }
            }
        }

        void c(BufferedSink bufferedSink) throws IOException {
            for (long j2 : this.f26287b) {
                bufferedSink.writeByte(32).writeDecimalLong(j2);
            }
        }
    }

    public final class Snapshot implements Closeable {
        private final String key;
        private final long[] lengths;
        private final long sequenceNumber;
        private final Source[] sources;

        Snapshot(String str, long j2, Source[] sourceArr, long[] jArr) {
            this.key = str;
            this.sequenceNumber = j2;
            this.sources = sourceArr;
            this.lengths = jArr;
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            for (Source source : this.sources) {
                Util.closeQuietly(source);
            }
        }

        @Nullable
        public Editor edit() throws IOException {
            return DiskLruCache.this.b(this.key, this.sequenceNumber);
        }

        public long getLength(int i2) {
            return this.lengths[i2];
        }

        public Source getSource(int i2) {
            return this.sources[i2];
        }

        public String key() {
            return this.key;
        }
    }

    DiskLruCache(FileSystem fileSystem, File file, int i2, int i3, long j2, Executor executor) {
        this.f26265a = fileSystem;
        this.f26266b = file;
        this.appVersion = i2;
        this.journalFile = new File(file, "journal");
        this.journalFileTmp = new File(file, "journal.tmp");
        this.journalFileBackup = new File(file, "journal.bkp");
        this.f26267c = i3;
        this.maxSize = j2;
        this.executor = executor;
    }

    private synchronized void checkNotClosed() {
        if (isClosed()) {
            throw new IllegalStateException("cache is closed");
        }
    }

    public static DiskLruCache create(FileSystem fileSystem, File file, int i2, int i3, long j2) {
        if (j2 <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        if (i3 > 0) {
            return new DiskLruCache(fileSystem, file, i2, i3, j2, new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory("OkHttp DiskLruCache", true)));
        }
        throw new IllegalArgumentException("valueCount <= 0");
    }

    private BufferedSink newJournalWriter() throws FileNotFoundException {
        return Okio.buffer(new FaultHidingSink(this.f26265a.appendingSink(this.journalFile)) { // from class: okhttp3.internal.cache.DiskLruCache.2
            @Override // okhttp3.internal.cache.FaultHidingSink
            protected void a(IOException iOException) {
                DiskLruCache.this.f26271g = true;
            }
        });
    }

    private void processJournal() throws IOException {
        this.f26265a.delete(this.journalFileTmp);
        Iterator it = this.f26269e.values().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            int i2 = 0;
            if (entry.f26291f == null) {
                while (i2 < this.f26267c) {
                    this.size += entry.f26287b[i2];
                    i2++;
                }
            } else {
                entry.f26291f = null;
                while (i2 < this.f26267c) {
                    this.f26265a.delete(entry.f26288c[i2]);
                    this.f26265a.delete(entry.f26289d[i2]);
                    i2++;
                }
                it.remove();
            }
        }
    }

    private void readJournal() throws IOException {
        BufferedSource bufferedSourceBuffer = Okio.buffer(this.f26265a.source(this.journalFile));
        try {
            String utf8LineStrict = bufferedSourceBuffer.readUtf8LineStrict();
            String utf8LineStrict2 = bufferedSourceBuffer.readUtf8LineStrict();
            String utf8LineStrict3 = bufferedSourceBuffer.readUtf8LineStrict();
            String utf8LineStrict4 = bufferedSourceBuffer.readUtf8LineStrict();
            String utf8LineStrict5 = bufferedSourceBuffer.readUtf8LineStrict();
            if (!"libcore.io.DiskLruCache".equals(utf8LineStrict) || !"1".equals(utf8LineStrict2) || !Integer.toString(this.appVersion).equals(utf8LineStrict3) || !Integer.toString(this.f26267c).equals(utf8LineStrict4) || !"".equals(utf8LineStrict5)) {
                throw new IOException("unexpected journal header: [" + utf8LineStrict + ", " + utf8LineStrict2 + ", " + utf8LineStrict4 + ", " + utf8LineStrict5 + "]");
            }
            int i2 = 0;
            while (true) {
                try {
                    readJournalLine(bufferedSourceBuffer.readUtf8LineStrict());
                    i2++;
                } catch (EOFException unused) {
                    this.f26270f = i2 - this.f26269e.size();
                    if (bufferedSourceBuffer.exhausted()) {
                        this.f26268d = newJournalWriter();
                    } else {
                        f();
                    }
                    Util.closeQuietly(bufferedSourceBuffer);
                    return;
                }
            }
        } catch (Throwable th) {
            Util.closeQuietly(bufferedSourceBuffer);
            throw th;
        }
    }

    private void readJournalLine(String str) throws IOException {
        String strSubstring;
        int iIndexOf = str.indexOf(32);
        if (iIndexOf == -1) {
            throw new IOException("unexpected journal line: " + str);
        }
        int i2 = iIndexOf + 1;
        int iIndexOf2 = str.indexOf(32, i2);
        if (iIndexOf2 == -1) {
            strSubstring = str.substring(i2);
            if (iIndexOf == 6 && str.startsWith(REMOVE)) {
                this.f26269e.remove(strSubstring);
                return;
            }
        } else {
            strSubstring = str.substring(i2, iIndexOf2);
        }
        Entry entry = (Entry) this.f26269e.get(strSubstring);
        if (entry == null) {
            entry = new Entry(strSubstring);
            this.f26269e.put(strSubstring, entry);
        }
        if (iIndexOf2 != -1 && iIndexOf == 5 && str.startsWith(CLEAN)) {
            String[] strArrSplit = str.substring(iIndexOf2 + 1).split(" ");
            entry.f26290e = true;
            entry.f26291f = null;
            entry.a(strArrSplit);
            return;
        }
        if (iIndexOf2 == -1 && iIndexOf == 5 && str.startsWith(DIRTY)) {
            entry.f26291f = new Editor(entry);
            return;
        }
        if (iIndexOf2 == -1 && iIndexOf == 4 && str.startsWith(READ)) {
            return;
        }
        throw new IOException("unexpected journal line: " + str);
    }

    private void validateKey(String str) {
        if (f26264l.matcher(str).matches()) {
            return;
        }
        throw new IllegalArgumentException("keys must match regex [a-z0-9_-]{1,120}: \"" + str + "\"");
    }

    synchronized void a(Editor editor, boolean z2) {
        Entry entry = editor.f26282a;
        if (entry.f26291f != editor) {
            throw new IllegalStateException();
        }
        if (z2 && !entry.f26290e) {
            for (int i2 = 0; i2 < this.f26267c; i2++) {
                if (!editor.f26283b[i2]) {
                    editor.abort();
                    throw new IllegalStateException("Newly created entry didn't create value for index " + i2);
                }
                if (!this.f26265a.exists(entry.f26289d[i2])) {
                    editor.abort();
                    return;
                }
            }
        }
        for (int i3 = 0; i3 < this.f26267c; i3++) {
            File file = entry.f26289d[i3];
            if (!z2) {
                this.f26265a.delete(file);
            } else if (this.f26265a.exists(file)) {
                File file2 = entry.f26288c[i3];
                this.f26265a.rename(file, file2);
                long j2 = entry.f26287b[i3];
                long size = this.f26265a.size(file2);
                entry.f26287b[i3] = size;
                this.size = (this.size - j2) + size;
            }
        }
        this.f26270f++;
        entry.f26291f = null;
        if (entry.f26290e || z2) {
            entry.f26290e = true;
            this.f26268d.writeUtf8(CLEAN).writeByte(32);
            this.f26268d.writeUtf8(entry.f26286a);
            entry.c(this.f26268d);
            this.f26268d.writeByte(10);
            if (z2) {
                long j3 = this.nextSequenceNumber;
                this.nextSequenceNumber = 1 + j3;
                entry.f26292g = j3;
            }
        } else {
            this.f26269e.remove(entry.f26286a);
            this.f26268d.writeUtf8(REMOVE).writeByte(32);
            this.f26268d.writeUtf8(entry.f26286a);
            this.f26268d.writeByte(10);
        }
        this.f26268d.flush();
        if (this.size > this.maxSize || e()) {
            this.executor.execute(this.cleanupRunnable);
        }
    }

    synchronized Editor b(String str, long j2) {
        initialize();
        checkNotClosed();
        validateKey(str);
        Entry entry = (Entry) this.f26269e.get(str);
        if (j2 != -1 && (entry == null || entry.f26292g != j2)) {
            return null;
        }
        if (entry != null && entry.f26291f != null) {
            return null;
        }
        if (!this.f26274j && !this.f26275k) {
            this.f26268d.writeUtf8(DIRTY).writeByte(32).writeUtf8(str).writeByte(10);
            this.f26268d.flush();
            if (this.f26271g) {
                return null;
            }
            if (entry == null) {
                entry = new Entry(str);
                this.f26269e.put(str, entry);
            }
            Editor editor = new Editor(entry);
            entry.f26291f = editor;
            return editor;
        }
        this.executor.execute(this.cleanupRunnable);
        return null;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() throws IOException {
        try {
            if (this.f26272h && !this.f26273i) {
                for (Entry entry : (Entry[]) this.f26269e.values().toArray(new Entry[this.f26269e.size()])) {
                    Editor editor = entry.f26291f;
                    if (editor != null) {
                        editor.abort();
                    }
                }
                h();
                this.f26268d.close();
                this.f26268d = null;
                this.f26273i = true;
                return;
            }
            this.f26273i = true;
        } catch (Throwable th) {
            throw th;
        }
    }

    public void delete() throws IOException {
        close();
        this.f26265a.deleteContents(this.f26266b);
    }

    boolean e() {
        int i2 = this.f26270f;
        return i2 >= 2000 && i2 >= this.f26269e.size();
    }

    @Nullable
    public Editor edit(String str) throws IOException {
        return b(str, -1L);
    }

    public synchronized void evictAll() throws IOException {
        try {
            initialize();
            for (Entry entry : (Entry[]) this.f26269e.values().toArray(new Entry[this.f26269e.size()])) {
                g(entry);
            }
            this.f26274j = false;
        } catch (Throwable th) {
            throw th;
        }
    }

    synchronized void f() {
        try {
            BufferedSink bufferedSink = this.f26268d;
            if (bufferedSink != null) {
                bufferedSink.close();
            }
            BufferedSink bufferedSinkBuffer = Okio.buffer(this.f26265a.sink(this.journalFileTmp));
            try {
                bufferedSinkBuffer.writeUtf8("libcore.io.DiskLruCache").writeByte(10);
                bufferedSinkBuffer.writeUtf8("1").writeByte(10);
                bufferedSinkBuffer.writeDecimalLong(this.appVersion).writeByte(10);
                bufferedSinkBuffer.writeDecimalLong(this.f26267c).writeByte(10);
                bufferedSinkBuffer.writeByte(10);
                for (Entry entry : this.f26269e.values()) {
                    if (entry.f26291f != null) {
                        bufferedSinkBuffer.writeUtf8(DIRTY).writeByte(32);
                        bufferedSinkBuffer.writeUtf8(entry.f26286a);
                        bufferedSinkBuffer.writeByte(10);
                    } else {
                        bufferedSinkBuffer.writeUtf8(CLEAN).writeByte(32);
                        bufferedSinkBuffer.writeUtf8(entry.f26286a);
                        entry.c(bufferedSinkBuffer);
                        bufferedSinkBuffer.writeByte(10);
                    }
                }
                bufferedSinkBuffer.close();
                if (this.f26265a.exists(this.journalFile)) {
                    this.f26265a.rename(this.journalFile, this.journalFileBackup);
                }
                this.f26265a.rename(this.journalFileTmp, this.journalFile);
                this.f26265a.delete(this.journalFileBackup);
                this.f26268d = newJournalWriter();
                this.f26271g = false;
                this.f26275k = false;
            } catch (Throwable th) {
                bufferedSinkBuffer.close();
                throw th;
            }
        } catch (Throwable th2) {
            throw th2;
        }
    }

    @Override // java.io.Flushable
    public synchronized void flush() throws IOException {
        if (this.f26272h) {
            checkNotClosed();
            h();
            this.f26268d.flush();
        }
    }

    boolean g(Entry entry) throws IOException {
        Editor editor = entry.f26291f;
        if (editor != null) {
            editor.a();
        }
        for (int i2 = 0; i2 < this.f26267c; i2++) {
            this.f26265a.delete(entry.f26288c[i2]);
            long j2 = this.size;
            long[] jArr = entry.f26287b;
            this.size = j2 - jArr[i2];
            jArr[i2] = 0;
        }
        this.f26270f++;
        this.f26268d.writeUtf8(REMOVE).writeByte(32).writeUtf8(entry.f26286a).writeByte(10);
        this.f26269e.remove(entry.f26286a);
        if (e()) {
            this.executor.execute(this.cleanupRunnable);
        }
        return true;
    }

    public synchronized Snapshot get(String str) throws IOException {
        initialize();
        checkNotClosed();
        validateKey(str);
        Entry entry = (Entry) this.f26269e.get(str);
        if (entry != null && entry.f26290e) {
            Snapshot snapshotB = entry.b();
            if (snapshotB == null) {
                return null;
            }
            this.f26270f++;
            this.f26268d.writeUtf8(READ).writeByte(32).writeUtf8(str).writeByte(10);
            if (e()) {
                this.executor.execute(this.cleanupRunnable);
            }
            return snapshotB;
        }
        return null;
    }

    public File getDirectory() {
        return this.f26266b;
    }

    public synchronized long getMaxSize() {
        return this.maxSize;
    }

    void h() throws IOException {
        while (this.size > this.maxSize) {
            g((Entry) this.f26269e.values().iterator().next());
        }
        this.f26274j = false;
    }

    public synchronized void initialize() throws IOException {
        try {
            if (this.f26272h) {
                return;
            }
            if (this.f26265a.exists(this.journalFileBackup)) {
                if (this.f26265a.exists(this.journalFile)) {
                    this.f26265a.delete(this.journalFileBackup);
                } else {
                    this.f26265a.rename(this.journalFileBackup, this.journalFile);
                }
            }
            if (this.f26265a.exists(this.journalFile)) {
                try {
                    readJournal();
                    processJournal();
                    this.f26272h = true;
                    return;
                } catch (IOException e2) {
                    Platform.get().log(5, "DiskLruCache " + this.f26266b + " is corrupt: " + e2.getMessage() + ", removing", e2);
                    try {
                        delete();
                        this.f26273i = false;
                    } catch (Throwable th) {
                        this.f26273i = false;
                        throw th;
                    }
                }
            }
            f();
            this.f26272h = true;
        } catch (Throwable th2) {
            throw th2;
        }
    }

    public synchronized boolean isClosed() {
        return this.f26273i;
    }

    public synchronized boolean remove(String str) throws IOException {
        initialize();
        checkNotClosed();
        validateKey(str);
        Entry entry = (Entry) this.f26269e.get(str);
        if (entry == null) {
            return false;
        }
        boolean zG = g(entry);
        if (zG && this.size <= this.maxSize) {
            this.f26274j = false;
        }
        return zG;
    }

    public synchronized void setMaxSize(long j2) {
        this.maxSize = j2;
        if (this.f26272h) {
            this.executor.execute(this.cleanupRunnable);
        }
    }

    public synchronized long size() throws IOException {
        initialize();
        return this.size;
    }

    public synchronized Iterator<Snapshot> snapshots() throws IOException {
        initialize();
        return new Iterator<Snapshot>() { // from class: okhttp3.internal.cache.DiskLruCache.3

            /* renamed from: a, reason: collision with root package name */
            final Iterator f26278a;

            /* renamed from: b, reason: collision with root package name */
            Snapshot f26279b;

            /* renamed from: c, reason: collision with root package name */
            Snapshot f26280c;

            {
                this.f26278a = new ArrayList(DiskLruCache.this.f26269e.values()).iterator();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                Snapshot snapshotB;
                if (this.f26279b != null) {
                    return true;
                }
                synchronized (DiskLruCache.this) {
                    try {
                        if (DiskLruCache.this.f26273i) {
                            return false;
                        }
                        while (this.f26278a.hasNext()) {
                            Entry entry = (Entry) this.f26278a.next();
                            if (entry.f26290e && (snapshotB = entry.b()) != null) {
                                this.f26279b = snapshotB;
                                return true;
                            }
                        }
                        return false;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // java.util.Iterator
            public void remove() {
                Snapshot snapshot = this.f26280c;
                if (snapshot == null) {
                    throw new IllegalStateException("remove() before next()");
                }
                try {
                    DiskLruCache.this.remove(snapshot.key);
                } catch (IOException unused) {
                } catch (Throwable th) {
                    this.f26280c = null;
                    throw th;
                }
                this.f26280c = null;
            }

            @Override // java.util.Iterator
            public Snapshot next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Snapshot snapshot = this.f26279b;
                this.f26280c = snapshot;
                this.f26279b = null;
                return snapshot;
            }
        };
    }
}
