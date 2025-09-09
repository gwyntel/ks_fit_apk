package datasource.implemention.data;

import com.alibaba.ailabs.tg.mtop.data.BaseDataBean;
import datasource.bean.DeleteDeviceRespDataExtends;
import mtopsdk.mtop.domain.IMTOPDataObject;

/* loaded from: classes4.dex */
public class IotDeleteDeviceRespData extends BaseDataBean implements IMTOPDataObject {
    public DeleteDeviceRespDataExtends extensions;
    public boolean model;

    public static class Extensions {
        public String deviceKey;
        public int netKeyIndex;
        public int primaryUnicastAddress;

        public String getDeviceKey() {
            return this.deviceKey;
        }

        public int getNetKeyIndex() {
            return this.netKeyIndex;
        }

        public int getPrimaryUnicastAddress() {
            return this.primaryUnicastAddress;
        }

        public void setDeviceKey(String str) {
            this.deviceKey = str;
        }

        public void setNetKeyIndex(int i2) {
            this.netKeyIndex = i2;
        }

        public void setPrimaryUnicastAddress(int i2) {
            this.primaryUnicastAddress = i2;
        }
    }

    public DeleteDeviceRespDataExtends getExtensions() {
        return this.extensions;
    }

    public boolean getModel() {
        return this.model;
    }

    public void setExtensions(DeleteDeviceRespDataExtends deleteDeviceRespDataExtends) {
        this.extensions = deleteDeviceRespDataExtends;
    }

    public void setModel(boolean z2) {
        this.model = z2;
    }
}
