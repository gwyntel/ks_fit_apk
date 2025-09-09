package com.aliyun.iot.link.ui.component.wheelview.source;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface DataHolder<T> {

    public static class EmptyHolder<T> implements DataHolder<T> {
        @Override // com.aliyun.iot.link.ui.component.wheelview.source.DataHolder
        public T get(int i2) {
            return null;
        }

        @Override // com.aliyun.iot.link.ui.component.wheelview.source.DataHolder
        public boolean isEmpty() {
            return true;
        }

        @Override // com.aliyun.iot.link.ui.component.wheelview.source.DataHolder
        public int size() {
            return 0;
        }

        @Override // com.aliyun.iot.link.ui.component.wheelview.source.DataHolder
        public List<T> toList() {
            return new ArrayList();
        }
    }

    T get(int i2);

    boolean isEmpty();

    int size();

    List<T> toList();
}
