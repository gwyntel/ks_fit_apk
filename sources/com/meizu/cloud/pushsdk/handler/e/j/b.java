package com.meizu.cloud.pushsdk.handler.e.j;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class b implements Parcelable {
    public static final Parcelable.Creator<b> CREATOR = new a();

    /* renamed from: a, reason: collision with root package name */
    private String f19704a;

    /* renamed from: b, reason: collision with root package name */
    private com.meizu.cloud.pushsdk.handler.e.j.a f19705b;

    /* renamed from: c, reason: collision with root package name */
    private f f19706c;

    class a implements Parcelable.Creator<b> {
        a() {
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public b createFromParcel(Parcel parcel) {
            return new b(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public b[] newArray(int i2) {
            return new b[i2];
        }
    }

    public b() {
    }

    public com.meizu.cloud.pushsdk.handler.e.j.a a() {
        return this.f19705b;
    }

    public f b() {
        return this.f19706c;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "ControlMessage{controlMessage='" + this.f19704a + "', control=" + this.f19705b + ", statics=" + this.f19706c + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.f19704a);
        parcel.writeParcelable(this.f19705b, i2);
        parcel.writeParcelable(this.f19706c, i2);
    }

    protected b(Parcel parcel) {
        this.f19704a = parcel.readString();
        this.f19705b = (com.meizu.cloud.pushsdk.handler.e.j.a) parcel.readParcelable(com.meizu.cloud.pushsdk.handler.e.j.a.class.getClassLoader());
        this.f19706c = (f) parcel.readParcelable(f.class.getClassLoader());
    }

    public static b a(String str) {
        b bVar = new b();
        try {
            JSONObject jSONObject = new JSONObject(str);
            bVar.a(com.meizu.cloud.pushsdk.handler.e.j.a.a(jSONObject.getJSONObject("ctl")));
            bVar.a(f.a(jSONObject.getJSONObject("statics")));
        } catch (Exception e2) {
            DebugLogger.e("ControlMessage", "parse control message error " + e2.getMessage());
            bVar.a(new f());
            bVar.a(new com.meizu.cloud.pushsdk.handler.e.j.a());
        }
        return bVar;
    }

    public b(String str, String str2, String str3) {
        this.f19704a = str;
        if (TextUtils.isEmpty(str)) {
            this.f19705b = new com.meizu.cloud.pushsdk.handler.e.j.a();
            this.f19706c = new f();
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            this.f19705b = com.meizu.cloud.pushsdk.handler.e.j.a.a(jSONObject.getJSONObject("ctl"));
            f fVarA = f.a(jSONObject.getJSONObject("statics"));
            this.f19706c = fVarA;
            fVarA.a(str2);
            this.f19706c.b(str3);
        } catch (JSONException e2) {
            this.f19705b = new com.meizu.cloud.pushsdk.handler.e.j.a();
            this.f19706c = new f();
            DebugLogger.e("ControlMessage", "parse control message error " + e2.getMessage());
        }
    }

    public void a(com.meizu.cloud.pushsdk.handler.e.j.a aVar) {
        this.f19705b = aVar;
    }

    public void a(f fVar) {
        this.f19706c = fVar;
    }
}
