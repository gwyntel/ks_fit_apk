package com.meizu.cloud.pushsdk.handler.e.j;

import android.os.Parcel;
import android.os.Parcelable;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.meizu.cloud.pushinternal.DebugLogger;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class f implements Parcelable {
    public static final Parcelable.Creator<f> CREATOR = new a();

    /* renamed from: a, reason: collision with root package name */
    private String f19728a;

    /* renamed from: b, reason: collision with root package name */
    private String f19729b;

    /* renamed from: c, reason: collision with root package name */
    private boolean f19730c;

    /* renamed from: d, reason: collision with root package name */
    private String f19731d;

    /* renamed from: e, reason: collision with root package name */
    private String f19732e;

    class a implements Parcelable.Creator<f> {
        a() {
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public f createFromParcel(Parcel parcel) {
            return new f(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public f[] newArray(int i2) {
            return new f[i2];
        }
    }

    public f() {
        this.f19730c = false;
    }

    public static f a(JSONObject jSONObject) {
        String str;
        f fVar = new f();
        if (jSONObject != null) {
            try {
                if (!jSONObject.isNull(AlinkConstants.KEY_TASK_ID)) {
                    fVar.c(jSONObject.getString(AlinkConstants.KEY_TASK_ID));
                }
                if (!jSONObject.isNull("time")) {
                    fVar.d(jSONObject.getString("time"));
                }
                if (!jSONObject.isNull("pushExtra")) {
                    fVar.a(jSONObject.getInt("pushExtra") == 0);
                }
            } catch (JSONException e2) {
                str = " parse statics message error " + e2.getMessage();
            }
            return fVar;
        }
        str = "no control statics can parse ";
        DebugLogger.e("statics", str);
        return fVar;
    }

    public void b(String str) {
        this.f19732e = str;
    }

    public String c() {
        return this.f19732e;
    }

    public String d() {
        return this.f19728a;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String e() {
        return this.f19729b;
    }

    public String toString() {
        return "Statics{taskId='" + this.f19728a + "', time='" + this.f19729b + "', pushExtra=" + this.f19730c + ", deviceId='" + this.f19731d + "', seqId='" + this.f19732e + "'}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.f19728a);
        parcel.writeString(this.f19729b);
        parcel.writeByte(this.f19730c ? (byte) 1 : (byte) 0);
        parcel.writeString(this.f19731d);
        parcel.writeString(this.f19732e);
    }

    protected f(Parcel parcel) {
        this.f19730c = false;
        this.f19728a = parcel.readString();
        this.f19729b = parcel.readString();
        this.f19730c = parcel.readByte() != 0;
        this.f19731d = parcel.readString();
        this.f19732e = parcel.readString();
    }

    public String a() {
        return this.f19731d;
    }

    public boolean b() {
        return this.f19730c;
    }

    public void c(String str) {
        this.f19728a = str;
    }

    public void d(String str) {
        this.f19729b = str;
    }

    public void a(String str) {
        this.f19731d = str;
    }

    public void a(boolean z2) {
        this.f19730c = z2;
    }
}
