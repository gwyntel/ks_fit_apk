package b.c;

import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseIntArray;
import java.util.LinkedHashMap;
import java.util.Map;
import meshprovisioner.utils.MeshParserUtils;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public static final String f7373a = "" + a.class.getSimpleName();

    /* renamed from: b, reason: collision with root package name */
    public static volatile a f7374b;

    /* renamed from: c, reason: collision with root package name */
    public Map<String, b> f7375c = new LinkedHashMap();

    /* renamed from: d, reason: collision with root package name */
    public C0016a f7376d = new C0016a();

    /* renamed from: b.c.a$a, reason: collision with other inner class name */
    public class C0016a {

        /* renamed from: a, reason: collision with root package name */
        public SparseIntArray f7377a = new SparseIntArray();

        /* renamed from: b, reason: collision with root package name */
        public Map<String, SparseArray<C0017a>> f7378b = new LinkedHashMap();

        /* renamed from: b.c.a$a$a, reason: collision with other inner class name */
        class C0017a {

            /* renamed from: a, reason: collision with root package name */
            public int f7380a;

            /* renamed from: b, reason: collision with root package name */
            public byte[] f7381b;

            /* renamed from: c, reason: collision with root package name */
            public int f7382c;

            /* renamed from: d, reason: collision with root package name */
            public boolean f7383d = false;

            /* renamed from: e, reason: collision with root package name */
            public long f7384e = System.currentTimeMillis();

            public C0017a(int i2, byte[] bArr) {
                this.f7380a = i2;
                this.f7381b = bArr;
                this.f7382c = C0016a.this.f7377a.get(i2);
            }

            public void a() {
                this.f7383d = true;
            }
        }

        public C0016a() {
            this.f7377a.put(33282, 33284);
            this.f7377a.put(33350, -32187);
            this.f7377a.put(33438, -32187);
            this.f7377a.put(32795, -32737);
            this.f7377a.put(32796, -32737);
        }

        public void a(byte[] bArr, byte[] bArr2, int i2, byte[] bArr3) {
            if (this.f7377a.get(i2) == 0) {
                return;
            }
            String strB = a.this.b(bArr, bArr2);
            if (TextUtils.isEmpty(strB)) {
                return;
            }
            SparseArray<C0017a> sparseArray = this.f7378b.get(strB);
            if (sparseArray == null) {
                sparseArray = new SparseArray<>();
                this.f7378b.put(strB, sparseArray);
            }
            C0017a c0017a = new C0017a(i2, bArr3);
            sparseArray.put(a.this.a(c0017a.f7382c, bArr3), c0017a);
            a.a.a.a.b.m.a.a(a.f7373a, String.format("Measure on message sent, to: %s, opcode: %s", MeshParserUtils.bytesToHex(bArr2, true), MeshParserUtils.bytesToHex(MeshParserUtils.getOpCodes(i2), true)));
        }

        public void a(byte[] bArr, byte[] bArr2, int i2, byte[] bArr3, int i3, int i4) {
            int iA;
            C0017a c0017a;
            String strB = a.this.b(bArr, bArr2);
            if (TextUtils.isEmpty(strB)) {
                return;
            }
            b bVarA = a.this.a(bArr, bArr2);
            bVarA.c(i4);
            bVarA.a(i3);
            SparseArray<C0017a> sparseArray = this.f7378b.get(strB);
            if (sparseArray == null || (c0017a = sparseArray.get((iA = a.this.a(i2, bArr3)))) == null) {
                return;
            }
            c0017a.a();
            bVarA.b((int) (System.currentTimeMillis() - c0017a.f7384e));
            sparseArray.remove(iA);
            a.a.a.a.b.m.a.c(a.f7373a, String.format("Mesh node metric update, node: %s, retransmissionTimeout: %d", MeshParserUtils.bytesToHex(bArr2, true), Integer.valueOf(bVarA.a())));
        }
    }

    private static class b {

        /* renamed from: a, reason: collision with root package name */
        public int f7386a = 0;

        /* renamed from: b, reason: collision with root package name */
        public int f7387b = 0;

        /* renamed from: c, reason: collision with root package name */
        public int f7388c = 10;

        /* renamed from: e, reason: collision with root package name */
        public int f7390e = 10000;

        /* renamed from: d, reason: collision with root package name */
        public int f7389d = 10000;

        /* renamed from: f, reason: collision with root package name */
        public int f7391f = 10000;

        public void a(int i2) {
            this.f7386a = i2;
        }

        public void b(int i2) {
            this.f7389d = i2;
            int i3 = (int) ((this.f7390e * 0.8d) + (i2 * 0.2d));
            this.f7390e = i3;
            this.f7391f = (int) Math.min(15000.0d, Math.max(500.0d, i3 * 1.3d));
        }

        public void c(int i2) {
            this.f7387b = i2;
        }

        public int a() {
            return this.f7391f;
        }
    }

    public final int a(int i2, byte[] bArr) {
        return i2;
    }

    public C0016a c() {
        return this.f7376d;
    }

    public static a b() {
        if (f7374b == null) {
            synchronized (a.class) {
                try {
                    if (f7374b == null) {
                        f7374b = new a();
                    }
                } finally {
                }
            }
        }
        return f7374b;
    }

    public int c(byte[] bArr, byte[] bArr2) {
        return a(bArr, bArr2).a();
    }

    public final b a(byte[] bArr, byte[] bArr2) {
        String strB = b(bArr, bArr2);
        b bVar = this.f7375c.get(strB);
        if (bVar != null) {
            return bVar;
        }
        b bVar2 = new b();
        this.f7375c.put(strB, bVar2);
        return bVar2;
    }

    public final String b(byte[] bArr, byte[] bArr2) {
        if (bArr == null || bArr2 == null) {
            return null;
        }
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return MeshParserUtils.bytesToHex(bArr3, false);
    }
}
