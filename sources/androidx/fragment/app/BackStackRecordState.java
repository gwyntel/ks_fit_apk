package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import java.util.ArrayList;
import java.util.Map;

@SuppressLint({"BanParcelableUsage"})
/* loaded from: classes.dex */
final class BackStackRecordState implements Parcelable {
    public static final Parcelable.Creator<BackStackRecordState> CREATOR = new Parcelable.Creator<BackStackRecordState>() { // from class: androidx.fragment.app.BackStackRecordState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BackStackRecordState createFromParcel(Parcel parcel) {
            return new BackStackRecordState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BackStackRecordState[] newArray(int i2) {
            return new BackStackRecordState[i2];
        }
    };
    private static final String TAG = "FragmentManager";

    /* renamed from: a, reason: collision with root package name */
    final int[] f4095a;

    /* renamed from: b, reason: collision with root package name */
    final ArrayList f4096b;

    /* renamed from: c, reason: collision with root package name */
    final int[] f4097c;

    /* renamed from: d, reason: collision with root package name */
    final int[] f4098d;

    /* renamed from: e, reason: collision with root package name */
    final int f4099e;

    /* renamed from: f, reason: collision with root package name */
    final String f4100f;

    /* renamed from: g, reason: collision with root package name */
    final int f4101g;

    /* renamed from: h, reason: collision with root package name */
    final int f4102h;

    /* renamed from: i, reason: collision with root package name */
    final CharSequence f4103i;

    /* renamed from: j, reason: collision with root package name */
    final int f4104j;

    /* renamed from: k, reason: collision with root package name */
    final CharSequence f4105k;

    /* renamed from: l, reason: collision with root package name */
    final ArrayList f4106l;

    /* renamed from: m, reason: collision with root package name */
    final ArrayList f4107m;

    /* renamed from: n, reason: collision with root package name */
    final boolean f4108n;

