package com.tencent.bugly.proguard;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public final class i {

    /* renamed from: a, reason: collision with root package name */
    private StringBuilder f21100a;

    /* renamed from: b, reason: collision with root package name */
    private int f21101b;

    public i(StringBuilder sb, int i2) {
        this.f21100a = sb;
        this.f21101b = i2;
    }

    private void a(String str) {
        for (int i2 = 0; i2 < this.f21101b; i2++) {
            this.f21100a.append('\t');
        }
        if (str != null) {
            StringBuilder sb = this.f21100a;
            sb.append(str);
            sb.append(": ");
        }
    }

    public final i a(boolean z2, String str) {
        a(str);
        StringBuilder sb = this.f21100a;
        sb.append(z2 ? 'T' : 'F');
        sb.append('\n');
        return this;
    }

    public final i a(byte b2, String str) {
        a(str);
        StringBuilder sb = this.f21100a;
        sb.append((int) b2);
        sb.append('\n');
        return this;
    }

    private i a(char c2, String str) {
        a(str);
        StringBuilder sb = this.f21100a;
        sb.append(c2);
        sb.append('\n');
        return this;
    }

    public final i a(short s2, String str) {
        a(str);
        StringBuilder sb = this.f21100a;
        sb.append((int) s2);
        sb.append('\n');
        return this;
    }

    public final i a(int i2, String str) {
        a(str);
        StringBuilder sb = this.f21100a;
        sb.append(i2);
        sb.append('\n');
        return this;
    }

    public final i a(long j2, String str) {
        a(str);
        StringBuilder sb = this.f21100a;
        sb.append(j2);
        sb.append('\n');
        return this;
    }

    private i a(float f2, String str) {
        a(str);
        StringBuilder sb = this.f21100a;
        sb.append(f2);
        sb.append('\n');
        return this;
    }

    private i a(double d2, String str) {
        a(str);
        StringBuilder sb = this.f21100a;
        sb.append(d2);
        sb.append('\n');
        return this;
    }

    public final i a(String str, String str2) {
        a(str2);
        if (str == null) {
            this.f21100a.append("null\n");
        } else {
            StringBuilder sb = this.f21100a;
            sb.append(str);
            sb.append('\n');
        }
        return this;
    }

    public final i a(byte[] bArr, String str) {
        a(str);
        if (bArr == null) {
            this.f21100a.append("null\n");
            return this;
        }
        if (bArr.length == 0) {
            StringBuilder sb = this.f21100a;
            sb.append(bArr.length);
            sb.append(", []\n");
            return this;
        }
        StringBuilder sb2 = this.f21100a;
        sb2.append(bArr.length);
        sb2.append(", [\n");
        i iVar = new i(this.f21100a, this.f21101b + 1);
        for (byte b2 : bArr) {
            iVar.a(b2, (String) null);
        }
        a(']', (String) null);
        return this;
    }

    private i a(short[] sArr, String str) {
        a(str);
        if (sArr == null) {
            this.f21100a.append("null\n");
            return this;
        }
        if (sArr.length == 0) {
            StringBuilder sb = this.f21100a;
            sb.append(sArr.length);
            sb.append(", []\n");
            return this;
        }
        StringBuilder sb2 = this.f21100a;
        sb2.append(sArr.length);
        sb2.append(", [\n");
        i iVar = new i(this.f21100a, this.f21101b + 1);
        for (short s2 : sArr) {
            iVar.a(s2, (String) null);
        }
        a(']', (String) null);
        return this;
    }

    private i a(int[] iArr, String str) {
        a(str);
        if (iArr == null) {
            this.f21100a.append("null\n");
            return this;
        }
        if (iArr.length == 0) {
            StringBuilder sb = this.f21100a;
            sb.append(iArr.length);
            sb.append(", []\n");
            return this;
        }
        StringBuilder sb2 = this.f21100a;
        sb2.append(iArr.length);
        sb2.append(", [\n");
        i iVar = new i(this.f21100a, this.f21101b + 1);
        for (int i2 : iArr) {
            iVar.a(i2, (String) null);
        }
        a(']', (String) null);
        return this;
    }

    private i a(long[] jArr, String str) {
        a(str);
        if (jArr == null) {
            this.f21100a.append("null\n");
            return this;
        }
        if (jArr.length == 0) {
            StringBuilder sb = this.f21100a;
            sb.append(jArr.length);
            sb.append(", []\n");
            return this;
        }
        StringBuilder sb2 = this.f21100a;
        sb2.append(jArr.length);
        sb2.append(", [\n");
        i iVar = new i(this.f21100a, this.f21101b + 1);
        for (long j2 : jArr) {
            iVar.a(j2, (String) null);
        }
        a(']', (String) null);
        return this;
    }

    private i a(float[] fArr, String str) {
        a(str);
        if (fArr == null) {
            this.f21100a.append("null\n");
            return this;
        }
        if (fArr.length == 0) {
            StringBuilder sb = this.f21100a;
            sb.append(fArr.length);
            sb.append(", []\n");
            return this;
        }
        StringBuilder sb2 = this.f21100a;
        sb2.append(fArr.length);
        sb2.append(", [\n");
        i iVar = new i(this.f21100a, this.f21101b + 1);
        for (float f2 : fArr) {
            iVar.a(f2, (String) null);
        }
        a(']', (String) null);
        return this;
    }

    private i a(double[] dArr, String str) {
        a(str);
        if (dArr == null) {
            this.f21100a.append("null\n");
            return this;
        }
        if (dArr.length == 0) {
            StringBuilder sb = this.f21100a;
            sb.append(dArr.length);
            sb.append(", []\n");
            return this;
        }
        StringBuilder sb2 = this.f21100a;
        sb2.append(dArr.length);
        sb2.append(", [\n");
        i iVar = new i(this.f21100a, this.f21101b + 1);
        for (double d2 : dArr) {
            iVar.a(d2, (String) null);
        }
        a(']', (String) null);
        return this;
    }

    public final <K, V> i a(Map<K, V> map, String str) {
        a(str);
        if (map == null) {
            this.f21100a.append("null\n");
            return this;
        }
        if (map.isEmpty()) {
            StringBuilder sb = this.f21100a;
            sb.append(map.size());
            sb.append(", {}\n");
            return this;
        }
        StringBuilder sb2 = this.f21100a;
        sb2.append(map.size());
        sb2.append(", {\n");
        i iVar = new i(this.f21100a, this.f21101b + 1);
        i iVar2 = new i(this.f21100a, this.f21101b + 2);
        for (Map.Entry<K, V> entry : map.entrySet()) {
            iVar.a('(', (String) null);
            iVar2.a((i) entry.getKey(), (String) null);
            iVar2.a((i) entry.getValue(), (String) null);
            iVar.a(')', (String) null);
        }
        a('}', (String) null);
        return this;
    }

    private <T> i a(T[] tArr, String str) {
        a(str);
        if (tArr == null) {
            this.f21100a.append("null\n");
            return this;
        }
        if (tArr.length == 0) {
            StringBuilder sb = this.f21100a;
            sb.append(tArr.length);
            sb.append(", []\n");
            return this;
        }
        StringBuilder sb2 = this.f21100a;
        sb2.append(tArr.length);
        sb2.append(", [\n");
        i iVar = new i(this.f21100a, this.f21101b + 1);
        for (T t2 : tArr) {
            iVar.a((i) t2, (String) null);
        }
        a(']', (String) null);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T> i a(Collection<T> collection, String str) {
        if (collection == null) {
            a(str);
            this.f21100a.append("null\t");
            return this;
        }
        return a(collection.toArray(), str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T> i a(T t2, String str) {
        if (t2 == 0) {
            this.f21100a.append("null\n");
        } else if (t2 instanceof Byte) {
            a(((Byte) t2).byteValue(), str);
        } else if (t2 instanceof Boolean) {
            a(((Boolean) t2).booleanValue(), str);
        } else if (t2 instanceof Short) {
            a(((Short) t2).shortValue(), str);
        } else if (t2 instanceof Integer) {
            a(((Integer) t2).intValue(), str);
        } else if (t2 instanceof Long) {
            a(((Long) t2).longValue(), str);
        } else if (t2 instanceof Float) {
            a(((Float) t2).floatValue(), str);
        } else if (t2 instanceof Double) {
            a(((Double) t2).doubleValue(), str);
        } else if (t2 instanceof String) {
            a((String) t2, str);
        } else if (t2 instanceof Map) {
            a((Map) t2, str);
        } else if (t2 instanceof List) {
            a((Collection) t2, str);
        } else if (t2 instanceof m) {
            a((m) t2, str);
        } else if (t2 instanceof byte[]) {
            a((byte[]) t2, str);
        } else if (t2 instanceof boolean[]) {
            a((i) t2, str);
        } else if (t2 instanceof short[]) {
            a((short[]) t2, str);
        } else if (t2 instanceof int[]) {
            a((int[]) t2, str);
        } else if (t2 instanceof long[]) {
            a((long[]) t2, str);
        } else if (t2 instanceof float[]) {
            a((float[]) t2, str);
        } else if (t2 instanceof double[]) {
            a((double[]) t2, str);
        } else if (t2.getClass().isArray()) {
            a((Object[]) t2, str);
        } else {
            throw new j("write object error: unsupport type.");
        }
        return this;
    }

    public final i a(m mVar, String str) {
        a('{', str);
        if (mVar == null) {
            StringBuilder sb = this.f21100a;
            sb.append('\t');
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            mVar.a(this.f21100a, this.f21101b + 1);
        }
        a('}', (String) null);
        return this;
    }
}
