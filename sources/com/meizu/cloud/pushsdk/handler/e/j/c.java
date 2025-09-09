package com.meizu.cloud.pushsdk.handler.e.j;

import android.os.Parcel;
import android.os.Parcelable;
import com.meizu.cloud.pushsdk.handler.MessageV3;

/* loaded from: classes4.dex */
public class c implements Parcelable {
    public static final Parcelable.Creator<c> CREATOR = new a();

    /* renamed from: a, reason: collision with root package name */
    private MessageV3 f19707a;

    /* renamed from: b, reason: collision with root package name */
    private String f19708b;

    /* renamed from: c, reason: collision with root package name */
    private int f19709c;

    /* renamed from: d, reason: collision with root package name */
    private int f19710d;

    class a implements Parcelable.Creator<c> {
        a() {
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public c createFromParcel(Parcel parcel) {
            return new c(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public c[] newArray(int i2) {
            return new c[i2];
        }
    }

    protected c(Parcel parcel) {
        this.f19707a = (MessageV3) parcel.readParcelable(MessageV3.class.getClassLoader());
        this.f19708b = parcel.readString();
        this.f19709c = parcel.readInt();
        this.f19710d = parcel.readInt();
    }

    public MessageV3 a() {
        return this.f19707a;
    }

    public int b() {
        return this.f19709c;
    }

    public int c() {
        return this.f19710d;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "NotificationState{messageV3=" + this.f19707a + ", notificationPkg='" + this.f19708b + "', notificationId='" + this.f19709c + "', state='" + this.f19710d + "'}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeParcelable(this.f19707a, i2);
        parcel.writeString(this.f19708b);
        parcel.writeInt(this.f19709c);
        parcel.writeInt(this.f19710d);
    }

    public c(MessageV3 messageV3) {
        this.f19707a = messageV3;
    }

    public void a(int i2) {
        this.f19709c = i2;
    }

    public void b(int i2) {
        this.f19710d = i2;
    }

    public void a(String str) {
        this.f19708b = str;
    }
}
