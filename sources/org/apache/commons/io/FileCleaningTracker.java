package org.apache.commons.io;

import java.io.File;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/* loaded from: classes5.dex */
public class FileCleaningTracker {

    /* renamed from: a, reason: collision with root package name */
    ReferenceQueue f26594a = new ReferenceQueue();

    /* renamed from: b, reason: collision with root package name */
    final Collection f26595b = Collections.synchronizedSet(new HashSet());

    /* renamed from: c, reason: collision with root package name */
    final List f26596c = Collections.synchronizedList(new ArrayList());

    /* renamed from: d, reason: collision with root package name */
    volatile boolean f26597d = false;

    /* renamed from: e, reason: collision with root package name */
    Thread f26598e;

    private final class Reaper extends Thread {
        Reaper() {
            super("File Reaper");
            setPriority(10);
            setDaemon(true);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (true) {
                if (FileCleaningTracker.this.f26597d && FileCleaningTracker.this.f26595b.size() <= 0) {
                    return;
                }
                try {
                    Tracker tracker = (Tracker) FileCleaningTracker.this.f26594a.remove();
                    FileCleaningTracker.this.f26595b.remove(tracker);
                    if (!tracker.delete()) {
                        FileCleaningTracker.this.f26596c.add(tracker.getPath());
                    }
                    tracker.clear();
                } catch (InterruptedException unused) {
                }
            }
        }
    }

    private static final class Tracker extends PhantomReference<Object> {
        private final FileDeleteStrategy deleteStrategy;
        private final String path;

        Tracker(String str, FileDeleteStrategy fileDeleteStrategy, Object obj, ReferenceQueue referenceQueue) {
            super(obj, referenceQueue);
            this.path = str;
            this.deleteStrategy = fileDeleteStrategy == null ? FileDeleteStrategy.NORMAL : fileDeleteStrategy;
        }

        public boolean delete() {
            return this.deleteStrategy.deleteQuietly(new File(this.path));
        }

        public String getPath() {
            return this.path;
        }
    }

    private synchronized void addTracker(String str, Object obj, FileDeleteStrategy fileDeleteStrategy) {
        try {
            if (this.f26597d) {
                throw new IllegalStateException("No new trackers can be added once exitWhenFinished() is called");
            }
            if (this.f26598e == null) {
                Reaper reaper = new Reaper();
                this.f26598e = reaper;
                reaper.start();
            }
            this.f26595b.add(new Tracker(str, fileDeleteStrategy, obj, this.f26594a));
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void exitWhenFinished() {
        this.f26597d = true;
        Thread thread = this.f26598e;
        if (thread != null) {
            synchronized (thread) {
                this.f26598e.interrupt();
            }
        }
    }

    public List<String> getDeleteFailures() {
        return this.f26596c;
    }

    public int getTrackCount() {
        return this.f26595b.size();
    }

    public void track(File file, Object obj) {
        track(file, obj, (FileDeleteStrategy) null);
    }

    public void track(File file, Object obj, FileDeleteStrategy fileDeleteStrategy) {
        if (file == null) {
            throw new NullPointerException("The file must not be null");
        }
        addTracker(file.getPath(), obj, fileDeleteStrategy);
    }

    public void track(String str, Object obj) {
        track(str, obj, (FileDeleteStrategy) null);
    }

    public void track(String str, Object obj, FileDeleteStrategy fileDeleteStrategy) {
        if (str != null) {
            addTracker(str, obj, fileDeleteStrategy);
            return;
        }
        throw new NullPointerException("The path must not be null");
    }
}
