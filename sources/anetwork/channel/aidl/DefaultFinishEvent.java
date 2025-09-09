package anetwork.channel.aidl;

import android.os.Parcel;
import android.os.Parcelable;
import anet.channel.request.Request;
import anet.channel.statist.RequestStatistic;
import anet.channel.util.ErrorConstant;
import anetwork.channel.NetworkEvent;
import anetwork.channel.statist.StatisticData;

/* loaded from: classes2.dex */
public class DefaultFinishEvent implements Parcelable, NetworkEvent.FinishEvent {
    public static final Parcelable.Creator<DefaultFinishEvent> CREATOR = new a();

    /* renamed from: a, reason: collision with root package name */
    Object f7099a;

    /* renamed from: b, reason: collision with root package name */
    int f7100b;

    /* renamed from: c, reason: collision with root package name */
    String f7101c;

    /* renamed from: d, reason: collision with root package name */
    StatisticData f7102d;
    public final Request request;
    public final RequestStatistic rs;

    public DefaultFinishEvent(int i2) {
        this(i2, null, null, null);
    }

    static DefaultFinishEvent a(Parcel parcel) {
        DefaultFinishEvent defaultFinishEvent = new DefaultFinishEvent(0);
        try {
            defaultFinishEvent.f7100b = parcel.readInt();
            defaultFinishEvent.f7101c = parcel.readString();
            defaultFinishEvent.f7102d = (StatisticData) parcel.readSerializable();
        } catch (Throwable unused) {
        }
        return defaultFinishEvent;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Object getContext() {
        return this.f7099a;
    }

    @Override // anetwork.channel.NetworkEvent.FinishEvent
    public String getDesc() {
        return this.f7101c;
    }

    @Override // anetwork.channel.NetworkEvent.FinishEvent
    public int getHttpCode() {
        return this.f7100b;
    }

    @Override // anetwork.channel.NetworkEvent.FinishEvent
    public StatisticData getStatisticData() {
        return this.f7102d;
    }

    public void setContext(Object obj) {
        this.f7099a = obj;
    }

    public String toString() {
        return "DefaultFinishEvent [code=" + this.f7100b + ", desc=" + this.f7101c + ", context=" + this.f7099a + ", statisticData=" + this.f7102d + "]";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.f7100b);
        parcel.writeString(this.f7101c);
        StatisticData statisticData = this.f7102d;
        if (statisticData != null) {
            parcel.writeSerializable(statisticData);
        }
    }

    public DefaultFinishEvent(int i2, String str, RequestStatistic requestStatistic) {
        this(i2, str, null, requestStatistic);
    }

    public DefaultFinishEvent(int i2, String str, Request request) {
        this(i2, str, request, request != null ? request.f6850a : null);
    }

    private DefaultFinishEvent(int i2, String str, Request request, RequestStatistic requestStatistic) {
        this.f7102d = new StatisticData();
        this.f7100b = i2;
        this.f7101c = str == null ? ErrorConstant.getErrMsg(i2) : str;
        this.request = request;
        this.rs = requestStatistic;
    }
}
