package com.aliyun.alink.linksdk.tmp.event;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.linksdk.tmp.api.OutputParams;
import com.aliyun.alink.linksdk.tmp.connect.d;
import com.aliyun.alink.linksdk.tmp.connect.e;
import com.aliyun.alink.linksdk.tmp.device.payload.ValueWrapper;
import com.aliyun.alink.linksdk.tmp.device.payload.event.EventNotifyPayload;
import com.aliyun.alink.linksdk.tmp.listener.IEventListener;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.aliyun.alink.linksdk.tmp.utils.LogCat;
import com.aliyun.alink.linksdk.tmp.utils.TextHelper;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tools.ALog;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/* loaded from: classes2.dex */
public class EventManager implements INotifyHandler {
    protected static final String TAG = "[Tmp]EventManager";
    protected static EventManager mInstance = new EventManager();
    protected Map<String, Map<String, List<EventMsg>>> mEventList = new ConcurrentHashMap();
    protected Map<Integer, Map<String, ConcurrentSkipListSet<String>>> mSubscribedEventList = new ConcurrentHashMap();
    protected Map<Integer, Map<String, Map<String, IEventListener>>> mEventListenerList = new ConcurrentHashMap();

    protected EventManager() {
    }

    public static EventManager getInstance() {
        return mInstance;
    }

    public boolean addEventListener(int i2, String str, String str2, IEventListener iEventListener) {
        ALog.d(TAG, "addEventListener deviceId:" + str + " eventName:" + str2 + " listener:" + iEventListener + " ownerId:" + i2);
        if (TextUtils.isEmpty(str2) || iEventListener == null) {
            ALog.e(TAG, "addEventListener eventName or listener empty");
            return false;
        }
        Map<String, Map<String, IEventListener>> concurrentHashMap = this.mEventListenerList.get(Integer.valueOf(i2));
        if (concurrentHashMap == null) {
            concurrentHashMap = new ConcurrentHashMap<>();
            this.mEventListenerList.put(Integer.valueOf(i2), concurrentHashMap);
        }
        Map<String, IEventListener> concurrentHashMap2 = concurrentHashMap.get(str);
        if (concurrentHashMap2 == null) {
            concurrentHashMap2 = new ConcurrentHashMap<>();
            concurrentHashMap.put(str, concurrentHashMap2);
        }
        concurrentHashMap2.put(str2, iEventListener);
        return true;
    }

    public boolean addSubscribedEvent(int i2, String str, String str2) {
        ALog.d(TAG, "addSubscribedEvent deviceId:" + str + " eventName:" + str2 + " ownerId:" + i2);
        Map<String, ConcurrentSkipListSet<String>> concurrentHashMap = this.mSubscribedEventList.get(Integer.valueOf(i2));
        if (concurrentHashMap == null) {
            concurrentHashMap = new ConcurrentHashMap<>();
            this.mSubscribedEventList.put(Integer.valueOf(i2), concurrentHashMap);
        }
        ConcurrentSkipListSet<String> concurrentSkipListSet = concurrentHashMap.get(str);
        if (concurrentSkipListSet == null) {
            concurrentSkipListSet = new ConcurrentSkipListSet<>();
            concurrentHashMap.put(str, concurrentSkipListSet);
        }
        return concurrentSkipListSet.add(str2);
    }

    public Set<String> getDevSubedEventList(int i2, String str) {
        ALog.d(TAG, "getDevSubedEventList deviceId:" + str + " ownerId:" + i2);
        Map<String, ConcurrentSkipListSet<String>> map = this.mSubscribedEventList.get(Integer.valueOf(i2));
        if (map == null) {
            return null;
        }
        return map.get(str);
    }

    public List<EventMsg> getEventMsgList() {
        ArrayList arrayList = new ArrayList();
        Iterator<Map.Entry<String, Map<String, List<EventMsg>>>> it = this.mEventList.entrySet().iterator();
        while (it.hasNext()) {
            Iterator<Map.Entry<String, List<EventMsg>>> it2 = it.next().getValue().entrySet().iterator();
            while (it2.hasNext()) {
                arrayList.addAll(it2.next().getValue());
            }
        }
        return arrayList;
    }

