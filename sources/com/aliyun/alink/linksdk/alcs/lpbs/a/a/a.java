package com.aliyun.alink.linksdk.alcs.lpbs.a.a;

import com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.ICloudChannelFactory;
import com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.IDataDownListener;
import com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.IThingCloudChannel;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDeviceInfo;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f10685a = "[AlcsLPBS]CloudChannelMgr";

    /* renamed from: b, reason: collision with root package name */
    private Map<String, b> f10686b = new ConcurrentHashMap();

    /* renamed from: c, reason: collision with root package name */
    private ICloudChannelFactory f10687c;

    public IThingCloudChannel b(PalDeviceInfo palDeviceInfo) {
        return this.f10686b.get(palDeviceInfo.getDevId());
    }

    public void a(ICloudChannelFactory iCloudChannelFactory) {
        this.f10687c = iCloudChannelFactory;
    }

    public void a(PalDeviceInfo palDeviceInfo, Map<String, Object> map, final IDataDownListener iDataDownListener, final ICloudChannelFactory.FactoryListener factoryListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (palDeviceInfo == null) {
            ALog.e(f10685a, "createCloudChannel deviceInfo null");
            if (factoryListener != null) {
                factoryListener.onCreate(palDeviceInfo, null);
                return;
            }
            return;
        }
        ALog.d(f10685a, "createCloudChannel deviceInfo:" + palDeviceInfo.toString() + " listener: " + iDataDownListener);
        b bVar = this.f10686b.get(palDeviceInfo.getDevId());
        if (bVar != null) {
            ALog.d(f10685a, "channel exist");
            if (factoryListener != null) {
                factoryListener.onCreate(palDeviceInfo, bVar);
                return;
            }
            return;
        }
        ICloudChannelFactory iCloudChannelFactory = this.f10687c;
        if (iCloudChannelFactory == null) {
            ALog.e(f10685a, "mCloudChannelFactory null");
            if (factoryListener != null) {
                factoryListener.onCreate(palDeviceInfo, null);
                return;
            }
            return;
        }
        iCloudChannelFactory.createCloudChannel(palDeviceInfo, map, new ICloudChannelFactory.FactoryListener() { // from class: com.aliyun.alink.linksdk.alcs.lpbs.a.a.a.1
            @Override // com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.ICloudChannelFactory.FactoryListener
            public void onCreate(PalDeviceInfo palDeviceInfo2, IThingCloudChannel iThingCloudChannel) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.d(a.f10685a, "createCloudChannel onCreate channel:" + iThingCloudChannel);
                if (iThingCloudChannel == null) {
                    ALog.e(a.f10685a, "createCloudChannel error channel null");
                    ICloudChannelFactory.FactoryListener factoryListener2 = factoryListener;
                    if (factoryListener2 != null) {
                        factoryListener2.onCreate(palDeviceInfo2, null);
                        return;
                    }
                    return;
                }
                IDataDownListener iDataDownListener2 = iDataDownListener;
                if (iDataDownListener2 != null) {
                    iThingCloudChannel.addDownDataListener(iDataDownListener2);
                }
                b bVar2 = new b(iThingCloudChannel, iDataDownListener);
                a.this.f10686b.put(palDeviceInfo2.getDevId(), bVar2);
                ICloudChannelFactory.FactoryListener factoryListener3 = factoryListener;
                if (factoryListener3 != null) {
                    factoryListener3.onCreate(palDeviceInfo2, bVar2);
                }
            }
        });
    }

    public void a(PalDeviceInfo palDeviceInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (palDeviceInfo == null) {
            ALog.e(f10685a, "removeCloudChannel deviceInfo null");
            return;
        }
        ALog.d(f10685a, "removeCloudChannel deviceInfo: " + palDeviceInfo.toString());
        b bVar = this.f10686b.get(palDeviceInfo.getDevId());
        if (bVar != null && bVar.a() != null) {
            bVar.a().removeDownDataListener(null);
        }
        ICloudChannelFactory iCloudChannelFactory = this.f10687c;
        if (iCloudChannelFactory != null) {
            iCloudChannelFactory.releaseCloudChannel(palDeviceInfo);
        }
        this.f10686b.remove(palDeviceInfo.getDevId());
    }
}
