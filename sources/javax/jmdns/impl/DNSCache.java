package javax.jmdns.impl;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.jmdns.impl.constants.DNSRecordClass;
import javax.jmdns.impl.constants.DNSRecordType;

/* loaded from: classes4.dex */
public class DNSCache extends AbstractMap<String, List<? extends DNSEntry>> {
    public static final DNSCache EmptyCache = new _EmptyCache();
    private transient Set<Map.Entry<String, List<? extends DNSEntry>>> _entrySet;

    protected static class _CacheEntry implements Map.Entry<String, List<? extends DNSEntry>> {
        private String _key;
        private List<? extends DNSEntry> _value;

        protected _CacheEntry(String str, List list) {
            this._key = str != null ? str.trim().toLowerCase() : null;
            this._value = list;
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            return getKey().equals(entry.getKey()) && getValue().equals(entry.getValue());
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            String str = this._key;
            if (str == null) {
                return 0;
            }
            return str.hashCode();
        }

        public boolean isEmpty() {
            return getValue().isEmpty();
        }

        public synchronized String toString() {
            StringBuffer stringBuffer;
            try {
                stringBuffer = new StringBuffer(200);
                stringBuffer.append("\n\t\tname '");
                stringBuffer.append(this._key);
                stringBuffer.append("' ");
                List<? extends DNSEntry> list = this._value;
                if (list == null || list.isEmpty()) {
                    stringBuffer.append(" no entries");
                } else {
                    for (DNSEntry dNSEntry : this._value) {
                        stringBuffer.append("\n\t\t\t");
                        stringBuffer.append(dNSEntry.toString());
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
            return stringBuffer.toString();
        }

        @Override // java.util.Map.Entry
        public String getKey() {
            String str = this._key;
            return str != null ? str : "";
        }

        @Override // java.util.Map.Entry
        public List<? extends DNSEntry> getValue() {
            return this._value;
        }

        @Override // java.util.Map.Entry
        public List<? extends DNSEntry> setValue(List<? extends DNSEntry> list) {
            List<? extends DNSEntry> list2 = this._value;
            this._value = list;
            return list2;
        }
    }

    static final class _EmptyCache extends DNSCache {
        _EmptyCache() {
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(Object obj) {
            return false;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsValue(Object obj) {
            return false;
        }

        @Override // javax.jmdns.impl.DNSCache, java.util.AbstractMap, java.util.Map
        public Set<Map.Entry<String, List<? extends DNSEntry>>> entrySet() {
            return Collections.emptySet();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean equals(Object obj) {
            return (obj instanceof Map) && ((Map) obj).size() == 0;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public List<DNSEntry> get(Object obj) {
            return null;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int hashCode() {
            return 0;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean isEmpty() {
            return true;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<String> keySet() {
            return Collections.emptySet();
        }

        @Override // javax.jmdns.impl.DNSCache, java.util.AbstractMap, java.util.Map
        public List<? extends DNSEntry> put(String str, List<? extends DNSEntry> list) {
            return null;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int size() {
            return 0;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Collection<List<? extends DNSEntry>> values() {
            return Collections.emptySet();
        }
    }

    public DNSCache() {
        this(1024);
    }

    private Collection<? extends DNSEntry> _getDNSEntryList(String str) {
        return get(str != null ? str.toLowerCase() : null);
    }

    protected Map.Entry a(String str) {
        String lowerCase = str != null ? str.trim().toLowerCase() : null;
        for (Map.Entry<String, List<? extends DNSEntry>> entry : entrySet()) {
            if (lowerCase != null) {
                if (lowerCase.equals(entry.getKey())) {
                    return entry;
                }
            } else if (entry.getKey() == null) {
                return entry;
            }
        }
        return null;
    }

    public synchronized boolean addDNSEntry(DNSEntry dNSEntry) {
        boolean z2;
        if (dNSEntry != null) {
            try {
                Map.Entry entryA = a(dNSEntry.getKey());
                ArrayList arrayList = entryA != null ? new ArrayList((Collection) entryA.getValue()) : new ArrayList();
                arrayList.add(dNSEntry);
                if (entryA != null) {
                    entryA.setValue(arrayList);
                } else {
                    entrySet().add(new _CacheEntry(dNSEntry.getKey(), arrayList));
                }
                z2 = true;
            } finally {
            }
        } else {
            z2 = false;
        }
        return z2;
    }

    public synchronized Collection<DNSEntry> allValues() {
        ArrayList arrayList;
        arrayList = new ArrayList();
        for (List<? extends DNSEntry> list : values()) {
            if (list != null) {
                arrayList.addAll(list);
            }
        }
        return arrayList;
    }

    @Override // java.util.AbstractMap
    protected Object clone() {
        return new DNSCache(this);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<String, List<? extends DNSEntry>>> entrySet() {
        if (this._entrySet == null) {
            this._entrySet = new HashSet();
        }
        return this._entrySet;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized javax.jmdns.impl.DNSEntry getDNSEntry(javax.jmdns.impl.DNSEntry r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            if (r4 == 0) goto L27
            java.lang.String r0 = r4.getKey()     // Catch: java.lang.Throwable -> L24
            java.util.Collection r0 = r3._getDNSEntryList(r0)     // Catch: java.lang.Throwable -> L24
            if (r0 == 0) goto L27
            java.util.Iterator r0 = r0.iterator()     // Catch: java.lang.Throwable -> L24
        L11:
            boolean r1 = r0.hasNext()     // Catch: java.lang.Throwable -> L24
            if (r1 == 0) goto L27
            java.lang.Object r1 = r0.next()     // Catch: java.lang.Throwable -> L24
            javax.jmdns.impl.DNSEntry r1 = (javax.jmdns.impl.DNSEntry) r1     // Catch: java.lang.Throwable -> L24
            boolean r2 = r1.isSameEntry(r4)     // Catch: java.lang.Throwable -> L24
            if (r2 == 0) goto L11
            goto L28
        L24:
            r4 = move-exception
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L24
            throw r4
        L27:
            r1 = 0
        L28:
            monitor-exit(r3)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: javax.jmdns.impl.DNSCache.getDNSEntry(javax.jmdns.impl.DNSEntry):javax.jmdns.impl.DNSEntry");
    }

    public synchronized Collection<? extends DNSEntry> getDNSEntryList(String str) {
        Collection<? extends DNSEntry> collection_getDNSEntryList;
        try {
            collection_getDNSEntryList = _getDNSEntryList(str);
        } catch (Throwable th) {
            throw th;
        }
        return collection_getDNSEntryList != null ? new ArrayList<>(collection_getDNSEntryList) : Collections.emptyList();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x002e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized boolean removeDNSEntry(javax.jmdns.impl.DNSEntry r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            if (r3 == 0) goto L2e
            java.lang.String r0 = r3.getKey()     // Catch: java.lang.Throwable -> L2b
            java.util.Map$Entry r0 = r2.a(r0)     // Catch: java.lang.Throwable -> L2b
            if (r0 == 0) goto L2e
            java.lang.Object r1 = r0.getValue()     // Catch: java.lang.Throwable -> L2b
            java.util.List r1 = (java.util.List) r1     // Catch: java.lang.Throwable -> L2b
            boolean r3 = r1.remove(r3)     // Catch: java.lang.Throwable -> L2b
            java.lang.Object r1 = r0.getValue()     // Catch: java.lang.Throwable -> L2b
            java.util.List r1 = (java.util.List) r1     // Catch: java.lang.Throwable -> L2b
            boolean r1 = r1.isEmpty()     // Catch: java.lang.Throwable -> L2b
            if (r1 == 0) goto L2f
            java.util.Set r1 = r2.entrySet()     // Catch: java.lang.Throwable -> L2b
            r1.remove(r0)     // Catch: java.lang.Throwable -> L2b
            goto L2f
        L2b:
            r3 = move-exception
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L2b
            throw r3
        L2e:
            r3 = 0
        L2f:
            monitor-exit(r2)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: javax.jmdns.impl.DNSCache.removeDNSEntry(javax.jmdns.impl.DNSEntry):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0050  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized boolean replaceDNSEntry(javax.jmdns.impl.DNSEntry r4, javax.jmdns.impl.DNSEntry r5) {
        /*
            r3 = this;
            monitor-enter(r3)
            if (r4 == 0) goto L50
            if (r5 == 0) goto L50
            java.lang.String r0 = r4.getKey()     // Catch: java.lang.Throwable -> L29
            java.lang.String r1 = r5.getKey()     // Catch: java.lang.Throwable -> L29
            boolean r0 = r0.equals(r1)     // Catch: java.lang.Throwable -> L29
            if (r0 == 0) goto L50
            java.lang.String r0 = r4.getKey()     // Catch: java.lang.Throwable -> L29
            java.util.Map$Entry r0 = r3.a(r0)     // Catch: java.lang.Throwable -> L29
            if (r0 == 0) goto L2b
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L29
            java.lang.Object r2 = r0.getValue()     // Catch: java.lang.Throwable -> L29
            java.util.Collection r2 = (java.util.Collection) r2     // Catch: java.lang.Throwable -> L29
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L29
            goto L30
        L29:
            r4 = move-exception
            goto L4e
        L2b:
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L29
            r1.<init>()     // Catch: java.lang.Throwable -> L29
        L30:
            r1.remove(r5)     // Catch: java.lang.Throwable -> L29
            r1.add(r4)     // Catch: java.lang.Throwable -> L29
            if (r0 == 0) goto L3c
            r0.setValue(r1)     // Catch: java.lang.Throwable -> L29
            goto L4c
        L3c:
            java.util.Set r5 = r3.entrySet()     // Catch: java.lang.Throwable -> L29
            javax.jmdns.impl.DNSCache$_CacheEntry r0 = new javax.jmdns.impl.DNSCache$_CacheEntry     // Catch: java.lang.Throwable -> L29
            java.lang.String r4 = r4.getKey()     // Catch: java.lang.Throwable -> L29
            r0.<init>(r4, r1)     // Catch: java.lang.Throwable -> L29
            r5.add(r0)     // Catch: java.lang.Throwable -> L29
        L4c:
            r4 = 1
            goto L51
        L4e:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L29
            throw r4
        L50:
            r4 = 0
        L51:
            monitor-exit(r3)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: javax.jmdns.impl.DNSCache.replaceDNSEntry(javax.jmdns.impl.DNSEntry, javax.jmdns.impl.DNSEntry):boolean");
    }

    @Override // java.util.AbstractMap
    public synchronized String toString() {
        StringBuffer stringBuffer;
        try {
            stringBuffer = new StringBuffer(2000);
            stringBuffer.append("\t---- cache ----");
            for (Map.Entry<String, List<? extends DNSEntry>> entry : entrySet()) {
                stringBuffer.append("\n\t\t");
                stringBuffer.append(entry.toString());
            }
        } catch (Throwable th) {
            throw th;
        }
        return stringBuffer.toString();
    }

    public DNSCache(DNSCache dNSCache) {
        this(dNSCache != null ? dNSCache.size() : 1024);
        if (dNSCache != null) {
            putAll(dNSCache);
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public List<? extends DNSEntry> put(String str, List<? extends DNSEntry> list) {
        List<? extends DNSEntry> list2;
        synchronized (this) {
            try {
                Map.Entry entryA = a(str);
                if (entryA != null) {
                    list2 = (List) entryA.setValue(list);
                } else {
                    entrySet().add(new _CacheEntry(str, list));
                    list2 = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return list2;
    }

    public DNSCache(int i2) {
        this._entrySet = null;
        this._entrySet = new HashSet(i2);
    }

    public synchronized Collection<? extends DNSEntry> getDNSEntryList(String str, DNSRecordType dNSRecordType, DNSRecordClass dNSRecordClass) {
        Collection<? extends DNSEntry> collectionEmptyList;
        try {
            Collection<? extends DNSEntry> collection_getDNSEntryList = _getDNSEntryList(str);
            if (collection_getDNSEntryList != null) {
                collectionEmptyList = new ArrayList<>(collection_getDNSEntryList);
                Iterator<? extends DNSEntry> it = collectionEmptyList.iterator();
                while (it.hasNext()) {
                    DNSEntry next = it.next();
                    if (!next.getRecordType().equals(dNSRecordType) || (DNSRecordClass.CLASS_ANY != dNSRecordClass && !next.getRecordClass().equals(dNSRecordClass))) {
                        it.remove();
                    }
                }
            } else {
                collectionEmptyList = Collections.emptyList();
            }
        } catch (Throwable th) {
            throw th;
        }
        return collectionEmptyList;
    }

    public synchronized DNSEntry getDNSEntry(String str, DNSRecordType dNSRecordType, DNSRecordClass dNSRecordClass) {
        DNSEntry next;
        Collection<? extends DNSEntry> collection_getDNSEntryList = _getDNSEntryList(str);
        if (collection_getDNSEntryList != null) {
            Iterator<? extends DNSEntry> it = collection_getDNSEntryList.iterator();
            while (it.hasNext()) {
                next = it.next();
                if (next.getRecordType().equals(dNSRecordType) && (DNSRecordClass.CLASS_ANY == dNSRecordClass || next.getRecordClass().equals(dNSRecordClass))) {
                    break;
                }
            }
            next = null;
        } else {
            next = null;
        }
        return next;
    }
}
