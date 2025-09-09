package a.a.a.a.b.i;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.alibaba.ailabs.iot.mesh.bean.MeshNodeStatus;
import com.alibaba.ailabs.iot.mesh.utils.Utils;

/* loaded from: classes.dex */
public class x extends BroadcastReceiver {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ J f1523a;

    public x(J j2) {
        this.f1523a = j2;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (Utils.ACTION_PROVISIONING_STATE.equals(intent.getAction())) {
            if (intent.getIntExtra(Utils.EXTRA_PROVISIONING_STATE, -1) == MeshNodeStatus.PROVISIONING_FAILED.getState()) {
                this.f1523a.i();
            }
        } else if (Utils.ACTION_BIND_STATE.equals(intent.getAction())) {
            intent.getIntExtra(Utils.EXTRA_BIND_CODE, -1);
            this.f1523a.i();
        }
    }
}
