package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.fragment.app.FragmentManager;
import java.util.ArrayList;

@SuppressLint({"BanParcelableUsage"})
/* loaded from: classes.dex */
final class FragmentManagerState implements Parcelable {
    public static final Parcelable.Creator<FragmentManagerState> CREATOR = new Parcelable.Creator<FragmentManagerState>() { // from class: androidx.fragment.app.FragmentManagerState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FragmentManagerState createFromParcel(Parcel parcel) {
            return new FragmentManagerState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FragmentManagerState[] newArray(int i2) {
            return new FragmentManagerState[i2];
        }
    };

    /* renamed from: a, reason: collision with root package name */
    ArrayList f4201a;

    /* renamed from: b, reason: collision with root package name */
    ArrayList f4202b;

    /* renamed from: c, reason: collision with root package name */
    BackStackRecordState[] f4203c;

    /* renamed from: d, reason: collision with root package name */
    int f4204d;

    /* renamed from: e, reason: collision with root package name */
    String f4205e;

    /* renamed from: f, reason: collision with root package name */
    ArrayList f4206f;

    /* renamed from: g, reason: collision with root package name */
    ArrayList f4207g;

    /* renamed from: h, reason: collision with root package name */
    ArrayList f4208h;

    public FragmentManagerState() {
        this.f4205e = null;
        this.f4206f = new ArrayList();
        this.f4207g = new ArrayList();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeStringList(this.f4201a);
        parcel.writeStringList(this.f4202b);
        parcel.writeTypedArray(this.f4203c, i2);
        parcel.writeInt(this.f4204d);
        parcel.writeString(this.f4205e);
        parcel.writeStringList(this.f4206f);
        parcel.writeTypedList(this.f4207g);
        parcel.writeTypedList(this.f4208h);
    }

    public FragmentManagerState(Parcel parcel) {
        this.f4205e = null;
        this.f4206f = new ArrayList();
        this.f4207g = new ArrayList();
        this.f4201a = parcel.createStringArrayList();
        this.f4202b = parcel.createStringArrayList();
        this.f4203c = (BackStackRecordState[]) parcel.createTypedArray(BackStackRecordState.CREATOR);
        this.f4204d = parcel.readInt();
        this.f4205e = parcel.readString();
        this.f4206f = parcel.createStringArrayList();
        this.f4207g = parcel.createTypedArrayList(BackStackState.CREATOR);
        this.f4208h = parcel.createTypedArrayList(FragmentManager.LaunchedFragmentInfo.CREATOR);
    }
}
