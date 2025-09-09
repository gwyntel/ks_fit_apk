package a.a.a.a.b.j.a;

import android.os.Parcel;
import android.os.Parcelable;
import com.alibaba.ailabs.iot.mesh.task.bean.MeshControlDevice;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class a implements Parcelable.Creator<MeshControlDevice> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public MeshControlDevice createFromParcel(Parcel parcel) {
        return new MeshControlDevice(parcel);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public MeshControlDevice[] newArray(int i2) {
        return new MeshControlDevice[i2];
    }
}
