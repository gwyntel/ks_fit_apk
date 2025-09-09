package a.a.a.a.b.i;

import datasource.bean.DeviceStatus;
import java.util.LinkedList;
import java.util.List;

/* renamed from: a.a.a.a.b.i.b, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0233b {

    /* renamed from: a, reason: collision with root package name */
    public String f1416a;

    /* renamed from: b, reason: collision with root package name */
    public List<DeviceStatus> f1417b = new LinkedList();

    public String a() {
        return this.f1416a;
    }

    public void a(String str) {
        this.f1416a = str;
    }

    public void a(List<DeviceStatus> list) {
        if (list != null) {
            this.f1417b.addAll(list);
        }
    }
}
