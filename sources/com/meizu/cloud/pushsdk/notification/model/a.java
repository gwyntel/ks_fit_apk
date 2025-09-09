package com.meizu.cloud.pushsdk.notification.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class a implements Parcelable {
    public static final Parcelable.Creator<a> CREATOR = new C0166a();

    /* renamed from: a, reason: collision with root package name */
    private int f19769a;

    /* renamed from: b, reason: collision with root package name */
    private String f19770b;

    /* renamed from: com.meizu.cloud.pushsdk.notification.model.a$a, reason: collision with other inner class name */
    class C0166a implements Parcelable.Creator<a> {
        C0166a() {
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
        this.f19769a = 0;
    }

    public static a b(MessageV3 messageV3) {
        a aVarA;
        try {
            aVarA = !TextUtils.isEmpty(messageV3.getNotificationMessage()) ? a(new JSONObject(messageV3.getNotificationMessage()).getJSONObject("data").getJSONObject(PushConstants.EXTRA).getJSONObject("no")) : null;
        } catch (Exception e2) {
            DebugLogger.e("NotifyOption", "parse flyme NotifyOption setting error " + e2.getMessage() + " so get from notificationMessage");
            aVarA = a(messageV3.getNotificationMessage());
        }
        DebugLogger.i("NotifyOption", "current notify option is " + aVarA);
        return aVarA;
    }

    public int a() {
        return this.f19769a;
    }

    public void c(String str) {
        this.f19770b = str;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "NotifyOption{notifyId=" + this.f19769a + ", notifyKey='" + this.f19770b + "'}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.f19769a);
        parcel.writeString(this.f19770b);
    }

    protected a(Parcel parcel) {
        this.f19769a = 0;
        this.f19769a = parcel.readInt();
        this.f19770b = parcel.readString();
    }

    public static int a(MessageV3 messageV3) {
        a aVarB = b(messageV3);
        if (aVarB != null) {
            return aVarB.a();
        }
        return 0;
    }

    public static a b(String str) {
        JSONObject jSONObject;
        if (TextUtils.isEmpty(str)) {
            jSONObject = null;
        } else {
            try {
                jSONObject = new JSONObject(str);
            } catch (JSONException e2) {
                DebugLogger.e("NotifyOption", "parse json string error " + e2.getMessage());
            }
        }
        return a(jSONObject);
    }

    private static a a(String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                return b(new JSONObject(str).getString("no"));
            }
        } catch (JSONException e2) {
            DebugLogger.e("NotifyOption", "parse notificationMessage error " + e2.getMessage());
        }
        return null;
    }

    public String b() {
        return this.f19770b;
    }

    public static a a(JSONObject jSONObject) {
        String str;
        a aVar = new a();
        if (jSONObject != null) {
            try {
                if (!jSONObject.isNull("ni")) {
                    aVar.a(jSONObject.getInt("ni"));
                }
                if (!jSONObject.isNull("nk")) {
                    aVar.c(jSONObject.getString("nk"));
                }
            } catch (JSONException e2) {
                str = "parse json obj error " + e2.getMessage();
            }
            return aVar;
        }
        str = "no such tag NotifyOption";
        DebugLogger.e("NotifyOption", str);
        return aVar;
    }

    public void a(int i2) {
        this.f19769a = i2;
    }
}
