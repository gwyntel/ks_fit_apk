package datasource.bean.local;

import java.util.List;

/* loaded from: classes4.dex */
public class DeviceBindModel extends BaseDeviceInfoModel {
    public List<DeviceBindItem> data;

    public List<DeviceBindItem> getData() {
        return this.data;
    }

    public void setData(List<DeviceBindItem> list) {
        this.data = list;
    }
}
