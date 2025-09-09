package anetwork.channel.aidl;

import android.os.Parcel;
import android.os.Parcelable;
import anet.channel.util.ALog;
import anet.channel.util.ErrorConstant;
import anetwork.channel.Response;
import anetwork.channel.statist.StatisticData;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class NetworkResponse implements Parcelable, Response {
    public static final Parcelable.Creator<NetworkResponse> CREATOR = new c();

    /* renamed from: a, reason: collision with root package name */
    int f7109a;

    /* renamed from: b, reason: collision with root package name */
    byte[] f7110b;

    /* renamed from: c, reason: collision with root package name */
    private String f7111c;

    /* renamed from: d, reason: collision with root package name */
    private Map<String, List<String>> f7112d;

    /* renamed from: e, reason: collision with root package name */
    private Throwable f7113e;

    /* renamed from: f, reason: collision with root package name */
    private StatisticData f7114f;

    public NetworkResponse() {
    }

    public static NetworkResponse readFromParcel(Parcel parcel) {
        NetworkResponse networkResponse = new NetworkResponse();
        try {
            networkResponse.f7109a = parcel.readInt();
            networkResponse.f7111c = parcel.readString();
            int i2 = parcel.readInt();
            if (i2 > 0) {
                byte[] bArr = new byte[i2];
                networkResponse.f7110b = bArr;
                parcel.readByteArray(bArr);
            }
            networkResponse.f7112d = parcel.readHashMap(NetworkResponse.class.getClassLoader());
            try {
                networkResponse.f7114f = (StatisticData) parcel.readSerializable();
            } catch (Throwable unused) {
                ALog.i("anet.NetworkResponse", "[readFromParcel] source.readSerializable() error", null, new Object[0]);
            }
        } catch (Exception e2) {
            ALog.w("anet.NetworkResponse", "[readFromParcel]", null, e2, new Object[0]);
        }
        return networkResponse;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // anetwork.channel.Response
    public byte[] getBytedata() {
        return this.f7110b;
    }

    @Override // anetwork.channel.Response
    public Map<String, List<String>> getConnHeadFields() {
        return this.f7112d;
    }

    @Override // anetwork.channel.Response
    public String getDesc() {
        return this.f7111c;
    }

    @Override // anetwork.channel.Response
    public Throwable getError() {
        return this.f7113e;
    }

    @Override // anetwork.channel.Response
    public StatisticData getStatisticData() {
        return this.f7114f;
    }

    @Override // anetwork.channel.Response
    public int getStatusCode() {
        return this.f7109a;
    }

    public void setBytedata(byte[] bArr) {
        this.f7110b = bArr;
    }

    public void setConnHeadFields(Map<String, List<String>> map) {
        this.f7112d = map;
    }

    public void setDesc(String str) {
        this.f7111c = str;
    }

    public void setError(Throwable th) {
        this.f7113e = th;
    }

    public void setStatisticData(StatisticData statisticData) {
        this.f7114f = statisticData;
    }

    public void setStatusCode(int i2) {
        this.f7109a = i2;
        this.f7111c = ErrorConstant.getErrMsg(i2);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("NetworkResponse [");
        sb.append("statusCode=");
        sb.append(this.f7109a);
        sb.append(", desc=");
        sb.append(this.f7111c);
        sb.append(", connHeadFields=");
        sb.append(this.f7112d);
        sb.append(", bytedata=");
        byte[] bArr = this.f7110b;
        sb.append(bArr != null ? new String(bArr) : "");
        sb.append(", error=");
        sb.append(this.f7113e);
        sb.append(", statisticData=");
        sb.append(this.f7114f);
        sb.append("]");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.f7109a);
        parcel.writeString(this.f7111c);
        byte[] bArr = this.f7110b;
        int length = bArr != null ? bArr.length : 0;
        parcel.writeInt(length);
        if (length > 0) {
            parcel.writeByteArray(this.f7110b);
        }
        parcel.writeMap(this.f7112d);
        StatisticData statisticData = this.f7114f;
        if (statisticData != null) {
            parcel.writeSerializable(statisticData);
        }
    }

    public NetworkResponse(int i2) {
        this.f7109a = i2;
        this.f7111c = ErrorConstant.getErrMsg(i2);
    }
}
