package datasource.channel.data;

import com.aliyun.alink.linksdk.connectsdk.BaseApiResponse;
import datasource.bean.Sigmesh;
import java.util.List;

/* loaded from: classes4.dex */
public class FeiyanGroupControlData extends BaseApiResponse {
    public String deviceType;
    public boolean operateAll;
    public List<Sigmesh> sigmesh;

    public String getDeviceType() {
        return this.deviceType;
    }

    public List<Sigmesh> getSigmesh() {
        return this.sigmesh;
    }

    public boolean isOperateAll() {
        return this.operateAll;
    }

    public void setDeviceType(String str) {
        this.deviceType = str;
    }

    public void setOperateAll(boolean z2) {
        this.operateAll = z2;
    }

    public void setSigmesh(List<Sigmesh> list) {
        this.sigmesh = list;
    }
}
