package a.a.a.a.b.i.c;

import aisscanner.ScanFilter;
import aisscanner.ScanResult;
import com.alibaba.ailabs.iot.aisbase.scanner.ILeScanStrategy;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceSubtype;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper;
import com.alibaba.ailabs.iot.mesh.provision.bean.FastProvisionDevice;
import com.alibaba.ailabs.tg.utils.ConvertUtils;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class f implements ILeScanStrategy {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ g f1444a;

    public f(g gVar) {
        this.f1444a = gVar;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.scanner.ILeScanStrategy
    public BluetoothDeviceWrapper createFromScanResult(ScanResult scanResult) {
        if (scanResult == null || scanResult.getScanRecord() == null) {
            a.a.a.a.b.m.a.c(this.f1444a.f1445a, "scanResult or scanRecord is null");
            return null;
        }
        byte[] bytes = scanResult.getScanRecord().getBytes();
        if (bytes == null || bytes.length < 8) {
            String str = this.f1444a.f1445a;
            StringBuilder sb = new StringBuilder();
            sb.append("originData is ");
            sb.append(bytes == null ? TmpConstant.GROUP_ROLE_UNKNOWN : Integer.valueOf(bytes.length));
            a.a.a.a.b.m.a.c(str, sb.toString());
            return null;
        }
        a.a.a.a.b.m.a.c(this.f1444a.f1445a, "--createFromScanResult: " + ConvertUtils.bytes2HexString(bytes));
        int length = bytes.length;
        int i2 = length + (-3);
        byte[] bArr = new byte[i2];
        System.arraycopy(bytes, 3, bArr, 0, i2);
        if (bArr[1] != -1) {
            a.a.a.a.b.m.a.c(this.f1444a.f1445a, "adType illegal, except 255, receive " + ((int) bArr[1]));
            return null;
        }
        if (bArr[2] == 1 && bArr[3] == -88) {
            int i3 = length - 8;
            byte[] bArr2 = new byte[i3];
            System.arraycopy(bArr, 5, bArr2, 0, i3);
            FastProvisionDevice fastProvisionDevice = new FastProvisionDevice();
            fastProvisionDevice.setScanResult(scanResult);
            fastProvisionDevice.a(bArr2);
            return fastProvisionDevice;
        }
        a.a.a.a.b.m.a.c(this.f1444a.f1445a, "cid illegal. except 0x01A8, receive " + ((int) bArr[2]) + " " + ((int) bArr[3]));
        return null;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.scanner.ILeScanStrategy
    public BluetoothDeviceSubtype getBluetoothDeviceSubtype() {
        return BluetoothDeviceSubtype.UNKNOWN;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.scanner.ILeScanStrategy
    public List<ScanFilter> getCustomScanFilters() {
        return new ArrayList();
    }
}
