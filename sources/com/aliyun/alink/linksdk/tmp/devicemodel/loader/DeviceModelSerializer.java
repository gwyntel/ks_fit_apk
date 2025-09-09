package com.aliyun.alink.linksdk.tmp.devicemodel.loader;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.devicemodel.DeviceModel;
import com.aliyun.alink.linksdk.tmp.devicemodel.Event;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.aliyun.alink.linksdk.tmp.utils.LogCat;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class DeviceModelSerializer {
    protected static final String TAG = "[Tmp]DeviceModelSerializer";
    protected DeviceModelSerializer mChild;
    protected String mId;

    public DeviceModelSerializer(String str) {
        this.mId = str;
    }

    public static void addChildModel(DeviceModel deviceModel, DeviceModel deviceModel2) {
        if (deviceModel == null || deviceModel2 == null) {
            return;
        }
        addList(deviceModel.getProperties(), deviceModel2.getProperties());
        addList(deviceModel.getServices(), deviceModel2.getServices());
        addList(deviceModel.getEvents(), deviceModel2.getEvents());
    }

    protected static void addList(List list, List list2) {
        if (list2 == null || list2.isEmpty()) {
            return;
        }
        if (list == null) {
            list = new LinkedList();
        }
        list.addAll(list2);
    }

    public static String expand(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return str2;
        }
        return str + "." + str2;
    }

    protected static void expandEvent(String str, DeviceModel deviceModel) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (deviceModel == null) {
            LogCat.d(TAG, "expandEvent model empty");
            return;
        }
        if (deviceModel.getEvents() == null || deviceModel.getEvents().isEmpty()) {
            LogCat.d(TAG, "expandEvent event empty");
            return;
        }
        for (int i2 = 0; i2 < deviceModel.getEvents().size(); i2++) {
            Event event = deviceModel.getEvents().get(i2);
            if (event != null) {
                event.setName(expand(str, event.getIdentifier()));
            }
        }
    }

    public static String froamtUrl(String str, String str2) {
        return str + "/" + str2 + ".json";
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0087 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0074 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0091 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x007e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v8, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARN: Type inference failed for: r3v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String requestDeviceModel(java.lang.String r7, java.lang.String r8) throws java.lang.Throwable {
        /*
            r0 = 0
            java.net.URL r1 = new java.net.URL     // Catch: java.lang.Throwable -> L65 java.lang.Exception -> L6b
            java.lang.String r7 = froamtUrl(r7, r8)     // Catch: java.lang.Throwable -> L65 java.lang.Exception -> L6b
            r1.<init>(r7)     // Catch: java.lang.Throwable -> L65 java.lang.Exception -> L6b
            java.net.URLConnection r7 = r1.openConnection()     // Catch: java.lang.Throwable -> L65 java.lang.Exception -> L6b
            java.net.HttpURLConnection r7 = (java.net.HttpURLConnection) r7     // Catch: java.lang.Throwable -> L65 java.lang.Exception -> L6b
            r8 = 10000(0x2710, float:1.4013E-41)
            r7.setConnectTimeout(r8)     // Catch: java.lang.Throwable -> L5c java.lang.Exception -> L61
            r8 = 5000(0x1388, float:7.006E-42)
            r7.setReadTimeout(r8)     // Catch: java.lang.Throwable -> L5c java.lang.Exception -> L61
            java.io.BufferedInputStream r8 = new java.io.BufferedInputStream     // Catch: java.lang.Throwable -> L5c java.lang.Exception -> L61
            java.io.InputStream r1 = r7.getInputStream()     // Catch: java.lang.Throwable -> L5c java.lang.Exception -> L61
            r8.<init>(r1)     // Catch: java.lang.Throwable -> L5c java.lang.Exception -> L61
            r1 = 1024(0x400, float:1.435E-42)
            byte[] r2 = new byte[r1]     // Catch: java.lang.Throwable -> L55 java.lang.Exception -> L59
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Throwable -> L55 java.lang.Exception -> L59
            r3.<init>()     // Catch: java.lang.Throwable -> L55 java.lang.Exception -> L59
        L2c:
            r4 = 0
            int r5 = r8.read(r2, r4, r1)     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L3b
            r6 = -1
            if (r5 == r6) goto L3d
            r3.write(r2, r4, r5)     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L3b
            goto L2c
        L38:
            r0 = move-exception
            goto L85
        L3b:
            r1 = move-exception
            goto L6f
        L3d:
            java.lang.String r0 = r3.toString()     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L3b
            r8.close()     // Catch: java.lang.Exception -> L45
            goto L49
        L45:
            r8 = move-exception
            r8.printStackTrace()
        L49:
            r3.close()     // Catch: java.lang.Exception -> L4d
            goto L51
        L4d:
            r8 = move-exception
        L4e:
            r8.printStackTrace()
        L51:
            r7.disconnect()
            goto L84
        L55:
            r1 = move-exception
            r3 = r0
            r0 = r1
            goto L85
        L59:
            r1 = move-exception
            r3 = r0
            goto L6f
        L5c:
            r8 = move-exception
            r3 = r0
            r0 = r8
            r8 = r3
            goto L85
        L61:
            r1 = move-exception
            r8 = r0
        L63:
            r3 = r8
            goto L6f
        L65:
            r7 = move-exception
            r8 = r0
            r3 = r8
            r0 = r7
            r7 = r3
            goto L85
        L6b:
            r1 = move-exception
            r7 = r0
            r8 = r7
            goto L63
        L6f:
            r1.printStackTrace()     // Catch: java.lang.Throwable -> L38
            if (r8 == 0) goto L7c
            r8.close()     // Catch: java.lang.Exception -> L78
            goto L7c
        L78:
            r8 = move-exception
            r8.printStackTrace()
        L7c:
            if (r3 == 0) goto L51
            r3.close()     // Catch: java.lang.Exception -> L82
            goto L51
        L82:
            r8 = move-exception
            goto L4e
        L84:
            return r0
        L85:
            if (r8 == 0) goto L8f
            r8.close()     // Catch: java.lang.Exception -> L8b
            goto L8f
        L8b:
            r8 = move-exception
            r8.printStackTrace()
        L8f:
            if (r3 == 0) goto L99
            r3.close()     // Catch: java.lang.Exception -> L95
            goto L99
        L95:
            r8 = move-exception
            r8.printStackTrace()
        L99:
            r7.disconnect()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.alink.linksdk.tmp.devicemodel.loader.DeviceModelSerializer.requestDeviceModel(java.lang.String, java.lang.String):java.lang.String");
    }

    public void appendSerializer(DeviceModelSerializer deviceModelSerializer) {
        DeviceModelSerializer deviceModelSerializer2 = this.mChild;
        if (deviceModelSerializer2 == null) {
            this.mChild = deviceModelSerializer;
        } else {
            deviceModelSerializer2.appendSerializer(deviceModelSerializer);
        }
    }

    public abstract boolean deserialize(String str, String str2, ILoaderHandler iLoaderHandler);

    protected DeviceModel deserializeInner(String str) {
        return (DeviceModel) GsonUtils.fromJson(str, new TypeToken<DeviceModel>() { // from class: com.aliyun.alink.linksdk.tmp.devicemodel.loader.DeviceModelSerializer.1
        }.getType());
    }

    public DeviceModel deserializeSync(String str, String str2) {
        return deserializeInner(str2);
    }

    protected DeviceModelSerializer dispatch(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (TextUtils.isEmpty(this.mId)) {
            LogCat.e(TAG, "dispatch empty id error");
        } else if (this.mId.equalsIgnoreCase(str)) {
            return this;
        }
        DeviceModelSerializer deviceModelSerializer = this.mChild;
        if (deviceModelSerializer != null) {
            return deviceModelSerializer.dispatch(str);
        }
        return null;
    }

    public abstract String serialize(String str, DeviceModel deviceModel);

    protected String serializeInner(DeviceModel deviceModel) {
        return GsonUtils.toJson(deviceModel);
    }
}
