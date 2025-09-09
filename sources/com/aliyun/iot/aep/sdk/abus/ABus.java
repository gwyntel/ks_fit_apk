package com.aliyun.iot.aep.sdk.abus;

import android.app.Fragment;
import android.util.SparseArray;
import com.aliyun.iot.aep.sdk.abus.utils.ALog;
import de.greenrobot.event.EventBus;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes3.dex */
public class ABus implements IBus {
    private static final String TAG = "ABus";
    private SparseArray<ChannelInfo> channelMap = null;
    private SparseArray<CopyOnWriteArrayList<Object>> delayEvents = null;
    private Map<Object, CopyOnWriteArrayList<Integer>> eventToChannelsMap = null;
    private SparseArray<Boolean> channelBlockMap = null;

    private static final class ChannelInfo {
        boolean blocked = false;
        EventBus channel;
        int channelId;
        HashMap<Object, CopyOnWriteArrayList<Class<? extends Object>>> listenerToEventMap;

        public ChannelInfo(int i2) {
            this.channel = null;
            this.listenerToEventMap = null;
            this.channelId = i2;
            this.channel = new EventBus();
            this.listenerToEventMap = new HashMap<>();
        }
    }

    private synchronized void _attachListener(int i2, Object obj, String str, Class<? extends Object> cls) {
        if (obj != null && str != null) {
            try {
                if (str.trim().length() > 0 && i2 != 0 && cls != null) {
                    if (this.channelMap == null) {
                        this.channelMap = new SparseArray<>();
                    }
                    ChannelInfo channelInfo = this.channelMap.get(i2);
                    if (channelInfo == null) {
                        channelInfo = obj instanceof Fragment ? new ChannelInfo(i2) : new ChannelInfo(i2);
                        channelInfo.channel.register(obj, str, cls, new Class[0]);
                        this.channelMap.put(i2, channelInfo);
                    } else {
                        channelInfo.channel.register(obj, str, cls, new Class[0]);
                    }
                    CopyOnWriteArrayList<Class<? extends Object>> copyOnWriteArrayList = channelInfo.listenerToEventMap.get(obj);
                    if (copyOnWriteArrayList != null) {
                        Iterator<Class<? extends Object>> it = copyOnWriteArrayList.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                copyOnWriteArrayList.add(cls);
                                break;
                            } else if (it.next() == cls) {
                                ALog.w(TAG, obj.getClass() + " already registered to event " + cls);
                                break;
                            }
                        }
                    } else {
                        CopyOnWriteArrayList<Class<? extends Object>> copyOnWriteArrayList2 = new CopyOnWriteArrayList<>();
                        copyOnWriteArrayList2.add(cls);
                        channelInfo.listenerToEventMap.put(obj, copyOnWriteArrayList2);
                    }
                    return;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        ALog.w(TAG, "_attachListener(): ERROR: bad parameters");
    }

    private synchronized void _detachListener(int i2, Object obj, Class<? extends Object> cls) {
        try {
            if (i2 == 0 || obj == null) {
                ALog.w(TAG, "_dettachListener(): ERROR: bad parameters");
                return;
            }
            ChannelInfo channelInfo = this.channelMap.get(i2);
            if (channelInfo == null) {
                return;
            }
            if (cls != null) {
                channelInfo.channel.unregister(obj, cls);
                CopyOnWriteArrayList<Class<? extends Object>> copyOnWriteArrayList = channelInfo.listenerToEventMap.get(obj);
                if (copyOnWriteArrayList != null) {
                    copyOnWriteArrayList.remove(cls);
                    if (copyOnWriteArrayList.size() == 0) {
                        channelInfo.listenerToEventMap.remove(obj);
                    }
                } else {
                    channelInfo.listenerToEventMap.remove(obj);
                }
            } else {
                channelInfo.channel.unregister(obj);
                channelInfo.listenerToEventMap.remove(obj);
            }
            if (channelInfo.listenerToEventMap.isEmpty()) {
                this.channelMap.remove(i2);
            }
            removeDelayChannel(i2);
            removeChannelBlock(i2);
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // com.aliyun.iot.aep.sdk.abus.IBus
    public void attachListener(int i2, Object obj, String str, Class<? extends Object> cls) {
        try {
            _attachListener(i2, obj, str, cls);
        } catch (Exception e2) {
            ALog.e(TAG, "attachListener(): " + e2.getMessage());
        }
    }

    @Override // com.aliyun.iot.aep.sdk.abus.IBus
    public void blockChannel(int i2, boolean z2) {
        SparseArray<ChannelInfo> sparseArray = this.channelMap;
        ChannelInfo channelInfo = sparseArray != null ? sparseArray.get(i2) : null;
        if (channelInfo != null) {
            channelInfo.blocked = z2;
            if (z2) {
                setChannelBlock(i2, true);
            } else {
                setChannelBlock(i2, false);
            }
            postDelayEvents(i2);
        }
    }

    @Override // com.aliyun.iot.aep.sdk.abus.IBus
    public void cancelChannel(int i2) {
        SparseArray<ChannelInfo> sparseArray = this.channelMap;
        if (sparseArray != null) {
            sparseArray.remove(i2);
        }
    }

    public void delayEvent(int i2, Object obj) {
        if (i2 == 0 || obj == null) {
            ALog.w(TAG, "delayEvent(): ERROR: bad parameters");
            return;
        }
        if (this.delayEvents == null) {
            this.delayEvents = new SparseArray<>();
        }
        CopyOnWriteArrayList<Object> copyOnWriteArrayList = this.delayEvents.get(i2);
        if (copyOnWriteArrayList == null) {
            copyOnWriteArrayList = new CopyOnWriteArrayList<>();
            this.delayEvents.put(i2, copyOnWriteArrayList);
        } else if (!copyOnWriteArrayList.isEmpty() && copyOnWriteArrayList.contains(obj)) {
            copyOnWriteArrayList.remove(obj);
        }
        copyOnWriteArrayList.add(obj);
    }

    @Override // com.aliyun.iot.aep.sdk.abus.IBus
    public void detachListener(int i2, Object obj) {
        try {
            _detachListener(i2, obj, null);
        } catch (Exception e2) {
            ALog.e(TAG, "detachListener(): " + e2.getMessage());
        }
    }

    public boolean getChannelBlock(int i2) {
        SparseArray<Boolean> sparseArray;
        if (i2 == 0 || (sparseArray = this.channelBlockMap) == null || sparseArray.size() <= 0) {
            return false;
        }
        return this.channelBlockMap.get(i2, Boolean.FALSE).booleanValue();
    }

    public void postDelayEvents(int i2) {
        SparseArray<CopyOnWriteArrayList<Object>> sparseArray;
        CopyOnWriteArrayList<Object> copyOnWriteArrayList;
        if (i2 == 0 || getChannelBlock(i2) || (sparseArray = this.delayEvents) == null || sparseArray.size() < 1 || (copyOnWriteArrayList = this.delayEvents.get(i2)) == null || copyOnWriteArrayList.isEmpty()) {
            return;
        }
        Iterator<Object> it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            postEvent(i2, it.next());
        }
        copyOnWriteArrayList.clear();
        this.delayEvents.remove(i2);
    }

    @Override // com.aliyun.iot.aep.sdk.abus.IBus
    public void postEvent(int i2, Object obj) {
        if (i2 == 0 || obj == null) {
            ALog.w(TAG, "postEvent(channelId, eventClass): 0 == channelId || null == eventClass");
            return;
        }
        SparseArray<ChannelInfo> sparseArray = this.channelMap;
        if (sparseArray == null || sparseArray.size() <= 0) {
            ALog.d(TAG, "postEvent(channelId, eventClass): no channel");
            return;
        }
        ChannelInfo channelInfo = this.channelMap.get(i2);
        if (channelInfo == null) {
            ALog.d(TAG, "postEvent(channelId, eventClass): no the channel: " + i2);
            return;
        }
        if (!channelInfo.blocked) {
            channelInfo.channel.post(obj);
            return;
        }
        delayEvent(i2, obj);
        ALog.d(TAG, "postEvent(channelId, eventClass): the " + i2 + " channel is blocked");
    }

    public void removeChannelBlock(int i2) {
        SparseArray<Boolean> sparseArray = this.channelBlockMap;
        if (sparseArray != null) {
            sparseArray.remove(i2);
        }
    }

    public void removeDelayChannel(int i2) {
        SparseArray<CopyOnWriteArrayList<Object>> sparseArray = this.delayEvents;
        if (sparseArray == null || sparseArray.size() < 1) {
            return;
        }
        this.delayEvents.remove(i2);
    }

    public void setChannelBlock(int i2, boolean z2) {
        if (this.channelBlockMap == null) {
            this.channelBlockMap = new SparseArray<>();
        }
        this.channelBlockMap.put(i2, Boolean.valueOf(z2));
    }

    @Override // com.aliyun.iot.aep.sdk.abus.IBus
    public void detachListener(int i2, Object obj, Class<? extends Object> cls) {
        try {
            _detachListener(i2, obj, cls);
        } catch (Exception e2) {
            ALog.e(TAG, "detachListener(): " + e2.getMessage());
        }
    }
}
