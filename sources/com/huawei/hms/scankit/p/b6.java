package com.huawei.hms.scankit.p;

import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.view.animation.Interpolator;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class b6 {

    /* renamed from: r, reason: collision with root package name */
    private static final Object f17008r = new Object();

    /* renamed from: c, reason: collision with root package name */
    private int f17011c;

    /* renamed from: d, reason: collision with root package name */
    private long f17012d;

    /* renamed from: e, reason: collision with root package name */
    private boolean f17013e;

    /* renamed from: f, reason: collision with root package name */
    private float f17014f;

    /* renamed from: g, reason: collision with root package name */
    private int[] f17015g;

    /* renamed from: h, reason: collision with root package name */
    private int f17016h;

    /* renamed from: i, reason: collision with root package name */
    private int f17017i;

    /* renamed from: j, reason: collision with root package name */
    private int f17018j;

    /* renamed from: k, reason: collision with root package name */
    private int f17019k;

    /* renamed from: l, reason: collision with root package name */
    private int f17020l;

    /* renamed from: n, reason: collision with root package name */
    private Path f17022n;

    /* renamed from: o, reason: collision with root package name */
    private PathMeasure f17023o;

    /* renamed from: a, reason: collision with root package name */
    private final ArrayList<w5> f17009a = new ArrayList<>();

    /* renamed from: b, reason: collision with root package name */
    private final ArrayList<w5> f17010b = new ArrayList<>();

    /* renamed from: m, reason: collision with root package name */
    private boolean f17021m = false;

    /* renamed from: p, reason: collision with root package name */
    private x5 f17024p = new x5();

    /* renamed from: q, reason: collision with root package name */
    private z5 f17025q = new z5();

    public b6(int i2, long j2) {
        a(i2, j2);
        a((Bitmap) null);
    }

    private void a(@NonNull g4 g4Var) {
        if (this.f17025q == null) {
            this.f17025q = new z5();
        }
        this.f17025q.a(g4Var);
    }

    public b6 b(float f2, float f3) {
        a(new a6(f2, f3));
        return this;
    }

    public List<w5> c() {
        List<w5> listUnmodifiableList;
        synchronized (f17008r) {
            listUnmodifiableList = Collections.unmodifiableList(this.f17010b);
        }
        return listUnmodifiableList;
    }

    public void b(long j2) {
        boolean z2 = this.f17013e;
        float f2 = this.f17014f * j2;
        ArrayList arrayList = new ArrayList();
        synchronized (f17008r) {
            while (z2) {
                try {
                    if (this.f17009a.isEmpty() || this.f17016h >= f2) {
                        break;
                    } else {
                        a(j2);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            Iterator<w5> it = this.f17010b.iterator();
            while (it.hasNext()) {
                w5 next = it.next();
                if (!next.a(j2)) {
                    it.remove();
                    arrayList.add(next);
                }
            }
        }
        this.f17009a.addAll(arrayList);
    }

    public b6 a(int i2, int i3, long j2, long j3, @NonNull Interpolator interpolator) {
        a(new j5(i2, i3, j2, j3, interpolator));
        return this;
    }

    private void a(@NonNull f4 f4Var) {
        if (this.f17024p == null) {
            this.f17024p = new x5();
        }
        this.f17024p.a(f4Var);
    }

    private void a(int i2, long j2) {
        this.f17015g = new int[2];
        this.f17011c = i2;
        this.f17012d = j2;
    }

    private void a(Bitmap bitmap) {
        for (int i2 = 0; i2 < this.f17011c; i2++) {
            this.f17009a.add(new w5(bitmap));
        }
    }

    public void a(@NonNull Rect rect, int i2) {
        a(rect);
        a(i2);
    }

    private void a(int i2) {
        synchronized (f17008r) {
            this.f17016h = 0;
        }
        this.f17014f = i2 / 1000.0f;
        this.f17013e = true;
    }

    private void b() {
        ArrayList arrayList;
        synchronized (f17008r) {
            arrayList = new ArrayList(this.f17010b);
        }
        this.f17009a.addAll(arrayList);
    }

    private void a(@NonNull Rect rect) {
        int i2 = rect.left - this.f17015g[0];
        this.f17018j = i2;
        this.f17017i = i2 + rect.width();
        int i3 = rect.top - this.f17015g[1];
        this.f17020l = i3;
        this.f17019k = i3 + rect.height();
    }

    private void a(long j2) {
        PathMeasure pathMeasure;
        w5 w5VarRemove = this.f17009a.remove(0);
        this.f17025q.a(w5VarRemove);
        if (this.f17021m && (pathMeasure = this.f17023o) != null) {
            float[] fArrA = a(0.0f, pathMeasure.getLength());
            w5VarRemove.a(this.f17012d, (int) fArrA[0], (int) fArrA[1], j2, this.f17024p);
        } else {
            w5VarRemove.a(this.f17012d, a(this.f17018j, this.f17017i), a(this.f17020l, this.f17019k), j2, this.f17024p);
        }
        synchronized (f17008r) {
            this.f17010b.add(w5VarRemove);
            this.f17016h++;
        }
    }

    private int a(int i2, int i3) {
        if (i2 == i3) {
            return i2;
        }
        if (i2 < i3) {
            return n6.a(i3 - i2) + i2;
        }
        return n6.a(i2 - i3) + i3;
    }

    private float[] a(float f2, float f3) {
        float fA;
        if (Float.compare(f2, f3) <= 0) {
            fA = n6.a(f3 - f2) + f2;
        } else {
            fA = f3 + n6.a(f2 - f3);
        }
        if (this.f17023o == null) {
            this.f17023o = new PathMeasure(this.f17022n, true);
        }
        float[] fArr = new float[2];
        this.f17023o.getPosTan(fA, fArr, null);
        float f4 = fArr[0];
        int[] iArr = this.f17015g;
        fArr[0] = f4 - iArr[0];
        fArr[1] = fArr[1] - iArr[1];
        return fArr;
    }

    public void a() {
        b();
    }
}
