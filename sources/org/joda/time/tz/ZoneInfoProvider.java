package org.joda.time.tz;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTimeZone;

/* loaded from: classes5.dex */
public class ZoneInfoProvider implements Provider {
    private final File iFileDir;
    private final ClassLoader iLoader;
    private final String iResourcePath;
    private final Map<String, Object> iZoneInfoMap;

    public ZoneInfoProvider(File file) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("No file directory provided");
        }
        if (!file.exists()) {
            throw new IOException("File directory doesn't exist: " + file);
        }
        if (!file.isDirectory()) {
            throw new IOException("File doesn't refer to a directory: " + file);
        }
        this.iFileDir = file;
        this.iResourcePath = null;
        this.iLoader = null;
        this.iZoneInfoMap = loadZoneInfoMap(openResource("ZoneInfoMap"));
    }

    /* JADX WARN: Not initialized variable reg: 1, insn: 0x001a: MOVE (r0 I:??[OBJECT, ARRAY]) = (r1 I:??[OBJECT, ARRAY]) (LINE:27), block:B:9:0x001a */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0032 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private org.joda.time.DateTimeZone loadZoneData(java.lang.String r6) throws java.lang.Throwable {
        /*
            r5 = this;
            r0 = 0
            java.io.InputStream r1 = r5.openResource(r6)     // Catch: java.lang.Throwable -> L1e java.io.IOException -> L20
            org.joda.time.DateTimeZone r2 = org.joda.time.tz.DateTimeZoneBuilder.readFrom(r1, r6)     // Catch: java.lang.Throwable -> L19 java.io.IOException -> L1c
            java.util.Map<java.lang.String, java.lang.Object> r3 = r5.iZoneInfoMap     // Catch: java.lang.Throwable -> L19 java.io.IOException -> L1c
            java.lang.ref.SoftReference r4 = new java.lang.ref.SoftReference     // Catch: java.lang.Throwable -> L19 java.io.IOException -> L1c
            r4.<init>(r2)     // Catch: java.lang.Throwable -> L19 java.io.IOException -> L1c
            r3.put(r6, r4)     // Catch: java.lang.Throwable -> L19 java.io.IOException -> L1c
            if (r1 == 0) goto L18
            r1.close()     // Catch: java.io.IOException -> L18
        L18:
            return r2
        L19:
            r6 = move-exception
            r0 = r1
            goto L30
        L1c:
            r2 = move-exception
            goto L22
        L1e:
            r6 = move-exception
            goto L30
        L20:
            r2 = move-exception
            r1 = r0
        L22:
            r5.a(r2)     // Catch: java.lang.Throwable -> L19
            java.util.Map<java.lang.String, java.lang.Object> r2 = r5.iZoneInfoMap     // Catch: java.lang.Throwable -> L19
            r2.remove(r6)     // Catch: java.lang.Throwable -> L19
            if (r1 == 0) goto L2f
            r1.close()     // Catch: java.io.IOException -> L2f
        L2f:
            return r0
        L30:
            if (r0 == 0) goto L35
            r0.close()     // Catch: java.io.IOException -> L35
        L35:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.joda.time.tz.ZoneInfoProvider.loadZoneData(java.lang.String):org.joda.time.DateTimeZone");
    }

    private static Map<String, Object> loadZoneInfoMap(InputStream inputStream) throws IOException {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        try {
            readZoneInfoMap(dataInputStream, concurrentHashMap);
            concurrentHashMap.put("UTC", new SoftReference(DateTimeZone.UTC));
            return concurrentHashMap;
        } finally {
            try {
                dataInputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    private InputStream openResource(String str) throws IOException {
        if (this.iFileDir != null) {
            return new FileInputStream(new File(this.iFileDir, str));
        }
        String strConcat = this.iResourcePath.concat(str);
        ClassLoader classLoader = this.iLoader;
        InputStream resourceAsStream = classLoader != null ? classLoader.getResourceAsStream(strConcat) : ClassLoader.getSystemResourceAsStream(strConcat);
        if (resourceAsStream != null) {
            return resourceAsStream;
        }
        StringBuilder sb = new StringBuilder(40);
        sb.append("Resource not found: \"");
        sb.append(strConcat);
        sb.append("\" ClassLoader: ");
        ClassLoader classLoader2 = this.iLoader;
        sb.append(classLoader2 != null ? classLoader2.toString() : "system");
        throw new IOException(sb.toString());
    }

    private static void readZoneInfoMap(DataInputStream dataInputStream, Map<String, Object> map) throws IOException {
        int unsignedShort = dataInputStream.readUnsignedShort();
        String[] strArr = new String[unsignedShort];
        for (int i2 = 0; i2 < unsignedShort; i2++) {
            strArr[i2] = dataInputStream.readUTF().intern();
        }
        int unsignedShort2 = dataInputStream.readUnsignedShort();
        for (int i3 = 0; i3 < unsignedShort2; i3++) {
            try {
                map.put(strArr[dataInputStream.readUnsignedShort()], strArr[dataInputStream.readUnsignedShort()]);
            } catch (ArrayIndexOutOfBoundsException unused) {
                throw new IOException("Corrupt zone info map");
            }
        }
    }

    protected void a(Exception exc) {
        Thread threadCurrentThread = Thread.currentThread();
        threadCurrentThread.getThreadGroup().uncaughtException(threadCurrentThread, exc);
    }

    @Override // org.joda.time.tz.Provider
    public Set<String> getAvailableIDs() {
        return new TreeSet(this.iZoneInfoMap.keySet());
    }

    @Override // org.joda.time.tz.Provider
    public DateTimeZone getZone(String str) {
        Object obj;
        if (str == null || (obj = this.iZoneInfoMap.get(str)) == null) {
            return null;
        }
        if (str.equals(obj)) {
            return loadZoneData(str);
        }
        if (!(obj instanceof SoftReference)) {
            return getZone((String) obj);
        }
        DateTimeZone dateTimeZone = (DateTimeZone) ((SoftReference) obj).get();
        return dateTimeZone != null ? dateTimeZone : loadZoneData(str);
    }

    public ZoneInfoProvider(String str) throws IOException {
        this(str, null, false);
    }

    public ZoneInfoProvider(String str, ClassLoader classLoader) throws IOException {
        this(str, classLoader, true);
    }

    private ZoneInfoProvider(String str, ClassLoader classLoader, boolean z2) throws IOException {
        if (str != null) {
            if (!str.endsWith("/")) {
                str = str + IOUtils.DIR_SEPARATOR_UNIX;
            }
            this.iFileDir = null;
            this.iResourcePath = str;
            if (classLoader == null && !z2) {
                classLoader = getClass().getClassLoader();
            }
            this.iLoader = classLoader;
            this.iZoneInfoMap = loadZoneInfoMap(openResource("ZoneInfoMap"));
            return;
        }
        throw new IllegalArgumentException("No resource path provided");
    }
}