    BackStackRecordState(BackStackRecord backStackRecord) {
        int size = backStackRecord.f4231a.size();
        this.f4095a = new int[size * 6];
        if (!backStackRecord.f4237g) {
            throw new IllegalStateException("Not on back stack");
        }
        this.f4096b = new ArrayList(size);
        this.f4097c = new int[size];
        this.f4098d = new int[size];
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            FragmentTransaction.Op op = (FragmentTransaction.Op) backStackRecord.f4231a.get(i3);
            int i4 = i2 + 1;
            this.f4095a[i2] = op.f4248a;
            ArrayList arrayList = this.f4096b;
            Fragment fragment = op.f4249b;
            arrayList.add(fragment != null ? fragment.mWho : null);
            int[] iArr = this.f4095a;
            iArr[i4] = op.f4250c ? 1 : 0;
            iArr[i2 + 2] = op.f4251d;
            iArr[i2 + 3] = op.f4252e;
            int i5 = i2 + 5;
            iArr[i2 + 4] = op.f4253f;
            i2 += 6;
            iArr[i5] = op.f4254g;
            this.f4097c[i3] = op.f4255h.ordinal();
            this.f4098d[i3] = op.f4256i.ordinal();
        }
        this.f4099e = backStackRecord.f4236f;
        this.f4100f = backStackRecord.f4239i;
        this.f4101g = backStackRecord.f4093t;
        this.f4102h = backStackRecord.f4240j;
        this.f4103i = backStackRecord.f4241k;
        this.f4104j = backStackRecord.f4242l;
        this.f4105k = backStackRecord.f4243m;
        this.f4106l = backStackRecord.f4244n;
        this.f4107m = backStackRecord.f4245o;
        this.f4108n = backStackRecord.f4246p;
    }

    private void fillInBackStackRecord(@NonNull BackStackRecord backStackRecord) {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            boolean z2 = true;
            if (i2 >= this.f4095a.length) {
                backStackRecord.f4236f = this.f4099e;
                backStackRecord.f4239i = this.f4100f;
                backStackRecord.f4237g = true;
                backStackRecord.f4240j = this.f4102h;
                backStackRecord.f4241k = this.f4103i;
                backStackRecord.f4242l = this.f4104j;
                backStackRecord.f4243m = this.f4105k;
                backStackRecord.f4244n = this.f4106l;
                backStackRecord.f4245o = this.f4107m;
                backStackRecord.f4246p = this.f4108n;
                return;
            }
            FragmentTransaction.Op op = new FragmentTransaction.Op();
            int i4 = i2 + 1;
            op.f4248a = this.f4095a[i2];
            if (FragmentManager.isLoggingEnabled(2)) {
                Log.v("FragmentManager", "Instantiate " + backStackRecord + " op #" + i3 + " base fragment #" + this.f4095a[i4]);
            }
            op.f4255h = Lifecycle.State.values()[this.f4097c[i3]];
            op.f4256i = Lifecycle.State.values()[this.f4098d[i3]];
            int[] iArr = this.f4095a;
            int i5 = i2 + 2;
            if (iArr[i4] == 0) {
                z2 = false;
            }
            op.f4250c = z2;
            int i6 = iArr[i5];
            op.f4251d = i6;
            int i7 = iArr[i2 + 3];
            op.f4252e = i7;
            int i8 = i2 + 5;
            int i9 = iArr[i2 + 4];
            op.f4253f = i9;
            i2 += 6;
            int i10 = iArr[i8];
            op.f4254g = i10;
            backStackRecord.f4232b = i6;
            backStackRecord.f4233c = i7;
            backStackRecord.f4234d = i9;
            backStackRecord.f4235e = i10;
            backStackRecord.b(op);
            i3++;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @NonNull
    public BackStackRecord instantiate(@NonNull FragmentManager fragmentManager) {
        BackStackRecord backStackRecord = new BackStackRecord(fragmentManager);
        fillInBackStackRecord(backStackRecord);
        backStackRecord.f4093t = this.f4101g;
        for (int i2 = 0; i2 < this.f4096b.size(); i2++) {
            String str = (String) this.f4096b.get(i2);
            if (str != null) {
                ((FragmentTransaction.Op) backStackRecord.f4231a.get(i2)).f4249b = fragmentManager.R(str);
            }
        }
        backStackRecord.d(1);
        return backStackRecord;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeIntArray(this.f4095a);
        parcel.writeStringList(this.f4096b);
        parcel.writeIntArray(this.f4097c);
        parcel.writeIntArray(this.f4098d);
        parcel.writeInt(this.f4099e);
        parcel.writeString(this.f4100f);
        parcel.writeInt(this.f4101g);
        parcel.writeInt(this.f4102h);
        TextUtils.writeToParcel(this.f4103i, parcel, 0);
        parcel.writeInt(this.f4104j);
        TextUtils.writeToParcel(this.f4105k, parcel, 0);
        parcel.writeStringList(this.f4106l);
        parcel.writeStringList(this.f4107m);
        parcel.writeInt(this.f4108n ? 1 : 0);
    }

    @NonNull
    public BackStackRecord instantiate(@NonNull FragmentManager fragmentManager, @NonNull Map<String, Fragment> map) {
        BackStackRecord backStackRecord = new BackStackRecord(fragmentManager);
        fillInBackStackRecord(backStackRecord);
        for (int i2 = 0; i2 < this.f4096b.size(); i2++) {
            String str = (String) this.f4096b.get(i2);
            if (str != null) {
                Fragment fragment = map.get(str);
                if (fragment != null) {
                    ((FragmentTransaction.Op) backStackRecord.f4231a.get(i2)).f4249b = fragment;
                } else {
                    throw new IllegalStateException("Restoring FragmentTransaction " + this.f4100f + " failed due to missing saved state for Fragment (" + str + ")");
                }
            }
        }
        return backStackRecord;
    }

    BackStackRecordState(Parcel parcel) {
        this.f4095a = parcel.createIntArray();
        this.f4096b = parcel.createStringArrayList();
        this.f4097c = parcel.createIntArray();
        this.f4098d = parcel.createIntArray();
        this.f4099e = parcel.readInt();
        this.f4100f = parcel.readString();
        this.f4101g = parcel.readInt();
        this.f4102h = parcel.readInt();
        Parcelable.Creator creator = TextUtils.CHAR_SEQUENCE_CREATOR;
        this.f4103i = (CharSequence) creator.createFromParcel(parcel);
        this.f4104j = parcel.readInt();
        this.f4105k = (CharSequence) creator.createFromParcel(parcel);
        this.f4106l = parcel.createStringArrayList();
        this.f4107m = parcel.createStringArrayList();
        this.f4108n = parcel.readInt() != 0;
    }
}
