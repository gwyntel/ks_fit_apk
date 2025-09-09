package com.meizu.cloud.pushsdk.handler.e.j;

import android.os.Parcel;
import android.os.Parcelable;
import com.meizu.cloud.pushinternal.DebugLogger;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class a implements Parcelable {
    public static final Parcelable.Creator<a> CREATOR = new C0163a();

    /* renamed from: a, reason: collision with root package name */
    private int f19701a;

    /* renamed from: b, reason: collision with root package name */
    private int f19702b;

    /* renamed from: c, reason: collision with root package name */
    private int f19703c;

    /* renamed from: com.meizu.cloud.pushsdk.handler.e.j.a$a, reason: collision with other inner class name */
    class C0163a implements Parcelable.Creator<a> {
        C0163a() {
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public a createFromParcel(Parcel parcel) {
            return new a(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public a[] newArray(int i2) {
            return new a[i2];
        }
    }

    public a() {
    }

    public int a() {
        return this.f19701a;
    }

    public void b(int i2) {
        this.f19702b = i2;
    }

    public void c(int i2) {
        this.f19701a = i2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "Control{pushType=" + this.f19701a + ", cached=" + this.f19702b + ", cacheNum=" + this.f19703c + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.f19701a);
        parcel.writeInt(this.f19702b);
        parcel.writeInt(this.f19703c);
    }

    protected a(Parcel parcel) {
        this.f19701a = parcel.readInt();
        this.f19702b = parcel.readInt();
        this.f19703c = parcel.readInt();
    }

    public static a a(JSONObject jSONObject) {
        String str;
        a aVar = new a();
        if (jSONObject != null) {
            try {
                if (!jSONObject.isNull("pushType")) {
                    aVar.c(jSONObject.getInt("pushType"));
                }
                if (!jSONObject.isNull("cached")) {
                    aVar.b(jSONObject.getInt("cached"));
                }
                if (!jSONObject.isNull("cacheNum")) {
                    aVar.a(jSONObject.getInt("cacheNum"));
                }
            } catch (JSONException e2) {
                str = " parse control message error " + e2.getMessage();
            }
            return aVar;
        }
        str = "no control message can parse ";
        DebugLogger.e("ctl", str);
        return aVar;
    }

    public void a(int i2) {
        this.f19703c = i2;
    }
}
