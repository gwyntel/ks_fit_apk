package com.aliyun.alink.linksdk.tmp.timing;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import androidx.exifinterface.media.ExifInterface;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.aliyun.alink.linksdk.tmp.device.panel.PanelDevice;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelEventCallback;
import com.aliyun.alink.linksdk.tmp.device.payload.discovery.GetTslResponsePayload;
import com.aliyun.alink.linksdk.tmp.timing.DeviceTimerAttributeModel;
import com.aliyun.alink.linksdk.tmp.timing.MeshTimerModel;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class MeshTimerTransaction {
    private static final String TAG = "[Tmp]MeshTimerTransaction";
    private DeviceTimerAttributeModel mDeviceTimerAttribute;
    private String mTargetDeviceIotID;
    private PanelDevice mTargetPanelDevice;
    private static Map<String, MeshTimerTransaction> mTimerTransactionInstances = new LinkedHashMap();
    private static ScheduledExecutorService mScheduledControlService = new ScheduledThreadPoolExecutor(1, new ThreadFactory() { // from class: com.aliyun.alink.linksdk.tmp.timing.MeshTimerTransaction.1
        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, MeshTimerTransaction.TAG);
        }
    });
    private final int TIMER_ADD_OP = 0;
    private final int TIMER_DELETE_OP = 1;
    private final int TIMER_EDIT_OP = 2;
    private LocalTimerExecuteContext mLocalTimerExecuteContext = null;
    private DeviceTimerAttributeModel.ValueItem mUnsetTimerValueObj = null;
    private int mLimitSize = 0;

    public interface ErrorCode {
        public static final int ERROR_EXIT_PENDING_TASK = -7;
        public static final int ERROR_INVALID_TIMER_ID = -4;
        public static final int ERROR_INVOCATION_PROCESS = -2;
        public static final int ERROR_SEND_FAILED = -5;
        public static final int ERROR_SERVER_API_ERROR = -9;
        public static final int ERROR_SET_TIMER_EXCEPTION = -10;
        public static final int ERROR_SET_TIMER_TIMEOUT = -6;
        public static final int ERROR_TIMING_ALREADY_FULL = -3;
        public static final int ERROR_UNSUPPORTED_OPERATIONS = -1;
        public static final int ERROR_WAIT_TIME_CALIBRATION_COMPLETE = -8;
    }

    private static class LocalTimerExecuteContext {
        int action;
        MeshTimerModel changedOperationTimerModel;
        int changedTimersIndex;
        DeviceTimerAttributeModel.ValueItem changedValueItem;
        DeviceTimerAttributeModel.ValueItem rawValueItem;
        ScheduledFuture<?> resultCheckScheduledFuture;
        ScheduledFuture<?> timeoutScheduledFuture;
        ITimerActionCallback<List<MeshTimerModel>> userCallback;

        private LocalTimerExecuteContext() {
        }
    }

    private MeshTimerTransaction(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.mTargetDeviceIotID = str;
        PanelDevice panelDevice = new PanelDevice(str);
        this.mTargetPanelDevice = panelDevice;
        panelDevice.subAllEvents(new IPanelEventCallback() { // from class: com.aliyun.alink.linksdk.tmp.timing.MeshTimerTransaction.2
            @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelEventCallback
            public void onNotify(String str2, String str3, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.d(MeshTimerTransaction.TAG, "onNotify, topic: " + str3 + ", data: " + obj.toString());
                if (TmpConstant.MQTT_TOPIC_PROPERTIES.equals(str3)) {
                    MeshTimerTransaction.this.mTargetDeviceIotID.equals(str2);
                }
            }
        }, new IPanelCallback() { // from class: com.aliyun.alink.linksdk.tmp.timing.MeshTimerTransaction.3
            @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback
            public void onComplete(boolean z2, @Nullable Object obj) {
            }
        });
    }

    private void getTimerLimitSize(final ITimerActionCallback<Integer> iTimerActionCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (iTimerActionCallback == null) {
            ALog.d(TAG, "getDeviceTSL() userCallback == null");
        } else {
            ALog.d(TAG, "getDeviceTSL() called");
            this.mTargetPanelDevice.getDeviceTSL(new IPanelCallback() { // from class: com.aliyun.alink.linksdk.tmp.timing.MeshTimerTransaction.5
                @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback
                public void onComplete(boolean z2, @Nullable Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    Map map;
                    String str;
                    ALog.i(MeshTimerTransaction.TAG, "getDeviceTSL() complete, isSuccess: " + z2 + ", data: " + JSON.toJSONString(obj));
                    if (!z2 || !(obj instanceof GetTslResponsePayload)) {
                        iTimerActionCallback.onFailure(-1, "get TSL failed");
                        return;
                    }
                    Object obj2 = ((GetTslResponsePayload) obj).data;
                    if (obj2 instanceof Map) {
                        List list = (List) ((Map) obj2).get(TmpConstant.DEVICE_MODEL_PROPERTIES);
                        if (list == null) {
                            str = "invalid TSL, undefined properties";
                        } else {
                            Iterator it = list.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    map = null;
                                    break;
                                } else {
                                    map = (Map) it.next();
                                    if ("DeviceTimer".equals((String) map.get("identifier"))) {
                                        break;
                                    }
                                }
                            }
                            if (map == null) {
                                str = "invalid TSL, undefined DeviceTimer property";
                            } else {
                                Map map2 = (Map) map.get("dataType");
                                if (map2 == null) {
                                    str = "invalid TSL, data type is incorrect for DeviceTimer property";
                                } else {
                                    Map map3 = (Map) map2.get("specs");
                                    if (map3 == null) {
                                        str = "invalid TSL, specs cannot be empty for DeviceTimer property";
                                    } else {
                                        if (map3.get("size") != null) {
                                            iTimerActionCallback.onSuccess(Integer.valueOf(Integer.parseInt(map3.get("size").toString())));
                                            return;
                                        }
                                        str = "invalid TSL, size in spec cannot be empty for DeviceTimer property";
                                    }
                                }
                            }
                        }
                        iTimerActionCallback.onFailure(-1, str);
                    }
                }
            });
        }
    }

    public static MeshTimerTransaction initWithIotID(String str) {
        MeshTimerTransaction meshTimerTransaction;
        if (mTimerTransactionInstances.get(str) != null) {
            return mTimerTransactionInstances.get(str);
        }
        synchronized (MeshTimerTransaction.class) {
            try {
                meshTimerTransaction = mTimerTransactionInstances.get(str);
                if (meshTimerTransaction == null) {
                    meshTimerTransaction = new MeshTimerTransaction(str);
                    mTimerTransactionInstances.put(str, meshTimerTransaction);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return meshTimerTransaction;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyFailed(ITimerActionCallback iTimerActionCallback, int i2, String str) {
        if (iTimerActionCallback != null) {
            try {
                iTimerActionCallback.onFailure(i2, str);
            } catch (Exception unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> void notifySuccess(ITimerActionCallback<T> iTimerActionCallback, T t2) {
        if (iTimerActionCallback != null) {
            try {
                iTimerActionCallback.onSuccess(t2);
            } catch (Exception unused) {
            }
        }
    }

    private DeviceTimerAttributeModel.ValueItem obtainUnsetTimerValue() {
        DeviceTimerAttributeModel.ValueItem valueItem = this.mUnsetTimerValueObj;
        if (valueItem != null) {
            return valueItem;
        }
        DeviceTimerAttributeModel.ValueItem valueItem2 = new DeviceTimerAttributeModel.ValueItem();
        this.mUnsetTimerValueObj = valueItem2;
        valueItem2.E = 0;
        valueItem2.Y = 0;
        return valueItem2;
    }

    private boolean preCheckEnvBeforeConfigTimer(ITimerActionCallback<List<MeshTimerModel>> iTimerActionCallback) {
        DeviceTimerAttributeModel deviceTimerAttributeModel = this.mDeviceTimerAttribute;
        if (deviceTimerAttributeModel == null) {
            notifyFailed(iTimerActionCallback, -2, "method getDeviceTimerList must be called first");
            return false;
        }
        if (deviceTimerAttributeModel.getValue() == null || this.mDeviceTimerAttribute.getValue().size() == 0) {
            notifyFailed(iTimerActionCallback, -1, "need check whether the TSL configuration of the product supports local timing");
            return false;
        }
        if (this.mLocalTimerExecuteContext == null) {
            return true;
        }
        notifyFailed(iTimerActionCallback, -7, "need to wait for the previous task call to complete");
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void queryTimerOperateTask(final ITimerActionCallback<List<MeshTimerModel>> iTimerActionCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "queryTimerOperateTask() called");
        this.mTargetPanelDevice.queryTimerDownlinkTask(new IPanelCallback() { // from class: com.aliyun.alink.linksdk.tmp.timing.MeshTimerTransaction.7
            @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback
            public void onComplete(boolean z2, @Nullable Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                MeshTimerModel meshTimerModelBuild;
                ALog.i(MeshTimerTransaction.TAG, "queryTimerDownlinkTask complete, isSuccess: " + z2 + ", data: " + JSON.toJSONString(obj));
                if (z2 && (obj instanceof GetTslResponsePayload)) {
                    Object obj2 = ((GetTslResponsePayload) obj).data;
                    if (obj2 instanceof Map) {
                        Map map = (Map) ((Map) obj2).get("timerOperateTask");
                        if (map == null) {
                            iTimerActionCallback.onFailure(-9, "unknown error, timerOperateTask is empty");
                            return;
                        }
                        int iIntValue = ((Integer) map.get("errorCode")).intValue();
                        LinkedList linkedList = new LinkedList();
                        if (iIntValue != 201) {
                            JSONObject object = JSON.parseObject(obj2.toString());
                            if (object.getJSONObject("deviceTimer") != null) {
                                MeshTimerTransaction.this.mDeviceTimerAttribute = (DeviceTimerAttributeModel) object.getJSONObject("deviceTimer").toJavaObject(DeviceTimerAttributeModel.class);
                                List<DeviceTimerAttributeModel.ValueItem> value = MeshTimerTransaction.this.mDeviceTimerAttribute.getValue();
                                for (int i2 = 0; i2 < MeshTimerTransaction.this.mLimitSize; i2++) {
                                    if (value.size() > i2) {
                                        meshTimerModelBuild = MeshTimerModel.fromTimerAttributeValueItem(value.get(i2));
                                    } else {
                                        meshTimerModelBuild = new MeshTimerModel.NormalTimerBuilder().build();
                                        value.add(meshTimerModelBuild.toAttributeModel());
                                    }
                                    meshTimerModelBuild.setTimerID(String.valueOf(i2));
                                    linkedList.add(meshTimerModelBuild);
                                }
                            }
                        }
                        if (iIntValue == 200) {
                            MeshTimerTransaction.this.notifySuccess(iTimerActionCallback, linkedList);
                        } else {
                            iTimerActionCallback.onFailure(iIntValue, (String) map.get("errorMessage"));
                        }
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void realGetDeviceTimerList(final ITimerActionCallback<List<MeshTimerModel>> iTimerActionCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "getDeviceTimerList() called");
        this.mTargetPanelDevice.getProperties(new IPanelCallback() { // from class: com.aliyun.alink.linksdk.tmp.timing.MeshTimerTransaction.6
            @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback
            public void onComplete(boolean z2, @Nullable Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                DeviceTimerAttributeModel.ValueItem valueItem;
                ALog.i(MeshTimerTransaction.TAG, "getDeviceTimerList complete, isSuccess: " + z2 + ", data: " + obj);
                if (z2 && obj != null) {
                    JSONObject object = JSON.parseObject(obj.toString());
                    if (200 == object.getInteger("code").intValue()) {
                        JSONObject jSONObject = object.getJSONObject("data");
                        LinkedList linkedList = new LinkedList();
                        if (jSONObject.getJSONObject("DeviceTimer") != null) {
                            MeshTimerTransaction.this.mDeviceTimerAttribute = (DeviceTimerAttributeModel) jSONObject.getJSONObject("DeviceTimer").toJavaObject(DeviceTimerAttributeModel.class);
                            List<DeviceTimerAttributeModel.ValueItem> value = MeshTimerTransaction.this.mDeviceTimerAttribute.getValue();
                            int size = value.size();
                            for (int i2 = 0; i2 < MeshTimerTransaction.this.mLimitSize; i2++) {
                                if (i2 < size) {
                                    valueItem = value.get(i2);
                                } else {
                                    valueItem = new DeviceTimerAttributeModel.ValueItem();
                                    valueItem.Y = 0;
                                    valueItem.E = 0;
                                    value.add(valueItem);
                                }
                                MeshTimerModel meshTimerModelFromTimerAttributeValueItem = MeshTimerModel.fromTimerAttributeValueItem(valueItem);
                                meshTimerModelFromTimerAttributeValueItem.setTimerID(String.valueOf(i2));
                                linkedList.add(meshTimerModelFromTimerAttributeValueItem);
                            }
                        }
                        MeshTimerTransaction.this.notifySuccess(iTimerActionCallback, linkedList);
                        return;
                    }
                }
                MeshTimerTransaction.this.notifyFailed(iTimerActionCallback, -9, obj == null ? "" : JSON.toJSONString(obj));
            }
        });
    }

    private void realSetDeviceTimer(List<DeviceTimerAttributeModel.ValueItem> list, final int i2, final DeviceTimerAttributeModel.ValueItem valueItem, final int i3, final ITimerActionCallback<List<MeshTimerModel>> iTimerActionCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final DeviceTimerAttributeModel.ValueItem valueItem2 = list.get(i2);
        HashMap map = new HashMap();
        map.put("DeviceTimer", list);
        HashMap map2 = new HashMap();
        map2.put("iotId", this.mTargetDeviceIotID);
        map2.put("items", map);
        this.mTargetPanelDevice.setProperties(JSON.toJSONString(map2, new PropertyFilter() { // from class: com.aliyun.alink.linksdk.tmp.timing.MeshTimerTransaction.10
            @Override // com.alibaba.fastjson.serializer.PropertyFilter
            public boolean apply(Object obj, String str, Object obj2) {
                if (!(obj instanceof DeviceTimerAttributeModel.ValueItem)) {
                    return true;
                }
                DeviceTimerAttributeModel.ValueItem valueItem3 = (DeviceTimerAttributeModel.ValueItem) obj;
                if (valueItem3.Y == 0 && valueItem3.E == 1) {
                    if (TextUtils.isEmpty(valueItem3.N)) {
                        return ("R".equals(str) || ExifInterface.LATITUDE_SOUTH.equals(str) || str.equals("N")) ? false : true;
                    }
                    return true;
                }
                if (!valueItem3.checkIsSet()) {
                    return ExifInterface.LONGITUDE_EAST.equals(str) || "Y".equals(str);
                }
                int i4 = valueItem3.Y;
                if (i4 == 2 || i4 == 1) {
                    return ("R".equals(str) || ExifInterface.LATITUDE_SOUTH.equals(str) || "N".equals(str)) ? false : true;
                }
                return true;
            }
        }, new SerializerFeature[0]), new IPanelCallback() { // from class: com.aliyun.alink.linksdk.tmp.timing.MeshTimerTransaction.11
            @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback
            public void onComplete(boolean z2, @Nullable Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.d(MeshTimerTransaction.TAG, "setProperties complete, isSuccess: " + z2 + ", data: " + obj.toString());
                MeshTimerTransaction.this.setLocalTimerExecuteContext(i2, valueItem2, valueItem, i3, iTimerActionCallback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setLocalTimerExecuteContext(int i2, DeviceTimerAttributeModel.ValueItem valueItem, DeviceTimerAttributeModel.ValueItem valueItem2, int i3, ITimerActionCallback<List<MeshTimerModel>> iTimerActionCallback) {
        if (this.mLocalTimerExecuteContext != null) {
            return;
        }
        LocalTimerExecuteContext localTimerExecuteContext = new LocalTimerExecuteContext();
        this.mLocalTimerExecuteContext = localTimerExecuteContext;
        localTimerExecuteContext.changedTimersIndex = i2;
        localTimerExecuteContext.changedValueItem = valueItem;
        localTimerExecuteContext.rawValueItem = valueItem2;
        localTimerExecuteContext.action = i3;
        localTimerExecuteContext.userCallback = iTimerActionCallback;
        ScheduledExecutorService scheduledExecutorService = mScheduledControlService;
        Runnable runnable = new Runnable() { // from class: com.aliyun.alink.linksdk.tmp.timing.MeshTimerTransaction.8
            @Override // java.lang.Runnable
            public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.e(MeshTimerTransaction.TAG, "timeout event trigger");
                ITimerActionCallback<List<MeshTimerModel>> iTimerActionCallback2 = MeshTimerTransaction.this.mLocalTimerExecuteContext.userCallback;
                MeshTimerTransaction.this.unsetLocalTimerExecuteContext();
                MeshTimerTransaction.this.notifyFailed(iTimerActionCallback2, -6, "query timer operate task timeout");
            }
        };
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        localTimerExecuteContext.timeoutScheduledFuture = scheduledExecutorService.schedule(runnable, 5000L, timeUnit);
        this.mLocalTimerExecuteContext.resultCheckScheduledFuture = mScheduledControlService.scheduleAtFixedRate(new Runnable() { // from class: com.aliyun.alink.linksdk.tmp.timing.MeshTimerTransaction.9
            @Override // java.lang.Runnable
            public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                MeshTimerTransaction.this.queryTimerOperateTask(new ITimerActionCallback<List<MeshTimerModel>>() { // from class: com.aliyun.alink.linksdk.tmp.timing.MeshTimerTransaction.9.1
                    @Override // com.aliyun.alink.linksdk.tmp.timing.ITimerActionCallback
                    public void onFailure(int i4, String str) {
                        if (i4 != 201) {
                            ITimerActionCallback<List<MeshTimerModel>> iTimerActionCallback2 = MeshTimerTransaction.this.mLocalTimerExecuteContext.userCallback;
                            MeshTimerTransaction.this.unsetLocalTimerExecuteContext();
                            if (i4 == 33129) {
                                MeshTimerTransaction.this.notifyFailed(iTimerActionCallback2, -8, str);
                            } else {
                                MeshTimerTransaction.this.notifyFailed(iTimerActionCallback2, -10, str);
                            }
                        }
                    }

                    @Override // com.aliyun.alink.linksdk.tmp.timing.ITimerActionCallback
                    public void onSuccess(List<MeshTimerModel> list) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                        ALog.i(MeshTimerTransaction.TAG, "On successful to queryTimerDownlinkTask");
                        MeshTimerTransaction meshTimerTransaction = MeshTimerTransaction.this;
                        meshTimerTransaction.notifySuccess(meshTimerTransaction.mLocalTimerExecuteContext.userCallback, list);
                        MeshTimerTransaction.this.unsetLocalTimerExecuteContext();
                    }
                });
            }
        }, 1000L, 1500L, timeUnit);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unsetLocalTimerExecuteContext() {
        ScheduledFuture<?> scheduledFuture = this.mLocalTimerExecuteContext.resultCheckScheduledFuture;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
        ScheduledFuture<?> scheduledFuture2 = this.mLocalTimerExecuteContext.timeoutScheduledFuture;
        if (scheduledFuture2 != null) {
            scheduledFuture2.cancel(true);
        }
        this.mLocalTimerExecuteContext = null;
    }

    public void addTimerWithTimerModel(MeshTimerModel meshTimerModel, ITimerActionCallback<List<MeshTimerModel>> iTimerActionCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "addTimerWithTimerModel() called, tobeAddTimerModel: " + JSON.toJSONString(meshTimerModel));
        if (preCheckEnvBeforeConfigTimer(iTimerActionCallback)) {
            List<DeviceTimerAttributeModel.ValueItem> value = this.mDeviceTimerAttribute.getValue();
            Iterator<DeviceTimerAttributeModel.ValueItem> it = value.iterator();
            byte b2 = 0;
            while (it.hasNext() && it.next().checkIsSet()) {
                b2 = (byte) (b2 + 1);
            }
            if (b2 >= this.mLimitSize) {
                notifyFailed(iTimerActionCallback, -3, "The timer is full and can no longer be added");
                return;
            }
            DeviceTimerAttributeModel.ValueItem valueItem = value.get(b2);
            DeviceTimerAttributeModel.ValueItem attributeModel = meshTimerModel.toAttributeModel();
            attributeModel.E = TimerEnableType.TIMER_ENABLE.getTypeValue();
            value.set(b2, attributeModel);
            realSetDeviceTimer(value, b2, valueItem, 0, iTimerActionCallback);
        }
    }

    public void deleteTimerWithTimerModel(MeshTimerModel meshTimerModel, ITimerActionCallback<List<MeshTimerModel>> iTimerActionCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "deleteTimerWithTimerModel() called, tobeDeleteTimerModel: " + JSON.toJSONString(meshTimerModel));
        if (preCheckEnvBeforeConfigTimer(iTimerActionCallback)) {
            try {
                int i2 = Integer.parseInt(meshTimerModel.getTimerID());
                List<DeviceTimerAttributeModel.ValueItem> value = this.mDeviceTimerAttribute.getValue();
                if (i2 >= value.size()) {
                    notifyFailed(iTimerActionCallback, -4, "invalid timerID");
                    return;
                }
                DeviceTimerAttributeModel.ValueItem valueItem = new DeviceTimerAttributeModel.ValueItem(value.get(i2));
                value.get(i2).Y = 0;
                realSetDeviceTimer(value, i2, valueItem, 1, iTimerActionCallback);
            } catch (NumberFormatException unused) {
                notifyFailed(iTimerActionCallback, -4, "invalid timerID");
            }
        }
    }

    public void editTimerWithTimerModel(MeshTimerModel meshTimerModel, ITimerActionCallback<List<MeshTimerModel>> iTimerActionCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "editTimerWithTimerModel() called, tobeAddTimerModel: " + JSON.toJSONString(meshTimerModel));
        if (preCheckEnvBeforeConfigTimer(iTimerActionCallback)) {
            try {
                int i2 = Integer.parseInt(meshTimerModel.getTimerID());
                List<DeviceTimerAttributeModel.ValueItem> value = this.mDeviceTimerAttribute.getValue();
                if (i2 < value.size() && i2 >= 0) {
                    DeviceTimerAttributeModel.ValueItem valueItem = value.get(i2);
                    value.set(i2, meshTimerModel.toAttributeModel());
                    realSetDeviceTimer(value, i2, valueItem, 2, iTimerActionCallback);
                    return;
                }
                notifyFailed(iTimerActionCallback, -4, "invalid timerID");
            } catch (NumberFormatException unused) {
                notifyFailed(iTimerActionCallback, -4, "invalid timerID");
            }
        }
    }

    public void getDeviceTimerList(final ITimerActionCallback<List<MeshTimerModel>> iTimerActionCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.mLimitSize != 0) {
            realGetDeviceTimerList(iTimerActionCallback);
        } else {
            getTimerLimitSize(new ITimerActionCallback<Integer>() { // from class: com.aliyun.alink.linksdk.tmp.timing.MeshTimerTransaction.4
                @Override // com.aliyun.alink.linksdk.tmp.timing.ITimerActionCallback
                public void onFailure(int i2, String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.e(MeshTimerTransaction.TAG, "On failed to get timer limit size, erromsg: " + str);
                    MeshTimerTransaction.this.notifyFailed(iTimerActionCallback, i2, str);
                }

                @Override // com.aliyun.alink.linksdk.tmp.timing.ITimerActionCallback
                public void onSuccess(Integer num) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.i(MeshTimerTransaction.TAG, "On successful to get timer limit size: " + num);
                    MeshTimerTransaction.this.mLimitSize = num.intValue();
                    MeshTimerTransaction.this.realGetDeviceTimerList(iTimerActionCallback);
                }
            });
        }
    }
}
