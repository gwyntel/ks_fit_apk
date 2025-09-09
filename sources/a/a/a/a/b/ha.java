package a.a.a.a.b;

import android.content.Context;
import android.os.Looper;
import com.alibaba.ailabs.iot.mesh.DeviceProvisioningWorker;
import com.alibaba.ailabs.iot.mesh.MeshService;
import com.alibaba.ailabs.iot.mesh.bean.ExtendedBluetoothDevice;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class ha implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ExtendedBluetoothDevice f1354a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ boolean f1355b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ MeshService f1356c;

    public ha(MeshService meshService, ExtendedBluetoothDevice extendedBluetoothDevice, boolean z2) {
        this.f1356c = meshService;
        this.f1354a = extendedBluetoothDevice;
        this.f1355b = z2;
    }

    @Override // java.lang.Runnable
    public void run() {
        Looper.prepare();
        if (this.f1356c.deviceProvisioningWorkerArray == null) {
            this.f1356c.deviceProvisioningWorkerArray = new ArrayList();
        }
        Context applicationContext = this.f1356c.getApplicationContext();
        MeshService meshService = this.f1356c;
        DeviceProvisioningWorker deviceProvisioningWorker = new DeviceProvisioningWorker(applicationContext, meshService, meshService.mSigmeshKeys, this.f1356c.mMeshManagerApi.b(), this.f1356c.mOnReadyToBindHandler, this.f1356c.mConcurrentProvisionContext);
        deviceProvisioningWorker.a(this.f1356c.mGlobalProvisionFinishedListener);
        deviceProvisioningWorker.a(this.f1354a, this.f1355b, this.f1356c.mProvisioningExtensionsParams);
        this.f1356c.deviceProvisioningWorkerArray.add(deviceProvisioningWorker);
    }
}
