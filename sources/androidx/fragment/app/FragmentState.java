package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;

@SuppressLint({"BanParcelableUsage"})
/* loaded from: classes.dex */
final class FragmentState implements Parcelable {
    public static final Parcelable.Creator<FragmentState> CREATOR = new Parcelable.Creator<FragmentState>() { // from class: androidx.fragment.app.FragmentState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FragmentState createFromParcel(Parcel parcel) {
            return new FragmentState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FragmentState[] newArray(int i2) {
            return new FragmentState[i2];
        }
    };

    /* renamed from: a, reason: collision with root package name */
    final String f4209a;

    /* renamed from: b, reason: collision with root package name */
    final String f4210b;

    /* renamed from: c, reason: collision with root package name */
    final boolean f4211c;

    /* renamed from: d, reason: collision with root package name */
    final int f4212d;

    /* renamed from: e, reason: collision with root package name */
    final int f4213e;

    /* renamed from: f, reason: collision with root package name */
    final String f4214f;

    /* renamed from: g, reason: collision with root package name */
    final boolean f4215g;

    /* renamed from: h, reason: collision with root package name */
    final boolean f4216h;

    /* renamed from: i, reason: collision with root package name */
    final boolean f4217i;

    /* renamed from: j, reason: collision with root package name */
    final boolean f4218j;

    /* renamed from: k, reason: collision with root package name */
    final int f4219k;

    /* renamed from: l, reason: collision with root package name */
    final String f4220l;

    /* renamed from: m, reason: collision with root package name */
    final int f4221m;

    /* renamed from: n, reason: collision with root package name */
    final boolean f4222n;

    FragmentState(Fragment fragment) {
        this.f4209a = fragment.getClass().getName();
        this.f4210b = fragment.mWho;
        this.f4211c = fragment.mFromLayout;
        this.f4212d = fragment.mFragmentId;
        this.f4213e = fragment.mContainerId;
        this.f4214f = fragment.mTag;
        this.f4215g = fragment.mRetainInstance;
        this.f4216h = fragment.mRemoving;
        this.f4217i = fragment.mDetached;
        this.f4218j = fragment.mHidden;
        this.f4219k = fragment.mMaxState.ordinal();
        this.f4220l = fragment.mTargetWho;
        this.f4221m = fragment.mTargetRequestCode;
        this.f4222n = fragment.mUserVisibleHint;
    }

    Fragment a(FragmentFactory fragmentFactory, ClassLoader classLoader) {
        Fragment fragmentInstantiate = fragmentFactory.instantiate(classLoader, this.f4209a);
        fragmentInstantiate.mWho = this.f4210b;
        fragmentInstantiate.mFromLayout = this.f4211c;
        fragmentInstantiate.mRestored = true;
        fragmentInstantiate.mFragmentId = this.f4212d;
        fragmentInstantiate.mContainerId = this.f4213e;
        fragmentInstantiate.mTag = this.f4214f;
        fragmentInstantiate.mRetainInstance = this.f4215g;
        fragmentInstantiate.mRemoving = this.f4216h;
        fragmentInstantiate.mDetached = this.f4217i;
        fragmentInstantiate.mHidden = this.f4218j;
        fragmentInstantiate.mMaxState = Lifecycle.State.values()[this.f4219k];
        fragmentInstantiate.mTargetWho = this.f4220l;
        fragmentInstantiate.mTargetRequestCode = this.f4221m;
        fragmentInstantiate.mUserVisibleHint = this.f4222n;
        return fragmentInstantiate;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("FragmentState{");
        sb.append(this.f4209a);
        sb.append(" (");
        sb.append(this.f4210b);
        sb.append(")}:");
        if (this.f4211c) {
            sb.append(" fromLayout");
        }
        if (this.f4213e != 0) {
            sb.append(" id=0x");
            sb.append(Integer.toHexString(this.f4213e));
        }
        String str = this.f4214f;
        if (str != null && !str.isEmpty()) {
            sb.append(" tag=");
            sb.append(this.f4214f);
        }
        if (this.f4215g) {
            sb.append(" retainInstance");
        }
        if (this.f4216h) {
            sb.append(" removing");
        }
        if (this.f4217i) {
            sb.append(" detached");
        }
        if (this.f4218j) {
            sb.append(" hidden");
        }
        if (this.f4220l != null) {
            sb.append(" targetWho=");
            sb.append(this.f4220l);
            sb.append(" targetRequestCode=");
            sb.append(this.f4221m);
        }
        if (this.f4222n) {
            sb.append(" userVisibleHint");
        }
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.f4209a);
        parcel.writeString(this.f4210b);
        parcel.writeInt(this.f4211c ? 1 : 0);
        parcel.writeInt(this.f4212d);
        parcel.writeInt(this.f4213e);
        parcel.writeString(this.f4214f);
        parcel.writeInt(this.f4215g ? 1 : 0);
        parcel.writeInt(this.f4216h ? 1 : 0);
        parcel.writeInt(this.f4217i ? 1 : 0);
        parcel.writeInt(this.f4218j ? 1 : 0);
        parcel.writeInt(this.f4219k);
        parcel.writeString(this.f4220l);
        parcel.writeInt(this.f4221m);
        parcel.writeInt(this.f4222n ? 1 : 0);
    }

    FragmentState(Parcel parcel) {
        this.f4209a = parcel.readString();
        this.f4210b = parcel.readString();
        this.f4211c = parcel.readInt() != 0;
        this.f4212d = parcel.readInt();
        this.f4213e = parcel.readInt();
        this.f4214f = parcel.readString();
        this.f4215g = parcel.readInt() != 0;
        this.f4216h = parcel.readInt() != 0;
        this.f4217i = parcel.readInt() != 0;
        this.f4218j = parcel.readInt() != 0;
        this.f4219k = parcel.readInt();
        this.f4220l = parcel.readString();
        this.f4221m = parcel.readInt();
        this.f4222n = parcel.readInt() != 0;
    }
}
