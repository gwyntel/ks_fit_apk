package a.a.a.a.b.a;

import androidx.annotation.NonNull;
import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest;

/* renamed from: a.a.a.a.b.a.a, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0212a extends SIGMeshBizRequest {

    /* renamed from: s, reason: collision with root package name */
    public String f1252s;

    /* renamed from: t, reason: collision with root package name */
    public String f1253t;

    /* renamed from: u, reason: collision with root package name */
    public final int f1254u;

    /* renamed from: v, reason: collision with root package name */
    public final int f1255v;

    /* renamed from: w, reason: collision with root package name */
    public final int f1256w;

    /* renamed from: x, reason: collision with root package name */
    public final int f1257x;

    public C0212a(@NonNull SIGMeshBizRequest.Type type, String str, String str2, int i2, int i3, int i4, int i5) {
        super(type, SIGMeshBizRequest.Mode.UNICAST);
        this.f1253t = str;
        this.f1252s = str2;
        this.f1254u = i2;
        this.f1255v = i3;
        this.f1256w = i4;
        this.f1257x = i5;
    }

    public String r() {
        return this.f1253t;
    }

    public String s() {
        return this.f1252s;
    }

    public int t() {
        return this.f1255v;
    }

    public int u() {
        return this.f1257x;
    }

    public int v() {
        return this.f1254u;
    }

    public int w() {
        return this.f1256w;
    }
}
