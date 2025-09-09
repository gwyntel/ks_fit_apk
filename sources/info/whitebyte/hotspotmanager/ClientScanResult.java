package info.whitebyte.hotspotmanager;

/* loaded from: classes4.dex */
public class ClientScanResult {
    private String Device;
    private String HWAddr;
    private String IpAddr;
    private boolean isReachable;

    public ClientScanResult(String str, String str2, String str3, boolean z2) {
        this.IpAddr = str;
        this.HWAddr = str2;
        this.Device = str3;
        this.isReachable = z2;
    }

    public String getDevice() {
        return this.Device;
    }

    public String getHWAddr() {
        return this.HWAddr;
    }

    public String getIpAddr() {
        return this.IpAddr;
    }

    public boolean isReachable() {
        return this.isReachable;
    }

    public void setDevice(String str) {
        this.Device = str;
    }

    public void setHWAddr(String str) {
        this.HWAddr = str;
    }

    public void setIpAddr(String str) {
        this.IpAddr = str;
    }

    public void setReachable(boolean z2) {
        this.isReachable = z2;
    }
}
