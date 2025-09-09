package com.luck.picture.lib.config;

import java.util.LinkedList;

/* loaded from: classes4.dex */
public class SelectorProviders {
    private static volatile SelectorProviders selectorProviders;
    private final LinkedList<SelectorConfig> selectionConfigsQueue = new LinkedList<>();

    public static SelectorProviders getInstance() {
        if (selectorProviders == null) {
            synchronized (SelectorProviders.class) {
                try {
                    if (selectorProviders == null) {
                        selectorProviders = new SelectorProviders();
                    }
                } finally {
                }
            }
        }
        return selectorProviders;
    }

    public void addSelectorConfigQueue(SelectorConfig selectorConfig) {
        this.selectionConfigsQueue.add(selectorConfig);
    }

    public void destroy() {
        SelectorConfig selectorConfig = getSelectorConfig();
        if (selectorConfig != null) {
            selectorConfig.destroy();
            this.selectionConfigsQueue.remove(selectorConfig);
        }
    }

    public SelectorConfig getSelectorConfig() {
        return this.selectionConfigsQueue.size() > 0 ? this.selectionConfigsQueue.getLast() : new SelectorConfig();
    }

    public void reset() {
        for (int i2 = 0; i2 < this.selectionConfigsQueue.size(); i2++) {
            SelectorConfig selectorConfig = this.selectionConfigsQueue.get(i2);
            if (selectorConfig != null) {
                selectorConfig.destroy();
            }
        }
        this.selectionConfigsQueue.clear();
    }
}
