package com.github.barteksc.pdfviewer;

import android.graphics.RectF;
import androidx.annotation.Nullable;
import com.github.barteksc.pdfviewer.model.PagePart;
import com.github.barteksc.pdfviewer.util.Constants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

/* loaded from: classes3.dex */
class CacheManager {
    private final PriorityQueue<PagePart> activeCache;
    private final PagePartComparator orderComparator;
    private final Object passiveActiveLock = new Object();
    private final PriorityQueue<PagePart> passiveCache;
    private final List<PagePart> thumbnails;

    class PagePartComparator implements Comparator<PagePart> {
        PagePartComparator() {
        }

        @Override // java.util.Comparator
        public int compare(PagePart pagePart, PagePart pagePart2) {
            if (pagePart.getCacheOrder() == pagePart2.getCacheOrder()) {
                return 0;
            }
            return pagePart.getCacheOrder() > pagePart2.getCacheOrder() ? 1 : -1;
        }
    }

    public CacheManager() {
        PagePartComparator pagePartComparator = new PagePartComparator();
        this.orderComparator = pagePartComparator;
        this.activeCache = new PriorityQueue<>(Constants.Cache.CACHE_SIZE, pagePartComparator);
        this.passiveCache = new PriorityQueue<>(Constants.Cache.CACHE_SIZE, pagePartComparator);
        this.thumbnails = new ArrayList();
    }

    private void addWithoutDuplicates(Collection<PagePart> collection, PagePart pagePart) {
        Iterator<PagePart> it = collection.iterator();
        while (it.hasNext()) {
            if (it.next().equals(pagePart)) {
                pagePart.getRenderedBitmap().recycle();
                return;
            }
        }
        collection.add(pagePart);
    }

    @Nullable
    private static PagePart find(PriorityQueue<PagePart> priorityQueue, PagePart pagePart) {
        Iterator<PagePart> it = priorityQueue.iterator();
        while (it.hasNext()) {
            PagePart next = it.next();
            if (next.equals(pagePart)) {
                return next;
            }
        }
        return null;
    }

    private void makeAFreeSpace() {
        synchronized (this.passiveActiveLock) {
            while (this.activeCache.size() + this.passiveCache.size() >= Constants.Cache.CACHE_SIZE && !this.passiveCache.isEmpty()) {
                try {
                    this.passiveCache.poll().getRenderedBitmap().recycle();
                } catch (Throwable th) {
                    throw th;
                }
            }
            while (this.activeCache.size() + this.passiveCache.size() >= Constants.Cache.CACHE_SIZE && !this.activeCache.isEmpty()) {
                this.activeCache.poll().getRenderedBitmap().recycle();
            }
        }
    }

    public void cachePart(PagePart pagePart) {
        synchronized (this.passiveActiveLock) {
            makeAFreeSpace();
            this.activeCache.offer(pagePart);
        }
    }

    public void cacheThumbnail(PagePart pagePart) {
        synchronized (this.thumbnails) {
            while (this.thumbnails.size() >= Constants.Cache.THUMBNAILS_CACHE_SIZE) {
                try {
                    this.thumbnails.remove(0).getRenderedBitmap().recycle();
                } catch (Throwable th) {
                    throw th;
                }
            }
            addWithoutDuplicates(this.thumbnails, pagePart);
        }
    }

    public boolean containsThumbnail(int i2, RectF rectF) {
        PagePart pagePart = new PagePart(i2, null, rectF, true, 0);
        synchronized (this.thumbnails) {
            try {
                Iterator<PagePart> it = this.thumbnails.iterator();
                while (it.hasNext()) {
                    if (it.next().equals(pagePart)) {
                        return true;
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public List<PagePart> getPageParts() {
        ArrayList arrayList;
        synchronized (this.passiveActiveLock) {
            arrayList = new ArrayList(this.passiveCache);
            arrayList.addAll(this.activeCache);
        }
        return arrayList;
    }

    public List<PagePart> getThumbnails() {
        List<PagePart> list;
        synchronized (this.thumbnails) {
            list = this.thumbnails;
        }
        return list;
    }

    public void makeANewSet() {
        synchronized (this.passiveActiveLock) {
            this.passiveCache.addAll(this.activeCache);
            this.activeCache.clear();
        }
    }

    public void recycle() {
        synchronized (this.passiveActiveLock) {
            try {
                Iterator<PagePart> it = this.passiveCache.iterator();
                while (it.hasNext()) {
                    it.next().getRenderedBitmap().recycle();
                }
                this.passiveCache.clear();
                Iterator<PagePart> it2 = this.activeCache.iterator();
                while (it2.hasNext()) {
                    it2.next().getRenderedBitmap().recycle();
                }
                this.activeCache.clear();
            } finally {
            }
        }
        synchronized (this.thumbnails) {
            try {
                Iterator<PagePart> it3 = this.thumbnails.iterator();
                while (it3.hasNext()) {
                    it3.next().getRenderedBitmap().recycle();
                }
                this.thumbnails.clear();
            } finally {
            }
        }
    }

    public boolean upPartIfContained(int i2, RectF rectF, int i3) {
        PagePart pagePart = new PagePart(i2, null, rectF, false, 0);
        synchronized (this.passiveActiveLock) {
            try {
                PagePart pagePartFind = find(this.passiveCache, pagePart);
                boolean z2 = true;
                if (pagePartFind == null) {
                    if (find(this.activeCache, pagePart) == null) {
                        z2 = false;
                    }
                    return z2;
                }
                this.passiveCache.remove(pagePartFind);
                pagePartFind.setCacheOrder(i3);
                this.activeCache.offer(pagePartFind);
                return true;
            } finally {
            }
        }
    }
}
