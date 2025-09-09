package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.tekartik.sqflite.Constant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SuppressLint({"BanParcelableUsage"})
/* loaded from: classes.dex */
class BackStackState implements Parcelable {
    public static final Parcelable.Creator<BackStackState> CREATOR = new Parcelable.Creator<BackStackState>() { // from class: androidx.fragment.app.BackStackState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BackStackState createFromParcel(Parcel parcel) {
            return new BackStackState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BackStackState[] newArray(int i2) {
            return new BackStackState[i2];
        }
    };

    /* renamed from: a, reason: collision with root package name */
    final List f4109a;

    /* renamed from: b, reason: collision with root package name */
    final List f4110b;

    BackStackState(List list, List list2) {
        this.f4109a = list;
        this.f4110b = list2;
    }

    List a(FragmentManager fragmentManager, Map map) {
        HashMap map2 = new HashMap(this.f4109a.size());
        for (String str : this.f4109a) {
            Fragment fragment = (Fragment) map.get(str);
            if (fragment != null) {
                map2.put(fragment.mWho, fragment);
            } else {
                Bundle bundleC = fragmentManager.Y().C(str, null);
                if (bundleC != null) {
                    ClassLoader classLoader = fragmentManager.getHost().b().getClassLoader();
                    Fragment fragmentA = ((FragmentState) bundleC.getParcelable("state")).a(fragmentManager.getFragmentFactory(), classLoader);
                    fragmentA.mSavedFragmentState = bundleC;
                    if (bundleC.getBundle("savedInstanceState") == null) {
                        fragmentA.mSavedFragmentState.putBundle("savedInstanceState", new Bundle());
                    }
                    Bundle bundle = bundleC.getBundle(Constant.PARAM_SQL_ARGUMENTS);
                    if (bundle != null) {
                        bundle.setClassLoader(classLoader);
                    }
                    fragmentA.setArguments(bundle);
                    map2.put(fragmentA.mWho, fragmentA);
                }
            }
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = this.f4110b.iterator();
        while (it.hasNext()) {
            arrayList.add(((BackStackRecordState) it.next()).instantiate(fragmentManager, map2));
        }
        return arrayList;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        parcel.writeStringList(this.f4109a);
        parcel.writeTypedList(this.f4110b);
    }

    BackStackState(Parcel parcel) {
        this.f4109a = parcel.createStringArrayList();
        this.f4110b = parcel.createTypedArrayList(BackStackRecordState.CREATOR);
    }
}
