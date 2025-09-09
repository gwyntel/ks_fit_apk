package com.alibaba.fplayer.flutter_aliplayer;

import io.flutter.plugin.common.BasicMessageChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes2.dex */
public class AliChannelPool {
    private static volatile AliChannelPool instance;
    private final Map<String, BasicMessageChannel<String>> channels = new HashMap();

    private AliChannelPool() {
    }

    public static synchronized AliChannelPool getInstance() {
        try {
            if (instance == null) {
                instance = new AliChannelPool();
            }
        } catch (Throwable th) {
            throw th;
        }
        return instance;
    }

    public boolean addChannel(String str, BasicMessageChannel<String> basicMessageChannel) {
        if (this.channels.containsKey(str)) {
            return false;
        }
        this.channels.put(str, basicMessageChannel);
        return true;
    }

    public BasicMessageChannel<String> channelForKey(String str) {
        return this.channels.get(str);
    }

    public void clear() {
        Iterator<Map.Entry<String, BasicMessageChannel<String>>> it = this.channels.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue();
        }
        this.channels.clear();
    }

    public boolean containChannel(String str) {
        return this.channels.containsKey(str);
    }

    public boolean removeChannel(String str) {
        if (!this.channels.containsKey(str)) {
            return false;
        }
        channelForKey(str);
        this.channels.remove(str);
        return true;
    }
}
