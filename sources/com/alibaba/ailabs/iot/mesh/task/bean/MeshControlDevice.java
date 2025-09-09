package com.alibaba.ailabs.iot.mesh.task.bean;

import a.a.a.a.b.j.a.a;
import android.os.Parcel;
import android.os.Parcelable;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;

/* loaded from: classes2.dex */
public class MeshControlDevice implements Parcelable {
    public static final Parcelable.Creator<MeshControlDevice> CREATOR = new a();

    /* renamed from: a, reason: collision with root package name */
    public int f8767a;

    /* renamed from: b, reason: collision with root package name */
    public int f8768b;

    /* renamed from: c, reason: collision with root package name */
    public int f8769c;

    /* renamed from: d, reason: collision with root package name */
    public int f8770d;

    /* renamed from: e, reason: collision with root package name */
    public byte[] f8771e;

    /* renamed from: f, reason: collision with root package name */
    public IActionListener f8772f;

    /* renamed from: g, reason: collision with root package name */
    public boolean f8773g;

    public MeshControlDevice() {
    }

    public void a(int i2) {
        this.f8768b = i2;
    }

    public int b() {
        return this.f8768b;
    }

    public int c() {
        return this.f8769c;
    }

    public void d(int i2) {
        this.f8767a = i2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public byte[] e() {
        return this.f8771e;
    }

    public int f() {
        return this.f8767a;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.f8767a);
        parcel.writeInt(this.f8768b);
        parcel.writeInt(this.f8769c);
        parcel.writeInt(this.f8770d);
        parcel.writeByteArray(this.f8771e);
        parcel.writeByte(this.f8773g ? (byte) 1 : (byte) 0);
    }

    public MeshControlDevice(Parcel parcel) {
        this.f8767a = parcel.readInt();
        this.f8768b = parcel.readInt();
        this.f8769c = parcel.readInt();
        this.f8770d = parcel.readInt();
        this.f8771e = parcel.createByteArray();
        this.f8773g = parcel.readByte() != 0;
    }

    public void a(byte[] bArr) {
        this.f8771e = bArr;
    }

    public void b(int i2) {
        this.f8769c = i2;
    }

    public void c(int i2) {
        this.f8770d = i2;
    }

    public int d() {
        return this.f8770d;
    }

    public void a(boolean z2) {
        this.f8773g = z2;
    }

    public void a(IActionListener iActionListener) {
        this.f8772f = iActionListener;
    }

    public IActionListener a() {
        return this.f8772f;
    }
}
