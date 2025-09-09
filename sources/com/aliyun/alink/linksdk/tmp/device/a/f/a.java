package com.aliyun.alink.linksdk.tmp.device.a.f;

import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.device.deviceshadow.TDeviceShadow;
import com.aliyun.alink.linksdk.tmp.devicemodel.DeviceModel;
import com.aliyun.alink.linksdk.tmp.devicemodel.Property;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class a extends b {
    public a(com.aliyun.alink.linksdk.tmp.device.a aVar, TDeviceShadow tDeviceShadow, DeviceBasicData deviceBasicData, IDevListener iDevListener) {
        super(tDeviceShadow, aVar, deviceBasicData, iDevListener);
        a(aVar);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.f.b, com.aliyun.alink.linksdk.tmp.device.a.d, com.aliyun.alink.linksdk.tmp.device.a.a
    public boolean a() {
        com.aliyun.alink.linksdk.tmp.device.a aVar;
        ArrayList arrayList = new ArrayList();
        if (this.f11296k == null && (aVar = this.f11293h) != null) {
            this.f11296k = aVar.d();
        }
        DeviceModel deviceModel = this.f11296k;
        if (deviceModel != null && deviceModel.getProperties() != null && !this.f11296k.getProperties().isEmpty()) {
            for (Property property : this.f11296k.getProperties()) {
                if (property != null) {
                    arrayList.add(property.getIdentifier());
                }
            }
        }
        a((List<String>) arrayList);
        return super.a();
    }
}
