package org.apache.commons.lang;

/* loaded from: classes5.dex */
class IntHashMap {
    private transient int count;
    private final float loadFactor;
    private transient Entry[] table;
    private int threshold;

    private static class Entry {

        /* renamed from: a, reason: collision with root package name */
        final int f26621a;

        /* renamed from: b, reason: collision with root package name */
        final int f26622b;

        /* renamed from: c, reason: collision with root package name */
        Object f26623c;

        /* renamed from: d, reason: collision with root package name */
        Entry f26624d;

        protected Entry(int i2, int i3, Object obj, Entry entry) {
            this.f26621a = i2;
            this.f26622b = i3;
            this.f26623c = obj;
            this.f26624d = entry;
        }
    }

    public IntHashMap() {
        this(20, 0.75f);
    }

    protected void a() {
        Entry[] entryArr = this.table;
        int length = entryArr.length;
        int i2 = (length * 2) + 1;
        Entry[] entryArr2 = new Entry[i2];
        this.threshold = (int) (i2 * this.loadFactor);
        this.table = entryArr2;
        while (true) {
            int i3 = length - 1;
            if (length <= 0) {
                return;
            }
            Entry entry = entryArr[i3];
            while (entry != null) {
                Entry entry2 = entry.f26624d;
                int i4 = (entry.f26621a & Integer.MAX_VALUE) % i2;
                entry.f26624d = entryArr2[i4];
                entryArr2[i4] = entry;
                entry = entry2;
            }
            length = i3;
        }
    }

    public synchronized void clear() {
        try {
            Entry[] entryArr = this.table;
            int length = entryArr.length;
            while (true) {
                length--;
                if (length >= 0) {
                    entryArr[length] = null;
                } else {
                    this.count = 0;
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public boolean contains(Object obj) {
        obj.getClass();
        Entry[] entryArr = this.table;
        int length = entryArr.length;
        while (true) {
            int i2 = length - 1;
            if (length <= 0) {
                return false;
            }
            for (Entry entry = entryArr[i2]; entry != null; entry = entry.f26624d) {
                if (entry.f26623c.equals(obj)) {
                    return true;
                }
            }
            length = i2;
        }
    }

    public boolean containsKey(int i2) {
        Entry[] entryArr = this.table;
        for (Entry entry = entryArr[(Integer.MAX_VALUE & i2) % entryArr.length]; entry != null; entry = entry.f26624d) {
            if (entry.f26621a == i2) {
                return true;
            }
        }
        return false;
    }

    public boolean containsValue(Object obj) {
        return contains(obj);
    }

    public Object get(int i2) {
        Entry[] entryArr = this.table;
        for (Entry entry = entryArr[(Integer.MAX_VALUE & i2) % entryArr.length]; entry != null; entry = entry.f26624d) {
            if (entry.f26621a == i2) {
                return entry.f26623c;
            }
        }
        return null;
    }

    public boolean isEmpty() {
        return this.count == 0;
    }

    public Object put(int i2, Object obj) {
        Entry[] entryArr = this.table;
        int i3 = Integer.MAX_VALUE & i2;
        int length = i3 % entryArr.length;
        for (Entry entry = entryArr[length]; entry != null; entry = entry.f26624d) {
            if (entry.f26621a == i2) {
                Object obj2 = entry.f26623c;
                entry.f26623c = obj;
                return obj2;
            }
        }
        if (this.count >= this.threshold) {
            a();
            entryArr = this.table;
            length = i3 % entryArr.length;
        }
        entryArr[length] = new Entry(i2, i2, obj, entryArr[length]);
        this.count++;
        return null;
    }

    public Object remove(int i2) {
        Entry[] entryArr = this.table;
        int length = (Integer.MAX_VALUE & i2) % entryArr.length;
        Entry entry = null;
        for (Entry entry2 = entryArr[length]; entry2 != null; entry2 = entry2.f26624d) {
            if (entry2.f26621a == i2) {
                if (entry != null) {
                    entry.f26624d = entry2.f26624d;
                } else {
                    entryArr[length] = entry2.f26624d;
                }
                this.count--;
                Object obj = entry2.f26623c;
                entry2.f26623c = null;
                return obj;
            }
            entry = entry2;
        }
        return null;
    }

    public int size() {
        return this.count;
    }

    public IntHashMap(int i2) {
        this(i2, 0.75f);
    }

    public IntHashMap(int i2, float f2) {
        if (i2 < 0) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal Capacity: ");
            stringBuffer.append(i2);
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        if (f2 > 0.0f) {
            i2 = i2 == 0 ? 1 : i2;
            this.loadFactor = f2;
            this.table = new Entry[i2];
            this.threshold = (int) (i2 * f2);
            return;
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Illegal Load: ");
        stringBuffer2.append(f2);
        throw new IllegalArgumentException(stringBuffer2.toString());
    }
}
