package com.aliyun.alink.business.devicecenter.provision.core.mesh;

import android.content.Context;
import com.aliyun.alink.business.devicecenter.api.add.LinkType;
import com.aliyun.alink.business.devicecenter.config.annotation.ConfigStrategy;

@ConfigStrategy(linkType = LinkType.ALI_APP_COMBO_MESH)
/* loaded from: classes2.dex */
public class AppComboMeshStrategy extends AppMeshStrategy {
    public AppComboMeshStrategy() {
    }

    @Override // com.aliyun.alink.business.devicecenter.provision.core.mesh.AppMeshStrategy, com.aliyun.alink.business.devicecenter.config.IConfigStrategy
    public boolean needWiFiSsidPwd() {
        return true;
    }

    public AppComboMeshStrategy(Context context) {
        super(context);
    }
}
