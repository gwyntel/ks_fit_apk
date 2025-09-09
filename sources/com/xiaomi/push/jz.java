package com.xiaomi.push;

import androidx.core.app.FrameMetricsAggregator;
import java.nio.ByteBuffer;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/* loaded from: classes4.dex */
public final class jz {

    /* renamed from: a, reason: collision with root package name */
    private static final Comparator f24366a = new a();

    private static class a implements Comparator {
        private a() {
        }

        @Override // java.util.Comparator
        public int compare(Object obj, Object obj2) {
            if (obj == null && obj2 == null) {
                return 0;
            }
            if (obj == null) {
                return -1;
            }
            if (obj2 == null) {
                return 1;
            }
            return obj instanceof List ? jz.a((List) obj, (List) obj2) : obj instanceof Set ? jz.a((Set) obj, (Set) obj2) : obj instanceof Map ? jz.a((Map) obj, (Map) obj2) : obj instanceof byte[] ? jz.a((byte[]) obj, (byte[]) obj2) : jz.a((Comparable) obj, (Comparable) obj2);
        }
    }

    public static int a(byte b2, byte b3) {
        if (b2 < b3) {
            return -1;
        }
        return b3 < b2 ? 1 : 0;
    }

    public static int a(int i2, int i3) {
        if (i2 < i3) {
            return -1;
        }
        return i3 < i2 ? 1 : 0;
    }

    public static int a(long j2, long j3) {
        if (j2 < j3) {
            return -1;
        }
        return j3 < j2 ? 1 : 0;
    }

    public static int a(short s2, short s3) {
        if (s2 < s3) {
            return -1;
        }
        return s3 < s2 ? 1 : 0;
    }

    public static int a(boolean z2, boolean z3) {
        return Boolean.valueOf(z2).compareTo(Boolean.valueOf(z3));
    }

    public static int a(String str, String str2) {
        return str.compareTo(str2);
    }

    public static int a(byte[] bArr, byte[] bArr2) {
        int iA = a(bArr.length, bArr2.length);
        if (iA != 0) {
            return iA;
        }
        for (int i2 = 0; i2 < bArr.length; i2++) {
            int iA2 = a(bArr[i2], bArr2[i2]);
            if (iA2 != 0) {
                return iA2;
            }
        }
        return 0;
    }

    public static int a(Comparable comparable, Comparable comparable2) {
        return comparable.compareTo(comparable2);
    }

    public static int a(List list, List list2) {
        int iA = a(list.size(), list2.size());
        if (iA != 0) {
            return iA;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            int iCompare = f24366a.compare(list.get(i2), list2.get(i2));
            if (iCompare != 0) {
                return iCompare;
            }
        }
        return 0;
    }

    public static int a(Set set, Set set2) {
        int iA = a(set.size(), set2.size());
        if (iA != 0) {
            return iA;
        }
        Comparator comparator = f24366a;
        TreeSet treeSet = new TreeSet(comparator);
        treeSet.addAll(set);
        TreeSet treeSet2 = new TreeSet(comparator);
        treeSet2.addAll(set2);
        Iterator it = treeSet.iterator();
        Iterator it2 = treeSet2.iterator();
        while (it.hasNext() && it2.hasNext()) {
            int iCompare = f24366a.compare(it.next(), it2.next());
            if (iCompare != 0) {
                return iCompare;
            }
        }
        return 0;
    }

    public static int a(Map map, Map map2) {
        int iA = a(map.size(), map2.size());
        if (iA != 0) {
            return iA;
        }
        Comparator comparator = f24366a;
        TreeMap treeMap = new TreeMap(comparator);
        treeMap.putAll(map);
        Iterator it = treeMap.entrySet().iterator();
        TreeMap treeMap2 = new TreeMap(comparator);
        treeMap2.putAll(map2);
        Iterator it2 = treeMap2.entrySet().iterator();
        while (it.hasNext() && it2.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Map.Entry entry2 = (Map.Entry) it2.next();
            Comparator comparator2 = f24366a;
            int iCompare = comparator2.compare(entry.getKey(), entry2.getKey());
            if (iCompare != 0) {
                return iCompare;
            }
            int iCompare2 = comparator2.compare(entry.getValue(), entry2.getValue());
            if (iCompare2 != 0) {
                return iCompare2;
            }
        }
        return 0;
    }

    public static void a(ByteBuffer byteBuffer, StringBuilder sb) {
        byte[] bArrArray = byteBuffer.array();
        int iArrayOffset = byteBuffer.arrayOffset();
        int iLimit = byteBuffer.limit();
        int i2 = iLimit - iArrayOffset > 128 ? iArrayOffset + 128 : iLimit;
        for (int i3 = iArrayOffset; i3 < i2; i3++) {
            if (i3 > iArrayOffset) {
                sb.append(" ");
            }
            sb.append(a(bArrArray[i3]));
        }
        if (iLimit != i2) {
            sb.append("...");
        }
    }

    public static String a(byte b2) {
        return Integer.toHexString((b2 | 256) & FrameMetricsAggregator.EVERY_DURATION).toUpperCase().substring(1);
    }

    /* renamed from: a, reason: collision with other method in class */
    public static byte[] m664a(ByteBuffer byteBuffer) {
        if (m663a(byteBuffer)) {
            return byteBuffer.array();
        }
        byte[] bArr = new byte[byteBuffer.remaining()];
        a(byteBuffer, bArr, 0);
        return bArr;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m663a(ByteBuffer byteBuffer) {
        return byteBuffer.hasArray() && byteBuffer.position() == 0 && byteBuffer.arrayOffset() == 0 && byteBuffer.remaining() == byteBuffer.capacity();
    }

    public static int a(ByteBuffer byteBuffer, byte[] bArr, int i2) {
        int iRemaining = byteBuffer.remaining();
        System.arraycopy(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), bArr, i2, iRemaining);
        return iRemaining;
    }

    public static ByteBuffer a(ByteBuffer byteBuffer) {
        return m663a(byteBuffer) ? byteBuffer : ByteBuffer.wrap(m664a(byteBuffer));
    }
}