    public EventMsg insertEventMsg(String str, String str2, EventMsg eventMsg) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        LogCat.d(TAG, "insertEventMsg uri:" + str + " notifyData:" + str2);
        Map<String, List<EventMsg>> concurrentHashMap = this.mEventList.get(str);
        if (concurrentHashMap == null) {
            concurrentHashMap = new ConcurrentHashMap<>();
            this.mEventList.put(str, concurrentHashMap);
        }
        if (TextUtils.isEmpty(str2)) {
            List<EventMsg> arrayList = concurrentHashMap.get(str2);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                concurrentHashMap.put(str2, arrayList);
            }
            arrayList.add(eventMsg);
        }
        return eventMsg;
    }

    public boolean isEventSubscribed(int i2, String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ConcurrentSkipListSet<String> concurrentSkipListSet;
        ALog.d(TAG, "isEventSubscribed deviceId:" + str + " eventName:" + str2);
        Map<String, ConcurrentSkipListSet<String>> map = this.mSubscribedEventList.get(Integer.valueOf(i2));
        return (map == null || (concurrentSkipListSet = map.get(str)) == null || !concurrentSkipListSet.contains(str2)) ? false : true;
    }

    public boolean isOtherDeviceSubscribed(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Map.Entry<Integer, Map<String, ConcurrentSkipListSet<String>>> next;
        ConcurrentSkipListSet<String> concurrentSkipListSet;
        Iterator<Map.Entry<Integer, Map<String, ConcurrentSkipListSet<String>>>> it = this.mSubscribedEventList.entrySet().iterator();
        boolean zContains = false;
        while (it.hasNext() && ((next = it.next()) == null || (concurrentSkipListSet = next.getValue().get(str)) == null || !(zContains = concurrentSkipListSet.contains(str2)))) {
        }
        ALog.d(TAG, "isOtherDeviceSubscribed deviceId:" + str + " eventName:" + str2 + " isOtherDevSubscribed:" + zContains);
        return zContains;
    }

    protected void notifyListener(String str, String str2, String str3, OutputParams outputParams) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "notifyListener deviceId:" + str + " eventName:" + str2 + " topic:" + str3 + " outputParams:" + outputParams);
        Map<Integer, Map<String, Map<String, IEventListener>>> map = this.mEventListenerList;
        if (map == null || map.isEmpty()) {
            ALog.e(TAG, "mEventListenerList null or empty");
            return;
        }
        for (Map.Entry<Integer, Map<String, Map<String, IEventListener>>> entry : this.mEventListenerList.entrySet()) {
            Map<String, IEventListener> map2 = entry.getValue().get(str);
            if (map2 == null) {
                ALog.e(TAG, "notifyListener not register ownerid:" + entry.getKey() + " deviceId:" + str);
            } else {
                IEventListener iEventListener = map2.get(str2);
                if (iEventListener != null) {
                    ALog.d(TAG, "notifyListener listener ownerid:" + entry.getKey() + " deviceId:" + str + " eventName:" + str2 + " listener:" + iEventListener);
                    iEventListener.onMessage(str2, str3, outputParams);
                } else {
                    ALog.e(TAG, "notifyListener null listener ownerid:" + entry.getKey() + " deviceId:" + str + " eventName:" + str2);
                }
            }
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.event.INotifyHandler
    public void onMessage(d dVar, e eVar) {
        OutputParams outputParams;
        if (dVar == null || eVar == null) {
            LogCat.e(TAG, "onMessage empty");
            return;
        }
        if (!eVar.b()) {
            LogCat.e(TAG, "onMessage isSuccess false");
            return;
        }
        try {
            JSONObject object = JSON.parseObject(eVar.e());
            if (object == null) {
                LogCat.e(TAG, "onMessage parseObject failed");
                return;
            }
            String string = object.getString("method");
            String strD = dVar.d();
            String strQueryEventIdentifier = TextHelper.queryEventIdentifier(string);
            if (TextUtils.isEmpty(strQueryEventIdentifier) && !TextUtils.isEmpty(strD)) {
                strQueryEventIdentifier = strD.substring(strD.lastIndexOf("/") + 1);
            }
            String strCombineStr = TextHelper.combineStr(dVar.e(), dVar.g());
            ALog.d(TAG, "onMessage eventIdentifier:" + strQueryEventIdentifier + " method:" + string + " topic:" + strD + " deviceId:" + strCombineStr);
            if (TextUtils.isEmpty(string) || !string.contains(TmpConstant.METHOD_PROPERTY_POST)) {
                EventNotifyPayload eventNotifyPayload = (EventNotifyPayload) GsonUtils.fromJson(eVar.e(), new TypeToken<EventNotifyPayload>() { // from class: com.aliyun.alink.linksdk.tmp.event.EventManager.1
                }.getType());
                if (eventNotifyPayload == null) {
                    LogCat.e(TAG, "onMessage notifypayload error");
                    return;
                }
                outputParams = new OutputParams(eventNotifyPayload.getParams());
            } else {
                outputParams = new OutputParams("params", new ValueWrapper(object.getJSONObject("params")));
            }
            try {
                notifyListener(strCombineStr, strQueryEventIdentifier, strD, outputParams);
            } catch (Exception e2) {
                ALog.e(TAG, "notifyListener error:" + e2.toString());
            }
        } catch (Exception e3) {
            ALog.w(TAG, e3.toString());
        }
    }

    public boolean removeEventListener(int i2, String str, String str2) {
        Map<String, IEventListener> map;
        ALog.d(TAG, "removeEventListener deviceId:" + str + " eventName:" + str2 + " ownerId:" + i2);
        Map<String, Map<String, IEventListener>> map2 = this.mEventListenerList.get(Integer.valueOf(i2));
        if (map2 == null || (map = map2.get(str)) == null) {
            return true;
        }
        map.remove(str2);
        if (map.isEmpty()) {
            map2.remove(str);
        }
        if (map2.isEmpty()) {
            this.mEventListenerList.remove(Integer.valueOf(i2));
        }
        return true;
    }

    public boolean removeSubscribedEvent(int i2, String str, String str2) {
        ConcurrentSkipListSet<String> concurrentSkipListSet;
        ALog.d(TAG, "removeSubscribedEvent deviceId:" + str + " eventName:" + str2 + " ownerId:" + i2);
        Map<String, ConcurrentSkipListSet<String>> map = this.mSubscribedEventList.get(Integer.valueOf(i2));
        if (map == null || (concurrentSkipListSet = map.get(str)) == null) {
            return true;
        }
        return concurrentSkipListSet.remove(str2);
    }

    public List<EventMsg> getEventMsgList(String str) {
        Map<String, List<EventMsg>> map;
        ArrayList arrayList = new ArrayList();
        if (!TextUtils.isEmpty(str) && (map = this.mEventList.get(str)) != null && !map.isEmpty()) {
            Iterator<Map.Entry<String, List<EventMsg>>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                arrayList.addAll(it.next().getValue());
            }
        }
        return arrayList;
    }
}
