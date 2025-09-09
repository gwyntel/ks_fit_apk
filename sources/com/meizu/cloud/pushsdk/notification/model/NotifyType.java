package com.meizu.cloud.pushsdk.notification.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class NotifyType implements Parcelable {
    public static final Parcelable.Creator<NotifyType> CREATOR = new a();
    public static final String LIGHTS = "l";
    public static final String NOTIFY_TYPE = "nt";
    public static final String SOUND = "s";
    public static final String TAG = "notify_type";
    public static final String VIBRATE = "v";
    boolean lights;
    boolean sound;
    boolean vibrate;

    class a implements Parcelable.Creator<NotifyType> {
        a() {
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public NotifyType createFromParcel(Parcel parcel) {
            return new NotifyType(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public NotifyType[] newArray(int i2) {
            return new NotifyType[i2];
        }
    }

    public NotifyType() {
    }

    public static NotifyType parse(String str) {
        JSONObject jSONObject;
        if (TextUtils.isEmpty(str)) {
            jSONObject = null;
        } else {
            try {
                jSONObject = new JSONObject(str);
            } catch (JSONException e2) {
                DebugLogger.e("notify_type", "parse json string error " + e2.getMessage());
            }
        }
        return parse(jSONObject);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean isLights() {
        return this.lights;
    }

    public boolean isSound() {
        return this.sound;
    }

    public boolean isVibrate() {
        return this.vibrate;
    }

    public void setLights(boolean z2) {
        this.lights = z2;
    }

    public void setSound(boolean z2) {
        this.sound = z2;
    }

    public void setVibrate(boolean z2) {
        this.vibrate = z2;
    }

    public String toString() {
        return "NotifyType{vibrate=" + this.vibrate + ", lights=" + this.lights + ", sound=" + this.sound + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeByte(this.vibrate ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.lights ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.sound ? (byte) 1 : (byte) 0);
    }

    public NotifyType(Parcel parcel) {
        this.vibrate = parcel.readByte() != 0;
        this.lights = parcel.readByte() != 0;
        this.sound = parcel.readByte() != 0;
    }

    public static NotifyType parse(JSONObject jSONObject) {
        String str;
        NotifyType notifyType = new NotifyType();
        if (jSONObject != null) {
            try {
                if (!jSONObject.isNull("v")) {
                    notifyType.setVibrate(jSONObject.getInt("v") != 0);
                }
                if (!jSONObject.isNull("l")) {
                    notifyType.setLights(jSONObject.getInt("l") != 0);
                }
                if (!jSONObject.isNull("s")) {
                    notifyType.setSound(jSONObject.getInt("s") != 0);
                }
            } catch (JSONException e2) {
                str = "parse json obj error " + e2.getMessage();
            }
            return notifyType;
        }
        str = "no such tag notify_type";
        DebugLogger.e("notify_type", str);
        return notifyType;
    }
}
