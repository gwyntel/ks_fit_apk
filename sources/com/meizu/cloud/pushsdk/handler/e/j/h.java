package com.meizu.cloud.pushsdk.handler.e.j;

import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.meizu.cloud.pushinternal.DebugLogger;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class h implements Parcelable {
    public static final Parcelable.Creator<h> CREATOR = new a();

    /* renamed from: a, reason: collision with root package name */
    private b f19738a;

    /* renamed from: b, reason: collision with root package name */
    private String f19739b;

    /* renamed from: c, reason: collision with root package name */
    private int f19740c;

    class a implements Parcelable.Creator<h> {
        a() {
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public h createFromParcel(Parcel parcel) {
            return new h(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public h[] newArray(int i2) {
            return new h[i2];
        }
    }

    protected h(Parcel parcel) {
        this.f19738a = (b) parcel.readParcelable(b.class.getClassLoader());
        this.f19739b = parcel.readString();
        this.f19740c = parcel.readInt();
    }

    public b a() {
        return this.f19738a;
    }

    public int b() {
        return this.f19740c;
    }

    public String c() {
        return this.f19739b;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "WithDrawMessage{controlMessage=" + this.f19738a + ", revokePackageName='" + this.f19739b + "', notifyId=" + this.f19740c + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeParcelable(this.f19738a, i2);
        parcel.writeString(this.f19739b);
        parcel.writeInt(this.f19740c);
    }

    public h(String str, String str2, String str3, String str4, String str5) {
        this.f19739b = str2;
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.isNull(RemoteMessageConst.Notification.NOTIFY_ID)) {
                this.f19740c = jSONObject.getInt(RemoteMessageConst.Notification.NOTIFY_ID);
            }
        } catch (JSONException e2) {
            DebugLogger.e("WithDrawMessage", "parse WithDrawMessage error " + e2.getMessage());
        }
        this.f19738a = new b(str3, str4, str5);
    }
}
