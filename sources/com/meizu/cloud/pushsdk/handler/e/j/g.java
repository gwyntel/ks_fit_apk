package com.meizu.cloud.pushsdk.handler.e.j;

import android.os.Parcel;
import android.os.Parcelable;
import com.meizu.cloud.pushinternal.DebugLogger;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class g implements Parcelable {
    public static final Parcelable.Creator<g> CREATOR = new a();

    /* renamed from: a, reason: collision with root package name */
    private int f19733a;

    /* renamed from: b, reason: collision with root package name */
    private boolean f19734b;

    /* renamed from: c, reason: collision with root package name */
    private List<String> f19735c;

    /* renamed from: d, reason: collision with root package name */
    private b f19736d;

    /* renamed from: e, reason: collision with root package name */
    private String f19737e;

    class a implements Parcelable.Creator<g> {
        a() {
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public g createFromParcel(Parcel parcel) {
            return new g(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public g[] newArray(int i2) {
            return new g[i2];
        }
    }

    protected g(Parcel parcel) {
        this.f19733a = parcel.readInt();
        this.f19734b = parcel.readByte() != 0;
        this.f19735c = parcel.createStringArrayList();
        this.f19736d = (b) parcel.readParcelable(b.class.getClassLoader());
        this.f19737e = parcel.readString();
    }

    public b a() {
        return this.f19736d;
    }

    public List<String> b() {
        return this.f19735c;
    }

    public int c() {
        return this.f19733a;
    }

    public boolean d() {
        return this.f19734b;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "UploadLogMessage{maxSize=" + this.f19733a + ", wifiUpload=" + this.f19734b + ", fileList=" + this.f19735c + ", controlMessage=" + this.f19736d + ", uploadMessage='" + this.f19737e + "'}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.f19733a);
        parcel.writeByte(this.f19734b ? (byte) 1 : (byte) 0);
        parcel.writeStringList(this.f19735c);
        parcel.writeParcelable(this.f19736d, i2);
        parcel.writeString(this.f19737e);
    }

    public g(String str, String str2, String str3, String str4) throws JSONException {
        this.f19737e = str;
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.isNull("max_size")) {
                this.f19733a = jSONObject.getInt("max_size");
            }
            if (!jSONObject.isNull("wifi_upload")) {
                this.f19734b = jSONObject.getBoolean("wifi_upload");
            }
            if (!jSONObject.isNull("upload_files")) {
                JSONArray jSONArray = jSONObject.getJSONArray("upload_files");
                this.f19735c = new ArrayList();
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    this.f19735c.add(jSONArray.getString(i2));
                }
            }
        } catch (JSONException e2) {
            DebugLogger.e("UploadLogMessage", "parse upload message error " + e2.getMessage());
        }
        this.f19736d = new b(str2, str3, str4);
    }
}
